package com.example.my_ecommerce_project;

public class T_shairt_Category_Model {
    private String t_Shairt_Category_Cord;
    private String t_Shairt_Category_Name;
    private String Select_Size;
    private String product_image;
    private int t_Shairt_Category_price;
    private int t_Shairt_Category_quantity;
    private int t_Shairt_Category_discount;

    private String user_gmail;
    private String t_Shairt_Category_dataKey;

    public String getT_Shairt_Category_dataKey() {
        return t_Shairt_Category_dataKey;
    }

    public void setT_Shairt_Category_dataKey(String t_Shairt_Category_dataKey) {
        this.t_Shairt_Category_dataKey = t_Shairt_Category_dataKey;
    }

    public T_shairt_Category_Model() {
    }

    public T_shairt_Category_Model(String t_Shairt_Category_Cord, String t_Shairt_Category_Name, String select_Size, String product_image, int t_Shairt_Category_price, int t_Shairt_Category_quantity, int t_Shairt_Category_discount, String user_gmail) {
        this.t_Shairt_Category_Cord = t_Shairt_Category_Cord;
        this.t_Shairt_Category_Name = t_Shairt_Category_Name;
        Select_Size = select_Size;
        this.product_image = product_image;
        this.t_Shairt_Category_price = t_Shairt_Category_price;
        this.t_Shairt_Category_quantity = t_Shairt_Category_quantity;
        this.t_Shairt_Category_discount = t_Shairt_Category_discount;
        this.user_gmail = user_gmail;
    }

    public String getUser_gmail() {
        return user_gmail;
    }

    public void setUser_gmail(String user_gmail) {
        this.user_gmail = user_gmail;
    }

    public String getT_Shairt_Category_Cord() {
        return t_Shairt_Category_Cord;
    }

    public void setT_Shairt_Category_Cord(String t_Shairt_Category_Cord) {
        this.t_Shairt_Category_Cord = t_Shairt_Category_Cord;
    }

    public String getT_Shairt_Category_Name() {
        return t_Shairt_Category_Name;
    }

    public void setT_Shairt_Category_Name(String t_Shairt_Category_Name) {
        this.t_Shairt_Category_Name = t_Shairt_Category_Name;
    }

    public String getSelect_Size() {
        return Select_Size;
    }

    public void setSelect_Size(String select_Size) {
        Select_Size = select_Size;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getT_Shairt_Category_price() {
        return t_Shairt_Category_price;
    }

    public void setT_Shairt_Category_price(int t_Shairt_Category_price) {
        this.t_Shairt_Category_price = t_Shairt_Category_price;
    }

    public int getT_Shairt_Category_quantity() {
        return t_Shairt_Category_quantity;
    }

    public void setT_Shairt_Category_quantity(int t_Shairt_Category_quantity) {
        this.t_Shairt_Category_quantity = t_Shairt_Category_quantity;
    }

    public int getT_Shairt_Category_discount() {
        return t_Shairt_Category_discount;
    }

    public void setT_Shairt_Category_discount(int t_Shairt_Category_discount) {
        this.t_Shairt_Category_discount = t_Shairt_Category_discount;
    }


}
