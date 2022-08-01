package com.example.yangaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private CardView nationalID, studentsID, medicalID, driversID;
    private ImageButton home,qrCode, search, profile, NavBar, settings;
    private ProgressDialog mLoadingBar;
    private TextView greetings;
    private DatabaseReference reference;
    private long pressedTime;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reference = FirebaseDatabase.getInstance().getReference("Users/Id(s)/NationalVirtualId");

        final TextView userNameTextView = (TextView) findViewById(R.id.greetingUser);

        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NationalVirtualId nationalVirtualId = snapshot.getValue(NationalVirtualId.class);

                if (nationalVirtualId != null) {
                    String fName = nationalVirtualId.fName;

                    userNameTextView.setText(fName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        greetings = (TextView) findViewById(R.id.greeting);
        displayGreeting();

        nationalID = (CardView) findViewById(R.id.nationalID);
        studentsID = (CardView) findViewById(R.id.studentsID);
        medicalID = (CardView) findViewById(R.id.medicalID);
        driversID = (CardView) findViewById(R.id.driversID);
        home = (ImageButton) findViewById(R.id.home);
        search = (ImageButton) findViewById(R.id.search);
        qrCode = (ImageButton) findViewById(R.id.qrcode);
        profile = (ImageButton) findViewById(R.id.profile);

        settings = (ImageButton) findViewById(R.id.settings);

        mLoadingBar = new ProgressDialog(Home.this);

        NavBar= (ImageButton)  findViewById(R.id.NavBar);
        NavBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }

        });

        nationalID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomBox1();
            }
        });

        studentsID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomBox2();
            }
        });

        medicalID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomBox3();
            }
        });

        driversID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomBox4();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Home.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, search.class));
            }
        });

        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, qrCode.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, profile.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void displayGreeting() {
        Calendar cal = Calendar.getInstance();
        int hours = cal.get(Calendar.HOUR_OF_DAY);

        if (hours>=12 && hours<=17){
            greetings.setText("Good Afternoon,");

        }else if (hours >= 17 && hours < 21){
            greetings.setText("Good Evening,");
        }else if (hours >= 21 && hours < 24){
            greetings.setText("Good Night,");
        }else {
            greetings.setText("Good Morning,");
        }

    }

    private void showCustomBox4() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Home.this, R.style.BottomSheetTransparent);
        bottomSheetDialog.setContentView(R.layout.custom_box4);

        LinearLayout create = bottomSheetDialog.findViewById(R.id.create);
        LinearLayout view = bottomSheetDialog.findViewById(R.id.view);
        LinearLayout share = bottomSheetDialog.findViewById(R.id.share);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, progressBarDrivers.class));
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, driversID.class));
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, scan3.class));
                finish();
            }
        });

        bottomSheetDialog.show();
    }

    private void showCustomBox3() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Home.this, R.style.BottomSheetTransparent);
        bottomSheetDialog.setContentView(R.layout.custom_box3);

        LinearLayout create = bottomSheetDialog.findViewById(R.id.create);
        LinearLayout view = bottomSheetDialog.findViewById(R.id.view);
        LinearLayout share = bottomSheetDialog.findViewById(R.id.share);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, progressBarMedical.class));
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, medicalID.class));
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, scan2.class));
                finish();
            }
        });

        bottomSheetDialog.show();
    }

    private void showCustomBox2() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Home.this, R.style.BottomSheetTransparent);
        bottomSheetDialog.setContentView(R.layout.custom_box2);

        LinearLayout create = bottomSheetDialog.findViewById(R.id.create);
        LinearLayout view = bottomSheetDialog.findViewById(R.id.view);
        LinearLayout share = bottomSheetDialog.findViewById(R.id.share);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, progressBarStudents.class));
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, studentsID.class));
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, scan1.class));
                finish();
            }
        });

        bottomSheetDialog.show();
    }

    private void showCustomBox1() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Home.this, R.style.BottomSheetTransparent);
        bottomSheetDialog.setContentView(R.layout.custom_box1);

        LinearLayout create = bottomSheetDialog.findViewById(R.id.create);
        LinearLayout view = bottomSheetDialog.findViewById(R.id.view);
        LinearLayout share = bottomSheetDialog.findViewById(R.id.share);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, progressBarNational.class));
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, nationalID.class));
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, scanner.class));
                finish();
            }
        });

        bottomSheetDialog.show();
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Home.this, R.style.BottomSheetTransparent);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        LinearLayout help = bottomSheetDialog.findViewById(R.id.help);
        LinearLayout about = bottomSheetDialog.findViewById(R.id.about);
        LinearLayout signOut = bottomSheetDialog.findViewById(R.id.signOut);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, help.class));
                finish();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                startActivity(new Intent(Home.this, about.class));
                finish();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();

                mLoadingBar.setMessage("Signing Out...");
                mLoadingBar.setCanceledOnTouchOutside(false);
                mLoadingBar.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(4000);
                            signOut();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        bottomSheetDialog.show();
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(Home.this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {

    }

}
