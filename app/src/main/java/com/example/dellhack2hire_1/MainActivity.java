package com.example.dellhack2hire_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dellhack2hire_1.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String currentEmail;
    FirebaseUser currentUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    EditText EnteredEmail;
    EditText EnteredPassword;
    Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Verify user login using Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser=  firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        //Instantiate EditText and button components
        EnteredEmail = findViewById(R.id.editTextEmail);
        EnteredPassword = findViewById(R.id.editTextPassword);
        LoginButton = findViewById(R.id.buttonLogin);



        //Collect User and Password to verify using Firebase AUTH using verifyLogin() method


        if(currentUser!=null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        LoginButton.setOnClickListener(v -> {
            String enteredEmailString = EnteredEmail.getText().toString().trim(); // Trim removes leading and trailing spaces
            String enteredPasswordString = EnteredPassword.getText().toString().trim();

            if (!enteredEmailString.isEmpty() && !enteredPasswordString.isEmpty()) {
                verifyLogin(enteredEmailString, enteredPasswordString);
            } else {
                // Show a toast message indicating that email or password is empty
                Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void verifyLogin(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser=  firebaseAuth.getCurrentUser();
                            if(currentUser!=null){
                                currentEmail = currentUser.getEmail();
                                Log.d("Main_Activity Email: ",currentEmail);
                                checkRole();
                            }else{
                                Log.d("currentUser", "Is empty");
                            }


                        } else {
                            //Notifies error to user after unsuccessful login
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Only allow if HR or M role
    public void checkRole() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (currentEmail != null) {
                firebaseFirestore.collection("Users")
                        .document(currentEmail)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                String role = documentSnapshot.getString("Role");
                                if (role.equals("HR") || role.equals("M")) {
                                    Toast.makeText(MainActivity.this, "Authentication success.",Toast.LENGTH_SHORT).show();
                                    Log.d("Main_Activity Role", role);
                                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                } else {
                                  Toast.makeText(MainActivity.this, "You do not have permission", Toast.LENGTH_SHORT).show();
                                  firebaseAuth.signOut();
                                }
                            }
                            else {
                                Log.d("Main_Activity Role", "Document does not exist");
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.e("Role", "Error retrieving document", e);
                        });
            }
        } else {
            Log.d("Role", "No user is currently signed in");
        }
    }


}







