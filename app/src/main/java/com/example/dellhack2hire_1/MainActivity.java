package com.example.dellhack2hire_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dellhack2hire_1.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText EnteredEmail;
    EditText EnteredPassword;
    Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Verify user login using Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        //Instantiate EditText and button components
        EnteredEmail = findViewById(R.id.editTextEmail);
        EnteredPassword = findViewById(R.id.editTextPassword);
        LoginButton = findViewById(R.id.buttonLogin);

        LoginButton.setOnClickListener(v->{
            String emailVal, passwordVal;
            emailVal = EnteredEmail.getText().toString();
            passwordVal = EnteredPassword.getText().toString();
            verifyLogin(emailVal, passwordVal);
        });

        if(currentUser!=null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else{

        }

    }
    public void verifyLogin(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication success.",Toast.LENGTH_SHORT).show();
                            //Redirect to Home page after successful login
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));

                        } else {
                            //Notifies error to user after unsuccessful login
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}