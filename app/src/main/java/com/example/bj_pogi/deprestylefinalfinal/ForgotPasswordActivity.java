package com.example.bj_pogi.deprestylefinalfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView tvBack;
    EditText etResetPass;
    Button btnResetPass;

    protected FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tvBack = (TextView) findViewById(R.id.tvBack);
        etResetPass = (EditText) findViewById(R.id.etResetEmail);
        btnResetPass = (Button) findViewById(R.id.btnResetPass);

        mAuth = FirebaseAuth.getInstance();

        //back to login
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
            }
        });

        //get email then send reset email request
        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check et if null
                if(etResetPass.length() == 0){
                    Snackbar.make(v, "Please fill out the field.", Snackbar.LENGTH_SHORT).show();
                }
                else{
                mAuth.sendPasswordResetEmail(etResetPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this, "Request for password reset is send to: " + etResetPass.getText().toString(), Toast.LENGTH_SHORT).show();
                            etResetPass.setText("");
                            finish();
                        }
                        else {
                            Toast.makeText(ForgotPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
               }
            }
        });
    }
}
