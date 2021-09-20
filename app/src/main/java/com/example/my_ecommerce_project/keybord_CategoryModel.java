package com.example.my_ecommerce_project;

public class keybord_CategoryModel {
    private String category_key_mouse_id;
    private String category_key_mouse_name;
    private String category_key_mouse_Image;
    private int category_key_mouse_price;
    private int category_key_mouse_quantity;
    private int category_key_mouse_discount;

    private String user_gmail;
    private String category_key_mouse_DataKey;

    public keybord_CategoryModel() {

    }

    public keybord_CategoryModel(String category_key_mouse_id, String category_key_mouse_name, String category_key_mouse_Image, int category_key_mouse_price, int category_key_mouse_quantity, int category_key_mouse_discount, String user_gmail) {
        this.category_key_mouse_id = category_key_mouse_id;
        this.category_key_mouse_name = category_key_mouse_name;
        this.category_key_mouse_Image = category_key_mouse_Image;
        this.category_key_mouse_price = category_key_mouse_price;
        this.category_key_mouse_quantity = category_key_mouse_quantity;
        this.category_key_mouse_discount = category_key_mouse_discount;
        this.user_gmail = user_gmail;
    }


    public String getUser_gmail() {
        return user_gmail;
    }

    public String getCategory_key_mouse_id() {
        return category_key_mouse_id;
    }

    public void setCategory_key_mouse_id(String category_key_mouse_id) {
        this.category_key_mouse_id = category_key_mouse_id;
    }

    public String getCategory_key_mouse_name() {
        return category_key_mouse_name;
    }

    public void setCategory_key_mouse_name(String category_key_mouse_name) {
        this.category_key_mouse_name = category_key_mouse_name;
    }


    public String getCategory_key_mouse_Image() {
        return category_key_mouse_Image;
    }

    public void setCategory_key_mouse_Image(String category_key_mouse_Image) {
        this.category_key_mouse_Image = category_key_mouse_Image;
    }

    public int getCategory_key_mouse_price() {
        return category_key_mouse_price;
    }

    public void setCategory_key_mouse_price(int category_key_mouse_price) {
        this.category_key_mouse_price = category_key_mouse_price;
    }

    public int getCategory_key_mouse_quantity() {
        return category_key_mouse_quantity;
    }

    public void setCategory_key_mouse_quantity(int category_key_mouse_quantity) {
        this.category_key_mouse_quantity = category_key_mouse_quantity;
    }

    public int getCategory_key_mouse_discount() {
        return category_key_mouse_discount;
    }

    public void setCategory_key_mouse_discount(int category_key_mouse_discount) {
        this.category_key_mouse_discount = category_key_mouse_discount;
    }

    public String getCategory_key_mouse_DataKey() {
        return category_key_mouse_DataKey;
    }

    public void setCategory_key_mouse_DataKey(String category_key_mouse_DataKey) {
        this.category_key_mouse_DataKey = category_key_mouse_DataKey;
    }
}
