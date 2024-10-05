package com.example.glucemiccalcaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button btnCarbsIn100g;
    Button btnCarbsOfPortion;
    Button btnPortionOfCarbs;
    Button btnPrepareAMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCarbsIn100g = findViewById(R.id.btnMenuCarbsIn100g);
        btnCarbsOfPortion = findViewById(R.id.btnMenuCarbsOfPortion);
        btnPortionOfCarbs = findViewById(R.id.btnMenuPortionOfCarbs);
        btnPrepareAMeal = findViewById(R.id.btnPrepMeal);


        btnPrepareAMeal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                prepareAMeal();
            }
        });

        btnCarbsIn100g.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                CarbsIn100g();
            }
        });

        btnCarbsOfPortion.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                carbsOfXPortion();
            }
        });

        btnPortionOfCarbs.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                portionOfXCarbs();
            }
        });

    }

    protected void carbsOfXPortion(){
        Intent i = new Intent(MainActivity.this, CarbsOfXPortion.class);
        startActivity(i);
    }

    protected void portionOfXCarbs(){
        Intent i = new Intent(MainActivity.this, PortionOfXCarbs.class);
        startActivity(i);
    }

    protected void CarbsIn100g(){
        Intent i = new Intent(MainActivity.this, CarbsIn100g.class);
        startActivity(i);
    }

    protected void prepareAMeal(){
        Intent i = new Intent(MainActivity.this, DishBuilding.class);
        startActivity(i);
    }





}