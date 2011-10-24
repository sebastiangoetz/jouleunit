package org.qualitune.jouleunit.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.PowerRate;
import org.qualitune.jouleunit.SimpleEnergyProfile;

/**
 * Responsible to store {@link EnergyProfile}s to provide regression testing
 * using the history of test case executions.
 * 
 * @author Claas Wilke
 */
public final class EnergyProfileHistory {

	/** The singleton instance of {@link EnergyProfileHistory}. */
	public static EnergyProfileHistory INSTANCE = new EnergyProfileHistory();

	/** {@link PreparedStatement} to call the identity() function. */
	private PreparedStatement callIdentityStatement;

	/** {@link PreparedStatement} to persist all changes. */
	private PreparedStatement configureImmediateBackup;

	/**
	 * {@link PreparedStatement} to create the foreign key between the
	 * {@link PowerRate} and the {@link EnergyProfile} table.
	 */
	private PreparedStatement createConstraintFK1Statement;

	/** {@link PreparedStatement} to create the {@link EnergyProfile} table. */
	private PreparedStatement createTableEProfilesStatement;

	/** {@link PreparedStatement} to create the {@link PowerRate} table. */
	private PreparedStatement createTableEMeasuringValueStatement;

	/** The Database {@link Connection} used to store the energy profile data. */
	private Connection dbConnection;

	/** {@link PreparedStatement} to drop {@link EnergyProfile}s. */
	private PreparedStatement dropAllEProfiles;

	/** {@link PreparedStatement} to drop {@link EnergyProfile}s. */
	private PreparedStatement dropEProfiles;

	/** {@link PreparedStatement} to drop {@link PowerRate}s. */
	private PreparedStatement dropAllEMeasuringValues;

	/** {@link PreparedStatement} to drop {@link PowerRate}s. */
	private PreparedStatement dropEMeasuringValues;

	/** {@link PreparedStatement} to drop {@link PowerRate}s. */
	private PreparedStatement dropEMeasuringValuesByTimeStamp;

	/** {@link PreparedStatement} to select the best {@link EnergyProfile}. */
	private PreparedStatement selectBestProfile;

	/** {@link PreparedStatement} to select {@link EnergyProfile}s. */
	private PreparedStatement selectAllProfiles;

	/** {@link PreparedStatement} to select {@link EnergyProfile}s. */
	private PreparedStatement selectEProfiles;

	/** {@link PreparedStatement} to select {@link EnergyProfile}s. */
	private PreparedStatement selectEProfilesByTimeStamp;

	/** {@link PreparedStatement} to select {@link PowerRate}s. */
	private PreparedStatement selectEMeasuringValues;

	/** {@link PreparedStatement} to store an {@link EnergyProfile}. */
	private PreparedStatement storeEProfileStatement;

	/** {@link PreparedStatement} to store an {@link PowerRate}. */
	private PreparedStatement storeEMeasuringValueStatement;

	/** Private constructor for singleton pattern. */
	private EnergyProfileHistory() {
		init();
	}

