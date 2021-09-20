package com.example.my_ecommerce_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class babyPd_Category extends AppCompatActivity {

    private TextView textView_pdName,textView_pdCord,textView_pdDrecp,textView_pdstock,textView_pd_price;
    private DatabaseReference databaseRefere_stockQuantity;
    private TextView textView_discount,textView_sizeShow;
    private EditText editText_pd_Size;
    private ImageSlider imageSlider;
    String  image,product_Name,product_Cord,product_Drecpt;
    private int prodcut_price,pd_Stock,pd_discount;
    private ElegantNumberButton numberButton;
    private DatabaseReference databaseRefe_Category;
    private DatabaseReference databaseRefe_baby_rating;
    private SharedPreferences sharedPrefere_ration,sharedPreferences_userGmall;
    private ration_DataModel rationg_Item;
    private TextView textView_ratingValue;
    private RatingBar ratingBar;
  private String user_gmail;

  private Spinner spinner;
    private ArrayList<String>arrayList_pdSize=new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter_pdSize;
    private String[] size_pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_pd__category);

   databaseRefe_Category= FirebaseDatabase.getInstance().getReference("Baby_product_CategoryAdd");
   databaseRefere_stockQuantity= FirebaseDatabase.getInstance().getReference("Baby_product_Add");

    databaseRefe_baby_rating= FirebaseDatabase.getInstance().getReference("baby_rationg_ImageSave");

 textView_pdName=(TextView)findViewById(R.id.category_baby_name);
 textView_pdCord=(TextView)findViewById(R.id.category_baby_Cord);
 textView_pdDrecp=(TextView)findViewById(R.id.category_baby_drecpt);
 textView_discount=(TextView)findViewById(R.id.category_baby_discountId);
 textView_pd_price=(TextView)findViewById(R.id.category_baby_Price);

 // textView_pdstock=(TextView)findViewById(R.id.category_baby_stock);
 //editText_pd_Size=(EditText)findViewById(R.id.Category_pd_size);

 imageSlider=(ImageSlider)findViewById(R.id.babyCategory_rateImage);
 numberButton=(ElegantNumberButton)findViewById(R.id.category_baby_Quantity);
 textView_ratingValue=(TextView)findViewById(R.id.baby_rating_value);
 ratingBar=(RatingBar)findViewById(R.id.baby_rating_id);

 spinner=(Spinner)findViewById(R.id.spinner_baby_pd_size);
        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");

        getIntentData();

  sharedPrefere_ration=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        // Read from the database
        databaseRefe_baby_rating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    rationg_Item = dataSnapshot1.getValue(ration_DataModel.class);
                    rationg_Item.setRatig_dataKey(dataSnapshot1.getKey());
                    if(rationg_Item.getPd_image().contains(image)){
                        textView_ratingValue.setText("Rating:" +rationg_Item.getPd_ratig_value());
                        ratingBar.setRating(rationg_Item.pd_ratig_value);

                        sharedPrefere_ration.edit().putString("image_key",rationg_Item.getPd_image()).apply();
                        sharedPrefere_ration.edit().putString("pd_cord",rationg_Item.getPd_cord()).apply();
                        sharedPrefere_ration.edit().putString("pd_name",rationg_Item.getPd_name()).apply();
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
                            databaseRefe_baby_rating.child(rationg_Item.getRatig_dataKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                            Toast.makeText(babyPd_Category.this, "Ration Update SuccessFull..", Toast.LENGTH_SHORT).show();
                                    sharedPrefere_ration.edit().clear();
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

                            String key=databaseRefe_baby_rating.push().getKey();  //fierbase add
                            ration_DataModel ration_data=new ration_DataModel(product_Cord,product_Name,image,rating);
                            databaseRefe_baby_rating.child(key).setValue(ration_data).addOnSuccessListener(new OnSuccessListener<Void>() {
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
         Toast.makeText(babyPd_Category.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIntentData() {
  Bundle bundle=getIntent().getExtras();

        product_Name=bundle.getString("pd_name");
        product_Cord=bundle.getString("pd_cord");
        prodcut_price =bundle.getInt("pd_price");
        product_Drecpt=bundle.getString("pd_Desr");
        pd_Stock=bundle.getInt("pd_stock");
        image=bundle.getString("pd_image");

        size_pd=bundle.getString("pd_size").split(",");
        arrayList_pdSize.add("Select product Size..");
        for(String size:size_pd){
            arrayList_pdSize.add(size);
        }
        arrayAdapter_pdSize=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,arrayList_pdSize);
        spinner.setAdapter(arrayAdapter_pdSize);

        //Toast.makeText(this, "Data:"+size_pd, Toast.LENGTH_SHORT).show();

        textView_pdCord.setText("product Cord:"+product_Cord);
        textView_pdName.setText(product_Name);
        textView_pdstock.setText("Stock:"+ pd_Stock);
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
            Toast.makeText(this, "please prodcut Size Select..", Toast.LENGTH_SHORT).show();
             spinner.requestFocus();
        }else{
           // total_quan=pd_Stock-num_Quantity;
           // textView_pdstock.setText("Stock :"+total_quan);
            String pd_size=spinner.getSelectedItem().toString();
             String mykey= databaseRefe_Category.push().getKey();

   babyCategory_Model addItem=new babyCategory_Model(product_Cord,product_Name,image,pd_size,prodcut_price,num_Quantity,pd_discount,user_gmail);
      databaseRefe_Category.child(mykey).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {

  Toast.makeText(getApplicationContext(), "Your Cart Add SuccessFull..", Toast.LENGTH_SHORT).show();
             /* // Read from the database
              databaseRefere_stockQuantity.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      HashMap<String, Object> hashMap_Update = new HashMap<>();
                   for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                       baby_product_Model itemData = dataSnapshot1.getValue(baby_product_Model.class);
                    itemData.setBaby_pd_DataKey(dataSnapshot1.getKey());
                    //   hashMap_Update.put("baby_pd_stock",total_quan);

   if(itemData.getBaby_pd_id().contains(product_Cord)){
  databaseRefere_stockQuantity.child(itemData.getBaby_pd_DataKey()).updateChildren(hashMap_Update).addOnSuccessListener(new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void aVoid) {
       editText_pd_Size.setText("");
     Toast.makeText(getApplicationContext(), "Your Card Add SuccessFull..", Toast.LENGTH_SHORT).show();
              }
          });

                    }
                   }
                  }

                  @Override
                  public void onCancelled(DatabaseError error) {
               Toast.makeText(babyPd_Category.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                  }
              });
              */
          }
      });
        }

    }
    public void go_toShowCategory(View view) {
        Intent intent=new Intent(getApplicationContext(),baby_Category_Show.class);
        startActivity(intent);
        finish();
    }
}