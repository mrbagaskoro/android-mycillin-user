package com.mycillin.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mycillin.user.R;
import com.mycillin.user.activity.AccountActivity;
import com.mycillin.user.activity.AccountDetailActivity;
import com.mycillin.user.activity.ServiceBookDoctorActivity;
import com.mycillin.user.activity.ServiceConsultationActivity;
import com.mycillin.user.activity.ServiceMedicalReservationActivity;
import com.mycillin.user.adapter.AccountAdapter;
import com.mycillin.user.database.Banner;
import com.mycillin.user.list.AccountList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.accountList.ModelResultAccountList;
import com.mycillin.user.util.DaoDatabaseHelper;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.RecyclerTouchListener;
import com.mycillin.user.util.SessionManager;
import com.orhanobut.dialogplus.DialogPlus;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.greenrobot.greendao.query.Query;
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

public class HomeFragment extends Fragment {

    @BindView(R.id.homeFragment_et_dropdown)
    TextView tvDropdown;
    @BindView(R.id.homeFragment_cv_carouselView)
    CarouselView carouselView;

    @BindView(R.id.homeFragment_ll_bookADoctorService)
    LinearLayout goToBookDoctorService;
    @BindView(R.id.homeFragment_ll_medicalReservationService)
    LinearLayout goToMedicalReservationService;
    @BindView(R.id.homeFragment_ll_consultationService)
    LinearLayout goToConsultationService;
    /*@BindView(R.id.homeFragment_ll_ambulanceService)
    LinearLayout goToAmbulanceService;
    @BindView(R.id.homeFragment_ll_redeemPrescriptionService)
    LinearLayout goToRedeemPrescriptionService;*/

    private ArrayList<String> items = new ArrayList<>();
    private ProgressBarHandler progressBarHandler;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        progressBarHandler = new ProgressBarHandler(getContext());

        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(), items, getString(R.string.servicesActivity_dropdownTitle), R.style.DialogAnimations_SmileWindow);
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                tvDropdown.setText(s);
            }
        });

        tvDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAccountList(spinnerDialog);
            }
        });

        goToBookDoctorService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ServiceBookDoctorActivity.class);
                startActivity(intent);
            }
        });

        goToMedicalReservationService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ServiceMedicalReservationActivity.class);
                startActivity(intent);
            }
        });

        goToConsultationService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ServiceConsultationActivity.class);
                startActivity(intent);
            }
        });

        getBannerImage();

        return rootView;
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

    private void getBannerImage() {
        final List<String> urls = new ArrayList<>();

        DaoDatabaseHelper daoDatabaseHelper = new DaoDatabaseHelper(getActivity());
        Query<Banner> query = daoDatabaseHelper.getBanner();

        urls.clear();
        for (int i = 0; i < query.list().size(); i++) {
            String imageName = query.list().get(i).getImageName();
            urls.add(imageName);
        }

        carouselView.setPageCount(query.list().size());
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(getContext())
                        .load(urls.get(position))
                        .into(imageView);
            }
        });
    }
}
