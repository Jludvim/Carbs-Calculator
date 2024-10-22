package com.example.glucemiccalcaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class CarbsIn100g extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbs_in100g);
    }


    private double calculateCarbohydrates(double carbsGrams, double servingGrams){
    double result=0;


    return result;
    }
    public double CalcCarbsIn100(View v){
        EditText carbs = findViewById(R.id.editTextCarbsPerServing);
        EditText serving = findViewById(R.id.editTextServing);
        TextView answer = findViewById(R.id.textViewAnswerCarbsIn100g);
        double carbsNum;
        double servingNum;
        double answerNum;

        if(carbs.getText().equals("") || serving.getText().equals("")){
            answer.setText("");
            return 0;
        }

        try {
            carbsNum =  Integer.parseInt(carbs.getText().toString());
            servingNum = Integer.parseInt(serving.getText().toString());
        }
        catch(NumberFormatException exc){
            answer.setText("Error, make sure input is numeric");
            return 0;
        }

        answerNum = (carbsNum/servingNum) * 100;

        answer.setText("The amount of carbs in 100 g  is: "+answerNum+"g");

        return answerNum;
    }


    public void storeItem(View v){

        /*
        * so, this is the file structure. We will have assigned for each line alternatively a name and a carbs/100g value.
        * Basically:
        * 0. name
        * 1. numeric value (double)
        * 2. name
        * 3. numeric value (double)
        *   Being then that we have (if things work as we expect), a name for each pair (x % 2 = 0) value, and a number
        *   in each (x % 2 = 1) idle value for the line index, or getLine loop counter.
        * Also, the null value should be fetched in a pair index.
        * */

        double carbsAmountIn100g = CalcCarbsIn100(v);

        String filename = "storedCarbValues";
        TextView answer = findViewById(R.id.textViewAnswerCarbsIn100g);
        EditText name = findViewById(R.id.editTextName);
        if(name.getText().toString().equals("") || carbsAmountIn100g <= 0){
            if(name.getText().toString().equals("")){
                answer.setText("Error. name can't be empty");
                return;
            }
            else if(carbsAmountIn100g <= 0){
                answer.setText("Error. carbsAmount can't be negative or zero");
                return;
            }
        }
        String fileContents = name.getText() + "\n" + Double.toString(carbsAmountIn100g) + "\n";



        //Checks if file exists
        String[] files = (CarbsIn100g.this).fileList();
        Boolean flag = false;

        for(int i=0;i<files.length;i++){
            if(files[i].equals(filename)){
                flag = true;
                break;
            }
        }


        //Create file or append
        if(flag == true){
            //access file
            Log.d("fileManagement", "Accessing File");
            try (FileOutputStream fos = (CarbsIn100g.this).openFileOutput(filename, Context.MODE_APPEND)) {
                fos.write(fileContents.getBytes("UTF-16"));
            }catch(IOException ex){}

        } else {
            //create file
            Log.d("fileManagement", "Creating File");
            try (FileOutputStream fos = (CarbsIn100g.this).openFileOutput(filename, Context.MODE_PRIVATE)) {
                fos.write(fileContents.getBytes("UTF-16"));
            } catch (FileNotFoundException ex){

            }
            catch (IOException ex){

            }
        }

        answer.setText(name.getText()+ ": "+Double.toString(carbsAmountIn100g)+" g, stored");

    }

    public void showHelp(View v){
        TextView helpView = findViewById(R.id.helpView__CarbsIn100g);
        if(helpView.getVisibility() == View.GONE){
            helpView.setVisibility(View.VISIBLE);
        }
        else{
            helpView.setVisibility(View.GONE);
        }
    }

    public void gotoMenu(View v){
        Intent i = new Intent(CarbsIn100g.this, MainActivity.class);
        startActivity(i);
    }


}