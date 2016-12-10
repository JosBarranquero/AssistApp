package com.bitbits.assistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bitbits.assistapp.adapters.ConversationList_Adapter;
import com.bitbits.assistapp.models.User;

/**
 * Class which will list the available conversations
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class ConversationList_Activity extends AppCompatActivity {
    private ConversationList_Adapter mAdapter;
    private ListView mLstConvoList;
    private DrawerLayout mDrwLayout;
    private ListView mDrwList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversationlist);
        setTitle(Repository.getInstance().getCurrentUser().getName()+" "+Repository.getInstance().getCurrentUser().getSurname());

        mDrwLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrwList = (ListView) findViewById(R.id.left_drawer);

        mLstConvoList = (ListView)findViewById(R.id.lstConvoList);
        mLstConvoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ConversationList_Activity.this, Conversation_Activity.class);
                intent.putExtra("receiver", (User)parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });
        mAdapter = new ConversationList_Adapter(this);
        mLstConvoList.setAdapter(mAdapter);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
