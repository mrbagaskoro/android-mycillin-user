package com.mycillin.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycillin.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryCompletedDetailActivity extends AppCompatActivity {

    @BindView(R.id.historyCompletedDetailActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.historyCompletedDetailActivity_tv_doctorName)
    TextView doctorName;
    @BindView(R.id.historyCompletedDetailActivity_tv_bookDate)
    TextView bookDate;
    @BindView(R.id.historyCompletedDetailActivity_tv_bookType)
    TextView bookType;

    private GoogleMap gMap;

    public static String KEY_FLAG_DOCTOR_NAME = "KEY_FLAG_DOCTOR_NAME";
    public static String KEY_FLAG_DOCTOR_TYPE = "KEY_FLAG_DOCTOR_TYPE";
    public static String KEY_FLAG_DOCTOR_DATE = "KEY_FLAG_DOCTOR_DATE";
    public static String KEY_FLAG_DOCTOR_TIME = "KEY_FLAG_DOCTOR_TIME";
    public static String KEY_FLAG_DOCTOR_PIC = "KEY_FLAG_DOCTOR_PIC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_completed_detail);
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.historyFragment_completedTitle));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.historyCompletedDetailActivity_fr_mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;

                LatLng bapindo = new LatLng(-6.224190, 106.80791);
                gMap.addMarker(new MarkerOptions().position(bapindo).title("Plaza Bapindo"));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bapindo, 15.0f));
            }
        });

        doctorName.setText(getIntent().getStringExtra(KEY_FLAG_DOCTOR_NAME));
        bookDate.setText(getIntent().getStringExtra(KEY_FLAG_DOCTOR_DATE) + ", " + getIntent().getStringExtra(KEY_FLAG_DOCTOR_TIME));
        bookType.setText(getIntent().getStringExtra(KEY_FLAG_DOCTOR_TYPE));
    }
}
