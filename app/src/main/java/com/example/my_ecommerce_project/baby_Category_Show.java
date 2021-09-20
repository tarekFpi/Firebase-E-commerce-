package com.example.my_ecommerce_project;

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

public class baby_Category_Show extends AppCompatActivity {
    private RecyclerView recyclerView;
    //private DatabaseReference databaseRefere_setQuantity;
    private DatabaseReference databaseRefere_baby_category_Show;
    private DatabaseReference databaseRefere_baby_category_setQuantity;
    private  int  pdPrice,total_price,discount;
    private TextView textView_total;
    private SharedPreferences sharedPreferences__baby_category,sharedPreferences_userGmall;
    private List<babyCategory_Model> categoryModelList=new ArrayList<>();
    private baby_Category_Adapter adapter;
    private ProgressDialog progressDialog;

    private int current_quanit, quant_Firebase;
    private int new_Quant=0;
   private String item_key;
   private String user_gmail;
    private SharedPreferences sharedPreferences_add_quantity; //stock
   private baby_product_Model stock_Item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby__category__show);
        databaseRefere_baby_category_Show= FirebaseDatabase.getInstance().getReference("Baby_product_CategoryAdd");
        databaseRefere_baby_category_setQuantity= FirebaseDatabase.getInstance().getReference("Baby_product_Add");

        textView_total=(TextView)findViewById(R.id.baby_category_total_score);
        recyclerView=(RecyclerView)findViewById(R.id.baby_categoryShow_recyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite...");
        progressDialog.show();


        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");
        // Read from the database
        databaseRefere_baby_category_Show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryModelList.clear();
             for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                 babyCategory_Model add_item=dataSnapshot1.getValue(babyCategory_Model.class);
                 add_item.setBaby_pdCategory_Key(dataSnapshot1.getKey());


                if(user_gmail.contains(add_item.getUser_gmail())){
                    discount=add_item.getBaby_pd_discount();
                    total_price=add_item.getBaby_pd_price()*add_item.getBaby_pd_quantity()+total_price;
                    total_price=total_price-discount;

                    categoryModelList.add(add_item);
                    textView_total.setText("Amount:"+total_price);
                    adapter=new baby_Category_Adapter(getApplicationContext(),categoryModelList);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();


                    adapter.setOnItemClickListener(new baby_Category_Adapter.onItemClickListener() {
                        @Override
                        public void onItemclick(int position) {
                            final babyCategory_Model item_position=categoryModelList.get(position);
                            Toast.makeText(baby_Category_Show.this, "Name:"+item_position.getBaby_pd_Name(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onDelete(int position) {
                       babyCategory_Model item_position=categoryModelList.get(position);
                            String mykey=item_position.getBaby_pdCategory_Key();
                            databaseRefere_baby_category_Show.child(mykey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    adapter.notifyDataSetChanged();
        Toast.makeText(baby_Category_Show.this, "Your Category Delete SuccesFull..", Toast.LENGTH_SHORT).show();
                                }
                            });

                         /*   sharedPreferences_add_quantity=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);

                            try {
                                final babyCategory_Model item_position=categoryModelList.get(position);
                                final Map<String, Object> hashMap = new HashMap<String, Object>();

                                // Read from the database
                                databaseRefere_baby_category_setQuantity.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                            stock_Item=dataSnapshot1.getValue(baby_product_Model.class);
                                            stock_Item.setBaby_pd_DataKey(dataSnapshot1.getKey());

                                            if(stock_Item.getBaby_pd_id().contains(item_position.getBaby_pd_id())){
                                                quant_Firebase=  stock_Item.getBaby_pd_stock();
                                                current_quanit=  item_position.getBaby_pd_quantity();
                                                new_Quant=quant_Firebase+current_quanit;
                                                item_key=stock_Item.getBaby_pd_DataKey();

                                                // Toast.makeText(baby_Category_Show.this, "Stock .."+new_Quant, Toast.LENGTH_SHORT).show();
                                                sharedPreferences_add_quantity.edit().putInt("new_totalQuantity",new_Quant).commit();
                                                sharedPreferences_add_quantity.edit().putString("item_myKey",item_key).commit();
                                                sharedPreferences_add_quantity.edit().commit();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        Toast.makeText(baby_Category_Show.this, "Stock Data Lodding Failde..", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                String get_key=sharedPreferences_add_quantity.getString("item_myKey","Data Not Found");
                                final int pQunatity=sharedPreferences_add_quantity.getInt("new_totalQuantity" ,0);
                                hashMap.put("baby_pd_stock",pQunatity);
                                // stock_Item.setBaby_pd_stock(pQunatity);
                                databaseRefere_baby_category_setQuantity.child(get_key).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        String mkey=item_position.getBaby_pdCategory_Key();


                                        databaseRefere_baby_category_Show.child(mkey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(baby_Category_Show.this, "Remove SuccessFull", Toast.LENGTH_SHORT).show();
                                                if(categoryModelList.isEmpty()){
                                                    textView_total.setText("Amount:"+0);
                                                    total_price=0;
                                                }
                                                adapter.notifyDataSetChanged();
                                            }
                                        });

                                    }
                                });
                            }catch (Exception exception){
                                Toast.makeText(baby_Category_Show.this, "Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
*/
                        }
                    });
                 }else{
                    progressDialog.dismiss();
            Toast.makeText(baby_Category_Show.this, "ON Order..", Toast.LENGTH_SHORT).show();
                }
             }
                if(categoryModelList.isEmpty()){
                    progressDialog.dismiss();
                    textView_total.setText("Amount:"+0);
                }


            }
            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
      Toast.makeText(baby_Category_Show.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buy_category_baby_Data(View view) {

        final StringBuffer stringBuffer=new StringBuffer();
        sharedPreferences__baby_category=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences__baby_category.edit();
        editor.putString("my_Key","baby_CategoryShow_activity").apply();
        editor.commit();
        // Read from the database
        databaseRefere_baby_category_Show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    babyCategory_Model add_item=dataSnapshot1.getValue(babyCategory_Model.class);
                    add_item.setBaby_pdCategory_Key(dataSnapshot1.getKey());

                    stringBuffer.append("Id ="+add_item.getBaby_pd_id()+",");
                    stringBuffer.append("Name="+add_item.getBaby_pd_Name()+",");
                    stringBuffer.append("Quantity="+add_item.getBaby_pd_quantity()+",");
                    stringBuffer.append("Item_Price="+add_item.getBaby_pd_price()+",");
                    stringBuffer.append("Discount="+add_item.getBaby_pd_discount()+"/");
                }
                if(!categoryModelList.isEmpty()){
                    Intent intent=new Intent(baby_Category_Show.this,User_Address.class);
                    intent.putExtra("data_all_key", (Serializable) stringBuffer);
                    intent.putExtra("pd_totalPrice",total_price);
                    startActivity(intent);
                    finish();
                }else{
             Toast.makeText(baby_Category_Show.this, "Your Total Price Empty...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
           Toast.makeText(baby_Category_Show.this, "Your Data Lodding Failde....", Toast.LENGTH_SHORT).show();
            }
        });
    }
}