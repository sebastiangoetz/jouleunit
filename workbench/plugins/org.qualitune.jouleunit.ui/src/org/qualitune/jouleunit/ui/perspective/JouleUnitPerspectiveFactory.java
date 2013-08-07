package org.qualitune.jouleunit.ui.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.qualitune.jouleunit.ui.EnergyView;
import org.qualitune.jouleunit.ui.TestCaseView;

/**
 * Factory to create the JouleUnit perspective.
 * 
 * @author Claas Wilke
 */
public class JouleUnitPerspectiveFactory implements IPerspectiveFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui
	 * .IPageLayout)
	 */
	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}

	/**
	 * <p>
	 * Helper method defining actions of the DresdenOCL perspective.
	 * </p>
	 * 
	 * @param layout
	 *            The given {@link IPageLayout}.
	 */
	public void defineActions(IPageLayout layout) {

		/* Can be used to add new wizard and show view short cuts. */
		layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(EnergyView.ID);
		layout.addShowViewShortcut(TestCaseView.ID);
	}

	/**
	 * <p>
	 * Helper method to create the layout of the DresdenOCL perspective.
	 * </p>
	 * 
	 * @param layout
	 *            The given {@link IPageLayout}.
	 */
	public void defineLayout(IPageLayout layout) {

		String editorArea = layout.getEditorArea();

		/* Place project explorer and navigator left of editor area. */
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT,
				0.2f, editorArea);
		left.addView(IPageLayout.ID_PROJECT_EXPLORER);

		IFolderLayout jUnit = layout.createFolder("jUnit", IPageLayout.BOTTOM,
				0.5f, "left");
		jUnit.addView("org.eclipse.jdt.junit.ResultView");

		/* Place the console at the bottom. */
		IFolderLayout console = layout.createFolder("console",
				IPageLayout.BOTTOM, 0.75f, editorArea);
		console.addView("org.eclipse.ui.console.ConsoleView");
		console.addView(IPageLayout.ID_PROGRESS_VIEW);

		/* Place test case view above. */
		IFolderLayout testCases = layout.createFolder("testCaseView",
				IPageLayout.BOTTOM, 0.67f, editorArea);
		testCases.addView(TestCaseView.ID);

		/* Place energy view above. */
		IFolderLayout bottom = layout.createFolder("energyView",
				IPageLayout.BOTTOM, 0f, editorArea);
		bottom.addView(EnergyView.ID);
	}
}
