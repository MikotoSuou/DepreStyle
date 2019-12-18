package com.example.bj_pogi.deprestylefinalfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestActivity extends AppCompatActivity {

    TextView questions, score, tvCounter;
    Button answer1, answer2, answer3, answer4;

    Intent startIntent;

    Questions mQuestions;
    int questionsLength;
    public int mScore;

    List<Item> questionList;

    int currentQuestion = 0;
    String finalScore;
    Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "Cancel";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(TestActivity.this)
                        .setTitle("Cancel test")
                        .setIcon(R.drawable.ic_eks)
                        .setMessage("Are you sure you want to cancel your test?")
                        .setCancelable(true)
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog mdialog = new AlertDialog.Builder(TestActivity.this)
                                        .setTitle("Return home")
                                        .setIcon(R.drawable.ic_dead)
                                        .setMessage("I thought you want to know if you are depressed? Please rethink your decision because the progressed you've made so far will be lost!")
                                        .setCancelable(true)
                                        .setNegativeButton("Cancel", null)
                                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(TestActivity.this," Test was canceled :(",Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .create();
                                mdialog.show();
                            }
                        })
                        .create();
                alertDialog.show();

            }
        });

        //end of toolbar

        r = new Random();

        tvCounter = (TextView) findViewById(R.id.tvCounter);
        questions = (TextView) findViewById(R.id.questions);
        score = (TextView) findViewById(R.id.score);
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);

        mQuestions = new Questions();

        mScore = 0;
        questionsLength = mQuestions.mQuestions.length;

        questionList = new ArrayList<>();

        //add all items
        for(int i = 0; i < questionsLength; i++){
            questionList.add(new Item(mQuestions.getQuestion(i), mQuestions.getChoice1(i)));
        }


        //start quiz
        setQuestions(currentQuestion);


        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
                if(answer1.getText() == answer1.getText()){
                    score.setText("" + mScore);
                    //quiz continue
                    currentQuestion++;
                    if(currentQuestion < questionsLength){
                        setQuestions(currentQuestion);
                        //setQuestions(r.nextInt(questionsLength));
                    }
                    else {
                        finalScore = score.getText().toString();
                        startIntent = new Intent(TestActivity.this, ResultActivity.class);
                        startIntent.putExtra("score", finalScore);
                        startActivity(startIntent);
                        end();
                    }
                }
                else{
                    end();
                }

            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
                if(answer2.getText() == answer2.getText()){
                    mScore++;
                    score.setText("" + mScore);
                    //quiz continue
                    currentQuestion++;
                    if(currentQuestion < questionsLength){
                        setQuestions(currentQuestion);
                        //setQuestions(r.nextInt(questionsLength));
                    }
                    else{
                        finalScore = score.getText().toString();
                        startIntent = new Intent(TestActivity.this, ResultActivity.class);
                        startIntent.putExtra("score", finalScore);
                        startActivity(startIntent);
                        end();
                    }
                }
                else{
                    end();
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
                if(answer3.getText() == answer3.getText()){
                    mScore += 2;
                    score.setText("" + mScore);
                    //quiz continue
                    currentQuestion++;
                    if(currentQuestion < questionsLength){
                        setQuestions(currentQuestion);
                        //setQuestions(r.nextInt(questionsLength));
                    }
                    else{
                        finalScore = score.getText().toString();
                        startIntent = new Intent(TestActivity.this, ResultActivity.class);
                        startIntent.putExtra("score", finalScore);
                        startActivity(startIntent);
                        end();
                    }
                }
                else{
                    end();
                }

            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
                if(answer4.getText() == answer4.getText()){
                    mScore += 3;
                    score.setText("" + mScore);
                    //quiz continue
                    currentQuestion++;
                    if(currentQuestion < questionsLength){
                        setQuestions(currentQuestion);
                        //setQuestions(r.nextInt(questionsLength));
                    }
                    else{
                        finalScore = score.getText().toString();
                        startIntent = new Intent(TestActivity.this, ResultActivity.class);
                        startIntent.putExtra("score", finalScore);
                        startActivity(startIntent);
                        end();
                    }
                }
                else{
                    end();
                }

            }
        });
    }

    private void increment() {
        String presentValStr = tvCounter.getText().toString();
        int presentIntVal=Integer.parseInt(presentValStr);

            if(presentIntVal == 21){
                tvCounter.setText("");
            }
            else{
                presentIntVal++;
            }

        tvCounter.setText(String.valueOf(presentIntVal));
    }

    //show question
    private void setQuestions(int num){
        questions.setText(questionList.get(num).getQuestion());
        answer1.setText(mQuestions.getChoice1(num));
        answer2.setText(mQuestions.getChoice2(num));
        answer3.setText(mQuestions.getChoice3(num));
        answer4.setText(mQuestions.getChoice4(num));
    }

    @Override
    public void onBackPressed(){
        AlertDialog alertDialog = new AlertDialog.Builder(TestActivity.this)
                .setTitle("Cancel test")
                .setIcon(R.drawable.ic_eks)
                .setMessage("Are you sure you want to cancel your test?")
                .setCancelable(true)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog mdialog = new AlertDialog.Builder(TestActivity.this)
                                .setTitle("Return home")
                                .setIcon(R.drawable.ic_dead)
                                .setMessage("I thought you want to know if you are depressed? Please rethink your decision because the progressed you've made so far will be lost!")
                                .setCancelable(true)
                                .setNegativeButton("Cancel", null)
                                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(TestActivity.this," Test was canceled :(",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .create();
                        mdialog.show();
                    }
                })
                .create();
        alertDialog.show();
    }

    //end
    private void end(){
        Toast.makeText(this, "You have finished the test!", Toast.LENGTH_SHORT).show();
        finish();

    }
}
