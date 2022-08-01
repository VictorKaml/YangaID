package com.example.yangaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewDriversID extends AppCompatActivity {

    private DatabaseReference reference;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drivers_id);

        backButton = (ImageButton) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewDriversID.this, Home.class));
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users/Id(s)/DriversVirtualId");

        final TextView surname = (TextView) findViewById(R.id.driverSurname);
        final TextView dname = (TextView) findViewById(R.id.driverName);
        final TextView dob = (TextView) findViewById(R.id.driverDob);
        final TextView trn = (TextView) findViewById(R.id.driverTRN);
        final TextView code = (TextView) findViewById(R.id.driverCode);
        final TextView validity = (TextView) findViewById(R.id.LicenceValidity);
        final TextView issuedBy = (TextView) findViewById(R.id.LicenceIssueBy);
        final TextView firstIssue = (TextView) findViewById(R.id.driverIssue);
        final TextView licence = (TextView) findViewById(R.id.driverLicenceNo);

        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DriversVirtualId driversVirtualId = snapshot.getValue(DriversVirtualId.class);

                if (driversVirtualId != null) {
                    String driversSurname = driversVirtualId.driversSurname;
                    String driversName = driversVirtualId.driversName;
                    String driversDob = driversVirtualId.driversDob;
                    String driversTrn = driversVirtualId.driversTRN;
                    String driversCode = driversVirtualId.driversCode;
                    String driversValidity = driversVirtualId.driversValidity;
                    String driversIssuedBy = driversVirtualId.driversIssuedBy;
                    String driversFirstIssue = driversVirtualId.driversFirstIssue;
                    String driversLicence = driversVirtualId.driversLicence;

                    surname.setText(driversSurname);
                    dname.setText(driversName);
                    dob.setText(driversDob);
                    trn.setText(driversTrn);
                    code.setText(driversCode);
                    validity.setText(driversValidity);
                    issuedBy.setText(driversIssuedBy);
                    firstIssue.setText(driversFirstIssue);
                    licence.setText(driversLicence);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewDriversID.this, Home.class));
    }
}
