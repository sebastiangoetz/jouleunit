package org.qualitune.jouleunit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.ProfilingException;
import org.qualitune.jouleunit.SimpleEnergyProfile;

/**
 * Contains test cases for the {@link AbstractJouleProfiler}.
 * 
 * @author Claas Wilke
 */
public class AbstractJouleProfilerTest {

	public int methodToInvokeCounter = 0;

	/**
	 * Test case for {@link JouleProfiler#calibrate()}.
	 */
	@Test(expected = IllegalStateException.class)
	public void testCalibrateNegative01() {
		JouleProfiler profiler = new JouleProfilerMock(0, 0, true);
		profiler.calibrate();
	}

	/**
	 * Test case for {@link JouleProfiler#calibrate()}.
	 */
	@Test(expected = IllegalStateException.class)
	public void testCalibrateNegative02() {
		JouleProfiler profiler = new JouleProfilerMock(0, 0, false);
		profiler.calibrate();
	}

	/**
	 * Test case for {@link JouleProfiler#calibrate()}.
	 */
	@Test(expected = IllegalStateException.class)
	public void testCalibrateNegative03() {
		JouleProfiler profiler = new JouleProfilerMock(0, 10, false);
		profiler.calibrate();
	}

	/**
	 * Test case for {@link JouleProfiler#calibrate()}.
	 */
	@Test
	public void testCalibratePositive01() {
		JouleProfiler profiler = new JouleProfilerMock(0, -10000, false);

		/* No probe effect and noise estimation necessary. */
		profiler.setEstimationInterval(0);

		profiler.calibrate();

		/* Smallest calibration should be 20Hz (+/-2). */
		assertEquals(20, profiler.getProfilingInterval(), 2);
	}

	/**
	 * Test case for {@link JouleProfiler#calibrate()}.
	 */
	@Test
	public void testCalibratePositive02() {
		JouleProfiler profiler = new JouleProfilerMock(50, -10000, false);

		/* No probe effect and noise estimation necessary. */
		profiler.setEstimationInterval(0);

		profiler.calibrate();

		/* Calibration should be 20Hz (+/-2). */
		assertEquals(20, profiler.getProfilingInterval(), 2);
	}

	/**
	 * Test case for {@link JouleProfiler#calibrate()}.
	 */
	@Test
	public void testCalibratePositive03() {
		JouleProfiler profiler = new JouleProfilerMock(2000, -10000, false);

		/* No probe effect and noise estimation necessary. */
		profiler.setEstimationInterval(0);

		profiler.calibrate();

		/* Calibration should be 1Hz (+/-0.05). */
		assertEquals(1, profiler.getProfilingInterval(), 0.05f);
	}

	/**
	 * Test case for {@link JouleProfiler#calibrate()}.
	 */
	@Test
	public void testCalibratePositive04() {
		JouleProfiler profiler = new JouleProfilerMock(5000, -10000, false);

		/* No probe effect and noise estimation necessary. */
		profiler.setEstimationInterval(0);

		profiler.calibrate();

		/* Calibration should be 0.5Hz (maximal profiling interval). */
		assertEquals(0.5f, profiler.getProfilingInterval(), 0.0f);
	}

	@Test
	public void testEstimateBaseConsumptionPositive01() {

		JouleProfiler profiler = new JouleProfilerMock3(100, false);

		/* Should be null, as not calibrated yet. */
		assertEquals(0d, profiler.getEstimatedProbeEffect(), 0d);
		assertEquals(0d, profiler.getEstimatedBaseConsumption(), 0d);

		profiler.setEstimationInterval(1);
		profiler.calibrate();

		/*
		 * The profiler always returns its profilingInterval in milliseconds +
		 * the given power rate from the constructor as power rate. Thus, the
		 * base noise should equal to -150mW - (-50mW) = -100mW).
		 */
		assertEquals(-50d, profiler.getEstimatedProbeEffect(), 1d);
		assertEquals(-100d, profiler.getEstimatedBaseConsumption(), 1d);
	}

	@Test
	public void testEstimateProbeEffectPositive01() {

		JouleProfiler profiler = new JouleProfilerMock2(100, false);
		double probeEffect = profiler.getEstimatedProbeEffect();

		/* Should be null, as not calibrated yet. */
		assertEquals(0d, probeEffect, 0d);

		profiler.setEstimationInterval(1);
		profiler.calibrate();
		probeEffect = profiler.getEstimatedProbeEffect();

		/*
		 * The profiler always returns its profilingInterval in milliseconds as
		 * power rate. Thus, profiling with high frequency results in -50W, with
		 * low frequency in -25W. Results in a probe effect of (-50W - (-25W)) *
		 * 2 = -50W.
		 */
		assertEquals(-50d, probeEffect, 1d);
	}

	@Test(expected = IllegalStateException.class)
	public void testEndProfilingNegative01() {
		JouleProfiler profiler = new JouleProfilerMock(100, 10000, false);
		profiler.endProfiling();
	}

