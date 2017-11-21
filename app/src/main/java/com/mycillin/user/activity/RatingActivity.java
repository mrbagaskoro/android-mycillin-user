package com.mycillin.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.mycillin.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatingActivity extends AppCompatActivity {

    @BindView(R.id.ratingActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.ratingActivity_tv_doctorName)
    TextView partnerNameTxt;
    @BindView(R.id.ratingActivity_tv_bookDate)
    TextView bookDateTxt;

    @BindView(R.id.ratingActivity_et_comments)
    EditText additionalComments;
    @BindView(R.id.ratingActivity_tv_commentsCounter)
    TextView ratingCounter;

    public static final String EXTRA_PARAM_CREATED_DATE = "EXTRA_PARAM_CREATED_DATE";
    public static final String EXTRA_PARAM_BOOKING_ID = "EXTRA_PARAM_BOOKING_ID";
    public static final String EXTRA_PARAM_PARTNER_ID = "EXTRA_PARAM_PARTNER_ID";
    public static final String EXTRA_PARAM_PARTNER_NAME = "EXTRA_PARAM_PARTNER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.ratingActivity_title);

        String createdDate = getIntent().getStringExtra(EXTRA_PARAM_CREATED_DATE);
        String bookingId = getIntent().getStringExtra(EXTRA_PARAM_BOOKING_ID);
        String partnerId = getIntent().getStringExtra(EXTRA_PARAM_PARTNER_ID);
        String partnerName = getIntent().getStringExtra(EXTRA_PARAM_PARTNER_NAME);

        partnerNameTxt.setText(partnerName);
        bookDateTxt.setText(createdDate);

        additionalComments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ratingCounter.setText(additionalComments.getText().length() + " / 250");
            }
        });
    }
}
