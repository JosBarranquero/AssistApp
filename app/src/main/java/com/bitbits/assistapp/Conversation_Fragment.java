package com.bitbits.assistapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bitbits.assistapp.adapters.Conversation_Adapter;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.User;

/**
 * Fragment which will show the messages in between users and allows to write new ones
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Conversation_Fragment extends Fragment {
    ListView lstMessages;
    Conversation_Adapter mAdapter;
    EditText mEdtContent;
    Button mBtnSend;
    User receiptant;
    int id = 0;

    @Override
    public void onResume() {
        super.onResume();
        message();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_conversation, container, false);

        receiptant = (User)getArguments().getSerializable("receiver");
        getActivity().setTitle(receiptant.getName());

        lstMessages = (ListView)rootView.findViewById(R.id.lstMessages);
        mEdtContent = (EditText)rootView.findViewById(R.id.edtContent);
        mBtnSend = (Button)rootView.findViewById(R.id.btnSend);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEdtContent.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    Message message = new Message(++id, content, null, Repository.getInstance().getCurrentUser(), receiptant);
                    Repository.getInstance().writeMessage(message);

                    message();
                    mEdtContent.setText("");
                }
            }
        });
    }

    private void message() {
        mAdapter = new Conversation_Adapter(getActivity());
        lstMessages.setAdapter(mAdapter);
    }
}
