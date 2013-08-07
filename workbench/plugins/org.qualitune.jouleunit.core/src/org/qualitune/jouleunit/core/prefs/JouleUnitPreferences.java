package org.qualitune.jouleunit.core.prefs;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.qualitune.jouleunit.core.JouleUnitPlugin;

/**
 * Provides access to the {@link IEclipsePreferences} of the
 * {@link JouleUnitPlugin}.
 * 
 * @author Claas Wilke
 */
public class JouleUnitPreferences {

	/** Identifier of the preferences file to be used. */
	public static final String PREFERENCE_IDENTIFIER = "org.qualitune.jouleunit.ui";

	public static final String P_STRING_LIMIT_ENTRIES_TO = "stringPrefEntries";

	public static final String P_STRING_LIMIT_DAYS_BEFORE_DELETE = "stringPrefDays";

	public static final String P_STRING_LIMIT_BAR_ENTRIES_TO = "stringPrefEntriesBar";

	/** The IP of the profiling server. */
	public static final String P_STRING_SERVER_IP = "stringPrefServerIP";

	/** The default IP of the profiling server. */
	public static final String P_STRING_SERVER_IP_DEFAULT = "127.0.0.1";

	/** The port of the profiling service. */
	public static final String P_STRING_SERVER_PORT = "stringPrefServerPort";

	/** The default port of the profiling service. */
	public static final int P_STRING_SERVER_PORT_DEFAULT = 8080;

	/** The IP of the DB server. */
	public static final String P_STRING_DB_SERVER_IP = "stringPrefDbServerIP";

	/** The default IP of the DB server. */
	public static final String P_STRING_DB_SERVER_IP_DEFAULT = "127.0.0.1";

	/** The port of the DB server. */
	public static final String P_STRING_DB_SERVER_PORT = "stringPrefDbServerPort";

	/** The default port of the DB server. */
	public static final int P_STRING_DB_SERVER_PORT_DEFAULT = 3306;

	/** The login of the DB server. */
	public static final String P_STRING_DB_SERVER_LOGIN = "stringPrefDbServerLogin";

	/** The default login of the DB server. */
	public static final String P_STRING_DB_SERVER_LOGIN_DEFAULT = "root";

	/** The password of the DB server. */
	public static final String P_STRING_DB_SERVER_PASSWORD = "stringPrefDbServerPassword";

	/** The password port of the DB server. */
	public static final String P_STRING_DB_SERVER_PASSWORD_DEFAULT = "";

}
