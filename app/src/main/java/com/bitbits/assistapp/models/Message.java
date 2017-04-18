package com.bitbits.assistapp.models;

import android.text.format.DateFormat;
import android.util.Log;

import com.bitbits.assistapp.AssistApp_Application;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class which defines a message
 *
 * @author José Antonio Barranquero Fernández
 */
public class Message implements Serializable {
    private int id;
    private String content;
    private String date;
    private int sender, receiver;

    public Message(String content, int sender, int receiver) {
        this.content = content;
        this.date = setCurrentDate();
        this.sender = sender;
        this.receiver = receiver;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    /**
     * Method which sets the Message date to the current date
     * @return The current date in yyyy-MM-dd HH:mm:ss format
     */
    private String setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        return year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
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

    public String getFormattedDate() {
        if (this.date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                Date date = format.parse(this.date);
                String dateFormat = DateFormat.getDateFormat(AssistApp_Application.getContext()).format(date) + " ";
                dateFormat += DateFormat.getTimeFormat(AssistApp_Application.getContext()).format(date);
                return dateFormat;
            } catch (ParseException e) {
                Log.e("Msg", e.getMessage());
            }
        }
        return null;
    }
}
