/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun;

/**
 * A delegating reference resolver is an extension of a normal reference resolver
 * that can be configured with another resolver that it may delegate method calls
 * to. This interface can be implemented by additional resolvers to customize
 * resolving using the load option ADDITIONAL_REFERENCE_RESOLVERS.
 * 
 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunOptions
 */
public interface ITestrunDelegatingReferenceResolver<ContainerType extends org.eclipse.emf.ecore.EObject, ReferenceType extends org.eclipse.emf.ecore.EObject> extends org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver<ContainerType, ReferenceType> {
	
	/**
	 * Sets the delegate for this resolver.
	 */
	public void setDelegate(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver<ContainerType, ReferenceType> delegate);
	
}
