package org.qualitune.jouleunit.android.content;

import org.qualitune.jouleunit.android.hwservice.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Acitivity that can be called by other applications to return a default
 * picture that can be used as a resource for testing (e.g., as an email
 * attachment).
 * 
 * @author Claas Wilke
 */
public class SpeechMemoAttachmentActivity extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Uri _resultUri = Uri
				.parse("android.resource://org.qualitune.jouleunit.android.hwservice/"
						+ R.drawable.speechmemo);
		Intent _result = new Intent();
		_result.setData(_resultUri);
		setResult(Activity.RESULT_OK, _result);
		finish();
	}
}
