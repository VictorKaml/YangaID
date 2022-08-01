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

public class ViewMedicalID extends AppCompatActivity {

    private DatabaseReference reference;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_id);

        backButton = (ImageButton) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewMedicalID.this, Home.class));
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users/Id(s)/MedicalVirtualId");

        final TextView mediNameTextView = (TextView) findViewById(R.id.mediName);
        final TextView insuranceNoTextView = (TextView) findViewById(R.id.insuranceNo);
        final TextView validToTextView = (TextView) findViewById(R.id.validTo);

        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MedicalVirtualId medicalVirtualId = snapshot.getValue(MedicalVirtualId.class);

                if ( medicalVirtualId != null) {
                    String mediName = medicalVirtualId.mediName;
                    String insuranceNo = medicalVirtualId.insuranceNo;
                    String validTo = medicalVirtualId.validTo;

                    mediNameTextView.setText(mediName);
                    insuranceNoTextView.setText(insuranceNo);
                    validToTextView.setText(validTo);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewMedicalID.this, Home.class));
    }
}
