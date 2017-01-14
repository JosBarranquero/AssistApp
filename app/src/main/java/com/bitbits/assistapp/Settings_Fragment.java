package com.bitbits.assistapp;

import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Fragment which manages the app settings
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Settings_Fragment extends PreferenceFragment {
    EditTextPreference mEdtAbout;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_app);

        mEdtAbout = (EditTextPreference) findPreference("about");
        mEdtAbout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //TODO Implement about splash screen
                /*View messageView = (Settings_Fragment.this).getActivity().getLayoutInflater().inflate(R.layout.screen_about, null, false);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.logo);
                builder.setTitle(R.string.app_name);
                builder.setView(messageView);
                builder.create();
                builder.show();*/
                return false;
            }
        });
    }
}
