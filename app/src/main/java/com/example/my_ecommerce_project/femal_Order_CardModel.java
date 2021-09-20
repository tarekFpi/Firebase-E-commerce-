package com.example.my_ecommerce_project;

public class femal_Order_CardModel {
    private String pd_cord;
    private String pd_name;
    private int pd_price;
    private int pd_quantity;
    private String pd_size;
    private int  pd_total_Price;

    private String order_key;

    public femal_Order_CardModel() {
    }

    public femal_Order_CardModel(String pd_cord, String pd_name, int pd_price, int pd_quantity, String pd_size, int pd_total_Price) {
        this.pd_cord = pd_cord;
        this.pd_name = pd_name;
        this.pd_price = pd_price;
        this.pd_quantity = pd_quantity;
        this.pd_size = pd_size;
        this.pd_total_Price = pd_total_Price;
    }

    public String getPd_cord() {
        return pd_cord;
    }

    public void setPd_cord(String pd_cord) {
        this.pd_cord = pd_cord;
    }

    public String getPd_name() {
        return pd_name;
    }

    public void setPd_name(String pd_name) {
        this.pd_name = pd_name;
    }

    public int getPd_price() {
        return pd_price;
    }

    public void setPd_price(int pd_price) {
        this.pd_price = pd_price;
    }

    public int getPd_quantity() {
        return pd_quantity;
    }

    public void setPd_quantity(int pd_quantity) {
        this.pd_quantity = pd_quantity;
    }

    public String getPd_size() {
        return pd_size;
    }

    public void setPd_size(String pd_size) {
        this.pd_size = pd_size;
    }

    public int getPd_total_Price() {
        return pd_total_Price;
    }

    public void setPd_total_Price(int pd_total_Price) {
        this.pd_total_Price = pd_total_Price;
    }

    public String getOrder_key() {
        return order_key;
    }

    public void setOrder_key(String order_key) {
        this.order_key = order_key;
    }
}
