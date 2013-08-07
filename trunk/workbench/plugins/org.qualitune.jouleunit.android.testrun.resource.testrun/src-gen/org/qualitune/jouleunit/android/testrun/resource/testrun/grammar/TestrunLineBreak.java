/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.grammar;

public class TestrunLineBreak extends org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunFormattingElement {
	
	private final int tabs;
	
	public TestrunLineBreak(org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality cardinality, int tabs) {
		super(cardinality);
		this.tabs = tabs;
	}
	
	public int getTabs() {
		return tabs;
	}
	
	public String toString() {
		return "!" + getTabs();
	}
	
}
