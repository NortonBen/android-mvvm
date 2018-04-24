package com.norton.mvvmbase.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.norton.mvvmbase.AppExecutors;
import com.norton.mvvmbase.model.Category;
import com.norton.mvvmbase.repository.local.dao.CategoryDao;
import com.norton.mvvmbase.repository.local.entity.CategoryEntity;
import com.norton.mvvmbase.repository.remote.ApiResponse;
import com.norton.mvvmbase.repository.remote.NetworkBoundResource;
import com.norton.mvvmbase.repository.remote.Resource;
import com.norton.mvvmbase.repository.remote.api.CategoryAPI;
import com.norton.mvvmbase.repository.remote.data.CategoryAPI.CategoryResult;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by norton on 11/04/2018.
 */
@Singleton
public class CategoryRepository {

    private final CategoryAPI categoryAPI;

    private final AppExecutors appExecutors;

    private final CategoryDao categoryDao;

    @Inject
    public CategoryRepository(CategoryAPI categoryAPI, AppExecutors appExecutors, CategoryDao categoryDao) {
        this.categoryAPI = categoryAPI;
        this.appExecutors = appExecutors;
        this.categoryDao = categoryDao;
    }

    public LiveData<Resource<List<CategoryEntity>>> getListAll() {
        return new NetworkBoundResource<List<CategoryEntity>, List<CategoryResult>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<CategoryResult> list) {
                appExecutors.diskIO().execute(() -> {
                    for(CategoryResult item : list) {
                        CategoryEntity categoryEntity = item.loadImage().toCategoryEntity();
                        categoryDao.insertAll(categoryEntity);
                    }
                });
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CategoryEntity> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CategoryEntity>> loadFromDb() {
                return categoryDao.getAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<CategoryResult>>> createCall() {
                return categoryAPI.getAll();
            }
        }.asLiveData();

    }
}
