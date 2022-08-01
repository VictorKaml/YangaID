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

public class NationalShare extends AppCompatActivity {

    private DatabaseReference reference;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_national_share);

        backButton = (ImageButton) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NationalShare.this, Home.class));
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users/Id(s)/NationalVirtualId");


        final TextView firstNameTextView = (TextView) findViewById(R.id.firstName);
        final TextView genderTextView = (TextView) findViewById(R.id.gender);
        final TextView lastNameTextView = (TextView) findViewById(R.id.lastName);
        final TextView dateofbirthTextView = (TextView) findViewById(R.id.dateofbirth);
        final TextView dateofissueTextView = (TextView) findViewById(R.id.dateofissue);
        final TextView idNoTextView = (TextView) findViewById(R.id.idNo);
        final TextView nationalityTextView = (TextView) findViewById(R.id.nationality);

        Intent intent = getIntent();
        String StudentsUid = intent.getStringExtra("Student");

        reference.child(StudentsUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NationalVirtualId nationalVirtualId = snapshot.getValue(NationalVirtualId.class);

                if (nationalVirtualId != null) {
                    String fName = nationalVirtualId.fName;
                    String genderMy = nationalVirtualId.genderMy;
                    String lastName = nationalVirtualId.lName;
                    String dateOfBirthMy = nationalVirtualId.dateOfBirthMy;
                    String dateofIssueMy = nationalVirtualId.dateOfIssueMy;
                    String idNoMy = nationalVirtualId.idNoMy;
                    String nationalityMy = nationalVirtualId.nationalMy;

                    firstNameTextView.setText(fName);
                    genderTextView.setText(genderMy);
                    lastNameTextView.setText(lastName);
                    dateofbirthTextView.setText(dateOfBirthMy);
                    dateofissueTextView.setText(dateofIssueMy);
                    idNoTextView.setText(idNoMy);
                    nationalityTextView.setText(nationalityMy);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NationalShare.this, Home.class));
    }
}

