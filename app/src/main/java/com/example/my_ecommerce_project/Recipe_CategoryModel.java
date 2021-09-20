package com.example.my_ecommerce_project;

public class Recipe_CategoryModel {
    private String category_recipe_id;
    private String category_recipe_name;
    private String category_recipe_image;
    private int category_recipe_price;
    private int category_recipe_discount;
    private int category_recipe_quanity;
    private String user_gmail;

    private String category_recipe_data_Key;

    public Recipe_CategoryModel() {
    }

    public Recipe_CategoryModel(String category_recipe_id, String category_recipe_name, String category_recipe_image, int category_recipe_price, int category_recipe_discount, int category_recipe_quanity, String user_gmail) {
        this.category_recipe_id = category_recipe_id;
        this.category_recipe_name = category_recipe_name;
        this.category_recipe_image = category_recipe_image;
        this.category_recipe_price = category_recipe_price;
        this.category_recipe_discount = category_recipe_discount;
        this.category_recipe_quanity = category_recipe_quanity;
        this.user_gmail = user_gmail;
    }

    public String getUser_gmail() {
        return user_gmail;
    }

    public void setUser_gmail(String user_gmail) {
        this.user_gmail = user_gmail;
    }

    public String getCategory_recipe_id() {
        return category_recipe_id;
    }

    public void setCategory_recipe_id(String category_recipe_id) {
        this.category_recipe_id = category_recipe_id;
    }

    public String getCategory_recipe_name() {
        return category_recipe_name;
    }

    public void setCategory_recipe_name(String category_recipe_name) {
        this.category_recipe_name = category_recipe_name;
    }

    public String getCategory_recipe_image() {
        return category_recipe_image;
    }

    public void setCategory_recipe_image(String category_recipe_image) {
        this.category_recipe_image = category_recipe_image;
    }

    public int getCategory_recipe_price() {
        return category_recipe_price;
    }

    public void setCategory_recipe_price(int category_recipe_price) {
        this.category_recipe_price = category_recipe_price;
    }

    public int getCategory_recipe_discount() {
        return category_recipe_discount;
    }

    public void setCategory_recipe_discount(int category_recipe_discount) {
        this.category_recipe_discount = category_recipe_discount;
    }

    public int getCategory_recipe_quanity() {
        return category_recipe_quanity;
    }

    public void setCategory_recipe_quanity(int category_recipe_quanity) {
        this.category_recipe_quanity = category_recipe_quanity;
    }

    public String getCategory_recipe_data_Key() {
        return category_recipe_data_Key;
    }

    public void setCategory_recipe_data_Key(String category_recipe_data_Key) {
        this.category_recipe_data_Key = category_recipe_data_Key;
    }
}
