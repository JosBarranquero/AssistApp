package com.bitbits.assistapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.adapters.MedicalRecord_Adapter;
import com.bitbits.assistapp.interfaces.IRecord;
import com.bitbits.assistapp.models.MedicalData;
import com.bitbits.assistapp.models.User;
import com.bitbits.assistapp.presenters.MedicalRecord_Presenter;

/**
 * Fragment which will show the user Medical Record
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class MedicalRecord_Fragment extends Fragment implements IRecord.View {
    private TextView mTxvName, mTxvBirth, mTxvResidence, mTxvNationality, mTxvJob, mTxvSex;
    private TextView mTxvAlcohol;
    private TextView mTxvSmoker;
    private TextView mTxvDrugs;
    private ListView mLstRecord;

    private MedicalRecord_Adapter mAdapter;
    private IRecord.Presenter mPresenter;

    private User mPat;

    private Repository mRepository = Repository.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new MedicalRecord_Presenter(this);
        mPat = (User) getArguments().getSerializable(User.PATIENT);
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

        View rootView = inflater.inflate(R.layout.fragment_medrecord, container, false);

        if (mRepository.getCurrentUser().getType().equalsIgnoreCase(User.NURSE)) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);  //We set a back arrow in the top left of the screen
        }

        mTxvBirth = (TextView) rootView.findViewById(R.id.txvBirth);
        mTxvJob = (TextView) rootView.findViewById(R.id.txvJob);
        mTxvName = (TextView) rootView.findViewById(R.id.txvName);
        mTxvNationality = (TextView) rootView.findViewById(R.id.txvNationality);
        mTxvResidence = (TextView) rootView.findViewById(R.id.txvResidence);
        mTxvSex = (TextView) rootView.findViewById(R.id.txvSex);

        mTxvAlcohol = (TextView) rootView.findViewById(R.id.txvAlcohol);
        mTxvDrugs = (TextView) rootView.findViewById(R.id.tvxDrugs);
        mTxvSmoker = (TextView) rootView.findViewById(R.id.txvSmoker);

        mLstRecord = (ListView) rootView.findViewById(R.id.lstRecord);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.getData(mPat.getId());
    }

    /**
     * Method which loads the MedicalData in its corresponding Views
     *
     * @see MedicalData
     */
    @Override
    public void setData() {
        if (getActivity() != null) {        // We make sure the fragment is visible by trying to get its activity
            mAdapter = new MedicalRecord_Adapter(getActivity());
            mLstRecord.setAdapter(mAdapter);

            MedicalData data = mRepository.getMedData().get(0);
            mTxvName.setText(mPat.getFormattedName() + " - " + mPat.getIdDoc());
            mTxvNationality.setText(data.getNationality());
            mTxvJob.setText(data.getJob());
            mTxvResidence.setText(data.getResidence());
            if (data.getSex().equalsIgnoreCase(MedicalData.FEM))
                mTxvSex.setText(R.string.feminine);
            else
                mTxvSex.setText(R.string.masculine);
            mTxvBirth.setText(data.getFormattedDate());

            mTxvAlcohol.setEnabled(data.isAlcoholic());
            mTxvSmoker.setEnabled(data.isSmoker());
            mTxvDrugs.setEnabled(data.usesDrugs());
        }
    }

    @Override
    public void showError(String error) {
        if (getView() != null) {
            Snackbar.make(getView(), error, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
