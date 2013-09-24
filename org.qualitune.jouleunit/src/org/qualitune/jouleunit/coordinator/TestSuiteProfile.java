package org.qualitune.jouleunit.coordinator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.PowerRate;

/**
 * Contains a set of {@link TestCaseProfile}s as well as some information on the
 * platform under test (PUT) the {@link TestCaseProfile}s are from. E.g., its
 * offset to the global time for time stamps.
 * 
 * @author Claas Wilke
 */
public class TestSuiteProfile {

	/** The NTP server used to compute the time stamp offset. */
	private final static String NTP_SERVER_LOCATION = "ntps1-1.cs.tu-berlin.de";

	/** Character used to separated values in exported files. */
	private static final String EXPORT_SEPARATOR = ";";

	/** The {@link EnergyProfile} belonging to this {@link TestSuiteProfile}. */
	private EnergyProfile energyProfile;

	/**
	 * Contains a path to the file where this {@link TestSuiteProfile} was
	 * exported. If <code>null</code> no export was performed.
	 */
	private String exportLocation;

	/** Whether or not to use timestamp correction between test runner and PUT. */
	private boolean isTimestampCorrectionEnabled = true;

	/**
	 * Events logged during profiling. They consist of a unique ID (a
	 * {@link String}) and a time stamp (test runner time).
	 */
	private Map<String, Long> loggedEvents = new HashMap<String, Long>();

	/** The CPU Frequencies of the PUT logged with time stamps. */
	private Map<Long, Long[]> putCpuFrequencies = new HashMap<Long, Long[]>();

	/** The LCD Brightness of the PUT logged with time stamps. */
	private Map<Long, Long> putLcdBrightness = new HashMap<Long, Long>();

	/**
	 * The offset between time stamps of the contained {@link TestCaseProfile}s
	 * and a global time, e.g., European Main Time (EMT).
	 */
	private long putTimeStampOffset = 0l;

	/** The WiFi traffic of the PUT logged with time stamps. */
	private Map<Long, Long> putWiFiTraffic = new HashMap<Long, Long>();

	/** The {@link TestCaseProfile}s of this {@link TestSuiteProfile}. */
	private List<TestCaseProfile> testCases = new ArrayList<TestCaseProfile>();

	/**
	 * The offset between time stamps of the contained {@link EnergyProfile}s
	 * and a global time, e.g., European Main Time (EMT).
	 */
	private long trTimeStampOffset = 0l;

	/**
	 * Offset to convert nano time stamps of the test runners'
	 * {@link EnergyProfile} into milliseconds.
	 */
	private long trNanoTimeOffset;

	/**
	 * Helper method to adapt a time stamp of this {@link TestSuiteProfile} to
	 * the global clock.
	 * 
	 * @param timeStamp
	 *            The time stamp to be adapted.
	 * @return The adapted time stamp in milliseconds.
	 */
	public long adaptPutTimeStamp(long timeStamp) {
		if (isTimestampCorrectionEnabled)
			return timeStamp + putTimeStampOffset;
		else
			return timeStamp;
	}

	/**
	 * Helper method to adapt a time stamp of this {@link TestSuiteProfile}'s
	 * {@link EnergyProfile} to the global clock.
	 * 
	 * @param timeStamp
	 *            The time stamp to be adapted (in nanoseconds).
	 * @return The adapted time stamp in milliseconds.
	 */
	public double adaptTrTimeStamp(long timeStamp) {
		if (isTimestampCorrectionEnabled)
			return timeStamp / 1000000d + getTrTimeStampOffsetFromNanos();
		else
			return timeStamp / 1000000d;
	}

	/**
	 * Adds a given profiled CPU frequencies of the device under test.
	 * 
	 * @param timeStamp
	 *            The time stamp of the frequency.
	 * @param frequencies
	 *            The frequencies in Hz as an array of {@link Long}s.
	 */
	public void addCpuFrequencies(Long timeStamp, Long[] frequencies) {
		this.putCpuFrequencies.put(timeStamp, frequencies);
	}

	/**
	 * Adds a given profiled LCD brightness value of the device under test.
	 * 
	 * @param timeStamp
	 *            The time stamp of the frequency.
	 * @param brightness
	 *            The brightness (0 = off, 255 = 100%).
	 */
	public void addLcdBrightness(Long timeStamp, Long brightness) {
		this.putLcdBrightness.put(timeStamp, brightness);
	}

