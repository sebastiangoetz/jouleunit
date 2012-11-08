package org.qualitune.jouleunit.android;

import org.qualitune.jouleunit.AbstractJouleProfiler;
import org.qualitune.jouleunit.AbstractPowerRate;
import org.qualitune.jouleunit.PowerRate;

public class ProfilerDummy extends AbstractJouleProfiler {

	@Override
	public PowerRate getPowerRateValue() throws IllegalStateException {
		PowerRate result = new AbstractPowerRate() {

			private static final long serialVersionUID = 354999409769202227L;
			private long powerRate = new Double (Math.random() * -400).longValue();
			private long time = System.nanoTime();

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.qualitune.jouleunit.PowerRate#isPowerAdapterOnline()
			 */
			public boolean isPowerAdapterOnline() {
				return false;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.qualitune.jouleunit.PowerRate#getPowerRate()
			 */
			public double getPowerRate() {
				return powerRate;
			}
			
			/*
			 * (non-Javadoc)
			 * @see org.qualitune.jouleunit.AbstractPowerRate#getTimeStamp()
			 */
			@Override
			public long getTimeStamp() {
				return time;
			}
		};

		return result;
	}

}
