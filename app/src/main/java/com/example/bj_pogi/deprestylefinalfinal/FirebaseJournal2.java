package com.example.bj_pogi.deprestylefinalfinal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bj_pogi.deprestylefinalfinal.DateTime.DatePickerFragment;
import com.example.bj_pogi.deprestylefinalfinal.DateTime.TimePickerFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class FirebaseJournal2 extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    EditText etTitle, etLog;
    Button btnDate, btnTime;
    String currentDate, currentTime;
    FloatingActionButton submitFab;

    private DatabaseReference mAccountDatabase;
    private FirebaseUser mCurrentUser;

    int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_journal2);

        //custom toolbar
        TextView hcustomTV = (TextView) findViewById(R.id.hcustomTV);
        String z = "Back";
        hcustomTV.setText(z);
        hcustomTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), FirebaseJournal.class);
                startActivity(back);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        TextView homeTv = (TextView) findViewById(R.id.homeTV);
        homeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(home);
            }
        });
        //end of toolbar

        etTitle = (EditText) findViewById(R.id.etTitle);
        etLog = (EditText) findViewById(R.id.etLog);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnTime = (Button) findViewById(R.id.btnTime);
        submitFab = (FloatingActionButton) findViewById(R.id.btnAdd);

        final Calendar c = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
        btnDate.setText(currentDate);
        currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(c.getTime());
        btnTime.setText(currentTime);



        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date");

            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time");
            }
        });


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();


        mAccountDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid).child("Journal");

        submitFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getData = etTitle.getText().toString();
                String getData2 = etLog.getText().toString();
                String getData3 = btnDate.getText().toString();
                String getData4 = btnTime.getText().toString();

                if(getData.length() != 0) {
                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("title", getData);
                    userMap.put("log", getData2);
                    userMap.put("date", getData3);
                    userMap.put("time", getData4);

                    mAccountDatabase.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(FirebaseJournal2.this, "Successfully added a log", Toast.LENGTH_SHORT).show();
                                etLog.setText("");
                                etTitle.setText("");
                                finish();

                            } else {

                                Toast.makeText(FirebaseJournal2.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(FirebaseJournal2.this, "You must put a title to your log", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String pickDate = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
        btnDate.setText(pickDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String format;

        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "AM";
        } else if (hourOfDay == 12) {
            format = "PM";
        } else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        btnTime.setText(hourOfDay + ":" + minute + " " +format);
    }
}
