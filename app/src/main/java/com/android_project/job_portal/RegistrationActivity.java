package com.android_project.job_portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailReg;
    private EditText passReg;

    private Button btnReg;
    private Button btnLogin;

    //Firebase Auth
    private FirebaseAuth mAuth;

    //Progress Display
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        Register();
    }

    private void Register() {
        emailReg = findViewById(R.id.register_email);
        passReg = findViewById(R.id.register_password);

        btnReg = findViewById(R.id.btnreg_register);
        btnLogin = findViewById(R.id.btnreg_login);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailReg.getText().toString().trim();
                String pass = passReg.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailReg.setError("Email is a required Field!");
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    passReg.setError("Password is a required Field!");
                    return;
                }
                mDialog.setMessage("Processing...");
                mDialog.show();

                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_LONG).show();
                        }
                        mDialog.dismiss();
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}