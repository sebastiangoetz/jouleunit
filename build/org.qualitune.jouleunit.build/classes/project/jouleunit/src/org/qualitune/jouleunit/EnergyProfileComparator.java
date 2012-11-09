package org.qualitune.jouleunit;

import java.util.Comparator;

/**
 * {@link Comparator} implementation that compares {@link EnergyProfile}s w.r.t.
 * their energy consumption.
 * 
 * @author Claas Wilke
 */
public class EnergyProfileComparator implements Comparator<EnergyProfile> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(EnergyProfile first, EnergyProfile second) {

		if (first == null || second == null)
			throw new IllegalArgumentException(
					"The arguments 'first' and 'second' may not be null.");
		// no else.

		if (first.getConsumedEnergy() < second.getConsumedEnergy())
			return -1;
		else if (first.getConsumedEnergy() > second.getConsumedEnergy())
			return 1;
		else
			return 0;
	}
}
