package org.qualitune.jouleunit.android.logcat;

import org.qualitune.jouleunit.android.AndroidProcJouleProfiler;

/**
 * {@link ILogLineProcessor} implementation to received logged battery events.
 * 
 * @author Claas Wilke
 */
public class BatteryLogLineProcessor implements ILogLineProcessor {

	/** Tag for Log Cat messages that have been sent by the HW service. */
	private static final String LOG_CAT_BATTERY_SERVICE_TAG = "org.qualitune.jouleunit.android.batteryservice";

	/**
	 * The {@link AndroidProcJouleProfiler} to propagate profiled power rate
	 * values.
	 */
	private AndroidProcJouleProfiler mProfiler;

	/**
	 * Creates a new {@link AndroidProcJouleProfiler}.
	 * 
	 * @param profiler
	 *            The {@link AndroidProcJouleProfiler} to propagate profiled
	 *            power rate values.
	 */
	public BatteryLogLineProcessor(AndroidProcJouleProfiler profiler) {
		mProfiler = profiler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.android.logcat.ILogLineProcessor#processLine(
	 * java.lang.String)
	 */
	public boolean processLine(String line) {

		boolean processed = false;

		if (line.matches("^\\d\\d-\\d\\d\\s\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d\\sI/"
				+ LOG_CAT_BATTERY_SERVICE_TAG + ".*")) {

			String msgMsg = line.substring(line.indexOf("):") + 3);
			// TODO String msgTime = line.substring(0, 18);

			if (msgMsg.contains(":")) {
				String hardwareType = msgMsg.substring(0,
						msgMsg.indexOf(":") - 1);
				String value = msgMsg.substring(msgMsg.indexOf(":") + 1,
						msgMsg.length());

				/* TODO Propagate values. */
				if (hardwareType.equals("battery_power_rate")) {
					double powerrate = Double.parseDouble(value.trim());
					mProfiler.lastPowerRateValue = powerrate;
				}
				// no else.

				processed = true;
			}
			// no else.
		}
		// no else.

		return processed;
	}
}
