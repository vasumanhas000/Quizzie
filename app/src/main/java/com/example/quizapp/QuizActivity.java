package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityButtonController;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class QuizActivity extends AppCompatActivity {
    ArrayList<Question> arrayList;
    int currentIndex=0;
    int selectedOption;
    TextView questionNumber,question,option1Text,option2Text,option3Text;
    MaterialCardView firstOption,secondOption,thirdOption;
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        findViewByID();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getQuestions();
    }


    public void findViewByID(){
     questionNumber=findViewById(R.id.questionNumberText);
     question=findViewById(R.id.questionText);
     option1Text = findViewById(R.id.firstOptionText);
     option2Text = findViewById(R.id.secondOptionText);
     option3Text = findViewById(R.id.thirdOptionText);
     nextButton = findViewById(R.id.nextButton);
     firstOption = findViewById(R.id.firstOption);
     secondOption = findViewById(R.id.secondOption);
     thirdOption = findViewById(R.id.thirdOption);
    }

    public void updateUI(){
    }

    public void startQuiz(){

    }

    public void onTapNext(View view){

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onTapOption(View view){
        switch (view.getId()){
            case R.id.firstOption:{
                selectedOption=0;
                firstOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#107eeb")));
                secondOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1c223b")));
                thirdOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1c223b")));
                break;
            }
            case R.id.secondOption:{
                selectedOption=1;
                firstOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1c223b")));
                secondOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#107eeb")));
                thirdOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1c223b")));
                break;
            }
            case R.id.thirdOption:{
                selectedOption=2;
                firstOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1c223b")));
                secondOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1c223b")));
                thirdOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#107eeb")));
                break;
            }
        }
    }

    public void getQuestions(){
        arrayList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot snapshot1 : snapshot.getChildren()){
                   String question = Objects.requireNonNull(snapshot1.child("question").getValue()).toString();
                   String option1 = Objects.requireNonNull(snapshot1.child("option1").getValue()).toString();
                   String option2=Objects.requireNonNull(snapshot1.child("option2").getValue()).toString();
                   String option3 = Objects.requireNonNull(snapshot1.child("option3").getValue()).toString();
                   String correct =Objects.requireNonNull(snapshot1.child("correct").getValue()).toString();
                   int correctOption = Integer.parseInt(correct);
                   arrayList.add(new Question(question,option1,option2,option3,correctOption));
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}