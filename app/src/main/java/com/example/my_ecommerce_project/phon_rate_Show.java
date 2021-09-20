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

public class phon_rate_Show extends AppCompatActivity {
 private RecyclerView recyclerView;
 private phon_rate_Adapter adapter;
 private List<phon_Model>phon_modelList=new ArrayList<>();
 private DatabaseReference databaseReference_show;
 private DatabaseReference databaseReference_phon_rationg_remove;
 private ProgressDialog progressDialog;
 private EditText editText_search;
 private Button button_update,button_exit;

 private EditText editText_name,editText_stock,editText_price,editText_discount;
 private FirebaseStorage firebaseStorage;
    private LayoutInflater layoutInflater;
    private View view;
    private TextView textView_pdId;
    private AlertDialog alertDialog;
    private SharedPreferences sharedPrefere_status;
    private String status="Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phon_rate__show);
       databaseReference_show= FirebaseDatabase.getInstance().getReference("Android_phon_product_Add");
      databaseReference_phon_rationg_remove= FirebaseDatabase.getInstance().getReference("Android_phon_Rating_Add");

      firebaseStorage=FirebaseStorage.getInstance();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_phon_rateShow);
        editText_search=(EditText)findViewById(R.id.phon_order_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("please Wite...");
        progressDialog.show();

        sharedPrefere_status=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        status= sharedPrefere_status.getString("my_status_key","Data Not Found");

        // Read from the database
        databaseReference_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phon_modelList.clear();
              for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                  phon_Model add_item=dataSnapshot1.getValue(phon_Model.class);
                  add_item.setPhon_pdKey(dataSnapshot1.getKey());
                  phon_modelList.add(add_item);
              }
              adapter=new phon_rate_Adapter(getApplicationContext(),phon_modelList);
              recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

                if(status.contains("User")){
                    adapter.mystatus="User";
                    adapter.setUser_On_ClickListener(new phon_rate_Adapter.onItemClickListener_item_User() {
                        @Override
                        public void onItemclick(int position) {
                            phon_Model item_position=phon_modelList.get(position);
                            Intent intent=new Intent(phon_rate_Show.this,phon_Category.class);

                            intent.putExtra("pd_cord",item_position.getPhon_pdId());
                            intent.putExtra("pd_name",item_position.getPhon_pdName());
                            intent.putExtra("pd_Desr",item_position.getPhon_pdDecpt());
                            //intent.putExtra("pd_stock",item_position.getPhon_pd_stock());
                            intent.putExtra("pd_price",item_position.getPhon_pdprice());
                            intent.putExtra("pd_image",item_position.getPhon_pdimage());
                            intent.putExtra("pd_discount",item_position.getPhon_pdDiscount());
                            startActivity(intent);
                        }
                    });

                }

                else{
                    adapter.mystatus="Admin";
                    adapter.mystatus=status;

                    adapter.setOnItemClickLisiner(new phon_rate_Adapter.onItem_ClickLisiner() {
                        @Override
                        public void onClick(int position) {
                            phon_Model item_position=phon_modelList.get(position);
                          String date=item_position.getPhon_pdDate();
                      Toast.makeText(phon_rate_Show.this, "Add Date:"+date, Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(phon_rate_Show.this,phon_Category.class);

                            intent.putExtra("pd_cord",item_position.getPhon_pdId());
                            intent.putExtra("pd_name",item_position.getPhon_pdName());
                            intent.putExtra("pd_Desr",item_position.getPhon_pdDecpt());
                            //intent.putExtra("pd_stock",item_position.getPhon_pd_stock());
                            intent.putExtra("pd_price",item_position.getPhon_pdprice());
                            intent.putExtra("pd_image",item_position.getPhon_pdimage());
                            intent.putExtra("pd_discount",item_position.getPhon_pdDiscount());
                            startActivity(intent);
                        }
                        @Override
                        public void Update(int position) {
                            final phon_Model item_position=phon_modelList.get(position);
                            alertDialog=new AlertDialog.Builder(phon_rate_Show.this).create();
                            alertDialog.setCancelable(false);
                            alertDialog.setTitle("Update");
                            alertDialog.setIcon(R.drawable.recycle_icon);
                            layoutInflater=getLayoutInflater();

                            view= layoutInflater.inflate(R.layout.phon_product_update,null);
                            alertDialog.setView(view);

                            editText_name=(EditText)view.findViewById(R.id.edit_phon_Update_Name);
                            editText_price=(EditText)view.findViewById(R.id.edit_phon_update_Price);
                            //editText_stock=(EditText)view.findViewById(R.id.edit_phon_update_quan);
                            editText_discount=(EditText)view.findViewById(R.id.edit_update_discount);

                            button_update=(Button)view.findViewById(R.id.phon_update_Data);
                            button_exit=(Button)view.findViewById(R.id.exit_btn);
                            textView_pdId=(TextView) view.findViewById(R.id.phon_pd_Id);

                            String up_name=item_position.getPhon_pdName();
                            //final int up_stock=item_position.getPhon_pd_stock();
                            final int up_price=item_position.getPhon_pdprice();
                            final int up_Discount=item_position.getPhon_pdDiscount();

                            textView_pdId.setText("Product Cord:"+item_position.getPhon_pdId());
                            editText_name.setText(up_name);
                            //editText_stock.setText(""+up_stock);
                            editText_price.setText(""+up_price);
                            editText_discount.setText(""+up_Discount);

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
                                        editText_name.setError("Please Product Name...");
                                        editText_name.requestFocus();
                                    }else if(editText_price.getText().toString().isEmpty()){
                                        editText_price.setError("Please Your price ...");
                                        editText_price.requestFocus();

                                    }else if(editText_discount.getText().toString().isEmpty()){
                                        editText_discount.setError("Please Your Discount...");
                                        editText_discount.requestFocus();

                                    }else{
                                        // Read from the database
                                        databaseReference_show.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                //phon_modelList.clear();
                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                                                    phon_Model add_item=dataSnapshot1.getValue(phon_Model.class);
                                                    add_item.setPhon_pdKey(dataSnapshot1.getKey());
                                                    if(add_item.getPhon_pdId().contains(item_position.getPhon_pdId())){

                                                       // int stock=Integer.parseInt(editText_stock.getText().toString());
                                                        int pri=Integer.parseInt(editText_price.getText().toString());
                                                        int disc=Integer.parseInt(editText_discount.getText().toString());

                                                        hashMap.put("phon_pdName", editText_name.getText().toString());
                                                        hashMap.put("phon_pdprice", pri);
                                                        //hashMap.put("phon_pd_stock",stock);
                                                        hashMap.put("phon_pdDiscount",disc);
                                                        databaseReference_show.child(add_item.getPhon_pdKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(phon_rate_Show.this, "Your Product Update SuccessFull..", Toast.LENGTH_SHORT).show();
                                                                textView_pdId.setText("");
                                                                editText_name.setText("");

                                                                editText_price.setText("");
                                                                editText_discount.setText("");
                                                                alertDialog.dismiss();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(phon_rate_Show.this, "Your Product Update Failde.."+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                Toast.makeText(phon_rate_Show.this, "Your Lodding Data Failde..", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                }
                            });
                            alertDialog.show();
                        }

                        @Override
                        public void on_Delete(int position) {
                            final phon_Model item_position=phon_modelList.get(position);

                            // Read from the database
                            databaseReference_phon_rationg_remove.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        ration_DataModel ration_Item = dataSnapshot1.getValue(ration_DataModel.class);
                                        ration_Item.setRatig_dataKey(dataSnapshot1.getKey());

                                        if(ration_Item.getPd_image().contains(item_position.getPhon_pdimage())){
                                            databaseReference_phon_rationg_remove.child(ration_Item.getRatig_dataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    StorageReference storageRefer_image=firebaseStorage.getReferenceFromUrl(item_position.getPhon_pdimage());
                                                    storageRefer_image.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            databaseReference_show.child(item_position.getPhon_pdKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(phon_rate_Show.this, "Your Data Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                     Toast.makeText(phon_rate_Show.this, "Your Storage Image Delete Failde"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    });


                                                }
                                            });
                                        }else{
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Rating Image  Not Match", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                         Toast.makeText(phon_rate_Show.this, "Your Rating image Failde..", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
           Toast.makeText(phon_rate_Show.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
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
            List<phon_Model>filterList=new ArrayList<>();
            for(phon_Model item :phon_modelList){
                if(item.getPhon_pdName().toLowerCase().contains(textData.toLowerCase()))  {
                    filterList.add(item);
                }
            }
            adapter.filterdList(filterList);
        }catch (Exception exception){
            Toast.makeText(this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}