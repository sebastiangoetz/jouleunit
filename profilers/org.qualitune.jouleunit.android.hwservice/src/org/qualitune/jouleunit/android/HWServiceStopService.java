package org.qualitune.jouleunit.android;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class HWServiceStopService extends Service {

	/**
	 * Binder class to bind a client to the service.
	 */
	public class MyBinder extends Binder {
		HWServiceStopService getService() {
			return HWServiceStopService.this;
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

		Log.i(HWServiceActivity.LOG_TAG, "HWService stopped.");
	}

	/**
	 * Stops the HWService task.
	 */
	private void startService() {
		Log.i(HWServiceActivity.LOG_TAG, "Try to stop HWService...");
		stopService(new Intent("org.qualitune.jouleunit.android.HWService"));
		stopSelf();
	}
}
