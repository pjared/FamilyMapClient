package com.example.familymap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "in onCreate(Bundle)");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, "in onCreateView(...)");
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //TextView titleTextView = view.findViewById(R.id.titleTextView);
        //titleTextView.setText(title);

        //Server host, port, user name, password, first name, last name, email, gender
        serverEditText = view.findViewById(R.id.serverHostField);
        portEditText = view.findViewById(R.id.portField);
        userNameEditText = view.findViewById(R.id.userNameField);
        passWordEditText = view.findViewById(R.id.passWordField);
        firstNameEditText = view.findViewById(R.id.firstNameField);
        lastNameEditText = view.findViewById(R.id.lastNameField);
        emailEditText = view.findViewById(R.id.emailField);

        return view;
    }
}
