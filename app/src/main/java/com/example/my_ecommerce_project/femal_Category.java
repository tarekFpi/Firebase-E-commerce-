package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import yuku.ambilwarna.AmbilWarnaDialog;

public class femal_Category extends AppCompatActivity {
 private TextView textView_pdName,textView_pdCord,textView_pdDrecp,textView_pdstock,textView_pd_price;
 private ImageSlider imageSlider;
 String  image,product_Name,product_Cord,product_Drecpt;
  private int prodcut_price,pd_Stock;
 private ElegantNumberButton numberButton;
 private DatabaseReference databaseRefe_Category;
private TextView textView_select_size,textView_Exit,textView_discount,textView_sizeShow;

private  String Select_size;
 private  femal_product_Model get_Item;
 private RatingBar ratingBar;
 private TextView textView_rationValue;
 private DatabaseReference databaseRefe_rationData;
 private DatabaseReference databaseRef_stockUpdate;
 public  String order_status="0";
 //public   String pd_image,pd_name,pd_Cord,pd_price;
    private SharedPreferences sharedPreferences,sharedPreferences_userGmall;
   private int discount_pd;
    private   ration_DataModel ration_Item;
    private String user_gmail;
    private EditText editText_pdSize;

    private ArrayList<String>arrayList_pdSize=new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter_pdSize;
    private String[] size_pd;
    private Spinner spinner;
private Button button_Add_cardData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_femal__category);
        databaseRefe_Category= FirebaseDatabase.getInstance().getReference("femal_product_Category");

        databaseRef_stockUpdate= FirebaseDatabase.getInstance().getReference("Femal_productAdd");
        databaseRefe_rationData= FirebaseDatabase.getInstance().getReference("Femal_rating_ImageSave");

        textView_pdName =(TextView)findViewById(R.id.category_pdName);
       // textView_pdstock =(TextView)findViewById(R.id.category_pdstock);
        textView_pdDrecp =(TextView)findViewById(R.id.category_drecpt);
        textView_pd_price =(TextView)findViewById(R.id.category_Price);
        textView_pdCord =(TextView)findViewById(R.id.category_pdCord);
        ratingBar=(RatingBar)findViewById(R.id.rating_id);
         spinner=(Spinner)findViewById(R.id.spinner_femal_pd_size);
        button_Add_cardData=(Button)findViewById(R.id.femal_add_to_cart);

        textView_rationValue=(TextView)findViewById(R.id.text_rating_value);

        textView_discount=(TextView)findViewById(R.id.category_discountId);

        textView_rationValue.setText(""+ratingBar.getRating());

         imageSlider=(ImageSlider)findViewById(R.id.femal_rateImage);
         numberButton=(ElegantNumberButton)findViewById(R.id.category_Quantity);
        getIntent_Data();

        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");

        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
       // value_ching=ratingBar.getRating();
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
                            sharedPreferences.edit().putString("image_key",ration_Item.getPd_image()).apply();
                            sharedPreferences.edit().putString("pd_cord",ration_Item.getPd_cord()).apply();
                            sharedPreferences.edit().putString("pd_name",ration_Item.getPd_name()).apply();
                          sharedPreferences.edit().commit();

                        }
                    }
                    String pd_image= sharedPreferences.getString("image_key","Data Not Found");

                    if(pd_image.contains(image)){
                        final HashMap<String,Object>hashMap=new HashMap<>();
                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                                hashMap.put("pd_ratig_value",rating);
                                databaseRefe_rationData.child(ration_Item.getRatig_dataKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(femal_Category.this, "Ration Update SuccessFull..", Toast.LENGTH_SHORT).show();
                                        sharedPreferences.edit().clear();
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
            Toast.makeText(femal_Category.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception exception){
            Toast.makeText(this, "Exception:", Toast.LENGTH_SHORT).show();
        }


        button_Add_cardData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num_Quantity = Integer.parseInt(numberButton.getNumber().toString());
                final int total_quan,Sell_total_price;

                if(spinner.getSelectedItem().toString().contains("Select product Size..")){
                    spinner.requestFocus();
                    Toast.makeText(femal_Category.this, "please prodcut Size Select..", Toast.LENGTH_SHORT).show();

                }else {
                    String pd_size=spinner.getSelectedItem().toString();
                    femal_category_Model item = new femal_category_Model(product_Cord, product_Name, prodcut_price, num_Quantity,pd_size,image,product_Drecpt,user_gmail);

                    String data_key = databaseRefe_Category.push().getKey();
                    databaseRefe_Category.child(data_key).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                      Toast.makeText(getApplicationContext(), "Your Cart Add SuccessFull..", Toast.LENGTH_SHORT).show();

                            try {
                          }catch (Exception exception){
                                Toast.makeText(getApplicationContext(), "Your  Exception.."+exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Your Card Add Faild..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }


    void getIntent_Data(){
    // sharedPreferences_status=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
       Bundle bundle=getIntent().getExtras();

           product_Name=bundle.getString("pd_name");
            product_Cord=bundle.getString("pd_cord");
            prodcut_price =bundle.getInt("pd_price");
            product_Drecpt=bundle.getString("pd_Desr");
           // pd_Stock=bundle.getInt("pd_stock");
            image=bundle.getString("pd_image");

        textView_pdCord.setText("product Cord:" + product_Cord);
        textView_pdName.setText(product_Name);
        //textView_pdstock.setText("Stock:" + pd_Stock);
        textView_pdDrecp.setText("" + product_Drecpt);
        textView_pd_price.setText("Price:" +prodcut_price);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(image, product_Name, ScaleTypes.FIT));
        slideModels.add(new SlideModel(image, product_Name, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        //pd_image
        arrayList_pdSize.add("Select product Size..");
        size_pd=bundle.getString("pd_size").split(",");
        for(String size:size_pd){
         arrayList_pdSize.add(size);
        }
  arrayAdapter_pdSize=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,arrayList_pdSize);
         spinner.setAdapter(arrayAdapter_pdSize);

    }


    public void Add_cardData() {
  /*   if (pd_Stock <num_Quantity) {
            Toast.makeText(this, "Your Order Over..", Toast.LENGTH_SHORT).show();
        } */
   /*
    // Read from the database
    databaseRef_stockUpdate.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            HashMap<String, Object> hashMap = new HashMap<>();

            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                get_Item = dataSnapshot1.getValue(femal_product_Model.class);
                get_Item.setData_key(dataSnapshot1.getKey());
                hashMap.put("product_quantity",total_quan);

                if(get_Item.getPd_Cord().contains(product_Cord)){
                    databaseRef_stockUpdate.child(get_Item.getData_key()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Your Card Add SuccessFull..", Toast.LENGTH_SHORT).show();
                            editText_pdSize.setText("");
                        }
                    });
                }
            }
        }
        @Override
        public void onCancelled(DatabaseError error) {
            Toast.makeText(femal_Category.this, "Data UpdateFailde"+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
    */

    }

    public void go_toShowCategory(View view) {
        Intent intent=new Intent(femal_Category.this,femal_category_show.class);
        startActivity(intent);
    }


   /* public void Ratin_valueAdd(View view) {
        float ratAdd;
          *//*  if(view.isClickable()){
                // Read from the database
                databaseRefe_rationData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ration_DataModel ration_Item = dataSnapshot1.getValue(ration_DataModel.class);
                            ration_Item.setRatig_dataKey(dataSnapshot1.getKey());

                         if(ration_Item.getPd_image().contains(image)){
                           int value= 1+Integer.parseInt(ration_Item.getPd_ratig_value().toString());
                            textView_rationValue.setText("Rating:"+value);
                           ratingBar.setRating((float)value);
                         }else{

                         }
                        }
                        }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    Toast.makeText(femal_Category.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }*//*
      //  ratingBar.setRating((float)value);



    }*/

   /* @Override
    protected void onStart() {


        DatabaseReference databaseRef_femal_product= FirebaseDatabase.getInstance().getReference("Femal_productAdd");
        DatabaseReference databaseRef_allCap= FirebaseDatabase.getInstance().getReference("Cap_product_Add");
        try {
            if(order_status.equals("1")){
                Bundle bundle=getIntent().getExtras();
                pd_image= bundle.getString("image_key");
                pd_name= bundle.getString("pdname_key");
                pd_Cord= bundle.getString("pdCord_key");
                pd_price=  bundle.getString("Price_key");

                // Read from the database
                databaseRef_femal_product.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            femal_product_Model Item = dataSnapshot1.getValue(femal_product_Model.class);
                            Item.setData_key(dataSnapshot1.getKey());
                            if(Item.getProduct_Image().contains(pd_image) && Item.getPd_Cord().contains(pd_Cord)){

                                textView_pdName.setText(pd_name);
                                textView_pdCord.setText(pd_Cord);
                                textView_pd_price.setText(Item.getProduct_price());
                                textView_pdCord.setText(pd_Cord);
                                textView_pdDrecp.setText(Item.getProduct_Desrcip());
                                textView_pdstock.setText(Item.getProduct_quantity());

                                List<SlideModel> slideModels=new ArrayList<>();
                                slideModels.add(new SlideModel(pd_image,pd_name, ScaleTypes.FIT));
                                slideModels.add(new SlideModel(pd_image,pd_name, ScaleTypes.CENTER_CROP));
                                imageSlider.setImageList(slideModels,ScaleTypes.FIT);
                            }else{

                                order_status="2";
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(femal_Category.this, "Femele Prodcut Again Failde..", Toast.LENGTH_SHORT).show();
                    }
                });

            }else if(order_status.equals("2")){
                // Read from the database
                databaseRef_allCap.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            Cap_product_Model cap_item=dataSnapshot1.getValue(Cap_product_Model.class);
                            cap_item.setCap_product_Key(dataSnapshot1.getKey());

                            if(cap_item.getCap_pdImage().contains(pd_image) && cap_item.getCap_pdCord().contains(pd_Cord)){

                                textView_pdName.setText(pd_name);
                                textView_pdCord.setText(pd_Cord);
                                textView_pd_price.setText(cap_item.getCap_pdPrice());
                                textView_pdCord.setText(pd_Cord);
                                textView_pdDrecp.setText(cap_item.getCap_pdDecr());
                                textView_pdstock.setText(cap_item.getCap_pdstock());

                                List<SlideModel> slideModels=new ArrayList<>();
                                slideModels.add(new SlideModel(pd_image,pd_name, ScaleTypes.FIT));
                                slideModels.add(new SlideModel(pd_image,pd_name, ScaleTypes.CENTER_CROP));
                                imageSlider.setImageList(slideModels,ScaleTypes.FIT);
                            } else{
                                Toast.makeText(femal_Category.this, "Your Data Not Found..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(femal_Category.this, "Cap product Faild..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }catch (Exception exception){
            Toast.makeText(this, "error:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

        super.onStart();
    }*/
}