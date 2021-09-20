package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
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

public class cap_prductAdd extends AppCompatActivity {
  private EditText editText_capId,editText_capName,editText_cap_Decrption,editText_stock,editText_price;
  private ImageView imageView_cap;
  private TextView textView_date;
   private int request_cord=1;
    private Uri uri_image;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String currentData;
    private ProgressDialog progressDialog;
    private CheckBox checkBox_1,checkBox_2,checkBox_3,checkBox_4;
    private SharedPreferences sharedPrefe_id_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_prduct_add);
        databaseReference= FirebaseDatabase.getInstance().getReference("Cap_product_Add");
        storageReference= FirebaseStorage.getInstance().getReference("Cap_product_Add");

        Toolbar toolbar=(Toolbar)findViewById(R.id.cap_add_toolbar);
        setSupportActionBar(toolbar);

        editText_capId=(EditText)findViewById(R.id.cap_pdcord);
        editText_capName=(EditText)findViewById(R.id.cap_pdName);
        editText_cap_Decrption=(EditText)findViewById(R.id.cap_pdDescr);
       // editText_stock=(EditText)findViewById(R.id.cap_pdQuantity);
        editText_price=(EditText)findViewById(R.id.cap_pdPrice);
        textView_date=(TextView)findViewById(R.id.captext_data);

        checkBox_1=(CheckBox)findViewById(R.id.cap_check_1);
        checkBox_2=(CheckBox)findViewById(R.id.cap_check_2);
        checkBox_3=(CheckBox)findViewById(R.id.cap_check_3);
        checkBox_4=(CheckBox)findViewById(R.id.cap_check_4);

      progressDialog=new ProgressDialog(this);
      progressDialog.setMessage("Please Wite..");

        Date date=new Date();
        SimpleDateFormat sm=new SimpleDateFormat("YYYY-MM-dd");
        currentData= sm.format(date.getTime());
        textView_date.setText("Date:"+currentData);////currentData

        imageView_cap=(ImageView)findViewById(R.id.cap_PImage);

        imageView_cap.setOnClickListener(new View.OnClickListener() {
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
        Intent intent=new Intent(getApplicationContext(),All_CapShow_Rate.class);
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
            Picasso.get().load(uri_image).into(imageView_cap);
        }
    }

    @Override
    protected void onStart() {
        sharedPrefe_id_name=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);

        if(editText_capId.getText().toString().isEmpty()){
            editText_capId.setError("please Your Product Id");
            editText_capId.requestFocus();

        }else if(editText_capName.getText().toString().isEmpty()) {
            editText_capName.setError("please Your Product Name ");
            editText_capName.requestFocus();
        }else{
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                        Cap_product_Model getItem = dataSnapshot1.getValue(Cap_product_Model.class);
                        getItem.setCap_product_Key(dataSnapshot1.getKey());

                        if (getItem.getCap_pdCord().contains(editText_capId.getText().toString()) || getItem.getCap_pdName().contains(editText_capName.getText().toString().toLowerCase()))
                        {
                            sharedPrefe_id_name.edit().putString("id_key",getItem.getCap_pdCord()).commit();
                            sharedPrefe_id_name.edit().putString("name_key",getItem.getCap_pdName()).commit();
                            //  sharedPrefe_id_name.edit().putString("image_key",tShirtModer.getTshirt_pdImage()).apply();
                            sharedPrefe_id_name.edit().commit();
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(cap_prductAdd.this, "Match Lodding Data Failde:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        super.onStart();
    }

    public void Save_Data(View view) {

  /*      else if(editText_stock.getText().toString().isEmpty()) {
            editText_stock.setError("please Your product Stock");
            editText_stock.requestFocus();

        }
       */

        if(editText_capId.getText().toString().isEmpty()){
            editText_capId.setError("please Your Product Id");
            editText_capId.requestFocus();

        }else if(editText_capName.getText().toString().isEmpty()) {
            editText_capName.setError("please Your Product Name ");
            editText_capName.requestFocus();
        }else if(editText_cap_Decrption.getText().toString().isEmpty()) {
            editText_cap_Decrption.setError("please Your product Description");
            editText_cap_Decrption.requestFocus();

        }else if(editText_price.getText().toString().isEmpty()) {
            editText_price.setError("please Your product price");
            editText_price.requestFocus();

        }else if(imageView_cap.getDrawable()==null) {
      Toast.makeText(this, "Please Your Image..", Toast.LENGTH_SHORT).show();
            editText_price.requestFocus();
        }
        else if(checkBox_1.isChecked()==false || checkBox_2.isChecked()==false || checkBox_3.isChecked()==false || checkBox_4.isChecked()==false){
            Toast.makeText(this, "please Your Product Size Select", Toast.LENGTH_SHORT).show();
        }else {

            String match_id= sharedPrefe_id_name.getString("id_key","Id not Found");
            String match_name =sharedPrefe_id_name.getString("name_key","Name not Found");
            // String match_image= sharedPrefe_id_name.getString("image_key","Image not Found");
            if(match_id.contains(editText_capId.getText().toString())  || match_name.contains(editText_capName.getText().toString().toLowerCase())){

                if(match_id.contains(editText_capId.getText().toString()) ){
             Toast.makeText(cap_prductAdd.this, "Your Product Id  Alrady Add ..", Toast.LENGTH_LONG).show();
                } if(match_name.contains(editText_capName.getText().toString().toLowerCase())){
               Toast.makeText(cap_prductAdd.this, "Your Product Name  Alrady Add ..", Toast.LENGTH_LONG).show();
                }
             //   Toast.makeText(cap_prductAdd.this, "Your Product Id Name Alrady Add ..", Toast.LENGTH_SHORT).show();
            }else{

            final StringBuffer prodcut_size=new StringBuffer();

            if(checkBox_1.isChecked()){
                prodcut_size.append(checkBox_1.getText()+",");
            } if(checkBox_2.isChecked()){
                prodcut_size.append(checkBox_2.getText()+",");
            } if(checkBox_3.isChecked()){
                prodcut_size.append(checkBox_3.getText()+",");
            } if(checkBox_4.isChecked()){
                prodcut_size.append(checkBox_4.getText()+",");
            }
        progressDialog.show();
            final String pd_cord = editText_capId.getText().toString().trim();
            final String pd_name = editText_capName.getText().toString().trim();
            final String pd_decrpt = editText_cap_Decrption.getText().toString().trim();
            final int pd_stock = Integer.parseInt(editText_stock.getText().toString());
            final int pd_price = Integer.parseInt(editText_price.getText().toString());
            // final String pd_date = textView_date.getText().toString().trim();

            StorageReference storageRefe_imageExet = storageReference.child(System.currentTimeMillis() + ".png");

            storageRefe_imageExet.putFile(uri_image)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isComplete()) ;
                            Uri uriDowanlod =uriTask.getResult();

                            Cap_product_Model addItem = new Cap_product_Model(pd_cord, pd_name, pd_decrpt, pd_price, currentData, uriDowanlod.toString(),prodcut_size.toString());
                            String key = databaseReference.push().getKey();

                            databaseReference.child(key).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(cap_prductAdd.this, "Your Prodcut  Add SuccessFull", Toast.LENGTH_SHORT).show();
                                    editText_capId.setText("");
                                    editText_capName.setText("");
                                    editText_cap_Decrption.setText("");
                                  //  editText_stock.setText("");
                                    editText_price.setText("");
                                    editText_capId.requestFocus();
                                    imageView_cap.setImageResource(0);
                                    progressDialog.dismiss();
                                    checkBox_1.setChecked(false);
                                    checkBox_2.setChecked(false);
                                    checkBox_3.setChecked(false);
                                    checkBox_4.setChecked(false);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                             progressDialog.dismiss();
                           Toast.makeText(cap_prductAdd.this, "Your Prodcut Add Failde" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(cap_prductAdd.this, "Your Prodcut Image Upload Failde" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    float percent = (100*taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("please wait..." + (int) percent + "%");
                }
            });
        }
        }
    }
}