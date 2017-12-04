package com.mycillin.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.activity.HistoryInProgressDetailActivity;
import com.mycillin.user.adapter.InProgressAdapter;
import com.mycillin.user.list.InProgressList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.historyOnProgress.ModelResultHistoryOnProgress;
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

public class HistoryInProgressFragment extends Fragment {

    @BindView(R.id.historyInProgressFragment_sr_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.historyInProgressFragment_rv_recyclerView)
    RecyclerView inProgressRecyclerView;
    @BindView(R.id.historyInProgressFragment_ll_messageContainer)
    LinearLayout messageContainer;
    @BindView(R.id.historyInProgressFragment_tv_message)
    TextView message;

    private List<InProgressList> inProgressLists = new ArrayList<>();
    private InProgressAdapter inProgressAdapter;

    private ProgressBarHandler progressBarHandler;

    public HistoryInProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_in_progress, container, false);
        ButterKnife.bind(this, rootView);

        progressBarHandler = new ProgressBarHandler(getContext());

        getInProgressList();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInProgressList();
            }
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        inProgressRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), inProgressRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                InProgressList list = inProgressLists.get(position);

                Intent intent = new Intent(getContext(), HistoryInProgressDetailActivity.class);
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_ORDER_DATE, list.getOrderDate());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_ORDER_TIME, list.getOrderTime());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_BOOKING_ID, list.getBookingId());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_SERVICE_TYPE_ID, list.getServiceTypeId());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_SERVICE_TYPE_DESC, list.getServiceTypeDesc());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_PARTNER_ID, list.getPartnerId());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_PARTNER_NAME, list.getPartnerName());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_PARTNER_TYPE_ID, list.getPartnerTypeId());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_PARTNER_TYPE_DESC, list.getPartnerTypeDesc());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_PARTNER_SPECIALIZATION_ID, list.getPartnerSpecializationId());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_PARTNER_SPECIALIZATION_DESC, list.getPartnerSpecializationDesc());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_PARTNER_PIC, list.getPartnerPic());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_MOBILE_NO, list.getMobileNo());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_RATING, list.getRating());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_PAYMENT_ID, list.getPaymentId());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_PAYMENT_DESC, list.getPaymentDesc());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_PROMO_CODE, list.getPromoCode());
                intent.putExtra(HistoryInProgressDetailActivity.KEY_FLAG_PRICE_AMOUNT, list.getPriceAmount());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return rootView;
    }

    public void getInProgressList() {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);

        myCillinAPI.getHistoryOnProgress(token, params).enqueue(new Callback<ModelResultHistoryOnProgress>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultHistoryOnProgress> call, @NonNull Response<ModelResultHistoryOnProgress> response) {
                progressBarHandler.hide();
                swipeRefreshLayout.setRefreshing(false);

                if(response.isSuccessful()) {
                    ModelResultHistoryOnProgress modelResultHistoryOnProgress = response.body();

                    assert modelResultHistoryOnProgress != null;
                    if(modelResultHistoryOnProgress.getResult().isStatus()) {
                        int size = modelResultHistoryOnProgress.getResult().getData().size();
                        if(size > 0) {
                            if(size == 1 && modelResultHistoryOnProgress.getResult().getData().get(0).getBookingId() == null) {
                                messageContainer.setVisibility(View.VISIBLE);
                                message.setText(R.string.medicalRecordDetailActivity_noData);
                                inProgressRecyclerView.setVisibility(View.GONE);
                            }
                            else {
                                messageContainer.setVisibility(View.GONE);
                                inProgressRecyclerView.setVisibility(View.VISIBLE);

                                inProgressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                inProgressRecyclerView.setItemAnimator(new DefaultItemAnimator());

                                inProgressLists.clear();

                                for(int i = 0; i < size; i++) {
                                    String orderDate = modelResultHistoryOnProgress.getResult().getData().get(i).getOrderDate() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getOrderDate();
                                    String orderTime = modelResultHistoryOnProgress.getResult().getData().get(i).getOrderDate() == null ? "" : orderDate.split(" ")[1];
                                    String bookingId = modelResultHistoryOnProgress.getResult().getData().get(i).getBookingId() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getBookingId();
                                    String serviceTypeId = modelResultHistoryOnProgress.getResult().getData().get(i).getServiceTypeId() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getServiceTypeId();
                                    String serviceTypeDesc = modelResultHistoryOnProgress.getResult().getData().get(i).getServiceTypeDesc() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getServiceTypeDesc();
                                    String partnerId = modelResultHistoryOnProgress.getResult().getData().get(i).getPartnerSelected() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getPartnerSelected();
                                    String partnerName = modelResultHistoryOnProgress.getResult().getData().get(i).getPartnerName() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getPartnerName();
                                    String partnerTypeId = modelResultHistoryOnProgress.getResult().getData().get(i).getPartnerTypeId() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getPartnerTypeId();
                                    String partnerTypeDesc = "null";
                                    String partnerSpecializationId = modelResultHistoryOnProgress.getResult().getData().get(i).getSpesialisasiId() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getSpesialisasiId();
                                    String partnerSpecializationDesc = "null";
                                    String partnerPic = modelResultHistoryOnProgress.getResult().getData().get(i).getProfilePhoto() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getProfilePhoto();
                                    String mobileNo = modelResultHistoryOnProgress.getResult().getData().get(i).getMobileNo() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getMobileNo();
                                    String rating = modelResultHistoryOnProgress.getResult().getData().get(i).getRating() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getRating();
                                    String paymentId = modelResultHistoryOnProgress.getResult().getData().get(i).getPymtMethodeId() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getPymtMethodeId();
                                    String paymentDesc = modelResultHistoryOnProgress.getResult().getData().get(i).getPymtMethodeDesc() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getPymtMethodeDesc();
                                    String promoCode = modelResultHistoryOnProgress.getResult().getData().get(i).getPromoCode() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getPromoCode();
                                    String priceAmount = modelResultHistoryOnProgress.getResult().getData().get(i).getPriceAmount() == null ? "" : modelResultHistoryOnProgress.getResult().getData().get(i).getPriceAmount();

                                    inProgressLists.add(new InProgressList(orderDate.split(" ")[0],
                                            orderTime, bookingId, serviceTypeId, serviceTypeDesc,
                                            partnerId, partnerName, partnerTypeId, partnerTypeDesc,
                                            partnerSpecializationId, partnerSpecializationDesc, partnerPic,
                                            mobileNo, rating, paymentId, paymentDesc, promoCode, priceAmount));
                                }

                                inProgressAdapter = new InProgressAdapter(inProgressLists, HistoryInProgressFragment.this);
                                inProgressRecyclerView.setAdapter(inProgressAdapter);
                                inProgressAdapter.notifyDataSetChanged();
                            }
                        }
                        else {
                            messageContainer.setVisibility(View.VISIBLE);
                            message.setText(R.string.medicalRecordDetailActivity_noData);
                            inProgressRecyclerView.setVisibility(View.GONE);
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
            public void onFailure(@NonNull Call<ModelResultHistoryOnProgress> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
