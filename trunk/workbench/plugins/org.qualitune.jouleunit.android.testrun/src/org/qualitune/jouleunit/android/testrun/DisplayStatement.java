/**
 */
package org.qualitune.jouleunit.android.testrun;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Display Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.DisplayStatement#isSwitchOn <em>Switch On</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getDisplayStatement()
 * @model
 * @generated
 */
public interface DisplayStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>Switch On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Switch On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Switch On</em>' attribute.
	 * @see #setSwitchOn(boolean)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getDisplayStatement_SwitchOn()
	 * @model required="true"
	 * @generated
	 */
	boolean isSwitchOn();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.DisplayStatement#isSwitchOn <em>Switch On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Switch On</em>' attribute.
	 * @see #isSwitchOn()
	 * @generated
	 */
	void setSwitchOn(boolean value);

} // DisplayStatement
