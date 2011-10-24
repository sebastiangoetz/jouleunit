package org.qualitune.jouleunit.test;

import org.qualitune.jouleunit.AbstractPowerRate;

/**
 * Mock implementation of {@link PowerRateMock}.
 * 
 * @author Claas Wilke
 */
public class PowerRateMock extends AbstractPowerRate {

	/** Generated ID for serialization. */
	private static final long serialVersionUID = -6789373581851551496L;

	/** The power rate. */
	protected long powerRate;

	/** Whether or not a power adapter is online. */
	protected boolean isPowerAdapterOnline;

	/**
	 * Creates a new {@link PowerRateMock} for a given power rate.
	 * 
	 * @param chargingRate
	 *            The power rate.
	 * @param isPowerAdapterOnline
	 *            Whether or not a power adapter is online.
	 */
	public PowerRateMock(long chargingRate, boolean isPowerAdapterOnline) {
		this.powerRate = chargingRate;
		this.isPowerAdapterOnline = isPowerAdapterOnline;
		this.timeStamp = System.nanoTime();
	}

	/**
	 * Creates a new {@link PowerRateMock} for a given power rate.
	 * 
	 * @param chargingRate
	 *            The power rate.
	 * @param isPowerAdapterOnline
	 *            Whether or not a power adapter is online.
	 * @param timeStamp
	 *            The time stamp of this {@link PowerRateMock} in nanoseconds.
	 */
	public PowerRateMock(long chargingRate, boolean isPowerAdapterOnline,
			long timeStamp) {
		this.powerRate = chargingRate;
		this.isPowerAdapterOnline = isPowerAdapterOnline;
		this.timeStamp = timeStamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.eunit.profiler.EMeasuringValue#getChargingRateInMilliWatt()
	 */
	public long getPowerRate() {
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
}
