package com.mycillin.user.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mycillin.user.R;
import com.otaliastudios.cameraview.CameraUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InsuranceActivity extends AppCompatActivity {

    @BindView(R.id.insuranceActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.insuranceActivity_ll_photoArea)
    LinearLayout photoArea;
    @BindView(R.id.insuranceActivity_iv_photoIcon)
    ImageView photoIcon;
    @BindView(R.id.insuranceActivity_iv_photoPreview)
    ImageView photoPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.insuranceActivity_title);

        photoArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsuranceActivity.this, CameraViewActivity.class);
                startActivityForResult(intent, CameraViewActivity.REQUEST_CODE_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            if(requestCode == CameraViewActivity.REQUEST_CODE_CAMERA) {
                byte[] image = data.getByteArrayExtra(CameraViewActivity.EXTRA_IMAGE);
                int width = data.getIntExtra(CameraViewActivity.EXTRA_WIDTH, CameraViewActivity.DEFAULT_VALUE);
                int height = data.getIntExtra(CameraViewActivity.EXTRA_HEIGHT, CameraViewActivity.DEFAULT_VALUE);

                CameraUtils.decodeBitmap(image, new CameraUtils.BitmapCallback() {
                    @Override
                    public void onBitmapReady(Bitmap bitmap) {
                        photoIcon.setVisibility(View.GONE);
                        photoPreview.setVisibility(View.VISIBLE);
                        photoPreview.setImageBitmap(bitmap);
                    }
                });
            }
        }
    }
}
