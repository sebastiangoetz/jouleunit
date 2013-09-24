package org.qualitune.jouleunit.android.logcat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator;
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

	/** Tag for Log Cat messages that shall be filtered for test run evaluation. */
	public static final String LOG_CAT_FILTER_TAG = "TestRunner";

	/** Tag for Log Cat messages that have been sent by the HW service. */
	private static final String LOG_CAT_HARDWARE_SERVICE_TAG = "org.qualitune.jouleunit.android.hwservice";

	/** Tag for Log Cat messages that shall be filtered for test run evaluation. */
	private static final String LOG_CAT_TIME_SYNC_TAG = "TIME_SYNC";

	/** Whether or not this {@link LogOutputReceiver} has been cancelled. */
	private boolean isCancelled = false;

	/**
	 * The time stamp in millis of the last request for a time stamp to
	 * synchronize clocks.
	 */
	private long mLastRequestForTimeStamp = 0l;

	/** The {@link ILogLineProcessor}s of this {@link LogOutputReceiver}. */
	private List<ILogLineProcessor> mLineProcessors = new ArrayList<ILogLineProcessor>();

	/**
	 * The {@link AbstractAndroidJouleUnitCoordinator} that uses this
	 * {@link LogOutputReceiver}.
	 */
	protected AbstractAndroidJouleUnitCoordinator mCoordinator;

	/** The {@link TestSuiteProfile} to store the received events. */
	protected TestSuiteProfile mTestSuiteProfile;

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

		mTestSuiteProfile = profile;
		mCoordinator = coordinator;
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

				boolean processed = false;

				for (ILogLineProcessor processor : mLineProcessors) {
					processed = processor.processLine(line);

					if (processed)
						break;
					// no else.
				}
				// end for.

				/* Handle logged HW events. */
				if (!processed
						&& line.matches("^\\d\\d-\\d\\d\\s\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d\\sI/"
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
				else if (!processed
						&& line.matches("^\\d\\d-\\d\\d\\s\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d\\sD/"
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

	/**
	 * Adds a given {@link ILogLineProcessor} to this {@link LogOutputReceiver}.
	 * 
	 * @param processor
	 *            The {@link ILogLineProcessor} to be added.
	 * @return <code>true</code> if the given {@link ILogLineProcessor} has been
	 *         added.
	 */
	public boolean addLogLineProcessor(ILogLineProcessor processor) {
		if (!mLineProcessors.contains(processor))
			return mLineProcessors.add(processor);
		else
			return false;
	}

	/**
	 * Cancels this {@link LogOutputReceiver}.
	 */
	public void cancel() {
		this.isCancelled = true;
	}

	/**
	 * Removes an {@link ILogLineProcessor} from this {@link LogOutputReceiver}.
	 * 
	 * @param processor
	 *            The {@link ILogLineProcessor} to be removed.
	 * @return <code>true</code> if the {@link ILogLineProcessor} has been
	 *         removed.
	 */
	public boolean removeLogLineProcessor(ILogLineProcessor processor) {
		return mLineProcessors.remove(processor);
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
					- (computedTimeOfResponse
					- lastPossibleTime));
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
	protected long logDateToMillis(String logDate) {

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
