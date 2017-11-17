package com.example.promise.meal.CreateProfile;

/**
 * Created by promise on 11/4/17.
 */

public class ProfileInfo {

    private String name;
    private String email;
    private String phone_num;

    public ProfileInfo(){

    }

    public ProfileInfo(String email, String name, String phone_num) {
        this.email = email;
        this.name = name;
        this.phone_num = phone_num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

}
