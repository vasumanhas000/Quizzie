package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    public void onClick(View view){
        switch (view.getId()){
            case R.id.logInButton:{
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.signUpButton:{
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        goToHome(currentUser);
    }

    public void goToHome(FirebaseUser user){
        if(user!=null){
            Intent intent = new Intent(getApplicationContext(),HomePage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}