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
    Messaging_Fragment mMessagingFragment;
    ConversationList_Fragment mConversationListFragment;
    Settings_Fragment mSettingsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mConversationListFragment = new ConversationList_Fragment();
        getFragmentManager().beginTransaction().add(R.id.framehome, mConversationListFragment).commit();
    }

    @Override
    public void showMessaging(Bundle bundle) {
        mMessagingFragment = new Messaging_Fragment();
        mMessagingFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.framehome, mMessagingFragment).addToBackStack(null).commit();
    }

    @Override
    public void showMedicalRecord() {

    }

    @Override
    public void showSettings() {
        mSettingsFragment = new Settings_Fragment();
        getFragmentManager().beginTransaction().replace(R.id.framehome, mSettingsFragment).addToBackStack(null).commit();
    }

    @Override
    public void logOut() {

    }
}
