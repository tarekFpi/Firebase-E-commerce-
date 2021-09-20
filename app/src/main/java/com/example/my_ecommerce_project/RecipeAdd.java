package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class RecipeAdd extends AppCompatActivity {
  private EditText editText_name,editText_price,editText_Id,editText_Decpt,editText_quantity,editText_discount;
  private TextView textView_Expired_date;
  private ImageView imageView;
    private DatePickerDialog.OnDateSetListener mDateListener;
    StringBuilder Expired_date=new StringBuilder();
    private int request_cord=1;
    private Uri uri_image;
    private DatabaseReference databaseRefe_reciep;
    private StorageReference storageRefer;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPrefe_id_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add);

        Toolbar toolbar=(Toolbar)findViewById(R.id.recipe_add_toolbar);
        setSupportActionBar(toolbar);

        databaseRefe_reciep= FirebaseDatabase.getInstance().getReference("Recipe_product_Add");
        storageRefer= FirebaseStorage.getInstance().getReference("Recipe_product_Add");

        editText_Id=(EditText)findViewById(R.id.recipe_cord);
        editText_name=(EditText)findViewById(R.id.recipe_name);
        editText_Decpt=(EditText)findViewById(R.id.recipe_Descript);
        editText_price=(EditText)findViewById(R.id.reciep_pdPrice);
        editText_discount=(EditText)findViewById(R.id.recipe_discount_id);
        textView_Expired_date=(TextView)findViewById(R.id.reciep_expirt_Date);
        imageView=(ImageView)findViewById(R.id.recipe_image);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite...");
        textView_Expired_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                //textView.setText("");
                DatePickerDialog datePickerDialog =new DatePickerDialog(RecipeAdd.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,mDateListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

                mDateListener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;

                        Expired_date.append(dayOfMonth);
                        Expired_date.append("/"+month);
                        Expired_date.append("/"+year);
                       textView_Expired_date.setText(Expired_date);
                    }
                };
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_image();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.upload_item,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.upload_Id){
            Intent intent=new Intent(getApplicationContext(),Recipe_rateShow.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void choose_image() {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,request_cord);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(request_cord==requestCode && resultCode==RESULT_OK && data!=null){
            uri_image=data.getData();
            Picasso.get().load(uri_image).into(imageView);
        }
    }

    @Override
    protected void onStart() {

        sharedPrefe_id_name=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        if(editText_Id.getText().toString().isEmpty()){
            editText_Id.setError("please Your Product Id");
            editText_Id.requestFocus();

        }else if(editText_name.getText().toString().isEmpty()) {
            editText_name.setError("please Your Product Name ");
            editText_name.requestFocus();
        }else{

            databaseRefe_reciep.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                        Recipe_ModelAdd getItem = dataSnapshot1.getValue(Recipe_ModelAdd.class);
                        getItem.setRecipe_Data_key(dataSnapshot1.getKey());

                        if (getItem.getRecipe_pd_id().contains(editText_name.getText().toString()) || getItem.getRecipe_pd_Name().contains(editText_name.getText().toString().toLowerCase()))
                        {
                            sharedPrefe_id_name.edit().putString("id_key",getItem.getRecipe_pd_id()).apply();
                            sharedPrefe_id_name.edit().putString("name_key",getItem.getRecipe_pd_id()).apply();
          //  sharedPrefe_id_name.edit().putString("image_key",tShirtModer.getTshirt_pdImage()).apply();
                            sharedPrefe_id_name.edit().commit();
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Match Lodding Data Failde:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        super.onStart();
    }

    public void recipe_Data_Add(View view) {

        if(editText_Id.getText().toString().isEmpty()){
            editText_Id.setError("please Your Product Id");
            editText_Id.requestFocus();

        }else if(editText_name.getText().toString().isEmpty()) {
            editText_name.setError("please Your Product Name ");
            editText_name.requestFocus();
        }else if(editText_Decpt.getText().toString().isEmpty()) {
            editText_Decpt.setError("please Your product Description");
            editText_Decpt.requestFocus();
        }else if(editText_price.getText().toString().isEmpty()) {
            editText_price.setError("please Your product price");
            editText_price.requestFocus();
        }else if(editText_discount.getText().toString().isEmpty()) {
            editText_discount.setError("please Your product Discount");
            editText_discount.requestFocus();

        }/*else if(editText_quantity.getText().toString().isEmpty()) {
            editText_quantity.setError("please Your product Quantity");
            editText_quantity.requestFocus();
        }*/else if(Expired_date.toString().isEmpty()) {
            Toast.makeText(this, "Expired Date is Empty", Toast.LENGTH_SHORT).show();
        }else if(imageView.getDrawable()==null) {
            Toast.makeText(this, "Product Image is Empty", Toast.LENGTH_SHORT).show();
        }else{
            String match_id= sharedPrefe_id_name.getString("id_key","Id not Found");
            String match_name =sharedPrefe_id_name.getString("name_key","Name not Found");
            // String match_image= sharedPrefe_id_name.getString("image_key","Image not Found");

            if(match_id.contains(editText_Id.getText().toString())  || match_name.contains(editText_name.getText().toString().toLowerCase())){
                if(match_id.contains(editText_Id.getText().toString()) ){
                    Toast.makeText(RecipeAdd.this, "Your Product Id  Alrady Add ..", Toast.LENGTH_LONG).show();
                } if(match_name.contains(editText_name.getText().toString().toLowerCase())){
                    Toast.makeText(RecipeAdd.this, "Your Product Name  Alrady Add ..", Toast.LENGTH_LONG).show();
                }

            }else{
                progressDialog.show();
                final String pd_id= editText_Id.getText().toString();
                final String pd_name= editText_name.getText().toString().trim().toLowerCase();
                final String pd_Descpt= editText_Decpt.getText().toString();
                final int pd_price= Integer.parseInt(editText_price.getText().toString());
               // final int pd_stock=Integer.parseInt(editText_quantity.getText().toString());
                final int pd_discount=Integer.parseInt(editText_discount.getText().toString());

                StorageReference storageReferenImage_exten=storageRefer.child(System.currentTimeMillis()+".png");


                storageReferenImage_exten.putFile(uri_image)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                Uri uriDowanlod =uriTask.getResult();

                  Recipe_ModelAdd addItem=new Recipe_ModelAdd(pd_id,pd_name,pd_Descpt,Expired_date.toString(),pd_price,pd_discount,uriDowanlod.toString());
                                String myKey=databaseRefe_reciep.push().getKey();
                                databaseRefe_reciep.child(myKey).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RecipeAdd.this, "Your Product Add SuccessFull..", Toast.LENGTH_SHORT).show();
                                        editText_price.setText("");
                                        editText_name.setText("");
                                        editText_Decpt.setText("");
                                        editText_Id.setText("");
                                      //  editText_quantity.setText("");
                                        editText_discount.setText(""+0);
                                        textView_Expired_date.setText("");
                                        textView_Expired_date.setText("Expeirt Date");
                                        progressDialog.dismiss();
                                        Expired_date.append("");
                                        imageView.setImageResource(0);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(RecipeAdd.this, "Your Image Upload Failde", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("please wait..." + (int) percent + "%");
                    }
                });
            }


        }



    }
}