/**
 */
package org.qualitune.jouleunit.android.testrun;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Run</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestRun#getName <em>Name</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestRun#getAut <em>Aut</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestRun#getPackageUnderTest <em>Package Under Test</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestRun#getJunitApk <em>Junit Apk</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestRun#getJunitPackage <em>Junit Package</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestRun#getNoOfRuns <em>No Of Runs</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestRun#getIdleTime <em>Idle Time</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestRun#isHardwareProfilingOn <em>Hardware Profiling On</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestRun#isRemoteRun <em>Remote Run</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestRun#getExecutables <em>Executables</em>}</li>
 *   <li>{@link org.qualitune.jouleunit.android.testrun.TestRun#isWaitForFullBattery <em>Wait For Full Battery</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun()
 * @model
 * @generated
 */
public interface TestRun extends EObject {

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestRun#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Aut</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aut</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aut</em>' reference.
	 * @see #setAut(ApkFile)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun_Aut()
	 * @model
	 * @generated
	 */
	ApkFile getAut();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestRun#getAut <em>Aut</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Aut</em>' reference.
	 * @see #getAut()
	 * @generated
	 */
	void setAut(ApkFile value);

	/**
	 * Returns the value of the '<em><b>Package Under Test</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package Under Test</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Under Test</em>' attribute.
	 * @see #setPackageUnderTest(String)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun_PackageUnderTest()
	 * @model
	 * @generated
	 */
	String getPackageUnderTest();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestRun#getPackageUnderTest <em>Package Under Test</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Under Test</em>' attribute.
	 * @see #getPackageUnderTest()
	 * @generated
	 */
	void setPackageUnderTest(String value);

	/**
	 * Returns the value of the '<em><b>Junit Apk</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Junit Apk</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Junit Apk</em>' reference.
	 * @see #setJunitApk(ApkFile)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun_JunitApk()
	 * @model
	 * @generated
	 */
	ApkFile getJunitApk();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestRun#getJunitApk <em>Junit Apk</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Junit Apk</em>' reference.
	 * @see #getJunitApk()
	 * @generated
	 */
	void setJunitApk(ApkFile value);

	/**
	 * Returns the value of the '<em><b>Junit Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Junit Package</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Junit Package</em>' attribute.
	 * @see #setJunitPackage(String)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun_JunitPackage()
	 * @model
	 * @generated
	 */
	String getJunitPackage();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestRun#getJunitPackage <em>Junit Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Junit Package</em>' attribute.
	 * @see #getJunitPackage()
	 * @generated
	 */
	void setJunitPackage(String value);

	/**
	 * Returns the value of the '<em><b>No Of Runs</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>No Of Runs</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>No Of Runs</em>' attribute.
	 * @see #setNoOfRuns(int)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun_NoOfRuns()
	 * @model default="1"
	 * @generated
	 */
	int getNoOfRuns();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestRun#getNoOfRuns <em>No Of Runs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>No Of Runs</em>' attribute.
	 * @see #getNoOfRuns()
	 * @generated
	 */
	void setNoOfRuns(int value);

	/**
	 * Returns the value of the '<em><b>Idle Time</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Idle Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Idle Time</em>' attribute.
	 * @see #setIdleTime(int)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun_IdleTime()
	 * @model default="0"
	 * @generated
	 */
	int getIdleTime();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestRun#getIdleTime <em>Idle Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Idle Time</em>' attribute.
	 * @see #getIdleTime()
	 * @generated
	 */
	void setIdleTime(int value);

	/**
	 * Returns the value of the '<em><b>Hardware Profiling On</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hardware Profiling On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hardware Profiling On</em>' attribute.
	 * @see #setHardwareProfilingOn(boolean)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun_HardwareProfilingOn()
	 * @model default="true"
	 * @generated
	 */
	boolean isHardwareProfilingOn();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestRun#isHardwareProfilingOn <em>Hardware Profiling On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hardware Profiling On</em>' attribute.
	 * @see #isHardwareProfilingOn()
	 * @generated
	 */
	void setHardwareProfilingOn(boolean value);

	/**
	 * Returns the value of the '<em><b>Remote Run</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remote Run</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote Run</em>' attribute.
	 * @see #setRemoteRun(boolean)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun_RemoteRun()
	 * @model default="false"
	 * @generated
	 */
	boolean isRemoteRun();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestRun#isRemoteRun <em>Remote Run</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote Run</em>' attribute.
	 * @see #isRemoteRun()
	 * @generated
	 */
	void setRemoteRun(boolean value);

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
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun_Executables()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<TestExecutable> getExecutables();

	/**
	 * Returns the value of the '<em><b>Wait For Full Battery</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wait For Full Battery</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wait For Full Battery</em>' attribute.
	 * @see #setWaitForFullBattery(boolean)
	 * @see org.qualitune.jouleunit.android.testrun.TestrunPackage#getTestRun_WaitForFullBattery()
	 * @model default="false"
	 * @generated
	 */
	boolean isWaitForFullBattery();

	/**
	 * Sets the value of the '{@link org.qualitune.jouleunit.android.testrun.TestRun#isWaitForFullBattery <em>Wait For Full Battery</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wait For Full Battery</em>' attribute.
	 * @see #isWaitForFullBattery()
	 * @generated
	 */
	void setWaitForFullBattery(boolean value);
} // TestRun
