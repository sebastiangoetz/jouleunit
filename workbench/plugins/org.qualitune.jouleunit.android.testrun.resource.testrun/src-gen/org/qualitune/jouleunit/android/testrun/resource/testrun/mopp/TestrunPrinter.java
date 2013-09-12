/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;

public class TestrunPrinter implements org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextPrinter {
	
	protected org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolverFactory tokenResolverFactory = new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenResolverFactory();
	
	protected java.io.OutputStream outputStream;
	
	/**
	 * Holds the resource that is associated with this printer. This may be null if
	 * the printer is used stand alone.
	 */
	private org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource resource;
	
	private java.util.Map<?, ?> options;
	private String encoding = System.getProperty("file.encoding");
	
	public TestrunPrinter(java.io.OutputStream outputStream, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource resource) {
		super();
		this.outputStream = outputStream;
		this.resource = resource;
	}
	
	protected int matchCount(java.util.Map<String, Integer> featureCounter, java.util.Collection<String> needed) {
		int pos = 0;
		int neg = 0;
		
		for (String featureName : featureCounter.keySet()) {
			if (needed.contains(featureName)) {
				int value = featureCounter.get(featureName);
				if (value == 0) {
					neg += 1;
				} else {
					pos += 1;
				}
			}
		}
		return neg > 0 ? -neg : pos;
	}
	
