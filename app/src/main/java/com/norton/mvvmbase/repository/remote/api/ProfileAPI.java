package com.norton.mvvmbase.repository.remote.api;

import android.arch.lifecycle.LiveData;

import com.norton.mvvmbase.repository.remote.ApiResponse;
import com.norton.mvvmbase.repository.remote.data.LoginAPI.Login;
import com.norton.mvvmbase.repository.remote.data.LoginAPI.LoginResult;

import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by norton on 11/04/2018.
 */

public interface ProfileAPI {
    @POST("/login")
    LiveData<ApiResponse<LoginResult>> login(@Body Login login);

}
