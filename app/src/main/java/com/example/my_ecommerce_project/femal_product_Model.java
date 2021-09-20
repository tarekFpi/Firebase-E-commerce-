package com.example.my_ecommerce_project;

public class femal_product_Model   {
    private String pd_Cord;
    private String product_name;
    private String product_Desrcip;
    private int product_price;
    private String product_Image;
    private String current_date;
    private String pd_size;

    private String data_key;

    public femal_product_Model() {
    }

    public femal_product_Model(String pd_Cord, String product_name, String product_Desrcip,int product_price, String product_Image, String current_date, String pd_size) {
        this.pd_Cord = pd_Cord;
        this.product_name = product_name;
        this.product_Desrcip = product_Desrcip;
        this.product_price = product_price;
        this.product_Image = product_Image;
        this.current_date = current_date;
        this.pd_size = pd_size;
    }

    public String getPd_size() {
        return pd_size;
    }

    public void setPd_size(String pd_size) {
        this.pd_size = pd_size;
    }

    public String getProduct_Image() {
        return product_Image;
    }

    public void setProduct_Image(String product_Image) {
        this.product_Image = product_Image;
    }



    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getData_key() {
        return data_key;
    }

    public void setData_key(String data_key) {
        this.data_key = data_key;
    }

    public String getPd_Cord() {
        return pd_Cord;
    }

    public void setPd_Cord(String pd_Cord) {
        this.pd_Cord = pd_Cord;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_Desrcip() {
        return product_Desrcip;
    }

    public void setProduct_Desrcip(String product_Desrcip) {
        this.product_Desrcip = product_Desrcip;
    }


    public int getProduct_price() {
        return product_price;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
}

