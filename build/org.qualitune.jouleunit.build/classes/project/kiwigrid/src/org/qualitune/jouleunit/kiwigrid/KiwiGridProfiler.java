package org.qualitune.jouleunit.kiwigrid;

import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.PowerRate;

/**
 * An {@link JouleProfiler} using KiwiGrid plugs for profiling.
 * 
 * @author Claas Wilke
 */
public class KiwiGridProfiler extends AbstractJouleProfiler {

	/** The IPv6 address of the KiwiGrid plug used for profiling. */
	protected String ipV6address;

	/**
	 * Creates a new {@link KiwiGridProfiler}.
	 * 
	 * @param ipV6address
	 *            The IPv6 address of the KiwiGrid plug used for profiling.
	 * @throws InitializationError
	 *             Thrown, if the given plug is not available or the given IP
	 *             address is wrong.
	 */
	public KiwiGridProfiler(String ipV6address) throws IllegalStateException {
		if (ipV6address == null)
			throw new IllegalArgumentException(
					"Argument 'ipV6address' cannot be null.");
		// no else.

		this.ipV6address = ipV6address;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EProfiler#getEMeasuringValue()
	 */
	public PowerRate getPowerRateValue() {
		try {
			return new KiwiGridPowerRate(ipV6address);
		} catch (IllegalStateException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
