package com.norton.mvvmbase.factory;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.norton.mvvmbase.AppExecutors;
import com.norton.mvvmbase.repository.remote.ApiResponse;
import com.norton.mvvmbase.repository.local.dao.SettingDao;
import com.norton.mvvmbase.repository.local.entity.SettingEntity;
import com.norton.mvvmbase.repository.remote.api.LoginAPI;
import com.norton.mvvmbase.repository.remote.data.LoginAPI.UserResult;
import com.norton.mvvmbase.repository.remote.data.Result;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by norton on 11/04/2018.
 */

public class AuthenFactory {

    private final SettingDao settingDao;
    private final LoginAPI loginAPI;
    private final AppExecutors appExecutors;
    private final MutableLiveData<String> token = new MutableLiveData<>();
    private final MutableLiveData<UserResult> user = new MutableLiveData<>();
    private SettingEntity settingEntity = null;
    private final MutableLiveData<Status> status = new MutableLiveData<>();


    @Inject
    public AuthenFactory(SettingDao settingDao, LoginAPI loginAPI, AppExecutors appExecutors) {
        this.settingDao = settingDao;
        this.loginAPI = loginAPI;
        this.appExecutors = appExecutors;

        status.observeForever(s ->  {
            if (s == Status.LOGIN || s == Status.UNKNOWN) {
               if (token.getValue() != null) {

                   return;
               }
                appExecutors.diskIO().execute(() -> {
                    List<SettingEntity> settingEntities = settingDao.get("token");
                    if (!settingEntities.isEmpty()) {
                        settingEntity = settingEntities.get(0);
                        String _token = settingEntity.getOption();
                        appExecutors.mainThread().execute(() -> {
                            token.setValue(_token);
                        });
                    } else {
                        appExecutors.mainThread().execute(() -> {
                            status.setValue(Status.NOT_LOGIN);
                            user.setValue(null);
                        });
                    }
                });
            }
            if (s == Status.LOGOUT) {
                logoutAuth();
            }
        });

        token.observeForever(s -> {
            if (s != null && !s.isEmpty()) {
                appExecutors.networkIO().execute(() -> {
                    LiveData<ApiResponse<UserResult>> login = loginAPI.loginToken("Bearer "+s);
                    login.observeForever(userEntityApiResponse -> {
                        if (userEntityApiResponse.isSuccessful()) {
                            if (status.getValue() == Status.UNKNOWN ) {
                                status.setValue(Status.LOGIN);
                            }
                            user.setValue(userEntityApiResponse.body);
                        } else {
                            status.setValue(Status.NOT_LOGIN);
                            user.setValue(null);
                        }
                    });
                });
            }
        });
    }

    private void logoutAuth () {
        appExecutors.networkIO().execute(() -> {
            LiveData<ApiResponse<Result>> logout = loginAPI.logout(token.getValue());
            logout.observeForever(resultApiResponse -> {
                if(resultApiResponse.isSuccessful()) {
                    saveToken("", new AuthenFactory.CallBack() {
                        @Override
                        public void call() {
                            token.setValue(null);
                            status.setValue(Status.NOT_LOGIN);
                            user.setValue(null);
                        }
                    });
                }
            });
        });
    }

    public MutableLiveData<UserResult> getUser() {
        return user;
    }

    public MutableLiveData<String> getToken() {
        return token;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public void logout() {
       status.setValue(Status.LOGOUT);
    }

    public void saveToken(String token, CallBack callBack) {
        appExecutors.diskIO().execute(() -> {
            List<SettingEntity> settingEntities = settingDao.get("token");
            if (!settingEntities.isEmpty()) {
                settingEntity = settingEntities.get(0);
                settingEntity.setOption(token);
                settingDao.update(settingEntity);

            } else {
                settingEntity = new SettingEntity();
                settingEntity.setId(1);
                settingEntity.setName("token");
                settingEntity.setOption(token);
                settingDao.insert(settingEntity);
            }
            callBack.call();
        });
    }

    public enum Status {
        UNKNOWN,
        LOGIN,
        LOGOUT,
        NOT_LOGIN
    }

    public interface CallBack {
        public void call();
    }
}
