package org.qualitune.jouleunit.wt210;

import java.io.IOException;

import org.qualitune.jouleunit.AbstractPowerRate;
import org.qualitune.jouleunit.PowerRate;

import be.ac.ulb.gpib.GPIBDevice;

/**
 * {@link PowerRate} implementation for WT210 power meter connected to the test
 * runner PC via GPIB bus.
 * 
 * @author Claas Wilke
 */
public class WT210PowerRate extends AbstractPowerRate {

	/** Generated ID for serialization. */
	private static final long serialVersionUID = 77305826971936071L;

	/** The {@link GPIBDevice} of the WT210 this {@link PowerRate} belongs to. */
	private GPIBDevice device;

	/** The power rate value of this {@link WT210PowerRate} in <code>mW</code>. */
	private double powerRateValue;

	/**
	 * Indicates whether or not positive power rates represent power
	 * consumption.
	 */
	private boolean positiveRatesAreConsumption;

	/**
	 * Creates a new {@link WT210PowerRate} for a given GPIB address.
	 * 
	 * @param deviceAddress
	 *            The GPIB address of the WT210 this {@link PowerRate} belongs
	 *            to.
	 * @param positiveRatesAreConsumption
	 *            Indicates whether or not a positive power rate represents a
	 *            power consumption. In contrast to battery interfaces, where
	 *            negative power rates always represent discharging (i.e., power
	 *            consumption) and positive rates charging, in the context of a
	 *            power meter the actual meaning depends on the wiring. Thus,
	 *            this meaning can be considered by using this flag.
	 */
	public WT210PowerRate(int deviceAddress, boolean positiveRatesAreConsumption) {
		try {
			device = WT210Util.getDeviceAtAddress(deviceAddress);
			this.positiveRatesAreConsumption = positiveRatesAreConsumption;
			powerRateValue = fetchPowerRateValue();
		}

		catch (IllegalStateException e) {
			throw new IllegalArgumentException("Illegal Address "
					+ deviceAddress + ". " + e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.PowerRate#getPowerRate()
	 */
	public double getPowerRate() throws IllegalStateException {
		return powerRateValue;
	}

	/**
	 * Helper method to fetch the power rate value.
	 * 
	 * @return The power rate in <code>mW</code>.
	 */
	private double fetchPowerRateValue() {
		try {
			WT210Util.initializeDevice(device, false);

			/*
			 * Clear the extended event register (Read and trash the response).
			 */
			device.sendCommand("STATUS:EESR?");

			/* Wait for the completion of the data updating. */
			// device.writeCommand("COMMUNICATE:WAIT 1");

			/* Read out the measurement data. */
			String response = device.sendCommand("MEASURE:NORMAL:VALUE?");

			/* TODO Can this time stamp be more precise? */
			this.timeStamp = System.nanoTime();

			String[] values = response.split(",");

			if (values.length > 2) {
				try {
					double powerRate = Double.parseDouble(values[2].trim()) * 1000;

					if (positiveRatesAreConsumption)
						return -powerRate;
					else
						return powerRate;
				} catch (NumberFormatException e) {
					throw new IllegalStateException(
							"Could not convert value into power rate. Value was "
									+ values[2]);
				}
			}

			else
				throw new IllegalStateException(
						"Illegal response value. Expected at least three values for V, I, W. Result was: "
								+ response);
		}

		catch (IOException e) {
			throw new IllegalStateException(
					"IOException during reading probe from the device. "
							+ e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.PowerRate#isPowerAdapterOnline()
	 */
	public boolean isPowerAdapterOnline() {
		/* Device does not know. Will always return false. */
		return false;
	}
}
