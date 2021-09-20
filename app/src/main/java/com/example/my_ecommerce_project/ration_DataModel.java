package com.example.my_ecommerce_project;

public class ration_DataModel {
    String pd_cord;
    String pd_name;
    String pd_image;
    float pd_ratig_value;
    String  ratig_dataKey;

    public ration_DataModel() {
    }

    public ration_DataModel(String pd_cord, String pd_name, String pd_image, float pd_ratig_value) {
        this.pd_cord = pd_cord;
        this.pd_name = pd_name;
        this.pd_image = pd_image;
        this.pd_ratig_value = pd_ratig_value;
    }

    public String getPd_name() {
        return pd_name;
    }

    public void setPd_name(String pd_name) {
        this.pd_name = pd_name;
    }

    public String getPd_cord() {
        return pd_cord;
    }

    public void setPd_cord(String pd_cord) {
        this.pd_cord = pd_cord;
    }

    public String getPd_image() {
        return pd_image;
    }

    public void setPd_image(String pd_image) {
        this.pd_image = pd_image;
    }

    public double getPd_ratig_value() {
        return pd_ratig_value;
    }

    public void setPd_ratig_value(float pd_ratig_value) {
        this.pd_ratig_value = pd_ratig_value;
    }

    public String getRatig_dataKey() {
        return ratig_dataKey;
    }

    public void setRatig_dataKey(String ratig_dataKey) {
        this.ratig_dataKey = ratig_dataKey;
    }
}
