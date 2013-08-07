/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunBracketInformationProvider {
	
	public class BracketPair implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunBracketPair {
		
		private String opening;
		private String closing;
		private boolean closingEnabledInside;
		
		public BracketPair(String opening, String closing, boolean closingEnabledInside) {
			super();
			this.opening = opening;
			this.closing = closing;
			this.closingEnabledInside = closingEnabledInside;
		}
		
		public String getOpeningBracket() {
			return opening;
		}
		
		public String getClosingBracket() {
			return closing;
		}
		
		public boolean isClosingEnabledInside() {
			return closingEnabledInside;
		}
	}
	
	public java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunBracketPair> getBracketPairs() {
		java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunBracketPair> result = new java.util.ArrayList<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunBracketPair>();
		result.add(new BracketPair("{", "}", true));
		result.add(new BracketPair("\"", "\"", false));
		return result;
	}
	
}
