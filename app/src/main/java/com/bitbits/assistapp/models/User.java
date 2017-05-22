package com.bitbits.assistapp.models;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Model class for users
 *
 * @author José Antonio Barranquero Fernández
 */
public class User implements Serializable {
    private int id;
    private String password, idDoc, name, surname, email;
    private String type;
    private String img;
    private String apikey;

    public static final String NURSE = "Nurse";
    public static final String PATIENT = "Patient";

    /**
     * Comparators, so the users list can be sorted by name or surname
     */
    public static final Comparator<User> NAME_COMPARATOR = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    public static final Comparator<User> SURNAME_COMPARATOR = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getSurname().compareTo(o2.getSurname());
        }
    };

    public User(int id, String pass, String doc, String name, String surname, String email, String type, String img, String apikey) {
        this.id = id;
        this.password = pass;
        this.idDoc = doc;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.type = type;
        this.img = img;
        this.apikey = apikey;
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

    public String getWholeName() {
        return this.name + " " + this.surname;
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

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
}
