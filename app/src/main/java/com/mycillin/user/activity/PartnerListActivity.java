package com.mycillin.user.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.adapter.PartnerListAdapter;
import com.mycillin.user.fragment.HomeFragment;
import com.mycillin.user.list.PartnerList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.findClinic.ModelResultFindClinic;
import com.mycillin.user.rest.findConsultation.ModelResultFindConsultation;
import com.mycillin.user.rest.findHealthcare.ModelResultFindHealthcare;
import com.mycillin.user.rest.findPartner.ModelResultFindPartner;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.RecyclerTouchListener;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartnerListActivity extends AppCompatActivity {

    @BindView(R.id.partnerListActivity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.partnerListActivity_rv_recyclerView)
    RecyclerView medicalPersonnelRecyclerView;
    @BindView(R.id.partnerListActivity_ll_messageContainer)
    LinearLayout messageContainer;
    @BindView(R.id.partnerListActivity_tv_message)
    TextView message;
    @BindView(R.id.partnerListActivity_ll_searchContainer)
    LinearLayout searchContainer;
    @BindView(R.id.partnerListActivity_et_search)
    EditText searchEdtxt;
    @BindView(R.id.partnerListActivity_ll_recordsCountContainer)
    LinearLayout recordsCountContainer;
    @BindView(R.id.partnerListActivity_tv_recordsCount)
    TextView recordsCount;

    private List<PartnerList> partnerLists = new ArrayList<>();
    private PartnerListAdapter medicalPersonneldAdapter;

    public static final String EXTRA_PARTNER_TYPE_ID = "EXTRA_PARTNER_TYPE_ID";
    public static final String EXTRA_PARTNER_SPECIALIZATION_ID = "EXTRA_PARTNER_SPECIALIZATION_ID";
    public static final String EXTRA_PARTNER_GENDER = "EXTRA_PARTNER_GENDER";
    public static final String EXTRA_PARTNER_BPJS_STATUS = "EXTRA_PARTNER_BPJS_STATUS";
    public static final String EXTRA_USER_LATITUDE = "EXTRA_USER_LATITUDE";
    public static final String EXTRA_USER_LONGITUDE = "EXTRA_USER_LONGITUDE";

    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        progressBarHandler = new ProgressBarHandler(this);

        if(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_BOOK_DOCTOR)) {
            getSupportActionBar().setTitle(R.string.medicalPersonnelActivity_title);

            getDoctorList(getIntent().getStringExtra(EXTRA_PARTNER_TYPE_ID),
                    getIntent().getStringExtra(EXTRA_PARTNER_SPECIALIZATION_ID),
                    getIntent().getStringExtra(EXTRA_PARTNER_GENDER),
                    getIntent().getStringExtra(EXTRA_PARTNER_BPJS_STATUS),
                    getIntent().getStringExtra(EXTRA_USER_LATITUDE),
                    getIntent().getStringExtra(EXTRA_USER_LONGITUDE));
        }
        else if(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_MEDICAL_RESERVATION)) {
            getSupportActionBar().setTitle(R.string.partnerListActivity_clinicTitle);

            getClinicList(getIntent().getStringExtra(EXTRA_PARTNER_TYPE_ID),
                    getIntent().getStringExtra(EXTRA_PARTNER_SPECIALIZATION_ID),
                    getIntent().getStringExtra(EXTRA_PARTNER_GENDER),
                    getIntent().getStringExtra(EXTRA_PARTNER_BPJS_STATUS),
                    getIntent().getStringExtra(EXTRA_USER_LATITUDE),
                    getIntent().getStringExtra(EXTRA_USER_LONGITUDE));
        }
        else if(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_BOOK_HEALTHCARE)) {
            getSupportActionBar().setTitle(R.string.partnerListActivity_homecareTitle);

            getHealthcareList(getIntent().getStringExtra(EXTRA_PARTNER_TYPE_ID),
                    getIntent().getStringExtra(EXTRA_PARTNER_SPECIALIZATION_ID),
                    getIntent().getStringExtra(EXTRA_PARTNER_GENDER),
                    getIntent().getStringExtra(EXTRA_PARTNER_BPJS_STATUS),
                    getIntent().getStringExtra(EXTRA_USER_LATITUDE),
                    getIntent().getStringExtra(EXTRA_USER_LONGITUDE));
        }
        else if(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_CONSULTATION)) {
            getSupportActionBar().setTitle(R.string.partnerListActivity_consultationTitle);

            getConsultationList(getIntent().getStringExtra(EXTRA_PARTNER_TYPE_ID),
                    getIntent().getStringExtra(EXTRA_PARTNER_SPECIALIZATION_ID),
                    getIntent().getStringExtra(EXTRA_PARTNER_GENDER));
        }

        searchEdtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = searchEdtxt.getText().toString();
                medicalPersonneldAdapter.search(query);
                recordsCount.setText(getString(R.string.medicalPersonnelActivity_records, partnerLists.size()));
            }
        });

        medicalPersonnelRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), medicalPersonnelRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PartnerList list = partnerLists.get(position);

                Intent intent = new Intent(PartnerListActivity.this, PartnerDetailActivity.class);
                intent.putExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM, getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM));
                intent.putExtra(HomeFragment.EXTRA_RELATION_ID, getIntent().getStringExtra(HomeFragment.EXTRA_RELATION_ID));
                intent.putExtra(PartnerDetailActivity.KEY_FLAG_PARTNER_ID, list.getDoctorId());
                intent.putExtra(EXTRA_USER_LATITUDE, getIntent().getStringExtra(EXTRA_USER_LATITUDE));
                intent.putExtra(EXTRA_USER_LONGITUDE, getIntent().getStringExtra(EXTRA_USER_LONGITUDE));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getDoctorList(String partnerType, String partnerSpecialization, String gender, String bpjs, String latitude, String longitude) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("partner_type_id", partnerType);
        params.put("spesialisasi_id", partnerSpecialization);
        params.put("gender", gender);
        params.put("BPJS_RCV_status", bpjs);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        myCillinAPI.findPartner(token, params).enqueue(new Callback<ModelResultFindPartner>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultFindPartner> call, @NonNull Response<ModelResultFindPartner> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultFindPartner modelResultFindPartner = response.body();

                    assert modelResultFindPartner != null;
                    if(modelResultFindPartner.getResult().isStatus()) {
                        int size = modelResultFindPartner.getResult().getData().size();
                        if(size > 0) {
                            messageContainer.setVisibility(View.GONE);
                            medicalPersonnelRecyclerView.setVisibility(View.VISIBLE);
                            searchContainer.setVisibility(View.VISIBLE);
                            recordsCountContainer.setVisibility(View.VISIBLE);
                            recordsCount.setText(getString(R.string.medicalPersonnelActivity_records, size));

                            medicalPersonnelRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            medicalPersonnelRecyclerView.setItemAnimator(new DefaultItemAnimator());

                            partnerLists.clear();

                            for(int i = 0; i < size; i++) {
                                String doctorId = modelResultFindPartner.getResult().getData().get(i).getUserId() == null ? "" : modelResultFindPartner.getResult().getData().get(i).getUserId();
                                String doctorName = modelResultFindPartner.getResult().getData().get(i).getFullName() == null ? "" : modelResultFindPartner.getResult().getData().get(i).getFullName();
                                String doctorType = "PAK TOMMI TOLONG KELUARKAN DESC TIPE / DESC SPESIALISASI DOKTER DONG";
                                String doctorPermitt = modelResultFindPartner.getResult().getData().get(i).getNoSIP() == null ? "" : modelResultFindPartner.getResult().getData().get(i).getNoSIP();
                                String doctorPic = modelResultFindPartner.getResult().getData().get(i).getProfilePhoto() == null ? "" : modelResultFindPartner.getResult().getData().get(i).getProfilePhoto();
                                String doctorLatitude = modelResultFindPartner.getResult().getData().get(i).getLatitude() == null ? "" : modelResultFindPartner.getResult().getData().get(i).getLatitude();
                                String doctorLongitude = modelResultFindPartner.getResult().getData().get(i).getLongitude() == null ? "" : modelResultFindPartner.getResult().getData().get(i).getLongitude();
                                String doctorDistance = modelResultFindPartner.getResult().getData().get(i).getDistance() == null ? "" : modelResultFindPartner.getResult().getData().get(i).getDistance();

                                partnerLists.add(new PartnerList(doctorId, doctorName,
                                        doctorType, doctorPermitt, doctorPic, doctorLatitude,
                                        doctorLongitude, doctorDistance));

                            }

                            medicalPersonneldAdapter = new PartnerListAdapter(partnerLists, PartnerListActivity.this);
                            medicalPersonnelRecyclerView.setAdapter(medicalPersonneldAdapter);
                            medicalPersonneldAdapter.notifyDataSetChanged();
                        }
                        else {
                            messageContainer.setVisibility(View.VISIBLE);
                            message.setText(R.string.medicalRecordDetailActivity_noData);
                            medicalPersonnelRecyclerView.setVisibility(View.GONE);
                            searchContainer.setVisibility(View.GONE);
                            recordsCountContainer.setVisibility(View.GONE);
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
            public void onFailure(@NonNull Call<ModelResultFindPartner> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getClinicList(String partnerType, String partnerSpecialization, String gender, String bpjs, String latitude, String longitude) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("partner_type_id", partnerType);
        params.put("spesialisasi_id", partnerSpecialization);
        params.put("gender", gender);
        params.put("BPJS_RCV_status", bpjs);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        myCillinAPI.findClinic(token, params).enqueue(new Callback<ModelResultFindClinic>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultFindClinic> call, @NonNull Response<ModelResultFindClinic> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultFindClinic modelResultFindClinic = response.body();

                    assert modelResultFindClinic != null;
                    if(modelResultFindClinic.getResult().isStatus()) {
                        int size = modelResultFindClinic.getResult().getData().size();
                        if(size > 0) {
                            messageContainer.setVisibility(View.GONE);
                            medicalPersonnelRecyclerView.setVisibility(View.VISIBLE);
                            searchContainer.setVisibility(View.VISIBLE);
                            recordsCountContainer.setVisibility(View.VISIBLE);
                            recordsCount.setText(getString(R.string.medicalPersonnelActivity_records, size));

                            medicalPersonnelRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            medicalPersonnelRecyclerView.setItemAnimator(new DefaultItemAnimator());

                            partnerLists.clear();

                            for(int i = 0; i < size; i++) {
                                String doctorId = modelResultFindClinic.getResult().getData().get(i).getUserId() == null ? "" : modelResultFindClinic.getResult().getData().get(i).getUserId();
                                String doctorName = modelResultFindClinic.getResult().getData().get(i).getFullName() == null ? "" : modelResultFindClinic.getResult().getData().get(i).getFullName();
                                String doctorType = "PAK TOMMI TOLONG KELUARKAN DESC TIPE / DESC SPESIALISASI DOKTER DONG";
                                String doctorPermitt = modelResultFindClinic.getResult().getData().get(i).getNoSIP() == null ? "" : modelResultFindClinic.getResult().getData().get(i).getNoSIP();
                                String doctorPic = modelResultFindClinic.getResult().getData().get(i).getProfilePhoto() == null ? "" : modelResultFindClinic.getResult().getData().get(i).getProfilePhoto();
                                String doctorLatitude = modelResultFindClinic.getResult().getData().get(i).getLatitude() == null ? "" : modelResultFindClinic.getResult().getData().get(i).getLatitude();
                                String doctorLongitude = modelResultFindClinic.getResult().getData().get(i).getLongitude() == null ? "" : modelResultFindClinic.getResult().getData().get(i).getLongitude();
                                String doctorDistance = modelResultFindClinic.getResult().getData().get(i).getDistance() == null ? "" : modelResultFindClinic.getResult().getData().get(i).getDistance();

                                partnerLists.add(new PartnerList(doctorId, doctorName,
                                        doctorType, doctorPermitt, doctorPic, doctorLatitude,
                                        doctorLongitude, doctorDistance));

                            }

                            medicalPersonneldAdapter = new PartnerListAdapter(partnerLists, PartnerListActivity.this);
                            medicalPersonnelRecyclerView.setAdapter(medicalPersonneldAdapter);
                            medicalPersonneldAdapter.notifyDataSetChanged();
                        }
                        else {
                            messageContainer.setVisibility(View.VISIBLE);
                            message.setText(R.string.medicalRecordDetailActivity_noData);
                            medicalPersonnelRecyclerView.setVisibility(View.GONE);
                            searchContainer.setVisibility(View.GONE);
                            recordsCountContainer.setVisibility(View.GONE);
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
            public void onFailure(@NonNull Call<ModelResultFindClinic> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getHealthcareList(String partnerType, String partnerSpecialization, String gender, String bpjs, String latitude, String longitude) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("partner_type_id", partnerType);
        params.put("spesialisasi_id", partnerSpecialization);
        params.put("gender", gender);
        params.put("BPJS_RCV_status", bpjs);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        myCillinAPI.findHealthcare(token, params).enqueue(new Callback<ModelResultFindHealthcare>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultFindHealthcare> call, @NonNull Response<ModelResultFindHealthcare> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultFindHealthcare modelResultFindHealthcare = response.body();

                    assert modelResultFindHealthcare != null;
                    if(modelResultFindHealthcare.getResult().isStatus()) {
                        int size = modelResultFindHealthcare.getResult().getData().size();
                        if(size > 0) {
                            messageContainer.setVisibility(View.GONE);
                            medicalPersonnelRecyclerView.setVisibility(View.VISIBLE);
                            searchContainer.setVisibility(View.VISIBLE);
                            recordsCountContainer.setVisibility(View.VISIBLE);
                            recordsCount.setText(getString(R.string.medicalPersonnelActivity_records, size));

                            medicalPersonnelRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            medicalPersonnelRecyclerView.setItemAnimator(new DefaultItemAnimator());

                            partnerLists.clear();

                            for(int i = 0; i < size; i++) {
                                String doctorId = modelResultFindHealthcare.getResult().getData().get(i).getUserId() == null ? "" : modelResultFindHealthcare.getResult().getData().get(i).getUserId();
                                String doctorName = modelResultFindHealthcare.getResult().getData().get(i).getFullName() == null ? "" : modelResultFindHealthcare.getResult().getData().get(i).getFullName();
                                String doctorType = "PAK TOMMI TOLONG KELUARKAN DESC TIPE / DESC SPESIALISASI DOKTER DONG";
                                String doctorPermitt = modelResultFindHealthcare.getResult().getData().get(i).getNoSIP() == null ? "" : modelResultFindHealthcare.getResult().getData().get(i).getNoSIP();
                                String doctorPic = modelResultFindHealthcare.getResult().getData().get(i).getProfilePhoto() == null ? "" : modelResultFindHealthcare.getResult().getData().get(i).getProfilePhoto();
                                String doctorLatitude = modelResultFindHealthcare.getResult().getData().get(i).getLatitude() == null ? "" : modelResultFindHealthcare.getResult().getData().get(i).getLatitude();
                                String doctorLongitude = modelResultFindHealthcare.getResult().getData().get(i).getLongitude() == null ? "" : modelResultFindHealthcare.getResult().getData().get(i).getLongitude();
                                String doctorDistance = modelResultFindHealthcare.getResult().getData().get(i).getDistance() == null ? "" : modelResultFindHealthcare.getResult().getData().get(i).getDistance();

                                partnerLists.add(new PartnerList(doctorId, doctorName,
                                        doctorType, doctorPermitt, doctorPic, doctorLatitude,
                                        doctorLongitude, doctorDistance));

                            }

                            medicalPersonneldAdapter = new PartnerListAdapter(partnerLists, PartnerListActivity.this);
                            medicalPersonnelRecyclerView.setAdapter(medicalPersonneldAdapter);
                            medicalPersonneldAdapter.notifyDataSetChanged();
                        }
                        else {
                            messageContainer.setVisibility(View.VISIBLE);
                            message.setText(R.string.medicalRecordDetailActivity_noData);
                            medicalPersonnelRecyclerView.setVisibility(View.GONE);
                            searchContainer.setVisibility(View.GONE);
                            recordsCountContainer.setVisibility(View.GONE);
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
            public void onFailure(@NonNull Call<ModelResultFindHealthcare> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getConsultationList(String partnerType, String partnerSpecialization, String gender) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("partner_type_id", partnerType);
        params.put("spesialisasi_id", partnerSpecialization);
        params.put("gender", gender);

        myCillinAPI.findConsultation(token, params).enqueue(new Callback<ModelResultFindConsultation>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultFindConsultation> call, @NonNull Response<ModelResultFindConsultation> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultFindConsultation modelResultFindConsultation = response.body();

                    assert modelResultFindConsultation != null;
                    if(modelResultFindConsultation.getResult().isStatus()) {
                        int size = modelResultFindConsultation.getResult().getData().size();
                        if(size > 0) {
                            messageContainer.setVisibility(View.GONE);
                            medicalPersonnelRecyclerView.setVisibility(View.VISIBLE);
                            searchContainer.setVisibility(View.VISIBLE);
                            recordsCountContainer.setVisibility(View.VISIBLE);
                            recordsCount.setText(getString(R.string.medicalPersonnelActivity_records, size));

                            medicalPersonnelRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            medicalPersonnelRecyclerView.setItemAnimator(new DefaultItemAnimator());

                            partnerLists.clear();

                            for(int i = 0; i < size; i++) {
                                String doctorId = modelResultFindConsultation.getResult().getData().get(i).getUserId() == null ? "" : modelResultFindConsultation.getResult().getData().get(i).getUserId();
                                String doctorName = modelResultFindConsultation.getResult().getData().get(i).getFullName() == null ? "" : modelResultFindConsultation.getResult().getData().get(i).getFullName();
                                String doctorType = "PAK TOMMI TOLONG KELUARKAN DESC TIPE / DESC SPESIALISASI DOKTER DONG";
                                String doctorPermitt = modelResultFindConsultation.getResult().getData().get(i).getNoSIP() == null ? "" : modelResultFindConsultation.getResult().getData().get(i).getNoSIP();
                                String doctorPic = modelResultFindConsultation.getResult().getData().get(i).getProfilePhoto() == null ? "" : modelResultFindConsultation.getResult().getData().get(i).getProfilePhoto();
                                String doctorLatitude = "";
                                String doctorLongitude = "";
                                String doctorDistance = "";

                                partnerLists.add(new PartnerList(doctorId, doctorName,
                                        doctorType, doctorPermitt, doctorPic, doctorLatitude,
                                        doctorLongitude, doctorDistance));

                            }

                            medicalPersonneldAdapter = new PartnerListAdapter(partnerLists, PartnerListActivity.this);
                            medicalPersonnelRecyclerView.setAdapter(medicalPersonneldAdapter);
                            medicalPersonneldAdapter.notifyDataSetChanged();
                        }
                        else {
                            messageContainer.setVisibility(View.VISIBLE);
                            message.setText(R.string.medicalRecordDetailActivity_noData);
                            medicalPersonnelRecyclerView.setVisibility(View.GONE);
                            searchContainer.setVisibility(View.GONE);
                            recordsCountContainer.setVisibility(View.GONE);
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
            public void onFailure(@NonNull Call<ModelResultFindConsultation> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
