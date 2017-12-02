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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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

    public static final String EXTRA_SERVICE_CALLED_FROM = "EXTRA_SERVICE_CALLED_FROM";
    public static final String KEY_BOOK_DOCTOR = "KEY_BOOK_DOCTOR";
    public static final String KEY_MEDICAL_RESERVATION = "KEY_MEDICAL_RESERVATION";
    public static final String KEY_BOOK_HEALTHCARE = "KEY_BOOK_HEALTHCARE";

    public static final String KEY_ID_MENU_ALLERGIST = "KEY_ID_MENU_ALLERGIST";
    public static final String KEY_ID_MENU_CARDIOLOGIST = "KEY_ID_MENU_CARDIOLOGIST";
    public static final String KEY_ID_MENU_DENTIST = "KEY_ID_MENU_DENTIST";
    public static final String KEY_ID_MENU_DERMATOLOGIST = "KEY_ID_MENU_DERMATOLOGIST";
    public static final String KEY_ID_MENU_GENERAL_PRACTITIONER = "KEY_ID_MENU_GENERAL_PRACTITIONER";
    public static final String KEY_ID_MENU_INTERNIST = "KEY_ID_MENU_INTERNIST";
    public static final String KEY_ID_MENU_NEUROLOGIST = "KEY_ID_MENU_NEUROLOGIST";
    public static final String KEY_ID_MENU_OBGYN = "KEY_ID_MENU_OBGYN";
    public static final String KEY_ID_MENU_OPHTHALMOLOGIST = "KEY_ID_MENU_OPHTHALMOLOGIST";
    public static final String KEY_ID_MENU_ORTHOPAEDIST = "KEY_ID_MENU_ORTHOPAEDIST";
    public static final String KEY_ID_MENU_OTOLARYNGOLOGIST = "KEY_ID_MENU_OTOLARYNGOLOGIST";
    public static final String KEY_ID_MENU_PEDIATRICIAN = "KEY_ID_MENU_PEDIATRICIAN";
    public static final String KEY_ID_MENU_PSYCHIATRIST = "KEY_ID_MENU_PSYCHIATRIST";
    public static final String KEY_ID_MENU_PULMONOLOGIST = "KEY_ID_MENU_PULMONOLOGIST";
    public static final String KEY_ID_MENU_RADIOLOGIST = "KEY_ID_MENU_RADIOLOGIST";
    public static final String KEY_ID_MENU_GENERAL_SURGEON = "KEY_ID_MENU_GENERAL_SURGEON";
    public static final String KEY_ID_MENU_UROLOGIST = "KEY_ID_MENU_UROLOGIST";

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
                intent.putExtra(EXTRA_SERVICE_CALLED_FROM, KEY_BOOK_DOCTOR);
                startActivity(intent);
            }
        });

        goToMedicalReservationService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapServiceActivity.class);
                intent.putExtra(EXTRA_SERVICE_CALLED_FROM, KEY_MEDICAL_RESERVATION);
                startActivity(intent);
            }
        });

        goToHomeCareService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapServiceActivity.class);
                intent.putExtra(EXTRA_SERVICE_CALLED_FROM, KEY_BOOK_HEALTHCARE);
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
                RequestOptions requestOptions = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.banner_default)
                        .fitCenter();

                Glide.with(getContext())
                        .load(imageUrls.get(position))
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
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_ALLERGIST, getString(R.string.serviceConsultationActivity_menuAllergist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_CARDIOLOGIST, getString(R.string.serviceConsultationActivity_menuCardiologist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_DENTIST, getString(R.string.serviceConsultationActivity_menuDentist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_DERMATOLOGIST, getString(R.string.serviceConsultationActivity_menuDermatologist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_GENERAL_PRACTITIONER, getString(R.string.serviceConsultationActivity_menuGeneralPractitioner)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_INTERNIST, getString(R.string.serviceConsultationActivity_menuInternist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_NEUROLOGIST, getString(R.string.serviceConsultationActivity_menuNeurologist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_OBGYN, getString(R.string.serviceConsultationActivity_menuObstetricianGynecologist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_OPHTHALMOLOGIST, getString(R.string.serviceConsultationActivity_menuOphthalmologist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_ORTHOPAEDIST, getString(R.string.serviceConsultationActivity_menuOrthopaedist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_OTOLARYNGOLOGIST, getString(R.string.serviceConsultationActivity_menuOtolaryngologist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_PEDIATRICIAN, getString(R.string.serviceConsultationActivity_menuPediatrician)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_PSYCHIATRIST, getString(R.string.serviceConsultationActivity_menuPsychiatrist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_PULMONOLOGIST, getString(R.string.serviceConsultationActivity_menuPulmonologist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_RADIOLOGIST, getString(R.string.serviceConsultationActivity_menuRadiologist)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_GENERAL_SURGEON, getString(R.string.serviceConsultationActivity_menuGeneralSurgeon)));
        consultationMenuLists.add(new ConsultationMenuList(KEY_ID_MENU_UROLOGIST, getString(R.string.serviceConsultationActivity_menuUrologist)));
        consultationMenuAdapter = new ConsultationMenuAdapter(consultationMenuLists, getContext());
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
