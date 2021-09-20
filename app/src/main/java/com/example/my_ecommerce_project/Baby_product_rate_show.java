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

public class Baby_product_rate_show extends AppCompatActivity {
 private RecyclerView recyclerView;
 private baby_rateAdapter adapter;
 private List<baby_product_Model>babyProductModelList=new ArrayList<>();
 private DatabaseReference databaseRef_show;
  private DatabaseReference databaseRefe_remove_baby_rating;

   private ProgressDialog progressDialog;
    private LayoutInflater layoutInflater;
    private View view;
    private TextView textView_pdId;
    private AlertDialog alertDialog;
    private EditText editText_search;
    private Button button_update,button_exit;

    private EditText editText_name,editText_stock,editText_price,editText_discount;
    private FirebaseStorage firebaseStorage;
    private SharedPreferences sharedPrefere_status;
    private String status="Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_product_rate_show);
        databaseRef_show= FirebaseDatabase.getInstance().getReference("Baby_product_Add");
        databaseRefe_remove_baby_rating= FirebaseDatabase.getInstance().getReference("baby_rationg_ImageSave");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_baby_rate_Show);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseStorage=FirebaseStorage.getInstance();
        editText_search=(EditText)findViewById(R.id.baby_order_search);

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
                babyProductModelList.clear();
            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                baby_product_Model add_item=dataSnapshot1.getValue(baby_product_Model.class);
                add_item.setBaby_pd_DataKey(dataSnapshot1.getKey());
                babyProductModelList.add(add_item);
            }
            adapter=new baby_rateAdapter(getApplicationContext(),babyProductModelList);
            recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

                if(status.contains("User")){
                    adapter.mystatus ="User";
                    adapter.setUser_On_ClickListener(new baby_rateAdapter.onItemClickListener_item_User() {
                        @Override
                        public void onItemclick(int position) {
                            baby_product_Model item_position=babyProductModelList.get(position);
                            Intent intent=new Intent(Baby_product_rate_show.this,babyPd_Category.class);
                            intent.putExtra("pd_cord",item_position.getBaby_pd_id());
                            intent.putExtra("pd_name",item_position.getBaby_pd_Name());
                            intent.putExtra("pd_Desr",item_position.getBaby_pd_Desript());
                            //intent.putExtra("pd_stock",item_position.getBaby_pd_stock());
                            intent.putExtra("pd_price",item_position.getBaby_pd_price());
                            intent.putExtra("pd_image",item_position.getBaby_pd_image());
                            intent.putExtra("pd_discount",item_position.getBaby_pd_discount());
                            intent.putExtra("pd_size",item_position.getBaby_pd_size());
                            startActivity(intent);
                        }
                    });

                }else{
                    adapter.mystatus ="Admin";
                   adapter.setOnItemClickLisiner(new baby_rateAdapter.onItem_ClickLisiner() {
                       @Override
                       public void onClick(int position) {
                           baby_product_Model item_position=babyProductModelList.get(position);
                           String date=item_position.getBaby_pd_date();
                     Toast.makeText(Baby_product_rate_show.this, "Date:"+date, Toast.LENGTH_SHORT).show();

                           Intent intent=new Intent(Baby_product_rate_show.this,babyPd_Category.class);
                           intent.putExtra("pd_cord",item_position.getBaby_pd_id());
                           intent.putExtra("pd_name",item_position.getBaby_pd_Name());
                           intent.putExtra("pd_Desr",item_position.getBaby_pd_Desript());
                           //intent.putExtra("pd_stock",item_position.getBaby_pd_stock());
                           intent.putExtra("pd_price",item_position.getBaby_pd_price());
                           intent.putExtra("pd_image",item_position.getBaby_pd_image());
                           intent.putExtra("pd_discount",item_position.getBaby_pd_discount());
                           intent.putExtra("pd_size",item_position.getBaby_pd_size());
                           startActivity(intent);
                       }

                       @Override
                       public void Update(int position) {
                           final baby_product_Model item_position=babyProductModelList.get(position);
                           alertDialog=new AlertDialog.Builder(Baby_product_rate_show.this).create();
                           alertDialog.setCancelable(false);
                           alertDialog.setTitle("Update");
                           alertDialog.setIcon(R.drawable.recycle_icon);
                           layoutInflater=getLayoutInflater();

                           view= layoutInflater.inflate(R.layout.baby_product_update,null);
                           alertDialog.setView(view);

                           editText_name=(EditText)view.findViewById(R.id.edit_baby_Update_Name);
                           editText_price=(EditText)view.findViewById(R.id.edit_baby_update_Price);
                           //editText_stock=(EditText)view.findViewById(R.id.edit_phon_baby_quan);
                           editText_discount=(EditText)view.findViewById(R.id.edit_baby_discount);
                           textView_pdId=(TextView) view.findViewById(R.id.baby_up_pd_Id);

                           button_update=(Button)view.findViewById(R.id.baby_update_Data);
                           button_exit=(Button)view.findViewById(R.id.exit_btn);

                           editText_name.setText(item_position.getBaby_pd_Name());
                           editText_price.setText(""+item_position.getBaby_pd_price());
                           editText_discount.setText(""+item_position.getBaby_pd_discount());
                          // editText_stock.setText(""+item_position.getBaby_pd_stock());
                           textView_pdId.setText("Product Cord:"+item_position.getBaby_pd_id());

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

                                   } else if(editText_discount.getText().toString().isEmpty()){
                                       editText_discount.setError("Please Your Discount...");
                                       editText_discount.requestFocus();

                                   }else{
                                       // Read from the database
                                       databaseRef_show.addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                               HashMap<String, Object> hashMap = new HashMap<>();
                                               for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                                   baby_product_Model add_item=dataSnapshot1.getValue(baby_product_Model.class);
                                                   add_item.setBaby_pd_DataKey(dataSnapshot1.getKey());

                                                   if(add_item.getBaby_pd_id().contains(item_position.getBaby_pd_id())){

                                                       int pri=Integer.parseInt(editText_price.getText().toString());
                                                       int disc=Integer.parseInt(editText_discount.getText().toString());
                                                       hashMap.put("baby_pd_Name", editText_name.getText().toString());
                                                       hashMap.put("baby_pd_price", pri);
                                                       hashMap.put("baby_pd_discount",disc);

                                                       databaseRef_show.child(add_item.getBaby_pd_DataKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                           @Override
                                                           public void onSuccess(Void aVoid) {
                                                               editText_name.setText("");
                                                               editText_price.setText("");
                                                               editText_discount.setText("");
                                                               textView_pdId.setText("");
                                                               alertDialog.dismiss();
                                                               Toast.makeText(Baby_product_rate_show.this, "Your Data Update SuccessFull..", Toast.LENGTH_SHORT).show();
                                                           }
                                                       });
                                                   }
                                               }
                                           }

                                           @Override
                                           public void onCancelled(DatabaseError error) {
                                               progressDialog.dismiss();
                                               Toast.makeText(Baby_product_rate_show.this, "Your Data Lodding Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                   }
                               }
                           });
                           alertDialog.show();
                       }

                       @Override
                       public void on_Delete(int position) {
                           final baby_product_Model item_position=babyProductModelList.get(position);
                           // Read from the database
                           databaseRefe_remove_baby_rating.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(DataSnapshot dataSnapshot) {
                                   for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                       ration_DataModel ration_Item = dataSnapshot1.getValue(ration_DataModel.class);
                                       ration_Item.setRatig_dataKey(dataSnapshot1.getKey());

                                       if(item_position.getBaby_pd_image().contains(ration_Item.getPd_image())){
                                        databaseRefe_remove_baby_rating.child(ration_Item.getRatig_dataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                ///firebase image Delete SuccessFull
                                                StorageReference storageRefer_image=firebaseStorage.getReferenceFromUrl(item_position.getBaby_pd_image());
                                                storageRefer_image.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                String remove_key=databaseRef_show.getKey();
                                              databaseRef_show.child(item_position.getBaby_pd_DataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                         Toast.makeText(Baby_product_rate_show.this, "Your Product Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                  @Override
                                                  public void onFailure(@NonNull Exception e) {
                                         Toast.makeText(Baby_product_rate_show.this, "Your Product  Image Delete Failde"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                  }
                                              });
                                                    }
                                                });


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                  Toast.makeText(Baby_product_rate_show.this, "Rating Delete Failde"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                       }else{
                                           Toast.makeText(getApplicationContext(), "Rating Image  Not Match", Toast.LENGTH_SHORT).show();
                                       }
                                   }
                                   }

                               @Override
                               public void onCancelled(DatabaseError error) {
                   Toast.makeText(Baby_product_rate_show.this, "Rating Delete Failde"+error.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           });

                       }
                   });
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
          Toast.makeText(Baby_product_rate_show.this, "Data Lodding Failde..", Toast.LENGTH_SHORT).show();
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
            List<baby_product_Model>filterList=new ArrayList<>();

            for(baby_product_Model item :babyProductModelList){
                if(item.getBaby_pd_Name().toLowerCase().contains(textData.toLowerCase())){
                    filterList.add(item);
                }
            }
            adapter.filter_chang(filterList);
        }catch (Exception exception){
         Toast.makeText(this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}