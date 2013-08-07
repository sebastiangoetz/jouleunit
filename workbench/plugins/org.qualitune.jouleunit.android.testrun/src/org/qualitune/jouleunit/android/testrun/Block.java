/**
 */
package org.qualitune.jouleunit.android.testrun;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Block</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.Block#getStatements <em>Statements</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getBlock()
 * @model
 * @generated
 */
public interface Block extends TestBehavior {

	/**
	 * Returns the value of the '<em><b>Statements</b></em>' containment reference list.
	 * The list contents are of type {@link org.qualitune.jouleunit.android.testrun.Statement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Statements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statements</em>' containment reference list.
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getBlock_Statements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Statement> getStatements();
} // Block
