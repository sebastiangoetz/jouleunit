/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.grammar;

/**
 * A class to represent placeholders in a grammar.
 */
public class TestrunPlaceholder extends org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunTerminal {
	
	private final String tokenName;
	
	public TestrunPlaceholder(org.eclipse.emf.ecore.EStructuralFeature feature, String tokenName, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality cardinality, int mandatoryOccurencesAfter) {
		super(feature, cardinality, mandatoryOccurencesAfter);
		this.tokenName = tokenName;
	}
	
	public String getTokenName() {
		return tokenName;
	}
	
}
