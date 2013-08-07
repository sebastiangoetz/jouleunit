/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.ui;

public class TestrunOutlinePageLexicalSortingAction extends org.qualitune.jouleunit.android.testrun.resource.testrun.ui.AbstractTestrunOutlinePageAction {
	
	public TestrunOutlinePageLexicalSortingAction(org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageTreeViewer treeViewer) {
		super(treeViewer, "Sort alphabetically", org.eclipse.jface.action.IAction.AS_CHECK_BOX);
		initialize("icons/sort_lexically_icon.gif");
	}
	
	public void runInternal(boolean on) {
		getTreeViewerComparator().setSortLexically(on);
		getTreeViewer().refresh();
	}
	
}
