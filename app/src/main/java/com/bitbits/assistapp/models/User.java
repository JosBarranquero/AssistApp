package com.bitbits.assistapp.models;

import android.media.Image;

/**
 * Model class for users
 * @author José Antonio Barranquero Fernández
 */
public class User {
    int id, id_doc;
    String name, surname, email;
    Patient patient;
    Nurse nurse;
    Image img;
    boolean active;

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
