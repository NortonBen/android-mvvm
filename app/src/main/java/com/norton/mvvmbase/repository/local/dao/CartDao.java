package com.norton.mvvmbase.repository.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.norton.mvvmbase.repository.local.data.CartJoinProduct;
import com.norton.mvvmbase.repository.local.entity.CartEntity;
import com.norton.mvvmbase.repository.local.entity.CategoryEntity;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT c.id, c.quantity, c.product, p.image, p.name, p.price FROM carts as c JOIN products as p on c.product == p.id")
    LiveData<List<CartJoinProduct>> getWithProductAll();

    @Query("SELECT * FROM carts")
    LiveData<List<CartEntity>> getAll();

    @Query("SELECT * FROM carts WHERE id IN (:cartIds)")
    LiveData<CartEntity> loadAllByIds(int[] cartIds);

    @Query("SELECT * FROM carts WHERE product = product")
    LiveData<CartEntity> getCartToProduct(int product);

    @Insert
    void insert(CartEntity... carts);

    @Update
    void update(CartEntity... carts);

    @Delete
    void delete(CartEntity cart);
}
