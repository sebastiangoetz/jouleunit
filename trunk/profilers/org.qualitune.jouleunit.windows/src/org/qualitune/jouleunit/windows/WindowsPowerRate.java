package org.qualitune.jouleunit.windows;

import org.qualitune.jouleunit.AbstractPowerRate;
import org.qualitune.jouleunit.PowerRate;

/**
 * Windows specific implementation of {@link PowerRate}.
 * 
 * @author Claas Wilke
 */
public class WindowsPowerRate extends AbstractPowerRate {

	/** ID for serialization. */
	private static final long serialVersionUID = 838410674824811226L;

	/** The {@link SYSTEM_BATTERY_STATE} of this {@link WindowsPowerRate}. */
	protected PowrProf.SYSTEM_BATTERY_STATE batteryState;

	public WindowsPowerRate() {
		this.timeStamp = System.nanoTime();

		batteryState = new PowrProf.SYSTEM_BATTERY_STATE();
		PowrProf.INSTANCE.CallNtPowerInformation(5, null, 0,
				(PowrProf.SYSTEM_BATTERY_STATE) batteryState,
				batteryState.size());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.eunit.profiler.EMeasuringValue#getChargingRateInMilliWatt()
	 */
	public double getPowerRate() {
		return batteryState.getChargingRate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EMeasuringValue#isPowerAdapterOnline()
	 */
	public boolean isPowerAdapterOnline() {
		return batteryState.isAcOnline();
	}
}
