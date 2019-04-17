package com.acmeselect.camerapreview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button tvBtn;
    private FrameLayout flContainer;
    private CameraPreview cameraPreview;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBtn = (Button) findViewById(R.id.tv_btn);
        flContainer = (FrameLayout) findViewById(R.id.fl_container);

        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraWithPermissions();
            }
        });
    }

    private void openCameraWithPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            Toast.makeText(this, "打开相机权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCamera() {
        camera = Camera.open();
        cameraPreview = new CameraPreview(this, camera);
        flContainer.addView(cameraPreview);
    }
}
