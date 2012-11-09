package org.qualitune.jouleunit.persist;

import org.qualitune.jouleunit.AbstractPowerRate;
import org.qualitune.jouleunit.PowerRate;

/**
 * Represents {@link PowerRate}s that have been restored from the
 * {@link EnergyProfileHistory}.
 * 
 * @author Claas Wilke
 */
public class RestoredPowerRate extends AbstractPowerRate {

	/** Generated ID for serialization. */
	private static final long serialVersionUID = -8333248448352888849L;

	/** The power rate in <code>mW</code>. */
	protected double powerRate;

	/** If <code>true</code> power adapter is online. */
	protected boolean isPowerAdapterOnline;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.eunit.profiler.EMeasuringValue#getChargingRateInMilliWatt()
	 */
	public double getPowerRate() {
		return powerRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EMeasuringValue#isPowerAdapterOnline()
	 */
	public boolean isPowerAdapterOnline() {
		return isPowerAdapterOnline;
	}

	/**
	 * Sets the power rate in <code>mW</code>.
	 * 
	 * @param powerRate
	 *            The power rate in <code>mW</code>.
	 */
	public void setPowerRate(double powerRate) {
		this.powerRate = powerRate;
	}

	/**
	 * Enables the power adapter online flag.
	 * 
	 * @param enabled
	 *            If <code>true</code> power adapter is online.
	 */
	public void setPowerAdapterOnlineEnabled(boolean enabled) {
		isPowerAdapterOnline = enabled;
	}

	/**
	 * Sets the time stamp in <code>nano secs</code>.
	 * 
	 * @param timestamp
	 *            The time stamp in <code>nano secs</code>.
	 */
	public void setTimeStamp(long timestamp) {
		this.timeStamp = timestamp;
	}
}
