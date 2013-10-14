package org.qualitune.jouleunit.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.Layer;
import org.jfree.ui.TextAnchor;
import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.PowerRate;
import org.qualitune.jouleunit.coordinator.TestCaseProfile;
import org.qualitune.jouleunit.coordinator.TestSuiteProfile;
import org.qualitune.jouleunit.data.IProfilingResultReceiver;
import org.qualitune.jouleunit.data.PerformActionListener;
import org.qualitune.jouleunit.data.PerformActionPropagator;
import org.qualitune.jouleunit.data.ResultPropagator;

/**
 * View to display the current energy charging rate.
 * 
 * @author Claas Wilke
 */
public class EnergyView extends ViewPart implements IProfilingResultReceiver,
		PerformActionListener {

	/**
	 * {@link MouseListener} implementation that updates this {@link EnergyView}
	 * when the mouse is released.
	 * 
	 * @author Claas Wilke
	 */
	private class UpdateMouseListener implements MouseListener {

		/** The {@link Shell} used to display the {@link EnergyView}. */
		private Shell mShell;

		/**
		 * Creates a new {@link UpdateMouseListener}.
		 * 
		 * @param shell
		 *            The {@link Shell} used to display the {@link EnergyView}.
		 */
		public UpdateMouseListener(Shell shell) {
			mShell = shell;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.
		 * events.MouseEvent)
		 */
		public void mouseUp(MouseEvent e) {
			if (tabFolder.getSelectionIndex() == 0) {
				updateChartInterval(mShell);
			}
			if (tabFolder.getSelectionIndex() == 1) {
				updateBarChart(mShell);
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt
		 * .events.MouseEvent)
		 */
		public void mouseDown(MouseEvent e) {
			/* Do nothing. */
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse
		 * .swt.events.MouseEvent)
		 */
		public void mouseDoubleClick(MouseEvent e) {
			/* Do nothing. */
		}
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.qualitune.jouleunit.ui.views.energyview";

	/** Title of CPU frequency line. */
	private static final String CPU_VALUES = "CPU Frequency [MHz]";

	/** Title of WiFi traffic line. */
	private static final String LCD_VALUES = "LCD Brightness [0 .. 255]";

	/** Title of time axis. */
	private static final String TIME_AXIS = "Time [secs]";

	/** Title of time axis in progressDaigram. */
	private static final String TIME_AXIS_PROGRESS = "Testresult No.";

	/** Title of y axis in progressDaigram. */
	private static final String Y_AXIS_PROGRESS = "Values";

	/**
	 * The resolution of the view (typically 1/1000 for seconds instead of
	 * milliseconds).
	 */
	private final static double TIME_AXIS_RESOLUTION = 1 / 1000d;

	/** Title of power rate axis. */
	private static final String RATE_AXIS = "Power Rate [mW]";

	/** Title of diagram. */
	private static final String VIEW_TITLE = "Joule Unit Test Results";

	/** Title of diagram in progressDaigram. */
	private static final String VIEW_TITLE_PROGRESS = "Previous Testruns Comparison - Trendtracking";

	/** Title of WiFi traffic line. */
	private static final String WIFI_VALUES = "WiFi Traffic [Byte]";

	public static final int UPDATE_BAR_CHART = 0;

	public static final int SHOW_ENERGY_MODEL = 1;

	/** {@link ChartComposite} to contain {@link JFreeChart}. */
	private ChartComposite chart;

	/** {@link ChartComposite} to contain {@link JFreeChart}. */
	protected ChartComposite chartProgress;

	/** The {@link XYSeriesCollection} data set of the {@link JFreeChart}. */
	private XYSeriesCollection dataset;

	/** The {@link XYSeriesCollection} data set of the {@link JFreeChart}. */
	protected DefaultCategoryDataset datasetProgress;

	/** {@link JFreeChart} to display values. */
	private JFreeChart jfc;

	/** {@link JFreeChart} to display values progress. */
	protected JFreeChart jfcProgress;

	/**
	 * The currently displayed {@link TestSuiteProfile} or null.
	 */
	private TestSuiteProfile lastTestSuiteProfile;

	/**
	 * Lower bound to select the range of the visible time within the complete
	 * {@link TestSuiteProfile} in seconds.
	 */
	private float lowerBound = 0l;

	/** The {@link Text} for the lower bound. */
	private Text lowerBoundText;

	/**
	 * The minimum start time to be displayed in the view (used as offset for
	 * zero time on x-axis).
	 */
	private long minStartTime;

	/** The {@link Text} for the entriesCounter. */
	private Text counterEntriesText;

	private int countOfBarEntries = Util.LIMIT_BAR_ENTRIES_TO;

	/**
	 * Upper bound to select the range of the visible time within the complete
	 * {@link TestSuiteProfile} in seconds.
	 */
	private float upperBound = 10l;

	/** The {@link Text} for the upper bound. */
	private Text upperBoundText;

	/** The check box to enable or disable WiFi info display. */
	private Button showWiFiInfos;

	/** The check box to enable or disable WiFi info display. */
	private Button showCpuInfos;

	/** The check box to enable or disable WiFi info display. */
	private Button showLcdInfos;

	private Button orderResultsForID;

	private Button showJustTestsWithSameID;

	private Button showJustTheSingleTest;

	protected Button showFailedTest;

	protected Button showHistoryPowerRate;

	protected Button showHistoryDuration;

	protected Button showHistoryConsumption;

	/** Tabfolder containing all charts */
	private TabFolder tabFolder = null;

	/**
	 * remeber which ID was the last, which was shown in the bar-chart, to
	 * redraw bar-chart if settings are changed
	 */
	protected HistoryTestResultObject latestComparedHTRO = null;

	/**
	 * The constructor.
	 */
	public EnergyView() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	public void createPartControl(final Composite parent) {

		createViewer(parent);

		PerformActionPropagator.getInstance().registerEventListener(this);

	}

	private void createViewer(Composite parent) {

		tabFolder = new TabFolder(parent, SWT.NONE);

		/* Individual test case results. */
		{
			TabItem energiePartItem = new TabItem(tabFolder, SWT.NULL);

			energiePartItem.setText("Current Test Run");

			tabFolder.setLayout(new GridLayout(1, false));

			Composite controlWhole = new Composite(tabFolder, SWT.NONE);
			controlWhole.setLayout(new GridLayout(1, false));

			Composite control = new Composite(controlWhole, SWT.NONE);
			control.setLayout(new RowLayout(SWT.HORIZONTAL));

			Label lowerBoundLabel = new Label(control, SWT.NONE);
			lowerBoundLabel.setText("Show values from sec. ");
			lowerBoundText = new Text(control, SWT.BORDER);
			lowerBoundText.setText(lowerBound + "");
			RowData rowData = new RowData();
			rowData.width = 100;
			lowerBoundText.setLayoutData(rowData);

			Label upperBoundLabel = new Label(control, SWT.NONE);
			upperBoundLabel.setText(" to sec. ");
			upperBoundText = new Text(control, SWT.BORDER);
			upperBoundText.setText(upperBound + "");
			rowData = new RowData();
			rowData.width = 100;
			upperBoundText.setLayoutData(rowData);

			Button resizeButton = new Button(control, SWT.NONE);
			resizeButton.setText("Update Chart");

			resizeButton.addMouseListener(new UpdateMouseListener(parent
					.getShell()));

			showCpuInfos = new Button(control, SWT.CHECK);
			showCpuInfos.setSelection(true);
			showCpuInfos.addMouseListener(new UpdateMouseListener(parent
					.getShell()));
			Label showCpuInfoLabel = new Label(control, SWT.NONE);
			showCpuInfoLabel.setText("Show CPU infos ");

			showWiFiInfos = new Button(control, SWT.CHECK);
			showWiFiInfos.setSelection(true);
			showWiFiInfos.addMouseListener(new UpdateMouseListener(parent
					.getShell()));
			Label showWiFiInfoLabel = new Label(control, SWT.NONE);
			showWiFiInfoLabel.setText("Show WiFi infos ");

			showLcdInfos = new Button(control, SWT.CHECK);
			showLcdInfos.setSelection(true);
			showLcdInfos.addMouseListener(new UpdateMouseListener(parent
					.getShell()));
			Label showLcdInfoLabel = new Label(control, SWT.NONE);
			showLcdInfoLabel.setText("Show display infos ");

			Composite chartPane = new Composite(controlWhole, SWT.NONE);
			chartPane.setLayout(new FillLayout());
			GridData data = new GridData(GridData.FILL_BOTH);
			chartPane.setLayoutData(data);

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			jfc = ChartFactory.createLineChart(VIEW_TITLE, "", TIME_AXIS,
					dataset, PlotOrientation.HORIZONTAL, false, false, false);
			chart = new ChartComposite(chartPane, SWT.NONE);
			chart.setChart(jfc);

			makeActions();
			contributeToActionBars();

			ResultPropagator.registerListener(this);
			energiePartItem.setControl(controlWhole);

		}

		{
			TabItem progressPartItem = new TabItem(tabFolder, SWT.NULL);
			progressPartItem.setText("Test Run History");

			tabFolder.setLayout(new GridLayout(1, false));

			Composite controlWhole = new Composite(tabFolder, SWT.NONE);
			controlWhole.setLayout(new GridLayout(1, false));

			Composite control = new Composite(controlWhole, SWT.NONE);
			RowLayout rowL = new RowLayout();
			rowL.spacing = 10;
			rowL.type = SWT.HORIZONTAL;
			control.setLayout(rowL);

			Label counteEntriesLabel = new Label(control, SWT.NONE);
			counteEntriesLabel.setText("Show ");
			counterEntriesText = new Text(control, SWT.BORDER);
			counterEntriesText.setText(countOfBarEntries + "");
			RowData rowData = new RowData();
			rowData.width = 50;
			counterEntriesText.setLayoutData(rowData);
			Label counteEntriesLabel2 = new Label(control, SWT.NONE);
			counteEntriesLabel2.setText(" entries");

			Button resizeButton = new Button(control, SWT.NONE);
			resizeButton.setText("Refresh");
			resizeButton.addMouseListener(new UpdateMouseListener(parent
					.getShell()));

			showHistoryPowerRate = new Button(control, SWT.CHECK);
			showHistoryPowerRate.setSelection(true);
			Label showHistoryPowerRateLabel = new Label(control, SWT.NONE);
			showHistoryPowerRateLabel.setText("Power Rates");

			showHistoryDuration = new Button(control, SWT.CHECK);
			showHistoryDuration.setSelection(true);
			Label showHistoryDurationLabel = new Label(control, SWT.NONE);
			showHistoryDurationLabel.setText("Duration");

			showHistoryConsumption = new Button(control, SWT.CHECK);
			showHistoryConsumption.setSelection(true);
			Label showHistoryConsumptionLabel = new Label(control, SWT.NONE);
			showHistoryConsumptionLabel.setText("Consumption");

			showJustTestsWithSameID = new Button(control, SWT.CHECK);
			showJustTestsWithSameID.setSelection(true);
			Label showJustTestsWithSameIDLabel = new Label(control, SWT.NONE);
			showJustTestsWithSameIDLabel.setText("Only Tests with one ID");

			orderResultsForID = new Button(control, SWT.CHECK);
			orderResultsForID.setSelection(true);
			Label orderResultsForIDLabel = new Label(control, SWT.NONE);
			orderResultsForIDLabel.setText("Ordered by ID");

			// showJustTheSingleTest = new Button(control, SWT.CHECK);
			// showJustTheSingleTest.setSelection(false);
			// Label showJustTheSingleTestLabel = new Label(control, SWT.NONE);
			// showJustTheSingleTestLabel.setText("Show selected test on click");

			showFailedTest = new Button(control, SWT.CHECK);
			showFailedTest.setSelection(false);
			Label showFailedTestLabel = new Label(control, SWT.NONE);
			showFailedTestLabel.setText("Show failed tests");

			Composite chartPaneProgress = new Composite(controlWhole, SWT.NONE);
			chartPaneProgress.setLayout(new FillLayout());

			GridData data = new GridData(GridData.FILL_BOTH);
			chartPaneProgress.setLayoutData(data);

			DefaultCategoryDataset datasetProgress = new DefaultCategoryDataset();
			jfcProgress = ChartFactory.createBarChart(VIEW_TITLE_PROGRESS,
					TIME_AXIS_PROGRESS, Y_AXIS_PROGRESS, datasetProgress,
					PlotOrientation.VERTICAL, true, true, false);
			chartProgress = new ChartComposite(chartPaneProgress, SWT.NONE);
			chartProgress.setChart(jfcProgress);

			// updateBarChartValues(null);
			initBarChart(null, false);

			tabFolder.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if (tabFolder.getSelectionIndex() == 1)
						updateBarChartValues(null);
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			progressPartItem.setControl(controlWhole);
		}

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		chart.setFocus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.data.IProfilingResultReceiver#updateToNewResult
	 * (org.qualitune.jouleunit.data.TestSuiteProfile)
	 */
	public void updateToNewResult(TestSuiteProfile profile) {
		updateValues(profile);
	}

	/**
	 * Updates the {@link EnergyView} with a new given {@link TestSuiteProfile}.
	 * 
	 * @param profile
	 *            The {@link TestSuiteProfile}.
	 */
	public void updateValues(final TestSuiteProfile profile) {

		lastTestSuiteProfile = profile;
		minStartTime = 0l;

		/* Run on the UI thread ... */
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				selfUpdateValues(profile);
			}
		});
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

	/**
	 * Updates the {@link EnergyView} using a given {@link TestSuiteProfile}.
	 * 
	 * @param profile
	 *            The {@link TestSuiteProfile} to be displayed.
	 */
	public void selfUpdateValues(TestSuiteProfile profile) {

		if (null != profile) {
			if (minStartTime == 0l)
				computeMinStartTime(profile);
			// no else.

			/* Paint the chart. */
			if (chart.isVisible()) {
				updateXYChartValues(profile);
			}
			if (chartProgress.isVisible()) {
				updateBarChartValues(null);
			}
			// no else.
		}

		/* If the profile is null, draw an empty chart. */
		else {
			// XY-Chart
			jfc = ChartFactory.createXYLineChart(VIEW_TITLE, "", TIME_AXIS,
					dataset, PlotOrientation.HORIZONTAL, true, false, false);
			chart.setChart(jfc);
			chart.forceRedraw();

			// BAR-Chart
			jfcProgress = ChartFactory.createBarChart(VIEW_TITLE_PROGRESS,
					TIME_AXIS_PROGRESS, Y_AXIS_PROGRESS, datasetProgress,
					PlotOrientation.VERTICAL, true, true, false);
			chartProgress.setChart(jfcProgress);
			chartProgress.forceRedraw();
		}
	}

	private void updateBarChartValues(HistoryTestResultObject o) {
		updateBarChartValues(o, false);
	}

	private void updateBarChartValues(HistoryTestResultObject o,
			final boolean justSingle) {
		datasetProgress = new DefaultCategoryDataset();
		latestComparedHTRO = o;
		final String id = o != null ? o.getId() : "";
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				List<HistoryTestResultObject> allHTROList = Util
						.getInstance()
						.trimTestResultFromHistoryList(
								id,
								justSingle ? 1 : Integer
										.parseInt(counterEntriesText.getText()),
								false);

				UpdateEnergyViewJob job = new UpdateEnergyViewJob(allHTROList,
						EnergyView.this);
				job.schedule();
			}
		});

		jfcProgress = ChartFactory.createAreaChart(VIEW_TITLE_PROGRESS,
				TIME_AXIS_PROGRESS, Y_AXIS_PROGRESS, datasetProgress,
				PlotOrientation.VERTICAL, true, true, false);
		
		chartProgress.setChart(jfcProgress);
		chartProgress.forceRedraw();
	}

	private void initBarChart(HistoryTestResultObject o,
			final boolean justSingle) {
		datasetProgress = new DefaultCategoryDataset();
		latestComparedHTRO = o;

		jfcProgress = ChartFactory.createBarChart(VIEW_TITLE_PROGRESS,
				TIME_AXIS_PROGRESS, Y_AXIS_PROGRESS, datasetProgress,
				PlotOrientation.VERTICAL, true, true, false);
		chartProgress.setChart(jfcProgress);
		chartProgress.forceRedraw();
	}

	private void updateXYChartValues(TestSuiteProfile profile) {
		dataset = new XYSeriesCollection();
		jfc = ChartFactory.createXYLineChart("", "", TIME_AXIS, dataset,
				PlotOrientation.HORIZONTAL, true, false, false);
		final XYPlot plot = jfc.getXYPlot();
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		int lineNumber = 0;

		/* Display power rates. */
		addPowerRateSeries(Color.BLUE, dataset, renderer, profile,
				minStartTime, TIME_AXIS_RESOLUTION, lineNumber);
		lineNumber++;
		// end for.

		/* Paint a range for each test case. */
		for (TestCaseProfile aProfile : profile.getTestCaseProfiles()) {

			double start = (profile.adaptPutTimeStamp(aProfile.getStartTime()) - minStartTime)
					* TIME_AXIS_RESOLUTION;
			double stop = (profile.adaptPutTimeStamp(aProfile.getEndTime()) - minStartTime)
					* TIME_AXIS_RESOLUTION;

			/* Only add test cases within the specified range. */
			if (start >= lowerBound || stop <= upperBound) {

				/*
				 * If test cases overlap with bound, indicate this with a
				 * standard overlap of 0.5 secs.
				 */
				if (start <= lowerBound)
					start = lowerBound - 0.5d;
				else if (stop >= upperBound)
					stop = upperBound + 0.5d;
				// no else.

				IntervalMarker testCaseMarker = new IntervalMarker(start, stop);

				String labelText = aProfile.getId();
				if (labelText.contains("("))
					labelText = labelText.substring(0, labelText.indexOf("("));
				// no else.

				testCaseMarker.setLabel(labelText);
				testCaseMarker.setLabelTextAnchor(TextAnchor.TOP_LEFT);
				testCaseMarker.setLabelFont(testCaseMarker.getLabelFont()
						.deriveFont(12f));

				/* Set color of test case depending on its result. */
				if (aProfile.isFailed())
					testCaseMarker.setPaint(new Color(150, 100, 100));
				else
					testCaseMarker.setPaint(new Color(100, 150, 100));

				plot.addRangeMarker(testCaseMarker, Layer.BACKGROUND);
			}
			// no else.
		}
		// end for.

		/* Add values for the PUT CPU frequency. */
		if (showCpuInfos.getSelection()) {
			Map<Long, Long[]> cpuFrequencies = profile.getCpuFrequencies();
			if (cpuFrequencies.size() > 0) {
				/* Split one map into multiple maps. */
				int cpuCount = cpuFrequencies.values().iterator().next().length;
				List<Map<Long, Long>> singleCpuFrequencies = new ArrayList<Map<Long, Long>>(
						cpuCount);

				for (int index = 0; index < cpuCount; index++)
					singleCpuFrequencies.add(new HashMap<Long, Long>());
				// end for.

				for (Long key : cpuFrequencies.keySet()) {
					for (int index = 0; index < cpuCount; index++)
						singleCpuFrequencies.get(index).put(key,
								cpuFrequencies.get(key)[index]);
					// end for.
				}
				// end for.

				for (int index = 0; index < singleCpuFrequencies.size(); index++) {
					addLineSeries(singleCpuFrequencies.get(index), CPU_VALUES,
							new Color(200, 0, 0), dataset, renderer, profile,
							minStartTime, TIME_AXIS_RESOLUTION, lineNumber,
							1000);
					lineNumber++;
				}
				// end for.
			}
			// no else.
		}
		// no else.

		/* Add values for the PUT WiFi traffic. */
		if (showWiFiInfos.getSelection()) {
			addLineSeries(profile.getWiFiTraffic(), WIFI_VALUES, Color.ORANGE,
					dataset, renderer, profile, minStartTime,
					TIME_AXIS_RESOLUTION, lineNumber, 1);
			lineNumber++;
		}
		// no else.

		/* Add values for the LCD brightness. */
		if (showLcdInfos.getSelection()) {
			addLineSeries(profile.getLcdBrightness(), LCD_VALUES, new Color(
					050, 150, 050), dataset, renderer, profile, minStartTime,
					TIME_AXIS_RESOLUTION, lineNumber, 1);
			lineNumber++;
		}
		// no else.

		plot.setRenderer(renderer);

		// change the auto tick unit selection to integer units only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setRange(lowerBound - 0.5d, upperBound + 0.5d);

		chart.setChart(jfc);
		chart.forceRedraw();

	}

	/**
	 * Helper method to compute the least time stamp of the given
	 * {@link TestSuiteProfile}, either on the test runner or the test device
	 * side.
	 * 
	 * @param profile
	 *            The {@link TestSuiteProfile}.
	 * @return The mininmum start time in millis (normalized based on the
	 *         computed time stamp offsets).
	 */
	private long computeMinStartTime(TestSuiteProfile profile) {
		/* Compute significant values for all energy profiles. */
		List<PowerRate> energyValues = profile.getEnergyProfile()
				.getSignificantValues(EnergyProfile.START_EVENT_ID,
						EnergyProfile.END_EVENT_ID);

		long timeOffsetTestRunner = 0l;
		long timeOffsetPUT = 0l;

		if (profile.isTimestampCorrectionEnabled()) {
			timeOffsetTestRunner = profile.getTrTimeStampOffsetFromNanos();
			timeOffsetPUT = profile.getPutTimeStampOffset();
		}
		// no else.

		long minStartTimeTestRunner = energyValues.get(0).getTimeStamp();

		minStartTimeTestRunner = minStartTimeTestRunner / 1000000
				+ timeOffsetTestRunner;

		long minStartTimeAtPut = Long.MAX_VALUE;

		/* Compute the minimal start time at the platform under test. */
		List<TestCaseProfile> testCaseProfiles = new ArrayList<TestCaseProfile>(
				profile.getTestCaseProfiles());
		Collections.sort(testCaseProfiles);
		if (testCaseProfiles.size() > 0)
			minStartTimeAtPut = testCaseProfiles.get(0).getStartTime()
					+ timeOffsetPUT;
		// no else.

		/* Compute the global minimal time stamp. */
		minStartTime = Math.min(minStartTimeTestRunner, minStartTimeAtPut);

		return minStartTime;
	}

	/**
	 * Helper method to add a line to the chart.
	 * 
	 * @param values
	 *            The values of the line as a {@link Map} of key values.
	 * @param title
	 *            The title of the line as a {@link String}
	 * @param color
	 *            The {@link Color} of the line.
	 * @param dataSet
	 *            The {@link Dataset} the line belongs to.
	 * @param renderer
	 *            The {@link XYLineAndShapeRenderer} of the chart.
	 * @param profile
	 *            The {@link TestSuiteProfile} the data belongs to.
	 * @param minStartTime
	 *            The minimum start time of the chart to be displayed.
	 * @param resolution
	 *            The resolution of the time axis (x axis) as a factor to
	 *            milliseconds (e.g. 1/1000 means display in seconds).
	 * @param lineIndex
	 *            The index of the line to be added.
	 * @param valueFactor
	 *            The factor to adapt the value's resolution (e.g., 1000 means
	 *            kilo adaptation).
	 */
	private void addLineSeries(Map<Long, Long> values, String title,
			Color color, XYSeriesCollection dataSet,
			XYLineAndShapeRenderer renderer, TestSuiteProfile profile,
			long minStartTime, double resolution, int lineIndex,
			double valueFactor) {

		XYSeries lineSeries = new XYSeries(title);
		lineSeries.setDescription(title);

		/* Length of the visible interval. */
		float visibleInterval = (upperBound - lowerBound) / 200;
		long lastValue = Long.MIN_VALUE;
		double lastTime = Double.MIN_VALUE;

		List<Long> timeStamps = new ArrayList<Long>(values.keySet());
		Collections.sort(timeStamps);

		for (Long timeStamp : timeStamps) {
			double time = profile.adaptPutTimeStamp(timeStamp - minStartTime)
					* resolution;

			/* Only add values within the specified range. */
			if (time >= lowerBound && time <= upperBound) {

				/* Do not display similar values that are to dense to be seen. */
				if (values.get(timeStamp) != lastValue
						|| (time - lastTime > visibleInterval)) {
					lineSeries.add(values.get(timeStamp) / valueFactor, time);
					lastValue = values.get(timeStamp);
					lastTime = time;
				}
				// no else.
			}
			// no else.
		}
		// end for.

		dataSet.addSeries(lineSeries);

		renderer.setSeriesLinesVisible(lineIndex, false);
		renderer.setSeriesStroke(lineIndex, new BasicStroke(3));
		renderer.setSeriesShapesVisible(lineIndex, true);
		renderer.setSeriesPaint(lineIndex, color);
	}

	/**
	 * Helper method to add a line for a {@link EnergyProfile} to the chart.
	 * 
	 * @param color
	 *            The {@link Color} of the line.
	 * @param dataSet
	 *            The {@link Dataset} the line belongs to.
	 * @param renderer
	 *            The {@link XYLineAndShapeRenderer} of the chart.
	 * @param profile
	 *            The {@link TestSuiteProfile} the data belongs to.
	 * @param minStartTime
	 *            The minimum start time of the chart to be displayed.
	 * @param resolution
	 *            The resolution of the time axis (x axis) as a factor to
	 *            milliseconds (e.g. 1/1000 means display in seconds).
	 * @param lineIndex
	 *            The index of the line to be added.
	 */
	private void addPowerRateSeries(Color color, XYSeriesCollection dataSet,
			XYLineAndShapeRenderer renderer, TestSuiteProfile profile,
			long minStartTime, double resolution, int lineIndex) {

		XYSeries seriesPowerRate = new XYSeries(RATE_AXIS);
		seriesPowerRate.setDescription(RATE_AXIS);

		/* TODO Only retrieve necessary values. */
		List<PowerRate> values = profile.getEnergyProfile()
				.getSignificantValues(EnergyProfile.START_EVENT_ID,
						EnergyProfile.END_EVENT_ID);

		for (int index = 0; index < values.size(); index++) {
			PowerRate currentValue = values.get(index);
			double time = (profile
					.adaptTrTimeStamp(currentValue.getTimeStamp()) - minStartTime) / 1000d;

			/* Only add power rates within the specified range. */
			if (time >= lowerBound && time <= upperBound)
				/* Flip discharging into consumption *(-1). */
				seriesPowerRate.add(-currentValue.getPowerRate(), time);
			// no else.
		}
		// end for.

		dataset.addSeries(seriesPowerRate);

		renderer.setSeriesLinesVisible(lineIndex, false);
		renderer.setSeriesStroke(lineIndex, new BasicStroke(3));
		renderer.setSeriesShapesVisible(lineIndex, true);
		renderer.setSeriesPaint(lineIndex, color);
	}

	/** Creates action bars. */
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	/** Creates pull down menu. */
	private void fillLocalPullDown(IMenuManager manager) {
		/* No actions provided. */
	}

	/** Creates tool bar. */
	private void fillLocalToolBar(IToolBarManager manager) {
	}

	/** Creates {@link Action} implementations. */
	private void makeActions() {
		/* No actions provided. */
	}

	/**
	 * Updates the {@link EnergyView} by using the given time stamps as lower
	 * and upper bounds of the time interval to be displayed.
	 * 
	 * @param lowerBoundTimeStamp
	 *            The lower bound's time stamp.
	 * @param upperBoundTimeStamp
	 *            The upper bound's time stamp.
	 */
	public void updateChartWithBounds(final long lowerBoundTimeStamp,
			final long upperBoundTimeStamp) {

		if (null != lastTestSuiteProfile) {
			if (minStartTime == 0l)
				computeMinStartTime(lastTestSuiteProfile);
			// no else.

			/* Convert time stamps to relative values */
			float lowerBound = (lowerBoundTimeStamp - minStartTime) / 1000f;
			float upperBound = (upperBoundTimeStamp - minStartTime) / 1000f;
			lowerBoundText.setText(lowerBound + "");
			upperBoundText.setText(upperBound + "");

			updateChartInterval(this.getSite().getShell());
		}
		// no else.
	}

	/**
	 * Helper method to repaint the chart if the {@link Text} fields for its
	 * bounds have been modified.
	 * 
	 * @param shell
	 *            The {@link Shell} used to open {@link Dialog}s.
	 */
	private void updateChartInterval(Shell shell) {

		float start = lowerBound;
		float end = upperBound;

		/* Check for valid long values. */
		try {
			if (null != lowerBoundText)
				start = Float.parseFloat(lowerBoundText.getText());
			// no else.

			if (null != upperBoundText)
				end = Float.parseFloat(upperBoundText.getText());
			// no else.
		} catch (NumberFormatException ex) {
			/* Do nothing (use old value). */
		}

		/* No negative intervals. */
		if (start > end || end < start) {
			start = lowerBound;
			end = upperBound;
		}
		// no else.

		/* Check for too large intervals. */
		if (end - start > 300) {
			boolean openLargeInterval = MessageDialog
					.openQuestion(
							shell,
							"Large Interval Definition",
							"The inteval you specified is very large. This may leed to insufficient memory to display all the data. Do you want to continue?");
			if (!openLargeInterval) {
				start = lowerBound;
				end = upperBound;
			}
			// no else.
		}
		// no else.

		/* Update bounds. */
		lowerBoundText.setText(start + "");
		upperBoundText.setText(end + "");

		lowerBound = start;
		upperBound = end;

		/* Repaint. */
		if (lastTestSuiteProfile != null)
			updateValues(lastTestSuiteProfile);
		// no else.
	}

	/**
	 * Helper method to repaint the bar-chart if the {@link Text} fields have
	 * been modified.
	 * 
	 * @param shell
	 *            The {@link Shell} used to open {@link Dialog}s.
	 */
	private void updateBarChart(Shell shell) {
		updateBarChartValues(latestComparedHTRO);
	}

	@Override
	public void handleEvent(String id, int action, Object o) {
		if (id == ID) {
			switch (action) {
			case UPDATE_BAR_CHART:
				if (null != showJustTheSingleTest
						&& showJustTheSingleTest.getSelection()) {
					updateBarChartValues((HistoryTestResultObject) o, true);
				} else {
					updateBarChartValues((HistoryTestResultObject) o);
				}
				tabFolder.setSelection(1); // select history-diagram
				break;
			case SHOW_ENERGY_MODEL:
				tabFolder.setSelection(0); // select history-diagram
				break;
			default:
				break;
			}
		}
	}
}