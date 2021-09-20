package com.example.my_ecommerce_project;

public class Order_final_admin_Model {
    private String order_allData;
    private String user_name;
    private String user_emali;
    private String user_location;
    private String user_flat_building;
    private String user_city_twon;
    private String user_phon;
    private String order_date;
    private int order_Total_price;

    private String oreder_key;

    public Order_final_admin_Model() {
    }

    public Order_final_admin_Model(String order_allData, String user_name, String user_emali, String user_location, String user_flat_building, String user_city_twon, String user_phon, String order_date, int order_Total_price) {
        this.order_allData = order_allData;
        this.user_name = user_name;
        this.user_emali = user_emali;
        this.user_location = user_location;
        this.user_flat_building = user_flat_building;
        this.user_city_twon = user_city_twon;
        this.user_phon = user_phon;
        this.order_date = order_date;
        this.order_Total_price = order_Total_price;
    }

    public String getOrder_allData() {
        return order_allData;
    }

    public void setOrder_allData(String order_allData) {
        this.order_allData = order_allData;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_emali() {
        return user_emali;
    }

    public void setUser_emali(String user_emali) {
        this.user_emali = user_emali;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public String getUser_flat_building() {
        return user_flat_building;
    }

    public void setUser_flat_building(String user_flat_building) {
        this.user_flat_building = user_flat_building;
    }

    public String getUser_city_twon() {
        return user_city_twon;
    }

    public void setUser_city_twon(String user_city_twon) {
        this.user_city_twon = user_city_twon;
    }

    public String getUser_phon() {
        return user_phon;
    }

    public void setUser_phon(String user_phon) {
        this.user_phon = user_phon;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public int getOrder_Total_price() {
        return order_Total_price;
    }

    public void setOrder_Total_price(int order_Total_price) {
        this.order_Total_price = order_Total_price;
    }

    public String getOreder_key() {
        return oreder_key;
    }

    public void setOreder_key(String oreder_key) {
        this.oreder_key = oreder_key;
    }
}
