/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.debug;

public interface ITestrunDebugEventListener {
	
	/**
	 * Notification that the given event occurred in the while debugging.
	 */
	public void handleMessage(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage message);
}
