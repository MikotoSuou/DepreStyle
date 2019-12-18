package com.example.bj_pogi.deprestylefinalfinal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartActivity extends AppCompatActivity {

    private long mLastClickTime;

    EditText etUserEmail, etUserPass;
    Button btnLogin;
    TextView tvSignUp, tvForgotPass, tvAppName;
    TextInputLayout inputEmail, inputPassword;

    private Typeface fontStyle;

    private FirebaseAuth mAuth;
    private ProgressDialog mLoginProgress;
    private DatabaseReference mRef, mUserDatabase;
    private FirebaseUser mUser;

    //CLASS
    ControllerClass mController = new ControllerClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        checkNetworkConnectionStatus();

        mAuth = FirebaseAuth.getInstance();

        etUserEmail = (EditText) findViewById(R.id.etUserEmail);
        etUserPass = (EditText) findViewById(R.id.etUserPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
        inputEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        tvAppName = (TextView) findViewById(R.id.tvAppName);
        fontStyle = Typeface.createFromAsset(getAssets(), "font/Tahu!.ttf");
        tvAppName.setTypeface(fontStyle);


        mLoginProgress = new ProgressDialog(this);

        //login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){ return; }
                mLastClickTime = SystemClock.elapsedRealtime();

                String email = etUserEmail.getEditableText().toString();
                String password = etUserPass.getText().toString();

                if(mController.isNullOrEmpty(email) || mController.isNullOrEmpty(password)){
                    mController.makeToastMsg(StartActivity.this, Constants.FILL_UP_USR_AND_PASS);
                }
                else{
                    mLoginProgress.setTitle(Constants.LOGGING_IN);
                    mLoginProgress.setMessage(Constants.PLS_WAIT);
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    loginUser(email, password);
                }
            }
        });

        //go to forgot password
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){ return; }
                mLastClickTime = SystemClock.elapsedRealtime();

                startActivity(new Intent(StartActivity.this, ForgotPasswordActivity.class));
            }
        });

        //go to sign up
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){ return; }
                mLastClickTime = SystemClock.elapsedRealtime();

                startActivity(new Intent(StartActivity.this, SignUpActivity.class));
            }
        });


    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    mLoginProgress.dismiss();
                    if(mAuth.getCurrentUser().isEmailVerified()){
                        mUser = mAuth.getCurrentUser();
                        String id = mUser.getUid();
                        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("UserInformation");

                        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                        mUserDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.exists()){
                                    String user_password = etUserPass.getText().toString();
                                    mRef.child("password").setValue(user_password);
                                    mRef.child("verification").setValue("Verified");
                                    Intent intent = new Intent(StartActivity.this, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                } else{
                                    AlertDialog mdialog = new AlertDialog.Builder(StartActivity.this)
                                            .setTitle(Constants.ACC_DISABLED)
                                            .setIcon(R.drawable.ic_depressed)
                                            .setMessage("Your account has been disabled for multiple possible reasons. Please contact our support group: DeprestyleTeam@gmail.com for more information.")
                                            .setCancelable(false)
                                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    etUserEmail.setText("");
                                                    etUserPass.setText("");
                                                    FirebaseAuth.getInstance().signOut();
                                                }
                                            })
                                            .create();
                                    mdialog.show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                mController.makeToastMsg(StartActivity.this, databaseError.getMessage());
                            }
                        });

                    } else{
                        mController.makeToastMsg(StartActivity.this, Constants.PLS_VERIFY_EMAIL_ADD);

                    }

                } else{
                    mLoginProgress.hide();
                    String msg = mController.removeFirebaseExceptionMsg(task.getException().getMessage());
                    mController.makeToastMsg(StartActivity.this, msg);

                }

            }
        });
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
            mController.showAlertDialogWhenNoInternet(StartActivity.this);
        }
    }

    private void checkUser(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null && currentUser.isEmailVerified()){
            Intent intent = new Intent(StartActivity.this, HomeActivity.class);
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

