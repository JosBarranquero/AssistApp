package com.bitbits.assistapp.fragments;

import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbits.assistapp.R;

/**
 * About application fragment
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */
public class About_Fragment extends Fragment {
    private TextView mTxvVersion;
    private ImageView mImgAppLogo;
    private int mTaps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        mTxvVersion = (TextView)rootView.findViewById(R.id.txvAboutVersion);
        mImgAppLogo = (ImageView)rootView.findViewById(R.id.imgAppLogo);
        mTaps = 0;

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            String version = pInfo.versionName;

            mTxvVersion.setText(String.format(getString(R.string.about_Version), version));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("About", e.getMessage());
        }

        mImgAppLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaps++;
                if (mTaps % 2 == 0) {
                    View messageView = getActivity().getLayoutInflater().inflate(R.layout.screen_bitbits, null, false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setIcon(R.drawable.bitbits);
                    builder.setTitle(R.string.about_company);
                    builder.setView(messageView);
                    builder.create();
                    builder.show();
                }
            }
        });
    }
}
