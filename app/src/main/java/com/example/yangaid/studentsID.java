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

public class studentsID extends AppCompatActivity implements View.OnClickListener {

    private EditText sName,sNo, sBranch;
    private Button proceed;

    private ProgressDialog mLoadingBar;

    private ImageButton backButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dRef;

    StudentsVirtualId studentsVirtualId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_id);

        sName = (EditText) findViewById(R.id.studentName);
        sNo = (EditText) findViewById(R.id.studentReg);
        sBranch = (EditText) findViewById(R.id.studentBranch);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dRef = firebaseDatabase.getReference("Users/Id(s)/StudentsVirtualId");

        studentsVirtualId = new StudentsVirtualId();

        proceed = (Button) findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

        mLoadingBar = new ProgressDialog(studentsID.this);

        backButton = (ImageButton) findViewById(R.id.backTo);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(studentsID.this, Home.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(studentsID.this, Home.class));
    }

    private void addDataToFirebase(String studentName, String studentReg, String studentBranch) {

        studentsVirtualId.setStudentName(studentName);
        studentsVirtualId.setStudentReg(studentReg);
        studentsVirtualId.setStudentBranch(studentBranch);

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(studentsVirtualId);

                mLoadingBar.cancel();
                Toast.makeText(studentsID.this, "Virtual ID has been created successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(studentsID.this, Home.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                mLoadingBar.cancel();
                Toast.makeText(studentsID.this, "Failed to create Virtual ID!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(studentsID.this, nationalID.class));


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

        String studentName = sName.getText().toString().trim();
        String studentReg = sNo.getText().toString().trim();
        String studentBranch = sBranch.getText().toString().trim();


        if (TextUtils.isEmpty(studentName) && TextUtils.isEmpty(studentReg) && TextUtils.isEmpty(studentBranch)) {
            Toast.makeText(studentsID.this, "Please enter the required details!", Toast.LENGTH_SHORT).show();
        }else {
            mLoadingBar.setMessage("Creating Virtual ID...");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            addDataToFirebase(studentName, studentReg, studentBranch);
        }
    }
}
