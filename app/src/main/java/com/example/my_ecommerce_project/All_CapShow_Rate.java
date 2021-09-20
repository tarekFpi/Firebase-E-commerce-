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

public class All_CapShow_Rate extends AppCompatActivity {
private RecyclerView recyclerView;
private List<Cap_product_Model> modelList=new ArrayList<>();
private Cap_Adapte adapte;
private DatabaseReference databaseRefe_showCap;
private DatabaseReference databaseRefe_remove_Cap_rating;
private EditText editText_search;
private ProgressDialog progressDialog;
private FirebaseStorage firebaseStorage;
private LayoutInflater layoutInflater;
private View view;
private  AlertDialog alertDialog;
    Button button_UPdate,button_exit;
    private EditText editText_nameUp,editText_price_up,editText_stock;

    private TextView textView_pdId;
    private SharedPreferences sharedPrefere_status;
    private String status="Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__cap_show__rate);
        databaseRefe_showCap= FirebaseDatabase.getInstance().getReference("Cap_product_Add");
        databaseRefe_remove_Cap_rating= FirebaseDatabase.getInstance().getReference("Cap_rating_Add");
        firebaseStorage= FirebaseStorage.getInstance();

        recyclerView=(RecyclerView)findViewById(R.id.recycler_cap_rateShowAll);
        editText_search=(EditText)findViewById(R.id.cap_serchId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wite...");
         progressDialog.show();

        sharedPrefere_status=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        status= sharedPrefere_status.getString("my_status_key","Data Not Found");
        // Read from the database
        databaseRefe_showCap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                modelList.clear();
             for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
               Cap_product_Model cap_item=dataSnapshot1.getValue(Cap_product_Model.class);
                 cap_item.setCap_product_Key(dataSnapshot1.getKey());
                 modelList.add(cap_item);
             }

              adapte=new Cap_Adapte(getApplicationContext(),modelList);
               recyclerView.setAdapter(adapte);
                progressDialog.dismiss();

                if(status.contains("User")){
                    adapte.mystatus="User";
                    adapte.setUser_On_ClickListener(new Cap_Adapte.onItemClickListener_item_User() {
                        @Override
                        public void onItemclick(int position) {
                            Intent intent=new Intent(All_CapShow_Rate.this,Cap_Category.class);
                            Cap_product_Model item_Position=modelList.get(position);
                            intent.putExtra("pd_cord",item_Position.getCap_pdCord());
                            intent.putExtra("pd_name",item_Position.getCap_pdName());
                            intent.putExtra("pd_Desr",item_Position.getCap_pdDecr());
                           // intent.putExtra("pd_stock",item_Position.getCap_pdstock());
                            intent.putExtra("pd_price",item_Position.getCap_pdPrice());
                            intent.putExtra("pd_image",item_Position.getCap_pdImage());
                            intent.putExtra("pd_size",item_Position.getCap_pdSize());
                            startActivity(intent);
                        }
                    });
                } else {

                    adapte.mystatus="Admin";
                    adapte.setOnItemClick(new Cap_Adapte.onItemClickLisiner() {
                        @Override
                        public void onClick(int position) {
                            Cap_product_Model item_Position=modelList.get(position);
                            String date=modelList.get(position).getCap_pdDate();
                            Toast.makeText(All_CapShow_Rate.this, "Add Date :"+date, Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(All_CapShow_Rate.this,Cap_Category.class);
                            intent.putExtra("pd_cord",item_Position.getCap_pdCord());
                            intent.putExtra("pd_name",item_Position.getCap_pdName());
                            intent.putExtra("pd_Desr",item_Position.getCap_pdDecr());
                           // intent.putExtra("pd_stock",item_Position.getCap_pdstock());
                            intent.putExtra("pd_price",item_Position.getCap_pdPrice());
                            intent.putExtra("pd_image",item_Position.getCap_pdImage());
                            intent.putExtra("pd_size",item_Position.getCap_pdSize());
                            startActivity(intent);
                        }

                        @Override
                        public void OnUpdate(int position) {
                            alertDialog=new AlertDialog.Builder(All_CapShow_Rate.this).create();
                            alertDialog.setCancelable(false);
                            alertDialog.setTitle("Update");
                            alertDialog.setIcon(R.drawable.recycle_icon);
                            layoutInflater=getLayoutInflater();

                            view= layoutInflater.inflate(R.layout.all_cap_update_page,null);
                            alertDialog.setView(view);

                            editText_nameUp=(EditText)view.findViewById(R.id.edit_cap_Up_Name);
                            editText_price_up=(EditText)view.findViewById(R.id.edit_capUp_Price);

                            textView_pdId=(TextView)view.findViewById(R.id.text_Up_Id);

                            button_exit=(Button)view.findViewById(R.id.cap_exit_btn);
                            button_UPdate=(Button)view.findViewById(R.id.cap_update_Data);

                            final Cap_product_Model item_Position=modelList.get(position);

                            textView_pdId.setText(""+item_Position.getCap_pdCord());
                            editText_nameUp.setText(""+item_Position.getCap_pdName());
                            editText_price_up.setText(""+item_Position.getCap_pdPrice());
                            ///editText_stock.setText(""+item_Position.getCap_pdstock());

                            button_exit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });
                            button_UPdate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(editText_nameUp.getText().toString().isEmpty()){
                                        editText_nameUp.setError("Please Your product Name");
                                        editText_nameUp.requestFocus();

                                    }else if(editText_price_up.getText().toString().isEmpty()){
                                        editText_nameUp.setError("Please Your product price");
                                        editText_nameUp.requestFocus();

                                    }else {

                                        final HashMap<String, Object> hashMapUpdate = new HashMap<>();
                                        databaseRefe_showCap.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                                    Cap_product_Model cap_item=dataSnapshot1.getValue(Cap_product_Model.class);
                                                    cap_item.setCap_product_Key(dataSnapshot1.getKey());

                                                    if(cap_item.getCap_pdCord().contains(item_Position.getCap_pdCord())){

                                                        int stock=Integer.parseInt(editText_stock.getText().toString());
                                                        int pri=Integer.parseInt(editText_price_up.getText().toString());

                                                        hashMapUpdate.put("cap_pdName", editText_nameUp.getText().toString());
                                                        hashMapUpdate.put("cap_pdPrice", pri);
                                                        hashMapUpdate.put("cap_pdstock",stock);
                                                        databaseRefe_showCap.child(cap_item.getCap_product_Key()).updateChildren(hashMapUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(All_CapShow_Rate.this, "Your product Update SuccessFull..", Toast.LENGTH_SHORT).show();
                                                                editText_nameUp.setText("");
                                                                editText_price_up.setText("");
                                                                editText_stock.setText("");
                                                                textView_pdId.setText("");
                                                                adapte.notifyDataSetChanged();
                                                                // alertDialog.dismiss();
                                                            }
                                                        });
                                                    }

                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                Toast.makeText(All_CapShow_Rate.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });
                            alertDialog.show();
                        }

