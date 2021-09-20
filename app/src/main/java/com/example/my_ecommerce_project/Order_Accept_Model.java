package com.example.my_ecommerce_project;

public class Order_Accept_Model {

    private String order_getAllData;
    private String Acc_user_name;
    private String Acc_user_emali;
    private String Acc_user_location;
    private String Acc_user_flat_building;
    private String Acc_user_city_twon;
    private String Acc_user_phon;
    private String Accept_date;
    private String order_date;
    private String order_status;
    private int Accept_total;

    private String Acc_order_Key;

    public Order_Accept_Model() {
    }

    public Order_Accept_Model(String order_getAllData, String acc_user_name, String acc_user_emali, String acc_user_location, String acc_user_flat_building, String acc_user_city_twon, String acc_user_phon, String accept_date, String order_date, String order_status, int accept_total) {
        this.order_getAllData = order_getAllData;
        this. Acc_user_name = acc_user_name;
        this. Acc_user_emali = acc_user_emali;
        this. Acc_user_location = acc_user_location;
        this. Acc_user_flat_building = acc_user_flat_building;
        this. Acc_user_city_twon = acc_user_city_twon;
        this.Acc_user_phon = acc_user_phon;
        this. Accept_date = accept_date;
        this.order_date = order_date;
        this.order_status = order_status;
        this. Accept_total = accept_total;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getAcc_order_Key() {
        return Acc_order_Key;
    }

    public void setAcc_order_Key(String acc_order_Key) {
        Acc_order_Key = acc_order_Key;
    }

    public String getOrder_getAllData() {
        return order_getAllData;
    }

    public void setOrder_getAllData(String order_getAllData) {
        this.order_getAllData = order_getAllData;
    }

    public String getAcc_user_name() {
        return Acc_user_name;
    }

    public void setAcc_user_name(String acc_user_name) {
        Acc_user_name = acc_user_name;
    }

    public String getAcc_user_emali() {
        return Acc_user_emali;
    }

    public void setAcc_user_emali(String acc_user_emali) {
        Acc_user_emali = acc_user_emali;
    }

    public String getAcc_user_location() {
        return Acc_user_location;
    }

    public void setAcc_user_location(String acc_user_location) {
        Acc_user_location = acc_user_location;
    }

    public String getAcc_user_flat_building() {
        return Acc_user_flat_building;
    }

    public void setAcc_user_flat_building(String acc_user_flat_building) {
        Acc_user_flat_building = acc_user_flat_building;
    }

    public String getAcc_user_city_twon() {
        return Acc_user_city_twon;
    }

    public void setAcc_user_city_twon(String acc_user_city_twon) {
        Acc_user_city_twon = acc_user_city_twon;
    }

    public String getAcc_user_phon() {
        return Acc_user_phon;
    }

    public void setAcc_user_phon(String acc_user_phon) {
        Acc_user_phon = acc_user_phon;
    }

    public String getAccept_date() {
        return Accept_date;
    }

    public void setAccept_date(String accept_date) {
        Accept_date = accept_date;
    }

    public int getAccept_total() {
        return Accept_total;
    }

    public void setAccept_total(int accept_total) {
        Accept_total = accept_total;
    }

}
