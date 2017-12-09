package com.mycillin.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.adapter.PrescriptionRecordAdapter;
import com.mycillin.user.list.PrescriptionRecordList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.prescriptionRecordList.ModelResultPrescriptionRecordList;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalRecordDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MEDICAL_RECORD_DETAIL = "EXTRA_MEDICAL_RECORD_DETAIL";

    public static final String KEY_PARAM_CREATED_DATE = "KEY_PARAM_CREATED_DATE";
    public static final String KEY_PARAM_PARTNER_ID = "KEY_PARAM_PARTNER_ID";
    public static final String KEY_PARAM_PARTNER_NAME = "KEY_PARAM_PARTNER_NAME";
    public static final String KEY_PARAM_RECORD_ID = "KEY_PARAM_RECORD_ID";
    public static final String KEY_PARAM_USER_ID = "KEY_PARAM_USER_ID";
    public static final String KEY_PARAM_SERVICE_TYPE_DESC = "KEY_PARAM_SERVICE_TYPE_DESC";
    public static final String KEY_PARAM_BODY_TEMPERATURE = "KEY_PARAM_BODY_TEMPERATURE";
    public static final String KEY_PARAM_BLOOD_SUGAR_LEVEL = "KEY_PARAM_BLOOD_SUGAR_LEVEL";
    public static final String KEY_PARAM_CHOLESTEROL_LEVEL = "KEY_PARAM_CHOLESTEROL_LEVEL";
    public static final String KEY_PARAM_BLOOD_PRESS_UPPER = "KEY_PARAM_BLOOD_PRESS_UPPER";
    public static final String KEY_PARAM_BLOOD_PRESS_LOWER = "KEY_PARAM_BLOOD_PRESS_LOWER";
    public static final String KEY_PARAM_PATIENT_CONDITION = "KEY_PARAM_PATIENT_CONDITION";
    public static final String KEY_PARAM_DIAGNOSE = "KEY_PARAM_DIAGNOSE";
    public static final String KEY_PARAM_PRESCRIPTION_STATUS = "KEY_PARAM_PRESCRIPTION_STATUS";
    public static final String KEY_PARAM_PRESCRIPTION_ID = "KEY_PARAM_PRESCRIPTION_ID";
    public static final String KEY_PARAM_PRESCRIPTION_TYPE_DESC = "KEY_PARAM_PRESCRIPTION_TYPE_DESC";
    public static final String KEY_PARAM_PRESCRIPTION_IMG = "KEY_PARAM_PRESCRIPTION_IMG";

    @BindView(R.id.medicalRecordDetailActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.medicalRecordDetailActivity_tv_date)
    TextView dateTxt;
    @BindView(R.id.medicalRecordDetailActivity_tv_doctor)
    TextView doctorTxt;
    @BindView(R.id.medicalRecordDetailActivity_tv_diagnose)
    TextView diagnoseTxt;
    @BindView(R.id.medicalRecordDetailActivity_tv_result1)
    TextView systoleTxt;
    @BindView(R.id.medicalRecordDetailActivity_tv_result2)
    TextView diastoleTxt;
    @BindView(R.id.medicalRecordDetailActivity_tv_result3)
    TextView bodyTemperatureTxt;
    @BindView(R.id.medicalRecordDetailActivity_tv_result4)
    TextView bloodSugarTxt;
    @BindView(R.id.medicalRecordDetailActivity_tv_result5)
    TextView cholesterolTxt;
    @BindView(R.id.medicalRecordDetailActivity_tv_result6)
    TextView conditionTxt;
    @BindView(R.id.medicalRecordDetailActivity_tv_action)
    TextView actionTxt;
    @BindView(R.id.medicalRecordDetailActivity_ll_diagnoseTitleContainer)
    LinearLayout diagnoseTitleContainer;
    @BindView(R.id.medicalRecordDetailActivity_iv_diagnoseIcon)
    ImageView diagnoseTitleIcon;
    @BindView(R.id.medicalRecordDetailActivity_ll_diagnoseContentContainer)
    LinearLayout diagnoseContentContainer;
    @BindView(R.id.medicalRecordDetailActivity_ll_resultTitleContainer)
    LinearLayout resultTitleContainer;
    @BindView(R.id.medicalRecordDetailActivity_iv_resultIcon)
    ImageView resultTitleIcon;
    @BindView(R.id.medicalRecordDetailActivity_ll_resultContentContainer)
    LinearLayout resultContentContainer;
    @BindView(R.id.medicalRecordDetailActivity_ll_actionTitleContainer)
    LinearLayout actionTitleContainer;
    @BindView(R.id.medicalRecordDetailActivity_iv_actionIcon)
    ImageView actionTitleIcon;
    @BindView(R.id.medicalRecordDetailActivity_ll_actionContentContainer)
    LinearLayout actionContentContainer;
    @BindView(R.id.medicalRecordDetailActivity_ll_prescriptionTitleContainer)
    LinearLayout prescriptionTitleContainer;
    @BindView(R.id.medicalRecordDetailActivity_iv_prescriptionIcon)
    ImageView prescriptionTitleIcon;
    @BindView(R.id.medicalRecordDetailActivity_ll_prescriptionContentContainer)
    LinearLayout prescriptionContentContainer;
    @BindView(R.id.medicalRecordDetailActivity_ll_clickPrescriptionImageContainer)
    LinearLayout clickPrescriptionImageContainer;

    @BindView(R.id.medicalRecordDetailActivity_rv_recyclerView)
    RecyclerView medicalRecordDetailRecyclerView;
    @BindView(R.id.medicalRecordDetailActivity_ll_messageContainer)
    LinearLayout messageContainer;
    @BindView(R.id.medicalRecordDetailActivity_tv_message)
    TextView message;

    private List<PrescriptionRecordList> prescriptionRecordLists = new ArrayList<>();
    private PrescriptionRecordAdapter prescriptionRecordAdapter;

    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.medicalRecordDetailActivity_title);

        progressBarHandler = new ProgressBarHandler(this);

        final HashMap<String, String> medicalRecordDetailData = (HashMap<String, String>) getIntent().getSerializableExtra(EXTRA_MEDICAL_RECORD_DETAIL);

        dateTxt.setText(medicalRecordDetailData.get(KEY_PARAM_CREATED_DATE));
        doctorTxt.setText(medicalRecordDetailData.get(KEY_PARAM_PARTNER_NAME));
        diagnoseTxt.setText(medicalRecordDetailData.get(KEY_PARAM_DIAGNOSE));
        systoleTxt.setText(medicalRecordDetailData.get(KEY_PARAM_BLOOD_PRESS_UPPER));
        diastoleTxt.setText(medicalRecordDetailData.get(KEY_PARAM_BLOOD_PRESS_LOWER));
        bodyTemperatureTxt.setText(medicalRecordDetailData.get(KEY_PARAM_BODY_TEMPERATURE));
        bloodSugarTxt.setText(medicalRecordDetailData.get(KEY_PARAM_BLOOD_SUGAR_LEVEL));
        cholesterolTxt.setText(medicalRecordDetailData.get(KEY_PARAM_CHOLESTEROL_LEVEL));
        conditionTxt.setText(medicalRecordDetailData.get(KEY_PARAM_PATIENT_CONDITION));
        //actionTxt.setText(medicalRecordDetailData.get(KEY_PARAM_PARTNER_ID));

        clickPrescriptionImageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!medicalRecordDetailData.get(KEY_PARAM_PRESCRIPTION_IMG).equals("")) {
                    Intent intent = new Intent(MedicalRecordDetailActivity.this, ViewImageActivity.class);
                    intent.putExtra(ViewImageActivity.EXTRA_IMAGE_URL, medicalRecordDetailData.get(KEY_PARAM_PRESCRIPTION_IMG));
                    startActivity(intent);
                }
                else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), R.string.medicalRecordDetailActivity_prescriptionImageNotAvailableMessage, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        accordionMenu();
        getPrescriptionList(medicalRecordDetailData.get(KEY_PARAM_PRESCRIPTION_ID));
    }

    private void accordionMenu() {
        diagnoseContentContainer.setVisibility(View.GONE);
        diagnoseTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
        resultContentContainer.setVisibility(View.GONE);
        resultTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
        actionContentContainer.setVisibility(View.GONE);
        actionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
        prescriptionContentContainer.setVisibility(View.GONE);
        clickPrescriptionImageContainer.setVisibility(View.GONE);
        prescriptionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);

        diagnoseTitleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diagnoseTitleIcon.setImageResource(android.R.color.transparent);
                if(diagnoseContentContainer.getVisibility() == View.VISIBLE) {
                    diagnoseContentContainer.setVisibility(View.GONE);
                    diagnoseTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                }
                else {
                    diagnoseContentContainer.setVisibility(View.VISIBLE);
                    diagnoseTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_up_black);
                }

                resultContentContainer.setVisibility(View.GONE);
                resultTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                actionContentContainer.setVisibility(View.GONE);
                actionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                prescriptionContentContainer.setVisibility(View.GONE);
                clickPrescriptionImageContainer.setVisibility(View.GONE);
                prescriptionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
            }
        });

        resultTitleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultTitleIcon.setImageResource(android.R.color.transparent);
                if(resultContentContainer.getVisibility() == View.VISIBLE) {
                    resultContentContainer.setVisibility(View.GONE);
                    resultTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                }
                else {
                    resultContentContainer.setVisibility(View.VISIBLE);
                    resultTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_up_black);
                }

                diagnoseContentContainer.setVisibility(View.GONE);
                diagnoseTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                actionContentContainer.setVisibility(View.GONE);
                actionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                prescriptionContentContainer.setVisibility(View.GONE);
                clickPrescriptionImageContainer.setVisibility(View.GONE);
                prescriptionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
            }
        });

        actionTitleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionTitleIcon.setImageResource(android.R.color.transparent);
                if(actionContentContainer.getVisibility() == View.VISIBLE) {
                    actionContentContainer.setVisibility(View.GONE);
                    actionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                }
                else {
                    actionContentContainer.setVisibility(View.VISIBLE);
                    actionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_up_black);
                }

                diagnoseContentContainer.setVisibility(View.GONE);
                diagnoseTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                resultContentContainer.setVisibility(View.GONE);
                resultTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                prescriptionContentContainer.setVisibility(View.GONE);
                clickPrescriptionImageContainer.setVisibility(View.GONE);
                prescriptionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
            }
        });

        prescriptionTitleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prescriptionTitleIcon.setImageResource(android.R.color.transparent);
                if(prescriptionContentContainer.getVisibility() == View.VISIBLE) {
                    prescriptionContentContainer.setVisibility(View.GONE);
                    clickPrescriptionImageContainer.setVisibility(View.GONE);
                    prescriptionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                }
                else {
                    prescriptionContentContainer.setVisibility(View.VISIBLE);
                    clickPrescriptionImageContainer.setVisibility(View.VISIBLE);
                    prescriptionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_up_black);
                }

                diagnoseContentContainer.setVisibility(View.GONE);
                diagnoseTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                resultContentContainer.setVisibility(View.GONE);
                resultTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
                actionContentContainer.setVisibility(View.GONE);
                actionTitleIcon.setBackgroundResource(R.drawable.ic_arrow_drop_down_black);
            }
        });
    }

    private void getPrescriptionList(String prescriptionId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("prescription_no", prescriptionId);

        myCillinAPI.getPrescriptionRecordList(token, params).enqueue(new Callback<ModelResultPrescriptionRecordList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultPrescriptionRecordList> call, @NonNull Response<ModelResultPrescriptionRecordList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultPrescriptionRecordList modelResultPrescriptionRecordList = response.body();

                    assert modelResultPrescriptionRecordList != null;
                    if(modelResultPrescriptionRecordList.getResult().isStatus()) {
                        int size = modelResultPrescriptionRecordList.getResult().getData().size();
                        if(size > 0) {
                            messageContainer.setVisibility(View.GONE);
                            medicalRecordDetailRecyclerView.setVisibility(View.VISIBLE);

                            medicalRecordDetailRecyclerView.setLayoutManager(new LinearLayoutManager(MedicalRecordDetailActivity.this));
                            medicalRecordDetailRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            prescriptionRecordLists.clear();
                            for(int i = 0; i < size; i++) {
                                String createdDate = modelResultPrescriptionRecordList.getResult().getData().get(i).getCreatedDate();
                                String prescriptionNo = modelResultPrescriptionRecordList.getResult().getData().get(i).getPrescriptionNo();
                                String prescriptionName = modelResultPrescriptionRecordList.getResult().getData().get(i).getPrescriptionName();
                                String prescriptionQty = modelResultPrescriptionRecordList.getResult().getData().get(i).getPrescriptionQty();
                                String unitDesc = modelResultPrescriptionRecordList.getResult().getData().get(i).getPrescriptionUnitDesc();
                                String dosageDesc = modelResultPrescriptionRecordList.getResult().getData().get(i).getDosageDesc();

                                prescriptionRecordLists.add(new PrescriptionRecordList(createdDate,
                                        prescriptionNo, prescriptionName, prescriptionQty, unitDesc, dosageDesc));
                            }

                            prescriptionRecordAdapter = new PrescriptionRecordAdapter(prescriptionRecordLists);
                            medicalRecordDetailRecyclerView.setAdapter(prescriptionRecordAdapter);
                            prescriptionRecordAdapter.notifyDataSetChanged();
                        }
                        else {
                            messageContainer.setVisibility(View.VISIBLE);
                            message.setText(R.string.medicalRecordDetailActivity_noData);
                            medicalRecordDetailRecyclerView.setVisibility(View.GONE);
                        }
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
            public void onFailure(@NonNull Call<ModelResultPrescriptionRecordList> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
