package com.norton.mvvmbase.ui.product;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norton.mvvmbase.R;
import com.norton.mvvmbase.binding.FragmentDataBindingComponent;
import com.norton.mvvmbase.databinding.DetailProductFragmentBinding;
import com.norton.mvvmbase.dependency.Injectable;
import com.norton.mvvmbase.utils.AutoClearedValue;

import android.databinding.DataBindingComponent;

import javax.inject.Inject;

public class ProductFragment extends Fragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ProductViewModel productViewModel;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<DetailProductFragmentBinding> binding;

   int _id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DetailProductFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.detail_product_fragment,
                container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        productViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductViewModel.class);
        productViewModel.getProductResultLiveData().observe(this, productResult -> {
            if (productResult != null) {
                binding.get().setProduct(productResult);
            }
        });
        productViewModel.getIdProduct().setValue(_id);
    }


    private  void listener() {
        binding.get().addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void of(int id) {
        _id = id;
        if (productViewModel != null && productViewModel.getIdProduct() != null) {
            productViewModel.getIdProduct().setValue(id);
        }
    }
}
