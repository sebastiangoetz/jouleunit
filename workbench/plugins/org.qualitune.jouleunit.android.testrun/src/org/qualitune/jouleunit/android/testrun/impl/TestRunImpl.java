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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.qualitune.jouleunit.android.testrun.ApkFile;
import org.qualitune.jouleunit.android.testrun.TestExecutable;
import org.qualitune.jouleunit.android.testrun.TestRun;
import org.qualitune.jouleunit.android.testrun.TestrunPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test Run</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl#getAut <em>Aut</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl#getPackageUnderTest <em>Package Under Test</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl#getJunitApk <em>Junit Apk</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl#getJunitPackage <em>Junit Package</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl#getNoOfRuns <em>No Of Runs</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl#getIdleTime <em>Idle Time</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl#isHardwareProfilingOn <em>Hardware Profiling On</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl#isRemoteRun <em>Remote Run</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl#getExecutables <em>Executables</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.impl.TestRunImpl#isWaitForFullBattery <em>Wait For Full Battery</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TestRunImpl extends EObjectImpl implements TestRun {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAut() <em>Aut</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAut()
	 * @generated
	 * @ordered
	 */
	protected ApkFile aut;

	/**
	 * The default value of the '{@link #getPackageUnderTest() <em>Package Under Test</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageUnderTest()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_UNDER_TEST_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPackageUnderTest() <em>Package Under Test</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageUnderTest()
	 * @generated
	 * @ordered
	 */
	protected String packageUnderTest = PACKAGE_UNDER_TEST_EDEFAULT;
	/**
	 * The cached value of the '{@link #getJunitApk() <em>Junit Apk</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJunitApk()
	 * @generated
	 * @ordered
	 */
	protected ApkFile junitApk;
	/**
	 * The default value of the '{@link #getJunitPackage() <em>Junit Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJunitPackage()
	 * @generated
	 * @ordered
	 */
	protected static final String JUNIT_PACKAGE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getJunitPackage() <em>Junit Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJunitPackage()
	 * @generated
	 * @ordered
	 */
	protected String junitPackage = JUNIT_PACKAGE_EDEFAULT;
	/**
	 * The default value of the '{@link #getNoOfRuns() <em>No Of Runs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNoOfRuns()
	 * @generated
	 * @ordered
	 */
	protected static final int NO_OF_RUNS_EDEFAULT = 1;
	/**
	 * The cached value of the '{@link #getNoOfRuns() <em>No Of Runs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNoOfRuns()
	 * @generated
	 * @ordered
	 */
	protected int noOfRuns = NO_OF_RUNS_EDEFAULT;
	/**
	 * The default value of the '{@link #getIdleTime() <em>Idle Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdleTime()
	 * @generated
	 * @ordered
	 */
	protected static final int IDLE_TIME_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getIdleTime() <em>Idle Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdleTime()
	 * @generated
	 * @ordered
	 */
	protected int idleTime = IDLE_TIME_EDEFAULT;
	/**
	 * The default value of the '{@link #isHardwareProfilingOn() <em>Hardware Profiling On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHardwareProfilingOn()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HARDWARE_PROFILING_ON_EDEFAULT = true;
	/**
	 * The cached value of the '{@link #isHardwareProfilingOn() <em>Hardware Profiling On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHardwareProfilingOn()
	 * @generated
	 * @ordered
	 */
	protected boolean hardwareProfilingOn = HARDWARE_PROFILING_ON_EDEFAULT;
	/**
	 * The default value of the '{@link #isRemoteRun() <em>Remote Run</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRemoteRun()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REMOTE_RUN_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isRemoteRun() <em>Remote Run</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRemoteRun()
	 * @generated
	 * @ordered
	 */
	protected boolean remoteRun = REMOTE_RUN_EDEFAULT;
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
	 * The default value of the '{@link #isWaitForFullBattery() <em>Wait For Full Battery</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWaitForFullBattery()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WAIT_FOR_FULL_BATTERY_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isWaitForFullBattery() <em>Wait For Full Battery</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWaitForFullBattery()
	 * @generated
	 * @ordered
	 */
	protected boolean waitForFullBattery = WAIT_FOR_FULL_BATTERY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestRunImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestrunPackage.Literals.TEST_RUN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_RUN__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApkFile getAut() {
		if (aut != null && aut.eIsProxy()) {
			InternalEObject oldAut = (InternalEObject)aut;
			aut = (ApkFile)eResolveProxy(oldAut);
			if (aut != oldAut) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TestrunPackage.TEST_RUN__AUT, oldAut, aut));
			}
		}
		return aut;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApkFile basicGetAut() {
		return aut;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAut(ApkFile newAut) {
		ApkFile oldAut = aut;
		aut = newAut;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_RUN__AUT, oldAut, aut));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackageUnderTest() {
		return packageUnderTest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackageUnderTest(String newPackageUnderTest) {
		String oldPackageUnderTest = packageUnderTest;
		packageUnderTest = newPackageUnderTest;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_RUN__PACKAGE_UNDER_TEST, oldPackageUnderTest, packageUnderTest));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApkFile getJunitApk() {
		if (junitApk != null && junitApk.eIsProxy()) {
			InternalEObject oldJunitApk = (InternalEObject)junitApk;
			junitApk = (ApkFile)eResolveProxy(oldJunitApk);
			if (junitApk != oldJunitApk) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TestrunPackage.TEST_RUN__JUNIT_APK, oldJunitApk, junitApk));
			}
		}
		return junitApk;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApkFile basicGetJunitApk() {
		return junitApk;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJunitApk(ApkFile newJunitApk) {
		ApkFile oldJunitApk = junitApk;
		junitApk = newJunitApk;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_RUN__JUNIT_APK, oldJunitApk, junitApk));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getJunitPackage() {
		return junitPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJunitPackage(String newJunitPackage) {
		String oldJunitPackage = junitPackage;
		junitPackage = newJunitPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_RUN__JUNIT_PACKAGE, oldJunitPackage, junitPackage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNoOfRuns() {
		return noOfRuns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNoOfRuns(int newNoOfRuns) {
		int oldNoOfRuns = noOfRuns;
		noOfRuns = newNoOfRuns;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_RUN__NO_OF_RUNS, oldNoOfRuns, noOfRuns));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getIdleTime() {
		return idleTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIdleTime(int newIdleTime) {
		int oldIdleTime = idleTime;
		idleTime = newIdleTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_RUN__IDLE_TIME, oldIdleTime, idleTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHardwareProfilingOn() {
		return hardwareProfilingOn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHardwareProfilingOn(boolean newHardwareProfilingOn) {
		boolean oldHardwareProfilingOn = hardwareProfilingOn;
		hardwareProfilingOn = newHardwareProfilingOn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_RUN__HARDWARE_PROFILING_ON, oldHardwareProfilingOn, hardwareProfilingOn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRemoteRun() {
		return remoteRun;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoteRun(boolean newRemoteRun) {
		boolean oldRemoteRun = remoteRun;
		remoteRun = newRemoteRun;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_RUN__REMOTE_RUN, oldRemoteRun, remoteRun));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TestExecutable> getExecutables() {
		if (executables == null) {
			executables = new EObjectContainmentEList<TestExecutable>(TestExecutable.class, this, TestrunPackage.TEST_RUN__EXECUTABLES);
		}
		return executables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isWaitForFullBattery() {
		return waitForFullBattery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWaitForFullBattery(boolean newWaitForFullBattery) {
		boolean oldWaitForFullBattery = waitForFullBattery;
		waitForFullBattery = newWaitForFullBattery;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TestrunPackage.TEST_RUN__WAIT_FOR_FULL_BATTERY, oldWaitForFullBattery, waitForFullBattery));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TestrunPackage.TEST_RUN__EXECUTABLES:
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
			case TestrunPackage.TEST_RUN__NAME:
				return getName();
			case TestrunPackage.TEST_RUN__AUT:
				if (resolve) return getAut();
				return basicGetAut();
			case TestrunPackage.TEST_RUN__PACKAGE_UNDER_TEST:
				return getPackageUnderTest();
			case TestrunPackage.TEST_RUN__JUNIT_APK:
				if (resolve) return getJunitApk();
				return basicGetJunitApk();
			case TestrunPackage.TEST_RUN__JUNIT_PACKAGE:
				return getJunitPackage();
			case TestrunPackage.TEST_RUN__NO_OF_RUNS:
				return getNoOfRuns();
			case TestrunPackage.TEST_RUN__IDLE_TIME:
				return getIdleTime();
			case TestrunPackage.TEST_RUN__HARDWARE_PROFILING_ON:
				return isHardwareProfilingOn();
			case TestrunPackage.TEST_RUN__REMOTE_RUN:
				return isRemoteRun();
			case TestrunPackage.TEST_RUN__EXECUTABLES:
				return getExecutables();
			case TestrunPackage.TEST_RUN__WAIT_FOR_FULL_BATTERY:
				return isWaitForFullBattery();
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
			case TestrunPackage.TEST_RUN__NAME:
				setName((String)newValue);
				return;
			case TestrunPackage.TEST_RUN__AUT:
				setAut((ApkFile)newValue);
				return;
			case TestrunPackage.TEST_RUN__PACKAGE_UNDER_TEST:
				setPackageUnderTest((String)newValue);
				return;
			case TestrunPackage.TEST_RUN__JUNIT_APK:
				setJunitApk((ApkFile)newValue);
				return;
			case TestrunPackage.TEST_RUN__JUNIT_PACKAGE:
				setJunitPackage((String)newValue);
				return;
			case TestrunPackage.TEST_RUN__NO_OF_RUNS:
				setNoOfRuns((Integer)newValue);
				return;
			case TestrunPackage.TEST_RUN__IDLE_TIME:
				setIdleTime((Integer)newValue);
				return;
			case TestrunPackage.TEST_RUN__HARDWARE_PROFILING_ON:
				setHardwareProfilingOn((Boolean)newValue);
				return;
			case TestrunPackage.TEST_RUN__REMOTE_RUN:
				setRemoteRun((Boolean)newValue);
				return;
			case TestrunPackage.TEST_RUN__EXECUTABLES:
				getExecutables().clear();
				getExecutables().addAll((Collection<? extends TestExecutable>)newValue);
				return;
			case TestrunPackage.TEST_RUN__WAIT_FOR_FULL_BATTERY:
				setWaitForFullBattery((Boolean)newValue);
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
			case TestrunPackage.TEST_RUN__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TestrunPackage.TEST_RUN__AUT:
				setAut((ApkFile)null);
				return;
			case TestrunPackage.TEST_RUN__PACKAGE_UNDER_TEST:
				setPackageUnderTest(PACKAGE_UNDER_TEST_EDEFAULT);
				return;
			case TestrunPackage.TEST_RUN__JUNIT_APK:
				setJunitApk((ApkFile)null);
				return;
			case TestrunPackage.TEST_RUN__JUNIT_PACKAGE:
				setJunitPackage(JUNIT_PACKAGE_EDEFAULT);
				return;
			case TestrunPackage.TEST_RUN__NO_OF_RUNS:
				setNoOfRuns(NO_OF_RUNS_EDEFAULT);
				return;
			case TestrunPackage.TEST_RUN__IDLE_TIME:
				setIdleTime(IDLE_TIME_EDEFAULT);
				return;
			case TestrunPackage.TEST_RUN__HARDWARE_PROFILING_ON:
				setHardwareProfilingOn(HARDWARE_PROFILING_ON_EDEFAULT);
				return;
			case TestrunPackage.TEST_RUN__REMOTE_RUN:
				setRemoteRun(REMOTE_RUN_EDEFAULT);
				return;
			case TestrunPackage.TEST_RUN__EXECUTABLES:
				getExecutables().clear();
				return;
			case TestrunPackage.TEST_RUN__WAIT_FOR_FULL_BATTERY:
				setWaitForFullBattery(WAIT_FOR_FULL_BATTERY_EDEFAULT);
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
			case TestrunPackage.TEST_RUN__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TestrunPackage.TEST_RUN__AUT:
				return aut != null;
			case TestrunPackage.TEST_RUN__PACKAGE_UNDER_TEST:
				return PACKAGE_UNDER_TEST_EDEFAULT == null ? packageUnderTest != null : !PACKAGE_UNDER_TEST_EDEFAULT.equals(packageUnderTest);
			case TestrunPackage.TEST_RUN__JUNIT_APK:
				return junitApk != null;
			case TestrunPackage.TEST_RUN__JUNIT_PACKAGE:
				return JUNIT_PACKAGE_EDEFAULT == null ? junitPackage != null : !JUNIT_PACKAGE_EDEFAULT.equals(junitPackage);
			case TestrunPackage.TEST_RUN__NO_OF_RUNS:
				return noOfRuns != NO_OF_RUNS_EDEFAULT;
			case TestrunPackage.TEST_RUN__IDLE_TIME:
				return idleTime != IDLE_TIME_EDEFAULT;
			case TestrunPackage.TEST_RUN__HARDWARE_PROFILING_ON:
				return hardwareProfilingOn != HARDWARE_PROFILING_ON_EDEFAULT;
			case TestrunPackage.TEST_RUN__REMOTE_RUN:
				return remoteRun != REMOTE_RUN_EDEFAULT;
			case TestrunPackage.TEST_RUN__EXECUTABLES:
				return executables != null && !executables.isEmpty();
			case TestrunPackage.TEST_RUN__WAIT_FOR_FULL_BATTERY:
				return waitForFullBattery != WAIT_FOR_FULL_BATTERY_EDEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", packageUnderTest: ");
		result.append(packageUnderTest);
		result.append(", junitPackage: ");
		result.append(junitPackage);
		result.append(", noOfRuns: ");
		result.append(noOfRuns);
		result.append(", idleTime: ");
		result.append(idleTime);
		result.append(", hardwareProfilingOn: ");
		result.append(hardwareProfilingOn);
		result.append(", remoteRun: ");
		result.append(remoteRun);
		result.append(", waitForFullBattery: ");
		result.append(waitForFullBattery);
		result.append(')');
		return result.toString();
	}

} //TestRunImpl
