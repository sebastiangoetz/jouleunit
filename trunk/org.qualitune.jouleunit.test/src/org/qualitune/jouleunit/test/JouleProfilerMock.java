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
public class JouleProfilerMock extends AbstractJouleProfiler {

	/**
	 * The refresh rate of this {@link JouleProfilerMock} to created new
	 * {@link PowerRate}s in <code>milliseconds</code>.
	 */
	protected int refreshRate = 500;

	/**
	 * The maximal power rate of {@link PowerRate}s returned by the
	 * {@link JouleProfilerMock} in <code>mW</code>.
	 */
	protected int maxPowerRate = 10000;

	/**
	 * Whether or not the external power device of this
	 * {@link JouleProfilerMock} is online.
	 */
	protected boolean isAcOnline;

	/** The last {@link PowerRate} returned by the {@link JouleProfilerMock}. */
	protected PowerRate lastValue;

	/**
	 * Creates a new {@link JouleProfilerMock}.
	 * 
	 * @param refreshRate
	 *            The refresh rate of this {@link JouleProfilerMock} to created
	 *            new {@link PowerRate}s <code>milliseconds</code>.
	 * @param maxPowerRate
	 *            The maximal power rate of {@link PowerRate}s returned by the
	 *            {@link JouleProfilerMock} in <code>mW</code>.
	 * @param isAcOnline
	 *            Whether or not the external power device of this
	 *            {@link JouleProfilerMock} is online.
	 */
	public JouleProfilerMock(int refreshRate, int maxPowerRate,
			boolean isAcOnline) {
		this.refreshRate = refreshRate;
		this.maxPowerRate = maxPowerRate;
		this.isAcOnline = isAcOnline;

		super.profilingInterval = refreshRate / 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#getPowerRateValue()
	 */
	public PowerRate getPowerRateValue() {

		long nanoTime = System.nanoTime();

		if (lastValue == null
				|| (lastValue.getTimeStamp() + refreshRate * 1000000l) < nanoTime) {
			lastValue = new PowerRateMock(Math.round((1 - Math.random())
					* (maxPowerRate - 1)) - 1, this.isAcOnline);
		}
		// no else.

		return lastValue;
	}
}
