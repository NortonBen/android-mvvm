package com.norton.mvvmbase.ui.category;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;


import com.norton.mvvmbase.repository.ProductRepository;
import com.norton.mvvmbase.repository.local.entity.ProductEntity;
import com.norton.mvvmbase.repository.remote.Resource;
import com.norton.mvvmbase.repository.remote.data.CategoryAPI.CategoryResult;
import com.norton.mvvmbase.repository.remote.data.PruductAPI.ProductResult;
import com.norton.mvvmbase.ui.common.RetryCallback;
import com.norton.mvvmbase.utils.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by norton on 11/04/2018.
 */

public class CategoryViewModel extends ViewModel implements RetryCallback {

    private final MutableLiveData<Integer> idCategory;

    private final ProductRepository productRepository;

    private final LiveData<Resource<List<ProductEntity>>> productsResultLiveData;

    @Inject
    public CategoryViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;
        idCategory =  new MutableLiveData<>();
        this.productsResultLiveData = Transformations.switchMap(idCategory, (input) ->{
            if (input == 0) {
                return AbsentLiveData.create();
            }
            return productRepository.getListProductByCategoryId(input);
        });
    }

    public LiveData<Resource<List<ProductEntity>>> getProductsResultLiveData() {
        return productsResultLiveData;
    }

    public MutableLiveData<Integer> getIdCategory() {
        return idCategory;
    }

    public void loadById(int id) {
        idCategory.setValue(id);
    }

    @Override
    public void retry() {
        int id = idCategory.getValue();
        if (id != 0) {
            idCategory.setValue(id);
        }
    }
}
