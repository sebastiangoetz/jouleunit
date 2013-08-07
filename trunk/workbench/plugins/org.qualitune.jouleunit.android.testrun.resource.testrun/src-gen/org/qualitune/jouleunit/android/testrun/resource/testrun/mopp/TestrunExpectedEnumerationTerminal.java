/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

/**
 * A representation for a range in a document where an enumeration literal (i.e.,
 * a set of static strings) is expected.
 */
public class TestrunExpectedEnumerationTerminal extends org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunAbstractExpectedElement {
	
	private org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunEnumerationTerminal enumerationTerminal;
	
	public TestrunExpectedEnumerationTerminal(org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunEnumerationTerminal enumerationTerminal) {
		super(enumerationTerminal.getMetaclass());
		this.enumerationTerminal = enumerationTerminal;
	}
	
	public java.util.Set<String> getTokenNames() {
		// EnumerationTerminals are associated with multiple tokens, one for each literal
		// that was mapped to a string
		java.util.Set<String> tokenNames = new java.util.LinkedHashSet<String>();
		java.util.Map<String, String> mapping = enumerationTerminal.getLiteralMapping();
		for (String literalName : mapping.keySet()) {
			String text = mapping.get(literalName);
			if (text != null && !"".equals(text)) {
				tokenNames.add("'" + text + "'");
			}
		}
		return tokenNames;
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunEnumerationTerminal getEnumerationTerminal() {
		return this.enumerationTerminal;
	}
	
	/**
	 * Returns the expected enumeration terminal.
	 */
	public org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement getSymtaxElement() {
		return enumerationTerminal;
	}
	
	public String toString() {
		return "EnumTerminal \"" + getEnumerationTerminal() + "\"";
	}
	
}
