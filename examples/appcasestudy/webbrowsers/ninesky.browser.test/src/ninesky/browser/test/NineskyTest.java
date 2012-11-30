package ninesky.browser.test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

@SuppressWarnings("rawtypes")
public class NineskyTest extends ActivityInstrumentationTestCase2 {

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

	private static final String ACTIVITY_SIMPLE_NAME = "GuideActivity";

	/** Tag for logging during testing. */
	public static final String LOG_TAG = "TestRunner";

	/** ID of the app under test's main activity. */
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.ninesky.browsercommon.GuideActivity";

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
	public NineskyTest() throws ClassNotFoundException {
		super(launcherActivityClass);
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

	public void testNinesky() {
		assertTrue(solo.waitForActivity(ACTIVITY_SIMPLE_NAME, 10000));
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest01_RawWebsite");
		invokeSubTest("subtest02_CacheRawWebsite");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest03_JavaScriptWebsite");
		invokeSubTest("subtest04_CacheJavaScriptWebsite");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest05_StyleSheetWebsite");
		invokeSubTest("subtest06_CacheStyleSheetWebsite");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest07_ImageWebsite");
		invokeSubTest("subtest08_CacheImageWebsite");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest09_VideoWebsite");
		invokeSubTest("subtest10_CacheVideoWebsite");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest11_WebsiteGoogle");
		invokeSubTest("subtest12_CacheWebsiteGoogle");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest13_WebsiteNYTimes");
		invokeSubTest("subtest14_CacheWebsiteNYTimes");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest15_WebsiteYouTube");
		invokeSubTest("subtest16_CacheWebsiteYouTube");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest17_OpenImageLargeGif");
		invokeSubTest("subtest18_CacheOpenImageLargeGif");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest19_OpenImageLargeJpeg");
		invokeSubTest("subtest20_OPrepareCacheOpenImageLargeJpeg");
		invokeSubTest("subtest20_CacheOpenImageLargeJpeg");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest21_OpenImageSmallGif");
		invokeSubTest("subtest22_CacheOpenImageSmallGif");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest23_OpenImageSmallJpeg");
		invokeSubTest("subtest24_CacheOpenImageSmallJpeg");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest25_DownloadLargePdf");
		invokeSubTest("subtest26_CachedDownloadLargePdf");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest27_DownloadSmallPdf");
		invokeSubTest("subtest28_CachedDownloadSmallPdf");
		invokeSubTest("subtest01_0ClearCache");
		invokeSubTest("subtest29_SearchOnSite");
		invokeSubTest("subtest30_CacheSearchOnSite");
		invokeSubTest("subtest31_0ClearCache");
		invokeSubTest("subtest31_History");
		invokeSubTest("subtest32_CacheHistory");
	}

	// /** Simple test case to clear the browsers cache. */
	// public void subtest00_0TurnCachingOff() {
	// solo.clickOnMenuItem("Settings");
	// solo.sleep(100);
	//
	// /* Click on Settings. */
	// List<TextView> views = new ArrayList<TextView>();
	// for (View view : solo.getViews()) {
	// if (view instanceof TextView) {
	// TextView tView = (TextView) view;
	// if (tView.getText().equals("Settings"))
	// views.add(tView);
	// // no else.
	// }
	// // no else.
	// }
	// // end for.
	// solo.clickOnView(views.get(views.size() - 1));
	// solo.sleep(100);
	//
	// solo.clickOnText("Security");
	// solo.sleep(100);
	//
	// /* Disable cache */
	// solo.clickOnScreen(683, 355);
	// solo.sleep(100);
	//
	// solo.sendKey(KeyEvent.KEYCODE_BACK);
	// solo.sleep(100);
	//
	// solo.sleep(WAIT_TIME_AFTER_CLICK);
	// }

	/** Simple test case to clear the browsers cache. */
	public void subtest01_0ClearCache() {
		clearCache();
	}

	/** Simple test case to open a site without CSS and JavaScript. */
	public void subtest01_RawWebsite() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_RAW);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site without CSS and JavaScript. */
	public void subtest02_CacheRawWebsite() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_RAW);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site without CSS but with JavaScript. */
	public void subtest03_JavaScriptWebsite() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_JAVA_SCRIPT);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site without CSS but with JavaScript. */
	public void subtest04_CacheJavaScriptWebsite() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_JAVA_SCRIPT);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with CSS but without JavaScript. */
	public void subtest05_StyleSheetWebsite() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_STYLE_SHEETS);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with CSS but without JavaScript. */
	public void subtest06_CacheStyleSheetWebsite() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_STYLE_SHEETS);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with an embedded image. */
	public void subtest07_ImageWebsite() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_IMAGE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with an embedded image. */
	public void subtest08_CacheImageWebsite() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_IMAGE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with an embedded video. */
	public void subtest09_VideoWebsite() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_VIDEO);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* How to wait for the video to be loaded? */
		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open a site with an embedded video. */
	public void subtest10_CacheVideoWebsite() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_VIDEO);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* How to wait for the video to be loaded? */
		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open Google. */
	public void subtest11_WebsiteGoogle() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_GOOGLE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open Google. */
	public void subtest12_CacheWebsiteGoogle() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_GOOGLE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open New York Times. */
	public void subtest13_WebsiteNYTimes() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_NYTIMES);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open New York Times. */
	public void subtest14_CacheWebsiteNYTimes() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_NYTIMES);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open YouTube. */
	public void subtest15_WebsiteYouTube() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_YOUTUBE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open YouTube. */
	public void subtest16_CacheWebsiteYouTube() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		openWebsite(WEBSITE_YOUTUBE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open an image. */
	public void subtest17_OpenImageLargeGif() {
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
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "started: openImage[" + TAG_LARGE_JPEG + "]");
		openWebsite(IMAGE_URL_LARGE_JPEG);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: openImage[" + TAG_LARGE_JPEG + "]");
	}

	/** Simple test case to open an image. */
	public void subtest20_OPrepareCacheOpenImageLargeJpeg() {

		/* Regain the address bar. */
		solo.sendKey(KeyEvent.KEYCODE_MENU);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_MENU);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to open an image. */
	public void subtest20_CacheOpenImageLargeJpeg() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "started: openImage[" + TAG_LARGE_JPEG + "]");
		openWebsite(IMAGE_URL_LARGE_JPEG);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: openImage[" + TAG_LARGE_JPEG + "]");
	}

	/** Simple test case to open an image. */
	public void subtest21_OpenImageSmallGif() {
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
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		File file = new File("./sdcard/download/large.pdf");

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

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to download a PDF file. */
	public void subtest26_CachedDownloadLargePdf() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		File file = new File("./sdcard/download/large.pdf");

		Log.d(LOG_TAG, "started: downloadFile[" + TAG_LARGE_PDF + "]");
		openWebsite(DOWNLOAD_LARGE_PDF);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: downloadFile[" + TAG_LARGE_PDF + "]");

		/* Wait till the download finished. */
		while (file.length() < 1404865)
			solo.sleep(100);
		// end while.
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to download a PDF file. */
	public void subtest27_DownloadSmallPdf() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		File file = new File("./sdcard/download/small.pdf");

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

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/** Simple test case to download a PDF file. */
	public void subtest28_CachedDownloadSmallPdf() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		File file = new File("./sdcard/download/small.pdf");

		Log.d(LOG_TAG, "started: downloadFile[" + TAG_SMALL_PDF + "]");
		openWebsite(DOWNLOAD_SMALL_PDF);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: downloadFile[" + TAG_SMALL_PDF + "]");

		/* Wait till the download finished. */
		while (file.length() < 238757)
			solo.sleep(100);
		// end while.
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/**
	 * Simple test case to do a web search by using the default search machine.
	 */
	public void subtest29_SearchOnSite() {
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
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(SEARCH_STRING);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/**
	 * Simple test case to open Google, NY times and then switch back and
	 * forward.
	 */
	public void subtest31_History() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_GOOGLE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_MENU);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_MENU);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_NYTIMES);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* Back button. */
		solo.sendKey(KeyEvent.KEYCODE_BACK);
		waitToLoadPage();
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* And forward again. */
		/*
		 * TODO Unfortunately the forward button must be pressed via absolute
		 * position. Must be adapted for each device under test.
		 */
		solo.clickOnScreen(240f, 1213f);
		waitToLoadPage();
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	/**
	 * Simple test case to open Google, NY times and then switch back and
	 * forward.
	 */
	public void subtest32_CacheHistory() {
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_GOOGLE);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_MENU);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_MENU);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		openWebsite(WEBSITE_NYTIMES);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* Back button. */
		solo.sendKey(KeyEvent.KEYCODE_BACK);
		waitToLoadPage();
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* And forward again. */
		/*
		 * TODO Unfortunately the forward button must be pressed via absolute
		 * position. Must be adapted for each device under test.
		 */
		solo.clickOnScreen(240f, 1213f);
		waitToLoadPage();
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
	}

	private void clearCache() {
		solo.clickOnMenuItem("Settings");
		solo.sleep(100);

		/* Click on Settings. */
		List<TextView> views = new ArrayList<TextView>();
		for (View view : solo.getViews()) {
			if (view instanceof TextView) {
				TextView tView = (TextView) view;
				if (tView.getText().equals("Settings"))
					views.add(tView);
				// no else.
			}
			// no else.
		}
		// end for.
		solo.clickOnView(views.get(views.size() - 1));
		solo.sleep(100);

		solo.clickOnText("Security");
		solo.sleep(100);

		// solo.clickOnText("Verlauf");
		solo.clickOnScreen(150, 880);
		solo.sleep(100);

		solo.clickOnText("OK");
		solo.sleep(100);

		solo.clickOnText("Clear downloading history");
		solo.sleep(100);

		solo.clickOnText("OK");
		solo.sleep(100);

		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(100);

		solo.sleep(WAIT_TIME_AFTER_CLICK);
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

	/**
	 * Helper method to open a website.
	 * 
	 * @param url
	 *            The URL to be opened as a {@link String}.
	 */
	private void openWebsite(String url) {

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

	/** Helper method to wait for a website to be opened. */
	private void waitToLoadPage() {
		/* Sometimes this is not visible (e.g., PDF opened). */
		if (solo.getCurrentActivity().hasWindowFocus()
				&& solo.getCurrentProgressBars().size() > 0) {
			/* Wait to load the site. */
			ProgressBar bar = solo.getCurrentProgressBars().get(0);
			while (bar.isShown() && bar.getProgress() < bar.getMax())
				solo.sleep(100);
			// end while.
		}
		// no else.
	}

	/**
	 * Helper method to remove text from a {@link TextView} char by char.
	 * 
	 * @param index
	 *            The index of the {@link TextView} to clear.
	 */
	protected void clearTextCharByChar(int index) {

		TextView text = solo.getText(index);

		solo.clickOnEditText(index);
		int length = text.getText().length();
		/*
		 * Avoids that the cursor sits at the beginning although text is
		 * contained.
		 */
		solo.clearEditText(index);
		/* Simulate delete. */
		for (int counter = 0; counter < length; counter++)
			solo.sendKey(KeyEvent.KEYCODE_DEL);
		// end for.
	}

	/**
	 * Helper method to add text into a {@link TextView} char by char.
	 * 
	 * @param index
	 *            The index of the {@link TextView} to enter the text.
	 * @param text
	 *            The text to be entered.
	 */
	protected void enterTextCharByChar(int index, String text) {
		/* To avoid side effects. */
		String copy = new String(text);

		while (copy.length() > 0) {
			String aChar = copy.substring(0, 1);
			copy = copy.substring(1);

			solo.enterText(index, aChar);
		}
		// end while.
	}
}