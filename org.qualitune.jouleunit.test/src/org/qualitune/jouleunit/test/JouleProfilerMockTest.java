package org.qualitune.jouleunit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.PowerRate;

/**
 * Test cases for the {@link JouleProfilerMock} implementation.
 * 
 * @author Claas Wilke
 */
public class JouleProfilerMockTest {

	/**
	 * Test case for {@link JouleProfilerMock#getPowerRateValue()}.
	 */
	@Test
	public void testGetEMeasuringValuePositive01() {
		AbstractJouleProfiler profiler = new JouleProfilerMock(100, 10000, true);

		PowerRate value01 = profiler.getPowerRateValue();
		PowerRate value02 = profiler.getPowerRateValue();

		assertEquals(value01.getPowerRate(), value02.getPowerRate());
	}

	/**
	 * Test case for {@link JouleProfilerMock#getPowerRateValue()}.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testGetEMeasuringValuePositive02() throws InterruptedException {
		AbstractJouleProfiler profiler = new JouleProfilerMock(100, 10000, true);

		PowerRate value01 = profiler.getPowerRateValue();
		Thread.sleep(110);
		PowerRate value02 = profiler.getPowerRateValue();

		assertFalse(value01.getPowerRate() == value02.getPowerRate());
	}

	/**
	 * Test case for {@link JouleProfilerMock#getPowerRateValue()}.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testGetEMeasuringValuePositive03() throws InterruptedException {
		AbstractJouleProfiler profiler = new JouleProfilerMock(100, 10000, true);

		PowerRate value01 = profiler.getPowerRateValue();
		Thread.sleep(90);
		PowerRate value02 = profiler.getPowerRateValue();

		assertEquals(value01.getPowerRate(), value02.getPowerRate());
	}

	/**
	 * Test case for {@link JouleProfilerMock#getPowerRateValue()}.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testGetEMeasuringValuePositive04() throws InterruptedException {
		AbstractJouleProfiler profiler = new JouleProfilerMock(500, 10000, true);

		PowerRate value01 = profiler.getPowerRateValue();
		Thread.sleep(400);
		PowerRate value02 = profiler.getPowerRateValue();
		Thread.sleep(200);
		PowerRate value03 = profiler.getPowerRateValue();

		assertEquals(value01.getPowerRate(), value02.getPowerRate());
		assertFalse(value01.getPowerRate() == value03.getPowerRate());
	}
}