	/**
	 * Adds a {@link TestCaseProfile} to this {@link TestSuiteProfile}
	 * 
	 * @param profile
	 *            The {@link TestCaseProfile} to be added.
	 */
	public void addTestCase(TestCaseProfile profile) {
		if (!testCases.contains(profile)) {
			this.testCases.add(profile);
			profile.setTestSuiteProfile(this);
		}
		// no else.
	}

	/**
	 * Adds a given profiled WiFi Traffic of the device under test.
	 * 
	 * @param timeStamp
	 *            The time stamp of the frequency.
	 * @param traffic
	 *            The traffic in Bytes.
	 */
	public void addWiFiTraffic(Long timeStamp, Long traffic) {
		this.putWiFiTraffic.put(timeStamp, traffic);
	}

	/**
	 * Clears this {@link TestSuiteProfile}. Deletes all contained
	 * {@link TestCaseProfile}s as well as already profiled energy consumption
	 * and further hardware information.
	 * 
	 * Can be sensible, if long running {@link TestSuiteProfile} are exported
	 * incrementally.
	 */
	public void clear() {

		this.testCases.clear();
		this.putCpuFrequencies.clear();
		this.putLcdBrightness.clear();
		this.putWiFiTraffic.clear();
	}

	/**
	 * Returns an array of {@link AvgTestCaseResult} for the
	 * {@link TestCaseProfile}s of this {@link TestSuiteProfile} grouped by
	 * their ID.
	 * 
	 * @return An array of {@link AvgTestCaseResult} for the
	 *         {@link TestCaseProfile}s of this {@link TestSuiteProfile} grouped
	 *         by their ID.
	 */
	public AvgTestCaseResult[] getAvgTestCaseResults() {
		Map<String, AvgTestCaseResult> results = new HashMap<String, AvgTestCaseResult>();

		for (TestCaseProfile profile : testCases) {
			AvgTestCaseResult avgResult;
			if (results.containsKey(profile.getId()))
				avgResult = results.get(profile.getId());
			else {
				avgResult = new AvgTestCaseResult();
				results.put(profile.getId(), avgResult);
			}

			avgResult.addProfile(profile);
		}
		// end for.

		return results.values().toArray(new AvgTestCaseResult[0]);
	}

	/**
	 * @return The CPU Frequencies of the PUT logged with time stamps.
	 */
	public Map<Long, Long[]> getCpuFrequencies() {
		return putCpuFrequencies;
	}

	/**
	 * @return The {@link EnergyProfile} of this {@link TestSuiteProfile}.
	 */
	public EnergyProfile getEnergyProfile() {
		return energyProfile;
	}

	/**
	 * Returns the path to the file where this {@link TestSuiteProfile} was
	 * exported. If <code>null</code> no export was performed.
	 * 
	 * @return The path to the file where this {@link TestSuiteProfile} was
	 *         exported. If <code>null</code> no export was performed.
	 */
	public String getExportLocation() {
		return exportLocation;
	}

	/**
	 * @return The LCD brightness of the PUT logged with time stamps.
	 */
	public Map<Long, Long> getLcdBrightness() {
		return putLcdBrightness;
	}

	/**
	 * @return The logged events during profiling. They consist of a unique ID
	 *         (a {@link String}) and a time stamp (test runner time).
	 */
	public Map<String, Long> getLoggedEvents() {
		return loggedEvents;
	}

	/**
	 * @return The offset between time stamps from the test runner system
	 *         {@link TestCaseProfile}s and a global time, e.g., European Main
	 *         Time (EMT).
	 */
	public long getPutTimeStampOffset() {
		return putTimeStampOffset;
	}

	/**
	 * @return The {@link TestCaseProfile}s of this {@link TestSuiteProfile}.
	 */
	public List<TestCaseProfile> getTestCaseProfiles() {
		return testCases;
	}

	/**
	 * @param minimalStartTime
	 *            The minimal start time stamp of all {@link TestCaseProfile}s
	 *            that shall be returned.
	 * @return The {@link TestCaseProfile}s of this {@link TestSuiteProfile}
	 *         that have been started after the given start time.
	 */
	public List<TestCaseProfile> getTestCaseProfiles(long minimalStartTime) {

		List<TestCaseProfile> result = new ArrayList<TestCaseProfile>();

		for (TestCaseProfile aProfile : testCases) {
			if (aProfile.getStartTime() >= minimalStartTime)
				result.add(aProfile);
			// no else.
		}
		// end for.

		return result;
	}

