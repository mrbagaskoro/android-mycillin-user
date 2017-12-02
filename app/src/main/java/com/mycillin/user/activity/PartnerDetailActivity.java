package com.mycillin.user.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    @BindView(R.id.partnerDetailActivity_rg_radioGroup)
    RadioGroup payWithRadioGroup;
    @BindView(R.id.partnerDetailActivity_bt_requestBtn)
    Button requestBtn;

    @BindView(R.id.partnerDetailActivity_toolbar)
    Toolbar toolbar;

    public static String KEY_FLAG_PARTNER_ID = "KEY_FLAG_PARTNER_ID";

    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_detail);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.partnerDetailActivity_title);

        progressBarHandler = new ProgressBarHandler(this);

        String calledFrom = getIntent().getStringExtra(HomeFragment.EXTRA_SERVICE_CALLED_FROM);
        String partnerId = getIntent().getStringExtra(KEY_FLAG_PARTNER_ID);
        getPartnerDetail(partnerId);

        doctorFee.setText(getResources().getString(R.string.medicalPersonnelDetail_feeAmount));
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

                        Log.d("###", "onResponse: " + ratingValue);

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
}
