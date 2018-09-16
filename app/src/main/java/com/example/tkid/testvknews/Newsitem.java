package com.example.tkid.testvknews;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

public class Newsitem {

    public String text,imageURL;

    public Newsitem(String text, String imageURL) {
        this.text = text;
        this.imageURL = imageURL;
    }

    public String getText() {
        return text;
    }

    public String getImageURL() {
        return imageURL;
    }

}
