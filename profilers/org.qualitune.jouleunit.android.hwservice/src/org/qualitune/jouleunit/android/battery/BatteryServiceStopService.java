package org.qualitune.jouleunit.android.battery;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Helper service to stop the battery service.
 * 
 * @author Claas Wilke
 */
public class BatteryServiceStopService extends Service {

	/**
	 * Binder class to bind a client to the service.
	 */
	public class MyBinder extends Binder {
		BatteryServiceStopService getService() {
			return BatteryServiceStopService.this;
		}
	}

	/** {@link Binder} of this {@link Service}. */
	private Binder mBinder = new MyBinder();

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

		Log.i(BatteryService.LOG_TAG, "BatteryService stopped.");
	}

	/**
	 * Stops the HWService task.
	 */
	private void startService() {
		Log.i(BatteryService.LOG_TAG, "Try to stop BatteryService...");
		stopService(new Intent("org.qualitune.jouleunit.android.battery.BatteryService"));
		stopSelf();
	}
}
