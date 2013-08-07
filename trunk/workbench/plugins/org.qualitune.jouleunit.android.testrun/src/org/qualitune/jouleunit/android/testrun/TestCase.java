/**
 */
package org.qualitune.jouleunit.android.testrun;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Case</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestCase#getBehavior <em>Behavior</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestCase()
 * @model
 * @generated
 */
public interface TestCase extends TestExecutable {

	/**
	 * Returns the value of the '<em><b>Behavior</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Behavior</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behavior</em>' containment reference.
	 * @see #setBehavior(TestBehavior)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestCase_Behavior()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TestBehavior getBehavior();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestCase#getBehavior <em>Behavior</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behavior</em>' containment reference.
	 * @see #getBehavior()
	 * @generated
	 */
	void setBehavior(TestBehavior value);
} // TestCase
