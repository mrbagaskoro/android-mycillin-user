package com.mycillin.user.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.mycillin.user.R;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.partnerTypeList.ModelResultPartnerTypeList;
import com.mycillin.user.rest.specializationList.ModelResultSpecializationList;
import com.mycillin.user.util.ProgressBarHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterDoctorActivity extends AppCompatActivity {

    @BindView(R.id.filterDoctorActivity_et_partnerType)
    EditText partnerTypeDropdown;
    @BindView(R.id.filterDoctorActivity_et_partnerSpecialization)
    EditText partnerSpecializationDropdown;
    @BindView(R.id.filterDoctorActivity_rg_genderRg)
    RadioGroup genderRadioGroup;
    @BindView(R.id.filterDoctorActivity_sb_BPJSSwitch)
    Switch BPJSSwitch;
    @BindView(R.id.filterDoctorActivity_bt_applyButton)
    Button applyBtn;

    @BindView(R.id.filterDoctorActivity_toolbar)
    Toolbar toolbar;

    private ProgressBarHandler progressBarHandler;

    private ArrayList<String> items = new ArrayList<>();
    private HashMap<Integer, String> partnerTypeIdItemsTemp = new HashMap<>();
    private String selectedPartnerTypeId = "";
    private String selectedPartnerTypeDesc = "";
    private HashMap<Integer, String> partnerSpecializationItemsTemp = new HashMap<>();
    private String selectedPartnerSpecializationId = "";
    private String selectedPartnerSpecializationDesc = "";
    private String selectedGender = "";
    private String selectedBPJS = "0";

    public static final int REQUEST_CODE_FILTER = 2001;
    public static final String EXTRA_PARTNER_TYPE_ID = "EXTRA_PARTNER_TYPE_ID";
    public static final String EXTRA_PARTNER_TYPE_DESC = "EXTRA_PARTNER_TYPE_DESC";
    public static final String EXTRA_PARTNER_SPECIALIZATION_ID = "EXTRA_PARTNER_SPECIALIZATION_ID";
    public static final String EXTRA_PARTNER_SPECIALIZATION_DESC = "EXTRA_PARTNER_SPECIALIZATION_DESC";
    public static final String EXTRA_PARTNER_GENDER = "EXTRA_PARTNER_GENDER";
    public static final String EXTRA_PARTNER_BPJS_STATUS = "EXTRA_PARTNER_BPJS_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_doctor);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.serviceBookDoctorActivity_filterBtn);

        progressBarHandler = new ProgressBarHandler(this);

        selectedPartnerTypeId = getIntent().getStringExtra(EXTRA_PARTNER_TYPE_ID);
        selectedPartnerTypeDesc = getIntent().getStringExtra(EXTRA_PARTNER_TYPE_DESC);
        partnerTypeDropdown.setText(selectedPartnerTypeDesc);
        selectedPartnerSpecializationId = getIntent().getStringExtra(EXTRA_PARTNER_SPECIALIZATION_ID);
        selectedPartnerSpecializationDesc = getIntent().getStringExtra(EXTRA_PARTNER_SPECIALIZATION_DESC);
        partnerSpecializationDropdown.setText(selectedPartnerSpecializationDesc);
        selectedGender = getIntent().getStringExtra(EXTRA_PARTNER_GENDER);
        if(selectedGender.equals("")) {
            genderRadioGroup.check(R.id.filterDoctorActivity_rb_genderAllRb);
        }
        else if(selectedGender.equals("L")) {
            genderRadioGroup.check(R.id.filterDoctorActivity_rb_genderMaleRb);
        }
        else if(selectedGender.equals("P")) {
            genderRadioGroup.check(R.id.filterDoctorActivity_rb_genderFemaleRb);
        }
        selectedBPJS = getIntent().getStringExtra(EXTRA_PARTNER_BPJS_STATUS);
        if(selectedBPJS.equals("0")) {
            BPJSSwitch.setChecked(false);
        }
        else {
            BPJSSwitch.setChecked(true);
        }

        final SpinnerDialog partnerTypeSpinnerDialog = new SpinnerDialog(FilterDoctorActivity.this, items, "Select a Partner Type", R.style.DialogAnimations_SmileWindow);
        partnerTypeSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                partnerTypeDropdown.setText(s);
                for(int j = 0; j < partnerTypeIdItemsTemp.size(); j++) {
                    if(partnerTypeIdItemsTemp.get(j).split(" - ")[1].equals(s)) {
                        selectedPartnerTypeId = partnerTypeIdItemsTemp.get(j).split(" - ")[0];
                        selectedPartnerTypeDesc = partnerTypeIdItemsTemp.get(j).split(" - ")[1];
                    }
                }
            }
        });

        partnerTypeDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPartnerType(partnerTypeSpinnerDialog);
            }
        });

        final SpinnerDialog specializationSpinnerDialog = new SpinnerDialog(FilterDoctorActivity.this, items, "Select a Specialization", R.style.DialogAnimations_SmileWindow);
        specializationSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                partnerSpecializationDropdown.setText(s);
                for(int j = 0; j < partnerSpecializationItemsTemp.size(); j++) {
                    if(partnerSpecializationItemsTemp.get(j).split(" - ")[1].equals(s)) {
                        selectedPartnerSpecializationId = partnerSpecializationItemsTemp.get(j).split(" - ")[0];
                        selectedPartnerSpecializationDesc = partnerSpecializationItemsTemp.get(j).split(" - ")[1];
                    }
                }
            }
        });

        partnerSpecializationDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedPartnerTypeId.equals("")) {
                    getSpecialization(specializationSpinnerDialog, selectedPartnerTypeId);
                }
                else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), R.string.filterDoctorActivity_partnerSpecializationDropdownMessage, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.filterDoctorActivity_rb_genderAllRb) {
                    selectedGender = "";
                }
                else if(i == R.id.filterDoctorActivity_rb_genderMaleRb) {
                    selectedGender = "L";
                }
                else if(i == R.id.filterDoctorActivity_rb_genderFemaleRb) {
                    selectedGender = "P";
                }
            }
        });

        BPJSSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    selectedBPJS = "1";
                }
                else {
                    selectedBPJS = "0";
                }
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_PARTNER_TYPE_ID, selectedPartnerTypeId);
                intent.putExtra(EXTRA_PARTNER_TYPE_DESC, selectedPartnerTypeDesc);
                intent.putExtra(EXTRA_PARTNER_SPECIALIZATION_ID, selectedPartnerSpecializationId);
                intent.putExtra(EXTRA_PARTNER_SPECIALIZATION_DESC, selectedPartnerSpecializationDesc);
                intent.putExtra(EXTRA_PARTNER_GENDER, selectedGender);
                intent.putExtra(EXTRA_PARTNER_BPJS_STATUS, selectedBPJS);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            selectedPartnerTypeId = "";
            selectedPartnerTypeDesc = "";
            partnerTypeDropdown.setText("");

            selectedPartnerSpecializationId = "";
            selectedPartnerSpecializationDesc = "";
            partnerSpecializationDropdown.setText("");

            selectedGender = "";
            genderRadioGroup.check(R.id.filterDoctorActivity_rb_genderAllRb);

            selectedBPJS = "0";
            BPJSSwitch.setChecked(false);
        }

        return super.onOptionsItemSelected(item);
    }

    private void getPartnerType(final SpinnerDialog spinnerDialog) {
        progressBarHandler.show();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();
        myCillinAPI.getPartnerTypeList().enqueue(new Callback<ModelResultPartnerTypeList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultPartnerTypeList> call, @NonNull Response<ModelResultPartnerTypeList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultPartnerTypeList modelResultPartnerTypeList = response.body();

                    assert modelResultPartnerTypeList != null;
                    if(modelResultPartnerTypeList.getResult().isStatus()) {
                        items.clear();
                        partnerTypeIdItemsTemp.clear();
                        int size = modelResultPartnerTypeList.getResult().getData().size();
                        for(int i = 0; i < size; i++) {
                            String partnerTypeId = modelResultPartnerTypeList.getResult().getData().get(i).getPartnerTypeId();
                            String partnerTypeDesc = modelResultPartnerTypeList.getResult().getData().get(i).getPartnerTypeDesc();

                            items.add(partnerTypeDesc);
                            partnerTypeIdItemsTemp.put(i, partnerTypeId + " - " + partnerTypeDesc);
                        }
                        spinnerDialog.showSpinerDialog();
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
            public void onFailure(@NonNull Call<ModelResultPartnerTypeList> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getSpecialization(final SpinnerDialog spinnerDialog, String partnerTypeId) {
        progressBarHandler.show();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("partner_type_id", partnerTypeId);

        myCillinAPI.getSpecializationList(params).enqueue(new Callback<ModelResultSpecializationList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultSpecializationList> call, @NonNull Response<ModelResultSpecializationList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultSpecializationList modelResultSpecializationList = response.body();

                    assert modelResultSpecializationList != null;
                    if(modelResultSpecializationList.getResult().isStatus()) {
                        items.clear();
                        partnerSpecializationItemsTemp.clear();
                        int size = modelResultSpecializationList.getResult().getData().size();
                        for(int i = 0; i < size; i++) {
                            String specializationId = modelResultSpecializationList.getResult().getData().get(i).getSpecializationId();
                            String specializationDesc = modelResultSpecializationList.getResult().getData().get(i).getSpecializationDesc();

                            items.add(specializationDesc);
                            partnerSpecializationItemsTemp.put(i, specializationId + " - " + specializationDesc);
                        }
                        spinnerDialog.showSpinerDialog();
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
            public void onFailure(@NonNull Call<ModelResultSpecializationList> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
