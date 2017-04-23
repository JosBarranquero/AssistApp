package com.bitbits.assistapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bitbits.assistapp.fragments.About_Fragment;
import com.bitbits.assistapp.fragments.ConversationList_Fragment;
import com.bitbits.assistapp.fragments.MedicalRecord_Fragment;
import com.bitbits.assistapp.fragments.Messaging_Fragment;
import com.bitbits.assistapp.fragments.PatientList_Fragment;
import com.bitbits.assistapp.fragments.Settings_Fragment;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.models.User;
import com.bitbits.assistapp.preferences.User_Preferences;
import com.bitbits.assistapp.utilities.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Activity which will show the Fragments that compose this Application
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Home_Activity extends AppCompatActivity implements ConversationList_Fragment.ListConversationListener, PatientList_Fragment.ListPatientListener {
    private static final String MESSAGING_FRAGMENT = "Messaging";
    private static final String CONVERSATION_LIST_FRAGMENT = "ConversationList";
    private static final String MEDICAL_RECORD_FRAGMENT = "MedicalRecord";
    private static final String PATIENT_LIST_FRAGMENT = "PatientList";
    private static final String SETTINGS_FRAGMENT = "Settings";
    private static final String ABOUT_FRAGMENT = "About";

    Messaging_Fragment mMessagingFragment;
    ConversationList_Fragment mConversationListFragment;
    MedicalRecord_Fragment mMedicalRecordFragment;
    PatientList_Fragment mPatientListFragment;
    Settings_Fragment mSettingsFragment;
    About_Fragment mAboutFragment;

    Repository mRepository = Repository.getInstance();

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

        if (User_Preferences.getUser(this) != null && User_Preferences.getPass(this) != null) {
            logIn(User_Preferences.getUser(this), User_Preferences.getPass(this));
        } else {
            setupDrawer();
            showConversations();
        }
    }

    /**
     * Method which sets the Navigation Drawer Item Selected listener and header user information
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
                        setTitle(item.getTitle());
                        showMedicalRecord();
                        break;
                    case R.id.navSettings:
                        setTitle(item.getTitle());
                        showSettings();
                        break;
                    case R.id.navAbout:
                        item.setChecked(false);
                        showAbout();
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

        View header = mNavigationView.getHeaderView(0);

        TextView username = (TextView) header.findViewById(R.id.username);
        username.setText(mRepository.getCurrentUser().getFormattedName());

        CircleImageView profileImage = (CircleImageView) header.findViewById(R.id.profile_image);
        Picasso.with(Home_Activity.this)
                .load(AssistApp_Application.URL + mRepository.getCurrentUser().getImg())
                .error(R.drawable.logo)
                .placeholder(R.drawable.user)
                .into(profileImage);
    }

    /**
     * Method which controls which optionMenuItem has been tapped on
     *
     * @param item The tapped item
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Messaging_Fragment messaging = (Messaging_Fragment) getFragmentManager().findFragmentByTag(MESSAGING_FRAGMENT);
        MedicalRecord_Fragment record = (MedicalRecord_Fragment) getFragmentManager().findFragmentByTag(MEDICAL_RECORD_FRAGMENT);

        switch (item.getItemId()) {
            case android.R.id.home:
                //If we are in the Messaging_Fragment or MedicalRecord_Fragment (being a nurse), we show a back arrow, instead of the drawer icon, so we simulate a back key press
                if ((messaging != null && messaging.isVisible()) || (record != null && record.isVisible() && mRepository.getCurrentUser().getType().equalsIgnoreCase(User.NURSE))) {
                    this.onBackPressed();
                } else {    // We open the drawer
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method which controls the physical back key press
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {  //If drawer is opened, we close it
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {        // If it's not opened, we check if the Messaging, MedicalRecord (if curUser is a nurse) or About fragments are being displayed to unlock the drawer, show the action bar and set and show the home button
            Messaging_Fragment messaging = (Messaging_Fragment) getFragmentManager().findFragmentByTag(MESSAGING_FRAGMENT);
            About_Fragment about = (About_Fragment) getFragmentManager().findFragmentByTag(ABOUT_FRAGMENT);
            MedicalRecord_Fragment record = (MedicalRecord_Fragment) getFragmentManager().findFragmentByTag(MEDICAL_RECORD_FRAGMENT);

            if ((messaging != null && messaging.isVisible()) || (about != null && about.isVisible()) || (record != null && record.isVisible() && mRepository.getCurrentUser().getType().equalsIgnoreCase(User.NURSE))) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                getSupportActionBar().show();
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_home);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            super.onBackPressed();
        }
    }

    /**
     * Method which changes the current fragment to the Messaging_Fragment
     *
     * @param bundle The contact info
     * @see Messaging_Fragment
     */
    @Override
    public void showMessaging(Bundle bundle) {
        FragmentManager fragmentManager = getFragmentManager();

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        mMessagingFragment = new Messaging_Fragment();
        mMessagingFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framehome, mMessagingFragment, MESSAGING_FRAGMENT);
        fragmentTransaction.addToBackStack(MESSAGING_FRAGMENT);
        fragmentTransaction.commit();
    }

    /**
     * Method which changes the current fragment to the ConversationList_Fragment
     *
     * @see ConversationList_Fragment
     */
    public void showConversations() {
        FragmentManager fragmentManager = getFragmentManager();

        if (mConversationListFragment == null) {
            mConversationListFragment = new ConversationList_Fragment();
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framehome, mConversationListFragment, CONVERSATION_LIST_FRAGMENT);
        fragmentTransaction.commit();
    }

    /**
     * Method which changes the current fragment to the MedicalRecord_Fragment
     *
     * @see MedicalRecord_Fragment
     */
    public void showMedicalRecord() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (mRepository.getCurrentUser().getType().equalsIgnoreCase(User.PATIENT)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(User.PATIENT, mRepository.getCurrentUser());

            mMedicalRecordFragment = new MedicalRecord_Fragment();
            mMedicalRecordFragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.framehome, mMedicalRecordFragment, MEDICAL_RECORD_FRAGMENT);
            fragmentTransaction.commit();
        } else {
            mPatientListFragment = new PatientList_Fragment();

            fragmentTransaction.replace(R.id.framehome, mPatientListFragment, PATIENT_LIST_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    /**
     * Method which changes the current fragment to the MedicalRecord_Fragment for an specific user
     *
     * @param bundle The user that we want the data from
     */
    @Override
    public void showMedicalRecord(Bundle bundle) {
        FragmentManager fragmentManager = getFragmentManager();

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        mMedicalRecordFragment = new MedicalRecord_Fragment();
        mMedicalRecordFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framehome, mMedicalRecordFragment, MEDICAL_RECORD_FRAGMENT);
        fragmentTransaction.addToBackStack(MEDICAL_RECORD_FRAGMENT);
        fragmentTransaction.commit();
    }

    /**
     * Method which changes the current fragment to the Settings_Fragment
     *
     * @see Settings_Fragment
     */
    public void showSettings() {
        FragmentManager fragmentManager = getFragmentManager();

        if (mSettingsFragment == null) {
            mSettingsFragment = new Settings_Fragment();
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framehome, mSettingsFragment, SETTINGS_FRAGMENT);
        fragmentTransaction.commit();
    }

    /**
     * Method which shows the about splash screen
     */
    private void showAbout() {
        FragmentManager fragmentManager = getFragmentManager();

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().hide();

        if (mAboutFragment == null) {
            mAboutFragment = new About_Fragment();
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framehome, mAboutFragment, ABOUT_FRAGMENT);
        fragmentTransaction.addToBackStack(ABOUT_FRAGMENT);
        fragmentTransaction.commit();
    }

    /**
     * Method which deletes the saved user and password, and goes back to the Login_Activity
     *
     * @see User_Preferences
     * @see Login_Activity
     */
    public void logOut() {
        User_Preferences.savePass(null, this);
        User_Preferences.saveUser(null, this);
        User_Preferences.saveEmail(null, this);
        Intent intent = new Intent(Home_Activity.this, Login_Activity.class);
        startActivity(intent);
    }

    /**
     * Method which logs in, when the user credentials have been saved
     *
     * @param user
     * @param password
     */
    private void logIn(String user, final String password) {
        RequestParams params = new RequestParams();
        params.put("idDoc", user);
        params.put("password", password);

        ApiClient.post(ApiClient.USERS, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setTitle(R.string.loggingin);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                Result result;
                Gson gson = new Gson();
                result = gson.fromJson(String.valueOf(responseBody), Result.class);
                if (result != null) {
                    if (result.getCode()) {
                        mRepository.setCurrentUser(result.getUsers().get(0));
                        mRepository.getCurrentUser().setPassword(password);

                        setupDrawer();
                        showConversations();
                    } else {
                        Snackbar.make(findViewById(R.id.activity_home), (Home_Activity.this).getString(R.string.credentials_error), Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                logOut();
                            }
                        }).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Snackbar.make(findViewById(R.id.activity_home), (Home_Activity.this).getString(R.string.connection_error), Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (Home_Activity.this).finish();
                    }
                });
                Log.e("Login", responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Snackbar.make(findViewById(R.id.activity_home), (Home_Activity.this).getString(R.string.connection_error), Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (Home_Activity.this).finish();
                    }
                });
                Log.e("Login", throwable.getMessage());
            }
        });
    }
}