package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
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

public class Cap_Category extends AppCompatActivity {
    private TextView textView_pdName,textView_pdCord,textView_pdDrecp,textView_pdstock,textView_pd_price;
  private DatabaseReference databaseRefere_stockQuantity;
   private TextView textView_discount,textView_sizeShow ,textView_rationValue;
   private EditText editText_pd_Size;
    private ImageSlider imageSlider;
    String  image,product_Name,product_Cord,product_Drecpt;
    private int prodcut_price,pd_Stock,pd_discount;
    private ElegantNumberButton numberButton;
    private DatabaseReference databaseRefe_Category;
    private SharedPreferences sharedPrefere_ration,sharedPreferences_userGmall;
    private RatingBar ratingBar;
    private DatabaseReference databaseRefe_rationData;
    private ration_DataModel ration_Item;
   private String user_gmail;
  private Spinner spinner;
    private ArrayList<String>arrayList_pdSize=new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter_pdSize;
    private String[] size_pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap__category);
        databaseRefe_Category= FirebaseDatabase.getInstance().getReference("Cap_Category_Add");
        databaseRefere_stockQuantity=FirebaseDatabase.getInstance().getReference("Cap_product_Add");
        databaseRefe_rationData=FirebaseDatabase.getInstance().getReference("Cap_rating_Add");

        textView_pdCord=(TextView)findViewById(R.id.category_cap_Cord);
        textView_pdName=(TextView)findViewById(R.id.category_cap_name);
        textView_pd_price=(TextView)findViewById(R.id.category_cap_Price);
        //textView_pdstock=(TextView)findViewById(R.id.category_cap_stock);
        textView_pdDrecp=(TextView)findViewById(R.id.category_cap_drecpt);
        textView_sizeShow=(TextView)findViewById(R.id.category_cap_pd_size);

        textView_rationValue=(TextView)findViewById(R.id.cap_rating_value);

        imageSlider=(ImageSlider)findViewById(R.id.capCategory_rateImage);
        numberButton=(ElegantNumberButton)findViewById(R.id.category_cap_Quantity);
        spinner=(Spinner)findViewById(R.id.spinner_cap_pd_size);
        ratingBar=(RatingBar)findViewById(R.id.cap_rating_id);
        getIntentData();

        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");

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
                          Toast.makeText(Cap_Category.this, "Ration Update SuccessFull..", Toast.LENGTH_SHORT).show();
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

                                String key=databaseRefe_rationData.push().getKey();
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
       Toast.makeText(Cap_Category.this, "Error:"+error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception exception){
    Toast.makeText(this, "Exception:"+exception.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void getIntentData() {

        Bundle bundle=getIntent().getExtras();

        product_Name=bundle.getString("pd_name");
        product_Cord=bundle.getString("pd_cord");
        prodcut_price =bundle.getInt("pd_price");
        product_Drecpt=bundle.getString("pd_Desr");
       // pd_Stock=bundle.getInt("pd_stock");
        image=bundle.getString("pd_image");

        arrayList_pdSize.add("Select product Size..");
        size_pd=bundle.getString("ppd_size").split(",");
        textView_sizeShow.setText("Size:"+bundle.getString("pd_size"));

        for(String size:size_pd){
            arrayList_pdSize.add(size);
        }
        arrayAdapter_pdSize=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,arrayList_pdSize);
        spinner.setAdapter(arrayAdapter_pdSize);

        textView_pdCord.setText("product Cord:"+product_Cord);
        textView_pdName.setText(product_Name);
       // textView_pdstock.setText("Stock:"+ pd_Stock);
        textView_pdDrecp.setText("Desription:"+product_Drecpt);
        textView_pd_price.setText("Price:"+ prodcut_price);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(image, product_Name, ScaleTypes.FIT));
        slideModels.add(new SlideModel(image, product_Name, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }


    public void Add_cardData(View view) {
        int num_Quantity = Integer.parseInt(numberButton.getNumber().toString());
        final int total_quan;

         if(spinner.getSelectedItem().toString().contains("Select product Size..")){
            Toast.makeText(this, "prodcut Size Select..", Toast.LENGTH_SHORT).show();
             spinner.requestFocus();
        }else{

             try {
                 String pd_size=spinner.getSelectedItem().toString();

                // Read from the database
                Cap_Category_Model addItem=new Cap_Category_Model(product_Cord,product_Name,pd_size,image,prodcut_price,num_Quantity,user_gmail);
                String mykey= databaseRefe_Category.push().getKey();
                databaseRefe_Category.child(mykey).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
           Toast.makeText(getApplicationContext(), "Your Cart Add SuccessFull..", Toast.LENGTH_SHORT).show();
                      /*  // Read from the database
                        databaseRefere_stockQuantity.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                    Cap_product_Model  Item_data=dataSnapshot1.getValue(Cap_product_Model.class);
                                    Item_data.setCap_product_Key(dataSnapshot1.getKey());

                                    if(Item_data.getCap_pdCord().contains(product_Cord)){

                            databaseRefere_stockQuantity.child(Item_data.getCap_product_Key()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                 Toast.makeText(getApplicationContext(), "Your Card Add SuccessFull..", Toast.LENGTH_SHORT).show();
                                 editText_pd_Size.setText("");
                                            }
                                        });
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(Cap_Category.this, "Your quantity set Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
*/
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                Toast.makeText(Cap_Category.this, "Your Category Add Faild..", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception exception){
                Toast.makeText(this, "Exception"+exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
         }

    }

    public void go_toShowCategory(View view) {
        Intent intent=new Intent(Cap_Category.this,Cap_Category_Show.class);
        startActivity(intent);
        finish();
    }
}