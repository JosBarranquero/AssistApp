package com.bitbits.assistapp;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.View;

/**
 * Activity which manages the app settings
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Settings_Fragment extends PreferenceFragment {
    EditTextPreference mEdtAbout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_app);

        mEdtAbout = (EditTextPreference)findPreference("about");

    }

    public void onClickAbout(View view) {

    }
}
