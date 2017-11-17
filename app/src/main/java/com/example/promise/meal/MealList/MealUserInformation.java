package com.example.promise.meal.MealList;

/**
 * Created by promise on 11/7/17.
 */

public class MealUserInformation {
    private String  breakfast;
    private String lunch;
    private String dinner;
    private String date;

    public MealUserInformation(String date, int lunch, int breakfast, int dinner){

    }

    public MealUserInformation(String breakfast, String lunch, String dinner, String date) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.date = date;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}