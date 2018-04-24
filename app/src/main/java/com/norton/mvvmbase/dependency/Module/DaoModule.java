package com.norton.mvvmbase.dependency.Module;

import com.norton.mvvmbase.repository.local.AppDatabase;
import com.norton.mvvmbase.repository.local.dao.CartDao;
import com.norton.mvvmbase.repository.local.dao.CategoryDao;
import com.norton.mvvmbase.repository.local.dao.ProductDao;
import com.norton.mvvmbase.repository.local.dao.SettingDao;
import com.norton.mvvmbase.repository.local.dao.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by norton on 10/04/2018.
 */
@Module
public class DaoModule {

    @Singleton
    @Provides
    SettingDao provideSettingDao(AppDatabase db) {
        return db.settingDao();
    }

    @Singleton
    @Provides
    UserDao provideUserDao(AppDatabase db) {
        return db.userDao();
    }

    @Singleton
    @Provides
    CategoryDao provideCategoryDao(AppDatabase db) {
        return db.categoryDao();
    }

    @Singleton
    @Provides
    ProductDao provideProductDao(AppDatabase db) {
        return db.productDao();
    }

    @Singleton
    @Provides
    CartDao provideCartDao(AppDatabase db) {
        return db.cartDao();
    }
}
