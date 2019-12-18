package com.example.bj_pogi.deprestylefinalfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class FirebaseHistory extends AppCompatActivity {

    ListView historylv;
    FirebaseListAdapter adapter;
    FirebaseUser currentUser;
    private TextView tv_score, tv_result, tv_date, tv_time;
    DatabaseReference mRef;

    ControllerClass mController = new ControllerClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_history);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "History";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(FirebaseHistory.this, HomeActivity.class);
                startActivity(back);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        //end of toolbar

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();

        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("History");

        //query to display
        historylv = (ListView) findViewById(R.id.historylv);
        Query query = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("History");
        FirebaseListOptions<ResultData> data = new FirebaseListOptions.Builder<ResultData>()
                .setLayout(R.layout.historylv_model)
                .setLifecycleOwner(FirebaseHistory.this)
                .setQuery(query, ResultData.class)
                .build();

        //populate views
        adapter = new FirebaseListAdapter(data) {
            @Override
            protected void populateView(View v, Object model, int position) {
                tv_score = (TextView) v.findViewById(R.id.tv_score);
                tv_result = (TextView) v.findViewById(R.id.tv_result);
                tv_date = (TextView) v.findViewById(R.id.tv_date);
                tv_time = (TextView) v.findViewById(R.id.tv_time);

                ResultData history = (ResultData) model;
                tv_score.setText(history.getScore());
                tv_result.setText(history.getResult());
                tv_date.setText(history.getDate());
                tv_time.setText(history.getTime());

            }
        };

        historylv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        historylv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                final TextView getScore = (TextView) view.findViewById(R.id.tv_score);
                final TextView getResult = (TextView) view.findViewById(R.id.tv_result);
                final TextView getDate = (TextView) view.findViewById(R.id.tv_date);
                final TextView getTime = (TextView) view.findViewById(R.id.tv_time);
                final String mgetScore= getScore.getText().toString();
                final String mgetResult= getResult.getText().toString();
                final String mgetDate = getDate.getText().toString();
                final String mgetTime = getTime.getText().toString();
                android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(FirebaseHistory.this)
                        .setTitle("Oh hello there! this is your test result last " + mgetDate)
                        .setIcon(R.drawable.ic_scroll)
                        .setMessage("Score: " + mgetScore +  "\n" + mgetResult + "\n" + mgetDate + "\n" + mgetTime)
                        .setCancelable(true)
                        .setPositiveButton("Close", null)
                        .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                android.support.v7.app.AlertDialog mdialog = new android.support.v7.app.AlertDialog.Builder(FirebaseHistory.this)
                                        .setTitle("Remove result")
                                        .setIcon(R.drawable.ic_delete_black_24dp)
                                        .setMessage("Are you sure you want to remove your test result? this can be helpful to you if you want to compare your results in the future so you can know if something has changed in you in the past weeks!")
                                        .setCancelable(true)
                                        .setPositiveButton("Cancel", null)
                                        .setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                DatabaseReference archiveRef = FirebaseDatabase.getInstance().getReference().child("Archived Files").child(uid).child("History");
                                                ResultData d = (ResultData) adapter.getItem(position);
                                                mRef = adapter.getRef(position);

                                                String key = mRef.getKey();
                                                HashMap<String, String> userMap = new HashMap<>();
                                                userMap.put("score", d.getScore());
                                                userMap.put("result", d.getResult());
                                                userMap.put("date", d.getDate());
                                                userMap.put("time", d.getTime());

                                                archiveRef.child(key).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            mRef.removeValue();
                                                            mController.makeToastMsg(FirebaseHistory.this, "A result has been removed :(");

                                                        }
                                                        else{
                                                            String exceptionn = task.getException().toString();
                                                            mController.makeToastMsg(FirebaseHistory.this, exceptionn);
                                                        }
                                                    }
                                                });

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

        //go to before test
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.mainFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirebaseHistory.this, BeforeTestActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
