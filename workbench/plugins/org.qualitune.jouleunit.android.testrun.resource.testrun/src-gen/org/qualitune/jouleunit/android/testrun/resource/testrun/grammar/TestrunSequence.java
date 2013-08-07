/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.grammar;

public class TestrunSequence extends org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement {
	
	public TestrunSequence(org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality cardinality, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement... elements) {
		super(cardinality, elements);
	}
	
	public String toString() {
		return org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunStringUtil.explode(getChildren(), " ");
	}
	
}
