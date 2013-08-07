/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.util;

/**
 * Class TestrunTextResourceUtil can be used to perform common tasks on text
 * resources, such as loading and saving resources, as well as, checking them for
 * errors. This class is deprecated and has been replaced by
 * org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunResourceUti
 * l.
 */
public class TestrunTextResourceUtil {
	
	/**
	 * Use
	 * org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunResourceUti
	 * l.getResource() instead.
	 */
	@Deprecated	
	public static org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunResource getResource(org.eclipse.core.resources.IFile file) {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunEclipseProxy().getResource(file);
	}
	
	/**
	 * Use
	 * org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunResourceUti
	 * l.getResource() instead.
	 */
	@Deprecated	
	public static org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunResource getResource(java.io.File file, java.util.Map<?,?> options) {
		return org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunResourceUtil.getResource(file, options);
	}
	
	/**
	 * Use
	 * org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunResourceUti
	 * l.getResource() instead.
	 */
	@Deprecated	
	public static org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunResource getResource(org.eclipse.emf.common.util.URI uri) {
		return org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunResourceUtil.getResource(uri);
	}
	
	/**
	 * Use
	 * org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunResourceUti
	 * l.getResource() instead.
	 */
	@Deprecated	
	public static org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunResource getResource(org.eclipse.emf.common.util.URI uri, java.util.Map<?,?> options) {
		return org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunResourceUtil.getResource(uri, options);
	}
	
}
