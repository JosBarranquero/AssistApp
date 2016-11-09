package com.bitbits.assistapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.models.User;

import java.util.List;


/**
 * Adapter which manages the contacts shown on the conversation list
 */
public class Conversation_Adapter extends RecyclerView.Adapter<Conversation_Adapter.ConversationViewHolder> {
    private List<User> contacts;
    private Context context;

    public Conversation_Adapter(Context c) {
        this.context = c;

    }

    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ConversationViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView txvName;

        public ConversationViewHolder(View itemView) {
            super(itemView);
            product_image = (ImageView)itemView.findViewById(R.id.imgContact);
            txvName = (TextView)itemView.findViewById(R.id.txvContactName);
        }
    }
}
