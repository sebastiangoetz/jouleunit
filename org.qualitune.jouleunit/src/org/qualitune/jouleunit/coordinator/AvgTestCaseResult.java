package org.qualitune.jouleunit.coordinator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents an averaged value for a test case of a {@link TestCaseProfile}.
 * Thus, multiple runs of the same test case are averaged in such an
 * {@link AvgTestCaseResult}.
 * 
 * @author Claas Wilke
 */
public class AvgTestCaseResult {

	/**
	 * The average duration of all {@link TestCaseProfile} of this
	 * {@link AvgTestCaseResult} in [ms].
	 */
	private double avgDuration;

	/**
	 * The average power consumption of all {@link TestCaseProfile} of this
	 * {@link AvgTestCaseResult} in [mJ].
	 */
	private double avgPowerConsumption;

	/**
	 * The average power rate of all {@link TestCaseProfile} of this
	 * {@link AvgTestCaseResult} in [mW].
	 */
	private double avgPowerRate;

	/** The id of the test cases of this {@link AvgTestCaseResult}. */
	private String id;

	/**
	 * Indicates whether or not the results have been computed since the last
	 * update of this {@link AvgTestCaseResult} (i.e., since a new value has
	 * been added).
	 */
	private boolean isResultUpToDate;

	/** The {@link TestCaseProfile}s of this {@link AvgTestCaseResult}. */
	private Set<TestCaseProfile> myProfiles = new HashSet<TestCaseProfile>();

	/**
	 * The {@link TestCaseProfile}s which represent failed test cases of this
	 * {@link AvgTestCaseResult}.
	 */
	private Set<TestCaseProfile> myFailedProfiles = new HashSet<TestCaseProfile>();

	/**
	 * The standard deviation of the duration of all {@link TestCaseProfile} of
	 * this {@link AvgTestCaseResult} in [ms].
	 */
	private double stdDevDuration;

	/**
	 * The standard deviation of the power consumption of all
	 * {@link TestCaseProfile} of this {@link AvgTestCaseResult} in [mJ].
	 */
	private double stdDevPowerConsumption;

	/**
	 * The standard deviation of the power rate of all {@link TestCaseProfile}
	 * of this {@link AvgTestCaseResult} in [mW].
	 */
	private double stdDevPowerRate;

	/**
	 * Creates a new {@link AvgTestCaseResult}.
	 */
	public AvgTestCaseResult() {
	}

	/**
	 * Adds a {@link TestCaseProfile} to this {@link AvgTestCaseResult}.
	 * 
	 * @param profile
	 *            The {@link TestCaseProfile} to be added.
	 */
	public void addProfile(TestCaseProfile profile) {

		if (myProfiles.isEmpty() && myFailedProfiles.isEmpty())
			id = profile.getId();
		// no else.

		if (profile.isFailed()) {
			myFailedProfiles.add(profile);
		}

		else {
			myProfiles.add(profile);
			isResultUpToDate = false;
		}

		profile.setAvgTestCaseResult(this);
	}

	/**
	 * Removes a {@link TestCaseProfile} from this {@link AvgTestCaseResult}.
	 * 
	 * @param profile
	 *            The {@link TestCaseProfile} to be removed.
	 * @return <code>true</code>, if the {@link TestCaseProfile} belonged to
	 *         this {@link AvgTestCaseResult} and was removed. Else
	 *         <code>false</code>.
	 */
	public boolean removeProfile(TestCaseProfile profile) {

		boolean result = myProfiles.remove(profile)
				|| myFailedProfiles.remove(profile);

		if (result)
			isResultUpToDate = false;
		// no else.

		return result;
	}

	/**
	 * @return The average duration of all {@link TestCaseProfile} of this
	 *         {@link AvgTestCaseResult} in [ms].
	 */
	public double getAvgDuration() {
		if (!isResultUpToDate)
			computeResults();
		// no else.

		return avgDuration;
	}

	/**
	 * @return The average power consumption of all {@link TestCaseProfile} of
	 *         this {@link AvgTestCaseResult} in [mJ].
	 */
	public double getAvgPowerConsumption() {
		if (!isResultUpToDate)
			computeResults();
		// no else.

		return avgPowerConsumption;
	}

	/**
	 * @return The average power rate of all {@link TestCaseProfile} of this
	 *         {@link AvgTestCaseResult} in [mW].
	 */
	public double getAvgPowerRate() {
		if (!isResultUpToDate)
			computeResults();
		// no else.

		return avgPowerRate;
	}

	/**
	 * @return The ID of the {@link TestCaseProfile}s of this
	 *         {@link AvgTestCaseResult}.
	 */
	public String getID() {
		return id;
	}

