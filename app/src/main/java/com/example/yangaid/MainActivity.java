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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView signUp, forgotPassword;
    private Button signIn;
    private EditText emailAddress, password;

    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = (TextView) findViewById(R.id.signUp);
        signUp.setOnClickListener(this);

        emailAddress = (EditText) findViewById(R.id.emailAddress);
        password = (EditText) findViewById(R.id.password);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

        mLoadingBar = new ProgressDialog(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()){
            case R.id.signUp:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.signIn:
                UserLogin();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, Forgot_Password.class));
                break;
        }
    }

    @Override
    public void onBackPressed(){

    }

    private void UserLogin() {
        String email = emailAddress.getText().toString().trim();
        String passwordUser = password.getText().toString().trim();

        if(email.isEmpty()){
            emailAddress.setError("Email address is required!");
            emailAddress.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailAddress.setError("Please enter a valid email address!");
            emailAddress.requestFocus();
            return;
        }

        if(passwordUser.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailAddress.setError("Please enter a valid email address!");
            emailAddress.requestFocus();
            return;
        }

        if(passwordUser.length() < 6){
            password.setError("Min password length is 6 characters!");
            password.requestFocus();
            return;
        }

        mLoadingBar.setMessage("Signing In...");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();

        mAuth.signInWithEmailAndPassword(email, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    mLoadingBar.cancel();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()){
                        Toast.makeText(MainActivity.this, "Signed In!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, Home.class));
                    }else  {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email to verify your account", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                    }

                }else {
                    mLoadingBar.cancel();
                    Toast.makeText(MainActivity.this, "Failed to Sign In! Please check your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
