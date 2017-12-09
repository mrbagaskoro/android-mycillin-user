package com.mycillin.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.activity.MedicalRecordDetailActivity;
import com.mycillin.user.adapter.MedicalRecordAdapter;
import com.mycillin.user.list.MedicalRecordList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.accountList.ModelResultAccountList;
import com.mycillin.user.rest.medicalRecordList.ModelResultMedicalRecordList;
import com.mycillin.user.util.DateFormatter;
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
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalRecordFragment extends Fragment {

    @BindView(R.id.medicalRecordFragment_et_dropdown)
    TextView tvDropdown;
    @BindView(R.id.medicalRecordFragment_rv_recyclerView)
    RecyclerView medicalRecordRecyclerView;

    private List<MedicalRecordList> medicalRecordLists = new ArrayList<>();
    private MedicalRecordAdapter medicalRecordAdapter;
    private ArrayList<String> items = new ArrayList<>();
    private ProgressBarHandler progressBarHandler;

    @BindView(R.id.medicalRecordFragment_ll_messageContainer)
    LinearLayout messageContainer;
    @BindView(R.id.medicalRecordFragment_tv_message)
    TextView message;

    private HashMap<Integer, String> relationIdItemsTemp = new HashMap<>();
    private String selectedRelationId;

    public MedicalRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medical_record, container, false);
        ButterKnife.bind(this, rootView);

        progressBarHandler = new ProgressBarHandler(getContext());

        getActivity().setTitle(R.string.nav_medical_record);

        messageContainer.setVisibility(View.VISIBLE);
        message.setText(R.string.medicalRecordFragment_selectFamilyMessage);
        medicalRecordRecyclerView.setVisibility(View.GONE);

        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(), items, getString(R.string.servicesActivity_dropdownTitle), R.style.DialogAnimations_SmileWindow);
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                tvDropdown.setText(s);
                for(int j = 0; j < relationIdItemsTemp.size(); j++) {
                    if(relationIdItemsTemp.get(j).split(" - ")[1].equals(s)) {
                        selectedRelationId = relationIdItemsTemp.get(j).split(" - ")[0];
                        getMedicalRecordList(selectedRelationId);
                    }
                }
            }
        });

        tvDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAccountList(spinnerDialog);
            }
        });

        medicalRecordRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), medicalRecordRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MedicalRecordList list = medicalRecordLists.get(position);

                HashMap<String, String> params = new HashMap<>();
                params.put(MedicalRecordDetailActivity.KEY_PARAM_CREATED_DATE, list.getCreatedDate());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_PARTNER_ID, list.getPartnerId());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_PARTNER_NAME, list.getPartnerName());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_RECORD_ID, list.getRecordId());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_USER_ID, list.getUserId());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_SERVICE_TYPE_DESC, list.getServiceTypeDesc());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_BODY_TEMPERATURE, list.getBodyTemperature());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_BLOOD_SUGAR_LEVEL, list.getBloodSugarLevel());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_CHOLESTEROL_LEVEL, list.getCholesterolLevel());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_BLOOD_PRESS_UPPER, list.getBloodPressUpper());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_BLOOD_PRESS_LOWER, list.getBloodPressLower());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_PATIENT_CONDITION, list.getPatientCondition());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_DIAGNOSE, list.getDiagnose());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_PRESCRIPTION_STATUS, list.getPrescriptionStatus());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_PRESCRIPTION_ID, list.getPrescriptionId());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_PRESCRIPTION_TYPE_DESC, list.getServiceTypeDesc());
                params.put(MedicalRecordDetailActivity.KEY_PARAM_PRESCRIPTION_IMG, list.getPrescriptionImg());

                Intent intent = new Intent(getContext(), MedicalRecordDetailActivity.class);
                intent.putExtra(MedicalRecordDetailActivity.EXTRA_MEDICAL_RECORD_DETAIL, params);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return rootView;
    }

    private void getMedicalRecordList(String relationId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("relation_id", relationId);

        myCillinAPI.getMedicalRecordList(token, params).enqueue(new Callback<ModelResultMedicalRecordList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultMedicalRecordList> call, @NonNull Response<ModelResultMedicalRecordList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultMedicalRecordList modelResultMedicalRecordList = response.body();

                    assert modelResultMedicalRecordList != null;
                    if(modelResultMedicalRecordList.getResult().isStatus()) {
                        int size = modelResultMedicalRecordList.getResult().getData().size();
                        if(size > 0) {
                            messageContainer.setVisibility(View.GONE);
                            medicalRecordRecyclerView.setVisibility(View.VISIBLE);

                            medicalRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            medicalRecordRecyclerView.setItemAnimator(new DefaultItemAnimator());

                            medicalRecordLists.clear();

                            for(int i = 0; i < size; i++) {
                                String medicalRecordCreatedDate = modelResultMedicalRecordList.getResult().getData().get(i).getCreatedDate() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getCreatedDate();
                                String medicalRecordPartnerId = modelResultMedicalRecordList.getResult().getData().get(i).getPartnerId() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getPartnerId();
                                String medicalRecordPartnerName = modelResultMedicalRecordList.getResult().getData().get(i).getPartnerName() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getPartnerName();
                                String medicalRecordRecordId = modelResultMedicalRecordList.getResult().getData().get(i).getRecordId() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getRecordId();
                                String medicalRecordUserId = modelResultMedicalRecordList.getResult().getData().get(i).getUserId() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getUserId();
                                String medicalRecordServiceTypeDesc = modelResultMedicalRecordList.getResult().getData().get(i).getServiceTypeDesc() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getServiceTypeDesc();
                                String medicalRecordBodyTemperature = modelResultMedicalRecordList.getResult().getData().get(i).getBodyTemperature() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getBodyTemperature();
                                String medicalRecordBloodSugarLevel = modelResultMedicalRecordList.getResult().getData().get(i).getBloodSugarLevel() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getBloodSugarLevel();
                                String medicalRecordCholesterolLevel = modelResultMedicalRecordList.getResult().getData().get(i).getCholesterolLevel() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getCholesterolLevel();
                                String medicalRecordBloodPressUpper = modelResultMedicalRecordList.getResult().getData().get(i).getBloodPressUpper() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getBloodPressUpper();
                                String medicalRecordBloodPressLower = modelResultMedicalRecordList.getResult().getData().get(i).getBloodPressLower() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getBloodPressLower();
                                String medicalRecordPatientCondition = modelResultMedicalRecordList.getResult().getData().get(i).getPatientCondition() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getPatientCondition();
                                String medicalRecordDiagnose = modelResultMedicalRecordList.getResult().getData().get(i).getDiagnosa() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getDiagnosa();
                                String medicalRecordPrescriptionStatus = modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionStatus() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionStatus();
                                String medicalRecordPrescriptionId = modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionId() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionId();
                                String medicalRecordPrescriptionTypeDesc = modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionTypeDesc() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionTypeDesc();
                                String medicalRecordPrescriptionImg = modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionImg() == null ? "" : modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionImg();

                                DateFormatter dateFormatter = new DateFormatter(getActivity(), medicalRecordCreatedDate);
                                medicalRecordLists.add(new MedicalRecordList(
                                        dateFormatter.medicalRecordFragmentDateFormat().get(DateFormatter.KEY_DD).toString(),
                                        dateFormatter.medicalRecordFragmentDateFormat().get(DateFormatter.KEY_MM).toString(),
                                        dateFormatter.medicalRecordFragmentDateFormat().get(DateFormatter.KEY_YY).toString(),
                                        medicalRecordCreatedDate, medicalRecordPartnerId, medicalRecordPartnerName,
                                        medicalRecordRecordId, medicalRecordUserId, medicalRecordServiceTypeDesc,
                                        medicalRecordBodyTemperature, medicalRecordBloodSugarLevel, medicalRecordCholesterolLevel,
                                        medicalRecordBloodPressUpper, medicalRecordBloodPressLower, medicalRecordPatientCondition,
                                        medicalRecordDiagnose, medicalRecordPrescriptionStatus, medicalRecordPrescriptionId,
                                        medicalRecordPrescriptionTypeDesc, medicalRecordPrescriptionImg)
                                );
                            }

                            medicalRecordAdapter = new MedicalRecordAdapter(medicalRecordLists, getActivity());
                            medicalRecordRecyclerView.setAdapter(medicalRecordAdapter);
                            medicalRecordAdapter.notifyDataSetChanged();
                        }
                        else {
                            messageContainer.setVisibility(View.VISIBLE);
                            message.setText(R.string.medicalRecordDetailActivity_noData);
                            medicalRecordRecyclerView.setVisibility(View.GONE);
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
                        Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultMedicalRecordList> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void getAccountList(final SpinnerDialog spinnerDialog) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);

        myCillinAPI.getAccountList(token, params).enqueue(new Callback<ModelResultAccountList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultAccountList> call, @NonNull Response<ModelResultAccountList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultAccountList modelResultAccountList = response.body();

                    assert modelResultAccountList != null;
                    if(modelResultAccountList.getResult().isStatus()) {

                        items.clear();
                        relationIdItemsTemp.clear();
                        int size = modelResultAccountList.getResult().getData().size();
                        for(int i = 0; i < size; i++) {
                            String accountPic = "pic_01.jpg";
                            String accountName = modelResultAccountList.getResult().getData().get(i).getFullName();
                            String accountCreatedBy = modelResultAccountList.getResult().getData().get(i).getCreatedBy();
                            String accountCreatedDate = modelResultAccountList.getResult().getData().get(i).getCreatedDate();
                            String accountUpdatedBy = modelResultAccountList.getResult().getData().get(i).getUpdatedBy();
                            String accountUpdatedDate = modelResultAccountList.getResult().getData().get(i).getUpdatedDate();
                            String accountRelationId = modelResultAccountList.getResult().getData().get(i).getRelationId();
                            String accountRelationType = modelResultAccountList.getResult().getData().get(i).getRelationType();
                            String accountUserId = modelResultAccountList.getResult().getData().get(i).getUserId();
                            String accountGender = modelResultAccountList.getResult().getData().get(i).getGender();
                            String accountAddress = modelResultAccountList.getResult().getData().get(i).getAddress();
                            String accountMobileNo = modelResultAccountList.getResult().getData().get(i).getMobileNo();
                            String accountDOB = modelResultAccountList.getResult().getData().get(i).getDob();
                            String accountHeight = modelResultAccountList.getResult().getData().get(i).getHeight();
                            String accountWeight = modelResultAccountList.getResult().getData().get(i).getWeight();
                            String accountBloodType = modelResultAccountList.getResult().getData().get(i).getBloodType();
                            String accountInsuranceId = modelResultAccountList.getResult().getData().get(i).getInsuranceId();

                            items.add(accountName);
                            relationIdItemsTemp.put(i, accountRelationId + " - " + accountName);
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
                        Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultAccountList> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
