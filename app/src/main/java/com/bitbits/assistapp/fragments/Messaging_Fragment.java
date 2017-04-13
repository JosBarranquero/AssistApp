package com.bitbits.assistapp.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bitbits.assistapp.R;
import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.adapters.Messaging_CursorAdapter;
import com.bitbits.assistapp.interfaces.IMessage;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.User;
import com.bitbits.assistapp.presenters.Messaging_Presenter;

/**
 * Fragment which will show the messages in between users and allows to write new ones
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Messaging_Fragment extends Fragment implements IMessage.View {
    ListView mLstMessages;
    Messaging_CursorAdapter mAdapter;
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
        mAdapter = new Messaging_CursorAdapter(getActivity(), null, 1);
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

        mLstMessages.setAdapter(mAdapter);

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEdtContent.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    Message message = new Message(++id, content, null, Repository.getInstance().getCurrentUser(), receiver);
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

    @Override
    public void setCursor(Cursor cursor) {
        if (cursor != null) {
            mAdapter.swapCursor(cursor);
        }
    }
}
