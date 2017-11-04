package com.mycillin.user.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycillin.user.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicalRecordDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MEDICAL_RECORD_DETAIL = "EXTRA_MEDICAL_RECORD_DETAIL";
    public static final String KEY_PARAM_CREATED_BY = "KEY_PARAM_CREATED_BY";
    public static final String KEY_PARAM_CREATED_DATE = "KEY_PARAM_CREATED_DATE";
    public static final String KEY_PARAM_UPDATED_BY = "KEY_PARAM_UPDATED_BY";
    public static final String KEY_PARAM_UPDATED_DATE = "KEY_PARAM_UPDATED_DATE";
    public static final String KEY_PARAM_BOOKING_ID = "KEY_PARAM_BOOKING_ID";
    public static final String KEY_PARAM_RECORD_ID = "KEY_PARAM_RECORD_ID";
    public static final String KEY_PARAM_USER_ID = "KEY_PARAM_USER_ID";
    public static final String KEY_PARAM_RELATION_ID = "KEY_PARAM_RELATION_ID";
    public static final String KEY_PARAM_PARTNET_ID = "KEY_PARAM_PARTNET_ID";
    public static final String KEY_PARAM_SERVICE_TYPE_ID = "KEY_PARAM_SERVICE_TYPE_ID";
    public static final String KEY_PARAM_BODY_TEMPERATURE = "KEY_PARAM_BODY_TEMPERATURE";
    public static final String KEY_PARAM_BLOOD_SUGAR_LEVEL = "KEY_PARAM_BLOOD_SUGAR_LEVEL";
    public static final String KEY_PARAM_CHOLESTEROL_LEVEL = "KEY_PARAM_CHOLESTEROL_LEVEL";
    public static final String KEY_PARAM_BLOOD_PRESS_UPPER = "KEY_PARAM_BLOOD_PRESS_UPPER";
    public static final String KEY_PARAM_BLOOD_PRESS_LOWER = "KEY_PARAM_BLOOD_PRESS_LOWER";
    public static final String KEY_PARAM_PATIENT_CONDITION = "KEY_PARAM_PATIENT_CONDITION";
    public static final String KEY_PARAM_DIAGNOSE = "KEY_PARAM_DIAGNOSE";
    public static final String KEY_PARAM_PRESCRIPTION_STATUS = "KEY_PARAM_PRESCRIPTION_STATUS";
    public static final String KEY_PARAM_PRESCRIPTION_ID = "KEY_PARAM_PRESCRIPTION_ID";
    public static final String KEY_PARAM_PRESCRIPTION_TYPE_ID = "KEY_PARAM_PRESCRIPTION_TYPE_ID";
    public static final String KEY_PARAM_PRESCRIPTION_PHOTO = "KEY_PARAM_PRESCRIPTION_PHOTO";

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
    TextView actionTxt;/*
    @BindView(R.id.medicalRecordDetailActivity_tv_prescriptionInfo)
    TextView prescriptionInfo;
    @BindView(R.id.medicalRecordDetailActivity_v_prescriptionView)
    View prescriptionView;
    @BindView(R.id.medicalRecordDetailActivity_tv_prescription)
    TextView prescription;*/

    @BindView(R.id.medicalRecordDetailActivity_ll_diagnoseTitleContainer)
    LinearLayout diagnoseTitleContainer;
    @BindView(R.id.medicalRecordDetailActivity_ll_diagnoseContentContainer)
    LinearLayout diagnoseContentContainer;
    @BindView(R.id.medicalRecordDetailActivity_ll_resultTitleContainer)
    LinearLayout resultTitleContainer;
    @BindView(R.id.medicalRecordDetailActivity_ll_resultContentContainer)
    LinearLayout resultContentContainer;
    @BindView(R.id.medicalRecordDetailActivity_ll_actionTitleContainer)
    LinearLayout actionTitleContainer;
    @BindView(R.id.medicalRecordDetailActivity_ll_actionContentContainer)
    LinearLayout actionContentContainer;
    @BindView(R.id.medicalRecordDetailActivity_ll_prescriptionTitleContainer)
    LinearLayout prescriptionTitleContainer;
    @BindView(R.id.medicalRecordDetailActivity_ll_prescriptionContentContainer)
    LinearLayout prescriptionContentContainer;
    /*@BindView(R.id.medicalRecordDetailActivity_ll_prescriptionContent)
    LinearLayout prescriptionContent;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.medicalRecordDetailActivity_title);

        HashMap<String, String> medicalRecordDetailData = (HashMap<String, String>) getIntent().getSerializableExtra(EXTRA_MEDICAL_RECORD_DETAIL);

        dateTxt.setText(medicalRecordDetailData.get(KEY_PARAM_CREATED_DATE));
        doctorTxt.setText(medicalRecordDetailData.get(KEY_PARAM_PARTNET_ID));
        diagnoseTxt.setText(medicalRecordDetailData.get(KEY_PARAM_DIAGNOSE));
        systoleTxt.setText(medicalRecordDetailData.get(KEY_PARAM_BLOOD_PRESS_UPPER));
        diastoleTxt.setText(medicalRecordDetailData.get(KEY_PARAM_BLOOD_PRESS_LOWER));
        bodyTemperatureTxt.setText(medicalRecordDetailData.get(KEY_PARAM_BODY_TEMPERATURE));
        bloodSugarTxt.setText(medicalRecordDetailData.get(KEY_PARAM_BLOOD_SUGAR_LEVEL));
        cholesterolTxt.setText(medicalRecordDetailData.get(KEY_PARAM_CHOLESTEROL_LEVEL));
        conditionTxt.setText(medicalRecordDetailData.get(KEY_PARAM_PATIENT_CONDITION));
        //actionTxt.setText(medicalRecordDetailData.get(KEY_PARAM_PARTNET_ID));

        LinearLayout linearLayout = new LinearLayout(this);
        for(int i = 0; i < 5; i++) {

            LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            );

            LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(
                    1,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );

            LinearLayout.LayoutParams text2LayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );

            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1.0f
            );

            TextView text = new TextView(this);
            text.setLayoutParams(textLayoutParams);
            text.setPadding(10, 10, 10, 10);
            text.setGravity(Gravity.END);
            text.setTextColor(getResources().getColor(R.color.primaryText));
            text.setTypeface(Typeface.DEFAULT_BOLD);
            text.setText("The Value of i is :" + i);

            View view = new View(this);
            view.setLayoutParams(viewLayoutParams);
            view.setBackgroundColor(getResources().getColor(R.color.bgColor));

            TextView text2 = new TextView(this);
            text2.setLayoutParams(text2LayoutParams);
            text2.setPadding(10, 10, 10, 10);
            text2.setText(i + "");

            linearLayout.setLayoutParams(linearLayoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            linearLayout.addView(text);
            linearLayout.addView(view);
            linearLayout.addView(text2);

            prescriptionContentContainer.removeAllViews();
            prescriptionContentContainer.setOrientation(LinearLayout.VERTICAL);
            prescriptionContentContainer.addView(linearLayout);

            /*((ViewGroup)prescriptionContent.getParent()).removeView(prescriptionContent);
            prescriptionContentContainer.addView(prescriptionContent);*/
        }

        accordionMenu();
    }

    private void accordionMenu() {
        diagnoseTitleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(diagnoseContentContainer.getVisibility() == View.VISIBLE) {
                    diagnoseContentContainer.setVisibility(View.GONE);
                }
                else {
                    diagnoseContentContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        resultTitleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resultContentContainer.getVisibility() == View.VISIBLE) {
                    resultContentContainer.setVisibility(View.GONE);
                }
                else {
                    resultContentContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        actionTitleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actionContentContainer.getVisibility() == View.VISIBLE) {
                    actionContentContainer.setVisibility(View.GONE);
                }
                else {
                    actionContentContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        prescriptionTitleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prescriptionContentContainer.getVisibility() == View.VISIBLE) {
                    prescriptionContentContainer.setVisibility(View.GONE);
                }
                else {
                    prescriptionContentContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
