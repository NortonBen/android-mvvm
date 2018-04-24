package com.norton.mvvmbase.repository.remote.data.OrderAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderProduct {

    @SerializedName("note")
    public final String note;

    @SerializedName("products")
    public final List<Order> products;

    public OrderProduct(String note, List<Order> products) {
        this.note = note;
        this.products = products;
    }
}
