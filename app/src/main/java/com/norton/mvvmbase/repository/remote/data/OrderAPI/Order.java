package com.norton.mvvmbase.repository.remote.data.OrderAPI;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("email")
    public final int product_id;
    @SerializedName("email")
    public final int quantity;

    public Order(int product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }
}
