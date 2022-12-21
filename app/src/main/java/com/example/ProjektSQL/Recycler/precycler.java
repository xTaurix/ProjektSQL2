package com.example.ProjektSQL.Recycler;

public class precycler {
    int price;
    String name;
    String login;
    public precycler(String name, int price, String login) {
        this.price = price;
        this.name = name;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
