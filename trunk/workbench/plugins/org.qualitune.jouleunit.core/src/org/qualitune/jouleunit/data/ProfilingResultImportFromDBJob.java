package org.qualitune.jouleunit.data;

import java.io.PrintStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.qualitune.jouleunit.coordinator.TestSuiteProfile;
import org.qualitune.jouleunit.core.JouleUnitPlugin;

/**
 * This class is responsible to import the test results from a JouleUnit test
 * from the QMark DB.
 * 
 * @author Claas Wilke
 */
public class ProfilingResultImportFromDBJob extends Job {

	/** The ID of the test run to be imported. */
	private int mTestRunID;

	/** The {@link TestSuiteProfile} to be imported. */
	private TestSuiteProfile mProfile;

	/**
	 * Creates a new {@link ProfilingResultImportFromDBJob} for given
	 * {@link EnergyModel} and {@link UsageProfile} and approximates an average
	 * power rate for this combination.
	 * 
	 * @param testRunID
	 *            The ID of the test run to be imported.
	 */
	public ProfilingResultImportFromDBJob(int testRunID) {
		super("Import test run " + testRunID + " from DB");
		this.mTestRunID = testRunID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	protected IStatus run(IProgressMonitor monitor) {

		if (monitor.isCanceled())
			return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
					"Test result import was canceled.");
		// no else.

		monitor.beginTask("Import results ...", 100);

		IConsole androidConsole = JouleUnitPlugin.getDefault()
				.getAndroidConsole();
		DbManager dbMgr;

		if (null != androidConsole && androidConsole instanceof MessageConsole)
			dbMgr = new DbManager(new PrintStream(
					((MessageConsole) androidConsole).newOutputStream()));

		else
			dbMgr = new DbManager(System.out);

		mProfile = dbMgr.readTestSuiteProfile(mTestRunID);
		dbMgr.readTestRunConsole(mTestRunID);

		monitor.worked(99);

		if (monitor.isCanceled())
			return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
					"Test result import was canceled.");
		// no else.

		if (mProfile.getTestCaseProfiles().size() == 0)
			return new Status(IStatus.ERROR, JouleUnitPlugin.PLUGIN_ID,
					"Test run with ID " + mTestRunID + " was not found.");
		// no else.

		monitor.subTask("Propagate results ...");
		ResultPropagator.sendNewResult(mProfile);
		monitor.worked(1);

		return new Status(IStatus.OK, JouleUnitPlugin.PLUGIN_ID,
				"Test run import finished successfully.");
	}
}
