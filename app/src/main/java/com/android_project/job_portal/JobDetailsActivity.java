package com.android_project.job_portal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class JobDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView mTitle, mDate, mDesc, mSkills, mSalary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        toolbar = findViewById(R.id.toolbar_jobDetails);
        mTitle = findViewById(R.id.jobDetails_title);
        mDate = findViewById(R.id.jobDetails_date);
        mDesc = findViewById(R.id.jobDetails_desc);
        mSkills = findViewById(R.id.jobDetails_skills);
        mSalary = findViewById(R.id.jobDetails_salary);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Details");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Receive data using intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        String date = intent.getStringExtra("Date");
        String desc = intent.getStringExtra("Description");
        String skills = intent.getStringExtra("Skills");
        String salary = intent.getStringExtra("Salary");

        mTitle.setText(title);
        mDate.setText(date);
        mDesc.setText(desc);
        mSalary.setText(salary);
        mSkills.setText(skills);

    }
}