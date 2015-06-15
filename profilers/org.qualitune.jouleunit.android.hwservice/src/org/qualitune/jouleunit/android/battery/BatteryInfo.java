package org.qualitune.jouleunit.android.battery;

import org.qualitune.jouleunit.android.ProcUtil;

import android.util.Log;

/**
 * Provides methods to get information on the device under test's power
 * consumption via the software-based battery interface.
 * 
 * @author Claas Wilke
 */
public class BatteryInfo {

	/** The current voltage of the battery (in mV). */
	private long voltage = -1l;

	/** The current amperage of the battery (in mA). */
	private long current = -1l;

	/** The current capacity of the battery (in percent). */
	private long capacity = -1l;

	/**
	 * The name of the file in the proc file system containing the capacity
	 * infos.
	 */
	private String capacityInfoFile;

	/**
	 * The name of the file in the proc file system containing the current
	 * infos.
	 */
	private String currentInfoFile;

	/**
	 * The name of the file in the proc file system containing the voltage
	 * infos.
	 */
	private String voltageInfoFile;

	/**
	 * Creates a new {@link BatteryInfo}.
	 */
	public BatteryInfo() {
		voltageInfoFile = "/sys/class/power_supply/battery/batt_vol";

		if (!ProcUtil.isFileExisting(voltageInfoFile))
			voltageInfoFile = "/sys/class/power_supply/battery/voltage_now";
		// no else.

		if (!ProcUtil.isFileExisting(voltageInfoFile))
			throw new IllegalStateException(
					"The proc file to detect voltage infos cannot be found.");
		// no else.

		currentInfoFile = "/sys/class/power_supply/battery/smem_text";

//	TODO	if (!ProcUtil.isFileExisting(currentInfoFile))
//			throw new IllegalStateException(
//					"The proc file to detect current infos cannot be found.");
		// no else.

		capacityInfoFile = "/sys/class/power_supply/battery/capacity";

		if (!ProcUtil.isFileExisting(capacityInfoFile))
			throw new IllegalStateException(
					"The proc file to detect capacity infos cannot be found.");
		// no else.
	}

	/**
	 * Updates the infos regarding sent and received bytes and packages.
	 */
	public void updateInfo() {
		voltage = -1l;
		current = -1l;
		capacity = -1l;

		voltage = ProcUtil.getLongFromFile(voltageInfoFile);

		if (voltage == -1)
			Log.e(BatteryService.LOG_TAG, "voltage file not found: "
					+ voltageInfoFile);
		// no else.

		current = ProcUtil.getLongFromFileLine(currentInfoFile, "batt_current");

		if (current == -1)
			Log.e(BatteryService.LOG_TAG, "current file not found: "
					+ currentInfoFile);
		// no else.

		capacity = ProcUtil.getLongFromFile(capacityInfoFile);

		if (capacity == -1)
			Log.e(BatteryService.LOG_TAG, "capacity file not found: "
					+ capacityInfoFile);
		// no else.
	}

	/**
	 * Returns the current capacity.
	 * 
	 * @return The current capacity in percent.
	 */
	public long getCurrentCapacity() {
		return capacity;
	}

	/**
	 * Returns the current current.
	 * 
	 * @return The current current in mA.
	 */
	public long getCurrentCurrent() {
		return current;
	}

	/**
	 * Returns the current voltage.
	 * 
	 * @return The current voltage in mV.
	 */
	public long getCurrentVoltage() {
		return voltage;
	}

	/**
	 * Returns the current power rate.
	 * 
	 * @return The current power rate in mW.
	 */
	public double getCurrentPowerRate() {
		return (voltage * current) / 1000l;
	}
}
