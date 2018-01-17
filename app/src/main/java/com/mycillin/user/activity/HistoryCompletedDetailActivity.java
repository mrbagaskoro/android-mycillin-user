package com.mycillin.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycillin.user.R;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.emailMedicalRecord.ModelResultEmailMedicalRecord;
import com.mycillin.user.rest.emailReceipt.ModelResultEmailReceipt;
import com.mycillin.user.util.CurrencyTextWatcherTextView;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryCompletedDetailActivity extends AppCompatActivity {

    @BindView(R.id.historyCompletedDetailActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.historyCompletedDetailActivity_iv_userAvatar)
    CircleImageView doctorPic;
    @BindView(R.id.historyCompletedDetailActivity_tv_doctorName)
    TextView doctorName;
    @BindView(R.id.historyCompletedDetailActivity_tv_doctorType)
    TextView doctorType;
    @BindView(R.id.historyCompletedDetailActivity_rb_doctorRating)
    RatingBar doctorRating;
    @BindView(R.id.historyCompletedDetailActivity_tv_bookingId)
    TextView bookingId;
    @BindView(R.id.historyCompletedDetailActivity_tv_date)
    TextView bookDate;
    @BindView(R.id.historyCompletedDetailActivity_tv_serviceType)
    TextView bookType;
    @BindView(R.id.historyCompletedDetailActivity_tv_payment)
    TextView paymentType;
    @BindView(R.id.historyCompletedDetailActivity_tv_price)
    TextView priceAmount;
    @BindView(R.id.historyCompletedDetailActivity_tv_diagnoseInfo)
    TextView diagnoseInfo;
    @BindView(R.id.historyCompletedDetailActivity_tv_actionInfo)
    TextView actionInfo;
    @BindView(R.id.historyCompletedDetailActivity_bt_emailReceiptBtn)
    Button emailReceiptBtn;
    @BindView(R.id.historyCompletedDetailActivity_bt_emailDiagnosisBtn)
    Button emailDiagnosisBtn;

    private ProgressBarHandler progressBarHandler;

    public static String KEY_FLAG_ORDER_DATE = "KEY_FLAG_ORDER_DATE";
    public static String KEY_FLAG_ORDER_TIME = "KEY_FLAG_ORDER_TIME";
    public static String KEY_FLAG_BOOKING_ID = "KEY_FLAG_BOOKING_ID";
    public static String KEY_FLAG_SERVICE_TYPE_ID = "KEY_FLAG_SERVICE_TYPE_ID";
    public static String KEY_FLAG_SERVICE_TYPE_DESC = "KEY_FLAG_SERVICE_TYPE_DESC";
    public static String KEY_FLAG_PARTNER_ID = "KEY_FLAG_PARTNER_ID";
    public static String KEY_FLAG_PARTNER_NAME = "KEY_FLAG_PARTNER_NAME";
    public static String KEY_FLAG_PARTNER_TYPE_ID = "KEY_FLAG_PARTNER_TYPE_ID";
    public static String KEY_FLAG_PARTNER_TYPE_DESC = "KEY_FLAG_PARTNER_TYPE_DESC";
    public static String KEY_FLAG_PARTNER_SPECIALIZATION_ID = "KEY_FLAG_PARTNER_SPECIALIZATION_ID";
    public static String KEY_FLAG_PARTNER_SPECIALIZATION_DESC = "KEY_FLAG_PARTNER_SPECIALIZATION_DESC";
    public static String KEY_FLAG_PARTNER_PIC = "KEY_FLAG_PARTNER_PIC";
    public static String KEY_FLAG_MOBILE_NO = "KEY_FLAG_MOBILE_NO";
    public static String KEY_FLAG_RATING = "KEY_FLAG_RATING";
    public static String KEY_FLAG_PAYMENT_ID = "KEY_FLAG_PAYMENT_ID";
    public static String KEY_FLAG_PAYMENT_DESC = "KEY_FLAG_PAYMENT_DESC";
    public static String KEY_FLAG_PROMO_CODE = "KEY_FLAG_PROMO_CODE";
    public static String KEY_FLAG_PRICE_AMOUNT = "KEY_FLAG_PRICE_AMOUNT";
    public static String KEY_FLAG_CANCEL_BY = "KEY_FLAG_CANCEL_BY";
    public static String KEY_FLAG_CANCEL_REASON_BY_USER = "KEY_FLAG_CANCEL_REASON_BY_USER";
    public static String KEY_FLAG_CANCEL_REASON_BY_PARTNER = "KEY_FLAG_CANCEL_REASON_BY_PARTNER";
    public static String KEY_FLAG_DIAGNOSE = "KEY_FLAG_DIAGNOSE";
    public static String KEY_FLAG_ACTION_TYPE_DESC = "KEY_FLAG_ACTION_TYPE_DESC";
    public static String KEY_FLAG_PRESCRIPTION_TYPE_ID = "KEY_FLAG_PRESCRIPTION_TYPE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_completed_detail);
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.historyFragment_completedTitle));

        progressBarHandler = new ProgressBarHandler(this);

        String orderDate = getIntent().getStringExtra(KEY_FLAG_ORDER_DATE);
        String orderTime = getIntent().getStringExtra(KEY_FLAG_ORDER_TIME);
        final String orderBookingId = getIntent().getStringExtra(KEY_FLAG_BOOKING_ID);
        String orderServiceTypeId = getIntent().getStringExtra(KEY_FLAG_SERVICE_TYPE_ID);
        String orderServiceTypeDesc = getIntent().getStringExtra(KEY_FLAG_SERVICE_TYPE_DESC);
        String partnerId = getIntent().getStringExtra(KEY_FLAG_PARTNER_ID);
        String partnerName = getIntent().getStringExtra(KEY_FLAG_PARTNER_NAME);
        String partnerTypeId = getIntent().getStringExtra(KEY_FLAG_PARTNER_TYPE_ID);
        String partnerTypeDesc = getIntent().getStringExtra(KEY_FLAG_PARTNER_TYPE_DESC);
        String specializationId = getIntent().getStringExtra(KEY_FLAG_PARTNER_SPECIALIZATION_ID);
        String specializationDesc = getIntent().getStringExtra(KEY_FLAG_PARTNER_SPECIALIZATION_DESC);
        final String partnerPic = getIntent().getStringExtra(KEY_FLAG_PARTNER_PIC);
        String mobileNo = getIntent().getStringExtra(KEY_FLAG_MOBILE_NO);
        String rating = getIntent().getStringExtra(KEY_FLAG_RATING);
        String paymentId = getIntent().getStringExtra(KEY_FLAG_PAYMENT_ID);
        String paymentDesc = getIntent().getStringExtra(KEY_FLAG_PAYMENT_DESC);
        String promoCode = getIntent().getStringExtra(KEY_FLAG_PROMO_CODE);
        String priceAmt = getIntent().getStringExtra(KEY_FLAG_PRICE_AMOUNT);
        String cancelBy = getIntent().getStringExtra(KEY_FLAG_CANCEL_BY);
        String cancelReasonByUser = getIntent().getStringExtra(KEY_FLAG_CANCEL_REASON_BY_USER);
        String cancelReasonByPartner = getIntent().getStringExtra(KEY_FLAG_CANCEL_REASON_BY_PARTNER);
        String diagnose = getIntent().getStringExtra(KEY_FLAG_DIAGNOSE);
        String actionTypeDesc = getIntent().getStringExtra(KEY_FLAG_ACTION_TYPE_DESC);
        String prescriptionTypeId = getIntent().getStringExtra(KEY_FLAG_PRESCRIPTION_TYPE_ID);

        if(!partnerPic.equals("")) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_action_user)
                    .fitCenter();

            Glide.with(HistoryCompletedDetailActivity.this)
                    .load(partnerPic)
                    .apply(requestOptions)
                    .into(doctorPic);
        }
        doctorName.setText(partnerName);
        if(partnerTypeId.equals("03")) {
            doctorType.setText(specializationDesc);
        }
        else {
            doctorType.setText(partnerTypeDesc);
        }
        doctorRating.setMax(5);
        if(!rating.equals("")) {
            doctorRating.setRating(Float.parseFloat(rating));
        }
        bookingId.setText(orderBookingId);
        bookDate.setText(orderDate);
        bookType.setText(orderServiceTypeDesc);
        paymentType.setText(paymentDesc);

        priceAmount.addTextChangedListener(new CurrencyTextWatcherTextView(priceAmount));
        priceAmount.setText(priceAmt);

        diagnoseInfo.setText(diagnose);
        actionInfo.setText(actionTypeDesc);

        doctorPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!partnerPic.equals("")) {
                    Intent intent = new Intent(HistoryCompletedDetailActivity.this, ViewImageActivity.class);
                    intent.putExtra(ViewImageActivity.EXTRA_IMAGE_BASE_DATA, partnerPic);
                    startActivity(intent);
                }
            }
        });

        emailReceiptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(HistoryCompletedDetailActivity.this)
                        .setTitle(R.string.historyCompletedDetailActivity_emailReceiptTitle)
                        .setMessage(R.string.historyCompletedDetailActivity_emailReceiptMessage)
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton(R.string.historyCompletedDetailActivity_send, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendEmailReceipt(orderBookingId);
                            }
                        })
                        .setNegativeButton(R.string.accountActivity_cancelTitle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        emailDiagnosisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(HistoryCompletedDetailActivity.this)
                        .setTitle(R.string.historyCompletedDetailActivity_emailMedicalRecordTitle)
                        .setMessage(R.string.historyCompletedDetailActivity_emailMedicalRecordMessage)
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton(R.string.historyCompletedDetailActivity_send, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendEmailMedicalRecord(orderBookingId);
                            }
                        })
                        .setNegativeButton(R.string.accountActivity_cancelTitle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void sendEmailReceipt(String bookingId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("booking_id", bookingId);

        myCillinAPI.sendEmailReceipt(token, params).enqueue(new Callback<ModelResultEmailReceipt>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultEmailReceipt> call, @NonNull Response<ModelResultEmailReceipt> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultEmailReceipt modelResultEmailReceipt = response.body();

                    assert modelResultEmailReceipt != null;
                    if(modelResultEmailReceipt.getResult().isStatus()) {
                        String message = modelResultEmailReceipt.getResult().getMessage();
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
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
            public void onFailure(@NonNull Call<ModelResultEmailReceipt> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void sendEmailMedicalRecord(String bookingId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("booking_id", bookingId);

        myCillinAPI.sendEmailMedicalRecord(token, params).enqueue(new Callback<ModelResultEmailMedicalRecord>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultEmailMedicalRecord> call, @NonNull Response<ModelResultEmailMedicalRecord> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultEmailMedicalRecord modelResultEmailMedicalRecord = response.body();

                    assert modelResultEmailMedicalRecord != null;
                    if(modelResultEmailMedicalRecord.getResult().isStatus()) {
                        String message = modelResultEmailMedicalRecord.getResult().getMessage();
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
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
            public void onFailure(@NonNull Call<ModelResultEmailMedicalRecord> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
