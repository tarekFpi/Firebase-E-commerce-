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

public class phon_Category_show extends AppCompatActivity {
    private RecyclerView recyclerView;
    private phon_Category_ShowAdapter adapter;
    private List<phon_Category_Model> phon_category_modelList=new ArrayList<>();
    private DatabaseReference databaseRef_show;
    private TextView textView_totalPrice;
    private  int  pdPrice,total_price,discount;
    private   Cap_Category_Model getItem;
    private SharedPreferences sharedPreferences_phonCategor,sharedPreferences_userGmall;
    private DatabaseReference  databaseRefe_category_setQuantity;
    private   int current_quanit, stock_quant,quant_Firebase;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences_add_quantity;
    int new_Quant ;
    String item_key=null;
    String user_gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Android_phon_product_Add
        setContentView(R.layout.activity_phon__category_show);
        recyclerView=(RecyclerView)findViewById(R.id.phon_category_showRcycler);
        textView_totalPrice=(TextView)findViewById(R.id.phon_total_score);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      databaseRef_show= FirebaseDatabase.getInstance().getReference("Android_phon_product_CategoryAdd");
      databaseRefe_category_setQuantity= FirebaseDatabase.getInstance().getReference("Android_phon_product_Add");
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("please Lodding");
        progressDialog.show();

        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");
        // Read from the database
        databaseRef_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phon_category_modelList.clear();
            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
              phon_Category_Model getItem=dataSnapshot1.getValue(phon_Category_Model.class);
                getItem.setPhon_category_key(dataSnapshot1.getKey());


                if(user_gmail.contains(getItem.getUser_gmail())){
                    discount=getItem.getPhon_category_discount();
                    total_price=getItem.getPhon_category_price()*getItem.getPhon_category_quantity()+total_price;
                    total_price=total_price-discount;

                    phon_category_modelList.add(getItem) ;

                    textView_totalPrice.setText("Amount:"+total_price);
                    adapter=new phon_Category_ShowAdapter(getApplicationContext(),phon_category_modelList);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();

                    adapter.setOnItemClickLisiner(new phon_Category_ShowAdapter.onItem_ClickLisiner() {
                        @Override
                        public void onClick(int position) {
                            String name=phon_category_modelList.get(position).getPhon_category_Name();
                            Toast.makeText(phon_Category_show.this, "Name:"+name, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDelet(int position) {
                phon_Category_Model item_position=phon_category_modelList.get(position);
                             String mykey=item_position.getPhon_category_key();
                            databaseRef_show.child(mykey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                  adapter.notifyDataSetChanged();
         Toast.makeText(phon_Category_show.this, "Your Category Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                                }
                            });

                          //  Map<String, Object> hashMap = new HashMap<String, Object>();
                           // sharedPreferences_add_quantity=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
                            // Read from the database
                           /* databaseRefe_category_setQuantity.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //phon_Model
                                    Map<String, Object> hashMap = new HashMap<String, Object>();
                                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                        phon_Model getItem=dataSnapshot1.getValue(phon_Model.class);
                                        getItem.setPhon_pdKey(dataSnapshot1.getKey());

                                        if(getItem.getPhon_pdId().contains(item_position.getPhon_category_id())){
                                            quant_Firebase = getItem.getPhon_pd_stock();
                                            current_quanit = item_position.getPhon_category_quantity();
                                            new_Quant=current_quanit+quant_Firebase;

                                            item_key=getItem.getPhon_pdKey();
                                            sharedPreferences_add_quantity.edit().putInt("new_totalQuantity",new_Quant).commit();
                                            sharedPreferences_add_quantity.edit().putString("item_myKey",item_key).commit();
                                            sharedPreferences_add_quantity.edit().commit();
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Toast.makeText(phon_Category_show.this, "Remove Failde..", Toast.LENGTH_SHORT).show();
                                }
                            });*/

                          /*  String get_key=sharedPreferences_add_quantity.getString("item_myKey","Data Not Found");
                            int pQunatity=sharedPreferences_add_quantity.getInt("new_totalQuantity" ,0);
                            hashMap.put("product_quantity",pQunatity);

                            hashMap.put("phon_pd_stock",pQunatity);
                            databaseRefe_category_setQuantity.child(get_key).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    databaseRef_show.child(item_position.getPhon_category_key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            if(phon_category_modelList.isEmpty()){
                                                textView_totalPrice.setText("Amount:");
                                                total_price=0;
                                            }
                                            Toast.makeText(phon_Category_show.this, "Order Remove SuccessFull..", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(phon_Category_show.this, "Order Remove UnsuccessFull.."+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });*/

                        }
                    });
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(phon_Category_show.this, "Your ON Order..", Toast.LENGTH_SHORT).show();
                }

            }
                if(phon_category_modelList.isEmpty()){
                    progressDialog.dismiss();
                    textView_totalPrice.setText("Amount:"+0);
                }


            }
            @Override
            public void onCancelled(DatabaseError error) {
       Toast.makeText(phon_Category_show.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buy_category_Data(View view) {
        if(total_price ==0){
            Toast.makeText(phon_Category_show.this, "Your Total price is Empty...", Toast.LENGTH_SHORT).show();
        }else{

        try {
            final StringBuffer stringBuffer=new StringBuffer();
            sharedPreferences_phonCategor=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences_phonCategor.edit();
            editor.putString("myKey","phon_CategoryShow_activity").apply();
            editor.commit();

            // Read from the database
            databaseRef_show.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                        phon_Category_Model getItem=dataSnapshot1.getValue(phon_Category_Model.class);
                        getItem.setPhon_category_key(dataSnapshot1.getKey());

                        stringBuffer.append("Id ="+getItem.getPhon_category_id()+",");
                        stringBuffer.append("Name="+getItem.getPhon_category_Name()+",");
                        stringBuffer.append("Quantity="+getItem.getPhon_category_quantity()+",");
                        stringBuffer.append("Item_Price="+getItem.getPhon_category_price()+",");
                        stringBuffer.append("Discount="+getItem.getPhon_category_discount()+"/");
                    }

                    Intent intent=new Intent(phon_Category_show.this,User_Address.class);
                    intent.putExtra("data_all_key", (Serializable) stringBuffer);
                    intent.putExtra("pd_totalPrice",total_price);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError error) {
        Toast.makeText(phon_Category_show.this, "Data Lodding Failde..."+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception exception){
     Toast.makeText(this, "Your Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
        }
    }
}