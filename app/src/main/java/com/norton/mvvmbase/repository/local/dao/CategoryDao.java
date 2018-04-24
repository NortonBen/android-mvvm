package com.norton.mvvmbase.repository.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.norton.mvvmbase.model.Category;
import com.norton.mvvmbase.repository.local.entity.CategoryEntity;

import java.util.List;

/**
 * Created by norton on 11/04/2018.
 */
@Dao
public interface CategoryDao {

    @Query("SELECT * FROM categories")
    LiveData<List<CategoryEntity>> getAll();

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    CategoryEntity getById(int categoryId);

    @Query("SELECT * FROM categories WHERE id IN (:categoryIds)")
    LiveData<List<CategoryEntity>> loadAllByIds(int[] categoryIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CategoryEntity... categories);

    @Update
    void update(CategoryEntity... categories);

    @Delete
    void delete(CategoryEntity categories);
}
