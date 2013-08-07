/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.analysis;

public class TestrunNUMBERTokenResolver implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver {
	
	private org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestrunDefaultTokenResolver defaultTokenResolver = new org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestrunDefaultTokenResolver(true);
	
	public String deResolve(Object value, org.eclipse.emf.ecore.EStructuralFeature feature, org.eclipse.emf.ecore.EObject container) {
		// By default token de-resolving is delegated to the DefaultTokenResolver.
		String result = defaultTokenResolver.deResolve(value, feature, container, null, null, null);
		return result;
	}
	
	public void resolve(String lexem, org.eclipse.emf.ecore.EStructuralFeature feature, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result) {
		// By default token resolving is delegated to the DefaultTokenResolver.
		defaultTokenResolver.resolve(lexem, feature, result, null, null, null);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		defaultTokenResolver.setOptions(options);
	}
	
}
