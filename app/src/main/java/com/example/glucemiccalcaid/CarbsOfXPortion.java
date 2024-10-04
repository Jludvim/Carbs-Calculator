package com.example.glucemiccalcaid;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.glucemiccalcaid.databinding.ActivityCarbsOfXportionBinding;

public class CarbsOfXPortion extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    //private ActivityCarbsOfXportionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbs_of_xportion);

        /*
        binding = ActivityCarbsOfXportionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_carbs_of_xportion);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
*/
    }

   /* @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_carbs_of_xportion);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    */

    public void calcCarbs(View v){

        EditText carbsRatio =  findViewById(R.id.editTextCarbsRatio);
        EditText portion = findViewById(R.id.editTextPortionGrams);
        TextView carbsAnswer = findViewById(R.id.carbsAnswer);
        double carbsRatioNum;
        double portionNum;
        double answer;

        if(carbsRatio.getText().equals("") || portion.getText().equals("")){
            carbsAnswer.setText("");
            return;
        }

        try{
           carbsRatioNum =  Integer.parseInt(carbsRatio.getText().toString());
           portionNum =  Integer.parseInt(portion.getText().toString());

        }
        catch(NumberFormatException exc){
            carbsAnswer.setText("Error, make sure input is numeric");
            return;
        }

        answer = (carbsRatioNum / 100) * portionNum;

        carbsAnswer.setText("Amount of Carbs for a portion of "+portionNum+"g is: "+Double.toString(answer));

        return;
    }


    public void gotoMenu(View v){
        Intent i = new Intent(CarbsOfXPortion.this, MainActivity.class);
        startActivity(i);
    }


}