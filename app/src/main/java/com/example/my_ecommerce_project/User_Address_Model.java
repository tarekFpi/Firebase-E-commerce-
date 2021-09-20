package com.example.my_ecommerce_project;

public class User_Address_Model {

    private String user_name;
    private String user_email;
    private String user_location;
    private String user_flat_no;
    private String user_city;
    private String user_phon;
    private String DataAll;
    private int prod_TotalPrice;
    private String current_date;
    private int Shipping_rate;

  /*  private String prod_name;
    private int prod_price;
    private int prod_quantity;
    private String prod_size;*/


    private String user_address_key;

    public User_Address_Model() {

    }

    public User_Address_Model(String user_name, String user_email, String user_location, String user_flat_no, String user_city, String user_phon, String dataAll, int prod_TotalPrice, String current_date, int shipping_rate) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_location = user_location;
        this.user_flat_no = user_flat_no;
        this.user_city = user_city;
        this.user_phon = user_phon;
        DataAll = dataAll;
        this.prod_TotalPrice = prod_TotalPrice;
        this.current_date = current_date;
        Shipping_rate = shipping_rate;
    }

    public int getShipping_rate() {
        return Shipping_rate;
    }

    public void setShipping_rate(int shipping_rate) {
        Shipping_rate = shipping_rate;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public String getUser_flat_no() {
        return user_flat_no;
    }

    public void setUser_flat_no(String user_flat_no) {
        this.user_flat_no = user_flat_no;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_phon() {
        return user_phon;
    }

    public void setUser_phon(String user_phon) {
        this.user_phon = user_phon;
    }

    public String getDataAll() {
        return DataAll;
    }

    public void setDataAll(String dataAll) {
        DataAll = dataAll;
    }

    public int getProd_TotalPrice() {
        return prod_TotalPrice;
    }

    public void setProd_TotalPrice(int prod_TotalPrice) {
        this.prod_TotalPrice = prod_TotalPrice;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getUser_address_key() {
        return user_address_key;
    }

    public void setUser_address_key(String user_address_key) {
        this.user_address_key = user_address_key;
    }
}
