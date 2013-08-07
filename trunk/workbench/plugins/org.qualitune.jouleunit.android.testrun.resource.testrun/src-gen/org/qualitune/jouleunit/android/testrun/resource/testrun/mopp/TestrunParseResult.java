/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunParseResult implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunParseResult {
	
	private org.eclipse.emf.ecore.EObject root;
	private java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunCommand<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource>> commands = new java.util.ArrayList<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunCommand<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource>>();
	
	public TestrunParseResult() {
		super();
	}
	
	public void setRoot(org.eclipse.emf.ecore.EObject root) {
		this.root = root;
	}
	
	public org.eclipse.emf.ecore.EObject getRoot() {
		return root;
	}
	
	public java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunCommand<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource>> getPostParseCommands() {
		return commands;
	}
	
}
