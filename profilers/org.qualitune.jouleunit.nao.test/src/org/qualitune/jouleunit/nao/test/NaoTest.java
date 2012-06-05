package org.qualitune.jouleunit.nao.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleAssert;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.JouleUtil;
import org.qualitune.jouleunit.ProfilingException;
import org.qualitune.jouleunit.nao.NaoProfiler;
import org.qualitune.naoservice.client.Nao;
import org.qualitune.naoservice.client.Nao.ALMotion;
import org.qualitune.naoservice.client.Nao.ALTextToSpeech;
import org.qualitune.naoservice.client.NaoData;
import org.qualitune.naoservice.client.NaoUtil;

/**
 * Test cases for Nao profiling.
 * 
 * @author Claas Wilke
 */
public class NaoTest {

	protected static NaoData nao;
	protected static JouleProfiler profiler;

	@BeforeClass
	public static void setUp() {
		nao = new NaoData("192.168.0.139", 8070);

		profiler = new NaoProfiler(nao);
		profiler.setEstimationInterval(0);
		profiler.setProbeEffectConsiderationEnabled(true);
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.calibrate();

		System.out.println("Calibration done. Refresh rate: "
				+ profiler.getProfilingInterval());
	}

	@AfterClass
	public static void tearDown() {
		NaoUtil.sitDown(nao);
		NaoUtil.setStiffness(nao, 0.0f);
	}

	@Test
	public void profileConsumptionWithEvents() throws InterruptedException {

		ALTextToSpeech altts = Nao.createALTextToSpeech(nao.getIP(), nao.getPort());
		
		NaoUtil.sitDown(nao);
		NaoUtil.setStiffness(nao, 0f);

		profiler.startProfiling();
		altts.say("\'I talk while I am sitting.\'");
		altts.say("\'I talk while I am sitting.\'");
		profiler.logEvent("talked sitting");
		NaoUtil.standUp(nao);
		profiler.logEvent("stood up");
		altts.say("\'I talk while I am standing.\'");
		altts.say("\'I talk while I am standing.\'");
		profiler.logEvent("talked standing");
		NaoUtil.sitDown(nao);
		NaoUtil.setStiffness(nao, 0f);
		profiler.logEvent("sat down");
		EnergyProfile profile = profiler.endProfiling();

		JouleUtil.exportProfile(profile, new File("profileWithEventsNew.csv"),
				"Nao JouleUnit Profiling Test");
	}

	/**
	 * Test case to estimate the probe effect of EUnit.
	 * 
	 * @throws InterruptedException
	 * @throws ProfilingException
	 */
	@Test
	public void testProbeEffect() throws InterruptedException,
			ProfilingException {
		int iterations = 50;

		assertTrue(NaoUtil.standUp(nao));
		NaoUtil.say(nao, "Start profiling the probe effect.");

		/* Profiling with configured frequency. */
		profiler.setProfilingInterval(Math.round(1000 / 12.5f));
		float highFrequency = profiler.getProfilingInterval();
		System.out.println("Profiling with " + highFrequency + " Hz.");
		List<EnergyProfile> highFrequencyResult = profiler.profileNTimes(this,
				"testPobeEffectMethodToProfile", new Object[0], iterations);

		profiler.setProfilingInterval(Math.round(1000 / profiler
				.getProfilingInterval() * 2));
		float lowFrequency = profiler.getProfilingInterval();
		System.out.println("Profiling with " + lowFrequency + " Hz.");
		List<EnergyProfile> lowFrequencyResult = profiler.profileNTimes(this,
				"testPobeEffectMethodToProfile", new Object[0], iterations);

		System.out.println("Profiling result for standing 10 seconds for "
				+ iterations + " times.");
		System.out.println("Frequency & Maximum & Minimum & Average \\");
		System.out.println(highFrequency + " & "
				+ JouleUtil.getJouleMax(highFrequencyResult) + " & "
				+ JouleUtil.getJouleMin(highFrequencyResult) + " & "
				+ JouleUtil.getJouleAvg(highFrequencyResult) + " \\");
		System.out.println(lowFrequency + " & "
				+ JouleUtil.getJouleMax(lowFrequencyResult) + " & "
				+ JouleUtil.getJouleMin(lowFrequencyResult) + " & "
				+ JouleUtil.getJouleAvg(lowFrequencyResult) + " \\");
		System.out.println();
		System.out.println("Estimated probe effect: "
				+ (JouleUtil.getJouleAvg(highFrequencyResult) - JouleUtil
						.getJouleAvg(lowFrequencyResult)) * 2 / 10 * 1000
				+ " W / profiling with " + highFrequency + " Hz.");
	}

	public void testPobeEffectMethodToProfile() throws InterruptedException {
		Thread.sleep(10000);
	}