	/**
	 * @return The offset between time stamps of the contained
	 *         {@link EnergyProfile}s and a global time, e.g., European Main
	 *         Time (EMT).
	 */
	public long getTrTimeStampOffset() {
		if (trTimeStampOffset == 0l)
			computeTrTimeStampOffset();
		// no else.

		return trTimeStampOffset;
	}

	/**
	 * @return The offset between time stamps of the contained
	 *         {@link EnergyProfile}s and a global time, e.g., European Main
	 *         Time (EMT). <strong>This offset can be used for the conversion
	 *         from test runner nano time to global time in millis.</strong>
	 */
	public long getTrTimeStampOffsetFromNanos() {
		if (trTimeStampOffset == 0l)
			computeTrTimeStampOffset();
		// no else.

		return trNanoTimeOffset + trTimeStampOffset;
	}

	/**
	 * @return The WiFi traffic of the PUT logged with time stamps.
	 */
	public Map<Long, Long> getWiFiTraffic() {
		return putWiFiTraffic;
	}

	/**
	 * Indicates whether or not to use timestamp correction between test runner
	 * and PUT.
	 * 
	 * @return If <code>true</code> timestamp correction is enabled.
	 */
	public boolean isTimestampCorrectionEnabled() {
		return isTimestampCorrectionEnabled;
	}

	/**
	 * Logs an events during profiling. Events consist of a unique ID (a
	 * {@link String}) and a time stamp (test runner time).
	 * 
	 * @param ID
	 *            The ID of the event (time stamp is computed automatically.
	 */
	public void logEvent(String ID) {
		loggedEvents.put(ID, System.nanoTime());
	}

	/**
	 * Sets the {@link EnergyProfile} that belongs to the
	 * {@link TestCaseProfile}s of this {@link TestSuiteProfile}.
	 * 
	 * @param profile
	 *            The {@link EnergyProfile}.
	 */
	public void setEnergyProfile(EnergyProfile profile) {
		energyProfile = profile;
	}

	/**
	 * Sets the path to the file where this {@link TestSuiteProfile} was
	 * exported. If <code>null</code> no export was performed.
	 * 
	 * @param exportLocation
	 *            The path to the file where this {@link TestSuiteProfile} was
	 *            exported. If <code>null</code> no export was performed.
	 */
	public void setExportLocation(String exportLocation) {
		this.exportLocation = exportLocation;
	}

	/**
	 * Sets the offset between time stamps of the contained
	 * {@link TestCaseProfile}s and a global time, e.g., European Main Time
	 * (EMT).
	 * 
	 * @param timeStampOffset
	 *            The offset between time stamps of the contained
	 *            {@link TestCaseProfile}s and a global time, e.g., European
	 *            Main Time (EMT).
	 */
	public void setPutTimeStampOffset(long timeStampOffset) {
		this.putTimeStampOffset = timeStampOffset;
	}

	/**
	 * Sets whether or not to use timestamp correction between test runner and
	 * PUT.
	 * 
	 * @param isTimestampCorrectionEnabled
	 *            If <code>true</code> timestamp correction is enabled.
	 */
	public void setTimestampCorrectionEnabled(
			boolean isTimestampCorrectionEnabled) {
		this.isTimestampCorrectionEnabled = isTimestampCorrectionEnabled;
	}

