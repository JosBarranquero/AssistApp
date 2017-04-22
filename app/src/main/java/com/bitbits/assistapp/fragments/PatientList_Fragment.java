package com.bitbits.assistapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.adapters.UsersList_Adapter;
import com.bitbits.assistapp.models.User;

/**
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */

public class PatientList_Fragment extends Fragment {
    private UsersList_Adapter mAdapter;
    private ListView mLstPatientList;

    private ListPatientListener mCallback;

    public interface ListPatientListener {
        void showMedicalRecord(Bundle bundle);
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

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new UsersList_Adapter(getActivity());
        mLstPatientList.setAdapter(mAdapter);

        mLstPatientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(User.PATIENT, (User) parent.getItemAtPosition(position));
                mCallback.showMedicalRecord(bundle);
            }
        });
    }
}
