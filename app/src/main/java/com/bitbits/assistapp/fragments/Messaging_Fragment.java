package com.bitbits.assistapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.adapters.Messaging_Adapter;
import com.bitbits.assistapp.interfaces.IMessage;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.Result;
import com.bitbits.assistapp.models.User;
import com.bitbits.assistapp.presenters.Messaging_Presenter;
import com.bitbits.assistapp.utilities.ApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Fragment which will show the messages in between users and allows to write new ones
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Messaging_Fragment extends Fragment implements IMessage.View {
    ListView mLstMessages;
    Messaging_Adapter mAdapter;
    EditText mEdtContent;
    ImageButton mBtnSend;
    User receiver;
    IMessage.Presenter mPresenter;
    int id = 0;

    @Override
    public void onStart() {
        super.onStart();
        //mPresenter.getAllMessages(mAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new Messaging_Presenter(this);
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
        View rootView = inflater.inflate(R.layout.fragment_messaging, container, false);

        receiver = (User) getArguments().getSerializable("receiver");
        getActivity().setTitle(receiver.getName() + " " + receiver.getSurname());

        mLstMessages = (ListView) rootView.findViewById(R.id.lstMessages);
        mEdtContent = (EditText) rootView.findViewById(R.id.edtContent);
        mBtnSend = (ImageButton) rootView.findViewById(R.id.btnSend);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMessages();

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEdtContent.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    Message message = new Message(++id, content, Repository.getInstance().getCurrentUser().getId(), receiver.getId());
                    mPresenter.sendMessage(message);

                    mEdtContent.setText("");
                }
            }
        });
    }

    /**
     * Method which updates the adapter data
     */
    @Override
    public void message() {
        mAdapter.notifyDataSetChanged();
    }

    private void getMessages() {
        RequestParams params = new RequestParams();
        params.put("receiver", receiver.getId());
        params.put("sender", Repository.getInstance().getCurrentUser().getId());
        ApiClient.post(ApiClient.MESSAGES, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Result result;
                Gson gson = new Gson();
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null) {
                    if (result.getCode()) {
                        Repository.getInstance().setMessages(result.getMessages());

                        mAdapter = new Messaging_Adapter(getActivity());
                        mLstMessages.setAdapter(mAdapter);
                    } else {
                        Log.e("MSG", result.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("MSG", responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("MSG", throwable.getMessage());
            }
        });
    }

    /*@Override
    public void setCursor(Cursor cursor) {
        if (cursor != null) {
            mAdapter.swapCursor(cursor);
        }
    }*/
}
