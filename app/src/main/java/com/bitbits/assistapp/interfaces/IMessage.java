package com.bitbits.assistapp.interfaces;

import com.bitbits.assistapp.models.Message;

/**
 * Interface to be implemented by Messaging_Fragment and Messaging_Presenter
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          25/12/16
 * @see com.bitbits.assistapp.Messaging_Fragment
 * @see com.bitbits.assistapp.presenters.Messaging_Presenter
 */
public interface IMessage {
    interface View {
        void message();
    }

    interface Presenter {
        void sendMessage(Message message);
    }
}
