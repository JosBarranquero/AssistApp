package com.bitbits.assistapp;

import android.media.Image;

import com.bitbits.assistapp.models.Hospital;
import com.bitbits.assistapp.models.MedicalData;
import com.bitbits.assistapp.models.Patient;
import com.bitbits.assistapp.models.Speciality;
import com.bitbits.assistapp.models.User;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Singleton class which stores the data
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class Repository {
    private static Repository ourInstance;
    private static ArrayList<User> users;

    public static Repository getInstance() {
        if (ourInstance == null) {
            ourInstance = new Repository();
            users = new ArrayList<>();
        }
        return ourInstance;
    }

    private Repository() {
        putUser(new User(1, "00000000A", "Francisco", "Fernández", "pacofer@bitbits.com", new Patient(1, new Hospital(1, "Clínico", "Yo que sé", "953493349"), new Speciality(1, "Neurología"), new MedicalData(1, Calendar.getInstance().getTime(), "Francismo", "Fernández", "Masculino", "Español", "Parado", "Calle Falsa 123", true, true, true)), null, null, true));
    }

    public void putUser(User u){
        users.add(u);
    }
}
