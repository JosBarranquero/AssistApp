package com.bitbits.assistapp.presenters;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;

import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.database.DatabaseContract;
import com.bitbits.assistapp.database.DatabaseHelper;
import com.bitbits.assistapp.interfaces.IMessage;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.provider.ProviderContract;

/**
 * Presenter for Messaging_Fragment
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          25/12/16
 */
public class Messaging_Presenter implements IMessage.Presenter, LoaderManager.LoaderCallbacks<Cursor> {
    public static final int MESSAGES = 1;

    IMessage.View mView;
    Context context;

    public Messaging_Presenter(IMessage.View view) {
        mView = view;
        this.context = mView.getContext();
    }

    /**
     * Method which saves a message into the repository
     * @param message
     * @see Message
     */
    public void sendMessage(Message message) {
        //Repository.getInstance().writeMessage(message);
        SQLiteDatabase database = DatabaseHelper.getInstance().openDatabase();
        database.execSQL(String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (%s, %s, '%s', %s, '%s')",
                DatabaseContract.MessageEntry.TABLE_NAME,
                DatabaseContract.MessageEntry.COLUMN_RECEIVER,
                DatabaseContract.MessageEntry.COLUMN_SENDER,
                DatabaseContract.MessageEntry.COLUMN_CONTENT,
                DatabaseContract.MessageEntry.COLUMN_IMAGE,
                DatabaseContract.MessageEntry.COLUMN_DATE,
                message.getReceiver().getId(),
                message.getSender().getId(),
                message.getContent(),
                message.getImg(),
                "2017-02-01"));
        DatabaseHelper.getInstance().close();
    }

    @Override
    public void getAllMessages(CursorAdapter adapter) {
        ((Activity)context).getLoaderManager().initLoader(MESSAGES, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader ccl;

        switch (id){
            case MESSAGES:
                ccl = new CursorLoader(context, ProviderContract.Message.CONTENT_URI, ProviderContract.Message.PROJECTION, null, null, null);
                break;
            default:
                ccl=null;
        }

        return ccl;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.setNotificationUri(context.getContentResolver(), ProviderContract.Message.CONTENT_URI);
        mView.setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mView.setCursor(null);
    }
}
