/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.grammar;

/**
 * A class to represent a keyword in the grammar.
 */
public class TestrunKeyword extends org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement {
	
	private final String value;
	
	public TestrunKeyword(String value, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality cardinality) {
		super(cardinality, null);
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return value;
	}
	
}
