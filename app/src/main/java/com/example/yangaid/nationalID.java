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

public class nationalID extends AppCompatActivity implements View.OnClickListener {

    private EditText firstName,lastName, gender, dateofissue, dateofbirth, nationality, idNo;
    private Button proceed;

    private ProgressDialog mLoadingBar;

    private ImageButton backButton;

    private  FirebaseDatabase firebaseDatabase;
    private  DatabaseReference dRef;

    NationalVirtualId nationalVirtualId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_national_id);

        backButton = (ImageButton) findViewById(R.id.backTo);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        gender = (EditText) findViewById(R.id.gender);
        idNo = (EditText)findViewById(R.id.idNo);
        nationality = (EditText) findViewById(R.id.nationality);
        dateofbirth = (EditText) findViewById(R.id.dateofbirth);
        dateofissue = (EditText) findViewById(R.id.dateofissue);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dRef = firebaseDatabase.getReference("Users/Id(s)/NationalVirtualId");

        nationalVirtualId = new NationalVirtualId();
        
        proceed = (Button) findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

        mLoadingBar = new ProgressDialog(nationalID.this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(nationalID.this, Home.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(nationalID.this, Home.class));
    }

    private void addDataToFirebase(String fName, String lName, String genderMy, String nationalityMy, String idNoMy, String dateofbirthMy, String dateofissueMy) {

        nationalVirtualId.setfName(fName);
        nationalVirtualId.setlName(lName);
        nationalVirtualId.setGenderMy(genderMy);
        nationalVirtualId.setIdNoMy(idNoMy);
        nationalVirtualId.setNationalMy(nationalityMy);
        nationalVirtualId.setDateOfBirthMy(dateofbirthMy);
        nationalVirtualId.setDateOfIssueMy(dateofissueMy);

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(nationalVirtualId);

                mLoadingBar.cancel();
                Toast.makeText(nationalID.this, "Virtual ID has been created successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(nationalID.this, Home.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                mLoadingBar.cancel();
                Toast.makeText(nationalID.this, "Failed to create Virtual ID!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(nationalID.this, nationalID.class));


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

        String FName = firstName.getText().toString().trim();
        String LName = lastName.getText().toString().trim();
        String genderMy = gender.getText().toString().trim();
        String nationalityMy = nationality.getText().toString().trim();
        String idNoMy = idNo.getText().toString().trim();
        String dateofissueMy = dateofissue.getText().toString().trim();
        String dateofbirthMy = dateofbirth.getText().toString().trim();

        if (TextUtils.isEmpty(FName) && TextUtils.isEmpty(FName) && TextUtils.isEmpty(genderMy) && TextUtils.isEmpty(nationalityMy) && TextUtils.isEmpty(idNoMy) && TextUtils.isEmpty(dateofissueMy) && TextUtils.isEmpty(dateofbirthMy)) {
            Toast.makeText(nationalID.this, "Please enter the required details!", Toast.LENGTH_SHORT).show();
        }else {
            mLoadingBar.setMessage("Creating Virtual ID...");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            addDataToFirebase(FName, LName, genderMy, nationalityMy, idNoMy, dateofbirthMy, dateofissueMy);
        }
    }
}
