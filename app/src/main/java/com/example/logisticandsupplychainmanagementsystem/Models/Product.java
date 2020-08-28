package com.example.logisticandsupplychainmanagementsystem.Models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Product implements Serializable{
    String ID;
    String Name;
    String Category;
    String Cost;
    String Time;
    String Stock;
    String Description;
    String Uri;

    public Product() {
    }

    public Product(String ID, String name, String category, String cost, String time, String stock, String description, String uri) {
        this.ID = ID;
        Name = name;
        Category = category;
        Cost = cost;
        Time = time;
        Stock = stock;
        Description = description;
        Uri = uri;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }
}
