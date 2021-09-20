package com.example.my_ecommerce_project;

public class shose_pdModel {
  private   String shose_pd_id;
    private  String shose_pd_name;
    private  String shose_Decpt;
    private String shose_size;
    private String shose_image;

    private int shose_price;
    private int shose_dicount;
    private String current_date;
    private String shose_product_key;

    public shose_pdModel() {
    }

    public shose_pdModel(String shose_pd_id, String shose_pd_name, String shose_Decpt, String shose_size, String shose_image, int shose_price, int shose_dicount, String current_date) {
        this.shose_pd_id = shose_pd_id;
        this.shose_pd_name = shose_pd_name;
        this.shose_Decpt = shose_Decpt;
        this.shose_size = shose_size;
        this.shose_image = shose_image;
        this.shose_price = shose_price;
        this.shose_dicount = shose_dicount;
        this.current_date = current_date;
    }

    public String getShose_product_key() {
        return shose_product_key;
    }

    public void setShose_product_key(String shose_product_key) {
        this.shose_product_key = shose_product_key;
    }

    public String getShose_image() {
        return shose_image;
    }

    public void setShose_image(String shose_image) {
        this.shose_image = shose_image;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getShose_pd_id() {
        return shose_pd_id;
    }

    public void setShose_pd_id(String shose_pd_id) {
        this.shose_pd_id = shose_pd_id;
    }

    public String getShose_pd_name() {
        return shose_pd_name;
    }

    public void setShose_pd_name(String shose_pd_name) {
        this.shose_pd_name = shose_pd_name;
    }

    public String getShose_Decpt() {
        return shose_Decpt;
    }

    public void setShose_Decpt(String shose_Decpt) {
        this.shose_Decpt = shose_Decpt;
    }

    public String getShose_size() {
        return shose_size;
    }

    public void setShose_size(String shose_size) {
        this.shose_size = shose_size;
    }

    public int getShose_price() {
        return shose_price;
    }

    public void setShose_price(int shose_price) {
        this.shose_price = shose_price;
    }


    public int getShose_dicount() {
        return shose_dicount;
    }

    public void setShose_dicount(int shose_dicount) {
        this.shose_dicount = shose_dicount;
    }


}
