/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunTaskItem {
	
	private String keyword;
	
	private String message;
	
	private int line;
	
	private int charStart;
	
	private int charEnd;
	
	public TestrunTaskItem(String keyword, String message, int line, int charStart, int charEnd) {
		super();
		this.keyword = keyword;
		this.message = message;
		this.line = line;
		this.charStart = charStart;
		this.charEnd = charEnd;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getCharStart() {
		return charStart;
	}
	
	public int getCharEnd() {
		return charEnd;
	}
	
}
