package org.qualitune.jouleunit.wt210;

import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.PowerRate;

public class WT210Profiler extends AbstractJouleProfiler {

	/** The GPIB address of the WT210 belonging to this {@link WT210Profiler}. */
	private int gpibAddress;

	/**
	 * Indicates whether or not positive power rates represent power
	 * consumption.
	 */
	private boolean positiveRatesAreConsumption;

	/**
	 * Creates a new {@link WT210Profiler}.
	 * 
	 * @param gpibAddress
	 *            The GPIB address of the WT210 belonging to this
	 *            {@link WT210Profiler}.
	 * @param positiveRatesAreConsumption
	 *            Indicates whether or not a positive power rate represents a
	 *            power consumption. In contrast to battery interfaces, where
	 *            negative power rates always represent discharging (i.e., power
	 *            consumption) and positive rates charging, in the context of a
	 *            power meter the actual meaning depends on the wiring. Thus,
	 *            this meaning can be considered by using this flag.
	 */
	public WT210Profiler(int gpibAddress, boolean positiveRatesAreConsumption) {
		this.gpibAddress = gpibAddress;
		this.positiveRatesAreConsumption = positiveRatesAreConsumption;

		/* Check that the GPIB address does work. */
		try {
			this.getPowerRateValue();
		}

		catch (IllegalStateException e) {
			throw new IllegalArgumentException("Illegal GPIB address "
					+ gpibAddress + ". " + e.getMessage(), e);
		}
	}

	@Override
	public PowerRate getPowerRateValue() throws IllegalStateException {
		return new WT210PowerRate(gpibAddress, positiveRatesAreConsumption);
	}

	/**
	 * Checks whether or not the GPIB device at the set GPIB address of this
	 * {@link WT210Profiler} can be found.
	 * 
	 * @return <code>true</code> if the device can be found.
	 */
	public boolean isWT210Online() {
		try {
			WT210Util.getDeviceAtAddress(gpibAddress);
			return true;
		}

		catch (IllegalStateException e) {
			return false;
		}
	}
}
