package org.qualitune.jouleunit.data;

import org.qualitune.jouleunit.coordinator.TestSuiteProfile;

/**
 * Interface for classes that can receiver {@link TestSuiteProfile} from the
 * {@link ResultPropagator}.
 * 
 * @author Claas Wilke
 */
public interface IProfilingResultReceiver {

	/**
	 * Called, if a new {@link TestSuiteProfile} is available.
	 * 
	 * @param profile
	 *            The latest {@link TestSuiteProfile}. <strong>Attention: this
	 *            method should also be able to handle calls with having a
	 *            <code>null</code>-valued profile.</strong>
	 */
	public void updateToNewResult(TestSuiteProfile profile);
}
