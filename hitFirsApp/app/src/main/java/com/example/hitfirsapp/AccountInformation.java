package com.example.hitfirsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountInformation extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView textViewUser;
    private TextView textViewEmail;
    private TextView textViewAddress;
    private TextView textViewPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference myRef = database.getReference("Persons").child(uid);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Person value = dataSnapshot.getValue(Person.class);
                textViewUser = findViewById(R.id.textViewUserLable);
                textViewUser.setText(value.getName());
                textViewEmail = findViewById(R.id.textViewEmailLable);
                textViewEmail.setText(value.getEmail());
                textViewAddress = findViewById(R.id.textViewAddressLable);
                textViewAddress.setText(value.getAddress());
                textViewPhone = findViewById(R.id.textViewPhonelLable);
                textViewPhone.setText(value.getPhone());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }


    public void backToCalc(View view) {

        Intent intent = new Intent(AccountInformation.this,MainActivity.class);
        startActivity(intent);
    }
}