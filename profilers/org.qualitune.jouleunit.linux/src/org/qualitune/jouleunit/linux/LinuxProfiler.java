package org.qualitune.jouleunit.linux;

import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.PowerRate;

public class LinuxProfiler extends AbstractJouleProfiler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.AbstractEProfiler#getEMeasuringValue()
	 */
	public PowerRate getPowerRateValue() {
		return new LinuxPowerRate(1);
	}
}
