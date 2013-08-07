/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.ui;

/**
 * A class which can be overridden to customize code completion proposals.
 */
public class TestrunProposalPostProcessor {
	
	public java.util.List<org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCompletionProposal> process(java.util.List<org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCompletionProposal> proposals) {
		// the default implementation does returns the proposals as they are
		return proposals;
	}
	
}
