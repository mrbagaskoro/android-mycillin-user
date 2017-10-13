package com.mycillin.user.rest;

import com.mycillin.user.rest.login.ModelResultLogin;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 16003041 on 12/10/2017.
 */

public interface MyCillinAPI {

    @POST("login/")
    Call<ModelResultLogin> doLogin(@Body HashMap<String, String> params);
}
