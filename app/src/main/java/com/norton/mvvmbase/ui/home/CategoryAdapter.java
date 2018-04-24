package com.norton.mvvmbase.ui.home;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.norton.mvvmbase.R;
import com.norton.mvvmbase.databinding.CategoryItemBinding;
import com.norton.mvvmbase.model.Category;
import com.norton.mvvmbase.repository.local.entity.CategoryEntity;
import com.norton.mvvmbase.ui.common.DataBoundListAdapter;
import com.norton.mvvmbase.utils.Objects;


/**
 * Created by norton on 11/04/2018.
 */

public class CategoryAdapter  extends DataBoundListAdapter<CategoryEntity, CategoryItemBinding> {

    private final DataBindingComponent dataBindingComponent;
    private final OnClickCategoryItem onClickCategoryItem;


    public CategoryAdapter(DataBindingComponent dataBindingComponent, OnClickCategoryItem onClickCategoryItem) {
        this.dataBindingComponent = dataBindingComponent;
        this.onClickCategoryItem = onClickCategoryItem;
    }


    @Override
    protected CategoryItemBinding createBinding(ViewGroup parent) {
        CategoryItemBinding itemBinding =  DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.category_item, parent, false,
                        dataBindingComponent);
        itemBinding.getRoot().setOnClickListener(v -> {
            CategoryEntity categoryResult =  itemBinding.getCategory();
            if (categoryResult != null && onClickCategoryItem != null) {
                onClickCategoryItem.onClick(categoryResult);
            }
        });
        return itemBinding;
    }

    @Override
    protected void bind(CategoryItemBinding binding, CategoryEntity item) {
        binding.setCategory(item);
    }

    @Override
    protected boolean areItemsTheSame(CategoryEntity oldItem, CategoryEntity newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    protected boolean areContentsTheSame(CategoryEntity oldItem, CategoryEntity newItem) {
        return Objects.equals(oldItem.getName(), newItem.getName()) && Objects.equals(oldItem.getImage(), newItem.getImage());
    }


    public interface OnClickCategoryItem {
        public void onClick(CategoryEntity categoryResult);
    }
}
