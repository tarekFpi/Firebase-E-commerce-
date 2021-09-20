package com.example.my_ecommerce_project;

import java.util.HashMap;

public class laptop_CategoryModel {

  private String laptop_Id;
  private String lapt_name;
  private int lapt_quatity;
  private int lapt_price;
  private int lapt_discount;
  private String laptop_decpt;
  private String laptop_image;

    private String   user_gmail;

   private String laptop_DataKey;

    public laptop_CategoryModel() {
    }

    public laptop_CategoryModel(String laptop_Id, String lapt_name, int lapt_quatity, int lapt_price, int lapt_discount, String laptop_decpt, String laptop_image, String user_gmail) {
        this.laptop_Id = laptop_Id;
        this.lapt_name = lapt_name;
        this.lapt_quatity = lapt_quatity;
        this.lapt_price = lapt_price;
        this.lapt_discount = lapt_discount;
        this.laptop_decpt = laptop_decpt;
        this.laptop_image = laptop_image;
        this.user_gmail = user_gmail;
    }

    public String getUser_gmail() {
        return user_gmail;
    }

    public void setUser_gmail(String user_gmail) {
        this.user_gmail = user_gmail;
    }

    public String getLaptop_image() {
        return laptop_image;
    }

    public void setLaptop_image(String laptop_image) {
        this.laptop_image = laptop_image;
    }

    public String getLaptop_Id() {
        return laptop_Id;
    }

    public void setLaptop_Id(String laptop_Id) {
        this.laptop_Id = laptop_Id;
    }

    public String getLapt_name() {
        return lapt_name;
    }

    public void setLapt_name(String lapt_name) {
        this.lapt_name = lapt_name;
    }

    public int getLapt_quatity() {
        return lapt_quatity;
    }

    public void setLapt_quatity(int lapt_quatity) {
        this.lapt_quatity = lapt_quatity;
    }

    public int getLapt_price() {
        return lapt_price;
    }

    public void setLapt_price(int lapt_price) {
        this.lapt_price = lapt_price;
    }

    public int getLapt_discount() {
        return lapt_discount;
    }

    public void setLapt_discount(int lapt_discount) {
        this.lapt_discount = lapt_discount;
    }

    public String getLaptop_decpt() {
        return laptop_decpt;
    }

    public void setLaptop_decpt(String laptop_decpt) {
        this.laptop_decpt = laptop_decpt;
    }

    public String getLaptop_DataKey() {
        return laptop_DataKey;
    }

    public void setLaptop_DataKey(String laptop_DataKey) {
        this.laptop_DataKey = laptop_DataKey;
    }
}
