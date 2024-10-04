package com.example.glucemiccalcaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CarbsIn100g extends AppCompatActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbs_in100g);


    }

    public void CalcCarbsIn100(View v){

        EditText carbs = findViewById(R.id.editTextCarbsPerServing);
        EditText serving = findViewById(R.id.editTextServing);
        TextView answer = findViewById(R.id.textViewAnswerCarbsIn100g);
        double carbsNum;
        double servingNum;
        double answerNum;


        if(carbs.getText().equals("") || serving.getText().equals("")){
            answer.setText("");
            return;
        }

        try {
            carbsNum =  Integer.parseInt(carbs.getText().toString());
            servingNum = Integer.parseInt(serving.getText().toString());
        }
        catch(NumberFormatException exc){
            answer.setText("Error, make sure input is numeric");
            return;
        }

        answerNum = (carbsNum/servingNum) * 100;

        answer.setText("The amount of carbs in 100 g  is: "+answerNum+"g");

    }


    public void gotoMenu(View v){
        Intent i = new Intent(CarbsIn100g.this, MainActivity.class);
        startActivity(i);
    }


}