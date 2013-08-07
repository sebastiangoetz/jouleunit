/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.debug;

/**
 * The DebugProxy allows to communicate between the interpreter, which runs in a
 * separate thread or process and the Eclipse Debug framework (i.e., the
 * DebugTarget class).
 */
public class TestrunDebugProxy {
	
	public static final int STARTUP_DELAY = 1000;
	
	private java.io.PrintStream output;
	
	private java.io.BufferedReader reader;
	
	private org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugTarget debugTarget;
	
	private org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugCommunicationHelper communicationHelper = new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugCommunicationHelper();
	
	public TestrunDebugProxy(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugTarget debugTarget, int requestPort) throws java.net.UnknownHostException, java.io.IOException {
		this.debugTarget = debugTarget;
		// give interpreter a chance to start
		try {
			Thread.sleep(STARTUP_DELAY);
		} catch (InterruptedException e) {
		}
		startSocket(requestPort);
	}
	
	private void startSocket(int requestPort) throws java.net.UnknownHostException, java.io.IOException {
		// creating client proxy socket (trying to connect)...
		java.net.Socket client = new java.net.Socket("localhost", requestPort);
		// creating client proxy socket - done. (connected)
		try {
			java.io.BufferedInputStream input = new java.io.BufferedInputStream(client.getInputStream());
			reader = new java.io.BufferedReader(new java.io.InputStreamReader(input));
		} catch (java.io.IOException e) {
			System.out.println(e);
		}
		try {
			output = new java.io.PrintStream(client.getOutputStream());
		} catch (java.io.IOException e) {
			System.out.println(e);
		}
	}
	
	public void resume() {
		sendCommand(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes.RESUME);
	}
	
	public void stepOver() {
		sendCommand(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes.STEP_OVER);
	}
	
	public void stepInto() {
		sendCommand(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes.STEP_INTO);
	}
	
	public void stepReturn() {
		sendCommand(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes.STEP_RETURN);
	}
	
	public void terminate() {
		sendCommand(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes.EXIT);
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage getStack() {
		return sendCommandAndRead(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes.GET_STACK);
	}
	
	public void addLineBreakpoint(String location, int line) {
		org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage message = new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes.ADD_LINE_BREAKPOINT, new String[] {location, Integer.toString(line)});
		communicationHelper.sendEvent(message, output);
	}
	
	public void removeLineBreakpoint(String location, int line) {
		org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage message = new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes.REMOVE_LINE_BREAKPOINT, new String[] {location, Integer.toString(line)});
		communicationHelper.sendEvent(message, output);
	}
	
	public org.eclipse.debug.core.model.IVariable[] getStackVariables(String stackFrame) {
		org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage response = sendCommandAndRead(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes.GET_FRAME_VARIABLES, new String[] {stackFrame});
		String[] ids = response.getArguments();
		// fetch all variables
		org.eclipse.debug.core.model.IVariable[] variables = getVariables(ids);
		return variables;
	}
	
	public org.eclipse.debug.core.model.IVariable[] getVariables(String... requestedIDs) {
		org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage response = sendCommandAndRead(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes.GET_VARIABLES, requestedIDs);
		String[] varStrings = response.getArguments();
		org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugVariable[] variables  = new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugVariable[varStrings.length];
		int i = 0;
		for (String varString : varStrings) {
			java.util.Map<String, String> properties = org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunStringUtil.convertFromString(varString);
			
			// convert varString to variables and values
			String valueString = properties.get("!valueString");
			String valueRefType = "valueRefType";
			java.util.Map<String, Long> childVariables = new java.util.TreeMap<String, Long>(new java.util.Comparator<String>() {
				public int compare(String s1, String s2) {
					return s1.compareToIgnoreCase(s2);
				}
			});
			for (String property : properties.keySet()) {
				// ignore special properties - they are not children
				if (property.startsWith("!")) {
					continue;
				}
				childVariables.put(property, Long.parseLong(properties.get(property)));
			}
			String id = properties.get("!id");
			org.eclipse.debug.core.model.IValue value = new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugValue(debugTarget, id, valueString, valueRefType, childVariables);
			
			String variableName = properties.get("!name");
			String variableRefType = properties.get("!type");
			
			org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugVariable variable = new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugVariable(debugTarget, variableName, value, variableRefType);
			variables[i++] = variable;
		}
		return variables;
	}
	
	private void sendCommand(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes command, String... parameters) {
		org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage message = new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage(command, parameters);
		communicationHelper.sendEvent(message, output);
	}
	
	private org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage sendCommandAndRead(org.qualitune.jouleunit.android.testrun.resource.testrun.debug.ETestrunDebugMessageTypes command, String... parameters) {
		org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage message = new org.qualitune.jouleunit.android.testrun.resource.testrun.debug.TestrunDebugMessage(command, parameters);
		return communicationHelper.sendAndReceive(message, output, reader);
	}
	
}
