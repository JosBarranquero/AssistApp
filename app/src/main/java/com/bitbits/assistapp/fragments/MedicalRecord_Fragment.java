package com.bitbits.assistapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.adapters.MedicalRecord_Adapter;
import com.bitbits.assistapp.interfaces.IRecord;
import com.bitbits.assistapp.models.MedicalData;
import com.bitbits.assistapp.presenters.MedicalRecord_Presenter;

/**
 * Fragment which will show the user Medical Record
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class MedicalRecord_Fragment extends Fragment implements IRecord.View {
    TextView mTxvName, mTxvBirth, mTxvResidence, mTxvNationality, mTxvJob, mTxvSex;
    CheckBox mCbxAlcohol, mCbxSmoker, mCbxDrugs;
    ListView mLstRecord;

    MedicalRecord_Adapter mAdapter;
    IRecord.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MedicalRecord_Presenter(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_medrecord, container, false);

        mTxvBirth = (TextView) rootView.findViewById(R.id.txvBirth);
        mTxvJob = (TextView) rootView.findViewById(R.id.txvJob);
        mTxvName = (TextView) rootView.findViewById(R.id.txvName);
        mTxvNationality = (TextView) rootView.findViewById(R.id.txvNationality);
        mTxvResidence = (TextView) rootView.findViewById(R.id.txvResidence);
        mTxvSex = (TextView) rootView.findViewById(R.id.txvSex);

        mCbxAlcohol = (CheckBox) rootView.findViewById(R.id.cbxAlcohol);
        mCbxDrugs = (CheckBox) rootView.findViewById(R.id.cbxDrugs);
        mCbxSmoker = (CheckBox) rootView.findViewById(R.id.cbxSmoker);

        mLstRecord = (ListView) rootView.findViewById(R.id.lstRecord);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new MedicalRecord_Adapter(getActivity());
        mLstRecord.setAdapter(mAdapter);

        //TODO mPresenter.loadData();
    }

    /**
     * Method which loads the MedicalData in its corresponding Views
     *
     * @param name
     * @param nationality
     * @param job
     * @param residence
     * @param sex
     * @param birth
     * @param smoker
     * @param alcoholic
     * @param drugs
     * @see MedicalData
     */
    @Override
    public void setDataInfo(String name, String nationality, String job, String residence, String sex, String birth, boolean smoker, boolean alcoholic, boolean drugs) {
        mTxvName.setText(name);
        mTxvNationality.setText(nationality);
        mTxvJob.setText(job);
        mTxvResidence.setText(residence);
        switch (sex) {
            case MedicalData.FEM:
                mTxvSex.setText(R.string.feminine);
                break;
            case MedicalData.MASC:
                mTxvSex.setText(R.string.masculine);
                break;
        }
        mTxvBirth.setText(birth);

        mCbxAlcohol.setChecked(alcoholic);
        mCbxAlcohol.setEnabled(false);

        mCbxDrugs.setChecked(drugs);
        mCbxDrugs.setEnabled(false);

        mCbxSmoker.setChecked(smoker);
        mCbxSmoker.setEnabled(false);
    }
}
