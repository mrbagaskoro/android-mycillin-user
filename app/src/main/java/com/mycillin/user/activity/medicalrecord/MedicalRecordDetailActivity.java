package com.mycillin.user.activity.medicalrecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desnyki.library.infinitymenu.ChildScrollView;
import com.desnyki.library.infinitymenu.RootScrollView;
import com.mycillin.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicalRecordDetailActivity extends AppCompatActivity {

    public static String INTENT_KEY_DATE = "INTENT_KEY_DATE";
    public static String INTENT_KEY_DOCTOR = "INTENT_KEY_DOCTOR";

    @BindView(R.id.medicalRecordDetailActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.medicalRecordDetailActivity_tv_date)
    TextView dateTxt;
    @BindView(R.id.medicalRecordDetailActivity_tv_doctor)
    TextView doctorTxt;

    @BindView(R.id.medicalRecordDetailActivity_rl_diagnose)
    RelativeLayout diagnoseContainer;
    @BindView(R.id.medicalRecordDetailActivity_rl_result)
    RelativeLayout resultContainer;
    @BindView(R.id.medicalRecordDetailActivity_rl_action)
    RelativeLayout actionContainer;
    @BindView(R.id.medicalRecordDetailActivity_rl_prescription)
    RelativeLayout prescriptionContainer;
    @BindView(R.id.medicalRecordDetailActivity_childScroolView)
    ChildScrollView childScrollView;
    @BindView(R.id.medicalRecordDetailActivity_rootScrollView)
    RootScrollView rootScrollView;

    final LinearLayout[] childContainer = new LinearLayout[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.medicalRecordDetailActivity_title);

        dateTxt.setText(getIntent().getStringExtra(INTENT_KEY_DATE));
        doctorTxt.setText(getIntent().getStringExtra(INTENT_KEY_DOCTOR));

        accordionMenu();
    }

    private void accordionMenu() {
        childScrollView.setBackgroundScrollView(rootScrollView);
        childScrollView.setCloseDistance(50);

        childContainer[0]=(LinearLayout) getLayoutInflater().inflate(R.layout.medical_record_diagnose, null);
        diagnoseContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childScrollView.addView(childContainer[0], 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                childScrollView.openWithAnim(diagnoseContainer, false, true);

                // HOW TO ACCESS LAYOUT
                /*TextView xxx = childContainer[0].findViewById(R.id.xxx);
                xxx.setText("WKWKWK");*/
            }
        });

        childContainer[1] = (LinearLayout) getLayoutInflater().inflate(R.layout.medical_record_result, null);
        resultContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childScrollView.addView(childContainer[1], 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                childScrollView.openWithAnim(resultContainer, false, true);
            }
        });

        childContainer[2] = (LinearLayout) getLayoutInflater().inflate(R.layout.medical_record_action, null);
        actionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childScrollView.addView(childContainer[2], 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                childScrollView.openWithAnim(actionContainer, false, true);
            }
        });

        childContainer[3] = (LinearLayout) getLayoutInflater().inflate(R.layout.medical_record_prescription, null);
        prescriptionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childScrollView.addView(childContainer[3], 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                childScrollView.openWithAnim(prescriptionContainer, false, true);
            }
        });
    }
}
