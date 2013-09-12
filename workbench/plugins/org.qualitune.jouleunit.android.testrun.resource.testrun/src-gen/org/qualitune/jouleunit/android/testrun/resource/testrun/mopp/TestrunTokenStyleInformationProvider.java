/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunTokenStyleInformationProvider {
	
	public static String TASK_ITEM_TOKEN_NAME = "TASK_ITEM";
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenStyle getDefaultTokenStyle(String tokenName) {
		if ("ML_COMMENT".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x00, 0x80, 0x00}, null, false, true, false, false);
		}
		if ("SL_COMMENT".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x00, 0x80, 0x00}, null, false, true, false, false);
		}
		if ("TestRun".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("applicationUnderTest".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("unitTests".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("numberOfRuns".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("idleTime".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("hardwareProfiling".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("on".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("off".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("run".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("onServer".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("local".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("eachTestWithFullBattery".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("UnitTestCase".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("TestSuite".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("SetUp".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("TearDown".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("TestCase".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("CLICK".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("ON".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("SCREEN".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("CURSOR".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("DISPLAY".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("OFF".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("ENTER".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("HOME".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("BUTTON".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("OPEN".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("SETTINGS".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("SEND".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("MESSAGE".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("START".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("ACTIVITY".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("WAIT".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("FOR".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("UNLOCK".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("Test".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x80, 0x00, 0x55}, null, true, false, false, false);
		}
		if ("QUOTED_34_34".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x2A, 0x00, 0xFF}, null, false, false, false, false);
		}
		if ("TASK_ITEM".equals(tokenName)) {
			return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyle(new int[] {0x7F, 0x9F, 0xBF}, null, true, false, false, false);
		}
		return null;
	}
	
}
