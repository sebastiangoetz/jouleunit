package org.qualitune.jouleunit;

import java.io.Serializable;

/**
 * Represents one measured power rate of a device under test at a certain point
 * in time.
 * 
 * @author Claas Wilke
 */
public interface PowerRate extends Comparable<PowerRate>, Serializable {

	/**
	 * Returns the power rate of the device in <code>mW</code>. A negative value
	 * indicates discharging whereas a positive value indicates charging
	 * (typically discharging should be the case during energy testing).
	 * 
	 * @return The power rate of the battery device in <code>mW</code>.
	 */
	public double getPowerRate();

	/**
	 * Returns the time stamp at which this {@link PowerRate} has been created
	 * in <code>nano (10 ^ -9) seconds</code>.
	 * 
	 * @return The time stamp at which this {@link PowerRate} has been created
	 *         in <code>nano (10 ^ -9) seconds</code>.
	 */
	public long getTimeStamp();

	/**
	 * Indicates whether or not the platform under test is currently operated
	 * using a power adapter.
	 * 
	 * @return If <code>true</code>, the power adapter is online.
	 */
	public boolean isPowerAdapterOnline();
}
