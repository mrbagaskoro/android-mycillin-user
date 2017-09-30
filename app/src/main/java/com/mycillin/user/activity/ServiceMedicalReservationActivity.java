package com.mycillin.user.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycillin.user.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceMedicalReservationActivity extends AppCompatActivity {

    @BindView(R.id.serviceMedicalReservationActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.serviceMedicalReservationActivity_bt_searchBtn)
    Button searchBtn;
    @BindView(R.id.serviceMedicalReservationActivity_fab_filterFAB)
    FloatingActionButton filterBtn;

    private EditText filterMedicalPersonelType;
    private EditText filterSpecialistsType;
    private EditText filterMedicalPersonelGender;
    private Button filterCancelBtn;
    private Button filterApplyBtn;

    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_medical_reservation);
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.serviceActivity_medicalReservationTitle));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.serviceMedicalReservationActivity_fr_mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;

                LatLng bapindo = new LatLng(-6.224190, 106.80791);
                gMap.addMarker(new MarkerOptions().position(bapindo).title("Plaza Bapindo"));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bapindo, 15.0f));
            }
        });

        PlaceAutocompleteFragment placeAutocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.serviceMedicalReservationActivity_fr_placeAutoCompleteFragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("ID")
                .build();
        placeAutocompleteFragment.setFilter(typeFilter);
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                onMapSearch(place);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(ServiceMedicalReservationActivity.this,"An error occurred: " + status, Toast.LENGTH_LONG ).show();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialog();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceMedicalReservationActivity.this, MedicalPersonnelActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onMapSearch(Place place) {
        gMap.clear();
        gMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15.0f));
    }

    public void showFilterDialog() {
        final DialogPlus dialogPlus = DialogPlus.newDialog(ServiceMedicalReservationActivity.this)
                .setContentHolder(new ViewHolder(R.layout.dialog_filter_book_doctor_layout))
                .create();
        dialogPlus.show();

        View dialogPlusView = dialogPlus.getHolderView();

        filterMedicalPersonelType = dialogPlusView.findViewById(R.id.filterBookDoctor_et_medicalPersonnelType);
        filterSpecialistsType = dialogPlusView.findViewById(R.id.filterBookDoctor_et_specialistsType);
        filterMedicalPersonelGender = dialogPlusView.findViewById(R.id.filterBookDoctor_et_medicalPersonnelGender);
        filterCancelBtn = dialogPlusView.findViewById(R.id.filterBookDoctor_bt_cancelBtn);
        filterApplyBtn = dialogPlusView.findViewById(R.id.filterBookDoctor_bt_applyBtn);

        filterCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlus.dismiss();
            }
        });
    }
}
