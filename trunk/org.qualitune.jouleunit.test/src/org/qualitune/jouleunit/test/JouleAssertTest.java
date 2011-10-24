package org.qualitune.jouleunit.test;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleAssert;
import org.qualitune.jouleunit.SimpleEnergyProfile;
import org.qualitune.jouleunit.persist.EnergyProfileHistory;

/**
 * Test cases for the {@link JouleAssertTest} class.
 * 
 * @author Claas Wilke
 */
public class JouleAssertTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAssertAvgJoulesNegative01() {

		JouleAssert.assertAvgJoules((Collection<EnergyProfile>) null, 9.0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertAvgJoulesNegative02() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleAssert.assertAvgJoules(profiles, 9.0d);
	}

	@Test(expected = AssertionError.class)
	public void testAssertAvgJoulesNegative03() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10));
		JouleAssert.assertAvgJoules(profiles, 9.0d);
	}

	@Test(expected = AssertionError.class)
	public void testAssertAvgJoulesNegative04() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(9));
		profiles.add(new SimpleEnergyProfileMock(10));
		JouleAssert.assertAvgJoules(profiles, 9);
	}

	@Test
	public void testAssertAvgJoulesPositive01() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10));
		JouleAssert.assertAvgJoules(profiles, 10d);
	}

	@Test
	public void testAssertAvgJoulesPositive02() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10));
		JouleAssert.assertAvgJoules(profiles, 10.1d);
	}

	@Test
	public void testAssertAvgJoulesPositive03() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10));
		profiles.add(new SimpleEnergyProfileMock(12));
		JouleAssert.assertAvgJoules(profiles, 11.0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestEverNegative01() {

		JouleAssert
				.assertBestEver(null, "testAssertBestEverNegative01", 0f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestEverNegative02() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestEver(profile, null, 0f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestEverNegative03() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestEver(profile, "", 0f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestEverNegative04() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestEver(profile, "testAssertBestEverNegative04",
				-1f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestEverNegative05() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestEver(profile, "testAssertBestEverNegative05",
				1.1f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestEverNegative06() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestEver(profile, "testAssertBestEverNegative06", 0f,
				-10d);
	}

	@Test
	public void testAssertBestEverPositive01() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestEver(profile, "testAssertBestEverPositive01", 0f,
				0d);

		profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1100, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1100, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		boolean failed = false;
		try {
			JouleAssert.assertBestEver(profile, "testAssertBestEverPositive01",
					0f, 0d);
			failed = true;
		} catch (AssertionError e) {
			/* Should fail. */
		}

		if (failed)
			fail("Assertion did not fail as expected.");
		// no else.

		/* With variance it should work again. */
		JouleAssert.assertBestEver(profile, "testAssertBestEverPositive01",
				0.1f, 0d);
		JouleAssert.assertBestEver(profile, "testAssertBestEverPositive01",
				0.0f, 5d);
	}

	@Test
	public void testAssertBestEverPositive02() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestEver(profile, "testAssertBestEverPositive02");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative01() {

		JouleAssert.assertBestSince(null, "testAssertBestSinceNegative01", 1,
				0f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative02() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, null, 1, 0f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative03() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, "", 1, 0f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative04() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, "testAssertBestSinceNegative04",
				0, 0f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative05() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, "testAssertBestSinceNegative05",
				1, -1f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative06() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, "testAssertBestSinceNegative06",
				1, -1.1f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative07() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, "testAssertBestSinceNegative07",
				1, 0f, -1d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative08() {

		JouleAssert.assertBestSince(null, "testAssertBestSinceNegative01",
				new Date(), 0f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative09() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, null, new Date(), 0f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative10() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, "", new Date(), 0f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative11() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, "testAssertBestSinceNegative11",
				null, 0f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative12() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, "testAssertBestSinceNegative12",
				new Date(System.currentTimeMillis() + 1000000000l));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative13() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, "testAssertBestSinceNegative13",
				new Date(), -1f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative14() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, "testAssertBestSinceNegative14",
				new Date(), -1.1f, 0d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertBestSinceNegative15() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile.setStartedNanoTime(0l);
		profile.setStopNanoTime(50000000l);

		JouleAssert.assertBestSince(profile, "testAssertBestSinceNegative15",
				new Date(), 0f, -1d);
	}

	@Test
	public void testAssertBestSincePositive01() throws SQLException {

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testAssertBestSincePositive01");

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile1.setStartedNanoTime(0l);
		profile1.setStopNanoTime(50000000l);
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testAssertBestSincePositive01");

		SimpleEnergyProfile profile2 = new SimpleEnergyProfile();
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 50000000l));
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 100000000l));
		profile2.setStartedNanoTime(50000000l);
		profile2.setStopNanoTime(100000000l);
		EnergyProfileHistory.INSTANCE.storeProfile(profile2,
				"testAssertBestSincePositive01");

		SimpleEnergyProfile profile3 = new SimpleEnergyProfile();
		profile3.addPowerRateValue(new PowerRateMock(-500, false, 100000000l));
		profile3.addPowerRateValue(new PowerRateMock(-500, false, 150000000l));
		profile3.setStartedNanoTime(100000000l);
		profile3.setStopNanoTime(150000000l);

		JouleAssert.assertBestSince(profile3, "testAssertBestSincePositive01",
				2, 0f, 0d);

		profile3 = new SimpleEnergyProfile();
		profile3.addPowerRateValue(new PowerRateMock(-600, false, 150000000l));
		profile3.addPowerRateValue(new PowerRateMock(-600, false, 200000000l));
		profile3.setStartedNanoTime(150000000l);
		profile3.setStopNanoTime(200000000l);

		boolean failed = false;
		try {
			JouleAssert.assertBestSince(profile3,
					"testAssertBestSincePositive01", 3, 0f, 0d);
			failed = true;
		} catch (AssertionError e) {
			/* Should fail. */
		}

		if (failed)
			fail("Assertion did not fail as expected.");
		// no else.

		/* With variance it should work again. */
		JouleAssert.assertBestSince(profile3, "testAssertBestSincePositive01",
				3, 0.2f, 0d);
		JouleAssert.assertBestSince(profile3, "testAssertBestSincePositive01",
				4, 0.0f, 5d);
	}

	@Test
	public void testAssertBestSincePositive02() throws SQLException {

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testAssertBestSincePositive02");

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile1.setStartedNanoTime(0l);
		profile1.setStopNanoTime(50000000l);
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testAssertBestSincePositive02");

		SimpleEnergyProfile profile2 = new SimpleEnergyProfile();
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 50000000l));
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 100000000l));
		profile2.setStartedNanoTime(50000000l);
		profile2.setStopNanoTime(100000000l);
		EnergyProfileHistory.INSTANCE.storeProfile(profile2,
				"testAssertBestSincePositive02");

		SimpleEnergyProfile profile3 = new SimpleEnergyProfile();
		profile3.addPowerRateValue(new PowerRateMock(-500, false, 100000000l));
		profile3.addPowerRateValue(new PowerRateMock(-500, false, 150000000l));
		profile3.setStartedNanoTime(100000000l);
		profile3.setStopNanoTime(150000000l);

		JouleAssert.assertBestSince(profile3, "testAssertBestSincePositive02",
				2);

		profile3 = new SimpleEnergyProfile();
		profile3.addPowerRateValue(new PowerRateMock(-600, false, 150000000l));
		profile3.addPowerRateValue(new PowerRateMock(-600, false, 200000000l));
		profile3.setStartedNanoTime(150000000l);
		profile3.setStopNanoTime(200000000l);

		boolean failed = false;
		try {
			JouleAssert.assertBestSince(profile3,
					"testAssertBestSincePositive02", 3);
			failed = true;
		} catch (AssertionError e) {
			/* Should fail. */
		}

		if (failed)
			fail("Assertion did not fail as expected.");
		// no else.
	}

	@Test
	public void testAssertBestSincePositive03() throws SQLException {

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testAssertBestSincePositive03");

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile1.setStartedNanoTime(0l);
		profile1.setStopNanoTime(50000000l);
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testAssertBestSincePositive03");

		SimpleEnergyProfile profile2 = new SimpleEnergyProfile();
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 50000000l));
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 100000000l));
		profile2.setStartedNanoTime(50000000l);
		profile2.setStopNanoTime(100000000l);
		EnergyProfileHistory.INSTANCE.storeProfile(profile2,
				"testAssertBestSincePositive03");

		SimpleEnergyProfile profile3 = new SimpleEnergyProfile();
		profile3.addPowerRateValue(new PowerRateMock(-500, false, 100000000l));
		profile3.addPowerRateValue(new PowerRateMock(-500, false, 150000000l));
		profile3.setStartedNanoTime(100000000l);
		profile3.setStopNanoTime(150000000l);

		JouleAssert.assertBestSince(profile3, "testAssertBestSincePositive03",
				new Date(0l), 0f, 0d);

		profile3 = new SimpleEnergyProfile();
		profile3.addPowerRateValue(new PowerRateMock(-600, false, 150000000l));
		profile3.addPowerRateValue(new PowerRateMock(-600, false, 200000000l));
		profile3.setStartedNanoTime(150000000l);
		profile3.setStopNanoTime(200000000l);

		boolean failed = false;
		try {
			JouleAssert.assertBestSince(profile3,
					"testAssertBestSincePositive03", new Date(0l), 0f, 0d);
			failed = true;
		} catch (AssertionError e) {
			/* Should fail. */
		}

		if (failed)
			fail("Assertion did not fail as expected.");
		// no else.

		/* With variance it should work again. */
		JouleAssert.assertBestSince(profile3, "testAssertBestSincePositive03",
				new Date(0l), 0.2f, 0d);
		JouleAssert.assertBestSince(profile3, "testAssertBestSincePositive03",
				new Date(0l), 0.0f, 5d);
	}

	@Test
	public void testAssertBestSincePositive04() throws SQLException {

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testAssertBestSincePositive04");

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-1000, false, 50000000));
		profile1.setStartedNanoTime(0l);
		profile1.setStopNanoTime(50000000l);
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testAssertBestSincePositive04");

		SimpleEnergyProfile profile2 = new SimpleEnergyProfile();
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 50000000l));
		profile2.addPowerRateValue(new PowerRateMock(-2000, false, 100000000l));
		profile2.setStartedNanoTime(50000000l);
		profile2.setStopNanoTime(100000000l);
		EnergyProfileHistory.INSTANCE.storeProfile(profile2,
				"testAssertBestSincePositive04");

		SimpleEnergyProfile profile3 = new SimpleEnergyProfile();
		profile3.addPowerRateValue(new PowerRateMock(-500, false, 100000000l));
		profile3.addPowerRateValue(new PowerRateMock(-500, false, 150000000l));
		profile3.setStartedNanoTime(100000000l);
		profile3.setStopNanoTime(150000000l);

		JouleAssert.assertBestSince(profile3, "testAssertBestSincePositive04",
				new Date(0l));

		profile3 = new SimpleEnergyProfile();
		profile3.addPowerRateValue(new PowerRateMock(-600, false, 150000000l));
		profile3.addPowerRateValue(new PowerRateMock(-600, false, 200000000l));
		profile3.setStartedNanoTime(150000000l);
		profile3.setStopNanoTime(200000000l);

		boolean failed = false;
		try {
			JouleAssert.assertBestSince(profile3,
					"testAssertBestSincePositive04", new Date(0l));
			failed = true;
		} catch (AssertionError e) {
			/* Should fail. */
		}

		if (failed)
			fail("Assertion did not fail as expected.");
		// no else.
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative08() {

		JouleAssert.assertDecrease(null, "testAssertDecreaseNegative01",
				new Date(), 0.5f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative09() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, null, new Date(), 0.5f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative10() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, "", new Date(), 0.5f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative11() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, "testAssertDecreaseNegative11",
				null, 0.5f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative12() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, "testAssertDecreaseNegative12",
				new Date(System.currentTimeMillis() + 1000000), 0.5f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative13() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, "testAssertDecreaseNegative13",
				new Date(), -1.0f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative14() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, "testAssertDecreaseNegative14",
				new Date(), 0f, -1f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative15() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, "testAssertDecreaseNegative15",
				new Date(), 0f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative01() {

		JouleAssert.assertDecrease(null, "testAssertDecreaseNegative01", 2,
				0.5f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative02() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, null, 2, 0.5f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative03() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, "", 2, 0.5f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative04() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, "testAssertDecreaseNegative04", 0,
				0.5f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative05() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, "testAssertDecreaseNegative05", 2,
				-1.0f, 0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative06() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, "testAssertDecreaseNegative06", 2,
				0f, -1f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertDecreaseNegative07() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 0l));
		profile.addPowerRateValue(new PowerRateMock(-10l, false, 50000000000l));

		JouleAssert.assertDecrease(profile, "testAssertDecreaseNegative07", 2,
				0f, 0f);
	}

	@Test
	public void testAssertDecreasePositive01() throws SQLException {

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-100l, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-100l, false, 50000000000l));

		EnergyProfile profile2 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-80l, false, 100000000000l));
		profile1.addPowerRateValue(new PowerRateMock(-80l, false, 150000000000l));

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testAssertDecreasePositive01");
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testAssertDecreasePositive01");
		JouleAssert.assertDecrease(profile2, "testAssertDecreasePositive01", 1,
				0.2f, 0f);

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testAssertDecreasePositive01");
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testAssertDecreasePositive01");
		JouleAssert.assertDecrease(profile2, "testAssertDecreasePositive01", 1,
				0.0f, 20l);

		try {
			EnergyProfileHistory.INSTANCE
					.dropProfiles("testAssertDecreasePositive01");
			EnergyProfileHistory.INSTANCE.storeProfile(profile1,
					"testAssertDecreasePositive01");
			JouleAssert.assertDecrease(profile2,
					"testAssertDecreasePositive01", 1, 0.1f, 10l);
			fail("Expected exception was not thrown.");
		}

		catch (AssertionError e) {
			/* Expected exception. */
		}
	}

	@Test
	public void testAssertDecreasePositive02() throws SQLException {

		SimpleEnergyProfile profile1 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-100l, false, 0l));
		profile1.addPowerRateValue(new PowerRateMock(-100l, false, 50000000000l));

		EnergyProfile profile2 = new SimpleEnergyProfile();
		profile1.addPowerRateValue(new PowerRateMock(-80l, false, 100000000000l));
		profile1.addPowerRateValue(new PowerRateMock(-80l, false, 150000000000l));

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testAssertDecreasePositive02");
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testAssertDecreasePositive02");
		JouleAssert.assertDecrease(profile2, "testAssertDecreasePositive02",
				new Date(0l), 0.2f, 0f);

		EnergyProfileHistory.INSTANCE
				.dropProfiles("testAssertDecreasePositive02");
		EnergyProfileHistory.INSTANCE.storeProfile(profile1,
				"testAssertDecreasePositive02");
		JouleAssert.assertDecrease(profile2, "testAssertDecreasePositive02",
				new Date(0l), 0.0f, 20l);

		try {
			EnergyProfileHistory.INSTANCE
					.dropProfiles("testAssertDecreasePositive02");
			EnergyProfileHistory.INSTANCE.storeProfile(profile1,
					"testAssertDecreasePositive02");
			JouleAssert.assertDecrease(profile2,
					"testAssertDecreasePositive02", new Date(0l), 0.1f, 10l);
			fail("Expected exception was not thrown.");
		}

		catch (AssertionError e) {
			/* Expected exception. */
		}
	}

	@Test(expected = AssertionError.class)
	public void testAssertMaxJoulesNegative0() {

		EnergyProfile profile = new SimpleEnergyProfileMock(10);
		JouleAssert.assertMaxJoules(profile, 9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertMaxJoulesNegative02() {

		JouleAssert.assertMaxJoules((EnergyProfile) null, 9);
	}

	@Test(expected = AssertionError.class)
	public void testAssertMaxJoulesNegative03() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10));
		JouleAssert.assertMaxJoules(profiles, 9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertMaxJoulesNegative04() {

		JouleAssert.assertMaxJoules((Collection<EnergyProfile>) null, 9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertMaxJoulesNegative05() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		JouleAssert.assertMaxJoules(profiles, 9);
	}

	@Test(expected = AssertionError.class)
	public void testAssertMaxJoulesNegative06() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(9));
		profiles.add(new SimpleEnergyProfileMock(10));
		JouleAssert.assertMaxJoules(profiles, 9);
	}

	@Test
	public void testAssertMaxJoulesPositive01() {

		EnergyProfile profile = new SimpleEnergyProfileMock(10);
		JouleAssert.assertMaxJoules(profile, 10);
	}

	@Test
	public void testAssertMaxJoulesPositive02() {

		EnergyProfile profile = new SimpleEnergyProfileMock(10);
		JouleAssert.assertMaxJoules(profile, 11);
	}

	@Test
	public void testAssertMaxJoulesPositive03() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10));
		JouleAssert.assertMaxJoules(profiles, 10);
	}

	@Test
	public void testAssertMaxJoulesPositive04() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10));
		JouleAssert.assertMaxJoules(profiles, 11);
	}

	@Test
	public void testAssertMaxJoulesPositive05() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		profiles.add(new SimpleEnergyProfileMock(10));
		profiles.add(new SimpleEnergyProfileMock(11));
		JouleAssert.assertMaxJoules(profiles, 11);
	}

	@Test(expected = AssertionError.class)
	public void testAssertMaxWattsNegative01() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-9, false));
		profile.addPowerRateValue(new PowerRateMock(-10, false));
		profile.addPowerRateValue(new PowerRateMock(-11, false));

		JouleAssert.assertMaxWatts(profile, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertMaxWattsNegative02() {

		JouleAssert.assertMaxWatts((EnergyProfile) null, 10);
	}

	@Test(expected = AssertionError.class)
	public void testAssertMaxWattsNegative03() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10, false));
		profile.addPowerRateValue(new PowerRateMock(-11, false));
		profiles.add(profile);

		JouleAssert.assertMaxWatts(profiles, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertMaxWattsNegative04() {

		JouleAssert.assertMaxWatts((Collection<EnergyProfile>) null, 10);
	}

	@Test(expected = AssertionError.class)
	public void testAssertMaxWattsNegative05() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10, false));
		profile.addPowerRateValue(new PowerRateMock(-9, false));
		profiles.add(profile);
		profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-10, false));
		profile.addPowerRateValue(new PowerRateMock(-11, false));
		profiles.add(profile);

		JouleAssert.assertMaxWatts(profiles, 10);
	}

	@Test
	public void testAssertMaxWattsPositive01() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-8, false));
		profile.addPowerRateValue(new PowerRateMock(-9, false));
		profile.addPowerRateValue(new PowerRateMock(-10, false));

		JouleAssert.assertMaxWatts(profile, 10);
	}

	@Test
	public void testAssertMaxWattsPositive02() {

		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-7, false));
		profile.addPowerRateValue(new PowerRateMock(-8, false));
		profile.addPowerRateValue(new PowerRateMock(-9, false));

		JouleAssert.assertMaxWatts(profile, 10);
	}

	@Test
	public void testAssertMaxWattsPositive03() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-8, false));
		profile.addPowerRateValue(new PowerRateMock(-9, false));
		profile.addPowerRateValue(new PowerRateMock(-10, false));
		profiles.add(profile);

		JouleAssert.assertMaxWatts(profiles, 10);
	}

	@Test
	public void testAssertMaxWattsPositive04() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-7, false));
		profile.addPowerRateValue(new PowerRateMock(-8, false));
		profile.addPowerRateValue(new PowerRateMock(-9, false));
		profiles.add(profile);

		JouleAssert.assertMaxWatts(profiles, 10);
	}

	@Test
	public void testAssertMaxWattsPositive05() {

		Collection<EnergyProfile> profiles = new ArrayList<EnergyProfile>();
		SimpleEnergyProfile profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-7, false));
		profile.addPowerRateValue(new PowerRateMock(-8, false));
		profile.addPowerRateValue(new PowerRateMock(-9, false));
		profiles.add(profile);
		profile = new SimpleEnergyProfile();
		profile.addPowerRateValue(new PowerRateMock(-8, false));
		profile.addPowerRateValue(new PowerRateMock(-9, false));
		profile.addPowerRateValue(new PowerRateMock(-10, false));
		profiles.add(profile);

		JouleAssert.assertMaxWatts(profiles, 10);
	}
}
