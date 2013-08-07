/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.debug;

/**
 * DebugMessages are exchanged between the debug server (the Eclipse debug
 * framework) and the debug client (a running process or interpreter). To exchange
 * messages they are serialized and sent over sockets.
 */
public class TestrunDebugMessage {
	
	private static final char DELIMITER = ':';
	private org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes messageType;
	private String[] arguments;
	
	public TestrunDebugMessage(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes messageType, String[] arguments) {
		super();
		this.messageType = messageType;
		this.arguments = arguments;
	}
	
	public TestrunDebugMessage(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes messageType, java.util.List<String> arguments) {
		super();
		this.messageType = messageType;
		this.arguments = new String[arguments.size()];
		for (int i = 0; i < arguments.size(); i++) {
			this.arguments[i] = arguments.get(i);
		}
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes getMessageType() {
		return messageType;
	}
	
	public String[] getArguments() {
		return arguments;
	}
	
	public String serialize() {
		java.util.List<String> parts = new java.util.ArrayList<String>();
		parts.add(messageType.name());
		for (String argument : arguments) {
			parts.add(argument);
		}
		return org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunStringUtil.encode(DELIMITER, parts);
	}
	
	public static TestrunDebugMessage deserialize(String response) {
		java.util.List<String> parts = org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunStringUtil.decode(response, DELIMITER);
		String messageType = parts.get(0);
		String[] arguments = new String[parts.size() - 1];
		for (int i = 1; i < parts.size(); i++) {
			arguments[i - 1] = parts.get(i);
		}
		org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes type = org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes.valueOf(messageType);
		TestrunDebugMessage message = new TestrunDebugMessage(type, arguments);
		return message;
	}
	
	public boolean hasType(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes type) {
		return this.messageType == type;
	}
	
	public String getArgument(int index) {
		return getArguments()[index];
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + "[" + messageType.name() + ": " + org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunStringUtil.explode(arguments, ", ") + "]";
	}
	
}
