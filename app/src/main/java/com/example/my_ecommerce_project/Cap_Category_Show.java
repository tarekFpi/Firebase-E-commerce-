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

public class Cap_Category_Show extends AppCompatActivity {
private RecyclerView recyclerView;
private Cap_Category_Adapte adapte;
private List<Cap_Category_Model>cap_category_models_List=new ArrayList<>();
private DatabaseReference databaseRef_show;
private TextView textView_totalPrice;
    private  int  pdPrice,total_price,discount;
    private   Cap_Category_Model getItem;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferences_add_quantity; //stock
    private DatabaseReference  databaseRefe_category_setQuantity;
    private   int current_quanit, stock_quant,totoal_quantity;
    private ProgressDialog progressDialog;
    private  String item_key;
    private SharedPreferences sharedPreferences_userGmall;
   private String user_gmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap__category__show);
        databaseRefe_category_setQuantity=FirebaseDatabase.getInstance().getReference("Cap_product_Add");
        databaseRef_show= FirebaseDatabase.getInstance().getReference("Cap_Category_Add");
        recyclerView=(RecyclerView)findViewById(R.id.cap_category_showRcycler);
        textView_totalPrice=(TextView)findViewById(R.id.cap_total_score);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite...");
        progressDialog.show();


        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");
       // Read from the database
        databaseRef_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cap_category_models_List.clear();
           for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
               getItem=dataSnapshot1.getValue(Cap_Category_Model.class);
               getItem.setCapCategory_key(dataSnapshot1.getKey());


               if(user_gmail.contains(getItem.getUser_gmail())){
                   total_price=getItem.getCapCategory_price()*getItem.getCapCategory_quantity()+total_price;
                   cap_category_models_List.add(getItem);
                   textView_totalPrice.setText("Amount:"+total_price);
                   adapte=new Cap_Category_Adapte(getApplicationContext(),cap_category_models_List);
                   recyclerView.setAdapter(adapte);
                   progressDialog.dismiss();


                   adapte.setOnItemClickListener(new Cap_Category_Adapte.onItemClickListener() {
                       @Override
                       public void onItemclick(int position) {
                           String itme_name= cap_category_models_List.get(position).getCapCategory_name();
                           Toast.makeText(Cap_Category_Show.this, "Name:"+itme_name, Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void onDelete(int position) {
                           final Cap_Category_Model item_position=cap_category_models_List.get(position);
                           databaseRef_show.child(item_position.getCapCategory_key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   adapte.notifyDataSetChanged();
                       Toast.makeText(Cap_Category_Show.this, "Your Category Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                               }
                           });

                       /*    Map<String, Object> hashMap = new HashMap<String, Object>();
                           sharedPreferences_add_quantity=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);

                           try {
                               databaseRefe_category_setQuantity.addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot dataSnapshot) {

                                       for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                           Cap_product_Model  Item_data=dataSnapshot1.getValue(Cap_product_Model.class);
                                           Item_data.setCap_product_Key(dataSnapshot1.getKey());

                                           if(Item_data.getCap_pdCord().contains(item_position.getCapCategory_id())){
                                               stock_quant=Item_data.getCap_pdstock();
                                               current_quanit=item_position.getCapCategory_quantity();
                                               totoal_quantity=stock_quant+current_quanit;
                                               item_key=Item_data.getCap_product_Key();
                                               sharedPreferences_add_quantity.edit().putInt("new_totalQuantity",totoal_quantity).commit();
                                               sharedPreferences_add_quantity.edit().putString("item_myKey",item_key).commit();
                                               sharedPreferences_add_quantity.edit().commit();
                                           }else{
                                               Toast.makeText(Cap_Category_Show.this, "Product Remove Failde",Toast.LENGTH_SHORT).show();
                                           }
                                           ///  Toast.makeText(Cap_Category_Show.this, ""+Item_data.getCap_pdCord(),Toast.LENGTH_SHORT).show();
                                       }

                                   }
                                   @Override
                                   public void onCancelled(DatabaseError error) {
                                       progressDialog.dismiss();
                                       Toast.makeText(Cap_Category_Show.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               });

                               String get_key=sharedPreferences_add_quantity.getString("item_myKey","Data Not Found");
                               int pQunatity=sharedPreferences_add_quantity.getInt("new_totalQuantity" ,0);
                               hashMap.put("cap_pdstock",pQunatity);

                               databaseRefe_category_setQuantity.child(get_key).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       databaseRef_show.child(item_position.getCapCategory_key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void aVoid) {
                                               if(cap_category_models_List.isEmpty()){
                                                   textView_totalPrice.setText("Amount:"+0);
                                                   total_price=0;
                                               }

                                               Toast.makeText(Cap_Category_Show.this, "Your product Remove SuccessFull..", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                   }
                               });

                           }catch (Exception exception){
                               Toast.makeText(Cap_Category_Show.this, "Error:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
                           }
*/
                       }
                   });
               }else{
                   progressDialog.dismiss();
         Toast.makeText(Cap_Category_Show.this, "No Order...", Toast.LENGTH_SHORT).show();
               }

           }
                if(cap_category_models_List.isEmpty()){
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
    Toast.makeText(Cap_Category_Show.this, "Error :"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buy_category_Data(View view) {
        final StringBuffer stringBuffer=new StringBuffer();
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("my_recipe_key","Rciepe_categoryShow").apply();
        editor.commit();

    try {
        databaseRef_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    getItem=dataSnapshot1.getValue(Cap_Category_Model.class);
                    getItem.setCapCategory_key(dataSnapshot1.getKey());

                    stringBuffer.append("Id ="+getItem.getCapCategory_id()+",");
                    stringBuffer.append("Name="+getItem.getCapCategory_name()+",");
                    stringBuffer.append("Quantity="+getItem.getCapCategory_quantity()+",");
                    stringBuffer.append("Item_Price="+getItem.getCapCategory_price()+",");
                }if(total_price !=0){
                    Intent intent=new Intent(Cap_Category_Show.this,User_Address.class);
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
         Toast.makeText(Cap_Category_Show.this, "get Lodding Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }catch (Exception exception){
     Toast.makeText(this, "Exception :"+exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    }
}