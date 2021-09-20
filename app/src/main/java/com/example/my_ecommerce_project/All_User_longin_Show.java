package com.example.my_ecommerce_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class All_User_longin_Show extends AppCompatActivity {
  private RecyclerView recyclerView;
  private all_login_User_Adapter adapter;
  private List<sig_inDataModel>sig_inDataModelList=new ArrayList<>();
  private DatabaseReference databaseRefer_user_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__user_longin__show);
        databaseRefer_user_show= FirebaseDatabase.getInstance().getReference("User_login_Data");

        recyclerView=(RecyclerView)findViewById(R.id.rcycler_all_user_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Read from the database
        databaseRefer_user_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                 sig_inDataModel getItem=dataSnapshot1.getValue(sig_inDataModel.class);
                 getItem.setUser_key(dataSnapshot1.getKey());

                 sig_inDataModelList.add(getItem);
         /*      String pp=dataSnapshot1.getChildren().toString();
              Toast.makeText(All_User_longin_Show.this, ""+pp, Toast.LENGTH_SHORT).show();*/
             }
             adapter=new all_login_User_Adapter(getApplicationContext(),sig_inDataModelList);
             recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
        Toast.makeText(All_User_longin_Show.this, "Lodding Failde..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}