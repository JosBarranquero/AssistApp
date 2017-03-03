package com.bitbits.assistapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.bitbits.assistapp.R;

/**
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */

public class Messaging_CursorAdapter extends CursorAdapter {
    Context context;

    public Messaging_CursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = layoutInflater.inflate(R.layout.item_message, parent, false);

        MessageHolder messageHolder = new MessageHolder();

        messageHolder.txvSender = (TextView)rootView.findViewById(R.id.txvSender);
        messageHolder.txvReceiver = (TextView)rootView.findViewById(R.id.txvReceiver);
        messageHolder.txvContent = (TextView)rootView.findViewById(R.id.txvContent);

        rootView.setTag(messageHolder);

        return rootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MessageHolder messageHolder = (MessageHolder)view.getTag();

        messageHolder.txvContent.setText(context.getResources().getString(R.string.content) + ": " + cursor.getString(3));
        messageHolder.txvSender.setText(String.valueOf(context.getResources().getString(R.string.sender) + ": " + cursor.getInt(1)));
        messageHolder.txvReceiver.setText(String.valueOf(context.getResources().getString(R.string.receiver) + ": " + cursor.getInt(2)));
    }

    class MessageHolder {
        TextView txvSender;
        TextView txvReceiver;
        TextView txvContent;
    }
}
