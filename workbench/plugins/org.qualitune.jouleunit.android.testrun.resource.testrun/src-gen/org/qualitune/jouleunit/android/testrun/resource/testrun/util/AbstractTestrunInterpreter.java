/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.util;

/**
 * This class provides basic infrastructure to interpret models. To implement
 * concrete interpreters, subclass this abstract interpreter and override the
 * interprete_* methods. The interpretation can be customized by binding the two
 * type parameters (ResultType, ContextType). The former is returned by all
 * interprete_* methods, while the latter is passed from method to method while
 * traversing the model. The concrete traversal strategy can also be exchanged.
 * One can use a static traversal strategy by pushing all objects to interpret on
 * the interpretation stack (using addObjectToInterprete()) before calling
 * interprete(). Alternatively, the traversal strategy can be dynamic by pushing
 * objects on the interpretation stack during interpretation.
 */
public class AbstractTestrunInterpreter<ResultType, ContextType> {
	
	private java.util.Stack<org.eclipse.emf.ecore.EObject> interpretationStack = new java.util.Stack<org.eclipse.emf.ecore.EObject>();
	private java.util.List<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunInterpreterListener> listeners = new java.util.ArrayList<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunInterpreterListener>();
	private org.eclipse.emf.ecore.EObject nextObjectToInterprete;
	private Object currentContext;
	
	public ResultType interprete(ContextType context) {
		ResultType result = null;
		org.eclipse.emf.ecore.EObject next = null;
		currentContext = context;
		while (!interpretationStack.empty()) {
			try {
				next = interpretationStack.pop();
			} catch (java.util.EmptyStackException ese) {
				// this can happen when the interpreter was terminated between the call to empty()
				// and pop()
				break;
			}
			nextObjectToInterprete = next;
			notifyListeners(next);
			result = interprete(next, context);
			if (!continueInterpretation(context, result)) {
				break;
			}
		}
		currentContext = null;
		return result;
	}
	
	/**
	 * Override this method to stop the overall interpretation depending on the result
	 * of the interpretation of a single model elements.
	 */
	public boolean continueInterpretation(ContextType context, ResultType result) {
		return true;
	}
	
