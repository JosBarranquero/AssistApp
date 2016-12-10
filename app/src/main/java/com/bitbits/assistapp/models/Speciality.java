package com.bitbits.assistapp.models;

import java.io.Serializable;

/**
 * Class which defines a medical speciality
 * @author José Antonio Barranquero Fernández
 */
public class Speciality implements Serializable {
    int id;
    String name;

    public Speciality(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
