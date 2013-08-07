/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.debug;

public class TestrunSourceLookupParticipant extends org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant {
	
	public String getSourceName(Object object) throws org.eclipse.core.runtime.CoreException {
		if (object instanceof org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunStackFrame) {
			org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunStackFrame frame = (org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunStackFrame) object;
			return frame.getResourceURI();
		}
		return null;
	}
	
}
