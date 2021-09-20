package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class Laptop_keybordAdd extends AppCompatActivity {
  private ImageView imageView;
  private static  int  request_cord=1;
  private Uri uri_image;
  private DatabaseReference databaseRefe_laptop;
  private StorageReference storageReference;
  private EditText editText_lapCord,editText_lapName,editText_description,editText_price,
          editText_stock,editText_discount;
  private TextView textView_currnteDate;
  private   String currentDate;
  private ProgressDialog progressDialog;
  private SharedPreferences sharedPrefe_id_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_keybord_add);

        Toolbar toolbar=(Toolbar)findViewById(R.id.laptop_add_toolbar);
        setSupportActionBar(toolbar);

        databaseRefe_laptop= FirebaseDatabase.getInstance().getReference("Laptop_product_Add");
        storageReference= FirebaseStorage.getInstance().getReference("Laptop_product_Add");

        imageView=(ImageView)findViewById(R.id.laptop_keybord_image);

        editText_lapCord=(EditText)findViewById(R.id.laptop_cord);
        editText_lapName=(EditText)findViewById(R.id.laptop_name);
        editText_description=(EditText)findViewById(R.id.laptop_Descript);
        editText_price=(EditText)findViewById(R.id.laptop_pdPrice);
        editText_discount=(EditText)findViewById(R.id.pd_discount_id);
         textView_currnteDate=(TextView)findViewById(R.id.lap_pd_date);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite Data Uploding");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             file_Choose();
            }
        });

        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYY-MM-dd");
        currentDate=simpleDateFormat.format(date.getTime());
        textView_currnteDate.setText(currentDate);
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
            Intent intent=new Intent(getApplicationContext(),laptop_all_rateShow.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void file_Choose(){
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
        super.onStart();

        sharedPrefe_id_name=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
        if(editText_lapCord.getText().toString().isEmpty()){
            editText_lapCord.setError("plase Your Product Cord...");
            editText_lapCord.requestFocus();

            return;
        }else  if(editText_lapName.getText().toString().isEmpty()){
            editText_lapName.setError("plase Your Product Name...");
            editText_lapName.requestFocus();


        }else{
            databaseRefe_laptop.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                        laptop_prodcut_Module getItem = dataSnapshot1.getValue(laptop_prodcut_Module.class);
                        getItem.setLap_data_Key(dataSnapshot1.getKey());

                        if (getItem.getLap_cord().contains(editText_lapCord.getText().toString()) || getItem.getBrand_name().contains(editText_lapName.getText().toString().toLowerCase()))
                        {
                            sharedPrefe_id_name.edit().putString("id_key",getItem.getLap_cord()).commit();
                            sharedPrefe_id_name.edit().putString("name_key",getItem.getBrand_name()).commit();
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



    }

    public void laptop_Data_Add(View view) {

        if(editText_lapCord.getText().toString().isEmpty()){
            editText_lapCord.setError("plase Your Product Cord...");
            editText_lapCord.requestFocus();

            return;
        }else  if(editText_lapName.getText().toString().isEmpty()){
            editText_lapName.setError("plase Your Product Name...");
            editText_lapName.requestFocus();


        }else if(editText_lapName.getText().toString().isEmpty()){
            editText_lapName.setError("plase Your Product Desription...");
            editText_lapName.requestFocus();

        }else if(editText_price.getText().toString().isEmpty()){
            editText_price.setError("plase Your Product Price ...");
            editText_price.requestFocus();

        }/*else if(editText_stock.getText().toString().isEmpty()){
            editText_stock.setError("plase Your Stock....");
            editText_stock.requestFocus();
        }*/else if(editText_discount.getText().toString().isEmpty()){
            editText_discount.setError("plase Your Discount....");
            editText_discount.requestFocus();
        }else if(imageView.getDrawable()==null){
         Toast.makeText(this, "Please Your Product image..", Toast.LENGTH_SHORT).show();
            imageView.requestFocus();
        }else {

            String match_id= sharedPrefe_id_name.getString("id_key","Id not Found");
            String match_name =sharedPrefe_id_name.getString("name_key","Name not Found");
            // String match_image= sharedPrefe_id_name.getString("image_key","Image not Found");

            if(match_id.contains(editText_lapCord.getText().toString())  || match_name.contains(editText_lapName.getText().toString().toLowerCase())){
                if(match_id.contains(editText_lapCord.getText().toString()) ){
                    Toast.makeText(Laptop_keybordAdd.this, "Your Product Id  Alrady Add ..", Toast.LENGTH_LONG).show();
                } if(match_name.contains(editText_lapName.getText().toString().toLowerCase())){
                    Toast.makeText(Laptop_keybordAdd.this, "Your Product Name  Alrady Add ..", Toast.LENGTH_LONG).show();
                }
              //  Toast.makeText(getApplicationContext(), "Your Product Id Name Alrady Add ..", Toast.LENGTH_SHORT).show();
            }else{
                progressDialog.show();

                final String pd_id= editText_lapCord.getText().toString().trim();
                final String pd_name= editText_lapName.getText().toString().trim().toLowerCase();
                final int  pd_price= Integer.parseInt(editText_price.getText().toString().trim());
                final String pd_Desrcip= editText_description.getText().toString().trim();
                //final int pd_stock= Integer.parseInt(editText_stock.getText().toString().trim());
                final int pd_discount= Integer.parseInt(editText_discount.getText().toString());

                StorageReference storageRefer_Url = storageReference.child(System.currentTimeMillis() + ".png");

                storageRefer_Url.putFile(uri_image)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                Uri uri_dwanload = uriTask.getResult();

                                laptop_prodcut_Module add_item = new laptop_prodcut_Module(pd_id, pd_name, pd_Desrcip, pd_price, currentDate,pd_discount,uri_dwanload.toString());
                                String myKey = databaseRefe_laptop.push().getKey();

                                databaseRefe_laptop.child(myKey).setValue(add_item).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Laptop_keybordAdd.this, "Your Data Add SuccessFull..", Toast.LENGTH_SHORT).show();
                                        editText_description.setText("");
                                        editText_lapCord.setText("");
                                        editText_lapName.setText("");
                                        editText_discount.setText("");
                                        editText_price.setText("");
                                        imageView.setImageResource(0);
                                        progressDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Laptop_keybordAdd.this, "Your Data Add Failde..", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(Laptop_keybordAdd.this, "Your Image Upload Failde..", Toast.LENGTH_SHORT).show();
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