package com.bitbits.assistapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity which will show the Fragments that compose this Application
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Home_Activity extends AppCompatActivity implements ConversationList_Fragment.ListConversationListener {
    Conversation_Fragment mConversationFragment;
    ConversationList_Fragment mConversationListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mConversationListFragment = new ConversationList_Fragment();
        getFragmentManager().beginTransaction().add(R.id.framehome, mConversationListFragment).commit();
    }

    @Override
    public void showConversation(Bundle bundle) {
        mConversationFragment = new Conversation_Fragment();
        mConversationFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.framehome, mConversationFragment).addToBackStack(null).commit();
    }
}
