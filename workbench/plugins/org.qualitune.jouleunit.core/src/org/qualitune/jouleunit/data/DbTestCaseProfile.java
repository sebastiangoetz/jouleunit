package org.qualitune.jouleunit.data;

import org.qualitune.jouleunit.coordinator.TestCaseProfile;

/**
 * Represents a {@link TestCaseProfile} that is stored in the DB.
 * 
 * @author Claas Wilk
 */
public class DbTestCaseProfile extends TestCaseProfile {

	/** The ID of this {@link TestCaseProfile} in the DB. */
	private long dbID;

	/**
	 * Returns the ID of this {@link TestCaseProfile} in the DB. @return The ID
	 * of this {@link TestCaseProfile} in the DB.
	 */
	public long getDbID() {
		return dbID;
	}

	/**
	 * Sets the ID of this {@link TestCaseProfile} in the DB.
	 * 
	 * @param dbID
	 *            The ID of this {@link TestCaseProfile} in the DB.
	 */
	public void setDbID(long dbID) {
		this.dbID = dbID;
	}
}
