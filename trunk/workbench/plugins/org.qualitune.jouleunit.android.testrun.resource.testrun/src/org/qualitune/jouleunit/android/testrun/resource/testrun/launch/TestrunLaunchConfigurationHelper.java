/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.launch;

import java.io.PrintStream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.qualitune.jouleunit.android.testrun.resource.testrun.run.TestrunInterpreter;

/**
 * A class that provides common methods that are required by launch
 * configuration delegates.
 */
public class TestrunLaunchConfigurationHelper {

	public static class SystemOutInterpreter
			extends
			org.qualitune.jouleunit.android.testrun.resource.testrun.util.AbstractTestrunInterpreter<Void, Void> {

		@Override
		public Void interprete(org.eclipse.emf.ecore.EObject object,
				Void context) {
			System.out.println("Found " + object
					+ ", but don't know what to do with it.");
			return null;
		}
	}

	/**
	 * Launch an example interpreter that prints object to System.out.
	 */
	public void launch(
			org.eclipse.debug.core.ILaunchConfiguration configuration,
			String mode, org.eclipse.debug.core.ILaunch launch,
			org.eclipse.core.runtime.IProgressMonitor monitor)
			throws org.eclipse.core.runtime.CoreException {
		org.eclipse.emf.ecore.EObject root = getModelRoot(configuration);

		/* Try to get the Android console. */
		PrintStream console;

		/* TODO GUI dependency. Should be configurable from the outside. */
		if (Platform.isRunning()) {
			IConsole androidConsole = null;
			for (IConsole aConsole : ConsolePlugin.getDefault()
					.getConsoleManager().getConsoles()) {
				if (aConsole.getName().equals("Android")) {
					androidConsole = aConsole;
					break;
				}
				// no else.
			}
			// end for.

			if (null != androidConsole
					&& androidConsole instanceof MessageConsole)
				console = new PrintStream(
						((MessageConsole) androidConsole).newOutputStream());

			else
				console = System.out;
		}

		/* Platform is not running. */
		else
			console = System.out;

		TestrunInterpreter delegate = new TestrunInterpreter(console);

		delegate.addObjectToInterprete(root);
		launchInterpreter(configuration, mode, launch, monitor, delegate, null);
	}

	public <ResultType, ContextType> void launchInterpreter(
			org.eclipse.debug.core.ILaunchConfiguration configuration,
			String mode,
			org.eclipse.debug.core.ILaunch launch,
			org.eclipse.core.runtime.IProgressMonitor monitor,
			org.qualitune.jouleunit.android.testrun.resource.testrun.util.AbstractTestrunInterpreter<ResultType, ContextType> delegate,
			final ContextType context)
			throws org.eclipse.core.runtime.CoreException {
		final boolean enableDebugger = mode
				.equals(org.eclipse.debug.core.ILaunchManager.DEBUG_MODE);
		// step 1: find two free ports we can use to communicate between the
		// Eclipse and
		// the interpreter
		int requestPort = findFreePort();
		int eventPort = findFreePort();
		if (requestPort < 0 || eventPort < 0) {
			abort("Unable to find free port", null);
		}

		final org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebuggableInterpreter<ResultType, ContextType> interpreter = new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebuggableInterpreter<ResultType, ContextType>(
				delegate, eventPort);

		// step 2: prepare and start interpreter in separate thread
		Thread interpreterThread = new Thread(new Runnable() {

			public void run() {
				// if we are in debug mode, the interpreter must wait for the
				// debugger to attach
				interpreter.interprete(context, enableDebugger);
			}
		});
		interpreterThread.start();

		// step 3: start debugger listener (sends commands from Eclipse debug
		// framework to
		// running process
		org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebuggerListener<ResultType, ContextType> debugListener = new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebuggerListener<ResultType, ContextType>(
				requestPort);
		debugListener.setDebuggable(interpreter);
		new Thread(debugListener).start();

		// step 4: start debugger
		org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugProcess process = new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugProcess(
				launch);
		launch.addDebugTarget(new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugTarget(
				process, launch, requestPort, eventPort));
	}

	public org.eclipse.emf.common.util.URI getURI(
			org.eclipse.debug.core.ILaunchConfiguration configuration)
			throws org.eclipse.core.runtime.CoreException {
		return org.eclipse.emf.common.util.URI
				.createURI(configuration
						.getAttribute(
								org.qualitune.jouleunit.android.testrun.resource.testrun.launch.TestrunLaunchConfigurationDelegate.ATTR_RESOURCE_URI,
								(String) null));
	}

	public org.eclipse.emf.ecore.EObject getModelRoot(
			org.eclipse.debug.core.ILaunchConfiguration configuration)
			throws org.eclipse.core.runtime.CoreException {
		return org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunResourceUtil
				.getResourceContent(getURI(configuration));
	}

	/**
	 * Returns a free port number on localhost, or -1 if unable to find a free
	 * port.
	 */
	protected int findFreePort() {
		java.net.ServerSocket socket = null;
		try {
			socket = new java.net.ServerSocket(0);
			return socket.getLocalPort();
		} catch (java.io.IOException e) {
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (java.io.IOException e) {
				}
			}
		}
		return -1;
	}

	/**
	 * Throws an exception with a new status containing the given message and
	 * optional exception.
	 * 
	 * @param message
	 *            error message
	 * @param e
	 *            underlying exception
	 * 
	 * @throws CoreException
	 */
	protected void abort(String message, Throwable e)
			throws org.eclipse.core.runtime.CoreException {
		throw new org.eclipse.core.runtime.CoreException(
				new org.eclipse.core.runtime.Status(
						org.eclipse.core.runtime.IStatus.ERROR,
						org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunPlugin.DEBUG_MODEL_ID,
						0, message, e));
	}
}
