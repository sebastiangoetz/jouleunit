/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.analysis;

import java.io.File;

import org.eclipse.core.resources.ResourcesPlugin;
import org.qualitune.jouleunit.android.testrun.ApkFile;
import org.qualitune.jouleunit.android.testrun.TestrunFactory;

public class TestRunJunitApkReferenceResolver
		implements
		org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolver<org.qualitune.jouleunit.android.testrun.TestRun, org.qualitune.jouleunit.android.testrun.ApkFile> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.
	 * ITestrunReferenceResolver#resolve(java.lang.String,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference, int,
	 * boolean, org.qualitune.jouleunit.android.testrun.resource.testrun.
	 * ITestrunReferenceResolveResult)
	 */
	public void resolve(
			String identifier,
			org.qualitune.jouleunit.android.testrun.TestRun container,
			org.eclipse.emf.ecore.EReference reference,
			int position,
			boolean resolveFuzzy,
			final org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolveResult<org.qualitune.jouleunit.android.testrun.ApkFile> result) {

		String testRunFilePath = container.eResource().getURI().toString();
		if (testRunFilePath.startsWith("platform:/resource"))
			testRunFilePath = ResourcesPlugin.getWorkspace().getRoot()
					.getLocationURI().toString()
					+ testRunFilePath.substring("platform:/resource".length());
		// no else.

		String apkFilePath = testRunFilePath.substring(0,
				testRunFilePath.lastIndexOf("/"))
				+ "/" + identifier + ".apk";

		if (apkFilePath.startsWith("file:/"))
			apkFilePath = apkFilePath.substring("file:/".length());
		// no else.

		File apkFile = new File(apkFilePath);

		if (apkFile.exists()) {
			ApkFile resolvedFile = TestrunFactory.eINSTANCE.createApkFile();
			resolvedFile.setPath(apkFilePath);
			result.addMapping(identifier, resolvedFile);
		}
		// no else.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.android.testrun.resource.testrun.
	 * ITestrunReferenceResolver#deResolve(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference)
	 */
	public String deResolve(
			org.qualitune.jouleunit.android.testrun.ApkFile element,
			org.qualitune.jouleunit.android.testrun.TestRun container,
			org.eclipse.emf.ecore.EReference reference) {
		String path = element.getPath();
		path = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		return path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunConfigurable
	 * #setOptions(java.util.Map)
	 */
	public void setOptions(java.util.Map<?, ?> options) {
		// save options in a field or leave method empty if this resolver does
		// not depend
		// on any option
	}
}
