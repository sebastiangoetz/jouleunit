package org.qualitune.jouleunit.example.nao;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleAssert;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.JouleUtil;
import org.qualitune.jouleunit.ProfilingException;
import org.qualitune.jouleunit.nao.NaoProfiler;
import org.qualitune.jouleunit.persist.EnergyProfileHistory;
import org.qualitune.naoservice.client.Nao;
import org.qualitune.naoservice.client.NaoUtil;

/**
 * JouleUnit test cases to test the {@link NaoDemoApplication}'s enery
 * consumption.
 * 
 * @author Claas Wilke
 */
public class TestNaoDemoApplication {

	/** {@link Nao} proxy used during testing. */
	protected static Nao nao;

	/** {@link JouleProfiler} to profile the {@link Nao}. */
	protected static JouleProfiler profiler;

	/**
	 * Global setUp method. Creates {@link Nao} proxy and {@link JouleProfiler}.
	 */
	@BeforeClass
	public static void setUp() {
		nao = new Nao(NaoDemoApplication.NAO_IP, NaoDemoApplication.NAO_PORT);

		profiler = new NaoProfiler(nao);
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(true);
		profiler.calibrate();
	}

	/**
	 * Set Up method. Gets {@link Nao} to neutral (sitting) position.
	 */
	@Before
	public void setUpEach() {
		NaoUtil.sitDown(nao);
		NaoUtil.setStiffness(nao, 0f);
	}

	/**
	 * Tear down method. Gets {@link Nao} to neutral (sitting) position.
	 */
	@After
	public void tearDownEach() {
		/* Get to neutral position. */
		NaoUtil.sitDown(nao);
		NaoUtil.setStiffness(nao, 0f);
	}

	/**
	 * General test case to profile the energy consumption of the main() method
	 * 
	 * Demonstrates usage of profiling capabilities of JouleUnit. Exports a CSV
	 * file to get a general impression of the SUT's energy behavior.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void profileMain() throws InterruptedException {
		profiler.startProfiling();

		/*
		 * Run the main method twice with an interval of ten seconds between
		 * each iteration.
		 */
		NaoDemoApplication.main(new String[] { "10", "2" });

		EnergyProfile profile = profiler.endProfiling();

		JouleUtil.exportProfile(profile, new File("profileMain.csv"),
				"Test profileMain");

		System.out.println(profile.toString());

