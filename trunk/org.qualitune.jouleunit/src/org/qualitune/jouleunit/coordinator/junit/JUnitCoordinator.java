package org.qualitune.jouleunit.coordinator.junit;

import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.PowerRate;
import org.qualitune.jouleunit.ProfilingException;
import org.qualitune.jouleunit.coordinator.AbstractJouleUnitCoordinator;
import org.qualitune.jouleunit.coordinator.JouleUnitCoordinator;
import org.qualitune.jouleunit.coordinator.TestCaseProfile;
import org.qualitune.jouleunit.persist.RestoredPowerRate;

/**
 * {@link JouleUnitCoordinator} implementation for NAO robots.
 * 
 * @author Claas Wilke
 */
public class JUnitCoordinator extends AbstractJouleUnitCoordinator {

	/** The {@link Class} containing the JUnit test cases to test the NAO. */
	private Class<?> jUnitClass;

	/** The {@link JouleProfiler} to be used. */
	private JouleProfiler profiler;

	/**
	 * Creates a new {@link JUnitCoordinator}.
	 * 
	 * @param profiler
	 *            The {@link JouleProfiler} used for energy profiling.
	 * @param jUnitClass
	 *            The {@link Class} containing the JUnit test cases to be
	 *            profiled.
	 */
	public JUnitCoordinator(JouleProfiler profiler, Class<?> jUnitClass) {
		this.profiler = profiler;
		this.jUnitClass = jUnitClass;
	}

	@Override
	protected void propagateResults() throws ProfilingException {

		for (PowerRate powerRate : testSuiteProfile.getEnergyProfile()
				.getSignificantValues(EnergyProfile.START_EVENT_ID,
						EnergyProfile.END_EVENT_ID)) {
			if (powerRate instanceof RestoredPowerRate) {
				RestoredPowerRate restoredPowerRate = (RestoredPowerRate) powerRate;
				restoredPowerRate.setTimeStamp(restoredPowerRate.getTimeStamp()
						+ testSuiteProfile.getPutTimeStampOffset() * 1000000);
			}
			// no else.
		}
		// end for.

		System.out.println("Profiling Result:");
		System.out.println("Test, Duration (ms), Consumption (mJ):");
		System.out.println("======================================");

		for (TestCaseProfile profile : testSuiteProfile.getTestCaseProfiles()) {
			System.out.println(profile.getId() + ": "
					+ (profile.isFailed() ? "(failed) " : "")
					+ profile.getDuration() + ", "
					+ profile.getConsumedEnergy());
		}
		// end for.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#runTestCases()
	 */
	protected void runTestCases() throws ProfilingException {

		/* Wait a second to avoid too sharp time synchronization. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* Do nothing. */
		}

		Computer computer = new Computer();

		JUnitCore jUnitCore = new JUnitCore();
		jUnitCore.addListener(new JUnitListener(testSuiteProfile));
		jUnitCore.run(computer, jUnitClass);

		/* Wait a second to avoid too sharp time synchronization. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* Do nothing. */
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#startEnergyProfiling
	 * ()
	 */
	protected JouleProfiler startEnergyProfiling() throws ProfilingException {
		profiler.startProfiling();
		return profiler;
	}
}
