package com.example.my_ecommerce_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_page extends AppCompatActivity {
    private ProgressBar progressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 1;
    private TextView textView_marque;
    private FirebaseAuth mAuth;
    private SignInButton signInButton;
    private DatabaseReference databaseRef_userData;
    private DatabaseReference databaseReference_Admin;
    private static String Admin_email,status_name;
    private SharedPreferences sharedPreferences_userGmall;
   private int prog=0;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(getApplicationContext(),Home_page.class);
            startActivity(intent);
        }
        AdminData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth=FirebaseAuth.getInstance();
        signInButton=(SignInButton)findViewById(R.id.sing_data);
        databaseRef_userData= FirebaseDatabase.getInstance().getReference("User_login_Data");
        databaseReference_Admin= FirebaseDatabase.getInstance().getReference("Admin_information");
        textView_marque=(TextView)findViewById(R.id.log_data_marq);
        textView_marque.setSelected(true);

        progressBar=(ProgressBar)findViewById(R.id.log_progressId);
       /* progressBar = (ProgressBar) findViewById(R.id.log_progressId);
        progressBar.setVisibility(View.VISIBLE);*/

   CreateRequest();
   signInButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           signIn();

       }
   });
    }

    private void CreateRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                // Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "Your Sign in Failde"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }



    private void firebaseAuthWithGoogle(String idToken) {

            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                          // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Login_page.this, "SuccessFull", Toast.LENGTH_SHORT).show();
                              getData();
                                Intent intent=new Intent(getApplicationContext(),Home_page.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                             Toast.makeText(Login_page.this, "Firebase mAuth Failde..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }

    void getData(){

        progressBar.setVisibility(View.VISIBLE);
        GoogleSignInAccount googleSignInAccount=GoogleSignIn.getLastSignedInAccount(this);

        if(googleSignInAccount !=null){
            String DisName= googleSignInAccount.getDisplayName();
            final String email= googleSignInAccount.getEmail();
            Uri uriImage= googleSignInAccount.getPhotoUrl();

            for (prog=10; prog <= 100; prog=prog+10){
                try {
                    Thread.sleep(500);
                    progressBar.setProgress(prog);
                    if(prog==100){

                        // Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();
                        if(!Admin_email.contains(email)){
                            status_name="User";
                            sig_inDataModel addUser=new sig_inDataModel(DisName,email,uriImage.toString());

                            Toast.makeText(this, email, Toast.LENGTH_LONG).show();
                            String mykey= databaseRef_userData.push().getKey();
                            databaseRef_userData.child(mykey).setValue(addUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
        //Toast.makeText(Login_page.this, "Loging SuccessFull", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }if(Admin_email.contains(email)){
                            status_name="Admin";
                            Toast.makeText(this, "Loging Admin ", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (InterruptedException e) {
          Toast.makeText(this, "Your SingIn Data Empty..."+e.getMessage(), Toast.LENGTH_SHORT).show();   e.printStackTrace();
                }
            }

        }else{
            Toast.makeText(this, "Your SingIn Data Empty...", Toast.LENGTH_SHORT).show();
        }
    }


 void AdminData(){

     databaseReference_Admin.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                 sig_inDataModel getItem=dataSnapshot1.getValue(sig_inDataModel.class);
                 getItem.setUser_key(dataSnapshot1.getKey());
                 Admin_email=  getItem.getUser_Email();
                 status_name="Admin";
               // Toast.makeText(getApplicationContext(), "Login Admin"+Admin_email, Toast.LENGTH_LONG).show();
             }
         }
         @Override
         public void onCancelled(DatabaseError error) {
             Toast.makeText(getApplicationContext(), "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
         }
     });
 }



}