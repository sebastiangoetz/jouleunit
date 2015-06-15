package org.qualitune.jouleunit.android;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class HWService extends Service {

	/**
	 * Binder class to bind a client to the service.
	 */
	public class MyBinder extends Binder {
		HWService getService() {
			return HWService.this;
		}
	}

	/** Interval for updates of the HW service. */
	private static final long UPDATE_INTERVAL = 100;

	/** {@link IBinder} used by clients. */
	private final IBinder mBinder = new MyBinder();

	/** The {@link CpuInfo} of this {@link HWServiceActivity}. */
	private CpuInfo mCpuInfo;

	/** The {@link LcdInfo} of this {@link HWServiceActivity}. */
	private LcdInfo mLcdInfo;

	/** Timer used for updates. */
	private Timer mTimer = new Timer();

	/** The {@link WiFiInfo} of this {@link HWServiceActivity}. */
	private WiFiInfo mWiFiInfo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		mLcdInfo = new LcdInfo(this);
		mCpuInfo = new CpuInfo();

		try {
			mWiFiInfo = new WiFiInfo();
		}

		catch (IllegalStateException e) {
			Log.e(HWServiceActivity.LOG_TAG, e.getMessage());
		}

		startService();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mTimer != null)
			mTimer.cancel();
		// no else.

		Log.i(HWServiceActivity.LOG_TAG, "HWService stopped.");
	}

	/**
	 * Starts the HWService task.
	 */
	private void startService() {
		mTimer.scheduleAtFixedRate(new TimerTask() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.TimerTask#run()
			 */
			@Override
			public void run() {
				updateLCDBrightness();
				updateCPUFrequency();
				updateWiFiState();
			}
		}, 0, UPDATE_INTERVAL);

		Log.i(HWServiceActivity.LOG_TAG, "HWService started.");
	}

	/**
	 * Helper method to update the CPU frequency info.
	 */
	private void updateCPUFrequency() {

		StringBuffer frequencies = new StringBuffer();

		for (long frequency : mCpuInfo.getCPUFrequencies())
			frequencies.append(frequency + ", ");
		// end for.

		Log.i(HWServiceActivity.LOG_TAG, "cpu_freq : " + frequencies);
	}

	/**
	 * Helper method to update the LCD brightness info.
	 */
	private void updateLCDBrightness() {
		Log.i(HWServiceActivity.LOG_TAG,
				"lcd_brightness : " + mLcdInfo.getLcdBrightness());
	}

	/**
	 * Helper method to update the WiFi info.
	 */
	private void updateWiFiState() {

		if (mWiFiInfo != null) {
			mWiFiInfo.updateInfo();

			Log.i(HWServiceActivity.LOG_TAG, "wifi_sent_packages : "
					+ mWiFiInfo.getTransmittedPackages());
			Log.i(HWServiceActivity.LOG_TAG, "wifi_received_packages : "
					+ mWiFiInfo.getReceivedPackages());
			Log.i(HWServiceActivity.LOG_TAG,
					"wifi_sent_bytes : " + mWiFiInfo.getTransmittedBytes());
			Log.i(HWServiceActivity.LOG_TAG, "wifi_received_bytes : "
					+ mWiFiInfo.getReceivedBytes());

			Log.i(HWServiceActivity.LOG_TAG,
					"wifi_traffic : "
							+ (mWiFiInfo.getTransmittedBytes() + mWiFiInfo
									.getReceivedBytes()));
		}
		// no else.
	}
}
