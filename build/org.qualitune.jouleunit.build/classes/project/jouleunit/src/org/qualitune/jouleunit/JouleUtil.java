package org.qualitune.jouleunit;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JouleUnit Utility class.
 * 
 * @author Claas Wilke
 */
public class JouleUtil {

	/**
	 * Deserializes an {@link EnergyProfile} from a given {@link File}.
	 * 
	 * @param file
	 *            The {@link File} used for deserialization.
	 * @return The deserialized {@link EnergyProfile}.
	 * @throws IOException
	 *             Thrown, if the given {@link File} cannot be found or opened.
	 * @throws ClassNotFoundException
	 *             Thrown, if the deserialized object is of an unknown
	 *             {@link Class}.
	 */
	public static EnergyProfile deserializeProfile(File file)
			throws IOException, ClassNotFoundException {

		if (file == null)
			throw new IllegalArgumentException(
					"Argument 'file' cannot be null.");
		// no else.

		FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				fileInputStream);

		EnergyProfile result = (EnergyProfile) objectInputStream.readObject();
		objectInputStream.close();

		return result;
	}

	/**
	 * Deserializes a {@link List} of {@link EnergyProfile}s from a given
	 * {@link File}.
	 * 
	 * @param file
	 *            The {@link File} used for deserialization.
	 * @return The deserialized {@link EnergyProfile}s.
	 * @throws IOException
	 *             Thrown, if the given {@link File} cannot be found or opened.
	 * @throws ClassNotFoundException
	 *             Thrown, if the deserialized object is of an unknown
	 *             {@link Class}.
	 */
	public static List<EnergyProfile> deserializeProfiles(File file)
			throws IOException, ClassNotFoundException {

		if (file == null)
			throw new IllegalArgumentException(
					"Argument 'file' cannot be null.");
		// no else.

		FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				fileInputStream);

		List<EnergyProfile> result = new ArrayList<EnergyProfile>();

		while (true) {
			try {
				result.add((EnergyProfile) objectInputStream.readObject());
			} catch (EOFException e) {
				break;
			}
		}
		// end while.

		objectInputStream.close();

		return result;
	}

	/**
	 * Exports a given {@link EnergyProfile} into a given {@link File} as spread
	 * sheet (CSV) data.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile} to be exported.
	 * @param file
	 *            The {@link File} the {@link EnergyProfile} shall be exported
	 *            to.
	 * @param testRunName
	 *            The name of the test run (appears in the header of the CSV
	 *            data).
	 */
	public static void exportProfile(EnergyProfile profile, File file,
			String testRunName) {

		if (profile == null)
			throw new IllegalArgumentException(
					"Parameter 'profile' cannot be null.");
		else if (file == null)
			throw new IllegalArgumentException(
					"Parameter 'file' cannot be null.");
		else if (file.isDirectory())
			throw new IllegalArgumentException("The given file '"
					+ file.getAbsolutePath() + "' is a directory.");
		else if (testRunName == null || testRunName.length() == 0)
			throw new IllegalArgumentException(
					"Parameter 'testRunName' cannot be null or empty.");
		// no else.

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos);

			/* Write header. */
			osw.write("Profiling result from test run " + testRunName
					+ " exported at " + new Date().toString() + ";\n");

			/* Write new line. */
			osw.write("\n");

			osw.write(getOutputToExportProfile(profile));

			osw.close();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Helper method that generates output to export a given
	 * {@link EnergyProfile}.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile} to be exported.
	 * @return The generated output as a {@link String}.
	 */
	protected static String getOutputToExportProfile(EnergyProfile profile)
			throws IOException {

		StringBuffer result = new StringBuffer();
		result.append("time stamp [nano secs];event;power rate [mW];avg power rate [mW];avg power rate in interval [mW];\n");

		List<PowerRate> values;

		if (profile instanceof SimpleEnergyProfile)
			values = ((SimpleEnergyProfile) profile).getMeasuredValues();
		else if (profile instanceof CompositeEnergyProfile)
			values = ((CompositeEnergyProfile) profile).getSignificantValues(
					EnergyProfile.START_EVENT_ID, EnergyProfile.END_EVENT_ID);
		else
			throw new IllegalArgumentException(
					"Unknown type of EnergyProfile: "
							+ profile.getClass().getSimpleName()
							+ ". Expected was "
							+ SimpleEnergyProfile.class.getSimpleName()
							+ " or "
							+ CompositeEnergyProfile.class.getSimpleName()
							+ ".");
		// end else.

		Collections.sort(values);

		Map<String, PowerRate> events = profile.getLoggedEvents();
		Map<PowerRate, String> evenIDsByRate = new HashMap<PowerRate, String>();
		for (String id : events.keySet())
			evenIDsByRate.put(events.get(id), id);
		// end for.

		evenIDsByRate.put(values.get(0), EnergyProfile.START_EVENT_ID);

		List<String> eventIDsOrderedByTime = new ArrayList<String>();
		for (PowerRate value : values) {
			if (evenIDsByRate.containsKey(value))
				eventIDsOrderedByTime.add(evenIDsByRate.get(value));
			// no else.
		}
		// end for.

		eventIDsOrderedByTime.add(EnergyProfile.END_EVENT_ID);

		String currentEventID;

		double avgPowerRate = profile.getConsumedEnergy()
				/ profile.getDuration() * 1000000000d;
		double avgPowerRateInInterval = 0l;

		for (PowerRate value : values) {
			currentEventID = evenIDsByRate.get(value);
			if (currentEventID == null)
				currentEventID = "";
			else {
				int indexOfID = eventIDsOrderedByTime.indexOf(currentEventID);
				if (indexOfID < eventIDsOrderedByTime.size() - 1) {

					String followingEventID = eventIDsOrderedByTime
							.get(indexOfID + 1);
					double intervalConsumption = profile.getConsumedEnergy(
							currentEventID, followingEventID);
					long intervalDuration = profile.getDuration(currentEventID,
							followingEventID);

					avgPowerRateInInterval = intervalConsumption
							/ intervalDuration * 1000000000d;
				}
				// no else.
			}

			if (values.get(values.size() - 1) == value)
				currentEventID = EnergyProfile.END_EVENT_ID;
			// no else.

			result.append(value.getTimeStamp() + ";" + currentEventID + ";"
					+ (-1) * value.getPowerRate() + ";" + avgPowerRate + ";"
					+ avgPowerRateInInterval + ";\n");
		}
		// end for.

		if (profile instanceof CompositeEnergyProfile) {
			int counter = 0;
			for (EnergyProfile subProfile : ((CompositeEnergyProfile) profile)
					.getSubProfiles()) {
				counter++;
				result.append("\nResult for Sub Profile #" + counter + ":");
				result.append("\n" + getOutputToExportProfile(subProfile));
			}
			// end for.
		}
		// no else.

		return result.toString();
	}

	/**
	 * Returns the average duration from a given {@link List} of
	 * {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The average duration in <code>nano seconds</code>.
	 */
	public static double getDurationAvg(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		// no else.

		double result = 0;

		for (EnergyProfile profile : profiles)
			result += profile.getDuration();
		// end for.

		result = result / profiles.size();

		return result;
	}

	/**
	 * Returns the maximum duration from a given {@link List} of
	 * {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The maximum duration in <code>nano seconds</code>.
	 */
	public static double getDurationMax(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		// no else.

		double result = -1;

		for (EnergyProfile profile : profiles) {
			if (profile.getDuration() > result)
				result = profile.getDuration();
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * Returns the median duration from a given {@link List} of
	 * {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The median duration in <code>nano seconds</code>.
	 */
	public static double getDurationMedian(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		// no else.

		/* Copy list to avoid side effects. */
		profiles = new ArrayList<EnergyProfile>(profiles);
		Collections.sort(profiles, new EnergyProfileDurationComparator());

		/*
		 * Returns the center element for uneven size and the element above the
		 * center for even size.
		 */
		return profiles.get(profiles.size() / 2).getDuration();
	}

	/**
	 * Returns the minimum duration from a given {@link List} of
	 * {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The minimum duration in <code>nano seconds</code>.
	 */
	public static double getDurationMin(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		// no else.

		double result = Double.MAX_VALUE;

		for (EnergyProfile profile : profiles) {
			if (profile.getDuration() < result)
				result = profile.getDuration();
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * Returns the standard deviation of the duration from a given {@link List}
	 * of {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The standard deviation duration in <code>nano seconds</code>.
	 */
	public static double getDurationStdDev(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		else if (profiles == null || profiles.size() == 1)
			throw new IllegalArgumentException(
					"Argument 'profile' must contain at least two values.");
		// no else.

		double avgDuration = getDurationAvg(profiles);
		double result = 0d;

		for (EnergyProfile profile : profiles) {
			double difference = (profile.getDuration() - avgDuration);
			result += difference * difference;
		}
		// end for.

		/* Compute variance. */
		result = result / (profiles.size() - 1);

		/* Compute std. dev. */
		result = Math.sqrt(result);

		return result;
	}

	/**
	 * Returns the standard error of the mean duration from a given {@link List}
	 * of {@link EnergyProfile}s. The standard error is computed by deviding the
	 * standard deviation by the square root of the number of profiles.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The standard error of the mean duration in
	 *         <code>nano seconds</code>.
	 */
	public static double getDurationStdError(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		else if (profiles == null || profiles.size() == 1)
			throw new IllegalArgumentException(
					"Argument 'profile' must contain at least two values.");
		// no else.

		double stdDevDuration = getDurationStdDev(profiles);

		/* Std. error of mean. */
		double result = stdDevDuration / Math.sqrt(profiles.size());

		return result;
	}

	/**
	 * Returns the arithmetic mean energy consumption from a given {@link List}
	 * of {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The average energy consumption in <code>mJ</code>.
	 */
	public static double getJouleAvg(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		// no else.

		double result = 0;

		for (EnergyProfile profile : profiles)
			result += profile.getConsumedEnergy();
		// end for.

		result = result / profiles.size();

		return result;
	}

	/**
	 * Returns the maximum energy consumption from a given {@link List} of
	 * {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The maximum energy consumption in <code>mJ</code>.
	 */
	public static double getJouleMax(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		// no else.

		double result = -1;

		for (EnergyProfile profile : profiles) {
			if (profile.getConsumedEnergy() > result)
				result = profile.getConsumedEnergy();
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * Returns the median energy consumption from a given {@link List} of
	 * {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The median energy consumption in <code>mJ</code>
	 */
	public static double getJouleMedian(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		// no else.

		/* Copy list to avoid side effects. */
		profiles = new ArrayList<EnergyProfile>(profiles);
		Collections.sort(profiles, new EnergyProfileComparator());

		/*
		 * Returns the center element for uneven size and the element above the
		 * center for even size.
		 */
		return profiles.get(profiles.size() / 2).getConsumedEnergy();
	}

	/**
	 * Returns the minimum energy consumption from a given {@link List} of
	 * {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The minimum energy consumption in <code>mJ</code>.
	 */
	public static double getJouleMin(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		// no else.

		double result = Double.MAX_VALUE;

		for (EnergyProfile profile : profiles) {
			if (profile.getConsumedEnergy() < result)
				result = profile.getConsumedEnergy();
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * Returns the standard deviation of the energy consumption from a given
	 * {@link List} of {@link EnergyProfile}s.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The standard deviation energy consumption in <code>mJ</code>
	 */
	public static double getJouleStdDev(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		else if (profiles == null || profiles.size() == 1)
			throw new IllegalArgumentException(
					"Argument 'profile' must contain at least two values.");
		// no else.

		double avgConsumption = getJouleAvg(profiles);
		double result = 0d;

		for (EnergyProfile profile : profiles) {
			double difference = (profile.getConsumedEnergy() - avgConsumption);
			result += difference * difference;
		}
		// end for.

		/* Compute variance. */
		result = result / (profiles.size() - 1);

		/* Compute std. dev. */
		result = Math.sqrt(result);

		return result;
	}

	/**
	 * Returns the standard error of the mean energy consumption from a given
	 * {@link List} of {@link EnergyProfile}s. The standard error is computed by
	 * deviding the standard deviation by the square root of the number of
	 * profiles.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s.
	 * @return The standard error of the mean energy consumption in
	 *         <code>mJ</code>
	 */
	public static double getJouleStdError(List<EnergyProfile> profiles) {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null or empty.");
		else if (profiles == null || profiles.size() == 1)
			throw new IllegalArgumentException(
					"Argument 'profile' must contain at least two values.");
		// no else.

		double stdDevJoules = getJouleStdDev(profiles);

		/* Std. error of mean. */
		double result = stdDevJoules / Math.sqrt(profiles.size());

		return result;
	}

	/**
	 * Serializes a given {@link EnergyProfile} into a given {@link File}.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile} to be serialized.
	 * @param file
	 *            The {@link File} used for serialization.
	 * @throws IOException
	 *             Thrown, if the given {@link File} cannot be found or opened.
	 * 
	 */
	public static void serializeProfile(EnergyProfile profile, File file)
			throws IOException {

		if (profile == null)
			throw new IllegalArgumentException(
					"Argument 'profile' cannot be null.");
		else if (file == null)
			throw new IllegalArgumentException(
					"Argument 'file' cannot be null.");
		// no else.

		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				fileOutputStream);

		objectOutputStream.writeObject(profile);
		objectOutputStream.close();
	}

	/**
	 * Serializes a given {@link List} of {@link EnergyProfile}s into a given
	 * {@link File}.
	 * 
	 * @param profiles
	 *            The {@link EnergyProfile}s to be serialized.
	 * @param file
	 *            The {@link File} used for serialization.
	 * @throws IOException
	 *             Thrown, if the given {@link File} cannot be found or opened.
	 * 
	 */
	public static void serializeProfiles(List<EnergyProfile> profiles, File file)
			throws IOException {

		if (profiles == null || profiles.size() == 0)
			throw new IllegalArgumentException(
					"Argument 'profiles' cannot be null or empty.");
		else if (file == null)
			throw new IllegalArgumentException(
					"Argument 'file' cannot be null.");
		// no else.

		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				fileOutputStream);

		for (EnergyProfile profile : profiles)
			objectOutputStream.writeObject(profile);
		// end for.

		objectOutputStream.close();
	}
}