	@Test(expected = IllegalStateException.class)
	public void testLogEventNegative01() {
		JouleProfiler profiler = new JouleProfilerMock(100, 10000, false);
		profiler.logEvent("test");
	}

	@Test
	public void testLogEventPositive01() {
		JouleProfiler profiler = new JouleProfilerMock(100, 10000, false);
		profiler.setEstimationInterval(0);
		profiler.setProbeEffectConsiderationEnabled(false);
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.startProfiling();
		profiler.logEvent("test");
		EnergyProfile profile = profiler.endProfiling();

		assertEquals(1, profile.getLoggedEvents().size());
		assertTrue(profile.getLoggedEvents().keySet().contains("test"));
	}

	@Test
	public void testProfilingPositive01() throws InterruptedException {
		JouleProfiler profiler = new JouleProfilerMock(100, 10000, false);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		profiler.startProfiling();
		Thread.sleep(120);
		SimpleEnergyProfile profile = (SimpleEnergyProfile) profiler
				.endProfiling();

		/*
		 * Should contain 5 EMeasurementValues (profiling with 20Hz + ending
		 * value).
		 */
		assertEquals(5, profile.getNumberOfValues());
	}

	@Test
	public void testProfilingPositive02() throws InterruptedException {
		JouleProfiler profiler = new JouleProfilerMock(2000, 10000, false);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		profiler.startProfiling();
		Thread.sleep(1100);
		SimpleEnergyProfile profile = (SimpleEnergyProfile) profiler
				.endProfiling();

		/*
		 * Should contain 4 EMeasurementValues (profiling with 1Hz + ending
		 * value).
		 */
		assertEquals(4, profile.getNumberOfValues());
	}

	@Test
	public void testProfilingPositive03() throws InterruptedException {

		JouleProfiler profiler = new JouleProfilerMock3(2000, false);
		profiler.setEstimationInterval(1);
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(true);
		profiler.calibrate();
		profiler.startProfiling();
		Thread.sleep(100);
		EnergyProfile profile = profiler.endProfiling();

		/*
		 * Without probe effect the profiled consumption should be the base
		 * consumption (2W).
		 */
		assertEquals(2000d * profile.getDuration() / 1000000000d,
				profile.getConsumedEnergy(), 10d);
	}

	@Test
	public void testProfilingPositive04() throws InterruptedException {

		JouleProfiler profiler = new JouleProfilerMock3(2000, false);
		profiler.setEstimationInterval(1);
		profiler.setBaseConsumptionConsiderationEnabled(true);
		profiler.setProbeEffectConsiderationEnabled(false);
		profiler.calibrate();
		profiler.startProfiling();
		Thread.sleep(100);
		EnergyProfile profile = profiler.endProfiling();

		/*
		 * Without base consumption the profiled consumption should be the probe
		 * effect.
		 */
		assertEquals(profiler.getEstimatedProbeEffect() * profile.getDuration()
				/ -1000000000d, profile.getConsumedEnergy(), 1d);
	}

	@Test
	public void testProfilingPositive05() throws InterruptedException {

		JouleProfiler profiler = new JouleProfilerMock3(2000, false);
		profiler.setEstimationInterval(1);
		profiler.setBaseConsumptionConsiderationEnabled(true);
		profiler.setProbeEffectConsiderationEnabled(true);
		profiler.calibrate();
		profiler.startProfiling();
		Thread.sleep(100);
		EnergyProfile profile = profiler.endProfiling();

		/*
		 * Without probe effect and base consumption the profiled consumption
		 * should be null.
		 */
		assertEquals(0d, profile.getConsumedEnergy(), 10d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProfileNTimesNegative01() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		profiler.profileNTimes(null, "methodToInvoke01", new Object[0], 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProfileNTimesNegative02() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		profiler.profileNTimes(this, null, new Object[0], 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProfileNTimesNegative03() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		profiler.profileNTimes(this, "", new Object[0], 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProfileNTimesNegative04() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		profiler.profileNTimes(this, "methodToInvoke01", null, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProfileNTimesNegative05() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		profiler.profileNTimes(this, "methodToInvoke01", new Object[0], 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProfileNTimesNegative06() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		profiler.profileNTimes(this, "methodToInvoke01", new Object[0], -1);
	}

	@Test
	public void testProfileNTimesPositive01() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		methodToInvokeCounter = 0;
		List<EnergyProfile> result = profiler.profileNTimes(this,
				"methodToInvoke01", new Object[0], 3);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(3, methodToInvokeCounter);
	}

	@Test
	public void testProfileNTimesPositive02() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		methodToInvokeCounter = 0;
		Object[] args = new Object[1];
		args[0] = 2;

		List<EnergyProfile> result = profiler.profileNTimes(this,
				"methodToInvoke02", args, 3);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(6, methodToInvokeCounter);
	}

	@Test
	public void testProfileNTimesPositive03() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		methodToInvokeCounter = 0;
		Object[] args = new Object[1];
		args[0] = 2;

		/*
		 * Seems to be the same. But now a method having an argument of a
		 * primitive type is invoked.
		 */
		List<EnergyProfile> result = profiler.profileNTimes(this,
				"methodToInvoke03", args, 3);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(6, methodToInvokeCounter);
	}

	@Test
	public void testProfileNTimesPositive04() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		methodToInvokeCounter = 0;
		List<EnergyProfile> result = profiler.profileNTimes(this,
				"methodToInvoke04", new Object[0], 3);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(3, methodToInvokeCounter);
	}

	@Test
	public void testProfileNTimesPositive05() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		methodToInvokeCounter = 0;
		List<EnergyProfile> result = profiler.profileNTimes(this,
				"methodToInvoke05", new Object[0], 3);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(3, methodToInvokeCounter);
	}

	@Test
	public void testProfileNTimesPositive06() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		methodToInvokeCounter = 0;
		List<EnergyProfile> result = profiler.profileNTimes(this,
				"methodToInvoke01", new Object[0], "methodToInvoke05",
				new Object[0], null, null, 3);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(6, methodToInvokeCounter);
	}

