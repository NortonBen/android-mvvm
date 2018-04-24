package com.norton.mvvmbase.repository.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.norton.mvvmbase.repository.local.entity.CategoryEntity;
import com.norton.mvvmbase.repository.local.entity.ProductEntity;

import java.util.List;

/**
 * Created by norton on 11/04/2018.
 */

@Dao
public interface  ProductDao {

    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> getAll();

    @Query("SELECT * FROM products WHERE id = :productId")
    LiveData<ProductEntity> getById(int productId);

    @Query("SELECT * FROM products WHERE category_id = :category")
    LiveData<List<ProductEntity>> getByCategoryId(int category);

    @Query("SELECT * FROM products WHERE id IN (:productIds)")
    LiveData<List<ProductEntity>> loadAllByIds(int[] productIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductEntity... products);

    @Update
    void update(ProductEntity... products);

    @Delete
    void delete(ProductEntity products);

}
