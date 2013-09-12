/**
 */
package org.qualitune.jouleunit.android.testrun.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage
 * @generated
 */
public class TestrunAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TestrunPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestrunAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TestrunPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestrunSwitch<Adapter> modelSwitch =
		new TestrunSwitch<Adapter>() {
			@Override
			public Adapter caseTestRun(TestRun object) {
				return createTestRunAdapter();
			}
			@Override
			public Adapter caseTestExecutable(TestExecutable object) {
				return createTestExecutableAdapter();
			}
			@Override
			public Adapter caseTestSuite(TestSuite object) {
				return createTestSuiteAdapter();
			}
			@Override
			public Adapter caseTestCase(TestCase object) {
				return createTestCaseAdapter();
			}
			@Override
			public Adapter caseJunitTestCase(JunitTestCase object) {
				return createJunitTestCaseAdapter();
			}
			@Override
			public Adapter caseTestBehavior(TestBehavior object) {
				return createTestBehaviorAdapter();
			}
			@Override
			public Adapter caseBlock(Block object) {
				return createBlockAdapter();
			}
			@Override
			public Adapter caseStatement(Statement object) {
				return createStatementAdapter();
			}
			@Override
			public Adapter caseClickOnScreenStatement(ClickOnScreenStatement object) {
				return createClickOnScreenStatementAdapter();
			}
			@Override
			public Adapter caseCursorStatement(CursorStatement object) {
				return createCursorStatementAdapter();
			}
			@Override
			public Adapter caseEnterStatement(EnterStatement object) {
				return createEnterStatementAdapter();
			}
			@Override
			public Adapter caseDisplayStatement(DisplayStatement object) {
				return createDisplayStatementAdapter();
			}
			@Override
			public Adapter caseHomeButtonStatement(HomeButtonStatement object) {
				return createHomeButtonStatementAdapter();
			}
			@Override
			public Adapter caseOpenSettingsStatement(OpenSettingsStatement object) {
				return createOpenSettingsStatementAdapter();
			}
			@Override
			public Adapter caseSendPortMessageStatement(SendPortMessageStatement object) {
				return createSendPortMessageStatementAdapter();
			}
			@Override
			public Adapter caseStartActivityStatement(StartActivityStatement object) {
				return createStartActivityStatementAdapter();
			}
			@Override
			public Adapter caseTestStatement(TestStatement object) {
				return createTestStatementAdapter();
			}
			@Override
			public Adapter caseUnlockStatement(UnlockStatement object) {
				return createUnlockStatementAdapter();
			}
			@Override
			public Adapter caseWaitStatement(WaitStatement object) {
				return createWaitStatementAdapter();
			}
			@Override
			public Adapter caseApkFile(ApkFile object) {
				return createApkFileAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.TestRun <em>Test Run</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.TestRun
	 * @generated
	 */
	public Adapter createTestRunAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.TestExecutable <em>Test Executable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.TestExecutable
	 * @generated
	 */
	public Adapter createTestExecutableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.TestSuite <em>Test Suite</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.TestSuite
	 * @generated
	 */
	public Adapter createTestSuiteAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.TestCase <em>Test Case</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.TestCase
	 * @generated
	 */
	public Adapter createTestCaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.JunitTestCase <em>Junit Test Case</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.JunitTestCase
	 * @generated
	 */
	public Adapter createJunitTestCaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.TestBehavior <em>Test Behavior</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.TestBehavior
	 * @generated
	 */
	public Adapter createTestBehaviorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.Block <em>Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.Block
	 * @generated
	 */
	public Adapter createBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.Statement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.Statement
	 * @generated
	 */
	public Adapter createStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement <em>Click On Screen Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement
	 * @generated
	 */
	public Adapter createClickOnScreenStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.CursorStatement <em>Cursor Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.CursorStatement
	 * @generated
	 */
	public Adapter createCursorStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.EnterStatement <em>Enter Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.EnterStatement
	 * @generated
	 */
	public Adapter createEnterStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.WaitStatement <em>Wait Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.WaitStatement
	 * @generated
	 */
	public Adapter createWaitStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.TestStatement <em>Test Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.TestStatement
	 * @generated
	 */
	public Adapter createTestStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.DisplayStatement <em>Display Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.DisplayStatement
	 * @generated
	 */
	public Adapter createDisplayStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.UnlockStatement <em>Unlock Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.UnlockStatement
	 * @generated
	 */
	public Adapter createUnlockStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.OpenSettingsStatement <em>Open Settings Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.OpenSettingsStatement
	 * @generated
	 */
	public Adapter createOpenSettingsStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.SendPortMessageStatement <em>Send Port Message Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.SendPortMessageStatement
	 * @generated
	 */
	public Adapter createSendPortMessageStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.StartActivityStatement <em>Start Activity Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.StartActivityStatement
	 * @generated
	 */
	public Adapter createStartActivityStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.HomeButtonStatement <em>Home Button Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.HomeButtonStatement
	 * @generated
	 */
	public Adapter createHomeButtonStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.qualitune.jouleunit.android.testrun.ApkFile <em>Apk File</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.qualitune.jouleunit.android.testrun.ApkFile
	 * @generated
	 */
	public Adapter createApkFileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //TestrunAdapterFactory
