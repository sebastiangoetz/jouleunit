/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.ui;

public class TestrunOutlinePageActionProvider {
	
	public java.util.List<org.eclipse.jface.action.IAction> getActions(org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageTreeViewer treeViewer) {
		// To add custom actions to the outline view, set the
		// 'overrideOutlinePageActionProvider' option to <code>false</code> and modify
		// this method.
		java.util.List<org.eclipse.jface.action.IAction> defaultActions = new java.util.ArrayList<org.eclipse.jface.action.IAction>();
		defaultActions.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageLinkWithEditorAction(treeViewer));
		defaultActions.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageCollapseAllAction(treeViewer));
		defaultActions.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageExpandAllAction(treeViewer));
		defaultActions.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageAutoExpandAction(treeViewer));
		defaultActions.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageLexicalSortingAction(treeViewer));
		defaultActions.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageTypeSortingAction(treeViewer));
		return defaultActions;
	}
	
}
