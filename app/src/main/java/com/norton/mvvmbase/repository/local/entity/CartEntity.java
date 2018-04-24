package com.norton.mvvmbase.repository.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.norton.mvvmbase.model.Cart;

/**
 * Created by norton on 11/04/2018.
 */

@Entity(tableName = "carts")
public class CartEntity implements Cart {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "product")
    private int product;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public CartEntity(int id, int product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public CartEntity(int product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
