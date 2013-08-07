package org.qualitune.jouleunit.ui;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public class FilteredElementTreeSelectionDialog extends
		ElementTreeSelectionDialog {

	/**
	 * Constructs an instance of {@link FilteredElementTreeSelectionDialog}.
	 * 
	 * @param parent
	 *            The parent shell for the dialog
	 * @param labelProvider
	 *            the label provider to render the entries
	 * @param contentProvider
	 *            the content provider to evaluate the tree structure
	 * @param filter
	 *            A {@link ViewerFilter} used for filtering.
	 */
	public FilteredElementTreeSelectionDialog(Shell parent,
			ILabelProvider labelProvider, ITreeContentProvider contentProvider, ViewerFilter filter) {
		super(parent, labelProvider, contentProvider);
		addFilter(filter);
	}

	public int open() {
		create();
		return super.open();
	}

}
