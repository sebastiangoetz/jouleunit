package org.qualitune.jouleunit.android;

import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.PowerRate;

/**
 * {@link JouleProfiler} implementation for Android devices.
 * 
 * @author Claas Wilke
 */
public class AndroidJouleProfiler extends AbstractJouleProfiler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EProfiler#getEMeasuringValue()
	 */
	public PowerRate getPowerRateValue() throws IllegalStateException {
		return new AndroidPowerRate();
	}
}
