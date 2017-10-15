package com.mycillin.user.rest;

import com.mycillin.user.rest.forgotPassword.ModelResultForgotPassword;
import com.mycillin.user.rest.login.ModelResultLogin;
import com.mycillin.user.rest.register.ModelResultRegister;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
}
