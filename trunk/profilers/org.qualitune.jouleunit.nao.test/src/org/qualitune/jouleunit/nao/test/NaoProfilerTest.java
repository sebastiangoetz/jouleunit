package org.qualitune.jouleunit.nao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.SimpleEnergyProfile;
import org.qualitune.jouleunit.nao.NaoProfiler;
import org.qualitune.naoservice.client.Nao;

/**
 * Contains test cases for the {@link AbstractJouleProfiler}.
 * 
 * @author Claas Wilke
 */
public class NaoProfilerTest {

	protected static Nao nao;
	protected static JouleProfiler profiler;

	@BeforeClass
	public static void setUp() {
		nao = new Nao("192.168.0.139", 8070);

		profiler = new NaoProfiler(nao);
	}

	@AfterClass
	public static void tearDown() {
		nao = null;
		profiler = null;
	}

	@Test
	public void testEstimateBaseConsumptionAndProbeEffectPositive01() {

		profiler = new NaoProfiler(nao);

		/* Should be null, as not calibrated yet. */
		assertEquals(0d, profiler.getEstimatedProbeEffect(), 0d);
		assertEquals(0d, profiler.getEstimatedBaseConsumption(), 0d);

		profiler.setEstimationInterval(1);
		profiler.calibrate();

		assertTrue("Probe effect was:" + profiler.getEstimatedProbeEffect(),
				profiler.getEstimatedProbeEffect() != 0);
		assertTrue(
				"Base consumption was:"
						+ profiler.getEstimatedBaseConsumption(),
				profiler.getEstimatedBaseConsumption() != 0);
	}

	@Test(expected = IllegalStateException.class)
	public void testEndProfilingNegative01() {
		profiler.endProfiling();
	}

	@Test
	public void testProfilingPositive01() throws InterruptedException {

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		profiler.startProfiling();
		Thread.sleep(100);
		SimpleEnergyProfile profile = (SimpleEnergyProfile) profiler
				.endProfiling();

		assertTrue(profile.getNumberOfValues() > 0);
	}
}
