package org.jouleunit.helloworld.test;

import org.jouleunit.helloworld.MainActivity;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

/**
 * Contains test cases to profile the {@link com.fsck.k9.activity.Accounts}
 * activity w.r.t. energy consumption.
 * 
 * @author JouleUnit for Android
 */
@SuppressWarnings("rawtypes")
public class HelloWorldTests extends ActivityInstrumentationTestCase2 {

	/** Tag for logging during testing. */
	public static final String LOG_TAG = "TestRunner";

	/** Constructor. */
	@SuppressWarnings("unchecked")
	public HelloWorldTests() {
		super("org.jouleunit.helloworld", MainActivity.class);
	}

	/** {@link Solo} object for testing. */
	private Solo solo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.test.ActivityInstrumentationTestCase2#setUp()
	 */
	@Override
	public void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	/**
	 * Test case just running the app idle for 10 seconds (used to evaluate the
	 * idle energy consumption).
	 * 
	 * @throws InterruptedException
	 */
	public void testIdle() throws InterruptedException {
		Thread.sleep(10000);
	}
}
