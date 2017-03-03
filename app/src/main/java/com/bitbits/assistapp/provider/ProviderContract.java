package com.bitbits.assistapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.bitbits.assistapp.database.DatabaseContract;

/**
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */

public final class ProviderContract {
    public static final String AUTHORITY = "com.bitbits.assistapp";
    public static final Uri AUTHORITY_URI = Uri.parse("content://"+AUTHORITY);

    private ProviderContract() {

    }

    public static class Message implements BaseColumns {
        public static final String CONTENT_PATH = "message";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_PATH);
        public static final String[] PROJECTION = {_ID, DatabaseContract.MessageEntry.COLUMN_SENDER, DatabaseContract.MessageEntry.COLUMN_RECEIVER, DatabaseContract.MessageEntry.COLUMN_CONTENT, DatabaseContract.MessageEntry.COLUMN_IMAGE, DatabaseContract.MessageEntry.COLUMN_DATE};
    }
}
