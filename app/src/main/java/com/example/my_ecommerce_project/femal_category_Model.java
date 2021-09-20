package com.example.my_ecommerce_project;

public class femal_category_Model {

   private String product_Cord;
   private String product_Name;
    private   int product_price;
    private  int product_quantity;
    private  String product_Size;
     private String product_image;
     private String product_Decerption;

    private String user_gmail;
    private String category_key;

    public femal_category_Model() {
    }

    public femal_category_Model(String product_Cord, String product_Name, int product_price, int product_quantity, String product_Size, String product_image, String product_Decerption, String user_gmail) {
        this.product_Cord = product_Cord;
        this.product_Name = product_Name;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
        this.product_Size = product_Size;
        this.product_image = product_image;
        this.product_Decerption = product_Decerption;
        this.user_gmail = user_gmail;
    }

    public String getUser_gmail() {
        return user_gmail;
    }

    public void setUser_gmail(String user_gmail) {
        this.user_gmail = user_gmail;
    }

    public String getProduct_Decerption() {
        return product_Decerption;
    }

    public void setProduct_Decerption(String product_Decerption) {
        this.product_Decerption = product_Decerption;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_Size() {
        return product_Size;
    }

    public void setProduct_Size(String product_Size) {
        this.product_Size = product_Size;
    }

    public String getProduct_Cord() {
        return product_Cord;
    }

    public void setProduct_Cord(String product_Cord) {
        this.product_Cord = product_Cord;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getCategory_key() {
        return category_key;
    }

    public void setCategory_key(String category_key) {
        this.category_key = category_key;
    }
}
