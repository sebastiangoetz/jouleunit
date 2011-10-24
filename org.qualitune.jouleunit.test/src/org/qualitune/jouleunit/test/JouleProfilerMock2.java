package org.qualitune.jouleunit.test;

import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.PowerRate;

/**
 * Dummy {@link JouleProfiler} implementation to test other concepts of
 * JouleUnit.
 * 
 * @author Claas Wilke
 */
public class JouleProfilerMock2 extends AbstractJouleProfiler {

	/**
	 * The power rate of this {@link JouleProfilerMock2}.
	 */
	protected int powerRate = 500;

	/**
	 * Whether or not the external power device of this
	 * {@link JouleProfilerMock2} is online.
	 */
	protected boolean isAcOnline;

	/** The last {@link PowerRate} returned by the {@link JouleProfilerMock2}. */
	protected PowerRate lastValue;

	/**
	 * Creates a new {@link JouleProfilerMock2}.
	 * 
	 * @param powerRate
	 *            The power rate of this {@link JouleProfilerMock2}.
	 * @param isAcOnline
	 *            Whether or not the external power device of this
	 *            {@link JouleProfilerMock2} is online.
	 */
	public JouleProfilerMock2(int powerRate, boolean isAcOnline) {
		this.powerRate = powerRate;
		this.isAcOnline = isAcOnline;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#getPowerRateValue()
	 */
	public PowerRate getPowerRateValue() {

		return new PowerRateMock(-Math.round(getProfilingInterval() * 100), this.isAcOnline);
	}
}
