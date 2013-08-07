/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

/**
 * Abstract super class for all expected elements. Provides methods to add
 * followers.
 */
public abstract class TestrunAbstractExpectedElement implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement {
	
	private org.eclipse.emf.ecore.EClass ruleMetaclass;
	
	private java.util.Set<org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunPair<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContainedFeature[]>> followers = new java.util.LinkedHashSet<org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunPair<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContainedFeature[]>>();
	
	public TestrunAbstractExpectedElement(org.eclipse.emf.ecore.EClass ruleMetaclass) {
		super();
		this.ruleMetaclass = ruleMetaclass;
	}
	
	public org.eclipse.emf.ecore.EClass getRuleMetaclass() {
		return ruleMetaclass;
	}
	
	public void addFollower(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement follower, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContainedFeature[] path) {
		followers.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunPair<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContainedFeature[]>(follower, path));
	}
	
	public java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunPair<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContainedFeature[]>> getFollowers() {
		return followers;
	}
	
}
