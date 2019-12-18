package com.example.bj_pogi.deprestylefinalfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class VideoExerciseActivity extends AppCompatActivity implements View.OnClickListener{

    CardView vid1CV, vid2CV, vid3CV, vid4CV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_exercise);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "Back";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), FitnessActivity.class);
                startActivity(back);
            }
        });
        //end of toolbar

        vid1CV = (CardView) findViewById(R.id.videoExercise1CV);
        vid2CV = (CardView) findViewById(R.id.videoExercise2CV);
        vid3CV = (CardView) findViewById(R.id.videoExercise3CV);
        vid4CV = (CardView) findViewById(R.id.videoExercise4CV);

        vid1CV.setOnClickListener(this);
        vid2CV.setOnClickListener(this);
        vid3CV.setOnClickListener(this);
        vid4CV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.videoExercise1CV:
                startActivity(new Intent(this, VidEx1Activity.class));
                break;

            case R.id.videoExercise2CV:
                startActivity(new Intent(this, VideoExercise2Activity.class));
                break;

            case R.id.videoExercise3CV:
                startActivity(new Intent(this, VideoExercise3Activity.class));
                break;

            case R.id.videoExercise4CV:
                startActivity(new Intent(this, VideoExercise4Activity.class));
                break;
        }
    }
}
