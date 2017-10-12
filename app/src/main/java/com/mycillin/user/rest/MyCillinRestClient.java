package com.mycillin.user.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycillin.user.util.Configs;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 16003041 on 12/10/2017.
 */

public class MyCillinRestClient {
    private static Retrofit retrofit = null;

    private final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    public static MyCillinAPI getMyCillinRestInterface() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Configs.RETROFIT_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }

        return retrofit.create(MyCillinAPI.class);
    }
}
