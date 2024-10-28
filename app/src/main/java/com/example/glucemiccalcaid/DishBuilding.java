package com.example.glucemiccalcaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DishBuilding extends AppCompatActivity {

    double carbsAccum;
    double foodAccum;
    double SUMCarbsPer100g;

    private Spinner spinner;
    private ArrayList<String> itemList;
    String[] names;
    double[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_building);



        spinner = findViewById(R.id.spinnerDishBuilding);
        itemList = new ArrayList<>();

        //////////////////////////////////
        //File accessing

        String line="";
        names = new String[1000];
        values = new double[1000];
        for(int i=0; i<names.length; i++){
            names[i] = "";
        }
        String filename = "storedCarbValues";


        try ( FileInputStream fis = (DishBuilding.this).openFileInput(filename) ) {
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_16);

            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                line = reader.readLine();
                int i = 0;
                Boolean namesTurn = true;
                //namesTurn and i could definitively be a single variable,
                //and this get structured better

                while (line != null) {



                    if(namesTurn){
                        names[i] = line;
                        namesTurn = false;
                    }else{
                        values[i] = Double.parseDouble(line);
                        i++; //After the second stack is set, index advances.
                        namesTurn = true;
                        // If there is any error in the file, this will probably throw an error.
                    }

                    line = reader.readLine();

                    Log.d("success", "loop goes brrr");
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.

            }
            inputStreamReader.close();

        } catch (IOException ex){

        }
        //////////////////////////////////
        for(int i=0; i<(names.length-2);i++){ //see that names length is hardcoded, and some are empty
            if(names[i].equals("")){
                break;
            }
            itemList.add(names[i]);
        }


        if (spinner != null) {
            // on below line we are initializing and setting our adapter
            // to our spinner.
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, itemList);
            spinner.setAdapter(adapter);
        }

    }


    public void addIngredients(View v){



        TextView functionSwitcher = findViewById(R.id.AmountOrDesiredCarbs);
        EditText EditText_CarbsRatio = findViewById(R.id.editTextCarbsRatioOfIngredient);
        TextView TextView_lblHelp = findViewById(R.id.lblHelp);


        double carbsRatioNum=0;
        double amountIngredientNum=0;
        double carbsAmount=0;

        EditText amountFood = findViewById(R.id.editTextIngredientOrCarbsAmount); //Ingredient here

        //Integrity check
        if(EditText_CarbsRatio.getText().equals("") || amountFood.getText().equals("")){
            TextView_lblHelp.setText("Error, one of the fields is empty");
            return;
        }

        try{
            if(EditText_CarbsRatio.getVisibility() == View.VISIBLE ){
                carbsRatioNum =  Integer.parseInt(EditText_CarbsRatio.getText().toString());
            }else{
                String spinnerChoice = spinner.getSelectedItem().toString();

                for(int i=0;i< values.length; i++){

                    if(spinnerChoice.equals(names[i])){
                        carbsRatioNum = values[i];
                    }
                }
            }

        }catch(NumberFormatException exc){
            TextView_lblHelp.setText("Error, make sure the input is numeric");
            return;
        }


        if(functionSwitcher.getText().equals("Amount (g)")){ //If we know amount of food(g) and carbs in 100g...
           try {
               amountIngredientNum = Integer.parseInt(amountFood.getText().toString());
               carbsAmount = (carbsRatioNum / 100) * amountIngredientNum;

           } catch (NumberFormatException exc){
               TextView_lblHelp.setText("Error, make sure the input is numeric");
               return;
            }
           ((TextView)findViewById(R.id.lblHelp)).setText("Amount of carbs in ingredient is: "+Double.toString(carbsAmount));
        }
        else if (functionSwitcher.getText().equals("Desired Carbs (g)")){
            //If we know portion (g) and carbs in 100g...
            EditText desiredCarbs = findViewById(R.id.editTextIngredientOrCarbsAmount); //carbs here

            try{
                carbsAmount = Integer.parseInt(desiredCarbs.getText().toString());
            }catch(NumberFormatException exc){
                TextView_lblHelp.setText("Error, make sure the input is numeric");
                return;
            }

            amountIngredientNum = carbsAmount / (carbsRatioNum/100);
            amountIngredientNum = Math.floor(amountIngredientNum * 100) / 100;
            ((TextView)findViewById(R.id.lblHelp)).setText("Amount of ingredient to get is: "+Double.toString(amountIngredientNum));
        }

        carbsAccum = carbsAccum + carbsAmount;
        foodAccum = foodAccum + amountIngredientNum;
        SUMCarbsPer100g = carbsAccum / foodAccum * 100;
        SUMCarbsPer100g = Math.floor(SUMCarbsPer100g * 100) / 100;

        ((TextView)findViewById(R.id.textViewAnswerAmount)).setText("Amount (g): "+String.valueOf(amountIngredientNum));
        ((TextView)findViewById(R.id.textViewAnswerCarbs)).setText("Carbs (g): "+String.valueOf(carbsAmount));
        ((TextView)findViewById(R.id.lblTotalCarbs)).setText(Double.toString(carbsAccum));
        ((TextView)findViewById(R.id.lblTotalFood)).setText(Double.toString(foodAccum));
        ((TextView)findViewById(R.id.lblSUMcarbsPer100g)).setText(Double.toString(SUMCarbsPer100g));
    }



    public void calculateData(View v){
        //Variable declaration
        double carbsRatioNum=0;
        double amountIngredientNum=0;
        double carbsAmount=0;
        EditText EditText_CarbsRatio = findViewById(R.id.editTextCarbsRatioOfIngredient);
        TextView TextView_lblHelp = findViewById(R.id.lblHelp);
        EditText amountFood = findViewById(R.id.editTextIngredientOrCarbsAmount); //Ingredient here
        TextView AmountOrDesiredCarbs = findViewById(R.id.AmountOrDesiredCarbs);

        //Integrity check
        if(EditText_CarbsRatio.getText().equals("") || amountFood.getText().equals("")){
            TextView_lblHelp.setText("Error, one of the fields is empty");
            return;
        }

        //Setting varies depending on the functionality
        try{
            if(EditText_CarbsRatio.getVisibility() == View.VISIBLE ){
                carbsRatioNum =  Integer.parseInt(EditText_CarbsRatio.getText().toString());
            }else{
                String spinnerChoice = spinner.getSelectedItem().toString();

                for(int i=0;i< values.length; i++){

                    if(spinnerChoice.equals(names[i])){
                        carbsRatioNum = values[i];
                    }
                }
            }

        }catch(NumberFormatException exc){
            TextView_lblHelp.setText("Error, make sure the input is numeric");
            return;
        }


        if(AmountOrDesiredCarbs.getText().equals("Amount (g)")){ //If we know amount of food(g) and carbs in 100g...
            try{
                amountIngredientNum = Integer.parseInt(amountFood.getText().toString());
                carbsAmount = (carbsRatioNum / 100) * amountIngredientNum;
            }catch (NumberFormatException exc){
                TextView_lblHelp.setText("Error, make sure the input is numeric");
                return;
            }
            ((TextView)findViewById(R.id.lblHelp)).setText("Amount of carbs in ingredient is: "+Double.toString(carbsAmount));

        }else{ //If it is "Desired Carbs (g)"
            //If we know portion (g) and carbs in 100g...

            EditText desiredCarbs = findViewById(R.id.editTextIngredientOrCarbsAmount); //carbs here

            try{
                carbsAmount = Integer.parseInt(desiredCarbs.getText().toString());
            }catch(NumberFormatException exc){
                TextView_lblHelp.setText("Error, make sure the input is numeric");
                return;
            }

            amountIngredientNum = Math.floor(carbsAmount / (carbsRatioNum/100)); //the calculation, with a max of two decimal points
                //answer = desiredCarbsNum / (carbsRatioNum / 100);
            ((TextView)findViewById(R.id.lblHelp)).setText("Amount of ingredient to get is: "+Double.toString(amountIngredientNum));
        }

            ((TextView)findViewById(R.id.textViewAnswerAmount)).setText("Amount (g): "+String.valueOf(amountIngredientNum));
            ((TextView)findViewById(R.id.textViewAnswerCarbs)).setText("Carbs (g): "+String.valueOf(carbsAmount));
            //Creating better modularity here, so as to have a function that calculates passing arguments,
            // and so, might be better
        }


    public void resetAcc(View v){
        EditText carbsRatio = findViewById(R.id.editTextCarbsRatioOfIngredient);
        EditText amount = findViewById(R.id.editTextIngredientOrCarbsAmount);
        TextView lblHelp = findViewById(R.id.lblHelp);
        carbsRatio.setText("");
        amount.setText("");
        lblHelp.setText("");

        carbsAccum = 0;
        foodAccum = 0;
        SUMCarbsPer100g = 0;

        ((TextView)findViewById(R.id.textViewAnswerAmount)).setText("Amount (g): 0");
        ((TextView)findViewById(R.id.textViewAnswerCarbs)).setText("Carbs (g): 0");
        ((TextView)findViewById(R.id.lblTotalCarbs)).setText(Double.toString(carbsAccum));
        ((TextView)findViewById(R.id.lblTotalFood)).setText(Double.toString(foodAccum));
        ((TextView)findViewById(R.id.lblSUMcarbsPer100g)).setText(Double.toString(SUMCarbsPer100g));
    }


    public void switchFunction(View v){
        TextView amountOrDesiredCarbs = findViewById(R.id.AmountOrDesiredCarbs);
        if(amountOrDesiredCarbs.getText().equals("Amount (g)")){
            amountOrDesiredCarbs.setText("Desired Carbs (g)");
        }
        else{
            amountOrDesiredCarbs.setText("Amount (g)");
        }
    }


    public void switchToStorage(View v){
        Spinner spinner = findViewById(R.id.spinnerDishBuilding);
        EditText editTextCarbsRatio = findViewById(R.id.editTextCarbsRatioOfIngredient);

        if(spinner.getVisibility() == View.VISIBLE &&
                editTextCarbsRatio.getVisibility() == View.INVISIBLE)
        {
            spinner.setVisibility(View.INVISIBLE);
            editTextCarbsRatio.setVisibility(View.VISIBLE);
        }
        else if(editTextCarbsRatio.getVisibility() == View.VISIBLE &&
                spinner.getVisibility() == View.INVISIBLE)
        {
            spinner.setVisibility(View.VISIBLE);
            editTextCarbsRatio.setVisibility(View.INVISIBLE);
        }
        else{
            spinner.setVisibility(View.INVISIBLE);
            editTextCarbsRatio.setVisibility(View.VISIBLE);
        }
    }


    public void showHelp(View v){
        TextView helpView = findViewById(R.id.helpTextView__DishBuilding);
        if(helpView.getVisibility() == View.GONE){
            helpView.setVisibility(View.VISIBLE);
        }
        else{
            helpView.setVisibility(View.GONE);
        }
    }

    public void gotoMenu(View v){
        Intent i = new Intent(DishBuilding.this, MainActivity.class);
        startActivity(i);
    }


}