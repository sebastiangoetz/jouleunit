/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.grammar;

/**
 * A class to represent a rules in the grammar.
 */
public class TestrunRule extends org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement {
	
	private final org.eclipse.emf.ecore.EClass metaclass;
	
	public TestrunRule(org.eclipse.emf.ecore.EClass metaclass, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunChoice choice, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality cardinality) {
		super(cardinality, new org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement[] {choice});
		this.metaclass = metaclass;
	}
	
	public org.eclipse.emf.ecore.EClass getMetaclass() {
		return metaclass;
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunChoice getDefinition() {
		return (org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunChoice) getChildren()[0];
	}
	
}

