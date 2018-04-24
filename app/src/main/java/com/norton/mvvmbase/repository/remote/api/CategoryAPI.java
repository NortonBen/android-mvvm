package com.norton.mvvmbase.repository.remote.api;

import android.arch.lifecycle.LiveData;

import com.norton.mvvmbase.repository.remote.ApiResponse;
import com.norton.mvvmbase.repository.remote.data.CategoryAPI.CategoryResult;

import java.util.List;

import retrofit2.http.GET;

/**
 * Created by norton on 11/04/2018.
 */

public interface CategoryAPI {

    @GET("/api/categories")
    LiveData<ApiResponse<List<CategoryResult>>> getAll();

}
