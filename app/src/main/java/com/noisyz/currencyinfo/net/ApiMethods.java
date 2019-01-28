package com.noisyz.currencyinfo.net;

import com.noisyz.mvvmbase.network.Response;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiMethods {

    @GET("api/?get=currency_list&key=" + ApiConstants.API_KEY)
    Single<Response<List<String>>> getPairs();

    @GET("api/?get=rates&key=" + ApiConstants.API_KEY)
    Single<Response<Map<String, Double>>> getPrices(@Query("pairs") String pairs);

}
