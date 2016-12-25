package com.bitbits.assistapp.interfaces;

import com.bitbits.assistapp.models.Message;

/**
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          25/12/16
 */

public interface IMessage {
    interface View {
        void message();
    }

    interface Presenter {
        void sendMessage(Message message);
    }
}
