package org.qualitune.jouleunit.android.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity that can be called by other applications view a file and returns
 * immediately (e.g., to view an email attachment).
 * 
 * @author Claas Wilke
 */
public class FileViewActivity extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			/* No tragic. */
		}

		Intent _result = new Intent();
		setResult(Activity.RESULT_OK, _result);
		finish();
	}
}
