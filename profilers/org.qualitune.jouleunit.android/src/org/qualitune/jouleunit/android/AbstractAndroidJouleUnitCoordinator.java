package org.qualitune.jouleunit.android;

import org.qualitune.jouleunit.CompositeJouleProfiler;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.ProfilingException;
import org.qualitune.jouleunit.android.logcat.LogOutputReceiver;
import org.qualitune.jouleunit.coordinator.JouleUnitCoordinator;
import org.qualitune.jouleunit.wt210.WT210Profiler;

/**
 * Android-specific abstract implementation of the {@link JouleUnitCoordinator}.
 * 
 * @author Claas Wilke
 */
public abstract class AbstractAndroidJouleUnitCoordinator extends
		JouleUnitCoordinator {

	/** Responsible to receive logged events from the device under test. */
	private LogOutputReceiver logOutputReceiver;

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
	protected void computeDutTimestampOffset() {
		/* Clear the log and start listening to logged events. */
		clearLog();
		startLogReading();

		reportProgress("Compute time synchronization offset ...");

		/* If necessary, the HW Service should be shipped to the device. */
		/* TODO This is still eclipse-specific code. */
		// Bundle fragmentBundle = Platform
		// .getBundle("org.qualitune.jouleunit.adtfragment");
		// if (fragmentBundle != null) {
		// reportProgress("Install hardware profiling service ...");
		//
		// try {
		// URL apkURL = fragmentBundle
		// .getEntry("/resources/org.qualitune.jouleunit.android.hwservice.apk");
		// if (apkURL != null) {
		// File apkFile = new File(FileLocator.toFileURL(apkURL)
		// .getFile());
		//
		// /* Copy APK file to temporary directory. */
		// String tempdir = System.getProperty("java.io.tmpdir")
		// + "JouleUnit";
		// File tempDir = new File(tempdir);
		// boolean success = true;
		//
		// if (!tempDir.isDirectory())
		// success = tempDir.mkdir();
		// // no else.
		//
		// if (success) {
		// File tmpFile = File.createTempFile(
		// "org.qualitune.jouleunit.android.hwservice",
		// ".apk", tempDir);
		//
		// /* Copy file. */
		// FileChannel source = null;
		// FileChannel destination = null;
		//
		// try {
		// source = new FileInputStream(apkFile).getChannel();
		// destination = new FileOutputStream(tmpFile)
		// .getChannel();
		// destination.transferFrom(source, 0, source.size());
		// } finally {
		// if (source != null)
		// source.close();
		// // no else.
		// if (destination != null)
		// destination.close();
		// // no else.
		// }
		//
		// /* Install apk on device under test. */
		// if (tmpFile.exists()) {
		// String command = "adb install '"
		// + tmpFile.getAbsolutePath() + "'";
		// executeAdbCommand(command);
		// }
		//
		// else
		// reportError("The Hardware Service application could not be found. Thus, it cannot be installed.");
		//
		// tmpFile.deleteOnExit();
		// } else
		// reportError("The Hardware Service application could not be found. Thus, it cannot be installed.");
		// }
		//
		// else
		// reportError("Could not copy Hardware profiling service to tmp directory.");
		// }
		//
		// catch (Exception e) {
		// reportError("Error during installation of HW service: "
		// + e.getMessage());
		// }
		// }
		//
		// else
		// reportError("The Hardware Service application could not be found. Thus, it cannot be installed.");

		String command = "am startservice -a 'org.qualitune.jouleunit.android.TimeSyncService'";

		try {
			if (null != logOutputReceiver)
				logOutputReceiver.setLastRequestForTimeStamp(System
						.currentTimeMillis());
			// no else.

			executeAdbCommand(command);
		}

		catch (ProfilingException e) {
			reportError("Error during start of time sync service: "
					+ e.getMessage());
		}
	}

	/**
	 * Helper method to create a {@link LogOutputReceiver}. Can be overriden to
	 * solve type inheritance problem with Eclipse implementation.
	 */
	protected LogOutputReceiver createLogOutputReceiver() {
		return new LogOutputReceiver(testSuiteProfile, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.ide.eclipse.adt.internal.launch.junit.runtime.
	 * JouleUnitCoordinator#deployTestCases()
	 */
	protected void deployTestCases() throws ProfilingException {

		/* TODO APK deployment. */
		// AdtPlugin.printToConsole(mLaunchInfo.getProject(),
		// "Try to resign APK under test...");
		//
		// File apkFile = new File(apkLocation);
		// File resignedApkFile = null;
		//
		// /* Resign. */
		// boolean success = false;
		// if (apkFile.exists()) {
		//
		// resignedApkFile = new File(apkLocation.substring(0,
		// apkLocation.length() - 4)
		// + "_resigned.apk");
		// ResignerLogic.checkEnvironment();
		//
		// try {
		// ResignerLogic.resign(apkFile.getAbsolutePath(),
		// resignedApkFile.getAbsolutePath());
		// success = true;
		// }
		//
		// catch (Exception e) {
		// AdtPlugin.log(e, "Exception during resign of APK '"
		// + apkLocation + "' : " + e.getMessage());
		// reportErrorMessage("Error during resign of APK: "
		// + e.getMessage());
		// }
		// }
		//
		// else
		// reportErrorMessage("The APK file '" + apkLocation
		// + "' cannot be found.");
		//
		// /* Install apk on device under test. */
		// if (success && null != resignedApkFile) {
		// AdtPlugin.printToConsole(mLaunchInfo.getProject(),
		// "Install APK under test...");
		//
		// try {
		// IDevice device = mLaunchInfo.getDevice();
		// String command = "adb install \""
		// + resignedApkFile.getAbsolutePath() + "\"";
		//
		// device.executeShellCommand(command,
		// new DefaultShellOutputReceiver());
		// }
		//
		// catch (TimeoutException e) {
		// reportErrorMessage("Error during installation of APK under test: "
		// + e.getMessage());
		// AdtPlugin.log(
		// e,
		// "Error during installation of APK under test: "
		// + e.getMessage());
		// } catch (AdbCommandRejectedException e) {
		// reportErrorMessage("Error during installation of APK under test: "
		// + e.getMessage());
		// AdtPlugin.log(
		// e,
		// "Error during installation of APK under test: "
		// + e.getMessage());
		// } catch (ShellCommandUnresponsiveException e) {
		// reportErrorMessage("Error during installation of APK under test: "
		// + e.getMessage());
		// AdtPlugin.log(
		// e,
		// "Error during installation of APK under test: "
		// + e.getMessage());
		// } catch (IOException e) {
		// reportErrorMessage("Error during installation of APK under test: "
		// + e.getMessage());
		// AdtPlugin.log(
		// e,
		// "Error during installation of APK under test: "
		// + e.getMessage());
		// } catch (Exception e) {
		// reportErrorMessage("Error during installation of APK under test: "
		// + e.getMessage());
		// AdtPlugin.log(
		// e,
		// "Error during installation of APK under test: "
		// + e.getMessage());
		// }
		// }
		// // no else.
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
				profiler = new ProfilerDummy();
				reportError("WARNING: No profiler address set. Use a dummy profiler instead.");
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
					profiler = new ProfilerDummy();
					reportError("WARNING: No profilers could be found. Use a dummy profiler instead.");
				}
			}

			profiler.startProfiling();

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
	protected void stopEnergyProfiling(JouleProfiler profiler) {
		stopLogReading();

		super.stopEnergyProfiling(profiler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.ide.eclipse.adt.internal.launch.junit.runtime.
	 * JouleUnitCoordinator#stopHardwareProfiling()
	 */
	protected void stopHardwareProfiling() {
		String command = "am startservice -a 'org.qualitune.jouleunit.android.HWServiceStopService'";

		try {
			executeAdbCommand(command);
		} catch (ProfilingException e) {
			reportError(e);
		}

		reportProgress("Hardware probe service stopped.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.ide.eclipse.adt.internal.launch.junit.runtime.
	 * JouleUnitCoordinator#undeployTestCases()
	 */
	protected void undeployTestCases() throws ProfilingException {
		// /* Try to uninstall apks from the device under test. */
		// List<String> packageNames = new ArrayList<String>();
		// packageNames.add(mLaunchInfo.getAppPackage());

		/* TODO Uninstall APKs on device under test. */
		// for (String packageName : packageNames) {
		// AdtPlugin.printToConsole(mLaunchInfo.getProject(),
		// "Uninstall APK "
		// + packageName + " ...");
		//
		// try {
		// mLaunchInfo.getDevice().executeShellCommand(
		// "adb uninstall " + packageName,
		// new AbstractShellOutputReceiver() {
		//
		// @Override
		// public void addOutput(byte[] arg0, int arg1,
		// int arg2) {
		// System.out.println(new String(arg0));
		// }
		// });
		// AdtPlugin.printToConsole(mLaunchInfo.getProject(),
		// "... success");
		// }
		//
		// catch (TimeoutException e) {
		// reportError("Error during deinstallation of APK: "
		// + e.getMessage());
		// AdtPlugin
		// .log(e,
		// "Error during deinstallation of APK: "
		// + e.getMessage());
		// } catch (AdbCommandRejectedException e) {
		// reportError("Error during deinstallation of APK: "
		// + e.getMessage());
		// AdtPlugin
		// .log(e,
		// "Error during deinstallation of APK: "
		// + e.getMessage());
		// } catch (ShellCommandUnresponsiveException e) {
		// reportError("Error during deinstallation of APK: "
		// + e.getMessage());
		// AdtPlugin
		// .log(e,
		// "Error during deinstallation of APK: "
		// + e.getMessage());
		// } catch (IOException e) {
		// reportError("Error during deinstallation of APK: "
		// + e.getMessage());
		// AdtPlugin
		// .log(e,
		// "Error during deinstallation of APK: "
		// + e.getMessage());
		// } catch (Exception e) {
		// reportError("Error during deinstallation of APK: "
		// + e.getMessage());
		// AdtPlugin
		// .log(e,
		// "Error during deinstallation of APK: "
		// + e.getMessage());
		// }
		// }
		// // end for.

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
	private void startLogReading() {

		/* Create a new output receiver. */
		logOutputReceiver = createLogOutputReceiver();

		/* Start the logcat in a different thread. */
		new Thread("Logcat") { //$NON-NLS-1$

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

				while (null != logOutputReceiver
						&& !logOutputReceiver.isCancelled()) {

					/* TODO If the device is offline, wait 2 seconds. */
					// while (device.isOnline() == false
					// && logOutputReceiver != null
					// && logOutputReceiver.isCancelled() == false) {
					// try {
					// sleep(2000);
					// } catch (InterruptedException e) {
					// return;
					// }
					// }

					if (logOutputReceiver == null
							|| logOutputReceiver.isCancelled()) {
						/*
						 * Logcat was stopped/cancelled before the device became
						 * ready.
						 */
						return;
					}

					try {
						executeAdbCommand("logcat -v time", logOutputReceiver);
					} catch (Exception e) {
						reportError("Exception during receiving of log messages: "
								+ e.getMessage());
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
		}.start();
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
		}
	}
}