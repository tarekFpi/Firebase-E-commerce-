package com.example.my_ecommerce_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Femal_product_rate_Show extends AppCompatActivity  {
 private RecyclerView recyclerView;
 private femal_pd_rate_Adapter adapter;
 private List<femal_product_Model>modelList=new ArrayList<>();
 private DatabaseReference databaseReference_getFirebase;
 private DatabaseReference databaseRefere_remove_femal_image_rating;
 private ProgressDialog progressDialog;
 String status="Admin";
 private EditText editText_search;
    String sb = null;
    private FirebaseStorage firebaseStorage;
   private AlertDialog alertDialog;
   private LayoutInflater layoutInflater;
   private View view;
    private   femal_product_Model Item_update;

    private EditText editText_nameUp,editText_price_up,editText_stock;
    Button button_save,button_exit;
    private TextView textView_pdId;
    private SharedPreferences sharedPrefere_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_femal_product_rate__show);

        databaseReference_getFirebase= FirebaseDatabase.getInstance().getReference("Femal_productAdd");
        databaseRefere_remove_femal_image_rating= FirebaseDatabase.getInstance().getReference("Femal_rating_ImageSave");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_femal_ratShow);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         editText_search=(EditText)findViewById(R.id.femal_order_search);
       firebaseStorage=FirebaseStorage.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wite...");
        progressDialog.show();
        sharedPrefere_status=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        status= sharedPrefere_status.getString("my_status_key","Data Not Found");

        // Read from the database
        databaseReference_getFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                modelList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    femal_product_Model Item = dataSnapshot1.getValue(femal_product_Model.class);
                    Item.setData_key(dataSnapshot1.getKey());
                    modelList.add(Item);
                }

             adapter = new femal_pd_rate_Adapter(getApplicationContext(), modelList);
             recyclerView.setAdapter(adapter);
             progressDialog.dismiss();

                if(status.contains("User")){  //User onItem Click
              //  adapter.setOnItemClickListener(null);
                     adapter.mystatus = status;
                     adapter.setUser_On_ClickListener(new femal_pd_rate_Adapter.onItemClickListener_item_User() {
                         @Override
                         public void onItemclick(int position) {
                       femal_product_Model item_Position=modelList.get(position);
                         Intent intent=new Intent(Femal_product_rate_Show.this,femal_Category.class);
                             intent.putExtra("pd_cord",item_Position.getPd_Cord());
                             intent.putExtra("pd_name",item_Position.getProduct_name());
                             intent.putExtra("pd_Desr",item_Position.getProduct_Desrcip());
                             //intent.putExtra("pd_stock",item_Position.getProduct_quantity());
                             intent.putExtra("pd_price",item_Position.getProduct_price());
                             intent.putExtra("pd_image",item_Position.getProduct_Image());
                             intent.putExtra("pd_size",item_Position.getPd_size());
                             startActivity(intent);
                   // Toast.makeText(Femal_product_rate_Show.this, "User"+item_Position.getPd_size(), Toast.LENGTH_SHORT).show();
                         }
                     });
                } else {

                    // Admin onClick
                   adapter.mystatus ="Admin";
                    adapter.setOnItemClickListener(new femal_pd_rate_Adapter.onItemClickListener() {

                        @Override
                        public void onItemclick(int position) {
                        femal_product_Model item_Position=modelList.get(position);
                            Intent intent=new Intent(Femal_product_rate_Show.this,femal_Category.class);
                            intent.putExtra("pd_cord",item_Position.getPd_Cord());
                            intent.putExtra("pd_name",item_Position.getProduct_name());
                            intent.putExtra("pd_Desr",item_Position.getProduct_Desrcip());
                            //intent.putExtra("pd_stock",item_Position.getProduct_quantity());
                            intent.putExtra("pd_price",item_Position.getProduct_price());
                            intent.putExtra("pd_image",item_Position.getProduct_Image());
                            intent.putExtra("pd_size",item_Position.getPd_size());
                            startActivity(intent);
                        }

                        @Override
                        public void onDelete(int position) {
                      final femal_product_Model item_Position=modelList.get(position);
                           // databaseRefere_remove_femal_image_rating
                            // Read from the database
                            databaseRefere_remove_femal_image_rating.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        ration_DataModel ration_Item = dataSnapshot1.getValue(ration_DataModel.class);
                                        ration_Item.setRatig_dataKey(dataSnapshot1.getKey());

                                        if(ration_Item.getPd_image().contains(item_Position.getProduct_Image())){
                                            databaseRefere_remove_femal_image_rating.child(ration_Item.getRatig_dataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    StorageReference storageRef_image=firebaseStorage.getReferenceFromUrl(item_Position.getProduct_Image());
                                                    storageRef_image.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                    public void onSuccess(Void aVoid) {

                                                    String remove_key=item_Position.getData_key();
                                                   databaseReference_getFirebase.child(remove_key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                   Toast.makeText(Femal_product_rate_Show.this, "Your Product Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    });

                                                }
                                            });
                                        }else{
                                     Toast.makeText(Femal_product_rate_Show.this, "Your Rating Image Not Match..", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                            Toast.makeText(Femal_product_rate_Show.this, "Your Rating Image Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                             }

          @Override
           public void onUpdate(int position) {
             //ladis_pd_Id
              alertDialog=new AlertDialog.Builder(Femal_product_rate_Show.this).create();
              alertDialog.setCancelable(false);
              alertDialog.setTitle("Update");
              alertDialog.setIcon(R.drawable.recycle_icon);
              layoutInflater=getLayoutInflater();

              view= layoutInflater.inflate(R.layout.ladis_product_update,null);
              alertDialog.setView(view);
              editText_nameUp=(EditText)view.findViewById(R.id.edit_Update_Name_Id);
              editText_price_up=(EditText)view.findViewById(R.id.edit_pdate_Price);
             // editText_stock=(EditText)view.findViewById(R.id.edit_update_quan);

              button_save=(Button)view.findViewById(R.id.ladis_update_Data);
              button_exit=(Button)view.findViewById(R.id.exit_btn);
              textView_pdId=(TextView) view.findViewById(R.id.ladis_pd_Id);

              final femal_product_Model item_getData=modelList.get(position);
              String up_name=item_getData.getProduct_name();
              //final int up_stock=item_getData.getProduct_quantity();
              final int up_price=item_getData.getProduct_price();

              textView_pdId.setText("Product Cord:"+item_getData.getPd_Cord());
              editText_nameUp.setText(up_name);
             // editText_stock.setText(""+up_stock);
              editText_price_up.setText(""+up_price);

              button_save.setOnClickListener(new OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if(editText_nameUp.getText().toString().isEmpty()){
                          editText_nameUp.setError("Plase Product Name...");
                          editText_nameUp.requestFocus();
                      }else if(editText_price_up.getText().toString().isEmpty()){
                          editText_price_up.setError("Plase Your price ...");
                          editText_price_up.requestFocus();

                      }else {

                          databaseReference_getFirebase.addValueEventListener(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot dataSnapshot) {

                                  HashMap<String, Object> hashMap = new HashMap<>();
                                  for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                      Item_update = dataSnapshot1.getValue(femal_product_Model.class);
                                      Item_update.setData_key(dataSnapshot1.getKey());

                                      if(Item_update.getPd_Cord().contains(item_getData.getPd_Cord())){
                                      /*int up_stock=Item_update.getProduct_quantity();
                                      int up_price=Item_update.getProduct_price();*/

                                         // int stock=Integer.parseInt(editText_stock.getText().toString());
                                          int pri=Integer.parseInt(editText_price_up.getText().toString());

                                          hashMap.put("product_name", editText_nameUp.getText().toString());
                                          hashMap.put("product_price", pri);
                                         // hashMap.put("product_quantity",stock);
                                          databaseReference_getFirebase.child(Item_update.getData_key()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                              @Override
                                              public void onSuccess(Void aVoid) {
                                                  Toast.makeText(Femal_product_rate_Show.this, "Your product Update SuccessFull..", Toast.LENGTH_SHORT).show();
                                                  editText_nameUp.setText("");
                                                  editText_price_up.setText("");
                                                  textView_pdId.setText("");
                                                  adapter.notifyDataSetChanged();
                                                  alertDialog.dismiss();
                                              }
                                          });
                                      }
                                  }
                              }

                              @Override
                              public void onCancelled(DatabaseError error) {
                   Toast.makeText(Femal_product_rate_Show.this, "Update Lodding Failde" + error.getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          });
                      }
                  }
              });

              button_exit.setOnClickListener(new OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      alertDialog.dismiss();
                  }
              });
              alertDialog.show();
           }
         });
        }

            }
            @Override
            public void onCancelled(DatabaseError error) {
  Toast.makeText(getApplicationContext(), "Lodding Data Failde...", Toast.LENGTH_SHORT).show();
           progressDialog.dismiss();
            }
        });


        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
           fileList(s.toString());
            }
        });
    }

    private void fileList(String textData) {
        try {
            List<femal_product_Model>filterList=new ArrayList<>();
            for(femal_product_Model item: modelList){
                if(item.getProduct_name().toLowerCase().contains(textData.toLowerCase())){
                    filterList.add(item);
                }
            }
            adapter.filterdList(filterList);
        }catch (Exception exception){
            Toast.makeText(this, "Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


  /* @Override
    protected void onStart() {
        Bundle bundle=getIntent().getExtras();
        final String allData=  bundle.getString("order_all_key");

        String m[]=allData.split("Name:");

        for (int i = 0,j=0; i <m.length ; i++) {j++;
       sb=(j+""+m[i]+".\n");
        }
        // Read from the database
        databaseReference_getFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                femal_product_Model Item=null;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                     Item = dataSnapshot1.getValue(femal_product_Model.class);
                    Item.setData_key(dataSnapshot1.getKey());

                }

                if(sb.contains(Item.getProduct_name())) {
                    modelList.add(Item);
                    //recyclerView.setAdapter(adapter);
                    editText_search.setText(sb);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
       Toast.makeText(Femal_product_rate_Show.this, "Loding Failde..", Toast.LENGTH_SHORT).show();
            }
        });
        super.onStart();
    }*/
}