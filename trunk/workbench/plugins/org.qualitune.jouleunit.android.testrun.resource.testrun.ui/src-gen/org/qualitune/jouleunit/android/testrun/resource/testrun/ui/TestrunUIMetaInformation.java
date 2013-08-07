/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.ui;

public class TestrunUIMetaInformation extends org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunMetaInformation {
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunHoverTextProvider getHoverTextProvider() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunHoverTextProvider();
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunImageProvider getImageProvider() {
		return org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunImageProvider.INSTANCE;
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunColorManager createColorManager() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunColorManager();
	}
	
	/**
	 * @deprecated this method is only provided to preserve API compatibility. Use
	 * createTokenScanner(org.qualitune.jouleunit.android.testrun.resource.testrun.ITes
	 * trunTextResource,
	 * org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunColorManager)
	 * instead.
	 */
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunTokenScanner createTokenScanner(org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunColorManager colorManager) {
		return createTokenScanner(null, colorManager);
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunTokenScanner createTokenScanner(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource resource, org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunColorManager colorManager) {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunTokenScanner(resource, colorManager);
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCodeCompletionHelper createCodeCompletionHelper() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunCodeCompletionHelper();
	}
	
}
