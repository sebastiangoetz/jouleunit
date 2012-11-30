package easy.browser.classic.test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ProgressBar;

import com.jayway.android.robotium.solo.Solo;

@SuppressWarnings("rawtypes")
public class EasyBrowserTest extends ActivityInstrumentationTestCase2 {

	/** As PDF download we use a slide show on our work. */
	private static final String DOWNLOAD_LARGE_PDF = "www.claaswilke.de/testdata/large.pdf";

	/** As PDF download we use a slide show on our work. */
	private static final String DOWNLOAD_SMALL_PDF = "www.claaswilke.de/testdata/small.pdf";

	/** As image URL used for testing. */
	private static final String IMAGE_URL_LARGE_GIF = "www.claaswilke.de/testdata/large.gif";

	/** As image URL used for testing. */
	private static final String IMAGE_URL_LARGE_JPEG = "www.claaswilke.de/testdata/large.jpg";

	/** As image URL used for testing. */
	private static final String IMAGE_URL_SMALL_GIF = "www.claaswilke.de/testdata/small.gif";

	/** As image URL used for testing. */
	private static final String IMAGE_URL_SMALL_JPEG = "www.claaswilke.de/testdata/small.jpg";

	private static final String TAG_LARGE_GIF = "large gif";

	private static final String TAG_LARGE_JPEG = "large jpeg";

	private static final String TAG_LARGE_PDF = "large pdf";

	private static final String TAG_SMALL_GIF = "small gif";

	private static final String TAG_SMALL_JPEG = "small jpeg";

	private static final String TAG_SMALL_PDF = "small pdf";

	/** As search string we use Google's Zeitgeist top 1 2011 news search. */
	private static final String SEARCH_STRING = "Fukushima";

	private static final String WEBSITE_GOOGLE = "www.google.com";

	/** A site with an embedded image. */
	private static final String WEBSITE_IMAGE = "www.claaswilke.de/testdata/imagesite.html";

	/** A site with JavaScript. */
	private static final String WEBSITE_JAVA_SCRIPT = "www.claaswilke.de/testdata/jssite.html";

	private static final String WEBSITE_NYTIMES = "www.nytimes.com";

	/** A raw site without CSS and JavaScript. */
	private static final String WEBSITE_RAW = "www.claaswilke.de/testdata/rawsite.html";

	/** A site with CSS. */
	private static final String WEBSITE_STYLE_SHEETS = "www.claaswilke.de/testdata/csssite.html";

	/** A site with an embedded video. */
	private static final String WEBSITE_VIDEO = "www.claaswilke.de/testdata/videosite.html";

	private static final String WEBSITE_YOUTUBE = "www.youtube.com";

	private static final String ACTIVITY_SIMPLE_NAME = "SimpleBrowser";

	/** Tag for logging during testing. */
	public static final String LOG_TAG = "TestRunner";

	/** Package ID of the app under test. */
	private static final String TARGET_PACKAGE_ID = "easy.browser.classic";

	/** ID of the app under test's main activity. */
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "easy.lib.SimpleBrowser";

	private static final int WAIT_TIME_AFTER_CLICK = 1000;

	/** Launcher activity class. */
	private static Class<?> launcherActivityClass;

