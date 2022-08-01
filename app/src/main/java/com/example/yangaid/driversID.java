package com.example.yangaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class driversID extends AppCompatActivity implements View.OnClickListener {

    private EditText dSurname,dName, dBirth, dTRN, dValidity, dIssuedBy, dLicence, dCode, dFirstIssue;
    private Button proceed;

    private ProgressDialog mLoadingBar;

    private ImageButton backButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dRef;

    DriversVirtualId driversVirtualId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_id);

        dSurname = (EditText) findViewById(R.id.driverSur);
        dName = (EditText) findViewById(R.id.driversNam);
        dBirth = (EditText) findViewById(R.id.driverDateOfBirth);
        dTRN = (EditText)findViewById(R.id.driverTRNo);
        dValidity = (EditText) findViewById(R.id.driverValidity);
        dIssuedBy = (EditText) findViewById(R.id.driverIssueBy);
        dLicence = (EditText) findViewById(R.id.driverLicence);
        dCode = (EditText) findViewById(R.id.driverCod);
        dFirstIssue = (EditText) findViewById(R.id.driverFirstIssue);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dRef = firebaseDatabase.getReference("Users/Id(s)/DriversVirtualId");

        driversVirtualId = new DriversVirtualId();

        proceed = (Button) findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

        mLoadingBar = new ProgressDialog(driversID.this);

        backButton = (ImageButton) findViewById(R.id.backTo);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(driversID.this, Home.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(driversID.this, Home.class));
    }

    private void addDataToFirebase(String driversSurname, String driversName, String driversDob, String driversTRN, String driversValidity, String driversIssuedBy, String driversLicence, String driversCode, String driversFirstIssue) {

        driversVirtualId.setDriversSurname(driversSurname);
        driversVirtualId.setDriversName(driversName);
        driversVirtualId.setDriversDob(driversDob);
        driversVirtualId.setDriversTRN(driversTRN);
        driversVirtualId.setDriversValidity(driversValidity);
        driversVirtualId.setDriversFirstIssue(driversFirstIssue);
        driversVirtualId.setDriversIssuedBy(driversIssuedBy);
        driversVirtualId.setDriversLicence(driversLicence);
        driversVirtualId.setDriversCode(driversCode);

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(driversVirtualId);

                mLoadingBar.cancel();
                Toast.makeText(driversID.this, "Virtual ID has been created successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(driversID.this, Home.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                mLoadingBar.cancel();
                Toast.makeText(driversID.this, "Failed to create Virtual ID!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(driversID.this, driversID.class));


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

        String driversSurname = dSurname.getText().toString().trim();
        String driversName = dName.getText().toString().trim();
        String driversDob = dBirth.getText().toString().trim();
        String driversTRN = dTRN.getText().toString().trim();
        String driversValidity = dValidity.getText().toString().trim();
        String driversIssuedBy = dIssuedBy.getText().toString().trim();
        String driversLicence = dLicence.getText().toString().trim();
        String driversCode = dCode.getText().toString().trim();
        String driversFirstIssue = dFirstIssue.getText().toString().trim();

        if (TextUtils.isEmpty(driversSurname) && TextUtils.isEmpty(driversName) && TextUtils.isEmpty(driversDob) && TextUtils.isEmpty(driversTRN) && TextUtils.isEmpty(driversValidity) && TextUtils.isEmpty(driversIssuedBy) && TextUtils.isEmpty(driversLicence) && TextUtils.isEmpty(driversCode) && TextUtils.isEmpty(driversFirstIssue)) {
            Toast.makeText(driversID.this, "Please enter the required details!", Toast.LENGTH_SHORT).show();
        }else {
            mLoadingBar.setMessage("Creating Virtual ID...");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            addDataToFirebase(driversSurname, driversName, driversDob, driversTRN, driversValidity, driversIssuedBy, driversLicence, driversCode,driversFirstIssue);
        }
    }
}
