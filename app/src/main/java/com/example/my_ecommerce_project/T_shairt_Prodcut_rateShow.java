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

public class T_shairt_Prodcut_rateShow extends AppCompatActivity {
private RecyclerView recyclerView;
private t_shart_ProductAdapter adapter;
private List<T_shirt_Moder>t_shirt_moderList=new ArrayList<>();
private DatabaseReference databaseRefer_show;
private DatabaseReference databaseRefer__t_shirt_rating_delete;
 private FirebaseStorage firebaseStorage;
 private LayoutInflater layoutInflater;
 private View view;
 private AlertDialog alertDialog;

    private EditText editText_nameUp,editText_price_up,editText_stock;
    Button button_UPdate,button_exit;
    private TextView textView_pdId;
    private EditText editText_search_Id;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPrefere_status;
    private String status="Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_shairt__prodcut_rate_show);
        databaseRefer_show= FirebaseDatabase.getInstance().getReference("T_shirtProduct_Add");
        databaseRefer__t_shirt_rating_delete= FirebaseDatabase.getInstance().getReference("T_shirt_Rating");

        firebaseStorage= FirebaseStorage.getInstance();

        recyclerView=(RecyclerView)findViewById(R.id.recylcer_t_shirt_rate_Show);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite...");
        progressDialog.show();
        editText_search_Id=(EditText) findViewById(R.id.t_shairt_serchId);

        sharedPrefere_status=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        status= sharedPrefere_status.getString("my_status_key","Data Not Found");

        // Read from the database
        databaseRefer_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                t_shirt_moderList.clear();
            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
           T_shirt_Moder t_shirt_moder=dataSnapshot1.getValue(T_shirt_Moder.class);
           t_shirt_moder.setTshirt_pdKey(dataSnapshot1.getKey());

