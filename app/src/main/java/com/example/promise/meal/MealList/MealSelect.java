package com.example.promise.meal.MealList;

/**
 * Created by promise on 11/8/17.
 */

public class MealSelect {
    String breakfast;
    String lunch;
    String dinner;
    //String date;

    public  MealSelect(){

    }

    public MealSelect(String breakfast, String lunch, String dinner) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        //this.date = date;
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


}
