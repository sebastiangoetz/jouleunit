package org.qualitune.jouleunit.android;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods to get information on the device under test's CPU
 * utilization.
 * 
 * @author Claas Wilke
 */
public class CpuInfo {

	/**
	 * The paths of the directories in the proc file system for all CPUs
	 * existing within the device.
	 */
	private String[] mCpuDirectories;

	/**
	 * Creates a new {@link CpuInfo}.
	 */
	public CpuInfo() {
		List<String> cpuDirectories = new ArrayList<String>();

		int index = 0;

		while (ProcUtil.isDirectoryExisting("/sys/devices/system/cpu/cpu"
				+ index + "/")) {
			cpuDirectories.add("/sys/devices/system/cpu/cpu" + index + "/");
			index++;
		}
		// end while.

		if (cpuDirectories.size() == 0)
			throw new IllegalStateException(
					"No CPU found within the proc file system.");
		else
			mCpuDirectories = cpuDirectories.toArray(new String[0]);
	}

	/**
	 * Returns the devices current CPU frequencies as a long array.
	 * 
	 * @return The CPU frequencies in Hz.
	 */
	public long[] getCPUFrequencies() {

		long[] result = new long[mCpuDirectories.length];

		for (int index = 0; index < mCpuDirectories.length; index++) {
			result[index] = ProcUtil
					.getLongFromFile("/sys/devices/system/cpu/cpu" + index
							+ "/cpufreq/scaling_cur_freq");

			/*
			 * If the file cannot be found, the core is switched off, and thus,
			 * its frequency is 0.
			 */
			if (result[index] == -1l)
				result[index] = 0;
			// no else.
		}

		return result;
	}
}
