/**
 */
package org.qualitune.jouleunit.android.testrun.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.qualitune.jouleunit.android.testrun.DisplayStatement;
import org.qualitune.jouleunit.android.testrun.TestrunPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Display Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.DisplayStatementImpl#isSwitchOn <em>Switch On</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DisplayStatementImpl extends StatementImpl implements DisplayStatement {
	/**
	 * The default value of the '{@link #isSwitchOn() <em>Switch On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSwitchOn()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SWITCH_ON_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSwitchOn() <em>Switch On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSwitchOn()
	 * @generated
	 * @ordered
	 */
	protected boolean switchOn = SWITCH_ON_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DisplayStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestrunPackage.Literals.DISPLAY_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSwitchOn() {
		return switchOn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSwitchOn(boolean newSwitchOn) {
		boolean oldSwitchOn = switchOn;
		switchOn = newSwitchOn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.DISPLAY_STATEMENT__SWITCH_ON, oldSwitchOn, switchOn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TestrunPackage.DISPLAY_STATEMENT__SWITCH_ON:
				return isSwitchOn();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TestrunPackage.DISPLAY_STATEMENT__SWITCH_ON:
				setSwitchOn((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TestrunPackage.DISPLAY_STATEMENT__SWITCH_ON:
				setSwitchOn(SWITCH_ON_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TestrunPackage.DISPLAY_STATEMENT__SWITCH_ON:
				return switchOn != SWITCH_ON_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (switchOn: ");
		result.append(switchOn);
		result.append(')');
		return result.toString();
	}

} //DisplayStatementImpl
