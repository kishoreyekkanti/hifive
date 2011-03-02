package com.nfc.activities;

import android.app.Activity;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: ykkuumar
 * Date: 1/3/11
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class BusinessCardInputActivity extends Activity {

    static final String TAG = "BusinessCardInputActivity";
   /**
     * Smart Poster containing a URL and Text.
     */
    public static final byte[] BUSINESS_CARD_DETAIL =
        new byte[] {(byte) 0xd1, (byte) 0x01, (byte) 0x1c, (byte) 0x54, (byte) 0x02, (byte) 0x65,
            (byte) 0x6e, (byte) 0x53, (byte) 0x6f, (byte) 0x6d, (byte) 0x65, (byte) 0x20,
            (byte) 0x72, (byte) 0x61, (byte) 0x6e, (byte) 0x64, (byte) 0x6f, (byte) 0x6d,
            (byte) 0x20, (byte) 0x65, (byte) 0x6e, (byte) 0x67, (byte) 0x6c, (byte) 0x69,
            (byte) 0x73, (byte) 0x68, (byte) 0x20, (byte) 0x74, (byte) 0x65, (byte) 0x78,
            (byte) 0x74, (byte) 0x2e};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = new Intent(NfcAdapter.ACTION_TAG_DISCOVERED);
        try {
            intent.putExtra(NfcAdapter.EXTRA_NDEF_MESSAGES,new NdefMessage[] {new NdefMessage(BUSINESS_CARD_DETAIL)});
        } catch (FormatException e) {
            Log.e(TAG, e.toString());
        }
        startActivity((Intent.createChooser(intent,"Select Activity")));
    }
}