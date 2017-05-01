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
 * Class which defines a patient's medical data
 *
 * @author José Antonio Barranquero Fernández
 */
public class MedicalData implements Serializable {
    private int id;
    private int idPat;
    private String birthdate;
    private String nationality, job, residence;
    private String sex;
    private int smoker, alcohol, drugs;

    public static final String MASC = "M";
    public static final String FEM = "F";

    public MedicalData(int id, String birthdate, int idPat, String sex, String nationality, String job, String residence, boolean smoker, boolean alcoholic, boolean drugs) {
        this.id = id;
        this.birthdate = birthdate;
        this.idPat = idPat;
        this.sex = sex;
        this.nationality = nationality;
        this.job = job;
        this.residence = residence;
        if (smoker)
            this.smoker = 1;
        else
            this.smoker = 0;
        if (alcoholic)
            this.alcohol = 1;
        else
            this.alcohol = 0;
        if (drugs)
            this.drugs = 1;
        else
            this.drugs = 0;
    }

    public int getId() {
        return id;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getFormattedDate() {
        if (this.birthdate != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = format.parse(this.birthdate);
                String dateFormat = DateFormat.getDateFormat(AssistApp_Application.getContext()).format(date);
                return dateFormat;
            } catch (ParseException e) {
                Log.e("Data", e.getMessage());
            }
        }
        return null;
    }

    public int getIdPat() {
        return idPat;
    }

    public String getSex() {
        return sex;
    }

    public String getNationality() {
        return nationality;
    }

    public String getJob() {
        return job;
    }

    public String getResidence() {
        return residence;
    }

    public boolean isSmoker() {
        return smoker == 1;
    }

    public boolean isAlcoholic() {
        return alcohol == 1;
    }

    public boolean usesDrugs() {
        return drugs == 1;
    }
}
