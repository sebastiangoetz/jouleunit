package org.qualitune.jouleunit;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Interface for {@link JouleProfiler}s that can be used to create
 * {@link EnergyProfile}s by profiling. {@link JouleProfiler}s are expected to
 * be platform (OS) specific. Thus, multiple implementations can exist.
 * 
 * @author Claas Wilke
 */
public interface JouleProfiler {

	/**
	 * Calibrates the {@link JouleProfiler} for the platform under test (used to
	 * measure the minimum refresh interval for power rate values etc.).
	 */
	public void calibrate();

	/**
	 * Ends the profiling of this {@link JouleProfiler}. If profiling has not
	 * been started before, an {@link IllegalStateException} is expected to be
	 * thrown.
	 * 
	 * @return The {@link EnergyProfile} of the profiling.
	 * @throws IllegalStateException
	 *             Thrown, if profiling has not been initialized (using
	 *             {@link JouleProfiler#startProfiling()}).
	 */
	public EnergyProfile endProfiling() throws IllegalStateException;

	/**
	 * Returns the estimated base noise of this {@link JouleProfiler} in
	 * <code>mW</code>. The base noise is the energy consumption caused by the
	 * device under profiling without executing an application.
	 */
	public long getEstimatedBaseConsumption();

	/**
	 * Returns the estimated probe effect of this {@link JouleProfiler} in
	 * <code>mW</code>. The probe effect is the energy consumption caused by
	 * this {@link JouleProfiler} itself during profiling.
	 */
	public long getEstimatedProbeEffect();

	/**
	 * Returns how often (in <code>Hz</code>) this {@link JouleProfiler} will
	 * profile the energy consumption.
	 * 
	 * @return How often the energy consumption will be measured (in
	 *         <code>Hz</code>).
	 */
	public float getProfilingInterval();

	/**
	 * Indicates whether or not this {@link JouleProfiler} has been already
	 * calibrated.
	 */
	public boolean isCalibrated();

	/**
	 * Indicates whether or not this {@link JouleProfiler} is currently
	 * profiling.
	 * 
	 * @return <code>true</code> if profiling is running.
	 */
	public boolean isProfiling();

	/**
	 * Logs a given event for this {@link JouleProfiler}. An event consists of
	 * an identifier that must be unique within the currently profiled
	 * {@link EnergyProfile}. Events can be used to separate the
	 * {@link EnergyProfile} into multiple parts, for which individual energy
	 * consumptions can be computed. E.g., this can be used to log the begin or
	 * end of the specific method's invocation during testing.
	 * 
	 * @param identifier
	 *            The identifier of the event (<strong>Must be unique within the
	 *            {@link EnergyProfile}!</strong>).
	 */
	public void logEvent(String identifier);

	/**
	 * Invokes a given method on a given source a given timesand results in a
	 * {@link List} of {@link EnergyProfile}s containing one
	 * {@link EnergyProfile} for each iteration.
	 * 
	 * @param source
	 *            The {@link Object} used as source of the method invocation.
	 * @param methodName
	 *            The name of the {@link Method} to be invoked.
	 * @param args
	 *            The arguments with which the {@link Method} shall be invoked
	 *            (an array of {@link Object}s).
	 * @param iterations
	 *            The number of iterations (<b>> 0</b>).
	 * @return A {@link List} of {@link EnergyProfile}s.
	 * @throws ProfilingException
	 *             Thrown if method finding or invocation or profiling itself
	 *             fails.
	 */
	public List<EnergyProfile> profileNTimes(Object source, String methodName,
			Object[] args, int iterations) throws ProfilingException;

	/**
	 * Invokes a given method on a given source a given times using and results
	 * in a {@link List} of {@link EnergyProfile}s containing one
	 * {@link EnergyProfile} for each iteration.
	 * 
	 * @param source
	 *            The {@link Object} used as source of the method invocation.
	 * @param methodName
	 *            The name of the {@link Method} to be invoked.
	 * @param args
	 *            The arguments with which the {@link Method} shall be invoked
	 *            (an array of {@link Object}s).
	 * @param setUpMethodName
	 *            The name of the {@link Method} to be invoked as setUp method
	 *            before every profiling cycle (can be <code>null</code>).
	 * @param setUpArgs
	 *            The arguments with which the setUp {@link Method} shall be
	 *            invoked (an array of {@link Object}s). Can be
	 *            <code>null</code> if <code>setUpMethodName</code> is
	 *            <code>null</code> as well.
	 * @param tearDownMethodName
	 *            The name of the {@link Method} to be invoked as tearDown
	 *            method after every profiling cycle (can be <code>null</code>).
	 * @param tearDownArgs
	 *            The arguments with which the tearDown{@link Method} shall be
	 *            invoked (an array of {@link Object}s). Can be null if the
	 *            <code>tearDownMethodName</code> is <code>null</code> as well.
	 * @param iterations
	 *            The number of iterations (<b>> 0</b>).
	 * @return A {@link List} of {@link EnergyProfile}s.
	 * @throws ProfilingException
	 *             Thrown if method finding or invocation or profiling itself
	 *             fails.
	 */
	public List<EnergyProfile> profileNTimes(Object source, String methodName,
			Object[] args, String setUpMethodName, Object[] setUpArgs,
			String tearDownMethodName, Object[] tearDownArgs, int iterations)
			throws ProfilingException;

