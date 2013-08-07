/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun;

/**
 * A simple interface for commands that can be executed and that return
 * information about the success of their execution.
 */
public interface ITestrunCommand<ContextType> {
	
	public boolean execute(ContextType context);
}
