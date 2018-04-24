package com.norton.mvvmbase.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.norton.mvvmbase.AppExecutors;
import com.norton.mvvmbase.BaseApplication;
import com.norton.mvvmbase.repository.local.dao.ProductDao;
import com.norton.mvvmbase.repository.local.entity.ProductEntity;
import com.norton.mvvmbase.repository.remote.ApiResponse;
import com.norton.mvvmbase.repository.remote.NetworkBoundResource;
import com.norton.mvvmbase.repository.remote.Resource;
import com.norton.mvvmbase.repository.remote.api.ProductAPI;
import com.norton.mvvmbase.repository.remote.data.PruductAPI.ProductResult;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by norton on 11/04/2018.
 */

@Singleton
public class ProductRepository {

    private final ProductAPI productAPI;

    private final AppExecutors appExecutors;

    private final ProductDao productDao;


    @Inject
    public ProductRepository(ProductAPI productAPI, AppExecutors appExecutors, ProductDao productDao) {
        this.productAPI = productAPI;
        this.appExecutors = appExecutors;
        this.productDao = productDao;
    }

    public LiveData<Resource<List<ProductEntity>>> getListProductByCategoryId(int id) {
        return new NetworkBoundResource<List<ProductEntity>, List<ProductResult>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<ProductResult> list) {
                appExecutors.diskIO().execute(() -> {
                    for(ProductResult item  :list) {
                        ProductEntity productEntity = item.loadImage().toProductEntity();
                        productDao.insert(productEntity);
                    }
                });
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ProductEntity> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<ProductEntity>> loadFromDb() {
                return productDao.getByCategoryId(id);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<ProductResult>>> createCall() {
                return productAPI.getProductsByCategoryId(id);
            }
        }.asLiveData();
    }

    public  LiveData<ProductEntity> getProductById(int id) {
        MediatorLiveData<ProductEntity> productEntity = new MediatorLiveData<>();
        appExecutors.diskIO().execute(()-> {
            LiveData<ProductEntity> byId = productDao.getById(id);
            productEntity.addSource(byId, product -> {
                productEntity.setValue(product);
            });
        });
        return productEntity;
    }

}
