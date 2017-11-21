package com.mycillin.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.accountInsert.ModelResultAccountInsert;
import com.mycillin.user.rest.insuranceInsert.ModelResultInsuranceInsert;
import com.mycillin.user.rest.insuranceProviderList.ModelResultInsuranceProviderList;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;
import com.otaliastudios.cameraview.CameraUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsuranceActivity extends AppCompatActivity {

    @BindView(R.id.insuranceActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.insuranceActivity_et_insuranceProvider)
    EditText insuranceProviderEdtxt;
    @BindView(R.id.insuranceActivity_et_insurancePolicyNumber)
    EditText insurancePolicyNumberEdtxt;
    @BindView(R.id.insuranceActivity_et_policyHolderName)
    EditText policyHolderNameEdtxt;
    @BindView(R.id.insuranceActivity_et_insuredName)
    EditText insuredNameEdtxt;
    @BindView(R.id.insuranceActivity_iv_photoIcon)
    ImageView photoIcon;
    @BindView(R.id.insuranceActivity_iv_photoPreview)
    ImageView photoPreview;
    @BindView(R.id.insuranceActivity_tv_imageWarning)
    TextView imageWarningTxt;

    private ProgressBarHandler progressBarHandler;

    private ArrayList<String> insuranceProviderItems = new ArrayList<>();
    private HashMap<Integer, String> insuranceProviderItemsTemp = new HashMap<>();
    private String selectedInsuranceProvider;

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

        final SpinnerDialog insuranceProviderSpinnerDialog = new SpinnerDialog(InsuranceActivity.this, insuranceProviderItems, getString(R.string.insuranceActivity_insuranceProviderDropdownTitle), R.style.DialogAnimations_SmileWindow);
        insuranceProviderSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                insuranceProviderEdtxt.setText(s);
                for(int j = 0; j < insuranceProviderItemsTemp.size(); j++) {
                    if(insuranceProviderItemsTemp.get(j).split(" - ")[1].equals(s)) {
                        selectedInsuranceProvider = insuranceProviderItemsTemp.get(j).split(" - ")[0];
                    }
                }
            }
        });
        insuranceProviderEdtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInsuranceProviderList(insuranceProviderSpinnerDialog);
            }
        });

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
                            imageWarningTxt.setVisibility(View.INVISIBLE);
                        }
                    });
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
                                doInsert();
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

    private void getInsuranceProviderList(final SpinnerDialog spinnerDialog) {
        progressBarHandler.show();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();
        myCillinAPI.getInsuranceProviderList().enqueue(new Callback<ModelResultInsuranceProviderList>() {
            @Override
            public void onResponse(Call<ModelResultInsuranceProviderList> call, Response<ModelResultInsuranceProviderList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultInsuranceProviderList modelResultInsuranceProviderList = response.body();

                    assert modelResultInsuranceProviderList != null;
                    if(modelResultInsuranceProviderList.getResult().isStatus()) {

                        insuranceProviderItems.clear();
                        insuranceProviderItemsTemp.clear();
                        int size = modelResultInsuranceProviderList.getResult().getData().size();
                        for(int i = 0; i < size; i++) {
                            String insuranceProviderId = modelResultInsuranceProviderList.getResult().getData().get(i).getInsuranceProviderId();
                            String insuranceProviderDesc = modelResultInsuranceProviderList.getResult().getData().get(i).getInsuranceProviderDesc();

                            insuranceProviderItems.add(insuranceProviderDesc);
                            insuranceProviderItemsTemp.put(i, insuranceProviderId + " - " + insuranceProviderDesc);
                        }
                        spinnerDialog.showSpinerDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelResultInsuranceProviderList> call, Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void doInsert() {
        progressBarHandler.show();

        byte[] bytes = imagePreview == null ? null : imagePreview.get();
        if (bytes != null) {
            CameraUtils.decodeBitmap(bytes, 1000, 1000, new CameraUtils.BitmapCallback() {
                @Override
                public void onBitmapReady(Bitmap bitmap) {
                    try {
                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        String token = sessionManager.getUserToken();
                        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

                        String fileName = sessionManager.getUserId() + "_" +
                                getIntent().getStringExtra(InsuranceListActivity.KEY_PARAM_ACCOUNT_RELATION_ID) +
                                "_" + insurancePolicyNumberEdtxt.getText().toString() +  "_" +
                                selectedInsuranceProvider;
                        File file = new File(getApplicationContext().getFilesDir(), fileName + ".jpg");

                        OutputStream os;
                        os = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.flush();
                        os.close();

                        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getUserId());
                        RequestBody relationId = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra(InsuranceListActivity.KEY_PARAM_ACCOUNT_RELATION_ID));
                        RequestBody policyNo = RequestBody.create(MediaType.parse("text/plain"), insurancePolicyNumberEdtxt.getText().toString());
                        RequestBody insuranceProviderId = RequestBody.create(MediaType.parse("text/plain"), selectedInsuranceProvider);
                        RequestBody insuredName = RequestBody.create(MediaType.parse("text/plain"), insuredNameEdtxt.getText().toString());
                        RequestBody insuranceHolder = RequestBody.create(MediaType.parse("text/plain"), policyHolderNameEdtxt.getText().toString());

                        RequestBody insuranceCardImageFile = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part insuranceCardImage = MultipartBody.Part.createFormData("img_insr_card", file.getName(), insuranceCardImageFile);

                        myCillinAPI.doInsertInsurance(token, userId, relationId, policyNo, insuranceProviderId, insuredName, insuranceHolder, insuranceCardImage)
                                .enqueue(new Callback<ModelResultInsuranceInsert>() {
                                    @Override
                                    public void onResponse(@NonNull Call<ModelResultInsuranceInsert> call, @NonNull Response<ModelResultInsuranceInsert> response) {
                                        progressBarHandler.hide();

                                        if(response.isSuccessful()) {
                                            ModelResultInsuranceInsert modelResultInsuranceInsert = response.body();

                                            assert modelResultInsuranceInsert != null;
                                            if(modelResultInsuranceInsert.getResult().isStatus()) {
                                                String message = modelResultInsuranceInsert.getResult().getMessage();
                                                Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT)
                                                        .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                                            @Override
                                                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                                                super.onDismissed(transientBottomBar, event);
                                                                finish();
                                                            }
                                                        })
                                                        .show();
                                            }
                                        }
                                        else {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                                String message;
                                                if(jsonObject.has("result")) {
                                                    message = jsonObject.getJSONObject("result").getString("message");
                                                }
                                                else {

                                                    message = jsonObject.getString("message");
                                                }
                                                Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                                            } catch (JSONException | IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<ModelResultInsuranceInsert> call, @NonNull Throwable t) {
                                        // TODO: 12/10/2017 SET FAILURE SCENARIO
                                        progressBarHandler.hide();
                                        Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
