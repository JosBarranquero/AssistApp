package com.bitbits.assistapp.interfaces;

import android.content.Context;

import com.bitbits.assistapp.fragments.Messaging_Fragment;
import com.bitbits.assistapp.models.Message;

/**
 * Interface to be implemented by Messaging_Fragment and Messaging_Presenter
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          25/12/16
 * @see Messaging_Fragment
 * @see com.bitbits.assistapp.presenters.Messaging_Presenter
 */
public interface IMessage {
    interface View {
        void message();
        Context getContext();
        void setData();
    }

    interface Presenter {
        void getMessages(int receiver, int sender);
        void sendMessage(Message message);
    }
}
