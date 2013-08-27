package org.qualitune.jouleunit.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import org.qualitune.jouleunit.CompositeJouleProfiler;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.ProfilingException;
import org.qualitune.jouleunit.android.logcat.ILogLineProcessor;
import org.qualitune.jouleunit.android.logcat.LogOutputReceiver;
import org.qualitune.jouleunit.android.logcat.TestCaseLogLineProcessor;
import org.qualitune.jouleunit.coordinator.JouleUnitCoordinator;
import org.qualitune.jouleunit.wt210.WT210Profiler;

/**
 * Android-specific abstract implementation of the {@link JouleUnitCoordinator}.
 * 
 * @author Claas Wilke
 */
public abstract class AbstractAndroidJouleUnitCoordinator extends
		JouleUnitCoordinator {

	/** Thread used to run the logcat process. */
	private Thread logCatThread;

	/** Responsible to receive logged events from the device under test. */
	protected LogOutputReceiver logOutputReceiver;

	/** The GPIB addresses of all {@link WT210Profiler} used for profiling. */
	private int[] profilerAdresses;

	/**
	 * Helper method to set the GPIB addresses of all {@link WT210Profiler}s
	 * used for profiling.
	 * 
	 * @param adresses
	 *            The GPIB addresses of all {@link WT210Profiler} used for
	 *            profiling. Use an empty array for a {@link ProfilerDummy}
	 *            implementation.
	 */
	public void setProfilerAddresses(int[] adresses) {
		this.profilerAdresses = adresses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#
	 * computeDutTimestampOffset()
	 */
	protected void computeDutTimestampOffset() throws ProfilingException {
		/* Clear the log and start listening to logged events. */
		clearLog();
		startLogReading();

		reportProgress("Compute time synchronization offset ...");

		/* If necessary, the HW Service should be shipped to the device. */
		File apkFile = getHardwareServiceFile();

		/* Install apk on device under test. */
		if (null != apkFile && apkFile.exists()) {
			/* Copy APK file to temporary directory. */
			String tempdir = System.getProperty("java.io.tmpdir") + "JouleUnit";
			File tempDir = new File(tempdir);
			boolean success = true;

			if (!tempDir.isDirectory())
				success = tempDir.mkdir();
			// no else.

			if (success) {
				try {
					File tmpFile = File.createTempFile(
							"org.qualitune.jouleunit.android.hwservice",
							".apk", tempDir);

					/* Copy file. */
					FileChannel source = null;
					FileChannel destination = null;

					try {
						source = new FileInputStream(apkFile).getChannel();
						destination = new FileOutputStream(tmpFile)
								.getChannel();
						destination.transferFrom(source, 0, source.size());
					} finally {
						if (source != null)
							source.close();
						// no else.
						if (destination != null)
							destination.close();
						// no else.
					}

					installApk(tmpFile.getAbsolutePath());
					tmpFile.deleteOnExit();
				}

				catch (Exception e) {
					reportError("Error during installation of HW service: "
							+ e.getMessage());
				}
			} else
				reportError("The Hardware Service application could not be found. Thus, it cannot be installed.");
		}

		else
			reportError("The Hardware Service application could not be found. Thus, it cannot be installed.");

		final String command = "am startservice -a 'org.qualitune.jouleunit.android.TimeSyncService'";

		if (null != logOutputReceiver)
			logOutputReceiver.setLastRequestForTimeStamp(System
					.currentTimeMillis());
		// no else.

		/* Do this in an extra thread as it sometimes last unexpectedly long. */
		new Thread("TimeSyncService") {
			public void run() {
				try {
					executeAdbCommand(command);
				}

				catch (ProfilingException e) {
					reportError("Error during start of time sync service: "
							+ e.getMessage());
				}
			};
		}.start();
	}

	/**
	 * Helper method to create a {@link LogOutputReceiver}. Can be overriden to
	 * solve type inheritance problem with Eclipse implementation.
	 */
	protected LogOutputReceiver createLogOutputReceiver() {
		return new LogOutputReceiver(testSuiteProfile, this);
	}

	/**
	 * Helper method to execute a given ADB command.
	 * 
	 * @param cmd
	 *            The command to be executed.
	 * @throws ProfilingException
	 *             Thrown, if the command execution fails.
	 */
	protected abstract void executeAdbCommand(String cmd)
			throws ProfilingException;

	/**
	 * Helper method to execute a given ADB command and collect its result by a
	 * given {@link LogOutputReceiver}.
	 * 
	 * @param cmd
	 *            The command to be executed.
	 * @param logOutputReceiver
	 *            The {@link LogOutputReceiver} used to receive the output
	 *            results.
	 * @throws ProfilingException
	 *             Thrown, if the command execution fails.
	 */
	protected abstract void executeAdbCommand(String cmd,
			LogOutputReceiver logOutputReceiver) throws ProfilingException;

	/**
	 * Helper method that returns a {@link File} object that leads to the APK
	 * file of the hardware profiling service.
	 * 
	 * @return The {@link File} object that leads to the APK file of the
	 *         hardware profiling service.
	 */
	protected abstract File getHardwareServiceFile();

	/**
	 * Helper method to install an APK on the device under test.
	 * 
	 * @param path
	 *            The path to the APK file in the file system.
	 * @throws ProfilingException
	 *             Thrown, if installation fails.
	 */
	protected void installApk(String path) throws ProfilingException {
		File apkFile = new File(path);

		if (null != apkFile && !apkFile.exists())
			throw new ProfilingException("Error: APK file "
					+ apkFile.getAbsolutePath() + " not found.");
		// no else.

		String cmd = "install -r \"" + apkFile.getAbsolutePath() + "\"";
		reportProgress("Install APK ...");
		executeAdbCommand(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.ide.eclipse.adt.internal.launch.junit.runtime.
	 * JouleUnitCoordinator#startEnergyProfiling()
	 */
	protected JouleProfiler startEnergyProfiling() throws ProfilingException {
		reportProgress("Initialize profiling ...");

		try {
			/* Use either the configured or a default profiler. */
			JouleProfiler profiler = null;

			if (profilerAdresses.length == 0) {
				profiler = new AndroidProcJouleProfiler(this);
				reportError("WARNING: No WT210 profiler address set. Use a software-based profiler instead.");
			}

			else {
				profiler = new CompositeJouleProfiler();

				int foundProfilers = 0;

				for (int anAddress : profilerAdresses) {
					try {
						WT210Profiler aProfiler = new WT210Profiler(anAddress,
								true);
						aProfiler.setProbeEffectConsiderationEnabled(false);
						aProfiler.setBaseConsumptionConsiderationEnabled(false);
						aProfiler.setProfilingInterval(50);
						foundProfilers++;

						((CompositeJouleProfiler) profiler)
								.addProfiler(aProfiler);
					}

					catch (IllegalArgumentException e) {
						reportError("WARNING: No profiler found at GPIB address "
								+ anAddress);
					}

					catch (UnsatisfiedLinkError e) {
						reportError("WARNING: Unable to load the GPIB driver. Is the ddl file registered?");
					}

					catch (NoClassDefFoundError e) {
						reportError("WARNING: Unable to load the GPIB driver. Is the ddl file registered?");
					}
				}

				if (foundProfilers == 0) {
					/* TODO Integrate proc profiler into run configuration. */
					profiler = new AndroidProcJouleProfiler(this);
					reportError("WARNING: No hardware-based profilers could be found. Try to use a software-based profiler instead.");
				}
			}

			EnergyProfile profile = profiler.startProfiling();
			testSuiteProfile.setEnergyProfile(profile);

			return profiler;
		}

		catch (IllegalArgumentException e) {
			throw new ProfilingException("Error during start of profiling: "
					+ e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.ide.eclipse.adt.internal.launch.junit.runtime.
	 * JouleUnitCoordinator#startHardwareProfiling()
	 */
	protected void startHardwareProfiling() {
		reportProgress("Start Hardware probe service...");

		String command = "am startservice -a 'org.qualitune.jouleunit.android.HWService'";

		try {
			executeAdbCommand(command);
		} catch (ProfilingException e) {
			reportError(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.ide.eclipse.adt.internal.launch.junit.runtime.
	 * JouleUnitCoordinator
	 * #stopEnergyProfiling(org.qualitune.jouleunit.JouleProfiler)
	 */
	@Override
	protected void stopEnergyProfiling(JouleProfiler profiler)
			throws ProfilingException {
		stopLogReading();

		super.stopEnergyProfiling(profiler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#
	 * stopHardwareProfiling()
	 */
	protected void stopHardwareProfiling() throws ProfilingException {
		String command = "am startservice -a 'org.qualitune.jouleunit.android.HWServiceStopService'";

		try {
			executeAdbCommand(command);
		} catch (ProfilingException e) {
			reportError(e);
		}

		reportProgress("Hardware probe service stopped.");

		uninstallApk("org.qualitune.jouleunit.android.hwservice");
	}

	/**
	 * Helper method to install an APK on the device under test.
	 * 
	 * @param packageID
	 *            The package ID of the APK to be uninstalled.
	 * @throws ProfilingException
	 *             Thrown, if installation fails.
	 */
	protected void uninstallApk(String packageID) throws ProfilingException {
		String cmd = "uninstall  \"" + packageID + "\"";
		reportProgress("Uninstall APK " + packageID + " ...");
		executeAdbCommand(cmd);
	}

	/**
	 * Helper method to clear the log of the current device under test.
	 */
	private void clearLog() {
		reportProgress("Clear log ...");

		try {
			executeAdbCommand("logcat -c");
		}

		catch (ProfilingException e) {
			/* Not important. Can be ignored. */
		}
	}

	/**
	 * Starts reading from the log due to a manually implemented receiver, as
	 * the official solution always breaks.
	 */
	protected void startLogReading() {

		/*
		 * Check if log cat reading has been started yet (possible called
		 * multiple times).
		 */
		if (null == logOutputReceiver) {
			logOutputReceiver = createLogOutputReceiver();

			/* Start the logcat in a different thread. */
			logCatThread = new Thread("Logcat") { //$NON-NLS-1$

				/*
				 * (non-Javadoc)
				 * 
				 * @see java.lang.Thread#run()
				 */
				@Override
				public void run() {
					readLog();
				}

				/**
				 * Helper method to read from the log.
				 * 
				 * @param device
				 *            The IDevice whose log shall be read.
				 */
				private void readLog() {

					boolean deviceMaybeOffline = false;

					while (null != logOutputReceiver
							&& !logOutputReceiver.isCancelled()) {

						/* If the device is offline, wait 2 seconds. */
						while (deviceMaybeOffline == true
								&& logOutputReceiver != null
								&& logOutputReceiver.isCancelled() == false) {
							try {
								sleep(2000);
								/*
								 * TODO replace with check whether device is
								 * offline.
								 */
								deviceMaybeOffline = false;
							} catch (InterruptedException e) {
								return;
							}
						}

						if (logOutputReceiver == null
								|| logOutputReceiver.isCancelled()) {
							/*
							 * Logcat was stopped/cancelled before the device
							 * became ready.
							 */
							return;
						}

						try {
							executeAdbCommand("logcat -v time",
									logOutputReceiver);
						} catch (Exception e) {
							reportError("Exception during receiving of log messages: "
									+ e.getMessage());
							deviceMaybeOffline = true;
						}

						finally {
							/* At this point the command is terminated. */
							if (null != logOutputReceiver
									&& logOutputReceiver.isCancelled())
								logOutputReceiver = null;
							// no else.
						}
					}
				}
				// end while.
			};
			logCatThread.start();
		}
		// no else.

		ILogLineProcessor testProcessor = new TestCaseLogLineProcessor(
				logOutputReceiver);
		logOutputReceiver.addLogLineProcessor(testProcessor);
	}

	/** Stops reading logged events after the test run terminated. */
	private void stopLogReading() {
		if (logOutputReceiver != null) {
			logOutputReceiver.cancel();

			/*
			 * When the thread finishes, no one will reference that object and
			 * it'll be destroyed.
			 */
			logOutputReceiver = null;

			/*
			 * TODO Necessary to kill the log cat receiver. How to do this?
			 */
			logCatThread.stop();
		}
	}
}