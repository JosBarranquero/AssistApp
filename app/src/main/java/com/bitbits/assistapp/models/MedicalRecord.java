package com.bitbits.assistapp.models;

import android.text.format.DateFormat;
import android.util.Log;

import com.bitbits.assistapp.AssistApp_Application;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class which defines medical records
 *
 * @author José Antonio Barranquero Fernández
 */
public class MedicalRecord implements Serializable {
    int id;
    int idData;
    String reason, antecedents;
    int hospitalised;
    String date;

    public MedicalRecord(int id, int idData, String reason, String antecedents, boolean hospitalised, String date) {
        this.id = id;
        this.idData = idData;
        this.reason = reason;
        this.antecedents = antecedents;
        this.date = date;
        if (hospitalised)
            this.hospitalised = 1;
        else
            this.hospitalised = 0;
    }

    public int getId() {
        return id;
    }

    public int getData() {
        return idData;
    }

    public String getReason() {
        return reason;
    }

    public String getAntecedents() {
        return antecedents;
    }

    public boolean isHospitalised() {
        return hospitalised == 1;
    }

    public String getDate() {
        return date;
    }

    public String getFormattedDate() {
        if (this.date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                Date date = format.parse(this.date);
                String dateFormat = DateFormat.getDateFormat(AssistApp_Application.getContext()).format(date) + " ";
                dateFormat += DateFormat.getTimeFormat(AssistApp_Application.getContext()).format(date);
                return dateFormat;
            } catch (ParseException e) {
                Log.e("Record", e.getMessage());
            }
        }
        return null;
    }
}