	/**
	 * Prints the data as comma-separated values (CSV) that can be read by
	 * spread sheet software to the given {@link BufferedWriter}.
	 * 
	 * @param writer
	 *            The {@link BufferedWriter} used for export.
	 * @throws IOException
	 */
	public void export(BufferedWriter writer) throws IOException {

		writer.append("JouleUnit test suite result containing "
				+ testCases.size() + " test case runs");

		if (testCases.size() > 0)
			writer.append(":\nid" + EXPORT_SEPARATOR + "tag" + EXPORT_SEPARATOR
					+ "failed?" + EXPORT_SEPARATOR + "start [ms]"
					+ EXPORT_SEPARATOR + "stop [ms]" + EXPORT_SEPARATOR
					+ "duration [ms]" + EXPORT_SEPARATOR
					+ "avg. power rate [mW]" + EXPORT_SEPARATOR
					+ "consumption [mJ]\n");
		else
			writer.append(".");

		for (TestCaseProfile profile : testCases) {
			try {
				writer.append(profile.getId() + EXPORT_SEPARATOR);

				if (null != profile.getTag())
					writer.append(profile.getTag() + EXPORT_SEPARATOR);
				else
					writer.append("" + EXPORT_SEPARATOR);

				writer.append(profile.getId() + EXPORT_SEPARATOR);
				writer.append(profile.isFailed() ? "no" : "yes");
				writer.append(EXPORT_SEPARATOR);
				writer.append(adaptPutTimeStamp(profile.getStartTime())
						+ EXPORT_SEPARATOR);
				writer.append(adaptPutTimeStamp(profile.getEndTime())
						+ EXPORT_SEPARATOR);
				writer.append(profile.getDuration() + EXPORT_SEPARATOR);
				writer.append(profile.getPowerRate() + EXPORT_SEPARATOR);
				writer.append(profile.getConsumedEnergy() + "\n");
			}

			catch (IllegalArgumentException e) {
				writer.append(e.getMessage() + "\n");
			}
		}
		// end for.

		if (testCases.size() > 0)
			writer.append("\n\nAvg. results\nid" + EXPORT_SEPARATOR + "# runs"
					+ EXPORT_SEPARATOR + "# failed runs" + EXPORT_SEPARATOR
					+ "avg. duration [ms]" + EXPORT_SEPARATOR
					+ "std. dev. duration [ms]" + EXPORT_SEPARATOR
					+ "avg. power rate [mW]" + EXPORT_SEPARATOR
					+ "std. dev. power rate [mW]" + EXPORT_SEPARATOR
					+ "avg. power consumption [mJ]" + EXPORT_SEPARATOR
					+ "std. dev. power consumption [mJ]\n");
		// no else.

		for (AvgTestCaseResult avgResult : getAvgTestCaseResults()) {
			try {
				writer.append(avgResult.getID() + EXPORT_SEPARATOR);
				writer.append(avgResult.getNumberOfRuns() + EXPORT_SEPARATOR);
				writer.append(avgResult.getNumberOfFailedRuns()
						+ EXPORT_SEPARATOR);
				writer.append(avgResult.getAvgDuration() + EXPORT_SEPARATOR);
				writer.append(avgResult.getStdDevDuration() + EXPORT_SEPARATOR);
				writer.append(avgResult.getAvgPowerRate() + EXPORT_SEPARATOR);
				writer.append(avgResult.getStdDevPowerRate() + EXPORT_SEPARATOR);
				writer.append(avgResult.getAvgPowerConsumption()
						+ EXPORT_SEPARATOR);
				writer.append(avgResult.getStdDevPowerConsumption() + "\n");
			}

			catch (IllegalArgumentException e) {
				writer.append(e.getMessage() + "\n");
			}
		}
		// end for.

		/* Power Rate Data. */
		writer.append("\n\nPower Rate Values\ntime stamp [ms]"
				+ EXPORT_SEPARATOR + "power rate [mW]\n");

		for (PowerRate rate : energyProfile.getSignificantValues(
				EnergyProfile.START_EVENT_ID, EnergyProfile.END_EVENT_ID)) {
			try {
				writer.append(adaptTrTimeStamp(rate.getTimeStamp())
						+ EXPORT_SEPARATOR + rate.getPowerRate() + "\n");
			}

			catch (IllegalArgumentException e) {
				writer.append(e.getMessage() + "\n");
			}
		}
		// end for.

		/* CPU Data. */
		if (getCpuFrequencies().size() > 0) {

			writer.append("\n\nCPU Values\ntime stamp [ms]");
			for (int index = 1; index <= getCpuFrequencies().values()
					.iterator().next().length; index++)
				writer.append(EXPORT_SEPARATOR + "CPU" + index + " [Hz]");
			// end for.
			writer.append("\n");

			for (Long timeStamp : getCpuFrequencies().keySet()) {
				writer.append(adaptPutTimeStamp(timeStamp) + "");
				Long[] values = getCpuFrequencies().get(timeStamp);
				for (long value : values)
					writer.append("," + value);
				// end for.
				writer.append("\n");
			}
			// end for.
		}
		// no else.

		/* WiFi Data. */
		if (getWiFiTraffic().size() > 0) {

			writer.append("\n\nWiFi Traffic\ntime stamp [ms]"
					+ EXPORT_SEPARATOR + "sent and read [Byte]\n");

			for (Long timeStamp : getWiFiTraffic().keySet())
				writer.append(adaptPutTimeStamp(timeStamp) + EXPORT_SEPARATOR
						+ getWiFiTraffic().get(timeStamp) + "\n");
			// end for.
		}
		// no else.

		/* LCD Brightness. */
		if (getLcdBrightness().size() > 0) {

			writer.append("\n\nLCD Brigthness\ntime stamp [ms]"
					+ EXPORT_SEPARATOR + "brightness [0 .. 255]\n");

			for (Long timeStamp : getLcdBrightness().keySet())
				writer.append(adaptPutTimeStamp(timeStamp) + EXPORT_SEPARATOR
						+ getLcdBrightness().get(timeStamp) + "\n");
			// end for.
		}
		// no else.
	}

