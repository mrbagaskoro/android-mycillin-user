package com.mycillin.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mycillin.user.R;
import com.mycillin.user.fragment.HomeFragment;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.firebaseGet.ModelResultFirebaseGet;
import com.mycillin.user.rest.partnerDetailGet.ModelResultPartnerDetailGet;
import com.mycillin.user.rest.paymentMethodeList.ModelResultPaymentMethodeList;
import com.mycillin.user.rest.priceGet.ModelResultPriceGet;
import com.mycillin.user.rest.promoCheck.ModelResultPromoCheck;
import com.mycillin.user.rest.requestConsultation.ModelResultRequestConsultation;
import com.mycillin.user.rest.requestTransaction.ModelResultRequestTransaction;
import com.mycillin.user.util.CurrencyTextWatcherTextView;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartnerDetailActivity extends AppCompatActivity {

    @BindView(R.id.partnerDetailActivity_iv_userAvatar)
    CircleImageView doctorAvatar;
    @BindView(R.id.partnerDetailActivity_tv_doctorName)
    TextView doctorName;
    @BindView(R.id.partnerDetailActivity_tv_doctorType)
    TextView doctorType;
    @BindView(R.id.partnerDetailActivity_rb_doctorRating)
    RatingBar doctorRating;
    @BindView(R.id.partnerDetailActivity_ll_sippContainer)
    LinearLayout SIPPContainer;
    @BindView(R.id.partnerDetailActivity_tv_doctorPermitt)
    TextView doctorSIPPPermitt;
    @BindView(R.id.partnerDetailActivity_ll_strContainer)
    LinearLayout STRContainer;
    @BindView(R.id.partnerDetailActivity_tv_doctorPermitt2)
    TextView doctorSTRPermitt;
    @BindView(R.id.partnerDetailActivity_tv_doctorDescription)
    TextView doctorDesc;
    @BindView(R.id.partnerDetailActivity_tv_doctorYearOfWorks)
    TextView doctorYearOfWork;
    @BindView(R.id.partnerDetailActivity_tv_doctorWorkplace)
    TextView doctorWorkplace;
    @BindView(R.id.partnerDetailActivity_tv_doctorWorkAddress)
    TextView doctorWorkAddress;
    @BindView(R.id.partnerDetailActivity_tv_doctorWorkArea)
    TextView doctorWorkArea;
    @BindView(R.id.partnerDetailActivity_et_promoCodeEdtxt)
    EditText promoCodeEdtxt;
    @BindView(R.id.partnerDetailActivity_bt_checkPromoCodeBtn)
    Button checkPromoCodeBtn;
    @BindView(R.id.partnerDetailActivity_ib_clearPromoCodeBtn)
    ImageButton clearPromoCodeBtn;
    @BindView(R.id.partnerDetailActivity_tv_doctorFee)
    TextView doctorFee;
    @BindView(R.id.partnerDetailActivity_v_paymentSeparator)
    View paymentSeparator;
    @BindView(R.id.partnerDetailActivity_ll_paymentContainer)
    LinearLayout paymentContainer;
    @BindView(R.id.partnerDetailActivity_et_paymentDropdown)
    EditText paymentDropdown;
    @BindView(R.id.partnerDetailActivity_bt_requestBtn)
    Button requestBtn;

    @BindView(R.id.partnerDetailActivity_toolbar)
    Toolbar toolbar;

    public static String KEY_FLAG_PARTNER_ID = "KEY_FLAG_PARTNER_ID";

    private ArrayList<String> items = new ArrayList<>();
    private HashMap<Integer, String> paymentMethodIdItemsTemp = new HashMap<>();

    private ProgressBarHandler progressBarHandler;

    String selectedPartnerPicURL = "";
    String selectedPartnerSIPPURL = "";
    String selectedPartnerSTRURL = "";
    String selectedPartnerId = "";
    String selectedPartnerTypeId = "";
    String selectedPartnerSpecializationId = "";
    String selectedPaymentMethodId = "";
    String doctorFirebaseToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_detail);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.partnerDetailActivity_title);

        progressBarHandler = new ProgressBarHandler(this);

        String partnerId = getIntent().getStringExtra(KEY_FLAG_PARTNER_ID);
        getPartnerDetail(partnerId);

        doctorFee.setText("-");

        doctorAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedPartnerPicURL.equals("")) {
                    Intent intent = new Intent(PartnerDetailActivity.this, ViewImageActivity.class);
                    intent.putExtra(ViewImageActivity.EXTRA_IMAGE_BASE_DATA, selectedPartnerPicURL);
                    startActivity(intent);
                }
            }
        });

        SIPPContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedPartnerSIPPURL.equals("")) {
                    Intent intent = new Intent(PartnerDetailActivity.this, ViewImageActivity.class);
                    intent.putExtra(ViewImageActivity.EXTRA_IMAGE_BASE_DATA, selectedPartnerSIPPURL);
                    startActivity(intent);
                } else {
                    String message = getString(R.string.partnerDetailActivity_SIPPmessage);
                    Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        STRContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedPartnerSTRURL.equals("")) {
                    Intent intent = new Intent(PartnerDetailActivity.this, ViewImageActivity.class);
                    intent.putExtra(ViewImageActivity.EXTRA_IMAGE_BASE_DATA, selectedPartnerSTRURL);
                    startActivity(intent);
                } else {
                    String message = getString(R.string.partnerDetailActivity_STRmessage);
                    Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        final SpinnerDialog spinnerDialog = new SpinnerDialog(PartnerDetailActivity.this, items, getString(R.string.partnerDetailActivity_paymentMethodDropdownTitle), R.style.DialogAnimations_SmileWindow);
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                paymentDropdown.setText(s);
                for (int j = 0; j < paymentMethodIdItemsTemp.size(); j++) {
                    if(paymentMethodIdItemsTemp.get(j) != null) {
                        if (paymentMethodIdItemsTemp.get(j).split(" - ")[1].equals(s)) {
                            selectedPaymentMethodId = paymentMethodIdItemsTemp.get(j).split(" - ")[0];

                            getPrice(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM), selectedPaymentMethodId,
                                    selectedPartnerTypeId, selectedPartnerSpecializationId);
                        }
                    }
                }
            }
        });

        paymentDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPaymentMethod(spinnerDialog);
            }
        });

        if (getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_CONSULTATION)) {
            paymentSeparator.setVisibility(View.INVISIBLE);
            paymentContainer.setVisibility(View.INVISIBLE);
        } else {
            paymentSeparator.setVisibility(View.VISIBLE);
            paymentContainer.setVisibility(View.VISIBLE);
        }

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String promoCode = promoCodeEdtxt.getText().toString().trim();

                if (getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_CONSULTATION)) {
                    new AlertDialog.Builder(PartnerDetailActivity.this)
                            .setTitle(R.string.partnerDetailActivity_consultDialogTitle)
                            .setMessage(R.string.partnerDetailActivity_consultDialogMessage)
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton(getString(R.string.partnerDetailActivity_consultDialogTitle), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    doConsult(getIntent().getStringExtra(HomeFragment.EXTRA_RELATION_ID),
                                            selectedPartnerId, selectedPartnerTypeId, selectedPartnerSpecializationId,
                                            promoCode);
                                }
                            })
                            .setNegativeButton(R.string.accountActivity_cancelTitle, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    if (selectedPaymentMethodId.equals("")) {
                        String message = getString(R.string.partnerDetailActivity_paymentValidationMessage);
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } else {
                        new AlertDialog.Builder(PartnerDetailActivity.this)
                                .setTitle(R.string.partnerDetailActivity_requestDialogTitle)
                                .setMessage(R.string.partnerDetailActivity_requestDialogMessage)
                                .setIcon(R.mipmap.ic_launcher)
                                .setPositiveButton(getString(R.string.partnerDetailActivity_requestDialogTitle), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        doRequest(getIntent().getStringExtra(HomeFragment.EXTRA_RELATION_ID),
                                                selectedPartnerId, selectedPartnerTypeId, selectedPartnerSpecializationId,
                                                getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM),
                                                selectedPaymentMethodId, promoCode,
                                                getIntent().getStringExtra(PartnerListActivity.EXTRA_USER_LATITUDE),
                                                getIntent().getStringExtra(PartnerListActivity.EXTRA_USER_LONGITUDE));
                                    }
                                })
                                .setNegativeButton(R.string.accountActivity_cancelTitle, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                }
            }
        });

        checkPromoCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if(promoCodeEdtxt.getText().toString().trim().equals("")) {
                    promoCodeEdtxt.setError(getString(R.string.partnerDetailActivity_promoCodeWarning));
                    isValid = false;
                }
                if (!getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_CONSULTATION)) {
                    if (selectedPaymentMethodId.equals("")) {
                        String message = getString(R.string.partnerDetailActivity_paymentValidationMessage);
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                        isValid = false;
                    }
                }

                if(isValid) {
                    checkPromo(promoCodeEdtxt.getText().toString());
                }
            }
        });

        clearPromoCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promoCodeEdtxt.setText("");
                checkPromoCodeBtn.setVisibility(View.VISIBLE);
                clearPromoCodeBtn.setVisibility(View.GONE);

                if (getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_CONSULTATION)) {
                    getPrice(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM), "03",
                            selectedPartnerTypeId, selectedPartnerSpecializationId);
                }
                else {
                    getPrice(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM), selectedPaymentMethodId,
                            selectedPartnerTypeId, selectedPartnerSpecializationId);
                }
            }
        });

        getPartnerfirebase(partnerId);
    }

    private void getPartnerfirebase(final String partnerId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", partnerId);

        myCillinAPI.getFirebaseToken(token, params).enqueue(new Callback<ModelResultFirebaseGet>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultFirebaseGet> call, @NonNull Response<ModelResultFirebaseGet> response) {
                progressBarHandler.hide();
                if (response.isSuccessful()) {
                    ModelResultFirebaseGet modelResultDataFirebaseGet = response.body();
                    assert modelResultDataFirebaseGet != null;
                    doctorFirebaseToken = modelResultDataFirebaseGet.getResult().getData().get(0).getToken();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if (jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        } else {
                            message = jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    getPartnerfirebase(partnerId);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultFirebaseGet> call, @NonNull Throwable t) {
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), "Firebase : " + t.getMessage(), Snackbar.LENGTH_SHORT).show();
                getPartnerfirebase(partnerId);
            }
        });
    }

    private void getPaymentMethod(final SpinnerDialog spinnerDialog) {
        progressBarHandler.show();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        myCillinAPI.getPaymentMethodList().enqueue(new Callback<ModelResultPaymentMethodeList>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultPaymentMethodeList> call, @NonNull Response<ModelResultPaymentMethodeList> response) {
                progressBarHandler.hide();

                if (response.isSuccessful()) {
                    ModelResultPaymentMethodeList modelResultPaymentMethodeList = response.body();

                    assert modelResultPaymentMethodeList != null;
                    if (modelResultPaymentMethodeList.getResult().isStatus()) {
                        items.clear();
                        paymentMethodIdItemsTemp.clear();
                        int size = modelResultPaymentMethodeList.getResult().getData().size();
                        for (int i = 0; i < size; i++) {
                            String paymentId = modelResultPaymentMethodeList.getResult().getData().get(i).getPaymentMethodeId();
                            String paymentDesc = modelResultPaymentMethodeList.getResult().getData().get(i).getPaymentMethodeDesc();

                            if(paymentId.equals("01")) {
                                // HIDE BPJS PAYMENT METHODE IF SERVICE IS BOOK HEALTHCARE
                                if(!getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_BOOK_HEALTHCARE)) {
                                    items.add(paymentDesc);
                                    paymentMethodIdItemsTemp.put(i, paymentId + " - " + paymentDesc);
                                }
                            }
                            else {
                                items.add(paymentDesc);
                                paymentMethodIdItemsTemp.put(i, paymentId + " - " + paymentDesc);
                            }
                        }
                        spinnerDialog.showSpinerDialog();
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if (jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        } else {

                            message = jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultPaymentMethodeList> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void getPartnerDetail(String partnerId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", partnerId);

        myCillinAPI.getDetailPartner(token, params).enqueue(new Callback<ModelResultPartnerDetailGet>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultPartnerDetailGet> call, @NonNull Response<ModelResultPartnerDetailGet> response) {
                progressBarHandler.hide();

                if (response.isSuccessful()) {
                    ModelResultPartnerDetailGet modelResultPartnerDetailGet = response.body();

                    assert modelResultPartnerDetailGet != null;
                    if (modelResultPartnerDetailGet.getResult().isStatus()) {
                        String userId = modelResultPartnerDetailGet.getResult().getData().get(0).getUserId() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getUserId();
                        String fullName = modelResultPartnerDetailGet.getResult().getData().get(0).getFullName() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getFullName();
                        String profilePhoto = modelResultPartnerDetailGet.getResult().getData().get(0).getProfilePhoto() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getProfilePhoto();
                        String gender = modelResultPartnerDetailGet.getResult().getData().get(0).getGender() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getGender();
                        String address = modelResultPartnerDetailGet.getResult().getData().get(0).getAddress() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getAddress();
                        String dob = modelResultPartnerDetailGet.getResult().getData().get(0).getDob() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getDob();
                        String SIP = modelResultPartnerDetailGet.getResult().getData().get(0).getNoSIP() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getNoSIP();
                        String SIPEnd = modelResultPartnerDetailGet.getResult().getData().get(0).getsIPBerakhir() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getsIPBerakhir();
                        String SIPPhoto = modelResultPartnerDetailGet.getResult().getData().get(0).getPhotoSIP() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getPhotoSIP();
                        String STR = modelResultPartnerDetailGet.getResult().getData().get(0).getNoSTR() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getNoSTR();
                        String STREnd = modelResultPartnerDetailGet.getResult().getData().get(0).getsTRBerakhir() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getsTRBerakhir();
                        String STRPhoto = modelResultPartnerDetailGet.getResult().getData().get(0).getPhotoSTR() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getPhotoSTR();
                        String partnerTypeId = modelResultPartnerDetailGet.getResult().getData().get(0).getPartnerTypeId() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getPartnerTypeId();
                        String partnerTypeDesc = modelResultPartnerDetailGet.getResult().getData().get(0).getPartnerTypeDesc() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getPartnerTypeDesc();
                        String specializationId = modelResultPartnerDetailGet.getResult().getData().get(0).getSpesialisasiId() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getSpesialisasiId();
                        String specializationDesc = modelResultPartnerDetailGet.getResult().getData().get(0).getSpesialisasiDesc() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getSpesialisasiDesc();
                        String workArea = modelResultPartnerDetailGet.getResult().getData().get(0).getWilayahKerja() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getWilayahKerja();
                        String profileDesc = modelResultPartnerDetailGet.getResult().getData().get(0).getProfileDesc() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getProfileDesc();
                        String workYear = modelResultPartnerDetailGet.getResult().getData().get(0).getLamaProfessi() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getLamaProfessi();
                        String workAddress = modelResultPartnerDetailGet.getResult().getData().get(0).getAlamatPraktik() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getAlamatPraktik();
                        String workInstitution = modelResultPartnerDetailGet.getResult().getData().get(0).getNamaInstitusi() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getNamaInstitusi();
                        String ratingValue = modelResultPartnerDetailGet.getResult().getData().get(0).getRating() == null ? "" : modelResultPartnerDetailGet.getResult().getData().get(0).getRating();

                        if (!profilePhoto.equals("")) {

                            RequestOptions requestOptions = new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .placeholder(R.drawable.ic_action_user)
                                    .fitCenter();

                            Glide.with(getApplicationContext())
                                    .load(profilePhoto)
                                    .apply(requestOptions)
                                    .into(doctorAvatar);

                            selectedPartnerPicURL = profilePhoto;
                        }

                        if (!SIPPhoto.equals("")) {
                            selectedPartnerSIPPURL = SIPPhoto;
                        }

                        if (!STRPhoto.equals("")) {
                            selectedPartnerSTRURL = STRPhoto;
                        }

                        doctorName.setText(fullName);
                        if (partnerTypeId.equals("03")) {
                            doctorType.setText(specializationDesc);
                        } else {
                            doctorType.setText(partnerTypeDesc);
                        }
                        doctorRating.setMax(5);
                        if (!ratingValue.equals("")) {
                            doctorRating.setRating(Float.parseFloat(ratingValue));
                        }
                        doctorSIPPPermitt.setText(SIP);
                        doctorSTRPermitt.setText(STR);
                        doctorDesc.setText(profileDesc);
                        doctorYearOfWork.setText(String.format(getResources().getString(R.string.partnerDetailActivity_yearsValue), workYear));
                        doctorWorkplace.setText(workInstitution);
                        doctorWorkAddress.setText(workAddress);
                        doctorWorkArea.setText(workArea);

                        selectedPartnerId = userId;
                        selectedPartnerTypeId = partnerTypeId;
                        selectedPartnerSpecializationId = specializationId;

                        if (getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_CONSULTATION)) {
                            getPrice(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM), "03",
                                    selectedPartnerTypeId, selectedPartnerSpecializationId);
                        }
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if (jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        } else {

                            message = jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultPartnerDetailGet> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void doRequest(String relationId, String partnerId, String partnerTypeId, String partnerSpecializationId,
                           String menu, String paymentMethodId, String promoCode, String latitude, String longitude) {

        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("relation_id", relationId);
        params.put("partner_selected", partnerId);
        params.put("partner_type_id", partnerTypeId.equals("- ") ? "" : partnerTypeId);
        params.put("spesialisasi_id", partnerSpecializationId.equals("- ") ? "" : partnerSpecializationId);
        params.put("service_type_id", menu);
        params.put("pymt_methode_id", paymentMethodId.equals("- ") ? "" : paymentMethodId);
        params.put("promo_code", promoCode);
        params.put("latitude_request", latitude);
        params.put("longitude_request", longitude);

        myCillinAPI.requestTransaction(token, params).enqueue(new Callback<ModelResultRequestTransaction>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultRequestTransaction> call, @NonNull Response<ModelResultRequestTransaction> response) {
                progressBarHandler.hide();

                if (response.isSuccessful()) {
                    ModelResultRequestTransaction modelResultRequestTransaction = response.body();
                    assert modelResultRequestTransaction != null;
                    if (modelResultRequestTransaction.getResult().isStatus()) {
                        sendNotification();
                        Snackbar.make(getWindow().getDecorView().getRootView(), modelResultRequestTransaction.getResult().getMessage(), Snackbar.LENGTH_SHORT)
                                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);

                                        Intent intent = new Intent(PartnerDetailActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    } else {
                        Snackbar.make(getWindow().getDecorView().getRootView(), modelResultRequestTransaction.getResult().getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if (jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        } else {

                            message = jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultRequestTransaction> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void doConsult(String relationId, String partnerId, String partnerTypeId, String partnerSpecializationId,
                           String promoCode) {

        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();
        String userId = sessionManager.getUserId();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("relation_id", relationId);
        params.put("partner_selected", partnerId);
        params.put("partner_type_id", partnerTypeId.equals("- ") ? "" : partnerTypeId);
        params.put("spesialisasi_id", partnerSpecializationId.equals("- ") ? "" : partnerSpecializationId);
        params.put("promo_code", promoCode);

        myCillinAPI.requestConsultation(token, params).enqueue(new Callback<ModelResultRequestConsultation>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultRequestConsultation> call, @NonNull Response<ModelResultRequestConsultation> response) {
                progressBarHandler.hide();

                if (response.isSuccessful()) {
                    ModelResultRequestConsultation modelResultRequestConsultation = response.body();

                    assert modelResultRequestConsultation != null;
                    if (modelResultRequestConsultation.getResult().isStatus()) {
                        sendNotification();
                        Snackbar.make(getWindow().getDecorView().getRootView(), modelResultRequestConsultation.getResult().getMessage(), Snackbar.LENGTH_SHORT)
                                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);

                                        Intent intent = new Intent(PartnerDetailActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    } else {
                        Snackbar.make(getWindow().getDecorView().getRootView(), modelResultRequestConsultation.getResult().getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if (jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        } else {

                            message = jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultRequestConsultation> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });


    }

    private void getPrice(String serviceTypeId, String paymentMethodId, String partnerTypeId, String partnerSpecializationId) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("service_type_id", serviceTypeId.equals("- ") ? "" : serviceTypeId);
        params.put("pymt_methode_id", paymentMethodId.equals("- ") ? "" : paymentMethodId);
        params.put("partner_type_id", partnerTypeId.equals("- ") ? "" : partnerTypeId);
        params.put("spesialisasi_id", partnerSpecializationId.equals("- ") ? "" : partnerSpecializationId);

        myCillinAPI.getPrice(token, params).enqueue(new Callback<ModelResultPriceGet>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultPriceGet> call, @NonNull Response<ModelResultPriceGet> response) {
                progressBarHandler.hide();

                if (response.isSuccessful()) {
                    ModelResultPriceGet modelResultPriceGet = response.body();

                    assert modelResultPriceGet != null;
                    if (modelResultPriceGet.getResult().isStatus()) {
                        int size = modelResultPriceGet.getResult().getData().size();
                        if (size > 0) {
                            String price = modelResultPriceGet.getResult().getData().get(0).getPriceAmount();

                            doctorFee.addTextChangedListener(new CurrencyTextWatcherTextView(doctorFee));
                            doctorFee.setText(price);

                            if(!promoCodeEdtxt.getText().toString().trim().equals("")) {
                                checkPromo(promoCodeEdtxt.getText().toString());
                            }

                        } else {
                            paymentDropdown.setText("");
                            selectedPaymentMethodId = "";
                            doctorFee.setText("-");
                        }
                    } else {
                        paymentDropdown.setText("");
                        selectedPaymentMethodId = "";
                    }
                } else {
                    paymentDropdown.setText("");
                    selectedPaymentMethodId = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if (jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        } else {
                            message = jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultPriceGet> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                paymentDropdown.setText("");
                selectedPaymentMethodId = "";
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNotification() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        Map<String, String> paramsNotif = new HashMap<>();
        paramsNotif.put("body", "You Have New Request");
        paramsNotif.put("click_action", "HOME");

        Map<String, Object> params = new HashMap<>();
        params.put("to", doctorFirebaseToken);
        params.put("notification", paramsNotif);

        JSONObject jsonObject = new JSONObject(params);
        Log.d("#8#8#", "sendNotif: " + jsonObject);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
                .addHeader("Authorization", "key=AAAAbynyk1I:APA91bENZXh3N4QC-HrUy4ApIVe8CnW3F0k5mG5OXdUMApskyFTKDYnjd6Pdwko-hqvkekZoH5KxtC-gyxu0-XoXcItm9PJYGw9zzrc5Wbzr6CY3FuaSvXb7MCYMNfmNEVmUWZA8SqB5")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                String result = response.body().string();
                Log.d("#8#8#", "onResponse: " + result);
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("result")) {
                            boolean status = jsonObject.getJSONObject("result").getBoolean("status");
                            if (status) {
                                Log.d("#8#8#", "onResponse: SIP");
                            } else {
                                String message = jsonObject.getJSONObject("result").getString("message");
                                Log.d("#8#8#", "onResponse: SIP" + message);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String message;
                        if (jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        } else {
                            message = jsonObject.getString("message");
                        }

                        Log.d("#8#8#", "onResponse: gagal" + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void checkPromo(String promoCode) {
        progressBarHandler.show();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String token = sessionManager.getUserToken();

        MyCillinAPI myCillinAPI = MyCillinRestClient.getMyCillinRestInterface();

        HashMap<String, String> params = new HashMap<>();
        params.put("promo_code", promoCode);

        myCillinAPI.checkPromo(token, params).enqueue(new Callback<ModelResultPromoCheck>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultPromoCheck> call, @NonNull Response<ModelResultPromoCheck> response) {
                progressBarHandler.hide();

                if (response.isSuccessful()) {
                    ModelResultPromoCheck modelResultPromoCheck = response.body();
                    assert modelResultPromoCheck != null;
                    if (modelResultPromoCheck.getResult().isStatus()) {
                        int size = modelResultPromoCheck.getResult().getData().size();
                        if(size > 0) {
                            String promoCode = modelResultPromoCheck.getResult().getData().get(0).getPromoCode();
                            Double discount = Double.parseDouble(modelResultPromoCheck.getResult().getData().get(0).getDiscount());
                            int normalPrice = Integer.parseInt(doctorFee.getText().toString().replace(".", "").replace(",", ""));
                            int discountPrice = (int) (normalPrice - (normalPrice * discount));

                            doctorFee.addTextChangedListener(new CurrencyTextWatcherTextView(doctorFee));
                            doctorFee.setText(discountPrice + "");

                            String message = String.format(getResources().getString(R.string.partnerDetailActivity_promoSuccessMessage), promoCode);
                            Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();

                            checkPromoCodeBtn.setVisibility(View.GONE);
                            clearPromoCodeBtn.setVisibility(View.VISIBLE);
                        }
                        else {
                            promoCodeEdtxt.setText("");
                            String message = getString(R.string.partnerDetailActivity_promoFailedMessage);
                            Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();

                            if (getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_CONSULTATION)) {
                                getPrice(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM), "03",
                                        selectedPartnerTypeId, selectedPartnerSpecializationId);
                            }
                            else {
                                getPrice(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM), selectedPaymentMethodId,
                                        selectedPartnerTypeId, selectedPartnerSpecializationId);
                            }

                            checkPromoCodeBtn.setVisibility(View.VISIBLE);
                            clearPromoCodeBtn.setVisibility(View.GONE);
                        }
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message;
                        if (jsonObject.has("result")) {
                            message = jsonObject.getJSONObject("result").getString("message");
                        } else {

                            message = jsonObject.getString("message");
                        }
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelResultPromoCheck> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
