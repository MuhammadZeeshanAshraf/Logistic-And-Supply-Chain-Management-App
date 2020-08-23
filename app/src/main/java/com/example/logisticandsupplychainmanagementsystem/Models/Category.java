package com.example.logisticandsupplychainmanagementsystem.Models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Category implements Serializable{
    private String Id;
    private String Name;

    public Category() {
    }

    public Category(String id, String name) {
        Id = id;
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
