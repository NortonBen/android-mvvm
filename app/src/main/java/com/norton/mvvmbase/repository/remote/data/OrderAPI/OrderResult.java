package com.norton.mvvmbase.repository.remote.data.OrderAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResult {

    @SerializedName("Detail")
    public final List<Detail> Details;

    @SerializedName("date")
    public final String date;

    @SerializedName("status")
    public final int status;

    public OrderResult(int product_id, int quantity, List<Detail> details, String date, int status) {
        Details = details;
        this.date = date;
        this.status = status;
    }

    public class Detail {
        @SerializedName("product_id")
        public final int product_id;
        @SerializedName("quantity")
        public final int quantity;

        public Detail(int product_id, int quantity) {
            this.product_id = product_id;
            this.quantity = quantity;
        }
    }

    public List<Detail> getDetails() {
        return Details;
    }

    public String getDate() {
        return date;
    }
}
