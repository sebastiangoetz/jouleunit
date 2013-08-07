/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.ui;

public class TestrunOutlinePageTypeSortingAction extends org.qualitune.jouleunit.android.testrun.resource.testrun.ui.AbstractTestrunOutlinePageAction {
	
	public TestrunOutlinePageTypeSortingAction(org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageTreeViewer treeViewer) {
		super(treeViewer, "Group types", org.eclipse.jface.action.IAction.AS_CHECK_BOX);
		initialize("icons/group_types_icon.gif");
	}
	
	public void runInternal(boolean on) {
		getTreeViewerComparator().setGroupTypes(on);
		getTreeViewer().refresh();
	}
	
}
