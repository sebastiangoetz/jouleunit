package org.qualitune.jouleunit.wt210;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.qualitune.jouleunit.ProfilingException;

import be.ac.ulb.gpib.GPIBDevice;
import be.ac.ulb.gpib.GPIBDeviceIdentifier;
import be.ac.ulb.gpib.GPIBDriver;
import be.ac.ulb.tools.OSUtils;

/**
 * Utility class to communicate with a WT210 power meter via GP-IB.
 * 
 * @author Claas Wilke
 * 
 */
public class WT210Util {

	/** Indicates whether or not the native driver has been loaded, yet. */
	private static boolean initialized = false;

	/** Map of all {@link GPIBDevice}s and their address. */
	private static Map<Integer, GPIBDevice> cachedDevices = new HashMap<Integer, GPIBDevice>();

	/** Set of all initialized {@link GPIBDevice}s. */
	private static Set<GPIBDevice> initializedDevices = new HashSet<GPIBDevice>();

	/**
	 * Searches for an GPIB device at the given address and returns it.
	 * 
	 * @param address
	 *            The address.
	 * @return The {@link GPIBDevice}.
	 * @throws IllegalStateException
	 *             Thrown, if no device can be found at the given address.
	 */
	public static GPIBDevice getDeviceAtAddress(int address) {

		loadGPIBDriver();

		GPIBDevice result = null;

		if (cachedDevices.containsKey(address)) {
			result = cachedDevices.get(address);
		}

		else {
			Enumeration<?> gpibDevicesList = GPIBDeviceIdentifier.getDevices();

			while (gpibDevicesList.hasMoreElements()) {
				GPIBDeviceIdentifier deviceIdentifier = (GPIBDeviceIdentifier) gpibDevicesList
						.nextElement();
				if (deviceIdentifier.getAddress() == address) {
					result = new GPIBDevice(deviceIdentifier.getAddress(),
							deviceIdentifier.getDriver());
					break;
				}
				// no else.
			}
			// end while.

			if (null != result)
				cachedDevices.put(address, result);
			// no else.
		}

		if (null != result)
			return result;
		else
			throw new IllegalStateException("GPIB device with address "
					+ address + " was not found.");
	}

	/**
	 * Helper method to initialize a WT210 for profiling.
	 * 
	 * @param device
	 *            The {@link GPIBDevice} representing the WT210.
	 * @param reinitialize
	 *            Whether or not to reinitialize a device if its has been
	 *            initialized, yet.
	 * @throws IOException
	 *             Thrown if GPIB communication fails.
	 */
	public static void initializeDevice(GPIBDevice device, boolean reinitialize)
			throws IOException {

		if (reinitialize || !initializedDevices.contains(device)) {
			device.open();

			/* Initialize the settings. */
			device.writeCommand("*RST");

			/* Set the measurement condition. */
			device.writeCommand("SAMPLE:HOLD OFF");

			/* Measurement mode = RMS */
			device.writeCommand("MODE RMS");

			/* Frequency Filter = off. */
			device.writeCommand("FILTER OFF");

			/* Scaling & Averaging = off. */
			device.writeCommand("SCALING OFF;AVERAGING OFF");

			/* Voltage range = 7.5V. */
			device.writeCommand("VOLTAGE:RANGE 7.5V");

			/* Current range = 0.5A. */
			device.writeCommand("CURRENT:RANGE 0.5A");

			/* Update rate = 0.1s. */
			device.writeCommand("SAMPLE:RATE 0.1S");

			/*
			 * Set the communication output items: 1. V/A/W -> on, others ->
			 * off.
			 */
			device.writeCommand("MEASURE:ITEM:PRESET NORMAL");

			/*
			 * Set the transition filter used to detect the completion of the
			 * data updating.
			 */
			device.writeCommand("STATUS:FILTER1 FALL");

			initializedDevices.add(device);
		}
		// no else.
	}

	/**
	 * Helper method to load the native GPIB driver.
	 * 
	 * @throws IllegalStateException
	 *             If current OS is not supported.
	 */
	private static void loadGPIBDriver() throws IllegalStateException {

		if (!initialized) {
			/* Load the OS depended Class-Driver */
			switch (OSUtils.getOS()) {
			case WINDOWS32:
			case WINDOWS64:
				GPIBDeviceIdentifier.initialize(
						"be.ac.ulb.gpib.WindowsGPIBDriver", false); //$NON-NLS-1$
				break;

			default:
				throw new IllegalStateException("No supported driver for OS "
						+ OSUtils.getOS());
			}

			initialized = true;
		}
		// no else.
	}

	/**
	 * Sample Program (Output of Normal Measurement Data).
	 * 
	 * @return Execution status.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String args[]) throws ProfilingException,
			IOException, InterruptedException {

		/* String for queries. */
		String response;

		int gpbiAddress = 1;
		GPIBDevice device = getDeviceAtAddress(gpbiAddress);

		if (device == null)
			throw new ProfilingException("Cannot find device at GPIB address "
					+ gpbiAddress + ".");
		// no else.

		initializeDevice(device, true);

		Thread.sleep(1000);

		/*
		 * Read and display the measurement data (It is repeated 10 times in
		 * this program).
		 */
		for (int index = 0; index < 10; index++) {

			/*
			 * Clear the extended event register (Read and trash the response).
			 */
			response = device.sendCommand("STATUS:EESR?");

			/* Wait for the completion of the data updating. */
			device.writeCommand("COMMUNICATE:WAIT 1");

			/* Read out the measurement data. */
			response = device.sendCommand("MEASURE:NORMAL:VALUE?");

			// TODO No SYSO.
			System.out.println(response + "," + System.currentTimeMillis());
		}
		// end for.
	}

	private static void listAvailableDevices() {
		GPIBDeviceIdentifier deviceIdentifier;
		// Get list of all found devices
		Enumeration<?> gpibDevicesList;
		gpibDevicesList = GPIBDeviceIdentifier.getDevices();
		while (gpibDevicesList.hasMoreElements()) {
			deviceIdentifier = (GPIBDeviceIdentifier) gpibDevicesList
					.nextElement();
			int addr = deviceIdentifier.getAddress();
			if (addr > 0) {
				GPIBDriver gpibDriver = deviceIdentifier.getDriver();
				System.out.println("Found GPIB device with address '" + addr);

				// GET IDN
				GPIBDevice myDevice = new GPIBDevice(addr, gpibDriver);
				try {
					myDevice.open(1.0f);

					try {
						System.out.println(myDevice.sendCommand("ID"));
					} catch (IOException e) {
						System.out.println("No answer for the command 'ID'");
						// e.printStackTrace();
					}
					try {
						System.out.println(myDevice.sendCommand("*IDN?"));
					} catch (IOException e) {
						System.out.println("No answer for the command '*IDN?'");
						// e.printStackTrace();
					}
					System.out.println("");

				} catch (IOException e) {
					System.out.println("Error on setting GPIB");
					// e.printStackTrace();
				}
			}
		}
	}
}
