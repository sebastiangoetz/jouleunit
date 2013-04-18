package org.qualitune.jouleunit.coordinator;

import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.ProfilingException;

/**
 * Abstract implementation of the JouleUnit coordination layer. The
 * {@link JouleUnitCoordinator} is responsible to coordinate both test case
 * execution and profiling and aggregate their results.
 * 
 * @author Claas Wilke
 */
public abstract class JouleUnitCoordinator {

	/**
	 * Helper class to execute the test cases multiple times and supporting
	 * their abortion.
	 */
	private class TestRunner extends Thread {

		/** The number of test runs being performed. */
		private int runs;

		/**
		 * The number of seconds to profile idle power consumption before the
		 * execution of each test run.
		 */
		private int idleTime;

		/**
		 * Indicates whether or not this {@link TestRunner}'s run has been
		 * aborted or terminated.
		 */
		public boolean isAbortedOrTerminated;

		/**
		 * Crates a new {@link TestRunner}.
		 * 
		 * @param runs
		 * @param idleTime
		 */
		public TestRunner(int runs, int idleTime) {
			this.runs = runs;
			this.idleTime = idleTime;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			isAbortedOrTerminated = false;

			for (int run = 1; run <= runs; run++) {

				try {
					if (idleTime > 0)
						profileIdleConsumption(idleTime);
					// no else.

					reportProgress("Run #" + run + " of " + runs + " ...");
					runTestCases();
				}

				catch (ProfilingException e) {
					reportError(e);
					/* Abort the test runner. */
					isAbortedOrTerminated = true;
				}

				/* TODO Temporary export. How to obtain energy values, yet? */

				if (isAbortedOrTerminated)
					break;
				// no else.
			}
			// end for.

			isAbortedOrTerminated = true;
		}
	}

	/** The ID of the idle test case in the profiling results. */
	private static final String IDLE_TEST_CASE_ID = "org.jouleunit.test.Idle";

	/** The {@link JouleProfiler} used for profiling. */
	protected JouleProfiler profiler;
	
	/**
	 * The {@link TestSuiteProfile} of this {@link JouleUnitCoordinator} to
	 * store profiled power consumption, hardware information and executed test
	 * cases.
	 */
	protected TestSuiteProfile testSuiteProfile;

	/** The {@link TestRunner} used to run the test cases. */
	protected TestRunner testRunner;

	/**
	 * Aborts the {@link JouleUnitCoordinator} test case execution if it is
	 * currently performed. Otherwise, nothing will happen.
	 */
	public void abort() {
		if (null != testRunner)
			testRunner.isAbortedOrTerminated = true;
		// no else.
	}

	/**
	 * Helper method to report {@link Exception}s during profiling.
	 * 
	 * @param msg
	 *            A message explaining the cause of the {@link Exception}.
	 */
	public abstract void reportError(String msg);

	/**
	 * Helper method to report the current progress of this
	 * {@link JouleUnitCoordinator}.
	 * 
	 * @param msg
	 *            A {@link String} indicating the current progress.
	 */
	public abstract void reportProgress(String msg);

	/**
	 * Method to issue a test run including deployment of the test application,
	 * its execution as well as the parallel profiling of the device under test.
	 * 
	 * @param runs
	 *            The number of test runs to be profiled.
	 * @param idleTime
	 *            The number of seconds to profile the device's idle power
	 *            consumption in front of each test run iteration.
	 * @param isHwProfilingEnabled
	 *            Indicates whether or not to profile further hardware
	 *            information such as CPU utilization or display brightness.
	 */
	public void runTestRun(int runs, int idleTime, boolean isHwProfilingEnabled) {

		if (runs <= 0)
			runs = 1;
		// no else.

		if (idleTime < 0)
			idleTime = 0;
		// no else.

		try {
			deployTestCases();

			testSuiteProfile = new TestSuiteProfile();

			/*
			 * Compute timestamp offset for both test runner and device under
			 * test.
			 */
			testSuiteProfile.getTrTimeStampOffset();
			computeDutTimestampOffset();

			profiler = startEnergyProfiling();

			if (isHwProfilingEnabled)
				startHardwareProfiling();
			// no else.

			reportProgress("Running tests...");
			testRunner = new TestRunner(runs, idleTime);
			testRunner.start();

			/* Wait for the testRunner. */
			while (!testRunner.isAbortedOrTerminated) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					/* Not important. */
				}
			}

			testRunner = null;

			if (isHwProfilingEnabled)
				stopHardwareProfiling();
			// no else.

			stopEnergyProfiling(profiler);

			undeployTestCases();

