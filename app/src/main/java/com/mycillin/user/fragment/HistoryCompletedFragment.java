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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.activity.HistoryCompletedDetailActivity;
import com.mycillin.user.adapter.CompletedAdapter;
import com.mycillin.user.list.CompletedList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.historyCompleted.ModelResultHistoryCompleted;
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

public class HistoryCompletedFragment extends Fragment {

    @BindView(R.id.historyCompletedFragment_rv_recyclerView)
    RecyclerView completedRecyclerView;
    @BindView(R.id.historyCompletedFragment_ll_messageContainer)
    LinearLayout messageContainer;
    @BindView(R.id.historyCompletedFragment_tv_message)
    TextView message;

    private List<CompletedList> completedListList = new ArrayList<>();
    private CompletedAdapter completedAdapter;

    private ProgressBarHandler progressBarHandler;

    public HistoryCompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_completed, container, false);
        ButterKnife.bind(this, rootView);

        progressBarHandler = new ProgressBarHandler(getContext());

        getCompletedList();

        completedRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), completedRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CompletedList list = completedListList.get(position);

                Intent intent = new Intent(getContext(), HistoryCompletedDetailActivity.class);
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_ORDER_DATE, list.getOrderDate());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_ORDER_TIME, list.getOrderTime());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_BOOKING_ID, list.getBookingId());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_SERVICE_TYPE_ID, list.getServiceTypeId());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_SERVICE_TYPE_DESC, list.getServiceTypeDesc());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PARTNER_ID, list.getPartnerId());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PARTNER_NAME, list.getPartnerName());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PARTNER_TYPE_ID, list.getPartnerTypeId());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PARTNER_TYPE_DESC, list.getPartnerTypeDesc());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PARTNER_SPECIALIZATION_ID, list.getPartnerSpecializationId());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PARTNER_SPECIALIZATION_DESC, list.getPartnerSpecializationDesc());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PARTNER_PIC, list.getPartnerPic());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_MOBILE_NO, list.getMobileNo());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_RATING, list.getRating());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PAYMENT_ID, list.getPaymentId());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PAYMENT_DESC, list.getPaymentDesc());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PROMO_CODE, list.getPromoCode());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PRICE_AMOUNT, list.getPriceAmount());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_CANCEL_BY, list.getCancelBy());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_CANCEL_REASON_BY_USER, list.getCancelReasonByUser());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_CANCEL_REASON_BY_PARTNER, list.getCancelReasonByPartner());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_DIAGNOSE, list.getDiagnose());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_ACTION_TYPE_DESC, list.getActionDesc());
                intent.putExtra(HistoryCompletedDetailActivity.KEY_FLAG_PRESCRIPTION_TYPE_ID, list.getPrescriptionTypeId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return rootView;
    }

    public void getCompletedList() {

        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);

        myCillinAPI.getHistoryCompleted(token, params).enqueue(new Callback<ModelResultHistoryCompleted>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultHistoryCompleted> call, @NonNull Response<ModelResultHistoryCompleted> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultHistoryCompleted modelResultHistoryCompleted = response.body();

                    assert modelResultHistoryCompleted != null;
                    if(modelResultHistoryCompleted.getResult().isStatus()) {
                        int size = modelResultHistoryCompleted.getResult().getData().size();
                        if(size > 0) {
                            if(size == 1 && modelResultHistoryCompleted.getResult().getData().get(0).getBookingId() == null) {
                                messageContainer.setVisibility(View.VISIBLE);
                                message.setText(R.string.medicalRecordDetailActivity_noData);
                                completedRecyclerView.setVisibility(View.GONE);
                            }
                            else {
                                messageContainer.setVisibility(View.GONE);
                                completedRecyclerView.setVisibility(View.VISIBLE);

                                completedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                completedRecyclerView.setItemAnimator(new DefaultItemAnimator());

                                completedListList.clear();

                                for(int i = 0; i < size; i++) {
                                    String orderDate = modelResultHistoryCompleted.getResult().getData().get(i).getOrderDate() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getOrderDate();
                                    String orderTime = modelResultHistoryCompleted.getResult().getData().get(i).getOrderDate() == null ? "" : orderDate.split(" ")[1];
                                    String bookingId = modelResultHistoryCompleted.getResult().getData().get(i).getBookingId() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getBookingId();
                                    String serviceTypeId = modelResultHistoryCompleted.getResult().getData().get(i).getServiceTypeId() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getServiceTypeId();
                                    String serviceTypeDesc = modelResultHistoryCompleted.getResult().getData().get(i).getServiceTypeDesc() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getServiceTypeDesc();
                                    String partnerId = modelResultHistoryCompleted.getResult().getData().get(i).getPartnerSelected() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getPartnerSelected();
                                    String partnerName = modelResultHistoryCompleted.getResult().getData().get(i).getPartnerName() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getPartnerName();
                                    String partnerTypeId = modelResultHistoryCompleted.getResult().getData().get(i).getPartnerTypeId() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getPartnerTypeId();
                                    String partnerTypeDesc = "null";
                                    String partnerSpecializationId = modelResultHistoryCompleted.getResult().getData().get(i).getSpesialisasiId() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getSpesialisasiId();
                                    String partnerSpecializationDesc = "null";
                                    String partnerPic = modelResultHistoryCompleted.getResult().getData().get(i).getProfilePhoto() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getProfilePhoto();
                                    String mobileNo = modelResultHistoryCompleted.getResult().getData().get(i).getMobileNo() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getMobileNo();
                                    String rating = modelResultHistoryCompleted.getResult().getData().get(i).getRating() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getRating();
                                    String paymentId = modelResultHistoryCompleted.getResult().getData().get(i).getPymtMethodeId() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getPymtMethodeId();
                                    String paymentDesc = modelResultHistoryCompleted.getResult().getData().get(i).getPymtMethodeDesc() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getPymtMethodeDesc();
                                    String promoCode = modelResultHistoryCompleted.getResult().getData().get(i).getPromoCode() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getPromoCode();
                                    String priceAmount = modelResultHistoryCompleted.getResult().getData().get(i).getPriceAmount() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getPriceAmount();
                                    String cancelBy = modelResultHistoryCompleted.getResult().getData().get(i).getCancelBy() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getCancelBy();
                                    String cancelReasonByUser = modelResultHistoryCompleted.getResult().getData().get(i).getCancelReasonByUser() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getCancelReasonByUser();
                                    String cancelReasonByPartner = modelResultHistoryCompleted.getResult().getData().get(i).getCancelReasonByPartner() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getCancelReasonByPartner();
                                    String diagnose = modelResultHistoryCompleted.getResult().getData().get(i).getDiagnosa() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getDiagnosa();
                                    String actionDesc = modelResultHistoryCompleted.getResult().getData().get(i).getActionTypeDesc() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getActionTypeDesc();
                                    String prescriptionTypeId = modelResultHistoryCompleted.getResult().getData().get(i).getPrescriptionTypeId() == null ? "" : modelResultHistoryCompleted.getResult().getData().get(i).getPrescriptionTypeId();

                                    completedListList.add(new CompletedList(orderDate.split(" ")[0],
                                            orderTime, bookingId, serviceTypeId, serviceTypeDesc,
                                            partnerId, partnerName, partnerTypeId, partnerTypeDesc,
                                            partnerSpecializationId, partnerSpecializationDesc, partnerPic,
                                            mobileNo, rating, paymentId, paymentDesc, promoCode, priceAmount,
                                            cancelBy, cancelReasonByUser, cancelReasonByPartner, diagnose,
                                            actionDesc, prescriptionTypeId));
                                }

                                completedAdapter = new CompletedAdapter(completedListList, HistoryCompletedFragment.this);
                                completedRecyclerView.setAdapter(completedAdapter);
                                completedAdapter.notifyDataSetChanged();
                            }
                        }
                        else {
                            messageContainer.setVisibility(View.VISIBLE);
                            message.setText(R.string.medicalRecordDetailActivity_noData);
                            completedRecyclerView.setVisibility(View.GONE);
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
            public void onFailure(@NonNull Call<ModelResultHistoryCompleted> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
