package org.qualitune.jouleunit.ui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.WorkbenchException;
import org.qualitune.jouleunit.coordinator.TestCaseProfile;
import org.qualitune.jouleunit.core.prefs.JouleUnitPreferences;

public class Util {

	private static Util instance = null;

	/**
	 * how many entries should be displayed
	 */
	public static int LIMIT_ENTRIES_TO = Integer
			.parseInt(getPrefValue(JouleUnitPreferences.P_STRING_LIMIT_ENTRIES_TO));

	/**
	 * how many entries should be displayed in bar-chart
	 */
	public static int LIMIT_BAR_ENTRIES_TO = Integer
			.parseInt(getPrefValue(JouleUnitPreferences.P_STRING_LIMIT_BAR_ENTRIES_TO));

	/**
	 * when a test-case-entry should be removed in days
	 */
	public static int LIMIT_DAYS_BEFORE_DELETE = Integer
			.parseInt(getPrefValue(JouleUnitPreferences.P_STRING_LIMIT_DAYS_BEFORE_DELETE));

	/**
	 * server-ip for remote
	 */
	public static String SERVER_IP = getPrefValue(JouleUnitPreferences.P_STRING_SERVER_IP);

	/**
	 * server-port for remote
	 */
	public static int SERVER_PORT = Integer
			.parseInt(getPrefValue(JouleUnitPreferences.P_STRING_SERVER_PORT));

	/**
	 * just contains the testcases, that match a certain ID
	 */
	private static List<HistoryTestResultObject> importedList;

	private static int oldImportListCounter;

	private static int oldLimitToCounter;

	private static String oldCompareID;

	private static Long oldLastModifiedTimestamp = 0l;

	private static Long lastModifiedTimestamp = 0l;

	/**
	 * list, that contains all the testcases which are imported by all files!
	 */
	private static List<HistoryTestResultObject> importedAllTestcaseList;

	private static boolean wasTheLastListReversed = true;

	/**
	 * Singleton-Instance
	 */
	private Util() {

	}

	public static Util getInstance() {
		if (instance == null) {
			Util instance = new Util();
			return instance;
		}
		return instance;
	}

