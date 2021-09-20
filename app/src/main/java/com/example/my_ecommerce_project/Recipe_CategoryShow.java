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

public class Recipe_CategoryShow extends AppCompatActivity {
 private RecyclerView recyclerView;
 private Recipe_Category_Adapter adapter;
 private List<Recipe_CategoryModel>recipe_categoryModelList=new ArrayList<>();
private DatabaseReference databaseRef_categoryShow;
private DatabaseReference databaseRef_quantity_set;
 private SharedPreferences sharedPreferences;
    private TextView textView_totalPrice;
    private  int  pdPrice,total_price,discount;
    private ProgressDialog progressDialog;
    int current_quanit, quant_Firebase;
    int new_Quant ;
    String item_key=null;
    private SharedPreferences sharedPreferences_add_quantity, sharedPreferences_userGmall;
    private String user_gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__category_show);
        databaseRef_categoryShow= FirebaseDatabase.getInstance().getReference("Recipe_Category_Add");
        databaseRef_quantity_set= FirebaseDatabase.getInstance().getReference("Recipe_product_Add");

        recyclerView=(RecyclerView)findViewById(R.id.recipe_categoryShow_recyler);
        textView_totalPrice=(TextView)findViewById(R.id.recipe_total_score);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite...");
        progressDialog.show();

        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");
        // Read from the database
        databaseRef_categoryShow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                recipe_categoryModelList.clear();
            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
          Recipe_CategoryModel getItem=dataSnapshot1.getValue(Recipe_CategoryModel.class);
              getItem.setCategory_recipe_data_Key(dataSnapshot1.getKey());


              if(user_gmail.contains(getItem.getUser_gmail())){

                  discount=getItem.getCategory_recipe_discount();
                  total_price=getItem.getCategory_recipe_price()*getItem.getCategory_recipe_quanity()+total_price;
                  total_price=total_price-discount;

                  recipe_categoryModelList.add(getItem);
                  textView_totalPrice.setText("Amount:"+total_price);
                  adapter=new Recipe_Category_Adapter(getApplicationContext(),recipe_categoryModelList);
                  progressDialog.dismiss();
                  recyclerView.setAdapter(adapter);

                  adapter.setOnItemClickLisiner(new Recipe_Category_Adapter.onItem_ClickLisiner() {
                      @Override
                      public void onClick(int position) {
                String name=recipe_categoryModelList.get(position).getCategory_recipe_name();
            Toast.makeText(Recipe_CategoryShow.this, "Name:"+name, Toast.LENGTH_SHORT).show();
                      }

                      @Override
                      public void onDelet(int position) {
                            Recipe_CategoryModel item_position=recipe_categoryModelList.get(position);
                          databaseRef_categoryShow.child(item_position.getCategory_recipe_data_Key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                  adapter.notifyDataSetChanged();
        Toast.makeText(Recipe_CategoryShow.this, "Your Category Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                              }
                          });

                       /*   final Map<String, Object> hashMap = new HashMap<String, Object>();
                          final Recipe_CategoryModel item_position=recipe_categoryModelList.get(position);
                          sharedPreferences_add_quantity=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);

                          // Read from the database
                          databaseRef_quantity_set.addValueEventListener(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot dataSnapshot) {

                                  for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                                      Recipe_ModelAdd stock_item = dataSnapshot1.getValue(Recipe_ModelAdd.class);
                                      stock_item.setRecipe_Data_key(dataSnapshot1.getKey());

                                      if(stock_item.getRecipe_pd_id().contains(item_position.getCategory_recipe_id())) {
                                          quant_Firebase = stock_item.getRecipe_pd_stock();
                                          current_quanit = item_position.getCategory_recipe_quanity();
                                          item_key=stock_item.getRecipe_Data_key();
                                          new_Quant =current_quanit + quant_Firebase;

                                          //hashMap.put("recipe_pd_stock",new_Quant);
                                          /// Toast.makeText(Recipe_CategoryShow.this, "Order  "+stock_item.getRecipe_pd_id(), Toast.LENGTH_LONG).show();
                                          sharedPreferences_add_quantity.edit().putInt("new_totalQuantity",new_Quant).commit();
                                          sharedPreferences_add_quantity.edit().putString("item_myKey",item_key).commit();
                                          sharedPreferences_add_quantity.edit().commit();
                                      }
                                  }
                              }
                              @Override
                              public void onCancelled(DatabaseError error) {
                                  Toast.makeText(Recipe_CategoryShow.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          });

                          String get_key=sharedPreferences_add_quantity.getString("item_myKey","Data Not Found");
                          int pQunatity=sharedPreferences_add_quantity.getInt("new_totalQuantity" ,0);
                          hashMap.put("recipe_pd_stock",pQunatity);
                          Toast.makeText(Recipe_CategoryShow.this, ""+pQunatity, Toast.LENGTH_SHORT).show();

                          databaseRef_quantity_set.child(get_key).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                  String mkey=item_position.getCategory_recipe_data_Key();
                                  if(recipe_categoryModelList.isEmpty()){
                                      total_price=0;
                                      textView_totalPrice.setText("Amount:"+0);
                                  }

                                  databaseRef_categoryShow.child(mkey).removeValue();
                              }
                          }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  Toast.makeText(Recipe_CategoryShow.this, "Order Remove UnsuccessFull"+e.getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          });*/
                      }
                  });

              }else{
               progressDialog.dismiss();

 Toast.makeText(Recipe_CategoryShow.this, "Your Order Not Found..", Toast.LENGTH_SHORT).show();
              }
            }

                if(recipe_categoryModelList.isEmpty()){
                    progressDialog.dismiss();
                    textView_totalPrice.setText("Amount:"+0);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
        Toast.makeText(Recipe_CategoryShow.this, "Recipe Category Lodding Failde:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buy_category_recipe_Data(View view) {
        try {

            sharedPreferences=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("my_recipe_Key","recipe_categoryShow").apply();
            editor.commit();
            final StringBuffer stringBuffer=new StringBuffer();

            // Read from the database
            databaseRef_categoryShow.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                        Recipe_CategoryModel getItem=dataSnapshot1.getValue(Recipe_CategoryModel.class);
                        getItem.setCategory_recipe_data_Key(dataSnapshot1.getKey());

                        stringBuffer.append("Id ="+getItem.getCategory_recipe_id()+",");
                        stringBuffer.append("Name="+getItem.getCategory_recipe_name()+",");
                        stringBuffer.append("Quantity="+getItem.getCategory_recipe_quanity()+",");
                        stringBuffer.append("Item_Price="+getItem.getCategory_recipe_price()+",");
                    }if(!recipe_categoryModelList.isEmpty()){
                        Intent intent=new Intent(Recipe_CategoryShow.this,User_Address.class);
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
                    Toast.makeText(Recipe_CategoryShow.this, " Data Pussing Lodding  Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            progressDialog.dismiss();
         Toast.makeText(this, "Exception:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}