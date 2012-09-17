package com.fsck.k9.test;

import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

/**
 * Contains test cases to profile K9 mail w.r.t. energy consumption.
 * 
 * @author Claas Wilke
 */
@SuppressWarnings("rawtypes")
public class K9MailTests extends ActivityInstrumentationTestCase2 {

	/** Tag for logging during testing. */
	public static final String LOG_TAG = "TestRunner";

	private static final String ACCOUNT_ADDRESS = "to@be.done";

	private static final String ACCOUNT_NAME = "User";

	private static final String ACCOUNT_PWD = "password";

	private static final String ACCOUNT_SERVER = "mail.server";

	private static final String ACCOUNT_USER_NAME = "User";

	private static final String FORWARD_ADDRESS = "to@be.done";

	private static final String FORWARD_MSG = "FYI";

	private static final String MENU_ADD_NOTE = "Note";

	private static final String MENU_ADD_PICTURE = "Picture";

	private static final String MENU_ADD_SPEECH_MEMO = "Speech Memo";

	private static final String MSG_NOTE_MAIL = "Remember me!";

	private static final String MSG_PICTURE_MAIL = "What a nice picture!";

	private static final String MSG_SPEECH_MAIL = "Please type this for me.";

	private static final String TAG_LONG_MAIL = "long mail";

	private static final String TAG_NO_MAIL = "no mail";

	private static final String TAG_NOTE_MAIL = "note mail";

	private static final String TAG_PICTURE_MAIL = "picture mail";

	private static final String TAG_SHORT_MAIL = "short mail";

	private static final String TAG_SPEECH_MAIL = "speech mail";

	private static final String TOPIC_LONG_MAIL = "A long mail without an attachment";

	private static final String TOPIC_PICTURE_MAIL = "A mail with an attachment";

	private static final String TOPIC_SHORT_MAIL = "A simple mail without an attachment";

	private static int WAIT_TIME_AFTER_CLICK = 1000;

	/** Package ID of the app under test. */
	private static final String TARGET_PACKAGE_ID = "com.fsck.k9";

