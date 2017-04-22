package com.bitbits.assistapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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

/**
 * Adapter which manages the contacts shown on the ConversationList_Fragment and the patients show in the PatientList_Fragment
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.2
 */
public class UsersList_Adapter extends ArrayAdapter<User> {
    private Context context;

    public UsersList_Adapter(Context context) {
        super(context, R.layout.item_userlist, Repository.getInstance().getUsers());
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        UserHolder conversationHolder;

        if (item == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            item = layoutInflater.inflate(R.layout.item_userlist, null);
            conversationHolder = new UserHolder();

            conversationHolder.contact_image = (ImageView) item.findViewById(R.id.imgContact);
            conversationHolder.txvName = (TextView) item.findViewById(R.id.txvContactName);

            item.setTag(conversationHolder);
        } else {
            conversationHolder = (UserHolder) item.getTag();
        }

        User currentUser = getItem(position);

        Picasso.with(context)
                .load(AssistApp_Application.URL + currentUser.getImg())
                .error(R.drawable.logo)
                .placeholder(R.drawable.user)
                .into(conversationHolder.contact_image);
        conversationHolder.txvName.setText(currentUser.getFormattedName());

        return item;
    }

    class UserHolder {
        ImageView contact_image;
        TextView txvName;
    }
}
