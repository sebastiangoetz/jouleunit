package org.qualitune.jouleunit;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite {@link JouleProfiler} implementation to support profiling of
 * multiple devices in parallel.
 * 
 * @author Claas Wilke
 */
public class CompositeJouleProfiler extends AbstractProfilerNTimes {

	/**
	 * If this {@link JouleProfiler} is profiling, this is the resulting
	 * {@link EnergyProfile}.
	 */
	protected CompositeEnergyProfile currentProfile;

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

	/** The {@link JouleProfiler}s of this {@link CompositeJouleProfiler}. */
	protected List<JouleProfiler> profilers = new ArrayList<JouleProfiler>();

	/**
	 * How often the energy consumption will be measured (in
	 * <code>milliseconds</code>).
	 */
	protected int profilingInterval = 1000;

	/**
	 * Adds a given {@link JouleProfiler} to this {@link CompositeJouleProfiler}
	 * .
	 * 
	 * @param profiler
	 *            The {@link JouleProfiler} to be added.
	 */
	public void addProfiler(JouleProfiler profiler) {

		if (profiler == null)
			throw new IllegalArgumentException(
					"Argument 'profiler' cannot be null.");
		// no else.

		if (!profilers.contains(profiler))
			profilers.add(profiler);
		// no else.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#calibrate()
	 */
	public void calibrate() {
		for (JouleProfiler profiler : profilers)
			profiler.calibrate();
		// end for.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#endProfiling()
	 */
	public EnergyProfile endProfiling() throws IllegalStateException {

		for (JouleProfiler profiler : profilers)
			currentProfile.addProfile(profiler.endProfiling());
		// end for.

		isProfiling = false;

		return currentProfile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#getEstimatedBaseConsumption()
	 */
	public long getEstimatedBaseConsumption() {

		estimatedBaseNoise = 0l;

		for (JouleProfiler profiler : profilers)
			estimatedBaseNoise += profiler.getEstimatedBaseConsumption();
		// end for.

		return estimatedBaseNoise;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#getEstimatedProbeEffect()
	 */
	public long getEstimatedProbeEffect() {

		estimatedProbeEffect = 0l;

		for (JouleProfiler profiler : profilers)
			estimatedProbeEffect += profiler.getEstimatedProbeEffect();
		// end for.

		return estimatedProbeEffect;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#getProfilingInterval()
	 */
	public float getProfilingInterval() {

		if (profilers.size() > 0) {
			float result = Float.MAX_VALUE;

			for (JouleProfiler profiler : profilers)
				result = Math.min(result, profiler.getProfilingInterval());
			// end for.

			return result;
		}

		else
			return 0f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#isCalibrated()
	 */
	public boolean isCalibrated() {

		isCalibrated = true;

		for (JouleProfiler profiler : profilers)
			isCalibrated &= profiler.isCalibrated();
		// end for.

		return isCalibrated;
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
		for (JouleProfiler profiler : profilers)
			profiler.logEvent(identifier);
		// end for.

		currentProfile.loggedEvents.put(identifier, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.JouleProfiler#setBaseConsumptionConsiderationEnabled
	 * (boolean)
	 */
	public void setBaseConsumptionConsiderationEnabled(boolean enable) {
		for (JouleProfiler profiler : profilers)
			profiler.setBaseConsumptionConsiderationEnabled(enable);
		// end for.

		isBaseNoiseCosiderationEnabled = enable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#setEstimationInterval(int)
	 */
	public void setEstimationInterval(int interval) {
		for (JouleProfiler profiler : profilers)
			profiler.setEstimationInterval(interval);
		// end for.

		estimationPhaseLength = interval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.JouleProfiler#setProbeEffectConsiderationEnabled
	 * (boolean)
	 */
	public void setProbeEffectConsiderationEnabled(boolean enable) {
		for (JouleProfiler profiler : profilers)
			profiler.setProbeEffectConsiderationEnabled(enable);
		// end for.

		isProbeEffectCosiderationEnabled = enable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#setProfilingInterval(int)
	 */
	public void setProfilingInterval(int interval) {
		for (JouleProfiler profiler : profilers)
			profiler.setProfilingInterval(interval);
		// end for.

		profilingInterval = interval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.JouleProfiler#startProfiling()
	 */
	public EnergyProfile startProfiling() {

		currentProfile = new CompositeEnergyProfile();

		for (JouleProfiler profiler : profilers) {
			currentProfile.addProfile(profiler.startProfiling());
		}
		// end for.

		isProfiling = true;

		return currentProfile;
	}
}
