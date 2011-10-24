package org.qualitune.jouleunit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.qualitune.jouleunit.CompositeJouleProfiler;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.SimpleEnergyProfile;

/**
 * Contains test cases for the {@link CompositeJouleProfiler}.
 * 
 * @author Claas Wilke
 */
public class CompositeJouleProfilerTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAddProfilerNegative01() {
		CompositeJouleProfiler compProfiler = new CompositeJouleProfiler();
		compProfiler.addProfiler(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCalibrateNegative01() {
		CompositeJouleProfiler comProfiler = new CompositeJouleProfiler();
		JouleProfiler profiler = new JouleProfilerMock(0, 0, true);
		comProfiler.addProfiler(profiler);
		comProfiler.calibrate();
	}

	/**
	 * Test case for {@link JouleProfiler#calibrate()}.
	 */
	@Test
	public void testCalibratePositive01() {
		CompositeJouleProfiler comProfiler = new CompositeJouleProfiler();
		JouleProfiler profiler = new JouleProfilerMock(0, -10000, false);
		comProfiler.addProfiler(profiler);

		/* No probe effect and noise estimation necessary. */
		comProfiler.setEstimationInterval(0);

		comProfiler.calibrate();

		/* Smallest calibration should be 20Hz (+/-2). */
		assertEquals(20, comProfiler.getProfilingInterval(), 2);
	}

	@Test
	public void testEstimateBaseConsumptionPositive01() {

		CompositeJouleProfiler comProfiler = new CompositeJouleProfiler();
		JouleProfiler profiler1 = new JouleProfilerMock3(100, false);
		comProfiler.addProfiler(profiler1);
		JouleProfiler profiler2 = new JouleProfilerMock3(100, false);
		comProfiler.addProfiler(profiler2);

		/* Should be null, as not calibrated yet. */
		assertEquals(0d, comProfiler.getEstimatedProbeEffect(), 0d);
		assertEquals(0d, comProfiler.getEstimatedBaseConsumption(), 0d);

		comProfiler.setEstimationInterval(1);
		comProfiler.calibrate();

		assertEquals(
				profiler1.getEstimatedBaseConsumption()
						+ profiler2.getEstimatedBaseConsumption(),
				comProfiler.getEstimatedBaseConsumption(), 1l);
	}

	@Test
	public void testEstimateProbeEffectPositive01() {

		CompositeJouleProfiler comProfiler = new CompositeJouleProfiler();
		JouleProfiler profiler1 = new JouleProfilerMock3(100, false);
		comProfiler.addProfiler(profiler1);
		JouleProfiler profiler2 = new JouleProfilerMock3(100, false);
		comProfiler.addProfiler(profiler2);

		double probeEffect = comProfiler.getEstimatedProbeEffect();

		/* Should be null, as not calibrated yet. */
		assertEquals(0d, probeEffect, 0d);

		comProfiler.setEstimationInterval(1);
		comProfiler.calibrate();
		probeEffect = comProfiler.getEstimatedProbeEffect();

		/*
		 * The profiler always returns its profilingInterval in milliseconds as
		 * power rate. Thus, profiling with high frequency results in -50W, with
		 * low frequency in -25W. Results in a probe effect of (-50W - (-25W)) *
		 * 2 = -50W.
		 */
		assertEquals(
				profiler1.getEstimatedProbeEffect()
						+ profiler2.getEstimatedProbeEffect(), probeEffect, 1l);
	}

	@Test(expected = IllegalStateException.class)
	public void testEndProfilingNegative01() {
		CompositeJouleProfiler comProfiler = new CompositeJouleProfiler();
		JouleProfiler profiler = new JouleProfilerMock(100, 10000, false);
		comProfiler.addProfiler(profiler);

		comProfiler.endProfiling();
	}

	@Test(expected = IllegalStateException.class)
	public void testLogEventNegative01() {
		CompositeJouleProfiler comProfiler = new CompositeJouleProfiler();
		JouleProfiler profiler = new JouleProfilerMock(100, 10000, false);
		comProfiler.addProfiler(profiler);

		comProfiler.logEvent("test");
	}

	@Test
	public void testLogEventPositive01() {
		CompositeJouleProfiler comProfiler = new CompositeJouleProfiler();
		JouleProfiler profiler1 = new JouleProfilerMock(100, 10000, false);
		comProfiler.addProfiler(profiler1);
		JouleProfiler profiler2 = new JouleProfilerMock(100, 10000, false);
		comProfiler.addProfiler(profiler2);

		comProfiler.setEstimationInterval(0);
		comProfiler.setProbeEffectConsiderationEnabled(false);
		comProfiler.setBaseConsumptionConsiderationEnabled(false);
		comProfiler.startProfiling();
		comProfiler.logEvent("test");
		EnergyProfile profile = comProfiler.endProfiling();

		assertEquals(1, profile.getLoggedEvents().size());
		assertTrue(profile.getLoggedEvents().keySet().contains("test"));
	}

	@Test
	public void testProfilingPositive01() throws InterruptedException {
		CompositeJouleProfiler comProfiler = new CompositeJouleProfiler();
		JouleProfiler profiler = new JouleProfilerMock(100, 10000, false);
		comProfiler.addProfiler(profiler);

		/* Probe effect and base consumption shall not be considered here. */
		comProfiler.setBaseConsumptionConsiderationEnabled(false);
		comProfiler.setProbeEffectConsiderationEnabled(false);

		comProfiler.startProfiling();
		Thread.sleep(120);
		SimpleEnergyProfile profile = (SimpleEnergyProfile) comProfiler
				.endProfiling();

		assertTrue(profile.getConsumedEnergy() > 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetProfilingIntervalNegative01() {
		CompositeJouleProfiler comProfiler = new CompositeJouleProfiler();
		JouleProfiler profiler = new JouleProfilerMock(0, 0, false);
		comProfiler.addProfiler(profiler);
		comProfiler.setProfilingInterval(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetProfilingIntervalNegative02() {
		CompositeJouleProfiler comProfiler = new CompositeJouleProfiler();
		JouleProfiler profiler = new JouleProfilerMock(0, 0, false);
		comProfiler.addProfiler(profiler);
		profiler.setProfilingInterval(-42);
	}
}
