package com.example.dellhack2hire_1;

import android.os.Bundle;

import com.example.dellhack2hire_1.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dellhack2hire_1.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    public String currentEmail;
    FirebaseAuth firebaseAuth;

    private AppBarConfiguration appBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());

        //GET CURRENT USER EMAIL STRING
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        currentEmail = currentUser.getEmail();
        Log.d("Home_Activity Email: ",currentEmail);


        if(savedInstanceState==null){
            replaceFragment(new Home());
        }

        binding.bottom.setOnItemSelectedListener(item -> {
            int no=item.getItemId();

            if(R.id.item_1==no){ //HomePageFragment Button 1 (All logics are moved to Fragment instead of activity)
                replaceFragment(new Home());
                return true;
            }
            if(R.id.item_2==no){ //HomePageFragment Button 1 (All logics are moved to Fragment instead of activity)
                replaceFragment(new Home());
                return true;
            }
            if(R.id.item_3==no){ //HomePageFragment Button 1 (All logics are moved to Fragment instead of activity)
                replaceFragment(new Home());
                return true;
            }
            else{ return false;}
        });
        setContentView(binding.getRoot());
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.FragmentContainerMain, fragment);
        ft.commit();
    }
}