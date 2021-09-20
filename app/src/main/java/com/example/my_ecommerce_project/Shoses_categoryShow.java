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

public class Shoses_categoryShow extends AppCompatActivity {
private RecyclerView recyclerView;
private Shose_CategoryAdapter adapter;
private List<shose_category_Add>shose_category_List=new ArrayList<>();
private DatabaseReference databaseReferen_Category_show;
private DatabaseReference databaseReferen_stock_setQuantity;
    private  int  pdPrice,total_price,discount;
    private TextView textView_pd_totalprice;
    private SharedPreferences sharedPreferences;
    private shose_category_Add getItem;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences_add_quantity,sharedPreferences_userGmall;
    private    int current_quanit,totoal_quantity,quant_Firebase;
    String item_key=null;
    private String user_gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoses_category_show);
        databaseReferen_Category_show= FirebaseDatabase.getInstance().getReference("Shose_categoryAdd");
        databaseReferen_stock_setQuantity= FirebaseDatabase.getInstance().getReference("Shose_product_Add");

        recyclerView=(RecyclerView)findViewById(R.id.shose_categoryShow_recyler);
        textView_pd_totalprice=(TextView)findViewById(R.id.shose_total_score);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite...");
        progressDialog.show();

        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");

        // Read from the database
        databaseReferen_Category_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shose_category_List.clear();
            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                shose_category_Add  category_add=dataSnapshot1.getValue(shose_category_Add.class);
                category_add.setCategory_shose_Key(dataSnapshot1.getKey());


                if(user_gmail.contains(category_add.getUser_gmail())){
                    discount=category_add.getCategory_Shose_discount();
                    total_price=category_add.getCategory_Shose_price()*category_add.getCategory_Shose_quantity()+total_price;
                    total_price=total_price-discount;

                    shose_category_List.add(category_add);
                    textView_pd_totalprice.setText("Amount:"+total_price);
                    adapter=new Shose_CategoryAdapter(getApplicationContext(),shose_category_List);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();

                    adapter.setOnItemClickLisiner(new Shose_CategoryAdapter.onItem_ClickLisiner() {
                        @Override
                        public void onClick(int position) {
                            shose_category_Add item_position=shose_category_List.get(position);
                            String name=item_position.getCategory_Shose_name();
                            Toast.makeText(Shoses_categoryShow.this, "Name:"+name, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDelet(int position) {
                   shose_category_Add item_position=shose_category_List.get(position);
      databaseReferen_Category_show.child(item_position.getCategory_shose_Key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                        public void onSuccess(Void aVoid) {
                         adapter.notifyDataSetChanged();
              Toast.makeText(Shoses_categoryShow.this, "Your Category Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                          /*  try {
                                Map<String, Object> hashMap = new HashMap<String, Object>();
                                //  HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                final shose_category_Add item_position=shose_category_List.get(position);

                                sharedPreferences_add_quantity=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);

                                // databaseReferen_stock_setQuantity

                                // Read from the database
                                databaseReferen_stock_setQuantity.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                                            shose_pdModel getItem = dataSnapshot1.getValue(shose_pdModel.class);
                                            getItem.setShose_product_key(dataSnapshot1.getKey());

                                            if(getItem.getShose_pd_id().contains(item_position.getPd_id())){
                                                quant_Firebase = getItem.getShose_stock();
                                                current_quanit = item_position.getCategory_Shose_quantity();
                                                totoal_quantity=current_quanit+quant_Firebase;

                                                item_key=getItem.getShose_product_key();
                                                sharedPreferences_add_quantity.edit().putInt("new_totalQuantity",totoal_quantity).commit();
                                                sharedPreferences_add_quantity.edit().putString("item_myKey",item_key).commit();
                                                sharedPreferences_add_quantity.edit().commit();

                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        Toast.makeText(Shoses_categoryShow.this, "Stock Data Lodding Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                                final String get_key=sharedPreferences_add_quantity.getString("item_myKey","Data Not Found");
                                int pQunatity=sharedPreferences_add_quantity.getInt("new_totalQuantity" ,0);
                                hashMap.put("shose_stock",pQunatity);

                                databaseReferen_stock_setQuantity.child(get_key).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        databaseReferen_Category_show.child(item_position.getCategory_shose_Key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Shoses_categoryShow.this, "Order Remove SuccessFull..", Toast.LENGTH_SHORT).show();
                                                if(shose_category_List.isEmpty()){
                                                    textView_pd_totalprice.setText("Amount:"+0);
                                                    total_price=0;
                                                }
                                            }
                                        });
                                        //  Toast.makeText(Shoses_categoryShow.this, "Order Remove SuccessFull..", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Shoses_categoryShow.this, "Order Remove UnSuccessFull..", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }catch (Exception exception){
                                Toast.makeText(Shoses_categoryShow.this, "Exception Shose Update:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }*/
                    });
                }else{
                 progressDialog.dismiss();
     Toast.makeText(Shoses_categoryShow.this, "No Order..", Toast.LENGTH_SHORT).show();
                }
            }
                if(shose_category_List.isEmpty()){
                    progressDialog.dismiss();
                    textView_pd_totalprice.setText("Amount:"+0);
                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
         Toast.makeText(Shoses_categoryShow.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void buy_category_shose_Data(View view) {

        final StringBuffer stringBuffer=new StringBuffer();
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("myKey_shose","shose_categoryShow").apply();
        editor.commit();

        try {

            // Read from the database
            databaseReferen_Category_show.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        getItem=dataSnapshot1.getValue(shose_category_Add.class);
                        getItem.setCategory_shose_Key(dataSnapshot1.getKey());

                        stringBuffer.append("Id ="+getItem.getPd_id()+",");
                        stringBuffer.append("Name="+getItem.getCategory_Shose_name()+",");
                        stringBuffer.append("Quantity="+getItem.getCategory_Shose_quantity()+",");
                        stringBuffer.append("Item_Price="+getItem.getCategory_Shose_price()+",");
                        stringBuffer.append("Discount="+getItem.getCategory_Shose_discount()+"/");
                    } if(!shose_category_List.isEmpty()){
                        Intent intent=new Intent(Shoses_categoryShow.this,User_Address.class);
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
        Toast.makeText(Shoses_categoryShow.this, "Lodding Failde..:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception exception){
            Toast.makeText(this, "Error:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}