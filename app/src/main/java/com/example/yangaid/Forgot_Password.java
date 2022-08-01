package com.example.yangaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {

    private EditText emailAddress;
    private Button resetPassword;
    private ProgressDialog mLoadingBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailAddress = (EditText) findViewById(R.id.emailAddress);
        resetPassword = (Button) findViewById(R.id.resetPassword);

        mLoadingBar = new ProgressDialog(Forgot_Password.this);
        auth = FirebaseAuth.getInstance();
        
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPassword();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Forgot_Password.this, MainActivity.class));
    }

    private void resetPassword() {
        String email = emailAddress.getText().toString().trim();

        if (email.isEmpty()){
            emailAddress.setError("Email address is required!");
            emailAddress.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailAddress.setError("Please provide valid email Address!");
            emailAddress.requestFocus();
            return;
        }

        mLoadingBar.setMessage("Resetting Password...");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mLoadingBar.cancel();
                    Toast.makeText(Forgot_Password.this, "Check your email to reset your password", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Forgot_Password.this, MainActivity.class));

                }else {
                    mLoadingBar.cancel();
                    Toast.makeText(Forgot_Password.this, "Try again! Something wrong happened", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
