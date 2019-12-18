package com.example.bj_pogi.deprestylefinalfinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements
        View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    Button startBtn;
    Intent startIntent;
    Toolbar home_toolbar;
    LinearLayout selfcareLL, factsLL, fitnessLL, journalLL;
    TextView tvFname, tvLname, tvEmail, tvCurrentDate, tvNextDate, tvQuotes;

    String currentDate,  nextDate;

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    protected FirebaseAuth mAuth;
    protected FirebaseUser currentUser;
    private DatabaseReference mUserDatabase;

    ControllerClass mController = new ControllerClass();
    final Quotes mQuotes = new Quotes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setNavigationViewListner();
        checkNetworkConnectionStatus();
//        checkUser();

        //home toolbar
        home_toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(home_toolbar);
        //end of home toolbar

        tvQuotes = (TextView) findViewById(R.id.tvQuotes);
        tvCurrentDate = (TextView) findViewById(R.id.tvCurrentDate);
        tvNextDate = (TextView) findViewById(R.id.tvNextDate);
        startBtn = (Button)findViewById(R.id.startBtn);
        selfcareLL = (LinearLayout)findViewById(R.id.selfcareLL);
        factsLL = (LinearLayout) findViewById(R.id.factsLL);
        fitnessLL = (LinearLayout)findViewById(R.id.fitnessLL);
        journalLL = (LinearLayout)findViewById(R.id.journalLL);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.mDrawerlayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, home_toolbar, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        startBtn.setOnClickListener(this);
        selfcareLL.setOnClickListener(this);
        factsLL.setOnClickListener(this);
        fitnessLL.setOnClickListener(this);
        journalLL.setOnClickListener(this);

        //get current date
        Calendar c = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
        tvCurrentDate.setText(currentDate);

        //initialize firebase stuff
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("UserInformation");

        //get values in database
        mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){
                        String fname = dataSnapshot.child("first name").getValue().toString();
                        String lname = dataSnapshot.child("last name").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String next = dataSnapshot.child("nextDate").getValue().toString();

                        fname = fname.substring(0,1).toUpperCase() + fname.substring(1).toLowerCase();
                        lname = lname.substring(0,1).toUpperCase() + lname.substring(1).toLowerCase();

                        tvFname.setText(fname);
                        tvLname.setText(lname);
                        tvEmail.setText(email);
                        tvNextDate.setText(next);
                    }

                    else{
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(HomeActivity.this, StartActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tvQuotes.post(new Runnable() {
            //int i = 0;
            Random r = new Random();
            @Override
            public void run() {
                int n = r.nextInt(mQuotes.quoteList.length);
                tvQuotes.setText(mQuotes.quoteList[n]);
                tvQuotes.postDelayed(this, 10000);
            }
        });
        //endregion

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startBtn:
                //startActivity(new Intent(HomeActivity.this, BeforeTestActivity.class));
                try{

                   SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");

                   String c = tvCurrentDate.getText().toString();
                   Date date1 = format.parse(c);

                   String n = tvNextDate.getText().toString();
                   Date date2 = format.parse(n);


                    if(mController.isNullOrEmpty(n) || date1.compareTo(date2) > 0 || n.equals(c)){
                        startIntent = new Intent(getApplicationContext(), BeforeTestActivity.class);
                        //dashboard -> questions
                        startActivity(startIntent);
                    }
                    else {
                        String mDate = Constants.NEXT_TEST + tvNextDate.getText().toString();
                        mController.showAlertDialog(HomeActivity.this, Constants.OOPS, R.drawable.ic_warning,
                                mDate, true, Constants.OK);
                    }

                }catch (ParseException e1){
                    Log.i("UNHANDLED DATE : ", e1.getMessage());
                    mController.makeToastMsg(HomeActivity.this, Constants.PLS_WAIT);
                }

                break;

            case R.id.selfcareLL:
                startIntent = new Intent(getApplicationContext(), SelfCareActivity.class);
                //dashboard -> selfcare
                startActivity(startIntent);
                break;

            case R.id.factsLL:
                startIntent = new Intent(getApplicationContext(), FactsActivity.class);
                //dashboard -> facts
                startActivity(startIntent);
                break;

            case R.id.fitnessLL:
                startIntent = new Intent(getApplicationContext(), FitnessActivity.class);
                //dashboard -> fitness
                startActivity(startIntent);
                break;

            case R.id.journalLL:
                startIntent = new Intent(getApplicationContext(), FirebaseJournal.class);
                //dashboard -> journal
                startActivity(startIntent);
                break;

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            //General
            case R.id.btnHistory:
                startActivity(new Intent(HomeActivity.this, FirebaseHistory.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.btnContactUs:
                startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;


            case R.id.btnAbout:
                startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.btnVideos:
                startActivity(new Intent(HomeActivity.this, VideoTestimoniesActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.btnWebsites:
                startActivity(new Intent(HomeActivity.this, WebsiteActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            //Additional
            case R.id.btnhotline:
                startActivity(new Intent(HomeActivity.this, HotlineActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;


            //Account
            case R.id.btnAccountSettings:
                startActivity(new Intent(HomeActivity.this, AccountSettingsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.btnLogout:
                AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Log out")
                        .setIcon(R.drawable.ic_warning)
                        .setMessage("Are you sure you want to log out?")
                        .setCancelable(false)
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(HomeActivity.this, StartActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create();
                dialog.show();
                break;
        }

        return true;
    }

    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        tvFname = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvNavFName);
        tvLname = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvNavLName);
        tvEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvNavEmail);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void  checkNetworkConnectionStatus(){
        boolean wificonnected;
        boolean mobileconnected;

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if(activeInfo != null && activeInfo.isConnectedOrConnecting()){
            wificonnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileconnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(wificonnected){
                //    Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
            } else if(mobileconnected){
                // Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
            }

        } else {
            mController.showAlertDialogWhenNoInternet(HomeActivity.this);
        }
    }

    private void checkUser(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null || !currentUser.isEmailVerified()) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        checkNetworkConnectionStatus();
        checkUser();
    }


    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

}

