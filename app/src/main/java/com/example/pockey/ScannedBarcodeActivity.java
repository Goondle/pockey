package com.example.pockey;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScannedBarcodeActivity extends AppCompatActivity {


    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private int year, month, day;
    private int trash2, hour, minute, second;
    private int trash1, fik, rnk, sumqr;
    private String trash3;
    //fik = фиксальный признак
    //rnk = РНК

    Button btnAction;
    String intentData = "";
    boolean isEmail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_barcode);
        initViews();

        //начало кнопки назад
        Button buttonBack = findViewById(R.id.buttonback1);
        buttonBack.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(ScannedBarcodeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {
            }
        });
        //конец кнопки назад
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intentData.length() > 0) {
                    if (isEmail)
                        startActivity(new Intent(ScannedBarcodeActivity.this, QRActivity.class).putExtra("email_address", intentData));
                    else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
                    }
                }
            }
        });
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Сканер запущен", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannedBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannedBarcodeActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "Сканирование остановлено.", Toast.LENGTH_SHORT).show();
                //To prevent memory leaks barcode scanner has been stopped
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {
                            String srt = "ofd1.kz";
                            String srt1 = "oofd.kz";
                            intentData = barcodes.valueAt(0).displayValue;

                            if (intentData.contains(srt) || intentData.contains(srt1)) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                                btnAction.setText("Добавить средства");
                            }
                            else {
                                isEmail = false;
                                btnAction.setText("Открыть ссылку");
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodeValue.setText(intentData);

                            }
//                            if (barcodes.valueAt(0).displayValue != null) {
//                                txtBarcodeValue.removeCallbacks(null);
//                                intentData = barcodes.valueAt(0).displayValue;
//                                txtBarcodeValue.setText(intentData);
//                                isEmail = true;
//                                btnAction.setText("Добавить средства");
//                            }
//                            else {
//                                    isEmail = false;
//                                    btnAction.setText("Открыть ссылку");
//                                    intentData = barcodes.valueAt(0).displayValue;
//                                    txtBarcodeValue.setText(intentData);
//
//                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }
}