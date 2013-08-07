package org.qualitune.jouleunit.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.qualitune.jouleunit.core.prefs.JouleUnitPreferences;
import org.qualitune.jouleunit.ui.JouleUnitUiPlugIn;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 * 
 * @author PiQ
 * @author Claas Wilke
 */
public class MainPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public MainPreferencePage() {
		super(GRID);
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		// addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH,
		// "&Directory preference:", getFieldEditorParent()));
		// addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN,
		// "&Boolean preference", getFieldEditorParent()));
		// addField(new RadioGroupFieldEditor(PreferenceConstants.P_CHOICE,
		// "An example of a multiple-choice preference", 1, new String[][] { {
		// "&Choice 1", "choice1" }, { "C&hoice 2", "choice2" } },
		// getFieldEditorParent()));

		CLabel heading1 = new CLabel(getFieldEditorParent(), SWT.CENTER
				| SWT.SHADOW_IN | SWT.FILL);
		heading1.setText("General Settings");
		heading1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		CLabel placeholder1 = new CLabel(getFieldEditorParent(), SWT.RIGHT
				| SWT.SHADOW_NONE);
		placeholder1.setText("");
		placeholder1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addField(new StringFieldEditor(
				JouleUnitPreferences.P_STRING_LIMIT_ENTRIES_TO,
				"How many Entries should be loaded into table: (0 = unlimted)",
				getFieldEditorParent()));
		addField(new StringFieldEditor(
				JouleUnitPreferences.P_STRING_LIMIT_DAYS_BEFORE_DELETE,
				"After how many days results should be removed (0 = never):",
				getFieldEditorParent()));
		addField(new StringFieldEditor(
				JouleUnitPreferences.P_STRING_LIMIT_BAR_ENTRIES_TO,
				"How many Entries should be loaded into bar-chart: (0 = unlimted)",
				getFieldEditorParent()));

		CLabel wholeLineFree = new CLabel(getFieldEditorParent(), SWT.RIGHT
				| SWT.SHADOW_NONE);
		wholeLineFree.setText("");
		wholeLineFree.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		CLabel wholeLineFree2 = new CLabel(getFieldEditorParent(), SWT.RIGHT
				| SWT.SHADOW_NONE);
		wholeLineFree2.setText("");
		wholeLineFree2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		CLabel heading2 = new CLabel(getFieldEditorParent(), SWT.CENTER
				| SWT.SHADOW_IN | SWT.FILL);
		heading2.setText("Server Settings");
		heading2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		CLabel placeholder2 = new CLabel(getFieldEditorParent(), SWT.RIGHT
				| SWT.SHADOW_NONE);
		placeholder2.setText("");
		placeholder2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addField(new StringFieldEditor(JouleUnitPreferences.P_STRING_SERVER_IP,
				"Server-IP for remote-deployment", getFieldEditorParent()));
		addField(new StringFieldEditor(
				JouleUnitPreferences.P_STRING_SERVER_PORT,
				"Server-Port for remote-deployment", getFieldEditorParent()));

		addField(new StringFieldEditor(
				JouleUnitPreferences.P_STRING_DB_SERVER_IP,
				"DB Server IP for remote deployment", getFieldEditorParent()));
		addField(new StringFieldEditor(
				JouleUnitPreferences.P_STRING_DB_SERVER_PORT,
				"DB Server Port for remote deployment", getFieldEditorParent()));
		addField(new StringFieldEditor(
				JouleUnitPreferences.P_STRING_DB_SERVER_LOGIN,
				"DB Server Login for remote deployment", getFieldEditorParent()));
		addField(new StringFieldEditor(
				JouleUnitPreferences.P_STRING_DB_SERVER_PASSWORD,
				"DB Server Password for remote deployment",
				getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(JouleUnitUiPlugIn.getDefault().getPreferenceStore());
		setDescription("QualiTune Settings");
	}

}