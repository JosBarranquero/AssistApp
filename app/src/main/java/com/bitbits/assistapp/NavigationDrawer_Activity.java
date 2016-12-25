package com.bitbits.assistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Activity which will act as a Navigation Drawer
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class NavigationDrawer_Activity extends AppCompatActivity {
    Button mBtnRecord, mBtnSettings, mBtnLogout, mBtnAbout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);

        mBtnRecord = (Button) findViewById(R.id.btnRecord);
        mBtnSettings = (Button) findViewById(R.id.btnSettings);

        mBtnLogout = (Button) findViewById(R.id.btnLogout);
        mBtnAbout = (Button)findViewById(R.id.btnAbout);
        mBtnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View messageView = getLayoutInflater().inflate(R.layout.screen_about, null, false);

                AlertDialog.Builder builder = new AlertDialog.Builder(NavigationDrawer_Activity.this);
                builder.setIcon(R.drawable.logo);
                builder.setTitle(R.string.app_name);
                builder.setView(messageView);
                builder.create();
                builder.show();
            }
        });
    }
}
