package org.qualitune.jouleunit.android.logcat;

import java.util.HashMap;
import java.util.Map;

import org.qualitune.jouleunit.coordinator.TestCaseProfile;

/**
 * {@link ILogLineProcessor} to received start and stop events of test cases.
 * 
 * @author Claas Wilke
 */
public class TestCaseLogLineProcessor implements ILogLineProcessor {

	/** Prefix of Log Cat messages that indicate a test cases run failed. */
	private static final String LOG_CAT_FAILED_PREFIX = "failed: ";

	/** Prefix of Log Cat messages that indicate a test cases run finished. */
	private static final String LOG_CAT_FINISHED_PREFIX = "finished: ";

	/** Prefix of Log Cat messages that indicate a test cases run started. */
	private static final String LOG_CAT_STARTED_PREFIX = "started: ";

	/**
	 * A {@link Map} containing {@link TestCaseProfile}s for executed test cases
	 * received by this {@link LogOutputReceiver}.
	 */
	private Map<String, TestCaseProfile> mExecutedTestCases = new HashMap<String, TestCaseProfile>();

	/** The {@link LogOutputReceiver} this {@link ILogLineProcessor} belongs to. */
	private LogOutputReceiver mReceiver;

	/**
	 * Creates a new {@link TestCaseLogLineProcessor}.
	 * 
	 * @param receiver
	 *            The {@link LogOutputReceiver} this {@link ILogLineProcessor}
	 *            belongs to.
	 */
	public TestCaseLogLineProcessor(LogOutputReceiver receiver) {
		mReceiver = receiver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.android.logcat.ILogLineProcessor#processLine(
	 * java.lang.String)
	 */
	public boolean processLine(String line) {

		boolean processed = false;

		/* Handle logged test events. */
		if (line.matches("^\\d\\d-\\d\\d\\s\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d\\sI/"
				+ LogOutputReceiver.LOG_CAT_FILTER_TAG + ".*")) {

			String msgMsg = line.substring(line.indexOf("):") + 3);
			String msgTime = line.substring(0, 18);

			if (msgMsg.startsWith(LOG_CAT_STARTED_PREFIX)) {
				msgMsg = msgMsg.substring(LOG_CAT_STARTED_PREFIX.length(),
						msgMsg.length());

				/* Create test case profile. */
				TestCaseProfile profile = new TestCaseProfile();

				if (msgMsg.contains("[")
						&& msgMsg.substring(msgMsg.indexOf("[")).contains("]")) {
					profile.setId(msgMsg.substring(0, msgMsg.indexOf("[")));
					profile.setTag(msgMsg.substring(msgMsg.indexOf("[") + 1,
							msgMsg.indexOf("]")));
				} else
					profile.setId(msgMsg);

				mExecutedTestCases.put(msgMsg, profile);

				profile.setStartTime(mReceiver.logDateToMillis(msgTime));
				processed = true;
			}

			else if (msgMsg.startsWith(LOG_CAT_FAILED_PREFIX)) {
				msgMsg = msgMsg.substring(LOG_CAT_FAILED_PREFIX.length(),
						msgMsg.length());

				TestCaseProfile profile = mExecutedTestCases.get(msgMsg);

				if (null != profile) {
					profile.setEndTime(mReceiver.logDateToMillis(msgTime));
					profile.setFailed(true);
					mReceiver.mTestSuiteProfile.addTestCase(profile);
					mReceiver.mCoordinator.reportError(msgMsg + " failed.");

					/*
					 * All other test cases that where started but not yet
					 * finished failed as well (represent nested test cases).
					 */
					for (String id : mExecutedTestCases.keySet())
						mExecutedTestCases.get(id).setFailed(true);
					// end for.
				} else
					mReceiver.mCoordinator
							.reportError("WARNING: Got failure of test case without start: "
									+ msgMsg);

				processed = true;
			}

			else if (msgMsg.startsWith(LOG_CAT_FINISHED_PREFIX)) {
				msgMsg = msgMsg.substring(LOG_CAT_FINISHED_PREFIX.length(),
						msgMsg.length());

				TestCaseProfile profile = mExecutedTestCases.remove(msgMsg);

				if (null != profile) {
					profile.setEndTime(mReceiver.logDateToMillis(msgTime));
					mReceiver.mTestSuiteProfile.addTestCase(profile);
					mReceiver.mCoordinator.reportProgress("  " + msgMsg
							+ " finished.");
				} else
					mReceiver.mCoordinator
							.reportError("WARNING: Got end of test case without start: "
									+ msgMsg);

				processed = true;
			}
			/* no else. */
		}
		// no else.

		return processed;
	}
}
