package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Order_accept_andNotification_show extends AppCompatActivity {
  private RecyclerView recyclerView;
  private Order_Accept_Adapter accept_adapter;
  private List<Order_Accept_Model>accept_modelList=new ArrayList<>();
  private DatabaseReference databaseRefere_showAll_Order;
  private EditText editText_search;
  private CardView cardView_totalPric;
  private TextView text_totalPric;
  private ProgressDialog progressDialog;
    int total_pric=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_accept_and_notification_show);
      databaseRefere_showAll_Order= FirebaseDatabase.getInstance().getReference("User_Accept_order_notificaion_add");

        recyclerView=(RecyclerView)findViewById(R.id.Order_acceptShow_all);
        editText_search=(EditText)findViewById(R.id.edit_order_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardView_totalPric=(CardView)findViewById(R.id.total_price_card);
        text_totalPric=(TextView)findViewById(R.id.text_total_amount);

        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wite...");
        progressDialog.show();
        // Read from the database
        databaseRefere_showAll_Order.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                accept_modelList.clear();
              for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                  Order_Accept_Model addItem=dataSnapshot1.getValue(Order_Accept_Model.class);
                  addItem.setAcc_order_Key(dataSnapshot1.getKey());
                  total_pric=addItem.getAccept_total()+total_pric;

                  accept_modelList.add(addItem);
              }

                text_totalPric.setText("Total Amount:"+total_pric);
              accept_adapter=new Order_Accept_Adapter(getApplicationContext(),accept_modelList);
              recyclerView.setAdapter(accept_adapter);
                progressDialog.dismiss();

              accept_adapter.setOnItemClickLisiner(new Order_Accept_Adapter.onItem_ClickLisiner() {
                  @Override
                  public void onClick(int position) {
                      Order_Accept_Model item_position=accept_modelList.get(position);
         Toast.makeText(Order_accept_andNotification_show.this, "Delivary Date:"+item_position.getAccept_date(), Toast.LENGTH_SHORT).show();
                  }

                  @Override
                  public void on_Delete(int position) {
                Order_Accept_Model item_position=accept_modelList.get(position);
                 databaseRefere_showAll_Order.child(item_position.getAcc_order_Key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
     Toast.makeText(Order_accept_andNotification_show.this, "Your Order Remove SuccessFull..", Toast.LENGTH_SHORT).show();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
         Toast.makeText(Order_accept_andNotification_show.this, "Your Order Remove Failde.."+e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });
                  }
              });
            }
            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
        Toast.makeText(Order_accept_andNotification_show.this, "Your Data Lodding Failde"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            fileList(s.toString());
            }
        });
    }

    private void fileList(String textData) {
      int pp=0;

        List<Order_Accept_Model>filterList=new ArrayList<>();
        for(Order_Accept_Model item: accept_modelList){

            if(item.getAccept_date().toLowerCase().contains(textData.toLowerCase())){
                pp= item.getAccept_total()+pp;
              text_totalPric.setText("Total Amount:"+pp);
                filterList.add(item);
            }else{
                text_totalPric.setText("Total Amount:");
            }

        }
        accept_adapter.filterdList(filterList);

    }

    public void go_home(View view) {
        Intent intent=new Intent(getApplicationContext(),Home_page.class);
        finish();
        startActivity(intent);
    }
}