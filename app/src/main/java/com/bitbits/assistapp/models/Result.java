package com.bitbits.assistapp.models;

import java.util.ArrayList;

/**
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */
public class Result {
    boolean code;
    int status;
    String message;
    ArrayList<User> user;
    ArrayList<MedicalData> meddata;
    ArrayList<MedicalRecord> medrecord;
    ArrayList<Message> messages;

    public boolean getCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<User> getUser() {
        return user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }

    public ArrayList<MedicalData> getMeddata() {
        return meddata;
    }

    public void setMeddata(ArrayList<MedicalData> meddata) {
        this.meddata = meddata;
    }

    public ArrayList<MedicalRecord> getMedrecord() {
        return medrecord;
    }

    public void setMedrecord(ArrayList<MedicalRecord> medrecord) {
        this.medrecord = medrecord;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
