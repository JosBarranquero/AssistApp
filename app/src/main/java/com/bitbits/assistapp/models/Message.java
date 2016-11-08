package com.bitbits.assistapp.models;

import android.media.Image;

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

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
