package com.norton.mvvmbase.dependency.Module;

import android.app.Application;

import com.norton.mvvmbase.AppExecutors;
import com.norton.mvvmbase.BaseApplication;
import com.norton.mvvmbase.Constants;
import com.norton.mvvmbase.MainActivity;
import com.norton.mvvmbase.factory.AuthenFactory;
import com.norton.mvvmbase.factory.HistoryFactory;
import com.norton.mvvmbase.factory.HttpClientFactory;
import com.norton.mvvmbase.factory.LiveDataCallAdapterFactory;
import com.norton.mvvmbase.model.Product;
import com.norton.mvvmbase.repository.ProductRepository;
import com.norton.mvvmbase.repository.local.AppDatabase;
import com.norton.mvvmbase.repository.local.dao.ProductDao;
import com.norton.mvvmbase.repository.local.dao.SettingDao;
import com.norton.mvvmbase.repository.remote.api.LoginAPI;
import com.norton.mvvmbase.repository.remote.api.ProductAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by norton on 27/03/2018.
 */

@Module(includes = {ViewModelModule.class, DaoModule.class, ServiceModule.class})
public class AppModule {

    @Singleton @Provides
    Retrofit getRetrofit(HttpClientFactory httpClientFactory) {
        return new Retrofit.Builder()
                .baseUrl(Constants.SERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(httpClientFactory.getClient())
                .build();
    }

    @Singleton @Provides
    AppExecutors getAppExecutors() {
        return new AppExecutors();
    }


    @Singleton @Provides
    HistoryFactory getHistoryFactory() {
        return new HistoryFactory();
    }


    @Singleton @Provides
    AppDatabase getAppDatabase(Application application, AppExecutors executors) {
        return AppDatabase.getInstance(application, executors);
    }

    @Provides
    HttpClientFactory getHttpClientFactory(AuthenFactory authenFactory) {
        return new HttpClientFactory(authenFactory);
    }

    @Singleton
    @Provides
    AuthenFactory getAuthenFactory(SettingDao settingDao, LoginAPI loginAPI, AppExecutors appExecutors) {
        return new AuthenFactory(settingDao, loginAPI, appExecutors);
    }
}
