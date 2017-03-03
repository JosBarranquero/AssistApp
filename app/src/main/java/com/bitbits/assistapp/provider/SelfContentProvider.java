package com.bitbits.assistapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.bitbits.assistapp.database.DatabaseContract;
import com.bitbits.assistapp.database.DatabaseHelper;

/**
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */

public class SelfContentProvider extends ContentProvider {
    private SQLiteDatabase sql;

    public static final int MESSAGE = 1;
    public static final int MESSAGE_ID = 2;

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(ProviderContract.AUTHORITY, ProviderContract.Message.CONTENT_PATH, MESSAGE);
        matcher.addURI(ProviderContract.AUTHORITY, ProviderContract.Message.CONTENT_PATH + "/#", MESSAGE_ID);
    }

    @Override
    public boolean onCreate() {
        sql = DatabaseHelper.getInstance().openDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (matcher.match(uri)) {
            case MESSAGE:
                builder.setTables(DatabaseContract.MessageEntry.TABLE_NAME);

                if (!TextUtils.isEmpty(sortOrder))
                    sortOrder = DatabaseContract.MessageEntry.DEFAULT_SORT;
                break;
        }
        String sqlQuery = builder.buildQuery(projection, selection, null, null, sortOrder, null);
        Log.wtf("CONSULTA", sqlQuery);
        Cursor cursor = builder.query(sql, projection, selection, selectionArgs, null,null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
