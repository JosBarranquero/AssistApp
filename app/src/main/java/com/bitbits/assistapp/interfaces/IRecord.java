package com.bitbits.assistapp.interfaces;

import android.content.Context;

import com.bitbits.assistapp.fragments.MedicalRecord_Fragment;

/**
 * Interface to be implemented by MedicalRecord_Fragment and MedicalRecord_Presenter
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 * @see MedicalRecord_Fragment
 * @see com.bitbits.assistapp.presenters.MedicalRecord_Presenter
 */
public interface IRecord {
    interface View {
        void setData();
        void showError(String error);
        Context getContext();
    }

    interface Presenter {
        void getData(int userId);
        void getRecord(int dataId);
    }
}
