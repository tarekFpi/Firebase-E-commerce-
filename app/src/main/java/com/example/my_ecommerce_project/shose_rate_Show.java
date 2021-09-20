package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
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

public class shose_rate_Show extends AppCompatActivity {
private RecyclerView recyclerView;
private Shose_Adapter adapter;
private List<shose_pdModel>shose_pdModel_List=new ArrayList<>();
private DatabaseReference databaseRef_show;
private DatabaseReference databaseRef_shose_rating_ramove;
private FirebaseStorage firebaseStorage;
 private AlertDialog alertDialog;
 private Button button_update,button_exit;
    private EditText editText_search;
    private ProgressDialog progressDialog;

 private EditText editText_name,editText_stock,editText_price,editText_discount;
    private LayoutInflater layoutInflater;
    private View view;
    private TextView textView_pdId;
    private shose_pdModel Item_update;
    private SharedPreferences sharedPrefere_status;
    private String status="Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shose_rate__show);

        databaseRef_show= FirebaseDatabase.getInstance().getReference("Shose_product_Add");
        databaseRef_shose_rating_ramove= FirebaseDatabase.getInstance().getReference("Shose_Rating_Add");

        firebaseStorage=FirebaseStorage.getInstance();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_shose_show_id);
        editText_search=(EditText)findViewById(R.id.shose_order_search);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
         progressDialog =new ProgressDialog(this);
         progressDialog.setCancelable(false);
         progressDialog.setMessage("Please Wite...");
         progressDialog.show();


        sharedPrefere_status=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        status= sharedPrefere_status.getString("my_status_key","Data Not Found");
        // Read from the database
        databaseRef_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shose_pdModel_List.clear();
             for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                 shose_pdModel addItem=dataSnapshot1.getValue(shose_pdModel.class);
                 addItem.setShose_product_key(dataSnapshot1.getKey());

                 shose_pdModel_List.add(addItem);
             }
             adapter=new Shose_Adapter(getApplicationContext(),shose_pdModel_List);
             recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

                if(status.contains("User")){
                    adapter.mystatus="User";
                    adapter.setUser_On_ClickListener(new Shose_Adapter.onItemClickListener_item_User() {
                        @Override
                        public void onItemclick(int position) {
                            shose_pdModel item_position=shose_pdModel_List.get(position);
                            Intent intent=new Intent(shose_rate_Show.this,shose_Category.class);

                            intent.putExtra("pd_cord",item_position.getShose_pd_id());
                            intent.putExtra("pd_name",item_position.getShose_pd_name());
                            intent.putExtra("pd_Desr",item_position.getShose_Decpt());
                            intent.putExtra("pd_price",item_position.getShose_price());
                            intent.putExtra("pd_image",item_position.getShose_image());
                            intent.putExtra("pd_size",item_position.getShose_size());
                            intent.putExtra("pd_discount",item_position.getShose_dicount());
                            startActivity(intent);
                        }
                    });
                } else{

                    adapter.mystatus="Admin";
                    adapter.setOnItemClickLisiner(new Shose_Adapter.onItem_ClickLisiner() {
                        @Override
                        public void onClick(int position) {
                     shose_pdModel item_position=shose_pdModel_List.get(position);
                          String date=item_position.getCurrent_date();
                     Toast.makeText(shose_rate_Show.this, "Add Date:"+date, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(shose_rate_Show.this,shose_Category.class);
                            intent.putExtra("pd_cord",item_position.getShose_pd_id());
                            intent.putExtra("pd_name",item_position.getShose_pd_name());
                            intent.putExtra("pd_Desr",item_position.getShose_Decpt());
                            intent.putExtra("pd_price",item_position.getShose_price());
                            intent.putExtra("pd_image",item_position.getShose_image());
                            intent.putExtra("pd_size",item_position.getShose_size());
                            intent.putExtra("pd_discount",item_position.getShose_dicount());
                            startActivity(intent);
                        }

                        @Override
                        public void Update(int position) {

                            alertDialog=new AlertDialog.Builder(shose_rate_Show.this).create();
                            alertDialog.setCancelable(false);
                            alertDialog.setTitle("Update");
                            alertDialog.setIcon(R.drawable.recycle_icon);
                            layoutInflater=getLayoutInflater();

                            view= layoutInflater.inflate(R.layout.shose_uproduct_update,null);
                            alertDialog.setView(view);

                            editText_name=(EditText)view.findViewById(R.id.edit_shose_update_Name);
                            editText_price=(EditText)view.findViewById(R.id.edit_shose_update_Price);

                            editText_discount=(EditText)view.findViewById(R.id.edit_shose_update_discout);

                            button_update=(Button)view.findViewById(R.id.shose_update_Data);
                            button_exit=(Button)view.findViewById(R.id.exit_btn);
                            textView_pdId=(TextView) view.findViewById(R.id.shose_upid);

                            final shose_pdModel item_getData=shose_pdModel_List.get(position);
                            String up_name=item_getData.getShose_pd_name();
                            final int up_price=item_getData.getShose_price();
                            final int up_discount=item_getData.getShose_dicount();

                            textView_pdId.setText("Product Cord:"+item_getData.getShose_pd_id());
                            editText_name.setText(up_name);

                            editText_price.setText(""+up_price);
                            editText_discount.setText(""+up_discount);

                            button_update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(editText_name.getText().toString().isEmpty()){
                                        editText_name.setError("Please Product Name...");
                                        editText_name.requestFocus();
                                    }else if(editText_price.getText().toString().isEmpty()){
                                        editText_price.setError("Please Your price ...");
                                        editText_price.requestFocus();

                                    } else if(editText_discount.getText().toString().isEmpty()){
                                        editText_discount.setError("Please Your Discount...");
                                        editText_discount.requestFocus();

                                    }else{

                                        databaseRef_show.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                    Item_update = dataSnapshot1.getValue(shose_pdModel.class);
                                                    Item_update.setShose_product_key(dataSnapshot1.getKey());

                                                    if(Item_update.getShose_pd_id().contains(item_getData.getShose_pd_id())){
                                      /*int up_stock=Item_update.getProduct_quantity();
                                      int up_price=Item_update.getProduct_price();*/


                                                        int pri=Integer.parseInt(editText_price.getText().toString());
                                                        int disc=Integer.parseInt(editText_discount.getText().toString());

                                                        hashMap.put("shose_pd_name", editText_name.getText().toString());
                                                        hashMap.put("shose_price", pri);

                                                        hashMap.put("shose_dicount",disc);
                                                        databaseRef_show.child(Item_update.getShose_product_key()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(shose_rate_Show.this, "Your product Update SuccessFull..", Toast.LENGTH_SHORT).show();
                                                                editText_name.setText("");
                                                                editText_price.setText("");

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
                                                progressDialog.dismiss();
                                                Toast.makeText(shose_rate_Show.this, "Update Lodding Failde" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });

                            button_exit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        }

                        @Override
                        public void on_Delete(int position) {
                            final shose_pdModel item_position=shose_pdModel_List.get(position);

                            // Read from the database
                            databaseRef_shose_rating_ramove.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        ration_DataModel ration_Item = dataSnapshot1.getValue(ration_DataModel.class);
                                        ration_Item.setRatig_dataKey(dataSnapshot1.getKey());

                                        if(ration_Item.getPd_image().contains(item_position.getShose_image())){
                                          databaseRef_shose_rating_ramove.child(ration_Item.getRatig_dataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                              @Override
                                              public void onSuccess(Void aVoid) {
                                                  StorageReference storageRefer_image=firebaseStorage.getReferenceFromUrl(item_position.getShose_image());
                                                  storageRefer_image.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                      @Override
                                                      public void onSuccess(Void aVoid) {

                                                          databaseRef_show.child(item_position.getShose_product_key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                              @Override
                                                              public void onSuccess(Void aVoid) {
                                                Toast.makeText(shose_rate_Show.this, "Your Data Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                                                              }
                                                          }).addOnFailureListener(new OnFailureListener() {
                                                              @Override
                                                              public void onFailure(@NonNull Exception e) {
                                                         Toast.makeText(shose_rate_Show.this, "Your Storage image Delete Failde.."+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                              }
                                                          });
                                                      }
                                                  });
                                              }
                                          }).addOnFailureListener(new OnFailureListener() {
                                              @Override
                                              public void onFailure(@NonNull Exception e) {
                          Toast.makeText(shose_rate_Show.this, "Your Rating image Delete Failde.."+e.getMessage(), Toast.LENGTH_SHORT).show();

                                              }
                                          });
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Rating Image  Not Match", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });


                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
            Toast.makeText(shose_rate_Show.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
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
            List<shose_pdModel>filterList=new ArrayList<>();
            for(shose_pdModel item: shose_pdModel_List){
                if(item.getShose_pd_name().toLowerCase().contains(textData.toLowerCase())){
                    filterList.add(item);
                }
            }
            adapter.filterdList(filterList);
        }catch (Exception exception){
            Toast.makeText(this, "Search Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}