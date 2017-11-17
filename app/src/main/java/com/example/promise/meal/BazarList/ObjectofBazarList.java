package com.example.promise.meal.BazarList;

import java.text.DateFormat;
import java.util.Calendar;


public class ObjectofBazarList {
    private String date;
    private String name;
    private String cost;
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();

    public ObjectofBazarList(String date, String name, String cost) {
        this.date = date;
        this.name = name;
        this.cost = cost;


    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "ObjectofBazarList{" +
                "date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
