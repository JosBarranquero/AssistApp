package com.bitbits.assistapp.models;

import android.media.Image;
import android.support.annotation.StringDef;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Model class for users
 * @author José Antonio Barranquero Fernández
 */
public class User implements Serializable {
    int id;
    String pass, id_doc, name, surname, email;
    String type;
    Image img;
    boolean active;

    @Retention(RetentionPolicy.RUNTIME)
    @StringDef({NURSE, PATIENT})
    public @interface Type {}
    public static final String NURSE = "Nurse";
    public static final String PATIENT = "Patient";

    public User(int id, String pass, String doc, String name, String surname, String email, String type, Image img, boolean active) {
        this.id = id;
        this.pass = pass;
        this.id_doc = doc;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.type = type;
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

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getId_doc() {
        return id_doc;
    }

    public void setId_doc(String id_doc) {
        this.id_doc = id_doc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
