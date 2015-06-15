package org.qualitune.jouleunit.android;

import android.net.TrafficStats;
import android.util.Log;

/**
 * Provides methods to get information on the device under test's WiFi
 * utilization.
 * 
 * @author Claas Wilke
 */
public class WiFiInfo {

	/**
	 * The number of transmitted bytes before the last call of the updateInfo()
	 * method.
	 */
	private long transmittedBytes = -1l;

	/**
	 * The number of transmitted packages before the last call of the
	 * updateInfo() method.
	 */
	private long transmittedPackages = -1l;

	/**
	 * The number of received bytes before the last call of the updateInfo()
	 * method.
	 */
	private long receivedBytes = -1l;

	/**
	 * The number of received packages before the last call of the updateInfo()
	 * method.
	 */
	private long receivedPackages = -1l;

	/**
	 * The number of transmitted bytes before the second last call of the
	 * updateInfo() method.
	 */
	private long transmittedBytesOld;

	/**
	 * The number of transmitted packages before second the last call of the
	 * updateInfo() method.
	 */
	private long transmittedPackagesOld;

	/**
	 * The number of received bytes before the second last call of the
	 * updateInfo() method.
	 */
	private long receivedBytesOld;

	/**
	 * The number of received packages before the second last call of the
	 * updateInfo() method.
	 */
	private long receivedPackagesOld;

	/**
	 * The name of the directory in the proc file system containing the WiFi
	 * infos.
	 */
	private String wifiInfoDirectory;

	/**
	 * Creates a new {@link WiFiInfo}.
	 */
	public WiFiInfo() {
		wifiInfoDirectory = "/sys/devices/virtual/net/eth0";

		if (!ProcUtil.isDirectoryExisting(wifiInfoDirectory))
			wifiInfoDirectory = "/sys/devices/virtual/net/wlan0";
		// no else.

		/* Google Nexus 7. */
		if (!ProcUtil.isDirectoryExisting(wifiInfoDirectory))
			wifiInfoDirectory = "/sys/class/net/wlan0/";
		// no else.

//		if (!ProcUtil.isDirectoryExisting(wifiInfoDirectory))
//			wifiInfoDirectory = "/sys/devices/virtual/net/ip6tnl0";
//		// no else.
//
//		if (!ProcUtil.isDirectoryExisting(wifiInfoDirectory))
//			wifiInfoDirectory = "/sys/devices/virtual/net/lo";
//		// no else.
//
//		if (!ProcUtil.isDirectoryExisting(wifiInfoDirectory))
//			wifiInfoDirectory = "/sys/devices/virtual/net/sit0";
//		// no else.

		if (!ProcUtil.isDirectoryExisting(wifiInfoDirectory))
			wifiInfoDirectory = "";
//			throw new IllegalStateException(
//					"The proc file directory to detect WiFi infos cannot be found.");
		// no else.

		Log.i(HWServiceActivity.LOG_TAG, "use WiFi dir " + wifiInfoDirectory);
	}

	/**
	 * Updates the infos regarding sent and received bytes and packages.
	 */
	public void updateInfo() {
		transmittedPackagesOld = transmittedPackages;
		transmittedBytesOld = transmittedBytes;
		receivedPackagesOld = receivedPackages;
		receivedBytesOld = receivedBytes;

		if (null != wifiInfoDirectory && wifiInfoDirectory.length() > 0) {
		transmittedPackages = ProcUtil.getLongFromFile(wifiInfoDirectory
				+ "/statistics/tx_packets");
		// TODO
		if (transmittedPackages == -1)
			Log.e(HWServiceActivity.LOG_TAG, "wifi file not found: "
					+ wifiInfoDirectory + "/statistics/tx_packets");
		transmittedBytes = ProcUtil.getLongFromFile(wifiInfoDirectory
				+ "/statistics/tx_bytes");
		receivedPackages = ProcUtil.getLongFromFile(wifiInfoDirectory
				+ "/statistics/rx_packets");
		receivedBytes = ProcUtil.getLongFromFile(wifiInfoDirectory
				+ "/statistics/rx_bytes");
		}
		
		else {
			transmittedPackages = TrafficStats.getTotalTxBytes();
			transmittedBytes = TrafficStats.getTotalTxBytes();
			receivedPackages = TrafficStats.getTotalRxPackets();
			receivedBytes = TrafficStats.getTotalRxBytes();
		}

		/* Initially, the method has to be invoked for a second time. */
		if (transmittedPackagesOld == -1 && transmittedBytesOld == -1
				&& receivedPackagesOld == -1 && receivedBytesOld == -1)
			updateInfo();
		// no else.
	}

	/**
	 * Returns the number of transmitted bytes between the last two calls of the
	 * {@link WiFiInfo#updateInfo()} method.
	 * 
	 * @return The number of transmitted bytes between the last two calls of the
	 */
	public long getTransmittedBytes() {
		return transmittedBytes - transmittedBytesOld;
	}

	/**
	 * Returns the number of transmitted packages between the last two calls of
	 * the {@link WiFiInfo#updateInfo()} method.
	 * 
	 * @return The number of transmitted packages between the last two calls of
	 *         the
	 */
	public long getTransmittedPackages() {
		return transmittedPackages - transmittedPackagesOld;
	}

	/**
	 * Returns the number of received bytes between the last two calls of the
	 * {@link WiFiInfo#updateInfo()} method.
	 * 
	 * @return The number of received bytes between the last two calls of the
	 */
	public long getReceivedBytes() {
		return receivedBytes - receivedBytesOld;
	}

	/**
	 * Returns the number of received packages between the last two calls of the
	 * {@link WiFiInfo#updateInfo()} method.
	 * 
	 * @return The number of received packages between the last two calls of the
	 */
	public long getReceivedPackages() {
		return receivedPackages - receivedPackagesOld;
	}
}
