package org.qualitune.jouleunit.test.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.SimpleEnergyProfile;
import org.qualitune.jouleunit.persist.EnergyProfileHistory;
import org.qualitune.jouleunit.test.SimpleEnergyProfileMock;
import org.qualitune.jouleunit.test.PowerRateMock;

/**
 * Test cases for the {@link EnergyProfileHistory} class.
 * 
 * @author Claas Wilke
 */
public class EnergyProfileHistoryTest {

	@Test(expected = IllegalArgumentException.class)
	public void testDropProfilesNegative01() throws SQLException {

		EnergyProfileHistory.INSTANCE.dropProfiles(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDropProfilesNegative02() throws SQLException {

		EnergyProfileHistory.INSTANCE.dropProfiles("");
	}

	/**
	 * Tests both storing and reading an {@link EnergyProfile}.
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void testDropProfilesPositive01() throws SQLException {

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testDropProfilesPositive01");

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));

		/* Test storing / reading of one profile. */
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testDropProfilesPositive01");

		List<EnergyProfile> profiles = EnergyProfileHistory.INSTANCE
				.getProfiles("testDropProfilesPositive01", 1);
		assertNotNull(profiles);
		assertEquals(1, profiles.size());
		assertEquals(profile1.getConsumedEnergy(), profiles.get(0)
				.getConsumedEnergy(), 0d);
		assertEquals(profile1.getDuration(), profiles.get(0).getDuration());

		/* Drop the profile. */
		EnergyProfileHistory.INSTANCE
				.dropProfiles("testDropProfilesPositive01");

		profiles = EnergyProfileHistory.INSTANCE.getProfiles(
				"testDropProfilesPositive01", 2);
		assertNotNull(profiles);
		assertEquals(0, profiles.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDropTooOldValuesNegative01() throws SQLException {

		EnergyProfileHistory.INSTANCE.dropTooOldValues(null, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDropTooOldValuesNegative02() throws SQLException {

		EnergyProfileHistory.INSTANCE.dropTooOldValues("", 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDropTooOldValuesNegative03() throws SQLException {

		EnergyProfileHistory.INSTANCE.dropTooOldValues(
				"testDropTooOldValuesNegative03", 0);
	}

	/**
	 * Tests both storing and reading an {@link EnergyProfile}.
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void testDropTooOldValuesPositive01() throws SQLException {

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testDropTooOldValuesPositive01");

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));

		SimpleEnergyProfile profile2 = new SimpleEnergyProfile();
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 100000000l));
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 150000000));

		/* Test storing / reading of one profile. */
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testDropTooOldValuesPositive01");
		EnergyProfileHistory.INSTANCE.storeProfile(profile2,
				"testDropTooOldValuesPositive01");

		List<EnergyProfile> profiles = EnergyProfileHistory.INSTANCE
				.getProfiles("testDropTooOldValuesPositive01", 2);
		assertNotNull(profiles);
		assertEquals(2, profiles.size());
		assertEquals(profile1.getConsumedEnergy(), profiles.get(0)
				.getConsumedEnergy(), 0d);
		assertEquals(profile1.getDuration(), profiles.get(0).getDuration());
		assertEquals(profile2.getConsumedEnergy(), profiles.get(1)
				.getConsumedEnergy(), 0d);
		assertEquals(profile2.getDuration(), profiles.get(1).getDuration());

		/* Drop the profile. */
		EnergyProfileHistory.INSTANCE.dropTooOldValues(
				"testDropTooOldValuesPositive01", 1);

		profiles = EnergyProfileHistory.INSTANCE.getProfiles(
				"testDropTooOldValuesPositive01", 2);
		assertNotNull(profiles);
		assertEquals(1, profiles.size());
		assertEquals(profile2.getConsumedEnergy(), profiles.get(0)
				.getConsumedEnergy(), 0d);
		assertEquals(profile2.getDuration(), profiles.get(0).getDuration());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDropTooOldValuesByTimeStampNegative01() throws SQLException {

		EnergyProfileHistory.INSTANCE.dropTooOldValuesByTimeStamp(null,
				50000000l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDropTooOldValuesByTimeStampNegative02() throws SQLException {

		EnergyProfileHistory.INSTANCE
				.dropTooOldValuesByTimeStamp("", 50000000l);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDropTooOldValuesByTimeStampNegative03() throws SQLException {

		EnergyProfileHistory.INSTANCE.dropTooOldValuesByTimeStamp(
				"testDropTooOldValuesByTimeStampNegative04", Long.MAX_VALUE);
	}

	/**
	 * Tests both storing and reading an {@link EnergyProfile}.
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void testDropTooOldValuesByTimeStampPositive01() throws SQLException {

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testDropTooOldValuesByTimeStampPositive01");

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));

		SimpleEnergyProfile profile2 = new SimpleEnergyProfile();
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 100000000l));
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 150000000));

		/* Test storing / reading of one profile. */
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testDropTooOldValuesByTimeStampPositive01");
		EnergyProfileHistory.INSTANCE.storeProfile(profile2,
				"testDropTooOldValuesByTimeStampPositive01");

		List<EnergyProfile> profiles = EnergyProfileHistory.INSTANCE
				.getProfiles("testDropTooOldValuesByTimeStampPositive01", 2);
		assertNotNull(profiles);
		assertEquals(2, profiles.size());
		assertEquals(profile1.getConsumedEnergy(), profiles.get(0)
				.getConsumedEnergy(), 0d);
		assertEquals(profile1.getDuration(), profiles.get(0).getDuration());
		assertEquals(profile2.getConsumedEnergy(), profiles.get(1)
				.getConsumedEnergy(), 0d);
		assertEquals(profile2.getDuration(), profiles.get(1).getDuration());

		/* Drop the profile. */
		EnergyProfileHistory.INSTANCE.dropTooOldValuesByTimeStamp(
				"testDropTooOldValuesByTimeStampPositive01", 100000000l);

		profiles = EnergyProfileHistory.INSTANCE.getProfiles(
				"testDropTooOldValuesByTimeStampPositive01", 2);
		assertNotNull(profiles);
		assertEquals(1, profiles.size());
		assertEquals(profile2.getConsumedEnergy(), profiles.get(0)
				.getConsumedEnergy(), 0d);
		assertEquals(profile2.getDuration(), profiles.get(0).getDuration());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetBestProfileEverNegative01() throws SQLException {

		EnergyProfileHistory.INSTANCE.getBestProfileEver(null);
	}

	/**
	 * Tests both storing and reading an {@link EnergyProfile}.
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void testGetBestProfileEverPositive01() throws SQLException {

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testGetBestProfileEverPositive01");

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile1.setStartedNanoTime(0l);
		profile1.setStopNanoTime(50000000l);
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testGetBestProfileEverPositive01");

		SimpleEnergyProfile profile2 = new SimpleEnergyProfile();
		profile2.addPowerRateValue(new PowerRateMock(-500, false, 50000000l));
		profile2.addPowerRateValue(new PowerRateMock(-500, false, 100000000l));
		profile2.setStartedNanoTime(50000000l);
		profile2.setStopNanoTime(100000000l);
		EnergyProfileHistory.INSTANCE.storeProfile(profile2,
				"testGetBestProfileEverPositive01");

		SimpleEnergyProfile profile3 = new SimpleEnergyProfile();
		profile3.addPowerRateValue(new PowerRateMock(-2000, false, 100000000l));
		profile3.addPowerRateValue(new PowerRateMock(-2000, false, 150000000l));
		profile3.setStartedNanoTime(100000000l);
		profile3.setStopNanoTime(150000000l);
		EnergyProfileHistory.INSTANCE.storeProfile(profile3,
				"testGetBestProfileEverPositive01");

		EnergyProfile result = EnergyProfileHistory.INSTANCE
				.getBestProfileEver("testGetBestProfileEverPositive01");
		assertNotNull(result);
		assertEquals(profile2.getConsumedEnergy(), result.getConsumedEnergy(),
				0d);
		assertEquals(profile2.getDuration(), result.getDuration());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetProfilesNegative01() throws SQLException {

		EnergyProfileHistory.INSTANCE.getProfiles(null, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetProfilesNegative02() throws SQLException {

		EnergyProfile profile = new SimpleEnergyProfileMock(20);
		EnergyProfileHistory.INSTANCE.storeProfile(profile,
				"testGetProfilesNegative02");

		EnergyProfileHistory.INSTANCE.getProfiles("testGetProfilesNegative02",
				-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetProfilesNegative03() throws SQLException {

		EnergyProfile profile = new SimpleEnergyProfileMock(20);
		EnergyProfileHistory.INSTANCE.storeProfile(profile,
				"testGetProfilesNegative03");

		EnergyProfileHistory.INSTANCE.getProfiles("testGetProfilesNegative03",
				0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetProfilesNegative04() throws SQLException {

		EnergyProfileHistory.INSTANCE.getProfiles("", 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetProfilesNegative05() throws SQLException {

		EnergyProfileHistory.INSTANCE.getProfiles(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetProfilesNegative06() throws SQLException {

		EnergyProfileHistory.INSTANCE.getProfiles("");
	}

	@Test
	public void testGetProfilesPositive01() throws SQLException {

		EnergyProfileHistory.INSTANCE.dropProfiles("testGetProfilesPositive01");

		SimpleEnergyProfile profile01 = new SimpleEnergyProfile();
		profile01.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile01
				.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		EnergyProfileHistory.INSTANCE.storeProfile(profile01,
				"testGetProfilesPositive01");

		List<EnergyProfile> result = EnergyProfileHistory.INSTANCE
				.getProfiles("testGetProfilesPositive01");
		assertEquals(1, result.size());
		assertEquals(profile01.getConsumedEnergy(), result.get(0)
				.getConsumedEnergy(), 0l);
		assertEquals(profile01.getDuration(), result.get(0).getDuration(), 0l);

		result = EnergyProfileHistory.INSTANCE.getProfiles(
				"testGetProfilesPositive01", 1);
		assertEquals(1, result.size());
		assertEquals(profile01.getConsumedEnergy(), result.get(0)
				.getConsumedEnergy(), 0l);
		assertEquals(profile01.getDuration(), result.get(0).getDuration(), 0l);

		SimpleEnergyProfile profile02 = new SimpleEnergyProfile();
		profile02.addPowerRateValue(new PowerRateMock(-10l, false,
				100000000000l));
		profile02.addPowerRateValue(new PowerRateMock(-10l, false,
				150000000000l));

		EnergyProfileHistory.INSTANCE.storeProfile(profile02,
				"testGetProfilesPositive01");

		result = EnergyProfileHistory.INSTANCE
				.getProfiles("testGetProfilesPositive01");
		assertEquals(2, result.size());
		assertEquals(profile01.getConsumedEnergy(), result.get(0)
				.getConsumedEnergy(), 0l);
		assertEquals(profile01.getDuration(), result.get(0).getDuration(), 0l);
		assertEquals(profile02.getConsumedEnergy(), result.get(1)
				.getConsumedEnergy(), 0l);
		assertEquals(profile02.getDuration(), result.get(1).getDuration(), 0l);

		result = EnergyProfileHistory.INSTANCE.getProfiles(
				"testGetProfilesPositive01", 2);
		assertEquals(2, result.size());
		assertEquals(profile01.getConsumedEnergy(), result.get(0)
				.getConsumedEnergy(), 0l);
		assertEquals(profile01.getDuration(), result.get(0).getDuration(), 0l);
		assertEquals(profile02.getConsumedEnergy(), result.get(1)
				.getConsumedEnergy(), 0l);
		assertEquals(profile02.getDuration(), result.get(1).getDuration(), 0l);

		EnergyProfileHistory.INSTANCE.dropProfiles("testGetProfilesPositive01");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoreProfileNegative01() throws SQLException {

		EnergyProfileHistory.INSTANCE.storeProfile(null,
				"testStoreProfileNegative01");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoreProfileNegative02() throws SQLException {

		EnergyProfile profile = new SimpleEnergyProfileMock(20);
		EnergyProfileHistory.INSTANCE.storeProfile(profile, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoreProfileNegative03() throws SQLException {

		EnergyProfile profile = new SimpleEnergyProfileMock(20);
		EnergyProfileHistory.INSTANCE.storeProfile(profile, "");
	}

	/**
	 * Tests both storing and reading an {@link EnergyProfile}.
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void testStoreGetProfilesPositive01() throws SQLException {

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testStoreGetProfilesPositive01");

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile1.setStartedNanoTime(0l);
		profile1.setStopNanoTime(50000000l);

		/* Test storing / reading of one profile. */
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testStoreGetProfilesPositive01");

		List<EnergyProfile> profiles = EnergyProfileHistory.INSTANCE
				.getProfiles("testStoreGetProfilesPositive01", 1);
		assertNotNull(profiles);
		assertEquals(1, profiles.size());
		assertEquals(profile1.getConsumedEnergy(), profiles.get(0)
				.getConsumedEnergy(), 0d);
		assertEquals(profile1.getDuration(), profiles.get(0).getDuration());

		/* Test storing / reading of a second profile. */
		SimpleEnergyProfile profile2 = new SimpleEnergyProfile();
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 50000000l));
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 100000000l));
		profile2.setStartedNanoTime(0l);
		profile2.setStopNanoTime(50000000l);

		EnergyProfileHistory.INSTANCE.storeProfile(profile2,
				"testStoreGetProfilesPositive01");

		profiles = EnergyProfileHistory.INSTANCE.getProfiles(
				"testStoreGetProfilesPositive01", 2);
		assertNotNull(profiles);
		assertEquals(2, profiles.size());
		assertEquals(profile1.getConsumedEnergy(), profiles.get(0)
				.getConsumedEnergy(), 0d);
		assertEquals(profile1.getDuration(), profiles.get(0).getDuration());
		assertEquals(profile2.getConsumedEnergy(), profiles.get(1)
				.getConsumedEnergy(), 0d);
		assertEquals(profile2.getDuration(), profiles.get(1).getDuration());
	}

	/**
	 * Tests both storing and reading an {@link EnergyProfile}.
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void testStoreGetProfilesPositive02() throws SQLException,
			InterruptedException {

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testStoreGetProfilesPositive02");

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 50000000l));

		/* Test storing / reading of one profile. */
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testStoreGetProfilesPositive02");

		/* Try to get more profiles than possible. */
		List<EnergyProfile> profiles = EnergyProfileHistory.INSTANCE
				.getProfiles("testStoreGetProfilesPositive02", 10);
		assertNotNull(profiles);
		assertTrue(profiles.size() <= 10);
		assertEquals(profile1.getConsumedEnergy(),
				profiles.get(profiles.size() - 1).getConsumedEnergy(), 0d);
		assertEquals(profile1.getDuration(), profiles.get(profiles.size() - 1)
				.getDuration());

		/* Test storing / reading of a second profile. */
		SimpleEnergyProfile profile2 = new SimpleEnergyProfile();
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 50000000l));
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 100000000l));

		EnergyProfileHistory.INSTANCE.storeProfile(profile2,
				"testStoreGetProfilesPositive02");

		/* Try to get less profiles than possible. */
		profiles = EnergyProfileHistory.INSTANCE.getProfiles(
				"testStoreGetProfilesPositive02", 1);
		assertNotNull(profiles);
		assertEquals(1, profiles.size());
		assertEquals(profile2.getConsumedEnergy(), profiles.get(0)
				.getConsumedEnergy(), 0d);
		assertEquals(profile2.getDuration(), profiles.get(0).getDuration());
	}

	/**
	 * Tests both storing and reading an {@link EnergyProfile}.
	 * 
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void testStoreGetProfilesPositive03() throws SQLException,
			InterruptedException {

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testStoreGetProfilesPositive03");

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 50000000l));

		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testStoreGetProfilesPositive03");

		SimpleEnergyProfile profile2 = new SimpleEnergyProfile();
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 50000000l));
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 100000000l));

		EnergyProfileHistory.INSTANCE.storeProfile(profile2,
				"testStoreGetProfilesPositive03");

		/* Get two profiles. */
		List<EnergyProfile> profiles = EnergyProfileHistory.INSTANCE
				.getProfiles("testStoreGetProfilesPositive03", 2);
		assertEquals(2, profiles.size());

		/* Get one profile. */
		profiles = EnergyProfileHistory.INSTANCE.getProfiles(
				"testStoreGetProfilesPositive03", 1);
		assertEquals(1, profiles.size());

		/*
		 * Get two profiles. However, the older one should be deleted from the
		 * history to save space.
		 */
		profiles = EnergyProfileHistory.INSTANCE.getProfiles(
				"testStoreGetProfilesPositive03", 2);
		assertEquals(1, profiles.size());
	}
}
