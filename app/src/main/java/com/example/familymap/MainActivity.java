package com.example.familymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import Proxy.DataCache;


public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private MapFragment mapFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        //loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        loginFragment = new LoginFragment(this);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, loginFragment)
                .commit();
    }

    public void switchFragments() {
        //Pass in main activity
        mapFragment = new MapFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, mapFragment);
        ft.addToBackStack(null);
        ft.commit();
        //fragmentManager.beginTransaction().replace(R.id.fragment_container, mapFragment);
    }


}