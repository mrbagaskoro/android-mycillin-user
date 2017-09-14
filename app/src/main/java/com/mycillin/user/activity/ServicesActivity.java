package com.mycillin.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mycillin.user.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class ServicesActivity extends AppCompatActivity {

    @BindView(R.id.servicesActivity_et_dropdown)
    TextView tvDropdwon;

    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        ButterKnife.bind(this);

        final SpinnerDialog spinnerDialog = new SpinnerDialog(ServicesActivity.this, items, getString(R.string.servicesActivity_dropdownTitle), R.style.DialogAnimations_SmileWindow);

        items.add("Me");
        items.add("Wife");
        items.add("Son");
        items.add("Daughter");

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                tvDropdwon.setText(s);
            }
        });

        tvDropdwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });
    }
}
