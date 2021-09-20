package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
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
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Femal_DressAdd extends AppCompatActivity {
   private EditText editText_pdCord, editText_pdName,editText_pdDrec,editText_pd_quantity,editText_pdPriece;
   private ImageView pd_imageView;
   private static  int request_cord=1;
   private Uri uri_image;
   private TextView textView_currntDate;
   private DatabaseReference databaseReference;
   private StorageReference storageRef_product_Image;
    private ProgressDialog progressDialog;
    private  String currentData;
    private int pd_quan,Prod_price;
    private CheckBox checkBox_1,checkBox_2,checkBox_3,checkBox_4;
    private SharedPreferences sharedPrefe_id_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_femal__dress_add);
        Toolbar toolbar=(Toolbar)findViewById(R.id.cap_add_toolbar);
        setSupportActionBar(toolbar);

        databaseReference= FirebaseDatabase.getInstance().getReference("Femal_productAdd");
        storageRef_product_Image= FirebaseStorage.getInstance().getReference("Femal_productAdd");

        editText_pdName=(EditText)findViewById(R.id.la_pdName);
        editText_pdDrec=(EditText)findViewById(R.id.la_pdDescr);
      //  editText_pd_quantity=(EditText)findViewById(R.id.la_pdQuantity);
        editText_pdPriece=(EditText)findViewById(R.id.la_pdPrice);
        textView_currntDate=(TextView)findViewById(R.id.Ptext_data);
        editText_pdCord=(EditText) findViewById(R.id.la_pdcord);

        checkBox_1=(CheckBox)findViewById(R.id.femalcheck_1);
        checkBox_2=(CheckBox)findViewById(R.id.femalcheck_2);
        checkBox_3=(CheckBox)findViewById(R.id.femalcheck_3);
        checkBox_4=(CheckBox)findViewById(R.id.femalcheck_4);

        Date date=new Date();
        SimpleDateFormat sm=new SimpleDateFormat("YYYY-MM-dd");
          currentData= sm.format(date.getTime());
         textView_currntDate.setText("Date:"+currentData);


        pd_imageView=(ImageView)findViewById(R.id.ladess_PImage);
        pd_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_Choose();
            }
        });

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Lodding....");
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
            Intent intent=new Intent(getApplicationContext(),Femal_product_rate_Show.class);
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
            Picasso.get().load(uri_image).into(pd_imageView);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(editText_pdCord.getText().toString().isEmpty()){
            editText_pdCord.setError("plase Your Product Cord...");
            editText_pdCord.requestFocus();
            progressDialog.dismiss();
            return;
        }else  if(editText_pdName.getText().toString().isEmpty()){
            editText_pdName.setError("plase Your Product Name...");
            editText_pdName.requestFocus();
            progressDialog.dismiss();

        }else{
            sharedPrefe_id_name=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // T_shirt_Moder tShirtModer=null;
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                        femal_product_Model getItem = dataSnapshot1.getValue(femal_product_Model.class);
                        getItem.setData_key(dataSnapshot1.getKey());

                        ///Toast.makeText(Femal_DressAdd.this, "Id:"+tShirtModer.getTshirt_pdCord(), Toast.LENGTH_SHORT).show();
                        if(getItem.getPd_Cord().contains(editText_pdCord.getText().toString()) || getItem.getProduct_name().contains(editText_pdName.getText().toString().toLowerCase()))
                        {
                            sharedPrefe_id_name.edit().putString("id_key",getItem.getPd_Cord()).apply();
                            sharedPrefe_id_name.edit().putString("name_key",getItem.getProduct_name()).apply();
                            //  sharedPrefe_id_name.edit().putString("image_key",tShirtModer.getTshirt_pdImage()).apply();
                            sharedPrefe_id_name.edit().commit();
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(Femal_DressAdd.this, "Match Lodding Data Failde:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void Save_Data(View view) {
        final String pd_cord= editText_pdCord.getText().toString();
        final String pd_Name= editText_pdName.getText().toString().toLowerCase();

    if(editText_pdCord.getText().toString().isEmpty()){
            editText_pdCord.setError("plase Your Product Cord...");
          editText_pdCord.requestFocus();
           progressDialog.dismiss();
            return;
 }else  if(editText_pdName.getText().toString().isEmpty()){
        editText_pdName.setError("plase Your Product Name...");
        editText_pdName.requestFocus();
       progressDialog.dismiss();

   }else if(editText_pdDrec.getText().toString().isEmpty()){
       editText_pdDrec.setError("plase Your Product Desription...");
       editText_pdDrec.requestFocus();

   }/*else if(editText_pd_quantity.getText().toString().isEmpty()){
       editText_pd_quantity.setError("plase Your Product Quantity...");
       editText_pd_quantity.requestFocus();
        progressDialog.dismiss();

   }*/else if(editText_pdPriece.getText().toString().isEmpty()){
        editText_pdPriece.setError("plase Your Product price...");
        editText_pdPriece.requestFocus();
        progressDialog.dismiss();

   }else if(pd_imageView.getDrawable()==null){
   Toast.makeText(this, "plase Your Product Image", Toast.LENGTH_SHORT).show();
        pd_imageView.requestFocus();
        progressDialog.dismiss();

    }else if(checkBox_1.isChecked()==false && checkBox_2.isChecked()==false && checkBox_3.isChecked()==false && checkBox_4.isChecked()==false){
        Toast.makeText(this, "please Your Product Size Select", Toast.LENGTH_SHORT).show();
    }else{


        final String pd_Descript= editText_pdDrec.getText().toString();
      //  final String pd_quantiy= editText_pd_quantity.getText().toString();
        final String pd_price = editText_pdPriece.getText().toString();

        String match_id= sharedPrefe_id_name.getString("id_key","Id not Found");
        String match_name =sharedPrefe_id_name.getString("name_key","Name not Found");
        // String match_image= sharedPrefe_id_name.getString("image_key","Image not Found");

        if(match_id.contains(editText_pdCord.getText().toString())  || match_name.contains(editText_pdName.getText().toString().toLowerCase())){
            if(match_id.contains(editText_pdCord.getText().toString()) ){
           Toast.makeText(Femal_DressAdd.this, "Your Product Id  Alrady Add ..", Toast.LENGTH_LONG).show();
            } if(match_name.contains(editText_pdName.getText().toString().toLowerCase())){
           Toast.makeText(Femal_DressAdd.this, "Your Product Name  Alrady Add ..", Toast.LENGTH_LONG).show();
            }else{
            Toast.makeText(Femal_DressAdd.this, "Your Product Name and Id  Alrady Add ..", Toast.LENGTH_LONG).show();
            }

        }else{
            final StringBuffer prodcut_size=new StringBuffer();

            if(checkBox_1.isChecked()){
                prodcut_size.append(checkBox_1.getText()+",");
            } if(checkBox_2.isChecked()){
                prodcut_size.append(checkBox_2.getText()+",");
            } if(checkBox_3.isChecked()){
                prodcut_size.append(checkBox_3.getText()+",");
            } if(checkBox_4.isChecked()){
                prodcut_size.append("\n"+checkBox_4.getText()+",");
            }

            // final String pd_date = currentData.getEditableText().toString();

           // pd_quan=Integer.parseInt(pd_quantiy);
            Prod_price=Integer.parseInt(pd_price);
            progressDialog.show();
            StorageReference storageReference=storageRef_product_Image.child(System.currentTimeMillis()+".png");

            // StorageReference reference = storageReference.child(System.currentTimeMillis() + ".png");
            storageReference.putFile(uri_image)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri uriDowanload=uriTask.getResult();

                            femal_product_Model item=new femal_product_Model(pd_cord,pd_Name,pd_Descript,Prod_price,uriDowanload.toString(),currentData,prodcut_size.toString());
                            String myKey=  databaseReference.push().getKey();

                            databaseReference.child(myKey).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Femal_DressAdd.this, "Your product  Save SuccessFull.", Toast.LENGTH_SHORT).show();
                                    Notification_Add();
                                   // editText_pd_quantity.setText("");
                                    editText_pdCord.setText("");
                                    editText_pdDrec.setText("");
                                    editText_pdName.setText("");
                                    editText_pdPriece.setText("");
                                    editText_pdName.requestFocus();
                                    progressDialog.dismiss();
                                    pd_imageView.setImageResource(0);

                                    checkBox_1.setChecked(false);
                                    checkBox_2.setChecked(false);
                                    checkBox_3.setChecked(false);
                                    checkBox_4.setChecked(false);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Femal_DressAdd.this, "Your product Save Failde."+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(Femal_DressAdd.this, "Your product Image Save Failde."+exception.getMessage(), Toast.LENGTH_SHORT).show();
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
    private void Notification_Add(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel("Channel","Channel", NotificationManager.IMPORTANCE_HIGH);
              NotificationManager manager=getSystemService(NotificationManager.class);
              manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification= new NotificationCompat.Builder(this,"Channel")
       .setContentTitle("Your Product Add SuccessFull..")
        .setSmallIcon(R.drawable.ic_notifications)
        .setContentText(editText_pdName.getText().toString())
        .setAutoCancel(true)
         .setOnlyAlertOnce(true)
        .setWhen(System.currentTimeMillis());

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,notification.build());

    }


}