package com.example.glucemiccalcaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PortionOfXCarbs extends AppCompatActivity {

    private Spinner spinner;
    private ArrayList<String> itemList;
    String[] names;
    double[] values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portion_of_xcarbs);

        spinner = findViewById(R.id.spinnerStorage);
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


        try ( FileInputStream fis = (PortionOfXCarbs.this).openFileInput(filename) ) {
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_16);

            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                line = reader.readLine();
                int i = 0;
                Boolean namesTurn = true;
                //namesTurn and i could definitively be a single variable,
                //and this get structured better

                while (line != null) {
                   // Log.d("success", line+", <- This was the food");


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

    public void calcPortion(View v){

        EditText carbsRatio =  findViewById(R.id.editTextCarbsRatio);
        EditText desiredCarbs = findViewById(R.id.editTextDesiredCarbs);
        TextView portionAnswer = findViewById(R.id.portionAnswer);

        double carbsRatioNum = 0;
        double desiredCarbsNum = 0;
        double answer = 0;

        if(carbsRatio.getText().equals("") || desiredCarbs.getText().equals("")){
            portionAnswer.setText("");
            return;
        }

        try{
            if(carbsRatio.getVisibility() == View.VISIBLE ){
                carbsRatioNum =  Integer.parseInt(carbsRatio.getText().toString());
            }else{
                String spinnerChoice = spinner.getSelectedItem().toString();

                for(int i=0;i< values.length; i++){

                        if(spinnerChoice.equals(names[i])){
                            carbsRatioNum = values[i];
                        }

                }
            }
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

    public void showHelp(View v){
        TextView helpView = findViewById(R.id.HelpView__PortionOfXCarbs);
        if(helpView.getVisibility() == View.GONE){
            helpView.setVisibility(View.VISIBLE);
        }
        else{
            helpView.setVisibility(View.GONE);
        }
    }



    public void switchToStorage(View v){
        Spinner spinner = findViewById(R.id.spinnerStorage);
        EditText editTextCarbsRatio = findViewById(R.id.editTextCarbsRatio);

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


}