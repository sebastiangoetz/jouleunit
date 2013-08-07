/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

/**
 * A factory for ContextDependentURIFragments. Given a feasible reference
 * resolver, this factory returns a matching fragment that used the resolver to
 * resolver proxy objects.
 * 
 * @param <ContainerType> the type of the class containing the reference to be
 * resolved
 * @param <ReferenceType> the type of the reference to be resolved
 */
public class TestrunContextDependentURIFragmentFactory<ContainerType extends org.eclipse.emf.ecore.EObject, ReferenceType extends org.eclipse.emf.ecore.EObject>  implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunContextDependentURIFragmentFactory<ContainerType, ReferenceType> {
	
	private final org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver<ContainerType, ReferenceType> resolver;
	
	public TestrunContextDependentURIFragmentFactory(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver<ContainerType, ReferenceType> resolver) {
		this.resolver = resolver;
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunContextDependentURIFragment<?> create(String identifier, ContainerType container, org.eclipse.emf.ecore.EReference reference, int positionInReference, org.eclipse.emf.ecore.EObject proxy) {
		
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContextDependentURIFragment<ContainerType, ReferenceType>(identifier, container, reference, positionInReference, proxy) {
			public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver<ContainerType, ReferenceType> getResolver() {
				return resolver;
			}
		};
	}
}
