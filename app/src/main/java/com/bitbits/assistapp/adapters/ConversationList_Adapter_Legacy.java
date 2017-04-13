package com.bitbits.assistapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.models.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter which manages the contacts shown on the conversation list
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class ConversationList_Adapter_Legacy extends RecyclerView.Adapter<ConversationList_Adapter_Legacy.ConversationViewHolder> {
    private List<User> contacts = new ArrayList<>();
    private Context context;

    public ConversationList_Adapter_Legacy(Context c) {
        this.context = c;
        for (User account : Repository.getInstance().getUsers()) {
            if (!account.equals(Repository.getInstance().getCurrentUser())) {
                contacts.add(account);
            }
        }
    }

    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, null);
        return new ConversationViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder holder, int position) {
        holder.contact_image.setImageResource(R.drawable.logo);
        holder.txvName.setText(contacts.get(position).getName() + " " + contacts.get(position).getSurname());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ConversationViewHolder extends RecyclerView.ViewHolder {
        ImageView contact_image;
        TextView txvName;

        public ConversationViewHolder(View itemView) {
            super(itemView);
            contact_image = (ImageView) itemView.findViewById(R.id.imgContact);
            txvName = (TextView) itemView.findViewById(R.id.txvContactName);
        }
    }
}

