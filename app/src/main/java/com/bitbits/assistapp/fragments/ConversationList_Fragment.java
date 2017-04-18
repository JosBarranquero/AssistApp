package com.bitbits.assistapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.adapters.ConversationList_Adapter;
import com.bitbits.assistapp.interfaces.IConversation;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.models.User;
import com.bitbits.assistapp.presenters.ConversationList_Presenter;
import com.bitbits.assistapp.utilities.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Fragment which will list the available conversations
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class ConversationList_Fragment extends Fragment implements IConversation.View {
    private ConversationList_Adapter mAdapter;
    private ListView mLstConvoList;
    private ListConversationListener mCallback;
    private IConversation.Presenter mPresenter;

    public interface ListConversationListener {
        void showMessaging(Bundle bundle);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (ListConversationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getLocalClassName() + " must implement ListConversationListener interface");
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

        mPresenter = new ConversationList_Presenter(this);
        mPresenter.getUsers();

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

        getActivity().setTitle(Repository.getInstance().getCurrentUser().getFormattedName());

        mLstConvoList = (ListView) rootView.findViewById(R.id.lstConvoList);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setData();

        mLstConvoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("receiver", (User) parent.getItemAtPosition(position));
                mCallback.showMessaging(bundle);
            }
        });
    }


    /**
     * Method which inflates the ActionBar menu
     *
     * @param menu     The options menu in which the menu items are placed
     * @param inflater The MenuInflater
     * @see MenuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_conversation, menu);
    }

    /**
     * Method which handles the ActionBar menu taps
     *
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

    @Override
    public void setData() {
        mAdapter = new ConversationList_Adapter(getActivity());
        mLstConvoList.setAdapter(mAdapter);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
