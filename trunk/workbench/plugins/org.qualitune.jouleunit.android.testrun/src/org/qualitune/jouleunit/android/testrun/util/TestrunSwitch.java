/**
 */
package org.qualitune.jouleunit.android.testrun.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.qualitune.jouleunit.android.testrun.*;
import org.qualitune.jouleunit.android.testrun.ApkFile;
import org.qualitune.jouleunit.android.testrun.Block;
import org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement;
import org.qualitune.jouleunit.android.testrun.CursorStatement;
import org.qualitune.jouleunit.android.testrun.DisplayStatement;
import org.qualitune.jouleunit.android.testrun.EnterStatement;
import org.qualitune.jouleunit.android.testrun.HomeButtonStatement;
import org.qualitune.jouleunit.android.testrun.OpenSettingsStatement;
import org.qualitune.jouleunit.android.testrun.StartActivityStatement;
import org.qualitune.jouleunit.android.testrun.Statement;
import org.qualitune.jouleunit.android.testrun.TestBehavior;
import org.qualitune.jouleunit.android.testrun.TestCase;
import org.qualitune.jouleunit.android.testrun.TestExecutable;
import org.qualitune.jouleunit.android.testrun.TestRun;
import org.qualitune.jouleunit.android.testrun.TestStatement;
import org.qualitune.jouleunit.android.testrun.TestSuite;
import org.qualitune.jouleunit.android.testrun.TestrunPackage;
import org.qualitune.jouleunit.android.testrun.UnlockStatement;
import org.qualitune.jouleunit.android.testrun.WaitStatement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage
 * @generated
 */
public class TestrunSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TestrunPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestrunSwitch() {
		if (modelPackage == null) {
			modelPackage = TestrunPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TestrunPackage.TEST_RUN: {
				TestRun testRun = (TestRun)theEObject;
				T result = caseTestRun(testRun);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.TEST_EXECUTABLE: {
				TestExecutable testExecutable = (TestExecutable)theEObject;
				T result = caseTestExecutable(testExecutable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.TEST_SUITE: {
				TestSuite testSuite = (TestSuite)theEObject;
				T result = caseTestSuite(testSuite);
				if (result == null) result = caseTestExecutable(testSuite);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.TEST_CASE: {
				TestCase testCase = (TestCase)theEObject;
				T result = caseTestCase(testCase);
				if (result == null) result = caseTestExecutable(testCase);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.JUNIT_TEST_CASE: {
				JunitTestCase junitTestCase = (JunitTestCase)theEObject;
				T result = caseJunitTestCase(junitTestCase);
				if (result == null) result = caseTestExecutable(junitTestCase);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.TEST_BEHAVIOR: {
				TestBehavior testBehavior = (TestBehavior)theEObject;
				T result = caseTestBehavior(testBehavior);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.BLOCK: {
				Block block = (Block)theEObject;
				T result = caseBlock(block);
				if (result == null) result = caseTestBehavior(block);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.STATEMENT: {
				Statement statement = (Statement)theEObject;
				T result = caseStatement(statement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.CLICK_ON_SCREEN_STATEMENT: {
				ClickOnScreenStatement clickOnScreenStatement = (ClickOnScreenStatement)theEObject;
				T result = caseClickOnScreenStatement(clickOnScreenStatement);
				if (result == null) result = caseStatement(clickOnScreenStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.CURSOR_STATEMENT: {
				CursorStatement cursorStatement = (CursorStatement)theEObject;
				T result = caseCursorStatement(cursorStatement);
				if (result == null) result = caseStatement(cursorStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.ENTER_STATEMENT: {
				EnterStatement enterStatement = (EnterStatement)theEObject;
				T result = caseEnterStatement(enterStatement);
				if (result == null) result = caseStatement(enterStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.DISPLAY_STATEMENT: {
				DisplayStatement displayStatement = (DisplayStatement)theEObject;
				T result = caseDisplayStatement(displayStatement);
				if (result == null) result = caseStatement(displayStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.HOME_BUTTON_STATEMENT: {
				HomeButtonStatement homeButtonStatement = (HomeButtonStatement)theEObject;
				T result = caseHomeButtonStatement(homeButtonStatement);
				if (result == null) result = caseStatement(homeButtonStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.OPEN_SETTINGS_STATEMENT: {
				OpenSettingsStatement openSettingsStatement = (OpenSettingsStatement)theEObject;
				T result = caseOpenSettingsStatement(openSettingsStatement);
				if (result == null) result = caseStatement(openSettingsStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.SEND_PORT_MESSAGE_STATEMENT: {
				SendPortMessageStatement sendPortMessageStatement = (SendPortMessageStatement)theEObject;
				T result = caseSendPortMessageStatement(sendPortMessageStatement);
				if (result == null) result = caseStatement(sendPortMessageStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.START_ACTIVITY_STATEMENT: {
				StartActivityStatement startActivityStatement = (StartActivityStatement)theEObject;
				T result = caseStartActivityStatement(startActivityStatement);
				if (result == null) result = caseStatement(startActivityStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.TEST_STATEMENT: {
				TestStatement testStatement = (TestStatement)theEObject;
				T result = caseTestStatement(testStatement);
				if (result == null) result = caseStatement(testStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.UNLOCK_STATEMENT: {
				UnlockStatement unlockStatement = (UnlockStatement)theEObject;
				T result = caseUnlockStatement(unlockStatement);
				if (result == null) result = caseStatement(unlockStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.WAIT_STATEMENT: {
				WaitStatement waitStatement = (WaitStatement)theEObject;
				T result = caseWaitStatement(waitStatement);
				if (result == null) result = caseStatement(waitStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestrunPackage.APK_FILE: {
				ApkFile apkFile = (ApkFile)theEObject;
				T result = caseApkFile(apkFile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Run</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Run</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestRun(TestRun object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Executable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Executable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestExecutable(TestExecutable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Suite</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Suite</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestSuite(TestSuite object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Case</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Case</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestCase(TestCase object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Junit Test Case</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Junit Test Case</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJunitTestCase(JunitTestCase object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Behavior</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestBehavior(TestBehavior object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlock(Block object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStatement(Statement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Click On Screen Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Click On Screen Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClickOnScreenStatement(ClickOnScreenStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cursor Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cursor Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCursorStatement(CursorStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enter Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enter Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnterStatement(EnterStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Wait Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Wait Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWaitStatement(WaitStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestStatement(TestStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Display Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Display Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDisplayStatement(DisplayStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unlock Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unlock Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnlockStatement(UnlockStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Open Settings Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Open Settings Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOpenSettingsStatement(OpenSettingsStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Send Port Message Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Send Port Message Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSendPortMessageStatement(SendPortMessageStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Start Activity Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Start Activity Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStartActivityStatement(StartActivityStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Home Button Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Home Button Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHomeButtonStatement(HomeButtonStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Apk File</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Apk File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApkFile(ApkFile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //TestrunSwitch
