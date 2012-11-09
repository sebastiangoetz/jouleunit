package org.qualitune.jouleunit;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * An {@link EnergyProfile} contains the result of profiling issued by an
 * {@link JouleProfiler}.
 * 
 * @author Claas Wilke
 */
public interface EnergyProfile extends Serializable {

	/** ID for first event in the event log. */
	public static final String END_EVENT_ID = "End";

	/** ID for last event in the event log. */
	public static final String START_EVENT_ID = "Start";

	/**
	 * Returns the average profiling frequency of this {@link EnergyProfile}.
	 * 
	 * @return The average profiling frequency of this {@link EnergyProfile}.
	 */
	public double getAvgProfilingFrequency();

	/**
	 * Returns the consumed energy in <code>mJ</code>.
	 * 
	 * @return The consumed energy in <code>mJ</code>.
	 */
	public double getConsumedEnergy();

	/**
	 * Returns the consumed energy in <code>mJ</code> between logged events
	 * identified by two given IDs.
	 * 
	 * @param firstID
	 *            The first ID of the events whose interval duration shall be
	 *            returned.
	 * @param secondID
	 *            The second ID of the events whose interval duration shall be
	 *            returned.
	 * @return The consumed energy in <code>mJ</code>.
	 */
	public double getConsumedEnergy(String firstID, String secondID);

	/**
	 * Returns the consumed energy in <code>mJ</code> between two given time
	 * stamps.
	 * 
	 * @param start
	 *            The start time stamp of the interval whose energy consumption
	 *            shall be returned. <strong>Must lay within the interval of
	 *            this EnergyProfile and must be smaller than the end time
	 *            stamp!</strong>
	 * @param end
	 *            The end time stamp of the interval whose energy consumption
	 *            shall be returned. <strong>Must lay within the interval of
	 *            this EnergyProfile and must be bigger than the start time
	 *            stamp!</strong>
	 * @return The consumed energy in <code>mJ</code>.
	 */
	public double getConsumedEnergy(long start, long end);

	/**
	 * Returns the duration of this {@link EnergyProfile}
	 * <code>nano (10 ^ -9) seconds</code>.
	 * 
	 * @return The duration of this {@link EnergyProfile}
	 *         <code>nano (10 ^ -9) seconds</code>.
	 */
	public long getDuration();

	/**
	 * Returns the duration of this {@link EnergyProfile}in
	 * <code>nano (10 ^ -9) seconds</code> between logged events identified by
	 * two given IDs.
	 * 
	 * @param firstID
	 *            The first ID of the events whose interval duration shall be
	 *            returned.
	 * @param secondID
	 *            The second ID of the events whose interval duration shall be
	 *            returned.
	 * @return The duration of this {@link EnergyProfile} in
	 *         <code>nano (10 ^ -9) seconds</code> between the given logged
	 *         events.
	 */
	public long getDuration(String firstID, String secondID);

	/**
	 * Returns the logged Events of this {@link EnergyProfile}.
	 * 
	 * @return The logged Events of this {@link EnergyProfile}.
	 */
	public Map<String, PowerRate> getLoggedEvents();

	/**
	 * Returns the peak value of all {@link PowerRate}s within this
	 * {@link EnergyProfile} in <code>mW</code>. <strong>Please note that the
	 * peak highly depends on the refresh rate within the {@link JouleProfiler}
	 * used for the {@link EnergyProfile}'s creation!</strong>
	 * 
	 * @return The peak power rate of this {@link EnergyProfile} in
	 *         <code>mW</code>. Please note that this might be a negative value
	 *         as negative values indicate discharging.
	 */
	public double getPeakPowerRate();

	/**
	 * Returns the peak value of all {@link PowerRate}s within two given events
	 * logged for this {@link SimpleEnergyProfile} in <code>mW</code>.
	 * <strong>Please note that the peak highly depends on the refresh rate
	 * within the {@link JouleProfiler} used for the {@link SimpleEnergyProfile}
	 * 's creation!</strong>
	 * 
	 * @param firstID
	 *            The first ID of the events whose interval duration shall be
	 *            returned.
	 * @param secondID
	 *            The second ID of the events whose interval duration shall be
	 *            returned.
	 * @return The peak power rate of this {@link SimpleEnergyProfile} in the
	 *         given interval in <code>mW</code>. Please note that this might be
	 *         a negative value as negative values indicate discharging.
	 */
	public double getPeakPowerRate(String firstID, String secondID);

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
	public List<PowerRate> getSignificantValues(String firstID, String secondID);

	/**
	 * Returns the nano time stamp when profiling started.
	 * 
	 * @return The nano time stamp when profiling started.
	 */
	public long getStartedNanoTime();

	/**
	 * Returns the nano time stamp when profiling stopped.
	 * 
	 * @return The nano time stamp when profiling stopped.
	 */
	public long getStopNanoTime();

	/**
	 * Logs a given event for this {@link EnergyProfile}. An event consists of
	 * an identifier and a {@link PowerRate} value that includes the event's
	 * time stamp. Events can be used to separate the {@link EnergyProfile} into
	 * multiple parts, for which individual energy consumptions can be computed.
	 * E.g., this can be used to log the begin or end of the specific method's
	 * invocation during testing.
	 * 
	 * @param identifier
	 *            The identifier of the event (<strong>Must be unique within
	 *            this {@link EnergyProfile}!</strong>).
	 * @param powerRate
	 *            The associated {@link PowerRate} value (including the event's
	 *            time stamp.
	 */
	public void logEvent(String identifier, PowerRate powerRate);

	/**
	 * Logs a given event for this {@link EnergyProfile}. An event consists of
	 * an identifier and a time stamp. Events can be used to separate the
	 * {@link EnergyProfile} into multiple parts, for which individual energy
	 * consumptions can be computed. E.g., this can be used to log the begin or
	 * end of the specific method's invocation during testing. <strong>Note that
	 * the time stamp must lay between the time stamps of the first and last
	 * logged power rate value in this {@link EnergyProfile}!</strong>
	 * 
	 * @param identifier
	 *            The identifier of the event (<strong>Must be unique within
	 *            this {@link EnergyProfile}!</strong>).
	 * @param timeStamp
	 *            The event's time stamp.
	 */
	public void logEvent(String identifier, long timeStamp);

	/**
	 * Sets the nano time stamp when profiling started.
	 * 
	 * @param startedNanoTime
	 *            The nano time stamp when profiling started.
	 */
	public void setStartedNanoTime(long startedNanoTime);

	/**
	 * Sets the nano time stamp when profiling stopped.
	 * 
	 * @param stopNanoTime
	 *            The nano time stamp when profiling stopped.
	 */
	public void setStopNanoTime(long stopNanoTime);
}
