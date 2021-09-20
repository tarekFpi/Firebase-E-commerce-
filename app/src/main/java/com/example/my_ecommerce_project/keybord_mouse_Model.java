package com.example.my_ecommerce_project;

public class keybord_mouse_Model {
   private String keyMouse_id;
   private String keyMouse_name;
   private String keyMouse_Descrpt;
   private String keyMouse_image;
   private int keyMouse_price;
   private int keyMouse_discount;
   private String keyMouse_Date;

   private String keyMouse_Data_Key;

    public keybord_mouse_Model() {
    }

    public keybord_mouse_Model(String keyMouse_id, String keyMouse_name, String keyMouse_Descrpt, String keyMouse_image, int keyMouse_price, int keyMouse_discount, String keyMouse_Date) {
        this.keyMouse_id = keyMouse_id;
        this.keyMouse_name = keyMouse_name;
        this.keyMouse_Descrpt = keyMouse_Descrpt;
        this.keyMouse_image = keyMouse_image;
        this.keyMouse_price = keyMouse_price;
        this.keyMouse_discount = keyMouse_discount;
        this.keyMouse_Date = keyMouse_Date;
    }

    public String getKeyMouse_image() {
        return keyMouse_image;
    }

    public void setKeyMouse_image(String keyMouse_image) {
        this.keyMouse_image = keyMouse_image;
    }

    public String getKeyMouse_id() {
        return keyMouse_id;
    }

    public void setKeyMouse_id(String keyMouse_id) {
        this.keyMouse_id = keyMouse_id;
    }

    public String getKeyMouse_name() {
        return keyMouse_name;
    }

    public void setKeyMouse_name(String keyMouse_name) {
        this.keyMouse_name = keyMouse_name;
    }

    public String getKeyMouse_Descrpt() {
        return keyMouse_Descrpt;
    }

    public void setKeyMouse_Descrpt(String keyMouse_Descrpt) {
        this.keyMouse_Descrpt = keyMouse_Descrpt;
    }

    public int getKeyMouse_price() {
        return keyMouse_price;
    }

    public void setKeyMouse_price(int keyMouse_price) {
        this.keyMouse_price = keyMouse_price;
    }

    public int getKeyMouse_discount() {
        return keyMouse_discount;
    }

    public void setKeyMouse_discount(int keyMouse_discount) {
        this.keyMouse_discount = keyMouse_discount;
    }

    public String getKeyMouse_Date() {
        return keyMouse_Date;
    }

    public void setKeyMouse_Date(String keyMouse_Date) {
        this.keyMouse_Date = keyMouse_Date;
    }

    public String getKeyMouse_Data_Key() {
        return keyMouse_Data_Key;
    }

    public void setKeyMouse_Data_Key(String keyMouse_Data_Key) {
        this.keyMouse_Data_Key = keyMouse_Data_Key;
    }
}
