package com.bitbits.assistapp;

import com.bitbits.assistapp.models.MedicalData;
import com.bitbits.assistapp.models.MedicalRecord;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.User;

import java.util.ArrayList;

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
    private ArrayList<Message> unread;

    private User current;

    public synchronized static Repository getInstance() {
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
        unread = new ArrayList<>();
    }

    public void setCurrentUser(User u) {
        current = u;
    }

    public User getCurrentUser() {
        return current;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void writeMessage(Message m) {
        messages.add(m);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<MedicalData> getMedData() {
        return data;
    }

    public void setMedData(ArrayList<MedicalData> medData) {
        this.data = medData;
    }

    public ArrayList<MedicalRecord> getRecords() {
        return records;
    }

    public void setRecord(ArrayList<MedicalRecord> records) {
        this.records = records;
    }

    public void setUnread(ArrayList<Message> unread) {
        this.unread = unread;
    }

    public ArrayList<Message> getUnread() {
        return unread;
    }
}
