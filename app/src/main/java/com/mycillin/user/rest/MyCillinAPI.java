package com.mycillin.user.rest;

import com.mycillin.user.rest.accountDelete.ModelResultAccountDelete;
import com.mycillin.user.rest.accountInsert.ModelResultAccountInsert;
import com.mycillin.user.rest.accountList.ModelResultAccountList;
import com.mycillin.user.rest.accountPicGet.ModelResultAccountPicGet;
import com.mycillin.user.rest.accountUpdate.ModelResultAccountUpdate;
import com.mycillin.user.rest.cancelReasonList.ModelResultCancelReasonList;
import com.mycillin.user.rest.changePassword.ModelResultChangePassword;
import com.mycillin.user.rest.forgotPassword.ModelResultForgotPassword;
import com.mycillin.user.rest.login.ModelResultLogin;
import com.mycillin.user.rest.register.ModelResultRegister;
import com.mycillin.user.rest.relationList.ModelResultRelationList;
import com.mycillin.user.util.SessionManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
}
