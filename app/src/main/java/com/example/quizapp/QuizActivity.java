package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityButtonController;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    ArrayList<MaterialCardView> materialCardViews;
    int currentIndex=0;
    int selectedOption=-1;
    TextView questionNumber,question,option1Text,option2Text,option3Text;
    MaterialCardView firstOption,secondOption,thirdOption;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    Button nextButton;
    CountDownTimer countDownTimer;

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
        countDownTimer = new CountDownTimer(30000,1000) {
            int progress =100;
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTick(long millisUntilFinished) {
                progress = progress-3;
                TextView progressBarText = findViewById(R.id.progressBarInsideText);
                progressBarText.setText(Integer.toString((int) millisUntilFinished/1000));
                progressBar.setProgress(progress);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFinish() {
                progress=100;
                next();
            }
        };
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
     progressBar = findViewById(R.id.progress_bar);
     linearLayout = findViewById(R.id.main_linear_layout);
     materialCardViews= new ArrayList<>();
     materialCardViews.add(firstOption);
     materialCardViews.add(secondOption);
     materialCardViews.add(thirdOption);
    }

    public void updateUI(int index){
      linearLayout.setVisibility(View.VISIBLE);
      questionNumber.setText("Question "+index+1+"/10");
      question.setText(arrayList.get(index).getQuestion());
      option1Text.setText(arrayList.get(index).getOption1());
      option2Text.setText(arrayList.get(index).getOption2());
      option3Text.setText(arrayList.get(index).getOption3());
    }

    public void startQuiz(){
        progressBar.setProgress(100);
        countDownTimer.start();
        updateUI(currentIndex);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onTapNext(View view){
      countDownTimer.cancel();
      next();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void next(){
            int correctOption = arrayList.get(currentIndex).getCorrectOption();
            if(selectedOption!=-1){
            if(selectedOption==correctOption){
            materialCardViews.get(correctOption).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5efc82")));
            materialCardViews.get(correctOption).setStrokeColor(Color.parseColor("#5efc82"));}
            else{
                materialCardViews.get(correctOption).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5efc82")));
                materialCardViews.get(correctOption).setStrokeColor(Color.parseColor("#5efc82"));
                materialCardViews.get(selectedOption).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0134")));
                materialCardViews.get(selectedOption).setStrokeColor(Color.parseColor("#FF0134"));
            }}
            else{
                materialCardViews.get(correctOption).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5efc82")));
                materialCardViews.get(correctOption).setStrokeColor(Color.parseColor("#5efc82"));
            }
            CountDownTimer newCountDownTimer = new CountDownTimer(1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    firstOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1c223b")));
                    secondOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1c223b")));
                    thirdOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1c223b")));
                    materialCardViews.get(arrayList.get(currentIndex).getCorrectOption()).setStrokeColor(Color.parseColor("#107eeb"));
                    materialCardViews.get(selectedOption).setStrokeColor(Color.parseColor("#107eeb"));
                    currentIndex++;
                    startQuiz();
                }
            };
      newCountDownTimer.start();
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
               startQuiz();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}