package com.bitbits.assistapp.models;

import java.io.Serializable;

/**
 * Class which defines a hospital
 * @author José Antonio Barranquero Fernández
 */
public class Hospital implements Serializable {
    int id;
    String name, address, phone;

    public Hospital(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress(){
        return this.address;
    }

    public String getPhone() {
        return this.phone;
    }
}
