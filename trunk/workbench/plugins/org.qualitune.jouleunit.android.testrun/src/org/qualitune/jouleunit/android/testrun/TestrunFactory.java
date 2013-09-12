/**
 */
package org.qualitune.jouleunit.android.testrun;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage
 * @generated
 */
public interface TestrunFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TestrunFactory eINSTANCE = org.qualitune.jouleunit.android.testrun.impl.TestrunFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Test Run</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Run</em>'.
	 * @generated
	 */
	TestRun createTestRun();

	/**
	 * Returns a new object of class '<em>Test Suite</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Suite</em>'.
	 * @generated
	 */
	TestSuite createTestSuite();

	/**
	 * Returns a new object of class '<em>Test Case</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Case</em>'.
	 * @generated
	 */
	TestCase createTestCase();

	/**
	 * Returns a new object of class '<em>Junit Test Case</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Junit Test Case</em>'.
	 * @generated
	 */
	JunitTestCase createJunitTestCase();

	/**
	 * Returns a new object of class '<em>Block</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Block</em>'.
	 * @generated
	 */
	Block createBlock();

	/**
	 * Returns a new object of class '<em>Click On Screen Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Click On Screen Statement</em>'.
	 * @generated
	 */
	ClickOnScreenStatement createClickOnScreenStatement();

	/**
	 * Returns a new object of class '<em>Cursor Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Cursor Statement</em>'.
	 * @generated
	 */
	CursorStatement createCursorStatement();

	/**
	 * Returns a new object of class '<em>Enter Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enter Statement</em>'.
	 * @generated
	 */
	EnterStatement createEnterStatement();

	/**
	 * Returns a new object of class '<em>Wait Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Wait Statement</em>'.
	 * @generated
	 */
	WaitStatement createWaitStatement();

	/**
	 * Returns a new object of class '<em>Test Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Statement</em>'.
	 * @generated
	 */
	TestStatement createTestStatement();

	/**
	 * Returns a new object of class '<em>Display Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Display Statement</em>'.
	 * @generated
	 */
	DisplayStatement createDisplayStatement();

	/**
	 * Returns a new object of class '<em>Unlock Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unlock Statement</em>'.
	 * @generated
	 */
	UnlockStatement createUnlockStatement();

	/**
	 * Returns a new object of class '<em>Open Settings Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Open Settings Statement</em>'.
	 * @generated
	 */
	OpenSettingsStatement createOpenSettingsStatement();

	/**
	 * Returns a new object of class '<em>Send Port Message Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Send Port Message Statement</em>'.
	 * @generated
	 */
	SendPortMessageStatement createSendPortMessageStatement();

	/**
	 * Returns a new object of class '<em>Start Activity Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Start Activity Statement</em>'.
	 * @generated
	 */
	StartActivityStatement createStartActivityStatement();

	/**
	 * Returns a new object of class '<em>Home Button Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Home Button Statement</em>'.
	 * @generated
	 */
	HomeButtonStatement createHomeButtonStatement();

	/**
	 * Returns a new object of class '<em>Apk File</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Apk File</em>'.
	 * @generated
	 */
	ApkFile createApkFile();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TestrunPackage getTestrunPackage();

} //TestrunFactory
