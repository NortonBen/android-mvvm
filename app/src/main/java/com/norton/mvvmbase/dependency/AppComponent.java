package com.norton.mvvmbase.dependency;

import com.norton.mvvmbase.BaseApplication;
import com.norton.mvvmbase.dependency.Module.ActivityModule;
import com.norton.mvvmbase.dependency.Module.AppModule;
import com.norton.mvvmbase.dependency.Module.DaoModule;
import com.norton.mvvmbase.dependency.Module.ServiceModule;

/**
 * Created by norton on 27/03/2018.
 */


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityModule.class,
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
    void inject(BaseApplication application);
}