	/**
	 * @return The number of {@link TestCaseProfile}s in this
	 *         {@link AvgTestCaseResult} (does not include failed test cases).
	 */
	public int getNumberOfRuns() {
		return myProfiles.size();
	}

	/**
	 * @return The number of failed {@link TestCaseProfile}s in this
	 *         {@link AvgTestCaseResult}.
	 */
	public int getNumberOfFailedRuns() {
		return myFailedProfiles.size();
	}

	/**
	 * Computes the pTh quantile for the duration of this
	 * {@link AvgTestCaseResult}. Meaning that the duration of p percent [0 ..
	 * 1] of the contained {@link TestCaseProfile}s is below this duration.
	 * 
	 * @return The standard deviation of the duration of all
	 *         {@link TestCaseProfile} of this {@link AvgTestCaseResult} in
	 *         [ms].
	 */
	public long getQuantileDuration(float p) {

		if (p < 0f || p > 1f)
			throw new IllegalArgumentException("P must be between 0.0 and 1.0.");
		// no else.

		List<Long> durations = new ArrayList<Long>();
		for (TestCaseProfile profile : new ArrayList<TestCaseProfile>(
				myProfiles))
			durations.add(profile.getDuration());
		// end for.

		Collections.sort(durations);

		int index = Math.round((durations.size() - 1) * p);

		if (index < 0)
			index = 0;
		else if (index >= durations.size())
			index = durations.size() - 1;
		// no else.

		return durations.get(index);
	}

	/**
	 * @return The standard deviation of the duration of all
	 *         {@link TestCaseProfile} of this {@link AvgTestCaseResult} in
	 *         [ms].
	 */
	public double getStdDevDuration() {
		if (!isResultUpToDate)
			computeResults();
		// no else.

		return stdDevDuration;
	}

	/**
	 * @return The standard deviation of the power consumption of all
	 *         {@link TestCaseProfile} of this {@link AvgTestCaseResult} in
	 *         [mJ].
	 */
	public double getStdDevPowerConsumption() {
		if (!isResultUpToDate)
			computeResults();
		// no else.

		return stdDevPowerConsumption;
	}

	/**
	 * @return The standard deviation of the power rate of all
	 *         {@link TestCaseProfile} of this {@link AvgTestCaseResult} in
	 *         [mW].
	 */
	public double getStdDevPowerRate() {
		if (!isResultUpToDate)
			computeResults();
		// no else.

		return stdDevPowerRate;
	}

	/**
	 * Helper method that computes the results of this {@link AvgTestCaseResult}
	 * .
	 */
	private void computeResults() {

		avgDuration = 0d;
		stdDevDuration = 0d;
		avgPowerRate = 0d;
		stdDevPowerRate = 0d;
		avgPowerConsumption = 0d;
		stdDevPowerConsumption = 0d;

		for (TestCaseProfile profile : myProfiles) {
			avgDuration += profile.getDuration();

			avgPowerRate += profile.getPowerRate();

			avgPowerConsumption += profile.getConsumedEnergy();
		}
		// end for.

		if (myProfiles.size() > 0) {
			avgDuration /= myProfiles.size();

			avgPowerRate /= myProfiles.size();

			avgPowerConsumption /= myProfiles.size();
		}
		// no else.

		/* Std. Dev. computation. */
		if (myProfiles.size() > 1) {

			for (TestCaseProfile profile : myProfiles) {
				double differenceDuration = (profile.getDuration() - avgDuration);
				double differencePowerRate = profile.getPowerRate()
						- avgPowerRate;
				double differencePowerConsumption = (profile
						.getConsumedEnergy() - avgPowerConsumption);

				stdDevDuration += differenceDuration * differenceDuration;
				stdDevPowerRate += differencePowerRate * differencePowerRate;
				stdDevPowerConsumption += differencePowerConsumption
						* differencePowerConsumption;
			}
			// end for.

			/* Compute variance. */
			stdDevDuration = stdDevDuration / (myProfiles.size() - 1);
			stdDevPowerRate = stdDevPowerRate / (myProfiles.size() - 1);
			stdDevPowerConsumption = stdDevPowerConsumption
					/ (myProfiles.size() - 1);

			/* Compute std. dev. */
			stdDevDuration = Math.sqrt(stdDevDuration);
			stdDevPowerRate = Math.sqrt(stdDevPowerRate);
			stdDevPowerConsumption = Math.sqrt(stdDevPowerConsumption);
		}
		// no else.

		isResultUpToDate = true;
	}
}
