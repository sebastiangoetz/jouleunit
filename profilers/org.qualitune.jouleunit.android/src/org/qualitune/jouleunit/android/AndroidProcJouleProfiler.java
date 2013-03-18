package org.qualitune.jouleunit.android;

import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.PowerRate;
import org.qualitune.jouleunit.ProfilingException;
import org.qualitune.jouleunit.android.logcat.BatteryLogLineProcessor;

/**
 * {@link JouleProfiler} implementation based on battery information from the
 * Android proc file system. The {@link AndroidProcJouleProfiler} is executed on
 * the test runner PC and communicates with a service on the device under test
 * via the ADB and LogCat.
 * 
 * @author Claas Wilke
 */
public class AndroidProcJouleProfiler extends AbstractJouleProfiler {

	/** The last power rate value received from the DUT via log cat. */
	public double lastPowerRateValue = 0d;

	/**
	 * The {@link AbstractAndroidJouleUnitCoordinator} this
	 * {@link AndroidProcJouleProfiler} belongs to.
	 */
	private AbstractAndroidJouleUnitCoordinator mCoordinator;

	/**
	 * The {@link BatteryLogLineProcessor} of this
	 * {@link AndroidProcJouleProfiler}.
	 */
	private BatteryLogLineProcessor mProcessor;

	/**
	 * Creates a new {@link AndroidProcJouleProfiler}.
	 * 
	 * @param coordinator
	 *            The {@link AbstractAndroidJouleUnitCoordinator} this
	 *            {@link AndroidProcJouleProfiler} belongs to.
	 */
	public AndroidProcJouleProfiler(
			AbstractAndroidJouleUnitCoordinator coordinator) {
		mCoordinator = coordinator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.AbstractJouleProfiler#getPowerRateValue()
	 */
	public PowerRate getPowerRateValue() throws IllegalStateException {

		/*
		 * TODO May not work if values are picked when the profiler is not
		 * profiling.
		 */
		return new AndroidProcPowerRate(lastPowerRateValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.AbstractJouleProfiler#startProfiling()
	 */
	@Override
	public EnergyProfile startProfiling() {
		try {
			startBatteryService();
		} catch (ProfilingException e1) {
			mCoordinator
					.reportError("Error during start of profiling service: "
							+ e1.getMessage());
			return null;
		}

		mProcessor = new BatteryLogLineProcessor(this);
		mCoordinator.startLogReading();
		mCoordinator.logOutputReceiver.addLogLineProcessor(mProcessor);

		return super.startProfiling();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.AbstractJouleProfiler#endProfiling()
	 */
	@Override
	public EnergyProfile endProfiling() {
		EnergyProfile result = super.endProfiling();

		if (null != mProcessor && null != mCoordinator.logOutputReceiver) {
			mCoordinator.logOutputReceiver.removeLogLineProcessor(mProcessor);
			mProcessor = null;
		}
		// no else.

		try {
			stopBatteryService();
		}

		catch (ProfilingException e) {
			mCoordinator
					.reportError("Error during termination of profiling service: "
							+ e.getMessage());
		}
		return result;
	}

	/**
	 * Helper method to start the hardware profiling service.
	 * 
	 * @throws ProfilingException
	 */
	private void startBatteryService() throws ProfilingException {

		mCoordinator.reportProgress("Start Battery Proc service...");

		mCoordinator.startLogReading();

		String command = "am startservice -a 'org.qualitune.jouleunit.android.battery.BatteryService'";
		mCoordinator.executeAdbCommand(command);
	}

	/**
	 * Helper method to stop the hardware profiling service.
	 * 
	 * @throws ProfilingException
	 */
	private void stopBatteryService() throws ProfilingException {

		String command = "am startservice -a 'org.qualitune.jouleunit.android.battery.BatteryServiceStopService'";
		mCoordinator.executeAdbCommand(command);

		mCoordinator.reportProgress("Hardware probe service stopped.");
	}
}
