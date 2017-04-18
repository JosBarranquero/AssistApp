package com.bitbits.assistapp.interfaces;

import android.content.Context;

/**
 * Interface to be implemented by ConversationList_Presenter and ConversationList_Fragment
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */
public interface IConversation {
    interface View {
        void setData();
        Context getContext();
    }

    interface Presenter {
        void getUsers();
    }
}
