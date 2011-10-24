package org.qualitune.jouleunit.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.qualitune.jouleunit.CompositeEnergyProfile;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.JouleUtil;
import org.qualitune.jouleunit.SimpleEnergyProfile;

/**
 * Test cases for {@link JouleUtil}.
 * 
 * @author Claas Wilke
 */
public class JouleUtilTest {

	public static JouleProfiler profiler = new JouleProfilerMock(1000, -20,
			false);

	@BeforeClass
	public static void setUp() {
		profiler.setEstimationInterval(0);

		/* Probe effect and base consumption shall not be considered here. */
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.setProbeEffectConsiderationEnabled(false);
	}

	/**
	 * Test case for {@link JouleUtil#deserializeProfile(File)}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeserializeProfileNegative01() throws IOException,
			ClassNotFoundException {
		JouleUtil.deserializeProfile(null);
	}

	/**
	 * Test case for {@link JouleUtil#deserializeProfile(File)}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test(expected = IOException.class)
	public void testDeserializeProfileNegative02() throws IOException,
			ClassNotFoundException {
		File testFile = new File("./resources/serialization/text.eprf");
		JouleUtil.deserializeProfile(testFile);
	}

	/**
	 * Test case for {@link JouleUtil#deserializeProfile(File)}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testDeserializeProfilePositive01() throws IOException,
			ClassNotFoundException {
		File testFile = new File("./resources/serialization/test.eprf");

		if (!testFile.exists())
			testFile.createNewFile();
		// no else.

		EnergyProfile profile = new SimpleEnergyProfileMock(20);
		JouleUtil.serializeProfile(profile, testFile);

		EnergyProfile deSerializedProfile = JouleUtil
				.deserializeProfile(testFile);
		assertEquals(profile.getConsumedEnergy(),
				deSerializedProfile.getConsumedEnergy(), 0d);
	}

	/**
	 * Test case for {@link JouleUtil#deserializeProfiles(File)}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeserializeProfilesNegative01() throws IOException,
			ClassNotFoundException {
		JouleUtil.deserializeProfiles(null);
	}

	/**
	 * Test case for {@link JouleUtil#deserializeProfiles(File)}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test(expected = IOException.class)
	public void testDeserializeProfilesNegative02() throws IOException,
			ClassNotFoundException {
		File testFile = new File("./resources/serialization/text.eprf");
		JouleUtil.deserializeProfiles(testFile);
	}

	/**
	 * Test case for {@link JouleUtil#deserializeProfiles(File)}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testDeserializeProfilesPositive01() throws IOException,
			ClassNotFoundException {
		File testFile = new File("./resources/serialization/test.eprf");

		if (!testFile.exists())
			testFile.createNewFile();
		// no else.

		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>(3);
		profiles.add(new SimpleEnergyProfileMock(20));
		profiles.add(new SimpleEnergyProfileMock(15));
		profiles.add(new SimpleEnergyProfileMock(17));

		JouleUtil.serializeProfiles(profiles, testFile);

		List<EnergyProfile> deSerializedProfiles = JouleUtil
				.deserializeProfiles(testFile);

		assertEquals(profiles.size(), deSerializedProfiles.size());

		for (int index = 0; index < profiles.size(); index++)
			assertEquals(profiles.get(index).getConsumedEnergy(),
					deSerializedProfiles.get(index).getConsumedEnergy(), 0d);
		// end for.
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationAvgNegative01() {
		JouleUtil.getDurationAvg(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationAvgNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getDurationAvg(profiles);
	}

	@Test
	public void testDurationAvgPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		EnergyProfile mock = new SimpleEnergyProfileMock(0l, 10l);
		profiles.add(mock);
		assertEquals(10l, JouleUtil.getDurationAvg(profiles), 0d);
	}

	@Test
	public void testDurationAvgPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		EnergyProfile mock = new SimpleEnergyProfileMock(0l, 100l);
		profiles.add(mock);
		mock = new SimpleEnergyProfileMock(0l, 300l);
		profiles.add(mock);
		mock = new SimpleEnergyProfileMock(0l, 900l);
		profiles.add(mock);
		mock = new SimpleEnergyProfileMock(0l, 1200l);
		profiles.add(mock);

		assertEquals(625d, JouleUtil.getDurationAvg(profiles), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationMaxNegative01() {
		JouleUtil.getDurationMax(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationMaxNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getDurationMax(profiles);
	}

	@Test
	public void testDurationMaxPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 20l));
		assertEquals(20l, JouleUtil.getDurationMax(profiles), 0d);
	}

	@Test
	public void testDurationMaxPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 1l));
		profiles.add(new SimpleEnergyProfileMock(0l, 3l));
		profiles.add(new SimpleEnergyProfileMock(0l, 9l));
		profiles.add(new SimpleEnergyProfileMock(0l, 12l));

		assertEquals(12l, JouleUtil.getDurationMax(profiles), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationMedianNegative01() {
		JouleUtil.getDurationMedian(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationMedianNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getDurationMedian(profiles);
	}

	@Test
	public void testDurationMedianPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 20l));
		assertEquals(20l, JouleUtil.getDurationMedian(profiles), 0d);
	}

	@Test
	public void testDurationMedianPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 1l));
		profiles.add(new SimpleEnergyProfileMock(0l, 3l));
		profiles.add(new SimpleEnergyProfileMock(0l, 9l));
		profiles.add(new SimpleEnergyProfileMock(0l, 12l));

		assertEquals(9d, JouleUtil.getDurationMedian(profiles), 0d);
	}

	@Test
	public void testDurationMedianPositive03() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 1l));
		profiles.add(new SimpleEnergyProfileMock(0l, 3l));
		profiles.add(new SimpleEnergyProfileMock(0l, 9l));
		profiles.add(new SimpleEnergyProfileMock(0l, 12l));
		profiles.add(new SimpleEnergyProfileMock(0l, 15l));

		assertEquals(9d, JouleUtil.getDurationMedian(profiles), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationMinNegative01() {
		JouleUtil.getJouleMin(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationMinNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getDurationMin(profiles);
	}

	@Test
	public void testDurationMinPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 20l));
		assertEquals(20l, JouleUtil.getDurationMin(profiles), 0d);
	}

	@Test
	public void testDurationMinPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 1l));
		profiles.add(new SimpleEnergyProfileMock(0l, 3l));
		profiles.add(new SimpleEnergyProfileMock(0l, 9l));
		profiles.add(new SimpleEnergyProfileMock(0l, 12l));

		assertEquals(1l, JouleUtil.getDurationMin(profiles), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationStdDevNegative01() {
		JouleUtil.getDurationStdDev(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationStdDevNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getDurationStdDev(profiles);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationStdDevNegative03() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(20l));
		JouleUtil.getDurationStdDev(profiles);
	}

	@Test
	public void testDurationStdDevPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 20l));
		profiles.add(new SimpleEnergyProfileMock(0l, 20l));
		double stdDev = JouleUtil.getDurationStdDev(profiles);
		assertEquals(0d, stdDev, 0d);
	}

	@Test
	public void testDurationStdDevPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 10l));
		profiles.add(new SimpleEnergyProfileMock(0l, 20l));
		double stdDev = JouleUtil.getDurationStdDev(profiles);
		assertEquals(7.071067812d, stdDev, 0.00001d);
	}

	@Test
	public void testDurationStdDevPositive03() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 10l));
		profiles.add(new SimpleEnergyProfileMock(0l, 20l));
		profiles.add(new SimpleEnergyProfileMock(0l, 30l));
		profiles.add(new SimpleEnergyProfileMock(0l, 40l));
		profiles.add(new SimpleEnergyProfileMock(0l, 50l));
		double stdDev = JouleUtil.getDurationStdDev(profiles);
		assertEquals(15.8113883d, stdDev, 0.00001d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationStdErrorNegative01() {
		JouleUtil.getDurationStdError(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationStdErrorNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getDurationStdError(profiles);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDurationStdErrorNegative03() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(20l));
		JouleUtil.getDurationStdError(profiles);
	}

	@Test
	public void testDurationStdErrorPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 20l));
		profiles.add(new SimpleEnergyProfileMock(0l, 20l));
		double stdError = JouleUtil.getDurationStdError(profiles);
		assertEquals(0d, stdError, 0d);
	}

	@Test
	public void testDurationStdErrorPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 10l));
		profiles.add(new SimpleEnergyProfileMock(0l, 20l));
		double stdError = JouleUtil.getDurationStdError(profiles);
		assertEquals(5d, stdError, 0.00001d);
	}

	@Test
	public void testDurationStdErrorPositive03() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(0l, 10l));
		profiles.add(new SimpleEnergyProfileMock(0l, 20l));
		profiles.add(new SimpleEnergyProfileMock(0l, 30l));
		profiles.add(new SimpleEnergyProfileMock(0l, 40l));
		profiles.add(new SimpleEnergyProfileMock(0l, 50l));
		double stdError = JouleUtil.getDurationStdError(profiles);
		assertEquals(7.0710678d, stdError, 0.00001d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExportProfileNegative01() {

		File file = new File("test.csv");
		JouleUtil.exportProfile(null, file, "testRun");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExportProfileNegative02() {

		JouleUtil.exportProfile(new SimpleEnergyProfile(), null, "testRun");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExportProfileNegative03() {

		File file = new File("./");
		JouleUtil.exportProfile(new SimpleEnergyProfile(), file, "testRun");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExportProfileNegative04() {

		File file = new File("test.csv");
		JouleUtil.exportProfile(new SimpleEnergyProfile(), file, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExportProfileNegative05() {

		File file = new File("test.csv");
		JouleUtil.exportProfile(new SimpleEnergyProfile(), file, "");
	}

	@Test
	public void testExportProfilePositive01() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));
		profile.logEvent("ev01", new PowerRateMock(-12l, false, 100000000000l));
		profile.addPowerRateValue(new PowerRateMock(-11l, false, 150000000000l));
		profile.addPowerRateValue(new PowerRateMock(-11l, false, 200000000000l));
		profile.logEvent("ev02", new PowerRateMock(-12l, false, 250000000000l));
		profile.addPowerRateValue(new PowerRateMock(-13l, false, 300000000000l));
		profile.addPowerRateValue(new PowerRateMock(-9l, false, 350000000000l));

		File file = new File("test1.csv");
		JouleUtil.exportProfile(profile, file, "testRun");

		/* TODO */
	}

	@Test
	public void testExportProfilePositive02() {

		CompositeEnergyProfile compProfile = new CompositeEnergyProfile();

		// TODO include tests for logged events.

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));
		profile1.logEvent("ev01", new PowerRateMock(-12l, false, 100000000000l));
		profile1.addPowerRateValue(new PowerRateMock(-11l, false, 150000000000l));
		profile1.addPowerRateValue(new PowerRateMock(-11l, false, 200000000000l));
		profile1.logEvent("ev02", new PowerRateMock(-12l, false, 250000000000l));
		profile1.addPowerRateValue(new PowerRateMock(-13l, false, 300000000000l));
		profile1.addPowerRateValue(new PowerRateMock(-9l, false, 350000000000l));

		SimpleEnergyProfile profile2 = new SimpleEnergyProfile();
		profile2.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));
		// profile2.logEvent("ev01", new PowerRateMock(-12l, false,
		// 100000000000l));
		profile2.addPowerRateValue(new PowerRateMock(-11l, false, 150000000000l));
		profile2.addPowerRateValue(new PowerRateMock(-11l, false, 200000000000l));
		// profile2.logEvent("ev02", new PowerRateMock(-12l, false,
		// 250000000000l));
		profile2.addPowerRateValue(new PowerRateMock(-13l, false, 300000000000l));
		profile2.addPowerRateValue(new PowerRateMock(-9l, false, 350000000000l));

		compProfile.addProfile(profile1);
		compProfile.addProfile(profile2);
		// compProfile.logEvent("ev01", null);
		// compProfile.logEvent("ev02", null);

		File file = new File("test2.csv");
		JouleUtil.exportProfile(compProfile, file, "testRun");

		/* TODO */
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleAvgNegative01() {
		JouleUtil.getJouleAvg(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleAvgNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getJouleAvg(profiles);
	}

	@Test
	public void testJouleAvgPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(20l));
		assertEquals(20l, JouleUtil.getJouleAvg(profiles), 0d);
	}

	@Test
	public void testJouleAvgPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(1l));
		profiles.add(new SimpleEnergyProfileMock(3l));
		profiles.add(new SimpleEnergyProfileMock(9l));
		profiles.add(new SimpleEnergyProfileMock(12l));

		assertEquals(6.25d, JouleUtil.getJouleAvg(profiles), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleMaxNegative01() {
		JouleUtil.getJouleMax(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleMaxNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getJouleMax(profiles);
	}

	@Test
	public void testJouleMaxPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(20l));
		assertEquals(20l, JouleUtil.getJouleMax(profiles), 0d);
	}

	@Test
	public void testJouleMaxPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(1l));
		profiles.add(new SimpleEnergyProfileMock(3l));
		profiles.add(new SimpleEnergyProfileMock(9l));
		profiles.add(new SimpleEnergyProfileMock(12l));

		assertEquals(12l, JouleUtil.getJouleMax(profiles), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleMedianNegative01() {
		JouleUtil.getJouleMedian(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleMedianNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getJouleMedian(profiles);
	}

	@Test
	public void testJouleMedianPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(20l));
		assertEquals(20l, JouleUtil.getJouleMedian(profiles), 0d);
	}

	@Test
	public void testJouleMedianPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(1l));
		profiles.add(new SimpleEnergyProfileMock(3l));
		profiles.add(new SimpleEnergyProfileMock(9l));
		profiles.add(new SimpleEnergyProfileMock(12l));

		assertEquals(9d, JouleUtil.getJouleMedian(profiles), 0d);
	}

	@Test
	public void testJouleMedianPositive03() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(1l));
		profiles.add(new SimpleEnergyProfileMock(3l));
		profiles.add(new SimpleEnergyProfileMock(9l));
		profiles.add(new SimpleEnergyProfileMock(12l));
		profiles.add(new SimpleEnergyProfileMock(15l));

		assertEquals(9d, JouleUtil.getJouleMedian(profiles), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleMinNegative01() {
		JouleUtil.getJouleMin(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleMinNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getJouleMin(profiles);
	}

	@Test
	public void testJouleMinPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(20l));
		assertEquals(20l, JouleUtil.getJouleMin(profiles), 0d);
	}

	@Test
	public void testJouleMinPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(1l));
		profiles.add(new SimpleEnergyProfileMock(3l));
		profiles.add(new SimpleEnergyProfileMock(9l));
		profiles.add(new SimpleEnergyProfileMock(12l));

		assertEquals(1l, JouleUtil.getJouleMin(profiles), 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleStdDevNegative01() {
		JouleUtil.getJouleStdDev(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleStdDevNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getJouleStdDev(profiles);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleStdDevNegative03() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(20l));
		JouleUtil.getJouleStdDev(profiles);
	}

	@Test
	public void testJouleStdDevPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(20l));
		profiles.add(new SimpleEnergyProfileMock(20l));
		double stdDev = JouleUtil.getJouleStdDev(profiles);
		assertEquals(0d, stdDev, 0d);
	}

	@Test
	public void testJouleStdDevPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10l));
		profiles.add(new SimpleEnergyProfileMock(20l));
		double stdDev = JouleUtil.getJouleStdDev(profiles);
		assertEquals(7.071067812d, stdDev, 0.00001d);
	}

	@Test
	public void testJouleStdDevPositive03() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10l));
		profiles.add(new SimpleEnergyProfileMock(20l));
		profiles.add(new SimpleEnergyProfileMock(30l));
		profiles.add(new SimpleEnergyProfileMock(40l));
		profiles.add(new SimpleEnergyProfileMock(50l));
		double stdDev = JouleUtil.getJouleStdDev(profiles);
		assertEquals(15.8113883d, stdDev, 0.00001d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleStdErrorNegative01() {
		JouleUtil.getJouleStdError(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleStdErrorNegative02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleUtil.getJouleStdError(profiles);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testJouleStdErrorNegative03() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(20l));
		JouleUtil.getJouleStdError(profiles);
	}

	@Test
	public void testJouleStdErrorPositive01() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(20l));
		profiles.add(new SimpleEnergyProfileMock(20l));
		double stdError = JouleUtil.getJouleStdError(profiles);
		assertEquals(0d, stdError, 0d);
	}

	@Test
	public void testJouleStdErrorPositive02() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10l));
		profiles.add(new SimpleEnergyProfileMock(20l));
		double stdError = JouleUtil.getJouleStdError(profiles);
		assertEquals(5d, stdError, 0.00001d);
	}

	@Test
	public void testJouleStdErrorPositive03() {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10l));
		profiles.add(new SimpleEnergyProfileMock(20l));
		profiles.add(new SimpleEnergyProfileMock(30l));
		profiles.add(new SimpleEnergyProfileMock(40l));
		profiles.add(new SimpleEnergyProfileMock(50l));
		double stdError = JouleUtil.getJouleStdError(profiles);
		assertEquals(7.0710678d, stdError, 0.00001d);
	}

	/**
	 * Test case for {@link JouleUtil#serializeProfile(EnergyProfile, File)}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSerializeProfileNegative01() throws IOException,
			ClassNotFoundException {
		EnergyProfile profile = new SimpleEnergyProfileMock(20);
		JouleUtil.serializeProfile(profile, null);
	}

	/**
	 * Test case for {@link JouleUtil#serializeProfile(EnergyProfile, File)}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSerializeProfileNegative02() throws IOException,
			ClassNotFoundException {
		File testFile = new File("./resources/serialization/test.eprf");
		JouleUtil.serializeProfile(null, testFile);
	}

	/**
	 * Test case for {@link JouleUtil#serializeProfile(EnergyProfile, File)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSerializeProfilePositive01() throws IOException {
		File testFile = new File("./resources/serialization/test.eprf");

		if (!testFile.exists())
			testFile.createNewFile();
		// no else.

		EnergyProfile profile = new SimpleEnergyProfileMock(20);
		JouleUtil.serializeProfile(profile, testFile);
	}

	/**
	 * Test case for {@link JouleUtil#serializeProfiles(List, File)}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSerializeProfilesNegative01() throws IOException,
			ClassNotFoundException {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>(3);
		profiles.add(new SimpleEnergyProfileMock(20));
		profiles.add(new SimpleEnergyProfileMock(15));
		profiles.add(new SimpleEnergyProfileMock(17));
		JouleUtil.serializeProfiles(profiles, null);
	}

	/**
	 * Test case for {@link JouleUtil#serializeProfiles(List, File)}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSerializeProfilesNegative02() throws IOException,
			ClassNotFoundException {
		File testFile = new File("./resources/serialization/test.eprf");
		JouleUtil.serializeProfiles(null, testFile);
	}

	/**
	 * Test case for {@link JouleUtil#serializeProfiles(List, File)}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSerializeProfilesNegative03() throws IOException,
			ClassNotFoundException {
		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>(3);
		File testFile = new File("./resources/serialization/test.eprf");
		JouleUtil.serializeProfiles(profiles, testFile);
	}

	/**
	 * Test case for {@link EProfilerUtil#serializeProfiles(List<EProfile>,
	 * File)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSerialzeProfilesPositive01() throws IOException {
		File testFile = new File("./resources/serialization/test.eprf");

		if (!testFile.exists())
			testFile.createNewFile();
		// no else.

		List<EnergyProfile> profiles = new ArrayList<EnergyProfile>(3);
		profiles.add(new SimpleEnergyProfileMock(20));
		profiles.add(new SimpleEnergyProfileMock(15));
		profiles.add(new SimpleEnergyProfileMock(17));
		JouleUtil.serializeProfiles(profiles, testFile);
	}
}
