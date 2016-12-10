package com.bitbits.assistapp.models;

import android.media.Image;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Class which defines a message
 * @author José Antonio Barranquero Fernández
 */
public class Message implements Serializable {
    int id;
    String content;
    Image img;
    Date date;
    User sender, receiptant;

    public Message(int id, String content, Image img, User sender, User receiptant) {
        this.id = id;
        this.content = content;
        this.img = img;
        this.date = Calendar.getInstance().getTime();
        this.sender = sender;
        this.receiptant = receiptant;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Image getImg() {
        return img;
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiptant() {
        return receiptant;
    }
}
