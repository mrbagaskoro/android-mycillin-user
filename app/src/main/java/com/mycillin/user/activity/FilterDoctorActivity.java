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
import android.widget.EditText;
import android.widget.RadioGroup;

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

    @BindView(R.id.filterDoctorActivity_toolbar)
    Toolbar toolbar;

    private ProgressBarHandler progressBarHandler;

    private ArrayList<String> items = new ArrayList<>();
    private HashMap<Integer, String> partnerTypeIdItemsTemp = new HashMap<>();
    private String selectedPartnerTypeId = "";
    private HashMap<Integer, String> partnerSpecializationItemsTemp = new HashMap<>();
    private String selectedPartnerSpecializationId = "";

    public static final int REQUEST_CODE_FILTER = 2001;
    public static final String EXTRA_PARTNER_TYPE_ID = "EXTRA_PARTNER_TYPE_ID";
    public static final String EXTRA_PARTNER_SPECIALIZATION_ID = "EXTRA_PARTNER_SPECIALIZATION_ID";
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

        final SpinnerDialog partnerTypeSpinnerDialog = new SpinnerDialog(FilterDoctorActivity.this, items, "Select a Partner Type", R.style.DialogAnimations_SmileWindow);
        partnerTypeSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                partnerTypeDropdown.setText(s);
                for(int j = 0; j < partnerTypeIdItemsTemp.size(); j++) {
                    if(partnerTypeIdItemsTemp.get(j).split(" - ")[1].equals(s)) {
                        selectedPartnerTypeId = partnerTypeIdItemsTemp.get(j).split(" - ")[0];
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

        genderRadioGroup.check(R.id.filterDoctorActivity_rb_genderAllRb);
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
            String selectedGender;
            String selectedBPJSStatus = "1";

            int selectedGenderId = genderRadioGroup.indexOfChild(genderRadioGroup.findViewById(genderRadioGroup.getCheckedRadioButtonId()));
            if(selectedGenderId == 1) {
                selectedGender = "L";
            }
            else if(selectedGenderId == 2) {
                selectedGender = "P";
            }
            else {
                selectedGender = "";
            }

            Intent intent = new Intent();
            intent.putExtra(EXTRA_PARTNER_TYPE_ID, selectedPartnerTypeId);
            intent.putExtra(EXTRA_PARTNER_SPECIALIZATION_ID, selectedPartnerSpecializationId);
            intent.putExtra(EXTRA_PARTNER_GENDER, selectedGender);
            intent.putExtra(EXTRA_PARTNER_BPJS_STATUS, selectedBPJSStatus);
            setResult(RESULT_OK, intent);
            finish();
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
