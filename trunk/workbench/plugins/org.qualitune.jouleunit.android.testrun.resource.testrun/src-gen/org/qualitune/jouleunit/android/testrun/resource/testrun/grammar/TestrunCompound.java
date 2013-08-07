/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.grammar;

public class TestrunCompound extends org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement {
	
	public TestrunCompound(org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunChoice choice, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality cardinality) {
		super(cardinality, new org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement[] {choice});
	}
	
	public String toString() {
		return "(" + getChildren()[0] + ")";
	}
	
}
