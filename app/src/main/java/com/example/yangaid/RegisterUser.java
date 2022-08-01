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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private EditText password,phone_number, emailAddress, fullName;
    private Button proceed;

    private ProgressDialog mLoadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        proceed = (Button) findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

        emailAddress = (EditText) findViewById(R.id.emailAddress);
        password = (EditText) findViewById(R.id.password);
        phone_number = (EditText) findViewById(R.id.phone_number);
        fullName = (EditText) findViewById(R.id.fullName);

        mLoadingBar = new ProgressDialog(RegisterUser.this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterUser.this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.proceed:
                proceed();
                break;
        }

    }

    private void proceed() {
        String email = emailAddress.getText().toString().trim();
        String phone = phone_number.getText().toString().trim();
        String name = fullName.getText().toString().trim();
        String passwordUser = password.getText().toString().trim();

        if(name.isEmpty()){
            fullName.setError("Full name is required!");
            fullName.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            phone_number.setError("Phone number is required!");
            phone_number.requestFocus();
            return;
        }

        if (phone.length() < 10){
            phone_number.setError("Min phone number length should be 10 digits!");
            phone_number.requestFocus();
            return;
        }

        if(email.isEmpty()){
            emailAddress.setError("Email is required!");
            emailAddress.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailAddress.setError("Please provide a valid email address!");
            emailAddress.requestFocus();
            return;
        }

        if(passwordUser.isEmpty()){
            password.setError("Email address is required!");
            password.requestFocus();
            return;
        }

        if (passwordUser.length() < 6){
            password.setError("Min password length should be 6 characters!");
            password.requestFocus();
            return;
        }

        mLoadingBar.setMessage("Signing Up...");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();

        mAuth.createUserWithEmailAndPassword(email, passwordUser)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            mLoadingBar.cancel();
                            User user = new User(name, email, phone, passwordUser);

                            FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "Users has been registered successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                    }else {
                                        Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterUser.this, RegisterUser.class));

                                    }
                                }
                            });
                        }else {
                            mLoadingBar.cancel();
                            Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
}
