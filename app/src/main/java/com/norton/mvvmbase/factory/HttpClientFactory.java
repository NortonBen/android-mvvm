package com.norton.mvvmbase.factory;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClientFactory {

    private final AuthenFactory authenFactory;

    @Inject
    public HttpClientFactory(AuthenFactory authenFactory) {
        this.authenFactory = authenFactory;
    }

    public  OkHttpClient getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder builder = original.newBuilder();

                if(authenFactory.getToken().getValue() != null ) {
                    builder.header("Authorization", "Bearer "+authenFactory.getToken().getValue());
                }
                Request request =  builder.method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }
}