	protected void doPrint(org.eclipse.emf.ecore.EObject element, java.io.PrintWriter out, String globaltab) {
		if (element == null) {
			throw new java.lang.IllegalArgumentException("Nothing to write.");
		}
		if (out == null) {
			throw new java.lang.IllegalArgumentException("Nothing to write on.");
		}
		
		if (element instanceof org.qualitune.jouleunit.android.testrun.TestRun) {
			print_org_qualitune_jouleunit_android_testrun_TestRun((org.qualitune.jouleunit.android.testrun.TestRun) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.JunitTestCase) {
			print_org_qualitune_jouleunit_android_testrun_JunitTestCase((org.qualitune.jouleunit.android.testrun.JunitTestCase) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.TestSuite) {
			print_org_qualitune_jouleunit_android_testrun_TestSuite((org.qualitune.jouleunit.android.testrun.TestSuite) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.TestCase) {
			print_org_qualitune_jouleunit_android_testrun_TestCase((org.qualitune.jouleunit.android.testrun.TestCase) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.Block) {
			print_org_qualitune_jouleunit_android_testrun_Block((org.qualitune.jouleunit.android.testrun.Block) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement) {
			print_org_qualitune_jouleunit_android_testrun_ClickOnScreenStatement((org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.CursorStatement) {
			print_org_qualitune_jouleunit_android_testrun_CursorStatement((org.qualitune.jouleunit.android.testrun.CursorStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.DisplayStatement) {
			print_org_qualitune_jouleunit_android_testrun_DisplayStatement((org.qualitune.jouleunit.android.testrun.DisplayStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.EnterStatement) {
			print_org_qualitune_jouleunit_android_testrun_EnterStatement((org.qualitune.jouleunit.android.testrun.EnterStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.HomeButtonStatement) {
			print_org_qualitune_jouleunit_android_testrun_HomeButtonStatement((org.qualitune.jouleunit.android.testrun.HomeButtonStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.OpenSettingsStatement) {
			print_org_qualitune_jouleunit_android_testrun_OpenSettingsStatement((org.qualitune.jouleunit.android.testrun.OpenSettingsStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.SendPortMessageStatement) {
			print_org_qualitune_jouleunit_android_testrun_SendPortMessageStatement((org.qualitune.jouleunit.android.testrun.SendPortMessageStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.StartActivityStatement) {
			print_org_qualitune_jouleunit_android_testrun_StartActivityStatement((org.qualitune.jouleunit.android.testrun.StartActivityStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.WaitStatement) {
			print_org_qualitune_jouleunit_android_testrun_WaitStatement((org.qualitune.jouleunit.android.testrun.WaitStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.UnlockStatement) {
			print_org_qualitune_jouleunit_android_testrun_UnlockStatement((org.qualitune.jouleunit.android.testrun.UnlockStatement) element, globaltab, out);
			return;
		}
		if (element instanceof org.qualitune.jouleunit.android.testrun.TestStatement) {
			print_org_qualitune_jouleunit_android_testrun_TestStatement((org.qualitune.jouleunit.android.testrun.TestStatement) element, globaltab, out);
			return;
		}
		
		addWarningToResource("The printer can not handle " + element.eClass().getName() + " elements", element);
	}
	
	protected org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunReferenceResolverSwitch getReferenceResolverSwitch() {
		return (org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunReferenceResolverSwitch) new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunMetaInformation().getReferenceResolverSwitch();
	}
	
	protected void addWarningToResource(final String errorMessage, org.eclipse.emf.ecore.EObject cause) {
		org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource resource = getResource();
		if (resource == null) {
			// the resource can be null if the printer is used stand alone
			return;
		}
		resource.addProblem(new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunProblem(errorMessage, org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemType.PRINT_PROBLEM, org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemSeverity.WARNING), cause);
	}
	
	public void setOptions(java.util.Map<?,?> options) {
		this.options = options;
	}
	
	public java.util.Map<?,?> getOptions() {
		return options;
	}
	
	public void setEncoding(String encoding) {
		if (encoding != null) {
			this.encoding = encoding;
		}
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource getResource() {
		return resource;
	}
	
	/**
	 * Calls {@link #doPrint(EObject, PrintWriter, String)} and writes the result to
	 * the underlying output stream.
	 */
	public void print(org.eclipse.emf.ecore.EObject element) throws java.io.IOException {
		java.io.PrintWriter out = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.BufferedOutputStream(outputStream), encoding));
		doPrint(element, out, "");
		out.flush();
		out.close();
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestRun(org.qualitune.jouleunit.android.testrun.TestRun element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(11);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__AUT));
		printCountingMap.put("aut", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__PACKAGE_UNDER_TEST));
		printCountingMap.put("packageUnderTest", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_APK));
		printCountingMap.put("junitApk", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_PACKAGE));
		printCountingMap.put("junitPackage", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__NO_OF_RUNS));
		printCountingMap.put("noOfRuns", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__IDLE_TIME));
		printCountingMap.put("idleTime", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__HARDWARE_PROFILING_ON));
		printCountingMap.put("hardwareProfilingOn", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__REMOTE_RUN));
		printCountingMap.put("remoteRun", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__EXECUTABLES));
		printCountingMap.put("executables", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__WAIT_FOR_FULL_BATTERY));
		printCountingMap.put("waitForFullBattery", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CsString)
		out.print("TestRun");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__NAME));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__NAME), element));
				out.print(" ");
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_qualitune_jouleunit_android_testrun_TestRun_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_qualitune_jouleunit_android_testrun_TestRun_1(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_qualitune_jouleunit_android_testrun_TestRun_2(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_qualitune_jouleunit_android_testrun_TestRun_3(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_qualitune_jouleunit_android_testrun_TestRun_4(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_qualitune_jouleunit_android_testrun_TestRun_5(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_qualitune_jouleunit_android_testrun_TestRun_6(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		print_org_qualitune_jouleunit_android_testrun_TestRun_7(element, localtab, out, printCountingMap);
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_qualitune_jouleunit_android_testrun_TestRun_7(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestRun_0(org.qualitune.jouleunit.android.testrun.TestRun element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("applicationUnderTest");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderInQuotes)
		count = printCountingMap.get("aut");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__AUT));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getTestRunAutReferenceResolver().deResolve((org.qualitune.jouleunit.android.testrun.ApkFile) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__AUT)), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__AUT), element));
			}
			printCountingMap.put("aut", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("packageUnderTest");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__PACKAGE_UNDER_TEST));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("PACKAGE");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__PACKAGE_UNDER_TEST), element));
				out.print(" ");
			}
			printCountingMap.put("packageUnderTest", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestRun_1(org.qualitune.jouleunit.android.testrun.TestRun element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("unitTests");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderInQuotes)
		count = printCountingMap.get("junitApk");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_APK));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getTestRunJunitApkReferenceResolver().deResolve((org.qualitune.jouleunit.android.testrun.ApkFile) o, element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_APK)), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_APK), element));
			}
			printCountingMap.put("junitApk", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("junitPackage");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_PACKAGE));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("PACKAGE");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_PACKAGE), element));
				out.print(" ");
			}
			printCountingMap.put("junitPackage", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestRun_2(org.qualitune.jouleunit.android.testrun.TestRun element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("numberOfRuns");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("noOfRuns");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__NO_OF_RUNS));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("NUMBER");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__NO_OF_RUNS), element));
				out.print(" ");
			}
			printCountingMap.put("noOfRuns", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestRun_3(org.qualitune.jouleunit.android.testrun.TestRun element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("idleTime");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("idleTime");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__IDLE_TIME));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("NUMBER");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__IDLE_TIME), element));
				out.print(" ");
			}
			printCountingMap.put("idleTime", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestRun_4(org.qualitune.jouleunit.android.testrun.TestRun element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("hardwareProfiling");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (BooleanTerminal)
		count = printCountingMap.get("hardwareProfilingOn");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__HARDWARE_PROFILING_ON));
			if (o != null) {
			}
			printCountingMap.put("hardwareProfilingOn", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestRun_5(org.qualitune.jouleunit.android.testrun.TestRun element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("run");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (BooleanTerminal)
		count = printCountingMap.get("remoteRun");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__REMOTE_RUN));
			if (o != null) {
			}
			printCountingMap.put("remoteRun", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestRun_6(org.qualitune.jouleunit.android.testrun.TestRun element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("eachTestWithFullBattery");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print(":");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (BooleanTerminal)
		count = printCountingMap.get("waitForFullBattery");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__WAIT_FOR_FULL_BATTERY));
			if (o != null) {
			}
			printCountingMap.put("waitForFullBattery", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestRun_7(org.qualitune.jouleunit.android.testrun.TestRun element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("executables");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__EXECUTABLES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("executables", count - 1);
		}
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_JunitTestCase(org.qualitune.jouleunit.android.testrun.JunitTestCase element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.JUNIT_TEST_CASE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("UnitTestCase");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.JUNIT_TEST_CASE__NAME));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("TESTID");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.JUNIT_TEST_CASE__NAME), element));
				out.print(" ");
			}
			printCountingMap.put("name", count - 1);
		}
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_TestSuite(org.qualitune.jouleunit.android.testrun.TestSuite element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(4);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__SET_UP));
		printCountingMap.put("setUp", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__TEAR_DOWN));
		printCountingMap.put("tearDown", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__EXECUTABLES));
		printCountingMap.put("executables", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("TestSuite");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__NAME));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		out.print(" ");
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_qualitune_jouleunit_android_testrun_TestSuite_0(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		sWriter = new java.io.StringWriter();
		out1 = new java.io.PrintWriter(sWriter);
		printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
		print_org_qualitune_jouleunit_android_testrun_TestSuite_1(element, localtab, out1, printCountingMap1);
		if (printCountingMap.equals(printCountingMap1)) {
			out1.close();
		} else {
			out1.flush();
			out1.close();
			out.print(sWriter.toString());
			printCountingMap.putAll(printCountingMap1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		print_org_qualitune_jouleunit_android_testrun_TestSuite_2(element, localtab, out, printCountingMap);
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_qualitune_jouleunit_android_testrun_TestSuite_2(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
		out.print(" ");
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestSuite_0(org.qualitune.jouleunit.android.testrun.TestSuite element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("SetUp");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		out.print(" ");
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "		";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("setUp");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__SET_UP));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("setUp", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
		out.print(" ");
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestSuite_1(org.qualitune.jouleunit.android.testrun.TestSuite element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("TearDown");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		out.print(" ");
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "		";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("tearDown");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__TEAR_DOWN));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("tearDown", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
		out.print(" ");
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestSuite_2(org.qualitune.jouleunit.android.testrun.TestSuite element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("executables");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__EXECUTABLES));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("executables", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_TestCase(org.qualitune.jouleunit.android.testrun.TestCase element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_CASE__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_CASE__BEHAVIOR));
		printCountingMap.put("behavior", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("TestCase");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_CASE__NAME));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_CASE__NAME), element));
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("{");
		out.print(" ");
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("behavior");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_CASE__BEHAVIOR));
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("behavior", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (CsString)
		out.print("}");
		out.print(" ");
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_Block(org.qualitune.jouleunit.android.testrun.Block element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.BLOCK__STATEMENTS));
		printCountingMap.put("statements", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		boolean iterate = true;
		java.io.StringWriter sWriter = null;
		java.io.PrintWriter out1 = null;
		java.util.Map<String, Integer> printCountingMap1 = null;
		// DEFINITION PART BEGINS (CompoundDefinition)
		iterate = true;
		while (iterate) {
			sWriter = new java.io.StringWriter();
			out1 = new java.io.PrintWriter(sWriter);
			printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
			print_org_qualitune_jouleunit_android_testrun_Block_0(element, localtab, out1, printCountingMap1);
			if (printCountingMap.equals(printCountingMap1)) {
				iterate = false;
				out1.close();
			} else {
				out1.flush();
				out1.close();
				out.print(sWriter.toString());
				printCountingMap.putAll(printCountingMap1);
			}
		}
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_Block_0(org.qualitune.jouleunit.android.testrun.Block element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("statements");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.BLOCK__STATEMENTS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("statements", count - 1);
		}
		// DEFINITION PART BEGINS (LineBreak)
		out.println();
		out.print(localtab);
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_ClickOnScreenStatement(org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CLICK_ON_SCREEN_STATEMENT__X));
		printCountingMap.put("x", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CLICK_ON_SCREEN_STATEMENT__Y));
		printCountingMap.put("y", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("CLICK");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("ON");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("SCREEN");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("x");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CLICK_ON_SCREEN_STATEMENT__X));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("NUMBER");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CLICK_ON_SCREEN_STATEMENT__X), element));
			}
			printCountingMap.put("x", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("y");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CLICK_ON_SCREEN_STATEMENT__Y));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("NUMBER");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CLICK_ON_SCREEN_STATEMENT__Y), element));
				out.print(" ");
			}
			printCountingMap.put("y", count - 1);
		}
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_CursorStatement(org.qualitune.jouleunit.android.testrun.CursorStatement element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CURSOR_STATEMENT__DIRECTION));
		printCountingMap.put("direction", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("CURSOR");
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingDefaultToken)
		count = printCountingMap.get("direction");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CURSOR_STATEMENT__DIRECTION));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CURSOR_STATEMENT__DIRECTION), element));
				out.print(" ");
			}
			printCountingMap.put("direction", count - 1);
		}
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_DisplayStatement(org.qualitune.jouleunit.android.testrun.DisplayStatement element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.DISPLAY_STATEMENT__SWITCH_ON));
		printCountingMap.put("switchOn", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("DISPLAY");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (BooleanTerminal)
		count = printCountingMap.get("switchOn");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.DISPLAY_STATEMENT__SWITCH_ON));
			if (o != null) {
			}
			printCountingMap.put("switchOn", count - 1);
		}
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_EnterStatement(org.qualitune.jouleunit.android.testrun.EnterStatement element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		// print collected hidden tokens
		// DEFINITION PART BEGINS (CsString)
		out.print("ENTER");
		out.print(" ");
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_HomeButtonStatement(org.qualitune.jouleunit.android.testrun.HomeButtonStatement element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		// print collected hidden tokens
		// DEFINITION PART BEGINS (CsString)
		out.print("HOME");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("BUTTON");
		out.print(" ");
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_OpenSettingsStatement(org.qualitune.jouleunit.android.testrun.OpenSettingsStatement element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		// print collected hidden tokens
		// DEFINITION PART BEGINS (CsString)
		out.print("OPEN");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("SETTINGS");
		out.print(" ");
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_SendPortMessageStatement(org.qualitune.jouleunit.android.testrun.SendPortMessageStatement element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(3);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__IP));
		printCountingMap.put("ip", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__PORT));
		printCountingMap.put("port", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__MESSAGE));
		printCountingMap.put("message", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("SEND");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("MESSAGE");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderInQuotes)
		count = printCountingMap.get("ip");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__IP));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__IP), element));
			}
			printCountingMap.put("ip", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("port");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__PORT));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("NUMBER");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__PORT), element));
			}
			printCountingMap.put("port", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderInQuotes)
		count = printCountingMap.get("message");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__MESSAGE));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__MESSAGE), element));
				out.print(" ");
			}
			printCountingMap.put("message", count - 1);
		}
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_StartActivityStatement(org.qualitune.jouleunit.android.testrun.StartActivityStatement element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.START_ACTIVITY_STATEMENT__PACKAGE));
		printCountingMap.put("package", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.START_ACTIVITY_STATEMENT__CLASS));
		printCountingMap.put("class", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("START");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("ACTIVITY");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderInQuotes)
		count = printCountingMap.get("package");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.START_ACTIVITY_STATEMENT__PACKAGE));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.START_ACTIVITY_STATEMENT__PACKAGE), element));
			}
			printCountingMap.put("package", count - 1);
		}
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderInQuotes)
		count = printCountingMap.get("class");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.START_ACTIVITY_STATEMENT__CLASS));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.START_ACTIVITY_STATEMENT__CLASS), element));
				out.print(" ");
			}
			printCountingMap.put("class", count - 1);
		}
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_WaitStatement(org.qualitune.jouleunit.android.testrun.WaitStatement element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(1);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.WAIT_STATEMENT__SECONDS));
		printCountingMap.put("seconds", temp == null ? 0 : 1);
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("WAIT");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (CsString)
		out.print("FOR");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("seconds");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.WAIT_STATEMENT__SECONDS));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("NUMBER");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.WAIT_STATEMENT__SECONDS), element));
				out.print(" ");
			}
			printCountingMap.put("seconds", count - 1);
		}
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_UnlockStatement(org.qualitune.jouleunit.android.testrun.UnlockStatement element, String outertab, java.io.PrintWriter out) {
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		// print collected hidden tokens
		// DEFINITION PART BEGINS (CsString)
		out.print("UNLOCK");
		out.print(" ");
	}
	
	
	public void print_org_qualitune_jouleunit_android_testrun_TestStatement(org.qualitune.jouleunit.android.testrun.TestStatement element, String outertab, java.io.PrintWriter out) {
		String localtab = outertab;
		// The printCountingMap contains a mapping from feature names to the number of
		// remaining elements that still need to be printed. The map is initialized with
		// the number of elements stored in each structural feature. For lists this is the
		// list size. For non-multiple features it is either 1 (if the feature is set) or
		// 0 (if the feature is null).
		java.util.Map<String, Integer> printCountingMap = new java.util.LinkedHashMap<String, Integer>(2);
		Object temp;
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_STATEMENT__NAME));
		printCountingMap.put("name", temp == null ? 0 : 1);
		temp = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_STATEMENT__STATEMENTS));
		printCountingMap.put("statements", temp == null ? 0 : ((java.util.Collection<?>) temp).size());
		// print collected hidden tokens
		int count;
		// DEFINITION PART BEGINS (CsString)
		out.print("Test");
		// DEFINITION PART BEGINS (WhiteSpaces)
		out.print(" ");
		// DEFINITION PART BEGINS (PlaceholderUsingSpecifiedToken)
		count = printCountingMap.get("name");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_STATEMENT__NAME));
			if (o != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver resolver = tokenResolverFactory.createTokenResolver("TEXT");
				resolver.setOptions(getOptions());
				out.print(resolver.deResolve((Object) o, element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_STATEMENT__NAME), element));
				out.print(" ");
			}
			printCountingMap.put("name", count - 1);
		}
		// DEFINITION PART BEGINS (CompoundDefinition)
		print_org_qualitune_jouleunit_android_testrun_TestStatement_0(element, localtab, out, printCountingMap);
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestStatement_0(org.qualitune.jouleunit.android.testrun.TestStatement element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		int alt = -1;
		alt = 0;
		int matches = 		matchCount(printCountingMap, java.util.Arrays.asList(		"statements"		));
		int tempMatchCount;
		tempMatchCount = 		matchCount(printCountingMap, java.util.Arrays.asList(		"statements"		));
		if (tempMatchCount > matches) {
			alt = 1;
			matches = tempMatchCount;
		}
		switch(alt) {
			case 1:			{
				boolean iterate = true;
				java.io.StringWriter sWriter = null;
				java.io.PrintWriter out1 = null;
				java.util.Map<String, Integer> printCountingMap1 = null;
				// DEFINITION PART BEGINS (WhiteSpaces)
				out.print(" ");
				// DEFINITION PART BEGINS (CsString)
				out.print("{");
				out.print(" ");
				// DEFINITION PART BEGINS (CompoundDefinition)
				print_org_qualitune_jouleunit_android_testrun_TestStatement_0_0(element, localtab, out, printCountingMap);
				iterate = true;
				while (iterate) {
					sWriter = new java.io.StringWriter();
					out1 = new java.io.PrintWriter(sWriter);
					printCountingMap1 = new java.util.LinkedHashMap<String, Integer>(printCountingMap);
					print_org_qualitune_jouleunit_android_testrun_TestStatement_0_0(element, localtab, out1, printCountingMap1);
					if (printCountingMap.equals(printCountingMap1)) {
						iterate = false;
						out1.close();
					} else {
						out1.flush();
						out1.close();
						out.print(sWriter.toString());
						printCountingMap.putAll(printCountingMap1);
					}
				}
				// DEFINITION PART BEGINS (LineBreak)
				out.println();
				out.print(localtab);
				// DEFINITION PART BEGINS (CsString)
				out.print("}");
				out.print(" ");
			}
			break;
			default:			// DEFINITION PART BEGINS (LineBreak)
			localtab += "	";
			out.println();
			out.print(localtab);
			// DEFINITION PART BEGINS (Containment)
			count = printCountingMap.get("statements");
			if (count > 0) {
				Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_STATEMENT__STATEMENTS));
				java.util.List<?> list = (java.util.List<?>) o;
				int index = list.size() - count;
				if (index >= 0) {
					o = list.get(index);
				} else {
					o = null;
				}
				if (o != null) {
					doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
				}
				printCountingMap.put("statements", count - 1);
			}
			// DEFINITION PART BEGINS (LineBreak)
			out.println();
			out.print(localtab);
		}
	}
	
	public void print_org_qualitune_jouleunit_android_testrun_TestStatement_0_0(org.qualitune.jouleunit.android.testrun.TestStatement element, String outertab, java.io.PrintWriter out, java.util.Map<String, Integer> printCountingMap) {
		String localtab = outertab;
		int count;
		// DEFINITION PART BEGINS (LineBreak)
		localtab += "	";
		out.println();
		out.print(localtab);
		// DEFINITION PART BEGINS (Containment)
		count = printCountingMap.get("statements");
		if (count > 0) {
			Object o = element.eGet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_STATEMENT__STATEMENTS));
			java.util.List<?> list = (java.util.List<?>) o;
			int index = list.size() - count;
			if (index >= 0) {
				o = list.get(index);
			} else {
				o = null;
			}
			if (o != null) {
				doPrint((org.eclipse.emf.ecore.EObject) o, out, localtab);
			}
			printCountingMap.put("statements", count - 1);
		}
	}
	
	
}
