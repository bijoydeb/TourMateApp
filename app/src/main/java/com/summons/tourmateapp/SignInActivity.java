package com.summons.tourmateapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.summons.tourmateapp.Database.SignUpManager;
import com.summons.tourmateapp.Utils.TourMateSharedPreference;


public class SignInActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    EditText userNameEditText;
    EditText passwordEditText;
    SignUpManager signUpManager;
    TourMateSharedPreference sharedPreference;
    CheckBox checkBox;
    public static boolean status = false;

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.context = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkBox=(CheckBox)findViewById(R.id.stayCheekBox);

        signUpManager = new SignUpManager(context);
        sharedPreference = new TourMateSharedPreference(context);
        userName = getIntent().getStringExtra("userName");

        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        userNameEditText.setText(userName);

    }

    public void signIn(View view) {
        String userName = userNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String pass;
        status = checkBox.isChecked();
        if (userName.equals("")) {
            Toast.makeText(context, "Enter Username !", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(context, "Enter password !", Toast.LENGTH_SHORT).show();
        } else {

            if (!signUpManager.userNameExits(userName).equals("0")) {
                pass = signUpManager.getUserPass(userName);

                if (pass.equals(password)) {
                    String userId = signUpManager.getUserId(userName);
                    sharedPreference.saveUserId(userId);
                    sharedPreference.saveLoginInfo(status,userName,password);
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(context, "Password is Wrong !", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(context, "Inavail UserName !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void registration(View view) {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }


}
