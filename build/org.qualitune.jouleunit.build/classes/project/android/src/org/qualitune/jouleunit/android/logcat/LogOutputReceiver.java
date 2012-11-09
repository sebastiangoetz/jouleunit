package org.qualitune.jouleunit.android.logcat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator;
import org.qualitune.jouleunit.coordinator.TestCaseProfile;
import org.qualitune.jouleunit.coordinator.TestSuiteProfile;

/**
 * Responsible to receive logged events from the device under test and handle
 * the important device to derive the test and profiling results.
 * 
 * @author Claas Wilke
 */
public class LogOutputReceiver extends MultiLineReceiver {

	/** {@link DateFormat} for log cat to time stamp conversion. */
	private final static DateFormat DATE_FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	/** Prefix of Log Cat messages that indicate a test cases run failed. */
	private static final String LOG_CAT_FAILED_PREFIX = "failed: ";

	/** Prefix of Log Cat messages that indicate a test cases run finished. */
	private static final String LOG_CAT_FINISHED_PREFIX = "finished: ";

	/** Prefix of Log Cat messages that indicate a test cases run started. */
	private static final String LOG_CAT_STARTED_PREFIX = "started: ";

	/** Tag for Log Cat messages that shall be filtered for test run evaluation. */
	private static final String LOG_CAT_FILTER_TAG = "TestRunner";

	/** Tag for Log Cat messages that have been sent by the HW service. */
	private static final String LOG_CAT_HARDWARE_SERVICE_TAG = "org.qualitune.jouleunit.android.hwservice";

	/** Tag for Log Cat messages that shall be filtered for test run evaluation. */
	private static final String LOG_CAT_TIME_SYNC_TAG = "TIME_SYNC";

	/** Whether or not this {@link LogOutputReceiver} has been cancelled. */
	private boolean isCancelled = false;

	/**
	 * A {@link Map} containing {@link TestCaseProfile}s for executed test cases
	 * received by this {@link LogOutputReceiver}.
	 */
	private Map<String, TestCaseProfile> mExecutedTestCases;

	/**
	 * The time stamp in millis of the last request for a time stamp to
	 * synchronize clocks.
	 */
	private long mLastRequestForTimeStamp = 0l;

	/**
	 * The {@link AbstractAndroidJouleUnitCoordinator} that uses this
	 * {@link LogOutputReceiver}.
	 */
	private AbstractAndroidJouleUnitCoordinator mCoordinator;

	/** The {@link TestSuiteProfile} to store the received events. */
	private TestSuiteProfile mTestSuiteProfile;

