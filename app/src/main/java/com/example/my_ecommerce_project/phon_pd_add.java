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

public class phon_pd_add extends AppCompatActivity {
    private ImageView imageView;
    private EditText editText_pdname,editText_pdPrice,editText_pd_id,editText_Decpt,editText_Stock,editText_discount;
    private DatabaseReference databaseRefe_android_phon;
    private StorageReference storageReference;
    private TextView textView_date;
    private Uri uri_image;
    private int request_cord=1;
    private String currentData;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPrefe_id_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phon_pd_add);

        Toolbar toolbar=(Toolbar)findViewById(R.id.cap_add_toolbar);
        setSupportActionBar(toolbar);

        databaseRefe_android_phon= FirebaseDatabase.getInstance().getReference("Android_phon_product_Add");
        storageReference= FirebaseStorage.getInstance().getReference("Android_phon_product_Add");

        editText_pd_id=(EditText)findViewById(R.id.phon_pdcord);
        editText_pdname=(EditText)findViewById(R.id.phon_pdName);
        editText_Decpt=(EditText)findViewById(R.id.phon_pdDescr);
        editText_discount=(EditText)findViewById(R.id.phon_pddiscount);
        editText_pdPrice=(EditText)findViewById(R.id.phon_pdPrice);
        imageView=(ImageView)findViewById(R.id.phon_Product_Image);
        textView_date=(TextView)findViewById(R.id.phon_text_data);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wite..");
        progressDialog.setCancelable(false);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_image();
            }
        });
        Date date=new Date();
        SimpleDateFormat sm=new SimpleDateFormat("YYYY-MM-dd");
        currentData= sm.format(date.getTime());
        textView_date.setText("Date:"+currentData);////currentData
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
           Intent intent=new Intent(getApplicationContext(),phon_rate_Show.class);
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
        super.onStart();
        sharedPrefe_id_name=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);

        if(editText_pd_id.getText().toString().isEmpty()){
            editText_pd_id.setError("please Your Product Id");
            editText_pd_id.requestFocus();

        }else if(editText_pdname.getText().toString().isEmpty()) {
            editText_pdname.setError("please Your Product Name ");
            editText_pdname.requestFocus();
        }else{
            databaseRefe_android_phon.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                        phon_Model getItem = dataSnapshot1.getValue(phon_Model.class);
                        getItem.setPhon_pdKey(dataSnapshot1.getKey());

                        if (getItem.getPhon_pdId().contains(editText_pd_id.getText().toString()) || getItem.getPhon_pdName().contains(editText_pdname.getText().toString().toLowerCase()))
                        {
                            sharedPrefe_id_name.edit().putString("id_key",getItem.getPhon_pdId()).commit();
                            sharedPrefe_id_name.edit().putString("name_key", getItem.getPhon_pdName()).commit();
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

    public void Save_Data(View view) {

        final String pd_id=editText_pd_id.getText().toString().trim();
        final String pd_name=editText_pdname.getText().toString().trim().toLowerCase();
        final String pd_Decrpt=editText_Decpt.getText().toString().trim();
        final int pd_price=Integer.parseInt(editText_pdPrice.getText().toString());
        final int pd_quantity=Integer.parseInt(editText_Stock.getText().toString());
        final int pd_discount=Integer.parseInt(editText_discount.getText().toString());


        if(editText_pd_id.getText().toString().isEmpty()){
            editText_pd_id.setError("please Your Product Id");
            editText_pd_id.requestFocus();

        }else if(editText_pdname.getText().toString().isEmpty()) {
            editText_pdname.setError("please Your Product Name ");
            editText_pdname.requestFocus();
        }else if(editText_Decpt.getText().toString().isEmpty()) {
            editText_Decpt.setError("please Your product Description");
            editText_Decpt.requestFocus();

        }/*else if(editText_Stock.getText().toString().isEmpty()) {
            editText_Stock.setError("please Your product Stock");
            editText_Stock.requestFocus();

        }*/else if(editText_discount.getText().toString().isEmpty()) {
            editText_discount.setError("please Your Discount");
            editText_discount.requestFocus();

        } else if(editText_pdPrice.getText().toString().isEmpty()) {
            editText_pdPrice.setError("please Your product Price");
            editText_pdPrice.requestFocus();

        }else{

            String match_id= sharedPrefe_id_name.getString("id_key","Id not Found");
            String match_name =sharedPrefe_id_name.getString("name_key","Name not Found");
            if(match_id.contains(editText_pd_id.getText().toString())  || match_name.contains(editText_pdname.getText().toString().toLowerCase())){
                if(match_id.contains(editText_pd_id.getText().toString()) ){
                    Toast.makeText(phon_pd_add.this, "Your Product Id  Alrady Add ..", Toast.LENGTH_LONG).show();
                } if(match_name.contains(editText_pdname.getText().toString().toLowerCase())){
                    Toast.makeText(phon_pd_add.this, "Your Product Name  Alrady Add ..", Toast.LENGTH_LONG).show();
                }
               // Toast.makeText(getApplicationContext(), "Your Product Id Name Alrady Add ..", Toast.LENGTH_SHORT).show();
            }else{
                progressDialog.show();
                StorageReference storageRefereImage=storageReference.child(System.currentTimeMillis()+".png");

                storageRefereImage.putFile(uri_image)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                Uri uriDowanlod =uriTask.getResult();

   phon_Model add_Item=new phon_Model(pd_id,pd_name,pd_Decrpt,uriDowanlod.toString(),currentData,pd_price,pd_discount);
                                String mykey= databaseRefe_android_phon.push().getKey();
                                databaseRefe_android_phon.child(mykey).setValue(add_Item).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(phon_pd_add.this, "Your product Add SuccessFull..", Toast.LENGTH_SHORT).show();
                                        editText_pd_id.setText("");
                                        editText_pdname.setText("");
                                        editText_Decpt.setText("");
                                        editText_pdPrice.setText("");
                                        editText_discount.setText("");
                                        imageView.setImageResource(0);
                                        progressDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(phon_pd_add.this, "Your product Add Failde..", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(phon_pd_add.this, "Your Image UpLoad Failde...", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("please wait..."+(int) percent +"%");
                    }
                });
            }

        }
    }
}