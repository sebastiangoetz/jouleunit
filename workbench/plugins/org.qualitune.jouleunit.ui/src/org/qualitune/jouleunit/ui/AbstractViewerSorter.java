package org.qualitune.jouleunit.ui;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.internal.UIPlugin;

/**
 * Abstract implementation of a {@link ViewerSorter} to sort {@link TableViewer}
 * s by their columns.
 * 
 * Original version fom Dirk Buchhorn, jExam (http://www.jexam.org) project.
 * 
 * @author Dirk Buchhorn, Claas Wilke
 */
@SuppressWarnings("restriction")
public abstract class AbstractViewerSorter extends ViewerSorter {

	/** Whether or not to sort in ascending order. */
	protected boolean asc = true;

	/** The currently selected column for sorting identified by its header. */
	protected String currentSortColumn = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ViewerSorter#isSorterProperty(java.lang.Object,
	 * java.lang.String)
	 */
	public boolean isSorterProperty(Object element, String property) {
		return property.equals(currentSortColumn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ViewerComparator#sort(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object[])
	 */
	@Override
	public void sort(Viewer viewer, Object[] elements) {

		/* Set column images. */
		if (viewer instanceof TableViewer) {
			TableViewer tbViewer = (TableViewer) viewer;

			for (TableColumn col : tbViewer.getTable().getColumns()) {

				if (null != currentSortColumn
						&& currentSortColumn.equals(col.getText())) {
					if (!asc)
						col.setImage(UIPlugin.imageDescriptorFromPlugin(
								JouleUnitUiPlugIn.PLUGIN_ID, "/icons/up.gif")
								.createImage());
					else
						col.setImage(UIPlugin.imageDescriptorFromPlugin(
								JouleUnitUiPlugIn.PLUGIN_ID, "/icons/down.gif")
								.createImage());
				}

				else
					col.setImage(null);
			}
		}
		// no else.

		/* Real sorting. */
		if (currentSortColumn != null && !currentSortColumn.trim().equals(""))
			super.sort(viewer, elements);
		// no else.
	}

	/**
	 * Returns the currently selected column for sorting identified by its
	 * header.
	 * 
	 * @return The currently selected column for sorting identified by its
	 *         header.
	 */
	public String getSortColumn() {
		return currentSortColumn;
	}

	/**
	 * Indicates whether or not to sort in ascending order.
	 * 
	 * @return If <code>true</code> sorting in ascending order.
	 */
	public boolean isAscending() {
		return asc;
	}

	/**
	 * Sets the currently selected column for sorting identified by its header.
	 * 
	 * @param column
	 *            The column after which should be sorted.
	 */
	public void setSortColumn(String column) {

		if (column == null)
			column = "";
		// no else.

		if (currentSortColumn.equals(column)) {
			asc = !asc;
		}

		else
			asc = true;

		currentSortColumn = column;
	}
}
