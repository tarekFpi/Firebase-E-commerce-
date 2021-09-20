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

public class Laptop_categoryShow extends AppCompatActivity {
private RecyclerView recyclerView_categoryShow;
private laptop_categorey_Adapter adapter;
private List<laptop_CategoryModel>categoryModelList=new ArrayList<>();

private DatabaseReference databaseRefere_laptop_categoryShow;
private DatabaseReference databaseRefere_setQuantity;
private  int  pdPrice,total_price,discount;
  private   laptop_CategoryModel getItem;
private TextView textView_total;
private   int st_quan,currnet_quant,totStock;
private    laptop_prodcut_Module stock_data=null;
private  SharedPreferences sharedPreferences_tShirt,sharedPreferences_userGmall,sharedPrefer_stock_quantity;
private String user_gmail;
private ProgressDialog progressDialog;
private SharedPreferences sharedPreferences_add_quantity;  //stock quanitiy;

    int current_quanit, quant_Firebase;
    int new_Quant ;
    String item_key=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_category_show);

  databaseRefere_laptop_categoryShow= FirebaseDatabase.getInstance().getReference("laptop_product_category");
        databaseRefere_setQuantity= FirebaseDatabase.getInstance().getReference("Laptop_product_Add");

     recyclerView_categoryShow=(RecyclerView)findViewById(R.id.laptop_categoryShow_recyler);
     textView_total=(TextView)findViewById(R.id.cate_Lap_total_score);

     recyclerView_categoryShow.setLayoutManager(new LinearLayoutManager(this));
     recyclerView_categoryShow.setHasFixedSize(true);
        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite...");
        progressDialog.show();


        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");
        // Read from the database
        databaseRefere_laptop_categoryShow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                categoryModelList.clear();

             for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
             getItem=dataSnapshot1.getValue(laptop_CategoryModel.class);
           getItem.setLaptop_DataKey(dataSnapshot1.getKey());


                 if(user_gmail.contains(getItem.getUser_gmail())){
                     discount=getItem.getLapt_discount();
                     total_price=getItem.getLapt_price()*getItem.getLapt_quatity()+total_price;
                     total_price=total_price-discount;
                     categoryModelList.add(getItem);
                     textView_total.setText("Total Price:"+total_price);

                     adapter=new laptop_categorey_Adapter(getApplicationContext(),categoryModelList);
                    // adapter.notifyDataSetChanged();
                     recyclerView_categoryShow.setAdapter(adapter);

                     progressDialog.dismiss();


                     adapter.setOnItemClickLisiner(new laptop_categorey_Adapter.onItem_ClickLisiner() {
                         @Override
                         public void onClick(int position) {
                            // final String product_id = categoryModelList.get(position).getLaptop_Id();
                             //Toast.makeText(Laptop_categoryShow.this, "id:", Toast.LENGTH_SHORT).show();
                         }

                         @Override
                         public void onDelet(final int position) {

                             //sharedPreferences_add_quantity = getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
                           ///  Map<String, Object> hashMapUpdate = new HashMap<String, Object>();

             final laptop_CategoryModel Item_position = categoryModelList.get(position);
       databaseRefere_laptop_categoryShow.child(Item_position.getLaptop_DataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {

  Toast.makeText(Laptop_categoryShow.this, "Your Category Delete SuccessFull...", Toast.LENGTH_SHORT).show();
       }
      });

                            /* databaseRefere_setQuantity.addValueEventListener(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                     for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                         stock_data = dataSnapshot1.getValue(laptop_prodcut_Module.class);
                                         stock_data.setLap_data_Key(dataSnapshot1.getKey());

                                         if (stock_data.getLap_cord().contains(Item_position.getLaptop_Id())) {
                                             quant_Firebase = stock_data.getLap_stock();
                                             currnet_quant = Item_position.getLapt_quatity();
                                             new_Quant = quant_Firebase + current_quanit;

                                             item_key = stock_data.getLap_data_Key();
                                             sharedPreferences_add_quantity.edit().putInt("new_totalQuantity", new_Quant).commit();
                                             sharedPreferences_add_quantity.edit().putString("item_myKey", item_key).commit();
                                             sharedPreferences_add_quantity.edit().commit();
                                         }
                                     }

                                 }

                                 @Override
                                 public void onCancelled(DatabaseError error) {
                                     progressDialog.dismiss();
                                     Toast.makeText(Laptop_categoryShow.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             });

                             final String get_key = sharedPreferences_add_quantity.getString("item_myKey", "Data Not Found");
                             int pQunatity = sharedPreferences_add_quantity.getInt("new_totalQuantity", 0);
                             hashMapUpdate.put("lap_stock", pQunatity);
                             //  Toast.makeText(Laptop_categoryShow.this, "data:"+currnet_quant, Toast.LENGTH_SHORT).show();

                             databaseRefere_setQuantity.child(get_key).updateChildren(hashMapUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                     databaseRefere_laptop_categoryShow.child(Item_position.getLaptop_DataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void aVoid) {
                                             Toast.makeText(Laptop_categoryShow.this, "Your Order Revome SuccessFull..", Toast.LENGTH_SHORT).show();
                                             if (categoryModelList.isEmpty()) {
                                                 textView_total.setText("Amount:" + 0);
                                                 total_price = 0;
                                             }
                                         }
                                     });
                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     textView_total.setText("");
                                     Toast.makeText(Laptop_categoryShow.this, "Your Order Revome Unsuccessfull.." + e.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             });
*/
                         }
                     });

                 }else {
                   //  categoryModelList.clear();
                     progressDialog.dismiss();
                     Toast.makeText(Laptop_categoryShow.this, "Your Order Not Found..", Toast.LENGTH_SHORT).show();
                 }
             }

                if(categoryModelList.isEmpty()){
                    progressDialog.dismiss();
                    textView_total.setText("Total Price:"+0);
                }



             }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
         Toast.makeText(Laptop_categoryShow.this, "Your Category Lodding Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buy_category_Lapot_Data(View view) {
        final StringBuffer stringBuffer=new StringBuffer();
        sharedPreferences_tShirt=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project",Context.MODE_PRIVATE);
      SharedPreferences.Editor editor=sharedPreferences_tShirt.edit();
      editor.putString("myKey","laptop_activity").apply();
      editor.commit();

   sharedPrefer_stock_quantity=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);

      try {

          // Read from the database
          databaseRefere_laptop_categoryShow.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {

                  for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                      getItem=dataSnapshot1.getValue(laptop_CategoryModel.class);
                      getItem.setLaptop_DataKey(dataSnapshot1.getKey());

                      stringBuffer.append("Id ="+getItem.getLaptop_Id()+",");
                      stringBuffer.append("Name="+getItem.getLapt_name()+",");
                      stringBuffer.append("Quantity="+getItem.getLapt_quatity()+",");
                      stringBuffer.append("Item_Price="+getItem.getLapt_price()+",");
                      stringBuffer.append("Discount="+getItem.getLapt_discount()+"/");
                      sharedPrefer_stock_quantity.edit().putString("pd_id",getItem.getLaptop_Id()).commit();
                      sharedPrefer_stock_quantity.edit().putInt("pd_quanitiy",getItem.getLapt_quatity()).commit();

                  }if(!categoryModelList.isEmpty()){
                      Intent intent=new Intent(Laptop_categoryShow.this,User_Address.class);
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
            Toast.makeText(Laptop_categoryShow.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
              }
          });
      }catch (Exception exception){
          Toast.makeText(this, "Error:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
      }



    }

}