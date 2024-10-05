package com.example.glucemiccalcaid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DishBuilding extends AppCompatActivity {

    double carbsAccum;
    double foodAccum;
    double SUMCarbsPer100g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_building);

    }


    public void addIngredients(View v){
        EditText carbsRatio = findViewById(R.id.editTextCarbsRatioOfIngredient);
        EditText amount = findViewById(R.id.editTextIngredientAmount);
        TextView lblHelp = findViewById(R.id.lblHelp);

        double carbsRatioNum;
        double amountNum;
        double carbsInIngredient;


        if(carbsRatio.getText().equals("") || amount.getText().equals("")){
            lblHelp.setText("Error, one of the fields is empty");
            return;
        }

        try{

            carbsRatioNum = Integer.parseInt(carbsRatio.getText().toString());
            amountNum = Integer.parseInt(amount.getText().toString());

        }catch(NumberFormatException exc){
            lblHelp.setText("Error, make sure the input is numeric");
            return;
        }

        carbsInIngredient = (carbsRatioNum / 100) * amountNum;

        carbsAccum = carbsAccum + amountNum;
        foodAccum = foodAccum + carbsInIngredient;
        SUMCarbsPer100g = carbsAccum / foodAccum;
        SUMCarbsPer100g = Math.floor(SUMCarbsPer100g * 100) / 100;


        ((TextView)findViewById(R.id.lblTotalCarbs)).setText(Double.toString(carbsAccum));
        ((TextView)findViewById(R.id.lblTotalFood)).setText(Double.toString(foodAccum));
        ((TextView)findViewById(R.id.lblSUMcarbsPer100g)).setText(Double.toString(SUMCarbsPer100g));
    }


    public void resetAcc(View v){
        EditText carbsRatio = findViewById(R.id.editTextCarbsRatioOfIngredient);
        EditText amount = findViewById(R.id.editTextIngredientAmount);
        TextView lblHelp = findViewById(R.id.lblHelp);

        carbsAccum = 0;
        foodAccum = 0;
        SUMCarbsPer100g = 0;

        ((TextView)findViewById(R.id.lblTotalCarbs)).setText(Double.toString(carbsAccum));
        ((TextView)findViewById(R.id.lblTotalFood)).setText(Double.toString(foodAccum));
        ((TextView)findViewById(R.id.lblSUMcarbsPer100g)).setText(Double.toString(SUMCarbsPer100g));


    }

}