	/** ID of the app under test's main activity. */
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.fsck.k9.activity.Accounts";

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
	public K9MailTests() throws ClassNotFoundException {
		super(TARGET_PACKAGE_ID, launcherActivityClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.test.ActivityInstrumentationTestCase2#setUp()
	 */
	@Override
	public void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	/**
	 * Simple test case to configure a new mail account after first startup.
	 */
	public void test01_ConfigureAccount() {

		Log.d(LOG_TAG, "started: setupAccount");
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		solo.clickOnButton("Weiter");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		enterTextCharByChar(0, ACCOUNT_ADDRESS);
		enterTextCharByChar(1, ACCOUNT_PWD);
		solo.clickOnButton("Weiter");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		solo.clickOnButton("IMAP-Konto");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		enterTextCharByChar(0, "@jouleunit.org");
		clearTextCharByChar(2);
		enterTextCharByChar(2, ACCOUNT_SERVER);
		solo.clickOnButton("Weiter");

		/* Wait to check inbox settings. */
		solo.waitForDialogToClose(10000);

		clearTextCharByChar(0);
		enterTextCharByChar(0, ACCOUNT_SERVER);
		clearTextCharByChar(1);
		enterTextCharByChar(1, "25");
		solo.clickOnButton("Weiter");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		/* Disable push mailing. */
		solo.clickOnCheckBox(0);
		solo.clickOnButton("Weiter");

		enterTextCharByChar(0, ACCOUNT_NAME);
		enterTextCharByChar(1, ACCOUNT_USER_NAME);
		solo.clickOnButton("Fertig");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: setupAccount");
	}

	/**
	 * Simple test case to update inbox to check for new mails without receiving
	 * any new mail.
	 */
	public void test02_PullNoNewMails() {
		waitForPull(TAG_NO_MAIL);
	}

	/** Simple test case to send an email without any attachment. */
	public void test03_SendMailWithoutAttachment() {

		TextView titleBar = getTitleBar();
		writeMail(TOPIC_SHORT_MAIL, getMessageText(), TAG_SHORT_MAIL);
		sendMail(titleBar, TAG_SHORT_MAIL);
	}

	/**
	 * Simple test case to update inbox to check for new mails receiving a new
	 * mail without attachment.
	 */
	public void test04_PullMailWithoutAttachment() {
		waitForPull(TAG_SHORT_MAIL);
	}

	/** Simple test case to read a mail. */
	public void test05_ReadMailWithoutAttachment() {

		Log.d(LOG_TAG, "started: readMail[" + TAG_SHORT_MAIL + "]");
		readMail(getMessageText().length());

		/* Go back to the account. */
		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: readMail[" + TAG_SHORT_MAIL + "]");
	}

	/** Simple test case to read a mail. */
	public void test06_ReadCachedMailWithoutAttachment() {

		Log.d(LOG_TAG, "started: readCachedMail[" + TAG_SHORT_MAIL + "]");
		readMail(getMessageText().length());

		/* Go back to the account. */
		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: readCachedMail[" + TAG_SHORT_MAIL + "]");
	}

	/**
	 * Simple test case to forward a mail without attachment.
	 */
	public void test07_ForwardMailWithoutAttachment() {
		forwardMail(TAG_SHORT_MAIL);
	}

	/** Simple test case to drop a mail. */
	public void test08_DropMailWithoutAttachment() {
		dropMail(TAG_SHORT_MAIL);
	}

	/** Simple test case to send an email without any attachment. */
	public void test09_SendLongMailWithoutAttachment() {
		TextView titleBar = getTitleBar();
		writeMail(TOPIC_LONG_MAIL, getMessageText() + getMessageText(),
				TAG_LONG_MAIL);
		sendMail(titleBar, TAG_LONG_MAIL);
	}

	/**
	 * Simple test case to update inbox to check for new mails receiving a new
	 * mail without attachment.
	 */
	public void test10_PullLongMailWithoutAttachment() {
		waitForPull(TAG_LONG_MAIL);
	}

	/** Simple test case to read a mail. */
	public void test11_ReadLongMailWithoutAttachment() {
		Log.d(LOG_TAG, "started: readMail[" + TAG_LONG_MAIL + "]");
		readMail(getMessageText().length() * 2);

		/* Go back to the account. */
		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: readMail[" + TAG_LONG_MAIL + "]");
	}

	/** Simple test case to read a mail. */
	public void test12_ReadCachedLongMailWithoutAttachment() {
		Log.d(LOG_TAG, "started: readCachedMail[" + TAG_LONG_MAIL + "]");
		readMail(getMessageText().length() * 2);

		/* Go back to the account. */
		solo.sendKey(KeyEvent.KEYCODE_BACK);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: readCachedMail[" + TAG_LONG_MAIL + "]");
	}

	/**
	 * Simple test case to forward a mail without attachment.
	 */
	public void test13_ForwardLongMailWithoutAttachment() {
		forwardMail(TAG_LONG_MAIL);
	}

	/** Simple test case to drop a mail. */
	public void test14_DropLongMailWithoutAttachment() {
		dropMail(TAG_LONG_MAIL);
	}

	/** Simple test case to send an email with an attachment. */
	public void test15_SendMailWithPictureAttachment() {
		TextView titleBar = getTitleBar();

		writeMail(TOPIC_PICTURE_MAIL, MSG_PICTURE_MAIL, TAG_PICTURE_MAIL);
		addAtacchment(MENU_ADD_PICTURE, TAG_PICTURE_MAIL);
		sendMail(titleBar, TAG_PICTURE_MAIL);
	}

	/**
	 * Simple test case to update inbox to check for new mails receiving a new
	 * mail with an attachment.
	 */
	public void test16_PullMailWithPictureAttachment() {
		waitForPull(TAG_PICTURE_MAIL);
	}

	/** Simple test case to read a mail. */
	public void test17_ReadMailWithPictureAttachment() {
		Log.d(LOG_TAG, "started: readMail[" + TAG_PICTURE_MAIL + "]");
		readMail(MSG_PICTURE_MAIL.length() * 2);
		openAttachment(TAG_PICTURE_MAIL, false);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: readMail[" + TAG_PICTURE_MAIL + "]");
	}

	/** Simple test case to read a mail. */
	public void test18_ReadCachedMailWithPictureAttachment() {
		Log.d(LOG_TAG, "started: readCachedMail[" + TAG_PICTURE_MAIL + "]");
		readMail(MSG_PICTURE_MAIL.length() * 2);
		openAttachment(TAG_PICTURE_MAIL, true);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: readCachedMail[" + TAG_PICTURE_MAIL + "]");
	}

	/**
	 * Simple test case to forward a mail with an attachment.
	 */
	public void test19_ForwardMailWitPictureAttachment() {
		forwardMail(TAG_PICTURE_MAIL);
	}

	/** Simple test case to drop a mail. */
	public void test20_DropMailWithPictureAttachment() {
		dropMail(TAG_PICTURE_MAIL);
	}

	/** Simple test case to send an email with an attachment. */
	public void test21_SendMailWithNoteAttachment() {
		TextView titleBar = getTitleBar();

		writeMail(TOPIC_PICTURE_MAIL, MSG_NOTE_MAIL, TAG_NOTE_MAIL);
		addAtacchment(MENU_ADD_NOTE, TAG_NOTE_MAIL);
		sendMail(titleBar, TAG_NOTE_MAIL);
	}

	/**
	 * Simple test case to update inbox to check for new mails receiving a new
	 * mail with an attachment.
	 */
	public void test22_PullMailWithNoteAttachment() {
		waitForPull(TAG_NOTE_MAIL);
	}

	/** Simple test case to read a mail. */
	public void test23_ReadMailWithNoteAttachment() {
		Log.d(LOG_TAG, "started: readMail" + TAG_NOTE_MAIL + "]");
		readMail(MSG_NOTE_MAIL.length() * 2);
		openAttachment(TAG_NOTE_MAIL, false);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: readMail" + TAG_NOTE_MAIL + "]");
	}

	/** Simple test case to read a mail. */
	public void test24_ReadCachedMailWithNoteAttachment() {
		Log.d(LOG_TAG, "started: readCachedMail" + TAG_NOTE_MAIL + "]");
		readMail(MSG_NOTE_MAIL.length() * 2);
		openAttachment(TAG_NOTE_MAIL, true);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: readCachedMail" + TAG_NOTE_MAIL + "]");
	}

