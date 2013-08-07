/**
 */
package org.qualitune.jouleunit.android.testrun;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Wait Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.WaitStatement#getSeconds <em>Seconds</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getWaitStatement()
 * @model
 * @generated
 */
public interface WaitStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>Seconds</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Seconds</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Seconds</em>' attribute.
	 * @see #setSeconds(int)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getWaitStatement_Seconds()
	 * @model required="true"
	 * @generated
	 */
	int getSeconds();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.WaitStatement#getSeconds <em>Seconds</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Seconds</em>' attribute.
	 * @see #getSeconds()
	 * @generated
	 */
	void setSeconds(int value);

} // WaitStatement