	/**
	 * Returns the consumed energy for a given {@link TestCaseProfile} of this
	 * {@link TestSuiteProfile}.
	 * 
	 * @param profile
	 *            The {@link TestCaseProfile} whose energy consumption shall be
	 *            returned.
	 * @return The profiled energy consumption during the test cases execution.
	 */
	protected double getConsumedEnergy(TestCaseProfile profile) {

		long startTime = profile.getStartTime();
		long endTime = profile.getEndTime();

		if (isTimestampCorrectionEnabled) {
			if (trTimeStampOffset == 0)
				computeTrTimeStampOffset();
			// no else.

			/* Convert PUT time int test runner nano seconds. */
			startTime = profile.getStartTime();
			startTime += putTimeStampOffset;
			/* Start time in millis. */
			startTime -= trTimeStampOffset;
			/* Start time in nanos. */
			startTime = (startTime - trNanoTimeOffset) * 1000000;

			endTime = profile.getEndTime();
			endTime += putTimeStampOffset;
			/* End time in millis. */
			endTime -= trTimeStampOffset;
			/* End time in nanos. */
			endTime = (endTime - trNanoTimeOffset) * 1000000;
		}

		else {
			startTime = startTime * 1000000;
			endTime = endTime * 1000000;
		}

		return energyProfile.getConsumedEnergy(startTime, endTime);
	}

	/**
	 * Computes the duration of a given {@link TestCaseProfile}.
	 * 
	 * @param profile
	 *            The {@link TestCaseProfile}.
	 * @return The duration in milliseconds.
	 */
	protected long getDuration(TestCaseProfile profile) {
		return profile.getEndTime() - profile.getStartTime();
	}

	/**
	 * Compute offset of local clock in comparison to atom clock via NTP in
	 * milliseconds.
	 */
	private void computeTrTimeStampOffset() {

		String[] ntpServerLocations = new String[] { "ptbtime1.ptb.de",
				"ntps1-1.cs.tu-berlin.de" };

		/* Compute the mapping between nano time and millis for the test runner. */
		long nanoTime = System.nanoTime();
		long milliTime = System.currentTimeMillis();

		/* Conversion to millis. */
		trNanoTimeOffset = milliTime - (nanoTime / 1000000);

		/* Adaptation to global clock. */

		NTPUDPClient client = new NTPUDPClient();
		client.setDefaultTimeout(10000);
		String errMsg = null;

		try {
			client.open();

			for (String ntpServerAddress : ntpServerLocations) {
				try {
					InetAddress hostAddr = InetAddress
							.getByName(ntpServerAddress);
					TimeInfo info = client.getTime(hostAddr);

					/* Compute offset/delay. */
					info.computeDetails();
					trTimeStampOffset = info.getOffset();
					errMsg = null;
					break;
				} catch (IOException e) {
					/*
					 * Do nothing for now, try to fetch infos from the next
					 * server.
					 */
					errMsg = e.getMessage();
				}
			}
		}

		catch (IOException ioe) {
			errMsg = ioe.getMessage();
		}

		if (null != errMsg)
			System.out
					.println("Exception during computation of test runner clock time offset: "
							+ errMsg);

		client.close();
	}
}
