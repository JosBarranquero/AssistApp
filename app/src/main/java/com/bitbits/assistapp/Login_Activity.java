package com.bitbits.assistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Class which will manage the system login
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Login_Activity extends AppCompatActivity {
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtnLogin = (Button)findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, ConversationList_Activity.class);
                startActivity(intent);
            }
        });
    }
}
