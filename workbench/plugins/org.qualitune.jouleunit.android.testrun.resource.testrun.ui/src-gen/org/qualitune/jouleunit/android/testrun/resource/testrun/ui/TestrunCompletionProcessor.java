/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.ui;

public class TestrunCompletionProcessor implements org.eclipse.jface.text.contentassist.IContentAssistProcessor {
	
	private org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunResourceProvider resourceProvider;
	private org.qualitune.jouleunit.android.testrun.resource.testrun.ui.ITestrunBracketHandlerProvider bracketHandlerProvider;
	
	public TestrunCompletionProcessor(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunResourceProvider resourceProvider, org.qualitune.jouleunit.android.testrun.resource.testrun.ui.ITestrunBracketHandlerProvider bracketHandlerProvider) {
		this.resourceProvider = resourceProvider;
		this.bracketHandlerProvider = bracketHandlerProvider;
	}
	
	public org.eclipse.jface.text.contentassist.ICompletionProposal[] computeCompletionProposals(org.eclipse.jface.text.ITextViewer viewer, int offset) {
		org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource textResource = resourceProvider.getResource();
		if (textResource == null) {
			return new org.eclipse.jface.text.contentassist.ICompletionProposal[0];
		}
		String content = viewer.getDocument().get();
		org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCodeCompletionHelper helper = new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCodeCompletionHelper();
		org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCompletionProposal[] computedProposals = helper.computeCompletionProposals(textResource, content, offset);
		
		// call completion proposal post processor to allow for customizing the proposals
		org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunProposalPostProcessor proposalPostProcessor = new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunProposalPostProcessor();
		java.util.List<org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCompletionProposal> computedProposalList = java.util.Arrays.asList(computedProposals);
		java.util.List<org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCompletionProposal> extendedProposalList = proposalPostProcessor.process(computedProposalList);
		if (extendedProposalList == null) {
			extendedProposalList = java.util.Collections.emptyList();
		}
		java.util.List<org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCompletionProposal> finalProposalList = new java.util.ArrayList<org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCompletionProposal>();
		for (org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCompletionProposal proposal : extendedProposalList) {
			if (proposal.getMatchesPrefix()) {
				finalProposalList.add(proposal);
			}
		}
		org.eclipse.jface.text.contentassist.ICompletionProposal[] result = new org.eclipse.jface.text.contentassist.ICompletionProposal[finalProposalList.size()];
		int i = 0;
		for (org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCompletionProposal proposal : finalProposalList) {
			String proposalString = proposal.getInsertString();
			String displayString = proposal.getDisplayString();
			String prefix = proposal.getPrefix();
			org.eclipse.swt.graphics.Image image = proposal.getImage();
			org.eclipse.jface.text.contentassist.IContextInformation info;
			info = new org.eclipse.jface.text.contentassist.ContextInformation(image, proposalString, proposalString);
			int begin = offset - prefix.length();
			int replacementLength = prefix.length();
			// if a closing bracket was automatically inserted right before, we enlarge the
			// replacement length in order to overwrite the bracket.
			org.qualitune.jouleunit.android.testrun.resource.testrun.ui.ITestrunBracketHandler bracketHandler = bracketHandlerProvider.getBracketHandler();
			String closingBracket = bracketHandler.getClosingBracket();
			if (bracketHandler.addedClosingBracket() && proposalString.endsWith(closingBracket)) {
				replacementLength += closingBracket.length();
			}
			result[i++] = new org.eclipse.jface.text.contentassist.CompletionProposal(proposalString, begin, replacementLength, proposalString.length(), image, displayString, info, proposalString);
		}
		return result;
	}
	
	public org.eclipse.jface.text.contentassist.IContextInformation[] computeContextInformation(org.eclipse.jface.text.ITextViewer viewer, int offset) {
		return null;
	}
	
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}
	
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}
	
	public org.eclipse.jface.text.contentassist.IContextInformationValidator getContextInformationValidator() {
		return null;
	}
	
	public String getErrorMessage() {
		return null;
	}
}
