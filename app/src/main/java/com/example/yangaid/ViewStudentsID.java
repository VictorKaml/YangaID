
package com.example.yangaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class ViewStudentsID extends AppCompatActivity {

    private DatabaseReference reference;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students_id);

        backButton = (ImageButton) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewStudentsID.this, Home.class));
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users/Id(s)/StudentsVirtualId");

        final TextView studentNameTextView = (TextView) findViewById(R.id.studentName);
        final TextView registerTextView = (TextView) findViewById(R.id.studentReg);
        final TextView branchTextView = (TextView) findViewById(R.id.studentBranch);

        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentsVirtualId studentsVirtualId = snapshot.getValue(StudentsVirtualId.class);

                if (studentsVirtualId != null) {
                    String studentName = studentsVirtualId.studentName;
                    String studentReg = studentsVirtualId.studentReg;
                    String studentBranch = studentsVirtualId.studentBranch;

                    studentNameTextView.setText(studentName);
                    registerTextView.setText(studentReg);
                    branchTextView.setText(studentBranch);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewStudentsID.this, Home.class));
    }
}
