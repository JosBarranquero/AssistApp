package com.bitbits.assistapp.models;

import java.util.Date;

/**
 * Class which defines medical records
 * @author José Antonio Barranquero Fernández
 */
public class MedicalRecord {
    int id;
    MedicalData data;
    String reason, antecedents;
    boolean hospitalised;
    Date date;

    public int getId() {
        return id;
    }

    public MedicalData getData() {
        return data;
    }

    public String getReason() {
        return reason;
    }

    public String getAntecedents() {
        return antecedents;
    }

    public boolean isHospitalised() {
        return hospitalised;
    }

    public Date getDate() {
        return date;
    }
}
