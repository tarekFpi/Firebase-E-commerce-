package com.example.my_ecommerce_project;

public class T_shirt_Moder {
    private String tshirt_pdCord;
    private String tshirt_pdName;
    private String tshirt_pdDesr;
    ///private int tshirt_pdStock;
    private int tshirt_pdPrice;
    private String tshirt_pdImage;
    private int tshirt_Discount;
    private String tshirt_size;

    private String tshirt_pdKey;

    public T_shirt_Moder() {
    }

    public T_shirt_Moder(String tshirt_pdCord, String tshirt_pdName, String tshirt_pdDesr, int tshirt_pdPrice, String tshirt_pdImage, int tshirt_Discount, String tshirt_size) {
        this.tshirt_pdCord = tshirt_pdCord;
        this.tshirt_pdName = tshirt_pdName;
        this.tshirt_pdDesr = tshirt_pdDesr;
        this.tshirt_pdPrice = tshirt_pdPrice;
        this.tshirt_pdImage = tshirt_pdImage;
        this.tshirt_Discount = tshirt_Discount;
        this.tshirt_size = tshirt_size;
    }

    public String getTshirt_size() {
        return tshirt_size;
    }

    public void setTshirt_size(String tshirt_size) {
        this.tshirt_size = tshirt_size;
    }

    public String getTshirt_pdImage() {
        return tshirt_pdImage;
    }

    public void setTshirt_pdImage(String tshirt_pdImage) {
        this.tshirt_pdImage = tshirt_pdImage;
    }

    public String getTshirt_pdCord() {
        return tshirt_pdCord;
    }

    public void setTshirt_pdCord(String tshirt_pdCord) {
        this.tshirt_pdCord = tshirt_pdCord;
    }

    public String getTshirt_pdName() {
        return tshirt_pdName;
    }

    public void setTshirt_pdName(String tshirt_pdName) {
        this.tshirt_pdName = tshirt_pdName;
    }

    public String getTshirt_pdDesr() {
        return tshirt_pdDesr;
    }

    public void setTshirt_pdDesr(String tshirt_pdDesr) {
        this.tshirt_pdDesr = tshirt_pdDesr;
    }


    public int getTshirt_pdPrice() {
        return tshirt_pdPrice;
    }

    public void setTshirt_pdPrice(int tshirt_pdPrice) {
        this.tshirt_pdPrice = tshirt_pdPrice;
    }

    public int getTshirt_Discount() {
        return tshirt_Discount;
    }

    public void setTshirt_Discount(int tshirt_Discount) {
        this.tshirt_Discount = tshirt_Discount;
    }

    public String getTshirt_pdKey() {
        return tshirt_pdKey;
    }

    public void setTshirt_pdKey(String tshirt_pdKey) {
        this.tshirt_pdKey = tshirt_pdKey;
    }
}
