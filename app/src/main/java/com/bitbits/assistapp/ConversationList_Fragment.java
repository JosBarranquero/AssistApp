package com.bitbits.assistapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bitbits.assistapp.adapters.ConversationList_Adapter;
import com.bitbits.assistapp.models.User;

/**
 * Class which will list the available conversations
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class ConversationList_Fragment extends Fragment {
    private ConversationList_Adapter mAdapter;
    private ListView mLstConvoList;
    private ListConversationListener mCallback;

    public interface ListConversationListener {
        void showMessaging(Bundle bundle);
        void showMedicalRecord();
        void showSettings();
        void logOut();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (ListConversationListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage() + " activity must implement ListConversationListener interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ConversationList_Adapter(getActivity());

        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mAdapter = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_conversationlist, container, false);

        getActivity().setTitle(Repository.getInstance().getCurrentUser().getName() + " " + Repository.getInstance().getCurrentUser().getSurname());

        mLstConvoList = (ListView)rootView.findViewById(R.id.lstConvoList);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLstConvoList.setAdapter(mAdapter);
        mLstConvoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("receiver", (User)parent.getItemAtPosition(position));
                mCallback.showMessaging(bundle);
            }
        });
    }



    /**
     * Method which inflates the ActionBar menu
     * @param menu The options menu in which the menu items are placed
     * @param inflater The MenuInflater
     *                 @see MenuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_conversation, menu);
    }

    /**
     * Method which handles the ActionBar menu taps
     * @param item The item that has been tapped on
     * @return true when the event controlled by this has been consumed, false when it hasn't and gets propagated
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //TODO Implement search option
        }

        return super.onOptionsItemSelected(item);
    }
}
