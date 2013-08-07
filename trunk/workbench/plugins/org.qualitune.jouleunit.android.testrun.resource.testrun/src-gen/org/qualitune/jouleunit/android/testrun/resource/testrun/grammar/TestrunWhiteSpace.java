/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.grammar;

public class TestrunWhiteSpace extends org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunFormattingElement {
	
	private final int amount;
	
	public TestrunWhiteSpace(int amount, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality cardinality) {
		super(cardinality);
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public String toString() {
		return "#" + getAmount();
	}
	
}
