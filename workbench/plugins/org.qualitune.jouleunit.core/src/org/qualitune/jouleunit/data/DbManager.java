package org.qualitune.jouleunit.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Date;

import org.eclipse.core.runtime.Platform;
import org.qualitune.jouleunit.SimpleEnergyProfile;
import org.qualitune.jouleunit.coordinator.TestSuiteProfile;
import org.qualitune.jouleunit.core.prefs.JouleUnitPreferences;
import org.qualitune.jouleunit.persist.RestoredPowerRate;

/**
 * Helper class to read and store APK files and profiling data from and to the
 * DB.
 * 
 * @author maquiz, Claas Wilke
 */
public class DbManager {

	/** Connection URL to the DB. */
	private static String connectionURL = "jdbc:mysql://"
			+ Platform.getPreferencesService().getString(
					JouleUnitPreferences.PREFERENCE_IDENTIFIER,
					JouleUnitPreferences.P_STRING_DB_SERVER_IP,
					JouleUnitPreferences.P_STRING_DB_SERVER_IP_DEFAULT, null)
			+ ":"
			+ Platform.getPreferencesService().getInt(
					JouleUnitPreferences.PREFERENCE_IDENTIFIER,
					JouleUnitPreferences.P_STRING_DB_SERVER_PORT,
					JouleUnitPreferences.P_STRING_DB_SERVER_PORT_DEFAULT, null)
			+ "/qmark";

	/** DB login. */
	private static String login = Platform.getPreferencesService().getString(
			JouleUnitPreferences.PREFERENCE_IDENTIFIER,
			JouleUnitPreferences.P_STRING_DB_SERVER_LOGIN,
			JouleUnitPreferences.P_STRING_DB_SERVER_LOGIN_DEFAULT, null);

	/** DB Password. */
	private static String password = Platform.getPreferencesService()
			.getString(JouleUnitPreferences.PREFERENCE_IDENTIFIER,
					JouleUnitPreferences.P_STRING_DB_SERVER_PASSWORD,
					JouleUnitPreferences.P_STRING_DB_SERVER_PASSWORD_DEFAULT,
					null);

	/** The current DB {@link Connection} if any. */
	private Connection connection = null;

	/** The {@link PrintStream} used to log events of this {@link DbManager}. */
	private PrintStream console;

	/** The current {@link PreparedStatement} if any. */
	private PreparedStatement statement = null;

	/**
	 * Creates a new {@link DbManager}.
	 * 
	 * @param console
	 *            The {@link PrintStream} used to log events of this
	 *            {@link DbManager}.
	 */
	public DbManager(PrintStream console) {
		this.console = console;
	}

