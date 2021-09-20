package com.example.my_ecommerce_project;

public class Cap_product_Model {
    private String cap_pdCord;
    private String cap_pdName;
    private String cap_pdDecr;
    private int cap_pdPrice;

    private String cap_pdDate;
    private String cap_pdImage;
    private String cap_pdSize;

    private String cap_product_Key;

    public Cap_product_Model() {
    }

    public Cap_product_Model(String cap_pdCord, String cap_pdName, String cap_pdDecr, int cap_pdPrice,  String cap_pdDate, String cap_pdImage, String cap_pdSize) {
        this.cap_pdCord = cap_pdCord;
        this.cap_pdName = cap_pdName;
        this.cap_pdDecr = cap_pdDecr;
        this.cap_pdPrice = cap_pdPrice;
        this.cap_pdDate = cap_pdDate;
        this.cap_pdImage = cap_pdImage;
        this.cap_pdSize = cap_pdSize;
    }

    public String getCap_pdSize() {
        return cap_pdSize;
    }

    public void setCap_pdSize(String cap_pdSize) {
        this.cap_pdSize = cap_pdSize;
    }

    public String getCap_product_Key() {
        return cap_product_Key;
    }

    public void setCap_product_Key(String cap_product_Key) {
        this.cap_product_Key = cap_product_Key;
    }

    public String getCap_pdImage() {
        return cap_pdImage;
    }

    public void setCap_pdImage(String cap_pdImage) {
        this.cap_pdImage = cap_pdImage;
    }

    public String getCap_pdCord() {
        return cap_pdCord;
    }

    public void setCap_pdCord(String cap_pdCord) {
        this.cap_pdCord = cap_pdCord;
    }

    public String getCap_pdName() {
        return cap_pdName;
    }

    public void setCap_pdName(String cap_pdName) {
        this.cap_pdName = cap_pdName;
    }

    public String getCap_pdDecr() {
        return cap_pdDecr;
    }

    public void setCap_pdDecr(String cap_pdDecr) {
        this.cap_pdDecr = cap_pdDecr;
    }

    public int getCap_pdPrice() {
        return cap_pdPrice;
    }

    public void setCap_pdPrice(int cap_pdPrice) {
        this.cap_pdPrice = cap_pdPrice;
    }


    public String getCap_pdDate() {
        return cap_pdDate;
    }

    public void setCap_pdDate(String cap_pdDate) {
        this.cap_pdDate = cap_pdDate;
    }
}
