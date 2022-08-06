package com.android_project.job_portal;

import com.android_project.job_portal.Model.Data;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class insert_JobPostActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText job_title, job_desc, job_skills, job_salary;
    private Button btnPostJob;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mJobPost, mPublicDb;
    private FirebaseDatabase database;
    //private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_job_post);

        toolbar = findViewById(R.id.insert_jobs_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post Job");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        // Write a message to the database
        database = FirebaseDatabase.getInstance();

        mJobPost = database.getReference().child("Job Post").child(uid);
        mPublicDb = database.getReference().child("Public DB");

        insertJob();
    }

    private void insertJob() {
        job_title = findViewById(R.id.job_title);
        job_desc = findViewById(R.id.job_desc);
        job_skills = findViewById(R.id.job_skills);
        job_salary = findViewById(R.id.job_salary);
        btnPostJob = findViewById(R.id.btn_jobpost);

        btnPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jobTitle = job_title.getText().toString().trim();
                String jobDesc = job_desc.getText().toString().trim();
                String jobSkills = job_skills.getText().toString().trim();
                String jobSalary = job_salary.getText().toString().trim();

                if(TextUtils.isEmpty(jobTitle)){
                    job_title.setError("Title is Required!");
                    return;
                }

                if(TextUtils.isEmpty(jobDesc)){
                    job_desc.setError("Description is Required!");
                    return;
                }

                if(TextUtils.isEmpty(jobSkills)){
                    job_skills.setError("Skills are Required!");
                    return;
                }

                if(TextUtils.isEmpty(jobSalary)){
                    job_salary.setError("Salary is Required!");
                    return;
                }

                String id = mJobPost.push().getKey();

                String date = DateFormat.getDateInstance().format(new Date());
                Data data = new Data(jobTitle, jobDesc, jobSkills, jobSalary, id, date);

                mJobPost.child(id).setValue(data);
                mPublicDb.child(id).setValue(data);

                Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), PostJobsActivity.class));

            }
        });
    }
}