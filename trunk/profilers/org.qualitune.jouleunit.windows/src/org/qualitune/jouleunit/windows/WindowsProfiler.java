package org.qualitune.jouleunit.windows;

import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.PowerRate;

/**
 * {@link JouleProfiler} implementation for Windows OS.
 * 
 * @author Claas Wilke
 */
public class WindowsProfiler extends AbstractJouleProfiler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.AbstractEProfiler#getEMeasuringValue()
	 */
	public PowerRate getPowerRateValue() {
		return new WindowsPowerRate();
	}
}
