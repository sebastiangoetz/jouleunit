/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.ui;

public class TestrunOutlinePageCollapseAllAction extends org.qualitune.jouleunit.android.testrun.resource.testrun.ui.AbstractTestrunOutlinePageAction {
	
	public TestrunOutlinePageCollapseAllAction(org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageTreeViewer treeViewer) {
		super(treeViewer, "Collapse all", org.eclipse.jface.action.IAction.AS_PUSH_BUTTON);
		initialize("icons/collapse_all_icon.gif");
	}
	
	public void runInternal(boolean on) {
		if (on) {
			getTreeViewer().collapseAll();
		}
	}
	
	public boolean keepState() {
		return false;
	}
	
}