	/**
	 * Invokes a given method on a given source a given times and results in a
	 * {@link List} of {@link EnergyProfile}s containing one
	 * {@link EnergyProfile} for each iteration.
	 * 
	 * @param source
	 *            The {@link Object} used as source of the method invocation.
	 * @param methodName
	 *            The name of the {@link Method} to be invoked.
	 * @param args
	 *            The arguments with which the {@link Method} shall be invoked
	 *            (an array of {@link Object}s).
	 * @param setUpSource
	 *            The {@link Object} on which the possibly given setUp method
	 *            shall be invoked. If <code>null</code>, the same
	 *            <code>source</code> argument is used instead.
	 * @param setUpMethodName
	 *            The name of the {@link Method} to be invoked as setUp method
	 *            before every profiling cycle (can be <code>null</code>).
	 * @param setUpArgs
	 *            The arguments with which the setUp {@link Method} shall be
	 *            invoked (an array of {@link Object}s). Can be
	 *            <code>null</code> if <code>setUpMethodName</code> is
	 *            <code>null</code> as well.
	 * @param tearDownSource
	 *            The {@link Object} on which the possibly given setUp method
	 *            shall be invoked. If <code>null</code>, the same
	 *            <code>source</code> argument is used instead.
	 * @param tearDownMethodName
	 *            The name of the {@link Method} to be invoked as tearDown
	 *            method after every profiling cycle (can be <code>null</code>).
	 * @param tearDownArgs
	 *            The arguments with which the tearDown{@link Method} shall be
	 *            invoked (an array of {@link Object}s). Can be null if the
	 *            <code>tearDownMethodName</code> is <code>null</code> as well.
	 * @param iterations
	 *            The number of iterations (<b>> 0</b>).
	 * @return A {@link List} of {@link EnergyProfile}s.
	 * @throws ProfilingException
	 *             Thrown if method finding or invocation or profiling itself
	 *             fails.
	 */
	public List<EnergyProfile> profileNTimes(Object source, String methodName,
			Object[] args, Object setUpSource, String setUpMethodName,
			Object[] setUpArgs, Object tearDownSource,
			String tearDownMethodName, Object[] tearDownArgs, int iterations)
			throws ProfilingException;

	/**
	 * Enables or disables this {@link JouleProfiler} to consider the device's
	 * base noise (or base power rate) during profiling. If enabled, the base
	 * noise will subtracted from each {@link PowerRate} profiled with this
	 * {@link JouleProfiler}.
	 * 
	 * @param enable
	 *            If <code>true</code> base noise consideration is enabled
	 *            (default is <code>true</code>).
	 */
	public void setBaseConsumptionConsiderationEnabled(boolean enable);

	/**
	 * Sets the length in seconds used for each iteration in the
	 * calibration/estimation phase. E.g., to compute the probe effect.
	 * 
	 * @param interval
	 *            Length in seconds used for each iteration in the
	 *            calibration/estimation phase. E.g., to compute the probe
	 *            effect.
	 */
	public void setEstimationInterval(int interval);

	/**
	 * Enables or disables this {@link JouleProfiler} to consider its probe
	 * effect during profiling. If enabled, the probe effect will subtracted
	 * from each {@link PowerRate} profiled with this {@link JouleProfiler}.
	 * 
	 * @param enable
	 *            If <code>true</code> probe effect consideration is enabled
	 *            (default is <code>true</code>).
	 */
	public void setProbeEffectConsiderationEnabled(boolean enable);

	/**
	 * Configures how often (in <code>milliseconds</code>) this
	 * {@link JouleProfiler} will profile the energy consumption.
	 * 
	 * @param intervall
	 *            How often the energy consumption will be measured (in
	 *            <code>milliseconds</code>).
	 */
	public void setProfilingInterval(int intervall);

	/**
	 * Starts the profiling of this {@link JouleProfiler}.
	 * 
	 * @throws IllegalStateException
	 *             Thrown, if this {@link JouleProfiler} is not able to start
	 *             profiling (e.g., while profiling is already running).
	 * @return The {@link EnergyProfile} that will contain the {@link PowerRate}
	 *         s to be profiled.
	 */
	public EnergyProfile startProfiling();
}
