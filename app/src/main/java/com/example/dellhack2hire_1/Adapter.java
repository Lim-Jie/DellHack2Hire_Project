package com.example.dellhack2hire_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> dataList; // Your list of data

    public MyAdapter(List<String> dataList) {
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
        String data = dataList.get(position);
        holder.textView.setText(data); // Assuming you have a TextView with id textView in your card_item layout
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView; // Declare TextView here

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your views here
            textView = itemView.findViewById(R.id.EmailTextView); // Initialize TextView

            // Set OnClickListener on the itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click event
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String data = dataList.get(position);

                        Toast.makeText(itemView.getContext(), "Clicked item: " + data, Toast.LENGTH_SHORT).show();
                        moveToAnotherFragment(data);

                    }
                }
            });
        }

        private void moveToAnotherFragment(String data) {
            // Create a new instance of the fragment you want to move to
            OnBoarders onBoarders= new OnBoarders(data);
            // Pass data to the fragment using arguments
            Bundle bundle = new Bundle();
            bundle.putString("DATA_KEY", data);
            onBoarders.setArguments(bundle);
            // Get the FragmentManager
            FragmentManager fragmentManager = ((FragmentActivity)itemView.getContext()).getSupportFragmentManager();
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


