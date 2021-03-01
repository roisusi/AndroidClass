package com.example.hitfirsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private SharedPreferences pref;
    private Editor editor;
    private static boolean logFlag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.pref = getApplicationContext().getSharedPreferences("hitFirstApp", MODE_PRIVATE); // for getting and save on computer
        this.editor = this.pref.edit(); // for editing
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login_page);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (logFlag)
            this.tryLogIn();
        else { //clear cache so it not remember you
            editor.putString("email", null);
            editor.putString("password", null);
            editor.apply();
        }
    }

    public void loginFunc(View view) {
        EditText emailText = findViewById(R.id.emailLable);
        String email = emailText.getText().toString();

        EditText passText = findViewById(R.id.passeordLable);
        String password = passText.getText().toString();

        if (email.isEmpty()  || email == null|| password == null || password.isEmpty())
        {
            Toast.makeText(LoginPage.this, "Empty Cells", Toast.LENGTH_SHORT).show();
        }
        else {
           this.login(email, password);
        }
    }

    public void registerFucn(View view) {
        Intent intent = new Intent(LoginPage.this,RegistrationPage.class);
        startActivity(intent);
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    setLoginDetails(email, password);
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    String uid = user.getUid();
                    Toast.makeText(LoginPage.this, "Login Success ID : " + uid, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginPage.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        });
    }

    private void tryLogIn()  {
        final String email = this.pref.getString("email", null);
        final String password = this.pref.getString("password", null);
        if (email != null && password != null) {
            this.login(email, password);
        }
    }

    private void setLoginDetails(String email, String password) {
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    public void cleanLoginDetails() { // call this when logout
        setLoginDetails(null, null);
    }

    public void setFlag(boolean flag){
        logFlag = flag;
    }
}

