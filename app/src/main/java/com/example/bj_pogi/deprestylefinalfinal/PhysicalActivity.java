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

public class PhysicalActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager mSlideViewPager;
    private LinearLayout mDotsLayout;
    private TextView[] mDots;
    private SliderAdapter sliderAdapter;
    private Button buttonPrevious, buttonNext;

    private  int mCurrentPage;
    private boolean isLastPageSwiped;
    private int counterPageScroll;

    private static final String DOT_SOURCE = "&#8226;";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);

        mSlideViewPager = findViewById(R.id.slide_viewpager);
        mDotsLayout = findViewById(R.id.dots_layout);
        buttonNext = findViewById(R.id.btn_next);
        buttonPrevious = findViewById(R.id.btn_previous);


        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        buttonNext.setOnClickListener(this);
        buttonPrevious.setOnClickListener(this);

    }

    public void addDotsIndicator(int position){

        mDots = new TextView[7];
        mDotsLayout.removeAllViews(); //without this multiple number of dots will be created

        for (int i = 0; i< mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml(DOT_SOURCE)); //code for the dot icon like thing
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
            if (position == 6 && positionOffset == 0 && !isLastPageSwiped){
                if(counterPageScroll != 0){
                    isLastPageSwiped=true;
                    startActivity(new Intent(PhysicalActivity.this, FitnessActivity.class));
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
                buttonNext.setEnabled(true);
                buttonPrevious.setEnabled(true);

                buttonNext.setText(R.string.next);
                buttonPrevious.setText(R.string.close);
            } else if (position == mDots.length - 1){ //last page
                buttonNext.setEnabled(true);
                buttonPrevious.setEnabled(true);
                buttonPrevious.setVisibility(View.VISIBLE);

                buttonNext.setText(R.string.finish);
                buttonPrevious.setText(R.string.backk);
            } else { //neither on first nor on last page
                buttonNext.setEnabled(true);
                buttonPrevious.setEnabled(true);
                buttonPrevious.setVisibility(View.VISIBLE);

                buttonNext.setText(R.string.next);
                buttonPrevious.setText(R.string.backk);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                if (buttonNext.getText().toString().equalsIgnoreCase(Constants.NEXT)){
                    mSlideViewPager.setCurrentItem(mCurrentPage + 1);
                } else {
                    startActivity(new Intent(PhysicalActivity.this, FitnessActivity.class));
                }
                break;

            case R.id.btn_previous:
                if (buttonPrevious.getText().toString().equalsIgnoreCase(Constants.BACK)){
                    mSlideViewPager.setCurrentItem(mCurrentPage - 1);
                } else {
                    startActivity(new Intent(PhysicalActivity.this, FitnessActivity.class));
                }
                break;
            default: break;
        }
    }
}
