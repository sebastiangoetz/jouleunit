package org.qualitune.jouleunit.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.qualitune.jouleunit.coordinator.TestSuiteProfile;
import org.qualitune.jouleunit.core.JouleUnitPlugin;

/**
 * This class is responsible to update the {@link EnergyView} once new
 * {@link TestSuiteProfile} shall be displayed. This avoids blocking the UI
 * during long loading times of large bunches of data.
 * 
 * @author Claas Wilke
 */
public class UpdateEnergyViewJob extends Job {

	/** The {@link EnergyView} to display the {@link TestSuiteProfile}. */
	private EnergyView view;

	private List<HistoryTestResultObject> allHTROList;

	/**
	 * Creates a new {@link UpdateEnergyViewJob} for given
	 * {@link TestSuiteProfile} and the {@link EnergyView}.
	 * 
	 * @param profile
	 *            The {@link TestSuiteProfile} to be displayed.
	 * @param vieder
	 *            The {@link EnergyView} to display the {@link TestSuiteProfile}
	 *            .
	 */

	public UpdateEnergyViewJob(List<HistoryTestResultObject> allHTROList,
			EnergyView view) {
		super("Update EnergyView");
		// this.o = o;
		this.view = view;
		// this.justSingle = justSingle;
		this.allHTROList = new ArrayList<HistoryTestResultObject>(allHTROList);
		Collections.reverse(this.allHTROList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	int counterJob = 1;

	protected IStatus run(IProgressMonitor monitor) {
		if (monitor.isCanceled())
			return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
					"Test result import was canceled.");
		// no else.

		monitor.beginTask("Display old energy-values (" + allHTROList.size()
				+ " entries)", allHTROList.size());

		counterJob = 1;
		for (final HistoryTestResultObject h : allHTROList) {

			// if the checkbox is unchecked, dont show failed tests
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (!h.isFailed() || view.showFailedTest.getSelection()) {

						if (view.showHistoryConsumption.getSelection())
							view.datasetProgress.setValue(
									Double.parseDouble(h.getConsumption()),
									"Consumption", "No. " + counterJob);
						// no else.

						if (view.showHistoryDuration.getSelection())
							view.datasetProgress.setValue(h.getDuration(),
									"Duration", "No. " + counterJob);
						// no else.

						if (view.showHistoryPowerRate.getSelection())
							view.datasetProgress.setValue(
									Double.parseDouble(h.getAvgPowerRate()),
									"Average Power Rate", "No. " + counterJob);
						// no else.

						counterJob++;
					}
					// no else.
				}
			});

			monitor.worked(1);

			monitor.setTaskName("Display Test Case Results (" + counterJob
					+ "/" + allHTROList.size() + " entries)");

			if (monitor.isCanceled())
				return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
						"Test result display was canceled.");
			// no else.
		}

		if (monitor.isCanceled())
			return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
					"Test result display was canceled.");
		// no else.
		return new Status(IStatus.OK, JouleUnitPlugin.PLUGIN_ID,
				"Old energy-values display finished successfully.");
	}
}
