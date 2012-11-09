package org.qualitune.jouleunit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.qualitune.jouleunit.persist.RestoredPowerRate;

/**
 * A {@link CompositeEnergyProfile} can be used to combine multiple
 * {@link EnergyProfile}s that have been profiled in parallel.
 * 
 * @author Claas Wilke
 */
public class CompositeEnergyProfile extends AbstractEnergyProfile {

	/** ID for serialization. */
	private static final long serialVersionUID = 5074645306149369742L;

	/**
	 * Contains the {@link EnergyProfile}s of this
	 * {@link CompositeEnergyProfile}.
	 */
	protected List<EnergyProfile> profiles = new ArrayList<EnergyProfile>();

	/**
	 * Contains cached values for the computation of average {@link PowerRate}
	 * values for all sub {@link EnergyProfile}s of this
	 * {@link CompositeEnergyProfile}.
	 */
	protected Map<Long, PowerRate> cachedPowerRateValues = new HashMap<Long, PowerRate>();

	/**
	 * Creates a new {@link CompositeEnergyProfile} with no duration and energy
	 * consumption.
	 */
	public CompositeEnergyProfile() {
		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getAvgProfilingFrequency()
	 */
	public double getAvgProfilingFrequency() {

		double result = 0d;

		if (profiles.size() > 0) {
			for (EnergyProfile profile : profiles)
				result += profile.getAvgProfilingFrequency();
			// end for.

			result = result / profiles.size();
		}
		// no else.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getConsumedEnergy(long, long)
	 * 
	 * TODO test me
	 */
	public double getConsumedEnergy(long start, long end) {

		double result = 0;

		for (EnergyProfile profile : profiles)
			result += profile.getConsumedEnergy(start, end);
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.EnergyProfile#getConsumedEnergy(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public double getConsumedEnergy(String firstID, String secondID) {

		this.checkEventParameters(firstID, secondID);

		double result = 0;

		for (EnergyProfile profile : profiles)
			result += profile.getConsumedEnergy(firstID, secondID);
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getDuration(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public long getDuration(String firstID, String secondID) {

		this.checkEventParameters(firstID, secondID);

		long result = 0;

		for (EnergyProfile profile : profiles)
			result += profile.getDuration(firstID, secondID);
		// end for.

		/* TODO Probably this is too imprecise? */
		if (profiles.size() > 0)
			result = result / profiles.size();
		// no else.

		return result;
	}

	@Override
	public double getPeakPowerRate(String firstID, String secondID) {

		/* Includes parameter preconditions. */
		List<PowerRate> significantValues = getSignificantValues(firstID,
				secondID);

		double result = Double.MAX_VALUE;

		if (profiles.size() == 0)
			result = 0l;
		// no else.

		for (PowerRate value : significantValues) {
			if (value.getPowerRate() < result)
				result = value.getPowerRate();
			// no else.
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.EnergyProfile#getSignificantValues(java.lang.
	 * String, java.lang.String)
	 */
	public List<PowerRate> getSignificantValues(String firstID, String secondID) {

		checkEventParameters(firstID, secondID);

		/* Get significant values for all profiles. */
		List<List<PowerRate>> valuesPerProfile = new ArrayList<List<PowerRate>>(
				profiles.size());

		PowerRate[] lastValueOfEachProfile = new PowerRate[profiles.size()];
		PowerRate[] nextValueOfEachProfile = new PowerRate[profiles.size()];

		int index = 0;
		for (EnergyProfile profile : profiles) {
			List<PowerRate> valuesOfProfile = profile.getSignificantValues(
					firstID, secondID);
			valuesPerProfile.add(valuesOfProfile);

			if (valuesOfProfile.size() > 0)
				nextValueOfEachProfile[index] = valuesOfProfile.remove(0);
			// no else.

			index++;
		}

		List<PowerRate> result = new ArrayList<PowerRate>();

		while (true) {
			/* Pick the lowest from all next values w.r.t. their time stamps. */
			List<Integer> indexesOfNextValue = getIndexOfLowestValue(nextValueOfEachProfile);

			/* If no value remains, break. */
			if (indexesOfNextValue.size() == 0)
				break;
			// no else.

			/*
			 * Compute avg. power rate value for each value ((last + next) / 2)
			 * and their sum.
			 */
			long timeStamp = 0l;
			long powerRate = 0l;

			for (int index1 = 0; index1 < valuesPerProfile.size(); index1++) {
				if (indexesOfNextValue.contains(index1)) {
					timeStamp = nextValueOfEachProfile[index1].getTimeStamp();
					powerRate += nextValueOfEachProfile[index1].getPowerRate();
				}

				else {
					PowerRate lastValue = lastValueOfEachProfile[index1];
					PowerRate nextValue = nextValueOfEachProfile[index1];

					powerRate += getAvgPowerRate(lastValue, nextValue);
				}
			}

			/* Add power rate value to result. */
			if (cachedPowerRateValues.containsKey(timeStamp))
				result.add(cachedPowerRateValues.get(timeStamp));
			else {
				RestoredPowerRate newValue = new RestoredPowerRate();
				newValue.setPowerAdapterOnlineEnabled(false);
				newValue.setPowerRate(powerRate);
				newValue.setTimeStamp(timeStamp);
				result.add(newValue);
				cachedPowerRateValues.put(timeStamp, newValue);
			}

			/* Set the lowest value as its last, pick its next. */
			for (int index1 = 0; index1 < valuesPerProfile.size(); index1++) {
				if (indexesOfNextValue.contains(index1)) {
					lastValueOfEachProfile[index1] = nextValueOfEachProfile[index1];

					if (valuesPerProfile.get(index1).size() > 0)
						nextValueOfEachProfile[index1] = valuesPerProfile.get(
								index1).remove(0);
					else
						nextValueOfEachProfile[index1] = null;
				}
				// no else.
			}

		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getStartedNanoTime()
	 */
	public long getStartedNanoTime() {

		if (profiles.size() > 0) {
			long result = Long.MAX_VALUE;
			for (EnergyProfile profile : profiles)
				result = Math.min(result, profile.getStartedNanoTime());
			// end for.

			return result;
		}

		else
			return 0l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getStopNanoTime()
	 */
	public long getStopNanoTime() {

		if (profiles.size() > 0) {
			long result = Long.MIN_VALUE;
			for (EnergyProfile profile : profiles)
				result = Math.max(result, profile.getStopNanoTime());
			// end for.

			return result;
		}

		else
			return 0l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#logEvent(java.lang.String,
	 * long)
	 * 
	 * TODO test me (the same as other logEvent method)!
	 */
	public void logEvent(String identifier, long timeStamp) {
	
		if (identifier == null || identifier.length() == 0)
			throw new IllegalArgumentException(
					"The parameter 'identifier' cannot be null or empty.");
		else if (loggedEvents.keySet().contains(identifier))
			throw new IllegalArgumentException("The identifier '" + identifier
					+ "' was already logged as an event before. "
					+ "Identifiers must be unique within a profile.");
		else if (identifier.equals(START_EVENT_ID)
				|| identifier.equals(END_EVENT_ID))
			throw new IllegalArgumentException("'" + START_EVENT_ID + "' and '"
					+ END_EVENT_ID
					+ "' are reserved words and cannot be used as identifiers.");
		// no else.
	
		boolean allContainID = true;
		for (EnergyProfile profile : profiles)
			allContainID &= profile.getLoggedEvents().containsKey(identifier);
		// end for.
	
		if (!allContainID)
			throw new IllegalArgumentException(
					"All sub profiles should contain the logged ID before it can be added to the CompositeEnergyProfile.");
		// no else.
	
		loggedEvents.put(identifier, null);
	}

	/**
	 * This method should only be called by {@link CompositeJouleProfiler}s that
	 * logged the event for all the {@link EnergyProfile}s of this
	 * {@link CompositeEnergyProfile} before calling this method. Otherwise the
	 * call will fail with an {@link IllegalArgumentException}.
	 * 
	 * @param identifier
	 *            The identifier of the event to be logged.
	 * @param powerRate
	 *            Will be ignored. Thus, should be null.
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#logEvent(java.lang.String,
	 *      org.qualitune.jouleunit.PowerRate)
	 */
	@Override
	public void logEvent(String identifier, PowerRate powerRate) {

		logEvent(identifier, powerRate.getTimeStamp());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();

		result.append("Composite Energy Profile\n");
		result.append("duration: " + this.getDuration() / 1000000000d
				+ " secs\n");
		result.append("avg. profiling frequenzy: "
				+ this.getAvgProfilingFrequency() + " Hz\n");
		result.append("consumption: " + this.getConsumedEnergy() + " mJ\n");
		result.append("peak: " + this.getPeakPowerRate() + " mW");

		if (loggedEvents.keySet().size() > 0) {
			/* Get all events and their power rate. */
			/* Order them by time stamp. */
			List<String> ids = new ArrayList<String>(loggedEvents.keySet());
			ids.add(0, START_EVENT_ID);
			ids.add("End");
			/* Iterate and print result for each of them. */
			for (int index = 0; index < ids.size() - 1; index++) {
				String secondID = ids.get(index + 1);
				String firstID = ids.get(index);
				result.append("\n");
				result.append("* " + firstID + " - " + secondID + ": ["
						+ getDuration(firstID, secondID) / 1000000000d
						+ " secs, " + getConsumedEnergy(firstID, secondID)
						+ " mJ, peak: " + getPeakPowerRate(firstID, secondID)
						+ " mW]");
			}
			// end for.
		}
		// no else.

		/* Add reports for sub profiles. */
		int index = 1;
		for (EnergyProfile profile : profiles) {
			result.append("\n> Sub Profile #" + index + ":\n  ");
			result.append(profile.toString().replaceAll("\n", "\n  "));
			index++;
		}
		// end for.

		return result.toString();
	}

	/**
	 * Adds a given {@link EnergyProfile} to this {@link CompositeEnergyProfile}
	 * .
	 * 
	 * @param profile
	 *            The {@link EnergyProfile} to be added.
	 */
	public void addProfile(EnergyProfile profile) {

		if (profile == null)
			throw new IllegalArgumentException(
					"Parameter 'profile' cannot be null.");

		if (!profiles.contains(profile))
			profiles.add(profile);
		// no else.
	}

	/**
	 * Returns the sub {@link EnergyProfile}s of this
	 * {@link CompositeEnergyProfile} in a {@link List} (ordered as added to
	 * this {@link CompositeEnergyProfile}).
	 * 
	 * @return The sub {@link EnergyProfile}s of this
	 *         {@link CompositeEnergyProfile} in a {@link List}.
	 */
	public List<EnergyProfile> getSubProfiles() {
		return new ArrayList<EnergyProfile>(profiles);
	}

	/**
	 * Helper method to check two given parameters representing the bounds for
	 * an interval in this {@link EnergyProfile}.
	 * 
	 * @param firstID
	 *            The first ID of the events whose interval duration shall be
	 *            returned.
	 * @param secondID
	 *            The second ID of the events whose interval duration shall be
	 *            returned.
	 */
	protected void checkEventParameters(String firstID, String secondID) {

		if (firstID == null || firstID.length() == 0)
			throw new IllegalArgumentException(
					"Parameter 'firstID' cannot be null.");
		else if (secondID == null || secondID.length() == 0)
			throw new IllegalArgumentException(
					"Parameter 'secondID' cannot be null.");
		else if (!loggedEvents.keySet().contains(firstID)
				&& !firstID.equals(START_EVENT_ID))
			throw new IllegalArgumentException("Event ID '" + firstID
					+ "' does not exist or is invalid.");
		else if (!loggedEvents.keySet().contains(secondID)
				&& !secondID.equals(END_EVENT_ID))
			throw new IllegalArgumentException("Event ID '" + secondID
					+ "' does not exist or is invalid.");
		// no else.
	}

	/**
	 * Computes the avg power rate for two given {@link PowerRate} values.
	 * 
	 * @param lastValue
	 *            The first {@link PowerRate} value. Can be <code>null</code> if
	 *            nextValue is not <code>null</code>.
	 * @param nextValue
	 *            The second {@link PowerRate} value. Can be <code>null</code>
	 *            if lastValue is not <code>null</code>.
	 * @return Their averaged power rate as a long.
	 */
	private double getAvgPowerRate(PowerRate lastValue, PowerRate nextValue) {

		if (lastValue == null && nextValue == null)
			throw new IllegalArgumentException(
					"Both arguments 'lastValue' and 'nextValue' cannot be null.");
		// no else.

		if (lastValue == null)
			return nextValue.getPowerRate();
		else if (nextValue == null)
			return lastValue.getPowerRate();
		else
			return (lastValue.getPowerRate() + nextValue.getPowerRate()) / 2;
	}

	/**
	 * Helper method that computes the indexes of the lowest value in a given
	 * array of {@link PowerRate}s w.r.t. their time stamps. Can contain
	 * multiple values, as multiple values can have the same time stamp.
	 * 
	 * @param array
	 *            The array
	 * @return A {@link List} containing the indexes (between 0 and array.length
	 *         - 1).
	 */
	private List<Integer> getIndexOfLowestValue(PowerRate[] array) {

		List<Integer> result = new ArrayList<Integer>();
		long smallestTimeStamp = Long.MAX_VALUE;

		/* Identify smallest time stamp. */
		for (int index = 0; index < array.length; index++) {
			if (array[index] != null
					&& array[index].getTimeStamp() < smallestTimeStamp) {
				smallestTimeStamp = array[index].getTimeStamp();
			}
			// no else.
		}
		// end for.

		/* Collect all values with the smallest time stamp. */
		for (int index = 0; index < array.length; index++) {
			if (array[index] != null
					&& array[index].getTimeStamp() == smallestTimeStamp) {
				result.add(index);
			}
			// no else.
		}
		// end for.

		return result;
	}
}
