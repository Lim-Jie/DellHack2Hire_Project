package com.example.dellhack2hire_1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<List<String>> dataList; // Your list of data


    public MyAdapter(List<List<String>> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> data = dataList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar Submission;
        ProgressBar Progress;
        public TextView textView; // Declare TextView here
        public TextView progressTextView; // Declare TextView for progress
        public TextView submissionsTextView; // Declare TextView for submissions

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your views here
            textView = itemView.findViewById(R.id.EmailTextView); // Initialize TextView
            progressTextView = itemView.findViewById(R.id.Progress); // Initialize progress TextView
            submissionsTextView = itemView.findViewById(R.id.Submissions); // Initialize TextView
            Submission = itemView.findViewById(R.id.Submission_ProgressBar);
            Progress = itemView.findViewById(R.id.Progress_ProgressBar);

            // Set OnClickListener on the itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click event
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String data = dataList.get(position).get(0);

                        Toast.makeText(itemView.getContext(), "Clicked item: " + data, Toast.LENGTH_SHORT).show();
                        moveToAnotherFragment(data);
                    }
                }
            });
        }

        public void bind(List<String> data) {
            textView.setText(data.get(0)); // Assuming the first item is email
            progressTextView.setText(data.get(1)); // Assuming the second item is progress
            submissionsTextView.setText(data.get(2)); // Assuming the third item is submissions

            try{
                int progressInt = Integer.parseInt(data.get(1));
                int submissionInt = Integer.parseInt(data.get(2));
                Progress.setProgress(progressInt);
                Submission.setProgress(submissionInt);
                System.out.println("Progress Value: "+progressInt*100);


            }catch (Exception e){
                System.out.println("bind error"+e);
            }

        }

        private void moveToAnotherFragment(String data) {
            // Create a new instance of the fragment you want to move to
            OnBoarders onBoarders = new OnBoarders(data);
            // Pass data to the fragment using arguments
            Bundle bundle = new Bundle();
            bundle.putString("DATA_KEY", data);
            onBoarders.setArguments(bundle);
            // Get the FragmentManager
            FragmentManager fragmentManager = ((FragmentActivity) itemView.getContext()).getSupportFragmentManager();
            // Begin a transaction
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // Replace the current fragment with the new one
            fragmentTransaction.replace(R.id.FragmentContainerMain, onBoarders);
            // Add transaction to the back stack (optional)
            fragmentTransaction.addToBackStack(null);
            // Commit the transaction
            fragmentTransaction.commit();
        }
    }
}

