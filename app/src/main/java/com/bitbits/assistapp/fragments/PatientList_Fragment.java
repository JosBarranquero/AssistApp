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
import android.widget.ListView;
import android.widget.TextView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.adapters.UsersList_Adapter;
import com.bitbits.assistapp.models.User;

/**
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */

public class PatientList_Fragment extends Fragment {
    private ListView mLstPatientList;
    private EditText mEdtSearch;

    private UsersList_Adapter mAdapter;
    private ListPatientListener mCallback;

    private static boolean BY_NAME = true;

    public interface ListPatientListener {
        void showMedicalRecord(Bundle bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (ListPatientListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getLocalClassName() + " must implement ListPatientListener interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_patientlist, container, false);

        mLstPatientList = (ListView) rootView.findViewById(R.id.lstPatientList);
        mEdtSearch = (EditText) rootView.findViewById(R.id.edtSearch);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new UsersList_Adapter(getActivity(), false);
        mLstPatientList.setAdapter(mAdapter);

        mLstPatientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(User.PATIENT, (User) parent.getItemAtPosition(position));
                mCallback.showMedicalRecord(bundle);
            }
        });
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
                if (mEdtSearch.getVisibility() == View.GONE) {      // If the search bar is hidden
                    mEdtSearch.setVisibility(View.VISIBLE);         // Set it visible and give it focus
                    mEdtSearch.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mEdtSearch, InputMethodManager.SHOW_IMPLICIT);    // Show soft keyboard
                } else {
                    mEdtSearch.setText("");
                    mEdtSearch.setVisibility(View.GONE);            // Hide search bar
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), 0);        // Hide soft keyboard
                }
                break;
            case R.id.action_order:
                BY_NAME = !BY_NAME;
                mAdapter.sortUsers(BY_NAME);    // If BY_NAME is true, the list will be sorted by name, else, by surname
                if (BY_NAME)
                    item.setTitle(R.string.orderSurname);   // If list is sorted by name, we have to show the option to order by surname
                else
                    item.setTitle(R.string.orderName);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
