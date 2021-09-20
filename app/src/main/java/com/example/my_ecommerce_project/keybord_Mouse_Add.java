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

public class keybord_Mouse_Add extends AppCompatActivity {
private ImageView imageView;
private EditText editText_name,editText_pd_id,editText_pd_price,editText_pd_stock,editText_pd_discount,editText_Decept;
private TextView textView_date;
private int request_cord=1;
private Uri uri_image;
private String currentData;
private DatabaseReference databaseRef_add;
private StorageReference storageReference;
private ProgressDialog progressDialog;
private SharedPreferences sharedPrefe_id_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keybord__mouse__add);
    databaseRef_add= FirebaseDatabase.getInstance().getReference("Keybord_Mouse_Add");
    storageReference= FirebaseStorage.getInstance().getReference("Keybord_Mouse_Add");

        Toolbar toolbar=(Toolbar)findViewById(R.id.cap_add_toolbar);
        setSupportActionBar(toolbar);

        editText_pd_id=(EditText)findViewById(R.id.key_mouse_pdcord);
        editText_name=(EditText)findViewById(R.id.key_mouse__pdName);
        editText_Decept=(EditText)findViewById(R.id.key_mouse__pdDescr);
        editText_pd_discount=(EditText)findViewById(R.id.key_mouse_pdcord);
        editText_pd_price=(EditText)findViewById(R.id.key_mouse__pdPrice);
        editText_pd_stock=(EditText)findViewById(R.id.key_mouse__pdQuantity);
        editText_pd_discount=(EditText)findViewById(R.id.key_mouse__pd_discount);
       textView_date=(TextView)findViewById(R.id.key_mouse_text_data);
       imageView=(ImageView)findViewById(R.id.key_mouse_Image);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wite....");
        progressDialog.setCancelable(false);
        Date date=new Date();
        SimpleDateFormat sm=new SimpleDateFormat("YYYY-MM-dd");
        currentData= sm.format(date.getTime());
        textView_date.setText("Date:"+currentData);////currentData

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
            Intent intent=new Intent(getApplicationContext(),keybord_mouse_rate_show.class);
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

        sharedPrefe_id_name=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project",Context.MODE_PRIVATE);

        if(editText_pd_id.getText().toString().isEmpty()){
            editText_pd_id.setError("please Your Product Id");
            editText_pd_id.requestFocus();

        }else if(editText_name.getText().toString().isEmpty()) {
            editText_name.setError("please Your Product Name ");
            editText_name.requestFocus();
        }else{
            databaseRef_add.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                        keybord_mouse_Model getItem = dataSnapshot1.getValue(keybord_mouse_Model.class);
                        getItem.setKeyMouse_Data_Key(dataSnapshot1.getKey());

                        if (getItem.getKeyMouse_id().contains(editText_pd_id.getText().toString()) || getItem.getKeyMouse_name().contains(editText_name.getText().toString().toLowerCase()))
                        {
                            sharedPrefe_id_name.edit().putString("id_key",getItem.getKeyMouse_id()).commit();
                            sharedPrefe_id_name.edit().putString("name_key",getItem.getKeyMouse_name()).commit();
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

        if(editText_pd_id.getText().toString().isEmpty()){
            editText_pd_id.setError("please Your Product Id");
            editText_pd_id.requestFocus();

        }else if(editText_name.getText().toString().isEmpty()) {
            editText_name.setError("please Your Product Name ");
            editText_name.requestFocus();
        }else if(editText_pd_price.getText().toString().isEmpty()) {
            editText_pd_price.setError("please Your product Price");
            editText_pd_price.requestFocus();

        }/*else if(editText_pd_stock.getText().toString().isEmpty()) {
            editText_pd_stock.setError("please Your product Stock");
            editText_pd_stock.requestFocus();

        }*/else if(editText_pd_discount.getText().toString().isEmpty()) {
            editText_pd_discount.setError("please Your product Discount");
            editText_pd_discount.requestFocus();

        }else{

            String match_id= sharedPrefe_id_name.getString("id_key","Id not Found");
            String match_name =sharedPrefe_id_name.getString("name_key","Name not Found");
            // String match_image= sharedPrefe_id_name.getString("image_key","Image not Found");

            if(match_id.contains(editText_pd_id.getText().toString())  || match_name.contains(editText_name.getText().toString().toLowerCase())){
                if(match_id.contains(editText_pd_id.getText().toString()) ){
                    Toast.makeText(keybord_Mouse_Add.this, "Your Product Id  Alrady Add ..", Toast.LENGTH_LONG).show();
                } if(match_name.contains(editText_name.getText().toString().toLowerCase())){
                    Toast.makeText(keybord_Mouse_Add.this, "Your Product Name  Alrady Add ..", Toast.LENGTH_LONG).show();
                }

            }else{
                progressDialog.show();

                final String pd_cord=editText_pd_id.getText().toString();
                final String pd_name=editText_name.getText().toString().trim().toLowerCase();
                final String pd_Descpt=editText_Decept.getText().toString();
                final int pd_Price=Integer.parseInt(editText_pd_price.getText().toString());
               // final int pd_Stock=Integer.parseInt(editText_pd_stock.getText().toString());
                final int pd_Discount=Integer.parseInt(editText_pd_discount.getText().toString());

                StorageReference storageRef_exten=storageReference.child(System.currentTimeMillis()+".png");


                storageRef_exten.putFile(uri_image)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete());
                                Uri uriDowanlod=uriTask.getResult();
 keybord_mouse_Model addItem=new keybord_mouse_Model(pd_cord,pd_name,pd_Descpt,uriDowanlod.toString(),pd_Price,pd_Discount,currentData);
                                String mykey= databaseRef_add.push().getKey();
                                databaseRef_add.child(mykey).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(keybord_Mouse_Add.this, "Your product Add SuccessFull..", Toast.LENGTH_SHORT).show();
                                        editText_pd_id.setText("");
                                        editText_name.setText("");
                                        editText_Decept.setText("");
                                        editText_pd_price.setText("");
                                        editText_pd_discount.setText("");
                                        imageView.setImageResource(0);
                                 //  editText_pd_id.requestFocus();
                                        progressDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(keybord_Mouse_Add.this, "Your product Add Failde..", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(keybord_Mouse_Add.this, "Your product Image Failde..", Toast.LENGTH_SHORT).show();
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