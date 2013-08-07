/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

/**
 * The TestrunTokenResolverFactory class provides access to all generated token
 * resolvers. By giving the name of a defined token, the corresponding resolve can
 * be obtained. Despite the fact that this class is called TokenResolverFactory is
 * does NOT create new token resolvers whenever a client calls methods to obtain a
 * resolver. Rather, this class maintains a map of all resolvers and creates each
 * resolver at most once.
 */
public class TestrunTokenResolverFactory implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolverFactory {
	
	private java.util.Map<String, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver> tokenName2TokenResolver;
	private java.util.Map<String, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver> featureName2CollectInTokenResolver;
	private static org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver defaultResolver = new org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestrunDefaultTokenResolver();
	
	public TestrunTokenResolverFactory() {
		tokenName2TokenResolver = new java.util.LinkedHashMap<String, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver>();
		featureName2CollectInTokenResolver = new java.util.LinkedHashMap<String, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver>();
		registerTokenResolver("NUMBER", new org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestrunNUMBERTokenResolver());
		registerTokenResolver("PACKAGE", new org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestrunPACKAGETokenResolver());
		registerTokenResolver("TESTID", new org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestrunTESTIDTokenResolver());
		registerTokenResolver("TEXT", new org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestrunTEXTTokenResolver());
		registerTokenResolver("QUOTED_34_34", new org.qualitune.jouleunit.android.testrun.resource.testrun.analysis.TestrunQUOTED_34_34TokenResolver());
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver createTokenResolver(String tokenName) {
		return internalCreateResolver(tokenName2TokenResolver, tokenName);
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver createCollectInTokenResolver(String featureName) {
		return internalCreateResolver(featureName2CollectInTokenResolver, featureName);
	}
	
	protected boolean registerTokenResolver(String tokenName, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver){
		return internalRegisterTokenResolver(tokenName2TokenResolver, tokenName, resolver);
	}
	
	protected boolean registerCollectInTokenResolver(String featureName, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver){
		return internalRegisterTokenResolver(featureName2CollectInTokenResolver, featureName, resolver);
	}
	
	protected org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver deRegisterTokenResolver(String tokenName){
		return tokenName2TokenResolver.remove(tokenName);
	}
	
	private org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver internalCreateResolver(java.util.Map<String, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver> resolverMap, String key) {
		if (resolverMap.containsKey(key)){
			return resolverMap.get(key);
		} else {
			return defaultResolver;
		}
	}
	
	private boolean internalRegisterTokenResolver(java.util.Map<String, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver> resolverMap, String key, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver) {
		if (!resolverMap.containsKey(key)) {
			resolverMap.put(key,resolver);
			return true;
		}
		return false;
	}
	
}
