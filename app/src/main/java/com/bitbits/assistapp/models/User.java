package com.bitbits.assistapp.models;

import android.media.Image;

/**
 * Model class for users
 * @author José Antonio Barranquero Fernández
 */
public class User {
    int id;
    String id_doc, name, surname, email;
    Patient patient;
    Nurse nurse;
    Image img;
    boolean active;

    public User(int id, String doc, String name, String surname, String email, Patient p, Nurse n, Image img, boolean active) {
        this.id = id;
        this.id_doc = doc;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.patient = p;
        this.nurse = n;
        this.img = img;
        this.active = active;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method which checks whether the user is a patient or not
     * @return true if it's a patient, false if it's a nurse
     * @version 1.0
     */
    public boolean isPatient() {
        return nurse==null;
    }
}
