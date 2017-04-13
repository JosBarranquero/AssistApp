package com.bitbits.assistapp.models;

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
    String password, idDoc, name, surname, email;
    String type;
    String img;

    @Retention(RetentionPolicy.RUNTIME)
    @StringDef({NURSE, PATIENT})
    public @interface Type {}
    public static final String NURSE = "Nurse";
    public static final String PATIENT = "Patient";

    public User(int id, String pass, String doc, String name, String surname, String email, String type, String img) {
        this.id = id;
        this.password = pass;
        this.idDoc = doc;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.type = type;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
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

    public String getImg() {
        return img.replace(" ", "%20");
    }

    public void setImg(String img) {
        this.img = img;
    }
}