	public ResultType interprete(org.eclipse.emf.ecore.EObject object, ContextType context) {
		ResultType result = null;
		if (object instanceof org.qualitune.jouleunit.android.testrun.TestRun) {
			result = interprete_org_qualitune_jouleunit_android_testrun_TestRun((org.qualitune.jouleunit.android.testrun.TestRun) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.TestSuite) {
			result = interprete_org_qualitune_jouleunit_android_testrun_TestSuite((org.qualitune.jouleunit.android.testrun.TestSuite) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.TestCase) {
			result = interprete_org_qualitune_jouleunit_android_testrun_TestCase((org.qualitune.jouleunit.android.testrun.TestCase) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.JunitTestCase) {
			result = interprete_org_qualitune_jouleunit_android_testrun_JunitTestCase((org.qualitune.jouleunit.android.testrun.JunitTestCase) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.TestExecutable) {
			result = interprete_org_qualitune_jouleunit_android_testrun_TestExecutable((org.qualitune.jouleunit.android.testrun.TestExecutable) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.Block) {
			result = interprete_org_qualitune_jouleunit_android_testrun_Block((org.qualitune.jouleunit.android.testrun.Block) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.TestBehavior) {
			result = interprete_org_qualitune_jouleunit_android_testrun_TestBehavior((org.qualitune.jouleunit.android.testrun.TestBehavior) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_ClickOnScreenStatement((org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.CursorStatement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_CursorStatement((org.qualitune.jouleunit.android.testrun.CursorStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.EnterStatement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_EnterStatement((org.qualitune.jouleunit.android.testrun.EnterStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.DisplayStatement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_DisplayStatement((org.qualitune.jouleunit.android.testrun.DisplayStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.HomeButtonStatement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_HomeButtonStatement((org.qualitune.jouleunit.android.testrun.HomeButtonStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.OpenSettingsStatement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_OpenSettingsStatement((org.qualitune.jouleunit.android.testrun.OpenSettingsStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.SendPortMessageStatement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_SendPortMessageStatement((org.qualitune.jouleunit.android.testrun.SendPortMessageStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.StartActivityStatement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_StartActivityStatement((org.qualitune.jouleunit.android.testrun.StartActivityStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.TestStatement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_TestStatement((org.qualitune.jouleunit.android.testrun.TestStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.UnlockStatement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_UnlockStatement((org.qualitune.jouleunit.android.testrun.UnlockStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.WaitStatement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_WaitStatement((org.qualitune.jouleunit.android.testrun.WaitStatement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.Statement) {
			result = interprete_org_qualitune_jouleunit_android_testrun_Statement((org.qualitune.jouleunit.android.testrun.Statement) object, context);
		}
		if (result != null) {
			return result;
		}
		if (object instanceof org.qualitune.jouleunit.android.testrun.ApkFile) {
			result = interprete_org_qualitune_jouleunit_android_testrun_ApkFile((org.qualitune.jouleunit.android.testrun.ApkFile) object, context);
		}
		if (result != null) {
			return result;
		}
		return result;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_TestRun(org.qualitune.jouleunit.android.testrun.TestRun testRun, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_TestExecutable(org.qualitune.jouleunit.android.testrun.TestExecutable testExecutable, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_TestSuite(org.qualitune.jouleunit.android.testrun.TestSuite testSuite, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_TestCase(org.qualitune.jouleunit.android.testrun.TestCase testCase, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_JunitTestCase(org.qualitune.jouleunit.android.testrun.JunitTestCase junitTestCase, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_TestBehavior(org.qualitune.jouleunit.android.testrun.TestBehavior testBehavior, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_Block(org.qualitune.jouleunit.android.testrun.Block block, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_Statement(org.qualitune.jouleunit.android.testrun.Statement statement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_ClickOnScreenStatement(org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement clickOnScreenStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_CursorStatement(org.qualitune.jouleunit.android.testrun.CursorStatement cursorStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_EnterStatement(org.qualitune.jouleunit.android.testrun.EnterStatement enterStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_DisplayStatement(org.qualitune.jouleunit.android.testrun.DisplayStatement displayStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_HomeButtonStatement(org.qualitune.jouleunit.android.testrun.HomeButtonStatement homeButtonStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_OpenSettingsStatement(org.qualitune.jouleunit.android.testrun.OpenSettingsStatement openSettingsStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_SendPortMessageStatement(org.qualitune.jouleunit.android.testrun.SendPortMessageStatement sendPortMessageStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_StartActivityStatement(org.qualitune.jouleunit.android.testrun.StartActivityStatement startActivityStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_TestStatement(org.qualitune.jouleunit.android.testrun.TestStatement testStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_UnlockStatement(org.qualitune.jouleunit.android.testrun.UnlockStatement unlockStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_WaitStatement(org.qualitune.jouleunit.android.testrun.WaitStatement waitStatement, ContextType context) {
		return null;
	}
	
	public ResultType interprete_org_qualitune_jouleunit_android_testrun_ApkFile(org.qualitune.jouleunit.android.testrun.ApkFile apkFile, ContextType context) {
		return null;
	}
	
	private void notifyListeners(org.eclipse.emf.ecore.EObject element) {
		for (org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunInterpreterListener listener : listeners) {
			listener.handleInterpreteObject(element);
		}
	}
	
	/**
	 * Adds the given object to the interpretation stack. Attention: Objects that are
	 * added first, are interpret last.
	 */
	public void addObjectToInterprete(org.eclipse.emf.ecore.EObject object) {
		interpretationStack.push(object);
	}
	
	/**
	 * Adds the given collection of objects to the interpretation stack. Attention:
	 * Collections that are added first, are interpret last.
	 */
	public void addObjectsToInterprete(java.util.Collection<? extends org.eclipse.emf.ecore.EObject> objects) {
		for (org.eclipse.emf.ecore.EObject object : objects) {
			addObjectToInterprete(object);
		}
	}
	
	/**
	 * Adds the given collection of objects in reverse order to the interpretation
	 * stack.
	 */
	public void addObjectsToInterpreteInReverseOrder(java.util.Collection<? extends org.eclipse.emf.ecore.EObject> objects) {
		java.util.List<org.eclipse.emf.ecore.EObject> reverse = new java.util.ArrayList<org.eclipse.emf.ecore.EObject>(objects.size());
		reverse.addAll(objects);
		java.util.Collections.reverse(reverse);
		addObjectsToInterprete(reverse);
	}
	
	/**
	 * Adds the given object and all its children to the interpretation stack such
	 * that they are interpret in top down order.
	 */
	public void addObjectTreeToInterpreteTopDown(org.eclipse.emf.ecore.EObject root) {
		java.util.List<org.eclipse.emf.ecore.EObject> objects = new java.util.ArrayList<org.eclipse.emf.ecore.EObject>();
		objects.add(root);
		java.util.Iterator<org.eclipse.emf.ecore.EObject> it = root.eAllContents();
		while (it.hasNext()) {
			org.eclipse.emf.ecore.EObject eObject = (org.eclipse.emf.ecore.EObject) it.next();
			objects.add(eObject);
		}
		addObjectsToInterpreteInReverseOrder(objects);
	}
	
	public void addListener(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunInterpreterListener newListener) {
		listeners.add(newListener);
	}
	
	public boolean removeListener(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunInterpreterListener listener) {
		return listeners.remove(listener);
	}
	
	public org.eclipse.emf.ecore.EObject getNextObjectToInterprete() {
		return nextObjectToInterprete;
	}
	
	public java.util.Stack<org.eclipse.emf.ecore.EObject> getInterpretationStack() {
		return interpretationStack;
	}
	
	public void terminate() {
		interpretationStack.clear();
	}
	
	public Object getCurrentContext() {
		return currentContext;
	}
	
}
