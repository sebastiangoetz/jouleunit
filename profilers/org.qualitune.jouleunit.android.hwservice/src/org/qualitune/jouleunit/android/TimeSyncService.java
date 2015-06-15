package org.qualitune.jouleunit.android;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TimeSyncService extends Service {

	/**
	 * Binder class to bind a client to the service.
	 */
	public class MyBinder extends Binder {
		TimeSyncService getService() {
			return TimeSyncService.this;
		}
	}

	/** {@link IBinder} used by clients. */
	private final IBinder mBinder = new MyBinder();

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

	/**
	 * Starts the HWService task.
	 */
	private void startService() {
		/* Once log the time sync time stamp. Afterwards, stop the service. */
		new RetreiveFeedTask().execute();
		this.stopSelf();
	}

	/**
	 * This helper task computes an offset for the time stamps on the android
	 * device to sync them with other time stamps.
	 */
	class RetreiveFeedTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg0) {

			Long offsetValue = 0l;
			String ntpServers[] = new String[] { "ptbtime1.ptb.de",
					"ntps1-1.cs.tu-berlin.de" };

			NTPUDPClient client = new NTPUDPClient();
			// We want to timeout if a response takes longer than 10 seconds
			client.setDefaultTimeout(10000);
			String errorMsg = null;
			try {
				client.open();

				for (String ntpServer : ntpServers) {
					try {
						InetAddress hostAddr = InetAddress.getByName(ntpServer);
						TimeInfo info = client.getTime(hostAddr);

						/* Compute offset/delay. */
						info.computeDetails();
						offsetValue = info.getOffset();

						errorMsg = null;
						break;
					}

					catch (IOException e) {
						errorMsg = e.getMessage();
					}
				}
			}

			catch (IOException ioe) {
				errorMsg = ioe.getMessage();
			}

			if (null != errorMsg)
				Log.e(this.getClass().getCanonicalName(),
						"Error during retrieving time offset: " + errorMsg);
			// no else.

			client.close();

			/* Test code to compute the time stamp offset. */
			Log.d("TIME_SYNC", "Offset:" + offsetValue + ",CurrentMillis:"
					+ System.currentTimeMillis());

			return null;
		}
	}
}
