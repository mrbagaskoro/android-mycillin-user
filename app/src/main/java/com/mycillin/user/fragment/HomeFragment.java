package com.mycillin.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mycillin.user.R;
import com.mycillin.user.activity.ChatActivity;
import com.mycillin.user.activity.MapServiceActivity;
import com.mycillin.user.adapter.ConsultationMenuAdapter;
import com.mycillin.user.database.Banner;
import com.mycillin.user.list.ConsultationMenuList;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.accountList.ModelResultAccountList;
import com.mycillin.user.util.DaoDatabaseHelper;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.RecyclerTouchListener;
import com.mycillin.user.util.SessionManager;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.thefinestartist.finestwebview.FinestWebView;

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

    @BindView(R.id.homeFragment_ll_mainContainer)
    LinearLayout mainContainer;
    @BindView(R.id.homeFragment_ll_consultationContainer)
    LinearLayout consultationContainer;
    @BindView(R.id.serviceConsultationActivity_ib_backToMainBtn)
    ImageButton backToMainButton;

    @BindView(R.id.serviceConsultationActivity_rv_recyclerView)
    RecyclerView consultationMenuRecyclerView;

    @BindView(R.id.homeFragment_ll_bookADoctorService)
    LinearLayout goToBookDoctorService;
    @BindView(R.id.homeFragment_ll_medicalReservationService)
    LinearLayout goToMedicalReservationService;
    @BindView(R.id.homeFragment_ll_homecareService)
    LinearLayout goToHomeCareService;
    @BindView(R.id.homeFragment_ll_consultationService)
    LinearLayout goToConsultationService;
    /*@BindView(R.id.homeFragment_ll_redeemPrescriptionService)
    LinearLayout goToRedeemPrescriptionService;*/

    private ArrayList<String> items = new ArrayList<>();
    private ProgressBarHandler progressBarHandler;

    private List<ConsultationMenuList> consultationMenuLists = new ArrayList<>();
    private ConsultationMenuAdapter consultationMenuAdapter;

    public static final String KEY_ID_MENU_01 = "KEY_ID_MENU_01";
    public static final String KEY_ID_MENU_02 = "KEY_ID_MENU_02";
    public static final String KEY_ID_MENU_03 = "KEY_ID_MENU_03";
    public static final String KEY_ID_MENU_04 = "KEY_ID_MENU_04";
    public static final String KEY_ID_MENU_05 = "KEY_ID_MENU_05";
    public static final String KEY_ID_MENU_06 = "KEY_ID_MENU_06";
    public static final String KEY_ID_MENU_07 = "KEY_ID_MENU_07";
    public static final String KEY_ID_MENU_08 = "KEY_ID_MENU_08";
    public static final String KEY_ID_MENU_09 = "KEY_ID_MENU_09";

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

        mainContainer.setVisibility(View.VISIBLE);
        consultationContainer.setVisibility(View.GONE);

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
                Intent intent = new Intent(getContext(), MapServiceActivity.class);
                intent.putExtra(MapServiceActivity.EXTRA_MAP_SERVICE, MapServiceActivity.KEY_BOOK_DOCTOR);
                startActivity(intent);
            }
        });

        goToMedicalReservationService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapServiceActivity.class);
                intent.putExtra(MapServiceActivity.EXTRA_MAP_SERVICE, MapServiceActivity.KEY_MEDICAL_RESERVATION);
                startActivity(intent);
            }
        });

        goToConsultationService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainContainer.setVisibility(View.GONE);
                consultationContainer.setVisibility(View.VISIBLE);
                getConsultationMenu();
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
        final List<String> imageUrls = new ArrayList<>();
        final List<String> imageLinks = new ArrayList<>();

        DaoDatabaseHelper daoDatabaseHelper = new DaoDatabaseHelper(getActivity());
        Query<Banner> query = daoDatabaseHelper.getBanner();

        imageUrls.clear();
        imageLinks.clear();
        for (int i = 0; i < query.list().size(); i++) {
            String imageUrl = query.list().get(i).getImageName();
            String imageLink = query.list().get(i).getUrlLink();
            imageUrls.add(imageUrl);
            imageLinks.add(imageLink);
        }

        carouselView.setPageCount(query.list().size());
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(getContext())
                        .load(imageUrls.get(position))
                        .into(imageView);
            }
        });

        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                new FinestWebView.Builder(getContext()).theme(R.style.WebViewTheme)
                        .titleDefault("MyCillin")
                        .webViewBuiltInZoomControls(true)
                        .webViewDisplayZoomControls(true)
                        .dividerHeight(0)
                        .gradientDivider(false)
                        .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit,
                                R.anim.activity_close_enter, R.anim.activity_close_exit)
                        .show(imageLinks.get(position));
            }
        });
    }

    private void getConsultationMenu() {
        consultationMenuRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        consultationMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());

        consultationMenuLists.clear();
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_01, getString(R.string.serviceConsultationActivity_menu1Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_02, getString(R.string.serviceConsultationActivity_menu2Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_03, getString(R.string.serviceConsultationActivity_menu3Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_04, getString(R.string.serviceConsultationActivity_menu4Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_05, getString(R.string.serviceConsultationActivity_menu5Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_06, getString(R.string.serviceConsultationActivity_menu6Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_07, getString(R.string.serviceConsultationActivity_menu7Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_08, getString(R.string.serviceConsultationActivity_menu8Title)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_09, getString(R.string.serviceConsultationActivity_menu9Title)));

        consultationMenuAdapter = new ConsultationMenuAdapter(consultationMenuLists);
        consultationMenuRecyclerView.setAdapter(consultationMenuAdapter);
        consultationMenuAdapter.notifyDataSetChanged();

        consultationMenuRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), consultationMenuRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ConsultationMenuList list = consultationMenuLists.get(position);

                Intent intent = new Intent(getContext(), ChatActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainContainer.setVisibility(View.VISIBLE);
                consultationContainer.setVisibility(View.GONE);
            }
        });
    }
}
