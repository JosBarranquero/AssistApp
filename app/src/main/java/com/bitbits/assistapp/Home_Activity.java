package com.bitbits.assistapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView)findViewById(R.id.navigation_view);

        setupDrawer();

        mConversationListFragment = new ConversationList_Fragment();
        getFragmentManager().beginTransaction().add(R.id.framehome, mConversationListFragment).commit();
    }

    private void setupDrawer() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
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
    public void showMessaging(Bundle bundle) {
        mMessagingFragment = new Messaging_Fragment();
        mMessagingFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.framehome, mMessagingFragment).addToBackStack(null).commit();
    }

    @Override
    public void showMedicalRecord() {
        mMedicalRecordFragment = new MedicalRecord_Fragment();
        getFragmentManager().beginTransaction().replace(R.id.framehome, mMedicalRecordFragment).addToBackStack(null).commit();
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
