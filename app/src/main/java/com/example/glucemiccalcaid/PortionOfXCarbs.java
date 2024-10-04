package com.example.glucemiccalcaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PortionOfXCarbs extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portion_of_xcarbs);


    }

    public void calcPortion(View v){

        EditText carbsRatio =  findViewById(R.id.editTextCarbsRatio);
        EditText desiredCarbs = findViewById(R.id.editTextDesiredCarbs);
        TextView portionAnswer = findViewById(R.id.portionAnswer);
        double carbsRatioNum;
        double desiredCarbsNum;
        double answer;

        if(carbsRatio.getText().equals("") || desiredCarbs.getText().equals("")){
            portionAnswer.setText("");
            return;
        }

        try{
            carbsRatioNum =  Integer.parseInt(carbsRatio.getText().toString());
            desiredCarbsNum =  Integer.parseInt(desiredCarbs.getText().toString());

        }
        catch(NumberFormatException exc){
            portionAnswer.setText("Error, make sure input is numeric");
            return;
        }

        answer = desiredCarbsNum / (carbsRatioNum / 100);


        portionAnswer.setText("The portion having "+desiredCarbsNum+"g of carbs is: "+Double.toString(answer)+"g.");

        return;
    }

    public void gotoMenu(View v){
        Intent i = new Intent(PortionOfXCarbs.this, MainActivity.class);
        startActivity(i);
    }

}