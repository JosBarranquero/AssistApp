package com.bitbits.assistapp.models;

import android.media.Image;
import android.util.Log;

import com.bitbits.assistapp.AssistApp_Application;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class which defines a message
 * @author José Antonio Barranquero Fernández
 */
public class Message implements Serializable {
    int id;
    String content;
    String date;
    int sender, receiver;

    public Message(int id, String content, int sender, int receiver) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public int getSender() {
        return sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public String parseDate() {
        //TODO
        if (this.date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            Date date = null;
            try {
                date = simpleDateFormat.parse(this.date);
            } catch (ParseException e) {
                Log.e("Msg", e.getMessage());
            }
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(AssistApp_Application.getContext());
            return dateFormat.format(date);
        }
        return null;
    }
}
