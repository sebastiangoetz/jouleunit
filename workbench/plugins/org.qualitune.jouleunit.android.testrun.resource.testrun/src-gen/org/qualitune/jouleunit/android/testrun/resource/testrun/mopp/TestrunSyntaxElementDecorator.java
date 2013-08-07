/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunSyntaxElementDecorator {
	
	/**
	 * the syntax element to be decorated
	 */
	private org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement decoratedElement;
	
	/**
	 * an array of child decorators (one decorator per child of the decorated syntax
	 * element
	 */
	private TestrunSyntaxElementDecorator[] childDecorators;
	
	/**
	 * a list of the indices that must be printed
	 */
	private java.util.List<Integer> indicesToPrint = new java.util.ArrayList<Integer>();
	
	public TestrunSyntaxElementDecorator(org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement decoratedElement, TestrunSyntaxElementDecorator[] childDecorators) {
		super();
		this.decoratedElement = decoratedElement;
		this.childDecorators = childDecorators;
	}
	
	public void addIndexToPrint(Integer index) {
		indicesToPrint.add(index);
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement getDecoratedElement() {
		return decoratedElement;
	}
	
	public TestrunSyntaxElementDecorator[] getChildDecorators() {
		return childDecorators;
	}
	
	public Integer getNextIndexToPrint() {
		if (indicesToPrint.size() == 0) {
			return null;
		}
		return indicesToPrint.remove(0);
	}
	
	public String toString() {
		return "" + getDecoratedElement();
	}
	
}
