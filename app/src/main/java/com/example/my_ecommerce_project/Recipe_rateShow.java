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

public class Recipe_rateShow extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Recipe_rateAdapter adapter;
    private List<Recipe_ModelAdd>recipe_AddList=new ArrayList<>();
     private DatabaseReference databaseRefer_show;
     private DatabaseReference databaseRefer_rating_remove;
    private LayoutInflater layoutInflater;
    private View view;
    private AlertDialog alertDialog;
  private FirebaseStorage firebaseStorage;
    private EditText editText_nameUp,editText_price_up,editText_stock,editText_discount;
    Button button_UPdate,button_exit;
    private TextView textView_pdId;
    private ProgressDialog progressDialog;
    private EditText editText_search;

    private SharedPreferences sharedPrefere_status;
    private String status="Admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_rate_show);
        databaseRefer_show= FirebaseDatabase.getInstance().getReference("Recipe_product_Add");
        databaseRefer_rating_remove= FirebaseDatabase.getInstance().getReference("Recipe_Rating_Add");

        firebaseStorage=FirebaseStorage.getInstance();
        recyclerView=(RecyclerView)findViewById(R.id.recipe_rateShow);
        editText_search=(EditText)findViewById(R.id.recipe_serchId);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wite..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sharedPrefere_status=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        status= sharedPrefere_status.getString("my_status_key","Data Not Found");
        // Read from the database
        databaseRefer_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipe_AddList.clear();
           for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
               Recipe_ModelAdd item_data=dataSnapshot1.getValue(Recipe_ModelAdd.class);
               item_data.setRecipe_Data_key(dataSnapshot1.getKey());
               recipe_AddList.add(item_data);
            }
           adapter=new Recipe_rateAdapter(getApplicationContext(),recipe_AddList);
           recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

                if(status.contains("User")){
                    adapter.mystatus="User";
                    adapter.setUser_On_ClickListener(new Recipe_rateAdapter.onItemClickListener_item_User() {
                        @Override
                        public void onItemclick(int position) {
                            Recipe_ModelAdd item_Position=recipe_AddList.get(position);

                            Intent intent=new Intent(Recipe_rateShow.this,Recipe_Category.class);
                            intent.putExtra("pd_cord",item_Position.getRecipe_pd_id());
                            intent.putExtra("pd_name",item_Position.getRecipe_pd_Name());
                            intent.putExtra("pd_Desr",item_Position.getRecipe_pd_Descrpt());
                            intent.putExtra("pd_price",item_Position.getRecipe_pd_price());
                            intent.putExtra("pd_image",item_Position.getRecipe_pd_image());
                            intent.putExtra("pd_diccount",item_Position.getRecipe_pd_discount());
                            intent.putExtra("pd_expirt",item_Position.getRecipe_expiredDate());
                            startActivity(intent);
                        }
                    });
                }

               else{

                    adapter.mystatus="Admin";
                    adapter.setOnItemClickLisiner(new Recipe_rateAdapter.onItemClickLisiner() {
                        @Override
                        public void OnClick(int position) {

                Recipe_ModelAdd item_Position=recipe_AddList.get(position);
                  String date=recipe_AddList.get(position).getRecipe_expiredDate();
                    Toast.makeText(Recipe_rateShow.this, "ExpirtDate:"+date, Toast.LENGTH_SHORT).show();
                           // Toast.makeText(Recipe_rateShow.this, ""+item_Position.getRecipe_pd_Name(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Recipe_rateShow.this,Recipe_Category.class);
                            intent.putExtra("pd_cord",item_Position.getRecipe_pd_id());
                            intent.putExtra("pd_name",item_Position.getRecipe_pd_Name());
                            intent.putExtra("pd_Desr",item_Position.getRecipe_pd_Descrpt());
                            intent.putExtra("pd_price",item_Position.getRecipe_pd_price());
                            intent.putExtra("pd_image",item_Position.getRecipe_pd_image());
                            intent.putExtra("pd_diccount",item_Position.getRecipe_pd_discount());
                            intent.putExtra("pd_expirt",item_Position.getRecipe_expiredDate());
                            startActivity(intent);
                        }

                        @Override
                        public void OnUpdate(int position) {
                            final Recipe_ModelAdd item_position=recipe_AddList.get(position);

                            alertDialog=new AlertDialog.Builder(Recipe_rateShow.this).create();
                            alertDialog.setCancelable(false);
                            alertDialog.setTitle("Update");
                            alertDialog.setIcon(R.drawable.recycle_icon);
                            layoutInflater=getLayoutInflater();

                            view= layoutInflater.inflate(R.layout.recipe_update_layout,null);
                            alertDialog.setView(view);

                            editText_nameUp=(EditText)view.findViewById(R.id.edit_recipe_Up_Name);
                            editText_price_up=(EditText)view.findViewById(R.id.edit_recipe_Price);
                           // editText_stock=(EditText)view.findViewById(R.id.edit_recipe_up_quan);
                            editText_discount=(EditText)view.findViewById(R.id.edit_recipe_discount);
                            textView_pdId=(TextView)view.findViewById(R.id.text_recipe_Up_Id);

                            button_exit=(Button)view.findViewById(R.id.recipe_exit_btn);
                            button_UPdate=(Button)view.findViewById(R.id.recipe_update_Data);

                            textView_pdId.setText("ID:"+item_position.getRecipe_pd_id());
                            editText_nameUp.setText(""+item_position.getRecipe_pd_Name());
                            editText_price_up.setText(""+item_position.getRecipe_pd_price());

                            editText_discount.setText(""+item_position.getRecipe_pd_discount());

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
                                        editText_price_up.setError("Please Your product price");
                                        editText_price_up.requestFocus();

                                    } else if(editText_discount.getText().toString().isEmpty()){
                                        editText_discount.setError("Please Your product Discount");
                                        editText_discount.requestFocus();
                                    }else{
                                        final HashMap<String, Object> hashMapUpdate = new HashMap<>();
                                        databaseRefer_show.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                                    Recipe_ModelAdd item_data=dataSnapshot1.getValue(Recipe_ModelAdd.class);
                                                    item_data.setRecipe_Data_key(dataSnapshot1.getKey());

                                                    if(item_data.getRecipe_pd_id().contains(item_position.getRecipe_pd_id())){
                                                        //int stock=Integer.parseInt(editText_stock.getText().toString());
                                                        int pri=Integer.parseInt(editText_price_up.getText().toString());
                                                        int discount=Integer.parseInt(editText_discount.getText().toString());

                                                        hashMapUpdate.put("recipe_pd_Name",editText_nameUp.getText().toString());
                                                        hashMapUpdate.put("recipe_pd_price",pri);
                                                       // hashMapUpdate.put("recipe_pd_stock",stock);
                                                        hashMapUpdate.put("recipe_pd_discount",discount);

                                                        databaseRefer_show.child(item_position.getRecipe_Data_key()).updateChildren(hashMapUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(Recipe_rateShow.this, "Your product Update SuccessFull..", Toast.LENGTH_SHORT).show();
                                                                editText_nameUp.setText("");
                                                                editText_price_up.setText("");
                                                               // editText_stock.setText("");
                                                                textView_pdId.setText("");
                                                                editText_discount.setText("");
                                                                adapter.notifyDataSetChanged();
                                                                alertDialog.dismiss();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Recipe_rateShow.this, "Your Product Update Failde.."+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                Toast.makeText(Recipe_rateShow.this, "Your Recipe Update Lodding Failde:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }

                            });
                            alertDialog.show();
                        }

                        @Override
                        public void OnDelete(int position) {
                            final Recipe_ModelAdd item_position=recipe_AddList.get(position);
                            // Read from the database
                            databaseRefer_rating_remove.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        ration_DataModel ration_Item = dataSnapshot1.getValue(ration_DataModel.class);
                                        ration_Item.setRatig_dataKey(dataSnapshot1.getKey());

                                        if(ration_Item.getPd_image().contains(item_position.getRecipe_pd_image())){
                                            databaseRefer_rating_remove.child(ration_Item.getRatig_dataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    StorageReference storageRefer=firebaseStorage.getReferenceFromUrl(item_position.getRecipe_pd_image());
                                                    storageRefer.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            String mykey= item_position.getRecipe_Data_key();
                                                            databaseRefer_show.child(mykey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                              Toast.makeText(Recipe_rateShow.this, "Your Prodcut Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                              Toast.makeText(Recipe_rateShow.this, "Your Prodcut Delete Failde"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                        }
                                                    });
                                                }
                                            });
                                        }else{
                                       Toast.makeText(Recipe_rateShow.this, "Rating image Not Match", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    }

                                @Override
                                public void onCancelled(DatabaseError error) {
                           Toast.makeText(Recipe_rateShow.this, "Your Prodcut Rating Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });



                        }
                    });
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Recipe_rateShow.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
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
            public void afterTextChanged(Editable editable) {
                fileList(editable.toString());
            }
        });
    }

    private void fileList(String textData) {
        try {
            List<Recipe_ModelAdd>filterList=new ArrayList<>();
            for(Recipe_ModelAdd item: recipe_AddList){
       if(item.getRecipe_pd_Name().toLowerCase().contains(textData.toLowerCase())){
         filterList.add(item);
       }
      }
     adapter.filterdList(filterList);
        }catch (Exception exception){
            Toast.makeText(this, "Recipe Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}