package com.norton.mvvmbase.repository.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
public class Resource<T> {

    @NonNull
    public final Status status;

    @NonNull
    public final int code;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public Resource(@NonNull Status status, @Nullable T data,@Nullable int code, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static <T> Resource<T> success(@Nullable T data,@Nullable int code) {
        return new Resource<>(Status.SUCCESS, data, code, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data,@Nullable int code) {
        return new Resource<>(Status.ERROR, data, code, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, 0, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;

        if (status != resource.status) {
            return false;
        }
        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}