/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.grammar;

public class TestrunChoice extends org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement {
	
	public TestrunChoice(org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality cardinality, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement... choices) {
		super(cardinality, choices);
	}
	
	public String toString() {
		return org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunStringUtil.explode(getChildren(), "|");
	}
	
}
