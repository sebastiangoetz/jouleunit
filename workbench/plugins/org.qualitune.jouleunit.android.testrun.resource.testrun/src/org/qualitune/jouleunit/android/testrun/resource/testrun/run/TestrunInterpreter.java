package org.qualitune.jouleunit.android.testrun.resource.testrun.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.service.prefs.Preferences;
import org.qualitune.jouleunit.ProfilingException;
import org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator;
import org.qualitune.jouleunit.android.logcat.LogOutputReceiver;
import org.qualitune.jouleunit.android.testrun.ApkFile;
import org.qualitune.jouleunit.android.testrun.Block;
import org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement;
import org.qualitune.jouleunit.android.testrun.CursorDirection;
import org.qualitune.jouleunit.android.testrun.CursorStatement;
import org.qualitune.jouleunit.android.testrun.DisplayStatement;
import org.qualitune.jouleunit.android.testrun.EnterStatement;
import org.qualitune.jouleunit.android.testrun.HomeButtonStatement;
import org.qualitune.jouleunit.android.testrun.JunitTestCase;
import org.qualitune.jouleunit.android.testrun.OpenSettingsStatement;
import org.qualitune.jouleunit.android.testrun.StartActivityStatement;
import org.qualitune.jouleunit.android.testrun.Statement;
import org.qualitune.jouleunit.android.testrun.TestBehavior;
import org.qualitune.jouleunit.android.testrun.TestCase;
import org.qualitune.jouleunit.android.testrun.TestExecutable;
import org.qualitune.jouleunit.android.testrun.TestRun;
import org.qualitune.jouleunit.android.testrun.TestStatement;
import org.qualitune.jouleunit.android.testrun.TestSuite;
import org.qualitune.jouleunit.android.testrun.UnlockStatement;
import org.qualitune.jouleunit.android.testrun.WaitStatement;
import org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource;
import org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunPrinter2;
import org.qualitune.jouleunit.android.testrun.resource.testrun.util.AbstractTestrunInterpreter;
import org.qualitune.jouleunit.coordinator.JouleUnitCoordinator;
import org.qualitune.jouleunit.coordinator.TestCaseProfile;
import org.qualitune.jouleunit.coordinator.TestSuiteProfile;
import org.qualitune.jouleunit.core.JouleUnitPlugin;
import org.qualitune.jouleunit.core.prefs.JouleUnitPreferences;
import org.qualitune.jouleunit.data.DbManager;
import org.qualitune.jouleunit.data.ResultPropagator;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.InstallException;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

