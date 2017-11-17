package com.mycillin.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.util.ProgressBarHandler;
import com.otaliastudios.cameraview.CameraUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InsuranceActivity extends AppCompatActivity {

    @BindView(R.id.insuranceActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.insuranceActivity_et_insuranceProvider)
    TextView insuranceProviderEdtxt;
    @BindView(R.id.insuranceActivity_et_insurancePolicyNumber)
    TextView insurancePolicyNumberEdtxt;
    @BindView(R.id.insuranceActivity_et_policyHolderName)
    TextView policyHolderNameEdtxt;
    @BindView(R.id.insuranceActivity_et_insuredName)
    TextView insuredNameEdtxt;
    @BindView(R.id.insuranceActivity_iv_photoIcon)
    ImageView photoIcon;
    @BindView(R.id.insuranceActivity_iv_photoPreview)
    ImageView photoPreview;
    @BindView(R.id.insuranceActivity_tv_imageWarning)
    TextView imageWarningTxt;

    private ProgressBarHandler progressBarHandler;

    private static WeakReference<byte[]> imagePreview;

    public static void setImage(@Nullable byte[] im) {
        imagePreview = im != null ? new WeakReference<>(im) : null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.insuranceActivity_title);

        progressBarHandler = new ProgressBarHandler(this);

        photoIcon.setOnClickListener(new View.OnClickListener() {
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
                int width = data.getIntExtra(CameraViewActivity.EXTRA_WIDTH, CameraViewActivity.DEFAULT_VALUE);
                int height = data.getIntExtra(CameraViewActivity.EXTRA_HEIGHT, CameraViewActivity.DEFAULT_VALUE);

                byte[] bytes = imagePreview == null ? null : imagePreview.get();
                if (bytes != null) {
                    CameraUtils.decodeBitmap(bytes, 1000, 1000, new CameraUtils.BitmapCallback() {
                        @Override
                        public void onBitmapReady(Bitmap bitmap) {
                            photoIcon.setVisibility(View.GONE);
                            photoPreview.setVisibility(View.VISIBLE);
                            photoPreview.setImageBitmap(bitmap);
                        }
                    });
                }

                if(photoPreview.getVisibility() == View.VISIBLE && photoIcon.getVisibility() == View.GONE) {
                    imageWarningTxt.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            boolean isValid = true;

            if(insuranceProviderEdtxt.getText().toString().trim().equals("")) {
                insuranceProviderEdtxt.setError(getString(R.string.insuranceActivity_insuranceProviderWarning));
                isValid = false;
            }
            if(insurancePolicyNumberEdtxt.getText().toString().trim().equals("")) {
                insurancePolicyNumberEdtxt.setError(getString(R.string.insuranceActivity_insurancePolicyNumberWarning));
                isValid = false;
            }
            if(policyHolderNameEdtxt.getText().toString().trim().equals("")) {
                policyHolderNameEdtxt.setError(getString(R.string.insuranceActivity_policyHolderNameWarning));
                isValid = false;
            }
            if(insuredNameEdtxt.getText().toString().trim().equals("")) {
                insuredNameEdtxt.setError(getString(R.string.insuranceActivity_insuredNameWarning));
                isValid = false;
            }
            if(photoPreview.getVisibility() == View.GONE && photoIcon.getVisibility() == View.VISIBLE) {
                imageWarningTxt.setVisibility(View.VISIBLE);
                isValid = false;
            }

            if(isValid) {
                imageWarningTxt.setVisibility(View.INVISIBLE);

                new AlertDialog.Builder(InsuranceActivity.this)
                        .setTitle(getString(R.string.menu_save))
                        .setMessage(R.string.accountDetailActivity_saveMessage)
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton(getString(R.string.menu_save), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO: 18-Nov-17 ADD INSURANCE
                            }
                        })
                        .setNegativeButton(R.string.accountActivity_cancelTitle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
