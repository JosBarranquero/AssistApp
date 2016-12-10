package com.bitbits.assistapp.models;

import java.io.Serializable;

/**
 * Model class which defines nurses
 * @author José Antonio Barranquero Fernández
 */
public class Nurse implements Serializable {
    int id;
    Hospital works_in;
    Speciality speciality;

    public Nurse(int id, Hospital hospital, Speciality speciality) {
        this.id = id;
        this.works_in = hospital;
        this.speciality = speciality;
    }

    public int getId() {
        return id;
    }

    public Hospital getHospital() {
        return works_in;
    }

    public Speciality getSpeciality() {
        return speciality;
    }
}
