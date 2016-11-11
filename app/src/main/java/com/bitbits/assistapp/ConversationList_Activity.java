package com.bitbits.assistapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.bitbits.assistapp.adapters.Conversation_Adapter;

/**
 * Class which will list the available conversations
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class ConversationList_Activity extends AppCompatActivity {
    private Conversation_Adapter mAdapter;
    private RecyclerView mRcvConvoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversationlist);
        setTitle("Paco Pérez");

        mAdapter = new Conversation_Adapter(this);
        mRcvConvoList = (RecyclerView)findViewById(R.id.rcvConvoList);
    }

    /**
     * Method which inflates the ActionBar menu
     * @param menu The options menu in which the menu items are placed
     * @return true to display the menu, false will not display it
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conversation, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
