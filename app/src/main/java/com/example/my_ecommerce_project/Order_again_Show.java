package com.example.my_ecommerce_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Order_again_Show extends AppCompatActivity {
private RecyclerView recyclerView;
private order_again_adapter adapter;
private List<order_againModel>order_againModer_List=new ArrayList<>();
private DatabaseReference databaseR_again_OrdrShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_again__show);
        databaseR_again_OrdrShow= FirebaseDatabase.getInstance().getReference("user_again_allSave");
        recyclerView=(RecyclerView)findViewById(R.id.recycler_again_Show);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Read from the database
        databaseR_again_OrdrShow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                 order_againModel item=dataSnapshot1.getValue(order_againModel.class);
                 item.setOrder_againKey(dataSnapshot1.getKey());
                 order_againModer_List.add(item);
             }
            adapter=new order_again_adapter(getApplicationContext(),order_againModer_List);
          recyclerView.setAdapter(adapter);
          adapter.setOnItemClickLisiner(new order_again_adapter.onItem_ClickLisiner() {
              @Override
              public void onClick(int position) {
                  String itmem_Id=order_againModer_List.get(position).getUser_pdCord();
            Toast.makeText(Order_again_Show.this, "product Id:"+itmem_Id, Toast.LENGTH_SHORT).show();
              }

              @Override
              public void Order_Again(int position) {
                femal_Category model=new femal_Category();
                  model.order_status="1";

                 // order_againModel get_Item=order_againModer_List.get(position);
                 // Toast.makeText(getApplicationContext(), "name:"+get_Item.getUser_pdName(), Toast.LENGTH_SHORT).show();

                order_againModel get_Item=order_againModer_List.get(position);
                  Toast.makeText(getApplicationContext(), "name:"+get_Item.getUser_pdName(), Toast.LENGTH_SHORT).show();

                  Intent intent=new Intent(Order_again_Show.this,femal_Category.class);
                  intent.putExtra("image_key",get_Item.getUser_pdimage());
                  intent.putExtra("pdname_key",get_Item.getUser_pdName());
                  intent.putExtra("pdCord_key",get_Item.getUser_pdCord());
                  intent.putExtra("Price_key",get_Item.getUser_pdprice());
                 // intent.putExtra("all_value","1");
                  startActivity(intent);

              }

              @Override
              public void on_Delete(int position) {

              }
          });
            }

            @Override
            public void onCancelled(DatabaseError error) {
        Toast.makeText(Order_again_Show.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}