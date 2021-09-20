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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

public class T_shirt_Product_Add extends AppCompatActivity {
  private ImageView imageView;
  private EditText editText_pdName,editText_pdCord,editText_pdDrec,editText_pdPrice,editText_stock,editText_discount;
  private DatabaseReference databaseRefe_tshirt;
  private DatabaseReference databaseRefe_match_id_name_image;
  private StorageReference storageRef_shirt_Image;
  private static int request_cord=1;
  private Uri uri_image;
  private ProgressDialog progressDialog;
  private CheckBox checkBox_1,checkBox_2,checkBox_3,checkBox_4;
  private SharedPreferences sharedPrefe_id_name;
  private    T_shirt_Moder shirt_moder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_shirt__product__add);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

          databaseRefe_tshirt= FirebaseDatabase.getInstance().getReference("T_shirtProduct_Add");
        storageRef_shirt_Image= FirebaseStorage.getInstance().getReference("T_shirtProduct_Add");

        databaseRefe_match_id_name_image= FirebaseDatabase.getInstance().getReference("match_id_name_image");

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wite..");

         editText_pdCord=(EditText)findViewById(R.id.t_shirt_pdcord);
        editText_pdName=(EditText)findViewById(R.id.t_shirt_pdName);
        editText_pdDrec=(EditText)findViewById(R.id.t_shirt_pdDecr);
        editText_pdPrice=(EditText)findViewById(R.id.t_shirt_pdprice);
       // editText_stock=(EditText)findViewById(R.id.t_shirt_pdstock);
        imageView=(ImageView)findViewById(R.id.t_shirt_PdImage);
        editText_discount=(EditText)findViewById(R.id.t_shirt_pddiscount);

         checkBox_1=(CheckBox)findViewById(R.id.check_1);
         checkBox_2=(CheckBox)findViewById(R.id.check_2);
         checkBox_3=(CheckBox)findViewById(R.id.check_3);
         checkBox_4=(CheckBox)findViewById(R.id.check_4);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choose_Image();
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
            Intent intent=new Intent(getApplicationContext(),T_shairt_Prodcut_rateShow.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Choose_Image() {
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
        if(editText_pdCord.getText().toString().isEmpty()){
            editText_pdCord.setError("please Your Product Id");
            editText_pdCord.requestFocus();

        }else if(editText_pdName.getText().toString().isEmpty()){
            editText_pdName.setError("please Your Product Name ");
            editText_pdName.requestFocus();

        }else{
            sharedPrefe_id_name=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
            databaseRefe_tshirt.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                        T_shirt_Moder tShirtModer = dataSnapshot1.getValue(T_shirt_Moder.class);
                        tShirtModer.setTshirt_pdKey(dataSnapshot1.getKey());

                        if (tShirtModer.getTshirt_pdCord().contains(editText_pdCord.getText().toString()) || tShirtModer.getTshirt_pdName().contains(editText_pdName.getText().toString().toLowerCase()))
                        {
                            sharedPrefe_id_name.edit().putString("id_key",tShirtModer.getTshirt_pdCord()).apply();
                            sharedPrefe_id_name.edit().putString("name_key",tShirtModer.getTshirt_pdName()).apply();
                            //  sharedPrefe_id_name.edit().putString("image_key",tShirtModer.getTshirt_pdImage()).apply();
                            sharedPrefe_id_name.edit().commit();
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(T_shirt_Product_Add.this, "Match Lodding Data Failde:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    //  T_shirt_Moder add_item=new T_shirt_Moder(pd_cord,pd_pdName,pd_pdDecr,pd_pdPrice,pd_stock,);
    public void t_shirt_procutAdd(View view) {

     if(editText_pdCord.getText().toString().isEmpty()){
         editText_pdCord.setError("please Your Product Id");
         editText_pdCord.requestFocus();

     }else if(editText_pdName.getText().toString().isEmpty()){
         editText_pdName.setError("please Your Product Name ");
         editText_pdName.requestFocus();

     }else if(editText_pdDrec.getText().toString().isEmpty()){
         editText_pdDrec.setError("please Your Description ");
         editText_pdDrec.requestFocus();

     }else if(editText_discount.getText().toString().isEmpty()){
         editText_discount.setError("please Your Discount");
         editText_discount.requestFocus();

     }else if(editText_pdPrice.getText().toString().isEmpty()){
         editText_pdPrice.setError("please Your Price");
         editText_pdPrice.requestFocus();
     }/*else if(editText_stock.getText().toString().isEmpty()){
         editText_stock.setError("please Your Stock");
         editText_stock.requestFocus();
     }*/else if(checkBox_1.getText().toString().isEmpty() || checkBox_2.getText().toString().isEmpty() ||checkBox_3.getText().toString().isEmpty() ||checkBox_4.getText().toString().isEmpty()){
         Toast.makeText(T_shirt_Product_Add.this, "please Your Product Size Select", Toast.LENGTH_SHORT).show();
     }else {
         String match_id= sharedPrefe_id_name.getString("id_key","Id not Found");
         String match_name =sharedPrefe_id_name.getString("name_key","Name not Found");
        // String match_image= sharedPrefe_id_name.getString("image_key","Image not Found");

          if(match_id.contains(editText_pdCord.getText().toString())  || match_name.contains(editText_pdName.getText().toString().toLowerCase())){
              if(match_id.contains(editText_pdCord.getText().toString()) ){
                  Toast.makeText(T_shirt_Product_Add.this, "Your Product Id  Alrady Add ..", Toast.LENGTH_LONG).show();
              } if(match_name.contains(editText_pdName.getText().toString().toLowerCase())){
                  Toast.makeText(T_shirt_Product_Add.this, "Your Product Name  Alrady Add ..", Toast.LENGTH_LONG).show();
              }
             //Toast.makeText(T_shirt_Product_Add.this, "Your Product Id Name Alrady Add ..", Toast.LENGTH_SHORT).show();
          } else{

         final StringBuffer prodcut_size = new StringBuffer();

         if (checkBox_1.isChecked()) {
             prodcut_size.append(checkBox_1.getText() + ",");
         }
         if (checkBox_2.isChecked()) {
             prodcut_size.append(checkBox_2.getText() + ",");
         }
         if (checkBox_3.isChecked()) {
             prodcut_size.append(checkBox_3.getText() + ",");
         }
         if (checkBox_4.isChecked()) {
             prodcut_size.append("\n" + checkBox_4.getText() + ",");
         } else {

             progressDialog.show();
             final String pd_cord = editText_pdCord.getText().toString();
             final String pd_pdName = editText_pdName.getText().toString().toLowerCase();
             final String pd_pdDecr = editText_pdDrec.getText().toString();

             final int pd_pdPrice = Integer.parseInt(editText_pdPrice.getText().toString());
             ///final int pd_stock = Integer.parseInt(editText_stock.getText().toString());
             final int pd_discout = Integer.parseInt(editText_discount.getText().toString());

             final String key = databaseRefe_tshirt.push().getKey();
             StorageReference storageRefere_imageExt = storageRef_shirt_Image.child(System.currentTimeMillis() + ".png");

             storageRefere_imageExt.putFile(uri_image)
                     .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                             while (!uriTask.isComplete()) ;
                             final Uri uriDowanlod = uriTask.getResult();
                             // Read from the database

                             shirt_moder = new T_shirt_Moder(pd_cord, pd_pdName, pd_pdDecr, pd_pdPrice, uriDowanlod.toString(), pd_discout, prodcut_size.toString());
                             databaseRefe_tshirt.child(key).setValue(shirt_moder).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {

                                     match_id_nameModel match_id = new match_id_nameModel(pd_cord, pd_pdName, uriDowanlod.toString());
                                     String key_data = databaseRefe_match_id_name_image.push().getKey();
                                     databaseRefe_match_id_name_image.child(key_data).setValue(match_id);

                                     editText_pdCord.setText("");
                                     editText_discount.setText("");
                                     editText_pdDrec.setText("");
                                     editText_pdName.setText("");
                                     editText_pdPrice.setText("");

                                     imageView.setImageResource(0);
                                     progressDialog.dismiss();
                                     final StringBuffer prodcut_size = new StringBuffer();

                                     checkBox_1.setChecked(false);
                                     checkBox_2.setChecked(false);
                                     checkBox_3.setChecked(false);
                                     checkBox_4.setChecked(false);

                                     Toast.makeText(T_shirt_Product_Add.this, "Your product Add SuccessFull...", Toast.LENGTH_SHORT).show();
                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     progressDialog.dismiss();
                                     Toast.makeText(T_shirt_Product_Add.this, "Your product Match  Failde" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             });

                         }
                     })
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception exception) {
                             progressDialog.dismiss();
                             Toast.makeText(T_shirt_Product_Add.this, "Error :" + exception.getMessage(), Toast.LENGTH_SHORT).show();
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
}