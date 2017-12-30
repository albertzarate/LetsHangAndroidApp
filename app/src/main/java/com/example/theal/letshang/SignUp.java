package com.example.theal.letshang;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";

    private EditText fullName, password, email, birthdate, phone;
    private Integer user_mostRecentSelection, user_activityCount;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = findViewById(R.id.etName);
        password = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        birthdate = findViewById(R.id.etBirthdate);
        phone = findViewById(R.id.etPhone);
        progressBar = findViewById(R.id.pbRegister);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onStart() {
        super.onStart();
        checkIfLoggedIn();
    }

    //When user presses "Already have account" button, this function is called
    public void gotoLogin(View view){
        finish();
        startActivity(new Intent(SignUp.this, Login.class));
    }

    //registers user
    public void signUpUser(View view){
        final String user_name = fullName.getText().toString().trim();
        final String user_password = password.getText().toString().trim();
        final String user_email = email.getText().toString().trim();
        final String user_birthdate = birthdate.getText().toString().trim();
        final String user_phone = phone.getText().toString().trim();
        user_mostRecentSelection = 0;
        user_activityCount = 0;

        if(user_name.isEmpty()||user_password.isEmpty()||user_email.isEmpty()||user_birthdate.isEmpty()||user_phone.isEmpty()){
            Toast.makeText(this, "One of more fields were not filled out. Please try again.", Toast.LENGTH_SHORT).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        finish();
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserInfo userInfo = new UserInfo(user_name, user_birthdate, user_phone, user_mostRecentSelection, user_activityCount);
                        myRef.child(user.getUid()).setValue(userInfo);
                        Log.d(TAG, "createUserWithEmail: success");
                        mAuth.signInWithEmailAndPassword(user_email, user_password);
                        Toast.makeText(SignUp.this, "Registration Successful! Logged in.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, HomePage.class));
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        Log.d(TAG, "createUserWithEmail: failure");
                        Toast.makeText(SignUp.this, "This email is already registered. Login or try again.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    //checks if user is already logged in. return T if they are, F is they aren't
    public void checkIfLoggedIn(){
        if(mAuth.getCurrentUser()!=null){
            finish();
            Toast.makeText(SignUp.this, "You cannot make an account while logged in. Redirected to home page.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUp.this, HomePage.class));
        }
    }


}
