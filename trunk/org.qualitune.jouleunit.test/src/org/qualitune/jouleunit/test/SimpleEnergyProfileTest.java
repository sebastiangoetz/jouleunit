package org.qualitune.jouleunit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
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
	public void testGetPeakPowerRatePositive01() {
		SimpleEnergyProfile profile = new SimpleEnergyProfileMock(0);

		assertEquals(0l, profile.getPeakPowerRate());
	}

	@Test
	public void testGetPeakPowerRatePositive02() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false));

		assertEquals(-10l, profile.getPeakPowerRate());
	}

	@Test
	public void testGetPeakPowerRatePositive03() {
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false));
		profile.addPowerRateValue(new PowerRateMock(-20l, false));
		profile.addPowerRateValue(new PowerRateMock(-15l, false));

		assertEquals(-20l, profile.getPeakPowerRate());
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
		expectedResult.append("peak: 0 mW");

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
		expectedResult.append("peak: -10 mW");

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
		expectedResult.append("peak: -20 mW\n");
		expectedResult
				.append("* Start - ev01: [0.05 secs, 0.625 mJ, peak: -15 mW]\n");
		expectedResult
				.append("* ev01 - End: [0.05 secs, 0.875 mJ, peak: -20 mW]");

		assertEquals(expectedResult.toString(), profile.toString());
	}
}
