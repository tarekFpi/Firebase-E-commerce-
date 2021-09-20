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
import java.util.Map;

public class keybord_mouse_rate_show extends AppCompatActivity {
 private keybord_mouse_Adapter adapter;
 private List<keybord_mouse_Model>mouse_modelList=new ArrayList<>();
 private RecyclerView recyclerView;
 private DatabaseReference databaseRef_show;
 private DatabaseReference databaseRef_keybord_mouse_rating;
 private LayoutInflater layoutInflater;
 private View view;
 private AlertDialog alertDialog;
    private Button button_update,button_exit;
    private EditText editText_name,editText_stock,editText_price,editText_discount;
    private TextView textView_pdId,textView_date;
    private keybord_mouse_Model Item_update;
private FirebaseStorage firebaseStorage;
private ProgressDialog progressDialog;
private EditText editText_search;

    private SharedPreferences sharedPrefere_status;
    private String status="Admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keybord_mouse_rate_show);
        databaseRef_show= FirebaseDatabase.getInstance().getReference("Keybord_Mouse_Add");
  databaseRef_keybord_mouse_rating= FirebaseDatabase.getInstance().getReference("Keybord_Mouse_Rating_Add");

        firebaseStorage=FirebaseStorage.getInstance();
        recyclerView=(RecyclerView) findViewById(R.id.recycler_keybord_mouse_show_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editText_search=(EditText)findViewById(R.id.keybord_order_search);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wite...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sharedPrefere_status=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        status= sharedPrefere_status.getString("my_status_key","Data Not Found");
        // Read from the database
        databaseRef_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mouse_modelList.clear();
             for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
            keybord_mouse_Model itemData=dataSnapshot1.getValue(keybord_mouse_Model.class);
           itemData.setKeyMouse_Data_Key(dataSnapshot1.getKey());
            mouse_modelList.add(itemData);
             }
             adapter=new keybord_mouse_Adapter(getApplicationContext(),mouse_modelList);
             recyclerView.setAdapter(adapter);
            progressDialog.dismiss();

            if(status.contains("User")){
                adapter.mystatus="User";

                adapter.setUser_On_ClickListener(new keybord_mouse_Adapter.onItemClickListener_item_User() {
                    @Override
                    public void onItemclick(int position) {
                        keybord_mouse_Model itemPosition=mouse_modelList.get(position);
                        Intent intent=new Intent(keybord_mouse_rate_show.this,keybord_Mouse_Category.class);
                        intent.putExtra("pd_cord",itemPosition.getKeyMouse_id());
                        intent.putExtra("pd_name",itemPosition.getKeyMouse_name());
                        intent.putExtra("pd_Desr",itemPosition.getKeyMouse_Descrpt());
                       // intent.putExtra("pd_stock",itemPosition.getKeyMouse_stock());
                        intent.putExtra("pd_price",itemPosition.getKeyMouse_price());
                        intent.putExtra("pd_image",itemPosition.getKeyMouse_image());
                        intent.putExtra("pd_discount",itemPosition.getKeyMouse_discount());
                        startActivity(intent);
                    }
                });
            }


