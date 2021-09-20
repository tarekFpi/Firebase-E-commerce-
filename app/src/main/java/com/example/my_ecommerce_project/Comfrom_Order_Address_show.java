package com.example.my_ecommerce_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Comfrom_Order_Address_show extends AppCompatActivity {
  private RecyclerView recyclerView;
  private User_Address_Adapter adapter;
  private List<User_Address_Model>user_address_models=new ArrayList<>();
  private DatabaseReference databaseRefe_Use_infor;
  private DatabaseReference databaseRefe_femal_CategoryDelete;
  private DatabaseReference databaseRefe_shose_CategoryDelete;
  private DatabaseReference databaseRefe_recipe_CategoryDelete;
  private  SharedPreferences sharedPrefer_stock_quantity;
    private  String product_Cord;
    String count_Id;
    int count_aquan;
private int stock;
  private DatabaseReference databaseRefe_final_order;
  private LayoutInflater layoutInflater;
  private View view;
  AlertDialog alertDialog;
  private Button exit_button,Update_button;
   private   User_Address_Model model_getData;
  private EditText editText_name,editText_email,editText_loact,editText_flat_no,editText_number,editText_city;
  private RadioButton radioButton;
  private RadioGroup radioGroup;
  private ProgressDialog progressDialog;
  private SharedPreferences sharedPreferences;
  private SharedPreferences sharedPreferences_userGmall;
  private String mystatus="laptop_activity";
  private DatabaseReference databaseRefere_laptop_categoryDelete;
  private DatabaseReference databaseRefere_T_shirt_categoryDelete;
  private DatabaseReference databaseRefere_Cap_categoryDelete;
  private DatabaseReference databaseRefere_keybord_mouse_category;
  private DatabaseReference databaseRefere_baby_category;
  private DatabaseReference databaseRef_phon_CategoryDelete;
  private String user_gmail;
  private DatabaseReference databaseRefe_laptop_stock;
private BottomAppBar bottomAppBar;
private FloatingActionButton floatingActionButton;
 //Cap_Category_Add

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfrom__order_user);

        databaseRefe_Use_infor= FirebaseDatabase.getInstance().getReference("Use_information");
        databaseRefe_shose_CategoryDelete= FirebaseDatabase.getInstance().getReference("Shose_categoryAdd");
        databaseRefe_recipe_CategoryDelete= FirebaseDatabase.getInstance().getReference("Recipe_Category_Add");

        databaseRefe_final_order= FirebaseDatabase.getInstance().getReference("User_Final_order_Add");//send for Admin

        databaseRefe_femal_CategoryDelete= FirebaseDatabase.getInstance().getReference("femal_product_Category");

        databaseRefere_T_shirt_categoryDelete=FirebaseDatabase.getInstance().getReference("T_shairt_Category_Save");
        databaseRefere_Cap_categoryDelete=FirebaseDatabase.getInstance().getReference("Cap_Category_Add");

        databaseRefere_keybord_mouse_category=FirebaseDatabase.getInstance().getReference("Keybord_mouse_Category_Add");
        databaseRefere_baby_category=FirebaseDatabase.getInstance().getReference("Baby_product_CategoryAdd");
        databaseRef_phon_CategoryDelete= FirebaseDatabase.getInstance().getReference("Android_phon_product_CategoryAdd");

        //final order for stock -

        databaseRefe_laptop_stock= FirebaseDatabase.getInstance().getReference("Laptop_product_Add");

        recyclerView=(RecyclerView)findViewById(R.id.user_comfrom_Order_recycler);
        recyclerView.setHasFixedSize(true);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.home_floationg);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wite..");

        progressDialog.setCancelable(false);
        progressDialog.show();


        sharedPreferences_userGmall=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
       user_gmail= sharedPreferences_userGmall.getString("user_gmail","Your Gmail Not Found..");
       // user_gmail="mdmehedihasan55660@gmail.com";

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Home_page.class);
                startActivity(intent);
                finish();

            }
        });

        //sharedPrefer_stock_quantity = getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
      //  count_Id  = sharedPrefer_stock_quantity.getString("pd_id","Your Quantity  is Empty..");
        //count_aquan=sharedPrefer_stock_quantity.getInt("pd_quanitiy",0);

        // Read from the database
        databaseRefe_Use_infor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user_address_models.clear();
              for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                  User_Address_Model model=dataSnapshot1.getValue(User_Address_Model.class);
                  model.setUser_address_key(dataSnapshot1.getKey());


                  if(user_gmail.contains(model.getUser_email())){
                      user_address_models.add(model);
                      adapter=new User_Address_Adapter(getApplicationContext(),user_address_models);
                      recyclerView.setAdapter(adapter);
                      progressDialog.dismiss();


                      adapter.setOnItemClickLisiner(new User_Address_Adapter.onItem_ClickLisiner() {
                          @Override
                          public void onClick(int position) {
                    String name=user_address_models.get(position).getUser_name();
                     Toast.makeText(Comfrom_Order_Address_show.this, "Name:"+name, Toast.LENGTH_SHORT).show();
                          }

                          @Override
                          public void Update(int position) {
                              alertDialog=new AlertDialog.Builder(Comfrom_Order_Address_show.this).create();
                              alertDialog.setTitle("Address Update");
                              // alertDialog.setCancelable(false);
                              layoutInflater=getLayoutInflater();

                              view=layoutInflater.inflate(R.layout.user_deatils_update,null);
                              alertDialog.setView(view);

                              alertDialog.show();

                              editText_name=view.findViewById(R.id.edit_user_name);
                              editText_email=view.findViewById(R.id.edit_user_email);
                              editText_loact=view.findViewById(R.id.edit_user_location);
                              editText_flat_no=view.findViewById(R.id.edit_user_flat_no_id);
                              editText_city=view.findViewById(R.id.edit_user_city_id);
                              editText_number=view.findViewById(R.id.edit_user_phon);
                              exit_button=view.findViewById(R.id.exit_button);
                              Update_button=view.findViewById(R.id.edit_Adress_save);

                              // Read from the database
                              databaseRefe_Use_infor.addValueEventListener(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(DataSnapshot dataSnapshot) {
                                      for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                          model_getData=dataSnapshot1.getValue(User_Address_Model.class);
                                          model_getData.setUser_address_key(dataSnapshot1.getKey());
                                      }
                                      String us_name=model_getData.getUser_name();
                                      String us_email=model_getData.getUser_email();
                                      String us_loact=model_getData.getUser_location();
                                      String us_flat=model_getData.getUser_flat_no();
                                      String us_city=model_getData.getUser_city();
                                      String user_phon=model_getData.getUser_phon();

                                      editText_name.setText(us_name);
                                      editText_email.setText(us_email);
                                      editText_loact.setText(us_loact);
                                      editText_flat_no.setText(us_flat);
                                      editText_number.setText(user_phon);
                                      editText_city.setText(us_city);

                                      editText_email.setEnabled(false);

                                  }

                                  @Override
                                  public void onCancelled(DatabaseError error) {
                                      progressDialog.dismiss();
                                      Toast.makeText(Comfrom_Order_Address_show.this, "Data Loddng Failde..", Toast.LENGTH_SHORT).show();
                                  }
                              });

                              exit_button.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      alertDialog.dismiss();
                                  }
                              });

                              Update_button.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {


                                      if(editText_name.getText().toString().isEmpty()){
                                          editText_name.setError("Please User Name...");
                                          editText_name.requestFocus();
                                      }else if(editText_email.getText().toString().isEmpty()){
                                          editText_email.setError("Please Your Email ...");
                                          editText_email.requestFocus();

                                      }else if(editText_loact.getText().toString().isEmpty()){
                                          editText_loact.setError("Please Your Location No ...");
                                          editText_loact.requestFocus();

                                      }else if(editText_flat_no.getText().toString().isEmpty()){
                                          editText_loact.setError("Please Your Flat Building Name ...");
                                          editText_loact.requestFocus();
                                      }else if(editText_city.getText().toString().isEmpty()){
                                          editText_city.setError("Please City,Twon  No ...");
                                          editText_city.requestFocus();
                                      }else if(editText_number.getText().toString().isEmpty()){
                                          editText_number.setError("Please Your Number...");
                                          editText_number.requestFocus();
                                      }else{


                                          HashMap<String,Object> hashMap=new HashMap<>();
                                          hashMap.put("user_name",editText_name.getText().toString());
                                          hashMap.put("user_email",editText_email.getText().toString());
                                          hashMap.put("user_location",editText_loact.getText().toString());
                                          hashMap.put("user_flat_no",editText_flat_no.getText().toString());
                                          hashMap.put("user_city",editText_city.getText().toString());
                                          hashMap.put("user_phon",editText_number.getText().toString());
                                          databaseRefe_Use_infor.child(model_getData.getUser_address_key()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                              @Override
                                              public void onSuccess(Void aVoid) {
                                                  editText_name.setText("");
                                                  editText_email.setText("");
                                                  editText_loact.setText("");
                                                  editText_flat_no.setText("");
                                                  editText_number.setText("");
                                                  editText_city.setText("");
                                                  alertDialog.dismiss();
                                                  Toast.makeText(Comfrom_Order_Address_show.this, "Your Address Update SuccessFull", Toast.LENGTH_SHORT).show();
                                              }
                                          });
                                      }
                                  }
                              });
                          }

                          @Override
                          public void Order_CategorySelect(final int position) {

                              try {
                                  Notification_Add();
                                  sharedPreferences=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
                                  //sharedPreferences_tShirt=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
                                  databaseRefere_laptop_categoryDelete= FirebaseDatabase.getInstance().getReference("laptop_product_category");

                                  String order_AllData=user_address_models.get(position).getDataAll();
                                  String us_name=user_address_models.get(position).getUser_name();
                                  String us_email=user_address_models.get(position).getUser_email();
                                  String us_loact=user_address_models.get(position).getUser_location();
                                  String us_flat=user_address_models.get(position).getUser_flat_no();
                                  String us_city=user_address_models.get(position).getUser_city();
                                  String user_phon=user_address_models.get(position).getUser_phon();
                                  String order_date=user_address_models.get(position).getCurrent_date();
                                  int order_totPrice=user_address_models.get(position).getProd_TotalPrice();


                                  String myKey=databaseRefe_final_order.push().getKey();
                                  // editor.putString("my_capKey","Cap_categoryShow").apply();

                                  final Order_final_admin_Model addItem=new Order_final_admin_Model(order_AllData,us_name,us_email,us_loact,us_flat,us_city,user_phon,order_date,order_totPrice);
                                  databaseRefe_final_order.child(myKey).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void aVoid) {


                                          String laptop_pageData= sharedPreferences.getString("myKey","Data not found");
                                          String t_shairt_page= sharedPreferences.getString("myKey_tshirt","Data not found");
                                          String femalCategory_page= sharedPreferences.getString("myKey_femalCategory","Data not found");
                                          String shoseCategory_page= sharedPreferences.getString("myKey_shose","Data not found");
                                          String Cap_Category_page= sharedPreferences.getString("my_capKey","Data not found");
                                          String recipe_Category_page= sharedPreferences.getString("my_recipe_Key","Data not found");
                                          String keybord_mouse_Category_page= sharedPreferences.getString("myKey","Data not found");
                                          String baby_Category_page= sharedPreferences.getString("my_Key","Data not found");
                                          String phon_Category_page= sharedPreferences.getString("myKey","Data not found");

                                          if(laptop_pageData.contains(mystatus)){
                                              databaseRefere_laptop_categoryDelete.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                      Toast.makeText(Comfrom_Order_Address_show.this, "ORder Submet SuccessFull", Toast.LENGTH_SHORT).show();
                                                  }
                                              });

                                          } if(t_shairt_page.contains("t_shairt_category")){
                                              databaseRefere_T_shirt_categoryDelete.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                      Toast.makeText(Comfrom_Order_Address_show.this, "ORder T-shirt Submet SuccessFull", Toast.LENGTH_SHORT).show();
                                                  }
                                              });

                                          }if(femalCategory_page.contains("femal_category")){
                                              databaseRefe_femal_CategoryDelete.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                      Toast.makeText(Comfrom_Order_Address_show.this, "ORder Submet SuccessFull", Toast.LENGTH_SHORT).show();
                                                  }
                                              });
                                          }if(shoseCategory_page.contains("shose_categoryShow")) {
                                              databaseRefe_shose_CategoryDelete.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                      Toast.makeText(Comfrom_Order_Address_show.this, "ORder Submet SuccessFull", Toast.LENGTH_SHORT).show();
                                                  }
                                              });

                                          }if(Cap_Category_page.contains("Cap_categoryShow")) {
                                              databaseRefere_Cap_categoryDelete.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                      Toast.makeText(Comfrom_Order_Address_show.this, "ORder Submet SuccessFull", Toast.LENGTH_SHORT).show();
                                                  }
                                              });
                                          }

                                          if(recipe_Category_page.contains("my_recipe_Key")) {
                                              databaseRefe_recipe_CategoryDelete.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                      Toast.makeText(Comfrom_Order_Address_show.this, "ORder Submet SuccessFull", Toast.LENGTH_SHORT).show();
                                                  }
                                              });
                                          }

                                          if(keybord_mouse_Category_page.contains("keybord_mouse_CategoryShow_activity")) {
                                              databaseRefere_keybord_mouse_category.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                      Toast.makeText(Comfrom_Order_Address_show.this, "ORder Submet SuccessFull", Toast.LENGTH_SHORT).show();
                                                  }
                                              });
                                          }

                                          if(baby_Category_page.contains("baby_CategoryShow_activity")) {
                                              databaseRefere_keybord_mouse_category.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                      Toast.makeText(Comfrom_Order_Address_show.this, "ORder Submet SuccessFull", Toast.LENGTH_SHORT).show();
                                                  }
                                              });
                                          }
                                          if(phon_Category_page.contains("phon_CategoryShow_activity")) {
                                              databaseRef_phon_CategoryDelete.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                      Toast.makeText(Comfrom_Order_Address_show.this, "ORder Submet SuccessFull", Toast.LENGTH_SHORT).show();
                                                  }
                                              });
                                          }


                                      }
                                  });
                              }catch (Exception e){
                                  Toast.makeText(Comfrom_Order_Address_show.this, "error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          }
                          //   databaseRefere_keybord_mouse_category
                          @Override
                          public void on_Delete(int position) {
                              User_Address_Model remove_order=user_address_models.get(position);

                              databaseRefe_Use_infor.child(remove_order.getUser_address_key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void aVoid) {
                Toast.makeText(Comfrom_Order_Address_show.this, "Your Order Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                                  }
                              });
                          }

                      });
                  }else{
                      progressDialog.dismiss();
      Toast.makeText(Comfrom_Order_Address_show.this, "Your Ordere is Not Found..", Toast.LENGTH_SHORT).show();
                  }
              }




            }
            @Override
            public void onCancelled(DatabaseError error) {
        Toast.makeText(Comfrom_Order_Address_show.this, "Order Lodding Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

   /* public void final_order_Data(View view) {


                  databaseRefe_Use_infor.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                              model_getData=dataSnapshot1.getValue(User_Address_Model.class);
                              model_getData.setUser_address_key(dataSnapshot1.getKey());
                          }
                          String order_AllData=model_getData.getDataAll();
                          String us_name=model_getData.getUser_name();
                          String us_email=model_getData.getUser_email();
                          String us_loact=model_getData.getUser_location();
                          String us_flat=model_getData.getUser_flat_no();
                          String us_city=model_getData.getUser_city();
                          String user_phon=model_getData.getUser_phon();
                          String order_date=model_getData.getCurrent_date();
                          int order_totPrice=model_getData.getProd_TotalPrice();

                          String myKey=databaseRefe_final_order.push().getKey();
                          Order_final_admin_Model addItem=new Order_final_admin_Model(order_AllData,us_name,us_email,us_loact,us_flat,us_city,user_phon,order_date,order_totPrice);

                          databaseRefe_final_order.child(myKey).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                  // Read from the database
                                  databaseRefe_User_CategoryDelete.addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(DataSnapshot dataSnapshot) {
                                          for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                              femal_category_Model item=dataSnapshot1.getValue(femal_category_Model.class);
                                              item.setCategory_key(dataSnapshot1.getKey());
                                          }

                                      }
                                      @Override
                                      public void onCancelled(DatabaseError error) {
                                          Toast.makeText(Comfrom_Order_Address_show.this, "Category Remove Failde ", Toast.LENGTH_SHORT).show();
                                      }
                                  });

                              }
                          }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  Toast.makeText(Comfrom_Order_Address_show.this, "ORder Submet Failde..", Toast.LENGTH_SHORT).show();
                              }
                          });
                      }

                      @Override
                      public void onCancelled(DatabaseError error) {
                          Toast.makeText(Comfrom_Order_Address_show.this, "Data Loddng Submet..", Toast.LENGTH_SHORT).show();
                      }
                  });
              }*/


    private void Notification_Add(){
       // progress_order();
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel("Channel","Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification= new NotificationCompat.Builder(this,"Channel")
                .setContentTitle("Delivary SuccessFull..")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentText("Your Final Order SuccessFull..")
                .setAutoCancel(true)
                //.setSound(Uri.)
                .setWhen(System.currentTimeMillis());

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,notification.build());

    }
   /* void progress_order(){
        final int max_progess=100;
        final NotificationCompat.Builder  notificationCompat=new NotificationCompat.Builder(this,"channel_id");

            notificationCompat.setSmallIcon(R.drawable.ic_baseline_up)
                .setContentTitle("Dowandload")
                .setContentText("Download in progess")
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setProgress(max_progess,0,true)
                .setWhen(System.currentTimeMillis());


        final NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notificationCompat.build());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    for(int progress =0; progress <= max_progess; progress =progress+10){
                   *//* notificationCompat.setProgress(max_progess,progress,false);
                    notificationManager.notify(notificationId,notificationCompat.build());*//*
                        Thread.sleep(1000);
                    }
                    notificationCompat.setContentText("Upload Order finished")
                            .setProgress(0,0,false)
                            .setOngoing(false);

                    notificationManager.notify(1,notificationCompat.build());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/



    public void Home_page(View view) {

    }
}