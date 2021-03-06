package com.nfc.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: ykkuumar
 * Date: 1/3/11
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagWriteActivity extends Activity {
    private static final String BUSINESS_CARD = "businessCard";
    private static final String BUSINESS_CARD_NOT_SET = "business card not set";
    private static final String TAG = "TagWriteActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String businessCard = preferences.getString(BUSINESS_CARD, BUSINESS_CARD_NOT_SET);
        Log.i(TAG, "business card::" + businessCard);
        // get the tag from the Intent
        Tag mytag = (Tag) getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if (mytag == null) {
            Toast toast = Toast.makeText(this, "Unable to write to the tag. Tag not found", Toast.LENGTH_LONG);
            toast.show();
            finishActivity();
            return;
        }

        // get the Ndef (TagTechnology) from the tag
        Ndef ndefref = Ndef.get(mytag);
        writeTag(ndefref, businessCard);
        finishActivity();
    }

    private void finishActivity() {
        finish();
        return;
    }

    private void writeTag(final Ndef ndefref, final String text) {
        if (ndefref == null || text == null || !ndefref.isWritable()) {
            return;
        }
        (new Thread() {
            public void run() {
                try {
                    int count = 0;
                    // connect I/O
                    ndefref.connect();
                    // check for connection
                    while (!ndefref.isConnected()) {
                        if (count > 6000) {
                            throw new Exception("Unable to connect to tag");
                        }
                        count++;
                        sleep(10);
                    }

                    // construct the NdefMessage
                    NdefRecord[] rec = new NdefRecord[1];
                    rec[0] = newTextRecord(text);
                    NdefMessage msg = new NdefMessage(rec);

                    // write the NdefMessage
                    ndefref.writeNdefMessage(msg);

                    // close I/O
                    ndefref.close();

                } catch (Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            }
        }).start();
    }

    // create a new NdefRecord
    private NdefRecord newTextRecord(String text) {
        byte[] langBytes = Locale.ENGLISH.getLanguage().getBytes(Charset.forName("US-ASCII"));

        byte[] textBytes = text.getBytes(Charset.forName("UTF-8"));

        char status = (char) (langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }
}