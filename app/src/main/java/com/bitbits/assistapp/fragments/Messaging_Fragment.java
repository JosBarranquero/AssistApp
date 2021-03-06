package com.bitbits.assistapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.adapters.Messaging_Adapter;
import com.bitbits.assistapp.interfaces.IMessage;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.User;
import com.bitbits.assistapp.presenters.Messaging_Presenter;

/**
 * Fragment which will show the messages in between users and allows to write new ones
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Messaging_Fragment extends Fragment implements IMessage.View {
    private EditText mEdtContent;
    private ImageButton mBtnSend;
    private RecyclerView mLstMessages;

    private Messaging_Adapter mAdapter;
    private IMessage.Presenter mPresenter;

    private Repository mRepository = Repository.getInstance();

    private User mReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new Messaging_Presenter(this);

        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.stopFetching();
        mPresenter = null;
        mAdapter = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_messaging, container, false);

        mReceiver = (User) getArguments().getSerializable("receiver");
        getActivity().setTitle(mReceiver.getWholeName());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);  //We set a back arrow in the top left of the screen

        mLstMessages = (RecyclerView) rootView.findViewById(R.id.lstMessages);
        mEdtContent = (EditText) rootView.findViewById(R.id.edtContent);
        mBtnSend = (ImageButton) rootView.findViewById(R.id.btnSend);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.getMessages(mReceiver.getId(), mRepository.getCurrentUser().getId());

        mLstMessages.setHasFixedSize(true);     // So the RecyclerView doesn't change its size
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);  // The starts from the end
        mLstMessages.setLayoutManager(linearLayoutManager);

        mBtnSend.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_send));
        mBtnSend.setEnabled(false);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEdtContent.getText().toString();
                content = content.trim();

                Message message = new Message(content, mRepository.getCurrentUser().getId(), mReceiver.getId());
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

    @Override
    public void showMessage(String message) {
        if (getView() != null)
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
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

    @Override
    public void setData() {
        if (getActivity() != null) {    // We make sure the fragment is visible by trying to get its activity
            mPresenter.readMessage(mReceiver);   // We read our receiver messages

            mAdapter = new Messaging_Adapter(getActivity());
            mLstMessages.setAdapter(mAdapter);
        }
    }
}
