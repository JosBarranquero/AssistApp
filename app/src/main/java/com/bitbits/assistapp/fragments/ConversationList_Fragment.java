package com.bitbits.assistapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.adapters.UsersList_Adapter;
import com.bitbits.assistapp.interfaces.IConversation;
import com.bitbits.assistapp.models.User;
import com.bitbits.assistapp.presenters.ConversationList_Presenter;

/**
 * Fragment which will list the available conversations
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class ConversationList_Fragment extends Fragment implements IConversation.View {
    private ListView mLstConvoList;
    private EditText mEdtSearch;

    private UsersList_Adapter mAdapter;
    private ListConversationListener mCallback;
    private IConversation.Presenter mPresenter;

    private static boolean BY_NAME = true;

    Repository mRepository = Repository.getInstance();

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

        mPresenter = null;
        mAdapter = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_conversationlist, container, false);

        getActivity().setTitle(mRepository.getCurrentUser().getFormattedName());

        mLstConvoList = (ListView) rootView.findViewById(R.id.lstConvoList);
        mEdtSearch = (EditText) rootView.findViewById(R.id.edtSearch);

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
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {       // When the EditText content changes
                mAdapter.searchUser(mEdtSearch.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean action = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), 0);
                    action = true;
                }
                return action;
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
            case R.id.action_search:
                if (mEdtSearch.getVisibility() == View.GONE) {
                    mEdtSearch.setVisibility(View.VISIBLE);
                    mEdtSearch.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mEdtSearch, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    mEdtSearch.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), 0);
                }
                break;
            case R.id.action_order:
                BY_NAME = !BY_NAME;
                mAdapter.sortUsers(BY_NAME);
                if (BY_NAME)
                    item.setTitle(R.string.orderSurname);   //If current sort is by name, we have to show the option to order by surname
                else
                    item.setTitle(R.string.orderName);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setData() {
        mAdapter = new UsersList_Adapter(getActivity());
        mLstConvoList.setAdapter(mAdapter);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
