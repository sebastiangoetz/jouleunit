/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunReferenceResolverSwitch implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolverSwitch {
	
	/**
	 * This map stores a copy of the options the were set for loading the resource.
	 */
	private java.util.Map<Object, Object> options;
	
	protected org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestRunAutReferenceResolver testRunAutReferenceResolver = new org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestRunAutReferenceResolver();
	protected org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestRunJunitApkReferenceResolver testRunJunitApkReferenceResolver = new org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestRunJunitApkReferenceResolver();
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver<org.qualitune.jouleunit.android.testrun.TestRun, org.qualitune.jouleunit.android.testrun.ApkFile> getTestRunAutReferenceResolver() {
		return getResolverChain(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun_Aut(), testRunAutReferenceResolver);
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver<org.qualitune.jouleunit.android.testrun.TestRun, org.qualitune.jouleunit.android.testrun.ApkFile> getTestRunJunitApkReferenceResolver() {
		return getResolverChain(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun_JunitApk(), testRunJunitApkReferenceResolver);
	}
	
	public void setOptions(java.util.Map<?, ?> options) {
		if (options != null) {
			this.options = new java.util.LinkedHashMap<Object, Object>();
			this.options.putAll(options);
		}
		testRunAutReferenceResolver.setOptions(options);
		testRunJunitApkReferenceResolver.setOptions(options);
	}
	
	public void resolveFuzzy(String identifier, org.eclipse.emf.ecore.EObject container, org.eclipse.emf.ecore.EReference reference, int position, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolveResult<org.eclipse.emf.ecore.EObject> result) {
		if (container == null) {
			return;
		}
		if (org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun().isInstance(container)) {
			TestrunFuzzyResolveResult<org.qualitune.jouleunit.android.testrun.ApkFile> frr = new TestrunFuzzyResolveResult<org.qualitune.jouleunit.android.testrun.ApkFile>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("aut")) {
				testRunAutReferenceResolver.resolve(identifier, (org.qualitune.jouleunit.android.testrun.TestRun) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
		if (org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun().isInstance(container)) {
			TestrunFuzzyResolveResult<org.qualitune.jouleunit.android.testrun.ApkFile> frr = new TestrunFuzzyResolveResult<org.qualitune.jouleunit.android.testrun.ApkFile>(result);
			String referenceName = reference.getName();
			org.eclipse.emf.ecore.EStructuralFeature feature = container.eClass().getEStructuralFeature(referenceName);
			if (feature != null && feature instanceof org.eclipse.emf.ecore.EReference && referenceName != null && referenceName.equals("junitApk")) {
				testRunJunitApkReferenceResolver.resolve(identifier, (org.qualitune.jouleunit.android.testrun.TestRun) container, (org.eclipse.emf.ecore.EReference) feature, position, true, frr);
			}
		}
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver<? extends org.eclipse.emf.ecore.EObject, ? extends org.eclipse.emf.ecore.EObject> getResolver(org.eclipse.emf.ecore.EStructuralFeature reference) {
		if (reference == org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun_Aut()) {
			return getResolverChain(reference, testRunAutReferenceResolver);
		}
		if (reference == org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun_JunitApk()) {
			return getResolverChain(reference, testRunJunitApkReferenceResolver);
		}
		return null;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})	
	public <ContainerType extends org.eclipse.emf.ecore.EObject, ReferenceType extends org.eclipse.emf.ecore.EObject> org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver<ContainerType, ReferenceType> getResolverChain(org.eclipse.emf.ecore.EStructuralFeature reference, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver<ContainerType, ReferenceType> originalResolver) {
		if (options == null) {
			return originalResolver;
		}
		Object value = options.get(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunOptions.ADDITIONAL_REFERENCE_RESOLVERS);
		if (value == null) {
			return originalResolver;
		}
		if (!(value instanceof java.util.Map)) {
			// send this to the error log
			new org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunRuntimeUtil().logWarning("Found value with invalid type for option " + org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunOptions.ADDITIONAL_REFERENCE_RESOLVERS + " (expected " + java.util.Map.class.getName() + ", but was " + value.getClass().getName() + ")", null);
			return originalResolver;
		}
		java.util.Map<?,?> resolverMap = (java.util.Map<?,?>) value;
		Object resolverValue = resolverMap.get(reference);
		if (resolverValue == null) {
			return originalResolver;
		}
		if (resolverValue instanceof org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver) {
			org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver replacingResolver = (org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver) resolverValue;
			if (replacingResolver instanceof org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunDelegatingReferenceResolver) {
				// pass original resolver to the replacing one
				((org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunDelegatingReferenceResolver) replacingResolver).setDelegate(originalResolver);
			}
			return replacingResolver;
		} else if (resolverValue instanceof java.util.Collection) {
			java.util.Collection replacingResolvers = (java.util.Collection) resolverValue;
			org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver replacingResolver = originalResolver;
			for (Object next : replacingResolvers) {
				if (next instanceof org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceCache) {
					org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver nextResolver = (org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver) next;
					if (nextResolver instanceof org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunDelegatingReferenceResolver) {
						// pass original resolver to the replacing one
						((org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunDelegatingReferenceResolver) nextResolver).setDelegate(replacingResolver);
					}
					replacingResolver = nextResolver;
				} else {
					// The collection contains a non-resolver. Send a warning to the error log.
					new org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunRuntimeUtil().logWarning("Found value with invalid type in value map for option " + org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunOptions.ADDITIONAL_REFERENCE_RESOLVERS + " (expected " + org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunDelegatingReferenceResolver.class.getName() + ", but was " + next.getClass().getName() + ")", null);
				}
			}
			return replacingResolver;
		} else {
			// The value for the option ADDITIONAL_REFERENCE_RESOLVERS has an unknown type.
			new org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunRuntimeUtil().logWarning("Found value with invalid type in value map for option " + org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunOptions.ADDITIONAL_REFERENCE_RESOLVERS + " (expected " + org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunDelegatingReferenceResolver.class.getName() + ", but was " + resolverValue.getClass().getName() + ")", null);
			return originalResolver;
		}
	}
	
}
