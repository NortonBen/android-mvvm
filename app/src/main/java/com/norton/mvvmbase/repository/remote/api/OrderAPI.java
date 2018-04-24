package com.norton.mvvmbase.repository.remote.api;

import android.arch.lifecycle.LiveData;

import com.norton.mvvmbase.repository.remote.ApiResponse;
import com.norton.mvvmbase.repository.remote.data.OrderAPI.Order;
import com.norton.mvvmbase.repository.remote.data.OrderAPI.OrderResult;
import com.norton.mvvmbase.repository.remote.data.Result;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by norton on 11/04/2018.
 */

public interface OrderAPI {

    @POST("/api/orders")
    LiveData<ApiResponse<Result>> order(@Body Order order);

    @GET("/api/orders")
    LiveData<ApiResponse<List<OrderResult>>> getAllOrder();
}
