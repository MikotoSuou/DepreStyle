package com.example.bj_pogi.deprestylefinalfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class FirebaseJournal extends AppCompatActivity {

    ListView lv;
    FirebaseListAdapter adapter;
    FirebaseUser currentUser;
    private TextView tvTitle, tvLog, tvDate, tvTime;
    DatabaseReference mRef;

    private long mLastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_journal);

        //custom toolbar
        TextView customTV = (TextView) findViewById(R.id.customTV);
        String z = "Journal";
        customTV.setText(z);
        customTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent back = new Intent(FirebaseJournal.this, HomeActivity.class);
                startActivity(back);
            }
        });
        //end of toolbar



        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();

        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Journal");

        lv = (ListView) findViewById(R.id.lv);
        Query query = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Journal");
        FirebaseListOptions<UserData> data = new FirebaseListOptions.Builder<UserData>()
                .setLayout(R.layout.lv_model)
                .setLifecycleOwner(FirebaseJournal.this)
                .setQuery(query, UserData.class)
                .build();


        FloatingActionButton addFab = (FloatingActionButton) findViewById(R.id.addFab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(FirebaseJournal.this, FirebaseJournal2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        //populate views
        adapter = new FirebaseListAdapter(data) {
            @Override
            protected void populateView(View v, Object model, int position) {
                tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                tvLog = (TextView) v.findViewById(R.id.tvLog);
                tvDate = (TextView) v.findViewById(R.id.tvDate);
                tvTime = (TextView) v.findViewById(R.id.tvTime);

                UserData history = (UserData) model;
                tvTitle.setText(history.getTitle());
                tvLog.setText(history.getLog());
                tvDate.setText(history.getDate());
                tvTime.setText(history.getTime());

            }
        };

        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //edit, delete, and view an item in listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                final TextView getLog = (TextView) view.findViewById(R.id.tvLog);
                final TextView getTitle = (TextView) view.findViewById(R.id.tvTitle);
                final TextView getDate = (TextView) view.findViewById(R.id.tvDate);
                final TextView getTime = (TextView) view.findViewById(R.id.tvTime);
                final String mgetLog = getLog.getText().toString();
                final String mgetTitle = getTitle.getText().toString();
                final String mgetDate = getDate.getText().toString();
                final String mgetTime = getTime.getText().toString();
                AlertDialog alertDialog = new AlertDialog.Builder(FirebaseJournal.this)
                        .setTitle(mgetTitle)
                        .setIcon(R.drawable.ic_stars)
                        .setMessage(mgetDate + "    " + mgetTime + "\n" + "\n" + mgetLog)
                        .setCancelable(true)
                        .setNeutralButton("Close", null)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(FirebaseJournal.this, UpdateActivity.class);
                                UserData d = (UserData) adapter.getItem(position);
                                mRef = adapter.getRef(position);
                                String key = mRef.getKey();
                                intent.putExtra("title", d.getTitle());
                                intent.putExtra("log", d.getLog());
                                intent.putExtra("date", d.getDate());
                                intent.putExtra("time", d.getTime());
                                intent.putExtra("uniqueID", key);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog mdialog = new AlertDialog.Builder(FirebaseJournal.this)
                                        .setTitle("Remove Memory")
                                        .setIcon(R.drawable.ic_delete_black_24dp)
                                        .setMessage("This is a part of your journey are you sure you want to remove this from your journal?")
                                        .setCancelable(true)
                                        .setNegativeButton("Cancel", null)
                                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DatabaseReference archiveRef = FirebaseDatabase.getInstance().getReference().child("Archived Files").child(uid).child("Journal");
                                                UserData d = (UserData) adapter.getItem(position);
                                                mRef = adapter.getRef(position);

                                                String key = mRef.getKey();
                                                HashMap<String, String> userMap = new HashMap<>();
                                                userMap.put("title", d.getTitle());
                                                userMap.put("log", d.getLog());
                                                userMap.put("date", d.getDate());
                                                userMap.put("time", d.getTime());

                                                archiveRef.child(key).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            mRef.removeValue();
                                                            Toast.makeText(FirebaseJournal.this, mgetTitle + " is removed from journal :(", Toast.LENGTH_SHORT).show();

                                                        }
                                                        else{
                                                            Toast.makeText(FirebaseJournal.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
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