public class TestrunInterpreter extends
		AbstractTestrunInterpreter<Boolean, Boolean> {

	/** {@link AndroidDebugBridge} used to communicate with device under test. */
	protected AndroidDebugBridge debugBridge;

	/** The {@link IDevice} used for testing. */
	protected IDevice deviceUnderTest;

	/**
	 * The {@link PrintStream} used to print the progress during interpretation.
	 */
	protected PrintStream console;

	/** String to inline the console during interpretation. */
	protected String consoleInline = "";

	/** The {@link Coordinator} used for profiling. */
	protected Coordinator coordinator = null;

	/**
	 * Creates a new {@link TestrunInterpreter}.
	 * 
	 * @param console
	 *            The {@link PrintStream} used to propagate interpretation
	 *            progress and errors.
	 */
	public TestrunInterpreter(PrintStream console) {

		this.console = console;

		/* Get the debug bridge. */
		if (Platform.isRunning()) {
			debugBridge = AndroidDebugBridge.getBridge();
		}

		else {
			String androidHome = System.getenv("ANDROID_HOME");

			if (null == androidHome || androidHome.length() == 0)
				printError("Android Home is not set. Please update system properties.");

			else {
				if (null == AndroidDebugBridge.getBridge())
					AndroidDebugBridge.init(false);
				// no else.

				debugBridge = AndroidDebugBridge.createBridge(androidHome
						+ "/platform-tools/adb", true);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_Block
	 * (org.qualitune.jouleunit.android.testrun.Block, java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_Block(
			Block block, Boolean context) {

		return interpretStatements(block.getStatements(), context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_ClickOnScreenStatement
	 * (org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement,
	 * java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_ClickOnScreenStatement(
			ClickOnScreenStatement clickOnScreenStatement, Boolean context) {

		boolean result = executeShellCommand("input tap "
				+ clickOnScreenStatement.getX() + " "
				+ clickOnScreenStatement.getY());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* Not important. */
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_CursorStatement
	 * (org.qualitune.jouleunit.android.testrun.CursorStatement,
	 * java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_CursorStatement(
			CursorStatement cursorStatement, Boolean context) {

		boolean result;

		switch (cursorStatement.getDirection().getValue()) {

		case CursorDirection.UP_VALUE:
			result = executeShellCommand("input keyevent 19");
			break;

		case CursorDirection.RIGHT_VALUE:
			result = executeShellCommand("input keyevent 22");
			break;

		case CursorDirection.LEFT_VALUE:
			result = executeShellCommand("input keyevent 21");
			break;

		case CursorDirection.DOWN_VALUE:
			result = executeShellCommand("input keyevent 20");
			break;

		default:
			printError("Unknown type of CursorDirection: "
					+ cursorStatement.getDirection() + ". Cancelled TestRun.");
			return false;
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* Not important. */
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_DisplayStatement
	 * (org.qualitune.jouleunit.android.testrun.DisplayStatement,
	 * java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_DisplayStatement(
			DisplayStatement displayStatement, Boolean context) {

		return triggerDisplayOnOffEvent();
	}

	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_EnterStatement(
			EnterStatement enterStatement, Boolean context) {

		boolean result = executeShellCommand("input keyevent 66");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* Not important. */
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_HomeButtonStatement
	 * (org.qualitune.jouleunit.android.testrun.HomeButtonStatement,
	 * java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_HomeButtonStatement(
			HomeButtonStatement homeButtonStatement, Boolean context) {

		return triggerHomeButtonEvent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_JunitTestCase
	 * (org.qualitune.jouleunit.android.testrun.JunitTestCase, java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_JunitTestCase(
			JunitTestCase junitTestCase, Boolean context) {

		boolean result = triggerDisplayOnOffEvent();
		result &= triggerUnlockEvent();
		result &= triggerHomeButtonEvent();

		String cmd = "am instrument -w -e class " + junitTestCase.getName()
				+ " " + coordinator.testRun.getJunitPackage()
				+ "/android.test.InstrumentationTestRunner";
		result &= executeShellCommand(cmd);

		result &= triggerDisplayOnOffEvent();

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_OpenSettingsStatement
	 * (org.qualitune.jouleunit.android.testrun.OpenSettingsStatement,
	 * java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_OpenSettingsStatement(
			OpenSettingsStatement openSettingsStatement, Boolean context) {

		return executeShellCommand("am start -n com.android.settings/.Settings");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_StartActivityStatement
	 * (org.qualitune.jouleunit.android.testrun.StartActivityStatement,
	 * java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_StartActivityStatement(
			StartActivityStatement startActivityStatement, Boolean context) {

		boolean result = executeShellCommand("am start -n "
				+ startActivityStatement.getPackage() + "/."
				+ startActivityStatement.getClass_());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* Not important. */
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_Statement
	 * (org.qualitune.jouleunit.android.testrun.Statement, java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_Statement(
			Statement statement, Boolean context) {

		if (statement instanceof ClickOnScreenStatement)
			return interprete_org_qualitune_jouleunit_android_testrun_ClickOnScreenStatement(
					(ClickOnScreenStatement) statement, context);

		else if (statement instanceof CursorStatement)
			return interprete_org_qualitune_jouleunit_android_testrun_CursorStatement(
					(CursorStatement) statement, context);

		else if (statement instanceof DisplayStatement)
			return interprete_org_qualitune_jouleunit_android_testrun_DisplayStatement(
					(DisplayStatement) statement, context);

		else if (statement instanceof EnterStatement)
			return interprete_org_qualitune_jouleunit_android_testrun_EnterStatement(
					(EnterStatement) statement, context);

		else if (statement instanceof HomeButtonStatement)
			return interprete_org_qualitune_jouleunit_android_testrun_HomeButtonStatement(
					(HomeButtonStatement) statement, context);

		else if (statement instanceof OpenSettingsStatement)
			return interprete_org_qualitune_jouleunit_android_testrun_OpenSettingsStatement(
					(OpenSettingsStatement) statement, context);

		else if (statement instanceof StartActivityStatement)
			return interprete_org_qualitune_jouleunit_android_testrun_StartActivityStatement(
					(StartActivityStatement) statement, context);

		else if (statement instanceof TestStatement)
			return interprete_org_qualitune_jouleunit_android_testrun_TestStatement(
					(TestStatement) statement, context);

		else if (statement instanceof UnlockStatement)
			return interprete_org_qualitune_jouleunit_android_testrun_UnlockStatement(
					(UnlockStatement) statement, context);

		else if (statement instanceof WaitStatement)
			return interprete_org_qualitune_jouleunit_android_testrun_WaitStatement(
					(WaitStatement) statement, context);

		else {
			printError("Unknown type of Statement: "
					+ statement.getClass().getName() + ". TestRun cancelled.");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_TestBehavior
	 * (org.qualitune.jouleunit.android.testrun.TestBehavior, java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_TestBehavior(
			TestBehavior testBehavior, Boolean context) {

		if (testBehavior instanceof Block)
			return interprete_org_qualitune_jouleunit_android_testrun_Block(
					(Block) testBehavior, context);
		else {
			printError("Unknown type of TestBehavior: "
					+ testBehavior.getClass().getName()
					+ ". TestRun cancelled.");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_TestCase
	 * (org.qualitune.jouleunit.android.testrun.TestCase, java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_TestCase(
			TestCase testCase, Boolean context) {

		printProgress("Execute TestCase " + testCase.getName() + " ...");
		pushConsoleInline();

		boolean result = waitForFullBattery();

		if (result) {
			getInterpretationStack().add(testCase.getBehavior());

			long startNano = System.nanoTime();
			result = interprete_org_qualitune_jouleunit_android_testrun_TestBehavior(
					testCase.getBehavior(), context);
			getInterpretationStack().remove(testCase.getBehavior());
			long stoppedNano = System.nanoTime();

			logTestCase(testCase.getName(), startNano, stoppedNano);

			printProgress("... done");
		}
		// no else.

		popConsoleInline();

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_TestExecutable
	 * (org.qualitune.jouleunit.android.testrun.TestExecutable,
	 * java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_TestExecutable(
			TestExecutable testExecutable, Boolean context) {

		if (testExecutable instanceof TestCase)
			return interprete_org_qualitune_jouleunit_android_testrun_TestCase(
					(TestCase) testExecutable, context);

		else if (testExecutable instanceof TestSuite)
			return interprete_org_qualitune_jouleunit_android_testrun_TestSuite(
					(TestSuite) testExecutable, context);

		else if (testExecutable instanceof JunitTestCase)
			return interprete_org_qualitune_jouleunit_android_testrun_JunitTestCase(
					(JunitTestCase) testExecutable, context);

		else {
			printError("Unknown type of TestExecutable: "
					+ testExecutable.getClass().getName()
					+ ". Test run cancelled.");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_TestRun
	 * (org.qualitune.jouleunit.android.testrun.TestRun, java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_TestRun(
			final TestRun testRun, Boolean context) {

		if (testRun.isRemoteRun())
			return interpretTestRunRemote(testRun, context);
		else
			return interpretTestRunLocal(testRun, context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_TestStatement
	 * (org.qualitune.jouleunit.android.testrun.TestStatement, java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_TestStatement(
			TestStatement testStatement, Boolean context) {

		printProgress("Interpret subTest " + testStatement.getName() + " ...");
		pushConsoleInline();

		long startNano = System.nanoTime();
		boolean result = interpretStatements(testStatement.getStatements(),
				context);
		long stoppedNano = System.nanoTime();

		logTestCase(testStatement.getName(), startNano, stoppedNano);

		printProgress("... done");
		popConsoleInline();

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_TestSuite
	 * (org.qualitune.jouleunit.android.testrun.TestSuite, java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_TestSuite(
			TestSuite testSuite, Boolean context) {

		printProgress("Interpret test suite " + testSuite.getName() + " ...");
		pushConsoleInline();

		boolean goOn = true;

		if (null != testSuite.getSetUp()) {
			printProgress("Execute setUp method ...");
			pushConsoleInline();
			getInterpretationStack().add(testSuite.getSetUp());
			goOn = interprete_org_qualitune_jouleunit_android_testrun_TestBehavior(
					testSuite.getSetUp(), context);
			getInterpretationStack().remove(testSuite.getSetUp());
			printProgress("... done.");
			popConsoleInline();
		}
		// false;

		if (goOn)
			goOn = interpretTestExecutables(testSuite.getExecutables(), context);
		// no else.

		if (null != testSuite.getTearDown()) {
			printProgress("Execute tearDown method ...");
			pushConsoleInline();
			getInterpretationStack().add(testSuite.getTearDown());
			goOn &= interprete_org_qualitune_jouleunit_android_testrun_TestBehavior(
					testSuite.getTearDown(), context);
			getInterpretationStack().remove(testSuite.getTearDown());
			printProgress("... done");
			popConsoleInline();
		}
		// no else.

		printProgress("... done");
		popConsoleInline();
		return goOn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_UnlockStatement
	 * (org.qualitune.jouleunit.android.testrun.UnlockStatement,
	 * java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_UnlockStatement(
			UnlockStatement unlockStatement, Boolean context) {

		return triggerUnlockEvent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.util.
	 * AbstractTestrunInterpreter
	 * #interprete_org_qualitune_jouleunit_android_testrun_WaitStatement
	 * (org.qualitune.jouleunit.android.testrun.WaitStatement, java.lang.Object)
	 */
	@Override
	public Boolean interprete_org_qualitune_jouleunit_android_testrun_WaitStatement(
			WaitStatement waitStatement, Boolean context) {

		try {
			Thread.sleep(waitStatement.getSeconds() * 1000);
			return true;
		}

		catch (InterruptedException e) {
			printError("Wait was interrupted. TestRun cancelled.");
			return false;
		}
	}

	/**
	 * Factory method to create the {@link Coordinator} of this
	 * {@link TestrunInterpreter}.
	 * 
	 * @param testRun
	 *            The {@link TestRun} to be coordinated.
	 * @return The created {@link Coordinator}.
	 */
	protected Coordinator createCoordinator(final TestRun testRun) {
		return new Coordinator(testRun);
	}

	/**
	 * Helper method to executed and ADB shell command on the device under test.
	 * 
	 * @param command
	 *            The command to be executed.
	 * @return <code>true</code> if executed successfully.
	 */
	protected Boolean executeShellCommand(String command) {

		ShellReceiver receiver = null;

		try {
			receiver = new ShellReceiver();
			deviceUnderTest.executeShellCommand(command, receiver,
			/* Timeout set to 4 hours. */
			4 * 60 * 60 * 1000);
		}

		catch (TimeoutException e) {
			printError("Error during execution of shell command: "
					+ e.getMessage() + ". TestRun cancelled.");
			return false;
		}

		catch (AdbCommandRejectedException e) {
			printError("Error during execution of shell command: "
					+ e.getMessage() + ". TestRun cancelled.");
			return false;
		}

		catch (ShellCommandUnresponsiveException e) {
			printError("Error during execution of shell command: "
					+ e.getMessage() + ". TestRun cancelled.");
			return false;
		}

		catch (IOException e) {
			printError("Error during execution of shell command: "
					+ e.getMessage() + ". TestRun cancelled.");
			return false;
		}

		/* Wait for the command being executed */
		while (!receiver.done) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				/* not important. */
			}
		}
		// end while.

		return !receiver.gotError;
	}

	/**
	 * Helper method installing the application under test.
	 * 
	 * @param apkFile
	 *            The {@link ApkFile} representing the application under test.
	 * @return If <code>true</code>, install was successfull.
	 */
	protected boolean installAut(ApkFile apkFile) {

		/* Try to deploy APK. */
		if (null != apkFile) {
			try {
				File autFile = new File(apkFile.getPath());

				if (autFile.exists()) {
					printProgress("Install Application " + autFile.getName()
							+ " ...");
					String report = deviceUnderTest.installPackage(
							autFile.getAbsolutePath(), true);

					if (null != report && report.length() > 0) {
						printError("Install failed: " + report
								+ ". TestRun cancelled.");
						return false;
					}

					else {
						printProgress("... done.");
						return true;
					}
				}

				else {
					printError("Application under test ("
							+ autFile.getAbsolutePath()
							+ ") not found. TestRun cancelled.");
					return false;
				}
			}

			catch (InstallException e) {
				printError("Installation of application under test failed.");
				printProgress(e.getMessage());
				printError("TestRun cancelled.");
				return false;
			}
		}
		// no else.

		return true;
	}

	/**
	 * Helper method interpreting a given {@link List} of {@link Statement}s.
	 * 
	 * @param statements
	 *            The {@link List} of {@link Statement}s to be interpreted.
	 * @param context
	 *            The context.
	 * @return The result.
	 */
	protected boolean interpretStatements(List<Statement> statements,
			Boolean context) {

		boolean goOn = true;

		for (Statement statement : statements) {
			getInterpretationStack().add(statement);
			goOn = interprete_org_qualitune_jouleunit_android_testrun_Statement(
					statement, context);
			getInterpretationStack().remove(statement);

			if (!goOn)
				break;
			// false.
		}
		// end for.
		return goOn;
	}

	/**
	 * Helper method traversing a {@link List} of {@link TestExecutable}s and
	 * interpreting them.
	 * 
	 * @param executables
	 *            The {@link TestExecutable}s to be interpreted.
	 * @param context
	 *            The context.
	 * @return The result.
	 */
	protected boolean interpretTestExecutables(
			List<TestExecutable> executables, Boolean context) {

		for (TestExecutable executable : executables) {

			this.addObjectToInterprete(executable);
			boolean result = interprete_org_qualitune_jouleunit_android_testrun_TestExecutable(
					executable, context);
			this.getInterpretationStack().remove(executable);

			/* Probably cancel interpretation. */
			if (!result)
				return false;
			// no else.
		}
		// end for.

		/* Delete application data for clean reset. */
		if (null != coordinator.testRun.getPackageUnderTest())
			executeShellCommand("pm clear "
					+ coordinator.testRun.getPackageUnderTest());
		// no else.

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* Not important. */
		}

		return true;
	}

	/**
	 * Helper method triggering a local {@link TestRun}.
	 * 
	 * @param testRun
	 *            The {@link TestRun} to be run.
	 * @param context
	 *            The context.
	 * @return <code>true</code> if {@link TestRun} was successful.
	 */
	protected Boolean interpretTestRunLocal(final TestRun testRun,
			Boolean context) {
		if (null == debugBridge) {
			printError("DebugBridge is not initialized. TestRun cancelled.");
			return false;
		}
		// no else.

		IDevice[] devices = null;
		int counter = 0;

		printProgress("Try to find Android device ...");

		while (null == devices || devices.length == 0 && counter < 100) {

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				/* not important. */
			}

			devices = debugBridge.getDevices();
			counter++;
		}

		if (devices.length == 0) {
			printError("No Android device found. TestRun cancelled.");
			return false;
		}

		else {
			/** TODO dialog to select device if more than one device found. */
			deviceUnderTest = devices[0];
			printProgress("... done.\n");
		}

		printProgress("Start test run ...");

		/* Start the profiling. */
		coordinator = createCoordinator(testRun);
		/* TODO Extract addresses into settings/properties file. */
		coordinator.setProfilerAddresses(new int[] { 1, 10 });

		new Thread() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				/* Extract settings from test run. */
				int noOfRuns = testRun.getNoOfRuns();
				if (noOfRuns <= 0)
					noOfRuns = 1;
				// no else.

				int idleTime = testRun.getIdleTime();
				if (idleTime < 0)
					idleTime = 0;
				// no else.

				coordinator.runTestRun(noOfRuns, idleTime,
						testRun.isHardwareProfilingOn());
			}
		}.start();

		/* Wait till the profiler is ready. */
		while (!coordinator.isInitialized) {
			try {
				Thread.sleep(100);
			}

			catch (InterruptedException e) {
				/* Not that important. */
			}
		}
		// end while.

		boolean result = true;

		/* Wait till the profiler is terminated. */
		while (!coordinator.isTerminated) {

			/* Continue interpretation. */
			if (coordinator.isProfiling) {
				result &= interpretTestExecutables(testRun.getExecutables(),
						context);
				coordinator.isProfiling = false;
			}
			// no else.

			try {
				Thread.sleep(100);
			}

			catch (InterruptedException e) {
				/* Not that important. */
			}
		}
		// end while.

		return result;
	}

	/**
	 * Helper method to run a {@link TestRun} remotely.
	 * 
	 * @param testRun
	 *            The {@link TestRun} to be run.
	 * @param context
	 *            The context.
	 * @return If <code>true</code> {@link TestRun} was successful.
	 */
	protected Boolean interpretTestRunRemote(TestRun testRun, Boolean context) {

		/* TODO Adapt DBManager to real qMark Service. */
		DbManager dbMgr = new DbManager(console);

		/* Insert necessary testData into DB. */
		printProgress("Publish test run data to profiling server...");

		/* Get test script from test run. */
		OutputStreamBuffer buffer = new OutputStreamBuffer();
		TestrunPrinter2 printer = new TestrunPrinter2(buffer,
				(ITestrunTextResource) testRun.eResource());
		try {
			printer.print(testRun);
		} catch (IOException e1) {
			printError("Error during conversion of TestScript: "
					+ e1.getMessage() + ". TestRun cancelled.");
			return false;
		}

		int testRunID = dbMgr
				.saveTestRun((null == testRun.getAut() || null == testRun
						.getAut().getPath()) ? null : new File(testRun.getAut()
						.getPath()), testRun.getPackageUnderTest(),
						(null == testRun.getJunitApk() || null == testRun
								.getJunitApk().getPath()) ? null : new File(
								testRun.getJunitApk().getPath()), testRun
								.getJunitPackage(), testRun
								.isHardwareProfilingOn(),
						testRun.getIdleTime(), testRun.getNoOfRuns(),
						buffer.buffer.toString());

		if (testRunID <= 0) {
			printError("Illegal Test Run ID. Test Run Export to DataBase failed. TestRun cancelled.");
			return false;
		}
		// no else.

		/* Trigger test case execution. */
		String serverIP = Platform.getPreferencesService().getString(
				JouleUnitPreferences.PREFERENCE_IDENTIFIER,
				JouleUnitPreferences.P_STRING_SERVER_IP,
				JouleUnitPreferences.P_STRING_SERVER_IP_DEFAULT, null);
		int serverPort = Platform.getPreferencesService().getInt(
				JouleUnitPreferences.PREFERENCE_IDENTIFIER,
				JouleUnitPreferences.P_STRING_SERVER_PORT,
				JouleUnitPreferences.P_STRING_SERVER_PORT_DEFAULT, null);

		Socket kkSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		printProgress("Try to connect to profiling server...");

		try {
			kkSocket = new Socket(serverIP, serverPort);
			out = new PrintWriter(kkSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					kkSocket.getInputStream()));
		}

		catch (UnknownHostException e) {
			printError("Cannot connect to host "
					+ serverIP
					+ ". Are you sure that your QMark settings are correct? TestRun cancelled.");
			return false;
		}

		catch (IOException e) {
			printError("Couldn't get I/O for the connection to "
					+ serverIP
					+ ". Are you sure that your QMark settings are correct? TestRun cancelled.");
			return false;
		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));
		String fromServer;

		try {
			while ((fromServer = in.readLine()) != null) {
				printProgress(fromServer);

				if (fromServer.equals("Ready")) {
					/* Start test run. */
					out.println(testRunID);
				}

				else if (fromServer.equals("Done")) {
					break;
				}
			}
			in.close();
			out.close();
			stdIn.close();
			kkSocket.close();
		}

		catch (IOException e) {
			printError("Connection to remote sever failed. Are you sure that the QMark settings are correct?\nCaused Exception: "
					+ e.getMessage() + ". TestRun cancelled.");
			return false;
		}

		/* Read an present the profiling results. */
		TestSuiteProfile testSuiteProfile = dbMgr
				.readTestSuiteProfile(testRunID);
		propagateProfilingResults(testSuiteProfile);

		return true;
	}

	/**
	 * Helper method checking whether the current test {@link IDevice} is
	 * currently charging its battery.
	 * 
	 * @return If <code>true</code>, the DUT battery is currently charged.
	 */
	protected boolean isDutCharging() throws ProfilingException {

		Boolean result = null;
		final StringBuffer output = new StringBuffer();

		try {
			deviceUnderTest.executeShellCommand("dumpsys battery",
					new IShellOutputReceiver() {
						/*
						 * (non-Javadoc)
						 * 
						 * @see
						 * com.android.ddmlib.IShellOutputReceiver#isCancelled()
						 */
						public boolean isCancelled() {

							return false;
						}

						/*
						 * (non-Javadoc)
						 * 
						 * @see com.android.ddmlib.IShellOutputReceiver#flush()
						 */
						public void flush() {
							/* Do nothing. */
						}

						/*
						 * (non-Javadoc)
						 * 
						 * @see
						 * com.android.ddmlib.IShellOutputReceiver#addOutput
						 * (byte[], int, int)
						 */
						public void addOutput(byte[] arg0, int arg1, int arg2) {
							output.append(new String(arg0));
						}
					});
		} catch (TimeoutException e) {
			throw new ProfilingException(
					"Error during check of DUT battery state: "
							+ e.getMessage(), e);
		} catch (AdbCommandRejectedException e) {
			throw new ProfilingException(
					"Error during check of DUT battery state: "
							+ e.getMessage(), e);
		} catch (ShellCommandUnresponsiveException e) {
			throw new ProfilingException(
					"Error during check of DUT battery state: "
							+ e.getMessage(), e);
		} catch (IOException e) {
			throw new ProfilingException(
					"Error during check of DUT battery state: "
							+ e.getMessage(), e);
		}

		/* Wait for receival of battery status. */
		while (null == result) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				/* Do nothing. */
			}

			/* Check if the battery status has been received yet. */
			String out = output.toString();

			if (out.contains("status: ")) {
				int index = out.indexOf("status: ") + 8;
				String batteryState = out.substring(index, index + 1);
				result = batteryState.equals("2");
			}
			// no else.
		}
		// end while.

		return result;
	}

	/**
	 * Helper method propagating the profiling results.
	 * 
	 * @param testSuiteProfile
	 *            The {@link TestSuiteProfile} to be propagated.
	 */
	protected void propagateProfilingResults(TestSuiteProfile testSuiteProfile) {
		ResultPropagator.sendNewResult(testSuiteProfile);
	}

	/**
	 * Helper method triggering an event switching the display on/off.
	 * 
	 * @return If <code>true</code>, the event has been triggered successfully.
	 */
	protected Boolean triggerDisplayOnOffEvent() {
		boolean result = executeShellCommand("input keyevent 26");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* Not important. */
		}

		return result;
	}

	/**
	 * Helper method triggering a home button event.
	 * 
	 * @return If <code>true</code>, the event has been triggered successfully.
	 */
	protected Boolean triggerHomeButtonEvent() {
		boolean result = executeShellCommand("input keyevent 3");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* Not important. */
		}

		return result;
	}

	/**
	 * Helper method triggering an unlock event.
	 * 
	 * @return If <code>true</code>, the event has been triggered successfully.
	 */
	protected Boolean triggerUnlockEvent() {
		if (deviceUnderTest.getName().startsWith("asus-nexus_7"))
			return unlockNexus7();

		else if (deviceUnderTest.getName().startsWith("samsung-galaxy_nexus"))
			return executeShellCommand("input swipe 360 960 660 960");

		else {
			printError("Unknown type of device to be unlocked: "
					+ deviceUnderTest.getName() + ". TestRun cancelled.");
			return false;
		}
	}

	/**
	 * Helper method to log a test case during profiling.
	 * 
	 * @param name
	 *            The name of the test case.
	 * @param startNano
	 *            Its start time in nanoseconds.
	 * @param stoppedNano
	 *            Its stop time in nanoseconds.
	 */
	protected void logTestCase(String name, long startNano, long stoppedNano) {
		/* Log the test case represented by this test statement. */
		TestCaseProfile profile = new TestCaseProfile();
		profile.setId(name);

		profile.setStartTime(Math.round(coordinator.getTestSuiteProfile()
				.adaptTrTimeStamp(startNano))
				- coordinator.getTestSuiteProfile().getPutTimeStampOffset());
		profile.setEndTime(Math.round(coordinator.getTestSuiteProfile()
				.adaptTrTimeStamp(stoppedNano))
				- coordinator.getTestSuiteProfile().getPutTimeStampOffset());
		coordinator.getTestSuiteProfile().addTestCase(profile);
	}

	/**
	 * Helper method to print an error message on the console.
	 * 
	 * @param msg
	 *            The error message to be printed.
	 */
	protected void printError(String msg) {
		console.println(consoleInline + "Error: " + msg);
	}

	/**
	 * Helper method to print a progress message on the console.
	 * 
	 * @param msg
	 *            The message to be printed.
	 */
	protected void printProgress(String msg) {
		console.println(consoleInline + msg);
	}

	/**
	 * Helper method to remotely unlock a Nexus 7.
	 * 
	 * @return If <code>true</code> unlocked successfully.
	 * 
	 *         TODO This method should be located elsewhere.
	 */
	protected Boolean unlockNexus7() {
		boolean goOn = true;

		goOn = executeShellCommand("input swipe 40 300 540 300");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* Not important. */
		}

		if (goOn) {
			goOn = executeShellCommand("input tap 400 345");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				/* Not important. */
			}
		}

		else
			return false;

		if (goOn)
			return executeShellCommand("input keyevent 4");
		else
			return false;
	}

	protected void pushConsoleInline() {
		if (null == consoleInline || consoleInline.length() < 2)
			consoleInline += "|-";
		else
			consoleInline = "| " + consoleInline;
	}

	protected void popConsoleInline() {
		if (null != consoleInline && consoleInline.length() > 1)
			consoleInline = consoleInline.substring(2, consoleInline.length());
		// no else.
	}

	/**
	 * Helper method waiting for the test {@link IDevice} to have a fully
	 * charged battery if configured in the {@link TestRun}.
	 * 
	 * @return Whether the wait statement has been performed successfully or
	 *         not.
	 */
	protected boolean waitForFullBattery() {
		if (coordinator.testRun.isWaitForFullBattery()) {
			boolean batteryCharging;
			try {
				batteryCharging = isDutCharging();

				while (batteryCharging) {
					printProgress("Wait for full test device battery ...");
					batteryCharging = isDutCharging();
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						/* Do nothing. */
					}
				}
			} catch (ProfilingException e1) {
				printError("Could not ensure test device battery level. Reason: "
						+ e1.getMessage());
				return false;
			}
		}
		// no else.

		return true;
	}

	/**
	 * {@link JouleUnitCoordinator} to trigger energy profiling during
	 * interpretation.
	 */
	protected class Coordinator extends AbstractAndroidJouleUnitCoordinator {

		/**
		 * Indicates whether or not this
		 * {@link AbstractAndroidJouleUnitCoordinator} is ready for profiling.
		 */
		protected boolean isInitialized = false;

		/**
		 * Indicates whether or not this
		 * {@link AbstractAndroidJouleUnitCoordinator} is profiling.
		 */
		protected boolean isProfiling = false;

		/**
		 * Indicates whether or not this
		 * {@link AbstractAndroidJouleUnitCoordinator} is done with profiling.
		 */
		protected boolean isTerminated = false;

		/** The {@link TestRun} representing the tests to be executed. */
		protected TestRun testRun;

		/**
		 * Creates a new {@link Coordinator}.
		 * 
		 * @param testRun
		 *            The {@link TestRun} representing the tests to be executed.
		 */
		public Coordinator(TestRun testRun) {
			this.testRun = testRun;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#reportError
		 * (java.lang.String)
		 */
		public void reportError(String msg) {
			printError(msg);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#reportProgress
		 * (java.lang.String)
		 */
		public void reportProgress(String msg) {
			printProgress(msg);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#runTestRun
		 * (int, int, boolean)
		 */
		@Override
		public void runTestRun(int runs, int idleTime,
				boolean isHwProfilingEnabled) {
			super.runTestRun(runs, idleTime, isHwProfilingEnabled);
			isTerminated = true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#deployTestCases
		 * ()
		 */
		@Override
		protected void deployTestCases() throws ProfilingException {

			if (null != testRun.getAut() && null != testRun.getAut().getPath()
					&& testRun.getAut().getPath().length() > 0)
				if (!installAut(testRun.getAut()))
					throw new ProfilingException(
							"Failure during installation of Application under Test.");
			// no else.

			if (null != testRun.getJunitApk()
					&& null != testRun.getJunitApk().getPath()
					&& testRun.getJunitApk().getPath().length() > 0)
				if (!installAut(testRun.getJunitApk()))
					throw new ProfilingException(
							"Failure during installation of Unit Tests.");
			// no else.
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator
		 * #executeAdbCommand(java.lang.String)
		 */
		@Override
		protected void executeAdbCommand(String cmd) throws ProfilingException {
			executeShellCommand(cmd);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator
		 * #executeAdbCommand(java.lang.String,
		 * org.qualitune.jouleunit.android.logcat.LogOutputReceiver)
		 */
		protected void executeAdbCommand(String cmd,
				final LogOutputReceiver logOutputReceiver)
				throws ProfilingException {
			try {
				deviceUnderTest.executeShellCommand(cmd,
						new IShellOutputReceiver() {

							/*
							 * (non-Javadoc)
							 * 
							 * @see
							 * com.android.ddmlib.IShellOutputReceiver#isCancelled
							 * ()
							 */
							public boolean isCancelled() {
								return logOutputReceiver.isCancelled();
							}

							/*
							 * (non-Javadoc)
							 * 
							 * @see
							 * com.android.ddmlib.IShellOutputReceiver#flush()
							 */
							public void flush() {
								logOutputReceiver.flush();
							}

							/*
							 * (non-Javadoc)
							 * 
							 * @see
							 * com.android.ddmlib.IShellOutputReceiver#addOutput
							 * (byte[], int, int)
							 */
							public void addOutput(byte[] data, int offset,
									int length) {
								logOutputReceiver.addOutput(data, offset,
										length);
							}
						});
			} catch (TimeoutException e) {
				printError(e.getMessage());
			} catch (AdbCommandRejectedException e) {
				printError(e.getMessage());
			} catch (ShellCommandUnresponsiveException e) {
				printError(e.getMessage());
			} catch (IOException e) {
				printError(e.getMessage());
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator
		 * #getHardwareServiceFile()
		 */
		@Override
		protected File getHardwareServiceFile() {
			URL fileLocation;
			File file;
			String path = "/resources/org.qualitune.jouleunit.android.hwservice.apk";

			if (Platform.isRunning()) {
				fileLocation = Platform
						.getBundle(
								"org.qualitune.jouleunit.android.testrun.resource.testrun")
						.getResource(path);
				try {
					fileLocation = FileLocator.resolve(fileLocation);
					file = new File(fileLocation.getFile());
				} catch (IOException e) {
					return null;
				}
			}

			else {
				File testLocation = new File(
						"./../org.qualitune.jouleunit.android.testrun.resource.testrun/");
				file = new File(testLocation.getAbsolutePath() + File.separator
						+ path);
			}

			return file;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#propagateResults
		 * ()
		 */
		protected void propagateResults() throws ProfilingException {
			propagateProfilingResults(testSuiteProfile);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#runTestCases
		 * ()
		 */
		protected void runTestCases() throws ProfilingException {
			coordinator.isInitialized = true;
			coordinator.isProfiling = true;

			/* Wait for the interpretation to be done. */
			while (coordinator.isProfiling) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					/* Not important. */
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.qualitune.jouleunit.coordinator.JouleUnitCoordinator#
		 * undeployTestCases()
		 */
		@Override
		protected void undeployTestCases() throws ProfilingException {
			uninstallApk(testRun.getPackageUnderTest());

			if (null != testRun.getJunitPackage()
					&& testRun.getJunitPackage().length() > 0)
				uninstallApk(testRun.getJunitPackage());
			// no else.
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator
		 * #installApk(java.lang.String)
		 */
		@Override
		protected void installApk(String path) throws ProfilingException {
			try {
				deviceUnderTest.installPackage(
						new File(path).getAbsolutePath(), true);
			} catch (InstallException e) {
				reportError("Error during installation of APK: "
						+ e.getMessage());
				throw new ProfilingException(
						"Error during installation of APK: " + e.getMessage());
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.qualitune.jouleunit.android.AbstractAndroidJouleUnitCoordinator
		 * #uninstallApk(java.lang.String)
		 */
		@Override
		protected void uninstallApk(String packageID) throws ProfilingException {
			try {
				deviceUnderTest.uninstallPackage(packageID);
			} catch (InstallException e) {
				reportError("Error during uninstallation of package: "
						+ e.getMessage());
			}
		}

		/**
		 * Returns the {@link TestSuiteProfile} of this {@link Coordinator}.
		 * 
		 * @return The {@link TestSuiteProfile} of this {@link Coordinator}.
		 */
		protected TestSuiteProfile getTestSuiteProfile() {
			return testSuiteProfile;
		}
	}

	private class ShellReceiver implements IShellOutputReceiver {

		protected boolean done = false;

		protected boolean gotError = false;

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.android.ddmlib.IShellOutputReceiver#isCancelled()
		 */
		@Override
		public boolean isCancelled() {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.android.ddmlib.IShellOutputReceiver#flush()
		 */
		@Override
		public void flush() {
			done = true;
		}

		@Override
		public void addOutput(byte[] arg0, int arg1, int arg2) {
			if (new String(arg0).contains("error"))
				gotError = true;
			// no else.

			printProgress(new String(arg0));
		}
	}

	private class OutputStreamBuffer extends OutputStream {

		protected StringBuffer buffer = new StringBuffer();

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.OutputStream#write(int)
		 */
		@Override
		public void write(int b) throws IOException {
			buffer.append(new String(new byte[] { (byte) b }));
		}
	}
}
