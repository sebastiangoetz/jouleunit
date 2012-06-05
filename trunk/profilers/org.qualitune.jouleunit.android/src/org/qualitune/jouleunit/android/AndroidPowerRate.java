package org.qualitune.jouleunit.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.qualitune.jouleunit.AbstractPowerRate;

public class AndroidPowerRate extends AbstractPowerRate {

	/** Generated ID for serialization. */
	private static final long serialVersionUID = 4140187401144848194L;

	protected boolean isPowerAdapterOnline;

	/** The Android device's voltage in <code>mV</code>. */
	protected long voltage;

	/** The Android device's amperage in <code>mA</code>. */
	protected long current;

	public AndroidPowerRate() {

		timeStamp = System.nanoTime();

		/*
		 * Get the device's voltage. Works for HTC Desire S and ASUS T101.
		 * Probably other information for other devices necessary.
		 */
		String voltageString = getLineFromFile("/sys/class/power_supply/battery/batt_vol");

		if (voltageString.length() > 0)
			voltage = Long.parseLong(voltageString);
		else {
			voltageString = getLineFromFile("/sys/class/power_supply/battery/voltage_now");

			if (voltageString.length() > 0)
				voltage = Long.parseLong(voltageString);
			// no else.
		}
		// no else.

		/*
		 * Get the device's current. Works for HTC Desire S. Probably other
		 * information for other devices necessary.
		 */
		current = getLineFromFile("/sys/class/power_supply/battery/smem_text",
				"batt_current");

		/* Check whether or not the device's power adapter is online. */
		String acOnline = getLineFromFile("/sys/class/power_supply/ac/online");
		String usbOnline = getLineFromFile("/sys/class/power_supply/usb/online");

		isPowerAdapterOnline = Integer.parseInt(acOnline) == 1
				|| Integer.parseInt(usbOnline) == 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.eunit.profiler.EMeasuredValue#getChargingRateInMilliWatt()
	 */
	public double getPowerRate() {
		return (voltage * current) / 1000l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EMeasuredValue#isPowerAdapterOnline()
	 */
	public boolean isPowerAdapterOnline() {
		return isPowerAdapterOnline;
	}

	/**
	 * Tries to open a {@link File} for a given fileName and returns its first
	 * line as a {@link String}.
	 * 
	 * @param fileName
	 *            The name of the {@link File}.
	 * @return The {@link File}'s first line or an empty {@link String}.
	 */
	protected String getLineFromFile(String fileName) {

		File file = new File(fileName);
		if (file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));

				String line = br.readLine();
				br.close();

				return line;
			}

			catch (IOException e) { /* Do nothing. */
			}
			// no else.

		}
		// no else.

		return "";
	}

	/**
	 * Tries to open a {@link File} for a given fileName and returns the value
	 * of the line as the long which starts with the given identifier.
	 * 
	 * @param fileName
	 *            The name of the {@link File}.
	 * @param id
	 *            The line's value identifier that shall be returned (e.g.,
	 *            <code>batt_discharge_current</code>).
	 * @return The {@link File}'s first line or an empty {@link String}.
	 */
	private long getLineFromFile(String fileName, String id) {

		long result = 0l;

		File file = new File(fileName);
		if (file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));

				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					else if (line.startsWith(id)) {
						result = Long.parseLong(line.substring(id.length() + 1)
								.trim());
						break;
					}
					// no else.
				}
				// no else.

				br.close();
			}

			catch (IOException e) { /* Do nothing. */
			}
			// no else.
		}
		// no else.

		return result;
	}
}
