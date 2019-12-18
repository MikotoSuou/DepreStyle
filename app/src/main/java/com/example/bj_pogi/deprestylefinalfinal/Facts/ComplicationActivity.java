package com.example.bj_pogi.deprestylefinalfinal.Facts;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bj_pogi.deprestylefinalfinal.FactsActivity;
import com.example.bj_pogi.deprestylefinalfinal.R;

public class ComplicationActivity extends AppCompatActivity {

    private long mLastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complication);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "Back";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent back = new Intent(getApplicationContext(), FactsActivity.class);
                startActivity(back);
            }
        });
        //end of toolbar
    }
}
