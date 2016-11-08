package com.bitbits.assistapp.models;

/**
 * Class which defines a hospital
 * @author José Antonio Barranquero Fernández
 */
public class Hospital {
    int id;
    String name, address, phone;

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
