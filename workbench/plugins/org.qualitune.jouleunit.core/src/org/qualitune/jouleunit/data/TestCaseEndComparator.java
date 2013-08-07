package org.qualitune.jouleunit.data;

import java.util.Comparator;

import org.qualitune.jouleunit.coordinator.TestCaseProfile;

/**
 * {@link Comparator} implementation to compare {@link TestCaseProfile}s based
 * on their end time stamp.
 * 
 * @author Claas Wilke
 */
public class TestCaseEndComparator implements Comparator<TestCaseProfile> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(TestCaseProfile arg0, TestCaseProfile arg1) {
		return new Long(arg0.getEndTime())
				.compareTo(new Long(arg1.getEndTime()));
	}
}