	/**
	 * Simple test case to forward a mail with an attachment.
	 */
	public void test25_ForwardMailWitNoteAttachment() {
		forwardMail(TAG_NOTE_MAIL);
	}

	/** Simple test case to drop a mail. */
	public void test26_DropMailWithNoteAttachment() {
		dropMail(TAG_NOTE_MAIL);
	}

	/** Simple test case to send an email with an attachment. */
	public void test27_SendMailWithSpeechAttachment() {
		TextView titleBar = getTitleBar();

		writeMail(TOPIC_PICTURE_MAIL, MSG_SPEECH_MAIL, TAG_SPEECH_MAIL);
		addAtacchment(MENU_ADD_SPEECH_MEMO, TAG_SPEECH_MAIL);
		sendMail(titleBar, TAG_SPEECH_MAIL);
	}

	/**
	 * Simple test case to update inbox to check for new mails receiving a new
	 * mail with an attachment.
	 */
	public void test28_PullMailWithSpeechAttachment() {
		waitForPull(TAG_SPEECH_MAIL);
	}

	/** Simple test case to read a mail. */
	public void test29_ReadMailWithSpeechAttachment() {
		Log.d(LOG_TAG, "started: readMail[" + TAG_SPEECH_MAIL + "]");
		readMail(MSG_SPEECH_MAIL.length() * 2);
		openAttachment(TAG_SPEECH_MAIL, false);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: readMail[" + TAG_SPEECH_MAIL + "]");
	}

	/** Simple test case to read a mail. */
	public void test30_ReadCachedMailWithSpeechAttachment() {
		Log.d(LOG_TAG, "started: readCachedMail[" + TAG_SPEECH_MAIL + "]");
		readMail(MSG_SPEECH_MAIL.length() * 2);
		openAttachment(TAG_SPEECH_MAIL, true);
		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: readCachedMail[" + TAG_SPEECH_MAIL + "]");
	}

	/**
	 * Simple test case to forward a mail with an attachment.
	 */
	public void test31_ForwardMailWitSpeechAttachment() {
		forwardMail(TAG_SPEECH_MAIL);
	}

	/** Simple test case to drop a mail. */
	public void test32_DropMailWithSpeechAttachment() {
		dropMail(TAG_SPEECH_MAIL);
	}