	@Test
	public void testProfileNTimesPositive07() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		methodToInvokeCounter = 0;
		List<EnergyProfile> result = profiler.profileNTimes(this,
				"methodToInvoke01", new Object[0], "methodToInvoke03",
				new Object[] { 1 }, null, null, 3);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(6, methodToInvokeCounter);
	}

	@Test
	public void testProfileNTimesPositive08() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		methodToInvokeCounter = 0;
		List<EnergyProfile> result = profiler.profileNTimes(this,
				"methodToInvoke01", new Object[0], null, null,
				"methodToInvoke05", new Object[0], 3);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(6, methodToInvokeCounter);
	}

	@Test
	public void testProfileNTimesPositive09() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		methodToInvokeCounter = 0;
		List<EnergyProfile> result = profiler.profileNTimes(this,
				"methodToInvoke01", new Object[0], null, null,
				"methodToInvoke03", new Object[] { 1 }, 3);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(6, methodToInvokeCounter);
	}

	@Test
	public void testProfileNTimesPositive10() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		FurtherMethodClass innerClass = new FurtherMethodClass();
		methodToInvokeCounter = 0;
		innerClass.methodToInvokeCounter = 0;
		List<EnergyProfile> result = profiler.profileNTimes(this,
				"methodToInvoke01", new Object[0], innerClass,
				"methodToInvoke01", new Object[0], null, null, null, 3);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(3, methodToInvokeCounter);
		assertEquals(3, innerClass.methodToInvokeCounter);
	}

	@Test
	public void testProfileNTimesPositive11() throws ProfilingException {

		JouleProfiler profiler = new JouleProfilerMock(1000, -20, false);
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);

		FurtherMethodClass innerClass = new FurtherMethodClass();
		methodToInvokeCounter = 0;
		innerClass.methodToInvokeCounter = 0;
		List<EnergyProfile> result = profiler.profileNTimes(this,
				"methodToInvoke01", new Object[0], null, null, null,
				innerClass, "methodToInvoke01", new Object[0], 3);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(3, methodToInvokeCounter);
		assertEquals(3, innerClass.methodToInvokeCounter);
	}

	/**
	 * Test case for {@link JouleProfiler#setProfilingInterval(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetProfilingIntervalNegative01() {
		JouleProfiler profiler = new JouleProfilerMock(0, 0, false);
		profiler.setProfilingInterval(0);
	}

	/**
	 * Test case for {@link JouleProfiler#setProfilingInterval(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetProfilingIntervalNegative02() {
		JouleProfiler profiler = new JouleProfilerMock(0, 0, false);
		profiler.setProfilingInterval(-42);
	}

	/**
	 * Method used for testing of profiling the same method multiple times.
	 */
	public void methodToInvoke01() {
		methodToInvokeCounter++;
	}

	/**
	 * Method used for testing of profiling the same method multiple times.
	 */
	public void methodToInvoke02(Integer arg01) {
		methodToInvokeCounter += arg01;
	}

	/**
	 * Method used for testing of profiling the same method multiple times.
	 */
	public void methodToInvoke03(int arg01) {
		methodToInvokeCounter += arg01;
	}

	/**
	 * Method used for testing of profiling the same method multiple times.
	 */
	protected void methodToInvoke04() {
		methodToInvokeCounter++;
	}

	/**
	 * Method used for testing of profiling the same method multiple times.
	 */
	@SuppressWarnings("unused")
	private void methodToInvoke05() {
		methodToInvokeCounter++;
	}

	/**
	 * Inner {@link Class} providing further methods for tests invoking methods
	 * defined on other {@link Class}es.
	 */
	private class FurtherMethodClass {

		public int methodToInvokeCounter = 0;

		/**
		 * Method used for testing of profiling the same method multiple times.
		 */
		@SuppressWarnings("unused")
		public void methodToInvoke01() {
			methodToInvokeCounter++;
		}
	}
}
