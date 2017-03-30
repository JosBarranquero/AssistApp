package com.bitbits.assistapp.interfaces;

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
        void setDataInfo(String name, String nationality, String job, String residence, String sex, String birth, boolean smoker, boolean alcoholic, boolean drugs);
    }

    interface Presenter {
        void loadData();
    }
}
