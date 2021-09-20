package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class user_laptop_Category extends AppCompatActivity {
    private TextView textView_pdName,textView_pdCord,textView_pdDrecp,textView_pdstock,textView_pd_price,
            textView_Discount,textView_ratingValue;
    private RatingBar ratingBar;
    private ImageSlider imageSlider;
    String  image,product_Name,product_Cord,product_Drecpt;
    private int prodcut_price,pd_Stock,product_discount;
    private ElegantNumberButton numberButton;
    private DatabaseReference databaseReference,userDatabase;
    private DatabaseReference databaseRef_laptop_set_quantity;
  private SharedPreferences sharedPreferences_rationg;
    private SharedPreferences sharedPreferences_userGmall;
  private DatabaseReference databaseRef_laptop_rationg;
  private ration_DataModel rationg_Item;
    String user_gmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_laptop__category);

        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");

         databaseReference= FirebaseDatabase.getInstance().getReference("laptop_product_category");
        databaseRef_laptop_set_quantity=FirebaseDatabase.getInstance().getReference("Laptop_product_Add");

        databaseRef_laptop_rationg=FirebaseDatabase.getInstance().getReference("Laptop_Rationg_Add");

        textView_pdCord=(TextView)findViewById(R.id.categoryLp_pdCord);
        textView_pdName =(TextView)findViewById(R.id.categoryLp_pdName);
        textView_pdDrecp=(TextView)findViewById(R.id.category_Lpdrecpt);
        textView_pd_price=(TextView)findViewById(R.id.categoryLp_Price);
      //  textView_pdstock=(TextView)findViewById(R.id.category_Lp_pdstock);
        textView_Discount=(TextView)findViewById(R.id.categoryLp_discount);

        textView_ratingValue=(TextView)findViewById(R.id.textLp_rating_value);
        numberButton=(ElegantNumberButton)findViewById(R.id.categoryLp_Quantity);
        ratingBar=(RatingBar)findViewById(R.id.lap_rating_id);
       imageSlider=(ImageSlider) findViewById(R.id.lap_rateImage);
        getIntent_Data();

  //Toast.makeText(this, "user gmail:"+user_gmail, Toast.LENGTH_SHORT).show();
         try {
             sharedPreferences_rationg=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
             // Read from the database
             databaseRef_laptop_rationg.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                         rationg_Item = dataSnapshot1.getValue(ration_DataModel.class);
                         rationg_Item.setRatig_dataKey(dataSnapshot1.getKey());

                         if (rationg_Item.getPd_image().contains(image)) {
                             textView_ratingValue.setText("Rating:" +rationg_Item.getPd_ratig_value());
                             ratingBar.setRating(rationg_Item.pd_ratig_value);

                             sharedPreferences_rationg.edit().putString("image_key",rationg_Item.getPd_image()).apply();
                             sharedPreferences_rationg.edit().putString("pd_cord",rationg_Item.getPd_cord()).apply();
                             sharedPreferences_rationg.edit().putString("pd_name",rationg_Item.getPd_name()).apply();
                             sharedPreferences_rationg.edit().commit();
                         }
                     }
                     String pd_image= sharedPreferences_rationg.getString("image_key","Data Not Found");

                     if(pd_image.contains(image)){
                         final HashMap<String,Object>hashMap=new HashMap<>();
                         ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                             @Override
                             public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                                 hashMap.put("pd_ratig_value",rating);
                                 databaseRef_laptop_rationg.child(rationg_Item.getRatig_dataKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                          Toast.makeText(user_laptop_Category.this, "Ration Update SuccessFull..", Toast.LENGTH_SHORT).show();
                              sharedPreferences_rationg.edit().clear();
                                     }
                                 });
                             }
                         });

                     }else if(!pd_image.contains(image)){
                         ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                             @Override
                             public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                 textView_ratingValue.setText("Rating:"+rating);
                                 // final String rating_value=String.valueOf(rating);

                                 String key=databaseRef_laptop_rationg.push().getKey();  //fierbase add
                                 ration_DataModel ration_data=new ration_DataModel(product_Cord,product_Name,image,rating);
                                 databaseRef_laptop_rationg.child(key).setValue(ration_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Favourity Add", Toast.LENGTH_SHORT).show();
                                     }
                                 });
                             }
                         });
                     }

                 }
                 @Override
                 public void onCancelled(DatabaseError error) {
                     Toast.makeText(user_laptop_Category.this, "Rationg Lodding Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             });
         }catch (Exception exception){
             Toast.makeText(this, "Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
         }

    }

   void getIntent_Data(){

      Bundle bundle=getIntent().getExtras();

       product_Name=bundle.getString("pd_name");
       product_Cord=bundle.getString("pd_cord");
       prodcut_price =bundle.getInt("pd_price");
       product_Drecpt=bundle.getString("pd_Desr");
       product_discount=bundle.getInt("pd_discount");
       //pd_Stock=bundle.getInt("pd_stock");

       image=bundle.getString("pd_image");

       textView_pdCord.setText("ID:"+product_Cord);
       textView_pdName.setText("Name:"+product_Name);
       textView_pd_price.setText("Price:"+prodcut_price);
       textView_pdDrecp.setText(product_Drecpt);
      // textView_pdstock.setText("Stock:"+pd_Stock);
       textView_Discount.setText("Discount:"+product_discount);

       List<SlideModel> slideModels = new ArrayList<>();
       slideModels.add(new SlideModel(image, product_Name, ScaleTypes.FIT));
       slideModels.add(new SlideModel(image, product_Name, ScaleTypes.CENTER_CROP));
       imageSlider.setImageList(slideModels, ScaleTypes.FIT);
   }


    public void Add_cardData(View view) {


        int num_Quantity = Integer.parseInt(numberButton.getNumber().toString());
        final int total_quan,Sell_total_price;


            //userDatabase
       // databaseReference=databaseReference.child(user_gmail);
      final String myKey= databaseReference.push().getKey();
   laptop_CategoryModel laptop_category=new laptop_CategoryModel(product_Cord,product_Name,num_Quantity,prodcut_price,product_discount,product_Drecpt,image,user_gmail);
     databaseReference.child(myKey).setValue(laptop_category).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
         Toast.makeText(user_laptop_Category.this, "Your Order Cart SuccessFull..", Toast.LENGTH_SHORT).show();
                    //databaseRef_laptop_set_quantity
                    // Read from the database
                  /*  databaseRef_laptop_set_quantity.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            laptop_prodcut_Module itemData=null;
                            HashMap<String, Object> hashMapUpdate =new HashMap<>();
                            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                 itemData=dataSnapshot1.getValue(laptop_prodcut_Module.class);
                                itemData.setLap_data_Key(dataSnapshot1.getKey());

                                if(itemData.getLap_cord().contains(product_Cord)){
                                  hashMapUpdate.put("lap_stock",total_quan);
           databaseRef_laptop_set_quantity.child(itemData.getLap_data_Key()).updateChildren(hashMapUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
           Toast.makeText(user_laptop_Category.this, "Your Order Card SuccessFull..", Toast.LENGTH_SHORT).show();
                     }
                      });
                      }
                     }
                    }
                  @Override
               public void onCancelled(DatabaseError error) {
           Toast.makeText(user_laptop_Category.this, "Update Lodding Data Failde:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
*/
                }
            });


    }


    public void go_toShowCategory(View view) {
        Intent intent=new Intent(user_laptop_Category.this,Laptop_categoryShow.class);
        startActivity(intent);
        finish();
    }
}