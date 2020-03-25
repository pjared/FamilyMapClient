package com.example.familymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        loginFragment = new LoginFragment();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, loginFragment)
                .commit();
    }
}
