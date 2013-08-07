/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.grammar;

public class TestrunContainment extends org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunTerminal {
	
	private final org.eclipse.emf.ecore.EClass[] allowedTypes;
	
	public TestrunContainment(org.eclipse.emf.ecore.EStructuralFeature feature, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunCardinality cardinality, org.eclipse.emf.ecore.EClass[] allowedTypes, int mandatoryOccurencesAfter) {
		super(feature, cardinality, mandatoryOccurencesAfter);
		this.allowedTypes = allowedTypes;
	}
	
	public org.eclipse.emf.ecore.EClass[] getAllowedTypes() {
		return allowedTypes;
	}
	
	public String toString() {
		String typeRestrictions = null;
		if (allowedTypes != null && allowedTypes.length > 0) {
			typeRestrictions = org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunStringUtil.explode(allowedTypes, ", ", new org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunFunction1<String, org.eclipse.emf.ecore.EClass>() {
				public String execute(org.eclipse.emf.ecore.EClass eClass) {
					return eClass.getName();
				}
			});
		}
		return getFeature().getName() + (typeRestrictions == null ? "" : "[" + typeRestrictions + "]");
	}
	
}
