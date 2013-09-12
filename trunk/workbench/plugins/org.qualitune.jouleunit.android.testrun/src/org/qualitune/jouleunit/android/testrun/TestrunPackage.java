/**
 */
package org.qualitune.jouleunit.android.testrun;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.qualitune.jouleunit.android.testrun.TestrunFactory
 * @model kind="package"
 * @generated
 */
public interface TestrunPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "testrun";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.qualitune.org/jouleunit/android/testrun";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "testrun";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TestrunPackage eINSTANCE = org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl <em>Test Run</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestRunImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestRun()
	 * @generated
	 */
	int TEST_RUN = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__NAME = 0;

	/**
	 * The feature id for the '<em><b>Aut</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__AUT = 1;

	/**
	 * The feature id for the '<em><b>Package Under Test</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__PACKAGE_UNDER_TEST = 2;

	/**
	 * The feature id for the '<em><b>Junit Apk</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__JUNIT_APK = 3;

	/**
	 * The feature id for the '<em><b>Junit Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__JUNIT_PACKAGE = 4;

	/**
	 * The feature id for the '<em><b>No Of Runs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__NO_OF_RUNS = 5;

	/**
	 * The feature id for the '<em><b>Idle Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__IDLE_TIME = 6;

	/**
	 * The feature id for the '<em><b>Hardware Profiling On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__HARDWARE_PROFILING_ON = 7;

	/**
	 * The feature id for the '<em><b>Remote Run</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__REMOTE_RUN = 8;

	/**
	 * The feature id for the '<em><b>Executables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__EXECUTABLES = 9;

	/**
	 * The feature id for the '<em><b>Wait For Full Battery</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__WAIT_FOR_FULL_BATTERY = 10;

	/**
	 * The number of structural features of the '<em>Test Run</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_RUN_FEATURE_COUNT = 11;


	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestExecutableImpl <em>Test Executable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestExecutableImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestExecutable()
	 * @generated
	 */
	int TEST_EXECUTABLE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_EXECUTABLE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Test Executable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_EXECUTABLE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestSuiteImpl <em>Test Suite</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestSuiteImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestSuite()
	 * @generated
	 */
	int TEST_SUITE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_SUITE__NAME = TEST_EXECUTABLE__NAME;

	/**
	 * The feature id for the '<em><b>Set Up</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_SUITE__SET_UP = TEST_EXECUTABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tear Down</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_SUITE__TEAR_DOWN = TEST_EXECUTABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Executables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_SUITE__EXECUTABLES = TEST_EXECUTABLE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Test Suite</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_SUITE_FEATURE_COUNT = TEST_EXECUTABLE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestCaseImpl <em>Test Case</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestCaseImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestCase()
	 * @generated
	 */
	int TEST_CASE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_CASE__NAME = TEST_EXECUTABLE__NAME;

	/**
	 * The feature id for the '<em><b>Behavior</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_CASE__BEHAVIOR = TEST_EXECUTABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Test Case</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_CASE_FEATURE_COUNT = TEST_EXECUTABLE_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.JunitTestCaseImpl <em>Junit Test Case</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.JunitTestCaseImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getJunitTestCase()
	 * @generated
	 */
	int JUNIT_TEST_CASE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JUNIT_TEST_CASE__NAME = TEST_EXECUTABLE__NAME;

	/**
	 * The number of structural features of the '<em>Junit Test Case</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JUNIT_TEST_CASE_FEATURE_COUNT = TEST_EXECUTABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestBehaviorImpl <em>Test Behavior</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestBehaviorImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestBehavior()
	 * @generated
	 */
	int TEST_BEHAVIOR = 5;

	/**
	 * The number of structural features of the '<em>Test Behavior</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_BEHAVIOR_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.BlockImpl <em>Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.BlockImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getBlock()
	 * @generated
	 */
	int BLOCK = 6;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK__STATEMENTS = TEST_BEHAVIOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FEATURE_COUNT = TEST_BEHAVIOR_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.StatementImpl <em>Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.StatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getStatement()
	 * @generated
	 */
	int STATEMENT = 7;

	/**
	 * The number of structural features of the '<em>Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.ClickOnScreenStatementImpl <em>Click On Screen Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.ClickOnScreenStatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getClickOnScreenStatement()
	 * @generated
	 */
	int CLICK_ON_SCREEN_STATEMENT = 8;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLICK_ON_SCREEN_STATEMENT__X = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLICK_ON_SCREEN_STATEMENT__Y = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Click On Screen Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLICK_ON_SCREEN_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.CursorStatementImpl <em>Cursor Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.CursorStatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getCursorStatement()
	 * @generated
	 */
	int CURSOR_STATEMENT = 9;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CURSOR_STATEMENT__DIRECTION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Cursor Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CURSOR_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.EnterStatementImpl <em>Enter Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.EnterStatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getEnterStatement()
	 * @generated
	 */
	int ENTER_STATEMENT = 10;

	/**
	 * The number of structural features of the '<em>Enter Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTER_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.WaitStatementImpl <em>Wait Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.WaitStatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getWaitStatement()
	 * @generated
	 */
	int WAIT_STATEMENT = 18;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestStatementImpl <em>Test Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestStatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestStatement()
	 * @generated
	 */
	int TEST_STATEMENT = 16;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.DisplayStatementImpl <em>Display Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.DisplayStatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getDisplayStatement()
	 * @generated
	 */
	int DISPLAY_STATEMENT = 11;

	/**
	 * The feature id for the '<em><b>Switch On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISPLAY_STATEMENT__SWITCH_ON = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Display Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISPLAY_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.UnlockStatementImpl <em>Unlock Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.UnlockStatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getUnlockStatement()
	 * @generated
	 */
	int UNLOCK_STATEMENT = 17;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.OpenSettingsStatementImpl <em>Open Settings Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.OpenSettingsStatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getOpenSettingsStatement()
	 * @generated
	 */
	int OPEN_SETTINGS_STATEMENT = 13;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.HomeButtonStatementImpl <em>Home Button Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.HomeButtonStatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getHomeButtonStatement()
	 * @generated
	 */
	int HOME_BUTTON_STATEMENT = 12;

	/**
	 * The number of structural features of the '<em>Home Button Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOME_BUTTON_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Open Settings Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_SETTINGS_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.SendPortMessageStatementImpl <em>Send Port Message Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.SendPortMessageStatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getSendPortMessageStatement()
	 * @generated
	 */
	int SEND_PORT_MESSAGE_STATEMENT = 14;

	/**
	 * The feature id for the '<em><b>Ip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEND_PORT_MESSAGE_STATEMENT__IP = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEND_PORT_MESSAGE_STATEMENT__PORT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEND_PORT_MESSAGE_STATEMENT__MESSAGE = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Send Port Message Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEND_PORT_MESSAGE_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.StartActivityStatementImpl <em>Start Activity Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.StartActivityStatementImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getStartActivityStatement()
	 * @generated
	 */
	int START_ACTIVITY_STATEMENT = 15;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_ACTIVITY_STATEMENT__PACKAGE = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_ACTIVITY_STATEMENT__CLASS = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Start Activity Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_ACTIVITY_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_STATEMENT__NAME = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_STATEMENT__STATEMENTS = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Test Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Unlock Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNLOCK_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Seconds</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WAIT_STATEMENT__SECONDS = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Wait Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WAIT_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;


	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.impl.ApkFileImpl <em>Apk File</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.impl.ApkFileImpl
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getApkFile()
	 * @generated
	 */
	int APK_FILE = 19;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APK_FILE__PATH = 0;

	/**
	 * The number of structural features of the '<em>Apk File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APK_FILE_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link org.qualitune.jouleunit.android.testrun.CursorDirection <em>Cursor Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.qualitune.jouleunit.android.testrun.CursorDirection
	 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getCursorDirection()
	 * @generated
	 */
	int CURSOR_DIRECTION = 20;


	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.TestRun <em>Test Run</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Run</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun
	 * @generated
	 */
	EClass getTestRun();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.TestRun#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun#getName()
	 * @see #getTestRun()
	 * @generated
	 */
	EAttribute getTestRun_Name();

	/**
	 * Returns the meta object for the reference '{@link org.qualitune.jouleunit.android.testrun.TestRun#getAut <em>Aut</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Aut</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun#getAut()
	 * @see #getTestRun()
	 * @generated
	 */
	EReference getTestRun_Aut();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.TestRun#getPackageUnderTest <em>Package Under Test</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package Under Test</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun#getPackageUnderTest()
	 * @see #getTestRun()
	 * @generated
	 */
	EAttribute getTestRun_PackageUnderTest();

	/**
	 * Returns the meta object for the reference '{@link org.qualitune.jouleunit.android.testrun.TestRun#getJunitApk <em>Junit Apk</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Junit Apk</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun#getJunitApk()
	 * @see #getTestRun()
	 * @generated
	 */
	EReference getTestRun_JunitApk();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.TestRun#getJunitPackage <em>Junit Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Junit Package</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun#getJunitPackage()
	 * @see #getTestRun()
	 * @generated
	 */
	EAttribute getTestRun_JunitPackage();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.TestRun#getNoOfRuns <em>No Of Runs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>No Of Runs</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun#getNoOfRuns()
	 * @see #getTestRun()
	 * @generated
	 */
	EAttribute getTestRun_NoOfRuns();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.TestRun#getIdleTime <em>Idle Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Idle Time</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun#getIdleTime()
	 * @see #getTestRun()
	 * @generated
	 */
	EAttribute getTestRun_IdleTime();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.TestRun#isHardwareProfilingOn <em>Hardware Profiling On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hardware Profiling On</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun#isHardwareProfilingOn()
	 * @see #getTestRun()
	 * @generated
	 */
	EAttribute getTestRun_HardwareProfilingOn();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.TestRun#isRemoteRun <em>Remote Run</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remote Run</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun#isRemoteRun()
	 * @see #getTestRun()
	 * @generated
	 */
	EAttribute getTestRun_RemoteRun();

	/**
	 * Returns the meta object for the containment reference list '{@link org.qualitune.jouleunit.android.testrun.TestRun#getExecutables <em>Executables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Executables</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun#getExecutables()
	 * @see #getTestRun()
	 * @generated
	 */
	EReference getTestRun_Executables();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.TestRun#isWaitForFullBattery <em>Wait For Full Battery</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Wait For Full Battery</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun#isWaitForFullBattery()
	 * @see #getTestRun()
	 * @generated
	 */
	EAttribute getTestRun_WaitForFullBattery();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.TestExecutable <em>Test Executable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Executable</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestExecutable
	 * @generated
	 */
	EClass getTestExecutable();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.TestExecutable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestExecutable#getName()
	 * @see #getTestExecutable()
	 * @generated
	 */
	EAttribute getTestExecutable_Name();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.TestSuite <em>Test Suite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Suite</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestSuite
	 * @generated
	 */
	EClass getTestSuite();

	/**
	 * Returns the meta object for the containment reference '{@link org.qualitune.jouleunit.android.testrun.TestSuite#getSetUp <em>Set Up</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Set Up</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestSuite#getSetUp()
	 * @see #getTestSuite()
	 * @generated
	 */
	EReference getTestSuite_SetUp();

	/**
	 * Returns the meta object for the containment reference '{@link org.qualitune.jouleunit.android.testrun.TestSuite#getTearDown <em>Tear Down</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tear Down</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestSuite#getTearDown()
	 * @see #getTestSuite()
	 * @generated
	 */
	EReference getTestSuite_TearDown();

	/**
	 * Returns the meta object for the containment reference list '{@link org.qualitune.jouleunit.android.testrun.TestSuite#getExecutables <em>Executables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Executables</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestSuite#getExecutables()
	 * @see #getTestSuite()
	 * @generated
	 */
	EReference getTestSuite_Executables();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.TestCase <em>Test Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Case</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestCase
	 * @generated
	 */
	EClass getTestCase();

	/**
	 * Returns the meta object for the containment reference '{@link org.qualitune.jouleunit.android.testrun.TestCase#getBehavior <em>Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Behavior</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestCase#getBehavior()
	 * @see #getTestCase()
	 * @generated
	 */
	EReference getTestCase_Behavior();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.JunitTestCase <em>Junit Test Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Junit Test Case</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.JunitTestCase
	 * @generated
	 */
	EClass getJunitTestCase();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.TestBehavior <em>Test Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Behavior</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestBehavior
	 * @generated
	 */
	EClass getTestBehavior();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.Block <em>Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.Block
	 * @generated
	 */
	EClass getBlock();

	/**
	 * Returns the meta object for the containment reference list '{@link org.qualitune.jouleunit.android.testrun.Block#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.Block#getStatements()
	 * @see #getBlock()
	 * @generated
	 */
	EReference getBlock_Statements();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.Statement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.Statement
	 * @generated
	 */
	EClass getStatement();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement <em>Click On Screen Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Click On Screen Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement
	 * @generated
	 */
	EClass getClickOnScreenStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement#getX()
	 * @see #getClickOnScreenStatement()
	 * @generated
	 */
	EAttribute getClickOnScreenStatement_X();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement#getY()
	 * @see #getClickOnScreenStatement()
	 * @generated
	 */
	EAttribute getClickOnScreenStatement_Y();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.CursorStatement <em>Cursor Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cursor Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.CursorStatement
	 * @generated
	 */
	EClass getCursorStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.CursorStatement#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.CursorStatement#getDirection()
	 * @see #getCursorStatement()
	 * @generated
	 */
	EAttribute getCursorStatement_Direction();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.EnterStatement <em>Enter Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enter Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.EnterStatement
	 * @generated
	 */
	EClass getEnterStatement();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.WaitStatement <em>Wait Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wait Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.WaitStatement
	 * @generated
	 */
	EClass getWaitStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.WaitStatement#getSeconds <em>Seconds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Seconds</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.WaitStatement#getSeconds()
	 * @see #getWaitStatement()
	 * @generated
	 */
	EAttribute getWaitStatement_Seconds();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.TestStatement <em>Test Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestStatement
	 * @generated
	 */
	EClass getTestStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.TestStatement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestStatement#getName()
	 * @see #getTestStatement()
	 * @generated
	 */
	EAttribute getTestStatement_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.qualitune.jouleunit.android.testrun.TestStatement#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.TestStatement#getStatements()
	 * @see #getTestStatement()
	 * @generated
	 */
	EReference getTestStatement_Statements();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.DisplayStatement <em>Display Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Display Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.DisplayStatement
	 * @generated
	 */
	EClass getDisplayStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.DisplayStatement#isSwitchOn <em>Switch On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Switch On</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.DisplayStatement#isSwitchOn()
	 * @see #getDisplayStatement()
	 * @generated
	 */
	EAttribute getDisplayStatement_SwitchOn();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.UnlockStatement <em>Unlock Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unlock Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.UnlockStatement
	 * @generated
	 */
	EClass getUnlockStatement();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.OpenSettingsStatement <em>Open Settings Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Open Settings Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.OpenSettingsStatement
	 * @generated
	 */
	EClass getOpenSettingsStatement();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.SendPortMessageStatement <em>Send Port Message Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Send Port Message Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.SendPortMessageStatement
	 * @generated
	 */
	EClass getSendPortMessageStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.SendPortMessageStatement#getIp <em>Ip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ip</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.SendPortMessageStatement#getIp()
	 * @see #getSendPortMessageStatement()
	 * @generated
	 */
	EAttribute getSendPortMessageStatement_Ip();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.SendPortMessageStatement#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.SendPortMessageStatement#getPort()
	 * @see #getSendPortMessageStatement()
	 * @generated
	 */
	EAttribute getSendPortMessageStatement_Port();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.SendPortMessageStatement#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.SendPortMessageStatement#getMessage()
	 * @see #getSendPortMessageStatement()
	 * @generated
	 */
	EAttribute getSendPortMessageStatement_Message();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.StartActivityStatement <em>Start Activity Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Start Activity Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.StartActivityStatement
	 * @generated
	 */
	EClass getStartActivityStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.StartActivityStatement#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.StartActivityStatement#getPackage()
	 * @see #getStartActivityStatement()
	 * @generated
	 */
	EAttribute getStartActivityStatement_Package();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.StartActivityStatement#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.StartActivityStatement#getClass_()
	 * @see #getStartActivityStatement()
	 * @generated
	 */
	EAttribute getStartActivityStatement_Class();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.HomeButtonStatement <em>Home Button Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Home Button Statement</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.HomeButtonStatement
	 * @generated
	 */
	EClass getHomeButtonStatement();

	/**
	 * Returns the meta object for class '{@link org.qualitune.jouleunit.android.testrun.ApkFile <em>Apk File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Apk File</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.ApkFile
	 * @generated
	 */
	EClass getApkFile();

	/**
	 * Returns the meta object for the attribute '{@link org.qualitune.jouleunit.android.testrun.ApkFile#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.ApkFile#getPath()
	 * @see #getApkFile()
	 * @generated
	 */
	EAttribute getApkFile_Path();

	/**
	 * Returns the meta object for enum '{@link org.qualitune.jouleunit.android.testrun.CursorDirection <em>Cursor Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Cursor Direction</em>'.
	 * @see org.qualitune.jouleunit.android.testrun.CursorDirection
	 * @generated
	 */
	EEnum getCursorDirection();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TestrunFactory getTestrunFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl <em>Test Run</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestRunImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestRun()
		 * @generated
		 */
		EClass TEST_RUN = eINSTANCE.getTestRun();
		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_RUN__NAME = eINSTANCE.getTestRun_Name();
		/**
		 * The meta object literal for the '<em><b>Aut</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_RUN__AUT = eINSTANCE.getTestRun_Aut();
		/**
		 * The meta object literal for the '<em><b>Package Under Test</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_RUN__PACKAGE_UNDER_TEST = eINSTANCE.getTestRun_PackageUnderTest();
		/**
		 * The meta object literal for the '<em><b>Junit Apk</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_RUN__JUNIT_APK = eINSTANCE.getTestRun_JunitApk();
		/**
		 * The meta object literal for the '<em><b>Junit Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_RUN__JUNIT_PACKAGE = eINSTANCE.getTestRun_JunitPackage();
		/**
		 * The meta object literal for the '<em><b>No Of Runs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_RUN__NO_OF_RUNS = eINSTANCE.getTestRun_NoOfRuns();
		/**
		 * The meta object literal for the '<em><b>Idle Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_RUN__IDLE_TIME = eINSTANCE.getTestRun_IdleTime();
		/**
		 * The meta object literal for the '<em><b>Hardware Profiling On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_RUN__HARDWARE_PROFILING_ON = eINSTANCE.getTestRun_HardwareProfilingOn();
		/**
		 * The meta object literal for the '<em><b>Remote Run</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_RUN__REMOTE_RUN = eINSTANCE.getTestRun_RemoteRun();
		/**
		 * The meta object literal for the '<em><b>Executables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_RUN__EXECUTABLES = eINSTANCE.getTestRun_Executables();
		/**
		 * The meta object literal for the '<em><b>Wait For Full Battery</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_RUN__WAIT_FOR_FULL_BATTERY = eINSTANCE.getTestRun_WaitForFullBattery();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestExecutableImpl <em>Test Executable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestExecutableImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestExecutable()
		 * @generated
		 */
		EClass TEST_EXECUTABLE = eINSTANCE.getTestExecutable();
		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_EXECUTABLE__NAME = eINSTANCE.getTestExecutable_Name();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestSuiteImpl <em>Test Suite</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestSuiteImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestSuite()
		 * @generated
		 */
		EClass TEST_SUITE = eINSTANCE.getTestSuite();
		/**
		 * The meta object literal for the '<em><b>Set Up</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_SUITE__SET_UP = eINSTANCE.getTestSuite_SetUp();
		/**
		 * The meta object literal for the '<em><b>Tear Down</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_SUITE__TEAR_DOWN = eINSTANCE.getTestSuite_TearDown();
		/**
		 * The meta object literal for the '<em><b>Executables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_SUITE__EXECUTABLES = eINSTANCE.getTestSuite_Executables();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestCaseImpl <em>Test Case</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestCaseImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestCase()
		 * @generated
		 */
		EClass TEST_CASE = eINSTANCE.getTestCase();
		/**
		 * The meta object literal for the '<em><b>Behavior</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_CASE__BEHAVIOR = eINSTANCE.getTestCase_Behavior();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.JunitTestCaseImpl <em>Junit Test Case</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.JunitTestCaseImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getJunitTestCase()
		 * @generated
		 */
		EClass JUNIT_TEST_CASE = eINSTANCE.getJunitTestCase();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestBehaviorImpl <em>Test Behavior</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestBehaviorImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestBehavior()
		 * @generated
		 */
		EClass TEST_BEHAVIOR = eINSTANCE.getTestBehavior();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.BlockImpl <em>Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.BlockImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getBlock()
		 * @generated
		 */
		EClass BLOCK = eINSTANCE.getBlock();
		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK__STATEMENTS = eINSTANCE.getBlock_Statements();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.StatementImpl <em>Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.StatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getStatement()
		 * @generated
		 */
		EClass STATEMENT = eINSTANCE.getStatement();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.ClickOnScreenStatementImpl <em>Click On Screen Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.ClickOnScreenStatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getClickOnScreenStatement()
		 * @generated
		 */
		EClass CLICK_ON_SCREEN_STATEMENT = eINSTANCE.getClickOnScreenStatement();
		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLICK_ON_SCREEN_STATEMENT__X = eINSTANCE.getClickOnScreenStatement_X();
		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLICK_ON_SCREEN_STATEMENT__Y = eINSTANCE.getClickOnScreenStatement_Y();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.CursorStatementImpl <em>Cursor Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.CursorStatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getCursorStatement()
		 * @generated
		 */
		EClass CURSOR_STATEMENT = eINSTANCE.getCursorStatement();
		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CURSOR_STATEMENT__DIRECTION = eINSTANCE.getCursorStatement_Direction();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.EnterStatementImpl <em>Enter Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.EnterStatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getEnterStatement()
		 * @generated
		 */
		EClass ENTER_STATEMENT = eINSTANCE.getEnterStatement();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.WaitStatementImpl <em>Wait Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.WaitStatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getWaitStatement()
		 * @generated
		 */
		EClass WAIT_STATEMENT = eINSTANCE.getWaitStatement();
		/**
		 * The meta object literal for the '<em><b>Seconds</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WAIT_STATEMENT__SECONDS = eINSTANCE.getWaitStatement_Seconds();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.TestStatementImpl <em>Test Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestStatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getTestStatement()
		 * @generated
		 */
		EClass TEST_STATEMENT = eINSTANCE.getTestStatement();
		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_STATEMENT__NAME = eINSTANCE.getTestStatement_Name();
		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_STATEMENT__STATEMENTS = eINSTANCE.getTestStatement_Statements();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.DisplayStatementImpl <em>Display Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.DisplayStatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getDisplayStatement()
		 * @generated
		 */
		EClass DISPLAY_STATEMENT = eINSTANCE.getDisplayStatement();
		/**
		 * The meta object literal for the '<em><b>Switch On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DISPLAY_STATEMENT__SWITCH_ON = eINSTANCE.getDisplayStatement_SwitchOn();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.UnlockStatementImpl <em>Unlock Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.UnlockStatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getUnlockStatement()
		 * @generated
		 */
		EClass UNLOCK_STATEMENT = eINSTANCE.getUnlockStatement();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.OpenSettingsStatementImpl <em>Open Settings Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.OpenSettingsStatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getOpenSettingsStatement()
		 * @generated
		 */
		EClass OPEN_SETTINGS_STATEMENT = eINSTANCE.getOpenSettingsStatement();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.SendPortMessageStatementImpl <em>Send Port Message Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.SendPortMessageStatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getSendPortMessageStatement()
		 * @generated
		 */
		EClass SEND_PORT_MESSAGE_STATEMENT = eINSTANCE.getSendPortMessageStatement();
		/**
		 * The meta object literal for the '<em><b>Ip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEND_PORT_MESSAGE_STATEMENT__IP = eINSTANCE.getSendPortMessageStatement_Ip();
		/**
		 * The meta object literal for the '<em><b>Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEND_PORT_MESSAGE_STATEMENT__PORT = eINSTANCE.getSendPortMessageStatement_Port();
		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEND_PORT_MESSAGE_STATEMENT__MESSAGE = eINSTANCE.getSendPortMessageStatement_Message();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.StartActivityStatementImpl <em>Start Activity Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.StartActivityStatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getStartActivityStatement()
		 * @generated
		 */
		EClass START_ACTIVITY_STATEMENT = eINSTANCE.getStartActivityStatement();
		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute START_ACTIVITY_STATEMENT__PACKAGE = eINSTANCE.getStartActivityStatement_Package();
		/**
		 * The meta object literal for the '<em><b>Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute START_ACTIVITY_STATEMENT__CLASS = eINSTANCE.getStartActivityStatement_Class();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.HomeButtonStatementImpl <em>Home Button Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.HomeButtonStatementImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getHomeButtonStatement()
		 * @generated
		 */
		EClass HOME_BUTTON_STATEMENT = eINSTANCE.getHomeButtonStatement();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.impl.ApkFileImpl <em>Apk File</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.impl.ApkFileImpl
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getApkFile()
		 * @generated
		 */
		EClass APK_FILE = eINSTANCE.getApkFile();
		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APK_FILE__PATH = eINSTANCE.getApkFile_Path();
		/**
		 * The meta object literal for the '{@link org.qualitune.jouleunit.android.testrun.CursorDirection <em>Cursor Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.qualitune.jouleunit.android.testrun.CursorDirection
		 * @see org.qualitune.jouleunit.android.testrun.impl.TestrunPackageImpl#getCursorDirection()
		 * @generated
		 */
		EEnum CURSOR_DIRECTION = eINSTANCE.getCursorDirection();

	}

} //TestrunPackage
