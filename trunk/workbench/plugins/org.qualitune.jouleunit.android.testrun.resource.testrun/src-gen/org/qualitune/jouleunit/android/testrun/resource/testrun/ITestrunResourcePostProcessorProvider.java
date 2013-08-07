/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun;

/**
 * Implementors of this interface can provide a post-processor for text resources.
 */
public interface ITestrunResourcePostProcessorProvider {
	
	/**
	 * Returns the processor that shall be called after text resource are successfully
	 * parsed.
	 */
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunResourcePostProcessor getResourcePostProcessor();
	
}