	/** Simple test case to drop a mail account after testing. */
	public void test99_DropAccount() {

		Log.d(LOG_TAG, "started: dropAccount");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		solo.clickOnMenuItem("Mehr");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		solo.clickOnText("Konten");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		solo.clickLongOnText(ACCOUNT_NAME);

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		solo.clickOnText("Erweitert");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		solo.clickOnText("Konto entfernen");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		solo.clickOnButton("OK");

		solo.sleep(WAIT_TIME_AFTER_CLICK);

		Log.d(LOG_TAG, "finished: dropAccount");
	}

	/**
	 * Helper method to add an attachment to a mail during testing.
	 * 
	 * @param attachmentID
	 *            identifies the attachment to be added (e.g., Picture or Note).
	 * @param mailType
	 *            Metadata identifying the mail
	 */
	private void addAtacchment(String attachmentID, String mailType) {
		Log.d(LOG_TAG, "started: addAttachment" + mailType + "]");
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.clickOnMenuItem("Anhang hinzufügen");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		solo.clickOnText("Add " + attachmentID + " Attachment");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		Log.d(LOG_TAG, "finished: addAttachment" + mailType + "]");
	}

	/**
	 * Helper method to drop a mail during testing.
	 * 
	 * @param mailType
	 *            Metadata identifying the mail
	 */
	private void dropMail(String mailType) {
		Log.d(LOG_TAG, "started: dropMail[" + mailType + "]");

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		solo.clickLongInList(0);

		solo.sleep(WAIT_TIME_AFTER_CLICK);
		solo.clickOnText("Löschen");

		solo.sleep(WAIT_TIME_AFTER_CLICK);

		Log.d(LOG_TAG, "finished: dropMail[" + mailType + "]");
	}

	/**
	 * Helper method that forwards a mail.
	 * 
	 * @param mailType
	 *            Metadata identifying the mail
	 */
	private void forwardMail(String mailType) {
		TextView titleBar = getTitleBar();

		Log.d(LOG_TAG, "started: forwardMail[" + mailType + "]");
		solo.clickLongInList(1);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		solo.clickOnText("Weiterleiten");
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		enterTextCharByChar(0, FORWARD_ADDRESS);
		enterTextCharByChar(2, FORWARD_MSG);

		/* Send the message. */
		solo.clickOnMenuItem("Senden");

		/* Get the current status bar text. */
		String status = titleBar.getText().toString();

		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* Wait until the status bar has its old title (send finished) */
		while (!titleBar.getText().equals(status))
			solo.sleep(100);
		// end while.

		Log.d(LOG_TAG, "finished: forwardMail[" + mailType + "]");
	}

	/**
	 * Helper method that opens an attachment.
	 * 
	 * @param mailType
	 *            Metadata identifying the mail
	 * @param cached
	 *            If <code>true</code> the attachment has been cached before.
	 */
	private void openAttachment(String mailType, boolean cached) {
		Log.d(LOG_TAG, "started: open" + (cached ? "Cached" : "")
				+ "Attachment[" + mailType + "]");

		/* Open the attachment. */
		solo.clickOnButton("Öffnen");
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* Wait until the attachment has been downloaded and shown. */
		List<ProgressBar> bars = solo.getCurrentProgressBars();

		if (bars.size() > 0) {
			ProgressBar bar = bars.get(0);
			while (bar.isShown() && bar.getProgress() < bar.getMax())
				solo.sleep(100);
			// end while.
		}
		// no else.

		Log.d(LOG_TAG, "finished: open" + (cached ? "Cached" : "")
				+ "Attachment[" + mailType + "]");
	}

	/**
	 * Helper method to read a mail (duration depending on message length).
	 * 
	 * @param messageLength
	 *            The length of the message to be read.
	 */
	private void readMail(int messageLength) {
		/* Select the first message from the list. */
		solo.clickInList(1);

		/* Wait to read the message. */
		solo.sleep(messageLength * 40);
	}

