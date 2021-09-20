package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class User_Address extends AppCompatActivity {
  private EditText editText_UserName,editText_user_email,editText_location,editText_flat_no,editText_city,editText_phon;
  private DatabaseReference databaseRefere_UserInformation;
  private TextView text_current_date;
  private  String dat;
  private  int Total_pric;
   static int Shipping_Rate=100;
   private TextView textView_marque;
   private DatabaseReference databaseRefe_getUser;
   private ProgressDialog progressDialog;
   private SharedPreferences sharedPreferences_userGmall;
   private String user_gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__address);

        databaseRefere_UserInformation= FirebaseDatabase.getInstance().getReference("Use_information");
        databaseRefe_getUser= FirebaseDatabase.getInstance().getReference("User_login_Data");

        editText_UserName=(EditText)findViewById(R.id.user_name);
        editText_user_email=(EditText)findViewById(R.id.user_email);
        editText_location=(EditText)findViewById(R.id.user_location);
        editText_flat_no=(EditText)findViewById(R.id.user_flat_no_id);
        editText_city=(EditText)findViewById(R.id.user_city_id);
        editText_phon=(EditText)findViewById(R.id.user_phon);
        textView_marque=(TextView)findViewById(R.id.marquee_id);
        textView_marque.setSelected(true);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite..");
        progressDialog.show();
        getUser_email();

        text_current_date=(TextView) findViewById(R.id.deatils_current_Date);
        //editText_UserName=(EditText)findViewById(R.id.user_name);
        Date date=new Date();
        SimpleDateFormat sm=new SimpleDateFormat("YYYY-MM-dd");
          dat= sm.format(date.getTime());
        text_current_date.setText("Date:"+dat);

        editText_phon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
                user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");
                // Read from the database
                databaseRefere_UserInformation.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String ed_phon=editText_phon.getText().toString();
                     for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                   User_Address_Model getItem =dataSnapshot1.getValue(User_Address_Model.class);
                   getItem.setUser_address_key(getItem.getUser_address_key());

                   if((getItem.getUser_phon().contains(ed_phon)) &&(getItem.getUser_email().contains(user_gmail))){
                     editText_UserName.setText(""+getItem.getUser_name());
                     editText_city.setText(""+getItem.getUser_city());
                     editText_flat_no.setText(""+getItem.getUser_flat_no());
                     editText_location.setText(""+getItem.getUser_location());
                     editText_user_email.setText(""+getItem.getUser_email());
                    }else{
                  Toast.makeText(User_Address.this, "Your Phon Number Not Match!!", Toast.LENGTH_SHORT).show();
                     }
                     }

                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        progressDialog.dismiss();
               Toast.makeText(User_Address.this, "error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void Use_Adress_Data(View view) {
      /// intent GetData

        
    ///   data_key.split(",");
      /*String prod_Cord= bundle.getString("pd_Cord");
       int prod_price=bundle.getInt("pd_Price");
        int prod_quan=bundle.getInt("pd_Quan");
        String prod_size=bundle.getString("pd_Size");
        int prod_TotalPrice=bundle.getInt("prd_TotalPrice");*/

        if (editText_UserName.getText().toString().isEmpty()) {
            editText_UserName.setError("please Your User Name");
            editText_UserName.requestFocus();
        } else if (editText_user_email.getText().toString().isEmpty()) {
            editText_user_email.setError("please Your Email ");
            editText_user_email.requestFocus();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(editText_user_email.getText().toString()).matches()) {
            editText_user_email.setError("Your Email Address Invlide ");
            editText_user_email.requestFocus();

        } else if (editText_location.getText().toString().isEmpty()) {
            editText_location.setError("please Your Loction,area,street ");
            editText_location.requestFocus();

        } else if (editText_flat_no.getText().toString().isEmpty()) {
            editText_flat_no.setError("please Your Flat no,Building Name ");
            editText_flat_no.requestFocus();
        } else if (editText_city.getText().toString().isEmpty()) {
            editText_city.setError("please Your City/Twon ");
            editText_city.requestFocus();
        } else if (editText_phon.getText().toString().isEmpty()) {
            editText_phon.setError("please Your phonNumber ");
            editText_phon.requestFocus();
        } else if (editText_phon.getText().toString().length() < 11) {
            editText_phon.setError("Your phonNumber Invlide");
            editText_phon.requestFocus();
        } else {

            String city_status=editText_city.getText().toString().toLowerCase();
            if(city_status.contains("Dhaka")) {
                Toast.makeText(User_Address.this, "Flat Shipping Rate :"+Shipping_Rate, Toast.LENGTH_LONG).show();
            }else{
                Shipping_Rate=200;
                Toast.makeText(User_Address.this, "Flat Shipping Rate :"+Shipping_Rate, Toast.LENGTH_LONG).show();
            }

            Bundle bundle=getIntent().getExtras();
            String data_key= bundle.getString("data_all_key");
            Total_pric= bundle.getInt("pd_totalPrice");


            String user_name = editText_UserName.getText().toString();
            String user_email = editText_user_email.getText().toString();
            String user_locat = editText_location.getText().toString();
            String user_flat = editText_flat_no.getText().toString();
            String user_city = editText_city.getText().toString();
            String user_phon = editText_phon.getText().toString();

            String current_Date = text_current_date.getText().toString();

   User_Address_Model addItem = new User_Address_Model(user_name, user_email, user_locat, user_flat, user_city, user_phon,data_key,
           Total_pric,dat,Shipping_Rate);

            String mykey = databaseRefere_UserInformation.push().getKey();
            databaseRefere_UserInformation.child(mykey).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(User_Address.this, "User Information Add SuccessFull..", Toast.LENGTH_SHORT).show();
                    editText_UserName.setText("");
                    editText_user_email.setText("");
                    editText_location.setText("");
                    editText_flat_no.setText("");
                    editText_city.setText("");
                    editText_phon.setText("");
                    editText_UserName.requestFocus();

                    Intent intent = new Intent(User_Address.this, Comfrom_Order_Address_show.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
         Toast.makeText(User_Address.this, "User Information Add Failde..", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    void getUser_email(){
        // Read from the database
        databaseRefe_getUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                 sig_inDataModel getItem=dataSnapshot1.getValue(sig_inDataModel.class);
                 getItem.setUser_key(dataSnapshot1.getKey());
                  String email=getItem.getUser_Email();
                  editText_user_email.setText(""+email);

             }
                editText_user_email.setEnabled(false);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
          Toast.makeText(User_Address.this, "Your Login Data Failde..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}