              t_shirt_moderList.add(t_shirt_moder);
            }

            adapter=new t_shart_ProductAdapter(getApplicationContext(),t_shirt_moderList);
            recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

                if(status.contains("User")){
                    adapter.mystatus="User";
                    adapter.setUser_On_ClickListener(new t_shart_ProductAdapter.onItemClickListener_item_User() {
                        @Override
                        public void onItemclick(int position) {
                            T_shirt_Moder item_Position=t_shirt_moderList.get(position);
                       Intent intent=new Intent(T_shairt_Prodcut_rateShow.this,t_shairt_Category.class);
                            intent.putExtra("pd_cord",item_Position.getTshirt_pdCord());
                            intent.putExtra("pd_name",item_Position.getTshirt_pdName());
                            intent.putExtra("pd_Desr",item_Position.getTshirt_pdDesr());
                          //  intent.putExtra("pd_stock",item_Position.getTshirt_pdStock());
                            intent.putExtra("pd_price",item_Position.getTshirt_pdPrice());
                            intent.putExtra("pd_image",item_Position.getTshirt_pdImage());
                            intent.putExtra("pd_size",item_Position.getTshirt_size());
                            intent.putExtra("pd_discount",item_Position.getTshirt_Discount());
                            startActivity(intent);
                        }
                    });

                }else{

                   adapter.mystatus="Admin";
                    adapter.setOnItemClickLisiner(new t_shart_ProductAdapter.onItem_ClickLisiner() {
                        @Override
                        public void onClick(int position) {
                            T_shirt_Moder item_Position=t_shirt_moderList.get(position);
                            String Name=item_Position.getTshirt_pdName();
                   Toast.makeText(T_shairt_Prodcut_rateShow.this, "Name:"+Name, Toast.LENGTH_SHORT).show();

                       Intent intent=new Intent(T_shairt_Prodcut_rateShow.this,t_shairt_Category.class);
                            intent.putExtra("pd_cord",item_Position.getTshirt_pdCord());
                            intent.putExtra("pd_name",item_Position.getTshirt_pdName());
                            intent.putExtra("pd_Desr",item_Position.getTshirt_pdDesr());
                           // intent.putExtra("pd_stock",item_Position.getTshirt_pdStock());
                            intent.putExtra("pd_price",item_Position.getTshirt_pdPrice());
                            intent.putExtra("pd_image",item_Position.getTshirt_pdImage());
                            intent.putExtra("pd_size",item_Position.getTshirt_size());
                            intent.putExtra("pd_discount",item_Position.getTshirt_Discount());
                            startActivity(intent);

                        }

                        @Override
                        public void on_Delete(int position) {
                            final T_shirt_Moder item_Position=t_shirt_moderList.get(position);

                            // Read from the database
                            databaseRefer__t_shirt_rating_delete.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        ration_DataModel ration_Item = dataSnapshot1.getValue(ration_DataModel.class);
                                        ration_Item.setRatig_dataKey(dataSnapshot1.getKey());

                                        if(ration_Item.getPd_image().contains(item_Position.getTshirt_pdImage())){
                                            databaseRefer__t_shirt_rating_delete.child(ration_Item.getRatig_dataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    StorageReference storageReferImageUri=firebaseStorage.getReferenceFromUrl(item_Position.getTshirt_pdImage());
                                                    storageReferImageUri.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            String myKey=item_Position.getTshirt_pdKey();
                                                            databaseRefer_show.child(myKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(T_shairt_Prodcut_rateShow.this, "Your Product Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(T_shairt_Prodcut_rateShow.this, "Your Product Delete Failde.."+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                      Toast.makeText(T_shairt_Prodcut_rateShow.this, "Rating Image Lodding Failde"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else{
                                 Toast.makeText(T_shairt_Prodcut_rateShow.this, "Rating Image  Not Match", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    }

                                @Override
                                public void onCancelled(DatabaseError error) {
                          Toast.makeText(T_shairt_Prodcut_rateShow.this, "Rating Image Lodding Failde"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void on_Update(int position) {
                            final T_shirt_Moder item_Position=t_shirt_moderList.get(position);
                            alertDialog=new AlertDialog.Builder(T_shairt_Prodcut_rateShow.this).create();
                            alertDialog.setCancelable(false);
                            alertDialog.setTitle("Update");
                            alertDialog.setIcon(R.drawable.recycle_icon);
                            layoutInflater=getLayoutInflater();

                            view= layoutInflater.inflate(R.layout.t_shairt_update_page,null);
                            alertDialog.setView(view);

                            editText_nameUp=(EditText)view.findViewById(R.id.edit_t_shairt_Up_Name);
                            editText_price_up=(EditText)view.findViewById(R.id.edit_t_shairt_Price);
                          //  editText_stock=(EditText)view.findViewById(R.id.edit_t_shairt_quan);
                            textView_pdId=(TextView)view.findViewById(R.id.text_t_shirt_Up_Id);

                            button_exit=(Button)view.findViewById(R.id.t_shairt_exit_btn);
                            button_UPdate=(Button)view.findViewById(R.id.T_shairt_update_Data);

                            textView_pdId.setText("ID:"+item_Position.getTshirt_pdCord());
                            editText_nameUp.setText(""+item_Position.getTshirt_pdName());
                            editText_price_up.setText(""+item_Position.getTshirt_pdPrice());
                           // editText_stock.setText(""+item_Position.getTshirt_pdStock());

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

                                    } else{
                                        final HashMap<String, Object> hashMapUpdate = new HashMap<>();
                                        databaseRefer_show.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                                    T_shirt_Moder t_shirt_moder=dataSnapshot1.getValue(T_shirt_Moder.class);
                                                    t_shirt_moder.setTshirt_pdKey(dataSnapshot1.getKey());

                                                    if(t_shirt_moder.getTshirt_pdCord().contains(item_Position.getTshirt_pdCord())){
                                                       // int stock=Integer.parseInt(editText_stock.getText().toString());
                                                        int pri=Integer.parseInt(editText_price_up.getText().toString());
                                                        hashMapUpdate.put("tshirt_pdName", editText_nameUp.getText().toString());
                                                        hashMapUpdate.put("tshirt_pdPrice", pri);
                                                       // hashMapUpdate.put("tshirt_pdStock",stock);

                                                        databaseRefer_show.child(item_Position.getTshirt_pdKey()).updateChildren(hashMapUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(T_shairt_Prodcut_rateShow.this, "Your product Update SuccessFull..", Toast.LENGTH_SHORT).show();
                                                                editText_nameUp.setText("");
                                                                editText_price_up.setText("");
                                                              //  editText_stock.setText("");
                                                                textView_pdId.setText("");
                                                                adapter.notifyDataSetChanged();
                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                progressDialog.dismiss();
                                                Toast.makeText(T_shairt_Prodcut_rateShow.this, "Update Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });

                            alertDialog.show();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
            Toast.makeText(T_shairt_Prodcut_rateShow.this, "Lodding Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        editText_search_Id.addTextChangedListener(new TextWatcher() {
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
            List<T_shirt_Moder>filterList=new ArrayList<>();
            for(T_shirt_Moder item: t_shirt_moderList){
                if(item.getTshirt_pdImage().toLowerCase().contains(textData.toLowerCase())){
                    filterList.add(item);
                }
            }
            adapter.filterdList(filterList);
        }catch (Exception exception){
            Toast.makeText(this, "Errror:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


}