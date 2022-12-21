package com.example.ProjektSQL.Recycler;

import android.widget.SeekBar;
import android.widget.TextView;

public class zrecycler {
    String name;
    public String price;
    int image;
    TextView textView;
    SeekBar seekBar;
    String orgprice;
    public zrecycler(String name, String price, int image, TextView textView, SeekBar seekBar, String orgprice){
        this.price = price;
        this.name = name;
        this.image = image;
        this.textView = textView;
        this.seekBar = seekBar;
        this.orgprice = orgprice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public String getOrgprice() {
        return orgprice;
    }

    public void setOrgprice(String orgprice) {
        this.orgprice = orgprice;
    }
}
