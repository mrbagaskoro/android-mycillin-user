package com.mycillin.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mycillin.user.R;
import com.mycillin.user.adapter.CancelReasonAdapter;
import com.mycillin.user.list.CancelReasonList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.cancelReasonList.ModelResultCancelReasonList;
import com.mycillin.user.rest.requestCancel.ModelResultRequestCancel;
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

public class CancelActivity extends AppCompatActivity {

    @BindView(R.id.cancelActivity_rv_recyclertView)
    RecyclerView cancelReasonRecyclerView;

    @BindView(R.id.cancelActivity_toolbar)
    Toolbar toolbar;

    private List<CancelReasonList> cancelReasonList = new ArrayList<>();
    private CancelReasonAdapter cancelReasonAdapter;

    private ProgressBarHandler progressBarHandler;

    public static final String EXTRA_KEY_BOOKING_ID = "EXTRA_KEY_BOOKING_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.cancelActivity_cancelTitle);

        progressBarHandler = new ProgressBarHandler(this);

        getCancelReason();

        cancelReasonRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), cancelReasonRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final CancelReasonList list = cancelReasonList.get(position);

                new AlertDialog.Builder(CancelActivity.this)
                        .setTitle(R.string.cancelActivity_cancelDialogTitle)
                        .setMessage(R.string.cancelActivity_cancelDialogMessage)
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton(getString(R.string.cancelActivity_cancelDialogTitle), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelOrder(getIntent().getStringExtra(EXTRA_KEY_BOOKING_ID), list.getCancelReasonId());
                            }
                        })
                        .setNegativeButton(R.string.cancelActivity_noDialogTitle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getCancelReason() {
        progressBarHandler.show();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();
        myCillinAPI.getCancelReasonList().enqueue(new Callback<ModelResultCancelReasonList>() {
            @Override
            public void onResponse(Call<ModelResultCancelReasonList> call, Response<ModelResultCancelReasonList> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultCancelReasonList modelResultCancelReasonList = response.body();

                    assert modelResultCancelReasonList != null;
                    if(modelResultCancelReasonList.getResult().isStatus()) {
                        cancelReasonRecyclerView.setLayoutManager(new LinearLayoutManager(CancelActivity.this));
                        cancelReasonRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        cancelReasonList.clear();

                        int size = modelResultCancelReasonList.getResult().getData().size();
                        for(int i = 0; i < size; i++) {
                            String cancelReasonId = modelResultCancelReasonList.getResult().getData().get(i).getCancelReasonId();
                            String cancelReasonDesc = modelResultCancelReasonList.getResult().getData().get(i).getCancelReasonDesc();

                            cancelReasonList.add(new CancelReasonList(cancelReasonId, cancelReasonDesc));
                        }

                        cancelReasonAdapter = new CancelReasonAdapter(cancelReasonList);
                        cancelReasonRecyclerView.setAdapter(cancelReasonAdapter);
                        cancelReasonAdapter.notifyDataSetChanged();
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
            public void onFailure(Call<ModelResultCancelReasonList> call, Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelOrder(String bookingId, String cancelReasonId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("booking_id", bookingId);
        params.put("cancel_reason_id", cancelReasonId);

        Log.d("###", "cancelOrder: " + params);

        myCillinAPI.requestCancel(token, params).enqueue(new Callback<ModelResultRequestCancel>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultRequestCancel> call, @NonNull Response<ModelResultRequestCancel> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultRequestCancel modelResultRequestCancel = response.body();

                    assert modelResultRequestCancel != null;
                    if(modelResultRequestCancel.getResult().isStatus()) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), modelResultRequestCancel.getResult().getMessage(), Snackbar.LENGTH_SHORT)
                                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);

                                        Intent intent = new Intent(CancelActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                    else {
                        Snackbar.make(getWindow().getDecorView().getRootView(), modelResultRequestCancel.getResult().getMessage(), Snackbar.LENGTH_LONG).show();
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
            public void onFailure(@NonNull Call<ModelResultRequestCancel> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
