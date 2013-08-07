/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

/**
 * A representation for a range in a document where a structural feature (e.g., a
 * reference) is expected.
 */
public class TestrunExpectedStructuralFeature extends org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunAbstractExpectedElement {
	
	private org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunPlaceholder placeholder;
	
	public TestrunExpectedStructuralFeature(org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunPlaceholder placeholder) {
		super(placeholder.getMetaclass());
		this.placeholder = placeholder;
	}
	
	public org.eclipse.emf.ecore.EStructuralFeature getFeature() {
		return placeholder.getFeature();
	}
	
	/**
	 * Returns the expected placeholder.
	 */
	public org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunSyntaxElement getSymtaxElement() {
		return placeholder;
	}
	
	public String getTokenName() {
		return placeholder.getTokenName();
	}
	
	public java.util.Set<String> getTokenNames() {
		return java.util.Collections.singleton(getTokenName());
	}
	
	public String toString() {
		return "EFeature " + getFeature().getEContainingClass().getName() + "." + getFeature().getName();
	}
	
	public boolean equals(Object o) {
		if (o instanceof TestrunExpectedStructuralFeature) {
			return getFeature().equals(((TestrunExpectedStructuralFeature) o).getFeature());
		}
		return false;
	}
	@Override	
	public int hashCode() {
		return getFeature().hashCode();
	}
	
}
