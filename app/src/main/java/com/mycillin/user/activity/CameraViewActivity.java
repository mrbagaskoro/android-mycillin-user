package com.mycillin.user.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.mycillin.user.R;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Size;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraViewActivity extends AppCompatActivity {

    @BindView(R.id.cameraViewActivity_cv_camera)
    CameraView cameraView;
    @BindView(R.id.cameraViewActivity_ib_captureBtn)
    ImageButton captureBtn;

    private boolean isCapturingPicture;
    private long captureTime;
    private Size captureNativeSize;

    public static final int REQUEST_CODE_CAMERA = 1001;
    public static final String EXTRA_WIDTH = "EXTRA_WIDTH";
    public static final String EXTRA_HEIGHT = "EXTRA_HEIGHT";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    public static final int DEFAULT_VALUE = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_camera_view);
        ButterKnife.bind(this);

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                isCapturingPicture = false;
                long callbackTime = System.currentTimeMillis();

                if (captureTime == 0) {
                    captureTime = callbackTime - 300;
                }
                if (captureNativeSize == null) {
                    captureNativeSize = cameraView.getPictureSize();
                }

                Intent intent = new Intent();
                intent.putExtra(EXTRA_IMAGE, jpeg);
                intent.putExtra(EXTRA_WIDTH, captureNativeSize.getWidth());
                intent.putExtra(EXTRA_HEIGHT, captureNativeSize.getHeight());
                setResult(REQUEST_CODE_CAMERA, intent);
                finish();

                captureTime = 0;
                captureNativeSize = null;
            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("###", "onClick: WOW");
                cameraView.capturePicture();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }
}
