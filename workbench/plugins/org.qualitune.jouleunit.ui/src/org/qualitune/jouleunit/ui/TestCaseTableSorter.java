package org.qualitune.jouleunit.ui;

import org.eclipse.jface.viewers.Viewer;
import org.qualitune.jouleunit.coordinator.AvgTestCaseResult;
import org.qualitune.jouleunit.coordinator.TestCaseProfile;

/**
 * Sorter to sort the tables of the {@link TestCaseView}.
 * 
 * @author Claas Wilke
 */
public class TestCaseTableSorter extends AbstractViewerSorter {

	/*
	 * @see ViewerSorter#compare
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (e1 instanceof TestCaseProfile && e2 instanceof TestCaseProfile) {
			TestCaseProfile p1 = (TestCaseProfile) e1;
			TestCaseProfile p2 = (TestCaseProfile) e2;

			/* Probably sort in reverse order. */
			if (!asc) {
				TestCaseProfile swap = p2;
				p2 = p1;
				p1 = swap;
			}
			// no else.

			if (null != currentSortColumn && currentSortColumn.equals("Tag")) {
				String p1Tag = p1.getTag();
				String p2Tag = p2.getTag();

				if (null == p1Tag)
					p1Tag = "";
				// no else.

				if (null == p2Tag)
					p2Tag = "";
				// no else.

				return p1Tag.compareTo(p2Tag);
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Start [ms]")) {
				int result = new Long(p1.getStartTime()).compareTo(new Long(p2
						.getStartTime()));

				/*
				 * If two tests have the same start time, compare their end
				 * time. The longer one should come first (container).
				 */
				if (result == 0)
					result = new Long(p1.getEndTime()).compareTo(new Long(p2
							.getEndTime())) * (-1);
				// no else.

				return result;
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Stop [ms]")) {
				return new Long(p1.getEndTime()).compareTo(new Long(p2
						.getEndTime()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Duration [ms]")) {
				return new Long(p1.getDuration()).compareTo(new Long(p2
						.getDuration()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Avg. Power Rate [mW]")) {
				return new Double(p1.getPowerRate()).compareTo(new Double(p2
						.getPowerRate()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Energy Consumption [mJ]")) {
				return new Double(p1.getConsumedEnergy()).compareTo(new Double(
						p2.getConsumedEnergy()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Outlier?")) {
				return p1.getOutlierInfo().compareTo(p2.getOutlierInfo());
			}

			/* Default case (sort by id / test case name). */
			else {
				return p1.getId().compareTo(p2.getId());
			}
		}

		else if (e1 instanceof AvgTestCaseResult
				&& e2 instanceof AvgTestCaseResult) {
			AvgTestCaseResult p1 = (AvgTestCaseResult) e1;
			AvgTestCaseResult p2 = (AvgTestCaseResult) e2;

			/* Probably sort in reverse order. */
			if (!asc) {
				AvgTestCaseResult swap = p2;
				p2 = p1;
				p1 = swap;
			}
			// no else.

			if (null != currentSortColumn && currentSortColumn.equals("# runs")) {
				return new Integer(p1.getNumberOfRuns()).compareTo(new Integer(
						p2.getNumberOfRuns()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("# failures")) {
				return new Integer(p1.getNumberOfFailedRuns())
						.compareTo(new Integer(p2.getNumberOfFailedRuns()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Avg. Duration [ms]")) {
				return new Double(p1.getAvgDuration()).compareTo(new Double(p2
						.getAvgDuration()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Std. dev. [ms]")) {
				return new Double(p1.getStdDevDuration()).compareTo(new Double(
						p2.getStdDevDuration()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Avg. Power Rate [mW]")) {
				return new Double(p1.getAvgPowerRate()).compareTo(new Double(p2
						.getAvgPowerRate()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Std. dev. [mW]")) {
				return new Double(p1.getStdDevPowerRate())
						.compareTo(new Double(p2.getStdDevPowerRate()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Avg. Power Consumption [mJ]")) {
				return new Double(p1.getAvgPowerConsumption())
						.compareTo(new Double(p2.getAvgPowerConsumption()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Std. dev. [mJ]")) {
				return new Double(p1.getStdDevDuration()).compareTo(new Double(
						p2.getAvgPowerConsumption()));
			}

			/* Default case (sort by id / test case name). */
			else {
				return p1.getID().compareTo(p2.getID());
			}
		} else if (e1 instanceof HistoryTestResultObject
				&& e2 instanceof HistoryTestResultObject) {
			HistoryTestResultObject p1 = (HistoryTestResultObject) e1;
			HistoryTestResultObject p2 = (HistoryTestResultObject) e2;

			/* Probably sort in reverse order. */
			if (!asc) {
				HistoryTestResultObject swap = p2;
				p2 = p1;
				p1 = swap;
			}
			// no else.

			if (null != currentSortColumn
					&& currentSortColumn.equals("Start [ms]")) {
				return new Long(p1.getStartTime()).compareTo(new Long(p2
						.getStartTime()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Stop [ms]")) {
				return new Long(p1.getEndTime()).compareTo(new Long(p2
						.getEndTime()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Duration [ms]")) {
				return new Long(p1.getDuration()).compareTo(new Long(p2
						.getDuration()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Avg. Power Rate [mW]")) {
				return new Double(p1.getAvgPowerRate()).compareTo(new Double(p2
						.getAvgPowerRate()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Energy Consumption [mJ]")) {
				return new Double(p1.getConsumption()).compareTo(new Double(p2
						.getConsumption()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Created on")) {
				return new Long(p1.getCreatedOn().getTime())
						.compareTo(new Long(p2.getCreatedOn().getTime()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("File locally available?")) {
				return new Boolean(p1.isFileSavedLocally())
						.compareTo(new Boolean(p2.isFileSavedLocally()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Failed?")) {
				return new Boolean(p1.isFailed()).compareTo(new Boolean(p2
						.isFailed()));
			}

			else if (null != currentSortColumn
					&& currentSortColumn.equals("Location for local file")) {
				return new String(p1.getLocation()).compareTo(new String(p2
						.getLocation()));
			}

			/* Default case (sort by id / test case name). */
			else {
				return p1.getId().compareTo(p2.getId());
			}
		}

		else
			return 0;
	}
}
