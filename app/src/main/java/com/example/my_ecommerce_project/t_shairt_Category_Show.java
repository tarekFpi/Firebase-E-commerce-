package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class t_shairt_Category_Show extends AppCompatActivity {
private RecyclerView recyclerView;
private T_shairt_Category_Adapte adapte;
private List<T_shairt_Category_Model>categoryModels_List=new ArrayList<>();
private DatabaseReference databaseRefer_categoryShow;
private DatabaseReference databaseRefer_setQuantity;
 private  int  pdPrice,total_price,discount;
 private TextView textView_pd_totalprice;
 private SharedPreferences sharedPreferences;
 private T_shairt_Category_Model getItem;
 private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences_add_quantity,sharedPreferences_userGmall;
    int current_quanit, quant_Firebase;
    int new_Quant;

  private String user_gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_shairt__category__show);

       databaseRefer_categoryShow= FirebaseDatabase.getInstance().getReference("T_shairt_Category_Save");
        databaseRefer_setQuantity= FirebaseDatabase.getInstance().getReference("Recipe_product_Add");

        recyclerView=(RecyclerView)findViewById(R.id.t_shairt_categoryShow_recyler);
        textView_pd_totalprice=(TextView)findViewById(R.id.t_shairt_total_score);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite...");
        progressDialog.show();

        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");
        // Read from the database
        databaseRefer_categoryShow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryModels_List.clear();
              for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
              final T_shairt_Category_Model getItem=dataSnapshot1.getValue(T_shairt_Category_Model.class);
              getItem.setT_Shairt_Category_dataKey(dataSnapshot1.getKey());


              if(user_gmail.contains(getItem.getUser_gmail())){

                  discount=getItem.getT_Shairt_Category_discount();
                  total_price=getItem.getT_Shairt_Category_price()*getItem.getT_Shairt_Category_quantity()+total_price;
                  total_price=total_price-discount;

                  categoryModels_List.add(getItem);
                  textView_pd_totalprice.setText("Amount:"+total_price);
                  adapte=new T_shairt_Category_Adapte(getApplicationContext(),categoryModels_List);
                  recyclerView.setAdapter(adapte);
                  progressDialog.dismiss();

                  adapte.setOnItemClickLisiner(new T_shairt_Category_Adapte.onItem_ClickLisiner() {
                      @Override
                      public void onClick(int position) {
                          String name= getItem.getT_Shairt_Category_Name();
                          Toast.makeText(t_shairt_Category_Show.this, "Name:"+name, Toast.LENGTH_SHORT).show();
                      }

             @Override
           public void onDelet(final int position) {
            ///  Map<String, Object> hashMap = new HashMap<String, Object>();
           //  sharedPreferences_add_quantity=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
    T_shairt_Category_Model item_position=categoryModels_List.get(position);

       String mykey=item_position.getT_Shairt_Category_dataKey();
     //Toast.makeText(t_shairt_Category_Show.this, "Data:"+mykey, Toast.LENGTH_SHORT).show();

   databaseRefer_categoryShow.child(mykey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
    public void onSuccess(Void aVoid) {
       adapte.notifyDataSetChanged();
  //textView_pd_totalprice.setText("Amount:"+total_price);

   Toast.makeText(t_shairt_Category_Show.this, "Your Category Delete SuccessFull...", Toast.LENGTH_SHORT).show();
       }
        });

                          // Read from the database
                       /*   databaseRefer_setQuantity.addValueEventListener(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot dataSnapshot) {
                                  for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                      Recipe_ModelAdd stock_item=dataSnapshot1.getValue(Recipe_ModelAdd.class);

                                      if(stock_item.getRecipe_pd_id().contains(item_position.getT_Shairt_Category_Cord())){

                                          quant_Firebase = stock_item.getRecipe_pd_stock();
                                          current_quanit = item_position.getT_Shairt_Category_quantity();
                                          item_key=stock_item.getRecipe_Data_key();
                                          new_Quant =current_quanit + quant_Firebase;
                                          sharedPreferences_add_quantity.edit().putInt("new_totalQuantity",new_Quant).commit();
                                          sharedPreferences_add_quantity.edit().putString("item_myKey",item_key).commit();
                                          sharedPreferences_add_quantity.edit().commit();
                                      }
                                  }
                              }
                              @Override
                              public void onCancelled(DatabaseError error) {
                                  Toast.makeText(t_shairt_Category_Show.this, "Revome Lodding Data Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          });
*//*
                          String get_key=sharedPreferences_add_quantity.getString("item_myKey","Data Not Found");
                          int pQunatity=sharedPreferences_add_quantity.getInt("new_totalQuantity" ,0);
                          hashMap.put("product_quantity",pQunatity);
                          databaseRefer_setQuantity.child(get_key).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                  String mkey=item_position.getT_Shairt_Category_dataKey();
                                  databaseRefer_categoryShow.child(mkey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void aVoid) {
                                          if(categoryModels_List.isEmpty()){
                                              textView_pd_totalprice.setText("Amount:"+0);
                                              total_price=0;
                                          }
                                          Toast.makeText(getApplicationContext(), "Order Remove SuccessFull", Toast.LENGTH_SHORT).show();
                                      }
                                  });
                              }
                          }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  Toast.makeText(getApplicationContext(), "Order Remove UnsuccessFull..."+e.getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          });*/

                      }
                  });
              }else{
               progressDialog.dismiss();
    Toast.makeText(t_shairt_Category_Show.this, "Your Order Not Match..", Toast.LENGTH_SHORT).show();
              }
              }

    if(categoryModels_List.isEmpty()){
       progressDialog.dismiss();
        textView_pd_totalprice.setText("Amount:"+0);
        }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
       Toast.makeText(t_shairt_Category_Show.this, "Data Lodding Failde:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buy_category_t_shairt_Data(View view) {

        final StringBuffer stringBuffer=new StringBuffer();
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("myKey_tshirt","t_shairt_category").apply();
        editor.commit();
        try {

            // Read from the database
            databaseRefer_categoryShow.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        getItem=dataSnapshot1.getValue(T_shairt_Category_Model.class);
                        getItem.setT_Shairt_Category_dataKey(dataSnapshot1.getKey());

                        stringBuffer.append("Id ="+getItem.getT_Shairt_Category_Cord()+",");
                        stringBuffer.append("Name="+getItem.getT_Shairt_Category_Name()+",");
                        stringBuffer.append("Quantity="+getItem.getT_Shairt_Category_quantity()+",");
                        stringBuffer.append("Item_Price="+getItem.getT_Shairt_Category_price()+",");
                        stringBuffer.append("Discount="+getItem.getT_Shairt_Category_discount()+"/");
                    }if(!categoryModels_List.isEmpty()){
                        Intent intent=new Intent(t_shairt_Category_Show.this,User_Address.class);
                        intent.putExtra("data_all_key", (Serializable) stringBuffer);
                        intent.putExtra("pd_totalPrice",total_price);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Your Total Price Empty...", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    progressDialog.dismiss();
           Toast.makeText(t_shairt_Category_Show.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception exception){
            progressDialog.dismiss();
            Toast.makeText(this, "Error:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}