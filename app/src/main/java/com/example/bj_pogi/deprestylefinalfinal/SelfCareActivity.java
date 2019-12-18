package com.example.bj_pogi.deprestylefinalfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.bj_pogi.deprestylefinalfinal.SelfLove.ChallengeLowMoodActivity;
import com.example.bj_pogi.deprestylefinalfinal.SelfLove.ConnectWithPeopleActivity;
import com.example.bj_pogi.deprestylefinalfinal.SelfLove.KeepActiveActivity;
import com.example.bj_pogi.deprestylefinalfinal.SelfLove.LookAfterYourselfActivity;
import com.example.bj_pogi.deprestylefinalfinal.SelfLove.PracticeSelfCareActivity;

public class SelfCareActivity extends AppCompatActivity {

    CardView lookafterCV, practicescCV, keepactiveCV, lowmoodCV, connectCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_care);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "Self Love";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(back);
            }
        });
        //end of toolbar

        lookafterCV = (CardView)findViewById(R.id.lookafterCV);
        practicescCV = (CardView)findViewById(R.id.practicescCV);
        keepactiveCV = (CardView)findViewById(R.id.keepactiveCV);
        lowmoodCV = (CardView)findViewById(R.id.lowmoodCV);
        connectCV = (CardView)findViewById(R.id.connectCV);

        lookafterCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LookAfterYourselfActivity.class));
            }
        });

        practicescCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PracticeSelfCareActivity.class));
            }
        });

        keepactiveCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), KeepActiveActivity.class));
            }
        });

        lowmoodCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChallengeLowMoodActivity.class));
            }
        });

        connectCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ConnectWithPeopleActivity.class));
            }
        });
    }
}
