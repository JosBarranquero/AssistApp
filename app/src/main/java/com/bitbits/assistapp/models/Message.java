package com.bitbits.assistapp.models;

import android.media.Image;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Class which defines a message
 * @author José Antonio Barranquero Fernández
 */
public class Message {
    int id;
    String content;
    Image img;
    Date date;

    public Message(int id, String content, Image img) {
        this.id = id;
        this.content = content;
        this.img = img;
        this.date = Calendar.getInstance().getTime();
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

}
