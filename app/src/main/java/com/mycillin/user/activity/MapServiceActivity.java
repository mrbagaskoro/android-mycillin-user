package com.mycillin.user.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
import com.mycillin.user.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapServiceActivity extends AppCompatActivity {

    @BindView(R.id.serviceBookDoctorActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.serviceBookDoctorActivity_bt_searchBtn)
    Button searchBtn;
    @BindView(R.id.serviceBookDoctorActivity_fab_locationFAB)
    FloatingActionButton locationBtn;
    @BindView(R.id.serviceBookDoctorActivity_fab_filterFAB)
    FloatingActionButton filterBtn;

    private GoogleMap gMap;
    private LocationManager locationManager;

    private String filterPartnerTypeId = "";
    private String filterPartnerSpecializationId = "";
    private String filterPartnerGender = "";
    private String filterPartnerBPJSStatus = "1";
    private double selectedLatitude;
    private double selectedLongitude;

    public static final String KEY_MEDICAL_RESERVATION = "KEY_MEDICAL_RESERVATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_service);
        ButterKnife.bind(this);

        if(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_BOOK_DOCTOR)) {
            toolbar.setTitle(getResources().getString(R.string.servicesActivity_bookDoctorTitle));
        }
        else if(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_MEDICAL_RESERVATION)) {
            toolbar.setTitle(getResources().getString(R.string.serviceActivity_medicalReservationTitle));
        }

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.serviceBookDoctorActivity_fr_mapFragment);
        getLocation(mapFragment);

        PlaceAutocompleteFragment placeAutocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.serviceBookDoctorActivity_fr_placeAutoCompleteFragment);

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
                Toast.makeText(MapServiceActivity.this,"An error occurred: " + status, Toast.LENGTH_LONG ).show();
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation(mapFragment);
            }
        });
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapServiceActivity.this, FilterDoctorActivity.class);
                startActivityForResult(intent, FilterDoctorActivity.REQUEST_CODE_FILTER);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapServiceActivity.this, PartnerListActivity.class);
                intent.putExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM, getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM));
                intent.putExtra(PartnerListActivity.EXTRA_PARTNER_TYPE_ID, filterPartnerTypeId);
                intent.putExtra(PartnerListActivity.EXTRA_PARTNER_SPECIALIZATION_ID, filterPartnerSpecializationId);
                intent.putExtra(PartnerListActivity.EXTRA_PARTNER_GENDER, filterPartnerGender);
                intent.putExtra(PartnerListActivity.EXTRA_PARTNER_BPJS_STATUS, filterPartnerBPJSStatus);
                intent.putExtra(PartnerListActivity.EXTRA_USER_LATITUDE, selectedLatitude + "");
                intent.putExtra(PartnerListActivity.EXTRA_USER_LONGITUDE, selectedLongitude + "");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FilterDoctorActivity.REQUEST_CODE_FILTER) {
            if(resultCode == RESULT_OK) {
                filterPartnerTypeId = data.getStringExtra(FilterDoctorActivity.EXTRA_PARTNER_TYPE_ID);
                filterPartnerSpecializationId = data.getStringExtra(FilterDoctorActivity.EXTRA_PARTNER_SPECIALIZATION_ID);
                filterPartnerGender = data.getStringExtra(FilterDoctorActivity.EXTRA_PARTNER_GENDER);
                filterPartnerBPJSStatus = data.getStringExtra(FilterDoctorActivity.EXTRA_PARTNER_BPJS_STATUS);
            }
        }


    }

    public void onMapSearch(Place place) {
        gMap.clear();
        gMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15.0f));

        selectedLatitude = place.getLatLng().latitude;
        selectedLongitude = place.getLatLng().longitude;
    }

    private void getLocation(final SupportMapFragment mapFragment) {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            gMap = googleMap;
                            gMap.clear();

                            LatLng bapindo = new LatLng(location.getLatitude(), location.getLongitude());
                            gMap.addMarker(new MarkerOptions().position(bapindo).title(getResources().getString(R.string.serviceBookDoctorActivity_myLocationMarker)));
                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bapindo, 15.0f));

                            selectedLatitude = location.getLatitude();
                            selectedLongitude = location.getLongitude();
                        }
                    });
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.serviceBookDoctorActivity_locationDisabledWarning), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
}
