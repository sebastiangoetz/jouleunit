package org.qualitune.jouleunit;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.qualitune.jouleunit.persist.EnergyProfileHistory;

/**
 * Contains assert methods for energy consumption.
 * 
 * @author Claas Wilke
 */
public final class JouleAssert {

	/** Private constructor. Static class only. */
	private JouleAssert() {
		/* No content. */
	}

	/**
	 * Compares an expected average energy consumption in <code>mJ</code> with
	 * the measured values from a {@link Collection} of {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @param expectedConsumption
	 *            The expected consumption in <code>mJ</code>.
	 */
	public static void assertAvgJoules(Collection<EnergyProfile> profiles,
			double expectedConsumption) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument profiles must not be null or empty.");
		// no else.

		double totalConsumption = 0;

		for (EnergyProfile profile : profiles)
			totalConsumption += profile.getConsumedEnergy();
		// end for.

		double averageConsumption = totalConsumption / profiles.size();

		if (averageConsumption > expectedConsumption)
			efail("Average energy consumption was expected to be "
					+ expectedConsumption + " at maximum. But was "
					+ averageConsumption + ".");
		// no else.
	}

	/**
	 * Checks whether or not a given {@link EnergyProfile} represents the best
	 * profiled energy consumption for all {@link EnergyProfile}s stored for
	 * this identifier.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}s.
	 * @param identifier
	 *            Used to identify the associated {@link EnergyProfile}s for a
	 *            certain assert statement. <strong>Should be unique for each
	 *            assert statement to avoid side effects.</strong>
	 */
	public static void assertBestEver(EnergyProfile profile, String identifier) {
		assertBestEver(profile, identifier, 0f, 0d);
	}

	/**
	 * Checks whether or not a given {@link EnergyProfile} represents the best
	 * profiled energy consumption for all {@link EnergyProfile}s stored for
	 * this identifier.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}s.
	 * @param identifier
	 *            Used to identify the associated {@link EnergyProfile}s for a
	 *            certain assert statement. <strong>Should be unique for each
	 *            assert statement to avoid side effects.</strong>
	 * @param relativeDelta
	 *            The maximum relative delta two profiles shall have to fulfill
	 *            the assertion Given in percentage (0.0 .. 1.0). <strong>The
	 *            maximum of the relative and absolute delta will be used during
	 *            assertion checking.</strong>
	 * @param absoluteDelta
	 *            The maximum absolute delta two profiles shall have to fulfill
	 *            the assertion (given in <code>mJ</code>, >= 0.0). <strong>The
	 *            maximum of the relative and absolute delta will be used during
	 *            assertion checking.</strong>
	 */
	public static void assertBestEver(EnergyProfile profile, String identifier,
			float relativeDelta, double absoluteDelta) {

		if (profile == null)
			throw new IllegalArgumentException(
					"Argument profile must not be null.");
		else if (identifier == null || identifier.length() == 0)
			throw new IllegalArgumentException(
					"Argument identifier must not be null or empty.");
		else if (relativeDelta < 0.0d || relativeDelta > 1.0d)
			throw new IllegalArgumentException(
					"The relativeDelta must lay between 0.0 and 1.0");
		else if (absoluteDelta < 0.0d)
			throw new IllegalArgumentException(
					"The absoulteDelta can not be less than 0.0");
		// no else.

		try {
			/* Get the best profiler for this identifier from the history. */
			EnergyProfile bestProfile = EnergyProfileHistory.INSTANCE
					.getBestProfileEver(identifier);

			/* Check if the profile is better (with or without delta). */
			if (bestProfile != null
					&& profile.getConsumedEnergy() > (bestProfile
							.getConsumedEnergy() * (1 + relativeDelta))
					&& profile.getConsumedEnergy() > (bestProfile
							.getConsumedEnergy() + absoluteDelta)) {
				efail("Expected best consumption was "
						+ profile.getConsumedEnergy()
						+ "mJ but best consumption was "
						+ bestProfile.getConsumedEnergy() + "mJ.");
			}

			else {
				/*
				 * Replace the profile in the history (only if better without
				 * delta).
				 */
				if (bestProfile == null
						|| bestProfile.getConsumedEnergy() > profile
								.getConsumedEnergy()) {
					EnergyProfileHistory.INSTANCE.dropProfiles(identifier);
					EnergyProfileHistory.INSTANCE.storeProfile(profile,
							identifier);
				}
				// no else.
			}
		}

		catch (SQLException e) {
			efail("Error during access of profile history: " + e.getMessage());
		}
	}

	/**
	 * Checks whether or not a given {@link EnergyProfile} represents the best
	 * profiled energy consumption of a given number of the last N
	 * {@link EnergyProfile}s stored for this identifier.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}s.
	 * @param identifier
	 *            Used to identify the associated {@link EnergyProfile}s for a
	 *            certain assert statement. <strong>Should be unique for each
	 *            assert statement to avoid side effects.</strong>
	 * @param numberOfRuns
	 *            The number of runs of which the given {@link EnergyProfile}
	 *            should be the best one.
	 */
	public static void assertBestSince(EnergyProfile profile,
			String identifier, int numberOfRuns) {

		assertBestSince(profile, identifier, numberOfRuns, 0f, 0d);
	}

	/**
	 * Checks whether or not a given {@link EnergyProfile} represents the best
	 * profiled energy consumption of a given number of the last N
	 * {@link EnergyProfile}s stored for this identifier.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}s.
	 * @param identifier
	 *            Used to identify the associated {@link EnergyProfile}s for a
	 *            certain assert statement. <strong>Should be unique for each
	 *            assert statement to avoid side effects.</strong>
	 * @param numberOfRuns
	 *            The number of runs of which the given {@link EnergyProfile}
	 *            should be the best one.
	 * @param relativeDelta
	 *            The maximum relative delta two profiles shall have to fulfill
	 *            the assertion Given in percentage (0.0 .. 1.0). <strong>The
	 *            maximum of the relative and absolute delta will be used during
	 *            assertion checking.</strong>
	 * @param absoluteDelta
	 *            The maximum absolute delta two profiles shall have to fulfill
	 *            the assertion (given in <code>mJ</code>, >= 0.0). <strong>The
	 *            maximum of the relative and absolute delta will be used during
	 *            assertion checking.</strong>
	 */
	public static void assertBestSince(EnergyProfile profile,
			String identifier, int numberOfRuns, float relativeDelta,
			double absoluteDelta) {

		if (profile == null)
			throw new IllegalArgumentException(
					"Argument profile must not be null.");
		else if (identifier == null || identifier.length() == 0)
			throw new IllegalArgumentException(
					"Argument identifier must not be null or empty.");
		else if (numberOfRuns < 1)
			throw new IllegalArgumentException(
					"The numberOfRuns must be at least 1.");
		else if (relativeDelta < 0.0d || relativeDelta > 1.0d)
			throw new IllegalArgumentException(
					"The relativeDelta must lay between 0.0 and 1.0");
		else if (absoluteDelta < 0.0d)
			throw new IllegalArgumentException(
					"The absoulteDelta can not be less than 0.0");
		// no else.

		try {
			/* Drop unnecessary values from the history. */
			EnergyProfileHistory.INSTANCE.dropTooOldValues(identifier,
					numberOfRuns - 1);

			/* Get the best profiler for this identifier from the history. */
			EnergyProfile bestProfile = EnergyProfileHistory.INSTANCE
					.getBestProfileEver(identifier);

			/* Check if the profile is better (with or without delta). */
			if (bestProfile != null
					&& profile.getConsumedEnergy() > (bestProfile
							.getConsumedEnergy() * (1 + relativeDelta))
					&& profile.getConsumedEnergy() > (bestProfile
							.getConsumedEnergy() + absoluteDelta)) {
				efail("Expected best consumption was "
						+ profile.getConsumedEnergy()
						+ "mJ but best consumption was "
						+ bestProfile.getConsumedEnergy() + "mJ.");
			}
			// no else.

			EnergyProfileHistory.INSTANCE.storeProfile(profile, identifier);
		}

		catch (SQLException e) {
			efail("Error during access of profile history: " + e.getMessage());
		}
	}

	/**
	 * Checks whether or not a given {@link EnergyProfile} represents the best
	 * profiled energy consumption of all {@link EnergyProfile}s stored for this
	 * identifier since a given point in time as a {@link Date}.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}s.
	 * @param identifier
	 *            Used to identify the associated {@link EnergyProfile}s for a
	 *            certain assert statement. <strong>Should be unique for each
	 *            assert statement to avoid side effects.</strong>
	 * @param pointInTime
	 *            The point in time (as a {@link Date}) since when all
	 *            {@link EnergyProfile}s for the given identifier shall be
	 *            checked.
	 */
	public static void assertBestSince(EnergyProfile profile,
			String identifier, Date pointInTime) {

		assertBestSince(profile, identifier, pointInTime, 0f, 0d);
	}

	/**
	 * Checks whether or not a given {@link EnergyProfile} represents the best
	 * profiled energy consumption of all {@link EnergyProfile}s stored for this
	 * identifier since a given point in time as a {@link Date}.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}s.
	 * @param identifier
	 *            Used to identify the associated {@link EnergyProfile}s for a
	 *            certain assert statement. <strong>Should be unique for each
	 *            assert statement to avoid side effects.</strong>
	 * @param pointInTime
	 *            The point in time (as a {@link Date}) since when all
	 *            {@link EnergyProfile}s for the given identifier shall be
	 *            checked.
	 * @param relativeDelta
	 *            The maximum relative delta two profiles shall have to fulfill
	 *            the assertion Given in percentage (0.0 .. 1.0). <strong>The
	 *            maximum of the relative and absolute delta will be used during
	 *            assertion checking.</strong>
	 * @param absoluteDelta
	 *            The maximum absolute delta two profiles shall have to fulfill
	 *            the assertion (given in <code>mJ</code>, >= 0.0). <strong>The
	 *            maximum of the relative and absolute delta will be used during
	 *            assertion checking.</strong>
	 */
	public static void assertBestSince(EnergyProfile profile,
			String identifier, Date pointInTime, float relativeDelta,
			double absoluteDelta) {

		if (profile == null)
			throw new IllegalArgumentException(
					"Argument profile must not be null.");
		else if (identifier == null || identifier.length() == 0)
			throw new IllegalArgumentException(
					"Argument identifier must not be null or empty.");
		else if (pointInTime == null || pointInTime.after(new Date()))
			throw new IllegalArgumentException(
					"The pointInTime must not be null and must represent a date in the past.");
		else if (relativeDelta < 0.0d || relativeDelta > 1.0d)
			throw new IllegalArgumentException(
					"The relativeDelta must lay between 0.0 and 1.0");
		else if (absoluteDelta < 0.0d)
			throw new IllegalArgumentException(
					"The absoulteDelta can not be less than 0.0");
		// no else.

		try {
			/*
			 * Convert date into time stamp. Is a bit imprecise but should work
			 * in general.
			 */
			Long differenceInMillis = new Date().getTime()
					- pointInTime.getTime();
			Long differenceInNanos = System.nanoTime()
					- (differenceInMillis * 1000000l);

			/* Drop unnecessary values from the history. */
			EnergyProfileHistory.INSTANCE.dropTooOldValuesByTimeStamp(
					identifier, differenceInNanos);

			/* Get the best profiler for this identifier from the history. */
			EnergyProfile bestProfile = EnergyProfileHistory.INSTANCE
					.getBestProfileEver(identifier);

			/* Check if the profile is better (with or without delta). */
			if (bestProfile != null
					&& profile.getConsumedEnergy() > (bestProfile
							.getConsumedEnergy() * (1 + relativeDelta))
					&& profile.getConsumedEnergy() > (bestProfile
							.getConsumedEnergy() + absoluteDelta)) {
				efail("Expected best consumption was "
						+ profile.getConsumedEnergy()
						+ "mJ but best consumption was "
						+ bestProfile.getConsumedEnergy() + "mJ.");
			}
			// no else.

			EnergyProfileHistory.INSTANCE.storeProfile(profile, identifier);
		}

		catch (SQLException e) {
			efail("Error during access of profile history: " + e.getMessage());
		}
	}

	/**
	 * Checks whether or not a given {@link EnergyProfile} represents a
	 * decreased energy consumption of a given percentage or absolute delta
	 * w.r.t. a given number of the last N {@link EnergyProfile}s stored for
	 * this identifier.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}s.
	 * @param identifier
	 *            Used to identify the associated {@link EnergyProfile}s for a
	 *            certain assert statement. <strong>Should be unique for each
	 *            assert statement to avoid side effects.</strong>
	 * @param numberOfRuns
	 *            The number of runs of which the given {@link EnergyProfile}
	 *            should be compared.
	 * @param relativeDelta
	 *            The relative delta used to check the assertion given in
	 *            percentage (0.0 .. 1.0). <strong>The maximum of the relative
	 *            and absolute delta will be used during assertion
	 *            checking.</strong>
	 * @param absoluteDelta
	 *            The absolute delta used to check the assertion (given in
	 *            <code>mJ</code>, >= 0.0). <strong>The maximum of the relative
	 *            and absolute delta will be used during assertion
	 *            checking.</strong>
	 */
	public static void assertDecrease(EnergyProfile profile, String identifier,
			int numberOfRuns, float relativeDelta, double absoluteDelta) {

		if (profile == null)
			throw new IllegalArgumentException(
					"Argument profile must not be null.");
		else if (identifier == null || identifier.length() == 0)
			throw new IllegalArgumentException(
					"Argument identifier must not be null or empty.");
		else if (numberOfRuns < 1)
			throw new IllegalArgumentException(
					"The numberOfRuns must be at least 1.");
		else if (relativeDelta < 0.0d || relativeDelta > 1.0d)
			throw new IllegalArgumentException(
					"The relativeDelta must lay between 0.0 and 1.0");
		else if (absoluteDelta < 0.0d)
			throw new IllegalArgumentException(
					"The absoulteDelta can not be less than 0.0");
		else if (relativeDelta == 0f && absoluteDelta == 0d)
			throw new IllegalArgumentException(
					"Either the relative or the absoluteDelta must be greater than 0.");
		// no else.

		try {
			/* Drop unnecessary values from the history. */
			if (numberOfRuns - 1 > 0)
				EnergyProfileHistory.INSTANCE.dropTooOldValues(identifier,
						numberOfRuns - 1);
			// no else.

			/* Get last N values from the history. */
			List<EnergyProfile> lastNProfiles = EnergyProfileHistory.INSTANCE
					.getProfiles(identifier, numberOfRuns);

			/* Check if the profile is better (with or without delta). */
			if (lastNProfiles.size() > 0) {

				EnergyProfile oldestProfile = lastNProfiles.get(0);
				if (oldestProfile.getConsumedEnergy() * (1 - relativeDelta) < profile
						.getConsumedEnergy()
						|| (oldestProfile.getConsumedEnergy() - absoluteDelta) < profile
								.getConsumedEnergy()) {

					efail("Expected decreasing consumption was "
							+ Math.min(oldestProfile.getConsumedEnergy()
									* (1 - relativeDelta),
									oldestProfile.getConsumedEnergy()
											- absoluteDelta)
							+ "mJ but best consumption was "
							+ profile.getConsumedEnergy() + "mJ.");
				}
			}
			// no else.

			EnergyProfileHistory.INSTANCE.storeProfile(profile, identifier);
		}

		catch (SQLException e) {
			efail("Error during access of profile history: " + e.getMessage());
		}
	}

	/**
	 * Checks whether or not a given {@link EnergyProfile} represents a
	 * decreased energy consumption of a given percentage or absolute delta
	 * w.r.t. to all {@link EnergyProfile}s stored for this identifier since a
	 * given point in time as a {@link Date}.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}s.
	 * @param identifier
	 *            Used to identify the associated {@link EnergyProfile}s for a
	 *            certain assert statement. <strong>Should be unique for each
	 *            assert statement to avoid side effects.</strong>
	 * @param pointInTime
	 *            The point in time (as a {@link Date}) since when all
	 *            {@link EnergyProfile}s for the given identifier shall be
	 *            checked.
	 * @param relativeDelta
	 *            The relative delta used to check the assertion given in
	 *            percentage (0.0 .. 1.0). <strong>The maximum of the relative
	 *            and absolute delta will be used during assertion
	 *            checking.</strong>
	 * @param absoluteDelta
	 *            The absolute delta used to check the assertion (given in
	 *            <code>mJ</code>, >= 0.0). <strong>The maximum of the relative
	 *            and absolute delta will be used during assertion
	 *            checking.</strong>
	 */
	public static void assertDecrease(EnergyProfile profile, String identifier,
			Date pointInTime, float relativeDelta, double absoluteDelta) {

		if (profile == null)
			throw new IllegalArgumentException(
					"Argument profile must not be null.");
		else if (identifier == null || identifier.length() == 0)
			throw new IllegalArgumentException(
					"Argument identifier must not be null or empty.");
		else if (pointInTime == null || pointInTime.after(new Date()))
			throw new IllegalArgumentException(
					"The pointInTime must not be null and must represent a date in the past.");
		else if (relativeDelta < 0.0d || relativeDelta > 1.0d)
			throw new IllegalArgumentException(
					"The relativeDelta must lay between 0.0 and 1.0");
		else if (absoluteDelta < 0.0d)
			throw new IllegalArgumentException(
					"The absoulteDelta can not be less than 0.0");
		else if (relativeDelta == 0f && absoluteDelta == 0d)
			throw new IllegalArgumentException(
					"Either the relative or the absoluteDelta must be greater than 0.");
		// no else.

		try {
			/*
			 * Convert date into time stamp. Is a bit imprecise but should work
			 * in general.
			 */
			Long differenceInMillis = new Date().getTime()
					- pointInTime.getTime();
			Long differenceInNanos = System.nanoTime()
					- (differenceInMillis * 1000000l);

			/* Drop unnecessary values from the history. */
			EnergyProfileHistory.INSTANCE.dropTooOldValuesByTimeStamp(
					identifier, differenceInNanos);

			/* Get last N values from the history. */
			List<EnergyProfile> lastNProfiles = EnergyProfileHistory.INSTANCE
					.getProfiles(identifier);

			/* Check if the profile is better (with or without delta). */
			if (lastNProfiles.size() > 0) {

				EnergyProfile oldestProfile = lastNProfiles.get(0);
				if (oldestProfile.getConsumedEnergy() * (1 - relativeDelta) < profile
						.getConsumedEnergy()
						|| (oldestProfile.getConsumedEnergy() - absoluteDelta) < profile
								.getConsumedEnergy()) {

					efail("Expected decreasing consumption was "
							+ Math.min(oldestProfile.getConsumedEnergy()
									* (1 - relativeDelta),
									oldestProfile.getConsumedEnergy()
											- absoluteDelta)
							+ "mJ but best consumption was "
							+ profile.getConsumedEnergy() + "mJ.");
				}
			}
			// no else.

			EnergyProfileHistory.INSTANCE.storeProfile(profile, identifier);
		}

		catch (SQLException e) {
			efail("Error during access of profile history: " + e.getMessage());
		}
	}

	/**
	 * Compares an expected energy consumption in <code>mJ</code> with a
	 * measured value from an {@link EnergyProfile}.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}.
	 * @param expectedConsumption
	 *            The expected consumption in <code>mJ</code>.
	 */
	public static void assertMaxJoules(EnergyProfile profile,
			double expectedConsumption) {

		if (profile == null)
			throw new IllegalArgumentException(
					"Argument profile must not be null.");
		// no else.

		if (expectedConsumption < profile.getConsumedEnergy()) {
			efail("Expected maximal energy consumption was "
					+ expectedConsumption + " but was "
					+ profile.getConsumedEnergy());
		}
		// no else.
	}

	/**
	 * Compares an expected energy consumption in <code>mJ</code> with a
	 * measured values from a {@link Collection} of {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @param expectedConsumption
	 *            The expected consumption in <code>mJ</code>.
	 */
	public static void assertMaxJoules(Collection<EnergyProfile> profiles,
			double expectedConsumption) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument profiles must not be null or empty.");
		// no else.

		for (EnergyProfile profile : profiles)
			assertMaxJoules(profile, expectedConsumption);
		// end for.
	}

	/**
	 * TODO Add assertMaxWatts assertion that takes a minimum time interval as a
	 * second parameter.
	 */

	/**
	 * Compares an expected energy consumption peak in <code>mW</code> with the
	 * real measured values from an {@link EnergyProfile}. <strong>Please note
	 * that the peak highly depends on the refresh rate within the
	 * {@link JouleProfiler} used for the {@link EnergyProfile}'s
	 * creation.</strong>
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}.
	 * @param expectedPeak
	 *            The expected peak in <code>mW</code>.
	 */
	public static void assertMaxWatts(EnergyProfile profile, double expectedPeak) {

		if (profile == null)
			throw new IllegalArgumentException(
					"Argument profile must not be null.");
		// no else.

		if ((-1) * expectedPeak > profile.getPeakPowerRate()) {
			efail("Expected peak energy consumption rate was " + expectedPeak
					+ " but was " + (-1) * profile.getPeakPowerRate());
		}
		// no else.
	}

	/**
	 * Compares an expected energy consumption peak in <code>mW</code> with the
	 * real measured values from a {@link Collection} of {@link EnergyProfile}s.
	 * <strong>Please note that the peak highly depends on the refresh rate
	 * within the {@link JouleProfiler} used for the {@link EnergyProfile}s'
	 * creation.</strong>
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @param expectedPeak
	 *            The expected peak in <code>mW</code>.
	 */
	public static void assertMaxWatts(Collection<EnergyProfile> profiles,
			double expectedPeak) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument profiles must not be null or empty.");
		// no else.

		for (EnergyProfile profile : profiles)
			assertMaxWatts(profile, expectedPeak);
		// end for.
	}

	/**
	 * Fail method that can be used for EUnit test. Simply throws and
	 * {@link AssertionError} with the given exception message.
	 * 
	 * @param msg
	 *            The message of the failure.
	 */
	public static void efail(String msg) {
		throw new AssertionError(msg);
	}
}
