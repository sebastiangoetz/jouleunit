package org.qualitune.jouleunit;

import org.qualitune.jouleunit.persist.RestoredPowerRate;

/**
 * Abstract implementation of {@link JouleProfiler} containing platform
 * independent parts.
 * 
 * @author Claas Wilke
 */
public abstract class AbstractJouleProfiler extends AbstractProfilerNTimes {

	/**
	 * A private thread that measures a new {@link PowerRate} accordingly to the
	 * {@link AbstractJouleProfiler#profilingInterval} of this
	 * {@link JouleProfiler}.
	 */
	private class EProfilingThread extends Thread {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {

			while (isProfiling) {
				long currentTime = System.currentTimeMillis();
				currentProfile
						.addPowerRateValue(getPowerRateValueProbablyWithNoiseEstimation());

				try {
					long rest = System.currentTimeMillis() - currentTime
							+ profilingInterval;
					if (rest > 0)
						sleep(rest);
					// no else.
				}

				catch (InterruptedException e) {
					throw new IllegalStateException(
							"Thread for continous profiling failed.", e);
				}
			}
		}
	}

	/**
	 * The minimum refresh rate in <code>milliseconds</code> the profiler will
	 * use.
	 */
	protected int MINIMAL_REFRESH_RATE = 50;

	/**
	 * The maximum refresh rate in <code>milliseconds</code> the profiler will
	 * use.
	 */
	protected int MAXIMAL_REFRESH_RATE = 2000;

	/**
	 * If this {@link JouleProfiler} is profiling, this is the resulting
	 * {@link EnergyProfile}.
	 */
	protected SimpleEnergyProfile currentProfile;

	/**
	 * Length in seconds used for each iteration in the calibration/estimation
	 * phase. E.g., to compute the probe effect.
	 */
	protected int estimationPhaseLength = 5;

	/**
	 * The estimated base noise of the device under profiling in <code>mW</code>
	 * .
	 */
	protected long estimatedBaseNoise = 0l;

	/**
	 * The estimated probe effect of this {@link AbstractJouleProfiler} in
	 * <code>mW</code>.
	 */
	protected long estimatedProbeEffect = 0l;

	/**
	 * Indicates whether or not, the base noise of the device under profiling is
	 * considered during profiling (default is <code>true</code>).
	 */
	protected boolean isBaseNoiseCosiderationEnabled = true;

	/** Indicates whether or not this {@link JouleProfiler} has been calibrated. */
	protected boolean isCalibrated;

	/**
	 * Indicates whether or not, the probe effect of this {@link JouleProfiler}
	 * is considered during profiling (default is <code>true</code>).
	 */
	protected boolean isProbeEffectCosiderationEnabled = true;

	/**
	 * Indicates whether or not this {@link JouleProfiler} is currently
	 * profiling.
	 */
	protected boolean isProfiling;

	/**
	 * How often the energy consumption will be measured (in
	 * <code>milliseconds</code>).
	 */
	protected int profilingInterval = 1000;

	/** An {@link EProfilingThread} used for continous profiling. */
	protected EProfilingThread profilingThread;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EProfiler#calibrate()
	 */
	public void calibrate() {

		PowerRate firstValue = getPowerRateValue();

		/* Check if power rate sensor works. */
		if (firstValue.getPowerRate() >= 0) {
			// TODO throw new IllegalStateException(
			// "The power rate sensor of this JouleProfiler seems to not working correctly. "
			// + "Expected a discharging (negative) power rate.");
		}
		// no else.

		/* Compute the refresh rate of this profiler. */
		computeRefreshRate();

		if (estimationPhaseLength > 0) {
			/* Estimate the probe effect of this profiler. */
			estimateProbeEffect();

			/* Estimate the base consumption this profiler. */
			estimateBaseConsumption();
		}
		// no else.

		this.isCalibrated = true;
	}

