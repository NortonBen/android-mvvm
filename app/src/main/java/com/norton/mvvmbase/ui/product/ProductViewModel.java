package com.norton.mvvmbase.ui.product;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.norton.mvvmbase.repository.ProductRepository;
import com.norton.mvvmbase.repository.local.entity.ProductEntity;
import com.norton.mvvmbase.repository.remote.data.PruductAPI.ProductResult;
import com.norton.mvvmbase.ui.common.RetryCallback;
import com.norton.mvvmbase.utils.AbsentLiveData;

import javax.inject.Inject;

public class ProductViewModel extends ViewModel implements RetryCallback {

    private final ProductRepository productRepository;
    private final MutableLiveData<Integer> idProduct;
    private final LiveData<ProductEntity> productResultLiveData;

    @Inject
    public ProductViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;

        this.idProduct = new MutableLiveData<>();
        this.productResultLiveData = Transformations.switchMap(idProduct, input -> {
            if (input == 0) {
                return AbsentLiveData.create();
            }
            return productRepository.getProductById(input);
        });

    }

    public LiveData<ProductEntity> getProductResultLiveData() {
        return productResultLiveData;
    }


    public MutableLiveData<Integer> getIdProduct() {
        return idProduct;
    }

    @Override
    public void retry() {
        int id = idProduct.getValue();
        if (id != 0) {
            idProduct.setValue(id);
        }
    }
}
