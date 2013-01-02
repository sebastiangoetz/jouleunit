package org.qualitune.jouleunit.nao;

import javax.crypto.EncryptedPrivateKeyInfo;

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
public class NaoJouleUnitCoordinator extends AbstractJouleUnitCoordinator {

	/** The {@link Class} containing the JUnit test cases to test the NAO. */
	private Class<?> jUnitClass;

	/** The {@link JouleProfiler} to be used. */
	private NaoProfiler profiler;

	/**
	 * Creates a new {@link NaoJouleUnitCoordinator}.
	 * 
	 * @param ip
	 *            The IP address of the NAO to be profiled.
	 * @param port
	 *            The port of the NAO to be profiled.
	 * @param jUnitClass
	 *            The {@link Class} containing the JUnit test cases to test the
	 *            NAO.
	 */
	public NaoJouleUnitCoordinator(String ip, int port, Class<?> jUnitClass) {
		profiler = new NaoProfiler(ip, port);
		this.jUnitClass = jUnitClass;
	}

	@Override
	protected void propagateResults() throws ProfilingException {

		for (PowerRate powerRate : testSuiteProfile.getEnergyProfile()
				.getSignificantValues(EnergyProfile.START_EVENT_ID,
						EnergyProfile.END_EVENT_ID)) {
			if (powerRate instanceof RestoredPowerRate) {
				RestoredPowerRate restoredPowerRate = (RestoredPowerRate) powerRate;
				restoredPowerRate.setTimeStamp(restoredPowerRate.getTimeStamp() + testSuiteProfile
						.getPutTimeStampOffset() * 1000000);
			}
			// no else.
		}
		// end for.

		System.out.println("NAO Profiling Result:");
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
		jUnitCore.addListener(new NaoJUnitListener(testSuiteProfile));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.coordinator.AbstractJouleUnitCoordinator#
	 * computeDutTimestampOffset()
	 */
	@Override
	protected void computeDutTimestampOffset() {
		long timeBefore = System.currentTimeMillis();
		NaoPowerRate powerRate = new NaoPowerRate(profiler.ip, profiler.port);
		long timeAfter = System.currentTimeMillis();

		long trTimeStamp = (timeAfter + timeBefore) / 2;
		long putTimeStamp = powerRate.getTimeStamp() / 1000000;

		/* Time stamp between TR and PUT. */
		long putOffset = trTimeStamp - putTimeStamp;
		/*
		 * This is not the global offset but a local one (is sufficient here,
		 * due to a hack).
		 */
		testSuiteProfile.setPutTimeStampOffset(putOffset);

		/* We do not use time correction but do it manually instead. */
		testSuiteProfile.setTimestampCorrectionEnabled(false);
	}
}
