package org.qualitune.jouleunit.linux;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.qualitune.jouleunit.AbstractPowerRate;

/**
 * Represents the info of a battery's state as available from the Linux Proc
 * file system.
 * 
 * @author Claas Wilke
 */
public class LinuxPowerRate extends AbstractPowerRate {

	/** ID for serialization. */
	private static final long serialVersionUID = 4694750082570941402L;

	/**
	 * The device number of the battery this {@link LinuxPowerRate}
	 * belongs to.
	 */
	protected int batteryNumber;

	/** Indicates whether or not the battery is present. */
	protected boolean batteryPresent;

	/** The current {@link CapacityState} of the battery. */
	protected CapacityState capacityState;

	/** The current {@link ChargingState} of the battery. */
	protected ChargingState chargingState;

	/**
	 * The rate with which the battery is currently charged in <code>mA</code>.
	 * <b>This value will ever be positive or 0.</b> Use
	 * {@link LinuxPowerRate#chargingState} to identify whether the
	 * battery is currently charged or discharged.
	 */
	protected long chargingRate;

	/** The present voltage of the battery in <code>mV</code>. */
	protected long presentVoltage;

	/** The remaining capacity of the battery in <code>mAh</code>. */
	protected long remainingCapacity;

	/**
	 * Creates a new {@link LinuxPowerRate} for a given battery device
	 * number (typically starting with 1).
	 * 
	 * @param batteryNumber
	 *            The battery device number (typically <code>1</code>).
	 * @throws IllegalStateException
	 *             Thrown, if the battery state cannot be found in the proc file
	 *             system.
	 */
	public LinuxPowerRate(int batteryNumber) throws IllegalStateException {

		this.batteryNumber = batteryNumber;

		File file = new File("/proc/acpi/battery/BAT" + batteryNumber
				+ "/state");

		this.timeStamp = System.nanoTime();

		if (file.exists()) {
			try {
				FileInputStream fstream = new FileInputStream(file);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));

				String line;

				while ((line = br.readLine()) != null) {
					if (line.startsWith("present:")) {
						batteryPresent = (line.substring(8).trim()
								.equals("yes"));
					}

					else if (line.startsWith("capacity state:")) {
						String state = line.substring(15).trim();

						if (state.toLowerCase().equals("ok"))
							capacityState = CapacityState.OK;
						else if (state.toLowerCase().equals("unknown"))
							capacityState = CapacityState.UNKNOWN;
						/*
						 * TODO this is for testing. Investigate remaining
						 * possible values.
						 */
						else
							throw new IllegalArgumentException(
									"New capacity state detected: " + state);
					}

					else if (line.startsWith("charging state:")) {
						String state = line.substring(15).trim();

						if (state.toLowerCase().equals("charged"))
							chargingState = ChargingState.CHARGED;
						else if (state.toLowerCase().equals("charging"))
							chargingState = ChargingState.CHARGING;
						else if (state.toLowerCase().equals("discharging"))
							chargingState = ChargingState.DISCHARGING;
						else if (state.toLowerCase().equals("unknown"))
							chargingState = ChargingState.UNKNOWN;
						/*
						 * TODO this is for testing. Investigate remaining
						 * possible values.
						 */
						else
							throw new IllegalArgumentException(
									"New charging state detected: " + state);
					} else if (line.startsWith("present rate:"))
						chargingRate = Long.parseLong(line.substring(13,
								line.length() - 3).trim());
					else if (line.startsWith("remaining capacity:"))
						remainingCapacity = Long.parseLong(line.substring(19,
								line.length() - 4).trim());
					else if (line.startsWith("present voltage:"))
						presentVoltage = Long.parseLong(line.substring(16,
								line.length() - 3).trim());
					// no else.
				}

				in.close();
			}

			catch (FileNotFoundException e) {
				throw new IllegalStateException(
						"Cannot find status file in proc file system for battery "
								+ batteryNumber + ".", e);
			}

			catch (IOException e) {
				throw new IllegalStateException(
						"Cannot open status file in proc file system for battery "
								+ batteryNumber + ".", e);
			}
		}

		else {
			throw new IllegalStateException(
					"Cannot find status of battery "
							+ batteryNumber
							+ " in proc. Is this a Linux platform and does the file exist anyway?");
		}
	}

	/**
	 * Returns the device number of the battery this
	 * {@link LinuxPowerRate} belongs to.
	 * 
	 * @return The device number of the battery this
	 *         {@link LinuxPowerRate} belongs to.
	 */
	public int getBatteryNumber() {
		return batteryNumber;
	}

	/**
	 * Returns the current {@link CapacityState} of the battery.
	 * 
	 * @return The current {@link CapacityState} of the battery.
	 */
	public CapacityState getCapacityState() {
		return capacityState;
	}

	/**
	 * Returns the current {@link ChargingState} of the battery.
	 * 
	 * @return The current {@link ChargingState} of the battery.
	 */
	public ChargingState getChargingState() {
		return chargingState;
	}

	/**
	 * Returns the rate with which the battery is currently charged in
	 * <code>mA</code>. <b>This value will ever be positive or 0.</b> Use
	 * {@link LinuxPowerRate#chargingState} to identify whether the
	 * battery is currently charged or discharged.
	 * 
	 * @return The current charging rate in <code>mA</code>.
	 */
	public long getChargingRate() {
		return chargingRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.eunit.profiler.EMeasuringValue#getChargingRateInMilliWatt()
	 */
	public double getPowerRate() {
		return getChargingRate() * getPresentVoltage() / 1000 * -1;
	}

	/**
	 * Returns the present voltage of the battery in <code>mV</code>.
	 * 
	 * @return The present voltage of the battery in <code>mV</code>.
	 */
	public long getPresentVoltage() {
		return presentVoltage;
	}

	/**
	 * Returns the remaining capacity of the battery in <code>mAh</code>.
	 * 
	 * @return The remaining capacity of the battery in <code>mAh</code>.
	 */
	public long getRemainingCapacity() {
		return remainingCapacity;
	}

	/**
	 * Indicates whether or not the battery is present.
	 * 
	 * @return If <code>true</code> the battery is present.
	 */
	public boolean isBatteryPresent() {
		return batteryPresent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EMeasuringValue#isPowerAdapterOnline()
	 */
	public boolean isPowerAdapterOnline() {
		return getChargingState() != ChargingState.DISCHARGING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();

		result.append("Battery state for battery #" + batteryNumber + "\n");
		result.append("Timestamp:               " + timeStamp + "\n");
		result.append("present:                 " + batteryPresent + "\n");
		result.append("capacity state:          " + capacityState + "\n");
		result.append("charging state:          " + chargingState + "\n");
		result.append("present rate:            " + chargingRate + " mA" + "\n");
		result.append("remaining capacity:      " + remainingCapacity + " mAh"
				+ "\n");
		result.append("present voltage:         " + presentVoltage + " mV"
				+ "\n");

		return result.toString();
	}
}
