/**
 */
package org.qualitune.jouleunit.android.testrun.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.qualitune.jouleunit.android.testrun.TestBehavior;
import org.qualitune.jouleunit.android.testrun.TestExecutable;
import org.qualitune.jouleunit.android.testrun.TestSuite;
import org.qualitune.jouleunit.android.testrun.TestrunPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test Suite</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestSuiteImpl#getSetUp <em>Set Up</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestSuiteImpl#getTearDown <em>Tear Down</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestSuiteImpl#getExecutables <em>Executables</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TestSuiteImpl extends TestExecutableImpl implements TestSuite {
	/**
	 * The cached value of the '{@link #getSetUp() <em>Set Up</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSetUp()
	 * @generated
	 * @ordered
	 */
	protected TestBehavior setUp;
	/**
	 * The cached value of the '{@link #getTearDown() <em>Tear Down</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTearDown()
	 * @generated
	 * @ordered
	 */
	protected TestBehavior tearDown;
	/**
	 * The cached value of the '{@link #getExecutables() <em>Executables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecutables()
	 * @generated
	 * @ordered
	 */
	protected EList<TestExecutable> executables;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestSuiteImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestrunPackage.Literals.TEST_SUITE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestBehavior getSetUp() {
		return setUp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSetUp(TestBehavior newSetUp, NotificationChain msgs) {
		TestBehavior oldSetUp = setUp;
		setUp = newSetUp;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_SUITE__SET_UP, oldSetUp, newSetUp);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSetUp(TestBehavior newSetUp) {
		if (newSetUp != setUp) {
			NotificationChain msgs = null;
			if (setUp != null)
				msgs = ((InternalEObject)setUp).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TestrunPackage.TEST_SUITE__SET_UP, null, msgs);
			if (newSetUp != null)
				msgs = ((InternalEObject)newSetUp).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TestrunPackage.TEST_SUITE__SET_UP, null, msgs);
			msgs = basicSetSetUp(newSetUp, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_SUITE__SET_UP, newSetUp, newSetUp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestBehavior getTearDown() {
		return tearDown;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTearDown(TestBehavior newTearDown, NotificationChain msgs) {
		TestBehavior oldTearDown = tearDown;
		tearDown = newTearDown;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_SUITE__TEAR_DOWN, oldTearDown, newTearDown);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTearDown(TestBehavior newTearDown) {
		if (newTearDown != tearDown) {
			NotificationChain msgs = null;
			if (tearDown != null)
				msgs = ((InternalEObject)tearDown).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TestrunPackage.TEST_SUITE__TEAR_DOWN, null, msgs);
			if (newTearDown != null)
				msgs = ((InternalEObject)newTearDown).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TestrunPackage.TEST_SUITE__TEAR_DOWN, null, msgs);
			msgs = basicSetTearDown(newTearDown, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_SUITE__TEAR_DOWN, newTearDown, newTearDown));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TestExecutable> getExecutables() {
		if (executables == null) {
			executables = new EObjectContainmentEList<TestExecutable>(TestExecutable.class, this, TestrunPackage.TEST_SUITE__EXECUTABLES);
		}
		return executables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TestrunPackage.TEST_SUITE__SET_UP:
				return basicSetSetUp(null, msgs);
			case TestrunPackage.TEST_SUITE__TEAR_DOWN:
				return basicSetTearDown(null, msgs);
			case TestrunPackage.TEST_SUITE__EXECUTABLES:
				return ((InternalEList<?>)getExecutables()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TestrunPackage.TEST_SUITE__SET_UP:
				return getSetUp();
			case TestrunPackage.TEST_SUITE__TEAR_DOWN:
				return getTearDown();
			case TestrunPackage.TEST_SUITE__EXECUTABLES:
				return getExecutables();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TestrunPackage.TEST_SUITE__SET_UP:
				setSetUp((TestBehavior)newValue);
				return;
			case TestrunPackage.TEST_SUITE__TEAR_DOWN:
				setTearDown((TestBehavior)newValue);
				return;
			case TestrunPackage.TEST_SUITE__EXECUTABLES:
				getExecutables().clear();
				getExecutables().addAll((Collection<? extends TestExecutable>)newValue);
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
			case TestrunPackage.TEST_SUITE__SET_UP:
				setSetUp((TestBehavior)null);
				return;
			case TestrunPackage.TEST_SUITE__TEAR_DOWN:
				setTearDown((TestBehavior)null);
				return;
			case TestrunPackage.TEST_SUITE__EXECUTABLES:
				getExecutables().clear();
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
			case TestrunPackage.TEST_SUITE__SET_UP:
				return setUp != null;
			case TestrunPackage.TEST_SUITE__TEAR_DOWN:
				return tearDown != null;
			case TestrunPackage.TEST_SUITE__EXECUTABLES:
				return executables != null && !executables.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TestSuiteImpl
