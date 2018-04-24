package com.norton.mvvmbase.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.norton.mvvmbase.repository.LoginRepository;
import com.norton.mvvmbase.repository.remote.Resource;
import com.norton.mvvmbase.repository.remote.Status;
import com.norton.mvvmbase.repository.remote.data.LoginAPI.Login;
import com.norton.mvvmbase.repository.remote.data.LoginAPI.LoginResult;
import com.norton.mvvmbase.ui.common.RetryCallback;
import com.norton.mvvmbase.utils.AbsentLiveData;

import javax.inject.Inject;

/**
 * Created by norton on 08/04/2018.
 */

public class LoginViewModel extends ViewModel implements RetryCallback {

    private final LiveData<Resource<LoginResult>> result;
    private final MutableLiveData<Login> login = new MutableLiveData<>();

    private final LoginRepository loginRepository;

    @Inject
    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        result = Transformations.switchMap(login, input ->  {
            if (input == null) {
                return AbsentLiveData.create();
            }
            return loginRepository.login(input);
        });
    }

    public LiveData<Resource<LoginResult>> getResult() {
        return result;
    }

    public void login(Login login) {
        if (result.getValue() != null && result.getValue().status == Status.LOADING) {
            return;
        }
        this.login.setValue(login);
    }

    @Override
    public void retry() {

    }
}
