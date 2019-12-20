package com.example.bj_pogi.deprestylefinalfinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private static final int STORAGE_CODE = 200;
    TextView TextViewScore, TextViewResult, TextViewLifestyle, TextViewLifestyle2, TextViewResultInfo, tvNameContainer;
    Button btnCreateReport, btnHome, etDate, etTime;
    String currentDate, currentTime;
    String score;
    int d;


    private DatabaseReference mAccountDatabase, mAccountInformation;
    private FirebaseUser mCurrentUser;


    ControllerClass mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mUtils = new ControllerClass(this);

        TextViewScore = (TextView) findViewById(R.id.TextViewScore);
        TextViewResult = (TextView) findViewById(R.id.TextViewResult);
        TextViewResultInfo = (TextView) findViewById(R.id.TextViewResultInfo);
        TextViewLifestyle = (TextView) findViewById(R.id.TextViewLifestyle);
        TextViewLifestyle2 = (TextView) findViewById(R.id.TextViewLifestyle2);
        tvNameContainer = (TextView) findViewById(R.id.tvNameContainer);
        btnCreateReport = (Button) findViewById(R.id.btnCreateReport);
        btnHome = (Button) findViewById(R.id.btnHome);
        etDate = (Button) findViewById(R.id.etDate);
        etTime = (Button) findViewById(R.id.etTime);

        Calendar c = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
        etDate.setText(currentDate);
        currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(c.getTime());
        etTime.setText(currentTime);

        //display score
        TextViewScore.setText(getIntent().getStringExtra("score"));

        //condition for result
        score = TextViewScore.getText().toString();
        d = Integer.parseInt(score);
        if (d == 0) {
            String w = "None";
            String x = "Your test result suggest that you are not suffering depression.";
            String y = "If you're feeling down at the moment, try reading the information in Self Love page or do some activities in the Healthy Exercise page.";

            TextViewResult.setText(w);
            TextViewResultInfo.setText(x);
            TextViewLifestyle.setText(y);
        } else if (d <= 10) {
            String w = "Normal ups and downs";
            String x = "Your test result suggest that these ups and downs are considered normal.";
            String y = "The following activities may be helpful to you. Try adding them to your lifestyle:";
            String z = "• Do some physical exercises" + "\n" +
                    "• Get a good sleep" + "\n" +
                    "• Eat well" + "\n" +
                    "• Look after your hygiene" + "\n" +
                    "• Avoid drugs and alcohol";

            TextViewLifestyle2.setText(z);
            TextViewResult.setText(w);
            TextViewResultInfo.setText(x);
            TextViewLifestyle.setText(y);
        } else if (d <= 16) {
            String w = "Mild mood disturbance";
            String x = "Your test result suggest that you may be suffering from Mild mood disturbance.";
            String y = "The following activities may be helpful to you. Try adding them to your lifestyle:";
            String z = "• Try to keep active" + "\n" +
                    "• Join a group" + "\n" +
                    "• Try new things" + "\n" +
                    "• Try volunteering" + "\n" +
                    "• Set realistic goals";

            TextViewResult.setText(w);
            TextViewResultInfo.setText(x);
            TextViewLifestyle.setText(y);
            TextViewLifestyle2.setText(z);
        } else if (d <= 20) {
            String w = "Clinical depression";
            String x = "Your test result suggest that you may be suffering from Clinical depression.";
            String z = "We advice you to consult a mental health professional regarding with your results in your test.";

            TextViewResult.setText(w);
            TextViewResultInfo.setText(x);
            TextViewLifestyle.setText(z);
            TextViewLifestyle2.setText("");
        } else if (d <= 30) {
            String w = "Moderate depression";
            String x = "Your test result suggest that you may be suffering from Moderate depression.";
            String z = "We advice you to consult a mental health professional regarding with your results in your test.";

            TextViewResult.setText(w);
            TextViewResultInfo.setText(x);
            TextViewLifestyle.setText(z);
            TextViewLifestyle2.setText("");
        } else if (d <= 40) {
            String w = "Severe depression";
            String x = "Your test result suggest that you may be suffering from Severe depression.";
            String z = "We advice you to consult a mental health professional regarding with your results in your test.";

            TextViewResult.setText(w);
            TextViewResultInfo.setText(x);
            TextViewLifestyle.setText(z);
            TextViewLifestyle2.setText("");
        } else {
            String w = "Extreme depression";
            String x = "Your test result suggest that you may be suffering from Extreme depression";
            String y = "We advice you to consult a mental health professional regarding with your results in your test.";

            TextViewResult.setText(w);
            TextViewResultInfo.setText(x);
            TextViewLifestyle.setText(y);
        }

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mAccountDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid).child("History");
        mAccountInformation = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid).child("UserInformation");

        mAccountInformation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try{
                    String fname = dataSnapshot.child("first name").getValue().toString();
                    fname = fname.substring(0,1).toUpperCase() + fname.substring(1).toLowerCase();
                    String lname = dataSnapshot.child("last name").getValue().toString();
                    lname = lname.substring(0,1).toUpperCase() + lname.substring(1).toLowerCase();

                    tvNameContainer.setText(fname + " " + lname);
                } catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
                finish();
            }
        });

        //button for create result
        btnCreateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    }
                    else{
                        savePdf();
                        sendData();
                        disableButtons();
                    }
                }
                else{
                    savePdf();
                    sendData();
                    disableButtons();
                }
            }
        });

    }

    private void sendData() {
        //add 2 weeks
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,14);
        String newDate = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());

        String score = TextViewScore.getText().toString();
        String result = TextViewResult.getText().toString();
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();

        if (score.length() != 0 && !mUtils.isNullOrEmpty(score)) {
            HashMap<String, String> historyMap = new HashMap<>();
            historyMap.put("score", score);
            historyMap.put("result", result);
            historyMap.put("date", date);
            historyMap.put("time", time);

            mAccountInformation.child("nextDate").setValue(newDate);

            mAccountDatabase.push().setValue(historyMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        TextViewScore.setText("");
                        TextViewResult.setText("");

                    } else {
                        String msg = mUtils.removeFirebaseExceptionMsg(task.getException().toString());
                        mUtils.makeToastMsg(ResultActivity.this, msg);

                    }
                }
            });
        } else {
            Toast.makeText(ResultActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void savePdf() {

        String score = TextViewScore.getText().toString();
        String result = TextViewResult.getText().toString();
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();
        String name = tvNameContainer.getText().toString();

        //create object of document class
        Document document = new Document();
        //pdf file name
        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        //pdf file location
        String mFileLocation = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.logo_ds);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int indentation = 0;
        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin() - indentation) / img.getWidth()) * 20;

        img.scalePercent(scaler);

        try{
            PdfWriter.getInstance(document, new FileOutputStream(mFileLocation));

            document.open();

            //add paragraph to document
            Paragraph pImage = new Paragraph();
            Image i = Image.getInstance(img);
            pImage.add(i);
            document.add(pImage);

            String head = "DEPRESTYLE \n Depression Analyzer and Lifestyle Planner \n New Era University \n \n \n";
            Paragraph p = new Paragraph(new Phrase(head,
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.BOLD)));
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            Chunk glue = new Chunk(new VerticalPositionMark());
            Paragraph p2 = new Paragraph("Client name: " + name,  FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
            p2.add(new Chunk(glue));
            p2.add("Date taken: " + date);
            document.add(p2);
            Chunk glue2 = new Chunk(new VerticalPositionMark());
            Paragraph p21 = new Paragraph("Result from test: " + result,  FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
            p21.add(new Chunk(glue2));
            p21.add("Time taken: " + time);
            document.add(p21);
            Chunk glue3 = new Chunk(new VerticalPositionMark());
            Paragraph p22 = new Paragraph("Score from test: " + score +"\n \n \n",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
            p22.add(new Chunk(glue3));
            document.add(p22);

            String high = " and is highly encouraged to visit a Psychiatrist for a professional examination.\n";
            String normal = ". It is suggested to continue to monitor and be aware of the symptoms of depression or better yet visit a mental health professional. Try testing again in two weeks. \n";

            Paragraph p3 = new Paragraph();

            if(result.equals("None")){
                Chunk c3 = new Chunk("      The scores from the test taken suggest that " + name + " shows no sign of depression. Still it is suggested to continue to monitor and be aware of the symptoms of depression or better yet visit a mental health professional. Try testing again in two weeks. \n"  +
                        "This is not a diagnosis but a recommendation to seek a medical treatment. This application does not provide medical prescription and medical diagnosis.\n \n \n \n",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
                p3.add(c3);
            }
            else if(result.equals("Normal ups and downs") || result.equals("Mild mood disturbance")){
                Chunk c3 = new Chunk("      The scores from the test taken suggest that " + name + " is suffering from " + result + normal  +
                        "This is not a diagnosis but a recommendation to seek a medical treatment. This application does not provide medical prescription and medical diagnosis.\n \n \n \n",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
                p3.add(c3);
            }
            else{
                Chunk c3 = new Chunk("      The scores from the test taken suggest that " + name + " is suffering from " + result + high  +
                        "This is not a diagnosis but a recommendation to seek a medical treatment. This application does not provide medical prescription and medical diagnosis.\n \n \n \n",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
                p3.add(c3);
            }



            Chunk c4 = new Chunk("Pointing System from Beck Depression Inventory (BDI):\n" +
                    "1-10 ------------------------------------ These ups and downs are considered normal\n" +
                    "11-16 ---------------------------------- Mild mood disturbance\n" +
                    "17-20 ---------------------------------- Borderline clinical depression\n" +
                    "21-30 ---------------------------------- Moderate depression\n" +
                    "31-40 ---------------------------------- Severe depression\n" +
                    "Over 40 ------------------------------- Extreme depression\n" +
                    "The application Pointing System was based from Beck Depression Inventory.\n \n \n \n \n",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
            p3.add(c4);
            p3.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
            document.add(p3);

            Paragraph p4 = new Paragraph();
            Chunk c5 = new Chunk("BDI Source: https://www.ismanet.org/doctoryourspirit/pdfs/Beck-Depression-Inventory-BDI.pdf",  FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD));
            p4.add(c5);
            p4.setAlignment(Element.ALIGN_CENTER);
            document.add(p4);

            //close document
            document.close();

//            final File file = new File(mFileLocation, mFileName);
            final File file = new File(mFileLocation);

//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            startActivity(intent);
//            Toast.makeText(this, mFileName + ".pdf \n is saved to \n " + mFileLocation, Toast.LENGTH_SHORT).show();

            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, mFileName + ".pdf is saved to " + mFileLocation, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.open, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openReport(file);
                            finish();
                        }
                    })
                    .show();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void openReport(File report){
        Intent intent = new Intent();
        Uri uri = FileProvider.getUriForFile(ResultActivity.this, "com.example.bj_pogi.deprestylefinalfinal.fileprovider", report);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, report);
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }


    private void disableButtons(){
        btnHome.setEnabled(false);
        btnCreateReport.setEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sendData();
                    savePdf();
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

}
