package com.kobus.pitzer.updatedweather.repository.resource;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kobus.pitzer.updatedweather.repository.constant.Status;


/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    @Nullable
    public final Integer code;


    public final Boolean dataLimited;

    public boolean hasMore;

    public boolean hasOlder;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message, boolean hasMore, boolean hasOlder, boolean dataLimited) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.hasMore = hasMore;
        this.hasOlder = hasOlder;
        this.dataLimited = dataLimited;
        this.code = null;
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message, boolean hasMore, boolean hasOlder) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.hasMore = hasMore;
        this.hasOlder = hasOlder;
        this.dataLimited = false;
        this.code = null;
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message, boolean hasMore) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.hasMore = hasMore;
        this.hasOlder = false;
        this.dataLimited = false;
        this.code = null;
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.hasMore = false;
        this.hasOlder = false;
        this.dataLimited = false;
        this.code = null;
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message, Integer code) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.hasMore = false;
        this.hasOlder = false;
        this.dataLimited = false;
        this.code = code;
    }

    public static <T> Resource<T> success(@Nullable T data, boolean hasMore, boolean hasOlder, boolean dataLimited) {
        return new Resource<>(Status.SUCCESS, data, null, hasMore, hasOlder, dataLimited);
    }

    public static <T> Resource<T> success(@Nullable T data, boolean hasMore, boolean hasOlder) {
        return new Resource<>(Status.SUCCESS, data, null, hasMore, hasOlder);
    }

    public static <T> Resource<T> success(@Nullable T data, boolean hasMore) {
        return new Resource<>(Status.SUCCESS, data, null, hasMore);
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> cache(@Nullable T data) {
        return new Resource<>(Status.CACHE, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data, Integer code) {
        return new Resource<>(Status.ERROR, data, msg, code);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
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
