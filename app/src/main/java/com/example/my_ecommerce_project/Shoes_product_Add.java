package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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

public class Shoes_product_Add extends AppCompatActivity {
private EditText editText_pdId, editText_pdName,editText_pdPrice, editText_Decpt,editText_stock,
        editText_pdSize,editText_discount;
private TextView textView_currentDate;
private ImageView imageView;
private String current_Date;
private DatabaseReference databaseRef_shose;
private StorageReference storageReferen_shose;
private int request_cord=1;
private Uri uri_image;
private ProgressDialog progressDialog;
private SharedPreferences sharedPrefe_id_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes_product__add);

        Toolbar toolbar=(Toolbar)findViewById(R.id.shose_add_toolbar);
        setSupportActionBar(toolbar);

databaseRef_shose= FirebaseDatabase.getInstance().getReference("Shose_product_Add");
storageReferen_shose= FirebaseStorage.getInstance().getReference("Shose_product_Add");

        editText_pdId=(EditText)findViewById(R.id.shoes_pdcord);
        editText_pdName=(EditText)findViewById(R.id.shoes_pdName);
        editText_Decpt=(EditText)findViewById(R.id.shoes_pdDescr);
        editText_pdPrice=(EditText)findViewById(R.id.shose_pdPrice);
       // editText_stock=(EditText)findViewById(R.id.shose_pdQuantity);
        editText_discount=(EditText)findViewById(R.id.shose_pdDiscount);
        editText_pdSize=(EditText)findViewById(R.id.shose_pdSize);
        imageView=(ImageView)findViewById(R.id.shoes_PImage);

        textView_currentDate=(TextView)findViewById(R.id.shoes_text_data);
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYY-MM-dd");
         current_Date= simpleDateFormat.format(date.getTime());
         textView_currentDate.setText("Date:"+current_Date);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_Choose();
            }
        });

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wite..");
        progressDialog.setCancelable(false);
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
            Intent intent=new Intent(getApplicationContext(),shose_rate_Show.class);
            finish();
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

         if(editText_pdId.getText().toString().isEmpty()){
            editText_pdId.setError("please Your Product Cord...");
            editText_pdId.requestFocus();
            //progressDialog.dismiss();

        }else if(editText_pdName.getText().toString().isEmpty()){
           editText_pdName.setError("please Your Product Name...");
            editText_pdName.requestFocus();

        }else{
            try {
                sharedPrefe_id_name=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
                sharedPrefe_id_name.edit().clear().commit();
                databaseRef_shose.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                            shose_pdModel getItem = dataSnapshot1.getValue(shose_pdModel.class);
                            getItem.setShose_product_key(dataSnapshot1.getKey());

                            if(getItem.getShose_pd_id().contains(editText_pdId.getText().toString()) || getItem.getShose_pd_name().contains(editText_pdName.getText().toString().toLowerCase()))
                            {

                                sharedPrefe_id_name.edit().putString("id_key",getItem.getShose_pd_id()).commit();
                                sharedPrefe_id_name.edit().putString("name_key",getItem.getShose_pd_name()).commit();
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
            }catch (Exception exception){
                Toast.makeText(this, "Exception:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void Save_Data(View view) {
        if(editText_pdId.getText().toString().isEmpty()){
            editText_pdId.setError("please Your Product Cord...");
            editText_pdId.requestFocus();
            //progressDialog.dismiss();

        }else if(editText_pdName.getText().toString().isEmpty()){
            editText_pdName.setError("please Your Product Name...");
            editText_pdName.requestFocus();

        }else if(editText_Decpt.getText().toString().isEmpty()){
            editText_Decpt.setError("please Your Product Description...");
            editText_Decpt.requestFocus();

        }else if(editText_pdPrice.getText().toString().isEmpty()){
            editText_pdPrice.setError("please Your Product price...");
            editText_pdPrice.requestFocus();

        }/*else if(editText_stock.getText().toString().isEmpty()){
            editText_stock.setError("please Your Product Stock...");
            editText_stock.requestFocus();

        }*/else if(editText_discount.getText().toString().isEmpty()){
            editText_discount.setError("please Your Product Discount...");
            editText_discount.requestFocus();

        }else if(editText_pdSize.getText().toString().isEmpty()){
            editText_pdSize.setError("please Your Product size...");
            editText_pdSize.requestFocus();
        } else{

            String match_id= sharedPrefe_id_name.getString("id_key","Id not Found");
            String match_name =sharedPrefe_id_name.getString("name_key","Name not Found");
            // String match_image= sharedPrefe_id_name.getString("image_key","Image not Found");

            if(match_id.contains(editText_pdId.getText().toString())  || match_name.contains(editText_pdName.getText().toString().toLowerCase())){
                if(match_id.contains(editText_pdId.getText().toString()) ){
                    Toast.makeText(Shoes_product_Add.this, "Your Product Id  Alrady Add ..", Toast.LENGTH_LONG).show();
                } if(match_name.contains(editText_pdName.getText().toString().toLowerCase())){
                    Toast.makeText(Shoes_product_Add.this, "Your Product Name  Alrady Add ..", Toast.LENGTH_LONG).show();
                }
              //  Toast.makeText(getApplicationContext(), "Your Product Id Name Alrady Add ..", Toast.LENGTH_SHORT).show();
            }else{

                progressDialog.show();
                final String pd_Id=editText_pdId.getText().toString();
                final String pd_Name=editText_pdName.getText().toString().toLowerCase().toLowerCase();
                final int pd_price=Integer.parseInt(editText_pdPrice.getText().toString());
                final String pd_Decpt=editText_Decpt.getText().toString();
              //  final int  pd_stock=Integer.parseInt(editText_stock.getText().toString());
                final String pd_size=editText_pdSize.getText().toString();
                final int  pd_discount=Integer.parseInt(editText_discount.getText().toString());


                StorageReference storageRef_imageExten=storageReferen_shose.child(System.currentTimeMillis()+".png");

                storageRef_imageExten.putFile(uri_image)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful());
                                Uri uriDowanload=uriTask.getResult();

    shose_pdModel addItem=new shose_pdModel(pd_Id,pd_Name,pd_Decpt,pd_size,uriDowanload.toString(),pd_price,pd_discount,current_Date);
                                String myKey= databaseRef_shose.push().getKey();
                                databaseRef_shose.child(myKey).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Shoes_product_Add.this, "Your Prdouct Add SuccesFull..", Toast.LENGTH_SHORT).show();
                                        editText_pdPrice.setText("");
                                        editText_pdName.setText("");
                                        editText_discount.setText("");
                                        editText_Decpt.setText("");
                                        editText_pdId.setText("");
                                        editText_pdSize.setText("");
                                        progressDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Shoes_product_Add.this, "Your Product Add Failde.."+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(Shoes_product_Add.this, "Error:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
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