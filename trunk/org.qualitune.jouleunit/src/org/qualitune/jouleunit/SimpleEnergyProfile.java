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
	 * @see org.qualitune.jouleunit.EnergyProfile#getConsumedEnergy(long, long)
	 */
	public double getConsumedEnergy(long start, long end) {

		if (end <= start)
			throw new IllegalArgumentException(
					"Argument 'end' cannot be smaller or equal to argument 'start'.");
		else if (measuredValues.size() == 0)
			throw new IllegalStateException(
					"Cannot compute average energy consumption for an empty profile.");
		else {
			double result = 0d;

			Collections.sort(measuredValues);

			PowerRate firstValue = null;
			PowerRate lastValue = null;

			/* Find bounds. */
			for (PowerRate value : measuredValues) {

				if (value.getTimeStamp() <= start)
					firstValue = value;
				// no else.

				if (value.getTimeStamp() >= end) {
					lastValue = value;
					break;
				}
				// no else.
			}
			// end for.

			/* Check bounds. */
			if (firstValue == null)
				throw new IllegalArgumentException(
						"Argument 'start' is out of bounds. Must have a value within the interval ["
								+ measuredValues.get(0).getTimeStamp()
								+ ", "
								+ measuredValues.get(measuredValues.size() - 1)
										.getTimeStamp() + "] but was " + start
								+ ".");
			else if (lastValue == null)
				throw new IllegalArgumentException(
						"Argument 'end' is out of bounds. Must have a value within the interval ["
								+ measuredValues.get(0).getTimeStamp()
								+ ", "
								+ measuredValues.get(measuredValues.size() - 1)
										.getTimeStamp() + "] but was " + start
								+ ".");
			// no else.

			/* Compute average for each interval. */
			for (int index = measuredValues.indexOf(firstValue); index < measuredValues
					.indexOf(lastValue); index++) {
				PowerRate val1 = measuredValues.get(index);
				PowerRate val2 = measuredValues.get(index + 1);

				result += (val1.getPowerRate() + val2.getPowerRate())
						* (val2.getTimeStamp() - val1.getTimeStamp())
						/ 2000000000;
			}
			// end for.

			/*
			 * Subtract overlapping ends (these are the ends caused if the first
			 * value's time stamp is not equal to the start time stamp or the
			 * last value's time stamp is not equal to the end time stamp).
			 */
			if (firstValue.getTimeStamp() != start) {
				/*
				 * Compute power rate for first time stamp based on co-tangens
				 * of time an power rate of next two values.
				 */
				PowerRate val1 = firstValue;
				PowerRate val2 = measuredValues.get(measuredValues
						.indexOf(firstValue) + 1);

				double p1_2 = val1.getPowerRate() - val2.getPowerRate();
				long t1_2 = val2.getTimeStamp() - val1.getTimeStamp();

				long tS_2 = val2.getTimeStamp() - start;
				double pS_2;

				/* p1_2 / t1_2 = pS_2 / tS_2 */
				/* -> pS_2 = p1_2 / t1_2 * tS_2 */
				if (p1_2 != 0)
					pS_2 = p1_2 * tS_2 / t1_2;
				else
					pS_2 = 0l;

				double startPowerRate = val2.getPowerRate() + pS_2;

				/* Compute part to be subtracted. */
				result -= (val1.getPowerRate() + startPowerRate)
						* (start - val1.getTimeStamp()) / 2000000000d;
			}
			// no else.

			if (lastValue.getTimeStamp() != end) {
				/*
				 * Compute power rate for first time stamp based on co-tangens
				 * of time an power rate of next two values.
				 */
				PowerRate val1 = measuredValues.get(measuredValues
						.indexOf(lastValue) - 1);
				PowerRate val2 = lastValue;

				double p1_2 = val1.getPowerRate() - val2.getPowerRate();
				long t1_2 = val2.getTimeStamp() - val1.getTimeStamp();

				long tE_2 = val2.getTimeStamp() - end;
				/* b' */
				double pE_2;

				/* p1_2 / t1_2 = pE_2 / tE_2 */
				/* -> pE_2 = p1_2 / t1_2 * tE_2 */
				if (p1_2 != 0)
					pE_2 = p1_2 * tE_2 / t1_2;
				else
					pE_2 = 0l;

				double endPowerRate = val2.getPowerRate() + pE_2;

				/* Compute part to be subtracted. */
				result -= (endPowerRate + val2.getPowerRate())
						* (val2.getTimeStamp() - end) / 2000000000d;
			}
			// no else.

			/* Invert discharging rate to consumption. */
			return -result;
		}
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
	public double getPeakPowerRate(String firstID, String secondID) {

		/* Includes parameter preconditions. */
		List<PowerRate> significantValues = getSignificantValues(firstID,
				secondID);

		double result = Double.MAX_VALUE;

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
