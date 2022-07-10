package com.example.miniproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;


import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class Main_Page extends AppCompatActivity {
    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.txttxt);
        TextRecognizer textRecognizer = (TextRecognizer) new TextRecognizerOptions.Builder().build();
        if (textRecognizer.equals(textRecognizer)) {
            Log.w("MainActivity", "Detector dependencies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), (Detector<?>) textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(Main_Page.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
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

//            textRecognizer.setProcesses(new Detector.Processor<Text.TextBlock>() {
//                @Override
//                public void release() {
//
//                }
//
//                @Override
//                public void receiveDetections(@NonNull Detector.Detections<Text.TextBlock> detections) {
//                    final SparseArray<Text.TextBlock> items = detections.getDetectedItems();
//                    if (items.size() != 0) {
//                        textView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                StringBuilder stringBuilder = new StringBuilder();
//                                for (int i = 0; i < items.size(); i++) {
//                                    Text.TextBlock item = items.valueAt(i);
//                                    stringBuilder.append(item.getText());
//                                    stringBuilder.append("\n");
//                                }
//                                textView.setText(stringBuilder.toString());
//                            }
//                        });
//                    }
//                }
//            });



        }
    }
}
//
//
//            });








