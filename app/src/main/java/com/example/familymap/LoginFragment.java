package com.example.familymap;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

import Proxy.LoginTask;
import Proxy.RegisterTask;

public class LoginFragment extends Fragment {
    private static final String LOG_TAG = "LoginFragment";

    private EditText serverEditText;
    private EditText portEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText userNameEditText;
    private EditText passWordEditText;
    private EditText emailEditText;
    private RadioGroup gender;
    private String userGender = null;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkRegisterValues();
            checkLoginValues();
        }
    };

    private void checkRegisterValues(){
        Button b = (Button) view.findViewById(R.id.registerButton);

        String serverText = serverEditText.getText().toString();
        String portText = portEditText.getText().toString();
        String firstNameText = firstNameEditText.getText().toString();
        String lastNameText = lastNameEditText.getText().toString();
        String userNameText = userNameEditText.getText().toString();
        String passWordText = passWordEditText.getText().toString();
        String emailText = emailEditText.getText().toString();

        if(serverText.equals("") || portText.equals("") || firstNameText.equals("") ||
            lastNameText.equals("") || userNameText.equals("") || passWordText.equals("") ||
            emailText.equals("") || userGender == null) {
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }

    private void checkLoginValues(){
        Button b = (Button) view.findViewById(R.id.loginButton);

        String serverText = serverEditText.getText().toString();
        String portText = portEditText.getText().toString();
        String userNameText = userNameEditText.getText().toString();
        String passWordText = passWordEditText.getText().toString();

        if(serverText.equals("")|| portText.equals("") ||
                userNameText.equals("") || passWordText.equals("")){
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }

    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, "in onCreateView(...)");
        view = inflater.inflate(R.layout.fragment_login, container, false);

        serverEditText = view.findViewById(R.id.serverHostField);
        portEditText = view.findViewById(R.id.portField);
        userNameEditText = view.findViewById(R.id.userNameField);
        passWordEditText = view.findViewById(R.id.passWordField);
        firstNameEditText = view.findViewById(R.id.firstNameField);
        lastNameEditText = view.findViewById(R.id.lastNameField);
        emailEditText = view.findViewById(R.id.emailField);
        gender = view.findViewById(R.id.radioGender);

        serverEditText.addTextChangedListener(mTextWatcher);
        portEditText.addTextChangedListener(mTextWatcher);
        userNameEditText.addTextChangedListener(mTextWatcher);
        passWordEditText.addTextChangedListener(mTextWatcher);
        firstNameEditText.addTextChangedListener(mTextWatcher);
        lastNameEditText.addTextChangedListener(mTextWatcher);
        emailEditText.addTextChangedListener(mTextWatcher);

        checkRegisterValues();
        checkLoginValues();

        Button loginButton = view.findViewById(R.id.loginButton);
        Button registerButton = view.findViewById(R.id.registerButton);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton) view.findViewById(checkedId);
                //Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                userGender = rb.getText().toString();
                checkRegisterValues();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //String userName, String password, String email, String firstName, String lastName, String gender
                RegisterTask task = new RegisterTask();
                try {
                    //unsure of how to set the data in the task
                    task.execute(new URL("https://" + serverEditText.toString() +
                                            ":" + portEditText.toString() + "/user/register"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //String userName, String password
            public void onClick(View v) {
                LoginTask task = new LoginTask();
                try {
                    task.execute(new URL("https://" + serverEditText.toString() +
                            ":" + portEditText.toString() + "/user/login"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "in onCreate(Bundle)");
        super.onCreate(savedInstanceState);

    }
}
