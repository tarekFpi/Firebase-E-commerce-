package com.example.my_ecommerce_project;

public class Recipe_ModelAdd {
    private String recipe_pd_id;
    private String recipe_pd_Name;
    private String recipe_pd_Descrpt;
    private String recipe_expiredDate;
    private int recipe_pd_price;
    private int recipe_pd_discount;
    private String recipe_pd_image;


    private String recipe_Data_key;

    public String getRecipe_Data_key() {
        return recipe_Data_key;
    }

    public void setRecipe_Data_key(String recipe_Data_key) {
        this.recipe_Data_key = recipe_Data_key;
    }

    public Recipe_ModelAdd() {
    }

    public Recipe_ModelAdd(String recipe_pd_id, String recipe_pd_Name, String recipe_pd_Descrpt, String recipe_expiredDate, int recipe_pd_price, int recipe_pd_discount, String recipe_pd_image) {
        this.recipe_pd_id = recipe_pd_id;
        this.recipe_pd_Name = recipe_pd_Name;
        this.recipe_pd_Descrpt = recipe_pd_Descrpt;
        this.recipe_expiredDate = recipe_expiredDate;
        this.recipe_pd_price = recipe_pd_price;
        this.recipe_pd_discount = recipe_pd_discount;
        this.recipe_pd_image = recipe_pd_image;
    }

    public String getRecipe_pd_id() {
        return recipe_pd_id;
    }

    public void setRecipe_pd_id(String recipe_pd_id) {
        this.recipe_pd_id = recipe_pd_id;
    }

    public String getRecipe_pd_Name() {
        return recipe_pd_Name;
    }

    public void setRecipe_pd_Name(String recipe_pd_Name) {
        this.recipe_pd_Name = recipe_pd_Name;
    }

    public String getRecipe_pd_Descrpt() {
        return recipe_pd_Descrpt;
    }

    public void setRecipe_pd_Descrpt(String recipe_pd_Descrpt) {
        this.recipe_pd_Descrpt = recipe_pd_Descrpt;
    }

    public String getRecipe_expiredDate() {
        return recipe_expiredDate;
    }

    public void setRecipe_expiredDate(String recipe_expiredDate) {
        this.recipe_expiredDate = recipe_expiredDate;
    }

    public int getRecipe_pd_price() {
        return recipe_pd_price;
    }

    public void setRecipe_pd_price(int recipe_pd_price) {
        this.recipe_pd_price = recipe_pd_price;
    }


    public int getRecipe_pd_discount() {
        return recipe_pd_discount;
    }

    public void setRecipe_pd_discount(int recipe_pd_discount) {
        this.recipe_pd_discount = recipe_pd_discount;
    }

    public String getRecipe_pd_image() {
        return recipe_pd_image;
    }

    public void setRecipe_pd_image(String recipe_pd_image) {
        this.recipe_pd_image = recipe_pd_image;
    }
}
