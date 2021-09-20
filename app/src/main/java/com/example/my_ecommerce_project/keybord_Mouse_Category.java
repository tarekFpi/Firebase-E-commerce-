package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class keybord_Mouse_Category extends AppCompatActivity {
    private ImageSlider imageSlider;
    private RatingBar ratingBar;
    private TextView textView_ratingValue,textView_pdName,
            textView_price,textView_Pd_id,textView_stock,textView_descript
            ,textView_show_size,textView_discount,textView_rationValue;
    private TextView textView_Exit;
    private ElegantNumberButton numberButton;
    String  image,product_Name,product_Cord,product_Drecpt,size_pd;
    private int prodcut_price,pd_Stock,pd_discount=0;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseRefere_setQuantity;
    private DatabaseReference databaseRefe_rationData;
    private SharedPreferences sharedPrefere_ration,sharedPreferences_userGmall;
    private  ration_DataModel ration_Item;
    private String user_gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keybord__mouse__category);

        databaseReference= FirebaseDatabase.getInstance().getReference("Keybord_mouse_Category_Add");
        databaseRefere_setQuantity= FirebaseDatabase.getInstance().getReference("Keybord_Mouse_Add");
        databaseRefe_rationData= FirebaseDatabase.getInstance().getReference("Keybord_Mouse_Rating_Add");

        imageSlider=(ImageSlider)findViewById(R.id.cate_keybord_mouse_rateImage);
        textView_Pd_id=(TextView)findViewById(R.id.category_keymouse_Cord);
        textView_pdName=(TextView)findViewById(R.id.category_keyMouse_pdName);
        textView_price=(TextView)findViewById(R.id.category_keymouse_Price);
        textView_descript=(TextView)findViewById(R.id.category_keymouse_decpt);
       // textView_stock=(TextView)findViewById(R.id.category_keymouse_stock);
        textView_rationValue=(TextView)findViewById(R.id.cate_keyMouse_rating_value);

        textView_discount=(TextView)findViewById(R.id.category_keymouse_discountId);
        ratingBar=(RatingBar)findViewById(R.id.cate_keyMouse_rating_id);

        textView_ratingValue=(TextView)findViewById(R.id.cate_keyMouse_rating_value);
        numberButton=(ElegantNumberButton)findViewById(R.id.category_keymouse_Quantity);

        getIntent_Data();

        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");

        // value_ching=ratingBar.getRating();
        try {
            sharedPrefere_ration=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
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
                      Toast.makeText(keybord_Mouse_Category.this, "Ration Update SuccessFull..", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(keybord_Mouse_Category.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception exception){
            Toast.makeText(this, "Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void go_toShowCategory(View view) {
        Intent intent=new Intent(getApplicationContext(),keybord_Category_Show.class);
        startActivity(intent);
    }

    public void Add_cardData(View view) {
        int num_Quantity = Integer.parseInt(numberButton.getNumber().toString());
        final int total_quan,Sell_total_price;

         /*   total_quan=pd_Stock-num_Quantity;
            textView_stock.setText("Stock :"+total_quan);*/
  keybord_CategoryModel addItem=new keybord_CategoryModel(product_Cord,product_Name,image,prodcut_price,num_Quantity,pd_discount,user_gmail);
           String mykey= databaseReference.push().getKey();

           databaseReference.child(mykey).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                   Toast.makeText(getApplicationContext(), "Your Cart Add SuccessFull..", Toast.LENGTH_SHORT).show();

                /*   databaseRefere_setQuantity.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           HashMap<String, Object> hashMap = new HashMap<>();
                         for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                         keybord_mouse_Model addItem=dataSnapshot1.getValue(keybord_mouse_Model.class);
                         addItem.setKeyMouse_Data_Key(dataSnapshot1.getKey());

                         if(addItem.getKeyMouse_id().contains(product_Cord)){
                             hashMap.put("keyMouse_stock",pd_Stock);
           databaseRefere_setQuantity.child(addItem.getKeyMouse_Data_Key()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                   Toast.makeText(getApplicationContext(), "Your Card Add SuccessFull..", Toast.LENGTH_SHORT).show();
               }
           });
                         }
                         }
                       }
                       @Override
                       public void onCancelled(DatabaseError error) {
                    Toast.makeText(keybord_Mouse_Category.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
*/
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
       Toast.makeText(keybord_Mouse_Category.this, "Your Product Add Failde..", Toast.LENGTH_SHORT).show();
               }
           });

    }

    private void getIntent_Data() {

        // sharedPreferences_status=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        Bundle bundle=getIntent().getExtras();

        product_Name=bundle.getString("pd_name");
        product_Cord=bundle.getString("pd_cord");
        prodcut_price =bundle.getInt("pd_price");
        product_Drecpt=bundle.getString("pd_Desr");
      //  pd_Stock=bundle.getInt("pd_stock");
        image=bundle.getString("pd_image");
        pd_discount=bundle.getInt("pd_discount");


        textView_Pd_id.setText("product Cord:" + product_Cord);
        textView_pdName.setText("Name:"+product_Name);
        //textView_stock.setText("Stock:"+pd_Stock);
        textView_descript.setText("Description:"+product_Drecpt);
        textView_price.setText("Price:"+prodcut_price);
        textView_discount.setText("Discount:"+pd_discount);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(image, product_Name, ScaleTypes.FIT));
        slideModels.add(new SlideModel(image, product_Name, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

    }

}