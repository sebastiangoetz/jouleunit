/**
 */
package org.qualitune.jouleunit.android.testrun;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Start Activity Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.StartActivityStatement#getPackage <em>Package</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.StartActivityStatement#getClass_ <em>Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getStartActivityStatement()
 * @model
 * @generated
 */
public interface StartActivityStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' attribute.
	 * @see #setPackage(String)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getStartActivityStatement_Package()
	 * @model required="true"
	 * @generated
	 */
	String getPackage();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.StartActivityStatement#getPackage <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' attribute.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(String value);

	/**
	 * Returns the value of the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class</em>' attribute.
	 * @see #setClass(String)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getStartActivityStatement_Class()
	 * @model required="true"
	 * @generated
	 */
	String getClass_();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.StartActivityStatement#getClass_ <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class</em>' attribute.
	 * @see #getClass_()
	 * @generated
	 */
	void setClass(String value);

} // StartActivityStatement
