package com.bitbits.assistapp;

import com.bitbits.assistapp.models.MedicalData;
import com.bitbits.assistapp.models.MedicalRecord;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Singleton class which stores the data
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Repository {
    private static Repository myInstance;
    private ArrayList<User> users;
    private ArrayList<Message> messages;
    private ArrayList<MedicalData> data;
    private ArrayList<MedicalRecord> records;
    private User current;

    public static Repository getInstance() {
        if (myInstance == null) {
            myInstance = new Repository();
        }
        return myInstance;
    }

    private Repository() {
        users = new ArrayList<>();
        messages = new ArrayList<>();
        data = new ArrayList<>();
        records = new ArrayList<>();
    }

    public void putUser(User u) {
        users.add(u);
    }

    public void setCurrentUser(User u) {
        current = u;
    }

    public User getCurrentUser() {
        return current;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void writeMessage(Message m) {
        messages.add(m);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<MedicalData> getMedData() {
        return data;
    }

    public ArrayList<MedicalRecord> getRecords() {
        return records;
    }
}
