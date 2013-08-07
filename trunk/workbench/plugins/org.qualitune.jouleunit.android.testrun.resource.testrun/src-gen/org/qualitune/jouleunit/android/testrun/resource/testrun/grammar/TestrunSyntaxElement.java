/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.grammar;

/**
 * The abstract super class for all elements of a grammar. This class provides
 * methods to traverse the grammar rules.
 */
public abstract class TestrunSyntaxElement {
	
	private TestrunSyntaxElement[] children;
	private TestrunSyntaxElement parent;
	private org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality cardinality;
	
	public TestrunSyntaxElement(org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality cardinality, TestrunSyntaxElement[] children) {
		this.cardinality = cardinality;
		this.children = children;
		if (this.children != null) {
			for (TestrunSyntaxElement child : this.children) {
				child.setParent(this);
			}
		}
	}
	
	/**
	 * Sets the parent of this syntax element. This method must be invoked at most
	 * once.
	 */
	public void setParent(TestrunSyntaxElement parent) {
		assert this.parent == null;
		this.parent = parent;
	}
	
	/**
	 * Returns the parent of this syntax element. This parent is determined by the
	 * containment hierarchy in the CS model.
	 */
	public TestrunSyntaxElement getParent() {
		return parent;
	}
	
	public TestrunSyntaxElement[] getChildren() {
		if (children == null) {
			return new TestrunSyntaxElement[0];
		}
		return children;
	}
	
	public org.eclipse.emf.ecore.EClass getMetaclass() {
		return parent.getMetaclass();
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality getCardinality() {
		return cardinality;
	}
	
}
