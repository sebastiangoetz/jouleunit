package org.qualitune.jouleunit.nao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.PowerRate;
import org.qualitune.jouleunit.SimpleEnergyProfile;
import org.qualitune.jouleunit.persist.RestoredPowerRate;
import org.qualitune.naoservice.client.Nao;
import org.qualitune.naoservice.client.NaoConstants;
import org.qualitune.naoservice.client.NaoUtil;

/**
 * {@link JouleProfiler} implementation for Nao humanoid robot.
 * 
 * @author Claas Wilke
 */
public class NaoProfiler extends AbstractJouleProfiler {

	/** The IDs of the {@link Nao} that are profiled. */
	protected static List<String> ids;

	/** The IP of the Nao service. */
	protected String ip;

	/** The port of the Nao service. */
	protected int port;

	/** The {@link Nao} proxy of this {@link NaoProfiler}. */
	protected Nao nao;

	/**
	 * The ID of the profiling process on the {@link Nao} for this
	 * {@link NaoProfiler}.
	 */
	protected String profilingSessionID;

	/**
	 * Creates a new {@link NaoProfiler} for a given IP address and port at
	 * which the Nao service should be running.
	 * 
	 * @param ip
	 *            The IP of the Nao service.
	 * @param port
	 *            The port of the Nao service.
	 */
	public NaoProfiler(String ip, int port) {

		if (ip == null || ip.length() == 0)
			throw new IllegalArgumentException(
					"Argument 'ip' cannot be null or empty.");
		else if (port <= 0)
			throw new IllegalArgumentException(
					"Argument 'port' cannot be 0 or negative.");
		// no else.

		this.ip = ip;
		this.port = port;
	}

	/**
	 * Creates a new {@link NaoProfiler} for a given {@link Nao}.
	 * 
	 * @param nao
	 *            The {@link Nao} to be profiled.
	 */
	public NaoProfiler(Nao nao) {
		this.ip = nao.getIP();
		this.port = nao.getPort();
		this.nao = nao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EProfiler#getEMeasuringValue()
	 */
	public PowerRate getPowerRateValue() {
		return new NaoPowerRate(ip, port);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.EProfiler#endProfiling()
	 */
	@Override
	public EnergyProfile endProfiling() {
		if (this.isProfiling) {

			Map<Double, List<String>> rawValues = NaoUtil
					.stopProfilingOfSensors(nao, ids, profilingSessionID);

			for (Double timeStamp : rawValues.keySet()) {
				RestoredPowerRate value = new RestoredPowerRate();
				long voltageMax = Long.parseLong(rawValues.get(timeStamp)
						.get(0));
				long voltageMin = Long.parseLong(rawValues.get(timeStamp)
						.get(1));
				double current = Double.parseDouble(rawValues.get(timeStamp)
						.get(2));
				/* Voltage min and max is measured for six individual cells. */
				long powerRate = Math.round((voltageMax + voltageMin) * 3
						* current);

				if (isBaseNoiseCosiderationEnabled)
					powerRate = powerRate - estimatedBaseNoise;
				// no else.

				if (isProbeEffectCosiderationEnabled)
					powerRate = powerRate - estimatedProbeEffect;
				// no else.

				value.setPowerRate(powerRate);
				value.setPowerAdapterOnlineEnabled(powerRate > 0);
				value.setTimeStamp(Math.round(timeStamp * 1000000000l));
				currentProfile.addPowerRateValue(value);
			}

			this.isProfiling = false;
			EnergyProfile result = currentProfile;
			currentProfile = null;

			return result;
		}

		else
			throw new IllegalStateException(
					"Cannot end profiling before start profiling.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.EProfiler#startProfiling()
	 */
	@Override
	public void startProfiling() {

		if (isProfiling)
			throw new IllegalStateException("Profiler is already running.");

		else {
			currentProfile = new SimpleEnergyProfile();

			if (ids == null || ids.size() == 0) {
				ids = new ArrayList<String>();
				ids.add(NaoConstants.SENSOR_NAME_BATTERY_CELL_VOLTAGE_MAX);
				ids.add(NaoConstants.SENSOR_NAME_BATTERY_CELL_VOLTAGE_MIN);
				ids.add(NaoConstants.SENSOR_NAME_BATTERY_CURRENT);
			}
			// no else.

			profilingSessionID = NaoUtil.startProfilingOfSensors(nao, ids,
					this.profilingInterval);

			isProfiling = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.AbstractJouleProfiler#computeRefreshRate()
	 */
	@Override
	protected void computeRefreshRate() {
		profilingInterval = MINIMAL_REFRESH_RATE;
	}

}
