package org.qualitune.jouleunit.kiwigrid;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.qualitune.jouleunit.AbstractPowerRate;
import org.qualitune.jouleunit.PowerRate;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 * {@link PowerRate} obtained from a KiwiGrid plug.
 * 
 * @author Claas Wilke
 * @author Johannes Waltsgott (his source code was adapted).
 */
public class KiwiGridPowerRate extends AbstractPowerRate {

	/** ID for serialization. */
	private static final long serialVersionUID = -7924215605601160745L;

	private static final String strCommunity = "public";

	private static final String OBJECT_ID_KIWIGRID_PLUG = ".1.3.6.1.4.1.35389.5.3.1.3";

	/** The charging rate in <code>mWatt</code>. */
	protected long chargingRate;

	/**
	 * Creates a new {@link KiwiGridPowerRate}.
	 * 
	 * @param ipV6address
	 *            The IPv6 address of the KiwiGrid plug used for profiling.
	 * @throws IllegalStateException
	 *             Thrown, if the given plug is not available or the given IP
	 *             address is wrong.
	 */
	public KiwiGridPowerRate(String ipV6address) throws IllegalStateException {
		if (ipV6address == null)
			throw new IllegalArgumentException(
					"Argument 'ipV6address' cannot be null.");
		// no else.

		/* Create Payload Data Unit. */
		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(OBJECT_ID_KIWIGRID_PLUG)));
		pdu.setType(PDU.GET);

		try {
			/* Create target address. */
			Address targetAddress;
			targetAddress = new UdpAddress(InetAddress.getByName(ipV6address),
					161);

			/* Create CommunityTarget. */
			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString(strCommunity));
			target.setAddress(targetAddress);
			target.setVersion(SnmpConstants.version2c);
			target.setRetries(3);
			target.setTimeout(5000);

			/* Create new SNMP object and send request. */
			Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
			timeStamp = System.nanoTime();
			snmp.listen();
			ResponseEvent response = snmp.send(pdu, target);
			snmp.close();

			if (response.getResponse() == null) {
				throw new IllegalStateException("Request "
						+ response.getRequest().toString() + " timed out.");
			}

			else {
				if (response.getResponse().size() >= 1) {

					/* Convert the result from hex to float. */
					String result = response.getResponse().get(0).getVariable()
							.toString();
					Long i = Long.parseLong(result.replaceAll(":", ""), 16);
					Float f = Float.intBitsToFloat(i.intValue());

					chargingRate = -Math.round(new Float(f * 1000f + 0.5f)
							.longValue());
				}

				else
					throw new IllegalStateException(
							"Profiling of KiwiGrid device failed. Response was empty.");
			}
		}

		catch (UnknownHostException e) {
			throw new IllegalStateException(
					"Profiling of KiwiGrid device failed: " + e.getMessage());
		}

		catch (IOException e) {
			throw new IllegalStateException(
					"Profiling of KiwiGrid device failed: " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.eunit.profiler.EMeasuredValue#getChargingRateInMilliWatt()
	 */
	public double getPowerRate() {
		return chargingRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EMeasuredValue#isPowerAdapterOnline()
	 */
	public boolean isPowerAdapterOnline() {
		/*
		 * Power adapter for KiwiGrid is always online if the plug is working
		 * (online means operable).
		 */
		return true;
	}
}
