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

public class keybord_Category_Show extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference databaseRefere_setQuantity;
    private DatabaseReference databaseRefere_keybord_mouseShow;
    private DatabaseReference databaseRefere_keybord_mouse_setQuantity;
    private  int  pdPrice,total_price,discount;
    private TextView textView_total;
    private SharedPreferences sharedPreferences_keybord_mouse;
    private List<keybord_CategoryModel>categoryModelList=new ArrayList<>();
    private keybord_mouseCategory_Adapter adapter;
    private   int current_quanit,totoal_quantity,quant_Firebase;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences_add_quantity,sharedPreferences_userGmall;  // stock add
    private String user_gmail;
    int new_Quant;
    String item_key=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keybord__category__show);
        databaseRefere_keybord_mouseShow= FirebaseDatabase.getInstance().getReference("Keybord_mouse_Category_Add");
        databaseRefere_keybord_mouse_setQuantity= FirebaseDatabase.getInstance().getReference("Keybord_Mouse_Add");

        recyclerView=(RecyclerView)findViewById(R.id.keybord_mouse_categoryShow_recyler);
        textView_total=(TextView)findViewById(R.id.cate_keymouse_total_score);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite...");
        progressDialog.show();


        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");
        // Read from the database
        databaseRefere_keybord_mouseShow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryModelList.clear();
             for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                 keybord_CategoryModel addItem=dataSnapshot1.getValue(keybord_CategoryModel.class);
                 addItem.setCategory_key_mouse_DataKey(dataSnapshot1.getKey());



              if(user_gmail.contains(addItem.getUser_gmail())){
                  discount=addItem.getCategory_key_mouse_discount();
                  total_price=addItem.getCategory_key_mouse_price()*addItem.getCategory_key_mouse_quantity()+total_price;
                  total_price=total_price-discount;
                  categoryModelList.add(addItem);

                  textView_total.setText("Total Price:"+total_price);
                  adapter=new keybord_mouseCategory_Adapter(getApplicationContext(),categoryModelList);
                  recyclerView.setAdapter(adapter);
                  progressDialog.dismiss();

                  adapter.setOnItemClickLisiner(new keybord_mouseCategory_Adapter.onItem_ClickLisiner() {
                      @Override
                      public void onClick(int position) {
                          String name=categoryModelList.get(position).getCategory_key_mouse_name();
                          Toast.makeText(keybord_Category_Show.this, "Name:"+name, Toast.LENGTH_SHORT).show();
                      }

                      @Override
                      public void onDelet(int position) {
                          keybord_CategoryModel item_position=categoryModelList.get(position);
                          String mykey=item_position.getCategory_key_mouse_DataKey();
                          databaseRefere_keybord_mouseShow.child(mykey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                  adapter.notifyDataSetChanged();
                                  Toast.makeText(keybord_Category_Show.this, "Your Category Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                              }
                          });
                      }
                  });
              } else{
                  Toast.makeText(keybord_Category_Show.this, "Please Your Order..", Toast.LENGTH_SHORT).show();
              }



             /*   adapter.setOnItemClickLisiner(new keybord_mouseCategory_Adapter.onItem_ClickLisiner() {
                      @Override
                      public void onclick(int position) {
                   String name=categoryModelList.get(position).getCategory_key_mouse_name();
               Toast.makeText(keybord_Category_Show.this, "Name:"+name, Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   }
                      @Override
                      public void onDelet(int position) {
          Toast.makeText(keybord_Category_Show.this, "hello", Toast.LENGTH_SHORT).show();
                    keybord_CategoryModel item_position=categoryModelList.get(position);
                          String mykey=item_position.getCategory_key_mouse_DataKey();
                databaseRefere_keybord_mouseShow.child(mykey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                  adapter.notifyDataSetChanged();
           Toast.makeText(keybord_Category_Show.this, "Your Category Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                              }
                          });
*//*

                        HashMap<String, Object> hashMap = new HashMap<>();
                          sharedPreferences_add_quantity=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
                          final keybord_CategoryModel item_position=categoryModelList.get(position);

                          // Read from the database
                          databaseRefere_keybord_mouse_setQuantity.addValueEventListener(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot dataSnapshot) {
                                  Map<String, Object> hashMap = new HashMap<String, Object>();

                                  for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                      keybord_mouse_Model   stock_data=dataSnapshot1.getValue(keybord_mouse_Model.class);
                                      stock_data.setKeyMouse_Data_Key(dataSnapshot1.getKey());

                                      if(stock_data.getKeyMouse_id().contains(item_position.getCategory_key_mouse_id())){
                                          quant_Firebase = stock_data.getKeyMouse_stock();
                                          totoal_quantity = item_position.getCategory_key_mouse_quantity();
                                          current_quanit=current_quanit+quant_Firebase;

                                          item_key=stock_data.getKeyMouse_Data_Key();
                                          sharedPreferences_add_quantity.edit().putInt("new_totalQuantity",new_Quant).commit();
                                          sharedPreferences_add_quantity.edit().putString("item_myKey",item_key).commit();
                                          sharedPreferences_add_quantity.edit().commit();
                                      }
                                  }
                              }
                              @Override
                              public void onCancelled(DatabaseError error) {
                                  progressDialog.dismiss();
                                  Toast.makeText(keybord_Category_Show.this, "Delete Data Failde...", Toast.LENGTH_SHORT).show();
                              }
                          });
*//*

                          *//*String get_key=sharedPreferences_add_quantity.getString("item_myKey","Data Not Found");
                          int pQunatity=sharedPreferences_add_quantity.getInt("new_totalQuantity" ,0);
                          hashMap.put("product_quantity",pQunatity);

                          hashMap.put("keyMouse_stock",totoal_quantity);
                          databaseRefere_keybord_mouse_setQuantity.child(get_key).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                  databaseRefere_keybord_mouseShow.child(item_position.getCategory_key_mouse_DataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void aVoid) {
                                          Toast.makeText(keybord_Category_Show.this, "Order Remove SuccessFull..", Toast.LENGTH_SHORT).show();
                                          if(!categoryModelList.isEmpty()){
                                              textView_total.setText("Amount:"+0);
                                              total_price=0;
                                          }
                                      }
                                  });
                              }
                          }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {

                                  Toast.makeText(keybord_Category_Show.this, "Order Remove UnsuccessFull"+e.getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          });
                         *//*
                      }
                  });*/

             }

                if(categoryModelList.isEmpty()){
                    progressDialog.dismiss();
                    textView_total.setText("Total Price:"+0);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
     Toast.makeText(keybord_Category_Show.this, "Your Data Lodding Failde:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buy_category_keybord_mouse_Data(View view) {
        if(total_price==0){
      Toast.makeText(this, "Total price is Empty..", Toast.LENGTH_SHORT).show();

        }else{
            try {
                final StringBuffer stringBuffer=new StringBuffer();
                sharedPreferences_keybord_mouse=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences_keybord_mouse.edit();
                editor.putString("myKey","keybord_mouse_CategoryShow_activity").apply();
                editor.commit();
                // Read from the database
                databaseRefere_keybord_mouseShow.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            keybord_CategoryModel addItem=dataSnapshot1.getValue(keybord_CategoryModel.class);
                            addItem.setCategory_key_mouse_DataKey(dataSnapshot1.getKey());

                            stringBuffer.append("Id ="+addItem.getCategory_key_mouse_id()+",");
                            stringBuffer.append("Name="+addItem.getCategory_key_mouse_name()+",");
                            stringBuffer.append("Quantity="+addItem.getCategory_key_mouse_quantity()+",");
                            stringBuffer.append("Item_Price="+addItem.getCategory_key_mouse_price()+",");
                            stringBuffer.append("Discount="+addItem.getCategory_key_mouse_discount()+"/");
                        } if(!categoryModelList.isEmpty()){
                            Intent intent=new Intent(keybord_Category_Show.this,User_Address.class);
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
                        Toast.makeText(keybord_Category_Show.this, "Your Product Lodding Failde..", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception exception){
                Toast.makeText(this, "Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
     }
}