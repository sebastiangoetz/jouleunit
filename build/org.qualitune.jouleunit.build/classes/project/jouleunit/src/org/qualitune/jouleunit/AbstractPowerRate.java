package org.qualitune.jouleunit;

/**
 * Abstract implementation of {@link PowerRate}.
 * 
 * @author Claas Wilke
 */
public abstract class AbstractPowerRate implements PowerRate {

	/** ID for serialization. */
	private static final long serialVersionUID = -9135933483928308303L;

	/**
	 * The time stamp at which this {@link PowerRate} has been created in
	 * <code>nano (10 ^ -9) seconds</code>.
	 */
	protected long timeStamp;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(PowerRate o) {
		return new Long(timeStamp != 0 ? timeStamp : 0l).compareTo(new Long(o
				.getTimeStamp() != 0 ? o.getTimeStamp() : 0l));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.profiler.EMeasuringValue#getTimeStamp()
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();

		result.append(this.getClass().getSimpleName() + "[time=");
		result.append(this.timeStamp + ",rate=");
		result.append(this.getPowerRate() + "[mW],capacity=");
		result.append("[mJ]]");

		return result.toString();
	}
}
