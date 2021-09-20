package com.example.my_ecommerce_project;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class shose_Category extends AppCompatActivity {
   private String  image,product_Name,product_Cord,product_Drecpt;
    private int prodcut_price,pd_Stock,pd_discount;
    private ElegantNumberButton numberButton;
    private DatabaseReference databaseRefe_Category;
    private TextView textView_pdName,textView_pdCord,textView_pdDrecp,textView_pdstock,textView_pd_price,
            textView_pdSize,textView_discount;
    private ImageSlider imageSlider;
    private TextView textView_rationValue;
    private DatabaseReference databaseRefe_rationData;
    private DatabaseReference databaseRef_stockUpdate;
    private  String Select_size;
    private EditText editText_size;
    private SharedPreferences sharedPrefere_ration,sharedPreferences_userGmall;
    private ration_DataModel ration_Item;
    private RatingBar ratingBar;
   private String user_gmail;
   private Spinner spinner;
    private ArrayList<String>arrayList_pdSize=new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter_pdSize;
    private String[] size_pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shose__category);

        databaseRefe_Category= FirebaseDatabase.getInstance().getReference("Shose_categoryAdd");
        databaseRefe_rationData= FirebaseDatabase.getInstance().getReference("Shose_Rating_Add");
        databaseRef_stockUpdate= FirebaseDatabase.getInstance().getReference("Shose_product_Add");

       textView_pdCord=(TextView)findViewById(R.id.category_Shose_Cord);
       textView_pd_price=(TextView)findViewById(R.id.category_Shose_Price);
       textView_pdName=(TextView)findViewById(R.id.category_shose_name);
       textView_pdDrecp=(TextView)findViewById(R.id.category_Shose_drecpt);
       textView_pdstock=(TextView)findViewById(R.id.category_Shose_stock);
       textView_rationValue=(TextView)findViewById(R.id.shose_rating_value);
       //textView_pdSize=(TextView)findViewById(R.id.category_Shose_pd_size);

         textView_discount=(TextView)findViewById(R.id.category_shose_discountId);
         ratingBar=(RatingBar)findViewById(R.id.shose_rating_id);
        imageSlider=(ImageSlider)findViewById(R.id.shose_rateImage);
        numberButton=(ElegantNumberButton)findViewById(R.id.category_Shose_Quantity);
        spinner=(Spinner)findViewById(R.id.spinner_shose_pd_size);
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
                        final HashMap<String,Object> hashMap=new HashMap<>();
                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                                hashMap.put("pd_ratig_value",rating);
                                databaseRefe_rationData.child(ration_Item.getRatig_dataKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                              Toast.makeText(shose_Category.this, "Ration Update SuccessFull..", Toast.LENGTH_SHORT).show();
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
                                    }
                                });
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(shose_Category.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception exception){
            Toast.makeText(this, "Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

  void  getIntentData(){
      Bundle bundle=getIntent().getExtras();

      product_Name=bundle.getString("pd_name");
      product_Cord=bundle.getString("pd_cord");
      prodcut_price =bundle.getInt("pd_price");
      product_Drecpt=bundle.getString("pd_Desr");
     // pd_Stock=bundle.getInt("pd_stock");
      image=bundle.getString("pd_image");
      pd_discount=bundle.getInt("pd_discount");

      textView_pdName.setText("Name:"+product_Name);
      textView_pdCord.setText("Cord:"+product_Cord);
      textView_pd_price.setText("price:"+prodcut_price);
      textView_pdDrecp.setText("Descriptiion:"+product_Drecpt);
      textView_pdstock.setText("Stock:"+pd_Stock);
     // textView_pdSize.setText("Stock:"+size_pd);
      textView_discount.setText("Discount:"+pd_discount);

      List<SlideModel> slideModels = new ArrayList<>();
      slideModels.add(new SlideModel(image, product_Name, ScaleTypes.FIT));
      slideModels.add(new SlideModel(image, product_Name, ScaleTypes.CENTER_CROP));
      imageSlider.setImageList(slideModels, ScaleTypes.FIT);

      arrayList_pdSize.add("Select product Size..");
        size_pd=bundle.getString("pd_size").split(",");
      for(String size:size_pd){
          arrayList_pdSize.add(size);
      }
      arrayAdapter_pdSize=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,arrayList_pdSize);
      spinner.setAdapter(arrayAdapter_pdSize);

  }

    public void Add_cardData(View view) {
        Bundle bundle=getIntent().getExtras();

        product_Cord=bundle.getString("pd_cord");

        int num_Quantity = Integer.parseInt(numberButton.getNumber().toString());
        final int total_quan,Sell_total_price;

         /*   final HashMap<String, Object> hashMap = new HashMap<>();
            total_quan=pd_Stock-num_Quantity;
            textView_pdstock.setText("Stock :"+total_quan);*/
        if(spinner.getSelectedItem().toString().contains("Select product Size..")){
            Toast.makeText(this, "Product Size is Empty..", Toast.LENGTH_SHORT).show();
            spinner.requestFocus();
        }else{
            String pd_size=spinner.getSelectedItem().toString();
            String myKey= databaseRefe_Category.push().getKey();

            shose_category_Add addItem=new shose_category_Add(product_Cord,product_Name,image,pd_size,prodcut_price,pd_discount,num_Quantity,user_gmail);
            databaseRefe_Category.child(myKey).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Your Cart Add SuccessFull..", Toast.LENGTH_SHORT).show();
                    // Read from the database
        /*  databaseRef_stockUpdate.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {


               for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                   shose_pdModel getItem=dataSnapshot1.getValue(shose_pdModel.class);
                   getItem.setShose_product_key(dataSnapshot1.getKey());

                if(getItem.getShose_pd_id().contains(product_Cord)){
                   hashMap.put("shose_stock",total_quan);
        databaseRef_stockUpdate.child(getItem.getShose_product_key().toString()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
              editText_size.setText("");
           Toast.makeText(getApplicationContext(), "Your Card Add SuccessFull..", Toast.LENGTH_SHORT).show();
                          }
                      });

                   }

               }
              }

              @Override
              public void onCancelled(DatabaseError error) {
          Toast.makeText(shose_Category.this, "Your Shose Product Update Failde..."+error.getMessage(), Toast.LENGTH_SHORT).show();
              }
          });
*/
                }

            });

        }

    }

    public void go_toShowCategory(View view) {
        Intent intent=new Intent(shose_Category.this,Shoses_categoryShow.class);
        startActivity(intent);
        finish();
    }
}