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
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Adapter which manages the contacts shown on the ConversationList_Fragment and the patients show in the PatientList_Fragment
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.2
 */
public class UsersList_Adapter extends ArrayAdapter<User> {
    private Context context;
    private boolean showUnread;

    public UsersList_Adapter(Context context, boolean showUnread) {
        super(context, R.layout.item_userlist, new ArrayList<>(Repository.getInstance().getUsers()));
        this.context = context;
        this.showUnread = showUnread;
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

            conversationHolder.contact_image = (CircleImageView) item.findViewById(R.id.imgContact);
            conversationHolder.txvName = (TextView) item.findViewById(R.id.txvContactName);
            conversationHolder.imgUnread = (ImageView) item.findViewById(R.id.imgUnread);

            item.setTag(conversationHolder);
        } else {
            conversationHolder = (UserHolder) item.getTag();
        }

        User user = getItem(position);

        if (user != null) {
            Picasso.with(context)
                    .load(AssistApp_Application.URL + user.getImg())
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.user)
                    .noFade()
                    .into(conversationHolder.contact_image);

            conversationHolder.txvName.setText(user.getFormattedName());

            if (showUnread) {
                for (Message message : Repository.getInstance().getUnread()) {
                    if (message.getSender() == user.getId()) {
                        conversationHolder.imgUnread.setVisibility(View.VISIBLE);
                    } else {
                        conversationHolder.imgUnread.setVisibility(View.GONE);
                    }
                }
            }
        }
        return item;
    }

    public void sortUsers(boolean byName) {
        if (byName)
            sort(User.NAME_COMPARATOR);
        else
            sort(User.SURNAME_COMPARATOR);
    }

    /**
     * Method which searches for a user
     *
     * @param query Search query
     */
    public void searchUser(String query) {
        String myQuery = query.toLowerCase(Locale.getDefault());
        myQuery = replaceCharacters(myQuery);
        clear();
        if (myQuery.length() == 0) {
            addAll(Repository.getInstance().getUsers());
        } else {
            for (User user : Repository.getInstance().getUsers()) {
                if (user.getSurname().toLowerCase(Locale.getDefault()).startsWith(myQuery) ||
                        user.getIdDoc().toLowerCase(Locale.getDefault()).startsWith(myQuery) ||
                        user.getFormattedName().toLowerCase(Locale.getDefault()).startsWith(myQuery)) {
                    add(user);
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Method which
     *
     * @param query
     * @return
     */
    private String replaceCharacters(String query) {
        return query.replace('á', 'a').replace('é', 'e').replace('í', 'i').replace('ó', 'o').replace('ú', 'u');
    }

    class UserHolder {
        CircleImageView contact_image;
        TextView txvName;
        ImageView imgUnread;
    }
}
