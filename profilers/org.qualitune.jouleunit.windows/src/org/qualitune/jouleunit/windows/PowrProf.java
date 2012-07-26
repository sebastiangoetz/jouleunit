package org.qualitune.jouleunit.windows;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

/**
 * This class can be used to retrieve information from the MS Windows OS using
 * the PowrProf.dll of the OS. JNA is used to access the C code from Java.
 * 
 * @author Claas Wilke
 */
public interface PowrProf extends StdCallLibrary {

	public PowrProf INSTANCE = (PowrProf) Native.loadLibrary("PowrProf",
			PowrProf.class);

	/**
	 * Implementation against the SYSTEM_BATTERY_STATE struct as documented at
	 * 
	 * @see http://msdn.microsoft.com/en-us/library/aa373212.aspx
	 */
	public class SYSTEM_BATTERY_STATE extends Structure {

		/*
		 * Don't change the fields order! What comes first gets the first byte
		 * of C structs.
		 */

		/**
		 * Contains four flags: AcOnLine, BatteryPresent, Charging, Discharging.
		 */
		public int chargingStatus;

		/** Reserved byte (empty / 0). */
		public int spare1;

		/** The theoretical capacity of the battery when new, in mWh. */
		public int maxCapacity;

		/** The theoretical capacity of the battery when new, in mWh. */
		public int remainingCapacity;

		/**
		 * The current rate of discharge of the battery, in mW. A nonzero,
		 * positive rate indicates charging; a negative rate indicates
		 * discharging. Some batteries report only discharging rates. This value
		 * should be treated as a LONG as it can contain negative values (with
		 * the high bit set).
		 */
		public int rate;

		/** The estimated time remaining on the battery, in seconds. */
		public int estimatedTime;

		/**
		 * The manufacturer's suggestion of a capacity, in mWh, at which a low
		 * battery alert should occur. Definitions of low vary from manufacturer
		 * to manufacturer. In general, a warning state will occur before a low
		 * state, but you should not assume that it always will. To reduce risk
		 * of data loss, this value is usually used as the default setting for
		 * the critical battery alarm.
		 */
		public int defaultAlert1;

		/**
		 * The manufacturer's suggestion of a capacity, in mWh, at which a
		 * warning battery alert should occur. Definitions of warning vary from
		 * manufacturer to manufacturer. In general, a warning state will occur
		 * before a low state, but you should not assume that it always will. To
		 * reduce risk of data loss, this value is usually used as the default
		 * setting for the low battery alarm.
		 */
		public int defaultAlert2;

		/**
		 * Returns the current rate of discharge of the battery, in mW. A
		 * nonzero, positive rate indicates charging; a negative rate indicates
		 * discharging. Some batteries report only discharging rates. This value
		 * should be treated as a LONG as it can contain negative values (with
		 * the high bit set).
		 * 
		 * @return The current rate of discharge of the battery, in mW.
		 */
		public int getChargingRate() {
			/* Bug fix. Type conversion can lead to min_value if non available. */
			if (rate != Integer.MIN_VALUE)
				return rate;
			else
				return 0;
		}

		/**
		 * Returns the estimated time remaining on the battery, in seconds.
		 * 
		 * @return The estimated time remaining on the battery, in seconds.
		 */
		public int getEstimatedTime() {
			return this.estimatedTime;
		}

		/**
		 * Returns the theoretical capacity of the battery when new, in mWh.
		 * 
		 * @return The theoretical capacity of the battery when new, in mWh.
		 */
		public int getMaxCapacity() {
			return this.maxCapacity;
		}

		/**
		 * Returns the estimated remaining capacity of the battery, in mWh.
		 * 
		 * @return The estimated remaining capacity of the battery, in mWh.
		 */
		public int getRemainingCapacity() {
			return this.remainingCapacity;
		}

		/**
		 * If <code>true</code>, the system battery charger is currently
		 * operating on external power.
		 * 
		 * @return If <code>true</code>, the system battery charger is currently
		 *         operating on external power.
		 */
		public boolean isAcOnline() {
			return this.getFlag(chargingStatus, 0);
		}

