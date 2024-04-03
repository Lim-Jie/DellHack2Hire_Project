package com.example.dellhack2hire_1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OnBoarders extends Fragment {

    public String emailVisit;
    FirebaseFirestore Firestore;
    private ListView listView;
    private List<Map<String, Object>> dataList;

    public OnBoarders( String emailVisit){
        this.emailVisit=emailVisit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_on_boarders, container, false);
        Firestore = FirebaseFirestore.getInstance();

        Firestore = FirebaseFirestore.getInstance();
        listView = view.findViewById(R.id.listView);
        dataList = new ArrayList<>();

        loadDataFromFirestore();








        return view;
    }

    private void loadDataFromFirestore() {
        Firestore.collection("Users")
                .document(emailVisit)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Convert the document data to a HashMap
                                Map<String, Object> data = document.getData();
                                dataList.add(data);
                                // Populate ListView
                                System.out.println("TEST11"+dataList );
                                populateListView();
                            } else {
                                Log.d("Firestore", "No such document");
                                Toast.makeText(getContext(), "No such document in Firestore", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("Firestore", "Error getting document: ", task.getException());
                            Toast.makeText(getContext(), "Error getting document from Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void populateListView() {
        System.out.println("populateListView");
        List<String> displayList = new ArrayList<>();
        System.out.println("data list"+dataList);
        for (Map<String, Object> data : dataList) {
            // Iterate over HashMap and construct a display string
            StringBuilder displayString = new StringBuilder();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                displayString.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            displayList.add(displayString.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                R.layout.listview2, displayList);
        listView.setAdapter(adapter);
    }






}

//    private void populateListView() {
//        System.out.println("populateListView");
//        List<String> displayList = new ArrayList<>();
//        System.out.println("data list"+dataList);
//        // Define the keys used in the HashMap
//        String[] from = new String[] {"Name", "Role"}; // Replace with your actual keys
//
//        // Define the views where the data will be displayed
//        int[] to = new int[] {R.id.textView1, R.id.textView2}; // Replace with your actual TextView IDs
//
//        // Create a SimpleAdapter
//        SimpleAdapter adapter = new SimpleAdapter(getContext(), dataList,
//                R.layout.listview, from, to);
//
//        // Set the adapter to the ListView
//        listView.setAdapter(adapter);
//    }