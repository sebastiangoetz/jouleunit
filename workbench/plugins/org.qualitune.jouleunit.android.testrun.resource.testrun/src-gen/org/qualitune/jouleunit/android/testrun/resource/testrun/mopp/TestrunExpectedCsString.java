/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

/**
 * A representation for a range in a document where a keyword (i.e., a static
 * string) is expected.
 */
public class TestrunExpectedCsString extends org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunAbstractExpectedElement {
	
	private org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunKeyword keyword;
	
	public TestrunExpectedCsString(org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunKeyword keyword) {
		super(keyword.getMetaclass());
		this.keyword = keyword;
	}
	
	public String getValue() {
		return keyword.getValue();
	}
	
	/**
	 * Returns the expected keyword.
	 */
	public org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement getSymtaxElement() {
		return keyword;
	}
	
	public java.util.Set<String> getTokenNames() {
		return java.util.Collections.singleton("'" + getValue() + "'");
	}
	
	public String toString() {
		return "CsString \"" + getValue() + "\"";
	}
	
	public boolean equals(Object o) {
		if (o instanceof TestrunExpectedCsString) {
			return getValue().equals(((TestrunExpectedCsString) o).getValue());
		}
		return false;
	}
	
	@Override	
	public int hashCode() {
		return getValue().hashCode();
	}
	
}
