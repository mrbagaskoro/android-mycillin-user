package com.mycillin.user.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mycillin.user.R;
import com.mycillin.user.activity.ChatActivity;
import com.mycillin.user.activity.FindFacilityActivity;
import com.mycillin.user.activity.MapServiceActivity;
import com.mycillin.user.activity.PartnerListActivity;
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
    @BindView(R.id.homeFragment_ll_findFacilityService)
    LinearLayout goToFindFacilityService;

    private ArrayList<String> items = new ArrayList<>();
    private ProgressBarHandler progressBarHandler;

    private List<ConsultationMenuList> consultationMenuLists = new ArrayList<>();
    private ConsultationMenuAdapter consultationMenuAdapter;

    private HashMap<Integer, String> relationIdItemsTemp = new HashMap<>();
    private String selectedRelationId = "";

    public static final String EXTRA_SERVICE_CALLED_FROM = "EXTRA_SERVICE_CALLED_FROM";
    public static final String EXTRA_RELATION_ID = "EXTRA_RELATION_ID";
    public static final String KEY_BOOK_DOCTOR = "00";
    public static final String KEY_MEDICAL_RESERVATION = "01";
    public static final String KEY_CONSULTATION = "02";
    public static final String KEY_BOOK_HEALTHCARE = "05";

    public static final String KEY_ID_MENU_PEDIATRICIAN = "KEY_ID_MENU_PEDIATRICIAN";
    public static final String KEY_ID_MENU_GENERAL_SURGEON = "KEY_ID_MENU_GENERAL_SURGEON";
    public static final String KEY_ID_MENU_PULMONOLOGIST = "KEY_ID_MENU_PULMONOLOGIST";
    public static final String KEY_ID_MENU_NEUROLOGIST = "KEY_ID_MENU_NEUROLOGIST";
    public static final String KEY_ID_MENU_CARDIOLOGIST = "KEY_ID_MENU_CARDIOLOGIST";
    public static final String KEY_ID_MENU_OPHTHALMOLOGIST = "KEY_ID_MENU_OPHTHALMOLOGIST";
    public static final String KEY_ID_MENU_DERMATOLOGIST = "KEY_ID_MENU_DERMATOLOGIST";
    public static final String KEY_ID_MENU_INTERNIST = "KEY_ID_MENU_INTERNIST";
    public static final String KEY_ID_MENU_OTOLARYNGOLOGIST = "KEY_ID_MENU_OTOLARYNGOLOGIST";
    public static final String KEY_ID_MENU_UROLOGIST = "KEY_ID_MENU_UROLOGIST";
    public static final String KEY_ID_MENU_OBGYN = "KEY_ID_MENU_OBGYN";
    public static final String KEY_ID_MENU_ALLERGIST = "KEY_ID_MENU_ALLERGIST";
    public static final String KEY_ID_MENU_DENTIST = "KEY_ID_MENU_DENTIST";
    public static final String KEY_ID_MENU_GENERAL_PRACTITIONER = "KEY_ID_MENU_GENERAL_PRACTITIONER";
    public static final String KEY_ID_MENU_ORTHOPAEDIST = "KEY_ID_MENU_ORTHOPAEDIST";
    public static final String KEY_ID_MENU_PSYCHIATRIST = "KEY_ID_MENU_PSYCHIATRIST";
    public static final String KEY_ID_MENU_RADIOLOGIST = "KEY_ID_MENU_RADIOLOGIST";

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
                for(int j = 0; j < relationIdItemsTemp.size(); j++) {
                    if(relationIdItemsTemp.get(j).split(" - ")[1].equals(s)) {
                        selectedRelationId = relationIdItemsTemp.get(j).split(" - ")[0];
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

        goToBookDoctorService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedRelationId.equals("")) {
                    getAccountList(spinnerDialog);
                }
                else {
                    Intent intent = new Intent(getContext(), MapServiceActivity.class);
                    intent.putExtra(EXTRA_SERVICE_CALLED_FROM, KEY_BOOK_DOCTOR);
                    intent.putExtra(EXTRA_RELATION_ID, selectedRelationId);
                    startActivity(intent);
                }
            }
        });

        goToMedicalReservationService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedRelationId.equals("")) {
                    getAccountList(spinnerDialog);
                }
                else {
                    Intent intent = new Intent(getContext(), MapServiceActivity.class);
                    intent.putExtra(EXTRA_SERVICE_CALLED_FROM, KEY_MEDICAL_RESERVATION);
                    intent.putExtra(EXTRA_RELATION_ID, selectedRelationId);
                    startActivity(intent);
                }
            }
        });

        goToHomeCareService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedRelationId.equals("")) {
                    getAccountList(spinnerDialog);
                }
                else {
                    Intent intent = new Intent(getContext(), MapServiceActivity.class);
                    intent.putExtra(EXTRA_SERVICE_CALLED_FROM, KEY_BOOK_HEALTHCARE);
                    intent.putExtra(EXTRA_RELATION_ID, selectedRelationId);
                    startActivity(intent);
                }
            }
        });

        goToConsultationService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedRelationId.equals("")) {
                    getAccountList(spinnerDialog);
                }
                else {
                    mainContainer.setVisibility(View.GONE);
                    consultationContainer.setVisibility(View.VISIBLE);
                    getConsultationMenu();
                }
            }
        });

        goToFindFacilityService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FindFacilityActivity.class);
                startActivity(intent);
            }
        });

        consultationMenuRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), consultationMenuRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ConsultationMenuList list = consultationMenuLists.get(position);
                Intent intent = new Intent(getContext(), PartnerListActivity.class);
                intent.putExtra(EXTRA_SERVICE_CALLED_FROM, KEY_CONSULTATION);
                intent.putExtra(EXTRA_RELATION_ID, selectedRelationId);
                intent.putExtra(PartnerListActivity.EXTRA_PARTNER_TYPE_ID, list.getTypeId());
                intent.putExtra(PartnerListActivity.EXTRA_PARTNER_SPECIALIZATION_ID, list.getSpecializationId());
                intent.putExtra(PartnerListActivity.EXTRA_PARTNER_GENDER, "");
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

    private void getBannerImage() {
        final List<String> imageData = new ArrayList<>();
        final List<String> imageLinks = new ArrayList<>();

        DaoDatabaseHelper daoDatabaseHelper = new DaoDatabaseHelper(getActivity());
        Query<Banner> query = daoDatabaseHelper.getBanner();

        imageData.clear();
        imageLinks.clear();
        for (int i = 0; i < query.list().size(); i++) {
            String baseData = query.list().get(i).getBaseData();
            String imageLink = query.list().get(i).getUrlLink();
            imageData.add(baseData);
            imageLinks.add(imageLink);
        }

        carouselView.setPageCount(query.list().size());
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                RequestOptions requestOptions = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.banner_default)
                        .fitCenter();

                byte[] imageByteArray = Base64.decode(imageData.get(position), Base64.DEFAULT);

                Glide.with(getContext())
                        .load(imageByteArray)
                        .apply(requestOptions)
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

        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_GENERAL_PRACTITIONER, getString(R.string.serviceConsultationActivity_menuGeneralPractitioner), "02", ""));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_PSYCHIATRIST, getString(R.string.serviceConsultationActivity_menuPsychiatrist), "06", ""));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_ALLERGIST, getString(R.string.serviceConsultationActivity_menuAllergist), "03", "11"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_PEDIATRICIAN, getString(R.string.serviceConsultationActivity_menuPediatrician), "03", "00"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_GENERAL_SURGEON, getString(R.string.serviceConsultationActivity_menuGeneralSurgeon), "03", "03"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_DENTIST, getString(R.string.serviceConsultationActivity_menuDentist), "04", ""));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_CARDIOLOGIST, getString(R.string.serviceConsultationActivity_menuCardiologist), "03", "09"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_OBGYN, getString(R.string.serviceConsultationActivity_menuObstetricianGynecologist), "03", "15"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_DERMATOLOGIST, getString(R.string.serviceConsultationActivity_menuDermatologist), "03", "11"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_OPHTHALMOLOGIST, getString(R.string.serviceConsultationActivity_menuOphthalmologist), "03", "10"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_ORTHOPAEDIST, getString(R.string.serviceConsultationActivity_menuOrthopaedist), "03", "16"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_PULMONOLOGIST, getString(R.string.serviceConsultationActivity_menuPulmonologist), "03", "04"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_INTERNIST, getString(R.string.serviceConsultationActivity_menuInternist), "03", "12"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_RADIOLOGIST, getString(R.string.serviceConsultationActivity_menuRadiologist), "03", "17"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_NEUROLOGIST, getString(R.string.serviceConsultationActivity_menuNeurologist), "03", "08"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_OTOLARYNGOLOGIST, getString(R.string.serviceConsultationActivity_menuOtolaryngologist), "03", "13"));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_UROLOGIST, getString(R.string.serviceConsultationActivity_menuUrologist), "03", "14"));

        consultationMenuAdapter = new ConsultationMenuAdapter(consultationMenuLists, getContext());
        consultationMenuRecyclerView.setAdapter(consultationMenuAdapter);
        consultationMenuAdapter.notifyDataSetChanged();
    }
}
