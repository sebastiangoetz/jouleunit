/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.ui;

public class TestrunOutlinePageAutoExpandAction extends org.qualitune.jouleunit.android.testrun.resource.testrun.ui.AbstractTestrunOutlinePageAction {
	
	public TestrunOutlinePageAutoExpandAction(org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageTreeViewer treeViewer) {
		super(treeViewer, "Auto expand", org.eclipse.jface.action.IAction.AS_CHECK_BOX);
		initialize("icons/auto_expand_icon.gif");
	}
	
	public void runInternal(boolean on) {
		getTreeViewer().setAutoExpand(on);
		getTreeViewer().refresh();
	}
	
}
