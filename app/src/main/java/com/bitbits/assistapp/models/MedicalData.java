package com.bitbits.assistapp.models;

import java.util.Date;

/**
 * Class which defines a patient's medical data
 * @author José Antonio Barranquero Fernández
 */
public class MedicalData {
    int id;
    Date birthdate;
    String name, surname, sex, nationality, job, residence;
    boolean smoker, alcoholism, drugs;

    public int getId() {
        return id;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
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

    public boolean isAlcoholism() {
        return alcoholism;
    }

    public boolean isDrugs() {
        return drugs;
    }
}
