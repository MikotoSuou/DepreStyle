package com.example.bj_pogi.deprestylefinalfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity{

    //GLOBAL
    private String currentDate, currentTime;
    private long mLastClickTime;
    private View view;

    //COMPONTENTS
    EditText etSignUpName, etSignUpEmail, etSignUpPass, etSignUpLName, etSignUpLocation, etConfirmPass;
    TextInputLayout il_fname, il_lname, il_confirmpass, il_email, il_pass, il_location;
    Button btnSignUp;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView tvLogin, tvGetTime, tvGetDate, displayGender, tvNextDate, tvProfessional;
    ElegantNumberButton numberPicker;


    //CLASS
    ControllerClass mUtils;

    //FIREBASE
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mUtils = new ControllerClass(this);
        view = getWindow().getDecorView().getRootView();

        etSignUpName = (EditText) findViewById(R.id.etSignUpFName);
        etSignUpLocation = (EditText) findViewById(R.id.etSignUpLocation);
        etSignUpEmail = (EditText) findViewById(R.id.etSignUpEmail);
        etSignUpPass = (EditText) findViewById(R.id.etSignUpPass);
        etConfirmPass = (EditText) findViewById(R.id.etConfirmPass);
        etSignUpLName = (EditText) findViewById(R.id.etSignUpLName);
        il_fname = (TextInputLayout) findViewById(R.id.il_fname);
        il_lname = (TextInputLayout) findViewById(R.id.il_lname);
        il_pass = (TextInputLayout) findViewById(R.id.il_password);
        il_confirmpass = (TextInputLayout) findViewById(R.id.il_confirmpass);
        il_email = (TextInputLayout) findViewById(R.id.il_email);
        il_location = (TextInputLayout) findViewById(R.id.il_location);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvGetDate = (TextView) findViewById(R.id.tvGetDate);
        tvGetTime = (TextView) findViewById(R.id.tvGetTime);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        displayGender = (TextView) findViewById(R.id.displayGender);
        tvNextDate = (TextView) findViewById(R.id.tvNextDate);
        tvProfessional = (TextView) findViewById(R.id.tvProfessional);
        numberPicker = (ElegantNumberButton) findViewById(R.id.numberPicker);

        //exit button
        ImageView btnExit = (ImageView) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                finish();
            }
        });

        //initialize auth for firebase
        mAuth = FirebaseAuth.getInstance();
        mRegProgress = new ProgressDialog(this);

        //get current date and time
        Calendar c = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
        tvGetDate.setText(currentDate);
        currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(c.getTime());
        tvGetTime.setText(currentTime);

        tvNextDate.setText(currentDate);

        String Dr1 = Constants.DR_RIZAL;
        tvProfessional.setText(Dr1);

        //set up number picker for age
        numberPicker.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = numberPicker.getNumber();
                int getAge = Integer.parseInt(num);
                String Dr1 = Constants.DR_RIZAL;
                String Dr2 = Constants.DR_MABINI;
                String Dr3 = Constants.DR_BONIFACIO;
                String Dr4 = Constants.DR_AGUINALDO;

                if(getAge <= 19){
                    tvProfessional.setText(Dr1);
                }

                else if(getAge <= 22){
                    tvProfessional.setText(Dr2);
                }
                else if(getAge <= 26){
                    tvProfessional.setText(Dr3);
                }
                else{
                    tvProfessional.setText(Dr4);
                }

            }
        });


                //register account
                btnSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                            return;
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();

                        Calendar c = Calendar.getInstance();
                        String dt = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        try {
                            c.setTime(sdf.parse(dt));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c.add(Calendar.DATE, 2);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
                        String verification = sdf1.format(c.getTime());

                        String displayName = etSignUpName.getText().toString();
                        String lname = etSignUpLName.getText().toString();
                        String email = etSignUpEmail.getText().toString();
                        String pass = etSignUpPass.getText().toString();
                        String confirmpass = etConfirmPass.getText().toString();
                        String age = numberPicker.getNumber();
                        String location = etSignUpLocation.getText().toString();
                        String gender = displayGender.getText().toString();
                        String doctor = tvProfessional.getText().toString();
                        String nextDate = tvNextDate.getText().toString();

                        if (TextUtils.isEmpty(displayName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirmpass)
                                || TextUtils.isEmpty(lname) || TextUtils.isEmpty(age) || TextUtils.isEmpty(location) || TextUtils.isEmpty(gender)) {

                            mUtils.makeToastMsg(SignUpActivity.this, Constants.COMPLETE_FORM);

                        } else if (pass.length() == 5) {

                            mUtils.showSnackBarShortly(view, Constants.PASS_SHOULD_HAVE_6_CHAR);

                        } else if (pass.equals(confirmpass)) {

                            mRegProgress.setTitle(Constants.REGISTERING);
                            mRegProgress.setMessage(Constants.EMAIL_VERIFICATION);
                            mRegProgress.setCanceledOnTouchOutside(false);
                            mRegProgress.show();

                            registerUser(displayName, currentDate, currentTime, email, pass, age, location, gender, lname, nextDate, doctor, verification);

                        } else {

                            mUtils.showSnackBarShortly(view, Constants.PASS_NOT_EQUAL);

                        }

                    }
                });

        //go to log in
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(SignUpActivity.this, StartActivity.class));

            }
        });

    }

    private void registerUser(final String displayName, final String currentDate, final String currentTime, final String email, final String pass,
                               final String age, final String location, final String gender, final String  lname, final String nextDate, final String doctor,
                              final String verification) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    String uid = currentUser.getUid();

                    mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("UserInformation");
                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("first name", displayName);
                    userMap.put("date", currentDate);
                    userMap.put("time", currentTime);
                    userMap.put("password", pass);
                    userMap.put("age", age);
                    userMap.put("location", location);
                    userMap.put("gender", gender);
                    userMap.put("last name", lname);
                    userMap.put("email", email);
                    userMap.put("nextDate", nextDate);
                    userMap.put("doctor", doctor);
                    userMap.put("uid", uid);
                    userMap.put("verification", verification);

                    mRef.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(task.isSuccessful()){
                                mRegProgress.dismiss();

                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Calendar c = Calendar.getInstance();
                                            String dt = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
                                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                            try {
                                                c.setTime(sdf.parse(dt));
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            c.add(Calendar.DATE, 2);
                                            SimpleDateFormat sdf2 = new SimpleDateFormat("MMMM dd, yyyy");
                                            String expirationDate = sdf2.format(c.getTime());

                                            String message = "Verification sent to: " + email + " you need to verify your account before " + expirationDate;
                                            mUtils.setSessionData(Constants.VERMSG, message);
                                            mUtils.setSessionData(Constants.EVSUCC, "1");
                                            startActivity(new Intent(SignUpActivity.this, StartActivity.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                            finish();

                                        } else {
                                            String exceptionn = mUtils.removeFirebaseExceptionMsg(task.getException().toString());
                                            mUtils.showSnackBarWithDismiss(view, exceptionn, Constants.OKAY);
                                        }
                                    }
                                });

                            } else {
                                String exceptionn = mUtils.removeFirebaseExceptionMsg(task.getException().toString());
                                mUtils.showSnackBarWithDismiss(view, exceptionn, Constants.OKAY);
                            }
                        }
                    });
                } else {
                    mRegProgress.hide();
                    String exceptionn = mUtils.removeFirebaseExceptionMsg(task.getException().toString());
                    mUtils.showSnackBarWithDismiss(view, exceptionn, Constants.OKAY);

                }
            }
        });

    }


    public void checkButton(View v){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);

        String x = radioButton.getText().toString();
        displayGender.setText(x);
    }


    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

}