                else{
                    adapter.mystatus="Admin";

                    adapter.setOnItemClickLisiner(new keybord_mouse_Adapter.onItem_ClickLisiner() {
                        @Override
                        public void onClick(int position) {
                            keybord_mouse_Model itemPosition=mouse_modelList.get(position);
                            String itemDate=itemPosition.getKeyMouse_Date();
                            Toast.makeText(keybord_mouse_rate_show.this, "Date:"+itemDate, Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(keybord_mouse_rate_show.this,keybord_Mouse_Category.class);
                            intent.putExtra("pd_cord",itemPosition.getKeyMouse_id());
                            intent.putExtra("pd_name",itemPosition.getKeyMouse_name());
                            intent.putExtra("pd_Desr",itemPosition.getKeyMouse_Descrpt());
                           // intent.putExtra("pd_stock",itemPosition.getKeyMouse_stock());
                            intent.putExtra("pd_price",itemPosition.getKeyMouse_price());
                            intent.putExtra("pd_image",itemPosition.getKeyMouse_image());
                            intent.putExtra("pd_discount",itemPosition.getKeyMouse_discount());
                            startActivity(intent);
                        }

                        @Override
                        public void Update(int position) {
                            alertDialog=new AlertDialog.Builder(keybord_mouse_rate_show.this).create();

                            alertDialog.setCancelable(false);
                            alertDialog.setTitle("Update");
                            alertDialog.setIcon(R.drawable.recycle_icon);
                            layoutInflater=getLayoutInflater();

                            view= layoutInflater.inflate(R.layout.keybord_mouse_update,null);
                            alertDialog.setView(view);
                            editText_name=(EditText)view.findViewById(R.id.edit_keyM_Up_Name);
                            editText_price=(EditText)view.findViewById(R.id.edit_keyM_Price);
                          //  editText_stock=(EditText)view.findViewById(R.id.edit_keyM_quan);
                            editText_discount=(EditText)view.findViewById(R.id.edit_keyM_dicount);

                            button_update=(Button)view.findViewById(R.id.keymouse_update_Data);
                            button_exit=(Button)view.findViewById(R.id.keybord_exit_btn);
                            textView_pdId=(TextView) view.findViewById(R.id.text_keymouse_Up_Id);
                            textView_date=(TextView) view.findViewById(R.id.add_date);

                            final keybord_mouse_Model item_getData=mouse_modelList.get(position);
                            String up_name=item_getData.getKeyMouse_name();
                           // final int up_stock=item_getData.getKeyMouse_stock();
                            final int up_price=item_getData.getKeyMouse_price();
                            final int up_discount=item_getData.getKeyMouse_discount();

                            textView_pdId.setText("Product Cord:"+item_getData.getKeyMouse_id());
                            textView_date.setText("Add Date:"+item_getData.getKeyMouse_Date());
                            editText_name.setText(up_name);
                           // editText_stock.setText(""+up_stock);
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

                                    }else if(editText_discount.getText().toString().isEmpty()){
                                        editText_discount.setError("Please Your Discount...");
                                        editText_discount.requestFocus();
                                    }else {

                                        databaseRef_show.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                Map<String, Object> hashMap = new HashMap<String, Object>();
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                    Item_update = dataSnapshot1.getValue(keybord_mouse_Model.class);
                                                    Item_update.setKeyMouse_Data_Key(dataSnapshot1.getKey());

                                                    if(Item_update.getKeyMouse_id().contains(item_getData.getKeyMouse_id())){
                                      /*int up_stock=Item_update.getProduct_quantity();
                                      int up_price=Item_update.getProduct_price();*/

                                                      ///  int stock=Integer.parseInt(editText_stock.getText().toString());
                                                        int pri=Integer.parseInt(editText_price.getText().toString());
                                                        int disc=Integer.parseInt(editText_discount.getText().toString());

                                                        hashMap.put("keyMouse_name", editText_name.getText().toString());
                                                        hashMap.put("keyMouse_price", pri);
                                                      //  hashMap.put("keyMouse_stock",stock);
                                                        hashMap.put("keyMouse_discount",disc);
                                                        databaseRef_show.child(Item_update.getKeyMouse_Data_Key()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(keybord_mouse_rate_show.this, "Your product Update SuccessFull..", Toast.LENGTH_SHORT).show();
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
                                                Toast.makeText(keybord_mouse_rate_show.this, "Update Lodding Failde" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                            final keybord_mouse_Model item_Position=mouse_modelList.get(position);
                            // Read from the database
                            databaseRef_keybord_mouse_rating.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        ration_DataModel ration_Item = dataSnapshot1.getValue(ration_DataModel.class);
                                        ration_Item.setRatig_dataKey(dataSnapshot1.getKey());

                                        if(ration_Item.getPd_image().contains(item_Position.getKeyMouse_image())){
                                            databaseRef_keybord_mouse_rating.child(ration_Item.getRatig_dataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    StorageReference storageReferImageUri=firebaseStorage.getReferenceFromUrl(item_Position.getKeyMouse_image());
                                                    storageReferImageUri.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            String myKey=item_Position.getKeyMouse_Data_Key();
                                                            databaseRef_show.child(myKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                             Toast.makeText(keybord_mouse_rate_show.this, "Your Product Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                       Toast.makeText(keybord_mouse_rate_show.this, "Your Product Delete Failde.."+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                        }
                                                    });

                                                }
                                            });
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Rating Image  Not Match", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                    Toast.makeText(keybord_mouse_rate_show.this, "Rating image Lodding Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
          Toast.makeText(keybord_mouse_rate_show.this, "Your Product Lodding Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
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
        try{
            List<keybord_mouse_Model>filterList=new ArrayList<>();
            for(keybord_mouse_Model item: mouse_modelList){
                if(item.getKeyMouse_name().toLowerCase().contains(textData.toLowerCase())){
                    filterList.add(item);
                }
            }
            adapter.filterdList(filterList);
        }catch (Exception exception){
       Toast.makeText(this, "Exception"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}