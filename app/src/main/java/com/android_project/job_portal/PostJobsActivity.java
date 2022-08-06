package com.android_project.job_portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android_project.job_portal.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PostJobsActivity extends AppCompatActivity {

    private FloatingActionButton fabBtn;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private DatabaseReference mJobPostDb;
    private FirebaseDatabase database;
    private FirebaseRecyclerAdapter<Data, MyViewHolder> adapter;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_jobs);

        toolbar = findViewById(R.id.toolbar_post_jobs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Jobs Posts");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabBtn = findViewById(R.id.fab_add);
        recyclerView = findViewById(R.id.recycler_job_post_id);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        mJobPostDb = database.getReference().child("Job Post").child(uid);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();

        fabBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), insert_JobPostActivity.class)));


    }

    private void fetch() {
        Query query = mJobPostDb;


        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(query, new SnapshotParser<Data>() {
                    @NonNull
                    @Override
                    public Data parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new Data(snapshot.child("title").getValue().toString(),
                                snapshot.child("description").getValue().toString(),
                                snapshot.child("skills").getValue().toString(),
                                snapshot.child("salary").getValue().toString(),
                                snapshot.child("id").getValue().toString(),
                                snapshot.child("date").getValue().toString());
                    }

                }).build();

        adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model) {
                holder.setJobTitle(model.getTitle());
                holder.setJobDate(model.getDate());
                holder.setJobDesc(model.getDescription());
                holder.setJobSkills(model.getSkills());
                holder.setJobSalary(model.getSalary());
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.job_post_item, parent, false);

                return new MyViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View myview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }

        public void setJobTitle(String title){
            TextView mTitle = myview.findViewById(R.id.job_title);
            mTitle.setText(title);
        }

        public void setJobDate(String date){
            TextView mDate = myview.findViewById(R.id.job_date);
            mDate.setText(date);
        }

        public void setJobDesc(String desc){
            TextView mDesc = myview.findViewById(R.id.job_desc);
            mDesc.setText(desc);
        }

        public void setJobSkills(String skills){
            TextView mSkills = myview.findViewById(R.id.job_skills);
            mSkills.setText(skills);
        }

        public void setJobSalary(String salary){
            TextView mSalary = myview.findViewById(R.id.job_salary);
            mSalary.setText(salary);
        }

    }
}