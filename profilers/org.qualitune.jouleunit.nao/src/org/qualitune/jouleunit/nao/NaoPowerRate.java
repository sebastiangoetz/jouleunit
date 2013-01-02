package org.qualitune.jouleunit.nao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.qualitune.jouleunit.AbstractPowerRate;
import org.qualitune.jouleunit.PowerRate;
import org.qualitune.naoservice.client.NaoConstants;
import org.qualitune.naoservice.client.NaoData;
import org.qualitune.naoservice.client.NaoUtil;
import org.qualitune.naoservice.client.NaoUtilException;

/**
 * An {@link PowerRate} from a Nao humanoid robot.
 * 
 * @author Claas Wilke
 */
public class NaoPowerRate extends AbstractPowerRate {

	/** ID for serialization. */
	private static final long serialVersionUID = -5282066753324361344L;

	/** The Nao's battery current in <code>A</code>. */
	protected double batteryCurrent;

	/** The Nao's battery minimum voltage at one of it cells in <code>mV</code>. */
	protected double batteryVoltageMin;

	/** The Nao's battery maximum voltage at one of it cells in <code>mV</code>. */
	protected double batteryVoltageMax;

	/** Whether or not the Nao is plugged to the external power supply. */
	protected boolean isPowerAdapterOnline;

	/** The Nao's battery remaining capacity in <code>mJ</code>. */
	protected double remainingCapacity;

	private static Map<String, List<Integer>> identifiersForNao = new HashMap<String, List<Integer>>();

	/**
	 * Creates a new {@link NaoPowerRate}.
	 * 
	 * @param ipAddress
	 *            The IP address of the Nao.
	 * @param port
	 *            The port of the Nao service.
	 */
	public NaoPowerRate(String ipAddress, int port)
			throws IllegalStateException {

		if (ipAddress == null || ipAddress.length() == 0)
			throw new IllegalArgumentException(
					"Argument 'ipAddress' cannot be null or empty.");
		else if (port <= 0)
			throw new IllegalArgumentException(
					"Argument 'port' cannot be 0 or negative.");
		// no else.

		NaoData nao = new NaoData(ipAddress, port);

		Map<String, String> values;
		try {

			List<Integer> identifiers = identifiersForNao.get(ipAddress);

			/* Probably compute offset. */
			if (null == identifiers) {
				List<String> ids = NaoUtil.getSensorValueIDs(nao);

				identifiers = new ArrayList<Integer>(5);
				identifiers
						.add(ids.indexOf(NaoConstants.SENSOR_NAME_BATTERY_CELL_VOLTAGE_MIN));
				identifiers
						.add(ids.indexOf(NaoConstants.SENSOR_NAME_BATTERY_CELL_VOLTAGE_MAX));
				identifiers.add(ids
						.indexOf(NaoConstants.SENSOR_NAME_BATTERY_CURRENT));
				identifiers
						.add(ids.indexOf(NaoConstants.SENSOR_NAME_BATTERY_REMAINING_CAPACITY));
				identifiers.add(ids
						.indexOf(NaoConstants.SENSOR_NAME_BATTERY_STATUS));

				identifiersForNao.put(ipAddress, identifiers);
			}
			// no else.

			values = NaoUtil.getSensorValues(nao, identifiers);
			batteryCurrent = Double.parseDouble(values
					.get(NaoConstants.SENSOR_NAME_BATTERY_CURRENT));
			batteryVoltageMin = Double.parseDouble(values
					.get(NaoConstants.SENSOR_NAME_BATTERY_CELL_VOLTAGE_MIN));
			batteryVoltageMax = Double.parseDouble(values
					.get(NaoConstants.SENSOR_NAME_BATTERY_CELL_VOLTAGE_MAX));
			remainingCapacity = Double.parseDouble(values
					.get(NaoConstants.SENSOR_NAME_BATTERY_REMAINING_CAPACITY));

			// TODO FIXME
			isPowerAdapterOnline = false;
			// isPowerAdapterOnline = getBit(Integer.parseInt(values
			// .get(NaoConstants.SENSOR_NAME_BATTERY_STATUS)), 7);

			timeStamp = Math.round(Double.parseDouble(values
					.get(NaoConstants.TIME_STAMP)) * 1000000000);
		}

		catch (NaoUtilException e) {
			throw new IllegalStateException(
					"Where not able to request the Nao's energy consumption.",
					e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.eunit.profiler.EMeasuringValue#getChargingRateInMilliWatt()
	 */
	public double getPowerRate() {
		return Math.round(batteryCurrent
				* (batteryVoltageMin + batteryVoltageMax) * 3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.eunit.profiler.EMeasuringValue#getRemainingCapacityInMilliJoule
	 * ()
	 */
	public long getRemainingCapacityInMilliJoule() {
		return Math.round(remainingCapacity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EMeasuringValue#isPowerAdapterOnline()
	 */
	public boolean isPowerAdapterOnline() {
		return isPowerAdapterOnline;
	}

	/**
	 * Returns the value of a bit flag within a given integer.
	 * 
	 * @param value
	 *            The int masking the flag.
	 * @param position
	 *            The position of the flag within the integer.
	 * @return <code>true</code> or <code>false</code>.
	 */
	protected static boolean getBit(int value, int position) {
		int bitToGet = 1 << position;
		if ((value & bitToGet) != 0)
			return true;
		else
			return false;
	}
}
