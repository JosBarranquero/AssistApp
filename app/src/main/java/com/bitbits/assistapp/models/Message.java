package com.bitbits.assistapp.models;

import android.media.Image;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

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
        this.date = Calendar.getInstance().getTime().toString();
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
}
