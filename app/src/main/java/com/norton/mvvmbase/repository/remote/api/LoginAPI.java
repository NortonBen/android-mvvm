package com.norton.mvvmbase.repository.remote.api;

import android.arch.lifecycle.LiveData;


import com.norton.mvvmbase.repository.remote.ApiResponse;
import com.norton.mvvmbase.repository.remote.data.LoginAPI.Login;
import com.norton.mvvmbase.repository.remote.data.LoginAPI.LoginResult;
import com.norton.mvvmbase.repository.remote.data.Result;
import com.norton.mvvmbase.repository.remote.data.LoginAPI.UserResult;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by norton on 08/04/2018.
 */

public interface LoginAPI {

    @POST("api/auth/login")
    LiveData<ApiResponse<LoginResult>> login(@Body Login login);

    @POST("api/auth/token")
    LiveData<ApiResponse<UserResult>> loginToken(@Header("Authorization") String authorization);

    @POST("api/auth/logout")
    LiveData<ApiResponse<Result>> logout(@Header("Authorization") String authorization);
}
