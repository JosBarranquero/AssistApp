package com.bitbits.assistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bitbits.assistapp.preferences.User_Preferences;

/**
 * Activity which will show the Fragments that compose this Application
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Home_Activity extends AppCompatActivity implements ConversationList_Fragment.ListConversationListener {
    Messaging_Fragment mMessagingFragment;
    ConversationList_Fragment mConversationListFragment;
    MedicalRecord_Fragment mMedicalRecordFragment;
    Settings_Fragment mSettingsFragment;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_nav);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        setupDrawer();

        showConversations();
    }

    /**
     * Method which sets the Navigation Drawer Item Selected listener
     */
    private void setupDrawer() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.navConvo:
                        showConversations();
                        break;
                    case R.id.navHistory:
                        showMedicalRecord();
                        break;
                    case R.id.navSettings:
                        showSettings();
                        break;
                    case R.id.navLogout:
                        logOut();
                        break;
                    default:
                        item.setChecked(false);
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    /**
     * Method which controls which optionMenuItem has been tapped on
     * @param item The tapped item
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Method which changes the current fragment to the Messaging_Fragment
     * @param bundle The contact info
     * @see Messaging_Fragment
     */
    @Override
    public void showMessaging(Bundle bundle) {
        mMessagingFragment = new Messaging_Fragment();
        mMessagingFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.framehome, mMessagingFragment).addToBackStack(null).commit();
    }

    /**
     * Method which changes the current fragment to the ConversationList_Fragment
     * @see ConversationList_Fragment
     */
    public void showConversations() {
        if (mConversationListFragment == null) {
            mConversationListFragment = new ConversationList_Fragment();
        }
        getFragmentManager().beginTransaction().replace(R.id.framehome, mConversationListFragment).commit();
    }

    /**
     * Method which changes the current fragment to the MedicalRecord_Fragment
     * @see MedicalRecord_Fragment
     */
    public void showMedicalRecord() {
        mMedicalRecordFragment = new MedicalRecord_Fragment();
        getFragmentManager().beginTransaction().replace(R.id.framehome, mMedicalRecordFragment).commit();
    }

    /**
     * Method which changes the current fragment to the Settings_Fragment
     * @see Settings_Fragment
     */
    public void showSettings() {
        mSettingsFragment = new Settings_Fragment();
        getFragmentManager().beginTransaction().replace(R.id.framehome, mSettingsFragment).commit();
    }

    /**
     * Method which deletes the saved user and password, and goes back to the Login_Activity
     * @see User_Preferences
     * @see Login_Activity
     */
    public void logOut() {
        User_Preferences.savePass(null, this);
        User_Preferences.saveUser(null, this);
        Intent intent = new Intent(Home_Activity.this, Login_Activity.class);
        startActivity(intent);
    }
}
