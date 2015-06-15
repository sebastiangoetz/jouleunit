package org.qualitune.jouleunit.android;

import android.app.Service;
import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;

/**
 * Provides methods to get information on the device under test's LCD display
 * utilization.
 * 
 * @author Claas Wilke
 */
public class LcdInfo {

	/** The {@link Service} required by this LcfInfo. */
	private Service mService;

	/**
	 * Creates a new {@link LcdInfo}.
	 * 
	 * @param service
	 *            The {@link Service} required by this LcfInfo.
	 */
	public LcdInfo(Service service) {
		mService = service;
	}

	/**
	 * Returns the devices current LCD brightness as an int.
	 * 
	 * @return The LCD brightness as an int (between 0 (off) and 255 (full
	 *         brightness). -1 indicates an error during accessing the info.
	 */
	public int getLcdBrightness() {
		try {
			int result;
			PowerManager powerMgr = (PowerManager) mService
					.getSystemService(Context.POWER_SERVICE);

			if (powerMgr.isScreenOn())
				result = Settings.System.getInt(mService.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS);
			else
				result = 0;

			return result;
		}

		catch (SettingNotFoundException e) {
			Log.e(HWServiceActivity.LOG_TAG,
					"Failure during accessing the LCD brightness: "
							+ e.getMessage());
			return -1;
		}
	}
}
