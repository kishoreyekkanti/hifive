package com.nfc.activities;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by IntelliJ IDEA.
 * User: ykkuumar
 * Date: 1/3/11
 * Time: 7:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActivityChooser extends Activity {
    public static final String NFC_ENABLED = "NFC is enabled";
    public static final String NFC_DISABLED = "You have disabled your NFC. Enable it in your settings";
    Toast toast;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null || !nfcAdapter.isEnabled()) {
            toast = Toast.makeText(this, NFC_DISABLED, Toast.LENGTH_LONG);
        } else {
            toast = Toast.makeText(this, NFC_ENABLED, Toast.LENGTH_LONG);
        }
        toast.show();
        Button demo = (Button) findViewById(R.id.demo);
        demo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent fakeTagsIntent = new Intent(ActivityChooser.this, DemoDataInputActivity.class);
                startActivity(fakeTagsIntent);
            }
        });

    }
}