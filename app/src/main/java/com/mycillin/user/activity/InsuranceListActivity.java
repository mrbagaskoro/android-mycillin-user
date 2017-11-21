package com.mycillin.user.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mycillin.user.R;
import com.mycillin.user.adapter.InsuranceAdapter;
import com.mycillin.user.adapter.MedicalRecordAdapter;
import com.mycillin.user.list.InsuranceList;
import com.mycillin.user.list.MedicalRecordList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.insuranceList.ModelResultInsuranceList;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsuranceListActivity extends AppCompatActivity {

    @BindView(R.id.insuranceListActivity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.insuranceListActivity_rv_recyclerView)
    RecyclerView insuranceRecyclerView;
    @BindView(R.id.insuranceListActivity_ll_messageContainer)
    LinearLayout messageContainer;
    @BindView(R.id.insuranceListActivity_tv_message)
    TextView message;

    @BindView(R.id.insuranceListActivity_fab_addFAB)
    FloatingActionButton addBtn;

    private List<InsuranceList> insuranceLists = new ArrayList<>();
    private InsuranceAdapter insuranceAdapter;

    private ProgressBarHandler progressBarHandler;

    public static final String KEY_PARAM_ACCOUNT_RELATION_ID = "KEY_PARAM_ACCOUNT_RELATION_ID";
    public static final String KEY_PARAM_ACCOUNT_USER_ID = "KEY_PARAM_ACCOUNT_USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_list);
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.insuranceActivity_title));

        progressBarHandler = new ProgressBarHandler(this);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsuranceListActivity.this, InsuranceActivity.class);
                intent.putExtra(KEY_PARAM_ACCOUNT_RELATION_ID, getIntent().getStringExtra(KEY_PARAM_ACCOUNT_RELATION_ID));
                startActivity(intent);
            }
        });

        getInsuranceList(getIntent().getStringExtra(KEY_PARAM_ACCOUNT_RELATION_ID));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInsuranceList(getIntent().getStringExtra(KEY_PARAM_ACCOUNT_RELATION_ID));
    }

    public void getInsuranceList(String relationId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("relation_id", relationId);

        myCillinAPI.getInsuranceList(token, params).enqueue(new Callback<ModelResultInsuranceList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultInsuranceList> call, @NonNull Response<ModelResultInsuranceList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultInsuranceList modelResultInsuranceList = response.body();

                    assert modelResultInsuranceList != null;
                    if(modelResultInsuranceList.getResult().isStatus()) {
                        int size = modelResultInsuranceList.getResult().getData().size();
                        if(size > 0) {
                            messageContainer.setVisibility(View.GONE);
                            insuranceRecyclerView.setVisibility(View.VISIBLE);

                            insuranceRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            insuranceRecyclerView.setItemAnimator(new DefaultItemAnimator());

                            insuranceLists.clear();

                            for(int i = 0; i < size; i++) {
                                String insuranceId = modelResultInsuranceList.getResult().getData().get(i).getMemberInsuranceId();
                                String userId = modelResultInsuranceList.getResult().getData().get(i).getUserId();
                                String relationId = modelResultInsuranceList.getResult().getData().get(i).getRelationId();
                                String policyNo = modelResultInsuranceList.getResult().getData().get(i).getPolicyNo();
                                String insuranceProvicerId = modelResultInsuranceList.getResult().getData().get(i).getInsuranceProviderId();
                                String insuranceProviderDesc = modelResultInsuranceList.getResult().getData().get(i).getInsuranceProviderDesc();
                                String insuredName = modelResultInsuranceList.getResult().getData().get(i).getInsuredName();
                                String insuranceHolderName = modelResultInsuranceList.getResult().getData().get(i).getInsuranceHolder();
                                String insuranceImageUrl = modelResultInsuranceList.getResult().getData().get(i).getInsuranceCardImage();

                                insuranceLists.add(new InsuranceList(insuranceId, userId, relationId,
                                        policyNo, insuranceProvicerId, insuranceProviderDesc,
                                        insuredName, insuranceHolderName, insuranceImageUrl));

                            }

                            insuranceAdapter = new InsuranceAdapter(getApplicationContext(), insuranceLists);
                            insuranceRecyclerView.setAdapter(insuranceAdapter);
                            insuranceAdapter.notifyDataSetChanged();
                        }
                        else {
                            messageContainer.setVisibility(View.VISIBLE);
                            message.setText(R.string.medicalRecordDetailActivity_noData);
                            insuranceRecyclerView.setVisibility(View.GONE);
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
            public void onFailure(@NonNull Call<ModelResultInsuranceList> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
