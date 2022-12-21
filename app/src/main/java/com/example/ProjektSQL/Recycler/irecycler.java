package com.example.ProjektSQL.Recycler;

public class irecycler {
    String name;
    String price;
    int image;
        public irecycler(String name, String price, int image){
            this.price = price;
            this.name = name;
            this.image = image;
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

    public void setImage(int image) {
        this.image = image;
    }
}
