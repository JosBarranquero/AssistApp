package com.bitbits.assistapp.presenters;

import android.content.Context;
import android.util.Log;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.interfaces.IRecord;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.utilities.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Presenter for MedicalRecord_Fragment
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */

public class MedicalRecord_Presenter implements IRecord.Presenter {
    private static final String DATA_TAG = "MedData";
    private static final String RECORD_TAG = "MedRecord";

    private IRecord.View mView;
    private Context mContext;

    public MedicalRecord_Presenter(IRecord.View view) {
        this.mView = view;
        this.mContext = mView.getContext();
    }

    /**
     * Method which loads the medical mData and sends it to the mView, so it can be shown to the user
     */
    @Override
    public void getData(int userId) {
        ApiClient.get(ApiClient.MEDDATA + "/" + userId, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Result result;
                Gson gson = new Gson();
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null) {
                    if (result.getCode()) {
                        Repository.getInstance().setMedData(result.getMeddata());

                        getRecord(result.getMeddata().get(0).getId());
                    } else {
                        mView.showError(mContext.getString(R.string.no_data));
                        Log.e(DATA_TAG, result.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                mView.showError(mContext.getString(R.string.connection_error));
                Log.e(DATA_TAG, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                mView.showError(mContext.getString(R.string.connection_error));
                Log.e(DATA_TAG, throwable.getMessage());
            }
        });
    }

    @Override
    public void getRecord(int dataId) {
        ApiClient.get(ApiClient.MEDRECORD + "/" + dataId, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Result result;
                Gson gson = new Gson();
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null) {
                    if (result.getCode()) {
                        Repository.getInstance().setRecord(result.getMedrecord());

                        mView.setData();
                    } else {
                        mView.showError(mContext.getString(R.string.no_record));
                        Log.e(RECORD_TAG, result.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                mView.showError(mContext.getString(R.string.connection_error));
                Log.e(RECORD_TAG, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                mView.showError(mContext.getString(R.string.connection_error));
                Log.e(RECORD_TAG, throwable.getMessage());
            }
        });
    }
}
