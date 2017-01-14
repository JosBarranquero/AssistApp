package com.bitbits.assistapp.presenters;

import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.interfaces.IMessage;
import com.bitbits.assistapp.models.Message;

/**
 * Presenter for Messaging_Fragment
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          25/12/16
 */
public class Messaging_Presenter implements IMessage.Presenter {
    IMessage.View mView;

    public Messaging_Presenter(IMessage.View view) {
        mView = view;
    }

    /**
     * Method which saves a message into the repository
     * @param message
     * @see Message
     */
    public void sendMessage(Message message) {
        Repository.getInstance().writeMessage(message);
        mView.message();
    }
}
