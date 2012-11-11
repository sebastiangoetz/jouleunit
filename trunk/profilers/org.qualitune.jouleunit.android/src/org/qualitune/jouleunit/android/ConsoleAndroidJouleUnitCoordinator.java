package org.qualitune.jouleunit.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;

import org.qualitune.jouleunit.ProfilingException;
import org.qualitune.jouleunit.android.logcat.LogOutputReceiver;
import org.qualitune.jouleunit.coordinator.TestCaseProfile;

/**
 * Console implementation of the {@link AbstractAndroidJouleUnitCoordinator} .
 * Runs JouleUnit tests under android without any dependency to the ADT.
 * 
 * @author Claas Wilke
 */
public class ConsoleAndroidJouleUnitCoordinator extends
		AbstractAndroidJouleUnitCoordinator {

	/** Location of adb.exe in the file system as {@link File} object. */
	private File adbLocation;

	/** The location of the APK file that shall be tested. */
	private String apkUnderTest;

	/** The main package of the APK under test as defined in its manifest. */
	private String autPackage;

	/**
	 * The console used to log the progress of this
	 * {@link AndroidJouleUnitRunner}.
	 */
	private PrintStream console;

	/** The location of the APK file containing the test cases. */
	private String testApk;

	/** The test package used to run the workloads on the DUT. */
	private String testPackage;

	/**
	 * Creates a new {@link ConsoleAndroidJouleUnitCoordinator}.
	 * 
	 * @param adbLocation
	 *            Location of adb.exe in the file system as an absolute path (as
	 *            a {@link String}).
	 * @param console
	 *            The {@link PrintStream} used to log the progress of this
	 *            {@link AndroidJouleUnitRunner}.
	 * @param apkUnderTest
	 *            The location of the APK file that shall be tested.
	 * @param testApk
	 *            The location of the APK file containing the test cases.
	 * @param autPackage
	 *            The main package of the APK under test as defined in its
	 *            manifest.
	 * @param testPackage
	 *            The test package used to run the workloads on the DUT.
	 */
	public ConsoleAndroidJouleUnitCoordinator(String adbLocation,
			PrintStream console, String apkUnderTest, String testApk,
			String autPackage, String testPackage) {
		this.adbLocation = getFile(adbLocation);
		this.apkUnderTest = apkUnderTest;
		this.autPackage = autPackage;
		this.console = console;
		this.testApk = testApk;
		this.testPackage = testPackage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#reportError
	 * (java.lang.String)
	 */
	public void reportError(String msg) {
		console.println(new Date().toString() + " - Error: " + msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#reportProgress
	 * (java.lang.String)
	 */
	public void reportProgress(String msg) {
		console.println(new Date().toString() + " - " + msg);
	}

	/**
	 * Helper method to open create a {@link File} object for a given path and
	 * check whether the {@link File} exists.
	 * 
	 * @param path
	 *            The path of the {@link File}.
	 * @return The {@link File} object.
	 * @throws IllegalStateException
	 *             Thrown, if the {@link File} does not exist.
	 */
	public File getFile(String path) {
		File file = new File(path);

		if (!file.exists())
			throw new IllegalStateException("Error: APK file "
					+ file.getAbsolutePath() + " not found.");
		// no else.

		return file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator#
	 * deployTestCases()
	 */
	protected void deployTestCases() throws ProfilingException {
		installApk(apkUnderTest);
		installApk(testApk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator#
	 * undeployTestCases()
	 */
	protected void undeployTestCases() throws ProfilingException {
		uninstallApk(autPackage);
		uninstallApk(testPackage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator
	 * #executeAdbCommand(java.lang.String)
	 */
	protected void executeAdbCommand(String cmd) throws ProfilingException {
		invokeTerminalCommand(("\"" + adbLocation.getAbsolutePath()
				+ "/adb.exe\" " + cmd).split(" "));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator
	 * #executeAdbCommand(java.lang.String,
	 * org.qualitune.jouleunit.android.logcat.LogOutputReceiver)
	 */
	protected void executeAdbCommand(
			String cmd,
			org.qualitune.jouleunit.android.logcat.LogOutputReceiver logOutputReceiver)
			throws ProfilingException {
		invokeTerminalCommand(logOutputReceiver,
				("\"" + adbLocation.getAbsolutePath() + "/adb.exe\" " + cmd)
						.split(" "));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator#
	 * getHardwareServiceFile()
	 */
	protected File getHardwareServiceFile() {
		return new File("org.qualitune.jouleunit.android.hwservice.apk");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#propagateResults
	 * ()
	 */
	protected void propagateResults() {

		reportProgress("Test execution finished with "
				+ testSuiteProfile.getTestCaseProfiles().size()
				+ " excuted test cases [id;duration;consumption]:");

		for (TestCaseProfile profile : testSuiteProfile.getTestCaseProfiles()) {
			reportProgress(profile.getId() + "; " + profile.getDuration()
					+ "s; " + profile.getConsumedEnergy() + "mJ;");
		}
		// end for.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#runTestCases ()
	 */
	protected void runTestCases() throws ProfilingException {
		executeAdbCommand("shell am instrument -w " + testPackage
				+ "/android.test.InstrumentationTestRunner");
	}

	/**
	 * Helper method to append an array of individual command arguments into a
	 * single {@link String}.
	 * 
	 * @param cmd
	 *            The commands as an array.
	 * @return The concatenated {@link String}.
	 */
	private static String appendCommands(String[] cmd) {
		StringBuilder builder = new StringBuilder();

		for (String part : cmd)
			builder.append(part + " ");
		// end for.

		return builder.toString().trim();
	}

	/**
	 * Helper method to invoke a command on the terminal and print its result to
	 * the console {@link PrintStream}.
	 * 
	 * @param cmd
	 *            The command being executed as an array of {@link String}s.
	 * @throws ProfilingException
	 *             Thrown, if command invocation fails.
	 */
	private void invokeTerminalCommand(String... cmd) throws ProfilingException {
		invokeTerminalCommand(console, cmd);
	}

	/**
	 * Helper method to invoke a command on the terminal and to deliver its
	 * result to the given {@link LogOutputReceiver}.
	 * 
	 * @param receiver
	 *            The {@link LogOutputReceiver} to handle the result from the
	 *            command.
	 * @param cmd
	 *            The command being executed.
	 */
	private void invokeTerminalCommand(
			org.qualitune.jouleunit.android.logcat.LogOutputReceiver receiver,
			String... cmd) {

		Process process = null;

		try {
			process = new ProcessBuilder(cmd).start();

			InputStream inputStream = process.getInputStream();
			InputStreamReader isReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(isReader);

			String line;
			while ((line = bufferedReader.readLine()) != null)
				receiver.processNewLines(new String[] { line });
			// end while.

			int exitVal = process.waitFor();

			if (exitVal < 0)
				throw new IllegalStateException("Command '"
						+ appendCommands(cmd) + "' exited with error: "
						+ exitVal);
			// no else.
		}

		catch (IOException e) {
			throw new IllegalStateException("Error during command '"
					+ appendCommands(cmd) + "'", e);
		}

		catch (InterruptedException e) {
			throw new IllegalStateException("Error during command '"
					+ appendCommands(cmd) + "'", e);
		}

		finally {
			if (null != process)
				process.destroy();
			// no else.
		}
	}

	/**
	 * Helper method to invoke a command on the terminal and print its result to
	 * the given {@link PrintStream}.
	 * 
	 * @param ps
	 *            The {@link PrintStream} to document the commands output.
	 * @param cmd
	 *            The command being executed as an array of {@link String}s.
	 * @throws ProfilingException
	 *             Thrown, if command invocation fails.
	 */
	private void invokeTerminalCommand(PrintStream ps, String... cmd)
			throws ProfilingException {

		Process process = null;

		try {
			process = new ProcessBuilder(cmd).start();

			InputStream inputStream = process.getInputStream();
			InputStreamReader isReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(isReader);

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (null != ps)
					ps.println(line);
				// no else.
			}
			// end while.

			int exitVal = process.waitFor();

			if (exitVal < 0)
				throw new ProfilingException("Command '" + appendCommands(cmd)
						+ "' exited with error: " + exitVal);
			// no else.
		}

		catch (IOException e) {
			throw new ProfilingException("Error during command '"
					+ appendCommands(cmd) + "'", e);
		}

		catch (InterruptedException e) {
			throw new ProfilingException("Error during command '"
					+ appendCommands(cmd) + "'", e);
		}

		finally {
			if (null != process)
				process.destroy();
			// no else.
		}
	}
}