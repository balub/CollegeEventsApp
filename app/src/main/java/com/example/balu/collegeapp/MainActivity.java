package com.example.balu.collegeapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private FirebaseFirestore mFireStore;
    private List<Events> event_list;
    private RecyclerView recyclerView;
    private RecycleViewAdaptor adaptor;
    private String blogPostId;
    private FloatingActionButton event_newBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        event_newBtn = (FloatingActionButton)findViewById(R.id.event_new);
        mFireStore = FirebaseFirestore.getInstance();
        //initRecycler();
        event_list = new ArrayList<Events>();
        recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        adaptor = new RecycleViewAdaptor(this,event_list);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        event_newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewEventActivity.class);
                startActivity(intent);
            }
        });


      /* mFireStore.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });*/

        mFireStore.collection("events").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(!documentSnapshots.isEmpty()){
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                           // Log.d(TAG, doc.getDocument().getString("event_title"));
                            //Log.d(TAG, doc.getDocument().getId().toString());
                            blogPostId = doc.getDocument().getId();
                            //Events events = doc.getDocument().toObject(Events.class).withId(blogPostId);;
                            Events events = doc.getDocument().toObject(Events.class);
                            event_list.add(events);

                            adaptor.notifyDataSetChanged();

                        }

                    }

                }
            }
        });
    }




}
