package com.example.bj_pogi.deprestylefinalfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class WebsiteActivity extends AppCompatActivity {

    TextView tvWeb1, tvWeb2, tvWeb3, tvWeb4, tvWeb5, tvWeb6,
            tvWeb7, tvWeb8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "Websites";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(WebsiteActivity.this, HomeActivity.class);
                startActivity(back);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        //end of toolbar

        tvWeb1 = (TextView) findViewById(R.id.tvWeb1);
        tvWeb2 = (TextView) findViewById(R.id.tvWeb2);
        tvWeb3 = (TextView) findViewById(R.id.tvWeb3);
        tvWeb4 = (TextView) findViewById(R.id.tvWeb4);
        tvWeb5 = (TextView) findViewById(R.id.tvWeb5);
        tvWeb6 = (TextView) findViewById(R.id.tvWeb6);
        tvWeb7 = (TextView) findViewById(R.id.tvWeb7);
        tvWeb8 = (TextView) findViewById(R.id.tvWeb8);

        tvWeb1.setMovementMethod(LinkMovementMethod.getInstance());
        tvWeb2.setMovementMethod(LinkMovementMethod.getInstance());
        tvWeb3.setMovementMethod(LinkMovementMethod.getInstance());
        tvWeb4.setMovementMethod(LinkMovementMethod.getInstance());
        tvWeb5.setMovementMethod(LinkMovementMethod.getInstance());
        tvWeb6.setMovementMethod(LinkMovementMethod.getInstance());
        tvWeb7.setMovementMethod(LinkMovementMethod.getInstance());
        tvWeb8.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
