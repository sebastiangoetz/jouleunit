package org.qualitune.jouleunit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.qualitune.jouleunit.persist.RestoredPowerRate;

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
	 * Indicates whether or not the {@link PowerRate} values of this
	 * {@link SimpleEnergyProfile} are sorted.
	 */
	protected boolean areMeasuredValuesSorted = true;

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

			if (!areMeasuredValuesSorted) {
				Collections.sort(measuredValues);
				areMeasuredValuesSorted = true;
			}
			// no else.

			PowerRate firstValue = getNearestValue(measuredValues, start, true);
			PowerRate lastValue = getNearestValue(measuredValues, end, false);

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
			for (int index = getIndexOf(firstValue); index < getIndexOf(lastValue); index++) {
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
				PowerRate val2 = measuredValues.get(getIndexOf(firstValue) + 1);

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
				PowerRate val1 = measuredValues.get(getIndexOf(lastValue) - 1);
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

			if (!areMeasuredValuesSorted) {
				Collections.sort(measuredValues);
				areMeasuredValuesSorted = true;
			}
			// no else.

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

			int fromIndex = getIndexOf(firstValue);
			int toIndex = getIndexOf(lastValue);

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
	 * long)
	 * 
	 * TODO test me!
	 */
	public void logEvent(String identifier, long timeStamp) {

		RestoredPowerRate powerRate = new RestoredPowerRate();
		powerRate.setTimeStamp(timeStamp);

		/* Compute rate. */
		double rate = 0d;

		if (!areMeasuredValuesSorted) {
			Collections.sort(measuredValues);
			areMeasuredValuesSorted = true;
		}
		// no else.

		PowerRate firstValue = null;
		PowerRate lastValue = null;

		/* Find bounds. */
		for (PowerRate value : measuredValues) {

			if (value.getTimeStamp() <= timeStamp)
				firstValue = value;
			// no else.

			if (value.getTimeStamp() >= timeStamp) {
				lastValue = value;
				break;
			}
			// no else.
		}
		// end for.

		/* Check bounds. */
		if (firstValue == null || lastValue == null)
			throw new IllegalArgumentException(
					"Argument 'timeStamp' is out of bounds. Must have a value within the interval ["
							+ measuredValues.get(0).getTimeStamp()
							+ ", "
							+ measuredValues.get(measuredValues.size() - 1)
									.getTimeStamp()
							+ "] but was "
							+ timeStamp
							+ ".");
		// no else.

		if (firstValue.getTimeStamp() != timeStamp) {
			/*
			 * Compute power rate based on co-tangens of time an power rate of
			 * next two values.
			 */
			PowerRate val1 = firstValue;
			PowerRate val2 = measuredValues.get(getIndexOf(firstValue) + 1);

			double p1_2 = val1.getPowerRate() - val2.getPowerRate();
			long t1_2 = val2.getTimeStamp() - val1.getTimeStamp();

			long tS_2 = val2.getTimeStamp() - timeStamp;
			double pS_2;

			/* p1_2 / t1_2 = pS_2 / tS_2 */
			/* -> pS_2 = p1_2 / t1_2 * tS_2 */
			if (p1_2 != 0)
				pS_2 = p1_2 * tS_2 / t1_2;
			else
				pS_2 = 0l;

			rate = val2.getPowerRate() + pS_2;
		}

		else {
			rate = firstValue.getPowerRate();
		}

		powerRate.setPowerRate(rate);
		logEvent(identifier, powerRate);
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
		areMeasuredValuesSorted = false;
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
	 * Helper method to effectively find the {@link PowerRate} in an ordered
	 * location that is nearest to a specified time value.
	 * 
	 * @param values
	 *            The values as a ordered {@link List} of {@link PowerRate}s.
	 * @param timestamp
	 *            The timestamp whose nearest {@link PowerRate} shall be found.
	 * @param findNearestBelow
	 *            If <code>true</code>, the nearest value below the given
	 *            timestamp will be returned, else above.
	 * @return The found nearest {@link PowerRate} value to the given timestamp.
	 */
	public PowerRate getNearestValue(List<PowerRate> values, long timestamp,
			boolean findNearestBelow) {

		if (values.size() == 0)
			return null;
		else if (values.size() == 1) {
			if (findNearestBelow && values.get(0).getTimeStamp() <= timestamp)
				return values.get(0);
			else if (!findNearestBelow
					&& values.get(0).getTimeStamp() >= timestamp)
				return values.get(0);
			else
				return null;
		}

		else {
			/*
			 * Divide and conquer. Separate the list into two halves and check
			 * which one is the right one.
			 */
			int middleIndex = values.size() / 2;

			PowerRate middleRate = values.get(middleIndex);
			long middleTimestamp = middleRate.getTimeStamp();

			if (middleTimestamp == timestamp)
				return middleRate;
			// no else.

			if (findNearestBelow) {
				if (middleTimestamp > timestamp)
					return getNearestValue(values.subList(0, middleIndex),
							timestamp, findNearestBelow);
				else if (middleIndex + 1 == values.size())
					return middleRate;
				else if (values.get(middleIndex + 1).getTimeStamp() <= timestamp)
					return getNearestValue(
							values.subList(middleIndex + 1, values.size()),
							timestamp, findNearestBelow);
				else
					return middleRate;
			}

			else {
				if (middleTimestamp < timestamp)
					return getNearestValue(
							values.subList(middleIndex, values.size()),
							timestamp, findNearestBelow);
				else if (values.get(middleIndex - 1).getTimeStamp() < timestamp)
					return middleRate;
				else
					return getNearestValue(values.subList(0, middleIndex),
							timestamp, findNearestBelow);
			}
		}
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

	/**
	 * Helper method that computes the index of a given {@link PowerRate} within
	 * the measured values of this {@link SimpleEnergyProfile}.
	 * 
	 * @param powerRate
	 *            The {@link PowerRate} whose index shall be computed.
	 * @return The index of the given {@link PowerRate} or <code>-1</code>.
	 */
	public int getIndexOf(PowerRate powerRate) {
		return this.getIndexOf(powerRate, measuredValues);
	}

	/**
	 * Helper method that computes the index of a given {@link PowerRate} within
	 * the as given {@link List} of {@link PowerRate}s.
	 * 
	 * @param powerRate
	 *            The {@link PowerRate} whose index shall be computed.
	 * @param values
	 *            The {@link PowerRate}s.
	 * @return The index of the given {@link PowerRate} or <code>-1</code>.
	 */
	private int getIndexOf(PowerRate powerRate, List<PowerRate> values) {

		if (values.size() == 0)
			return -1;
		else if (values.size() == 1 && values.get(0).equals(powerRate))
			return 0;
		else {
			if (!areMeasuredValuesSorted) {
				Collections.sort(measuredValues);
				areMeasuredValuesSorted = true;
			}
			// no else.

			int middleIndex = values.size() / 2;
			PowerRate middleRate = values.get(middleIndex);

			if (middleRate.getTimeStamp() == powerRate.getTimeStamp())
				return middleIndex;
			else if (middleRate.getTimeStamp() > powerRate.getTimeStamp())
				return getIndexOf(powerRate, values.subList(0, middleIndex));
			else {
				int innerIndex = getIndexOf(powerRate,
						values.subList(middleIndex + 1, values.size()));
				if (innerIndex == -1)
					return innerIndex;
				else
					return middleIndex + innerIndex + 1;
			}
		}
	}
}
