package com.nfc.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by IntelliJ IDEA.
 * User: ykkuumar
 * Date: 1/3/11
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class SaveBusinessCardActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}