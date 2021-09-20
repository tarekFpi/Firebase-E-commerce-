package com.example.my_ecommerce_project;

public class shose_category_Add {
    private String pd_id;
    private String category_Shose_name;
    private String category_Shose_image;
    private String category_Shose_size;
    private int category_Shose_price;
    private int category_Shose_discount;
    private int category_Shose_quantity;

    private String user_gmail;
    private String category_shose_Key;

    public shose_category_Add() {
    }

    public shose_category_Add(String pd_id, String category_Shose_name, String category_Shose_image, String category_Shose_size, int category_Shose_price, int category_Shose_discount, int category_Shose_quantity, String user_gmail) {
        this.pd_id = pd_id;
        this.category_Shose_name = category_Shose_name;
        this.category_Shose_image = category_Shose_image;
        this.category_Shose_size = category_Shose_size;
        this.category_Shose_price = category_Shose_price;
        this.category_Shose_discount = category_Shose_discount;
        this.category_Shose_quantity = category_Shose_quantity;
        this.user_gmail = user_gmail;
    }


    public String getUser_gmail() {
        return user_gmail;
    }

    public void setUser_gmail(String user_gmail) {
        this.user_gmail = user_gmail;
    }

    public String getPd_id() {
        return pd_id;
    }

    public void setPd_id(String pd_id) {
        this.pd_id = pd_id;
    }

    public String getCategory_Shose_name() {
        return category_Shose_name;
    }

    public void setCategory_Shose_name(String category_Shose_name) {
        this.category_Shose_name = category_Shose_name;
    }


    public String getCategory_Shose_image() {
        return category_Shose_image;
    }

    public void setCategory_Shose_image(String category_Shose_image) {
        this.category_Shose_image = category_Shose_image;
    }

    public String getCategory_Shose_size() {
        return category_Shose_size;
    }

    public void setCategory_Shose_size(String category_Shose_size) {
        this.category_Shose_size = category_Shose_size;
    }

    public int getCategory_Shose_price() {
        return category_Shose_price;
    }

    public void setCategory_Shose_price(int category_Shose_price) {
        this.category_Shose_price = category_Shose_price;
    }

    public int getCategory_Shose_discount() {
        return category_Shose_discount;
    }

    public void setCategory_Shose_discount(int category_Shose_discount) {
        this.category_Shose_discount = category_Shose_discount;
    }

    public int getCategory_Shose_quantity() {
        return category_Shose_quantity;
    }

    public void setCategory_Shose_quantity(int category_Shose_quantity) {
        this.category_Shose_quantity = category_Shose_quantity;
    }

    public String getCategory_shose_Key() {
        return category_shose_Key;
    }

    public void setCategory_shose_Key(String category_shose_Key) {
        this.category_shose_Key = category_shose_Key;
    }
}
