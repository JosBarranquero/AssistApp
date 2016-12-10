package com.bitbits.assistapp.models;

import java.io.Serializable;

/**
* Model class which defines nurses
* @author José Antonio Barranquero Fernández
*/
public class Patient implements Serializable {
    int id;
    Hospital was_in;
    Speciality speciality;
    MedicalData data;

    public Patient(int id, Hospital h, Speciality s, MedicalData d) {
        this.id = id;
        this.was_in = h;
        this.speciality = s;
        this.data = d;
    }

    public int getId() {
        return id;
    }

    public Hospital getHospital() {
        return was_in;
    }

    public MedicalData getData() {
        return data;
    }

    public Speciality getSpeciality() {
        return speciality;
    }
}
