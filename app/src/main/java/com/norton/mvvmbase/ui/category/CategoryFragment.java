package com.norton.mvvmbase.ui.category;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norton.mvvmbase.R;
import com.norton.mvvmbase.binding.FragmentDataBindingComponent;
import com.norton.mvvmbase.databinding.CategoryFragmentBinding;
import com.norton.mvvmbase.dependency.Injectable;
import com.norton.mvvmbase.repository.local.entity.ProductEntity;
import com.norton.mvvmbase.repository.remote.Status;
import com.norton.mvvmbase.ui.common.NavigationController;
import com.norton.mvvmbase.ui.common.ProductAdapter;
import com.norton.mvvmbase.utils.AutoClearedValue;

import java.util.Collections;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment  implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    CategoryViewModel categoryViewModel;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<CategoryFragmentBinding> binding;

    AutoClearedValue<ProductAdapter> adapter;

    private int _id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CategoryFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.category_fragment,
                container, false, dataBindingComponent);
        dataBinding.setRetryCallback(() -> categoryViewModel.retry());

        dataBinding.productList.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.productList.setHasFixedSize(true);


        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        categoryViewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryViewModel.class);

        ProductAdapter adapter = new ProductAdapter(dataBindingComponent, new ProductAdapter.OnClickProductItem() {
            @Override
            public void onClick(ProductEntity product) {
                navigationController.toProductID(product.getId());
            }
        });

        binding.get().productList.setAdapter(adapter);
        this.adapter = new AutoClearedValue<>(this, adapter);

        categoryViewModel.getProductsResultLiveData().observe(this, productlist -> {
            if (productlist.data != null) {
                CategoryFragment.this.adapter.get().replace(productlist.data);
            } else {
                CategoryFragment.this.adapter.get().replace(null);
            }

            binding.get().setResource(productlist);
            binding.get().executePendingBindings();
        });
        categoryViewModel.loadById(_id);
    }

    public void of(int id) {
        _id = id;
        if (categoryViewModel != null) {
            categoryViewModel.loadById(id);
        }
    }
}
