/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunMetaInformation implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunMetaInformation {
	
	public String getSyntaxName() {
		return "testrun";
	}
	
	public String getURI() {
		return "http://www.qualitune.org/jouleunit/android/testrun";
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextScanner createLexer() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunAntlrScanner(new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunLexer());
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextParser createParser(java.io.InputStream inputStream, String encoding) {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunParser().createInstance(inputStream, encoding);
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextPrinter createPrinter(java.io.OutputStream outputStream, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource resource) {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunPrinter2(outputStream, resource);
	}
	
	public org.eclipse.emf.ecore.EClass[] getClassesWithSyntax() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunSyntaxCoverageInformationProvider().getClassesWithSyntax();
	}
	
	public org.eclipse.emf.ecore.EClass[] getStartSymbols() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunSyntaxCoverageInformationProvider().getStartSymbols();
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunReferenceResolverSwitch getReferenceResolverSwitch() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunReferenceResolverSwitch();
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolverFactory getTokenResolverFactory() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenResolverFactory();
	}
	
	public String getPathToCSDefinition() {
		return "org.qualitune.jouleunit.android.testrun/metamodel/testrun.cs";
	}
	
	public String[] getTokenNames() {
		return org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunParser.tokenNames;
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenStyle getDefaultTokenStyle(String tokenName) {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyleInformationProvider().getDefaultTokenStyle(tokenName);
	}
	
	public java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunBracketPair> getBracketPairs() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunBracketInformationProvider().getBracketPairs();
	}
	
	public org.eclipse.emf.ecore.EClass[] getFoldableClasses() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunFoldingInformationProvider().getFoldableClasses();
	}
	
	public org.eclipse.emf.ecore.resource.Resource.Factory createResourceFactory() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunResourceFactory();
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunNewFileContentProvider getNewFileContentProvider() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunNewFileContentProvider();
	}
	
	public void registerResourceFactory() {
		org.eclipse.emf.ecore.resource.Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(getSyntaxName(), new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunResourceFactory());
	}
	
	/**
	 * Returns the key of the option that can be used to register a preprocessor that
	 * is used as a pipe when loading resources. This key is language-specific. To
	 * register one preprocessor for multiple resource types, it must be registered
	 * individually using all keys.
	 */
	public String getInputStreamPreprocessorProviderOptionKey() {
		return getSyntaxName() + "_" + "INPUT_STREAM_PREPROCESSOR_PROVIDER";
	}
	
	/**
	 * Returns the key of the option that can be used to register a post-processors
	 * that are invoked after loading resources. This key is language-specific. To
	 * register one post-processor for multiple resource types, it must be registered
	 * individually using all keys.
	 */
	public String getResourcePostProcessorProviderOptionKey() {
		return getSyntaxName() + "_" + "RESOURCE_POSTPROCESSOR_PROVIDER";
	}
	
	public String getLaunchConfigurationType() {
		return "org.qualitune.jouleunit.android.testrun.resource.testrun.ui.launchConfigurationType";
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunNameProvider createNameProvider() {
		return new org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestrunDefaultNameProvider();
	}
	
	public String[] getSyntaxHighlightableTokenNames() {
		org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunAntlrTokenHelper tokenHelper = new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunAntlrTokenHelper();
		java.util.List<String> highlightableTokens = new java.util.ArrayList<String>();
		String[] parserTokenNames = getTokenNames();
		for (int i = 0; i < parserTokenNames.length; i++) {
			// If ANTLR is used we need to normalize the token names
			if (!tokenHelper.canBeUsedForSyntaxHighlighting(i)) {
				continue;
			}
			String tokenName = tokenHelper.getTokenName(parserTokenNames, i);
			if (tokenName == null) {
				continue;
			}
			highlightableTokens.add(tokenName);
		}
		highlightableTokens.add(org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenStyleInformationProvider.TASK_ITEM_TOKEN_NAME);
		return highlightableTokens.toArray(new String[highlightableTokens.size()]);
	}
	
}