	/**
	 * Initializes the singleton instance.
	 */
	private void init() {

		/* Create DB connection. */
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			dbConnection = DriverManager.getConnection(
					"jdbc:hsqldb:jouleunithistory", "SA", "");
			/* Probably create the table. */
			if (configureImmediateBackup == null)
				configureImmediateBackup = dbConnection
						.prepareStatement("SET WRITE_DELAY 0 MILLIS");
			// no else.
			configureImmediateBackup.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"Was not able to set up database for profiling.");
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Was not able to set up database for profiling.");
		}
	}

	/**
	 * Drops all {@link EnergyProfile}s for a given identifier
	 * {@link EnergyProfile}s.
	 * 
	 * @param identifier
	 *            The identifier of the {@link EnergyProfile}s to be dropped.
	 * @throws SQLException
	 *             Thrown, if something w.r.t. the DB fails.
	 */
	public void dropProfiles(String identifier) throws SQLException {

		if (identifier == null || identifier.length() == 0
				|| identifier.length() > 100)
			throw new IllegalArgumentException(
					"Argument 'identifier' must not be null or emtpy and should have at maximum 100 characters.");
		// no else.

		probablyCreatePowerRateTable();

		/* Delete all measurement values related to this IDs. */

		/* Probably prepare the statement. */
		if (dropAllEMeasuringValues == null) {
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM EMeasuringValue\n");
			query.append("WHERE profile_id IN (\n");
			query.append("    SELECT profile_id\n");
			query.append("    FROM EProfile\n");
			query.append("    WHERE identifier = ?\n");
			query.append(") ");
			dropAllEMeasuringValues = dbConnection.prepareStatement(query
					.toString());
			// no else.
		}

		dropAllEMeasuringValues.setString(1, identifier);
		dropAllEMeasuringValues.executeUpdate();

		/* Delete all profiles related to this IDs. */

		/* Probably prepare the statement. */
		if (dropAllEProfiles == null) {
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM EProfile\n");
			query.append("WHERE identifier = ?\n");
			dropAllEProfiles = dbConnection.prepareStatement(query.toString());
			// no else.
		}

		dropAllEProfiles.setString(1, identifier);
		dropAllEProfiles.executeUpdate();
	}

	/**
	 * Helper method that drops all {@link EnergyProfile}s for a given
	 * identifier except the last <code>numberOfProfiles</code>
	 * {@link EnergyProfile}s.
	 * 
	 * @param identifier
	 *            The identifier of the {@link EnergyProfile}s to be dropped.
	 * @param numberOfProfiles
	 *            The amount of {@link EnergyProfile}s to remain.
	 * @throws SQLException
	 *             Thrown, if something w.r.t. the DB fails.
	 */
	public void dropTooOldValues(String identifier, int numberOfProfiles)
			throws SQLException {

		if (identifier == null || identifier.length() == 0)
			throw new IllegalArgumentException(
					"Argument 'identifier' cannot be null or empty.");
		else if (numberOfProfiles <= 0)
			throw new IllegalArgumentException(
					"Argument 'numberOfProfiles' must be positive.");
		// no else.

		/* Delete all measurement values related to this IDs. */

		/* Probably prepare the statement. */
		if (dropEMeasuringValues == null) {
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM EMeasuringValue\n");
			query.append("WHERE profile_id IN (\n");
			query.append("    SELECT profile_id\n");
			query.append("    FROM EProfile\n");
			query.append("    WHERE identifier = ?\n");
			query.append("    ORDER BY endTime DESC\n");
			query.append("    LIMIT ?, 0\n");
			query.append(") ");
			dropEMeasuringValues = dbConnection.prepareStatement(query
					.toString());
			// no else.
		}

		dropEMeasuringValues.setString(1, identifier);
		/* Discard the first two rows. */
		dropEMeasuringValues.setInt(2, numberOfProfiles);
		dropEMeasuringValues.executeUpdate();

		/* Delete all profiles related to this IDs. */

		/* Probably prepare the statement. */
		if (dropEProfiles == null) {
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM EProfile\n");
			query.append("WHERE identifier = ? AND endTime < (\n");
			query.append("    SELECT MIN(endTime)\n");
			query.append("    FROM (\n");
			query.append("        SELECT endTime\n");
			query.append("        FROM EProfile\n");
			query.append("        WHERE identifier = ?\n");
			query.append("        ORDER BY endTime DESC\n");
			query.append("        LIMIT 0, ?\n");
			query.append("    )\n");
			query.append(")\n");
			dropEProfiles = dbConnection.prepareStatement(query.toString());
			// no else.
		}

		dropEProfiles.setString(1, identifier);
		dropEProfiles.setString(2, identifier);
		dropEProfiles.setInt(3, numberOfProfiles);
		dropEProfiles.executeUpdate();
	}

	/**
	 * Helper method that drops all {@link EnergyProfile}s for a given
	 * identifier that are older than a given time stamp. {@link EnergyProfile}
	 * s.
	 * 
	 * @param identifier
	 *            The identifier of the {@link EnergyProfile}s to be dropped.
	 * @param timeStamp
	 *            The time stamp.
	 * @throws SQLException
	 *             Thrown, if something w.r.t. the DB fails.
	 */
	public void dropTooOldValuesByTimeStamp(String identifier, long timeStamp)
			throws SQLException {

		if (identifier == null || identifier.length() == 0)
			throw new IllegalArgumentException(
					"Argument 'identifier' cannot be null or empty.");
		else if (timeStamp > System.nanoTime())
			throw new IllegalArgumentException(
					"Argument 'timeStamp' must lay in the past.");
		// no else.
		/* Delete all measurement values related to this IDs. */

		/* Probably prepare the statement. */
		if (dropEMeasuringValuesByTimeStamp == null) {
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM EMeasuringValue\n");
			query.append("WHERE profile_id IN (\n");
			query.append("    SELECT profile_id\n");
			query.append("    FROM EProfile\n");
			query.append("    WHERE identifier = ? AND endTime < ?\n");
			query.append(") ");
			dropEMeasuringValuesByTimeStamp = dbConnection
					.prepareStatement(query.toString());
			// no else.
		}

		dropEMeasuringValuesByTimeStamp.setString(1, identifier);
		/* Discard the first two rows. */
		dropEMeasuringValuesByTimeStamp.setLong(2, timeStamp);
		dropEMeasuringValuesByTimeStamp.executeUpdate();

		/* Delete all profiles related to this IDs. */

		/* Probably prepare the statement. */
		if (selectEProfilesByTimeStamp == null) {
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM EProfile\n");
			query.append("WHERE identifier = ? AND endTime < ?\n");
			selectEProfilesByTimeStamp = dbConnection.prepareStatement(query
					.toString());
			// no else.
		}

		selectEProfilesByTimeStamp.setString(1, identifier);
		selectEProfilesByTimeStamp.setLong(2, timeStamp);
		selectEProfilesByTimeStamp.executeUpdate();
	}

	/**
	 * Requests the {@link EnergyProfileHistory} DB for a given identifier at
	 * returns <strong>the best</strong> {@link EnergyProfile} w.r.t. energy
	 * consumption available for this identifier.
	 * 
	 * @param identifier
	 *            The identifier of the {@link EnergyProfile}s that shall be
	 *            returned.
	 * @return An {@link EnergyProfile} or <code>null</code>.
	 * @throws SQLException
	 *             Thrown, if the DB access fails.
	 */
	public EnergyProfile getBestProfileEver(String identifier)
			throws SQLException {

		if (identifier == null || identifier.length() == 0
				|| identifier.length() > 100)
			throw new IllegalArgumentException(
					"Argument 'identifier' must not be null or emtpy and should have at maximum 100 characters.");
		// no else.

		/* Probably prepare the statement. */
		if (selectBestProfile == null) {
			StringBuffer query = new StringBuffer();
			query.append("SELECT profile_id, startTime, endTime ");
			query.append("FROM EProfile ");
			query.append("WHERE identifier = ? ");
			query.append("ORDER BY energy ASC ");
			query.append("LIMIT 0, 1 ");
			selectBestProfile = dbConnection.prepareStatement(query.toString());
			// no else.
		}

		selectBestProfile.setString(1, identifier);
		ResultSet rs = selectBestProfile.executeQuery();

		EnergyProfile result = null;

		while (rs.next()) {
			long start, end, profile_id;

			profile_id = rs.getLong(1);
			start = rs.getLong(2);
			end = rs.getLong(3);

			SimpleEnergyProfile newProfile = new SimpleEnergyProfile();
			newProfile.setStartedNanoTime(start);
			newProfile.setStopNanoTime(end);

			/* Add all measuring values of the profile. */
			for (PowerRate value : getPowerRatesFromHistory(profile_id))
				newProfile.addPowerRateValue(value);
			// end for.

			result = newProfile;
		}
		// end while.

		return result;
	}

	/**
	 * Requests the {@link EnergyProfileHistory} DB for a given identifier at
	 * returns all {@link EnergyProfile}s available for this identifier.
	 * 
	 * @param identifier
	 *            The identifier of the {@link EnergyProfile}s that shall be
	 *            returned.
	 * @return A {@link List} of {@link EnergyProfile}s.
	 * @throws SQLException
	 *             Thrown, if the DB access fails.
	 */
	public List<EnergyProfile> getProfiles(String identifier)
			throws SQLException {

		if (identifier == null || identifier.length() == 0
				|| identifier.length() > 100)
			throw new IllegalArgumentException(
					"Argument 'identifier' must not be null or emtpy and should have at maximum 100 characters.");
		// no else.

		/* Probably prepare the statement. */
		if (selectAllProfiles == null) {
			StringBuffer query = new StringBuffer();
			query.append("SELECT profile_id, startTime, endTime ");
			query.append("FROM EProfile ");
			query.append("WHERE identifier = ? ");
			query.append("ORDER BY startTime DESC ");
			selectAllProfiles = dbConnection.prepareStatement(query.toString());
			// no else.
		}

		selectAllProfiles.setString(1, identifier);
		ResultSet rs = selectAllProfiles.executeQuery();

		List<EnergyProfile> result = new LinkedList<EnergyProfile>();

		while (rs.next()) {
			long start, end, profile_id;

			profile_id = rs.getLong(1);
			start = rs.getLong(2);
			end = rs.getLong(3);

			SimpleEnergyProfile newProfile = new SimpleEnergyProfile();
			newProfile.setStartedNanoTime(start);
			newProfile.setStopNanoTime(end);

			/* Add all measuring values of the profile. */
			for (PowerRate value : getPowerRatesFromHistory(profile_id))
				newProfile.addPowerRateValue(value);
			// end for.

			result.add(0, newProfile);
		}
		// end while.

		return result;
	}

	/**
	 * Requests the {@link EnergyProfileHistory} DB for a given identifier at
	 * returns <strong>at maximum</strong> the latest numberOfProfiles
	 * {@link EnergyProfile} s, if available for this identifier.
	 * 
	 * @param identifier
	 *            The identifier of the {@link EnergyProfile}s that shall be
	 *            returned.
	 * @param numberOfProfiles
	 *            The <strong>maximum</strong> number of latest
	 *            {@link EnergyProfile} s that shall be returned for this
	 *            identifier.
	 * @return A {@link List} of {@link EnergyProfile}s.
	 * @throws SQLException
	 *             Thrown, if the DB access fails.
	 */
	public List<EnergyProfile> getProfiles(String identifier,
			int numberOfProfiles) throws SQLException {

		if (identifier == null || identifier.length() == 0
				|| identifier.length() > 100)
			throw new IllegalArgumentException(
					"Argument 'identifier' must not be null or emtpy and should have at maximum 100 characters.");
		else if (numberOfProfiles <= 0)
			throw new IllegalArgumentException(
					"Argument 'numberOfProfiles' must greater than 0.");
		// no else.

		/* Drop values that are too old (probably with a global flag). */
		dropTooOldValues(identifier, numberOfProfiles);

		/* Probably prepare the statement. */
		if (selectEProfiles == null) {
			StringBuffer query = new StringBuffer();
			query.append("SELECT profile_id, startTime, endTime ");
			query.append("FROM EProfile ");
			query.append("WHERE identifier = ? ");
			query.append("ORDER BY startTime DESC ");
			query.append("LIMIT 0, ? ");
			selectEProfiles = dbConnection.prepareStatement(query.toString());
			// no else.
		}

		selectEProfiles.setString(1, identifier);
		selectEProfiles.setInt(2, numberOfProfiles);
		ResultSet rs = selectEProfiles.executeQuery();

		List<EnergyProfile> result = new LinkedList<EnergyProfile>();

		while (result.size() <= numberOfProfiles && rs.next()) {
			long start, end, profile_id;

			profile_id = rs.getLong(1);
			start = rs.getLong(2);
			end = rs.getLong(3);

			SimpleEnergyProfile newProfile = new SimpleEnergyProfile();
			newProfile.setStartedNanoTime(start);
			newProfile.setStopNanoTime(end);

			/* Add all measuring values of the profile. */
			for (PowerRate value : getPowerRatesFromHistory(profile_id))
				newProfile.addPowerRateValue(value);
			// end for.

			result.add(0, newProfile);
		}
		// end while.

		return result;
	}

	/**
	 * Stores an {@link EnergyProfile}.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}.
	 * @param identifier
	 *            The identifier for the assertion the {@link EnergyProfile}
	 *            belongs to.
	 * @throws SQLException
	 */
	public void storeProfile(EnergyProfile profile, String identifier)
			throws SQLException {

		if (profile == null)
			throw new IllegalArgumentException(
					"Argument 'profile' must not be null.");
		else if (identifier == null || identifier.length() == 0
				|| identifier.length() > 100)
			throw new IllegalArgumentException(
					"Argument 'identifier' must not be null or emtpy and should have at maximum 100 characters.");
		// no else.

		probablyCreateEnergyProfileTable();

		/* Store the values in the table. */

		if (storeEProfileStatement == null)
			storeEProfileStatement = dbConnection
					.prepareStatement("INSERT INTO EProfile VALUES (NULL, ?, ?, ?, ?)");
		// no else.

		storeEProfileStatement.setString(1, identifier);
		storeEProfileStatement.setDouble(2, profile.getConsumedEnergy());
		storeEProfileStatement.setDouble(3, profile.getStartedNanoTime());
		storeEProfileStatement.setDouble(4, profile.getStopNanoTime());

		storeEProfileStatement.executeUpdate();

		/* Get the id. */
		if (callIdentityStatement == null)
			callIdentityStatement = dbConnection.prepareCall("CALL IDENTITY()");
		// no else.

		ResultSet rs = callIdentityStatement.executeQuery();

		if (!rs.next())
			throw new SQLException("Unknown identifier found.");
		// no else.

		long profileID = rs.getLong(1);

		for (PowerRate value : ((SimpleEnergyProfile) profile)
				.getMeasuredValues())
			storePowerRate(value, profileID);
		// end for.
	}

	/**
	 * Requests the {@link EnergyProfileHistory} DB for a given
	 * {@link EnergyProfile} identifier at returns all the {@link PowerRate}s
	 * belonging to this {@link EnergyProfile}.
	 * 
	 * @param profile_id
	 *            The identifier of the {@link EnergyProfile}s whose
	 *            {@link PowerRate}s shall be returned.
	 * @return A {@link List} of {@link EnergyProfile}s.
	 * @throws SQLException
	 *             Thrown, if the DB access fails.
	 */
	protected List<PowerRate> getPowerRatesFromHistory(Long profile_id)
			throws SQLException {

		if (profile_id < 0)
			throw new IllegalArgumentException(
					"Argument 'profile_id' must be greater than 0.");
		// no else.

		/* Probably create the table. */
		if (selectEMeasuringValues == null) {
			StringBuffer query = new StringBuffer();
			query.append("SELECT chargingRate, timestamp, powerAdapterOnline ");
			query.append("FROM EMeasuringValue ");
			query.append("WHERE profile_id = ? ");
			selectEMeasuringValues = dbConnection.prepareStatement(query
					.toString());
		}

		selectEMeasuringValues.setLong(1, profile_id);
		ResultSet rs = selectEMeasuringValues.executeQuery();

		List<PowerRate> result = new ArrayList<PowerRate>();

		while (rs.next()) {
			long chargingRate, timestamp;
			boolean adapterOnline;

			chargingRate = rs.getLong(1);
			timestamp = rs.getLong(2);
			adapterOnline = rs.getBoolean(3);

			RestoredPowerRate newValue = new RestoredPowerRate();
			newValue.setPowerRate(chargingRate);
			newValue.setTimeStamp(timestamp);
			newValue.setPowerAdapterOnlineEnabled(adapterOnline);
			result.add(newValue);
		}
		// end while.

		return result;
	}

	/**
	 * Helper method that creates a table to store {@link EnergyProfile}s if it
	 * does not exist yet.
	 * 
	 * @throws SQLException
	 */
	protected void probablyCreateEnergyProfileTable() throws SQLException {
		/* Probably create the table. */
		if (createTableEProfilesStatement == null) {
			createTableEProfilesStatement = dbConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS EProfile (profile_id BIGINT IDENTITY, identifier VARCHAR(100), energy REAL, startTime BIGINT, endTime BIGINT)");
			createTableEProfilesStatement.executeUpdate();
		}
		// no else.
	}

	/**
	 * Helper method that creates a table to store {@link PowerRate}s if it does
	 * not exist yet.
	 * 
	 * @throws SQLException
	 */
	protected void probablyCreatePowerRateTable() throws SQLException {

		probablyCreateEnergyProfileTable();

		/* Probably create the table. */
		if (createTableEMeasuringValueStatement == null) {
			StringBuffer query = new StringBuffer();
			query.append("CREATE TABLE IF NOT EXISTS EMeasuringValue ");
			query.append("(value_id BIGINT IDENTITY, ");
			query.append("profile_id BIGINT, ");
			query.append("chargingRate BIGINT, ");
			query.append("timestamp BIGINT, ");
			query.append("powerAdapterOnline BOOLEAN)");
			createTableEMeasuringValueStatement = dbConnection
					.prepareStatement(query.toString());

			createTableEMeasuringValueStatement.executeUpdate();

			try {
				if (createConstraintFK1Statement == null) {
					createConstraintFK1Statement = dbConnection
							.prepareCall("ALTER TABLE EMeasuringValue ADD CONSTRAINT fk1 FOREIGN KEY (profile_id) REFERENCES EProfile (profile_id) ON DELETE CASCADE");
					createConstraintFK1Statement.executeUpdate();
				}
				// no else.
			} catch (SQLException e) {
				/* If catched, the constraints already exist. */
			}
		}
		// no else.
	}

	/**
	 * Stores an {@link PowerRate}.
	 * 
	 * @param value
	 *            The {@link PowerRate}.
	 * @param profileID
	 *            The identifier (forreign key) of the {@link EnergyProfile} the
	 *            {@link PowerRate} belongs to.
	 * @throws SQLException
	 */
	protected void storePowerRate(PowerRate value, long profileID)
			throws SQLException {

		if (value == null)
			throw new IllegalArgumentException(
					"Argument 'value' must not be null.");
		else if (profileID <= 0)
			throw new IllegalArgumentException(
					"Argument 'profileID' must not be negative.");
		// no else.

		probablyCreatePowerRateTable();

		/* Store the values in the table. */

		if (storeEMeasuringValueStatement == null)
			storeEMeasuringValueStatement = dbConnection
					.prepareStatement("INSERT INTO EMeasuringValue VALUES (NULL, ?, ?, ?, ?)");
		// no else.

		storeEMeasuringValueStatement.setLong(1, profileID);
		storeEMeasuringValueStatement.setDouble(2, value.getPowerRate());
		storeEMeasuringValueStatement.setDouble(3, value.getTimeStamp());
		storeEMeasuringValueStatement.setBoolean(4,
				value.isPowerAdapterOnline());

		storeEMeasuringValueStatement.executeUpdate();
	}
}
