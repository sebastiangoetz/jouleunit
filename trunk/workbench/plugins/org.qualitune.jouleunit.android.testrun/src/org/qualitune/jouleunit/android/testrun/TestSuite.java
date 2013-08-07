/**
 */
package org.qualitune.jouleunit.android.testrun;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Suite</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestSuite#getSetUp <em>Set Up</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestSuite#getTearDown <em>Tear Down</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestSuite#getExecutables <em>Executables</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestSuite()
 * @model
 * @generated
 */
public interface TestSuite extends TestExecutable {
	/**
	 * Returns the value of the '<em><b>Set Up</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Set Up</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Set Up</em>' containment reference.
	 * @see #setSetUp(TestBehavior)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestSuite_SetUp()
	 * @model containment="true"
	 * @generated
	 */
	TestBehavior getSetUp();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestSuite#getSetUp <em>Set Up</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Set Up</em>' containment reference.
	 * @see #getSetUp()
	 * @generated
	 */
	void setSetUp(TestBehavior value);

	/**
	 * Returns the value of the '<em><b>Tear Down</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tear Down</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tear Down</em>' containment reference.
	 * @see #setTearDown(TestBehavior)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestSuite_TearDown()
	 * @model containment="true"
	 * @generated
	 */
	TestBehavior getTearDown();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestSuite#getTearDown <em>Tear Down</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tear Down</em>' containment reference.
	 * @see #getTearDown()
	 * @generated
	 */
	void setTearDown(TestBehavior value);

	/**
	 * Returns the value of the '<em><b>Executables</b></em>' containment reference list.
	 * The list contents are of type {@link org.qualitune.jouleunit.android.testrun.TestExecutable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Executables</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Executables</em>' containment reference list.
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestSuite_Executables()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<TestExecutable> getExecutables();

} // TestSuite
