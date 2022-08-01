package com.example.yangaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class qrCode extends AppCompatActivity {

    private static final String TAG = com.example.yangaid.qrCode.class.getSimpleName();
    int progress = 0;
    ProgressBar simpleProgressBar;
    Handler handler = new Handler();

    private ImageButton home, search, qrCode, profile, shareQr;
    private ImageView QrImage, done;
    private  TextView percentage, percentage2, status, UID4;
    private ProgressDialog mLoadingBar;

    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        UID4 = (TextView) findViewById(R.id.UID4);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        percentage = (TextView) findViewById(R.id.percentage);
        percentage2 = (TextView) findViewById(R.id.percentage2);
        status = (TextView) findViewById(R.id.status);
        done =(ImageView) findViewById(R.id.done);

        new Thread((new Runnable() {
            @Override
            public void run() {
                while (progress < 100){
                    progress += 1;

                    try {
                        Thread.sleep(150);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            simpleProgressBar.setProgress(progress);
                            percentage.setText(progress + "%");
                            percentage2.setText(progress + "/100");
                            status.setText("Loading...");

                            if (progress == 100) {
                                shareQr.setVisibility(View.VISIBLE);
                                done.setVisibility(View.VISIBLE);
                                status.setText("Complete");
                                generateQR();
                            }
                        }
                    });

                }

            }
        })).start();

        QrImage = (ImageView) findViewById(R.id.NationalQR);

        home = (ImageButton) findViewById(R.id.home);
        search = (ImageButton) findViewById(R.id.search);
        qrCode = (ImageButton) findViewById(R.id.qrcode);
        profile = (ImageButton) findViewById(R.id.profile);

        shareQr = (ImageButton) findViewById(R.id.share);
        mLoadingBar = new ProgressDialog(qrCode.this);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(qrCode.this, Home.class));
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(qrCode.this, search.class));
            }
        });

        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(qrCode.this, qrCode.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(qrCode.this, profile.class));
            }
        });

        shareQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingBar.setMessage("Please wait a moment...");
                mLoadingBar.setCanceledOnTouchOutside(false);
                mLoadingBar.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            mLoadingBar.cancel();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(qrCode.this, Home.class));
    }

    private void generateQR() {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();

        Point point = new Point();
        display.getSize(point);

        int width = point.x;
        int height = point.y;

        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        String User = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UID4.setText(User);

        qrgEncoder = new QRGEncoder(UID4.getText().toString(), null, QRGContents.Type.TEXT, dimen);
        qrgEncoder.setColorBlack(Color.parseColor("#00C853"));
        qrgEncoder.setColorWhite(Color.parseColor("#FF000000"));
        try {

            bitmap = qrgEncoder.getBitmap();

            QrImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }
    }
}
