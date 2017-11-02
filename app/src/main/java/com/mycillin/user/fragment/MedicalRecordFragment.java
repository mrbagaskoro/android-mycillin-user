package com.mycillin.user.fragment;

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
import com.mycillin.user.adapter.MedicalRecordAdapter;
import com.mycillin.user.list.MedicalRecordList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.accountList.ModelResultAccountList;
import com.mycillin.user.rest.medicalRecordList.ModelResultMedicalRecordList;
import com.mycillin.user.util.ProgressBarHandler;
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

        Log.d("###", "getMedicalRecordList: " + token);

        myCillinAPI.getMedicalRecordList(token, params).enqueue(new Callback<ModelResultMedicalRecordList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultMedicalRecordList> call, @NonNull Response<ModelResultMedicalRecordList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultMedicalRecordList modelResultMedicalRecordList = response.body();

                    assert modelResultMedicalRecordList != null;
                    if(modelResultMedicalRecordList.getResult().isStatus()) {
                        messageContainer.setVisibility(View.GONE);
                        medicalRecordRecyclerView.setVisibility(View.VISIBLE);

                        medicalRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        medicalRecordRecyclerView.setItemAnimator(new DefaultItemAnimator());

                        medicalRecordLists.clear();

                        int size = modelResultMedicalRecordList.getResult().getData().size();
                        for(int i = 0; i < size; i++) {
                            String medicalRecordCreatedBy = modelResultMedicalRecordList.getResult().getData().get(i).getCreatedBy();
                            String medicalRecordCreatedDate = modelResultMedicalRecordList.getResult().getData().get(i).getCreatedDate();
                            String medicalRecordUpdatedBy = modelResultMedicalRecordList.getResult().getData().get(i).getUpdatedBy();
                            String medicalRecordUpdatedDate = modelResultMedicalRecordList.getResult().getData().get(i).getUpdatedDate();
                            String medicalRecordBookingId = modelResultMedicalRecordList.getResult().getData().get(i).getBookingId();
                            String medicalRecordRecordId = modelResultMedicalRecordList.getResult().getData().get(i).getRecordId();
                            String medicalRecordUserId = modelResultMedicalRecordList.getResult().getData().get(i).getUserId();
                            String medicalRecordRelationId = modelResultMedicalRecordList.getResult().getData().get(i).getRelationId();
                            String medicalRecordPartnerId = modelResultMedicalRecordList.getResult().getData().get(i).getPartnerId();
                            String medicalRecordServiceTypeId = modelResultMedicalRecordList.getResult().getData().get(i).getServiceTypeId();
                            String medicalRecordBodyTemperature = modelResultMedicalRecordList.getResult().getData().get(i).getBodyTemperature();
                            String medicalRecordBloodSugarLevel = modelResultMedicalRecordList.getResult().getData().get(i).getBloodSugarLevel();
                            String medicalRecordCholesterolLevel = modelResultMedicalRecordList.getResult().getData().get(i).getCholesterolLevel();
                            String medicalRecordBloodPressUpper = modelResultMedicalRecordList.getResult().getData().get(i).getBloodPressUpper();
                            String medicalRecordBloodPressLower = modelResultMedicalRecordList.getResult().getData().get(i).getBloodPressLower();
                            String medicalRecordPatientCondition = modelResultMedicalRecordList.getResult().getData().get(i).getPatientCondition();
                            String medicalRecordDiagnose = modelResultMedicalRecordList.getResult().getData().get(i).getDiagnosa();
                            String medicalRecordPrescriptionStatus = modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionStatus();
                            String medicalRecordPrescriptionId = modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionId();
                            String medicalRecordPrescriptionTypeId = modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionTypeId();
                            String medicalRecordPrescriptionPhoto = modelResultMedicalRecordList.getResult().getData().get(i).getPrescriptionPhoto();

                            medicalRecordLists.add(new MedicalRecordList("10", "JAN", "2017", medicalRecordPartnerId));
                        }

                        medicalRecordAdapter = new MedicalRecordAdapter(medicalRecordLists, getActivity());
                        medicalRecordRecyclerView.setAdapter(medicalRecordAdapter);
                        medicalRecordAdapter.notifyDataSetChanged();

                        /*accountRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), accountRecyclerView, new RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                AccountList list = accountLists.get(position);

                                HashMap<String, String> params = new HashMap<>();
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_NAME, list.getAccountName());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_CREATED_BY, list.getAccountCreatedBy());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_CREATED_DATE, list.getAccountCreatedDate());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_UPDATED_BY, list.getAccountUpdatedBy());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_UPDATED_DATE, list.getAccountUpdatedDate());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_RELATION_ID, list.getAccountRelationId());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_RELATION_TYPE, list.getAccountRelationType());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_USER_ID, list.getAccountUserId());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_GENDER, list.getAccountGender() == null ? "" : list.getAccountGender());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_ADDRESS, list.getAccountAddress());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_MOBILE_NO, list.getAccountMobileNo());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_DOB, list.getAccountDOB());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_HEIGHT, list.getAccountHeight());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_WEIGHT, list.getAccountWeight());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_BLOOD_TYPE, list.getAccountBloodType());
                                params.put(AccountDetailActivity.KEY_PARAM_ACCOUNT_INSURANCE_ID, list.getAccountInsuranceId());

                                Intent intent = new Intent(AccountActivity.this, AccountDetailActivity.class);
                                intent.putExtra(AccountDetailActivity.EXTRA_ACCOUNT_DETAIL, params);
                                intent.putExtra(AccountDetailActivity.EXTRA_ACCOUNT_DETAIL_IS_NEW, false);
                                startActivity(intent);
                                dialogPlus.dismiss();
                            }

                            @Override
                            public void onLongClick(View view, int position) {

                            }
                        }));*/
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
