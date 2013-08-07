package org.qualitune.jouleunit.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JouleUnitPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.qualitune.jouleunit.core"; //$NON-NLS-1$

	// The shared instance
	private static JouleUnitPlugin plugin;

	/**
	 * The constructor
	 */
	public JouleUnitPlugin() {
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static JouleUnitPlugin getDefault() {
		return plugin;
	}

	/**
	 * Logs an exception to the default Eclipse log.
	 * <p/>
	 * The status severity is always set to ERROR.
	 * 
	 * @param exception
	 *            the exception to log.
	 * @param format
	 *            The format string, like for
	 *            {@link String#format(String, Object...)}.
	 * @param args
	 *            The arguments for the format string, like for
	 *            {@link String#format(String, Object...)}.
	 */
	public static void log(Throwable exception, String format, Object... args) {
		String message = null;
		if (format != null) {
			message = String.format(format, args);
		} else {
			message = "";
		}
		Status status = new Status(IStatus.ERROR, PLUGIN_ID, message, exception);

		getDefault().getLog().log(status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Helper method that returns the Android console of the ADT.
	 * 
	 * @return The found {@link IConsole} of the ADT or <code>null</code>.
	 */
	public IConsole getAndroidConsole() {
		IConsole androidConsole = null;
		for (IConsole aConsole : ConsolePlugin.getDefault().getConsoleManager()
				.getConsoles()) {
			if (aConsole.getName().equals("Android")) {
				androidConsole = aConsole;
				break;
			}
			// no else.
		}
		// end for.

		return androidConsole;
	}
}
