package org.qualitune.jouleunit.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.qualitune.jouleunit.coordinator.TestSuiteProfile;
import org.qualitune.jouleunit.core.JouleUnitPlugin;

/**
 * This class is responsible to export the test results from a JouleUnit test
 * run into a CSV file.
 * 
 * @author Claas Wilke
 */
public class ProfilingResultExportJob extends Job {

	/** The path where to export the {@link TestSuiteProfile}. */
	private String mPath;

	/** The {@link TestSuiteProfile} to be exported. */
	private TestSuiteProfile mProfile;

	/**
	 * Creates a new {@link ProfilingResultExportJob} for given
	 * {@link EnergyModel} and {@link UsageProfile} and approximates an average
	 * power rate for this combination.
	 * 
	 * @param profile
	 *            The {@link TestSuiteProfile} to be exported.
	 * @param path
	 *            The path where to export the {@link TestSuiteProfile}.
	 */
	public ProfilingResultExportJob(TestSuiteProfile profile, String path) {
		super("Joule Unit Profiling Result Export to " + path);
		this.mPath = path;
		this.mProfile = profile;
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
					"Power rate approximation was canceled.");
		// no else.

		monitor.beginTask("Export results ...", 100);

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(mPath));
			monitor.worked(5);

			try {
				mProfile.export(out);
				mProfile.setExportLocation(mPath);
			}

			catch (IllegalArgumentException e) {
				out.close();
				return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
						"Failure during result export: " + e.getMessage());
			}

			monitor.worked(90);
			out.close();
			monitor.worked(5);
		}

		catch (IOException e) {
			return new Status(IStatus.CANCEL, JouleUnitPlugin.PLUGIN_ID,
					"Failure during result export: " + e.getMessage());
		}

		return new Status(IStatus.OK, JouleUnitPlugin.PLUGIN_ID,
				"Result export finished successfully.");
	}
}
