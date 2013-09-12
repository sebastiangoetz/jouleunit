/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunSyntaxCoverageInformationProvider {
	
	public org.eclipse.emf.ecore.EClass[] getClassesWithSyntax() {
		return new org.eclipse.emf.ecore.EClass[] {
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getJunitTestCase(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getClickOnScreenStatement(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getCursorStatement(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getDisplayStatement(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getEnterStatement(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getHomeButtonStatement(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getOpenSettingsStatement(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getSendPortMessageStatement(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getStartActivityStatement(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getWaitStatement(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getUnlockStatement(),
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(),
		};
	}
	
	public org.eclipse.emf.ecore.EClass[] getStartSymbols() {
		return new org.eclipse.emf.ecore.EClass[] {
			org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(),
		};
	}
	
}
