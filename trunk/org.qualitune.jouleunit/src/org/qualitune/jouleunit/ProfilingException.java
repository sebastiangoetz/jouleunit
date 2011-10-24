package org.qualitune.jouleunit;

/**
 * Thrown if an {@link Exception} occurs during profiling.
 * 
 * @author Claas Wilke
 */
public class ProfilingException extends Exception {

	/** ID for serialization. */
	private static final long serialVersionUID = -1846680812609971620L;

	/**
	 * Creates a new {@link ProfilingException} with a given error message.
	 * 
	 * @param msg
	 *            The error message.
	 */
	public ProfilingException(String msg) {
		super(msg);
	}

	/**
	 * Creates a new {@link ProfilingException} with a given error message and
	 * cause.
	 * 
	 * @param msg
	 *            The error message.
	 * @param cause
	 *            The cause.
	 */
	public ProfilingException(String msg, Throwable cause) {
		super(msg);
	}
}