	/**
	 * Helper method to send a mail
	 * 
	 * @param titleBar
	 *            The {@link TextView} representing the bar containing the
	 *            status info regarding the sent mail.
	 * @param mailType
	 *            Metadata identifying the mail
	 */
	private void sendMail(TextView titleBar, String mailType) {

		Log.d(LOG_TAG, "started: sendMail[" + mailType + "]");

		/* Send the message. */
		solo.clickOnMenuItem("Senden");

		/* Get the current status bar text. */
		String status = titleBar.getText().toString();

		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* Wait until the status bar has its old title (send finished) */
		while (!titleBar.getText().equals(status))
			solo.sleep(100);
		// end while.

		Log.d(LOG_TAG, "finished: sendMail[" + mailType + "]");
	}

	/**
	 * Helper method to compose a mail. Use {@link K9MailTests#sendMail()} to
	 * send the mail afterwards.
	 * 
	 * @param title
	 *            The title of the mail.
	 * @param content
	 *            The message content of the mail.
	 * @param mailType
	 *            Metadata identifying the mail
	 */
	private void writeMail(String title, String content, String mailType) {

		Log.d(LOG_TAG, "started: composeMail[" + mailType + "]");

		/* Click on the first button in the menu to write an email. */
		solo.pressMenuItem(0);
		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* Enter the message */
		enterTextCharByChar(0, ACCOUNT_ADDRESS);
		enterTextWordByWord(1, title);
		enterTextWordByWord(2, content);

		Log.d(LOG_TAG, "finished: composeMail[" + mailType + "]");
	}

	/**
	 * Helper method to wait for a mail pull.
	 * 
	 * @param mailType
	 *            Metadata identifying the mail
	 */
	private void waitForPull(String mailType) {

		Log.d(LOG_TAG, "started: pullMails[" + mailType + "]");

		/* Opening the account is sufficient to check for new mails. */
		int elemNo = solo.getCurrentListViews().get(0).getChildCount();
		LinearLayout refreshView = (LinearLayout) solo.getCurrentListViews()
				.get(0).getChildAt(elemNo - 1);
		solo.clickOnView(refreshView);
		ProgressBar progress = (ProgressBar) refreshView.getChildAt(0);

		solo.sleep(WAIT_TIME_AFTER_CLICK);

		/* Wait until pull is complete. */
		while (progress.isShown())
			solo.sleep(100);
		// end while.

		solo.sleep(WAIT_TIME_AFTER_CLICK);

		Log.d(LOG_TAG, "finished: pullMails[" + mailType + "]");
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
		for (int counter = 0; counter < text.getText().length(); counter++)
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

	/**
	 * Helper method to add text into a {@link TextView} word by word.
	 * 
	 * @param index
	 *            The index of the {@link TextView} to enter the text.
	 * @param text
	 *            The text to be entered.
	 */
	protected void enterTextWordByWord(int index, String text) {
		/* To avoid side effects. */
		String copy = new String(text);

		for (String word : copy.split(" ")) {
			solo.enterText(index, word);
			solo.enterText(index, " ");
		}
		// end while.
	}

	/**
	 * Helper method returning a {@link String} that can be sent as an email.
	 * 
	 * @return A {@link String} containing a message (lorem ipsum).
	 */
	protected String getMessageText() {

		StringBuffer buffer = new StringBuffer();
		buffer.append("Text sent from test case for testing purpose.\n\n");
		buffer.append("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed");
		buffer.append(" diam nonumy eirmod tempor invidunt ut labore et dolore magna");
		buffer.append(" aliquyam erat, sed diam voluptua. At vero eos et accusam et");
		buffer.append(" justo duo dolores et ea rebum. Stet clita kasd gubergren, no");
		buffer.append(" sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem");
		buffer.append(" ipsum dolor sit amet, consetetur sadipscing elitr, sed diam");
		buffer.append(" nonumy eirmod tempor invidunt ut labore et dolore magna");

		return buffer.toString();
	}

	/**
	 * Helper method that tries to find the title bar {@link TextView} of the
	 * current visible activity.
	 */
	protected TextView getTitleBar() {
		TextView titleBar = null;

		for (View view : solo.getViews()) {
			if (view instanceof TextView) {
				CharSequence text = ((TextView) view).getText();
				if (text.toString().contains("Posteingang")
						|| text.equals("K9 Mail"))
					titleBar = ((TextView) view);
				break;
				// no else.
			}
			// no else.
		}
		// end for.

		assertNotNull(titleBar);
		return titleBar;
	}
}