	/**
	 * Helper method reading a TestRun's console from the DB and propagates it
	 * to the configured console of this {@link DbManager}.
	 * 
	 * @param testRunID
	 *            The ID of the TestRun whose console shall be read.
	 * @return The read console output as a {@link String}.
	 */
	public String readTestRunConsole(int testRunID) {

		try {
			createConnection();

			/* Read test case data. */
			TestSuiteProfile result = new TestSuiteProfile();
			result.setTimestampCorrectionEnabled(false);

			String query = "SELECT console FROM TestRun WHERE testRunID = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, testRunID);

			ResultSet rs = statement.executeQuery();

			String readConsole = "";
			if (rs.next()) {
				readConsole = rs.getString(1);
				console.println("\nRestored Console for Test Run " + testRunID
						+ ":\n\n" + readConsole);
			}
			// end while.

			closeConnection();

			return readConsole;
		}

		catch (Exception e) {
			reportError("Error during read of TestRun data: " + e.getMessage());
			e.printStackTrace();

			try {
				closeConnection();
			}

			catch (Exception e2) {
				reportError("Error during read of TestRun data: "
						+ e2.getMessage());
				e2.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Helper method reading a {@link TestSuiteProfile} from the DB for a given
	 * TestRun ID.
	 * 
	 * @param testRunID
	 *            The ID of the TestRun that shall be read.
	 * @return The read {@link TestSuiteProfile}.
	 */
	public TestSuiteProfile readTestSuiteProfile(int testRunID) {

		try {
			createConnection();

			/* Read test case data. */
			TestSuiteProfile result = new TestSuiteProfile();
			result.setTimestampCorrectionEnabled(false);

			String query = "SELECT name, tag, `start`, `stop`, `result`, testCaseID FROM TestCase WHERE testRunID = ? ORDER BY `start` ASC;";
			statement = connection.prepareStatement(query);
			statement.setInt(1, testRunID);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				DbTestCaseProfile tcProfile = new DbTestCaseProfile();
				tcProfile.setId(rs.getString(1));
				tcProfile.setTag(rs.getString(2));
				tcProfile.setStartTime(rs.getLong(3));
				tcProfile.setEndTime(rs.getLong(4));
				tcProfile.setFailed((rs.getInt(5) == 0 ? true : false));
				tcProfile.setDbID(rs.getLong(6));
				result.addTestCase(tcProfile);
			}
			// end while.

			/* Read energy profile. */
			SimpleEnergyProfile energyProfile = new SimpleEnergyProfile();
			query = "SELECT power, `time` FROM ResultPowerRate WHERE testRunID = ? ORDER BY `time` ASC;";
			statement = connection.prepareStatement(query);
			statement.setInt(1, testRunID);

			rs = statement.executeQuery();

			while (rs.next()) {
				RestoredPowerRate powerRate = new RestoredPowerRate();
				powerRate.setPowerRate(rs.getLong(1));
				powerRate.setTimeStamp(rs.getLong(2) * 1000000);
				energyProfile.addPowerRateValue(powerRate);
			}
			// end while.

			result.setEnergyProfile(energyProfile);

			/* Read CPU data. */
			query = "SELECT MAX(`number`) FROM ResultCPU WHERE testRunID = ?;";
			statement = connection.prepareStatement(query);
			statement.setInt(1, testRunID);

			rs = statement.executeQuery();

			if (rs.next()) {
				int numberOfCPUs = rs.getInt(1);

				query = "SELECT frequence, `number`, `time` FROM ResultCPU WHERE testRunID = ? ORDER BY `time`, `number` ASC;";
				statement = connection.prepareStatement(query);
				statement.setInt(1, testRunID);

				rs = statement.executeQuery();

				Long frequencies[] = null;
				Long lastTimestamp = null;

				while (rs.next()) {
					long currentTimestamp = rs.getLong(3);
					int cpuNumber = rs.getInt(2);

					if (null != lastTimestamp
							&& currentTimestamp == lastTimestamp) {
						frequencies[cpuNumber - 1] = rs.getLong(1);
					}

					else {
						if (null != lastTimestamp)
							result.addCpuFrequencies(lastTimestamp, frequencies);
						// no else.

						frequencies = new Long[numberOfCPUs];

						for (int index = 0; index < numberOfCPUs; index++)
							frequencies[index] = 0l;
						// end for.

						lastTimestamp = currentTimestamp;
					}
				}
				// end while.

				if (null != lastTimestamp)
					result.addCpuFrequencies(lastTimestamp, frequencies);
				// no else.
			}
			/* No else (no CPU data found). */

			/* Read Display data. */
			query = "SELECT brightness, `time` FROM ResultDisplay WHERE testRunID = ? ORDER BY `time` ASC;";
			statement = connection.prepareStatement(query);
			statement.setInt(1, testRunID);

			rs = statement.executeQuery();

			while (rs.next())
				result.addLcdBrightness(rs.getLong(2), rs.getLong(1));
			// end while.

			/* Read WiFi data. */
			query = "SELECT traffic, `time` FROM ResultWifi WHERE testRunID = ? ORDER BY `time` ASC;";
			statement = connection.prepareStatement(query);
			statement.setInt(1, testRunID);

			rs = statement.executeQuery();

			while (rs.next())
				result.addWiFiTraffic(rs.getLong(2), rs.getLong(1));
			// end while.

			closeConnection();

			return result;
		}

		catch (Exception e) {
			reportError("Error during read of TestRun data: " + e.getMessage());
			e.printStackTrace();

			try {
				closeConnection();
			}

			catch (Exception e2) {
				reportError("Error during read of TestRun data: "
						+ e2.getMessage());
				e2.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 
	 * Helper method to save a background service test run in the DB to trigger
	 * its execution afterwards. Returns the ID of the created test run entry.
	 * 
	 * @param testRunID
	 *            The ID of the test run this background test run belongs to.
	 * @param setUpTest
	 *            Name of the test case used for setup.
	 * @param tearDownTest
	 *            Name of the test case used for tear down.
	 * @param serviceName
	 *            Name of the service to be tested.
	 * @param duration
	 *            Duration of the test run.
	 * @param displayState
	 *            State of the display (can be 'OFF', 'ON', or 'BOTH').
	 * @return The ID of the created test run entry.
	 */
	public int saveBackgroundTestRun(int testRunID, String setUpTest,
			String tearDownTest, String serviceName, int duration,
			String displayState) {

		try {
			createConnection();

			/* Save BeckgroundTestRun. */
			String query = "INSERT INTO BackgroundTestRun (testRunID, setUpTest, tearDownTest, serviceName, duration, display) VALUES (?,?,?,?,?,?);";
			statement = connection.prepareStatement(query);

			statement.setInt(1, testRunID);

			if (null != setUpTest && setUpTest.trim().length() > 0)
				statement.setString(2, setUpTest);
			else
				statement.setNull(2, Types.VARCHAR);

			if (null != tearDownTest && tearDownTest.trim().length() > 0)
				statement.setString(3, tearDownTest);
			else
				statement.setNull(3, Types.VARCHAR);

			if (null != serviceName && serviceName.trim().length() > 0)
				statement.setString(4, serviceName);
			else
				statement.setNull(4, Types.VARCHAR);

			statement.setInt(5, duration);

			statement.setString(6, displayState.toLowerCase());

			int status = statement.executeUpdate();

			if (status <= 0) {
				reportError("Error during saving BackgroundTestRun entry.");
				closeConnection();
				return -1;
			}
			// no else.

			/* Read TestRun ID. */
			query = "SELECT MAX(backgroundTestRunID) FROM BackgroundTestRun;";
			statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();

			int backgroundTesRunID = 0;

			if (rs.next()) {
				backgroundTesRunID = rs.getInt(1);
			}

			else {
				reportError("Error during read of BackgroundTestRun ID.");
				closeConnection();
				return -1;
			}

			closeConnection();
			return backgroundTesRunID;
		}

		catch (Exception e) {
			reportError("Error during export of profiling data: "
					+ e.getMessage());
			e.printStackTrace();

			try {
				closeConnection();
			}

			catch (Exception e2) {
				reportError("Error during export of profiling data: "
						+ e2.getMessage());
				e2.printStackTrace();
			}
		}

		return -1;
	}

	/**
	 * Helper method to save a test run in the DB to trigger its execution
	 * afterwards. Returns the ID of the created test run entry.
	 * 
	 * @param autApkFile
	 *            The APK of the application under test.
	 * @param autPackageID
	 *            The package ID of the application under test.
	 * @param testApkFile
	 *            The APK of the test suite to be executed.
	 * @param testPackageID
	 *            The package ID of the test suite to be executed.
	 * @param hwProfilingEnabled
	 *            Whether or not to profile hardware utilization.
	 * @param idleTime
	 *            The time in seconds to profile idle power consumption.
	 * @param noOfRuns
	 *            The number of test runs to be performed.
	 * @return The ID of the created test run entry.
	 */
	public int saveTestRun(File autApkFile, String autPackageID,
			File testApkFile, String testPackageID, boolean hwProfilingEnabled,
			int idleTime, int noOfRuns, String testScript) {

		try {
			createConnection();

			FileInputStream fis;

			/* Save app entry. */
			String query = "INSERT INTO App (packageID, genreID, paymentPlanID, vendorID) VALUES (?,?,?,?);";
			statement = connection.prepareStatement(query);

			statement.setString(1, autPackageID);
			/* TODO Should be extracted from project settings. */
			statement.setInt(2, 1);
			/* TODO Should be extracted from project settings. */
			statement.setInt(3, 1);
			/* TODO Should be extracted from project settings. */
			statement.setInt(4, 1);

			int status = statement.executeUpdate();

			if (status <= 0) {
				reportError("Error during saving the app entry.");
				closeConnection();
				return -1;
			}
			// no else.

			/* Get app ID. */
			query = "SELECT MAX(appID) FROM app;";
			statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();

			int appID = 0;

			if (rs.next()) {
				appID = rs.getInt(1);
			}

			else {
				reportError("Error during read of binary content ID");
				closeConnection();
				return -1;
			}

			/* Save binary for version entry. */
			query = "INSERT INTO BinaryContent (content, contentType, name) VALUES (?,?,?);";
			statement = connection.prepareStatement(query);

			fis = new FileInputStream(autApkFile);
			statement.setBinaryStream(1, (InputStream) fis,
					(int) (autApkFile.length()));
			statement.setString(3, "apk");
			statement.setString(2, autApkFile.getName());

			status = statement.executeUpdate();
			fis.close();

			if (status <= 0) {
				reportError("Error during saving the BinaryContent for version entry.");
				closeConnection();
				return -1;
			}
			// no else.

			/* Get binary ID. */
			query = "SELECT MAX(binaryID) FROM BinaryContent;";
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();

			int binaryID = 0;

			if (rs.next()) {
				binaryID = rs.getInt(1);
			}

			else {
				reportError("Error during read of binary content ID");
				closeConnection();
				return -1;
			}

			/* Get visibility type ID. */
			/* TODO Extract type name to constant. */
			query = "SELECT MAX(typeID) FROM Type WHERE name = 'versionPrivate';";
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();

			int visibilityTypeID = 0;

			if (rs.next()) {
				visibilityTypeID = rs.getInt(1);
			}

			else {
				reportError("Error during read of visibility type ID");
				closeConnection();
				return -1;
			}

			/* Save version entry. */
			query = "INSERT INTO Version (apkBinaryID, appID, vendorVersionID, visibilityTypeID) VALUES (?,?,?,?);";
			statement = connection.prepareStatement(query);

			statement.setInt(1, binaryID);
			statement.setInt(2, appID);
			/* TODO Should be extracted from project settings. */
			statement.setString(3, "application under test");
			statement.setInt(4, visibilityTypeID);

			status = statement.executeUpdate();
			fis.close();

			if (status <= 0) {
				reportError("Error during saving the Version entry.");
				closeConnection();
				return -1;
			}
			// no else.

			/* Get version ID. */
			query = "SELECT MAX(versionID) FROM Version;";
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();

			int versionID = 0;

			if (rs.next()) {
				versionID = rs.getInt(1);
			}

			else {
				reportError("Error during read of version ID");
				closeConnection();
				return -1;
			}

			/* Save binary for test suite entry. */
			if (null != testApkFile) {
				query = "INSERT INTO BinaryContent (content, contentType, name) VALUES (?,?,?);";
				statement = connection.prepareStatement(query);

				fis = new FileInputStream(testApkFile);
				statement.setBinaryStream(1, (InputStream) fis,
						(int) (testApkFile.length()));
				statement.setString(3, "apk");
				statement.setString(2, testApkFile.getName());

				status = statement.executeUpdate();
				fis.close();

				if (status <= 0) {
					reportError("Error during saving the BinaryContent for test suite entry.");
					closeConnection();
					return -1;
				}
				// no else.

				/* Get binary ID. */
				query = "SELECT MAX(binaryID) FROM BinaryContent;";
				statement = connection.prepareStatement(query);
				rs = statement.executeQuery();

				binaryID = 0;

				if (rs.next()) {
					binaryID = rs.getInt(1);
				}

				else {
					reportError("Error during read of binary content ID");
					closeConnection();
					return -1;
				}
			}
			// no else (not test apk).

			/* Save test suite entry. */
			int testSuiteID = 0;

			query = "INSERT INTO TestSuite (name, packageID, testScript, apkBinaryID, versionID) VALUES (?,?,?,?,?);";
			statement = connection.prepareStatement(query);

			/* TODO Should be extracted from project settings. */
			statement.setString(1, "test suite");
			if (null != testPackageID && testPackageID.length() > 0)
				statement.setString(2, testPackageID);
			else
				statement.setNull(2, Types.VARCHAR);

			if (null != testScript && testScript.length() > 0)
				statement.setString(3, testScript);
			else
				statement.setNull(3, Types.VARCHAR);

			if (null != testApkFile)
				statement.setInt(4, binaryID);
			else
				statement.setNull(4, Types.INTEGER);

			statement.setInt(5, versionID);

			status = statement.executeUpdate();
			fis.close();

			if (status <= 0) {
				reportError("Error during saving TestSuite entry.");
				closeConnection();
				return -1;
			}
			// no else.

			/* Get test suite ID. */
			query = "SELECT MAX(testSuiteID) FROM TestSuite;";
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();

			if (rs.next()) {
				testSuiteID = rs.getInt(1);
			}

			else {
				reportError("Error during read of TestSuite ID.");
				closeConnection();
				return -1;
			}

			/* Get test run type ID. */
			/* TODO Extract type name to constant. */
			query = "SELECT MAX(typeID) FROM Type WHERE name = 'scheduledTestRun';";
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();

			int testRunTypeID = 0;

			if (rs.next()) {
				testRunTypeID = rs.getInt(1);
			}

			else {
				reportError("Error during read of test run type ID");
				closeConnection();
				return -1;
			}

			/* Save TestRun. */
			query = "INSERT INTO TestRun (idleTime, hwProfiling, numberOfRuns, testScript, deviceID, testSuiteID, typeID) VALUES (?,?,?,?,?,?,?);";
			statement = connection.prepareStatement(query);

			statement.setInt(1, idleTime);
			statement.setInt(2, (hwProfilingEnabled ? 1 : 0));
			statement.setInt(3, noOfRuns);

			if (null != testScript && testScript.length() > 0)
				statement.setString(4, testScript);
			else
				statement.setNull(4, Types.VARCHAR);

			/* TODO What about device ID. */
			statement.setInt(5, 1);
			statement.setInt(6, testSuiteID);
			statement.setInt(7, testRunTypeID);

			status = statement.executeUpdate();

			if (status <= 0) {
				reportError("Error during saving TestRun entry.");
				closeConnection();
				return -1;
			}
			// no else.

			/* Read TestRun ID. */
			query = "SELECT MAX(testRunID) FROM TestRun;";
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();

			int tesRunID = 0;

			if (rs.next()) {
				tesRunID = rs.getInt(1);
			}

			else {
				reportError("Error during read of TestRun ID.");
				closeConnection();
				return -1;
			}

			closeConnection();
			return tesRunID;
		}

		catch (Exception e) {
			reportError("Error during export of profiling data: "
					+ e.getMessage());
			e.printStackTrace();

			try {
				closeConnection();
			}

			catch (Exception e2) {
				reportError("Error during export of profiling data: "
						+ e2.getMessage());
				e2.printStackTrace();
			}
		}

		return -1;
	}

	/**
	 * Helper method to update a {@link DbTestCaseProfile} in the DB.
	 * 
	 * @param testCaseProfile
	 *            The {@link DbTestCaseProfile} to be updated.
	 */
	public void updateTestCase(DbTestCaseProfile testCaseProfile) {

		try {
			createConnection();

			/* Save version entry. */
			String query = "UPDATE TestCase SET `result` = ? WHERE testCaseID = ?";
			statement = connection.prepareStatement(query);

			statement.setInt(1, testCaseProfile.isFailed() ? 0 : 1);
			statement.setLong(2, testCaseProfile.getDbID());

			int status = statement.executeUpdate();

			if (status <= 0) {
				reportError("Error during update of TestCase entry.");
			}
			// no else.

			closeConnection();
		}

		catch (Exception e) {
			reportError("Error during update of TestCase entry: "
					+ e.getMessage());
			e.printStackTrace();

			try {
				closeConnection();
			}

			catch (Exception e2) {
				reportError("Error during update of TestCase entry: "
						+ e2.getMessage());
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Helper method to create a {@link Connection} to the DB.
	 * 
	 * @throws Exception
	 */
	private void createConnection() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, login,
					password);
		}

		catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Helper method to close the current DB connection.
	 * 
	 * @throws Exception
	 */
	private void closeConnection() throws Exception {
		try {
			if (null != statement) {
				statement.close();
				statement = null;
			}
			// no else.

			if (null != connection) {
				connection.close();
				connection = null;
			}
			// no else.
		}

		catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Logs an error to the console of this {@link DbManager}.
	 * 
	 * @param msg
	 *            The error message.
	 */
	private void reportError(String msg) {
		console.println(new Date().toString() + " - Error: " + msg);
	}
}
