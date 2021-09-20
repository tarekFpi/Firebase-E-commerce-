package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_page extends AppCompatActivity {
  private CardView cardView_femal_dress,cardView_AllCap,
          cardView_lapTop,cardView_T_shairt,cardView_recipe,cardView_shose,cardView_android_phon,
          cardView_babyProduct,cardView_keybor_mouse,admin_cardviwe,cardView_exit;
  private View view;
  private LayoutInflater layoutInflater;
  private TextView user_profile_name,profile_image,profile_email;
  private CircleImageView circleImageView;

   private ImageSlider imageSlider;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private DatabaseReference databaseRefe_userLogin,databaseReference_Admin;
   private sig_inDataModel getItemUser=null;
    private static String Admin_email,status_name;
    private TextView textView_status;
    private String user_name,user_email,user_image;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPrefere_status;
  //  private SharedPreferences sharedPreferences_userGmall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        databaseRefe_userLogin=FirebaseDatabase.getInstance().getReference("User_login_Data");
        databaseReference_Admin=FirebaseDatabase.getInstance().getReference("Admin_information");

        drawerLayout=(DrawerLayout)findViewById(R.id.home_drawable_id);
        navigationView=(NavigationView)findViewById(R.id.home_navigation_id);

        Toolbar toolbar=(Toolbar)findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string. close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

       // navigationView.setNavigationItemSelectedListener(this);
       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

               switch (menuItem.getItemId()) {
                   case R.id.my_hom_id:
                       Toast.makeText(Home_page.this, "Home", Toast.LENGTH_SHORT).show();
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;
                  //my_exit
                   case R.id.my_order_Id:
                      Intent intent=new Intent(Home_page.this,Comfrom_Order_Address_show.class);
                      startActivity(intent);
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;

                   case R.id.my_exit:
                      // Intent signOut=new Intent(Home_page.this,Login_page.class);
                       FirebaseAuth.getInstance(). signOut();
                    //  startActivity(signOut);
                       finish();
                       drawerLayout.closeDrawer(GravityCompat.START);
                       break;
               }

               return true;
           }
       });


      /*  sig_inDataModel addUser=new sig_inDataModel("Tarek","Utcsoftwar@gmail.com",googleSignInAccount.getPhotoUrl().toString());
      String key=  databaseReference_Admin.push().getKey();
        databaseReference_Admin.child(key).setValue(addUser);
       */

       progressDialog=new ProgressDialog(this);
       progressDialog.setCancelable(false);
       progressDialog.setMessage("Please Wite..");
       progressDialog.show();
        imageSlider=(ImageSlider) findViewById(R.id.e_comm_home_imageSlider);
        List<SlideModel> slideModels=new ArrayList<>();

      //  slideModels.add(new SlideModel(R.drawable.dress2s,"Ladis", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.kirill_martynov,"Desktop", ScaleTypes.FIT));
         slideModels.add(new SlideModel(R.drawable.den_trushtin,"T-Shirt", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.john,"Man", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.andre,"Cap", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.keybord,"keybord", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.hadphon,"Hardphon", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
        textView_status=(TextView)findViewById(R.id.status_id);
        cardView_AllCap=(CardView)findViewById(R.id.card_cap_Id);
        cardView_femal_dress=(CardView)findViewById(R.id.Card_femal_dres);
        cardView_lapTop=(CardView) findViewById(R.id.laptop_cardData);

        cardView_T_shairt=(CardView) findViewById(R.id.card_t_shairt_id);
        cardView_recipe=(CardView) findViewById(R.id.card_recipe_Id);
        cardView_shose=(CardView) findViewById(R.id.card_shose_id);

        cardView_android_phon=(CardView) findViewById(R.id.phon_card_id);
        cardView_keybor_mouse=(CardView) findViewById(R.id.keybord_mouse_id);
        cardView_babyProduct=(CardView)findViewById(R.id.baby_card_id);
        admin_cardviwe=(CardView)findViewById(R.id.admin_cardviwe);
        cardView_exit=(CardView)findViewById(R.id.sign_out_id);
        textView_status.setSelected(true);

        view=navigationView.inflateHeaderView(R.layout.hader_layout);
        circleImageView=(CircleImageView)view.findViewById(R.id.profile_imageId);
        user_profile_name= (TextView)view. findViewById(R.id.profile_name_id);
        profile_email= (TextView)view. findViewById(R.id.profile_email_id);

     /*   layoutInflater=getLayoutInflater();
        view=  layoutInflater.inflate(R.layout.hader_layout,null);

        circleImageView=  view.findViewById(R.id.profile_imageId);
        user_profile_name= view.findViewById(R.id.profile_name_id);
        profile_email= view.findViewById(R.id.profile_email_id);*/
        sharedPrefere_status=getApplicationContext().getSharedPreferences("com.example.my_ecommerce_project", Context.MODE_PRIVATE);
          getLonin_admin_user();
        if(textView_status.getText().toString().contains("Admin")){
            toggle.setDrawerIndicatorEnabled(false);
            navigationView.setEnabled(false);

        }
      /*  if(status_name.contains("Admin")){
            drawerLayout.setEnabled(false);
        }*/
        cardView_lapTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,laptop_all_rateShow.class);
                startActivity(intent);
            }
        });

        cardView_femal_dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,Femal_product_rate_Show.class);
                startActivity(intent);
            }
        });

        cardView_AllCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,All_CapShow_Rate.class);
                startActivity(intent);
            }
        });

        cardView_T_shairt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,T_shairt_Prodcut_rateShow.class);
                startActivity(intent);
            }
        });

        cardView_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,Recipe_rateShow.class);
                startActivity(intent);
            }
        });

        cardView_shose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,shose_rate_Show.class);
                startActivity(intent);
            }
        });

        cardView_android_phon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,phon_rate_Show.class);
                startActivity(intent);
            }
        });

        cardView_keybor_mouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,keybord_mouse_rate_show.class);
                startActivity(intent);
            }
        });

        cardView_babyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,Baby_product_rate_show.class);
                startActivity(intent);
            }
        });

        admin_cardviwe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,Product_Add_All.class);
                startActivity(intent);
                finish();
            }
        });

        cardView_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,Login_page.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(intent);
                finish();
            }
        });
    }



    private void getLonin_admin_user() {

        // Read from the database
      /*  databaseRefe_userLogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
             getItemUser=dataSnapshot1.getValue(sig_inDataModel.class);
                getItemUser.setUser_key(dataSnapshot1.getKey());
                if(Admin_email.contains(getItemUser.getUser_Email())){
               Toast.makeText(Home_page.this, "Login Admin", Toast.LENGTH_SHORT).show();
                    status_name="Admin";
                    textView_status.setText(""+status_name);
                }if(!Admin_email.contains(getItemUser.getUser_Email())){
                    status_name="User";
                    textView_status.setText(""+status_name);
                Toast.makeText(Home_page.this, "Login User"+status_name, Toast.LENGTH_LONG).show();
                }
               }
            }
            @Override
            public void onCancelled(DatabaseError error) {
         Toast.makeText(Home_page.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

        GoogleSignInAccount googleSignInAccount= GoogleSignIn.getLastSignedInAccount(this);
        user_name=googleSignInAccount.getDisplayName();
        user_email=googleSignInAccount.getEmail();
        user_image=googleSignInAccount.getPhotoUrl().toString();
        // Toast.makeText(this, "Login "+user_name, Toast.LENGTH_SHORT).show();


        databaseReference_Admin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {
                    sig_inDataModel getItem = dataSnapshot1.getValue(sig_inDataModel.class);
                    getItem.setUser_key(dataSnapshot1.getKey());
                    Admin_email = getItem.getUser_Email();
                    //  Toast.makeText(Home_page.this, "Login Admin"+Admin_email, Toast.LENGTH_LONG).show();
                    //  status_name="Admin";
                    if (user_email.contains(Admin_email)) {
                        status_name = "Admin";
                        Toast.makeText(Home_page.this, "Login Admin SuccesssFull..", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        textView_status.setText(status_name);
                        cardView_exit.setVisibility(View.VISIBLE);
                        drawerLayout.setDrawingCacheEnabled(false);
                    } else {
                        status_name = "User";
                        admin_cardviwe.setVisibility(View.INVISIBLE);
                        textView_status.setText(status_name);
                        cardView_exit.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Login User SuccesssFull...", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        sharedPrefere_status.edit().putString("my_status_key", status_name).apply();
                        sharedPrefere_status.edit().putString("user_gmail", user_email).apply();
                        sharedPrefere_status.edit().commit();
    //Toast.makeText(getApplicationContext(), "User gamil:"+user_email, Toast.LENGTH_SHORT).show();

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Home_page.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });



        Picasso.get().load(user_image).into(circleImageView);
        user_profile_name.setText(user_name);
        profile_email.setText(user_email);

    }

}