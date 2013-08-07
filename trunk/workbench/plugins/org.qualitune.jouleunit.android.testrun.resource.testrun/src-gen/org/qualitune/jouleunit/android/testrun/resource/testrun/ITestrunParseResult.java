/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun;

/**
 * An interface used to access the result of parsing a document.
 */
public interface ITestrunParseResult {
	
	/**
	 * Returns the root object of the document.
	 */
	public org.eclipse.emf.ecore.EObject getRoot();
	
	/**
	 * Returns a list of commands that must be executed after parsing the document.
	 */
	public java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunCommand<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource>> getPostParseCommands();
	
}
