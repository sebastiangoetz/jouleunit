/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunAntlrScanner implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextScanner {
	
	private org.antlr.runtime3_4_0.Lexer antlrLexer;
	
	public TestrunAntlrScanner(org.antlr.runtime3_4_0.Lexer antlrLexer) {
		this.antlrLexer = antlrLexer;
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextToken getNextToken() {
		if (antlrLexer.getCharStream() == null) {
			return null;
		}
		final org.antlr.runtime3_4_0.Token current = antlrLexer.nextToken();
		if (current == null || current.getType() < 0) {
			return null;
		}
		org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextToken result = new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunANTLRTextToken(current);
		return result;
	}
	
	public void setText(String text) {
		antlrLexer.setCharStream(new org.antlr.runtime3_4_0.ANTLRStringStream(text));
	}
	
}
