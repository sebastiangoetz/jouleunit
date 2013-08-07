package org.qualitune.jouleunit.data;

import java.io.PrintStream;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.qualitune.jouleunit.core.JouleUnitPlugin;

/**
 * This class is responsible to propagate changes on {@link DbTestCaseProfile}s
 * to the DB.
 * 
 * @author Claas Wilke
 */
public class UpdateTestCaseToDBJob extends Job {

	/** The {@link DbTestCaseProfile}s to be updated. */
	private List<DbTestCaseProfile> testCaseProfiles;

	/**
	 * Creates a new {@link UpdateTestCaseToDBJob} for given {@link List} of
	 * {@link DbTestCaseProfile}s to be updated.
	 * 
	 * @param testCaseProfiles
	 *            The {@link DbTestCaseProfile}s to be updated.
	 */
	public UpdateTestCaseToDBJob(List<DbTestCaseProfile> testCaseProfiles) {
		super("Update TestCases in the DB");
		this.testCaseProfiles = testCaseProfiles;
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
					"TestCase update was canceled.");
		// no else.

		monitor.beginTask("Create DB connection.", testCaseProfiles.size() + 1);

		DbManager dbMgr;
		IConsole androidConsole = JouleUnitPlugin.getDefault()
				.getAndroidConsole();
		monitor.worked(1);

		if (null != androidConsole && androidConsole instanceof MessageConsole)
			dbMgr = new DbManager(new PrintStream(
					((MessageConsole) androidConsole).newOutputStream()));
		else
			dbMgr = new DbManager(System.out);

		int counter = 0;

		for (DbTestCaseProfile profile : testCaseProfiles) {
			if (monitor.isCanceled())
				return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
						"TestCase update was canceled.");
			// no else.

			counter++;
			monitor.setTaskName("Update TestCase " + counter + "/"
					+ testCaseProfiles.size());

			dbMgr.updateTestCase(profile);
			monitor.worked(1);
		}
		// end for.

		return new Status(IStatus.OK, JouleUnitPlugin.PLUGIN_ID,
				"TestCase update finished successfully.");
	}
}
