package org.qualitune.jouleunit.android;

import java.util.Timer;
import java.util.TimerTask;

import org.qualitune.jouleunit.android.battery.BatteryInfo;
import org.qualitune.jouleunit.android.hwservice.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HWServiceActivity extends Activity {

	/** The tag used for logging of error and info messages. */
	public static final String LOG_TAG = "org.qualitune.jouleunit.android.hwservice";

	/** Interval for updates of the HW service. */
	private static final long UPDATE_INTERVAL = 100;

	/** Timer used for updates. */
	private Timer mTimer = new Timer();

	/** The {@link BatteryInfo} of this {@link HWServiceActivity}. */
	private BatteryInfo mBatteryInfo;

	/** {@link TextView} used to update battery info. */
	private TextView mTextView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mTextView = (TextView) findViewById(R.id.textView1);

		Button startButton = (Button) findViewById(R.id.buttonStartService);
		startButton.setOnClickListener(new OnClickListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				startService(new Intent(
						"org.qualitune.jouleunit.android.HWService"));

				mBatteryInfo = new BatteryInfo();
				mTimer.scheduleAtFixedRate(new TimerTask() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see java.util.TimerTask#run()
					 */
					@Override
					public void run() {
						mBatteryInfo.updateInfo();
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								mTextView.setText(mBatteryInfo.getCurrentVoltage() + "mV, " + mBatteryInfo.getCurrentCurrent() + "mA");
							}
						});
					}
				}, 0, UPDATE_INTERVAL);

			}
		});

		Button stopButton = (Button) findViewById(R.id.buttonStopService);
		stopButton.setOnClickListener(new OnClickListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				stopService(new Intent(
						"org.qualitune.jouleunit.android.HWService"));
				mTimer.cancel();
				mBatteryInfo = null;
			}
		});
	}
}