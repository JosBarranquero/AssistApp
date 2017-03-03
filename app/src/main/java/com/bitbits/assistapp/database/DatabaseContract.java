package com.bitbits.assistapp.database;

import android.provider.BaseColumns;

/**
 * Class which has the SQLite tables structure
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 *          AssistApp
 */
public class DatabaseContract {
    public static class SpecialityEntry implements BaseColumns {
        public static final String TABLE_NAME = "speciality";
        public static final String COLUMN_NAME = "name";
        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT NOT NULL)",
                TABLE_NAME,
                _ID,
                COLUMN_NAME);
        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        public static final String INSERT_DEFAULT = String.format("INSERT INTO %s (%s) VALUES (%s), (%s), (%s)",
                TABLE_NAME,
                COLUMN_NAME,
                "'Neurología'",
                "'Traumatología'",
                "'Odontología'");
    }

    public static class HospitalEntry implements BaseColumns {
        public static final String TABLE_NAME = "hospital";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PHONE = "phone";
        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL)",
                TABLE_NAME,
                _ID,
                COLUMN_NAME,
                COLUMN_ADDRESS,
                COLUMN_PHONE);
        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        public static final String INSERT_DEFAULT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (%s, %s, %s)",
                TABLE_NAME,
                COLUMN_NAME,
                COLUMN_ADDRESS,
                COLUMN_PHONE,
                "'Clínico'",
                "'Yo que sé'",
                "'953493349'");
    }

    public static class MedicalDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "medical_data";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_NATIONALITY = "nationality";
        public static final String COLUMN_JOB = "job";
        public static final String COLUMN_RESIDENCE = "residence";
        public static final String COLUMN_SEX = "sex";
        public static final String COLUMN_SMOKER = "smoker";
        public static final String COLUMN_ALCOHOLISM = "alcoholism";
        public static final String COLUMN_DRUGS = "drugs";
        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s BOOLEAN NOT NULL, " +
                "%s BOOLEAN NOT NULL, " +
                "%s BOOLEAN NOT NULL)",
                TABLE_NAME,
                _ID,
                COLUMN_DATE,
                COLUMN_NAME,
                COLUMN_SURNAME,
                COLUMN_NATIONALITY,
                COLUMN_JOB,
                COLUMN_RESIDENCE,
                COLUMN_SEX,
                COLUMN_SMOKER,
                COLUMN_ALCOHOLISM,
                COLUMN_DRUGS);
        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        public static final String INSERT_DEFAULT = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s,%s, %s, %s, %s) VALUES (%s, %s, %s, %s, %s, %s,%s, %s, %s, %s)",
                TABLE_NAME, COLUMN_DATE, COLUMN_NAME, COLUMN_SURNAME, COLUMN_SEX, COLUMN_NATIONALITY, COLUMN_JOB, COLUMN_RESIDENCE, COLUMN_SMOKER, COLUMN_ALCOHOLISM, COLUMN_DRUGS,
                "'1980-01-01'",
                "'Lourdes'", "'Rodríguez'", "'Mujer'", "'Español'", "'Profesora'", "'Calle Falsa 123'", 0, 1, 0);
    }

    public static class MedicalRecordEntry implements BaseColumns {
        public static final String TABLE_NAME = "medical_record";
        public static final String COLUMN_ID_MED_DATA = "id_med_data";
        public static final String REFERENCE_ID_MED_DATA = String.format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE RESTRICT", MedicalDataEntry.TABLE_NAME, MedicalDataEntry._ID);
        public static final String COLUMN_REASON = "reason";
        public static final String COLUMN_ANTECEDENTS = "antecedents";
        public static final String COLUMN_HOSPITALISED = "hospitalised";
        public static final String COLUMN_DATE = "date";
        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s INTEGER NOT NULL %s," +
                "%s TEXT NOT NULL," +
                "%s TEXT NOT NULL," +
                "%s BOOLEAN NOT NULL," +
                "%s TEXT NOT NULL)",
                TABLE_NAME,
                _ID,
                COLUMN_ID_MED_DATA, REFERENCE_ID_MED_DATA,
                COLUMN_REASON,
                COLUMN_ANTECEDENTS,
                COLUMN_HOSPITALISED,
                COLUMN_DATE);
        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        public static final String INSERT_DEFAULT = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (%s, %s, %s, %s, %s), (%s, %s, %s, %s, %s)",
                TABLE_NAME, COLUMN_ID_MED_DATA, COLUMN_REASON, COLUMN_ANTECEDENTS, COLUMN_HOSPITALISED, COLUMN_DATE,
                1, "'Dolor fuerte de barriga'", "'Ninguno'", 0, "'2017-01-01'",
                2, "'Desmayo'", "'Trabajo de alto estrés'", 1, "'2017-01-01'");
    }

    public static class PatientEntry implements BaseColumns {
        public static final String TABLE_NAME = "patient";
        public static final String COLUMN_HOSPITAL = "was_in";
        public static final String REFERENCE_ID_HOSPITAL = String.format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE RESTRICT", HospitalEntry.TABLE_NAME, HospitalEntry._ID);
        public static final String COLUMN_SPECIALITY = "speciality";
        public static final String REFERENCE_ID_SPECIALITY = String.format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE RESTRICT", SpecialityEntry.TABLE_NAME, SpecialityEntry._ID);
        public static final String COLUMN_MED_DATA = "data";
        public static final String REFERENCE_ID_MED_DATA = String.format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE RESTRICT", MedicalDataEntry.TABLE_NAME, MedicalDataEntry._ID);
        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s INTEGER NOT NULL %s," +
                "%s INTEGER NOT NULL %s," +
                "%s INTEGER NOT NULL %s)",
                TABLE_NAME,
                BaseColumns._ID,
                COLUMN_HOSPITAL, REFERENCE_ID_HOSPITAL,
                COLUMN_SPECIALITY, REFERENCE_ID_SPECIALITY,
                COLUMN_MED_DATA, REFERENCE_ID_MED_DATA);
        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        public static final String INSERT_DEFAULT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (%s, %s, %s)",
                TABLE_NAME, COLUMN_HOSPITAL, COLUMN_SPECIALITY, COLUMN_MED_DATA,
                1, 2, 1);
    }

    public static class NurseEntry implements BaseColumns {
        public static final String TABLE_NAME = "nurse";
        public static final String COLUMN_HOSPITAL = "works_in";
        public static final String REFERENCE_ID_HOSPITAL = String.format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE RESTRICT", HospitalEntry.TABLE_NAME, HospitalEntry._ID);
        public static final String COLUMN_SPECIALITY = "speciality";
        public static final String REFERENCE_ID_SPECIALITY = String.format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE RESTRICT", SpecialityEntry.TABLE_NAME, SpecialityEntry._ID);
        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s INTEGER NOT NULL %s," +
                "%s INTEGER NOT NULL %s)",
                TABLE_NAME,
                BaseColumns._ID,
                COLUMN_HOSPITAL, REFERENCE_ID_HOSPITAL,
                COLUMN_SPECIALITY, REFERENCE_ID_SPECIALITY);
        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        public static final String INSERT_DEFAULT = String.format("INSERT INTO %s (%s, %s) VALUES (%s, %s)",
                TABLE_NAME, COLUMN_HOSPITAL, COLUMN_SPECIALITY,
                1,2);
    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID_DOC = "id_doc";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_ID_PATIENT = "id_pat";
        public static final String REFERENCE_ID_PATIENT = String.format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE RESTRICT", PatientEntry.TABLE_NAME, BaseColumns._ID);
        public static final String COLUMN_ID_NURSE = "id_nur";
        public static final String REFERENCE_ID_NURSE = String.format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE RESTRICT", NurseEntry.TABLE_NAME, BaseColumns._ID);
        public static final String COLUMN_IMAGE = "img";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_ACTIVE = "active";
        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s INTEGER %s, " +
                "%s INTEGER %s, " +
                "%s BLOB, " +
                "%s TEXT NOT NULL, " +
                "%s BOOLEAN NOT NULL)",
                TABLE_NAME,
                BaseColumns._ID,
                COLUMN_ID_DOC,
                COLUMN_PASSWORD,
                COLUMN_NAME,
                COLUMN_SURNAME,
                COLUMN_ID_PATIENT,
                REFERENCE_ID_PATIENT,
                COLUMN_ID_NURSE,
                REFERENCE_ID_NURSE,
                COLUMN_IMAGE,
                COLUMN_EMAIL,
                COLUMN_ACTIVE);
        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        public static final String INSERT_DEFAULT = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s), (%s, %s, %s, %s, %s, %s, %s, %s, %s)",
                TABLE_NAME, COLUMN_ID_DOC, COLUMN_PASSWORD, COLUMN_NAME, COLUMN_SURNAME, COLUMN_ID_PATIENT, COLUMN_ID_NURSE, COLUMN_IMAGE, COLUMN_EMAIL, COLUMN_ACTIVE,
                "'12345678A'", "'Aa123456'", "'Lourdes'", "'Rodríguez'", 1, null, null, "'moronlu18@gmail.com'", 1,
                "'12345678B'", "'Aa123456'", "'José Antonio'", "'Barranquero'", null, 2, null, "'joseantbarranquero@gmail.com'", 1);
    }

    public static class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "message";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_SENDER = "sender";
        public static final String COLUMN_RECEIVER = "receiver";
        public static final String REFERENCE_ID_USER = String.format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE RESTRICT", UserEntry.TABLE_NAME, UserEntry._ID);
        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT," +
                "%s BLOB," +
                "%s TEXT NOT NULL," +
                "%s INTEGER NOT NULL %s," +
                "%s INTEGER NOT NULL %s)",
                TABLE_NAME,
                _ID,
                COLUMN_CONTENT,
                COLUMN_IMAGE,
                COLUMN_DATE,
                COLUMN_SENDER, REFERENCE_ID_USER,
                COLUMN_RECEIVER, REFERENCE_ID_USER);
        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        public static final String DEFAULT_SORT = _ID;
    }
}
