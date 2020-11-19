package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ResultActivity extends AppCompatActivity {

    public void onTapLogOut(View view){
        logOut();
        Intent intent = new Intent(getApplicationContext(),StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onTapTryAgain(View view){
        Intent intent = new Intent(getApplicationContext(),QuizActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textView = findViewById(R.id.score);
        firebaseAuth = FirebaseAuth.getInstance();
        textView.setText(Integer.toString(QuizActivity.score)+"/10");
    }

    public void logOut(){
        firebaseAuth.signOut();
    }
}