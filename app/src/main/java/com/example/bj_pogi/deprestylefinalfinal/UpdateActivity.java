package com.example.bj_pogi.deprestylefinalfinal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.SystemClock;
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
import java.util.Calendar;
import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    //GLOBAL
    private long mLastClickTime;


    //COMPONENTS
    Button btnEditTime, btnEditDate;
    EditText etEditTitle, etEditLog;
    FloatingActionButton btnEdit;

    //FIREBASE
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;

    //CLASS
    ControllerClass mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mUtils = new ControllerClass(this);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "Cancel";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent back = new Intent(UpdateActivity.this, FirebaseJournal.class);
                startActivity(back);
            }
        });
        //end of toolbar

        etEditLog = (EditText) findViewById(R.id.etEditLog);
        etEditTitle = (EditText) findViewById(R.id.etEditTitle);
        btnEditDate = (Button) findViewById(R.id.btnEditDate);
        btnEditTime = (Button) findViewById(R.id.btnEditTime);
        btnEdit = (FloatingActionButton) findViewById(R.id.btnEdit);

        etEditTitle.setText(getIntent().getStringExtra("title"));
        etEditLog.setText(getIntent().getStringExtra("log"));
        btnEditDate.setText(getIntent().getStringExtra("date"));
        btnEditTime.setText(getIntent().getStringExtra("time"));

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = mCurrentUser.getUid();

        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Journal");

        btnEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date");

            }
        });

        btnEditTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time");
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                String getEditTitle = etEditTitle.getText().toString();
                String getEditLog = etEditLog.getText().toString();
                String getEditDate = btnEditDate.getText().toString();
                String getEditTime = btnEditTime.getText().toString();

                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("title", getEditTitle);
                userMap.put("log", getEditLog);
                userMap.put("date", getEditDate);
                userMap.put("time", getEditTime);

                String key = getIntent().getStringExtra("uniqueID");
                mRef.child(key).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UpdateActivity.this, "Edited", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateActivity.this, FirebaseJournal.class));
                            finish();
                        }
                        else{
                            try{
                                String exceptionn = mUtils.removeFirebaseExceptionMsg(task.getException().toString());
                                mUtils.makeToastMsg(UpdateActivity.this, exceptionn);
                            } catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });
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
        btnEditDate.setText(pickDate);
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

        btnEditTime.setText(hourOfDay + ":" + minute + " " +format);
    }
}
