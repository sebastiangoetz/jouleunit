/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package org.qualitune.jouleunit.android.testrun.resource.testrun.ui;

public class TestrunOutlinePageLinkWithEditorAction extends org.qualitune.jouleunit.android.testrun.resource.testrun.ui.AbstractTestrunOutlinePageAction {
	
	public TestrunOutlinePageLinkWithEditorAction(org.qualitune.jouleunit.android.testrun.resource.testrun.ui.TestrunOutlinePageTreeViewer treeViewer) {
		super(treeViewer, "Link with Editor", org.eclipse.jface.action.IAction.AS_CHECK_BOX);
		initialize("icons/link_with_editor_icon.gif");
	}
	
	public void runInternal(boolean on) {
		getTreeViewer().setLinkWithEditor(on);
	}
	
}
