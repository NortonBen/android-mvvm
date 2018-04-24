package com.norton.mvvmbase.repository.local.data;

import android.arch.persistence.room.ColumnInfo;

import com.norton.mvvmbase.repository.local.entity.CartEntity;

public class CartJoinProduct extends CartEntity {

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @ColumnInfo(name = "price")
    private  int price;

    public CartJoinProduct(int id, int product, int quantity, String name, byte[] image, int price) {
        super(id, product, quantity);
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
