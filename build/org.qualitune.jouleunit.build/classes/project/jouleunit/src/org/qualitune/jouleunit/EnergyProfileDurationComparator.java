package org.qualitune.jouleunit;

import java.util.Comparator;

/**
 * {@link Comparator} implementation that compares {@link EnergyProfile}s w.r.t.
 * their duration.
 * 
 * @author Claas Wilke
 */
public class EnergyProfileDurationComparator implements
		Comparator<EnergyProfile> {

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

		if (first.getDuration() < second.getDuration())
			return -1;
		else if (first.getDuration() > second.getDuration())
			return 1;
		else
			return 0;
	}
}