	/** Static code to find the activity class. */
	static {
		try {
			launcherActivityClass = Class
					.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		}

		catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/** {@link Solo} used for testing. */
	private Solo solo;

	/**
	 * Constructor
	 * 
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public EasyBrowserTest() throws ClassNotFoundException {
		super(TARGET_PACKAGE_ID, launcherActivityClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.test.ActivityInstrumentationTestCase2#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testEasyBrowser() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		invokeSubTest("subtest00_dummy");
		invokeSubTest("subtest01_RawWebsite");
		invokeSubTest("subtest02_CacheRawWebsite");
		invokeSubTest("subtest03_JavaScriptWebsite");
		invokeSubTest("subtest04_CacheJavaScriptWebsite");
		invokeSubTest("subtest05_StyleSheetWebsite");
		invokeSubTest("subtest06_CacheStyleSheetWebsite");
		invokeSubTest("subtest07_ImageWebsite");
		invokeSubTest("subtest08_CacheImageWebsite");
		invokeSubTest("subtest09_VideoWebsite");
		invokeSubTest("subtest10_CacheVideoWebsite");
		invokeSubTest("subtest11_WebsiteGoogle");
		invokeSubTest("subtest12_CacheWebsiteGoogle");
		invokeSubTest("subtest13_WebsiteNYTimes");
		invokeSubTest("subtest14_CacheWebsiteNYTimes");
		invokeSubTest("subtest15_WebsiteYouTube");
		invokeSubTest("subtest16_CacheWebsiteYouTube");
		invokeSubTest("subtest17_OpenImageLargeGif");
		invokeSubTest("subtest18_CacheOpenImageLargeGif");
		invokeSubTest("subtest19_OpenImageLargeJpeg");
		invokeSubTest("subtest20_CacheOpenImageLargeJpeg");
		invokeSubTest("subtest21_OpenImageSmallGif");
		invokeSubTest("subtest22_CacheOpenImageSmallGif");
		invokeSubTest("subtest23_OpenImageSmallJpeg");
		invokeSubTest("subtest24_CacheOpenImageSmallJpeg");
		invokeSubTest("subtest25_DownloadLargePdf");
		invokeSubTest("subtest26_CachedDownloadLargePdf");
		invokeSubTest("subtest26_CachedDownloadLargePdf");
		invokeSubTest("subtest27_DownloadSmallPdf");
		invokeSubTest("subtest28_CachedDownloadSmallPdf");
		invokeSubTest("subtest29_SearchOnSite");
		invokeSubTest("subtest30_CacheSearchOnSite");
		invokeSubTest("subtest31_History");
		invokeSubTest("subtest32_CacheHistory");
	}

	/** Simple test case to open a site without CSS and JavaScript. */
	public void subtest01_RawWebsite() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_RAW);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site without CSS and JavaScript. */
	public void subtest02_CacheRawWebsite() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_RAW);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site without CSS but with JavaScript. */
	public void subtest03_JavaScriptWebsite() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_JAVA_SCRIPT);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site without CSS but with JavaScript. */
	public void subtest04_CacheJavaScriptWebsite() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_JAVA_SCRIPT);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with CSS but without JavaScript. */
	public void subtest05_StyleSheetWebsite() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_STYLE_SHEETS);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with CSS but without JavaScript. */
	public void subtest06_CacheStyleSheetWebsite() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_STYLE_SHEETS);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with an embedded image. */
	public void subtest07_ImageWebsite() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_IMAGE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with an embedded image. */
	public void subtest08_CacheImageWebsite() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_IMAGE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with an embedded video. */
	public void subtest09_VideoWebsite() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_VIDEO);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with an embedded video. */
	public void subtest10_CacheVideoWebsite() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_VIDEO);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open Google. */
	public void subtest11_WebsiteGoogle() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_GOOGLE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open Google. */
	public void subtest12_CacheWebsiteGoogle() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_GOOGLE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open New York Times. */
	public void subtest13_WebsiteNYTimes() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_NYTIMES);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open New York Times. */
	public void subtest14_CacheWebsiteNYTimes() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_NYTIMES);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open YouTube. */
	public void subtest15_WebsiteYouTube() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_YOUTUBE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open YouTube. */
	public void subtest16_CacheWebsiteYouTube() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_YOUTUBE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open an image. */
	public void subtest17_OpenImageLargeGif() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		Log.d(LOG_TAG, "started: openImage[" + TAG_LARGE_GIF + "]");
		openWebsite(IMAGE_URL_LARGE_GIF);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: openImage[" + TAG_LARGE_GIF + "]");

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open an image. */
	public void subtest18_CacheOpenImageLargeGif() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		Log.d(LOG_TAG, "started: openImage[" + TAG_LARGE_GIF + "]");
		openWebsite(IMAGE_URL_LARGE_GIF);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: openImage[" + TAG_LARGE_GIF + "]");

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open an image. */
	public void subtest19_OpenImageLargeJpeg() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		Log.d(LOG_TAG, "started: openImage[" + TAG_LARGE_JPEG + "]");
		openWebsite(IMAGE_URL_LARGE_JPEG);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: openImage[" + TAG_LARGE_JPEG + "]");

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open an image. */
	public void subtest20_CacheOpenImageLargeJpeg() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		Log.d(LOG_TAG, "started: openImage[" + TAG_LARGE_JPEG + "]");
		openWebsite(IMAGE_URL_LARGE_JPEG);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: openImage[" + TAG_LARGE_JPEG + "]");

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open an image. */
	public void subtest21_OpenImageSmallGif() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		Log.d(LOG_TAG, "started: openImage[" + TAG_SMALL_GIF + "]");
		openWebsite(IMAGE_URL_SMALL_GIF);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: openImage[" + TAG_SMALL_GIF + "]");

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open an image. */
	public void subtest22_CacheOpenImageSmallGif() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		Log.d(LOG_TAG, "started: openImage[" + TAG_SMALL_GIF + "]");
		openWebsite(IMAGE_URL_SMALL_GIF);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: openImage[" + TAG_SMALL_GIF + "]");

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open an image. */
	public void subtest23_OpenImageSmallJpeg() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		Log.d(LOG_TAG, "started: openImage[" + TAG_SMALL_JPEG + "]");
		openWebsite(IMAGE_URL_SMALL_JPEG);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: openImage[" + TAG_SMALL_JPEG + "]");

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open an image. */
	public void subtest24_CacheOpenImageSmallJpeg() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		Log.d(LOG_TAG, "started: openImage[" + TAG_SMALL_JPEG + "]");
		openWebsite(IMAGE_URL_SMALL_JPEG);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: openImage[" + TAG_SMALL_JPEG + "]");

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to download a PDF file. */
	public void subtest25_DownloadLargePdf() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		File file = new File("./sdcard/simpleHome/large.pdf");

		/* Clear cache. */
		if (file.exists())
			file.delete();
		// no else.

		Log.d(LOG_TAG, "started: downloadFile[" + TAG_LARGE_PDF + "]");
		openWebsite(DOWNLOAD_LARGE_PDF);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: downloadFile[" + TAG_LARGE_PDF + "]");

		/* Wait till the download finished. */
		while (file.length() < 1404865)
			solo.sleep(100);
		// end while.
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to download a PDF file. */
	public void subtest26_CachedDownloadLargePdf() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		File file = new File("./sdcard/simpleHome/large.pdf");

		Log.d(LOG_TAG, "started: downloadFile[" + TAG_LARGE_PDF + "]");
		openWebsite(DOWNLOAD_LARGE_PDF);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: downloadFile[" + TAG_LARGE_PDF + "]");

		/* Wait till the download finished. */
		while (file.length() < 1404865)
			solo.sleep(100);
		// end while.
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to download a PDF file. */
	public void subtest27_DownloadSmallPdf() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		File file = new File("./sdcard/simpleHome/small.pdf");

		/* Clear cache. */
		if (file.exists())
			file.delete();
		// no else.

		Log.d(LOG_TAG, "started: downloadFile[" + TAG_SMALL_PDF + "]");
		openWebsite(DOWNLOAD_SMALL_PDF);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: downloadFile[" + TAG_SMALL_PDF + "]");

		/* Wait till the download finished. */
		while (file.length() < 238757)
			solo.sleep(100);
		// end while.
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to download a PDF file. */
	public void subtest28_CachedDownloadSmallPdf() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		File file = new File("./sdcard/simpleHome/small.pdf");

		Log.d(LOG_TAG, "started: downloadFile[" + TAG_SMALL_PDF + "]");
		openWebsite(DOWNLOAD_SMALL_PDF);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: downloadFile[" + TAG_SMALL_PDF + "]");

		/* Wait till the download finished. */
		while (file.length() < 238757)
			solo.sleep(100);
		// end while.
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/**
	 * Simple test case to do a web search by using the default search machine.
	 */
	public void subtest29_SearchOnSite() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(SEARCH_STRING);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/**
	 * Simple test case to do a web search by using the default search machine.
	 */
	public void subtest30_CacheSearchOnSite() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(SEARCH_STRING);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/**
	 * Simple test case to open Google, ny times and then switch back and
	 * forward.
	 */
	public void subtest31_History() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_GOOGLE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_NYTIMES);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* Back button. */
		solo.clickOnImage(3);
		waitToLoadPage();
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* And forward again. */
		solo.clickOnImage(4);
		waitToLoadPage();
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/**
	 * Simple test case to open Google, ny times and then switch back and
	 * forward.
	 */
	public void subtest32_CacheHistory() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_GOOGLE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_NYTIMES);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* Back button. */
		solo.clickOnImage(3);
		waitToLoadPage();
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* And forward again. */
		solo.clickOnImage(4);
		waitToLoadPage();
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/**
	 * Helper method to open a website.
	 * 
	 * @param url
	 *            The URL to be opened as a {@link String}.
	 */
	private void openWebsite(String url) {
		/* Enter URL. */
		solo.clickOnEditText(0);
		solo.clearEditText(0);
		solo.enterText(0, url);

		/* Wait seven seconds to simulate manual URL edit. */
		solo.sleep(7000);

		/* Open website. */
		solo.sendKey(KeyEvent.KEYCODE_ENTER);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		waitToLoadPage();
	}

	/**
	 * Helper method to invoke a subtest.
	 * 
	 * @param methodName
	 *            The name of the {@link Method} to be invoked.
	 */
	private void invokeSubTest(String methodName) {

		try {
			Method method = this.getClass().getMethod(methodName);

			Log.i(LOG_TAG, "started: " + methodName + "()");

			try {
				method.invoke(this);
			}

			catch (AssertionError e) {
				Log.i(LOG_TAG, "failed: " + methodName + "()");
				Log.i(LOG_TAG,
						e.getClass().getSimpleName() + ": " + e.getMessage());
			}

			catch (IllegalArgumentException e) {
				Log.i(LOG_TAG, "failed: " + methodName + "()");
				Log.i(LOG_TAG,
						e.getClass().getSimpleName() + ": " + e.getMessage());
			}

			catch (IllegalAccessException e) {
				Log.i(LOG_TAG, "failed: " + methodName + "()");
				Log.i(LOG_TAG,
						e.getClass().getSimpleName() + ": " + e.getMessage());
			}

			catch (InvocationTargetException e) {
				Log.i(LOG_TAG, "failed: " + methodName + "()");
				Log.i(LOG_TAG,
						e.getClass().getSimpleName() + ": " + e.getMessage());
				Log.i(LOG_TAG, "cause: "
						+ e.getCause().getClass().getSimpleName());
				e.getCause().printStackTrace();
			}

			Log.i(LOG_TAG, "finished: " + methodName + "()");
		}

		catch (NoSuchMethodException e1) {
			Log.e(LOG_TAG, methodName + "() was not found");
		}

	}

	/** Helper method to wait for a page to be opened. */
	private void waitToLoadPage() {
		/* Sometimes this is not visible (e.g., PDF opened). */
		if (solo.getCurrentActivity().hasWindowFocus()) {
			/* Wait to load the site. */
			ProgressBar bar = solo.getCurrentProgressBars().get(0);
			while (bar.getProgress() < bar.getMax())
				solo.sleep(100);
			// end while.
		}
		// no else.
	}

	/** Simple test case to open the application and not to do anything further. */
	public void subtest00_dummy() {

		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}
}