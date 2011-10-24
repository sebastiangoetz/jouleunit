package org.qualitune.jouleunit.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.qualitune.jouleunit.CompositeEnergyProfile;
import org.qualitune.jouleunit.SimpleEnergyProfile;

/** Test cases for the class {@link CompositeEnergyProfile}. */
public class CompositeEnergyProfileTest {

	@Test
	public void testGetAvgProfilingFrequencyPositive01() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		assertEquals(0, profile.getAvgProfilingFrequency(), 0d);
	}

	@Test
	public void testGetAvgProfilingFrequencyPositive02() {

		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		subProfile1.addPowerRateValue(new PowerRateMock(0l, true, 0l));
		subProfile1.addPowerRateValue(new PowerRateMock(0l, true, 1000000000l));
		subProfile1.setStartedNanoTime(0l);
		subProfile1.setStopNanoTime(1000000000l);
		profile.addProfile(subProfile1);

		assertEquals(1, subProfile1.getAvgProfilingFrequency(), 0d);
	}

	@Test
	public void testGetAvgProfilingFrequencyPositive03() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		subProfile1.addPowerRateValue(new PowerRateMock(0l, true, 0l));
		subProfile1.addPowerRateValue(new PowerRateMock(0l, true, 1000000000l));
		subProfile1.setStartedNanoTime(0l);
		subProfile1.setStopNanoTime(1000000000l);
		profile.addProfile(subProfile1);
		
		SimpleEnergyProfile subProfile2 = new SimpleEnergyProfile();
		subProfile2.addPowerRateValue(new PowerRateMock(0l, true, 0l));
		subProfile2.addPowerRateValue(new PowerRateMock(0l, true, 500000000l));
		subProfile2.setStartedNanoTime(0l);
		subProfile2.setStopNanoTime(1000000000l);
		profile.addProfile(subProfile2);
	
		assertEquals(1.5, profile.getAvgProfilingFrequency(), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddProfileNegtative01() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.addProfile(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative01() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.getConsumedEnergy(null, CompositeEnergyProfile.END_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative02() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.getConsumedEnergy(CompositeEnergyProfile.START_EVENT_ID, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative03() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.getConsumedEnergy(CompositeEnergyProfile.END_EVENT_ID,
				CompositeEnergyProfile.START_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative04() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.getConsumedEnergy("test", CompositeEnergyProfile.END_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConsumedEnergyNegative05() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.getConsumedEnergy(CompositeEnergyProfile.START_EVENT_ID, "test");
	}

	@Test
	public void testGetConsumedEnergyPositive01() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		assertEquals(0d, profile.getConsumedEnergy(), 0d);
	}

	@Test
	public void testGetConsumedEnergyPositive02() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		subProfile1.addPowerRateValue(new PowerRateMock(-10, true, 0l));
		profile.addProfile(subProfile1);

		assertEquals(0d, profile.getConsumedEnergy(), 0d);
	}

	@Test
	public void testGetConsumedEnergyPositive03() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		subProfile1.addPowerRateValue(new PowerRateMock(-10, true, 0l));
		subProfile1
				.addPowerRateValue(new PowerRateMock(-20, true, 50000000000l));
		profile.addProfile(subProfile1);

		assertEquals(750d, profile.getConsumedEnergy(), 0d);
	}

	@Test
	public void testGetConsumedEnergyPositive04() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		profile.addProfile(subProfile1);

		subProfile1.addPowerRateValue(new PowerRateMock(-10l, true, 0l));
		subProfile1.logEvent("ev01",
				new PowerRateMock(-20l, true, 50000000000l));
		profile.logEvent("ev01", new PowerRateMock(-20l, true, 50000000000l));
		subProfile1.logEvent("ev02", new PowerRateMock(-20l, true,
				100000000000l));
		profile.logEvent("ev02", new PowerRateMock(-20l, true, 100000000000l));
		subProfile1.addPowerRateValue(new PowerRateMock(-10l, true,
				150000000000l));

		assertEquals(2500d, profile.getConsumedEnergy(), 0d);
		assertEquals(2500d, profile.getConsumedEnergy(
				CompositeEnergyProfile.START_EVENT_ID,
				CompositeEnergyProfile.END_EVENT_ID), 0d);
		assertEquals(750d, profile.getConsumedEnergy(
				CompositeEnergyProfile.START_EVENT_ID, "ev01"), 0d);
		assertEquals(1000d, profile.getConsumedEnergy("ev01", "ev02"), 0d);
		assertEquals(750d, profile.getConsumedEnergy("ev02",
				CompositeEnergyProfile.END_EVENT_ID), 0d);
	}

	@Test
	public void testGetConsumedEnergyPositive05() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		profile.addProfile(subProfile1);
		SimpleEnergyProfile subProfile2 = new SimpleEnergyProfile();
		profile.addProfile(subProfile2);

		subProfile1.addPowerRateValue(new PowerRateMock(-10l, true, 0l));
		subProfile2.addPowerRateValue(new PowerRateMock(-10l, true, 0l));

		subProfile1.logEvent("ev01",
				new PowerRateMock(-20l, true, 50000000000l));
		subProfile2.logEvent("ev01",
				new PowerRateMock(-20l, true, 50000000000l));
		profile.logEvent("ev01", new PowerRateMock(-20l, true, 50000000000l));

		subProfile1.logEvent("ev02", new PowerRateMock(-20l, true,
				100000000000l));
		subProfile2.logEvent("ev02", new PowerRateMock(-20l, true,
				100000000000l));
		profile.logEvent("ev02", new PowerRateMock(-20l, true, 100000000000l));

		subProfile1.addPowerRateValue(new PowerRateMock(-10l, true,
				150000000000l));
		subProfile2.addPowerRateValue(new PowerRateMock(-10l, true,
				150000000000l));

		assertEquals(5000d, profile.getConsumedEnergy(), 0d);
		assertEquals(5000d, profile.getConsumedEnergy(
				CompositeEnergyProfile.START_EVENT_ID,
				CompositeEnergyProfile.END_EVENT_ID), 0d);
		assertEquals(1500d, profile.getConsumedEnergy(
				CompositeEnergyProfile.START_EVENT_ID, "ev01"), 0d);
		assertEquals(2000d, profile.getConsumedEnergy("ev01", "ev02"), 0d);
		assertEquals(1500d, profile.getConsumedEnergy("ev02",
				CompositeEnergyProfile.END_EVENT_ID), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDurationNegative01() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.getDuration(null, CompositeEnergyProfile.END_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDurationNegative02() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.getDuration(CompositeEnergyProfile.START_EVENT_ID, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDurationNegative03() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.getDuration("test", CompositeEnergyProfile.END_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDurationNegative04() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.getDuration(CompositeEnergyProfile.START_EVENT_ID, "test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDurationNegative05() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.getDuration(CompositeEnergyProfile.END_EVENT_ID,
				CompositeEnergyProfile.START_EVENT_ID);
	}

	@Test
	public void testGetDurationPositive01() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		profile.addProfile(subProfile1);
		subProfile1.addPowerRateValue(new PowerRateMock(0l, true, 0l));

		subProfile1.logEvent("ev01", new PowerRateMock(0l, true, 50000000000l));
		profile.logEvent("ev01", new PowerRateMock(0l, true, 50000000000l));
		subProfile1
				.logEvent("ev02", new PowerRateMock(0l, true, 150000000000l));
		profile.logEvent("ev02", new PowerRateMock(0l, true, 150000000000l));
		subProfile1
				.addPowerRateValue(new PowerRateMock(0l, true, 200000000000l));

		assertEquals(200000000000l, profile.getDuration());
		assertEquals(200000000000l, profile.getDuration(
				CompositeEnergyProfile.START_EVENT_ID,
				CompositeEnergyProfile.END_EVENT_ID));
		assertEquals(50000000000l, profile.getDuration(
				CompositeEnergyProfile.START_EVENT_ID, "ev01"));
		assertEquals(100000000000l, profile.getDuration("ev01", "ev02"));
		assertEquals(50000000000l, profile.getDuration("ev02",
				CompositeEnergyProfile.END_EVENT_ID));
	}

	@Test
	public void testGetDurationPositive02() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		profile.addProfile(subProfile1);
		SimpleEnergyProfile subProfile2 = new SimpleEnergyProfile();
		profile.addProfile(subProfile2);

		subProfile1.addPowerRateValue(new PowerRateMock(0l, true, 0l));
		subProfile2.addPowerRateValue(new PowerRateMock(0l, true, 0l));

		subProfile1.logEvent("ev01", new PowerRateMock(0l, true, 50000000000l));
		subProfile2.logEvent("ev01", new PowerRateMock(0l, true, 50000000000l));
		profile.logEvent("ev01", new PowerRateMock(0l, true, 50000000000l));

		subProfile1
				.logEvent("ev02", new PowerRateMock(0l, true, 150000000000l));
		subProfile2
				.logEvent("ev02", new PowerRateMock(0l, true, 150000000000l));
		profile.logEvent("ev02", new PowerRateMock(0l, true, 150000000000l));

		subProfile1
				.addPowerRateValue(new PowerRateMock(0l, true, 200000000000l));
		subProfile2
				.addPowerRateValue(new PowerRateMock(0l, true, 200000000000l));

		assertEquals(200000000000l, profile.getDuration());
		assertEquals(200000000000l, profile.getDuration(
				CompositeEnergyProfile.START_EVENT_ID,
				CompositeEnergyProfile.END_EVENT_ID));
		assertEquals(50000000000l, profile.getDuration(
				CompositeEnergyProfile.START_EVENT_ID, "ev01"));
		assertEquals(100000000000l, profile.getDuration("ev01", "ev02"));
		assertEquals(50000000000l, profile.getDuration("ev02",
				CompositeEnergyProfile.END_EVENT_ID));
	}

	@Test
	public void testGetPeakPowerRatePositive01() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		assertEquals(0l, profile.getPeakPowerRate());
	}

	@Test
	public void testGetPeakPowerRatePositive02() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		profile.addProfile(subProfile1);
		subProfile1.addPowerRateValue(new PowerRateMock(-10l, false));

		assertEquals(-10l, profile.getPeakPowerRate());
	}

	@Test
	public void testGetPeakPowerRatePositive03() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		profile.addProfile(subProfile1);
		subProfile1.addPowerRateValue(new PowerRateMock(-10l, false));
		subProfile1.addPowerRateValue(new PowerRateMock(-20l, false));
		subProfile1.addPowerRateValue(new PowerRateMock(-15l, false));

		assertEquals(-20l, profile.getPeakPowerRate());
	}

	@Test
	public void testGetPeakPowerRatePositive04() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		profile.addProfile(subProfile1);
		subProfile1.addPowerRateValue(new PowerRateMock(-100l, false, 0l));
		subProfile1.addPowerRateValue(new PowerRateMock(-200l, false, 50l));
		subProfile1.addPowerRateValue(new PowerRateMock(-150l, false, 100l));

		SimpleEnergyProfile subProfile2 = new SimpleEnergyProfile();
		profile.addProfile(subProfile2);
		subProfile2.addPowerRateValue(new PowerRateMock(-100l, false, 0l));
		subProfile2.addPowerRateValue(new PowerRateMock(-100l, false, 5l));
		subProfile2.addPowerRateValue(new PowerRateMock(-150l, false, 75l));
		subProfile2.addPowerRateValue(new PowerRateMock(-150l, false, 100l));

		assertEquals(-325l, profile.getPeakPowerRate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogEventNegative01() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.logEvent(null, new PowerRateMock(0l, false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogEventNegative02() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.logEvent("", new PowerRateMock(0l, false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogEventNegative03() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.logEvent("id1", new PowerRateMock(0l, false));
		profile.logEvent("id1", new PowerRateMock(0l, false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogEventNegative04() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.logEvent("Start", new PowerRateMock(0l, false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLogEventNegative05() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		profile.logEvent("End", new PowerRateMock(0l, false));
	}

	@Test
	public void testToStringPositive01() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("Composite Energy Profile\n");
		expectedResult.append("duration: 0.0 secs\n");
		expectedResult.append("avg. profiling frequenzy: 0.0 Hz\n");
		expectedResult.append("consumption: 0.0 mJ\n");
		expectedResult.append("peak: 0 mW");

		assertEquals(expectedResult.toString(), profile.toString());
	}

	@Test
	public void testToStringPositive02() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		profile.addProfile(subProfile1);

		subProfile1.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		subProfile1
				.addPowerRateValue(new PowerRateMock(-10l, false, 50000000l));
		subProfile1.setStartedNanoTime(0l);
		subProfile1.setStopNanoTime(50000000l);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("Composite Energy Profile\n");
		expectedResult.append("duration: 0.05 secs\n");
		expectedResult.append("avg. profiling frequenzy: 20.0 Hz\n");
		expectedResult.append("consumption: 0.5 mJ\n");
		expectedResult.append("peak: -10 mW\n");
		expectedResult.append("> Sub Profile #1:\n");
		expectedResult.append("  Energy Profile\n");
		expectedResult.append("  duration: 0.05 secs\n");
		expectedResult.append("  avg. profiling frequenzy: 20.0 Hz\n");
		expectedResult.append("  consumption: 0.5 mJ\n");
		expectedResult.append("  peak: -10 mW");

		assertEquals(expectedResult.toString(), profile.toString());
	}

	@Test
	public void testToStringPositive03() {
		CompositeEnergyProfile profile = new CompositeEnergyProfile();
		SimpleEnergyProfile subProfile1 = new SimpleEnergyProfile();
		profile.addProfile(subProfile1);

		subProfile1.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		subProfile1.logEvent("ev01", new PowerRateMock(-15l, false, 50000000l));
		profile.logEvent("ev01", null);
		subProfile1
				.addPowerRateValue(new PowerRateMock(-20l, false, 100000000l));
		subProfile1.setStartedNanoTime(0l);
		subProfile1.setStopNanoTime(100000000l);

		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("Composite Energy Profile\n");
		expectedResult.append("duration: 0.1 secs\n");
		expectedResult.append("avg. profiling frequenzy: 20.0 Hz\n");
		expectedResult.append("consumption: 1.5 mJ\n");
		expectedResult.append("peak: -20 mW\n");
		expectedResult
				.append("* Start - ev01: [0.05 secs, 0.625 mJ, peak: -15 mW]\n");
		expectedResult
				.append("* ev01 - End: [0.05 secs, 0.875 mJ, peak: -20 mW]\n");
		expectedResult.append("> Sub Profile #1:\n");
		expectedResult.append("  Energy Profile\n");
		expectedResult.append("  duration: 0.1 secs\n");
		expectedResult.append("  avg. profiling frequenzy: 20.0 Hz\n");
		expectedResult.append("  consumption: 1.5 mJ\n");
		expectedResult.append("  peak: -20 mW\n");
		expectedResult
				.append("  * Start - ev01: [0.05 secs, 0.625 mJ, peak: -15 mW]\n");
		expectedResult
				.append("  * ev01 - End: [0.05 secs, 0.875 mJ, peak: -20 mW]");

		assertEquals(expectedResult.toString(), profile.toString());
	}
}
