package com.example.my_ecommerce_project;

public class sig_inDataModel {
    private String user_name;
    private String user_Email;
    private String user_image;

    private String user_key;

    public sig_inDataModel() {
    }

    public sig_inDataModel(String user_name, String user_Email, String user_image) {
        this.user_name = user_name;
        this.user_Email = user_Email;
        this.user_image = user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_Email() {
        return user_Email;
    }

    public void setUser_Email(String user_Email) {
        this.user_Email = user_Email;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }
}
