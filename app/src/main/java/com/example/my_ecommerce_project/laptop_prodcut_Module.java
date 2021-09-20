package com.example.my_ecommerce_project;

public class laptop_prodcut_Module {
    private String lap_cord;
    private String brand_name;
    private String lap_Description;
    private int  lap_price;
    private String  lap_AddDate;
    private int   lap_Discount;
    private String   lap_image;
    private String lap_data_Key;

    public laptop_prodcut_Module() {
    }

    public laptop_prodcut_Module(String lap_cord, String brand_name, String lap_Description, int lap_price, String lap_AddDate, int lap_Discount, String lap_image) {
        this.lap_cord = lap_cord;
        this.brand_name = brand_name;
        this.lap_Description = lap_Description;
        this.lap_price = lap_price;
        this.lap_AddDate = lap_AddDate;
        this.lap_Discount = lap_Discount;
        this.lap_image = lap_image;
    }

    public String getLap_image() {
        return lap_image;
    }

    public void setLap_image(String lap_image) {
        this.lap_image = lap_image;
    }

    public int getLap_Discount() {
        return lap_Discount;
    }

    public void setLap_Discount(int lap_Discount) {
        this.lap_Discount = lap_Discount;
    }

    public String getLap_AddDate() {
        return lap_AddDate;
    }

    public void setLap_AddDate(String lap_AddDate) {
        this.lap_AddDate = lap_AddDate;
    }

    public String getLap_cord() {
        return lap_cord;
    }

    public void setLap_cord(String lap_cord) {
        this.lap_cord = lap_cord;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getLap_Description() {
        return lap_Description;
    }

    public void setLap_Description(String lap_Description) {
        this.lap_Description = lap_Description;
    }


    public int getLap_price() {
        return lap_price;
    }

    public void setLap_price(int lap_price) {
        this.lap_price = lap_price;
    }

    public String getLap_data_Key() {
        return lap_data_Key;
    }

    public void setLap_data_Key(String lap_data_Key) {
        this.lap_data_Key = lap_data_Key;
    }
}
