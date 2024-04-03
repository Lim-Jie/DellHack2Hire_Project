package com.example.dellhack2hire_1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class Profile extends Fragment {

//    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    Button logoutButton;
    private TextView profileUsername, profileRoleUser, profileEmailUser, profileContactNumUser, profileGenderUser, profileDoBUser, profileResUser;
    private FirebaseUser currentUser;
    private DatabaseReference userRef;

//    public Profile() {
//        // Required empty public constructor
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
//        firestore= FirebaseFirestore.getInstance();

        logoutButton = view.findViewById(R.id.buttonLogout);
        logoutButton.setOnClickListener(v->{
            logout();
        });

        profileUsername = view.findViewById(R.id.profileUsername);
        profileRoleUser = view.findViewById(R.id.profileRoleUser);
        profileEmailUser = view.findViewById(R.id.profileEmailUser);
        profileContactNumUser = view.findViewById(R.id.profileContactNumUser);
        profileGenderUser = view.findViewById(R.id.profileGenderUser);
        profileDoBUser = view.findViewById(R.id.profileDoBUser);
        profileResUser = view.findViewById(R.id.profileResUser);

        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Get reference to the user data in the Firebase Realtime Database
            userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        if (dataSnapshot.child("Role").exists()) {
                            String username = dataSnapshot.child("Role").getValue(String.class);
                            profileRoleUser.setText(username);
                        }

                        if (dataSnapshot.child("Name").exists()) {
                            String username = dataSnapshot.child("Name").getValue(String.class);
                            profileUsername.setText(username);
                        }

                        if (dataSnapshot.child("contactNum").exists()) {
                            String contactNum = dataSnapshot.child("contactNum").getValue(String.class);
                            profileContactNumUser.setText(contactNum);
                        }

                        if (dataSnapshot.child("gender").exists()) {
                            String gender = dataSnapshot.child("gender").getValue(String.class);
                            profileGenderUser.setText(gender);
                        }

                        if (dataSnapshot.child("dob").exists()) {
                            String dob = dataSnapshot.child("dob").getValue(String.class);
                            profileDoBUser.setText(dob);
                        }

                        // Load the profile image using Picasso (or any image loading library of your choice)
//                        if (dataSnapshot.child("profileImageUrl").exists()) {
//                            String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue(String.class);
//                            Picasso.get().load(profileImageUrl).into(uploadProfilePic);
//                        }
                    }

                    // Get email and profileResUser from Firebase Authentication
                    String email = currentUser.getEmail();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String creationDate = dateFormat.format(currentUser.getMetadata().getCreationTimestamp());

                    profileEmailUser.setText(email);
                    profileResUser.setText(creationDate);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

    return view;
    }

    private void logout() {
        firebaseAuth.signOut();
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Transition to MainActivity after logging out
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}