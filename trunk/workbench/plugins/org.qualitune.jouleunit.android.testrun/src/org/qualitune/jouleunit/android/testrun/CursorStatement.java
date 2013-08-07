/**
 */
package org.qualitune.jouleunit.android.testrun;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cursor Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.CursorStatement#getDirection <em>Direction</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getCursorStatement()
 * @model
 * @generated
 */
public interface CursorStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link org.qualitune.jouleunit.android.testrun.CursorDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see org.qualitune.jouleunit.android.testrun.CursorDirection
	 * @see #setDirection(CursorDirection)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getCursorStatement_Direction()
	 * @model required="true"
	 * @generated
	 */
	CursorDirection getDirection();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.CursorStatement#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see org.qualitune.jouleunit.android.testrun.CursorDirection
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(CursorDirection value);

} // CursorStatement
