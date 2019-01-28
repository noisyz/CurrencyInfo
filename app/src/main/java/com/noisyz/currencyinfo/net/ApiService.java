package com.noisyz.currencyinfo.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService implements ApiConstants {
    private static ApiService instance;
    private ApiMethods apiMethods;

    public static ApiService getInstance() {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }

    private ApiService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(Level.BODY);

        this.apiMethods = new Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiConstants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(interceptor).build())
                .build()
                .create(ApiMethods.class);

    }

    public ApiMethods getApiMethods() {
        return apiMethods;
    }
}
