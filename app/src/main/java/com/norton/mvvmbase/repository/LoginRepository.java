package com.norton.mvvmbase.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.norton.mvvmbase.AppExecutors;
import com.norton.mvvmbase.factory.AuthenFactory;
import com.norton.mvvmbase.repository.remote.ApiResponse;
import com.norton.mvvmbase.repository.remote.NetworkOnlyBoundResource;
import com.norton.mvvmbase.repository.remote.Resource;
import com.norton.mvvmbase.repository.remote.api.LoginAPI;
import com.norton.mvvmbase.repository.remote.data.LoginAPI.Login;
import com.norton.mvvmbase.repository.remote.data.LoginAPI.LoginResult;
import com.norton.mvvmbase.utils.AbsentLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by norton on 10/04/2018.
 */
@Singleton
public class LoginRepository {

    private final AuthenFactory authenFactory;

    private final LoginAPI loginAPI;

    private final AppExecutors appExecutors;


    @Inject
    public LoginRepository(AuthenFactory authenFactory, LoginAPI loginAPI, AppExecutors appExecutors) {
        this.authenFactory = authenFactory;
        this.loginAPI = loginAPI;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<LoginResult>> login(Login login) {
        return new NetworkOnlyBoundResource<LoginResult>(appExecutors) {

            @SuppressLint("WrongThread")
            @Override
            protected void saveCallResult(@NonNull LoginResult item) {
                authenFactory.saveToken(item.token, new AuthenFactory.CallBack() {
                        @Override
                        public void call() {
                            appExecutors.mainThread().execute(() -> {
                                authenFactory.getStatus().setValue(AuthenFactory.Status.LOGIN);
                            });
                        }
                    });

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<LoginResult>> createCall() {
                return loginAPI.login(login);
            }
        }.asLiveData();
    }
}
