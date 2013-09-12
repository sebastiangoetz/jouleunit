package org.qualitune.jouleunit.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.qualitune.jouleunit.coordinator.AvgTestCaseResult;
import org.qualitune.jouleunit.coordinator.TestCaseProfile;
import org.qualitune.jouleunit.coordinator.TestSuiteProfile;
import org.qualitune.jouleunit.core.JouleUnitPlugin;

/**
 * This class is responsible to update the {@link TestCaseView} once new
 * {@link TestSuiteProfile} shall be displayed. This avoids blocking the UI
 * during long loading times of large bunches of data.
 * 
 * @author Claas Wilke
 */
public class UpdateTestCaseViewJob extends Job {

	/** The {@link TestSuiteProfile} to be displayed. */
	private TestSuiteProfile profile;

	/** The {@link TestCaseView} to display the {@link TestSuiteProfile}. */
	private TestCaseView view;

	/**
	 * Creates a new {@link UpdateTestCaseViewJob} for given
	 * {@link TestSuiteProfile} and the {@link TestCaseView}.
	 * 
	 * @param profile
	 *            The {@link TestSuiteProfile} to be displayed.
	 * @param vieder
	 *            The {@link TestCaseView} to display the
	 *            {@link TestSuiteProfile}.
	 */
	public UpdateTestCaseViewJob(TestSuiteProfile profile, TestCaseView view) {
		super("Update TestCaseView");
		this.profile = profile;
		this.view = view;
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

		monitor.beginTask("Disable menu options", 1);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				view.saveAction.setEnabled(false);
				view.exportEnergyModelAction.setEnabled(false);

			}
		});
		monitor.worked(1);

		monitor.beginTask("Display Test Case Results ("
				+ profile.getTestCaseProfiles().size() + " entries)", profile
				.getTestCaseProfiles().size());

		int counter = 0;
		for (TestCaseProfile testProfile : profile.getTestCaseProfiles()) {
			final TestCaseProfile testCase = testProfile;

			/*
			 * Compute consume energy before running on UI thread to avoid
			 * blocking.
			 */
			try {
				testProfile.getConsumedEnergy();
			} catch (IllegalArgumentException e) {
				// TODO How to handle this? (Happens when start and stop
				// timestamps interfere.
			}

			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					view.viewer.add(testCase);
				}
			});

			monitor.worked(1);
			counter++;

			monitor.setTaskName("Display Test Case Results (" + counter + "/"
					+ profile.getTestCaseProfiles().size() + " entries)");

			if (monitor.isCanceled())
				return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
						"Test result display was canceled.");
			// no else.
		}

		if (monitor.isCanceled())
			return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
					"Test result display was canceled.");
		// no else.

		/*
		 * After the incrementally update set all data again as once to make it
		 * sortable.
		 */
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				view.viewer.setInput(profile.getTestCaseProfiles());
			}
		});

		if (monitor.isCanceled())
			return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
					"Test result display was canceled.");
		// no else.

		monitor.beginTask("Display Average Test Case Results",
				profile.getAvgTestCaseResults().length);

		for (AvgTestCaseResult testProfile : profile.getAvgTestCaseResults()) {
			final AvgTestCaseResult testCase = testProfile;

			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					view.avgViewer.add(testCase);
				}
			});

			monitor.worked(1);

			if (monitor.isCanceled())
				return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
						"Test result display was canceled.");
			// no else.
		}

		if (monitor.isCanceled())
			return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
					"Test result display was canceled.");
		// no else.

		/*
		 * After the incrementally update set all data again as once to make it
		 * sortable.
		 */
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				view.avgViewer.setInput(profile.getAvgTestCaseResults());
			}
		});

		if (monitor.isCanceled())
			return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
					"Test result display was canceled.");
		// no else.

		monitor.beginTask("Enable menu options", 1);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				view.saveAction.setEnabled(true);
				view.exportEnergyModelAction.setEnabled(true);

			}
		});
		monitor.worked(1);

		if (monitor.isCanceled())
			return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
					"Test result display was canceled.");
		// no else.

		monitor.beginTask("Export test results for history", 1);
		if ("".equals(profile.getExportLocation())
				|| profile.getExportLocation() == null) {
			Util.getInstance().exportTestResultForHistory(
					profile.getTestCaseProfiles(), "");
		} else {
			Util.getInstance().exportTestResultForHistory(
					profile.getTestCaseProfiles(), profile.getExportLocation());
		}
		monitor.worked(1);

		if (monitor.isCanceled())
			return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
					"Test result display was canceled.");
		// no else.

		monitor.beginTask("Update old test results", 1);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				view.updateOldTestResultViewer();
			}
		});
		monitor.worked(1);

		return new Status(IStatus.OK, JouleUnitPlugin.PLUGIN_ID,
				"Test result display finished successfully.");
	}
}
