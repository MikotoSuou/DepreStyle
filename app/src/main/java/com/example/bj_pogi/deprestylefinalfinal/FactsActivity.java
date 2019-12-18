package com.example.bj_pogi.deprestylefinalfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.bj_pogi.deprestylefinalfinal.Facts.ComplicationActivity;
import com.example.bj_pogi.deprestylefinalfinal.Facts.DepressionActivity;
import com.example.bj_pogi.deprestylefinalfinal.Facts.PreventionActivity;
import com.example.bj_pogi.deprestylefinalfinal.Facts.RiskFactorActivity;
import com.example.bj_pogi.deprestylefinalfinal.Facts.SymptomActivity;

public class FactsActivity extends AppCompatActivity {

    CardView depressionCV, symptomCV, riskfactorCV, complicationCV, preventionCV, differenceCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "Facts";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(back);
            }
        });
        //end of toolbar

        depressionCV = (CardView) findViewById(R.id.depressionCV);
        symptomCV = (CardView) findViewById(R.id.symptomCV);
        riskfactorCV = (CardView) findViewById(R.id.riskfactorCV);
        complicationCV = (CardView) findViewById(R.id.complicationTV);
        preventionCV = (CardView) findViewById(R.id.preventionCV);
        differenceCV = (CardView) findViewById(R.id.differenceCV);

       depressionCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), DepressionActivity.class));
           }
       });

       symptomCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), SymptomActivity.class));
           }
       });

       riskfactorCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), RiskFactorActivity.class));
           }
       });

       complicationCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), ComplicationActivity.class));
           }
       });

       preventionCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), PreventionActivity.class));
           }
       });

       differenceCV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), OtherMentalActivity.class));
           }
       });

    }

}
