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
public class JouleProfilerMock3 extends AbstractJouleProfiler {

	/**
	 * The power rate of this {@link JouleProfilerMock3}.
	 */
	protected int powerRate = 500;

	/**
	 * Whether or not the external power device of this
	 * {@link JouleProfilerMock3} is online.
	 */
	protected boolean isAcOnline;

	/** The last {@link PowerRate} returned by the {@link JouleProfilerMock3}. */
	protected PowerRate lastValue;

	/**
	 * Creates a new {@link JouleProfilerMock3}.
	 * 
	 * @param powerRate
	 *            The power rate of this {@link JouleProfilerMock3}.
	 * @param isAcOnline
	 *            Whether or not the external power device of this
	 *            {@link JouleProfilerMock3} is online.
	 */
	public JouleProfilerMock3(int powerRate, boolean isAcOnline) {
		this.powerRate = powerRate;
		this.isAcOnline = isAcOnline;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#getPowerRateValue()
	 */
	public PowerRate getPowerRateValue() {

		return new PowerRateMock(-Math.round(getProfilingInterval() * 100 + powerRate), this.isAcOnline);
	}
}
