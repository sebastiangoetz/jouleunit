package org.qualitune.jouleunit.android.battery;

import java.util.Timer;
import java.util.TimerTask;

import org.qualitune.jouleunit.android.HWServiceActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BatteryService extends Service {

	/**
	 * Binder class to bind a client to the service.
	 */
	public class MyBinder extends Binder {
		BatteryService getService() {
			return BatteryService.this;
		}
	}

	/** The tag used for logging of error and info messages. */
	public static final String LOG_TAG = "org.qualitune.jouleunit.android.batteryservice";

	/** Interval for updates of the HW service. */
	private static final long UPDATE_INTERVAL = 100;

	/** {@link IBinder} used by clients. */
	private final IBinder mBinder = new MyBinder();

	/** The {@link BatteryInfo} of this {@link HWServiceActivity}. */
	private BatteryInfo mBatteryInfo;

	/** Timer used for updates. */
	private Timer mTimer = new Timer();

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

		try {
			mBatteryInfo = new BatteryInfo();
			startService();
		}

		catch (IllegalStateException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
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

		Log.i(LOG_TAG, "BatteryService stopped.");
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
				updateBatteryState();
			}
		}, 0, UPDATE_INTERVAL);

		Log.i(LOG_TAG, "BatteryService started.");
	}

	/**
	 * Helper method to update the Battery info.
	 */
	private void updateBatteryState() {

		if (mBatteryInfo != null) {
			mBatteryInfo.updateInfo();

			Log.i(LOG_TAG,
					"battery_power_rate : "
							+ mBatteryInfo.getCurrentPowerRate());
			Log.i(LOG_TAG,
					"battery_capacity : " + mBatteryInfo.getCurrentCapacity());
		}
		// no else.
	}
}
