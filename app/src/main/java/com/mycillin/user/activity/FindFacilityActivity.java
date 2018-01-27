package com.mycillin.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycillin.user.R;
import com.mycillin.user.adapter.FacilityAdapter;
import com.mycillin.user.list.FacilityList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.findFacility.ModelResultFindFacility;
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

public class FindFacilityActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.findFacilityActivity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.findFacilityActivity_fab_locationFAB)
    FloatingActionButton locationBtn;

    private GoogleMap gMap;
    private SupportMapFragment mapFragment;

    private Location mLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_facility);
        ButterKnife.bind(this);

        progressBarHandler = new ProgressBarHandler(this);

        toolbar.setTitle(getResources().getString(R.string.servicesActivity_facilityServiceTitle));

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.findFacilityActivity_fr_mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                gMap.getUiSettings().setMapToolbarEnabled(false);

                gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        FacilityList facilityList = (FacilityList) marker.getTag();
                        if(facilityList != null) {
                            try {
                                Uri uri = Uri.parse("google.navigation:q=" + facilityList.getLatitude() + "," + facilityList.getLongitude() + "&mode=d&avoid=f");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                intent.setPackage("com.google.android.apps.maps");
                                startActivity(intent);

                            } catch (android.content.ActivityNotFoundException e) {
                                String message = getResources().getString(R.string.historyInProgressDetailActivity_googleMapsApk);
                                Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        PlaceAutocompleteFragment placeAutocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.findFacilityActivity_fr_placeAutoCompleteFragment);

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
                Toast.makeText(FindFacilityActivity.this,"An error occurred: " + status, Toast.LENGTH_LONG ).show();
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation(currentLatitude, currentLongitude);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkPlayServices()) {
            new AlertDialog.Builder(FindFacilityActivity.this)
                    .setTitle(R.string.mainActivity_infoTitle)
                    .setMessage(R.string.findServiceActivity_googlePlayServiceWarningMessage)
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton(getString(R.string.ratingActivity_ratingDesc3), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi
                    .removeLocationUpdates(mGoogleApiClient, new com.google.android.gms.location.LocationListener() {
                        @Override
                        public void onLocationChanged(final Location location) {
                            if(location != null) {
                                currentLatitude = location.getLatitude();
                                currentLongitude = location.getLongitude();
                                getLocation(location.getLatitude(), location.getLongitude());
                            }
                        }
                    });
            mGoogleApiClient.disconnect();
        }
    }

    public void onMapSearch(Place place) {
        gMap.clear();
        gMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));

        getFacilityList(place.getLatLng().latitude, place.getLatLng().longitude);
    }

    private void getLocation(final double latitude, final double longitude) {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                gMap.clear();

                LatLng bapindo = new LatLng(latitude, longitude);
                gMap.addMarker(new MarkerOptions().position(bapindo).title(getResources().getString(R.string.serviceBookDoctorActivity_myLocationMarker)));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bapindo, 12.0f));

                getFacilityList(latitude, longitude);
            }
        });
    }

    private void getFacilityList(double latitude, double longitude) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("latitude", latitude + "");
        params.put("longitude", longitude + "");

        myCillinAPI.findFacility(token, params).enqueue(new Callback<ModelResultFindFacility>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultFindFacility> call, @NonNull Response<ModelResultFindFacility> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultFindFacility modelResultFindFacility = response.body();

                    assert modelResultFindFacility != null;
                    if(modelResultFindFacility.getResult().isStatus()) {
                        int size = modelResultFindFacility.getResult().getData().size();
                        if(size > 0) {
                            for(int i = 0; i < size; i++) {
                                String facilityName = modelResultFindFacility.getResult().getData().get(i).getFacilityName() == null ? "" : modelResultFindFacility.getResult().getData().get(i).getFacilityName();
                                String facilityLatitude = modelResultFindFacility.getResult().getData().get(i).getLatitude() == null ? "" : modelResultFindFacility.getResult().getData().get(i).getLatitude();
                                String facilityLongitude = modelResultFindFacility.getResult().getData().get(i).getLongitude() == null ? "" : modelResultFindFacility.getResult().getData().get(i).getLongitude();
                                String facilityDistance = modelResultFindFacility.getResult().getData().get(i).getDistance() == null ? "" : modelResultFindFacility.getResult().getData().get(i).getDistance();
                                String facilityAddress = modelResultFindFacility.getResult().getData().get(i).getAddress() == null ? "" : modelResultFindFacility.getResult().getData().get(i).getAddress();
                                String facilityPhone = modelResultFindFacility.getResult().getData().get(i).getPhoneNo() == null ? "" : modelResultFindFacility.getResult().getData().get(i).getPhoneNo();
                                String facilityPicture = modelResultFindFacility.getResult().getData().get(i).getFacilityPicture() == null ? "" : modelResultFindFacility.getResult().getData().get(i).getFacilityPicture();

                                LatLng facility = new LatLng(Double.parseDouble(facilityLatitude), Double.parseDouble(facilityLongitude));
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(facility)
                                        .title(facilityName)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_facility_marker));

                                FacilityList facilityList = new FacilityList();
                                facilityList.setImage(facilityPicture);
                                facilityList.setName(facilityName);
                                facilityList.setLatitude(facilityLatitude);
                                facilityList.setLongitude(facilityLongitude);
                                facilityList.setDistance(facilityDistance);
                                facilityList.setAddress(facilityAddress);
                                facilityList.setPhone(facilityPhone);

                                FacilityAdapter facilityAdapter = new FacilityAdapter(FindFacilityActivity.this);
                                gMap.setInfoWindowAdapter(facilityAdapter);

                                Marker marker = gMap.addMarker(markerOptions);
                                marker.setTag(facilityList);
                            }
                        }
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
            public void onFailure(@NonNull Call<ModelResultFindFacility> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLocation != null) {
            currentLatitude = mLocation.getLatitude();
            currentLongitude = mLocation.getLongitude();
            getLocation(mLocation.getLatitude(), mLocation.getLongitude());
        }

        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else
                finish();

            return false;
        }
        return true;
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Enable Permissions", Toast.LENGTH_LONG).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, new com.google.android.gms.location.LocationListener() {
                    @Override
                    public void onLocationChanged(final Location location) {
                        if(location != null) {
                            currentLatitude = location.getLatitude();
                            currentLongitude = location.getLongitude();
                            getLocation(location.getLatitude(), location.getLongitude());
                        }
                    }
                });


    }
}
