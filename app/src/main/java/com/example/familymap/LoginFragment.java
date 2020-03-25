package com.example.familymap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import Proxy.LoginTask;

public class LoginFragment extends Fragment {
    private static final String LOG_TAG = "LoginFragment";

    private String title;

    private EditText serverEditText;
    private EditText portEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText userNameEditText;
    private EditText passWordEditText;
    private EditText emailEditText;
    private RadioButton gender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "in onCreate(Bundle)");
        super.onCreate(savedInstanceState);

    }

    public EditText getServerEditText() {
        return serverEditText;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, "in onCreateView(...)");
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        serverEditText = view.findViewById(R.id.serverHostField);
        portEditText = view.findViewById(R.id.portField);
        userNameEditText = view.findViewById(R.id.userNameField);
        passWordEditText = view.findViewById(R.id.passWordField);
        firstNameEditText = view.findViewById(R.id.firstNameField);
        lastNameEditText = view.findViewById(R.id.lastNameField);
        emailEditText = view.findViewById(R.id.emailField);
        gender = view.findViewById(R.id.radioGender);

        Button loginButton = view.findViewById(R.id.loginButton);
        Button registerButton = view.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LoginTask task = new LoginTask();
                try {
                    //unsure of how to set the data in the task
                    task.execute(new URL("www.icarly.com"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LoginTask task = new LoginTask();
                try {
                    task.execute(new URL("www.icarly.com"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        boolean checked = gender.isChecked();
        switch(gender.getId()) {
            case R.id.radioMale:
                if(checked) {
                    //gender is male
                }
                break;
            case R.id.radioFemale:
                if(checked) {

                }
                break;
        }

        return view;
    }
}
