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
 * Adapter which manages the messages listed in Messaging_Fragment
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 * @see Message
 */
public class Messaging_Adapter extends ArrayAdapter<Message> {
    private Context context;

    public Messaging_Adapter(Context context) {
        super(context, R.layout.item_message, Repository.getInstance().getMessages());
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        MessageHolder messageHolder;

        if (item == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());

            item = layoutInflater.inflate(R.layout.item_message, null);

            messageHolder = new MessageHolder();

            messageHolder.txvDate = (TextView) item.findViewById(R.id.txvDate);
            messageHolder.txvContent = (TextView) item.findViewById(R.id.txvContent);

            item.setTag(messageHolder);
        } else {
            messageHolder = (MessageHolder) item.getTag();
        }

        messageHolder.txvDate.setText(getItem(position).getDate());
        messageHolder.txvContent.setText(getItem(position).getContent());

        if (getItem(position).getSender() == Repository.getInstance().getCurrentUser().getId()) {
            item.setBackground(context.getResources().getDrawable(R.drawable.shape_ownmsg));
        }

        return item;
    }

    class MessageHolder {
        TextView txvDate;
        TextView txvContent;
    }
}
