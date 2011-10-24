package org.qualitune.jouleunit.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.qualitune.jouleunit.test.persist.AllPersistTests;

/**
 * Test suite of all JouleUnit test cases.
 * 
 * @author Claas Wilke
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AbstractJouleProfilerTest.class,
		CompositeEnergyProfileTest.class, SimpleEnergyProfileTest.class,
		EnergyProfileComparatorTest.class,
		EnergyProfileDurationComparatorTest.class, JouleAssertTest.class,
		JouleProfilerMockTest.class, JouleUtilTest.class, AllPersistTests.class })
public class AllJouleUnitTests {
	/* Remains empty. */
}
