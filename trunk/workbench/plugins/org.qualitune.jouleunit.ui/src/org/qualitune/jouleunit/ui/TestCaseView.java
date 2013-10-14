package org.qualitune.jouleunit.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.internal.UIPlugin;
import org.eclipse.ui.part.ViewPart;
import org.qualitune.jouleunit.SimpleEnergyProfile;
import org.qualitune.jouleunit.coordinator.AvgTestCaseResult;
import org.qualitune.jouleunit.coordinator.TestCaseProfile;
import org.qualitune.jouleunit.coordinator.TestSuiteProfile;
import org.qualitune.jouleunit.data.DbTestCaseProfile;
import org.qualitune.jouleunit.data.IProfilingResultReceiver;
import org.qualitune.jouleunit.data.PerformActionListener;
import org.qualitune.jouleunit.data.PerformActionPropagator;
import org.qualitune.jouleunit.data.ProfilingResultExportJob;
import org.qualitune.jouleunit.data.ProfilingResultImportFromDBJob;
import org.qualitune.jouleunit.data.ResultPropagator;
import org.qualitune.jouleunit.data.UpdateTestCaseToDBJob;
import org.qualitune.jouleunit.persist.RestoredPowerRate;

@SuppressWarnings("restriction")
public class TestCaseView extends ViewPart implements IProfilingResultReceiver,
		PerformActionListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.qualitune.jouleunit.ui.views.testcaseview";

	/** {@link Image} used to display a test case as failed. */
	private static Image FAILURE_IMAGE = UIPlugin.imageDescriptorFromPlugin(
			JouleUnitUiPlugIn.PLUGIN_ID, "/icons/failure.gif").createImage();

	/** {@link Image} used to display a test case as run successfully. */
	private static Image OK_IMAGE = UIPlugin.imageDescriptorFromPlugin(
			JouleUnitUiPlugIn.PLUGIN_ID, "/icons/ok.gif").createImage();

	/** {@link TableViewer} to display test results. */
	protected TableViewer viewer;

	/** {@link TableViewer} to display averaged test results. */
	protected TableViewer avgViewer;

	/** {@link TableViewer} to display old test results. */
	private TableViewer oldTestResultViewer;

	/** {@link Action} to load a {@link TestSuiteProfile} from the QMark DB. */
	private Action loadTestResultAction;

	/** {@link Action} to save the current {@link TestSuiteProfile}. */
	protected Action saveAction;

	/**
	 * {@link Action} to save the current {@link TestSuiteProfile} as an
	 * {@link EnergyModel}.
	 */
	protected Action exportEnergyModelAction;

	/** The current {@link TestSuiteProfile} to be displayed. */
	private TestSuiteProfile testSuiteProfile;

	public static final int UPDATE_OLD_HISTORY_TABLE = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	public void createPartControl(Composite parent) {
		// GridLayout layout = new GridLayout(1, false);
		FillLayout layout = new FillLayout();
		parent.setLayout(layout);

		createViewer(parent);

		makeActions();
		contributeToActionBars();

		ResultPropagator.registerListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.data.IProfilingResultReceiver#updateToNewResult
	 * (org.qualitune.jouleunit.data.TestSuiteProfile)
	 */
	public void updateToNewResult(TestSuiteProfile profile) {
		this.updateContent(profile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		ResultPropagator.unregisterListener(this);
		super.dispose();
	}

	public void updateContent(final TestSuiteProfile profile) {
		testSuiteProfile = profile;

		/* Run on the UI thread ... */
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (null != profile) {

					viewer.setInput(new ArrayList<TestCaseProfile>());
					avgViewer.setInput(new ArrayList<AvgTestCaseResult>());

					UpdateTestCaseViewJob job = new UpdateTestCaseViewJob(
							profile, TestCaseView.this);
					job.schedule();
				} else {
					viewer.setInput(null);
					avgViewer.setInput(null);
					updateOldTestResultViewer();
					saveAction.setEnabled(false);
					exportEnergyModelAction.setEnabled(false);
				}
			}
		});
	}

	protected void setClipboardContent(String s) {
		StringSelection stringSelection = new StringSelection(s);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	/** Creates action bars. */
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	/**
	 * Helper method creating columns for the table.
	 * 
	 * @param parent
	 *            The parent {@link Composite}.
	 * @param viewer
	 *            The {@link TableViewer}.
	 */
	private void createAvgColumns(final Composite parent,
			final TableViewer viewer) {

		String[] titles = { "Test Case", "# runs", "# failures",
				"Avg. Duration [ms]", "Std. dev. [ms]", "Avg. Power Rate [mW]",
				"Std. dev. [mW]", "Avg. Power Consumption [mJ]",
				"Std. dev. [mJ]" };
		int[] bounds = { 300, 50, 50, 150, 150, 150, 150, 150, 150 };

		/* First column is the id. */
		TableViewerColumn col = createTableViewerColumn(viewer, titles[0],
				bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				return ((AvgTestCaseResult) element).getID();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang
			 * .Object)
			 */
			@Override
			public Image getImage(Object element) {
				if ((((AvgTestCaseResult) element).getNumberOfRuns() == 0)
						&& (((AvgTestCaseResult) element)
								.getNumberOfFailedRuns() > 0))
					return FAILURE_IMAGE;
				else
					return OK_IMAGE;
			}
		});

		col = createTableViewerColumn(viewer, titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				return ((AvgTestCaseResult) element).getNumberOfRuns() + "";
			}
		});

		col = createTableViewerColumn(viewer, titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				return ((AvgTestCaseResult) element).getNumberOfFailedRuns()
						+ "";
			}
		});

		col = createTableViewerColumn(viewer, titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				try {
					return String.format("%.2f",
							((AvgTestCaseResult) element).getAvgDuration());
				} catch (IllegalArgumentException e) {
					return e.getMessage();
				}
			}
		});

		col = createTableViewerColumn(viewer, titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				try {
					return String.format("%.2f",
							((AvgTestCaseResult) element).getStdDevDuration());
				} catch (IllegalArgumentException e) {
					return e.getMessage();
				}
			}
		});

		col = createTableViewerColumn(viewer, titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				try {
					return String.format("%.2f",
							((AvgTestCaseResult) element).getAvgPowerRate());
				} catch (IllegalArgumentException e) {
					return e.getMessage();
				}
			}
		});

		col = createTableViewerColumn(viewer, titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				try {
					return String.format("%.2f",
							((AvgTestCaseResult) element).getStdDevPowerRate());
				} catch (IllegalArgumentException e) {
					return e.getMessage();
				}
			}
		});

		col = createTableViewerColumn(viewer, titles[7], bounds[7], 7);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				try {
					return String.format("%.2f", ((AvgTestCaseResult) element)
							.getAvgPowerConsumption());
				} catch (IllegalArgumentException e) {
					return e.getMessage();
				}
			}
		});

		col = createTableViewerColumn(viewer, titles[8], bounds[8], 8);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				try {
					return String.format("%.2f", ((AvgTestCaseResult) element)
							.getStdDevPowerConsumption());
				} catch (IllegalArgumentException e) {
					return e.getMessage();
				}
			}
		});
	}

	/**
	 * Helper method creating columns for the table.
	 * 
	 * @param parent
	 *            The parent {@link Composite}.
	 * @param viewer
	 *            The {@link TableViewer}.
	 */
	private void createColumns(final Composite parent, final TableViewer viewer) {

		String[] titles = { "Test Case", "Tag", "Duration [ms]",
				"Avg. Power Rate [mW]", "Energy Consumption [mJ]", "Outlier?",
				"Start [ms]", "Stop [ms]" };
		int[] bounds = { 300, 100, 100, 100, 100, 200, 200, 100 };

		/* First column is the id. */
		TableViewerColumn col = createTableViewerColumn(viewer, titles[0],
				bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				TestCaseProfile profile = (TestCaseProfile) element;
				return profile.getId();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang
			 * .Object)
			 */
			@Override
			public Image getImage(Object element) {
				if (((TestCaseProfile) element).isFailed()) {
					return FAILURE_IMAGE;
				} else {
					return OK_IMAGE;
				}
			}
		});

		/* Second column is the tag. */
		col = createTableViewerColumn(viewer, titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				TestCaseProfile profile = (TestCaseProfile) element;
				try {
					return ((null != profile.getTag()) ? profile.getTag() : "");
				} catch (IllegalArgumentException e) {
					JouleUnitUiPlugIn.log(
							e,
							"Error during test result report: "
									+ e.getMessage());
					return e.getMessage();
				}
			}
		});

		/* Third column is the duration. */
		col = createTableViewerColumn(viewer, titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				TestCaseProfile profile = (TestCaseProfile) element;
				try {
					return String.format("%.2f",
							new Double(profile.getDuration()));
				} catch (IllegalArgumentException e) {
					return e.getMessage();
				}
			}
		});

		/* Fourth column is the Power Rate. */
		col = createTableViewerColumn(viewer, titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				TestCaseProfile profile = (TestCaseProfile) element;
				try {
					return String.format("%.2f",
							new Double(profile.getPowerRate()));
				} catch (IllegalArgumentException e) {
					return e.getMessage();
				}
			}
		});

		/* Fifth column is energy consumption. */
		col = createTableViewerColumn(viewer, titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			@Override
			public String getText(Object element) {
				TestCaseProfile profile = (TestCaseProfile) element;
				try {
					return String.format("%.2f",
							new Double(profile.getConsumedEnergy()));
				} catch (IllegalArgumentException e) {
					return e.getMessage();
				}
			}
		});

		/* Sixth column enlists outliers. */
		col = createTableViewerColumn(viewer, titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			@Override
			public String getText(Object element) {
				TestCaseProfile profile = (TestCaseProfile) element;
				try {
					return profile.getOutlierInfo();
				} catch (IllegalArgumentException e) {
					return e.getMessage();
				}
			}
		});

		/* Seventh column is the start time. */
		col = createTableViewerColumn(viewer, titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				TestCaseProfile profile = (TestCaseProfile) element;
				try {
					return new Long(testSuiteProfile.adaptPutTimeStamp(profile
							.getStartTime())).toString();
				} catch (IllegalArgumentException e) {
					JouleUnitUiPlugIn.log(
							e,
							"Error during test result report: "
									+ e.getMessage());
					return e.getMessage();
				}
			}
		});

		/* Eighth column is the stop time. */
		col = createTableViewerColumn(viewer, titles[7], bounds[7], 7);
		col.setLabelProvider(new ColumnLabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang
			 * .Object)
			 */
			public String getText(Object element) {
				TestCaseProfile profile = (TestCaseProfile) element;
				try {
					return new Long(testSuiteProfile.adaptPutTimeStamp(profile
							.getEndTime())).toString();
				} catch (IllegalArgumentException e) {
					JouleUnitUiPlugIn.log(
							e,
							"Error during test result report: "
									+ e.getMessage());
					return e.getMessage();
				}
			}
		});
	}

	/**
	 * create a menu for the history-table to give the user the possibility, to
	 * compare his previous results with current ones
	 * 
	 * @param oldTestResultViewer
	 * @return
	 */

	private MenuManager createMenuForOldTestResultViewer(
			final TableViewer oldTestResultViewer) {
		MenuManager manager = new MenuManager();

		manager.add(new Action(
				"Show selected energy-model (using locally saved csv-file)",
				ImageDescriptor.createFromImage(UIPlugin
						.imageDescriptorFromPlugin(JouleUnitUiPlugIn.PLUGIN_ID,
								"/icons/showresult.gif").createImage())) {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) oldTestResultViewer
						.getSelection();
				HistoryTestResultObject htro = ((HistoryTestResultObject) selection
						.getFirstElement());
				if (htro != null) {
					if (htro.isFileSavedLocally()) {
						showEnergyModel(htro);
						PerformActionPropagator.getInstance().propagateEvent(
								EnergyView.ID, EnergyView.SHOW_ENERGY_MODEL);
					} else
						MessageDialog
								.openInformation(null, "Information",
										"A test-case without a local stored file can´t be displayed.");
				}
			}
		});

		manager.add(new Action("Show history-runs as bar chart",
				ImageDescriptor.createFromImage(UIPlugin
						.imageDescriptorFromPlugin(JouleUnitUiPlugIn.PLUGIN_ID,
								"/icons/barchart.png").createImage())) {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) oldTestResultViewer
						.getSelection();
				HistoryTestResultObject htro = ((HistoryTestResultObject) selection
						.getFirstElement());
				PerformActionPropagator.getInstance().propagateEvent(
						EnergyView.ID, EnergyView.UPDATE_BAR_CHART, htro);
			}
		});

		// manager.add(new Action("Compare selected Test-ID with latest result",
		// ImageDescriptor.createFromImage(UIPlugin.imageDescriptorFromPlugin(JouleUnitUiPlugIn.PLUGIN_ID,
		// "/icons/compare.gif").createImage())) {
		// @Override
		// public void run() {
		// if(testSuiteProfile != null &&
		// testSuiteProfile.getTestCaseProfiles().size() > 0) {
		// IStructuredSelection selection = (IStructuredSelection)
		// oldTestResultViewer.getSelection();
		// if(selection != null) {
		// compareToCurrentTest(((HistoryTestResultObject)
		// selection.getFirstElement()));
		// }
		// } else {
		// MessageDialog.openError(null, "Error",
		// "There is no latest result to compare with. Please run at least one Test.");
		// }
		//
		// }
		// });

		manager.add(new Action("Refresh this view", ImageDescriptor
				.createFromImage(UIPlugin.imageDescriptorFromPlugin(
						JouleUnitUiPlugIn.PLUGIN_ID, "/icons/refresh.gif")
						.createImage())) {
			@Override
			public void run() {
				updateOldTestResultViewer();
			}
		});

		manager.add(new Action("Copy local file-location to clipboard",
				ImageDescriptor.createFromImage(UIPlugin
						.imageDescriptorFromPlugin(JouleUnitUiPlugIn.PLUGIN_ID,
								"/icons/clipboard.png").createImage())) {
			@Override
			public void run() {
				IStructuredSelection selection = (IStructuredSelection) oldTestResultViewer
						.getSelection();
				setClipboardContent(((HistoryTestResultObject) selection
						.getFirstElement()).getLocation());
			}
		});

		return manager;
	}

	/**
	 * Helper method creating columns for the table.
	 * 
	 * @param parent
	 *            The parent {@link Composite}.
	 * @param viewer
	 *            The {@link TableViewer}.
	 */
	private void createOldTestResultColumns(final Composite parent,
			final TableViewer viewer) {

		String[] titles = { "No.", "Test-Case-ID", "Created on", "Failed?",
				"Duration [ms]", "APR", "Avg. Power Rate [mW]", "AEC",
				"Energy Consumption [mJ]", "Start [ms]", "Stop [ms]",
				"File available?", "File-location", "Modified on" };
		int[] bounds = { 50, 175, 175, 50, 100, 40, 150, 40, 150, 100, 100,
				100, 75, 175 };
		int i = 0;

		/* First column is the id. */
		TableViewerColumn col = createTableViewerColumn(viewer, titles[i],
				bounds[i], i);

		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				return ((HistoryTestResultObject) element).getNumber() + "";
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				return ((HistoryTestResultObject) element).getId();
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				return ((HistoryTestResultObject) element).getCreatedOn()
						.toString();
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				return ((HistoryTestResultObject) element).isFailed() ? "failed"
						: "";
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				return ((HistoryTestResultObject) element).getDuration() + "";
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new CenterImageLabelProvider() {

			public Image getImage(Object element) {
				return ((HistoryTestResultObject) element)
						.interpretValueAsImage(HistoryTestResultObject.AVG_POWER_RATE);
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				double diff = ((HistoryTestResultObject) element)
						.getAvgPowerRateDiff();
				return ((HistoryTestResultObject) element).getAvgPowerRate()
						+ " (Compared: "
						+ (diff == 0.00 ? ("+- " + diff) : ((diff > 0) ? "+ "
								+ diff : diff)) + ")";
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new CenterImageLabelProvider() {

			public Image getImage(Object element) {
				return ((HistoryTestResultObject) element)
						.interpretValueAsImage(HistoryTestResultObject.AVG_ENERGY_CONSUMPTION);
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				double diff = ((HistoryTestResultObject) element)
						.getAvgEnergyConsumptionDiff();

				return ((HistoryTestResultObject) element).getConsumption()
						+ " (Compared: "
						+ (diff == 0.0 ? ("+- " + diff) : ((diff > 0) ? "+ "
								+ diff : diff)) + ")";
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				return ((HistoryTestResultObject) element).getStartTime() + "";
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				return ((HistoryTestResultObject) element).getEndTime() + "";
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				return ((HistoryTestResultObject) element).isFileSavedLocally()
						+ "";
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				return ((HistoryTestResultObject) element).getLocation();
			}
		});
		i++;

		col = createTableViewerColumn(viewer, titles[i], bounds[i], i);
		col.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				return ((HistoryTestResultObject) element).getModifiedOn()
						.toString();
			}
		});
		i++;
	}

	/**
	 * Helper method to create the {@link TableViewerColumn}.
	 * 
	 * @param the
	 *            {@link TableViewer} for which the column shall be created.
	 * @param title
	 *            The title.
	 * @param bound
	 *            The bound.
	 * @param colNumber
	 *            The number of columns.
	 * @return The created {@link TableViewerColumn}.
	 */
	private TableViewerColumn createTableViewerColumn(final TableViewer viewer,
			String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);

		/* Selection listener for sorting. */
		column.addSelectionListener(new SelectionListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				TestCaseTableSorter sorter = (TestCaseTableSorter) viewer
						.getSorter();
				sorter.setSortColumn(column.getText());
				viewer.refresh();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected
			 * (org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				/* Do nothing. */
			}
		});

		return viewerColumn;
	}

	private TestSuiteProfile createTestSuiteProfile(
			HistoryTestResultObject historyTestResultObject) {
		TestSuiteProfile tsp = new TestSuiteProfile();

		TestCaseProfile tcp = new TestCaseProfile();
		tcp.setId(historyTestResultObject.getId());
		tcp.setFailed(historyTestResultObject.isFailed());

		SimpleEnergyProfile energyProfile = new SimpleEnergyProfile();

		long startTime = -1;
		long endTime = -1;

		try {
			FileReader fr = new FileReader(
					historyTestResultObject.getLocation());
			BufferedReader br = new BufferedReader(fr);
			String zeile = br.readLine();

			while (!zeile.startsWith("time stamp")) {
				zeile = br.readLine();
			}

			while ((zeile = br.readLine()) != null && zeile.contains(";")) {

				RestoredPowerRate powerRate = new RestoredPowerRate();
				String s = zeile.split(";")[0];
				s = s.substring(0, s.indexOf("E")).replace(".", "");

				// add missing decimal places to fit into graph
				while (s.length() < 19) {
					s = s + "0";
				}

				powerRate.setTimeStamp(Long.parseLong(s));
				if (startTime == -1) {
					startTime = powerRate.getTimeStamp();
				}
				endTime = powerRate.getTimeStamp();
				powerRate.setPowerRate(Double.parseDouble(zeile.split(";")[1]));
				energyProfile.addPowerRateValue(powerRate);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// FIXME i dont know why, if i use the htro-times start and end, why the
		// graph isnt displayed right;
		// actually it is the originalvalue given by testProfileCase
		// tcp.setStartTime(historyTestResultObject.getStartTime());
		// tcp.setEndTime(historyTestResultObject.getEndTime());
		tcp.setStartTime(startTime);
		tcp.setEndTime(endTime);

		tsp.setEnergyProfile(energyProfile);
		tsp.addTestCase(tcp);

		return tsp;
	}

	/**
	 * Helper method to create this {@link TestCaseView}.
	 * 
	 * @param parent
	 *            The parent {@link Composite}.
	 */
	private void createViewer(Composite parent) {

		final TabFolder tabFolder = new TabFolder(parent, SWT.NONE);

		/* Individual test case results. */
		TabItem testCaseItem = new TabItem(tabFolder, SWT.NULL);
		testCaseItem.setText("Individual Test Case Results");

		viewer = new TableViewer(tabFolder, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());

		/* Add context menu. */
		MenuManager popupMenu = new MenuManager();
		popupMenu.add(new Action("Mark selected entries as failed") {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				markColumnsAsFailed();
			}
		});
		Menu menu = popupMenu.createContextMenu(table);
		table.setMenu(menu);

		// Make the selection available to other views
		getSite().setSelectionProvider(viewer);

		viewer.addDoubleClickListener(new IDoubleClickListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org
			 * .eclipse.jface.viewers.DoubleClickEvent)
			 */
			@Override
			public void doubleClick(DoubleClickEvent event) {
				if (null != event.getSelection()
						&& event.getSelection() instanceof StructuredSelection) {
					StructuredSelection selection = (StructuredSelection) event
							.getSelection();

					IViewPart energyViewPart = getSite().getWorkbenchWindow()
							.getActivePage().findView(EnergyView.ID);

					/* Only necessary if view is visible. */
					if (null != energyViewPart
							&& energyViewPart instanceof EnergyView) {
						EnergyView energyView = (EnergyView) energyViewPart;

						long minStart = Long.MAX_VALUE;
						long maxStop = Long.MIN_VALUE;

						/*
						 * Search for the time stamp bounds of the current
						 * selection.
						 */
						for (Object selectedObject : selection.toArray()) {
							if (selectedObject instanceof TestCaseProfile) {
								TestCaseProfile profile = (TestCaseProfile) selectedObject;
								if (profile.getStartTime() < minStart)
									minStart = profile.getStartTime();
								// no else.

								if (profile.getEndTime() > maxStop)
									maxStop = profile.getEndTime();
								// no else.
							}
							// no else.
						}

						/* Update the energy view. */
						if (minStart < maxStop) {
							/* Add 5% tolerance before and after the bounds. */
							long length = maxStop - minStart;
							minStart = minStart - (long) (length * 0.05d);
							maxStop = maxStop + (long) (length * 0.05d);

							energyView.updateChartWithBounds(
									minStart
											+ testSuiteProfile
													.getPutTimeStampOffset(),
									maxStop
											+ testSuiteProfile
													.getPutTimeStampOffset());
						}
						// no else.
					}
					// no else.
				}
				// no else.
			}
		});

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

		testCaseItem.setControl(viewer.getTable());
		viewer.setSorter(new TestCaseTableSorter());

		/* Averaged results. */
		TabItem avgCaseItem = new TabItem(tabFolder, SWT.NULL);
		avgCaseItem.setText("Avg. Test Case Results");

		avgViewer = new TableViewer(tabFolder, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		createAvgColumns(parent, avgViewer);
		final Table avgTable = avgViewer.getTable();
		avgTable.setHeaderVisible(true);
		avgTable.setLinesVisible(true);

		avgViewer.setContentProvider(new ArrayContentProvider());

		// Make the selection available to other views
		getSite().setSelectionProvider(avgViewer);

		// Layout the viewer
		gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		avgViewer.getControl().setLayoutData(gridData);

		avgCaseItem.setControl(avgViewer.getTable());
		avgViewer.setSorter(new TestCaseTableSorter());

		/* Old test results. */
		TabItem oldResultsItem = new TabItem(tabFolder, SWT.NULL);
		oldResultsItem.setText("History of Test Results");

		oldTestResultViewer = new TableViewer(tabFolder, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		createOldTestResultColumns(parent, oldTestResultViewer);
		final Table oldResultTable = oldTestResultViewer.getTable();
		oldResultTable.setHeaderVisible(true);
		oldResultTable.setLinesVisible(true);

		oldTestResultViewer.setContentProvider(new ArrayContentProvider());

		// Make the selection available to other views
		getSite().setSelectionProvider(oldTestResultViewer);

		// Layout the viewer
		gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		oldTestResultViewer.getControl().setLayoutData(gridData);

		oldResultsItem.setControl(oldTestResultViewer.getTable());
		oldTestResultViewer.setSorter(new TestCaseTableSorter());

		// add historyTestResults to Table
		// updateOldTestResultViewer();

		// add menu to table
		oldTestResultViewer.getControl().setMenu(
				createMenuForOldTestResultViewer(oldTestResultViewer)
						.createContextMenu(oldTestResultViewer.getControl()));

		oldTestResultViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						if (null != event.getSelection()
								&& event.getSelection() instanceof StructuredSelection) {
							StructuredSelection selection = (StructuredSelection) event
									.getSelection();
							HistoryTestResultObject htro = ((HistoryTestResultObject) selection
									.getFirstElement());
							PerformActionPropagator.getInstance()
									.propagateEvent(EnergyView.ID,
											EnergyView.UPDATE_BAR_CHART, htro);
						}
					}
				});

		tabFolder.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// if the old-history-table gets selected, update content
				if (tabFolder.getSelectionIndex() == 2) {
					updateOldTestResultViewer();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	/** Helper method to export an {@link EnergyModel} from the profiled data. */
	private void exportEnergyModel() {

		// TODO Extract export of EnergyModel
		// ElementTreeSelectionDialog dialog;
		// WorkbenchLabelProvider aWorkbenchLabelProvider;
		// WorkbenchContentProvider aWorkbenchContentProvider;
		//
		// int pressedButton;
		// IResource resource;
		//
		// /* Create a dialog to select a File. */
		// aWorkbenchLabelProvider = new WorkbenchLabelProvider();
		// aWorkbenchContentProvider = new WorkbenchContentProvider();
		//
		// /* File filter for UML models only. */
		// PatternFilter filter = new PatternFilter();
		// filter.setPattern("*.uml");
		//
		// /* Configure the Dialog properties. */
		// dialog = new FilteredElementTreeSelectionDialog(getSite().getShell(),
		// aWorkbenchLabelProvider, aWorkbenchContentProvider, filter);
		//
		// dialog.setTitle("Select Service Model");
		// dialog.setMessage("Please select the service model, the profiled data belongs to.");
		// dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		// dialog.setComparator(new
		// ResourceComparator(ResourceComparator.NAME));
		// dialog.setAllowMultiple(false);
		//
		// /* Open the dialog. */
		// pressedButton = dialog.open();
		// resource = (IResource) dialog.getFirstResult();
		//
		// /* If OK was pressed set the File path. */
		// if (pressedButton == IDialogConstants.OK_ID) {
		//
		// /* Get the EMF model. */
		// ResourceSet rs = new ResourceSetImpl();
		// Resource emfResource = rs
		// .createResource(org.eclipse.emf.common.util.URI
		// .createFileURI(resource.getFullPath().toFile()
		// .toString()));
		//
		// if (!emfResource.isLoaded())
		// try {
		// emfResource.load(null);
		// } catch (IOException e) {
		// JouleUnitUiPlugIn.log(e, "Unable to open UML resource.");
		// }
		// // no else.
		//
		// Activity activityModel = null;
		//
		// for (EObject object : emfResource.getContents()) {
		// if (object instanceof Model) {
		// Model model = (Model) object;
		// for (NamedElement element : model.getMembers()) {
		// if (element instanceof Activity) {
		// activityModel = (Activity) element;
		// break;
		// }
		// // no else.
		// }
		// // end for.
		// }
		// // no else.
		// }
		// // end for.
		//
		// if (activityModel != null) {
		//
		// FileDialog fd = new FileDialog(getSite().getShell(), SWT.SAVE);
		// fd.setText("Select Energy Model File for Export");
		// String[] filterExt = { "*.energymodel", "*.*" };
		// fd.setFilterExtensions(filterExt);
		// fd.setFilterPath(resource.getLocation().removeLastSegments(1)
		// .toFile().toString());
		// fd.setFileName("my.energymodel");
		//
		// String path = fd.open();
		//
		// EnergyModel energyModel = EnergymodelFactory.eINSTANCE
		// .createEnergyModel();
		// energyModel.setActivityModel(activityModel);
		//
		// /* Add profiled results for mapping transitions. */
		// if (testSuiteProfile != null) {
		// for (AvgTestCaseResult testCaseResult : testSuiteProfile
		// .getAvgTestCaseResults()) {
		// String transitionName = testCaseResult.getID();
		//
		// ActivityEdge testedTransition = activityModel.getEdge(
		// transitionName, true, null, false);
		//
		// if (testedTransition != null
		// && testedTransition instanceof ControlFlow) {
		// PowerRateValue value = EnergymodelFactory.eINSTANCE
		// .createPowerRateValue();
		// value.setTransition((ControlFlow) testedTransition);
		// value.setPowerRate(new Double(testCaseResult
		// .getAvgPowerRate()).floatValue());
		// value.setDuration(new Double(testCaseResult
		// .getAvgDuration()).floatValue());
		// energyModel.getValues().add(value);
		// }
		// // no else.
		// }
		// // end for.
		// }
		// // no else.
		//
		// BufferedOutputStream bos;
		// try {
		// bos = new BufferedOutputStream(new FileOutputStream(path));
		//
		// EnergymodelPrinter2 printer = new EnergymodelPrinter2(bos,
		// null);
		// printer.print(energyModel);
		//
		// bos.close();
		// } catch (FileNotFoundException e) {
		// JouleUnitUiPlugIn.log(e, "Unable to export energy model.");
		// } catch (IOException e) {
		// JouleUnitUiPlugIn.log(e, "Unable to export energy model.");
		// }
		//
		// /* Refresh the project to get external updates. */
		// try {
		// IResource wsResource = ResourcesPlugin
		// .getWorkspace()
		// .getRoot()
		// .findMember(
		// new Path(resource
		// .getLocation()
		// .removeLastSegments(1)
		// .removeFirstSegments(
		// ResourcesPlugin
		// .getWorkspace()
		// .getRoot()
		// .getLocation()
		// .segmentCount())
		// .toFile()
		// .toString()
		// .substring(
		// resource.getLocation()
		// .getDevice()
		// .length())));
		// wsResource.refreshLocal(IResource.DEPTH_INFINITE, null);
		// }
		//
		// catch (CoreException e) {
		// JouleUnitUiPlugIn.log(
		// e,
		// "Refresh of newly created test cases failed: "
		// + e.getMessage());
		// }
		// }
		//
		// else {
		// ErrorDialog eDialog = new ErrorDialog(
		// getSite().getShell(),
		// "Service Model not found",
		// "Cannot find an activity within the selected service model.",
		// new Status(
		// IStatus.ERROR,
		// JouleUnitUiPlugIn.PLUGIN_ID,
		// "Cannot find an activity within the selected service model.",
		// null), SWT.NONE);
		// eDialog.open();
		// }
		// }
		// // no else.
	}

	/** Creates pull down menu. */
	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(saveAction);
		manager.add(exportEnergyModelAction);
		manager.add(loadTestResultAction);
	}

	/** Creates tool bar. */
	private void fillLocalToolBar(IToolBarManager manager) {
	}

	/**
	 * Helper method to load a {@link TestSuiteProfile} from the DB.
	 */
	private void loadTestResultFromDB() {

		/* Get test run id. */
		InputDialog dialog = new InputDialog(this.getSite().getShell(),
				"Import Test Run from DB", "Please enter the Test Run ID:",
				"0", new IInputValidator() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.jface.dialogs.IInputValidator#isValid(java
					 * .lang.String)
					 */
					public String isValid(String newText) {
						try {
							int id = Integer.parseInt(newText.trim());
							if (id > 0)
								return null;
							else
								return "The ID must be a positive Integer value.";
						}

						catch (NumberFormatException e) {
							return "The ID must be an Integer value.";
						}
					}
				});
		int returnCode = dialog.open();

		if (returnCode == Window.OK) {
			int testRunID = Integer.parseInt(dialog.getValue().trim());

			ProfilingResultImportFromDBJob job = new ProfilingResultImportFromDBJob(
					testRunID);
			job.schedule();
		}
		// no else.
	}

	private void makeActions() {
		saveAction = new Action("Save Data as CSV ...") {
			public void run() {
				FileDialog fd = new FileDialog(getSite().getShell(), SWT.SAVE);
				fd.setText("Save");
				String[] filterExt = { "*.txt", "*.*" };
				fd.setFilterExtensions(filterExt);

				final String path = fd.open();

				/* Export profile to text file. */
				ProfilingResultExportJob job = new ProfilingResultExportJob(
						testSuiteProfile, path);
				job.schedule();
				Util.getInstance().exportTestResultForHistory(
						testSuiteProfile.getTestCaseProfiles(), path);
				updateOldTestResultViewer();
			}
		};
		saveAction.setEnabled(false);

		exportEnergyModelAction = new Action("Export Energy Model ...") {
			public void run() {
				exportEnergyModel();
			}
		};
		exportEnergyModelAction.setEnabled(true);

		loadTestResultAction = new Action("Load Test Results from DB ...") {
			public void run() {
				loadTestResultFromDB();
			}
		};
		loadTestResultAction.setEnabled(true);
	}

	// private void compareToCurrentTest(HistoryTestResultObject
	// historyTestResultObject) {
	// MessageDialog.openInformation(null, "Information",
	// "Not implemented yet.");
	// }

	/**
	 * Helper method to mark the currently selected {@link TestCaseProfile}s as
	 * failed.
	 */
	private void markColumnsAsFailed() {

		if (null != viewer.getSelection()
				&& viewer.getSelection() instanceof StructuredSelection) {
			StructuredSelection selection = (StructuredSelection) viewer
					.getSelection();

			List<DbTestCaseProfile> testCaseProfiles = new ArrayList<DbTestCaseProfile>();

			/*
			 * Search for the time stamp bounds of the current selection.
			 */
			for (Object selectedObject : selection.toArray()) {
				if (selectedObject instanceof TestCaseProfile) {
					TestCaseProfile profile = (TestCaseProfile) selectedObject;
					profile.setFailed(true);

					/* Update table viewer. */
					viewer.update(profile, new String[] { "Test Case" });

					/* Probably propagate change to DB. */
					if (profile instanceof DbTestCaseProfile)
						testCaseProfiles.add((DbTestCaseProfile) profile);
					// no else.

					/* Propagate to affected avg test case results. */
					for (AvgTestCaseResult result : testSuiteProfile
							.getAvgTestCaseResults()) {
						/* Update in UI. */
						if (result.removeProfile(profile)) {
							result.addProfile(profile);
							avgViewer.update(result, null);
							/*
							 * Assume that Profile cannot belong to multiple
							 * average results.
							 */
							break;
						}
						// no else.
					}
				}
				// no else.
			}
			// end for.

			/* Propagate changes to DB. */
			UpdateTestCaseToDBJob updateJob = new UpdateTestCaseToDBJob(
					testCaseProfiles);
			updateJob.schedule();
		}
		// no else.
	}

	private void showEnergyModel(HistoryTestResultObject historyTestResultObject) {
		IViewPart energyViewPart = getSite().getWorkbenchWindow()
				.getActivePage().findView(EnergyView.ID);

		if (null != energyViewPart && energyViewPart instanceof EnergyView) {
			EnergyView energyView = (EnergyView) energyViewPart;
			energyView
					.updateValues(createTestSuiteProfile(historyTestResultObject));
		}
	}

	// private void compareToCurrentTest(HistoryTestResultObject
	// historyTestResultObject) {
	// MessageDialog.openInformation(null, "Information",
	// "Not implemented yet.");
	// }

	protected void updateOldTestResultViewer() {
		oldTestResultViewer.setInput(Util.getInstance()
				.importTestResultFromHistory(Util.LIMIT_ENTRIES_TO, true));
	}

	// private void compareToCurrentTest(HistoryTestResultObject
	// historyTestResultObject) {
	// MessageDialog.openInformation(null, "Information",
	// "Not implemented yet.");
	// }

	/**
	 * display an image in the table and center it
	 * 
	 * @author Piq
	 * 
	 */
	private class CenterImageLabelProvider extends OwnerDrawLabelProvider {

		private Image image = null;

		@Override
		public void update(ViewerCell cell) {
			Object element = cell.getElement();

			this.image = getImage(element);
		}

		public Image getImage(Object element) {
			return null;
		}

		@Override
		protected void measure(Event event, Object element) {
			// no-op
		}

		@Override
		protected void paint(Event event, Object element) {

			this.image = getImage(element);

			Widget item = event.item;
			Rectangle bounds = ((TableItem) item).getBounds(event.index);

			Rectangle imgBounds = image.getBounds();
			bounds.width /= 2;
			bounds.width -= imgBounds.width / 2;
			bounds.height /= 2;
			bounds.height -= imgBounds.height / 2;

			int x = bounds.width > 0 ? bounds.x + bounds.width : bounds.x;
			int y = bounds.height > 0 ? bounds.y + bounds.height : bounds.y;

			event.gc.drawImage(image, x, y);
		}
	}

	@Override
	public void handleEvent(String id, int action, Object o) {
		if (id == ID) {
			switch (action) {
			case UPDATE_OLD_HISTORY_TABLE:
				updateOldTestResultViewer();
				break;
			default:
				break;
			}
		}

	}
}
