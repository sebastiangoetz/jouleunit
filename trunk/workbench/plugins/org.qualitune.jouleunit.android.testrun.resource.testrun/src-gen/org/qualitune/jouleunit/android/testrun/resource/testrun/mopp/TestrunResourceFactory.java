/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunResourceFactory implements org.eclipse.emf.ecore.resource.Resource.Factory {
	
	public TestrunResourceFactory() {
		super();
	}
	
	public org.eclipse.emf.ecore.resource.Resource createResource(org.eclipse.emf.common.util.URI uri) {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunResource(uri);
	}
	
}
