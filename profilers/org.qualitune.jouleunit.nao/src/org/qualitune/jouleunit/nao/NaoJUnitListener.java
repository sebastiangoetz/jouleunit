package org.qualitune.jouleunit.nao;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.qualitune.jouleunit.coordinator.TestCaseProfile;
import org.qualitune.jouleunit.coordinator.TestSuiteProfile;

/**
 * {@link RunListener} implementation to log the execution of NAO test cases.
 * 
 * @author Claas Wilke
 */
public class NaoJUnitListener extends RunListener {

	/** The {@link TestSuiteProfile} to log the listened events to. */
	private TestSuiteProfile tsProfile;

	private Map<String, TestCaseProfile> startedTestCases = new HashMap<String, TestCaseProfile>();

	/**
	 * Creates a new {@link NaoJUnitListener}.
	 * 
	 * @param tsProfile
	 *            The {@link TestSuiteProfile} to log the listened events to.
	 */
	public NaoJUnitListener(TestSuiteProfile tsProfile) {
		this.tsProfile = tsProfile;
	}

	@Override
	public void testFailure(Failure failure) throws Exception {

		/* Retrieve a TestCaseProfile and log the failure. */
		String testCaseName = failure.getDescription().getClassName() + "."
				+ failure.getDescription().getMethodName();
		TestCaseProfile profile = startedTestCases.get(testCaseName);

		if (null != profile) {
			profile.setFailed(true);
		}
		// no else.

		super.testFailure(failure);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.junit.runner.notification.RunListener#testFinished(org.junit.runner
	 * .Description)
	 */
	@Override
	public void testFinished(Description description) throws Exception {

		/* Retrieve a TestCaseProfile and log the end time. */
		long endTime = System.currentTimeMillis();
		String testCaseName = description.getClassName() + "."
				+ description.getMethodName();
		TestCaseProfile profile = startedTestCases.get(testCaseName);

		if (null != profile) {
			profile.setEndTime(endTime);
			tsProfile.addTestCase(profile);
			startedTestCases.remove(testCaseName);
		}
		// no else.

		super.testFinished(description);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.junit.runner.notification.RunListener#testStarted(org.junit.runner
	 * .Description)
	 */
	@Override
	public void testStarted(Description description) throws Exception {

		/* Create a new TestCaseProfile and log the start time. */
		long startTime = System.currentTimeMillis();
		String testCaseName = description.getClassName() + "."
				+ description.getMethodName();
		TestCaseProfile profile = new TestCaseProfile();
		profile.setId(testCaseName);
		profile.setStartTime(startTime);

		startedTestCases.put(testCaseName, profile);

		super.testStarted(description);
	}
}
