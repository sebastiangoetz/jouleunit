package org.qualitune.jouleunit.data;

import java.util.HashSet;
import java.util.Set;

import org.qualitune.jouleunit.coordinator.TestSuiteProfile;

/**
 * Helper class to propagate test results to the UI.
 * 
 * @author Claas Wilke
 */
public class ResultPropagator {

	/**
	 * The last {@link TestSuiteProfile} propagated by the
	 * {@link ResultPropagator} or <code>null</code>.
	 */
	private static TestSuiteProfile lastPropagatedResult;

	/** The current listeners of the {@link ResultPropagator}. */
	private static Set<IProfilingResultReceiver> myListeners = new HashSet<IProfilingResultReceiver>();

	/**
	 * Adds a {@link IProfilingResultReceiver} as listener of the
	 * {@link ResultPropagator}.
	 * 
	 * @param receiver
	 *            The {@link IProfilingResultReceiver} to be registered.
	 */
	public static void registerListener(IProfilingResultReceiver receiver) {
		if (myListeners.add(receiver) && null != lastPropagatedResult)
			receiver.updateToNewResult(lastPropagatedResult);
		// no else.
	}

	/**
	 * Sends a new {@link TestSuiteProfile} to the {@link ResultPropagator} that
	 * is then propagated to all registered listeners.
	 * 
	 * @param profile
	 *            The {@link TestSuiteProfile} to be propagated.
	 */
	public static void sendNewResult(TestSuiteProfile profile) {

		lastPropagatedResult = profile;

		/* Propagation. */
		for (IProfilingResultReceiver receiver : myListeners)
			receiver.updateToNewResult(profile);
		// end for.
	}

	/**
	 * Removes a {@link IProfilingResultReceiver} as listener of the
	 * {@link ResultPropagator}.
	 * 
	 * @param receiver
	 *            The {@link IProfilingResultReceiver} to be registered.
	 */
	public static void unregisterListener(IProfilingResultReceiver receiver) {
		if (null != receiver)
			myListeners.remove(receiver);
		// no else.
	}
}
