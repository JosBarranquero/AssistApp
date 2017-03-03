package com.bitbits.assistapp.models;

import android.media.Image;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Model class for users
 *
 * @author José Antonio Barranquero Fernández
 */
public class User implements Serializable {
    int id;
    String pass, id_doc, name, surname, email;
    Patient patient;
    Nurse nurse;
    Image img;
    boolean active;

    public User(int id, String pass, String doc, String name, String surname, String email, Patient p, Nurse n, Image img, boolean active) {
        this.id = id;
        this.pass = pass;
        this.id_doc = doc;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.patient = p;
        this.nurse = n;
        this.img = img;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public String getId_doc() {
        return id_doc;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    /**
     * Method which checks whether the user is a patient or not
     * @return true if it's a patient, false if it's a nurse
     * @version 1.0
     */
    public boolean isPatient() {
        return nurse == null;
    }

    public boolean isActive() {
        return this.active;
    }
}
