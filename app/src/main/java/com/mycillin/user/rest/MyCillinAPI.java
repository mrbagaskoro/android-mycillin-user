package com.mycillin.user.rest;

import com.mycillin.user.rest.accountDelete.ModelResultAccountDelete;
import com.mycillin.user.rest.accountInsert.ModelResultAccountInsert;
import com.mycillin.user.rest.accountList.ModelResultAccountList;
import com.mycillin.user.rest.accountPicGet.ModelResultAccountPicGet;
import com.mycillin.user.rest.accountPicUpdate.ModelResultAccountPicUpdate;
import com.mycillin.user.rest.accountUpdate.ModelResultAccountUpdate;
import com.mycillin.user.rest.bannerImage.ModelResultBannerImage;
import com.mycillin.user.rest.cancelReasonList.ModelResultCancelReasonList;
import com.mycillin.user.rest.changePassword.ModelResultChangePassword;
import com.mycillin.user.rest.facebookLogin.ModelResultFacebookLogin;
import com.mycillin.user.rest.findClinic.ModelResultFindClinic;
import com.mycillin.user.rest.findHealthcare.ModelResultFindHealthcare;
import com.mycillin.user.rest.findPartner.ModelResultFindPartner;
import com.mycillin.user.rest.forgotPassword.ModelResultForgotPassword;
import com.mycillin.user.rest.insuranceDelete.ModelResultInsuranceDelete;
import com.mycillin.user.rest.insuranceInsert.ModelResultInsuranceInsert;
import com.mycillin.user.rest.insuranceList.ModelResultInsuranceList;
import com.mycillin.user.rest.insuranceProviderList.ModelResultInsuranceProviderList;
import com.mycillin.user.rest.login.ModelResultLogin;
import com.mycillin.user.rest.medicalRecordList.ModelResultMedicalRecordList;
import com.mycillin.user.rest.partnerDetailGet.ModelResultPartnerDetailGet;
import com.mycillin.user.rest.partnerTypeList.ModelResultPartnerTypeList;
import com.mycillin.user.rest.prescriptionRecordList.ModelResultPrescriptionRecordList;
import com.mycillin.user.rest.ratingInsert.ModelResultRatingInsert;
import com.mycillin.user.rest.register.ModelResultRegister;
import com.mycillin.user.rest.relationList.ModelResultRelationList;
import com.mycillin.user.rest.requestTransaction.ModelResultRequestTransaction;
import com.mycillin.user.rest.specializationList.ModelResultSpecializationList;
import com.mycillin.user.rest.unratedList.ModelResultUnratedList;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by 16003041 on 12/10/2017.
 */

public interface MyCillinAPI {

    @POST("login/")
    Call<ModelResultLogin> doLogin(@Body HashMap<String, String> params);

    @FormUrlEncoded
    @POST("register/")
    Call<ModelResultRegister> doRegister(@Field("email") String email,
                                         @Field("password") String password,
                                         @Field("name") String name,
                                         @Field("ref_id") String referral);

    @FormUrlEncoded
    @POST("login_fb/")
    Call<ModelResultFacebookLogin> doFacebookLogin(@Field("fb_id") String id,
                                                   @Field("fb_name") String name,
                                                   @Field("fb_email") String email);

    @POST("forgot_password/")
    Call<ModelResultForgotPassword> doForgotPassword(@Body HashMap<String, String> params);

    @POST("change_password/")
    Call<ModelResultChangePassword> doChangePassword(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @POST("list_family/")
    Call<ModelResultAccountList> getAccountList(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @POST("update_family/")
    Call<ModelResultAccountUpdate> doUpdateAccount(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @POST("add_family/")
    Call<ModelResultAccountInsert> doInsertAccount(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @POST("delete_family/")
    Call<ModelResultAccountDelete> doDeleteAccount(@Header("Authorization") String token, @Body HashMap<String, Object> params);

    @GET("list_relation/")
    Call<ModelResultRelationList> getRelationList();

    @POST("get_avatar/")
    Call<ModelResultAccountPicGet> getAvatar(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @GET("list_cancel_reason/")
    Call<ModelResultCancelReasonList> getCancelReasonList();

    @POST("list_medical_record/")
    Call<ModelResultMedicalRecordList> getMedicalRecordList(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @POST("detail_prescription/")
    Call<ModelResultPrescriptionRecordList> getPrescriptionRecordList(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @GET("list_partner_type/")
    Call<ModelResultPartnerTypeList> getPartnerTypeList();

    @POST("list_spesialisasi/")
    Call<ModelResultSpecializationList> getSpecializationList(@Body HashMap<String, String> params);

    @GET("banner/")
    Call<ModelResultBannerImage> getBannerImage();

    @POST("list_member_insurance/")
    Call<ModelResultInsuranceList> getInsuranceList(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @GET("list_insr_provider/")
    Call<ModelResultInsuranceProviderList> getInsuranceProviderList();

    @Multipart
    @POST("add_member_insurance/")
    Call<ModelResultInsuranceInsert> doInsertInsurance(@Header("Authorization") String token,
                                                       @Part("user_id") RequestBody userId,
                                                       @Part("relation_id") RequestBody relationId,
                                                       @Part("no_polis_insr") RequestBody policyNo,
                                                       @Part("insr_provider_id") RequestBody providerId,
                                                       @Part("nama_tertanggung") RequestBody insuredName,
                                                       @Part("nama_pemilik_insr") RequestBody insuranceHolder,
                                                       @Part MultipartBody.Part insuranceCardImage);

    @POST("delete_member_insurance/")
    Call<ModelResultInsuranceDelete> doDeleteInsurance(@Header("Authorization") String token, @Body HashMap<String, Object> params);

    @POST("rating_fill_checking/")
    Call<ModelResultUnratedList> getUnratedList(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @POST("user_rating_feedback/")
    Call<ModelResultRatingInsert> rateTransaction(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @Multipart
    @POST("change_avatar/")
    Call<ModelResultAccountPicUpdate> updateAccountPic(@Header("Authorization") String token,
                                                       @Part("user_id") RequestBody userId,
                                                       @Part MultipartBody.Part profileImg);

    @POST("find_partner/")
    Call<ModelResultFindPartner> findPartner(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @POST("find_clinic/")
    Call<ModelResultFindClinic> findClinic(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @POST("find_healthcare/")
    Call<ModelResultFindHealthcare> findHealthcare(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @POST("detail_partner_information/")
    Call<ModelResultPartnerDetailGet> getDetailPartner(@Header("Authorization") String token, @Body HashMap<String, String> params);

    @POST("add_request/")
    Call<ModelResultRequestTransaction> requestTransaction(@Header("Authorization") String token, @Body HashMap<String, String> params);
}
