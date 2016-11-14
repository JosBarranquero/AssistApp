package com.bitbits.assistapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.models.Message;

/**
 * Adapter which manages the messages listed in Conversation_Activity
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */

public class Conversation_Adapter extends ArrayAdapter<Message> {
    private Context context;

    public Conversation_Adapter(Context context) {
        super(context, R.layout.item_message, Repository.getInstance().getMessages());
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        MessageHolder messageHolder;

        if (item == null) {
            // 1. Create Inflater object and initialise it from the Context casted to LayoutInflater
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            // LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 2. Inflate the view. Create into system memory the View object which contains the widgets defined in the XML file
            // If the second parameter is null, it will create a RelativeLayout inside the ListView. View hierarchy
            item = layoutInflater.inflate(R.layout.item_message, null);

            messageHolder = new MessageHolder();


            // 3. Set variables to Widget with findViewById()

            messageHolder.txvSender = (TextView) item.findViewById(R.id.txvSender);
            messageHolder.txvReceiptant = (TextView) item.findViewById(R.id.txvReceiptant);
            messageHolder.txvContent = (TextView) item.findViewById(R.id.txvContent);

            item.setTag(messageHolder);
        } else {
            messageHolder = (MessageHolder) item.getTag();
        }

        // 4. Assign data to Adapter

        messageHolder.txvSender.setText(context.getResources().getString(R.string.sender) + ": " + getItem(position).getSender().getName());
        messageHolder.txvReceiptant.setText(context.getResources().getString(R.string.receiptant) + ": " + getItem(position).getReceiptant().getName());
        messageHolder.txvContent.setText(context.getResources().getString(R.string.content) + ": " + getItem(position).getContent());

        return item;
    }

    // Internal class that contains the XML file Widgets
    class MessageHolder {
        TextView txvSender;
        TextView txvReceiptant;
        TextView txvContent;
    }
}
