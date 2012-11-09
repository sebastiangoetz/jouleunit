package org.qualitune.jouleunit.coordinator;

import org.qualitune.jouleunit.ProfilingException;

/**
 * A default {@link JouleUnitCoordinator} implementation with default
 * implementations for most required methods.
 * 
 * @author Claas Wilke
 */
public abstract class AbstractJouleUnitCoordinator extends JouleUnitCoordinator {

	/**
	 * Uses std out to print the errors.
	 * 
	 * @see org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#reportError(java.lang.String)
	 */
	public void reportError(String msg) {
		System.out.println("Error: " + msg);
	}

	/**
	 * Uses std out to report the progress.
	 * 
	 * @see org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#reportProgress
	 *      (java.lang.String)
	 */
	public void reportProgress(String msg) {
		System.out.println(msg);
	}

	/**
	 * Just use the same offset as for the test runner. Assumes that the test
	 * events use timestamps from the test runner machine.
	 * 
	 * @see org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#computeDutTimestampOffset()
	 */
	protected void computeDutTimestampOffset() {
		testSuiteProfile.setPutTimeStampOffset(testSuiteProfile
				.getTrTimeStampOffset());
	}

	/**
	 * Assumes nothing has to be deployed.
	 * 
	 * @see org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#deployTestCases()
	 */
	protected void deployTestCases() throws ProfilingException {
		/* Remains empty. */
	}

	/**
	 * Assumes no hardware profiling is used.
	 * 
	 * @see org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#stopHardwareProfiling()
	 */
	protected void stopHardwareProfiling() {
		/* Remains empty. */
	}

	/**
	 * Assumes no hardware profiling is used.
	 * 
	 * @see org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#startHardwareProfiling()
	 */
	protected void startHardwareProfiling() {
		/* Remains empty. */
	}

	/**
	 * Assumes that nothing has to be undeployed.
	 * 
	 * @see org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#undeployTestCases()
	 */
	protected void undeployTestCases() throws ProfilingException {
		/* Remains empty. */
	}
}
