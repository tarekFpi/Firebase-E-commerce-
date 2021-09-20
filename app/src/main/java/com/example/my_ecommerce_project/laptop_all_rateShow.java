package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class laptop_all_rateShow extends AppCompatActivity {
 private RecyclerView recyclerView;
 private laptop_Adapter adapter;
 private List<laptop_prodcut_Module>laptop_list=new ArrayList<>();
 private DatabaseReference databaseRefe_all_rateShow;
 private DatabaseReference databaseRefe_rating_laptop_remove;
private LayoutInflater layoutInflater;
private AlertDialog alertDialog;
private View view;
private EditText editText_id,editText_name,editText_price,editText_stock,editText_discount;
private Button button_update,button_exit;
private TextView textView_updId;
private EditText editText_SearchId;
private ProgressDialog progressDialog;
private FirebaseStorage firebaseStorage;

    private SharedPreferences sharedPrefere_status;
    private String status="Admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_all_rate_show);
        databaseRefe_all_rateShow= FirebaseDatabase.getInstance().getReference("Laptop_product_Add");
        databaseRefe_rating_laptop_remove= FirebaseDatabase.getInstance().getReference("Laptop_Rationg_Add");
       firebaseStorage=FirebaseStorage.getInstance();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_laptop_rat_show);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       editText_SearchId=(EditText)findViewById(R.id.laptop_serchId);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wite...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sharedPrefere_status=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        status= sharedPrefere_status.getString("my_status_key","Data Not Found");
        // Read from the database
        databaseRefe_all_rateShow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                laptop_list.clear();
            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
         laptop_prodcut_Module itemData=dataSnapshot1.getValue(laptop_prodcut_Module.class);
         itemData.setLap_data_Key(dataSnapshot1.getKey());
         laptop_list.add(itemData);
            }

            adapter= new laptop_Adapter(getApplicationContext(),laptop_list);
            recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

                if(status.contains("User")){
                    adapter.mystatus=status;
                    adapter.setUser_On_ClickListener(new laptop_Adapter.onItemClickListener_item_User() {
                        @Override
                        public void onItemclick(int position) {
                            laptop_prodcut_Module item_Position=laptop_list.get(position);
                            Intent intent=new Intent(laptop_all_rateShow.this,user_laptop_Category.class);
                            intent.putExtra("pd_cord",item_Position.getLap_cord());
                            intent.putExtra("pd_name",item_Position.getBrand_name());
                            intent.putExtra("pd_Desr",item_Position.getLap_Description());
                          ///  intent.putExtra("pd_stock",item_Position.getLap_stock());
                            intent.putExtra("pd_price",item_Position.getLap_price());
                            intent.putExtra("pd_image",item_Position.getLap_image());
                            intent.putExtra("pd_discount",item_Position.getLap_Discount());
                            intent.putExtra("status_data","1");
                            startActivity(intent);

                        }
                    });
                }else{
                    adapter.mystatus="Admin";
                    adapter.setOnItemClickLisiner(new laptop_Adapter.onItemClickLisiner() {
                        @Override
                        public void OnClick(int position) {

                            laptop_prodcut_Module item_Position=laptop_list.get(position);
                            String date=item_Position.getLap_AddDate();
                            Toast.makeText(laptop_all_rateShow.this, "Date:"+date, Toast.LENGTH_LONG).show();

                            Intent intent=new Intent(laptop_all_rateShow.this,user_laptop_Category.class);
                            intent.putExtra("pd_cord",item_Position.getLap_cord());
                            intent.putExtra("pd_name",item_Position.getBrand_name());
                            intent.putExtra("pd_Desr",item_Position.getLap_Description());
                           // intent.putExtra("pd_stock",item_Position.getLap_stock());
                            intent.putExtra("pd_price",item_Position.getLap_price());
                            intent.putExtra("pd_image",item_Position.getLap_image());
                            intent.putExtra("pd_discount",item_Position.getLap_Discount());
                            intent.putExtra("status_data","1");
                            startActivity(intent);
                        }

                        @Override
                        public void OnUpdate(int position) {
                            final laptop_prodcut_Module item_data=laptop_list.get(position);

                            alertDialog=new AlertDialog.Builder(laptop_all_rateShow.this).create();
                            alertDialog.setCancelable(false);
                            layoutInflater=getLayoutInflater();
                            view= layoutInflater.inflate(R.layout.laptop_update_item,null);
                            alertDialog.setView(view);


                            textView_updId=view.findViewById(R.id.lap_UPdatepd_Id);
                            editText_name=view.findViewById(R.id.lp_Update_Name_Id);
                            editText_price=view.findViewById(R.id.lp_pdate_Price);
                            editText_discount=view.findViewById(R.id.lp_update_discount);
                           // editText_stock=view.findViewById(R.id.lp_update_quan);

                            button_update=(Button) view.findViewById(R.id.lap_update_Data);
                            button_exit=(Button) view.findViewById(R.id.lap_exit_btn);


                            textView_updId.setText("Id:"+item_data.getLap_cord());
                            editText_name.setText(item_data.getBrand_name());
                            editText_price.setText(""+item_data.getLap_price());
                            editText_discount.setText(""+item_data.getLap_Discount());
                            ///editText_stock.setText(""+item_data.getLap_stock());


                            button_exit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });

                            button_update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(editText_name.getText().toString().isEmpty()){
                                        editText_name.setError("Plase Product Name...");
                                        editText_name.requestFocus();
                                    }else if(editText_price.getText().toString().isEmpty()){
                                        editText_price.setError("Plase Your price ...");
                                        editText_price.requestFocus();

                                    }else if(editText_discount.getText().toString().isEmpty()){
                                        editText_discount.setError("Plase Your Discount...");
                                        editText_discount.requestFocus();
                                    }else{
                                        final HashMap<String, Object> hashMapUpd = new HashMap<>();
                                        // Read from the database
                                        databaseRefe_all_rateShow.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                                    laptop_prodcut_Module Item_update =dataSnapshot1.getValue(laptop_prodcut_Module.class);
                                                    Item_update.setLap_data_Key(dataSnapshot1.getKey());

                                                    if(Item_update.getLap_cord().contains(item_data.getLap_cord())){

                                                        //int stock=Integer.parseInt(editText_stock.getText().toString());
                                                        int pri=Integer.parseInt(editText_price.getText().toString());
                                                        int discount=Integer.parseInt(editText_discount.getText().toString());


                                                        hashMapUpd.put("lap_price",pri);
                                                        hashMapUpd.put("lap_Discount",discount);
                                                        hashMapUpd.put("brand_name",editText_name.getText().toString());

                                                        databaseRefe_all_rateShow.child(Item_update.getLap_data_Key()).updateChildren(hashMapUpd).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                     Toast.makeText(laptop_all_rateShow.this, "Your Data Update SuccessFull..", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                progressDialog.dismiss();
                                                Toast.makeText(laptop_all_rateShow.this, "Update Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });
                            alertDialog.show();

                        }
                        @Override
                        public void OnDelete(int position) {
                            final laptop_prodcut_Module item_position=laptop_list.get(position);

                            // Read from the database
                            databaseRefe_rating_laptop_remove.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        ration_DataModel ration_Item = dataSnapshot1.getValue(ration_DataModel.class);
                                        ration_Item.setRatig_dataKey(dataSnapshot1.getKey());

                                        if(ration_Item.getPd_image().contains(item_position.getLap_image())){
                                          databaseRefe_rating_laptop_remove.child(ration_Item.getRatig_dataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                              @Override
                                              public void onSuccess(Void aVoid) {

                                                  StorageReference storageRef_image=firebaseStorage.getReferenceFromUrl(item_position.getLap_image());
                                                  storageRef_image.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                      @Override
                                                      public void onSuccess(Void aVoid) {
                                                          databaseRefe_all_rateShow.child(item_position.getLap_data_Key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                              @Override
                                                              public void onSuccess(Void aVoid) {
                                                    Toast.makeText(laptop_all_rateShow.this, "Your Data Delete SuccessFulll", Toast.LENGTH_SHORT).show();
                                                              }
                                                          }).addOnFailureListener(new OnFailureListener() {
                                                              @Override
                                                              public void onFailure(@NonNull Exception e) {
                                                                  Toast.makeText(laptop_all_rateShow.this, "Firebase product Image Failde..", Toast.LENGTH_SHORT).show();
                                                              }
                                                          });
                                                      }
                                                  }).addOnFailureListener(new OnFailureListener() {
                                                      @Override
                                                      public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(laptop_all_rateShow.this, "Your Storage image Delete Failde..", Toast.LENGTH_SHORT).show();
                                                      }
                                                  });


                                              }
                                          }).addOnFailureListener(new OnFailureListener() {
                                              @Override
                                              public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(laptop_all_rateShow.this, "Rating image Delete  Failde.."+e.getMessage(), Toast.LENGTH_SHORT).show();
                                              }
                                          });
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Rating Image  Not Match", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Toast.makeText(laptop_all_rateShow.this, "Rating Lodding Data Failde..."+error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });
                }



               /*if(status.contains("Admin")){
                    adapter.mystatus=status;
                    adapter.setOnItemClickLisiner(new laptop_Adapter.onItemClickLisiner() {
                        @Override
                        public void OnClick(int position) {

                            laptop_prodcut_Module item_Position=laptop_list.get(position);

                            Intent intent=new Intent(laptop_all_rateShow.this,user_laptop_Category.class);
                            intent.putExtra("pd_cord",item_Position.getLap_cord());
                            intent.putExtra("pd_name",item_Position.getBrand_name());
                            intent.putExtra("pd_Desr",item_Position.getLap_Description());
                            intent.putExtra("pd_stock",item_Position.getLap_stock());
                            intent.putExtra("pd_price",item_Position.getLap_price());
                            intent.putExtra("pd_image",item_Position.getLap_image());
                            intent.putExtra("pd_discount",item_Position.getLap_Discount());
                            intent.putExtra("status_data","1");
                            startActivity(intent);
                        }

                        @Override
                        public void OnUpdate(int position) {
                            final laptop_prodcut_Module item_data=laptop_list.get(position);

                            alertDialog=new AlertDialog.Builder(laptop_all_rateShow.this).create();
                            alertDialog.setCancelable(false);
                            layoutInflater=getLayoutInflater();
                            view= layoutInflater.inflate(R.layout.laptop_update_item,null);
                            alertDialog.setView(view);


                            textView_updId=view.findViewById(R.id.lap_UPdatepd_Id);
                            editText_name=view.findViewById(R.id.lp_Update_Name_Id);
                            editText_price=view.findViewById(R.id.lp_pdate_Price);
                            editText_discount=view.findViewById(R.id.lp_update_discount);
                            editText_stock=view.findViewById(R.id.lp_update_quan);

                            button_update=(Button) view.findViewById(R.id.lap_update_Data);
                            button_exit=(Button) view.findViewById(R.id.lap_exit_btn);


                            textView_updId.setText("Id:"+item_data.getLap_cord());
                            editText_name.setText(item_data.getBrand_name());
                            editText_price.setText(""+item_data.getLap_price());
                            editText_discount.setText(""+item_data.getLap_Discount());
                            editText_stock.setText(""+item_data.getLap_stock());


                            button_exit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });

                            button_update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(editText_name.getText().toString().isEmpty()){
                                        editText_name.setError("Plase Product Name...");
                                        editText_name.requestFocus();
                                    }else if(editText_price.getText().toString().isEmpty()){
                                        editText_price.setError("Plase Your price ...");
                                        editText_price.requestFocus();

                                    }else if(editText_stock.getText().toString().isEmpty()){
                                        editText_stock.setError("Plase Your Stock...");
                                        editText_stock.requestFocus();

                                    }else if(editText_discount.getText().toString().isEmpty()){
                                        editText_discount.setError("Plase Your Discount...");
                                        editText_discount.requestFocus();
                                    }else{
                                        final HashMap<String, Object> hashMapUpd = new HashMap<>();
                                        // Read from the database
                                        databaseRefe_all_rateShow.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                                    laptop_prodcut_Module Item_update =dataSnapshot1.getValue(laptop_prodcut_Module.class);
                                                    Item_update.setLap_data_Key(dataSnapshot1.getKey());

                                                    if(Item_update.getLap_cord().contains(item_data.getLap_cord())){

                                                        int stock=Integer.parseInt(editText_stock.getText().toString());
                                                        int pri=Integer.parseInt(editText_price.getText().toString());
                                                        int discount=Integer.parseInt(editText_discount.getText().toString());

                                                        hashMapUpd.put("lap_stock",stock);
                                                        hashMapUpd.put("lap_price",pri);
                                                        hashMapUpd.put("lap_Discount",discount);
                                                        hashMapUpd.put("brand_name",editText_name.getText().toString());

                                                        databaseRefe_all_rateShow.child(Item_update.getLap_data_Key()).updateChildren(hashMapUpd).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(laptop_all_rateShow.this, "Your Data Update SuccessFull..", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                progressDialog.dismiss();
                                                Toast.makeText(laptop_all_rateShow.this, "Update Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });
                            alertDialog.show();

                        }
                        @Override
                        public void OnDelete(int position) {
                            final laptop_prodcut_Module item_data=laptop_list.get(position);
                            databaseRefe_all_rateShow.child(item_data.getLap_data_Key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(laptop_all_rateShow.this, "Your Data Delete SuccessFulll", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(laptop_all_rateShow.this, "Delete Failde..", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }*/

            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
      Toast.makeText(laptop_all_rateShow.this, "Data Lodding Failde..", Toast.LENGTH_SHORT).show();
            }
        });

        editText_SearchId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
             fileList(editable.toString());
            }
        });
    }

    private void fileList(String textData) {
  try {
      List<laptop_prodcut_Module>filterList=new ArrayList<>();
      for(laptop_prodcut_Module item: laptop_list){
          if(item.getBrand_name().toLowerCase().contains(textData.toLowerCase())){
              filterList.add(item);
          }
      }
      adapter.filterdList(filterList);
  }catch (Exception exception){

  }

    }
}