		/**
		 * If <code>true</code>, at least one battery is present in the system.
		 * 
		 * @return If <code>true</code>, at least one battery is present in the
		 *         system.
		 */
		public boolean isBatteryPresent() {
			return this.getFlag(chargingStatus, 1);
		}

		/**
		 * If <code>true</code>, a battery is currently charging.
		 * 
		 * @return If <code>true</code>, a battery is currently charging.
		 */
		public boolean isCharging() {
			return this.getFlag(chargingStatus, 2);
		}

		/**
		 * If <code>true</code>, a battery is currently discharging.
		 * 
		 * @return If <code>true</code>, a battery is currently discharging.
		 */
		public boolean isDischarging() {
			return this.getFlag(chargingStatus, 2);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.sun.jna.Structure#toString()
		 */
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("AcOnLine: " + getFlag(chargingStatus, 0) + "\n");
			result.append("BatteryPresent: " + getFlag(chargingStatus, 1)
					+ "\n");
			result.append("Charging: " + getFlag(chargingStatus, 2) + "\n");
			result.append("Discharging: " + getFlag(chargingStatus, 3) + "\n");
			result.append("Spare1: " + spare1 + "\n");
			result.append("MaxCapacity: " + maxCapacity + "\n");
			result.append("RemainingCapacity: " + remainingCapacity + "\n");
			result.append("Rate: " + rate + "\n");
			result.append("EstimatedTime: " + estimatedTime + "\n");
			result.append("DefaultAlert1: " + defaultAlert1 + "\n");
			result.append("DefaultAlert2: " + defaultAlert2 + "\n");
			return result.toString();
		}

		/**
		 * Converts a given <code>int</code> into a boolean representing the
		 * value of one of its bytes as a boolean.
		 * 
		 * @param value
		 *            The <code>int</code> to be converted.
		 * @param bit
		 *            Which byte shall be returned. If out of range,
		 *            <code>false</code> is returned.
		 * @return The specified byte as a boolean.
		 */
		protected boolean getFlag(int value, int bit) {

			boolean result;

			switch (bit) {
			case 0:
				result = (value >> 24) == 0;
				break;
			case 1:
				result = ((value << 8) >> 24) == 0;
				break;
			case 2:
				result = ((value << 16) >> 24) == 0;
				break;
			case 3:
				result = ((value << 24) >> 24) == 0;
				break;
			default:
				result = false;
			}

			return result;
		}
	}

	/**
	 * CallNtPowerInformation function as documented at @see
	 * http://msdn.microsoft.com/en-us/library/aa372675.aspx
	 * 
	 * Can be used to retrieve the {@link SYSTEM_BATTERY_STATE} from the OS.
	 * 
	 * @param informationLevel
	 *            The information level requested. This value indicates the
	 *            specific power information to be set or retrieved. This
	 *            parameter must be one of the following POWER_INFORMATION_LEVEL
	 *            enumeration type values:
	 *            <ul>
	 *            <li>
	 *            <b>SystemBatteryState 5:</b> The lpInBuffer parameter must be
	 *            NULL; otherwise, the function returns ERROR_INVALID_PARAMETER.
	 *            The lpOutputBuffer buffer receives a SYSTEM_BATTERY_STATE
	 *            structure containing information about the current system
	 *            battery.</li>
	 *            </ul>
	 * @param lpInputBuffer
	 *            A pointer to an optional input buffer. The data type of this
	 *            buffer depends on the information level requested in the
	 *            InformationLevel parameter.
	 * @param nInputBufferSize
	 *            The size of the input buffer, in bytes.
	 * @param lpOutputBuffer
	 *            A pointer to an optional output buffer. The data type of this
	 *            buffer depends on the information level requested in the
	 *            InformationLevel parameter. If the buffer is too small to
	 *            contain the information, the function returns
	 *            STATUS_BUFFER_TOO_SMALL.
	 * @param nOutputBufferSize
	 *            The size of the output buffer, in bytes. Depending on the
	 *            information level requested, this may be a variably sized
	 *            buffer.
	 * @return
	 */
	public int CallNtPowerInformation(int informationLevel,
			Structure lpInputBuffer, long nInputBufferSize,
			Structure lpOutputBuffer, long nOutputBufferSize);
}