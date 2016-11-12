package com.bitbits.assistapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.ListView;

import com.bitbits.assistapp.adapters.Conversation_Adapter;

/**
 * Class which will list the available conversations
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class ConversationList_Activity extends AppCompatActivity {
    private Conversation_Adapter mAdapter;
    private RecyclerView mRcvConvoList;
    private DrawerLayout mDrwLayout;
    private ListView mDrwList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversationlist);
        setTitle("Paco Pérez");

        mDrwLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrwList = (ListView) findViewById(R.id.left_drawer);

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
