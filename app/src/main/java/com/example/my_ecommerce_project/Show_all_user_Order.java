package com.example.my_ecommerce_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Show_all_user_Order extends AppCompatActivity {
private RecyclerView recyclerView;
    private DatabaseReference databaseRefe_final;
    private ProgressDialog progressDialog;
    private EditText editText_search;

    private List<Order_final_admin_Model> modelList_admin=new ArrayList<>();
    private Order_final_admidAdapter adapter;
    private Order_final_admin_Model showItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_user__order);
        databaseRefe_final= FirebaseDatabase.getInstance().getReference("User_Final_order_Add");
        recyclerView=(RecyclerView)findViewById(R.id.admin_comfrom_OrderShow_recycler);
        editText_search=(EditText)findViewById(R.id.User_all_order_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wite..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        // Read from the database
        databaseRefe_final.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                modelList_admin.clear();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                     showItem=dataSnapshot1.getValue(Order_final_admin_Model.class);
                    showItem.setOreder_key(dataSnapshot1.getKey());
                    modelList_admin.add(showItem);
                }
                adapter=new Order_final_admidAdapter(getApplicationContext(),modelList_admin);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                adapter.setOnItemClickLisiner(new Order_final_admidAdapter.onItem_ClickLisiner() {
                    @Override
                    public void onClick(int position) {
                        String name=modelList_admin.get(position).getUser_name();
             Toast.makeText(Show_all_user_Order.this, "Name:"+name, Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(Show_all_user_Order.this,User_order_payment.class);

                   Order_final_admin_Model remove_position=modelList_admin.get(position);
                    intent.putExtra("order_allData",modelList_admin.get(position).getOrder_allData());
                    intent.putExtra("user_Name",name);
                    intent.putExtra("user_email",modelList_admin.get(position).getUser_emali());
                    intent.putExtra("user_locti",modelList_admin.get(position).getUser_location());
                    intent.putExtra("user_flatOn",modelList_admin.get(position).getUser_flat_building());
                    intent.putExtra("user_twon",modelList_admin.get(position).getUser_city_twon());
                    intent.putExtra("user_phon",modelList_admin.get(position).getUser_phon());
                    intent.putExtra("order_Date",modelList_admin.get(position).getOrder_date());
                    intent.putExtra("total_pric",modelList_admin.get(position).getOrder_Total_price());

                    intent.putExtra("list_position_key",modelList_admin.get(position).getOreder_key());
                    startActivity(intent);
                    }

                    @Override
                    public void onDelete(int position) {
          Order_final_admin_Model remove_position=modelList_admin.get(position);
                    databaseRefe_final.child(remove_position.getOreder_key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
             Toast.makeText(Show_all_user_Order.this, "Your Data Update SuccessFull", Toast.LENGTH_SHORT).show();
                        }
                    });
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
      Toast.makeText(Show_all_user_Order.this, "Error :"+error.getMessage(), Toast.LENGTH_SHORT).show();
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

            private void fileList(String textData) {
                List<Order_final_admin_Model>filter_list=new ArrayList<>();
                for(Order_final_admin_Model item :modelList_admin){
                    if(item.getOrder_date().toLowerCase().contains(textData.toLowerCase().toString())){
                        filter_list.add(item);
                    }
                }
                adapter.Filter_changData(filter_list);
            }
        });
    }
}