package com.nfc.utils;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ykkuumar
 * Date: 1/3/11
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class NdefMessageParser {
       // Utility class
    private NdefMessageParser() {

    }

    /** Parse an NdefMessage */
    public static List<ParsedNdefRecord> parse(NdefMessage message) {
        return getRecords(message.getRecords());
    }

    public static List<ParsedNdefRecord> getRecords(NdefRecord[] records) {
        List<ParsedNdefRecord> elements = new ArrayList<ParsedNdefRecord>();
        for (NdefRecord record : records) {
          if (TextRecord.isText(record)) {
                elements.add(TextRecord.parse(record));
            }
        }
        return elements;
    }
}
