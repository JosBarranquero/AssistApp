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

        MedicalData lourdesData = new MedicalData(1, Calendar.getInstance().getTime(), "Lourdes", "Rodríguez", MedicalData.FEM, "Español", "Profesora", "Calle Falsa 123", false, true, false);
        data.add(lourdesData);

        MedicalRecord lourdesRecord = new MedicalRecord(1, lourdesData, "Dolor fuerte de barriga", "Ninguno", false, Calendar.getInstance().getTime());
        MedicalRecord lourdesRecord2 = new MedicalRecord(2, lourdesData, "Desmayo", "Trabajo de alto estrés", true, Calendar.getInstance().getTime());
        records.add(lourdesRecord);
        records.add(lourdesRecord2);

        User lourdes = new User(1, "Aa123456", "12345678A", "Lourdes", "Rodríguez", "moronlu18@gmail.com", User.PATIENT, null, true);
        User jose = new User(2, "Aa123456", "12345678B", "José Antonio", "Barranquero", "joseantbarranquero@gmail.com", User.NURSE, null, true);

        putUser(lourdes);
        putUser(jose);
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

    public List<User> getUser() {
        return users;
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
