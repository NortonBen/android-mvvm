package com.norton.mvvmbase.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.norton.mvvmbase.AppExecutors;
import com.norton.mvvmbase.repository.local.dao.CartDao;
import com.norton.mvvmbase.repository.local.data.CartJoinProduct;
import com.norton.mvvmbase.repository.local.entity.CartEntity;
import com.norton.mvvmbase.repository.remote.ApiResponse;
import com.norton.mvvmbase.repository.remote.NetworkBoundResource;
import com.norton.mvvmbase.repository.remote.NetworkOnlyBoundResource;
import com.norton.mvvmbase.repository.remote.Resource;
import com.norton.mvvmbase.repository.remote.api.OrderAPI;
import com.norton.mvvmbase.repository.remote.data.OrderAPI.Order;
import com.norton.mvvmbase.repository.remote.data.OrderAPI.OrderResult;
import com.norton.mvvmbase.repository.remote.data.Result;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CartRepository {

    private final CartDao cartDao;
    private final OrderAPI orderAPI;
    private final AppExecutors appExecutors;

    @Inject
    public CartRepository(CartDao cartDao, OrderAPI orderAPI, AppExecutors appExecutors) {
        this.cartDao = cartDao;
        this.orderAPI = orderAPI;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<Result>> orderCart(Order order) {
        return new NetworkOnlyBoundResource<Result>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull Result item) {
                appExecutors.diskIO().execute(() -> {
                    LiveData<List<CartEntity>> entityLiveData = cartDao.getAll();
                    entityLiveData.observeForever(list -> {
                        appExecutors.mainThread().execute(() -> {
                           for(CartEntity entity: list) {
                               cartDao.delete(entity);
                           }
                        });
                    });
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Result>> createCall() {
                return orderAPI.order(order);
            }
        }.asLiveData();
    }

    public LiveData<List<CartJoinProduct>>  getListCart() {
        MutableLiveData<List<CartJoinProduct>> liveData = new MutableLiveData<>();
        appExecutors.diskIO().execute(() -> {
            LiveData<List<CartJoinProduct>> live = new MutableLiveData<>();
            live.observeForever(cartJoinProducts -> {
                liveData.setValue(cartJoinProducts);
            });
        });
        return liveData;
    }

    public LiveData<Resource<List<OrderResult>>> getListOrder() {
        return new NetworkOnlyBoundResource<List<OrderResult>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<OrderResult> item) {

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<OrderResult>>> createCall() {
                return orderAPI.getAllOrder();
            }
        }.asLiveData();
    }


    public void addToCart(int quantity, int product) {
        appExecutors.diskIO().execute(() -> {
            LiveData<CartEntity> entityLiveData = cartDao.getCartToProduct(product);
            entityLiveData.observeForever(cartEntity -> {
                appExecutors.mainThread().execute(() -> {
                   if (cartEntity == null) {
                       CartEntity cart = new CartEntity(product, quantity);
                       cartDao.insert(cart);
                   } else {
                       cartEntity.setQuantity(cartEntity.getQuantity() + quantity);
                       cartDao.update(cartEntity);
                   }
                });
            });
        });
    }

    public void updateCart(CartEntity cartEntity, int quantity) {
        appExecutors.diskIO().execute(() -> {
            cartEntity.setQuantity(quantity);
           cartDao.update(cartEntity);
        });
    }
}
