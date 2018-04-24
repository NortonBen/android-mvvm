package com.norton.mvvmbase.dependency.Module;

import com.norton.mvvmbase.Constants;
import com.norton.mvvmbase.factory.LiveDataCallAdapterFactory;
import com.norton.mvvmbase.repository.LoginRepository;
import com.norton.mvvmbase.repository.remote.api.CategoryAPI;
import com.norton.mvvmbase.repository.remote.api.LoginAPI;
import com.norton.mvvmbase.repository.remote.api.OrderAPI;
import com.norton.mvvmbase.repository.remote.api.ProductAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by norton on 28/03/2018.
 */

@Module
public class ServiceModule {

    @Singleton
    @Provides
    LoginAPI provideLoginAPI() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(Constants.SERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory()).client(client)
                .build();
        return retrofit.create(LoginAPI.class);
    }

    @Singleton
    @Provides
    CategoryAPI provideCategoryAPI(Retrofit retrofit) {
        return retrofit.create(CategoryAPI.class);
    }

    @Singleton
    @Provides
    ProductAPI provideProductAPI(Retrofit retrofit) {
        return retrofit.create(ProductAPI.class);
    }

    @Singleton
    @Provides
    OrderAPI provideOrderAPI(Retrofit retrofit) {
        return retrofit.create(OrderAPI.class);
    }
}
