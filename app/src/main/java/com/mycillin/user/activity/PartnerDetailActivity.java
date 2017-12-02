package com.mycillin.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mycillin.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartnerDetailActivity extends AppCompatActivity {

    public static String KEY_FLAG_DOCTOR_NAME = "KEY_FLAG_DOCTOR_NAME";
    public static String KEY_FLAG_DOCTOR_TYPE = "KEY_FLAG_DOCTOR_TYPE";
    public static String KEY_FLAG_DOCTOR_PERMITT = "KEY_FLAG_DOCTOR_PERMITT";

    @BindView(R.id.partnerDetailActivity_tv_doctorName)
    TextView doctorName;
    @BindView(R.id.partnerDetailActivity_tv_doctorType)
    TextView doctorType;
    @BindView(R.id.partnerDetailActivity_tv_doctorPermitt)
    TextView doctorPermitt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_detail);
        ButterKnife.bind(this);

        doctorName.setText(getIntent().getStringExtra(KEY_FLAG_DOCTOR_NAME));
        doctorType.setText(getIntent().getStringExtra(KEY_FLAG_DOCTOR_TYPE));
        doctorPermitt.setText(getIntent().getStringExtra(KEY_FLAG_DOCTOR_PERMITT));
    }
}
