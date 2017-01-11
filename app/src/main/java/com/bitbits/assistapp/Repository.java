package com.bitbits.assistapp;

import com.bitbits.assistapp.models.Hospital;
import com.bitbits.assistapp.models.MedicalData;
import com.bitbits.assistapp.models.MedicalRecord;
import com.bitbits.assistapp.models.Message;
import com.bitbits.assistapp.models.Nurse;
import com.bitbits.assistapp.models.Patient;
import com.bitbits.assistapp.models.Speciality;
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
    private ArrayList<Hospital> hospitals;
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
        hospitals = new ArrayList<>();
        data = new ArrayList<>();
        records = new ArrayList<>();

        Hospital clinico = new Hospital(1, "Clínico", "Yo que sé", "953493349");
        putHospitals(clinico);

        Speciality trauma = new Speciality(2, "Traumatología");

        MedicalData lourdesData = new MedicalData(1, Calendar.getInstance().getTime(), "Lourdes", "Rodríguez", MedicalData.FEM, "Español", "Profesora", "Calle Falsa 123", false, false, false);
        data.add(lourdesData);

        MedicalRecord lourdesRecord = new MedicalRecord(1, lourdesData, "Dolor fuerte de barriga", "Ninguno", false, Calendar.getInstance().getTime());
        records.add(lourdesRecord);

        Patient patient = new Patient(1, clinico, trauma, lourdesData);
        Nurse nurse = new Nurse(1, clinico, trauma);

        User lourdes = new User(1, "Aa123456", "12345678A", "Lourdes", "Rodríguez", "moronlu18@gmail.com", patient, null, null, true);
        User jose = new User(2, "Aa123456", "12345678B", "José Antonio", "Barranquero", "joseantbarranquero@gmail.com", null, nurse, null, true);

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

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void putHospitals(Hospital h) {
        hospitals.add(h);
    }

    public List<MedicalData> getMedData() {
        return data;
    }

    public ArrayList<MedicalRecord> getRecords() {
        return records;
    }
}
