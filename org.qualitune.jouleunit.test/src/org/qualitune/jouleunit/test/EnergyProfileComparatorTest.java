package org.qualitune.jouleunit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.qualitune.jouleunit.EnergyProfileComparator;

/** Test cases for the class {@link EnergyProfileComparator}. */
public class EnergyProfileComparatorTest {

	private static EnergyProfileComparator comparator;

	@BeforeClass
	public static void setUp() {
		comparator = new EnergyProfileComparator();
	}

	@AfterClass
	public static void tearDown() {
		comparator = null;
	}

	@Test(expected = IllegalArgumentException.class)
	public void compareNegative01() {
		comparator.compare(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void compareNegative02() {
		comparator.compare(new SimpleEnergyProfileMock(20l), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void compareNegative03() {
		comparator.compare(null, new SimpleEnergyProfileMock(20l));
	}

	@Test
	public void comparePositive01() {
		assertEquals(0, comparator.compare(new SimpleEnergyProfileMock(20l),
				new SimpleEnergyProfileMock(20l)));
		assertTrue(0 > comparator.compare(new SimpleEnergyProfileMock(10l),
				new SimpleEnergyProfileMock(20l)));
		assertTrue(0 < comparator.compare(new SimpleEnergyProfileMock(20l),
				new SimpleEnergyProfileMock(10l)));
	}
}
