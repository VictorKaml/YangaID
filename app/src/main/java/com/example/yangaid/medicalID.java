package com.example.yangaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class medicalID extends AppCompatActivity implements View.OnClickListener {

    private EditText medName,insurancNo, validT;
    private Button proceed;

    private ProgressDialog mLoadingBar;

    private ImageButton backButton;

    private  FirebaseDatabase firebaseDatabase;
    private  DatabaseReference dRef;

    MedicalVirtualId medicalVirtualId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_id);

        backButton = (ImageButton) findViewById(R.id.backTo);
        medName = (EditText) findViewById(R.id.mediName);
        insurancNo = (EditText) findViewById(R.id.insuranceNo);
        validT = (EditText) findViewById(R.id.validTo);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dRef = firebaseDatabase.getReference("Users/Id(s)/MedicalVirtualId");

        medicalVirtualId = new MedicalVirtualId();

        proceed = (Button) findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

        mLoadingBar = new ProgressDialog(medicalID.this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(medicalID.this, Home.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(medicalID.this, Home.class));
    }

    private void addDataToFirebase(String mediName, String insuranceNo, String validTo) {

        medicalVirtualId.setMediName(mediName);
        medicalVirtualId.setInsuranceNo(insuranceNo);
        medicalVirtualId.setValidTo(validTo);

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(medicalVirtualId);

                mLoadingBar.cancel();
                Toast.makeText(medicalID.this, "Virtual ID has been created successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(medicalID.this, Home.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                mLoadingBar.cancel();
                Toast.makeText(medicalID.this, "Failed to create Virtual ID!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(medicalID.this, medicalID.class));


            }
        });
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

        String mediName = medName.getText().toString().trim();
        String insuranceNo = insurancNo.getText().toString().trim();
        String validTo = validT.getText().toString().trim();

        if (TextUtils.isEmpty(mediName) && TextUtils.isEmpty(insuranceNo) && TextUtils.isEmpty(validTo)) {
            Toast.makeText(medicalID.this, "Please enter the required details!", Toast.LENGTH_SHORT).show();
        } else {
            mLoadingBar.setMessage("Creating Virtual ID...");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            addDataToFirebase(mediName, insuranceNo, validTo);
        }
    }
}
