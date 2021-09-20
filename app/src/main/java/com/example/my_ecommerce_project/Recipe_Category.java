package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recipe_Category extends AppCompatActivity {
    private TextView textView_pdName,textView_pdCord,textView_pdDrecp,textView_pdstock,textView_pd_price;
    private DatabaseReference databaseRefere_stockQuantity;
    private DatabaseReference databaseRefere_reciep_CategoryShow;
    private DatabaseReference databaseRefe_rationData;

    private TextView textView_discount,textView_expirtDate,textView_rationValue;
    private ImageSlider imageSlider;
    String  image,product_Name,product_Cord,product_Drecpt,expirtDate;
    private int prodcut_price,pd_Stock,pd_discount;
    private ElegantNumberButton numberButton;
    private SharedPreferences sharedPrefere_ration,sharedPreferences_userGmall;
    private ration_DataModel ration_Item;
    private RatingBar ratingBar;
    private String user_gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__category);
        databaseRefere_reciep_CategoryShow= FirebaseDatabase.getInstance().getReference("Recipe_Category_Add");
        databaseRefere_stockQuantity= FirebaseDatabase.getInstance().getReference("Recipe_product_Add");
        databaseRefe_rationData= FirebaseDatabase.getInstance().getReference("Recipe_Rating_Add");

       imageSlider =(ImageSlider)findViewById(R.id.reciep_Category_rateImage);

       textView_pdCord=(TextView)findViewById(R.id.Category_recipe_pd_Cord);
       textView_pdName=(TextView)findViewById(R.id.category_recipe_name);
       textView_discount=(TextView)findViewById(R.id.category_recipe_discountId);
       textView_pdDrecp=(TextView)findViewById(R.id.category_recipe_decpt);
       //textView_pdstock=(TextView)findViewById(R.id.recipe_pd_stock);
        textView_pd_price=(TextView)findViewById(R.id.category_recipe_Price);
        textView_expirtDate=(TextView)findViewById(R.id.category_recipe_expirtDate);
        textView_rationValue=(TextView)findViewById(R.id.recipe_rating_value);
       numberButton=(ElegantNumberButton)findViewById(R.id.category_recipe_Quantity);
        ratingBar=(RatingBar)findViewById(R.id.recipe_rating_id);

        getIntentData();

        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");

        sharedPrefere_ration=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        try {
            // Read from the database
            databaseRefe_rationData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ration_Item = dataSnapshot1.getValue(ration_DataModel.class);
                        ration_Item.setRatig_dataKey(dataSnapshot1.getKey());

                        if (ration_Item.getPd_image().contains(image)) {
                            // int value= Integer.parseInt(data.toString());
                            textView_rationValue.setText("Rating:" + ration_Item.getPd_ratig_value());
                            ratingBar.setRating(ration_Item.pd_ratig_value);

                            sharedPrefere_ration.edit().putString("image_key",ration_Item.getPd_image()).apply();
                            sharedPrefere_ration.edit().putString("pd_cord",ration_Item.getPd_cord()).apply();
                            sharedPrefere_ration.edit().putString("pd_name",ration_Item.getPd_name()).apply();
                            sharedPrefere_ration.edit().commit();
                        }
                    }
                    String pd_image= sharedPrefere_ration.getString("image_key","Data Not Found");

                    if(pd_image.contains(image)){
                        final HashMap<String,Object>hashMap=new HashMap<>();
                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                                hashMap.put("pd_ratig_value",rating);
                                databaseRefe_rationData.child(ration_Item.getRatig_dataKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                         Toast.makeText(Recipe_Category.this, "Ration Update SuccessFull..", Toast.LENGTH_SHORT).show();
                       sharedPrefere_ration.edit().clear();
                                    }
                                });
                            }
                        });

                    }else if(!pd_image.contains(image)){
                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                textView_rationValue.setText("Rating:"+rating);
                                // final String rating_value=String.valueOf(rating);

                                String key=databaseRefe_rationData.push().getKey();  //fierbase add
                                ration_DataModel ration_data=new ration_DataModel(product_Cord,product_Name,image,rating);
                                databaseRefe_rationData.child(key).setValue(ration_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Favourity Add", Toast.LENGTH_SHORT).show();
                                        //  value_ching=value_ching+value_ching;
                                    }
                                });
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(Recipe_Category.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception exception){
            Toast.makeText(this, "Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    private void getIntentData() {

        Bundle bundle=getIntent().getExtras();

        product_Name=bundle.getString("pd_name");
        product_Cord=bundle.getString("pd_cord");
        prodcut_price =bundle.getInt("pd_price");
        product_Drecpt=bundle.getString("pd_Desr");
        expirtDate=bundle.getString("pd_expirt");
       // pd_Stock=bundle.getInt("pd_stock");
        pd_discount=bundle.getInt("pd_diccount");
        image=bundle.getString("pd_image");

        textView_pdCord.setText("product Cord:"+product_Cord);
        textView_pdName.setText( product_Name);
        //textView_pdstock.setText("Stock:"+ pd_Stock);
        textView_pdDrecp.setText("Desription:"+product_Drecpt);
        textView_pd_price.setText("Price:"+ prodcut_price);
        textView_discount.setText("Discount:"+ pd_discount);
        textView_expirtDate.setText("Exp:"+ expirtDate);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(image, product_Name, ScaleTypes.FIT));
        slideModels.add(new SlideModel(image, product_Name, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }
    public void go_toShowCategory(View view) {
        Intent intent=new Intent(getApplicationContext(),Recipe_CategoryShow.class);
        finish();
        startActivity(intent);

    }

    public void Add_cardData(View view) {
        int num_Quantity = Integer.parseInt(numberButton.getNumber().toString());
        final int total_quan;


        try {
            total_quan=pd_Stock-num_Quantity;
            textView_pdstock.setText("Stock :"+total_quan);

            String myKey= databaseRefere_reciep_CategoryShow.push().getKey();
            Recipe_CategoryModel add_category=new Recipe_CategoryModel(product_Cord,product_Name,image,prodcut_price,pd_discount,num_Quantity,user_gmail);
            databaseRefere_reciep_CategoryShow.child(myKey).setValue(add_category).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                   /* // Read from the database
                    databaseRefere_stockQuantity.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Recipe_ModelAdd item_data = null;
                            HashMap<String, Object> hashMap = new HashMap<>();
                            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                  item_data=dataSnapshot1.getValue(Recipe_ModelAdd.class);
                                item_data.setRecipe_Data_key(dataSnapshot1.getKey());

                                if(item_data.getRecipe_pd_id().contains(product_Cord)){
                                    hashMap.put("recipe_pd_stock",total_quan);
                                    databaseRefere_stockQuantity.child(item_data.getRecipe_Data_key()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                              Toast.makeText(getApplicationContext(), "Your Card Add SuccessFull..", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                    
                            }
                           // Toast.makeText(getApplicationContext(),"Data:"+ item_data.getRecipe_pd_Name(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Your Stock Lodding Data Failde", Toast.LENGTH_SHORT).show();
                        }
                    });
*/

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Your Card Add Failde..", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception exception){
         Toast.makeText(this, "Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }
}