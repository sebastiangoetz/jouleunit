package org.qualitune.jouleunit.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Provides methods to access information available in the proc file system of
 * the device under test.
 * 
 * @author Claas Wilke
 */
public abstract class ProcUtil {

	/**
	 * Reads the first line from a given File name (absolute path) and returns
	 * it converted into a long.
	 * 
	 * @param fileName
	 *            The file to be read (absolute path).
	 * @return The file's first line as a long <strong>(-1, if conversion
	 *         failed)</strong>.
	 */
	public static long getLongFromFile(String fileName) {

		long result = -1l;

		BufferedReader fileReader = null;

		try {
			fileReader = new BufferedReader(new FileReader(fileName), 500);
			result = Long.parseLong(fileReader.readLine().trim());
		}

		catch (IOException e) {
			/* Do not log here. Increases overhead. */
		}

		/* Close the file reader. */
		if (null != fileReader) {
			try {
				fileReader.close();
			} catch (IOException e1) {
				/* Already closed. */
			}
		}
		// no else.

		return result;
	}

	/**
	 * Tries to open a {@link File} for a given fileName and returns the value
	 * of the line as a long which starts with the given identifier.
	 * 
	 * @param fileName
	 *            The name of the {@link File}.
	 * @param id
	 *            The line's value identifier that shall be returned (e.g.,
	 *            <code>batt_discharge_current</code>).
	 * @return The {@link File}'s first line or an empty {@link String}.
	 */
	public static long getLongFromFileLine(String fileName, String id) {

		long result = 0l;

		File file = new File(fileName);
		if (file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));

				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					else if (line.startsWith(id)) {
						result = Long.parseLong(line.substring(id.length() + 1)
								.trim());
						break;
					}
					// no else.
				}
				// no else.

				br.close();
			}

			catch (IOException e) { /* Do nothing. */
			}
			// no else.
		}
		// no else.

		return result;
	}

	/**
	 * Checks whether or not a given path exists as a directory in the device's
	 * file system.
	 * 
	 * @param dirName
	 *            The directory to be checked (absolute path).
	 * @return <code>true</code> if the directory exists, <code>false</code>
	 *         otherwise.
	 */
	public static boolean isDirectoryExisting(String dirName) {

		File dir = new File(dirName);
		return dir.exists() && dir.isDirectory();
	}

	/**
	 * Checks whether or not a given path exists as a file in the device's file
	 * system.
	 * 
	 * @param fileName
	 *            The file to be checked (absolute path).
	 * @return <code>true</code> if the file exists, <code>false</code>
	 *         otherwise.
	 */
	public static boolean isFileExisting(String fileName) {

		File file = new File(fileName);
		return file.exists();
	}

}
