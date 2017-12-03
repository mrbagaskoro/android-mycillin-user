package com.mycillin.user.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryInProgressDetailActivity extends AppCompatActivity {

    @BindView(R.id.historyInProgressDetailActivity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.historyInProgressDetailActivity_fab_callFAB)
    FloatingActionButton callBtn;
    @BindView(R.id.historyInProgressDetailActivity_fab_cancelFAB)
    FloatingActionButton cancelBtn;

    @BindView(R.id.historyInProgressDetailActivity_iv_userAvatar)
    CircleImageView doctorPic;
    @BindView(R.id.historyInProgressDetailActivity_tv_doctorName)
    TextView doctorName;
    @BindView(R.id.historyInProgressDetailActivity_tv_doctorType)
    TextView doctorType;
    @BindView(R.id.historyInProgressDetailActivity_rb_doctorRating)
    RatingBar doctorRating;
    @BindView(R.id.historyInProgressDetailActivity_tv_bookingId)
    TextView bookingId;
    @BindView(R.id.historyInProgressDetailActivity_tv_date)
    TextView bookDate;
    @BindView(R.id.historyInProgressDetailActivity_tv_serviceType)
    TextView bookType;
    @BindView(R.id.historyInProgressDetailActivity_tv_payment)
    TextView paymentType;
    @BindView(R.id.historyInProgressDetailActivity_tv_price)
    TextView priceAmount;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_in_progress_detail);
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.historyFragment_inProgressTitle));

        String orderDate = getIntent().getStringExtra(KEY_FLAG_ORDER_DATE);
        String orderTime = getIntent().getStringExtra(KEY_FLAG_ORDER_TIME);
        String orderBookingId = getIntent().getStringExtra(KEY_FLAG_BOOKING_ID);
        String orderServiceTypeId = getIntent().getStringExtra(KEY_FLAG_SERVICE_TYPE_ID);
        String orderServiceTypeDesc = getIntent().getStringExtra(KEY_FLAG_SERVICE_TYPE_DESC);
        String partnerId = getIntent().getStringExtra(KEY_FLAG_PARTNER_ID);
        String partnerName = getIntent().getStringExtra(KEY_FLAG_PARTNER_NAME);
        String partnerTypeId = getIntent().getStringExtra(KEY_FLAG_PARTNER_TYPE_ID);
        String partnerTypeDesc = getIntent().getStringExtra(KEY_FLAG_PARTNER_TYPE_DESC);
        String specializationId = getIntent().getStringExtra(KEY_FLAG_PARTNER_SPECIALIZATION_ID);
        String specializationDesc = getIntent().getStringExtra(KEY_FLAG_PARTNER_SPECIALIZATION_DESC);
        final String partnerPic = getIntent().getStringExtra(KEY_FLAG_PARTNER_PIC);
        final String mobileNo = getIntent().getStringExtra(KEY_FLAG_MOBILE_NO);
        String rating = getIntent().getStringExtra(KEY_FLAG_RATING);
        String paymentId = getIntent().getStringExtra(KEY_FLAG_PAYMENT_ID);
        String paymentDesc = getIntent().getStringExtra(KEY_FLAG_PAYMENT_DESC);
        String promoCode = getIntent().getStringExtra(KEY_FLAG_PROMO_CODE);
        String priceAmt = getIntent().getStringExtra(KEY_FLAG_PRICE_AMOUNT);

        if(!partnerPic.equals("")) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_action_user)
                    .fitCenter();

            Glide.with(HistoryInProgressDetailActivity.this)
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
        paymentType.setText(paymentDesc);
        priceAmount.setText(priceAmt);

        doctorPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!partnerPic.equals("")) {
                    Intent intent = new Intent(HistoryInProgressDetailActivity.this, ViewImageActivity.class);
                    intent.putExtra(ViewImageActivity.EXTRA_IMAGE_URL, partnerPic);
                    startActivity(intent);
                }
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobileNo));
                startActivity(intent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryInProgressDetailActivity.this, CancelActivity.class);
                startActivity(intent);
            }
        });
    }
}
