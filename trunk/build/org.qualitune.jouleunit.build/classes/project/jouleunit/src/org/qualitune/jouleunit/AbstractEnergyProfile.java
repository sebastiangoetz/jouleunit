package org.qualitune.jouleunit;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract implementation of {@link EnergyProfile}.
 * 
 * @author Claas Wilke
 */
public abstract class AbstractEnergyProfile implements EnergyProfile {

	/** Generated ID for seralization. */
	private static final long serialVersionUID = -3056938128369231407L;

	/** The profiled consumed energy in <code>mJ</code>. */
	protected double consumedEnergy;

	/**
	 * The duration of this {@link AbstractEnergyProfile} in
	 * <code>nano (10 ^ -9) seconds</code>.
	 */
	protected long duration;

	/**
	 * Contains events logged during profiling this
	 * {@link AbstractEnergyProfile}.
	 */
	protected Map<String, PowerRate> loggedEvents = new HashMap<String, PowerRate>();

	/**
	 * Time stamp when profiling started in <code>nano (10 ^ -9) seconds</code>.
	 */
	protected long startedNanoTime;

	/**
	 * Time stamp when profiling stopped in <code>nano (10 ^ -9) seconds</code>.
	 */
	protected long stopNanoTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getConsumedEnergy()
	 */
	public double getConsumedEnergy() {
		if (consumedEnergy <= 0) {
			consumedEnergy = getConsumedEnergy(START_EVENT_ID, END_EVENT_ID);
		}
		// no else.

		return consumedEnergy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getDuration()
	 */
	public long getDuration() {
		if (duration <= 0)
			duration = getDuration(START_EVENT_ID, END_EVENT_ID);
		// no else.

		return duration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getLoggedEvents()
	 */
	public Map<String, PowerRate> getLoggedEvents() {
		return new HashMap<String, PowerRate>(loggedEvents);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#setStartedNanoTime(long)
	 */
	public void setStartedNanoTime(long startedNanoTime) {
		this.startedNanoTime = startedNanoTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getPeakPowerRate()
	 */
	public double getPeakPowerRate() {

		return getPeakPowerRate(START_EVENT_ID, END_EVENT_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#setStopNanoTime(long)
	 */
	public void setStopNanoTime(long stopNanoTime) {
		this.stopNanoTime = stopNanoTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();

		result.append("Energy Profile\n");
		result.append("duration: " + this.getDuration() / 1000000000d
				+ " secs\n");
		result.append("avg. profiling frequenzy: " + getAvgProfilingFrequency()
				+ " Hz\n");
		result.append("consumption: " + this.getConsumedEnergy() + " mJ\n");
		result.append("peak: " + this.getPeakPowerRate() + " mW");

		// if (loggedEvents.keySet().size() > 0) {
		// /* Get all events and their power rate. */
		// /* Order them by time stamp. */
		// List<String> ids = new ArrayList<String>(loggedEvents.keySet());
		// ids.add(0, START_EVENT_ID);
		// ids.add("End");
		// /* Iterate and print result for each of them. */
		// for (int index = 0; index < ids.size() - 1; index++) {
		// String secondID = ids.get(index + 1);
		// String firstID = ids.get(index);
		// result.append("\n");
		// result.append("* " + firstID + " - " + secondID + ": ["
		// + getDuration(firstID, secondID) / 1000000000d
		// + " secs, " + getConsumedEnergy(firstID, secondID)
		// + " mJ, peak: " + getPeakPowerRate(firstID, secondID)
		// + " mW]");
		// }
		// // end for.
		// }
		// no else.

		return result.toString();
	}

	/**
	 * Resets cached computed values of this {@link AbstractEnergyProfile} due
	 * to modification.
	 */
	protected void resetCache() {
		duration = -1;
		consumedEnergy = -1;
	}
}
