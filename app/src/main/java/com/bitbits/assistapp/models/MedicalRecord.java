package com.bitbits.assistapp.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Class which defines medical records
 * @author José Antonio Barranquero Fernández
 */
public class MedicalRecord implements Serializable {
    int id;
    MedicalData data;
    String reason, antecedents;
    boolean hospitalised;
    Date date;

    public MedicalRecord(int id, MedicalData data,String reason, String antecedents, boolean hospitalised, Date date) {
        this.id = id;
        this.data = data;
        this.reason = reason;
        this.antecedents = antecedents;
        this.hospitalised = hospitalised;
        this.date = date;
    }

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
