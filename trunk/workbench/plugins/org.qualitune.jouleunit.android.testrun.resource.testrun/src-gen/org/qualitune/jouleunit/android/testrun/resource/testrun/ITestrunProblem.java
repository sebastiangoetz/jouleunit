/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun;

public interface ITestrunProblem {
	public String getMessage();
	public org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemSeverity getSeverity();
	public org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemType getType();
	public java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix> getQuickFixes();
}
