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

    public MedicalData(int id, Date birthdate, String name, String surname, String sex, String nationality, String job, String residence, boolean smoker, boolean alcoholic, boolean drugs) {
        this.id = id;
        this.birthdate = birthdate;
        this.name = name;
        this.surname = surname;
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

    public boolean isAlcoholic() {
        return alcoholism;
    }

    public boolean usesDrugs() {
        return drugs;
    }
}
