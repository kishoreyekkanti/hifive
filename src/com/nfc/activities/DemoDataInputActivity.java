package com.nfc.activities;

import android.app.Activity;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;


public class DemoDataInputActivity extends Activity {

    static final String TAG = "DemoDataInputActivity";

    public static final byte[] BUSINESS_CARD_DETAIL =
            new byte[]{(byte) 0xd1, (byte) 0x01, (byte) 0x1c, (byte) 0x54, (byte) 0x02, (byte) 0x65,
                    (byte) 0x6e, (byte) 0x53, (byte) 0x6f, (byte) 0x6d, (byte) 0x65, (byte) 0x20,
                    (byte) 0x72, (byte) 0x61, (byte) 0x6e, (byte) 0x64, (byte) 0x6f, (byte) 0x6d,
                    (byte) 0x20, (byte) 0x65, (byte) 0x6e, (byte) 0x67, (byte) 0x6c, (byte) 0x69,
                    (byte) 0x73, (byte) 0x68, (byte) 0x20, (byte) 0x74, (byte) 0x65, (byte) 0x78,
                    (byte) 0x74, (byte) 0x2e};
    public static final byte[] HEADER = new
            byte[]{(byte) 0xd1, (byte) 0x01, (byte) 0xff, (byte) 0x54, (byte) 0x02, (byte) 0x65, (byte) 0x6e};

    public static final byte[] FOOTER =
            new byte[]{(byte) 0x65, (byte) 0x78, (byte) 0x74, (byte) 0x2e};

    public static final String TEXT =
            "Y Kishore Kumar,ThoughtWorks Technologies India Pvt. Ltd. R.R.Tower 5, Guindy, Chennai-600032" +
                    " Cell: 9500037878" +
                    " Home: 04443838501" +
                    " Web: http://kishoreyekkanti.blogspot.com" +
                    " github: http://github.com/kishoreyekkanti" +
                    " twitter: kishoreyekkanti  ";

    public static final byte[] DATA = TEXT.getBytes();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = new Intent(NfcAdapter.ACTION_TAG_DISCOVERED);
        try {
            byte[] DATA_TO_SEND = new byte[1 + HEADER.length + DATA.length + FOOTER.length];
            System.arraycopy(HEADER, 0, DATA_TO_SEND, 0, HEADER.length);
            System.arraycopy(DATA, 0, DATA_TO_SEND, 1 + HEADER.length, DATA.length);
            System.arraycopy(FOOTER, 0, DATA_TO_SEND, 1 + DATA.length, FOOTER.length);

            intent.putExtra(NfcAdapter.EXTRA_NDEF_MESSAGES, new NdefMessage[]{new NdefMessage(DATA_TO_SEND)});
        } catch (FormatException e) {
            Log.e(TAG, e.toString());
        }
        startActivity((Intent.createChooser(intent, "Select Activity")));
    }
}