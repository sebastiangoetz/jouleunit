package org.qualitune.jouleunit.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.qualitune.jouleunit.core.prefs.JouleUnitPreferences;
import org.qualitune.jouleunit.ui.JouleUnitUiPlugIn;

/**
 * Class used to initialize default preference values.
 * 
 * @author Claas Wilke
 * @author PiQ
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = JouleUnitUiPlugIn.getDefault()
				.getPreferenceStore();
		store.setDefault(JouleUnitPreferences.P_STRING_LIMIT_ENTRIES_TO, "500");
		store.setDefault(JouleUnitPreferences.P_STRING_LIMIT_BAR_ENTRIES_TO,
				"50");
		store.setDefault(
				JouleUnitPreferences.P_STRING_LIMIT_DAYS_BEFORE_DELETE, "15");

		store.setDefault(JouleUnitPreferences.P_STRING_SERVER_IP,
				JouleUnitPreferences.P_STRING_SERVER_IP_DEFAULT);
		store.setDefault(JouleUnitPreferences.P_STRING_SERVER_PORT,
				JouleUnitPreferences.P_STRING_SERVER_PORT_DEFAULT);
		store.setDefault(JouleUnitPreferences.P_STRING_DB_SERVER_IP,
				JouleUnitPreferences.P_STRING_DB_SERVER_IP_DEFAULT);
		store.setDefault(JouleUnitPreferences.P_STRING_DB_SERVER_PORT,
				JouleUnitPreferences.P_STRING_DB_SERVER_PORT_DEFAULT);
		store.setDefault(JouleUnitPreferences.P_STRING_DB_SERVER_LOGIN,
				JouleUnitPreferences.P_STRING_DB_SERVER_LOGIN_DEFAULT);
		store.setDefault(JouleUnitPreferences.P_STRING_DB_SERVER_PASSWORD,
				JouleUnitPreferences.P_STRING_DB_SERVER_PASSWORD_DEFAULT);
	}
}
