package com.example.my_ecommerce_project;

public class phon_Category_Model {
    private String phon_category_id;
    private String phon_category_Name;
    private String phon_category_image;
    private int phon_category_price;
    private int phon_category_quantity;
    private int phon_category_discount;
    private String user_gmail;
    private String phon_category_key;

    public phon_Category_Model() {
    }

    public phon_Category_Model(String phon_category_id, String phon_category_Name, String phon_category_image, int phon_category_price, int phon_category_quantity, int phon_category_discount, String user_gmail) {
        this.phon_category_id = phon_category_id;
        this.phon_category_Name = phon_category_Name;
        this.phon_category_image = phon_category_image;
        this.phon_category_price = phon_category_price;
        this.phon_category_quantity = phon_category_quantity;
        this.phon_category_discount = phon_category_discount;
        this.user_gmail = user_gmail;
    }

    public String getUser_gmail() {
        return user_gmail;
    }

    public void setUser_gmail(String user_gmail) {
        this.user_gmail = user_gmail;
    }

    public String getPhon_category_id() {
        return phon_category_id;
    }

    public void setPhon_category_id(String phon_category_id) {
        this.phon_category_id = phon_category_id;
    }

    public String getPhon_category_Name() {
        return phon_category_Name;
    }

    public void setPhon_category_Name(String phon_category_Name) {
        this.phon_category_Name = phon_category_Name;
    }

    public String getPhon_category_image() {
        return phon_category_image;
    }

    public void setPhon_category_image(String phon_category_image) {
        this.phon_category_image = phon_category_image;
    }

    public int getPhon_category_price() {
        return phon_category_price;
    }

    public void setPhon_category_price(int phon_category_price) {
        this.phon_category_price = phon_category_price;
    }

    public int getPhon_category_quantity() {
        return phon_category_quantity;
    }

    public void setPhon_category_quantity(int phon_category_quantity) {
        this.phon_category_quantity = phon_category_quantity;
    }

    public int getPhon_category_discount() {
        return phon_category_discount;
    }

    public void setPhon_category_discount(int phon_category_discount) {
        this.phon_category_discount = phon_category_discount;
    }

    public String getPhon_category_key() {
        return phon_category_key;
    }

    public void setPhon_category_key(String phon_category_key) {
        this.phon_category_key = phon_category_key;
    }
}
