package com.alaa.ahmed.androidnetworklibraries.Model;

/**
 * Created by ahmed on 7/28/2016.
 */
public class Flower {
    String name;
    String photo;
    String price;

    public Flower(String name, String photo, String price) {
        this.name = name;
        this.photo = photo;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPrice() {
        return price;
    }
}
