package com.example.my_ecommerce_project;

public class Cap_Category_Model {
    private String capCategory_id;
    private String capCategory_name;
    private String capCategory_Size;
    private String capCategory_image;
    private int capCategory_price;
    private int capCategory_quantity;

    private String user_gmail;
    private String capCategory_key;

    public Cap_Category_Model() {
    }

    public Cap_Category_Model(String capCategory_id, String capCategory_name, String capCategory_Size, String capCategory_image, int capCategory_price, int capCategory_quantity, String user_gmail) {
        this.capCategory_id = capCategory_id;
        this.capCategory_name = capCategory_name;
        this.capCategory_Size = capCategory_Size;
        this.capCategory_image = capCategory_image;
        this.capCategory_price = capCategory_price;
        this.capCategory_quantity = capCategory_quantity;
        this.user_gmail = user_gmail;
    }

    public String getUser_gmail() {
        return user_gmail;
    }

    public void setUser_gmail(String user_gmail) {
        this.user_gmail = user_gmail;
    }

    public String getCapCategory_key() {
        return capCategory_key;
    }

    public void setCapCategory_key(String capCategory_key) {
        this.capCategory_key = capCategory_key;
    }

    public String getCapCategory_image() {
        return capCategory_image;
    }

    public void setCapCategory_image(String capCategory_image) {
        this.capCategory_image = capCategory_image;
    }

    public String getCapCategory_id() {
        return capCategory_id;
    }

    public void setCapCategory_id(String capCategory_id) {
        this.capCategory_id = capCategory_id;
    }

    public String getCapCategory_name() {
        return capCategory_name;
    }

    public void setCapCategory_name(String capCategory_name) {
        this.capCategory_name = capCategory_name;
    }


    public String getCapCategory_Size() {
        return capCategory_Size;
    }

    public void setCapCategory_Size(String capCategory_Size) {
        this.capCategory_Size = capCategory_Size;
    }

    public int getCapCategory_price() {
        return capCategory_price;
    }

    public void setCapCategory_price(int capCategory_price) {
        this.capCategory_price = capCategory_price;
    }

    public int getCapCategory_quantity() {
        return capCategory_quantity;
    }

    public void setCapCategory_quantity(int capCategory_quantity) {
        this.capCategory_quantity = capCategory_quantity;
    }


}