		/*
		 * Energy Profile duration: 137.060724224 secs avg. profiling frequenzy:
		 * 34.31325805862393 Hz consumption: 5224633.412445568 mJ peak: -75180
		 * mW
		 */
	}

	/**
	 * General test case to profile the energy consumption of the presentation
	 * part of the application.
	 * 
	 * Demonstrates usage of profiling capabilities of JouleUnit. Uses the
	 * profileNTimes method and computes average consumption as well as its std.
	 * error.
	 * 
	 * @throws ProfilingException
	 */
	@Test
	public void profilePresentationPart() throws ProfilingException {

		/* Profile ten times. */
		List<EnergyProfile> profiles = profiler.profileNTimes(
				NaoDemoApplication.class, "presentationPart",
				new Object[] { nao }, 10);

		printResult(profiles, "presentationPart()");

		/*
		 * Results (presentationPart()): Energy consumption [mJ] | duration
		 * [nano secs] 2425710.1210851856 | 63631826944 2436939.8100641314 |
		 * 63332866304 2371833.9109703694 | 63583418112 2478357.95661952 |
		 * 64477234176 2449989.104670717 | 63511563008 2456692.5022666245 |
		 * 63298027008 2486116.0493163513 | 63595872256 2475741.8909995523 |
		 * 63137116160 2483086.5597798447 | 63300755200 2450586.3904189444 |
		 * 63674375936
		 * 
		 * Summary Method | avg. consumption | consumption error | avg. duration
		 * | duration error 2451.505429619124 | | 10.933578625723134 |
		 * 63.5543055104 secs.0.1168890626902767 secs.
		 */
	}

	/**
	 * General test case to profile the energy consumption of the waiting part
	 * of the application.
	 * 
	 * Demonstrates usage of profiling capabilities of JouleUnit. Uses the
	 * profileNTimes method and computes average consumption as well as its std.
	 * error.
	 * 
	 * @throws ProfilingException
	 */
	@Test
	public void profileWaitingPart() throws ProfilingException {

		/* Profile ten times. */
		List<EnergyProfile> profiles = profiler.profileNTimes(
				NaoDemoApplication.class, "waitNSeconds", new Object[] { 10 },
				10);

		printResult(profiles, "waitNSeconds()");

		/*
		 * Results (waitNSeconds()): Energy consumption [mJ] | duration [nano
		 * secs] 188762.58281408 | 9368739072 200157.17466739196 | 9935205888
		 * 192102.90860620805 | 9536623872 204042.81647232 | 10129968128
		 * 191624.78138393603 | 9513239040 194262.4283714561 | 9647634944
		 * 198365.1845998079 | 9851785728 197117.1685475842 | 9789317120
		 * 188244.2995828479 | 9356535808 203985.90909478394 | 10137942016
		 * 
		 * Summary Method | avg. consumption | consumption error | avg. duration
		 * | duration error 195.86652541404163 | | 1.832690378513381 |
		 * 9.726699161600001 secs.0.09115862738154748 secs.
		 */
	}

	/**
	 * General test case to profile the energy consumption of the waiting part
	 * of the application.
	 * 
	 * Demonstrates usage of profiling capabilities of JouleUnit. Uses the
	 * profileNTimes method including setUp and tearDown methods and computes
	 * average consumption as well as its std. error.
	 * 
	 * @throws ProfilingException
	 */
	@Test
	public void profileWaitingPartWithSetUpAndTearDown()
			throws ProfilingException {

		/* Let the nao stand up before starting profiling. */
		NaoUtil.standUp(nao);

		/* Profile ten times. */
		List<EnergyProfile> profiles = profiler.profileNTimes(
				NaoDemoApplication.class, "waitNSeconds", new Object[] { 10 },
				"presentationPart", new Object[] { nao }, null, null, 10);

		printResult(profiles, "waitNSeconds()");

		/*
		 * Results (waitNSeconds()): Energy consumption [mJ] | duration [nano
		 * secs] 244227.91327424007 | 9966452736 225339.93396249594 | 9448675840
		 * 234816.533626624 | 9454973184 235765.54840076796 | 9680440064
		 * 223849.01014553598 | 9560175104 237376.95171903985 | 9539775744
		 * 243966.42372710412 | 9818131968 222492.04152780803 | 9453330176
		 * 239648.70384243224 | 9984307968 238113.75881855993 | 9889414144
		 * 
		 * Summary Method | avg. consumption | consumption error | avg. duration
		 * | duration error 234.55968190446083 | | 2.528280879388174 |
		 * 9.6795676928 secs.0.06889118231798702 secs.
		 */
	}

	/**
	 * General test case to test bounds of the waiting part of the application.
	 * 
	 * Demonstrates usage of energy bound test capabilities of JouleUnit.
	 * 
	 * @throws ProfilingException
	 */
	@Test
	public void testBoundsOfWaitingPart() {

		/* Prepare the right state. */
		NaoDemoApplication.presentationPart(nao);

		/* Profile the waiting interval. */
		profiler.startProfiling();
		NaoDemoApplication.waitNSeconds(10);
		EnergyProfile profile = profiler.endProfiling();

		/* Do not consume more than 200 J. */
		JouleAssert.assertMaxJoules(profile, 200000);

		/* Do never consume more than 22 W. */
		JouleAssert.assertMaxWatts(profile, 22000);
	}

	/**
	 * General test case to test bounds of the waiting part of the application
	 * with the fixed version of the application.
	 * 
	 * Demonstrates usage of energy bound test capabilities of JouleUnit.
	 * 
	 * @throws ProfilingException
	 */
	@Test
	public void testBoundsOfWaitingPartFixed() {

		/* Prepare the right state. */
		NaoDemoApplication.presentationPartFixed(nao);

		/* Profile the waiting interval. */
		profiler.startProfiling();
		NaoDemoApplication.waitNSeconds(10);
		EnergyProfile profile = profiler.endProfiling();

		/* Do not consume more than 210 J. */
		JouleAssert.assertMaxJoules(profile, 210000);

		/* Do never consume more than 22 W. */
		JouleAssert.assertMaxWatts(profile, 22000);
	}

	/**
	 * Energy regression test case to optimize the energy consumption of the
	 * main() method
	 * 
	 * Demonstrates usage of regression testing capabilities of JouleUnit.
	 * 
	 * @throws InterruptedException
	 * @throws SQLException 
	 */
	@Test
	public void testRegressionMain() throws InterruptedException, SQLException {

		/*
		 * This is the preparation. Profiles the unoptimized variant and stores
		 * it in JoulUnits database history. This is just exemplary to prepare
		 * the history and let the test case work after its first iteration.
		 */
		profiler.startProfiling();
		NaoDemoApplication.main(new String[] { "300", "2" });
		EnergyProfile profile = profiler.endProfiling();
		EnergyProfileHistory.INSTANCE.storeProfile(profile,
				"testRegressionMain");

		/* Here starts the real test case. */
		profiler.startProfiling();
		NaoDemoApplication.mainFixed(new String[] { "300", "2" });
		profile = profiler.endProfiling();

		/* Asserts a decrease of 700J. */
		JouleAssert.assertDecrease(profile, "testRegressionMain", 1,
				0, 700000);
		
		/* Asserts a decrease of 5%. */
		JouleAssert.assertDecrease(profile, "testRegressionMain", 2,
				0.05f, 0);
	}

	/**
	 * Prints a detailed result on the console for a given {@link List} of
	 * {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s to be printed.
	 * @param methodName
	 *            The name of the method that has been profiled.
	 */
	protected void printResult(List<EnergyProfile> profiles, String methodName) {
		System.out.println("Results (" + methodName + "):");
		System.out.println("Energy consumption [mJ] | duration [nano secs]");
		for (EnergyProfile profile : profiles)
			System.out.println(profile.getConsumedEnergy() + " | "
					+ profile.getDuration());

		System.out.println("\nSummary");
		System.out
				.println("Method | avg. consumption | consumption error | avg. duration | duration error");
		System.out.println(JouleUtil.getJouleAvg(profiles) / 1000d + " | "
				+ " | " + JouleUtil.getJouleStdError(profiles) / 1000d + " | "
				+ JouleUtil.getDurationAvg(profiles) / 1000000000d + " secs."
				+ JouleUtil.getDurationStdError(profiles) / 1000000000d
				+ " secs.");
		System.out.println("\n");
	}
}
