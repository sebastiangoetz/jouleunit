package org.qualitune.jouleunit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.qualitune.jouleunit.PowerRate;
import org.qualitune.jouleunit.SimpleEnergyProfile;

/** Test cases for the class {@link SimpleEnergyProfile}. */
public class SimpleEnergyProfileTest {

	@Test
	public void testGetAvgProfilingFrequencyPositive01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		assertEquals(0, profile.getAvgProfilingFrequency(), 0d);
	}

	@Test
	public void testGetAvgProfilingFrequencyPositive02() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(0l, true, 0l));
		profile.addPowerRateValue(new PowerRateMock(0l, true, 1000000000l));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(1000000000l);

		assertEquals(1, profile.getAvgProfilingFrequency(), 0d);
	}

	@Test
	public void testGetAvgProfilingFrequencyPositive03() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(0l, true, 0l));
		profile.addPowerRateValue(new PowerRateMock(0l, true, 1l));
		profile.addPowerRateValue(new PowerRateMock(0l, true, 999999l));
		profile.addPowerRateValue(new PowerRateMock(0l, true, 1000000000l));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(1000000000l);

		assertEquals(3, profile.getAvgProfilingFrequency(), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.getConsumedEnergy(null, SimpleEnergyProfile.END_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative02() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.getConsumedEnergy(SimpleEnergyProfile.START_EVENT_ID, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative03() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.getConsumedEnergy(SimpleEnergyProfile.END_EVENT_ID,
				SimpleEnergyProfile.START_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative04() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.getConsumedEnergy("test", SimpleEnergyProfile.END_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative05() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.getConsumedEnergy(SimpleEnergyProfile.START_EVENT_ID, "test");
	}

	@Test(expected = IllegalStateException.class)
	public void testGetConsumedEnergyNegative06() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.getConsumedEnergy(0l, 1l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative07() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10, true, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10, true, 10l));

		profile.getConsumedEnergy(1l, 0l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative08() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10, true, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10, true, 10l));

		profile.getConsumedEnergy(-5l, 5l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative09() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10, true, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10, true, 10l));

		profile.getConsumedEnergy(5l, 15l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative10() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10, true, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10, true, 10l));

		profile.getConsumedEnergy(-10l, -5l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative11() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10, true, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10, true, 10l));

		profile.getConsumedEnergy(15l, 20l);
	}

	@Test
	public void testGetConsumedEnergyPositive01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		assertEquals(0d, profile.getConsumedEnergy(), 0d);
	}

	@Test
	public void testGetConsumedEnergyPositive02() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10, true, 0l));

		assertEquals(0d, profile.getConsumedEnergy(), 0d);
	}

	@Test
	public void testGetConsumedEnergyPositive03() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10, true, 0l));
		profile.addPowerRateValue(new PowerRateMock(-20, true, 50000000000l));

		assertEquals(750d, profile.getConsumedEnergy(), 0d);
	}

	@Test
	public void testGetConsumedEnergyPositive04() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, true, 0l));
		profile.logEvent("ev01", new PowerRateMock(-20l, true, 50000000000l));
		profile.logEvent("ev02", new PowerRateMock(-20l, true, 100000000000l));
		profile.addPowerRateValue(new PowerRateMock(-10l, true, 150000000000l));

		assertEquals(2500d, profile.getConsumedEnergy(), 0d);
		assertEquals(2500d, profile.getConsumedEnergy(
				SimpleEnergyProfile.START_EVENT_ID,
				SimpleEnergyProfile.END_EVENT_ID), 0d);
		assertEquals(750d, profile.getConsumedEnergy(
				SimpleEnergyProfile.START_EVENT_ID, "ev01"), 0d);
		assertEquals(1000d, profile.getConsumedEnergy("ev01", "ev02"), 0d);
		assertEquals(750d, profile.getConsumedEnergy("ev02",
				SimpleEnergyProfile.END_EVENT_ID), 0d);
	}

	@Test
	public void testGetConsumedEnergyPositive05() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, true, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, true, 1000000000l));

		assertEquals(10d, profile.getConsumedEnergy(0l, 1000000000l), 0d);
		assertEquals(5d, profile.getConsumedEnergy(0l, 500000000l), 0d);
		assertEquals(5d, profile.getConsumedEnergy(500000000l, 1000000000l), 0d);
	}

	@Test
	public void testGetConsumedEnergyPositive06() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, true, 0l));
		profile.addPowerRateValue(new PowerRateMock(-20l, true, 1000000000l));

		assertEquals(15d, profile.getConsumedEnergy(0l, 1000000000l), 0.001d);
		assertEquals(6.25d, profile.getConsumedEnergy(0l, 500000000l), 0.001d);
		assertEquals(8.75d, profile.getConsumedEnergy(500000000l, 1000000000l),
				0.001d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDurationNegative01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.getDuration(null, SimpleEnergyProfile.END_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDurationNegative02() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.getDuration(SimpleEnergyProfile.START_EVENT_ID, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDurationNegative03() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.getDuration("test", SimpleEnergyProfile.END_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDurationNegative04() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.getDuration(SimpleEnergyProfile.START_EVENT_ID, "test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDurationNegative05() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.getDuration(SimpleEnergyProfile.END_EVENT_ID,
				SimpleEnergyProfile.START_EVENT_ID);
	}

	@Test
	public void testGetDurationPositive01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(0l, true, 0l));
		profile.logEvent("ev01", new PowerRateMock(0l, true, 50000000000l));
		profile.logEvent("ev02", new PowerRateMock(0l, true, 150000000000l));
		profile.addPowerRateValue(new PowerRateMock(0l, true, 200000000000l));

		assertEquals(200000000000l, profile.getDuration());
		assertEquals(200000000000l, profile.getDuration(
				SimpleEnergyProfile.START_EVENT_ID,
				SimpleEnergyProfile.END_EVENT_ID));
		assertEquals(50000000000l,
				profile.getDuration(SimpleEnergyProfile.START_EVENT_ID, "ev01"));
		assertEquals(100000000000l, profile.getDuration("ev01", "ev02"));
		assertEquals(50000000000l,
				profile.getDuration("ev02", SimpleEnergyProfile.END_EVENT_ID));
	}

	@Test
	public void testGetIndexOf01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfileMock(0);

		List<PowerRate> values = new ArrayList<PowerRate>();
		PowerRate rate1 = new PowerRateMock(0, false, 0l);

		/* Non-existent values should result in -1. */
		assertEquals(-1, profile.getIndexOf(rate1));
	}

	@Test
	public void testGetIndexOf02() {
		SimpleEnergyProfile profile = new SimpleEnergyProfileMock(0);

		PowerRate rate1 = new PowerRateMock(0, false, 0l);
		PowerRate rate2 = new PowerRateMock(0, false, 10l);
		profile.addPowerRateValue(rate1);

		/* Existent values should result in their index. */
		assertEquals(0, profile.getIndexOf(rate1));
		/* Non-existent values should result in -1. */
		assertEquals(-1, profile.getIndexOf(rate2));
	}

	@Test
	public void testGetIndexOf03() {
		SimpleEnergyProfile profile = new SimpleEnergyProfileMock(0);

		PowerRate rate1 = new PowerRateMock(0, false, 0l);
		PowerRate rate2 = new PowerRateMock(0, false, 10l);
		PowerRate rate3 = new PowerRateMock(0, false, 20l);
		profile.addPowerRateValue(rate1);
		profile.addPowerRateValue(rate2);

		/* Existent values should result in their index. */
		assertEquals(0, profile.getIndexOf(rate1));
		assertEquals(1, profile.getIndexOf(rate2));
		/* Non-existent values should result in -1. */
		assertEquals(-1, profile.getIndexOf(rate3));
	}

	@Test
	public void testGetIndexOf04() {
		SimpleEnergyProfile profile = new SimpleEnergyProfileMock(0);

		PowerRate rate1 = new PowerRateMock(0, false, 0l);
		PowerRate rate2 = new PowerRateMock(0, false, 10l);
		PowerRate rate3 = new PowerRateMock(0, false, 20l);
		PowerRate rate4 = new PowerRateMock(0, false, 30l);
		profile.addPowerRateValue(rate1);
		profile.addPowerRateValue(rate2);
		profile.addPowerRateValue(rate3);

		/* Existent values should result in their index. */
		assertEquals(0, profile.getIndexOf(rate1));
		assertEquals(1, profile.getIndexOf(rate2));
		assertEquals(2, profile.getIndexOf(rate3));
		/* Non-existent values should result in -1. */
		assertEquals(-1, profile.getIndexOf(rate4));
	}

	@Test
	public void testGetNearestValue01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfileMock(0);

		List<PowerRate> values = new ArrayList<PowerRate>();

		/* Empty values should result in null. */
		assertNull(profile.getNearestValue(values, 0, false));
		assertNull(profile.getNearestValue(values, 0, true));
	}

	@Test
	public void testGetNearestValue02() {
		SimpleEnergyProfile profile = new SimpleEnergyProfileMock(0);

		List<PowerRate> values = new ArrayList<PowerRate>();
		PowerRate rate1 = new PowerRateMock(0, false, 0l);
		values.add(rate1);

		/* Check functionality for find values below. */
		assertNull(profile.getNearestValue(values, -5, true));
		assertEquals(rate1, profile.getNearestValue(values, 0, true));
		assertEquals(rate1, profile.getNearestValue(values, 5, true));

		/* Check functionality for find values above. */
		assertEquals(rate1, profile.getNearestValue(values, -5, false));
		assertEquals(rate1, profile.getNearestValue(values, 0, false));
		assertNull(profile.getNearestValue(values, 5, false));
	}

	@Test
	public void testGetNearestValue03() {
		SimpleEnergyProfile profile = new SimpleEnergyProfileMock(0);

		List<PowerRate> values = new ArrayList<PowerRate>();
		PowerRate rate1 = new PowerRateMock(0, false, 0l);
		PowerRate rate2 = new PowerRateMock(0, false, 10l);
		values.add(rate1);
		values.add(rate2);

		/* Check functionality for find values below. */
		assertNull(profile.getNearestValue(values, -5, true));
		assertEquals(rate1, profile.getNearestValue(values, 0, true));
		assertEquals(rate1, profile.getNearestValue(values, 5, true));
		assertEquals(rate2, profile.getNearestValue(values, 10, true));
		assertEquals(rate2, profile.getNearestValue(values, 15, true));

		/* Check functionality for find values above. */
		assertEquals(rate1, profile.getNearestValue(values, -5, false));
		assertEquals(rate1, profile.getNearestValue(values, 0, false));
		assertEquals(rate2, profile.getNearestValue(values, 5, false));
		assertEquals(rate2, profile.getNearestValue(values, 10, false));
		assertNull(profile.getNearestValue(values, 15, false));
	}

	@Test
	public void testGetNearestValue04() {
		SimpleEnergyProfile profile = new SimpleEnergyProfileMock(0);

		List<PowerRate> values = new ArrayList<PowerRate>();
		PowerRate rate1 = new PowerRateMock(0, false, 0l);
		PowerRate rate2 = new PowerRateMock(0, false, 10l);
		PowerRate rate3 = new PowerRateMock(0, false, 20l);
		values.add(rate1);
		values.add(rate2);
		values.add(rate3);

		/* Check functionality for find values below. */
		assertNull(profile.getNearestValue(values, -5, true));
		assertEquals(rate1, profile.getNearestValue(values, 0, true));
		assertEquals(rate1, profile.getNearestValue(values, 5, true));
		assertEquals(rate2, profile.getNearestValue(values, 10, true));
		assertEquals(rate2, profile.getNearestValue(values, 15, true));
		assertEquals(rate3, profile.getNearestValue(values, 20, true));
		assertEquals(rate3, profile.getNearestValue(values, 25, true));

		/* Check functionality for find values above. */
		assertEquals(rate1, profile.getNearestValue(values, -5, false));
		assertEquals(rate1, profile.getNearestValue(values, 0, false));
		assertEquals(rate2, profile.getNearestValue(values, 5, false));
		assertEquals(rate2, profile.getNearestValue(values, 10, false));
		assertEquals(rate3, profile.getNearestValue(values, 15, false));
		assertEquals(rate3, profile.getNearestValue(values, 20, false));
		assertNull(profile.getNearestValue(values, 25, false));
	}

	@Test
	public void testGetPeakPowerRatePositive01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfileMock(0);

		assertEquals(0d, profile.getPeakPowerRate(), 0d);
	}

	@Test
	public void testGetPeakPowerRatePositive02() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false));

		assertEquals(-10d, profile.getPeakPowerRate(), 0d);
	}

	@Test
	public void testGetPeakPowerRatePositive03() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false));
		profile.addPowerRateValue(new PowerRateMock(-20l, false));
		profile.addPowerRateValue(new PowerRateMock(-15l, false));

		assertEquals(-20d, profile.getPeakPowerRate(), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogEventNegative01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.logEvent(null, new PowerRateMock(0l, false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogEventNegative02() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.logEvent("", new PowerRateMock(0l, false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogEventNegative03() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.logEvent("id1", new PowerRateMock(0l, false));
		profile.logEvent("id1", new PowerRateMock(0l, false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogEventNegative04() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.logEvent("id1", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogEventNegative05() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.logEvent("Start", new PowerRateMock(0l, false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogEventNegative06() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();

		profile.logEvent("End", new PowerRateMock(0l, false));
	}

	@Test
	public void testLogEventPostive01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		PowerRateMock value = new PowerRateMock(0l, false);

		profile.logEvent("id1", value);
		assertTrue(profile.getMeasuredValues().contains(value));
	}

	@Test
	public void testToStringPositive01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfileMock(0);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("Energy Profile\n");
		expectedResult.append("duration: 0.0 secs\n");
		expectedResult.append("avg. profiling frequenzy: 0.0 Hz\n");
		expectedResult.append("consumption: 0.0 mJ\n");
		expectedResult.append("peak: 0.0 mW");

		assertEquals(expectedResult.toString(), profile.toString());
	}

	@Test
	public void testToStringPositive02() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000l));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("Energy Profile\n");
		expectedResult.append("duration: 0.05 secs\n");
		expectedResult.append("avg. profiling frequenzy: 20.0 Hz\n");
		expectedResult.append("consumption: 0.5 mJ\n");
		expectedResult.append("peak: -10.0 mW");

		assertEquals(expectedResult.toString(), profile.toString());
	}

	@Test
	public void testToStringPositive03() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.logEvent("ev01", new PowerRateMock(-15l, false, 50000000l));
		profile.addPowerRateValue(new PowerRateMock(-20l, false, 100000000l));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(100000000l);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("Energy Profile\n");
		expectedResult.append("duration: 0.1 secs\n");
		expectedResult.append("avg. profiling frequenzy: 20.0 Hz\n");
		expectedResult.append("consumption: 1.5 mJ\n");
		expectedResult.append("peak: -20.0 mW\n");
		expectedResult
				.append("* Start - ev01: [0.05 secs, 0.625 mJ, peak: -15 mW]\n");
		expectedResult
				.append("* ev01 - End: [0.05 secs, 0.875 mJ, peak: -20 mW]");

		assertEquals(expectedResult.toString(), profile.toString());
	}
}
