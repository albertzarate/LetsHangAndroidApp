package com.example.theal.letshang;
//TODO -- add rum raisin font
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

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";

    private EditText email, password;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.pbLogin);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        checkIfLoggedIn();
    }

    public void LoginUser(View view){
        final String user_email = email.getText().toString();
        String user_password = password.getText().toString();
        if(user_email.isEmpty()||user_password.isEmpty()){
            Toast.makeText(this, "One of more fields were not filled out. Please try again.", Toast.LENGTH_SHORT).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "signInWithEmail: success");
                        finish();
                        Toast.makeText(Login.this, "Logged in  as " + user_email, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, HomePage.class));
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        //TODO -- see if email exists in system or not
                        Log.d(TAG, "signInWithEmail: failure");
                        Toast.makeText(Login.this, "Login unsuccessful. Please try again.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    public void gotoSignUp(View view){
        finish();
        startActivity(new Intent(this, SignUp.class));
    }

    public void checkIfLoggedIn(){
        if(mAuth.getCurrentUser()!=null){
            finish();
            Toast.makeText(Login.this, "Already Logged in. Redirected to home page.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login.this, HomePage.class));
        }
    }

}
