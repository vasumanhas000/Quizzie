package com.example.quizapp;

public class Question {

    private String question,option1,option2,option3;
    private int correctOption;

    public Question(){}

    public Question(String question,String option1,String option2,String option3,int correctOption){
     this.correctOption=correctOption;
     this.option1=option1;
     this.option2=option2;
     this.option3=option3;
     this.question=question;
    }
    public String getQuestion(){return this.question;}
    public String getOption1(){return this.option1;}
    public String getOption2(){return this.option2;}
    public String getOption3(){return this.option3;}
    public int getCorrectOption(){return this.correctOption;}
}
