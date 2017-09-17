package com.mycillin.user.activity.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mycillin.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InsuranceActivity extends AppCompatActivity {

    @BindView(R.id.insuranceActivity_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.insuranceActivity_title);
    }
}
