/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.debug;

public class TestrunDebugCommunicationHelper {
	
	public void sendEvent(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage message, java.io.PrintStream stream) {
		synchronized (stream) {
			stream.println(message.serialize());
		}
	}
	
	/**
	 * Sends a message using the given stream and waits for an answer.
	 * 
	 * @param messageType the type of message to send
	 * @param stream the stream to send the message to
	 * @param reader the reader to obtain the answer from
	 * @param parameters additional parameter to send
	 * 
	 * @return the answer that is received
	 */
	public org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage sendAndReceive(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage message, java.io.PrintStream stream, java.io.BufferedReader reader) {
		synchronized (stream) {
			sendEvent(message, stream);
			org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage response = receive(reader);
			return response;
		}
	}
	
	/**
	 * Receives a message from the given reader. This method block until a message has
	 * arrived.
	 * 
	 * @param reader the read to obtain the message from
	 * 
	 * @return the received message
	 */
	public org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage receive(java.io.BufferedReader reader) {
		try {
			String response = reader.readLine();
			if (response == null) {
				return null;
			}
			org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage receivedMessage = org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage.deserialize(response);
			return receivedMessage;
		} catch (java.io.IOException e) {
			return null;
		}
	}
	
}
