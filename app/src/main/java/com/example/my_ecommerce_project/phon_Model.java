package com.example.my_ecommerce_project;

public class phon_Model {
    private String phon_pdId;
    private String phon_pdName;
    private String phon_pdDecpt;
    private String phon_pdimage;
    private String phon_pdDate;
    private int phon_pdprice;
    private int phon_pdDiscount;

    private String phon_pdKey;

    public phon_Model() {
    }

    public phon_Model(String phon_pdId, String phon_pdName, String phon_pdDecpt, String phon_pdimage, String phon_pdDate, int phon_pdprice, int phon_pdDiscount) {
        this.phon_pdId = phon_pdId;
        this.phon_pdName = phon_pdName;
        this.phon_pdDecpt = phon_pdDecpt;
        this.phon_pdimage = phon_pdimage;
        this.phon_pdDate = phon_pdDate;
        this.phon_pdprice = phon_pdprice;
        this.phon_pdDiscount = phon_pdDiscount;
    }

    public String getPhon_pdId() {
        return phon_pdId;
    }

    public void setPhon_pdId(String phon_pdId) {
        this.phon_pdId = phon_pdId;
    }

    public String getPhon_pdName() {
        return phon_pdName;
    }

    public void setPhon_pdName(String phon_pdName) {
        this.phon_pdName = phon_pdName;
    }

    public String getPhon_pdDecpt() {
        return phon_pdDecpt;
    }

    public void setPhon_pdDecpt(String phon_pdDecpt) {
        this.phon_pdDecpt = phon_pdDecpt;
    }

    public String getPhon_pdimage() {
        return phon_pdimage;
    }

    public void setPhon_pdimage(String phon_pdimage) {
        this.phon_pdimage = phon_pdimage;
    }

    public String getPhon_pdDate() {
        return phon_pdDate;
    }

    public void setPhon_pdDate(String phon_pdDate) {
        this.phon_pdDate = phon_pdDate;
    }

    public int getPhon_pdprice() {
        return phon_pdprice;
    }

    public void setPhon_pdprice(int phon_pdprice) {
        this.phon_pdprice = phon_pdprice;
    }


    public int getPhon_pdDiscount() {
        return phon_pdDiscount;
    }

    public void setPhon_pdDiscount(int phon_pdDiscount) {
        this.phon_pdDiscount = phon_pdDiscount;
    }

    public String getPhon_pdKey() {
        return phon_pdKey;
    }

    public void setPhon_pdKey(String phon_pdKey) {
        this.phon_pdKey = phon_pdKey;
    }
}
