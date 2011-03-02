package com.nfc.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;
import com.nfc.utils.NdefMessageParser;
import com.nfc.utils.ParsedNdefRecord;

import java.util.List;

public class TagReadActivity extends Activity {
    static final String TAG = "TagReadActivity";
    private static final String SMS_CONTENT_PROVIDER = "content://sms/sent";
    private static final String BODY = "body";
    private static final String ADDRESS = "address";
    StringBuilder detailsRead = new StringBuilder();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        resolveIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }

    void resolveIntent(Intent intent) {
        // Parse the intent
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            // When a tag is discovered we send it to the service to be save. We
            // include a PendingIntent for the service to call back onto. This
            // will cause this activity to be restarted with onNewIntent(). At
            // that time we read it from the database and view it.
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[]{};
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
                msgs = new NdefMessage[]{msg};
            }
            List<ParsedNdefRecord> records = NdefMessageParser.parse(msgs[0]);
            for (int i = 0; i < records.size(); i++) {
                Log.e(TAG, ((ParsedNdefRecord) records.get(i)).getText());
                detailsRead.append(((ParsedNdefRecord) records.get(i)).getText());
            }
            Toast toast = Toast.makeText(this, detailsRead.toString(), Toast.LENGTH_LONG);
            toast.show();
            ContentValues values = new ContentValues();
            values.put(ADDRESS, "hifive");
            values.put(BODY, detailsRead.toString());
            getContentResolver().insert(Uri.parse(SMS_CONTENT_PROVIDER), values);

        } else {
            Log.e(TAG, "Unknown intent " + intent);
            Toast toast = Toast.makeText(this, "Unable to read the contents", Toast.LENGTH_LONG);
            toast.show();
        }

        finish();
        return;
    }

}
