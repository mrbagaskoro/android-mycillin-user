package com.mycillin.user.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.ratingInsert.ModelResultRatingInsert;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {

    @BindView(R.id.ratingActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.ratingActivity_tv_doctorName)
    TextView partnerNameTxt;
    @BindView(R.id.ratingActivity_tv_bookDate)
    TextView bookDateTxt;
    @BindView(R.id.ratingActivity_rb_ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.ratingActivity_tv_ratingDesc)
    TextView ratingDescTxt;
    @BindView(R.id.ratingActivity_et_comments)
    EditText additionalComments;
    @BindView(R.id.ratingActivity_tv_commentsCounter)
    TextView ratingCounter;
    @BindView(R.id.ratingActivity_bt_submitBtn)
    Button submitBtn;

    private ProgressBarHandler progressBarHandler;

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

        progressBarHandler = new ProgressBarHandler(this);

        String createdDate = getIntent().getStringExtra(EXTRA_PARAM_CREATED_DATE);
        String partnerName = getIntent().getStringExtra(EXTRA_PARAM_PARTNER_NAME);

        partnerNameTxt.setText(partnerName);
        bookDateTxt.setText(createdDate);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v == 0.0) {
                    ratingDescTxt.setVisibility(View.INVISIBLE);
                }
                if(v == 1.0) {
                    ratingDescTxt.setVisibility(View.VISIBLE);
                    ratingDescTxt.setText(R.string.ratingActivity_ratingDesc1);
                }
                if(v == 2.0) {
                    ratingDescTxt.setVisibility(View.VISIBLE);
                    ratingDescTxt.setText(R.string.ratingActivity_ratingDesc2);
                }
                if(v == 3.0) {
                    ratingDescTxt.setVisibility(View.VISIBLE);
                    ratingDescTxt.setText(R.string.ratingActivity_ratingDesc3);
                }
                if(v == 4.0) {
                    ratingDescTxt.setVisibility(View.VISIBLE);
                    ratingDescTxt.setText(R.string.ratingActivity_ratingDesc4);
                }
                if(v == 5.0) {
                    ratingDescTxt.setVisibility(View.VISIBLE);
                    ratingDescTxt.setText(R.string.ratingActivity_ratingDesc5);
                }
            }
        });

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

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookingId = getIntent().getStringExtra(EXTRA_PARAM_BOOKING_ID);
                String ratingValue = Math.round(ratingBar.getRating()) + "";
                String additionalComment = additionalComments.getText().toString();
                if(ratingValue.equals("0")) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), R.string.ratingActivity_emptyRatingMessage, Snackbar.LENGTH_SHORT).show();
                }
                else {
                    rateTransaction(bookingId, ratingValue, additionalComment);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Snackbar.make(getWindow().getDecorView().getRootView(), getString(R.string.ratingActivity_onBackPressedMessage), Snackbar.LENGTH_SHORT).show();
    }

    private void rateTransaction(String bookingId, String ratingValue, String additionalComment) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("booking_id", bookingId);
        params.put("service_rating", ratingValue);
        params.put("user_comment", additionalComment);

        myCillinAPI.rateTransaction(token, params).enqueue(new Callback<ModelResultRatingInsert>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultRatingInsert> call, @NonNull Response<ModelResultRatingInsert> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultRatingInsert modelResultRatingInsert = response.body();

                    assert modelResultRatingInsert != null;
                    if(modelResultRatingInsert.getResult().isStatus()) {
                        String message = modelResultRatingInsert.getResult().getMessage();
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
            public void onFailure(@NonNull Call<ModelResultRatingInsert> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
