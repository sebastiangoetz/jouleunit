package org.qualitune.jouleunit.wt210.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.qualitune.jouleunit.CompositeJouleProfiler;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.wt210.WT210Profiler;

/**
 * Test cases for Nao profiling.
 * 
 * @author Claas Wilke
 */
public class WT210ProfilerTest {

	/**
	 * Tests the creation of a {@link WT210Profiler}.
	 * 
	 * @throws InitializationError
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitializationNegative01() throws InitializationError {
		/* Should fail. */
		new WT210Profiler(-1, true);
	}

	/**
	 * Tests the calibration using an {@link WT210Profiler}.
	 * 
	 * <strong>Please note, that this test will fail, if no device is connected
	 * to {@link WT210PowerRateTest.GPIB_ADDRESS_WT210_DEVICE_1}.</strong>
	 * 
	 * @throws InitializationError
	 */
	@Test
	@Ignore
	public void testCalibrationPositive01() throws InitializationError {
		WT210Profiler profiler = new WT210Profiler(
				WT210PowerRateTest.GPIB_ADDRESS_WT210_DEVICE_1, true);
		profiler.calibrate();

		/*
		 * TODO This seems to be incorrect. Result is too low. Should be near 10
		 * Hz.
		 */
		System.out.println(profiler.getProfilingInterval());
	}

	/**
	 * Tests the pofiling using an {@link WT210Profiler}.
	 * 
	 * <strong>Please note, that this test will fail, if no device is connected
	 * to {@link WT210PowerRateTest.GPIB_ADDRESS_WT210_DEVICE_1}.</strong>
	 * 
	 * @throws InitializationError
	 * @throws InterruptedException
	 */
	@Test
	public void testProfilingPositive01() throws InitializationError,
			InterruptedException {
		WT210Profiler profiler = new WT210Profiler(
				WT210PowerRateTest.GPIB_ADDRESS_WT210_DEVICE_1, true);
		profiler.setProfilingInterval(50);
		profiler.startProfiling();
		Thread.sleep(1000);
		EnergyProfile result = profiler.endProfiling();

		System.out.println(result);

		assertNotNull(result.getConsumedEnergy());
		assertTrue(
				"Consumed Energy should be positive. Was: "
						+ result.getConsumedEnergy(),
				result.getConsumedEnergy() > 0);
	}

	/**
	 * Tests the pofiling using an {@link WT210Profiler}.
	 * 
	 * <strong>Please note, that this test will fail, if no device is connected
	 * to {@link WT210PowerRateTest.GPIB_ADDRESS_WT210_DEVICE_1}.</strong>
	 * 
	 * @throws InitializationError
	 * @throws InterruptedException
	 */
	@Test
	public void testCompositeProfilingPositive01() throws InitializationError,
			InterruptedException {

		WT210Profiler profiler1 = new WT210Profiler(
				WT210PowerRateTest.GPIB_ADDRESS_WT210_DEVICE_1, true);
		profiler1.setProfilingInterval(50);

		WT210Profiler profiler2 = new WT210Profiler(
				WT210PowerRateTest.GPIB_ADDRESS_WT210_DEVICE_2, true);
		profiler2.setProfilingInterval(50);

		CompositeJouleProfiler profiler = new CompositeJouleProfiler();
		profiler.addProfiler(profiler1);
		profiler.addProfiler(profiler2);

		profiler.startProfiling();
		Thread.sleep(1000);
		EnergyProfile result = profiler.endProfiling();

		System.out.println(result);

		assertNotNull(result.getConsumedEnergy());
		assertTrue(
				"Consumed Energy should be positive. Was: "
						+ result.getConsumedEnergy(),
				result.getConsumedEnergy() > 0);
	}
}
