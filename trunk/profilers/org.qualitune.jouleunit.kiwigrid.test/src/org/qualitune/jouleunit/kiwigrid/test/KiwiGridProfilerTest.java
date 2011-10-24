package org.qualitune.jouleunit.kiwigrid.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.kiwigrid.KiwiGridProfiler;

/**
 * Test cases for Nao profiling.
 * 
 * @author Claas Wilke
 */
public class KiwiGridProfilerTest {

	/**
	 * Tests the creation of a {@link KiwiGridProfiler}.
	 * 
	 * @throws InitializationError
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitializationNegative01() throws InitializationError {
		/* Should fail. */
		new KiwiGridProfiler(null);
	}

	/**
	 * Tests the calibration using an {@link KiwiGridProfiler}.
	 * 
	 * <strong>Please note, that this test will fail, if Plug 4 is not running
	 * or has a different IP as declared above.</strong>
	 * 
	 * @throws InitializationError
	 */
	@Test
	public void testCalibrationPositive01() throws InitializationError {
		KiwiGridProfiler profiler = new KiwiGridProfiler(
				KiwiGridPowerRateTest.IPv6_ADDRESS_KIWIGRID_PLUG_4);
		profiler.calibrate();

		System.out.println(profiler.getProfilingInterval());
	}

	/**
	 * Tests the calibration using an {@link KiwiGridProfiler}.
	 * 
	 * <strong>Please note, that this test will fail, if Plug 4 is not running
	 * or has a different IP as declared above.</strong>
	 * 
	 * @throws InitializationError
	 * @throws InterruptedException
	 */
	@Test
	public void testProfilingPositive01() throws InitializationError,
			InterruptedException {
		KiwiGridProfiler profiler = new KiwiGridProfiler(
				KiwiGridPowerRateTest.IPv6_ADDRESS_KIWIGRID_PLUG_4);
		profiler.calibrate();
		profiler.startProfiling();
		Thread.sleep(1000);
		EnergyProfile result = profiler.endProfiling();

		assertNotNull(result.getConsumedEnergy());
		assertTrue(result.getConsumedEnergy() > 0);

		System.out.println(result);
	}
}
