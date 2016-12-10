package com.bitbits.assistapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bitbits.assistapp.adapters.Conversation_Adapter;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.User;

/**
 * Activity which will show the messages in between users and allows to write new ones
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Conversation_Activity extends AppCompatActivity {
    ListView lstMessages;
    Conversation_Adapter mAdapter;
    EditText mEdtContent;
    Button mBtnSend;
    User receiptant;
    int id = 0;

    @Override
    protected void onResume() {
        super.onResume();
        message();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        receiptant = (User)getIntent().getSerializableExtra("receiver");
        setTitle(receiptant.getName());

        lstMessages = (ListView)findViewById(R.id.lstMessages);

        mEdtContent = (EditText)findViewById(R.id.edtContent);
        mBtnSend = (Button) findViewById(R.id.btnSend);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEdtContent.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    Message message = new Message(++id, content, null, Repository.getInstance().getCurrentUser(), receiptant);
                    Repository.getInstance().writeMessage(message);

                    message();
                    mEdtContent.setText("");
                }
            }
        });


    }

    private void message() {
        mAdapter = new Conversation_Adapter(this);
        lstMessages.setAdapter(mAdapter);
    }
}