	/**
	 * receive all Files in the history-Folder
	 * 
	 * @return
	 */
	private File[] getAllFilesInFolder() {
		File file = new File(requestPluginPath().toOSString()
				+ java.io.File.separator + "historyFiles");
		File[] files = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File arg0) {
				if (arg0.getName().endsWith("xml"))
					return true;
				return false;
			}
		});
		return files;
	}

	/**
	 * Order all history-files according to their modified-time-stamp
	 * 
	 * @param files
	 * @return
	 */
	private File[] sortFiles(File[] files) {
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f2, File f1) {
				return Long.valueOf(f1.lastModified()).compareTo(
						f2.lastModified());
			}
		});
		return files;
	}

	/**
	 * import all HistoryFiles from previous process read in all xml-files in
	 * folder and present them in the right table compared to a given ID and
	 * limitTo the last X elements
	 * 
	 * @return
	 */

	public List<HistoryTestResultObject> importTestResultFromHistory(
			String compareID, int limitTo, boolean reverse) {
		long startW = System.currentTimeMillis();

		List<HistoryTestResultObject> list = new ArrayList<HistoryTestResultObject>();
		File[] files = getAllFilesInFolder();

		// check, if the old importedList equals from size to the new to be
		// imported list to reduce operations
		if (importedList != null && oldImportListCounter == files.length
				&& oldLimitToCounter == limitTo
				&& compareID.equals(oldCompareID)) {
			System.out.println("Duration in ms: "
					+ (System.currentTimeMillis() - startW));
			return importedList;
		}

		System.out.println("Importing new Values...");

		// remember values for later comparison, if the same values are being
		// displayed
		oldImportListCounter = files.length;
		oldLimitToCounter = limitTo;
		oldCompareID = compareID;

		// receive last timestamp, cause this way is 10 times faster than
		// comapring and resorting whole list
		for (File f : files) {
			if (f.lastModified() > lastModifiedTimestamp) {
				lastModifiedTimestamp = f.lastModified();
			}
		}

		// if timestamps are eqaul and no files where created, just use the
		// list. that was created before
		if (importedAllTestcaseList != null
				&& oldLastModifiedTimestamp.equals(lastModifiedTimestamp)) {
			// dont init all files again, if they are the same and continue
		} else {
			files = sortFiles(files);

			// if too much files are found, try to delete files
			if (files.length > LIMIT_ENTRIES_TO) {
				deleteUnnecessaryFiles(LIMIT_DAYS_BEFORE_DELETE);

				// reinit all files
				files = getAllFilesInFolder();
				files = sortFiles(files);
			}

			HistoryTestResultObject htroPrevious = null;
			HashMap<String, HistoryTestResultObject> oldObjectsWithID = new HashMap<String, HistoryTestResultObject>();
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			MyXMLMemento xml = null;
			int counter = 1;

			for (File f : files) {
				Reader reader = null;
				try {
					fis = new FileInputStream(f);
					bis = new BufferedInputStream(fis);
					reader = new InputStreamReader(bis);
					xml = MyXMLMemento.createReadRoot(reader);
					//

					// new way of exported files
					if (xml.getChildren("testcase").length > 0) {
						// every single dataset in the xml-files has the same id
						String xmlID = xml.getChildren("testcase")[0]
								.getChildren("id")[0].getString("value");
						// if (!"".equals(compareID) &&
						// !compareID.equals(xmlID)) {
						// bis.close();
						// continue;
						// }
						// iterate over every single group of childrens
						for (int i = 0; i < xml.getChildren("testcase").length; i++) {
							HistoryTestResultObject htro = new HistoryTestResultObject();
							htro.setId(xmlID);
							htro.setFailed((xml.getChildren("testcase")[i]
									.getChild("failed").getBoolean("value")));
							htro.setStartTime(Long.parseLong((xml
									.getChildren("testcase")[i]
									.getChild("start").getString("value"))));
							htro.setEndTime(Long.parseLong((xml
									.getChildren("testcase")[i]
									.getChild("stop").getString("value"))));
							htro.setDuration(Long.parseLong((xml
									.getChildren("testcase")[i]
									.getChild("duration").getString("value"))));
							htro.setAvgPowerRate((xml.getChildren("testcase")[i]
									.getChild("avg_power_rate")
									.getString("value")));
							htro.setConsumption((xml.getChildren("testcase")[i]
									.getChild("consumption").getString("value")));
							htro.setFileSavedLocally((xml
									.getChildren("testcase")[i]
									.getChild("file_saved_locally")
									.getBoolean("value")));
							htro.setLocation((xml.getChildren("testcase")[i]
									.getChild("location_for_file")
									.getString("value")));
							htro.setModifiedOn(f.lastModified());
							htro.setNumber(counter++);
							htroPrevious = oldObjectsWithID.get(htro.getId());
							if (htroPrevious != null) {
								htro.setPreviousHTROAvgEnergyConsumption(Double
										.parseDouble(htroPrevious
												.getConsumption()));
								htro.setPreviousHTROAvgPowerRate(Double
										.parseDouble(htroPrevious
												.getAvgPowerRate()));
							}
							if ("".equals(htro.getLocation())) {
								htro.setLocation("N/A");
							}
							oldObjectsWithID.put(htro.getId(), htro);
							list.add(htro);
						}
					} else {
						// old way of imported files
						int i = 0;
						HistoryTestResultObject htro = new HistoryTestResultObject();
						String xmlID = xml.getChildren("id")[i]
								.getString("value");
						// if (!"".equals(compareID) &&
						// !compareID.equals(xmlID)) {
						// bis.close();
						// continue;
						// }
						htro.setId(xmlID);
						htro.setFailed((xml.getChildren("failed")[i]
								.getBoolean("value")));
						htro.setStartTime(Long.parseLong((xml
								.getChildren("start")[i].getString("value"))));
						htro.setEndTime(Long.parseLong((xml.getChildren("stop")[i]
								.getString("value"))));
						htro.setDuration(Long.parseLong((xml
								.getChildren("duration")[i].getString("value"))));
						htro.setAvgPowerRate((xml.getChildren("avg_power_rate")[i]
								.getString("value")));
						htro.setConsumption((xml.getChildren("consumption")[i]
								.getString("value")));
						htro.setFileSavedLocally((xml
								.getChildren("file_saved_locally")[i]
								.getBoolean("value")));
						htro.setLocation((xml.getChildren("location_for_file")[i]
								.getString("value")));
						htro.setModifiedOn(f.lastModified());
						htro.setNumber(counter++);
						htroPrevious = oldObjectsWithID.get(htro.getId());
						if (htroPrevious != null) {
							htro.setPreviousHTROAvgEnergyConsumption(Double
									.parseDouble(htroPrevious.getConsumption()));
							htro.setPreviousHTROAvgPowerRate(Double
									.parseDouble(htroPrevious.getAvgPowerRate()));
						}
						if ("".equals(htro.getLocation())) {
							htro.setLocation("N/A");
						}
						oldObjectsWithID.put(htro.getId(), htro);
						list.add(htro);
					}
				} catch (CoreException e) {
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			// remember all testcases/files
			importedAllTestcaseList = list;
			oldLastModifiedTimestamp = lastModifiedTimestamp;
		}
		// maybe reverse the list and limitTo X elements
		System.out.println("Complete size: " + list.size());
		System.out.println("Importing finished...");

		// filter whole list for certain compareID
		list = filterListForID(compareID, importedAllTestcaseList);
		if (limitTo == 0) {
			if (!reverse) {
				Collections.reverse(list);
			}
			setImportedList(list);
			System.out.println("Duration in ms: "
					+ (System.currentTimeMillis() - startW));
			return list;
		} else {
			List<HistoryTestResultObject> listLimit = new ArrayList<HistoryTestResultObject>();
			for (int i = 0; i < ((list.size() <= limitTo) ? list.size()
					: limitTo); i++) {
				listLimit.add(list.get(i));
			}
			if (!reverse) {
				Collections.reverse(listLimit);
			}
			setImportedList(listLimit);
			System.out.println("Duration in ms: "
					+ (System.currentTimeMillis() - startW));
			return listLimit;
		}
	}

	/**
	 * filter a given list for testcases with the same id
	 * 
	 * @param compareID
	 * @param importedAllTestcaseList
	 * @return
	 */
	private List<HistoryTestResultObject> filterListForID(String compareID,
			List<HistoryTestResultObject> importedAllTestcaseList) {
		if (compareID.equals(""))
			return importedAllTestcaseList;
		List<HistoryTestResultObject> list = new ArrayList<HistoryTestResultObject>();
		for (HistoryTestResultObject o : importedAllTestcaseList) {
			if (o.getId().equals(compareID)) {
				list.add(o);
			}
		}
		return list;
	}

	/**
	 * cut the resultlist to a given limitto value
	 * 
	 * @param compareID
	 * @param limitTo
	 * @param reverse
	 * @return
	 */
	public List<HistoryTestResultObject> trimTestResultFromHistoryList(
			String compareID, int limitTo, boolean reverse) {
		if (!compareID.equals(oldCompareID)) {
			return importTestResultFromHistory(compareID, limitTo, reverse);
		}
		List<HistoryTestResultObject> list = new ArrayList<HistoryTestResultObject>();
		list = importedList;
		if (wasTheLastListReversed == reverse) {
			reverse = true;
		} else {
			wasTheLastListReversed = reverse;
		}

		if (limitTo == 0) {
			if (!reverse) {
				Collections.reverse(list);
			}
			return list;
		} else {
			list = importedList.subList(
					0,
					importedList.size() > limitTo ? limitTo : importedList
							.size());
			// if(importedList.size() > limitTo) {
			// list = importedList.subList(importedList.size()-limitTo,
			// importedList.size());
			// } else {
			// list = importedList;
			// }

			// TODO WTF? if (!reverse) {
			// Collections.reverse(list);
			// }
			return list;
		}
	}

	public void setImportedList(List<HistoryTestResultObject> importedList) {
		this.importedList = importedList;
	}

	/**
	 * receive plugin-path for saved resultObjects in xml-format
	 * 
	 * @return
	 */

	private IPath requestPluginPath() {
		IPath iPath = JouleUnitUiPlugIn.getDefault().getStateLocation();
		IFolder iFolder = ResourcesPlugin.getWorkspace().getRoot()
				.getFolder(iPath);
		String partition = ResourcesPlugin.getWorkspace().getRoot()
				.getLocation().toString().substring(0, 2);
		IPath iPathTmp = new Path(iFolder.getFullPath().toOSString())
				.makeRelativeTo(ResourcesPlugin.getWorkspace().getRoot()
						.getLocation());
		new File(partition + iPathTmp.toOSString() + java.io.File.separator
				+ "historyFiles").mkdirs();
		iPathTmp = new Path(partition + iPathTmp.toOSString());
		return iPathTmp;
	}

	/**
	 * creates a testResult-file in xml-format in plugins-folder
	 * 
	 * @param testCaseProfiles
	 *            , locationForFile
	 */
	public void exportTestResultForHistory(
			List<TestCaseProfile> testCaseProfiles, String locationForFile) {
		boolean locallySaved = "".equals(locationForFile) ? false : true;
		for (TestCaseProfile tcp : testCaseProfiles) {
			// TestCaseProfile tcp = new TestCaseProfile();
			// tcp.setId("test");
			// tcp.setEndTime(System.currentTimeMillis());
			// tcp.setStartTime(System.currentTimeMillis());
			// tcp.setFailed(false);

			String filename = tcp.getId();

			File file = new File(requestPluginPath().toOSString()
					+ java.io.File.separator + "historyFiles"
					+ java.io.File.separator + filename + ".xml");
			MyXMLMemento memento = null;
			// create a new file with xml-tag, if file doenst exist
			if (file.exists()) {
				// memento = MyXMLMemento.createWriteRoot("jouleunit_testcases",
				// false);
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				Reader reader = null;
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bis = new BufferedInputStream(fis);
				reader = new InputStreamReader(bis);
				try {
					memento = MyXMLMemento.createReadRoot(reader);
				} catch (WorkbenchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				memento = MyXMLMemento.createWriteRoot("jouleunit_testcases",
						true);
			}
			int counter = memento.getChildren("testcase").length;
			System.out.println(counter);
			memento.createChild("testcase").putString("value", counter + "");
			memento.getChildren("testcase")[counter].createChild("id")
					.putString("value", tcp.getId());
			memento.getChildren("testcase")[counter].createChild("failed")
					.putBoolean("value", tcp.isFailed());
			memento.getChildren("testcase")[counter].createChild("start")
					.putString("value", tcp.getStartTime() + "");
			memento.getChildren("testcase")[counter].createChild("stop")
					.putString("value", tcp.getEndTime() + "");
			memento.getChildren("testcase")[counter].createChild("duration")
					.putString("value", tcp.getDuration() + "");
			memento.getChildren("testcase")[counter].createChild(
					"avg_power_rate").putString("value",
					tcp.getPowerRate() + "");
			memento.getChildren("testcase")[counter].createChild("consumption")
					.putString("value", tcp.getConsumedEnergy() + "");
			memento.getChildren("testcase")[counter].createChild(
					"file_saved_locally").putBoolean("value", locallySaved);
			memento.getChildren("testcase")[counter].createChild(
					"location_for_file").putString("value", locationForFile);

			try {
				FileOutputStream out = new FileOutputStream(file);
				memento.save(new PrintWriter(out));
//				System.out.println("Saved File at '" + file.getAbsolutePath()
//						+ "' started: "
//						+ new Date(tcp.getStartTime()).toString()
//						+ " Entries in File: "
//						+ memento.getChildren("testcase").length);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * import all HistoryFiles from previous process read in all xml-files in
	 * folder and present them in the right table limited to a given number
	 * 
	 * @return
	 */
	public List<HistoryTestResultObject> importTestResultFromHistory(
			int limitTo, boolean reverse) {
		return importTestResultFromHistory("", limitTo, reverse);
	}

	/**
	 * import all HistoryFiles from previous process read in all xml-files in
	 * folder and present them in the right table compared to a given id
	 * 
	 * @return
	 */
	public List<HistoryTestResultObject> importTestResultFromHistory(
			String compareID, boolean reverse) {
		return importTestResultFromHistory(compareID, 0, reverse);
	}

	/**
	 * delete unnecessary files from history-folder that are older than a given
	 * date
	 */

	public void deleteUnnecessaryFiles(int days) {
		if (days == 0) {
			return;
		}
		File file = new File(requestPluginPath().toOSString()
				+ java.io.File.separator + "historyFiles");
		// IWorkspace workspace = ResourcesPlugin.getWorkspace();
		File[] files = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File arg0) {
				if (arg0.getName().endsWith("xml"))
					return true;
				return false;
			}
		});

		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f2, File f1) {
				return Long.valueOf(f1.lastModified()).compareTo(
						f2.lastModified());
			}
		});

		BasicFileAttributes view = null;
		int i = 0;
		for (File fi : files) {
			java.nio.file.Path fileP = Paths.get(fi.getAbsolutePath());
			try {
				view = Files.getFileAttributeView(fileP,
						BasicFileAttributeView.class).readAttributes();
				if (view.lastModifiedTime().toMillis() < System
						.currentTimeMillis()
						- LIMIT_DAYS_BEFORE_DELETE
						* 24
						* 60 * 60 * 1000) {
					fi.delete();
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (i > 0)
			System.out.println(i
					+ " File(s) deleted, cause they were older than "
					+ LIMIT_DAYS_BEFORE_DELETE + " days.");
	}

	/**
	 * receive a preferences-string-value with a given String - all available
	 * pref-constants under @PreferenceConstants
	 * 
	 * @param prefConst
	 * @return
	 */

	public static String getPrefValue(final String prefConst) {
		final IPreferenceStore preferenceStore = JouleUnitUiPlugIn.getDefault()
				.getPreferenceStore();
		// Preferences prefs =
		// Platform.getPreferencesService().getRootNode().node(Plugin.PLUGIN_PREFEERENCES_SCOPE).node(MY_PLUGIN_ID);
		// InstanceScope.INSTANCE.getNode(JouleUnitUiPlugIn.PLUGIN_ID); // does
		// all the above behind the scenes
		// Preferences prefsLoad =
		// InstanceScope.INSTANCE.getNode(JouleUnitUiPlugIn.PLUGIN_ID);
		final String string = preferenceStore.getString(prefConst);
		// keep sync adt-plugin-pref and jouleunit-ui-pref
		if (prefConst == JouleUnitPreferences.P_STRING_SERVER_IP
				&& preferenceStore
						.getString(JouleUnitPreferences.P_STRING_SERVER_IP) != string) {
			preferenceStore.putValue(JouleUnitPreferences.P_STRING_SERVER_IP,
					string);
		}
		if (prefConst == JouleUnitPreferences.P_STRING_SERVER_PORT
				&& preferenceStore
						.getString(JouleUnitPreferences.P_STRING_SERVER_PORT) != string) {
			preferenceStore.putValue(JouleUnitPreferences.P_STRING_SERVER_PORT,
					string);
		}
		JouleUnitUiPlugIn.getDefault().getPreferenceStore()
				.addPropertyChangeListener(new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (event.getProperty() == JouleUnitPreferences.P_STRING_LIMIT_DAYS_BEFORE_DELETE) {
							LIMIT_DAYS_BEFORE_DELETE = Integer.parseInt(event
									.getNewValue().toString());
						}
						if (event.getProperty() == JouleUnitPreferences.P_STRING_LIMIT_ENTRIES_TO) {
							LIMIT_ENTRIES_TO = Integer.parseInt(event
									.getNewValue().toString());
						}
						if (event.getProperty() == JouleUnitPreferences.P_STRING_LIMIT_BAR_ENTRIES_TO) {
							LIMIT_BAR_ENTRIES_TO = Integer.parseInt(event
									.getNewValue().toString());
						}
						if (event.getProperty() == JouleUnitPreferences.P_STRING_SERVER_IP) {
							SERVER_IP = event.getNewValue().toString();
							preferenceStore.setValue(
									JouleUnitPreferences.P_STRING_SERVER_IP,
									SERVER_IP);
						}
						if (event.getProperty() == JouleUnitPreferences.P_STRING_SERVER_PORT) {
							SERVER_PORT = Integer.parseInt(event.getNewValue()
									.toString());
							preferenceStore.setValue(
									JouleUnitPreferences.P_STRING_SERVER_PORT,
									SERVER_PORT);
						}
					}
				});
		return string;
	}
}
