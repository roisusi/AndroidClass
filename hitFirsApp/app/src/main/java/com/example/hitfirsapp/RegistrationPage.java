package com.example.hitfirsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationPage extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void registerFucn(View view) {

        EditText nameText = findViewById(R.id.UserLable);
        String name = nameText.getText().toString();

        EditText emailText = findViewById(R.id.EmaiLable);
        String email = emailText.getText().toString();

        EditText passText = findViewById(R.id.PasswordRegLable);
        String password = passText.getText().toString();

        EditText addressTest = findViewById(R.id.AddressLable);
        String address = addressTest.getText().toString();

        EditText phoneText = findViewById(R.id.PhoneLable);
        String phone = phoneText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();

                            Toast.makeText(RegistrationPage.this, "Registration Success", Toast.LENGTH_SHORT).show();

                            // Write a message to the database
                            FirebaseDatabase database = FirebaseDatabase.getInstance(); // connect to firebase

                            // The child meas all the data will be below it
                            DatabaseReference myRef = database.getReference("Persons").child(uid); //the "Nativ" the head of the Baselines
                            Person person = new Person(name,email,address,phone);
                            
                            myRef.setValue(person);

                            Intent intent = new Intent(RegistrationPage.this,MainActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistrationPage.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
}