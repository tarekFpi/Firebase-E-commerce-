package com.example.my_ecommerce_project;

import java.util.HashMap;

public class order_againModel {

private  String user_pdName;
private   int user_pdprice;
private  String user_pdimage;
 private String user_pdCord;
 private String user_pd_decrpti;

 private String order_againKey;

    public order_againModel() {
    }

    public order_againModel(String user_pdName, int user_pdprice, String user_pdimage, String user_pdCord, String user_pd_decrpti) {
        this.user_pdName = user_pdName;
        this.user_pdprice = user_pdprice;
        this.user_pdimage = user_pdimage;
        this.user_pdCord = user_pdCord;
        this.user_pd_decrpti = user_pd_decrpti;
    }

    public String getUser_pd_decrpti() {
        return user_pd_decrpti;
    }

    public void setUser_pd_decrpti(String user_pd_decrpti) {
        this.user_pd_decrpti = user_pd_decrpti;
    }

    public String getUser_pdName() {
        return user_pdName;
    }

    public void setUser_pdName(String user_pdName) {
        this.user_pdName = user_pdName;
    }

    public int getUser_pdprice() {
        return user_pdprice;
    }

    public void setUser_pdprice(int user_pdprice) {
        this.user_pdprice = user_pdprice;
    }

    public String getUser_pdimage() {
        return user_pdimage;
    }

    public void setUser_pdimage(String user_pdimage) {
        this.user_pdimage = user_pdimage;
    }

    public String getUser_pdCord() {
        return user_pdCord;
    }

    public void setUser_pdCord(String user_pdCord) {
        this.user_pdCord = user_pdCord;
    }

    public String getOrder_againKey() {
        return order_againKey;
    }

    public void setOrder_againKey(String order_againKey) {
        this.order_againKey = order_againKey;
    }
}
