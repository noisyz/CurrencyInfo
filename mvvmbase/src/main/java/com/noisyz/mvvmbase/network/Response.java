package com.noisyz.mvvmbase.network;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private String message;
    private T data;
    private int status;

    public boolean isSuccess() {
        return this.status == 200;
    }

    public String getMessage() {
        return this.message;
    }

    public T getResult() {
        return this.data;
    }
}
