package com.bitbits.assistapp.presenters;

import com.bitbits.assistapp.Repository;
import com.bitbits.assistapp.interfaces.IRecord;
import com.bitbits.assistapp.models.MedicalData;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Presenter for MedicalRecord_Fragment
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */

public class MedicalRecord_Presenter implements IRecord.Presenter {
    IRecord.View view;

    public MedicalRecord_Presenter(IRecord.View view) {
        this.view = view;
    }

    /**
     * Method which loads the medical data and sends it to the view, so it can be shown to the user
     */
    @Override
    public void loadData(int userId) {
        //TODO load medical data
        MedicalData data = Repository.getInstance().getMedData().get(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        view.setDataInfo(String.valueOf(data.getIdPat()), data.getNationality(), data.getJob(), data.getResidence(), data.getSex(), dateFormat.format(data.getBirthdate()), data.isSmoker(), data.isAlcoholic(), data.usesDrugs());
    }
}
