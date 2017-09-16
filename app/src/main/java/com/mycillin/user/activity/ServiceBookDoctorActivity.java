package com.mycillin.user.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ServiceBookDoctorActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.serviceBookDoctorActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.serviceBookDoctorActivity_bt_searchBtn)
    Button searchBtn;
    @BindView(R.id.serviceBookDoctorActivity_bt_filterBtn)
    Button filterBtn;

    private EditText filterMedicalPersonelType;
    private EditText filterSpecialistsType;
    private EditText filterMedicalPersonelGender;
    private Button filterCancelBtn;
    private Button filterApplyBtn;

    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_book_doctor);
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.servicesActivity_bookDoctorTitle));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.serviceBookDoctorActivity_fr_mapFragment);
        mapFragment.getMapAsync(this);

        PlaceAutocompleteFragment placeAutocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.serviceBookDoctorActivity_fr_placeAutoCompleteFragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();
        placeAutocompleteFragment.setFilter(typeFilter);
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                onMapSearch(place);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(ServiceBookDoctorActivity.this,"An error occurred: " + status, Toast.LENGTH_LONG ).show();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialog();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        LatLng jakarta = new LatLng(6.1751, 106.8650);
        gMap.addMarker(new MarkerOptions().position(jakarta).title("Jakarta"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(jakarta));
    }

    public void onMapSearch(Place place) {
        gMap.clear();
        gMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
        gMap.animateCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
    }

    public void showFilterDialog() {
        final DialogPlus dialogPlus = DialogPlus.newDialog(ServiceBookDoctorActivity.this)
                .setContentHolder(new ViewHolder(R.layout.filter_book_doctor_dialog_layout))
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
