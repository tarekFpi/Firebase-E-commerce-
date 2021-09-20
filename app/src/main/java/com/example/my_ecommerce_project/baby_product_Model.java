package com.example.my_ecommerce_project;

public class baby_product_Model {
    private String baby_pd_id;
    private String baby_pd_Name;
    private String baby_pd_Desript;
    private String baby_pd_image;
    private String baby_pd_size;
    private int baby_pd_price;
    private int baby_pd_discount;
    private String baby_pd_date;
    private String baby_pd_DataKey;

    public baby_product_Model() {
    }

    public baby_product_Model(String baby_pd_id, String baby_pd_Name, String baby_pd_Desript, String baby_pd_image, String baby_pd_size, int baby_pd_price,int baby_pd_discount, String baby_pd_date) {
        this.baby_pd_id = baby_pd_id;
        this.baby_pd_Name = baby_pd_Name;
        this.baby_pd_Desript = baby_pd_Desript;
        this.baby_pd_image = baby_pd_image;
        this.baby_pd_size = baby_pd_size;
        this.baby_pd_price = baby_pd_price;
        this.baby_pd_discount = baby_pd_discount;
        this.baby_pd_date = baby_pd_date;
    }

    public String getBaby_pd_date() {
        return baby_pd_date;
    }

    public void setBaby_pd_date(String baby_pd_date) {
        this.baby_pd_date = baby_pd_date;
    }

    public String getBaby_pd_size() {
        return baby_pd_size;
    }

    public void setBaby_pd_size(String baby_pd_size) {
        this.baby_pd_size = baby_pd_size;
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

    public String getBaby_pd_Desript() {
        return baby_pd_Desript;
    }

    public void setBaby_pd_Desript(String baby_pd_Desript) {
        this.baby_pd_Desript = baby_pd_Desript;
    }

    public String getBaby_pd_image() {
        return baby_pd_image;
    }

    public void setBaby_pd_image(String baby_pd_image) {
        this.baby_pd_image = baby_pd_image;
    }

    public int getBaby_pd_price() {
        return baby_pd_price;
    }

    public void setBaby_pd_price(int baby_pd_price) {
        this.baby_pd_price = baby_pd_price;
    }

    public int getBaby_pd_discount() {
        return baby_pd_discount;
    }

    public void setBaby_pd_discount(int baby_pd_discount) {
        this.baby_pd_discount = baby_pd_discount;
    }

    public String getBaby_pd_DataKey() {
        return baby_pd_DataKey;
    }

    public void setBaby_pd_DataKey(String baby_pd_DataKey) {
        this.baby_pd_DataKey = baby_pd_DataKey;
    }
}
