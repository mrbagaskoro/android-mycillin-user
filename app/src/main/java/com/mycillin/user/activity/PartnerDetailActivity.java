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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mycillin.user.R;
import com.mycillin.user.fragment.HomeFragment;
import com.mycillin.user.rest.MyCillinAPI;
import com.mycillin.user.rest.MyCillinRestClient;
import com.mycillin.user.rest.partnerDetailGet.ModelResultPartnerDetailGet;
import com.mycillin.user.rest.priceGet.ModelResultPriceGet;
import com.mycillin.user.rest.requestConsultation.ModelResultRequestConsultation;
import com.mycillin.user.rest.requestTransaction.ModelResultRequestTransaction;
import com.mycillin.user.util.CurrencyTextWatcherTextView;
import com.mycillin.user.util.ProgressBarHandler;
import com.mycillin.user.util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
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
    @BindView(R.id.partnerDetailActivity_tv_doctorFee)
    TextView doctorFee;
    @BindView(R.id.partnerDetailActivity_v_paymentSeparator)
    View paymentSeparator;
    @BindView(R.id.partnerDetailActivity_ll_paymentContainer)
    LinearLayout paymentContainer;
    @BindView(R.id.partnerDetailActivity_rg_radioGroup)
    RadioGroup payWithRadioGroup;
    @BindView(R.id.partnerDetailActivity_bt_requestBtn)
    Button requestBtn;

    @BindView(R.id.partnerDetailActivity_toolbar)
    Toolbar toolbar;

    public static String KEY_FLAG_PARTNER_ID = "KEY_FLAG_PARTNER_ID";

    private ProgressBarHandler progressBarHandler;

    String selectedPartnerPicURL = "";
    String selectedPartnerSIPPURL = "";
    String selectedPartnerSTRURL = "";
    String selectedPartnerId = "";
    String selectedPartnerTypeId = "";
    String selectedPartnerSpecializationId = "";
    String selectedPaymentMethodId = "";

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
                if(!selectedPartnerPicURL.equals("")) {
                    Intent intent = new Intent(PartnerDetailActivity.this, ViewImageActivity.class);
                    intent.putExtra(ViewImageActivity.EXTRA_IMAGE_URL, selectedPartnerPicURL);
                    startActivity(intent);
                }
            }
        });

        SIPPContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedPartnerSIPPURL.equals("")) {
                    Intent intent = new Intent(PartnerDetailActivity.this, ViewImageActivity.class);
                    intent.putExtra(ViewImageActivity.EXTRA_IMAGE_URL, selectedPartnerSIPPURL);
                    startActivity(intent);
                }
                else {
                    String message = getString(R.string.partnerDetailActivity_SIPPmessage);
                    Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        STRContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedPartnerSTRURL.equals("")) {
                    Intent intent = new Intent(PartnerDetailActivity.this, ViewImageActivity.class);
                    intent.putExtra(ViewImageActivity.EXTRA_IMAGE_URL, selectedPartnerSTRURL);
                    startActivity(intent);
                }
                else {
                    String message = getString(R.string.partnerDetailActivity_STRmessage);
                    Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        payWithRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(i == R.id.partnerDetailActivity_rb_radioButtonCash) {
                    selectedPaymentMethodId = "00";
                }
                else if(i == R.id.partnerDetailActivity_rb_radioButtonEWallet) {
                    selectedPaymentMethodId = "03";
                }

                if(i != -1) {
                    getPrice(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM), selectedPaymentMethodId,
                            selectedPartnerTypeId, selectedPartnerSpecializationId);
                }
            }
        });

        if(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_CONSULTATION)) {
            paymentSeparator.setVisibility(View.INVISIBLE);
            paymentContainer.setVisibility(View.INVISIBLE);
        }
        else {
            paymentSeparator.setVisibility(View.VISIBLE);
            paymentContainer.setVisibility(View.VISIBLE);
        }

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM).equals(HomeFragment.KEY_CONSULTATION)) {
                    new AlertDialog.Builder(PartnerDetailActivity.this)
                            .setTitle(R.string.partnerDetailActivity_consultDialogTitle)
                            .setMessage(R.string.partnerDetailActivity_consultDialogMessage)
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton(getString(R.string.partnerDetailActivity_consultDialogTitle), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    doConsult(getIntent().getStringExtra(HomeFragment.EXTRA_RELATION_ID),
                                            selectedPartnerId, selectedPartnerTypeId, selectedPartnerSpecializationId,
                                            "TEST");
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
                else {
                    if(selectedPaymentMethodId.equals("")) {
                        String message = getString(R.string.partnerDetailActivity_paymentValidationMessage);
                        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
                    }
                    else {
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
                                                selectedPaymentMethodId, "TEST",
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

                if(response.isSuccessful()) {
                    ModelResultPartnerDetailGet modelResultPartnerDetailGet = response.body();

                    assert modelResultPartnerDetailGet != null;
                    if(modelResultPartnerDetailGet.getResult().isStatus()) {
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

                        if(!profilePhoto.equals("")) {

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

                        if(!SIPPhoto.equals("")) {
                            selectedPartnerSIPPURL = SIPPhoto;
                        }

                        if(!STRPhoto.equals("")) {
                            selectedPartnerSTRURL = STRPhoto;
                        }

                        doctorName.setText(fullName);
                        if(partnerTypeId.equals("03")) {
                            doctorType.setText(specializationDesc);
                        }
                        else {
                            doctorType.setText(partnerTypeDesc);
                        }
                        doctorRating.setMax(5);
                        if(!ratingValue.equals("")) {
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
        params.put("partner_type_id", partnerTypeId);
        params.put("spesialisasi_id", partnerSpecializationId);
        params.put("service_type_id", menu);
        params.put("pymt_methode_id", paymentMethodId);
        params.put("promo_code", promoCode);
        params.put("latitude_request", latitude);
        params.put("longitude_request", longitude);

        myCillinAPI.requestTransaction(token, params).enqueue(new Callback<ModelResultRequestTransaction>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultRequestTransaction> call, @NonNull Response<ModelResultRequestTransaction> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultRequestTransaction modelResultRequestTransaction = response.body();

                    assert modelResultRequestTransaction != null;
                    if(modelResultRequestTransaction.getResult().isStatus()) {
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
                    }
                    else {
                        Snackbar.make(getWindow().getDecorView().getRootView(), modelResultRequestTransaction.getResult().getMessage(), Snackbar.LENGTH_LONG).show();
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
        params.put("partner_type_id", partnerTypeId);
        params.put("spesialisasi_id", partnerSpecializationId);
        params.put("promo_code", promoCode);

        myCillinAPI.requestConsultation(token, params).enqueue(new Callback<ModelResultRequestConsultation>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultRequestConsultation> call, @NonNull Response<ModelResultRequestConsultation> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultRequestConsultation modelResultRequestConsultation = response.body();

                    assert modelResultRequestConsultation != null;
                    if(modelResultRequestConsultation.getResult().isStatus()) {
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
                    }
                    else {
                        Snackbar.make(getWindow().getDecorView().getRootView(), modelResultRequestConsultation.getResult().getMessage(), Snackbar.LENGTH_LONG).show();
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
        params.put("service_type_id", serviceTypeId);
        params.put("pymt_methode_id", paymentMethodId);
        params.put("partner_type_id", partnerTypeId);
        params.put("spesialisasi_id", partnerSpecializationId);

        myCillinAPI.getPrice(token, params).enqueue(new Callback<ModelResultPriceGet>() {
            @Override
            public void onResponse(@NonNull Call<ModelResultPriceGet> call, @NonNull Response<ModelResultPriceGet> response) {
                progressBarHandler.hide();

                if(response.isSuccessful()) {
                    ModelResultPriceGet modelResultPriceGet = response.body();

                    assert modelResultPriceGet != null;
                    if(modelResultPriceGet.getResult().isStatus()) {
                        int size = modelResultPriceGet.getResult().getData().size();
                        if(size > 0) {
                            String price = modelResultPriceGet.getResult().getData().get(0).getPriceAmount();

                            doctorFee.addTextChangedListener(new CurrencyTextWatcherTextView(doctorFee));
                            doctorFee.setText(price);
                        }
                        else {
                            payWithRadioGroup.clearCheck();
                            selectedPaymentMethodId = "";
                            doctorFee.setText("-");
                        }
                    }
                    else {
                        payWithRadioGroup.clearCheck();
                        selectedPaymentMethodId = "";
                    }
                }
                else {
                    payWithRadioGroup.clearCheck();
                    selectedPaymentMethodId = "";
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
            public void onFailure(@NonNull Call<ModelResultPriceGet> call, @NonNull Throwable t) {
                // TODO: 12/10/2017 SET FAILURE SCENARIO
                payWithRadioGroup.clearCheck();
                selectedPaymentMethodId = "";
                progressBarHandler.hide();
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
