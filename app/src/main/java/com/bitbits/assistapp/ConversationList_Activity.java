package com.bitbits.assistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.bitbits.assistapp.adapters.ConversationList_Adapter;

/**
 * Class which will list the available conversations
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class ConversationList_Activity extends AppCompatActivity {
    private ConversationList_Adapter mAdapter;
    private RecyclerView mRcvConvoList;
    private DrawerLayout mDrwLayout;
    private ListView mDrwList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversationlist);
        setTitle(this.getIntent().getExtras().getString("name"));

        mDrwLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrwList = (ListView) findViewById(R.id.left_drawer);

        mAdapter = new ConversationList_Adapter(this);
        mRcvConvoList = (RecyclerView)findViewById(R.id.rcvConvoList);
        mRcvConvoList.setLayoutManager(new LinearLayoutManager(this));
        mRcvConvoList.setAdapter(mAdapter);

        Toast.makeText(this, R.string.provisional_2, Toast.LENGTH_LONG).show();
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

    /**
     * Method which handles the ActionBar menu taps
     * @param item The item that has been tapped on
     * @return true when the event controlled by this has been consumed, false when it hasn't and gets propagated
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_navDrawer:
                intent = new Intent(ConversationList_Activity.this, NavigationDrawer_Activity.class);
                startActivity(intent);
                break;
            case R.id.action_message:
                intent = new Intent(ConversationList_Activity.this, Conversation_Activity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