	@Test
	public void testProfileStanding01() throws InterruptedException {

		assertTrue(NaoUtil.standUp(nao));

		NaoUtil.say(nao, "Start profiling while standing.");

		profiler.startProfiling();

		int seconds = 10;
		Thread.sleep(seconds * 1000);

		EnergyProfile profile = profiler.endProfiling();

		NaoUtil.say(nao,
				"Consumed " + Math.round(profile.getConsumedEnergy() / 1000)
						+ " Joules of Energy for standing 10 seconds.");
		System.out.println(profile.getConsumedEnergy());

		JouleAssert.assertMaxJoules(profile, 30000);
		JouleAssert.assertMaxWatts(profile, 40000);
	}

	/**
	 * Runs the same test case multiple times.
	 * 
	 * @throws InterruptedException
	 * @throws ProfilingException
	 */
	@Test
	public void testProfileStanding02() throws InterruptedException,
			ProfilingException {

		assertTrue(NaoUtil.standUp(nao));
		NaoUtil.say(nao, "Start profiling while standing for 5 times.");

		List<EnergyProfile> result = profiler.profileNTimes(this,
				"testProfileStanding02MethodToProfile", new Object[0], 5);

		NaoUtil.say(
				nao,
				"Consumed "
						+ Math.round(JouleUtil.getJouleMax(result) / 1000)
						+ " Joules of Energy at maximum for standing 5 seconds for 5 times.");
		NaoUtil.say(
				nao,
				"Consumed "
						+ Math.round(JouleUtil.getJouleMin(result) / 1000)
						+ " Joules of Energy at minimum for standing 5 seconds for 5 times.");
		NaoUtil.say(
				nao,
				"Consumed an average "
						+ Math.round(JouleUtil.getJouleAvg(result) / 1000)
						+ " Joules of Energy for standing 5 seconds for 5 times.");

		System.out.println(JouleUtil.getJouleMax(result));
		System.out.println(JouleUtil.getJouleMin(result));
		System.out.println(JouleUtil.getJouleAvg(result));

		JouleAssert.assertMaxJoules(result, 181000);
		JouleAssert.assertAvgJoules(result, 180000);
		JouleAssert.assertMaxWatts(result, 30);
	}

	public void testProfileStanding02MethodToProfile()
			throws InterruptedException {
		Thread.sleep(5000);
	}

	@Test
	public void testProfileWalking01() throws InterruptedException {

		assertTrue(NaoUtil.standUp(nao));

		NaoUtil.say(nao, "Start profiling while walking.");

		profiler.startProfiling();

		ALMotion alMotion = Nao.createALMotion(nao.getIP(), nao.getPort());
		alMotion.walkTo(1.0f, 0f, 0f);

		EnergyProfile profile = profiler.endProfiling();

		NaoUtil.say(nao,
				"Consumed " + Math.round(profile.getConsumedEnergy() / 1000)
						+ " Joules of Energy for walking 1 meter to the front.");
		System.out.println(profile.getConsumedEnergy());
	}

	@Test
	public void testProfileWalking02() throws InterruptedException {

		assertTrue(NaoUtil.standUp(nao));

		NaoUtil.say(nao, "Start profiling while walking.");

		profiler.startProfiling();

		ALMotion alMotion = Nao.createALMotion(nao.getIP(), nao.getPort());
		alMotion.walkTo(-1.0f, 0f, 0f);

		EnergyProfile profile = profiler.endProfiling();

		NaoUtil.say(nao,
				"Consumed " + Math.round(profile.getConsumedEnergy() / 1000)
						+ " Joules of Energy for walking 1 meter to the back.");
		System.out.println(profile.getConsumedEnergy());
	}

	@Test
	public void testProfileWalking03() throws InterruptedException,
			ProfilingException {

		int n = 5;

		assertTrue(NaoUtil.standUp(nao));
		ALMotion alMotion = Nao.createALMotion(nao.getIP(), nao.getPort());

		System.out.println("Profiled different walking distances, " + n
				+ " times each.");
		System.out
				.println("Steps [1/30m] | Min Energy Consumption [mJ] | Max Energy Consumption [mJ] | Avg Energy Consumption [mJ]");

		for (int steps = 1; steps <= 10; steps++) {
			float distance = 1f / 30 * steps;

			Object[] args = new Object[2];
			args[0] = alMotion;
			args[1] = distance;
			List<EnergyProfile> profiles = profiler.profileNTimes(this,
					"testProfileWalking03MethodToProfile", args, null, null,
					"testProfileWalking03TearDownOfProfile", args, n);

			System.out.println(steps + " | " + JouleUtil.getJouleMin(profiles)
					+ " | " + JouleUtil.getJouleMax(profiles) + " | "
					+ JouleUtil.getJouleAvg(profiles));
		}
		// end for.
	}

	public void testProfileWalking03MethodToProfile(ALMotion alMotion,
			float distance) {
		alMotion.walkTo(distance, 0f, 0f);
	}

	public void testProfileWalking03TearDownOfProfile(ALMotion alMotion,
			float distance) {
		alMotion.walkTo(-distance, 0f, 0f);
	}
}
