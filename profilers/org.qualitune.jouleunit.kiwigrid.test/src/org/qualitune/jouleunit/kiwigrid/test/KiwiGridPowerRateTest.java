package org.qualitune.jouleunit.kiwigrid.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.qualitune.jouleunit.PowerRate;
import org.qualitune.jouleunit.kiwigrid.KiwiGridPowerRate;

/**
 * Test cases for Nao profiling.
 * 
 * @author Claas Wilke
 */
public class KiwiGridPowerRateTest {

	/** IP (v6) address of KiwiGrid plug 1. */
	public final static String IPv6_ADDRESS_KIWIGRID_PLUG_1 = "fe80::11:7d00:21:140c";

	/** IP (v6) address of KiwiGrid plug 2. */
	public final static String IPv6_ADDRESS_KIWIGRID_PLUG_2 = "fe80::11:7d00:21:151a";

	/** IP (v6) address of KiwiGrid plug 3. */
	public final static String IPv6_ADDRESS_KIWIGRID_PLUG_3 = "fe80::11:7d00:21:142a";

	/** IP (v6) address of KiwiGrid plug 4. */
	public final static String IPv6_ADDRESS_KIWIGRID_PLUG_4 = "fe80::11:7d00:21:1929";

	/** Suffix of the IP address. Might change when running on another server. */
	public final static String IPv6_SUFFIX = "%11";

	/**
	 * Tests the creation of a {@link KiwiGridPowerRate}.
	 * 
	 * @throws InitializationError
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitializationNegative01() throws InitializationError {
		/* Should fail. */
		new KiwiGridPowerRate(null);
	}

	/**
	 * Tests the creation of a {@link KiwiGridPowerRate}.
	 * 
	 * @throws InitializationError
	 */
	@Test(expected = IllegalStateException.class)
	public void testInitializationNegative02() throws InitializationError {
		/* Should fail. */
		new KiwiGridPowerRate("something wrong");
	}

	/**
	 * Tests the creation of a {@link KiwiGridPowerRate}.
	 * 
	 * <strong>Please note, that this test will fail, if Plug 4 is not running
	 * or has a different IP as declared above.</strong>
	 * 
	 * @throws InitializationError
	 */
	@Test
	public void testInitializationPositive01() throws InitializationError {
		/* Should not fail. */
		new KiwiGridPowerRate(IPv6_ADDRESS_KIWIGRID_PLUG_4 + IPv6_SUFFIX);
	}

	/**
	 * Tests the measured value of a {@link KiwiGridPowerRate}.
	 * 
	 * <strong>Please note, that this test will fail, if Plug 4 is not running
	 * or has a different IP as declared above.</strong>
	 * 
	 * @throws InitializationError
	 */
	@Test
	public void testChargingRatePositive01() throws InitializationError {
		/* Should not fail. */
		PowerRate value = new KiwiGridPowerRate(
				IPv6_ADDRESS_KIWIGRID_PLUG_4 + IPv6_SUFFIX);
		assertNotNull(value.getPowerRate());
		assertTrue(value.getPowerRate() < 0);
	}
}
