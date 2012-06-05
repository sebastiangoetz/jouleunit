package org.qualitune.jouleunit.wt210.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.qualitune.jouleunit.PowerRate;
import org.qualitune.jouleunit.wt210.WT210PowerRate;

/**
 * Test cases for Nao profiling.
 * 
 * @author Claas Wilke
 */
public class WT210PowerRateTest {

	/** GPIB address of WT210 1. */
	public final static int GPIB_ADDRESS_WT210_DEVICE_1 = 1;

	/** GPIB address of WT210 2. */
	public final static int GPIB_ADDRESS_WT210_DEVICE_2 = 10;

	/**
	 * Tests the creation of a {@link WT210PowerRate}.
	 * 
	 * @throws InitializationError
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitializationNegative01() throws InitializationError {
		/* Should fail. */
		new WT210PowerRate(-1);
	}

	/**
	 * Tests the creation of a {@link WT210PowerRate}.
	 * 
	 * @throws InitializationError
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitializationNegative02() throws InitializationError {
		/* Should fail. */
		new WT210PowerRate(32);
	}

	/**
	 * Tests the creation of a {@link WT210PowerRate}.
	 * 
	 * <strong>Please note, that this test will fail, if no device is connected
	 * to GPIB address {@link WT210PowerRate#GPIB_ADDRESS_WT210_DEVICE_1}
	 * .</strong>
	 * 
	 * @throws InitializationError
	 */
	@Test
	public void testInitializationPositive01() throws InitializationError {
		/* Should not fail. */
		new WT210PowerRate(GPIB_ADDRESS_WT210_DEVICE_1);
	}

	/**
	 * Tests the measured value of a {@link WT210PowerRate}.
	 * 
	 * <strong>Please note, that this test will fail, if no device is connected
	 * to GPIB address {@link WT210PowerRate#GPIB_ADDRESS_WT210_DEVICE_1}
	 * .</strong>
	 * 
	 * @throws InitializationError
	 */
	@Test
	public void testChargingRatePositive01() throws InitializationError {
		/* Should not fail. */
		PowerRate value = new WT210PowerRate(GPIB_ADDRESS_WT210_DEVICE_1);
		assertNotNull(value.getPowerRate());
		assertTrue("Expected negative value. But was " + value.getPowerRate(),
				value.getPowerRate() < 0);
	}
}
