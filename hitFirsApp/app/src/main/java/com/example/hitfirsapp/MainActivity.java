package com.example.hitfirsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private int num1;
    private int num2;
    private int calc;
    char op;
    private Animation animation;
    private Button b;
    TextView textView;

    private TextView textViewUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                textViewUser = findViewById(R.id.textViewHello);
                textViewUser.setText("Hello, " + value.getName());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



        //R means Layout 1 and i must take the ID from THAT LAYER
        textView = findViewById(R.id.textViewResult); //all pakadim not just 1 layout
    }

    public void funcNumber(View view) {
        //View is General Object and i must cast it to what it received
        b = (Button) view;
        textView.append(b.getText());
        animation = AnimationUtils.loadAnimation(this,R.anim.animattionnumberbuttons);
        view.startAnimation(animation);
    }

    public void operation(View view) {
        num1 = Integer.parseInt(textView.getText().toString());
        op = ((Button)view).getText().charAt(0);
        textView.setText("");
        Animation animation;
        animation = AnimationUtils.loadAnimation(this,R.anim.animationbutton);
        view.startAnimation(animation);

    }

    public void calculate(View view) {

        num2 = Integer.parseInt(textView.getText().toString());
        switch (op) {
            case '+':
                calc = num2 + num1;
                break;
            case '-':
                calc = num2 - num1;
                break;
            case '*':
                calc = num2 * num1;
                break;
            case '/':
                if (num1 != 0 )
                    calc = num2 / num1;
                else
                    calc = 0;
                break;
        }
        String text = String.valueOf(calc);
        textView.setText(text);

    }

    public void Clear(View view){

        textView.setText("");

    }


    public void accountInfo(View view) {
        Intent intent = new Intent(MainActivity.this, AccountInformation.class);
        startActivity(intent);

    }

    public void cleanLoginDetails(View view) {
        LoginPage loginPage = new LoginPage();
        loginPage.setFlag(false);
        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);
    }

}
