package org.qualitune.jouleunit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An {@link SimpleEnergyProfile} contains the result of profiling issued by an
 * {@link JouleProfiler}.
 * 
 * @author Claas Wilke
 */
public class SimpleEnergyProfile extends AbstractEnergyProfile {

	/** ID for serialization. */
	private static final long serialVersionUID = -8057143666601561092L;

	/** Contains the {@link PowerRate}s of this {@link SimpleEnergyProfile}. */
	protected List<PowerRate> measuredValues = new ArrayList<PowerRate>(2);

	/**
	 * Creates a new {@link SimpleEnergyProfile} with no duration and energy
	 * consumption. Use {@link SimpleEnergyProfile#addPowerRateValue(PowerRate)}
	 * to modify these values.
	 */
	public SimpleEnergyProfile() {
		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getAvgProfilingFrequency()
	 * 
	 * TODO test me
	 */
	public double getAvgProfilingFrequency() {
		if (getDuration() > 0)
			return (measuredValues.size() - 1) * 1000000000d / getDuration();
		else
			return 0d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.EnergyProfile#getConsumedEnergy(java.lang.String,
	 * java.lang.String)
	 */
	public double getConsumedEnergy(String firstID, String secondID) {

		/* Includes parameter preconditions. */
		List<PowerRate> significantValues = getSignificantValues(firstID,
				secondID);

		double result = 0;

		for (int index = 1; index < significantValues.size(); index++) {
			PowerRate value1 = significantValues.get(index - 1);
			PowerRate value2 = significantValues.get(index);

			/*
			 * Factor 500000000 is optimized (-1 from negation, factor 2 from
			 * power rate mean, factor 1000000000 from unit conversion (nano)).
			 */
			double value = (value2.getTimeStamp() - value1.getTimeStamp())
					/ -2000000000d
					* (value1.getPowerRate() + value2.getPowerRate());
			result += value;
		}
		// end for.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getDuration(java.lang.String,
	 * java.lang.String)
	 */
	public long getDuration(String firstID, String secondID) {

		/* Includes parameter preconditions. */
		List<PowerRate> significantValues = getSignificantValues(firstID,
				secondID);

		if (significantValues.size() > 0) {
			return significantValues.get(significantValues.size() - 1)
					.getTimeStamp() - significantValues.get(0).getTimeStamp();
		} else
			return 0l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.EnergyProfile#getPeakPowerRate(java.lang.String,
	 * java.lang.String)
	 */
	public long getPeakPowerRate(String firstID, String secondID) {

		/* Includes parameter preconditions. */
		List<PowerRate> significantValues = getSignificantValues(firstID,
				secondID);

		long result = Long.MAX_VALUE;

		if (measuredValues.size() == 0)
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

	/**
	 * Returns all {@link PowerRate} values of this {@link SimpleEnergyProfile}
	 * between to given events.
	 * 
	 * @param firstID
	 *            The first ID of the events whose {@link PowerRate} values
	 *            shall be returned.
	 * @param secondID
	 *            The second ID of the events whose {@link PowerRate} values
	 *            shall be returned.
	 * @return All {@link PowerRate} values of this {@link SimpleEnergyProfile}
	 *         between to given events.
	 */
	public List<PowerRate> getSignificantValues(String firstID, String secondID) {

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

		if (measuredValues.size() > 0) {

			Collections.sort(measuredValues);

			PowerRate firstValue;
			PowerRate lastValue;

			if (firstID.equals(START_EVENT_ID))
				firstValue = measuredValues.get(0);
			else
				firstValue = loggedEvents.get(firstID);

			if (secondID.equals(END_EVENT_ID))
				lastValue = measuredValues.get(measuredValues.size() - 1);
			else
				lastValue = loggedEvents.get(secondID);

			int fromIndex = measuredValues.indexOf(firstValue);
			int toIndex = measuredValues.indexOf(lastValue);

			List<PowerRate> significantValues = new ArrayList<PowerRate>(
					measuredValues.subList(fromIndex, toIndex + 1));

			if (significantValues.size() == 0)
				throw new IllegalArgumentException(
						"No Power Rate values defined inbetween the events '"
								+ firstID + "' and '" + secondID + "'.");
			// no else.

			return significantValues;
		}

		else
			return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getStartedNanoTime()
	 */
	public long getStartedNanoTime() {
		if (startedNanoTime != 0)
			return startedNanoTime;
		else {
			long earliest = Long.MAX_VALUE;
			for (PowerRate value : measuredValues)
				earliest = Math.min(earliest, value.getTimeStamp());
			// end for.

			if (earliest == Long.MAX_VALUE)
				earliest = 0;
			// no else.

			return earliest;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getStopNanoTime()
	 */
	public long getStopNanoTime() {
		if (stopNanoTime != 0)
			return stopNanoTime;
		else {
			long latest = Long.MIN_VALUE;
			for (PowerRate value : measuredValues)
				latest = Math.max(latest, value.getTimeStamp());
			// end for.

			if (latest == Long.MIN_VALUE)
				latest = 0;
			// no else.

			return latest;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#logEvent(java.lang.String,
	 * org.qualitune.jouleunit.PowerRate)
	 */
	public void logEvent(String identifier, PowerRate powerRate) {

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
		else if (powerRate == null)
			throw new IllegalArgumentException(
					"The parameter 'powerRate' cannot be null.");
		// no else.

		measuredValues.add(powerRate);
		loggedEvents.put(identifier, powerRate);
	}

	/**
	 * Adds a given {@link PowerRate} to this {@link SimpleEnergyProfile}.
	 * 
	 * @param value
	 *            The {@link PowerRate} to be added.
	 * @return <code>true</code> if adding was successful.
	 */
	public boolean addPowerRateValue(PowerRate value) {

		if (value == null)
			throw new IllegalArgumentException(
					"Argument 'value' cannot be null.");
		// no else.

		resetCache();
		return this.measuredValues.add(value);
	}

	/**
	 * Returns the {@link PowerRate}s of this {@link SimpleEnergyProfile}.
	 * 
	 * @return The {@link PowerRate}s of this {@link SimpleEnergyProfile}.
	 */
	public List<PowerRate> getMeasuredValues() {
		return measuredValues;
	}

	/**
	 * Returns the count of {@link PowerRate}s in this
	 * {@link SimpleEnergyProfile}.
	 * 
	 * @return The count of {@link PowerRate} in this
	 *         {@link SimpleEnergyProfile} .
	 */
	public int getNumberOfValues() {
		return measuredValues.size();
	}
}
