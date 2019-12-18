package com.example.bj_pogi.deprestylefinalfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class VideoTestimoniesActivity extends AppCompatActivity implements View.OnClickListener {

    CardView videoCV, video2CV, video3CV, video4CV, video5CV, video6CV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_testimonies);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "Videos";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        //end of toolbar

        videoCV = (CardView) findViewById(R.id.videoCV);
        video2CV = (CardView) findViewById(R.id.video2CV);
        video3CV = (CardView) findViewById(R.id.video3CV);
        video4CV = (CardView) findViewById(R.id.video4CV);
        video5CV = (CardView) findViewById(R.id.video5CV);
        video6CV = (CardView) findViewById(R.id.video6CV);

        videoCV.setOnClickListener(this);
        video2CV.setOnClickListener(this);
        video3CV.setOnClickListener(this);
        video4CV.setOnClickListener(this);
        video5CV.setOnClickListener(this);
        video6CV.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.videoCV:
                startActivity(new Intent(VideoTestimoniesActivity.this, VideoActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.video2CV:
                startActivity(new Intent(VideoTestimoniesActivity.this, Video2Activity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.video3CV:
                startActivity(new Intent(VideoTestimoniesActivity.this, Video3Activity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.video4CV:
                startActivity(new Intent(VideoTestimoniesActivity.this, Video4Activity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.video5CV:
                startActivity(new Intent(VideoTestimoniesActivity.this, Video5Activity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.video6CV:
                startActivity(new Intent(VideoTestimoniesActivity.this, Video6Activity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }
}
