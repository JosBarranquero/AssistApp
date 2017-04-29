package com.bitbits.assistapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.models.Message;

import java.util.ArrayList;

/**
 * Adapter which manages the messages listed in Messaging_Fragment
 *
 * @author José Antonio Barranquero Fernández
 * @version 2.0
 * @see Message
 */
public class Messaging_Adapter extends RecyclerView.Adapter<Messaging_Adapter.Messaging_Holder> {
    private ArrayList<Message> messages;
    private Context context;

    public Messaging_Adapter(Context context) {
        this.context = context;
        this.messages = Repository.getInstance().getMessages();
    }

    @Override
    public Messaging_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_message_card, parent, false);

        return new Messaging_Holder(v);
    }

    @Override
    public void onBindViewHolder(Messaging_Holder holder, int position) {
        Message myMessage = messages.get(position);

        holder.txvContent.setText(myMessage.getContent());
        holder.txvDate.setText(myMessage.getFormattedDate());

        LinearLayout.LayoutParams paramsCardView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams paramsDatetime = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (myMessage.getSender() == Repository.getInstance().getCurrentUser().getId()) {
            paramsCardView.gravity = Gravity.END;
            paramsCardView.leftMargin = 200;
            paramsDatetime.gravity = Gravity.END;
            holder.cvMessage.setCardBackgroundColor(context.getResources().getColor(R.color.colorOwnMessage));
        } else {
            paramsCardView.gravity = Gravity.START;
            paramsCardView.rightMargin = 200;
            paramsDatetime.gravity = Gravity.START;
            holder.cvMessage.setCardBackgroundColor(context.getResources().getColor(R.color.colorOtherMessage));
        }

        holder.cvMessage.setLayoutParams(paramsCardView);
        holder.txvDate.setLayoutParams(paramsDatetime);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    static class Messaging_Holder extends RecyclerView.ViewHolder {
        CardView cvMessage;
        TextView txvDate;
        TextView txvContent;

        public Messaging_Holder(View itemView) {
            super(itemView);

            cvMessage = (CardView) itemView.findViewById(R.id.cvMessage);
            txvDate = (TextView) itemView.findViewById(R.id.txvDate);
            txvContent = (TextView) itemView.findViewById(R.id.txvContent);
        }
    }
}
