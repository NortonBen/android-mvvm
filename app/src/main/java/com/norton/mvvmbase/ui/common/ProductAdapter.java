package com.norton.mvvmbase.ui.common;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.norton.mvvmbase.R;
import com.norton.mvvmbase.databinding.ProductItemBinding;
import com.norton.mvvmbase.repository.local.entity.ProductEntity;
import com.norton.mvvmbase.repository.remote.data.PruductAPI.ProductResult;
import com.norton.mvvmbase.utils.Objects;

/**
 * Created by norton on 11/04/2018.
 */

public class ProductAdapter extends DataBoundListAdapter<ProductEntity, ProductItemBinding> {


    private final OnClickProductItem onClickProductItem;
    private final DataBindingComponent dataBindingComponent;

    public ProductAdapter(DataBindingComponent dataBindingComponent, OnClickProductItem onClickProductItem) {
        this.onClickProductItem = onClickProductItem;
        this.dataBindingComponent = dataBindingComponent;
    }

    @Override
    protected ProductItemBinding createBinding(ViewGroup parent) {
        ProductItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.product_item, parent, false,
                    dataBindingComponent);
        binding.getRoot().setOnClickListener(v -> {
            ProductEntity product =  binding.getProduct();
            if (product != null && onClickProductItem != null) {
                onClickProductItem.onClick(product);
            }
        });
        return binding;
    }

    @Override
    protected void bind(ProductItemBinding binding, ProductEntity item) {
        binding.setProduct(item);
    }

    @Override
    protected boolean areItemsTheSame(ProductEntity oldItem, ProductEntity newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    protected boolean areContentsTheSame(ProductEntity oldItem, ProductEntity newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName())
                && Objects.equals(oldItem.getPrice(), newItem.getPrice())
                && Objects.equals(oldItem.getImage(), newItem.getImage())
                && Objects.equals(oldItem.getDetail(), newItem.getDetail());
    }


    public interface OnClickProductItem {
        public void onClick(ProductEntity productResult);
    }
}
