package com.example.dellhack2hire_1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    Button logoutButton;
    public String currentEmail;
    List<List<String>> listOfNames;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_home, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();

        logoutButton = view.findViewById(R.id.buttonLogout);
        logoutButton.setOnClickListener(v->{
            logout();
        });

        HomeActivity homeActivity = (HomeActivity) getActivity();
        currentEmail = homeActivity.currentEmail;
        Log.d("Home_Fragment Email: ",currentEmail);


        //LOAD DATA FROM FIRESTORE OF ONBOARDERS ROLE = "ON"
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LoadData(new OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List<List<String>> documentIds) {
                listOfNames = documentIds;
                // Initialize RecyclerView adapter with loaded data
                MyAdapter adapter = new MyAdapter(listOfNames);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        //all checked ^^^ DO NOT MODIFY













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

    public interface OnDataLoadedListener {
        void onDataLoaded(List<List<String>> documentIds);
    }

    public void LoadData(OnDataLoadedListener listener) {
        firestore.collection("Users")
                .whereEqualTo("Role", "ON")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<List<String>> dataList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String documentId = document.getId();
                        String progress = document.getString("Progress");
                        String submission = document.getString("Submissions");

                        // Add document ID, progress, and submission to a list
                        List<String> documentData = new ArrayList<>();
                        documentData.add(documentId);
                        documentData.add(progress);
                        documentData.add(submission);

                        // Add this list to the main data list
                        dataList.add(documentData);
                    }
                    listener.onDataLoaded(dataList); // Callback to update List<List<String>>
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    Log.e("Firestore", "Error getting documents: ", e);
                });
    }
























}