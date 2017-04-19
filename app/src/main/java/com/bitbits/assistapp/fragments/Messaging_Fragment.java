package com.bitbits.assistapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Messaging_Fragment extends Fragment implements IMessage.View {
    private EditText mEdtContent;
    private ImageButton mBtnSend;
    private ListView mLstMessages;

    private Messaging_Adapter mAdapter;
    private IMessage.Presenter mPresenter;

    private User receiver;

    @Override
    public void onStart() {
        super.onStart();
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
        getActivity().setTitle(receiver.getFormattedName());

        rootView.setBackgroundColor(getResources().getColor(R.color.colorOtherMessage));

        mLstMessages = (ListView) rootView.findViewById(R.id.lstMessages);
        mEdtContent = (EditText) rootView.findViewById(R.id.edtContent);
        mBtnSend = (ImageButton) rootView.findViewById(R.id.btnSend);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMessages();

        mBtnSend.setBackground(getResources().getDrawable(R.drawable.ic_action_send));
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEdtContent.getText().toString();
                content = content.trim();

                Message message = new Message(content, Repository.getInstance().getCurrentUser().getId(), receiver.getId());
                mPresenter.sendMessage(message);

                mEdtContent.setText("");
            }
        });
        mEdtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = mEdtContent.getText().toString();
                content = content.trim();
                if (!TextUtils.isEmpty(content)) {
                    mBtnSend.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_send_av));
                    mBtnSend.setEnabled(true);
                } else {
                    mBtnSend.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_send));
                    mBtnSend.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Method which updates the adapter data
     */
    @Override
    public void message() {
        mAdapter.notifyDataSetChanged();
        mLstMessages.setSelection(mLstMessages.getCount());
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
                        mLstMessages.setSelection(mLstMessages.getCount());
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

    /**
     * Method which returns the Context
     *
     * @return The context
     * @see Context
     */
    @Override
    public Context getContext() {
        return getActivity();
    }
}
