package com.example.glucemiccalcaid;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.glucemiccalcaid.databinding.ActivityCarbsOfXportionBinding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CarbsOfXPortion extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    private Spinner spinner;
    private ArrayList<String> itemList;
    String[] names;
    double[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbs_of_xportion);

        spinner = findViewById(R.id.spinnerCarbsOfXPortion);
        itemList = new ArrayList<>();



        String line = "";
        names = new String[1000];
        values = new double[1000];
        for (int i = 0; i < names.length; i++) {
            names[i] = "";
        }
        String filename = "storedCarbValues";


        try (FileInputStream fis = (CarbsOfXPortion.this).openFileInput(filename)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_16);

            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                line = reader.readLine();
                int i = 0;
                Boolean namesTurn = true;
                //namesTurn and i could definitively be a single variable,
                //and this get structured better

                while (line != null) {
                    // Log.d("success", line+", <- This was the food");


                    if (namesTurn) {
                        names[i] = line;
                        namesTurn = false;
                    } else {
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

        } catch (IOException ex) {

        }
        //////////////////////////////////

        for (int i = 0; i < (names.length - 2); i++) { //see that names length is hardcoded, and some are empty
            if (names[i].equals("")) {
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

    public void calcCarbs(View v){

        EditText carbsRatio =  findViewById(R.id.editTextCarbsRatio);
        EditText portion = findViewById(R.id.editTextPortionGrams);
        TextView carbsAnswer = findViewById(R.id.carbsAnswer);
        double carbsRatioNum=0;
        double portionNum=0;
        double answer=0;

        if(carbsRatio.getText().equals("") || portion.getText().equals("")){
            carbsAnswer.setText("");
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

           portionNum =  Integer.parseInt(portion.getText().toString());

        }catch(NumberFormatException exc){
            carbsAnswer.setText("Error, make sure input is numeric");
            return;
        }

        answer = (carbsRatioNum / 100) * portionNum;

        carbsAnswer.setText("Amount of Carbs for a portion of "+portionNum+"g is: "+Double.toString(answer));

        return;
    }


    public void showHelp(View v){
        TextView helpView = findViewById(R.id.helpView__CarbsOfXPortion);
        if(helpView.getVisibility() == View.GONE){
            helpView.setVisibility(View.VISIBLE);
        }
        else{
            helpView.setVisibility(View.GONE);
        }
    }

    public void gotoMenu(View v){
        Intent i = new Intent(CarbsOfXPortion.this, MainActivity.class);
        startActivity(i);
    }

    public void switchToStorage(View v){
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