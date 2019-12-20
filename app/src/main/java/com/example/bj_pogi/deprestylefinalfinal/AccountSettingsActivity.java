package com.example.bj_pogi.deprestylefinalfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AccountSettingsActivity extends AppCompatActivity {

    Button btnDeleteAcc, btnChangePass;
    TextView tvAccEmail;

    protected FirebaseAuth mAuth;
    protected FirebaseUser currentUser;
    private DatabaseReference mUserDatabase, mArchiveDatabase;

    private static final String TAG = "HomeActivity";

    ControllerClass mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        mUtils = new ControllerClass(this);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = Constants.ACC_SETTINGS;
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AccountSettingsActivity.this, HomeActivity.class);
                startActivity(back);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        //end of toolbar


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String current_uid = currentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mArchiveDatabase = FirebaseDatabase.getInstance().getReference().child("Archived Files").child(current_uid);

        btnChangePass = (Button) findViewById(R.id.btnChangePass);
        btnDeleteAcc = (Button) findViewById(R.id.btnDeleteAcc);
        tvAccEmail = (TextView)findViewById(R.id.tvAccEmail);


        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnChangePass.setEnabled(false);
                //display email
                mUserDatabase.child("UserInformation").child("email").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String email = dataSnapshot.getValue().toString();
                        tvAccEmail.setText(email);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //check et if null
                final String email = tvAccEmail.getText().toString();
                if(!mUtils.isNullOrEmpty(email)){
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                String message = "Request for change password is sent to " + email + " you won't be able to request again for the next 5 minutes";
                                mUtils.showAlertDialog(AccountSettingsActivity.this, Constants.CHANGE_PASS, R.drawable.ic_changepass,
                                        message, true, Constants.CLOSE);
                            }
                            else {
                                String msg = mUtils.removeFirebaseExceptionMsg(task.getException().getMessage());
                                mUtils.makeToastMsg(AccountSettingsActivity.this, msg);
                            }

                        }
                    });
                }
                else{
                    mUtils.showSnackBarShortly(v, Constants.SOMETHING_WENT_WRONG);
                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        btnChangePass.setEnabled(true);
                        Log.d(TAG,"resend1");

                    }
                },300000);// set time as per your requirement

            }
        });

        btnDeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(AccountSettingsActivity.this)
                        .setTitle("Delete Account")
                        .setIcon(R.drawable.ic_eks)
                        .setMessage("Are you sure you want to permanently delete your account? Please rethink before proceeding with this action buddy.")
                        .setNegativeButton("Close", null)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mUserDatabase.child("UserInformation").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String email = dataSnapshot.child("email").getValue().toString();
                                        String pass = dataSnapshot.child("password").getValue().toString();
                                        String fname = dataSnapshot.child("first name").getValue().toString();
                                        String lname = dataSnapshot.child("last name").getValue().toString();
                                        String age = dataSnapshot.child("age").getValue().toString();
                                        String gender = dataSnapshot.child("gender").getValue().toString();
                                        String location = dataSnapshot.child("location").getValue().toString();
                                        String date = dataSnapshot.child("date").getValue().toString();
                                        String time = dataSnapshot.child("time").getValue().toString();
                                        String nextDate = dataSnapshot.child("nextDate").getValue().toString();
                                        String doctor = dataSnapshot.child("doctor").getValue().toString();
                                        String uid = dataSnapshot.child("uid").getValue().toString();
                                        String verification = dataSnapshot.child("verification").getValue().toString();

                                        HashMap<String, String> archiveMap = new HashMap<>();
                                        archiveMap.put("email", email);
                                        archiveMap.put("password", pass);
                                        archiveMap.put("first name", fname);
                                        archiveMap.put("last name", lname);
                                        archiveMap.put("age", age);
                                        archiveMap.put("gender", gender);
                                        archiveMap.put("location", location);
                                        archiveMap.put("date", date);
                                        archiveMap.put("time", time);
                                        archiveMap.put("nextDate", nextDate);
                                        archiveMap.put("verification", verification);
                                        archiveMap.put("doctor", doctor);
                                        archiveMap.put("uid", uid);

                                        mArchiveDatabase.child("UserInformation").setValue(archiveMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    mUserDatabase.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(AccountSettingsActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                                                FirebaseAuth.getInstance().signOut();
                                                                Intent intent = new Intent(AccountSettingsActivity.this, StartActivity.class);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(intent);
                                                                finish();
                                                                mUserDatabase.removeValue();
                                                            }
                                                            else{
                                                                Toast.makeText(AccountSettingsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                                }
                                                else{
                                                    Toast.makeText(AccountSettingsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(AccountSettingsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        })
                        .create();
                dialog.show();
            }
        });

    }
}
