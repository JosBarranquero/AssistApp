package com.bitbits.assistapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter which manages the contacts shown on the conversation list
 * @author José Antonio Barranquero Fernández
 * @version 1.1
 */
public class ConversationList_Adapter extends ArrayAdapter<User> {
    Context context;
    List<User> contacts = new ArrayList<>();

    public ConversationList_Adapter(Context context) {
        super(context, R.layout.item_conversation);
        this.context = context;
        for (User account : Repository.getInstance().getUser()) {
            if (!account.equals(Repository.getInstance().getCurrentUser())) {
                contacts.add(account);
            }
        }
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ConversationHolder conversationHolder;

        if (item == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            item = layoutInflater.inflate(R.layout.item_conversation, null);
            conversationHolder = new ConversationHolder();

            conversationHolder.contact_image = (ImageView) item.findViewById(R.id.imgContact);
            conversationHolder.txvName = (TextView) item.findViewById(R.id.txvContactName);

            item.setTag(conversationHolder);
        } else {
            conversationHolder = (ConversationHolder) item.getTag();
        }

        conversationHolder.contact_image.setImageResource(R.drawable.logo);
        conversationHolder.txvName.setText(contacts.get(position).getName());

        return item;
    }

    class ConversationHolder {
        ImageView contact_image;
        TextView txvName;
    }
}