                        @Override
                        public void OnDelete(int position) {
                            final Cap_product_Model item_Position=modelList.get(position);

                            // Read from the database
                            databaseRefe_remove_Cap_rating.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        ration_DataModel ration_Item = dataSnapshot1.getValue(ration_DataModel.class);
                                        ration_Item.setRatig_dataKey(dataSnapshot1.getKey());

                                        if(ration_Item.getPd_image().contains(item_Position.getCap_pdImage())){
                                            databaseRefe_remove_Cap_rating.child(ration_Item.getRatig_dataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(All_CapShow_Rate.this, "Product Delete SuccessFull...", Toast.LENGTH_SHORT).show();

                                                    StorageReference storageRefeimage=firebaseStorage.getReferenceFromUrl(item_Position.getCap_pdImage());
                                                    storageRefeimage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            String remveKey=item_Position.getCap_product_Key();
                                                            databaseRefe_showCap.child(remveKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                     Toast.makeText(All_CapShow_Rate.this, "Your Data Remove SuccessFull...", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                               Toast.makeText(All_CapShow_Rate.this, "Your Data Remove Failde..", Toast.LENGTH_SHORT).show();

                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                           Toast.makeText(All_CapShow_Rate.this, "Product Delete Failde...", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Rating Image  Not Match", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError error) {
                            Toast.makeText(All_CapShow_Rate.this, "Rating Lodding Failde", Toast.LENGTH_SHORT).show();
                                }
                            });




                        }
                    });
                }




            }

            @Override
            public void onCancelled(DatabaseError error) {
           Toast.makeText(All_CapShow_Rate.this, "Lodding Failde....", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    try {

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
  }catch (Exception exception){
    Toast.makeText(this, "Exception"+exception.getMessage(), Toast.LENGTH_SHORT).show();
  }
    }

    private void fileList(String textData) {
     try {
         List<Cap_product_Model>filterList=new ArrayList<>();
         for(Cap_product_Model item: modelList){
             if(item.getCap_pdName().toLowerCase().contains(textData.toLowerCase())){
                 filterList.add(item);
             }
         }
         adapte.filterdList(filterList);
     }catch (Exception exception){
     Toast.makeText(this, "Error:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
     }

    }

}