	protected void computeRefreshRate() {
		PowerRate firstValue;
		PowerRate nextValue = getPowerRateValue();
		firstValue = nextValue;

		/* Measure the interval. */
		long currentTimeStamp = System.currentTimeMillis();
		while (nextValue.getPowerRate() == firstValue.getPowerRate()
				&& currentTimeStamp + (MAXIMAL_REFRESH_RATE * 2) > System
						.currentTimeMillis()) {
			try {
				Thread.sleep(MINIMAL_REFRESH_RATE * 2);
				nextValue = getPowerRateValue();
			} catch (InterruptedException e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
		}
		// end while.

		double intervalSize = nextValue.getTimeStamp()
				- firstValue.getTimeStamp();

		/*
		 * Profiling interval must be at least twice as high as the sensor's
		 * frequency (Shannon's law).
		 */
		profilingInterval = (int) Math.round(intervalSize / 1000000 / 2);

		if (intervalSize == 0)
			profilingInterval = MAXIMAL_REFRESH_RATE;
		else if (profilingInterval > MAXIMAL_REFRESH_RATE)
			profilingInterval = MAXIMAL_REFRESH_RATE;
		else if (profilingInterval < MINIMAL_REFRESH_RATE)
			profilingInterval = MINIMAL_REFRESH_RATE;
		// no else.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.EProfiler#endProfiling()
	 */
	public EnergyProfile endProfiling() {
		if (this.isProfiling) {
			currentProfile.setStopNanoTime(System.nanoTime());

			PowerRate lastValue = getPowerRateValueProbablyWithNoiseEstimation();
			this.isProfiling = false;

			/* Stops the thread for profiling. */
			// try {
			// profilingThread.join();
			// } catch (InterruptedException e) {
			// throw new IllegalStateException(
			// "Profiling Thread terminated abanormally.", e);
			// }

			currentProfile.addPowerRateValue(lastValue);
			currentProfile.setStopNanoTime(lastValue.getTimeStamp());
			EnergyProfile result = currentProfile;
			currentProfile = null;

			return result;
		}

		else
			throw new IllegalStateException(
					"Cannot end profiling before start profiling.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#getEstimatedBaseNoise()
	 */
	public long getEstimatedBaseConsumption() {
		return estimatedBaseNoise;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#getEstimatedProbeEffect()
	 */
	public long getEstimatedProbeEffect() {
		return estimatedProbeEffect;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EProfiler#getProfilingInterval()
	 */
	public float getProfilingInterval() {
		return 1000f / profilingInterval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EProfiler#isCalibrated()
	 */
	public boolean isCalibrated() {
		return this.isCalibrated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#isProfiling()
	 */
	public boolean isProfiling() {
		return isProfiling;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#logEvent(java.lang.String)
	 */
	public void logEvent(String identifier) {

		if (isProfiling) {
			currentProfile.logEvent(identifier,
					getPowerRateValueProbablyWithNoiseEstimation());
		}

		else
			throw new IllegalStateException(
					"Cannot log event when profiling is not running.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.JouleProfiler#setBaseNoiseConsiderationEnabled
	 * (boolean)
	 */
	public void setBaseConsumptionConsiderationEnabled(boolean enable) {
		this.isBaseNoiseCosiderationEnabled = enable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#setEstimationInterval(int)
	 */
	public void setEstimationInterval(int interval) {
		this.estimationPhaseLength = interval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.JouleProfiler#setProbeEffectConsiderationEnabled
	 * (boolean)
	 */
	public void setProbeEffectConsiderationEnabled(boolean enable) {
		isProbeEffectCosiderationEnabled = enable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EProfiler#setProfilingInterval(int)
	 */
	public void setProfilingInterval(int intervall) {
		if (intervall <= 0)
			throw new IllegalArgumentException(
					"Intervall less or equal than 0.");
		// no else.

		this.profilingInterval = intervall;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#startProfiling()
	 */
	public EnergyProfile startProfiling() {

		if (isProfiling)
			throw new IllegalStateException("Profiler is already running.");

		else {
			currentProfile = new SimpleEnergyProfile();
			PowerRate firstValue = getPowerRateValueProbablyWithNoiseEstimation();
			currentProfile.addPowerRateValue(firstValue);

			profilingThread = new EProfilingThread();
			isProfiling = true;
			currentProfile.setStartedNanoTime(firstValue.getTimeStamp());

			/* Starts a thread that measures continuously. */
			profilingThread.start();
		}

		return currentProfile;
	}

	/**
	 * Returns a platform specific {@link PowerRate} during profiling.
	 * 
	 * @return A platform specific {@link PowerRate}.
	 * 
	 * @throws IllegalStateException
	 *             Thrown, if no new {@link PowerRate} can be obtained for some
	 *             reason (e.g., lost connection).
	 */
	public abstract PowerRate getPowerRateValue() throws IllegalStateException;

	/**
	 * Helper method to estimate the base consumption of this
	 * {@link AbstractJouleProfiler}.
	 */
	protected void estimateBaseConsumption() {

		/* Ensure that the base consumption will be ignored. */
		boolean wasBaseConsumptionConsiderationEnabled = isBaseNoiseCosiderationEnabled;
		isBaseNoiseCosiderationEnabled = false;

		/* Ensure that the probe effect is considered. */
		boolean wasProbeEffectConsiderationEnabled = isProbeEffectCosiderationEnabled;
		isProbeEffectCosiderationEnabled = true;

		try {
			/* Do profiling with high frequency. */
			startProfiling();
			Thread.sleep(estimationPhaseLength * 1000);
			EnergyProfile profile = endProfiling();

			/* Compute base consumption. */
			long rate = Math.round(profile.getConsumedEnergy()
					/ profile.getDuration() * -1000000000);
			estimatedBaseNoise = rate;

		} catch (InterruptedException e) {
			estimatedBaseNoise = 0;
		}

		/* Reset probe effect and base consumption consideration. */
		isBaseNoiseCosiderationEnabled = wasBaseConsumptionConsiderationEnabled;
		isProbeEffectCosiderationEnabled = wasProbeEffectConsiderationEnabled;
	}

	/**
	 * Helper method to estimate the probe effect of this
	 * {@link AbstractJouleProfiler}.
	 */
	protected void estimateProbeEffect() {

		/* Ensure that the probe effect will be ignored. */
		boolean wasProbeEffectConsiderationEnabled = isProbeEffectCosiderationEnabled;
		isProbeEffectCosiderationEnabled = false;

		/* Ensure that the base consumption will be ignored. */
		boolean wasBaseConsumptionConsiderationEnabled = isBaseNoiseCosiderationEnabled;
		isBaseNoiseCosiderationEnabled = false;

		try {
			/* Do profiling with high frequency. */
			startProfiling();
			Thread.sleep(estimationPhaseLength * 1000);
			EnergyProfile highProfile = endProfiling();

			/* Do profiling with half the frequency. */
			this.setProfilingInterval(Math.round(1000 / this
					.getProfilingInterval() * 2));
			startProfiling();
			Thread.sleep(estimationPhaseLength * 1000);
			EnergyProfile lowProfile = endProfiling();

			/* Reset profiling frequency. */
			this.setProfilingInterval(Math.round(1000 / this
					.getProfilingInterval() / 2));

			/* Compute probe effect. */
			long highRate = Math.round(highProfile.getConsumedEnergy()
					/ highProfile.getDuration() * 1000000000);
			long lowRate = Math.round(lowProfile.getConsumedEnergy()
					/ lowProfile.getDuration() * 1000000000);
			estimatedProbeEffect = (highRate - lowRate) * -2;

		} catch (InterruptedException e) {
			estimatedProbeEffect = 0;
		}

		/* Reset probe effect consideration. */
		isProbeEffectCosiderationEnabled = wasProbeEffectConsiderationEnabled;
		isBaseNoiseCosiderationEnabled = wasBaseConsumptionConsiderationEnabled;
	}

	/**
	 * Helper method that returns a power rate value including or excluding the
	 * profiler's probe effect and the device's base consumption regarding the
	 * respective flags of this class.
	 * 
	 * @return A {@link PowerRate} value.
	 */
	protected PowerRate getPowerRateValueProbablyWithNoiseEstimation() {

		PowerRate rateValue = getPowerRateValue();
		double powerRate = rateValue.getPowerRate();

		if (isProbeEffectCosiderationEnabled || isBaseNoiseCosiderationEnabled) {

			/* Probably subtract the probe effect. */
			if (isProbeEffectCosiderationEnabled) {

				if (estimatedProbeEffect != 0)
					powerRate = powerRate - estimatedProbeEffect;
				// no else.
			}
			// no else.

			/* Probably subtract the base consumption. */
			if (isBaseNoiseCosiderationEnabled) {

				if (estimatedBaseNoise != 0)
					powerRate = powerRate - estimatedBaseNoise;
				// no else.
			}
			// no else.

			RestoredPowerRate newValue = new RestoredPowerRate();
			newValue.setPowerAdapterOnlineEnabled(rateValue
					.isPowerAdapterOnline());
			newValue.setTimeStamp(rateValue.getTimeStamp());
			newValue.setPowerRate(powerRate);

			return newValue;
		}

		else
			return rateValue;
	}

}
