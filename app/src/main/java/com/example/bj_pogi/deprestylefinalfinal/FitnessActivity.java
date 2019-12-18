package com.example.bj_pogi.deprestylefinalfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class FitnessActivity extends AppCompatActivity {

    CardView paActivtiesCV, maActivitiesCV, vaActivitiesCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "Healthy Exercise";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(back);
            }
        });
        //end of toolbar

        paActivtiesCV = (CardView) findViewById(R.id.paActivtiesCV);
        maActivitiesCV = (CardView) findViewById(R.id.maActivitiesCV);
        vaActivitiesCV = (CardView) findViewById(R.id.vaActivitiesCV);

        paActivtiesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FitnessActivity.this, PhysicalActivity.class));
            }
        });

        maActivitiesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FitnessActivity.this, MentalActivity.class));
            }
        });

        vaActivitiesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FitnessActivity.this, VideoExerciseActivity.class));
            }
        });

    }
}
