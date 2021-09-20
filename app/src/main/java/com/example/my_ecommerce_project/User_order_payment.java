package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
import java.util.List;

public class User_order_payment extends AppCompatActivity {

   /// private DatabaseReference databaseRefe_final;
private TextView textView_order_item, textView_name,textView_email,
        textView_location,textView_flat_no,textView_city,
        textView_phon,text_total_price,
    textView_order_date;
private TextView textView_currentDate;
private EditText editText_stutes;
private DatabaseReference databaseRef_order_accept;

private DatabaseReference databaseRefe_final_order_accept_remove;

private String Us_name,Us_email,Us_loct,Us_flat,us_twon,phon,or_date, list_Position;

private int pd_Total;
private  StringBuilder sb=new StringBuilder();
private   String delivary_date;
private Order_final_admin_Model getItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_order_payment);

     Toolbar toolbar=(Toolbar)findViewById(R.id.orderAccept_toolbar);
      setSupportActionBar(toolbar);

        databaseRef_order_accept= FirebaseDatabase.getInstance().getReference("User_Accept_order_notificaion_add");

        databaseRefe_final_order_accept_remove= FirebaseDatabase.getInstance().getReference("User_Final_order_Add");

        textView_order_item=(TextView)findViewById(R.id.final_pd_all);
        textView_name=(TextView)findViewById(R.id.pd_userName);
        textView_email=(TextView)findViewById(R.id.final_pd_email);
        textView_location=(TextView)findViewById(R.id.final_pd_location);
        editText_stutes=(EditText)findViewById(R.id.edit_send_status);

        textView_flat_no=(TextView)findViewById(R.id.final_pd_flat);
        textView_phon=(TextView)findViewById(R.id.final_pd_phon);
        textView_name=(TextView)findViewById(R.id.final_totalPrice);
        textView_order_date=(TextView)findViewById(R.id.final_pd_date);
        textView_city=(TextView)findViewById(R.id.final_pd_city);
        text_total_price=(TextView)findViewById(R.id.final_totalPrice);
        textView_currentDate=(TextView)findViewById(R.id.Delivary_current_Date);

        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYY-MM-dd");
        delivary_date= simpleDateFormat.format(date.getTime());
        textView_currentDate.setText(delivary_date);
        getIntent_data();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.simple_item,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.check_out_orderId){
            Intent intent=new Intent(User_order_payment.this,Order_accept_andNotification_show.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void delivary_save_Order(View view) {
        int p=1;
/// Us_email,Us_loct,Us_flat,us_twon,phon,or_date;

        String accept_stutas=editText_stutes.getText().toString();
        if(editText_stutes.getText().toString().isEmpty()){
            editText_stutes.setError("Your Order Status Messeage Empty..");
            editText_stutes.requestFocus();
        }else{

                 Intent intent=new Intent();
                 intent.setAction(Intent.ACTION_SEND);
                 intent.setType("text/plain");
                 intent.putExtra(Intent.EXTRA_SUBJECT,editText_stutes.getText().toString());
                 intent.putExtra(Intent.EXTRA_TEXT,Us_email);
                 startActivity(Intent.createChooser(intent,"send"));

                Order_Accept_Model add_item=new Order_Accept_Model(sb.toString(),Us_name,Us_email,Us_loct,Us_flat,us_twon,phon,delivary_date,or_date,accept_stutas,pd_Total);
                String myKey= databaseRef_order_accept.push().getKey();

                databaseRef_order_accept.child(myKey).setValue(add_item).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        databaseRefe_final_order_accept_remove.child(list_Position).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                editText_stutes.setText("");
                                Toast.makeText(User_order_payment.this, "User Order Accept SuccessFull..", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
      Toast.makeText(User_order_payment.this, "Order Accept UnSuccessFull.."+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

    void getIntent_data(){
      Bundle bundle=getIntent().getExtras();
    String all_order=  bundle.getString("order_allData");

        String m[]=all_order.split("/");

        for (int i = 0,j=0; i <m.length ; i++) {j++;
         sb.append(j+".Order No:"+m[i]+".\n");
        }
    Us_name=  bundle.getString("user_Name");
    Us_email= bundle.getString("user_email");
     Us_loct= bundle.getString("user_locti");
    Us_flat= bundle.getString("user_flatOn");
    us_twon=   bundle.getString("user_twon");
    phon= bundle.getString("user_phon");
    or_date= bundle.getString("order_Date");
     pd_Total= bundle.getInt("total_pric");

     list_Position= bundle.getString("list_position_key");

   Toast.makeText(this, "name:"+Us_name, Toast.LENGTH_SHORT).show();
   textView_order_item.setText(sb.toString());
  // textView_name.setText(Us_name);
   textView_email.setText("User Gmail:"+Us_email);
   textView_location.setText("Use;r Location:"+Us_loct);
   textView_flat_no.setText("User Flat no,Building Name:"+Us_flat);
   textView_city.setText("City,Twon:"+us_twon);
   textView_phon.setText("phon Number:"+phon);
   textView_order_date.setText("ORder Date:"+or_date);
   text_total_price.setText("Total price:"+pd_Total);
    }

    private void Notification_Add(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel("Channel","Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification= new NotificationCompat.Builder(this,"Channel")
                .setContentTitle("Your  Recive Notification..")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentText(editText_stutes.getText().toString())
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis());

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,notification.build());

    }
}