	/**
	 * Creates a new {@link LogOutputReceiver}.
	 * 
	 * @param profile
	 *            The {@link TestSuiteProfile} to store the received events.
	 * @param coordinator
	 *            The {@link AbstractAndroidJouleUnitCoordinator} that uses this
	 *            {@link LogOutputReceiver}.
	 */
	public LogOutputReceiver(TestSuiteProfile profile,
			AbstractAndroidJouleUnitCoordinator coordinator) {
		super();

		setTrimLine(false);

		mExecutedTestCases = new HashMap<String, TestCaseProfile>();
		mTestSuiteProfile = profile;
		mCoordinator = coordinator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android.ddmlib.MultiLineReceiver#processNewLines(java.lang.String[])
	 */
	@Override
	public void processNewLines(String[] lines) {
		if (isCancelled == false) {

			/* Evaluate the logged events. */
			for (String line : lines) {

				/* Handle logged test events. */
				if (line.matches("^\\d\\d-\\d\\d\\s\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d\\sI/"
						+ LOG_CAT_FILTER_TAG + ".*")) {

					String msgMsg = line.substring(line.indexOf("):") + 3);
					String msgTime = line.substring(0, 18);

					if (msgMsg.startsWith(LOG_CAT_STARTED_PREFIX)) {
						msgMsg = msgMsg.substring(
								LOG_CAT_STARTED_PREFIX.length(),
								msgMsg.length());

						/* Create test case profile. */
						TestCaseProfile profile = new TestCaseProfile();
						profile.setId(msgMsg);
						mExecutedTestCases.put(msgMsg, profile);

						profile.setStartTime(logDateToMillis(msgTime));
					}

					else if (msgMsg.startsWith(LOG_CAT_FAILED_PREFIX)) {
						msgMsg = msgMsg
								.substring(LOG_CAT_FAILED_PREFIX.length(),
										msgMsg.length());

						TestCaseProfile profile = mExecutedTestCases
								.get(msgMsg);

						if (null != profile) {
							profile.setEndTime(logDateToMillis(msgTime));
							profile.setFailed(true);
							mTestSuiteProfile.addTestCase(profile);
							mCoordinator.reportError(msgMsg + " failed.");
						} else
							mCoordinator
									.reportError("WARNING: Got failure of test case without start: "
											+ msgMsg);
					}

					else if (msgMsg.startsWith(LOG_CAT_FINISHED_PREFIX)) {
						msgMsg = msgMsg.substring(
								LOG_CAT_FINISHED_PREFIX.length(),
								msgMsg.length());

						TestCaseProfile profile = mExecutedTestCases
								.remove(msgMsg);

						if (null != profile) {
							profile.setEndTime(logDateToMillis(msgTime));
							mTestSuiteProfile.addTestCase(profile);
							mCoordinator.reportProgress("  " + msgMsg
									+ " finished.");
						} else
							mCoordinator
									.reportError("WARNING: Got end of test case without start: "
											+ msgMsg);
					}
					// no else.
				}

				/* Handle logged HW events. */
				else if (line
						.matches("^\\d\\d-\\d\\d\\s\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d\\sI/"
								+ LOG_CAT_HARDWARE_SERVICE_TAG + ".*")) {

					String msgMsg = line.substring(line.indexOf("):") + 3);
					String msgTime = line.substring(0, 18);

					if (msgMsg.contains(":")) {
						String hardwareType = msgMsg.substring(0,
								msgMsg.indexOf(":") - 1);
						String value = msgMsg.substring(
								msgMsg.indexOf(":") + 1, msgMsg.length());

						if (hardwareType.equals("cpu_freq")) {
							String[] frequencyLogs = value.trim().split(",");
							Long[] frequencies = new Long[frequencyLogs.length];

							for (int index = 0; index < frequencyLogs.length; index++)
								frequencies[index] = Long
										.parseLong(frequencyLogs[index].trim());
							// end for.

							mTestSuiteProfile.addCpuFrequencies(
									logDateToMillis(msgTime), frequencies);
						}

						else if (hardwareType.equals("wifi_traffic"))
							mTestSuiteProfile.addWiFiTraffic(
									logDateToMillis(msgTime),
									Long.parseLong(value.trim()));

						else if (hardwareType.equals("lcd_brightness"))
							mTestSuiteProfile.addLcdBrightness(
									logDateToMillis(msgTime),
									Long.parseLong(value.trim()));
						// no else.
					}
					// no else.

					// no else.
				}

				/* Handle logged time sync events. */
				else if (line
						.matches("^\\d\\d-\\d\\d\\s\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d\\sD/"
								+ LOG_CAT_TIME_SYNC_TAG + ".*")) {
					long lastPossibleTime = System.currentTimeMillis();

					/* Set the offset. */
					long timeOffset = Long.parseLong(line.substring(
							line.indexOf("Offset:") + 7, line.indexOf(","))
							.trim());
					mTestSuiteProfile.setPutTimeStampOffset(timeOffset);

					mCoordinator.reportProgress("Computed time offset: "
							+ timeOffset + " millis.");

					correctComputedTimeStamp(
							line,
							mLastRequestForTimeStamp
									+ mTestSuiteProfile.getTrTimeStampOffset(),
							lastPossibleTime
									+ mTestSuiteProfile.getTrTimeStampOffset());
				}
				// no else.
			}
			// end for.
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.ddmlib.IShellOutputReceiver#isCancelled()
	 */
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * Cancels this {@link LogOutputReceiver}.
	 */
	public void cancel() {
		this.isCancelled = true;
	}

	/**
	 * Sets the time stamp in millis of the last request for a time stamp to
	 * synchronize clocks (used to check whether or not the received offset is
	 * sensible).
	 * 
	 * @param timeStamp
	 *            The time stamp of the time the request has been issued.
	 */
	public void setLastRequestForTimeStamp(long timeStamp) {
		mLastRequestForTimeStamp = timeStamp;
	}

	/**
	 * Helper method to probably correct the received time synchronization
	 * offset.
	 * 
	 * @param line
	 *            The current line received (the log message).
	 * @param firstPossibleTime
	 *            The first possible time the time stamp response could have
	 *            been logged at.
	 * @param lastPossibleTime
	 *            The last possible time the time stamp response could have been
	 *            logged at.
	 */
	private void correctComputedTimeStamp(String line, long firstPossibleTime,
			long lastPossibleTime) {

		/* Check the offset. */
		String msgTime = line.substring(0, 18);
		long computedTimeOfResponse = logDateToMillis(msgTime);
		computedTimeOfResponse = computedTimeOfResponse
				+ mTestSuiteProfile.getPutTimeStampOffset();

		if (firstPossibleTime != 0
				&& computedTimeOfResponse < firstPossibleTime) {
			mTestSuiteProfile.setPutTimeStampOffset(mTestSuiteProfile
					.getPutTimeStampOffset()
					+ firstPossibleTime
					- computedTimeOfResponse);
			mCoordinator
					.reportError("WARNING: Fixed time synchronization offset by +"
							+ (firstPossibleTime - computedTimeOfResponse)
							+ " millis. Are you sure, that youre device under test has a valid Internet connection?");
		}

		else if (computedTimeOfResponse > lastPossibleTime) {
			mTestSuiteProfile.setPutTimeStampOffset(mTestSuiteProfile
					.getPutTimeStampOffset()
					- computedTimeOfResponse
					- lastPossibleTime);
			mCoordinator
					.reportError("WARNING: Fixed time synchronization offset by -"
							+ (computedTimeOfResponse - lastPossibleTime)
							+ " millis. Are you sure, that youre device under test has a valid Internet connection?");
		}
		// no else.
	}

	/**
	 * Helper method that transforms a date from Log Cat into a time stamp
	 * (since 1970-1-1).
	 * 
	 * @param logDate
	 *            The log date as a {@link String} as given by log cat.
	 * @return The given date as a long.
	 */
	private long logDateToMillis(String logDate) {

		try {
			@SuppressWarnings("deprecation")
			long millis = DATE_FORMATTER.parse(
					(new java.util.Date().getYear() + 1900) + "-" + logDate)
					.getTime();
			return millis;
		}

		catch (ParseException e) {
			mCoordinator
					.reportError("Error during converting Log Cat date into time stamp.");
			e.printStackTrace();
			return -1;
		}
	}
}
