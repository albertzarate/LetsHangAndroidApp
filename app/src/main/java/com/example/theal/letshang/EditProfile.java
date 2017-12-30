package com.example.theal.letshang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    private static final String TAG = "EditProfile";

    private ProgressBar progressBar;
    private TextView email;
    private EditText nameField, birthdateField, phoneField;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressBar = findViewById(R.id.pbEditProfile);
        email = findViewById(R.id.tvEmail);
        nameField = findViewById(R.id.etFullName);
        birthdateField = findViewById(R.id.etBirthdate);
        phoneField = findViewById(R.id.etPhone);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        email.setText("Email: " + user.getEmail());

        progressBar.setVisibility(View.VISIBLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = mAuth.getCurrentUser();
                UserInfo userInfo = new UserInfo();
                userInfo=dataSnapshot.child(user.getUid()).getValue(UserInfo.class);
                nameField.setText(userInfo.getFullName());
                birthdateField.setText(userInfo.getBirthdate());
                phoneField.setText(userInfo.getPhone());
                Log.d(TAG, "showData: fullName: " + userInfo.getFullName());
                Log.d(TAG, "showData: birthdate: " + userInfo.getBirthdate());
                Log.d(TAG, "showData: phone: " + userInfo.getPhone());
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void save(View view){
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = nameField.getText().toString().trim();
                String birthdate = birthdateField.getText().toString().trim();
                String phone = phoneField.getText().toString().trim();
                FirebaseUser user = mAuth.getCurrentUser();
                UserInfo userInfo = new UserInfo();
                userInfo=dataSnapshot.child(user.getUid()).getValue(UserInfo.class);
                Integer mostRecentSelection = userInfo.getMostRecentSelection();
                Integer activityCount = userInfo.getActivityCount();
                UserInfo updatedUserInfo = new UserInfo(name, birthdate, phone, mostRecentSelection, activityCount);
                myRef.child(user.getUid()).setValue(updatedUserInfo);
                Toast.makeText(EditProfile.this, "Profile updated.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Update Profile: success");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

}
