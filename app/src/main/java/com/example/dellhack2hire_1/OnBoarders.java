package com.example.dellhack2hire_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class OnBoarders extends Fragment {

    public String emailVisit;

    public OnBoarders( String emailVisit){
        this.emailVisit=emailVisit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarders, container, false);
    }
}