package com.example.bj_pogi.deprestylefinalfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class BeforeTestActivity extends AppCompatActivity {

    ImageView exitIV;
    Button btnCancel, btnConfirm;
    TextView popupTitleTV, popupInfoTV, tvCurrentDate, tvNextDate;

    protected FirebaseAuth mAuth;
    protected FirebaseUser currentUser;
    private DatabaseReference mUserDatabase;

    ControllerClass mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_test);
        mUtils = new ControllerClass(this);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "Start test";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(back);
            }
        });
        //end of toolbar

        TextView aboutbdiTV = (TextView)findViewById(R.id.aboutbdiTV);
        aboutbdiTV.setMovementMethod(LinkMovementMethod.getInstance());

        tvCurrentDate = (TextView) findViewById(R.id.tvCurrentDate2);
        tvNextDate = (TextView) findViewById(R.id.tvNextDate2);


        //get current date
        Calendar c = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
        tvCurrentDate.setText(currentDate);

        //initialize firebase stuff
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("UserInformation");

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String next = dataSnapshot.child("nextDate").getValue().toString();

                tvNextDate.setText(next);
                try{
                    SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");

                    String c2 = tvCurrentDate.getText().toString();
                    Date date1 = format.parse(c2);

                    String n = tvNextDate.getText().toString();
                    Date date2 = format.parse(n);

                    if(mUtils.isNullOrEmpty(n) || date1.compareTo(date2) > 0 || n.equals(c2)){
                        System.out.print("Read!!!");
                    } else {
                        finish();
                    }

                }catch (ParseException e1){
                    e1.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Button startTest = (Button) findViewById(R.id.startTest);
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog mdialog = new AlertDialog.Builder(BeforeTestActivity.this)
                        .setTitle("Start test?")
                        .setIcon(R.drawable.ic_starttest)
                        .setMessage("Before confirming please make sure that you have read and understood the instructions stated. Please don't rush and take your time for better result")
                        .setCancelable(true)
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                                startActivity(intent);
                                Toast.makeText(BeforeTestActivity.this,"Goodluck!",Toast.LENGTH_LONG).show();
                            }
                        })
                        .create();
                mdialog.show();
            }
        });
    }

}
