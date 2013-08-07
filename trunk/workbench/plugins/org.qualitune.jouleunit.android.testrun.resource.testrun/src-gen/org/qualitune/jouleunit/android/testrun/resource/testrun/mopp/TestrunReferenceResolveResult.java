/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

/**
 * A basic implementation of the
 * org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolv
 * eResult interface that collects mappings in a list.
 * 
 * @param <ReferenceType> the type of the references that can be contained in this
 * result
 */
public class TestrunReferenceResolveResult<ReferenceType> implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolveResult<ReferenceType> {
	
	private java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceMapping<ReferenceType>> mappings;
	private String errorMessage;
	private boolean resolveFuzzy;
	private java.util.Set<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix> quickFixes;
	
	public TestrunReferenceResolveResult(boolean resolveFuzzy) {
		super();
		this.resolveFuzzy = resolveFuzzy;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix> getQuickFixes() {
		if (quickFixes == null) {
			quickFixes = new java.util.LinkedHashSet<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix>();
		}
		return java.util.Collections.unmodifiableSet(quickFixes);
	}
	
	public void addQuickFix(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix quickFix) {
		if (quickFixes == null) {
			quickFixes = new java.util.LinkedHashSet<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix>();
		}
		quickFixes.add(quickFix);
	}
	
	public java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceMapping<ReferenceType>> getMappings() {
		return mappings;
	}
	
	public boolean wasResolved() {
		return mappings != null;
	}
	
	public boolean wasResolvedMultiple() {
		return mappings != null && mappings.size() > 1;
	}
	
	public boolean wasResolvedUniquely() {
		return mappings != null && mappings.size() == 1;
	}
	
	public void setErrorMessage(String message) {
		errorMessage = message;
	}
	
	public void addMapping(String identifier, ReferenceType target) {
		if (!resolveFuzzy && target == null) {
			throw new IllegalArgumentException("Mapping references to null is only allowed for fuzzy resolution.");
		}
		addMapping(identifier, target, null);
	}
	
	public void addMapping(String identifier, ReferenceType target, String warning) {
		if (mappings == null) {
			mappings = new java.util.ArrayList<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceMapping<ReferenceType>>(1);
		}
		mappings.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunElementMapping<ReferenceType>(identifier, target, warning));
		errorMessage = null;
	}
	
	public void addMapping(String identifier, org.eclipse.emf.common.util.URI uri) {
		addMapping(identifier, uri, null);
	}
	
	public void addMapping(String identifier, org.eclipse.emf.common.util.URI uri, String warning) {
		if (mappings == null) {
			mappings = new java.util.ArrayList<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceMapping<ReferenceType>>(1);
		}
		mappings.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunURIMapping<ReferenceType>(identifier, uri, warning));
	}
}
