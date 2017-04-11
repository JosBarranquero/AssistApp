package com.bitbits.assistapp.models;

import android.support.annotation.StringDef;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

/**
 * Class which defines a patient's medical data
 * @author José Antonio Barranquero Fernández
 */
public class MedicalData implements Serializable {
    int id;
    int idPat;
    Date birthdate;
    String nationality, job, residence;
    @Sex String sex;
    boolean smoker, alcoholism, drugs;

    @Retention(RetentionPolicy.RUNTIME)
    @StringDef({MASC, FEM})
    public @interface Sex {}
    public static final String MASC = "M";
    public static final String FEM = "F";

    public MedicalData(int id, Date birthdate, int idPat, @Sex String sex, String nationality, String job, String residence, boolean smoker, boolean alcoholic, boolean drugs) {
        this.id = id;
        this.birthdate = birthdate;
        this.idPat = idPat;
        this.sex = sex;
        this.nationality = nationality;
        this.job = job;
        this.residence = residence;
        this.smoker = smoker;
        this.alcoholism = alcoholic;
        this.drugs = drugs;
    }

    public int getId() {
        return id;
    }

    public Date getBirthdate() {
        return birthdate;
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
        return smoker;
    }

    public boolean isAlcoholic() {
        return alcoholism;
    }

    public boolean usesDrugs() {
        return drugs;
    }
}
