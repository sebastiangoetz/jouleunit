/**
 */
package org.qualitune.jouleunit.android.testrun.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.qualitune.jouleunit.android.testrun.*;
import org.qualitune.jouleunit.android.testrun.ApkFile;
import org.qualitune.jouleunit.android.testrun.Block;
import org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement;
import org.qualitune.jouleunit.android.testrun.CursorDirection;
import org.qualitune.jouleunit.android.testrun.CursorStatement;
import org.qualitune.jouleunit.android.testrun.DisplayStatement;
import org.qualitune.jouleunit.android.testrun.EnterStatement;
import org.qualitune.jouleunit.android.testrun.HomeButtonStatement;
import org.qualitune.jouleunit.android.testrun.OpenSettingsStatement;
import org.qualitune.jouleunit.android.testrun.StartActivityStatement;
import org.qualitune.jouleunit.android.testrun.TestCase;
import org.qualitune.jouleunit.android.testrun.TestRun;
import org.qualitune.jouleunit.android.testrun.TestStatement;
import org.qualitune.jouleunit.android.testrun.TestSuite;
import org.qualitune.jouleunit.android.testrun.TestrunFactory;
import org.qualitune.jouleunit.android.testrun.TestrunPackage;
import org.qualitune.jouleunit.android.testrun.UnlockStatement;
import org.qualitune.jouleunit.android.testrun.WaitStatement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TestrunFactoryImpl extends EFactoryImpl implements TestrunFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TestrunFactory init() {
		try {
			TestrunFactory theTestrunFactory = (TestrunFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.qualitune.org/jouleunit/android/testrun"); 
			if (theTestrunFactory != null) {
				return theTestrunFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TestrunFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestrunFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TestrunPackage.TEST_RUN: return createTestRun();
			case TestrunPackage.TEST_SUITE: return createTestSuite();
			case TestrunPackage.TEST_CASE: return createTestCase();
			case TestrunPackage.JUNIT_TEST_CASE: return createJunitTestCase();
			case TestrunPackage.BLOCK: return createBlock();
			case TestrunPackage.CLICK_ON_SCREEN_STATEMENT: return createClickOnScreenStatement();
			case TestrunPackage.CURSOR_STATEMENT: return createCursorStatement();
			case TestrunPackage.ENTER_STATEMENT: return createEnterStatement();
			case TestrunPackage.DISPLAY_STATEMENT: return createDisplayStatement();
			case TestrunPackage.HOME_BUTTON_STATEMENT: return createHomeButtonStatement();
			case TestrunPackage.OPEN_SETTINGS_STATEMENT: return createOpenSettingsStatement();
			case TestrunPackage.SEND_PORT_MESSAGE_STATEMENT: return createSendPortMessageStatement();
			case TestrunPackage.START_ACTIVITY_STATEMENT: return createStartActivityStatement();
			case TestrunPackage.TEST_STATEMENT: return createTestStatement();
			case TestrunPackage.UNLOCK_STATEMENT: return createUnlockStatement();
			case TestrunPackage.WAIT_STATEMENT: return createWaitStatement();
			case TestrunPackage.APK_FILE: return createApkFile();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TestrunPackage.CURSOR_DIRECTION:
				return createCursorDirectionFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TestrunPackage.CURSOR_DIRECTION:
				return convertCursorDirectionToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestRun createTestRun() {
		TestRunImpl testRun = new TestRunImpl();
		return testRun;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestSuite createTestSuite() {
		TestSuiteImpl testSuite = new TestSuiteImpl();
		return testSuite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestCase createTestCase() {
		TestCaseImpl testCase = new TestCaseImpl();
		return testCase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JunitTestCase createJunitTestCase() {
		JunitTestCaseImpl junitTestCase = new JunitTestCaseImpl();
		return junitTestCase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Block createBlock() {
		BlockImpl block = new BlockImpl();
		return block;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClickOnScreenStatement createClickOnScreenStatement() {
		ClickOnScreenStatementImpl clickOnScreenStatement = new ClickOnScreenStatementImpl();
		return clickOnScreenStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CursorStatement createCursorStatement() {
		CursorStatementImpl cursorStatement = new CursorStatementImpl();
		return cursorStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnterStatement createEnterStatement() {
		EnterStatementImpl enterStatement = new EnterStatementImpl();
		return enterStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WaitStatement createWaitStatement() {
		WaitStatementImpl waitStatement = new WaitStatementImpl();
		return waitStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestStatement createTestStatement() {
		TestStatementImpl testStatement = new TestStatementImpl();
		return testStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DisplayStatement createDisplayStatement() {
		DisplayStatementImpl displayStatement = new DisplayStatementImpl();
		return displayStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnlockStatement createUnlockStatement() {
		UnlockStatementImpl unlockStatement = new UnlockStatementImpl();
		return unlockStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OpenSettingsStatement createOpenSettingsStatement() {
		OpenSettingsStatementImpl openSettingsStatement = new OpenSettingsStatementImpl();
		return openSettingsStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SendPortMessageStatement createSendPortMessageStatement() {
		SendPortMessageStatementImpl sendPortMessageStatement = new SendPortMessageStatementImpl();
		return sendPortMessageStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartActivityStatement createStartActivityStatement() {
		StartActivityStatementImpl startActivityStatement = new StartActivityStatementImpl();
		return startActivityStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HomeButtonStatement createHomeButtonStatement() {
		HomeButtonStatementImpl homeButtonStatement = new HomeButtonStatementImpl();
		return homeButtonStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApkFile createApkFile() {
		ApkFileImpl apkFile = new ApkFileImpl();
		return apkFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CursorDirection createCursorDirectionFromString(EDataType eDataType, String initialValue) {
		CursorDirection result = CursorDirection.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCursorDirectionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestrunPackage getTestrunPackage() {
		return (TestrunPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TestrunPackage getPackage() {
		return TestrunPackage.eINSTANCE;
	}

} //TestrunFactoryImpl
