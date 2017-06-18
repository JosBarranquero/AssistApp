package com.bitbits.assistapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private TextView mTxvName, mTxvIdDoc, mTxvBirth, mTxvAge, mTxvResidence, mTxvNationality, mTxvJob, mTxvSex;
    private TextView mTxvAlcohol;
    private TextView mTxvSmoker;
    private TextView mTxvDrugs;
    private ListView mLstRecord;

    private MedicalRecord_Adapter mAdapter;
    private IRecord.Presenter mPresenter;

    private User mPat;
    MedicalData mData;

    private Repository mRepository = Repository.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new MedicalRecord_Presenter(this);
        mPat = (User) getArguments().getSerializable(User.PATIENT);

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

        View rootView = inflater.inflate(R.layout.fragment_medrecord, container, false);

        if (mRepository.getCurrentUser().getType().equalsIgnoreCase(User.NURSE)) {  // If we are a nurse, we can go back to our patients list
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);  //We set a back arrow in the top left of the screen
        }

        mTxvBirth = (TextView) rootView.findViewById(R.id.txvBirth);
        mTxvAge = (TextView) rootView.findViewById(R.id.txvAge);
        mTxvJob = (TextView) rootView.findViewById(R.id.txvJob);
        mTxvName = (TextView) rootView.findViewById(R.id.txvName);
        mTxvIdDoc = (TextView) rootView.findViewById(R.id.txvIdDoc);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (mRepository.getCurrentUser().getType().equalsIgnoreCase(User.NURSE))    // If the user is a nurse, we show the option to send an email
            inflater.inflate(R.menu.menu_medrecord, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_email:
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                Uri uri = Uri.parse("mailto:" + mPat.getEmail());
                intent.setData(uri);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, getActivity().getString(R.string.send_email)));
                } else {
                    if (getView() != null)
                        Snackbar.make(getView(), R.string.no_email, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_call:
                if (mData != null) {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mData.getPhone()));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                } else {
                    if (getView() != null) {
                        Snackbar.make(getView(), R.string.no_phone, Snackbar.LENGTH_SHORT).show();
                    }
                }
                break;
        }

        return super.onOptionsItemSelected(item);
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

            mData = mRepository.getMedData().get(0);
            mTxvName.setText(mPat.getWholeName());
            mTxvIdDoc.setText(mPat.getIdDoc());
            mTxvNationality.setText(mData.getNationality());
            mTxvJob.setText(mData.getJob());
            mTxvResidence.setText(mData.getResidence());
            if (mData.getSex().equalsIgnoreCase(MedicalData.FEM))
                mTxvSex.setText(R.string.feminine);
            else
                mTxvSex.setText(R.string.masculine);
            mTxvBirth.setText(mData.getFormattedDate());
            mTxvAge.setText(String.format(getActivity().getString(R.string.years), mData.getAge()));

            mTxvAlcohol.setEnabled(mData.isAlcoholic());
            mTxvSmoker.setEnabled(mData.isSmoker());
            mTxvDrugs.setEnabled(mData.usesDrugs());
        }
    }

    @Override
    public void showMessage(String message) {
        if (getView() != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
