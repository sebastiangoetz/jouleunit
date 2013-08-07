/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun;

/**
 * An element that is expected at a given position in a resource stream.
 */
public interface ITestrunExpectedElement {
	
	/**
	 * Returns the names of all tokens that are expected at the given position.
	 */
	public java.util.Set<String> getTokenNames();
	
	/**
	 * Returns the metaclass of the rule that contains the expected element.
	 */
	public org.eclipse.emf.ecore.EClass getRuleMetaclass();
	
	/**
	 * Returns the syntax element that is expected.
	 */
	public org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement getSymtaxElement();
	
	/**
	 * Adds an element that is a valid follower for this element.
	 */
	public void addFollower(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement follower, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContainedFeature[] path);
	
	/**
	 * Returns all valid followers for this element. Each follower is represented by a
	 * pair of an expected elements and the containment trace that leads from the
	 * current element to the follower.
	 */
	public java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunPair<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContainedFeature[]>> getFollowers();
	
}
