package com.norton.mvvmbase.repository.remote.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by norton on 11/04/2018.
 */

public class Result {
    @SerializedName("message")
    public final String message;

    @SerializedName("error")
    public final  Object error;

    @SerializedName("success")
    public final Object success;

    public Result(String message, Object error, Object success) {
        this.message = message;
        this.error = error;
        this.success = success;
    }
}
