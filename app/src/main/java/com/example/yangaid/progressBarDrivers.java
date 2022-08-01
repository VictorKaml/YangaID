package com.example.yangaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class progressBarDrivers extends AppCompatActivity {
    int progress = 0;
    ProgressBar simpleProgressBar;
    TextView percentage, percentage2, status;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar_drivers);

        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        percentage = (TextView) findViewById(R.id.percentage);
        percentage2 = (TextView) findViewById(R.id.percentage2);
        status = (TextView) findViewById(R.id.status);
        new Thread((new Runnable() {
            @Override
            public void run() {
                while (progress < 100){
                    progress += 2;

                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            simpleProgressBar.setProgress(progress);
                            percentage.setText(progress + "%");
                            percentage2.setText(progress + "/100");
                            status.setText("Generating...");

                            if (progress == 100) {
                                startActivity(new Intent(progressBarDrivers.this, ViewDriversID.class));
                                finish();
                            }
                            if (progress > 65) {
                                status.setText("Opening...");
                            }
                        }
                    });

                }

            }
        })).start();
    }

    @Override
    public void onBackPressed() {

    }
}
