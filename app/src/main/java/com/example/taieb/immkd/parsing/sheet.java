package com.example.taieb.immkd.parsing;

/**
 * Created by KHALED on 09/04/2015.
 */
public class sheet {
    private String Name;
    private int id;

    public sheet() {
    }

    public sheet(String name, int id) {
        Name = name;
        this.id = id;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
