/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunProblem implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunProblem {
	
	private String message;
	private org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemType type;
	private org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemSeverity severity;
	private java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix> quickFixes;
	
	public TestrunProblem(String message, org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemType type, org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemSeverity severity) {
		this(message, type, severity, java.util.Collections.<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix>emptySet());
	}
	
	public TestrunProblem(String message, org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemType type, org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemSeverity severity, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix quickFix) {
		this(message, type, severity, java.util.Collections.singleton(quickFix));
	}
	
	public TestrunProblem(String message, org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemType type, org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemSeverity severity, java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix> quickFixes) {
		super();
		this.message = message;
		this.type = type;
		this.severity = severity;
		this.quickFixes = new java.util.LinkedHashSet<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix>();
		this.quickFixes.addAll(quickFixes);
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemType getType() {
		return type;
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemSeverity getSeverity() {
		return severity;
	}
	
	public String getMessage() {
		return message;
	}
	
	public java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix> getQuickFixes() {
		return quickFixes;
	}
	
}
