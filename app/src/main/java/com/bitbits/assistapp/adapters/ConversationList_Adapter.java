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

import com.bitbits.assistapp.AssistApp_Application;
import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter which manages the contacts shown on the conversation list
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.1
 */
public class ConversationList_Adapter extends ArrayAdapter<User> {
    private Context context;

    public ConversationList_Adapter(Context context) {
        super(context, R.layout.item_conversation, Repository.getInstance().getUsers());
        this.context = context;
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

        User currentUser = getItem(position);

        Picasso.with(context)
                .load(AssistApp_Application.URL + currentUser.getImg())
                .error(R.drawable.logo)
                .into(conversationHolder.contact_image);
        conversationHolder.txvName.setText(currentUser.getFormattedName());

        return item;
    }

    class ConversationHolder {
        ImageView contact_image;
        TextView txvName;
    }
}
