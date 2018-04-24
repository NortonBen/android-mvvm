package com.norton.mvvmbase.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.norton.mvvmbase.model.Category;
import com.norton.mvvmbase.repository.CategoryRepository;
import com.norton.mvvmbase.repository.local.entity.CategoryEntity;
import com.norton.mvvmbase.repository.remote.Resource;
import com.norton.mvvmbase.repository.remote.data.CategoryAPI.CategoryResult;
import com.norton.mvvmbase.ui.common.RetryCallback;
import com.norton.mvvmbase.utils.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by norton on 08/04/2018.
 */

public class HomeViewModel extends ViewModel implements RetryCallback {

    private final CategoryRepository categoryRepository;

    private final LiveData<Resource<List<CategoryEntity>>>  categoriesResultLiveData;

   private final MutableLiveData controll = new MutableLiveData();

    @Inject
    public HomeViewModel(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        categoriesResultLiveData = Transformations.switchMap(controll, input -> {
            return categoryRepository.getListAll();
        });
        controll.setValue(null);
    }

    public LiveData<Resource<List<CategoryEntity>>> getCategoriesResultLiveData() {
        return categoriesResultLiveData;
    }

    @Override
    public void retry() {
        controll.setValue(null);
    }
}
