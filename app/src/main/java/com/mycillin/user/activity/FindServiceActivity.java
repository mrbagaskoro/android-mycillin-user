package com.mycillin.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.mycillin.user.R;
import com.mycillin.user.fragment.HomeFragment;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.partnerTypeList.ModelResultPartnerTypeList;
import com.mycillin.user.rest.specializationList.ModelResultSpecializationList;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindServiceActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.findServiceActivity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.findServiceActivity_rg_locationRg)
    RadioGroup radioGroupLocation;
    @BindView(R.id.findServiceActivity_tv_placeInfo)
    TextView placeInfoTxt;
    @BindView(R.id.findServiceActivity_et_partnerType)
    EditText partnerTypeDropdown;
    @BindView(R.id.findServiceActivity_et_partnerSpecialization)
    EditText partnerSpecializationDropdown;
    @BindView(R.id.findServiceActivity_rg_genderRg)
    RadioGroup genderRadioGroup;
    @BindView(R.id.findServiceActivity_sb_BPJSSwitch)
    Switch BPJSSwitch;
    @BindView(R.id.findServiceActivity_bt_searchBtn)
    Button searchBtn;
    @BindView(R.id.findServiceActivity_tv_clearLocation)
    TextView clearLocationTxt;

    private ProgressBarHandler progressBarHandler;

    private ArrayList<String> items = new ArrayList<>();
    private HashMap<Integer, String> partnerTypeIdItemsTemp = new HashMap<>();
    private String selectedPartnerTypeId = "";
    private String selectedPartnerTypeDesc = "";
    private HashMap<Integer, String> partnerSpecializationItemsTemp = new HashMap<>();
    private String selectedPartnerSpecializationId = "";
    private String selectedPartnerSpecializationDesc = "";
    private String selectedGender = "";
    private String selectedBPJS = "0";
    private double selectedCurrentLatitude = 0.0;
    private double selectedCurrentLongitude = 0.0;
    private double selectedSearchedLatitude = 0.0;
    private double selectedSearchedLongitude = 0.0;

    private Location mLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 15000;  /* 15 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public final int REQUEST_CODE_PLACE_AUTOCOMPLETE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_service);
        ButterKnife.bind(this);

        progressBarHandler = new ProgressBarHandler(this);

        if(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_BOOK_DOCTOR)) {
            toolbar.setTitle(getResources().getString(R.string.servicesActivity_bookDoctorTitle));
        }
        else if(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_MEDICAL_RESERVATION)) {
            toolbar.setTitle(getResources().getString(R.string.serviceActivity_medicalReservationTitle));
        }
        else if(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_BOOK_HEALTHCARE)) {
            toolbar.setTitle(getResources().getString(R.string.servicesActivity_homecareServiceText));
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        radioGroupLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton;

                if(i == R.id.findServiceActivity_rb_currentLocationRb) {
                    checkedRadioButton = radioGroup.findViewById(i);
                    if (checkedRadioButton.isChecked()) {
                        placeInfoTxt.setVisibility(View.VISIBLE);
                        placeInfoTxt.setText(getAdressFromCoordinates(selectedCurrentLatitude, selectedCurrentLongitude));
                    }
                }
                else if(i == R.id.findServiceActivity_rb_searchLocationRb) {
                    checkedRadioButton = radioGroup.findViewById(i);
                    if (checkedRadioButton.isChecked()) {
                        try {
                            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                                    .setTypeFilter(Place.TYPE_COUNTRY)
                                    .setCountry("ID")
                                    .build();

                            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .setFilter(typeFilter)
                                    .build(FindServiceActivity.this);

                            startActivityForResult(intent, REQUEST_CODE_PLACE_AUTOCOMPLETE);

                        } catch (GooglePlayServicesRepairableException e) {
                            Snackbar.make(getWindow().getDecorView().getRootView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            Snackbar.make(getWindow().getDecorView().getRootView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        final SpinnerDialog partnerTypeSpinnerDialog = new SpinnerDialog(FindServiceActivity.this, items, "Select a Partner Type", R.style.DialogAnimations_SmileWindow);
        partnerTypeSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                partnerTypeDropdown.setText(s);
                for(int j = 0; j < partnerTypeIdItemsTemp.size(); j++) {
                    if(partnerTypeIdItemsTemp.get(j).split(" - ")[1].equals(s)) {
                        selectedPartnerTypeId = partnerTypeIdItemsTemp.get(j).split(" - ")[0];
                        selectedPartnerTypeDesc = partnerTypeIdItemsTemp.get(j).split(" - ")[1];
                    }
                }
            }
        });

        partnerTypeDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPartnerType(partnerTypeSpinnerDialog, getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM));
            }
        });

        final SpinnerDialog specializationSpinnerDialog = new SpinnerDialog(FindServiceActivity.this, items, "Select a Specialization", R.style.DialogAnimations_SmileWindow);
        specializationSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                partnerSpecializationDropdown.setText(s);
                for(int j = 0; j < partnerSpecializationItemsTemp.size(); j++) {
                    if(partnerSpecializationItemsTemp.get(j).split(" - ")[1].equals(s)) {
                        selectedPartnerSpecializationId = partnerSpecializationItemsTemp.get(j).split(" - ")[0];
                        selectedPartnerSpecializationDesc = partnerSpecializationItemsTemp.get(j).split(" - ")[1];
                    }
                }
            }
        });

        partnerSpecializationDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedPartnerTypeId.equals("")) {
                    getSpecialization(specializationSpinnerDialog, selectedPartnerTypeId);
                }
                else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), R.string.filterDoctorActivity_partnerSpecializationDropdownMessage, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.findServiceActivity_rb_genderAllRb) {
                    selectedGender = "";
                }
                else if(i == R.id.findServiceActivity_rb_genderMaleRb) {
                    selectedGender = "L";
                }
                else if(i == R.id.findServiceActivity_rb_genderFemaleRb) {
                    selectedGender = "P";
                }
            }
        });

        BPJSSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    selectedBPJS = "1";
                }
                else {
                    selectedBPJS = "0";
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioGroupLocation.getCheckedRadioButtonId() == -1) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Please select your location first.", Snackbar.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(FindServiceActivity.this, PartnerListActivity.class);
                    intent.putExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM, getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM));
                    intent.putExtra(HomeFragment.EXTRA_RELATION_ID, getIntent().getStringExtra(HomeFragment.EXTRA_RELATION_ID));
                    intent.putExtra(PartnerListActivity.EXTRA_PARTNER_TYPE_ID, selectedPartnerTypeId);
                    intent.putExtra(PartnerListActivity.EXTRA_PARTNER_SPECIALIZATION_ID, selectedPartnerSpecializationId);
                    intent.putExtra(PartnerListActivity.EXTRA_PARTNER_GENDER, selectedGender);
                    intent.putExtra(PartnerListActivity.EXTRA_PARTNER_BPJS_STATUS, selectedBPJS);

                    if(radioGroupLocation.getCheckedRadioButtonId() == R.id.findServiceActivity_rb_currentLocationRb) {
                        intent.putExtra(PartnerListActivity.EXTRA_USER_LATITUDE, selectedCurrentLatitude + "");
                        intent.putExtra(PartnerListActivity.EXTRA_USER_LONGITUDE, selectedCurrentLongitude + "");
                    }
                    else if(radioGroupLocation.getCheckedRadioButtonId() == R.id.findServiceActivity_rb_searchLocationRb) {
                        intent.putExtra(PartnerListActivity.EXTRA_USER_LATITUDE, selectedSearchedLatitude + "");
                        intent.putExtra(PartnerListActivity.EXTRA_USER_LONGITUDE, selectedSearchedLongitude + "");
                    }

                    startActivity(intent);
                }
            }
        });

        clearLocationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroupLocation.clearCheck();
                placeInfoTxt.setVisibility(View.GONE);
                placeInfoTxt.setText("");
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
            new AlertDialog.Builder(FindServiceActivity.this)
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
                        public void onLocationChanged(Location location) {
                            if(location != null) {
                                selectedCurrentLatitude = location.getLatitude();
                                selectedCurrentLongitude = location.getLongitude();
                            }
                        }
                    });
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_PLACE_AUTOCOMPLETE) {
            if(resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                selectedSearchedLatitude = place.getLatLng().latitude;
                selectedSearchedLongitude = place.getLatLng().longitude;

                placeInfoTxt.setVisibility(View.VISIBLE);
                placeInfoTxt.setText(place.getAddress());
            }
            else if(resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Snackbar.make(getWindow().getDecorView().getRootView(), status.getStatusMessage(), Snackbar.LENGTH_LONG).show();

            }
            else if(resultCode == RESULT_CANCELED) {
                radioGroupLocation.clearCheck();
                placeInfoTxt.setVisibility(View.GONE);
                placeInfoTxt.setText("");
            }
        }
    }

    private void getPartnerType(final SpinnerDialog spinnerDialog, String serviceTypeId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("service_type_id", serviceTypeId);

        myCillinAPI.getPartnerTypeList(token, params).enqueue(new Callback<ModelResultPartnerTypeList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultPartnerTypeList> call, @NonNull Response<ModelResultPartnerTypeList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultPartnerTypeList modelResultPartnerTypeList = response.body();

                    assert modelResultPartnerTypeList != null;
                    if(modelResultPartnerTypeList.getResult().isStatus()) {
                        items.clear();
                        partnerTypeIdItemsTemp.clear();
                        int size = modelResultPartnerTypeList.getResult().getData().size();
                        for(int i = 0; i < size; i++) {
                            String partnerTypeId = modelResultPartnerTypeList.getResult().getData().get(i).getPartnerTypeId();
                            String partnerTypeDesc = modelResultPartnerTypeList.getResult().getData().get(i).getPartnerTypeDesc();

                            items.add(partnerTypeDesc);
                            partnerTypeIdItemsTemp.put(i, partnerTypeId + " - " + partnerTypeDesc);
                        }
                        spinnerDialog.showSpinerDialog();
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
            public void onFailure(@NonNull Call<ModelResultPartnerTypeList> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getSpecialization(final SpinnerDialog spinnerDialog, String partnerTypeId) {
        progressBarHandler.show();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("partner_type_id", partnerTypeId);

        myCillinAPI.getSpecializationList(params).enqueue(new Callback<ModelResultSpecializationList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultSpecializationList> call, @NonNull Response<ModelResultSpecializationList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultSpecializationList modelResultSpecializationList = response.body();

                    assert modelResultSpecializationList != null;
                    if(modelResultSpecializationList.getResult().isStatus()) {
                        items.clear();
                        partnerSpecializationItemsTemp.clear();
                        int size = modelResultSpecializationList.getResult().getData().size();
                        for(int i = 0; i < size; i++) {
                            String specializationId = modelResultSpecializationList.getResult().getData().get(i).getSpecializationId();
                            String specializationDesc = modelResultSpecializationList.getResult().getData().get(i).getSpecializationDesc();

                            items.add(specializationDesc);
                            partnerSpecializationItemsTemp.put(i, specializationId + " - " + specializationDesc);
                        }
                        spinnerDialog.showSpinerDialog();
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
            public void onFailure(@NonNull Call<ModelResultSpecializationList> call, @NonNull Throwable t) {
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
            selectedCurrentLatitude = mLocation.getLatitude();
            selectedCurrentLongitude = mLocation.getLongitude();
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
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Enable Permissions", Toast.LENGTH_LONG).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, new com.google.android.gms.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if(location != null) {
                            selectedCurrentLatitude = location.getLatitude();
                            selectedCurrentLongitude = location.getLongitude();
                        }
                    }
                });


    }

    private String getAdressFromCoordinates(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        String res = "";

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(addresses.size() > 0) {
                res = addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }
}
