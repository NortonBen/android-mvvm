package com.norton.mvvmbase.ui.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norton.mvvmbase.R;
import com.norton.mvvmbase.binding.FragmentDataBindingComponent;
import com.norton.mvvmbase.dependency.Injectable;
import com.norton.mvvmbase.databinding.HomeFragmentBinding;
import com.norton.mvvmbase.model.Category;
import com.norton.mvvmbase.repository.local.entity.CategoryEntity;
import com.norton.mvvmbase.repository.remote.Status;
import com.norton.mvvmbase.ui.common.NavigationController;
import com.norton.mvvmbase.utils.AutoClearedValue;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


public class HomeFragment extends Fragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    private HomeViewModel homeViewModel;
    AutoClearedValue<CategoryAdapter> adapter;

    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<HomeFragmentBinding> binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        HomeFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment,
                container, false, dataBindingComponent);
        dataBinding.setRetryCallback(() -> homeViewModel.retry());

        dataBinding.categoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.categoryList.setHasFixedSize(true);

        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);

        CategoryAdapter adapter = new CategoryAdapter(dataBindingComponent, new CategoryAdapter.OnClickCategoryItem() {
            @Override
            public void onClick(CategoryEntity categoryResult) {
                navigationController.toCategiryID(categoryResult.getId());
            }
        });
        binding.get().categoryList.setAdapter(adapter);
        this.adapter = new AutoClearedValue<>(this, adapter);

        homeViewModel.getCategoriesResultLiveData().observe(this, categories -> {
            if (categories.data != null) {
                 HomeFragment.this.adapter.get().replace(categories.data);
            } else {
                HomeFragment.this.adapter.get().replace(Collections.emptyList());
            }
            binding.get().setResource(categories);
            binding.get().executePendingBindings();
        });
    }
}
