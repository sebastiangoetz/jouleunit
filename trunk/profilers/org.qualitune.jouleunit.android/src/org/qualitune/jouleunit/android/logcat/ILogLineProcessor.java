package org.qualitune.jouleunit.android.logcat;

/**
 * Strategy interface to process a line received by a {@link LogOutputReceiver}.
 * 
 * @author Claas Wilke
 */
public interface ILogLineProcessor {

	/**
	 * Processes the given line. Returns <code>true</code> if the line has been
	 * accepted (processed with an output) by this {@link ILogLineProcessor}.
	 * 
	 * @param line
	 *            The line to be processed.
	 * @return <code>true</code> if this {@link ILogLineProcessor} accepted the
	 *         given line.
	 */
	public boolean processLine(String line);
}
