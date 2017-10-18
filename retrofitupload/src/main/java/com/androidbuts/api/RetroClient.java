package com.androidbuts.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author Pratik Butani
 */
public class RetroClient {


    private static final String ROOT_URL = " http://cdd.xiaoxi6.com/";


    public RetroClient() {

    }
    
    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        return getRetroClient().create(ApiService.class);
    }
}
