package com.bitbits.assistapp.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.bitbits.assistapp.R;

/**
 * Fragment which manages the app settings
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Settings_Fragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_app);
    }
}
