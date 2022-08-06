package com.android_project.job_portal;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android_project.job_portal.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllJobsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    //recycler view

    private RecyclerView recyclerView;

    //Firebase

    private DatabaseReference mAllJobPost;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alljobposts);

        toolbar = findViewById(R.id.all_job_post);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Job Post");

        mAllJobPost= FirebaseDatabase.getInstance().getReference().child("Public DB");
        mAllJobPost.keepSynced(true);

        recyclerView=findViewById(R.id.recycler_all_job);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Data,AllJobPostViewHolder> adapter = new FirebaseRecyclerAdapter<Data, AllJobPostViewHolder>(
                Data.class,
                R.layout.alljobposts,
                AllJobPostViewHolder.class,
                mAllJobPost
        ) {
            @Override
            protected void onBindViewHolder(AllJobPostViewHolder holder, int position,Data model) {

                holder.setJobTitle(model.getTitle());
                holder.setJobDate(model.getDate());
                holder.setJobDesc(model.getDescription());
                holder.setJobSkills(model.getSkills());
                holder.setJobSalary(model.getSalary());

            }

            @NonNull
            @Override
            public AllJobPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.job_post_item, parent, false);

                return new AllJobPostViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class AllJobPostViewHolder extends RecyclerView.ViewHolder{

        View myView;

        public AllJobPostViewHolder(View itemView) {
            super(itemView);
            myView=itemView;
        }

        public void setJobTitle(String title){
            TextView mTitle = myView.findViewById(R.id.all_job_post_title);
            mTitle.setText(title);
        }

        public void setJobDate(String date){
            TextView mDate = myView.findViewById(R.id.all_job_post_date);
            mDate.setText(date);
        }

        public void setJobDesc(String desc){
            TextView mDesc = myView.findViewById(R.id.all_job_post_desc);
            mDesc.setText(desc);
        }

        public void setJobSkills(String skills){
            TextView mSkills = myView.findViewById(R.id.all_job_post_skills);
            mSkills.setText(skills);
        }

        public void setJobSalary(String salary){
            TextView mSalary = myView.findViewById(R.id.all_job_post_skills);
            mSalary.setText(salary);
        }
    }
}
