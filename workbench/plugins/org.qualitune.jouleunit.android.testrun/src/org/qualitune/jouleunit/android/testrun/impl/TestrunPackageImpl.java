/**
 */
package org.qualitune.jouleunit.android.testrun.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.qualitune.jouleunit.android.testrun.ApkFile;
import org.qualitune.jouleunit.android.testrun.Block;
import org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement;
import org.qualitune.jouleunit.android.testrun.CursorDirection;
import org.qualitune.jouleunit.android.testrun.CursorStatement;
import org.qualitune.jouleunit.android.testrun.DisplayStatement;
import org.qualitune.jouleunit.android.testrun.EnterStatement;
import org.qualitune.jouleunit.android.testrun.HomeButtonStatement;
import org.qualitune.jouleunit.android.testrun.JunitTestCase;
import org.qualitune.jouleunit.android.testrun.OpenSettingsStatement;
import org.qualitune.jouleunit.android.testrun.SendPortMessageStatement;
import org.qualitune.jouleunit.android.testrun.StartActivityStatement;
import org.qualitune.jouleunit.android.testrun.Statement;
import org.qualitune.jouleunit.android.testrun.TestBehavior;
import org.qualitune.jouleunit.android.testrun.TestCase;
import org.qualitune.jouleunit.android.testrun.TestExecutable;
import org.qualitune.jouleunit.android.testrun.TestRun;
import org.qualitune.jouleunit.android.testrun.TestStatement;
import org.qualitune.jouleunit.android.testrun.TestSuite;
import org.qualitune.jouleunit.android.testrun.TestrunFactory;
import org.qualitune.jouleunit.android.testrun.TestrunPackage;
import org.qualitune.jouleunit.android.testrun.UnlockStatement;
import org.qualitune.jouleunit.android.testrun.WaitStatement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TestrunPackageImpl extends EPackageImpl implements TestrunPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testRunEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testExecutableEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testSuiteEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testCaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass junitTestCaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testBehaviorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass clickOnScreenStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cursorStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enterStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass waitStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass displayStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unlockStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass openSettingsStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sendPortMessageStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass startActivityStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass homeButtonStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass apkFileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum cursorDirectionEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TestrunPackageImpl() {
		super(eNS_URI, TestrunFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link TestrunPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TestrunPackage init() {
		if (isInited) return (TestrunPackage)EPackage.Registry.INSTANCE.getEPackage(TestrunPackage.eNS_URI);

		// Obtain or create and register package
		TestrunPackageImpl theTestrunPackage = (TestrunPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TestrunPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TestrunPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theTestrunPackage.createPackageContents();

		// Initialize created meta-data
		theTestrunPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTestrunPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TestrunPackage.eNS_URI, theTestrunPackage);
		return theTestrunPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTestRun() {
		return testRunEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestRun_Name() {
		return (EAttribute)testRunEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestRun_Aut() {
		return (EReference)testRunEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestRun_PackageUnderTest() {
		return (EAttribute)testRunEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestRun_JunitApk() {
		return (EReference)testRunEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestRun_JunitPackage() {
		return (EAttribute)testRunEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestRun_NoOfRuns() {
		return (EAttribute)testRunEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestRun_IdleTime() {
		return (EAttribute)testRunEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestRun_HardwareProfilingOn() {
		return (EAttribute)testRunEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestRun_RemoteRun() {
		return (EAttribute)testRunEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestRun_Executables() {
		return (EReference)testRunEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestRun_WaitForFullBattery() {
		return (EAttribute)testRunEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTestExecutable() {
		return testExecutableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestExecutable_Name() {
		return (EAttribute)testExecutableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTestSuite() {
		return testSuiteEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestSuite_SetUp() {
		return (EReference)testSuiteEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestSuite_TearDown() {
		return (EReference)testSuiteEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestSuite_Executables() {
		return (EReference)testSuiteEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTestCase() {
		return testCaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestCase_Behavior() {
		return (EReference)testCaseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJunitTestCase() {
		return junitTestCaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTestBehavior() {
		return testBehaviorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBlock() {
		return blockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBlock_Statements() {
		return (EReference)blockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatement() {
		return statementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClickOnScreenStatement() {
		return clickOnScreenStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClickOnScreenStatement_X() {
		return (EAttribute)clickOnScreenStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClickOnScreenStatement_Y() {
		return (EAttribute)clickOnScreenStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCursorStatement() {
		return cursorStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCursorStatement_Direction() {
		return (EAttribute)cursorStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnterStatement() {
		return enterStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWaitStatement() {
		return waitStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWaitStatement_Seconds() {
		return (EAttribute)waitStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTestStatement() {
		return testStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTestStatement_Name() {
		return (EAttribute)testStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestStatement_Statements() {
		return (EReference)testStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDisplayStatement() {
		return displayStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDisplayStatement_SwitchOn() {
		return (EAttribute)displayStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUnlockStatement() {
		return unlockStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOpenSettingsStatement() {
		return openSettingsStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSendPortMessageStatement() {
		return sendPortMessageStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSendPortMessageStatement_Ip() {
		return (EAttribute)sendPortMessageStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSendPortMessageStatement_Port() {
		return (EAttribute)sendPortMessageStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSendPortMessageStatement_Message() {
		return (EAttribute)sendPortMessageStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStartActivityStatement() {
		return startActivityStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStartActivityStatement_Package() {
		return (EAttribute)startActivityStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStartActivityStatement_Class() {
		return (EAttribute)startActivityStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHomeButtonStatement() {
		return homeButtonStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApkFile() {
		return apkFileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApkFile_Path() {
		return (EAttribute)apkFileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCursorDirection() {
		return cursorDirectionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestrunFactory getTestrunFactory() {
		return (TestrunFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		testRunEClass = createEClass(TEST_RUN);
		createEAttribute(testRunEClass, TEST_RUN__NAME);
		createEReference(testRunEClass, TEST_RUN__AUT);
		createEAttribute(testRunEClass, TEST_RUN__PACKAGE_UNDER_TEST);
		createEReference(testRunEClass, TEST_RUN__JUNIT_APK);
		createEAttribute(testRunEClass, TEST_RUN__JUNIT_PACKAGE);
		createEAttribute(testRunEClass, TEST_RUN__NO_OF_RUNS);
		createEAttribute(testRunEClass, TEST_RUN__IDLE_TIME);
		createEAttribute(testRunEClass, TEST_RUN__HARDWARE_PROFILING_ON);
		createEAttribute(testRunEClass, TEST_RUN__REMOTE_RUN);
		createEReference(testRunEClass, TEST_RUN__EXECUTABLES);
		createEAttribute(testRunEClass, TEST_RUN__WAIT_FOR_FULL_BATTERY);

		testExecutableEClass = createEClass(TEST_EXECUTABLE);
		createEAttribute(testExecutableEClass, TEST_EXECUTABLE__NAME);

		testSuiteEClass = createEClass(TEST_SUITE);
		createEReference(testSuiteEClass, TEST_SUITE__SET_UP);
		createEReference(testSuiteEClass, TEST_SUITE__TEAR_DOWN);
		createEReference(testSuiteEClass, TEST_SUITE__EXECUTABLES);

		testCaseEClass = createEClass(TEST_CASE);
		createEReference(testCaseEClass, TEST_CASE__BEHAVIOR);

		junitTestCaseEClass = createEClass(JUNIT_TEST_CASE);

		testBehaviorEClass = createEClass(TEST_BEHAVIOR);

		blockEClass = createEClass(BLOCK);
		createEReference(blockEClass, BLOCK__STATEMENTS);

		statementEClass = createEClass(STATEMENT);

		clickOnScreenStatementEClass = createEClass(CLICK_ON_SCREEN_STATEMENT);
		createEAttribute(clickOnScreenStatementEClass, CLICK_ON_SCREEN_STATEMENT__X);
		createEAttribute(clickOnScreenStatementEClass, CLICK_ON_SCREEN_STATEMENT__Y);

		cursorStatementEClass = createEClass(CURSOR_STATEMENT);
		createEAttribute(cursorStatementEClass, CURSOR_STATEMENT__DIRECTION);

		enterStatementEClass = createEClass(ENTER_STATEMENT);

		displayStatementEClass = createEClass(DISPLAY_STATEMENT);
		createEAttribute(displayStatementEClass, DISPLAY_STATEMENT__SWITCH_ON);

		homeButtonStatementEClass = createEClass(HOME_BUTTON_STATEMENT);

		openSettingsStatementEClass = createEClass(OPEN_SETTINGS_STATEMENT);

		sendPortMessageStatementEClass = createEClass(SEND_PORT_MESSAGE_STATEMENT);
		createEAttribute(sendPortMessageStatementEClass, SEND_PORT_MESSAGE_STATEMENT__IP);
		createEAttribute(sendPortMessageStatementEClass, SEND_PORT_MESSAGE_STATEMENT__PORT);
		createEAttribute(sendPortMessageStatementEClass, SEND_PORT_MESSAGE_STATEMENT__MESSAGE);

		startActivityStatementEClass = createEClass(START_ACTIVITY_STATEMENT);
		createEAttribute(startActivityStatementEClass, START_ACTIVITY_STATEMENT__PACKAGE);
		createEAttribute(startActivityStatementEClass, START_ACTIVITY_STATEMENT__CLASS);

		testStatementEClass = createEClass(TEST_STATEMENT);
		createEAttribute(testStatementEClass, TEST_STATEMENT__NAME);
		createEReference(testStatementEClass, TEST_STATEMENT__STATEMENTS);

		unlockStatementEClass = createEClass(UNLOCK_STATEMENT);

		waitStatementEClass = createEClass(WAIT_STATEMENT);
		createEAttribute(waitStatementEClass, WAIT_STATEMENT__SECONDS);

		apkFileEClass = createEClass(APK_FILE);
		createEAttribute(apkFileEClass, APK_FILE__PATH);

		// Create enums
		cursorDirectionEEnum = createEEnum(CURSOR_DIRECTION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		testSuiteEClass.getESuperTypes().add(this.getTestExecutable());
		testCaseEClass.getESuperTypes().add(this.getTestExecutable());
		junitTestCaseEClass.getESuperTypes().add(this.getTestExecutable());
		blockEClass.getESuperTypes().add(this.getTestBehavior());
		clickOnScreenStatementEClass.getESuperTypes().add(this.getStatement());
		cursorStatementEClass.getESuperTypes().add(this.getStatement());
		enterStatementEClass.getESuperTypes().add(this.getStatement());
		displayStatementEClass.getESuperTypes().add(this.getStatement());
		homeButtonStatementEClass.getESuperTypes().add(this.getStatement());
		openSettingsStatementEClass.getESuperTypes().add(this.getStatement());
		sendPortMessageStatementEClass.getESuperTypes().add(this.getStatement());
		startActivityStatementEClass.getESuperTypes().add(this.getStatement());
		testStatementEClass.getESuperTypes().add(this.getStatement());
		unlockStatementEClass.getESuperTypes().add(this.getStatement());
		waitStatementEClass.getESuperTypes().add(this.getStatement());

		// Initialize classes and features; add operations and parameters
		initEClass(testRunEClass, TestRun.class, "TestRun", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestRun_Name(), ecorePackage.getEString(), "name", null, 1, 1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestRun_Aut(), this.getApkFile(), null, "aut", null, 0, 1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestRun_PackageUnderTest(), ecorePackage.getEString(), "packageUnderTest", null, 0, 1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestRun_JunitApk(), this.getApkFile(), null, "junitApk", null, 0, 1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestRun_JunitPackage(), ecorePackage.getEString(), "junitPackage", null, 0, 1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestRun_NoOfRuns(), ecorePackage.getEInt(), "noOfRuns", "1", 0, 1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestRun_IdleTime(), ecorePackage.getEInt(), "idleTime", "0", 0, 1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestRun_HardwareProfilingOn(), ecorePackage.getEBoolean(), "hardwareProfilingOn", "true", 0, 1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestRun_RemoteRun(), ecorePackage.getEBoolean(), "remoteRun", "false", 0, 1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestRun_Executables(), this.getTestExecutable(), null, "executables", null, 1, -1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestRun_WaitForFullBattery(), ecorePackage.getEBoolean(), "waitForFullBattery", "false", 0, 1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testExecutableEClass, TestExecutable.class, "TestExecutable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestExecutable_Name(), ecorePackage.getEString(), "name", null, 1, 1, TestExecutable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testSuiteEClass, TestSuite.class, "TestSuite", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTestSuite_SetUp(), this.getTestBehavior(), null, "setUp", null, 0, 1, TestSuite.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestSuite_TearDown(), this.getTestBehavior(), null, "tearDown", null, 0, 1, TestSuite.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestSuite_Executables(), this.getTestExecutable(), null, "executables", null, 1, -1, TestSuite.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testCaseEClass, TestCase.class, "TestCase", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTestCase_Behavior(), this.getTestBehavior(), null, "behavior", null, 1, 1, TestCase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(junitTestCaseEClass, JunitTestCase.class, "JunitTestCase", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(testBehaviorEClass, TestBehavior.class, "TestBehavior", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(blockEClass, Block.class, "Block", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlock_Statements(), this.getStatement(), null, "statements", null, 0, -1, Block.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(statementEClass, Statement.class, "Statement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(clickOnScreenStatementEClass, ClickOnScreenStatement.class, "ClickOnScreenStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getClickOnScreenStatement_X(), ecorePackage.getEInt(), "x", null, 1, 1, ClickOnScreenStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getClickOnScreenStatement_Y(), ecorePackage.getEInt(), "y", null, 1, 1, ClickOnScreenStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cursorStatementEClass, CursorStatement.class, "CursorStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCursorStatement_Direction(), this.getCursorDirection(), "direction", null, 1, 1, CursorStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enterStatementEClass, EnterStatement.class, "EnterStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(displayStatementEClass, DisplayStatement.class, "DisplayStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDisplayStatement_SwitchOn(), ecorePackage.getEBoolean(), "switchOn", null, 1, 1, DisplayStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(homeButtonStatementEClass, HomeButtonStatement.class, "HomeButtonStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(openSettingsStatementEClass, OpenSettingsStatement.class, "OpenSettingsStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(sendPortMessageStatementEClass, SendPortMessageStatement.class, "SendPortMessageStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSendPortMessageStatement_Ip(), ecorePackage.getEString(), "ip", null, 1, 1, SendPortMessageStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSendPortMessageStatement_Port(), ecorePackage.getEInt(), "port", null, 1, 1, SendPortMessageStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSendPortMessageStatement_Message(), ecorePackage.getEString(), "message", null, 1, 1, SendPortMessageStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(startActivityStatementEClass, StartActivityStatement.class, "StartActivityStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStartActivityStatement_Package(), ecorePackage.getEString(), "package", null, 1, 1, StartActivityStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStartActivityStatement_Class(), ecorePackage.getEString(), "class", null, 1, 1, StartActivityStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testStatementEClass, TestStatement.class, "TestStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestStatement_Name(), ecorePackage.getEString(), "name", null, 1, 1, TestStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestStatement_Statements(), this.getStatement(), null, "statements", null, 1, -1, TestStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(unlockStatementEClass, UnlockStatement.class, "UnlockStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(waitStatementEClass, WaitStatement.class, "WaitStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWaitStatement_Seconds(), ecorePackage.getEInt(), "seconds", null, 1, 1, WaitStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(apkFileEClass, ApkFile.class, "ApkFile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getApkFile_Path(), ecorePackage.getEString(), "path", null, 1, 1, ApkFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(cursorDirectionEEnum, CursorDirection.class, "CursorDirection");
		addEEnumLiteral(cursorDirectionEEnum, CursorDirection.UP);
		addEEnumLiteral(cursorDirectionEEnum, CursorDirection.DOWN);
		addEEnumLiteral(cursorDirectionEEnum, CursorDirection.LEFT);
		addEEnumLiteral(cursorDirectionEEnum, CursorDirection.RIGHT);

		// Create resource
		createResource(eNS_URI);
	}

} //TestrunPackageImpl
