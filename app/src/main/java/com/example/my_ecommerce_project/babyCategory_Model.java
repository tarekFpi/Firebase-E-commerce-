package com.example.my_ecommerce_project;

public class babyCategory_Model {
    private String baby_pd_id;
    private String baby_pd_Name;
    private String baby_pd_image;
    private String baby_pd_size;
    private int baby_pd_price;
    private int baby_pd_quantity;
    private int baby_pd_discount;
    private String user_gmail;
    private String baby_pdCategory_Key;

    public babyCategory_Model() {
    }

    public babyCategory_Model(String baby_pd_id, String baby_pd_Name, String baby_pd_image, String baby_pd_size, int baby_pd_price, int baby_pd_quantity, int baby_pd_discount, String user_gmail) {
        this.baby_pd_id = baby_pd_id;
        this.baby_pd_Name = baby_pd_Name;
        this.baby_pd_image = baby_pd_image;
        this.baby_pd_size = baby_pd_size;
        this.baby_pd_price = baby_pd_price;
        this.baby_pd_quantity = baby_pd_quantity;
        this.baby_pd_discount = baby_pd_discount;
        this.user_gmail = user_gmail;
    }

    public String getUser_gmail() {
        return user_gmail;
    }

    public void setUser_gmail(String user_gmail) {
        this.user_gmail = user_gmail;
    }

    public String getBaby_pd_size() {
        return baby_pd_size;
    }

    public void setBaby_pd_size(String baby_pd_size) {
        this.baby_pd_size = baby_pd_size;
    }

    public String getBaby_pd_image() {
        return baby_pd_image;
    }

    public void setBaby_pd_image(String baby_pd_image) {
        this.baby_pd_image = baby_pd_image;
    }

    public String getBaby_pd_id() {
        return baby_pd_id;
    }

    public void setBaby_pd_id(String baby_pd_id) {
        this.baby_pd_id = baby_pd_id;
    }

    public String getBaby_pd_Name() {
        return baby_pd_Name;
    }

    public void setBaby_pd_Name(String baby_pd_Name) {
        this.baby_pd_Name = baby_pd_Name;
    }

    public int getBaby_pd_price() {
        return baby_pd_price;
    }

    public void setBaby_pd_price(int baby_pd_price) {
        this.baby_pd_price = baby_pd_price;
    }

    public int getBaby_pd_quantity() {
        return baby_pd_quantity;
    }

    public void setBaby_pd_quantity(int baby_pd_quantity) {
        this.baby_pd_quantity = baby_pd_quantity;
    }

    public int getBaby_pd_discount() {
        return baby_pd_discount;
    }

    public void setBaby_pd_discount(int baby_pd_discount) {
        this.baby_pd_discount = baby_pd_discount;
    }

    public String getBaby_pdCategory_Key() {
        return baby_pdCategory_Key;
    }

    public void setBaby_pdCategory_Key(String baby_pdCategory_Key) {
        this.baby_pdCategory_Key = baby_pdCategory_Key;
    }
}
