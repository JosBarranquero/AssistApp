package com.bitbits.assistapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.bitbits.assistapp.AssistApp_Application;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "AssistApp.db";
    private static DatabaseHelper databaseHelper;
    private AtomicInteger mOpenCounter;
    private SQLiteDatabase mDatabase;
    private Context context;

    private DatabaseHelper() {
        super(AssistApp_Application.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        mOpenCounter = new AtomicInteger();
    }

    public static synchronized DatabaseHelper getInstance() {
        if (databaseHelper == null)
            databaseHelper = new DatabaseHelper();
        return databaseHelper;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1)
            mDatabase = getWritableDatabase();
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0)
            mDatabase.close();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(!db.isReadOnly())
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                db.setForeignKeyConstraintsEnabled(true);
            else
                db.execSQL("PRAGMA foreign_keys = ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();

            db.execSQL(DatabaseContract.SpecialityEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.HospitalEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.MedicalDataEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.MedicalRecordEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.PatientEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.NurseEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.UserEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.MessageEntry.SQL_CREATE_ENTRIES);

            db.execSQL(DatabaseContract.SpecialityEntry.INSERT_DEFAULT);
            db.execSQL(DatabaseContract.HospitalEntry.INSERT_DEFAULT);
            db.execSQL(DatabaseContract.MedicalDataEntry.INSERT_DEFAULT);
            db.execSQL(DatabaseContract.MedicalRecordEntry.INSERT_DEFAULT);
            db.execSQL(DatabaseContract.PatientEntry.INSERT_DEFAULT);
            db.execSQL(DatabaseContract.NurseEntry.INSERT_DEFAULT);
            db.execSQL(DatabaseContract.UserEntry.INSERT_DEFAULT);

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.wtf("DB ERROR", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.beginTransaction();
            db.execSQL(DatabaseContract.SpecialityEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.HospitalEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.MedicalDataEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.MedicalRecordEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.PatientEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.NurseEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.UserEntry.SQL_DELETE_ENTRIES);
            db.execSQL(DatabaseContract.MessageEntry.SQL_DELETE_ENTRIES);
            onCreate(db);
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.wtf("DB ERROR", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, newVersion, oldVersion);
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public SQLiteDatabase open() {
        return getWritableDatabase();
    }
}
