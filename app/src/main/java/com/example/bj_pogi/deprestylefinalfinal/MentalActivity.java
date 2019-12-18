package com.example.bj_pogi.deprestylefinalfinal;


import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MentalActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager mSlideViewPager;
    private LinearLayout mDotsLayout;

    private TextView[] mDots;

    private mSliderAdapter msliderAdapter;

    private Button mbuttonPrevious, mbuttonNext;

    private  int mCurrentPage;
    private boolean isLastPageSwiped;
    private int counterPageScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental);

        mSlideViewPager = findViewById(R.id.mslide_viewpager);
        mDotsLayout = findViewById(R.id.mdots_layout);
        mbuttonNext = findViewById(R.id.mbtn_next);
        mbuttonPrevious = findViewById(R.id.mbtn_previous);


        msliderAdapter = new mSliderAdapter(this);

        mSlideViewPager.setAdapter(msliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mbuttonNext.setOnClickListener(this);
        mbuttonPrevious.setOnClickListener(this);

    }

    public void addDotsIndicator(int position){

        mDots = new TextView[6];
        mDotsLayout.removeAllViews(); //without this multiple number of dots will be created

        for (int i = 0; i< mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;")); //code for the dot icon like thing
            mDots[i].setTextSize(35);

            mDots[i].setTextColor(getResources().getColor(R.color.silver));

            mDotsLayout.addView(mDots[i]);
        }

        if (mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.white)); //setting currently selected dot to white
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == 5 && positionOffset == 0 && !isLastPageSwiped){
                if(counterPageScroll != 0){
                    isLastPageSwiped=true;
                    startActivity(new Intent(MentalActivity.this, FitnessActivity.class));
                }
                counterPageScroll++;
            }else{
                counterPageScroll=0;

            }
        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);

            mCurrentPage = position;

            if (position == 0){//we are on first page
                mbuttonNext.setEnabled(true);
                mbuttonPrevious.setEnabled(true);

                mbuttonNext.setText("Next");
                mbuttonPrevious.setText("Close");
            } else if (position == mDots.length - 1){ //last page
               mbuttonNext.setEnabled(true);
                mbuttonPrevious.setEnabled(true);
                mbuttonPrevious.setVisibility(View.VISIBLE);

                mbuttonNext.setText("Finish");
                mbuttonPrevious.setText("Back");
            } else { //neither on first nor on last page
                mbuttonNext.setEnabled(true);
                mbuttonPrevious.setEnabled(true);
                mbuttonPrevious.setVisibility(View.VISIBLE);

                mbuttonNext.setText("Next");
                mbuttonPrevious.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mbtn_next:
                if (mbuttonNext.getText().toString().equalsIgnoreCase("next")){
                    mSlideViewPager.setCurrentItem(mCurrentPage + 1);
                } else {
                    startActivity(new Intent(MentalActivity.this, FitnessActivity.class));
                }
                break;
            case R.id.mbtn_previous:
                if (mbuttonPrevious.getText().toString().equalsIgnoreCase("back")){
                    mSlideViewPager.setCurrentItem(mCurrentPage - 1);
                } else {
                    startActivity(new Intent(MentalActivity.this, FitnessActivity.class));
                }
                break;
            default: break;
        }
    }
}
