package org.qualitune.jouleunit.android;

import org.qualitune.jouleunit.PowerRate;

/**
 * A {@link PowerRate} picked from the Android proc file system battery info.
 * 
 * @author Claas Wilke
 */
public class AndroidProcPowerRate implements PowerRate {

	/** ID for serialization. */
	private static final long serialVersionUID = 4189332169806751328L;

	/** The power rate value in mW. */
	private double powerRate;

	/** The time stamp of the value in nano time. */
	private long timeStamp;

	/**
	 * Creates a new {@link AndroidProcPowerRate}.
	 * 
	 * @param powerRate
	 *            The power rate value in mW.
	 */
	public AndroidProcPowerRate(double powerRate) {
		this.powerRate = powerRate;
		this.timeStamp = System.nanoTime();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(PowerRate o) {
		return new Long(timeStamp).compareTo(new Long(o.getTimeStamp()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.PowerRate#getPowerRate()
	 */
	public double getPowerRate() {
		return powerRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.PowerRate#getTimeStamp()
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	@Override
	public boolean isPowerAdapterOnline() {
		// TODO Auto-generated method stub
		return false;
	}

}