			propagateResults();
		}

		catch (ProfilingException e) {
			reportError(e);
		}
	}

	/**
	 * Helper method to compute the device under test's timestamp offset to UTC
	 * based on the network time protocol. The timestamp offset of the test
	 * runner will be computed automatically and is not part of this method's
	 * invocation.
	 * 
	 * @throws ProfilingException
	 *             Thrown, if the timestamp computation fails.
	 */
	protected abstract void computeDutTimestampOffset()
			throws ProfilingException;

	/**
	 * Helper method to deploy the test cases or workload application on the
	 * device under test.
	 * 
	 * @throws ProfilingException
	 *             Thrown, if the deployment fails.
	 */
	protected abstract void deployTestCases() throws ProfilingException;

	/**
	 * Helper method to profile the device's idle power consumption.
	 * 
	 * @param time
	 *            The number of seconds to profile the idle consumption.
	 * @throws ProfilingException
	 *             Thrown, if idle profiling fails.
	 */
	protected void profileIdleConsumption(int time) throws ProfilingException {
		reportProgress("Profile idle energy consumption ...");

		try {
			long startNano = System.nanoTime();
			Thread.sleep(time * 1000);
			long stoppedNano = System.nanoTime();

			TestCaseProfile profile = new TestCaseProfile();
			profile.setId(IDLE_TEST_CASE_ID);
			profile.setStartTime(Math.round(testSuiteProfile
					.adaptTrTimeStamp(startNano))
					- testSuiteProfile.getPutTimeStampOffset());
			profile.setEndTime(Math.round(testSuiteProfile
					.adaptTrTimeStamp(stoppedNano))
					- testSuiteProfile.getPutTimeStampOffset());
			testSuiteProfile.addTestCase(profile);
		}

		catch (InterruptedException e) {
			/* Not important. */
		}
	}

	/**
	 * Helper method to evaluate and propagate the profiling results.
	 * 
	 * @throws ProfilingException
	 *             Thrown, if test result propagation fails.
	 */
	protected abstract void propagateResults() throws ProfilingException;

	/**
	 * Helper method to report {@link Exception}s during profiling
	 * 
	 * @param e
	 *            The {@link Exception} to be reported.
	 */
	protected void reportError(Exception e) {
		reportError(e.getMessage());
	}

	/**
	 * Helper method to start the test case execution on the device under test.
	 * 
	 * @throws ProfilingException
	 *             Thrown, if test case execution fails (not if an individual
	 *             test case fails!!!).
	 */
	protected abstract void runTestCases() throws ProfilingException;

	/**
	 * Helper method to create, initialize and start the energy profiling.
	 * 
	 * @return The {@link JouleProfiler} that performs the profiling.
	 * @throws ProfilingException
	 *             Thrown, if the {@link JouleProfiler} initialization fails.
	 */
	protected abstract JouleProfiler startEnergyProfiling()
			throws ProfilingException;

	/**
	 * Helper method to stop the energy profiling. The default implementation
	 * will wait for one second and then simply stop the {@link JouleProfiler}
	 * and add its resulting {@link EnergyProfile} to the
	 * {@link TestSuiteProfile} of this {@link JouleUnitCoordinator}.
	 * 
	 * @param profiler
	 *            The {@link JouleProfiler} which profiling shall be terminated.
	 * @throws ProfilingException
	 *             Thrown, if stopping the energy profiling fails.
	 */
	protected void stopEnergyProfiling(JouleProfiler profiler)
			throws ProfilingException {
		/*
		 * Wait a second to avoid exceptions due to too sharp profiling time
		 * stamps.
		 */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* Do nothing. */
		}

		EnergyProfile profile = profiler.endProfiling();
		testSuiteProfile.setEnergyProfile(profile);

		reportProgress("Profiling terminated.");
	}

	/**
	 * Helper method to create, initialize and start the extra hardware
	 * profiling (e.g., CPU utilization).
	 * 
	 * @throws ProfilingException
	 *             Thrown, if starting the hardware profiling fails.
	 */
	protected abstract void startHardwareProfiling() throws ProfilingException;

	/**
	 * Helper method to stop the extra hardware profiling (e.g., CPU
	 * utilization).
	 * 
	 * @throws ProfilingException
	 *             Thrown, if stopping the HW service fails.
	 */
	protected abstract void stopHardwareProfiling() throws ProfilingException;

	/**
	 * Helper method to undeploy the test cases or workload application from the
	 * device under test.
	 * 
	 * @throws ProfilingException
	 *             Thrown, if the undeployment fails.
	 */
	protected abstract void undeployTestCases() throws ProfilingException;
}
