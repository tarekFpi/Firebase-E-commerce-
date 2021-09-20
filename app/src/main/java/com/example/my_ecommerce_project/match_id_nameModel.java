package com.example.my_ecommerce_project;

public class match_id_nameModel {
    private String prodcunt_id;
    private String prodcunt_name;
    private String prodcunt_image;
    private String prodcunt_data_key;

    public match_id_nameModel() {
    }

    public match_id_nameModel(String prodcunt_id, String prodcunt_name, String prodcunt_image) {
        this.prodcunt_id = prodcunt_id;
        this.prodcunt_name = prodcunt_name;
        this.prodcunt_image = prodcunt_image;
    }

    public String getProdcunt_image() {
        return prodcunt_image;
    }

    public void setProdcunt_image(String prodcunt_image) {
        this.prodcunt_image = prodcunt_image;
    }

    public String getProdcunt_id() {
        return prodcunt_id;
    }

    public void setProdcunt_id(String prodcunt_id) {
        this.prodcunt_id = prodcunt_id;
    }

    public String getProdcunt_name() {
        return prodcunt_name;
    }

    public void setProdcunt_name(String prodcunt_name) {
        this.prodcunt_name = prodcunt_name;
    }

    public String getProdcunt_data_key() {
        return prodcunt_data_key;
    }

    public void setProdcunt_data_key(String prodcunt_data_key) {
        this.prodcunt_data_key = prodcunt_data_key;
    }
}
