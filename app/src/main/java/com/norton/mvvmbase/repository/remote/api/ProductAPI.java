package com.norton.mvvmbase.repository.remote.api;

import android.arch.lifecycle.LiveData;

import com.norton.mvvmbase.repository.remote.ApiResponse;
import com.norton.mvvmbase.repository.remote.data.PruductAPI.ProductResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by norton on 11/04/2018.
 */

public interface ProductAPI {

    @GET("api/categories/{id}")
    LiveData<ApiResponse<List<ProductResult>>> getProductsByCategoryId(@Path("id") int id);

}
