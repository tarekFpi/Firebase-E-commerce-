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

public class femal_category_show extends AppCompatActivity {
 private RecyclerView recyclerView;
 private femal_Category_Adapter adapter;
 private DatabaseReference databaseRefer_show;
 private List<femal_category_Model>modelList=new ArrayList<>();

 //private List<femal_product_Model>femal_product_Model_modelList=new ArrayList<>();
 private int pPrice,pQuan,total_price;
 private TextView textView_total_score;
  private DatabaseReference databaseRefe_category_setQuantity;

  //private DatabaseReference databaseRefer_order_card;
  private DatabaseReference databaseRefer_CapAll;
  private SharedPreferences sharedPreferences,sharedPreferences_userGmall;


  int current_quanit, quant_Firebase;
    int new_Quant ;
    String item_key=null;
   private String pd_cord ;
    private String pd_name ;
    private  int pd_quan ;
    private   String pd_size;
    private   int pd_price ;
    femal_category_Model item ;

private ProgressDialog progressDialog;
    femal_product_Model fire_get_Desript;
    private SharedPreferences sharedPreferences_add_quantity;
    private String user_gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_femal_category_show);

        databaseRefer_show= FirebaseDatabase.getInstance().getReference("femal_product_Category");
        databaseRefe_category_setQuantity=FirebaseDatabase.getInstance().getReference("Femal_productAdd");

       // databaseRefer_order_card=FirebaseDatabase.getInstance().getReference("Femal_Order_Card");

        databaseRefer_CapAll=FirebaseDatabase.getInstance().getReference("Cap_product_Add");

        recyclerView=(RecyclerView)findViewById(R.id.category_showRcycler);
        textView_total_score=(TextView)findViewById(R.id.cate_total_score);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite...");
        progressDialog.show();

        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");
        // Read from the database
        databaseRefer_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               modelList.clear();
             for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                femal_category_Model item=dataSnapshot1.getValue(femal_category_Model.class);
                item.setCategory_key(dataSnapshot1.getKey());


                 if(user_gmail.contains(item.getUser_gmail())){
           total_price=item.getProduct_price()*item.getProduct_quantity()+total_price;
                     modelList.add(item);
                     textView_total_score.setText("Total Price:"+total_price);
                     adapter=new femal_Category_Adapter(getApplicationContext(),modelList);
                     recyclerView.setAdapter(adapter);
                     progressDialog.dismiss();

                     adapter.setOnItemClickLisiner(new femal_Category_Adapter.onItem_ClickLisiner() {
                         @Override
                         public void onClick(int position) {
                             String name=modelList.get(position).getProduct_Name();
                             Toast.makeText(femal_category_show.this, "Name:"+name, Toast.LENGTH_SHORT).show();
                         }

                         @Override
                         public void onDelet(int position) {

                             final femal_category_Model item_position=modelList.get(position);
                             databaseRefer_show.child(item_position.getCategory_key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                              adapter.notifyDataSetChanged();
                         Toast.makeText(femal_category_show.this, "Your Category Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                                 }
                             });

                           //  Map<String, Object> hashMap = new HashMap<String, Object>();
                          /*   sharedPreferences_add_quantity=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
                             final femal_category_Model item_position=modelList.get(position);
                             // Read from the database
                             databaseRefe_category_setQuantity.addValueEventListener(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                     modelList.clear();
                                     for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                         femal_product_Model get_Item  = dataSnapshot1.getValue(femal_product_Model.class);
                                         get_Item.setData_key(dataSnapshot1.getKey());

                                         if(get_Item.getPd_Cord().contains(item_position.getProduct_Cord())) {
                                             quant_Firebase = get_Item.getProduct_quantity();
                                             current_quanit = item_position.getProduct_quantity();
                                             item_key=get_Item.getData_key();
                                             new_Quant =current_quanit + quant_Firebase;
                                             sharedPreferences_add_quantity.edit().putInt("new_totalQuantity",new_Quant).commit();
                                             sharedPreferences_add_quantity.edit().putString("item_myKey",item_key).commit();
                                             sharedPreferences_add_quantity.edit().commit();
                                         }else{
                                             Toast.makeText(getApplicationContext(), "Product Remove Failde",Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 }
                                 @Override
                                 public void onCancelled(DatabaseError error) {
                                     progressDialog.dismiss();
                                     Toast.makeText(femal_category_show.this, "Data Remove Failde..", Toast.LENGTH_SHORT).show();
                                 }
                             });

                             String get_key=sharedPreferences_add_quantity.getString("item_myKey","Data Not Found");
                             int pQunatity=sharedPreferences_add_quantity.getInt("new_totalQuantity" ,0);
                             hashMap.put("product_quantity",pQunatity);
                             Toast.makeText(femal_category_show.this, ""+pQunatity, Toast.LENGTH_SHORT).show();
                             databaseRefe_category_setQuantity.child(get_key).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                     String mkey=item_position.getCategory_key();
                                     databaseRefer_show.child(mkey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void aVoid) {
                                             if(modelList.isEmpty()){
                                                 textView_total_score.setText("Amount:"+0);
                                                 total_price=0;
                                             }

                                             Toast.makeText(femal_category_show.this, "Remove SuccessFull", Toast.LENGTH_SHORT).show();
                                         }
                                     });

                                 }
                             });*/
                         }
                     });
                 }else{
          Toast.makeText(femal_category_show.this, "No Order..", Toast.LENGTH_SHORT).show();
          progressDialog.dismiss();
                 }
                }

                if(modelList.isEmpty()){
                    progressDialog.dismiss();
                    textView_total_score.setText("Total Price:"+0);
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
        Toast.makeText(femal_category_show.this, "Your Data Lodding Failde...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buy_category_Data(View view) {
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("myKey_femalCategory","femal_category").apply();
        editor.commit();

     try {
         // Read from the database
         databaseRefer_show.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {


                 StringBuffer stringBf_cord_pdName=new StringBuffer();

                 for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                     item=dataSnapshot1.getValue(femal_category_Model.class);
                     item.setCategory_key(dataSnapshot1.getKey());

                     stringBf_cord_pdName.append("Cord:"+item.getProduct_Cord()+",");
                     stringBf_cord_pdName.append("Name:"+item.getProduct_Name()+",");
                     stringBf_cord_pdName.append("Quantity:"+item.getProduct_quantity()+",");
                     stringBf_cord_pdName.append("Size:"+item.getProduct_Size()+",");
                     stringBf_cord_pdName.append("Price:"+item.getProduct_price()+"/");
                 }if(total_price !=0){
                     Intent intent=new Intent(femal_category_show.this,User_Address.class);
                     intent.putExtra("data_all_key", (Serializable) stringBf_cord_pdName);
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
                 Toast.makeText(femal_category_show.this, "Lodding Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });
     }catch (Exception e){

     }




    }
}